package org.apache.commons.math4.geometry.euclidean.threed;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Line;
import org.apache.commons.math4.geometry.euclidean.twod.PolygonsSet;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.util.FastMath;

public class OutlineExtractor {
    private final Vector3D u;
    private final Vector3D v;
    private final Vector3D w;

    private class BoundaryProjector implements BSPTreeVisitor<Euclidean3D> {
        private PolygonsSet projected;
        private final double tolerance;

        public BoundaryProjector(double tolerance) {
            this.projected = new PolygonsSet(new BSPTree(Boolean.FALSE), tolerance);
            this.tolerance = tolerance;
        }

        public Order visitOrder(BSPTree<Euclidean3D> bSPTree) {
            return Order.MINUS_SUB_PLUS;
        }

        public void visitInternalNode(BSPTree<Euclidean3D> node) {
            BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute) node.getAttribute();
            if (attribute.getPlusOutside() != null) {
                addContribution(attribute.getPlusOutside(), false);
            }
            if (attribute.getPlusInside() != null) {
                addContribution(attribute.getPlusInside(), true);
            }
        }

        public void visitLeafNode(BSPTree<Euclidean3D> bSPTree) {
        }

        private void addContribution(SubHyperplane<Euclidean3D> facet, boolean reversed) {
            AbstractSubHyperplane<Euclidean3D, Euclidean2D> absFacet = (AbstractSubHyperplane) facet;
            Plane plane = (Plane) facet.getHyperplane();
            double scal = plane.getNormal().dotProduct(OutlineExtractor.this.w);
            if (FastMath.abs(scal) > 0.001d) {
                Vector2D[] loop;
                Vector2D[][] vertices = ((PolygonsSet) absFacet.getRemainingRegion()).getVertices();
                if (((scal < 0.0d ? 1 : 0) ^ reversed) != 0) {
                    Vector2D[][] newVertices = new Vector2D[vertices.length][];
                    for (int i = 0; i < vertices.length; i++) {
                        loop = vertices[i];
                        Vector2D[] newLoop = new Vector2D[loop.length];
                        int j;
                        if (loop[0] == null) {
                            newLoop[0] = null;
                            for (j = 1; j < loop.length; j++) {
                                newLoop[j] = loop[loop.length - j];
                            }
                        } else {
                            for (j = 0; j < loop.length; j++) {
                                newLoop[j] = loop[loop.length - (j + 1)];
                            }
                        }
                        newVertices[i] = newLoop;
                    }
                    vertices = newVertices;
                }
                ArrayList<SubHyperplane<Euclidean2D>> edges = new ArrayList();
                for (Vector2D[] loop2 : vertices) {
                    boolean closed = loop2[0] != null;
                    int previous = closed ? loop2.length - 1 : 1;
                    Vector3D previous3D = plane.toSpace(loop2[previous]);
                    int current = (previous + 1) % loop2.length;
                    Vector2D pPoint = new Vector2D(previous3D.dotProduct(OutlineExtractor.this.u), previous3D.dotProduct(OutlineExtractor.this.v));
                    while (current < loop2.length) {
                        Vector3D current3D = plane.toSpace(loop2[current]);
                        Vector2D cPoint = new Vector2D(current3D.dotProduct(OutlineExtractor.this.u), current3D.dotProduct(OutlineExtractor.this.v));
                        Line line = new Line(pPoint, cPoint, this.tolerance);
                        SubHyperplane<Euclidean2D> edge = line.wholeHyperplane();
                        if (closed || previous != 1) {
                            edge = edge.split(new Line(pPoint, line.getAngle() + Angle2D.M_PI_2, this.tolerance)).getPlus();
                        }
                        if (closed || current != loop2.length - 1) {
                            edge = edge.split(new Line(cPoint, line.getAngle() + Angle2D.M_PI_2, this.tolerance)).getMinus();
                        }
                        edges.add(edge);
                        previous = current;
                        previous3D = current3D;
                        pPoint = cPoint;
                        current++;
                    }
                }
                this.projected = (PolygonsSet) new RegionFactory().union(this.projected, new PolygonsSet((Collection) edges, this.tolerance));
            }
        }

        public PolygonsSet getProjected() {
            return this.projected;
        }
    }

    public OutlineExtractor(Vector3D u, Vector3D v) {
        this.u = u;
        this.v = v;
        this.w = Vector3D.crossProduct(u, v);
    }

    public Vector2D[][] getOutline(PolyhedronsSet polyhedronsSet) {
        BoundaryProjector projector = new BoundaryProjector(polyhedronsSet.getTolerance());
        polyhedronsSet.getTree(true).visit(projector);
        Vector2D[][] outline = projector.getProjected().getVertices();
        for (int i = 0; i < outline.length; i++) {
            Vector2D[] rawLoop = outline[i];
            int end = rawLoop.length;
            int j = 0;
            while (j < end) {
                if (pointIsBetween(rawLoop, end, j)) {
                    for (int k = j; k < end - 1; k++) {
                        rawLoop[k] = rawLoop[k + 1];
                    }
                    end--;
                } else {
                    j++;
                }
            }
            if (end != rawLoop.length) {
                outline[i] = new Vector2D[end];
                System.arraycopy(rawLoop, 0, outline[i], 0, end);
            }
        }
        return outline;
    }

    private boolean pointIsBetween(Vector2D[] loop, int n, int i) {
        Vector2D previous = loop[((i + n) - 1) % n];
        Vector2D current = loop[i];
        Vector2D next = loop[(i + 1) % n];
        double dx1 = current.getX() - previous.getX();
        double dy1 = current.getY() - previous.getY();
        double dx2 = next.getX() - current.getX();
        double dy2 = next.getY() - current.getY();
        return FastMath.abs((dx1 * dy2) - (dx2 * dy1)) <= AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY * FastMath.sqrt(((dx1 * dx1) + (dy1 * dy1)) * ((dx2 * dx2) + (dy2 * dy2))) && (dx1 * dx2) + (dy1 * dy2) >= 0.0d;
    }
}
