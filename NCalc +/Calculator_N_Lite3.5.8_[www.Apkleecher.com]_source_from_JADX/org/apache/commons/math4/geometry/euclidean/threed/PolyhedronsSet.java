package org.apache.commons.math4.geometry.euclidean.threed;

import java.awt.geom.AffineTransform;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Line;
import org.apache.commons.math4.geometry.euclidean.twod.PolygonsSet;
import org.apache.commons.math4.geometry.euclidean.twod.SubLine;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.partitioning.AbstractRegion;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.geometry.partitioning.BoundaryAttribute;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Region.Location;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Transform;
import org.apache.commons.math4.util.FastMath;

public class PolyhedronsSet extends AbstractRegion<Euclidean3D, Euclidean2D> {

    private class FacetsContributionVisitor implements BSPTreeVisitor<Euclidean3D> {
        public FacetsContributionVisitor() {
            PolyhedronsSet.this.setSize(0.0d);
            PolyhedronsSet.this.setBarycenter(new Vector3D(0.0d, 0.0d, 0.0d));
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
            Region<Euclidean2D> polygon = ((SubPlane) facet).getRemainingRegion();
            double area = polygon.getSize();
            if (Double.isInfinite(area)) {
                PolyhedronsSet.this.setSize(Double.POSITIVE_INFINITY);
                PolyhedronsSet.this.setBarycenter(Vector3D.NaN);
                return;
            }
            Plane plane = (Plane) facet.getHyperplane();
            Vector3D facetB = plane.toSpace(polygon.getBarycenter());
            double scaled = area * facetB.dotProduct(plane.getNormal());
            if (reversed) {
                scaled = -scaled;
            }
            PolyhedronsSet.this.setSize(PolyhedronsSet.this.getSize() + scaled);
            PolyhedronsSet.this.setBarycenter(new Vector3D(1.0d, (Vector3D) PolyhedronsSet.this.getBarycenter(), scaled, facetB));
        }
    }

    private static class RotationTransform implements Transform<Euclidean3D, Euclidean2D> {
        private Plane cachedOriginal;
        private Transform<Euclidean2D, Euclidean1D> cachedTransform;
        private final Vector3D center;
        private final Rotation rotation;

        public RotationTransform(Vector3D center, Rotation rotation) {
            this.center = center;
            this.rotation = rotation;
        }

        public Vector3D apply(Point<Euclidean3D> point) {
            Vector3D delta = ((Vector3D) point).subtract(this.center);
            return new Vector3D(1.0d, this.center, 1.0d, this.rotation.applyTo(delta));
        }

        public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
            return ((Plane) hyperplane).rotate(this.center, this.rotation);
        }

        public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
            if (original != this.cachedOriginal) {
                Plane oPlane = (Plane) original;
                Plane tPlane = (Plane) transformed;
                Vector3D p00 = oPlane.getOrigin();
                Vector3D p10 = oPlane.toSpace(new Vector2D(1.0d, 0.0d));
                Vector3D p01 = oPlane.toSpace(new Vector2D(0.0d, 1.0d));
                Vector2D tP00 = tPlane.toSubSpace(apply((Point) p00));
                Vector2D tP10 = tPlane.toSubSpace(apply((Point) p10));
                Vector2D tP01 = tPlane.toSubSpace(apply((Point) p01));
                AffineTransform at = new AffineTransform(tP10.getX() - tP00.getX(), tP10.getY() - tP00.getY(), tP01.getX() - tP00.getX(), tP01.getY() - tP00.getY(), tP00.getX(), tP00.getY());
                this.cachedOriginal = (Plane) original;
                this.cachedTransform = Line.getTransform(at);
            }
            return ((SubLine) sub).applyTransform(this.cachedTransform);
        }
    }

    private static class TranslationTransform implements Transform<Euclidean3D, Euclidean2D> {
        private Plane cachedOriginal;
        private Transform<Euclidean2D, Euclidean1D> cachedTransform;
        private final Vector3D translation;

        public TranslationTransform(Vector3D translation) {
            this.translation = translation;
        }

        public Vector3D apply(Point<Euclidean3D> point) {
            return new Vector3D(1.0d, (Vector3D) point, 1.0d, this.translation);
        }

        public Plane apply(Hyperplane<Euclidean3D> hyperplane) {
            return ((Plane) hyperplane).translate(this.translation);
        }

        public SubHyperplane<Euclidean2D> apply(SubHyperplane<Euclidean2D> sub, Hyperplane<Euclidean3D> original, Hyperplane<Euclidean3D> transformed) {
            if (original != this.cachedOriginal) {
                Vector2D shift = ((Plane) transformed).toSubSpace(apply(((Plane) original).getOrigin()));
                AffineTransform at = AffineTransform.getTranslateInstance(shift.getX(), shift.getY());
                this.cachedOriginal = (Plane) original;
                this.cachedTransform = Line.getTransform(at);
            }
            return ((SubLine) sub).applyTransform(this.cachedTransform);
        }
    }

    public PolyhedronsSet(double tolerance) {
        super(tolerance);
    }

    public PolyhedronsSet(BSPTree<Euclidean3D> tree, double tolerance) {
        super((BSPTree) tree, tolerance);
    }

    public PolyhedronsSet(Collection<SubHyperplane<Euclidean3D>> boundary, double tolerance) {
        super((Collection) boundary, tolerance);
    }

    public PolyhedronsSet(List<Vector3D> vertices, List<int[]> facets, double tolerance) {
        super(buildBoundary(vertices, facets, tolerance), tolerance);
    }

    public PolyhedronsSet(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double tolerance) {
        super(buildBoundary(xMin, xMax, yMin, yMax, zMin, zMax, tolerance), tolerance);
    }

    private static BSPTree<Euclidean3D> buildBoundary(double xMin, double xMax, double yMin, double yMax, double zMin, double zMax, double tolerance) {
        if (xMin >= xMax - tolerance || yMin >= yMax - tolerance || zMin >= zMax - tolerance) {
            return new BSPTree(Boolean.FALSE);
        }
        Plane pxMin = new Plane(new Vector3D(xMin, 0.0d, 0.0d), Vector3D.MINUS_I, tolerance);
        Plane pxMax = new Plane(new Vector3D(xMax, 0.0d, 0.0d), Vector3D.PLUS_I, tolerance);
        Plane pyMin = new Plane(new Vector3D(0.0d, yMin, 0.0d), Vector3D.MINUS_J, tolerance);
        Plane pyMax = new Plane(new Vector3D(0.0d, yMax, 0.0d), Vector3D.PLUS_J, tolerance);
        Plane pzMin = new Plane(new Vector3D(0.0d, 0.0d, zMin), Vector3D.MINUS_K, tolerance);
        Plane pzMax = new Plane(new Vector3D(0.0d, 0.0d, zMax), Vector3D.PLUS_K, tolerance);
        return new RegionFactory().buildConvex(pxMin, pxMax, pyMin, pyMax, pzMin, pzMax).getTree(false);
    }

    private static List<SubHyperplane<Euclidean3D>> buildBoundary(List<Vector3D> vertices, List<int[]> facets, double tolerance) {
        int i;
        for (i = 0; i < vertices.size() - 1; i++) {
            Vector3D vi = (Vector3D) vertices.get(i);
            for (int j = i + 1; j < vertices.size(); j++) {
                if (Vector3D.distance(vi, (Vector3D) vertices.get(j)) <= tolerance) {
                    throw new MathIllegalArgumentException(LocalizedFormats.CLOSE_VERTICES, Double.valueOf(vi.getX()), Double.valueOf(vi.getY()), Double.valueOf(vi.getZ()));
                }
            }
        }
        int[][] successors = successors(vertices, facets, findReferences(vertices, facets));
        int vA = 0;
        while (vA < vertices.size()) {
            for (int vB : successors[vA]) {
                if (vB >= 0) {
                    boolean found = false;
                    for (int v : successors[vB]) {
                        found = found || v == vA;
                    }
                    if (!found) {
                        Vector3D start = (Vector3D) vertices.get(vA);
                        Vector3D end = (Vector3D) vertices.get(vB);
                        throw new MathIllegalArgumentException(LocalizedFormats.EDGE_CONNECTED_TO_ONE_FACET, Double.valueOf(start.getX()), Double.valueOf(start.getY()), Double.valueOf(start.getZ()), Double.valueOf(end.getX()), Double.valueOf(end.getY()), Double.valueOf(end.getZ()));
                    }
                }
            }
            vA++;
        }
        List<SubHyperplane<Euclidean3D>> boundary = new ArrayList();
        for (int[] facet : facets) {
            Plane plane = new Plane((Vector3D) vertices.get(facet[0]), (Vector3D) vertices.get(facet[1]), (Vector3D) vertices.get(facet[2]), tolerance);
            Vector2D[] two2Points = new Vector2D[facet.length];
            i = 0;
            while (i < facet.length) {
                Vector3D v2 = (Vector3D) vertices.get(facet[i]);
                if (plane.contains(v2)) {
                    two2Points[i] = plane.toSubSpace((Vector) v2);
                    i++;
                } else {
                    throw new MathIllegalArgumentException(LocalizedFormats.OUT_OF_PLANE, Double.valueOf(v2.getX()), Double.valueOf(v2.getY()), Double.valueOf(v2.getZ()));
                }
            }
            boundary.add(new SubPlane(plane, new PolygonsSet(tolerance, two2Points)));
        }
        return boundary;
    }

    private static int[][] findReferences(List<Vector3D> vertices, List<int[]> facets) {
        int[] nbFacets = new int[vertices.size()];
        int maxFacets = 0;
        for (int[] facet : facets) {
            if (facet.length < 3) {
                throw new NumberIsTooSmallException(LocalizedFormats.WRONG_NUMBER_OF_POINTS, Integer.valueOf(3), Integer.valueOf(facet.length), true);
            }
            for (int index : facet) {
                int i = nbFacets[index] + 1;
                nbFacets[index] = i;
                maxFacets = FastMath.max(maxFacets, i);
            }
        }
        int[][] references = (int[][]) Array.newInstance(Integer.TYPE, new int[]{vertices.size(), maxFacets});
        for (int[] r : references) {
            Arrays.fill(r, -1);
        }
        for (int f = 0; f < facets.size(); f++) {
            for (int v : (int[]) facets.get(f)) {
                int k = 0;
                while (k < maxFacets && references[v][k] >= 0) {
                    k++;
                }
                references[v][k] = f;
            }
        }
        return references;
    }

    private static int[][] successors(List<Vector3D> vertices, List<int[]> facets, int[][] references) {
        int[][] successors = (int[][]) Array.newInstance(Integer.TYPE, new int[]{vertices.size(), references[0].length});
        for (int[] s : successors) {
            Arrays.fill(s, -1);
        }
        int v = 0;
        while (v < vertices.size()) {
            int k = 0;
            while (k < successors[v].length && references[v][k] >= 0) {
                int[] facet = (int[]) facets.get(references[v][k]);
                int i = 0;
                while (i < facet.length && facet[i] != v) {
                    i++;
                }
                successors[v][k] = facet[(i + 1) % facet.length];
                for (int l = 0; l < k; l++) {
                    if (successors[v][l] == successors[v][k]) {
                        Vector3D start = (Vector3D) vertices.get(v);
                        Vector3D end = (Vector3D) vertices.get(successors[v][k]);
                        throw new MathIllegalArgumentException(LocalizedFormats.FACET_ORIENTATION_MISMATCH, Double.valueOf(start.getX()), Double.valueOf(start.getY()), Double.valueOf(start.getZ()), Double.valueOf(end.getX()), Double.valueOf(end.getY()), Double.valueOf(end.getZ()));
                    }
                }
                k++;
            }
            v++;
        }
        return successors;
    }

    public PolyhedronsSet buildNew(BSPTree<Euclidean3D> tree) {
        return new PolyhedronsSet((BSPTree) tree, getTolerance());
    }

    protected void computeGeometricalProperties() {
        getTree(true).visit(new FacetsContributionVisitor());
        if (getSize() < 0.0d) {
            setSize(Double.POSITIVE_INFINITY);
            setBarycenter(Vector3D.NaN);
            return;
        }
        setSize(getSize() / 3.0d);
        setBarycenter(new Vector3D(1.0d / (4.0d * getSize()), (Vector3D) getBarycenter()));
    }

    public SubHyperplane<Euclidean3D> firstIntersection(Vector3D point, Line line) {
        return recurseFirstIntersection(getTree(true), point, line);
    }

    private SubHyperplane<Euclidean3D> recurseFirstIntersection(BSPTree<Euclidean3D> node, Vector3D point, Line line) {
        SubHyperplane<Euclidean3D> cut = node.getCut();
        if (cut == null) {
            return null;
        }
        BSPTree<Euclidean3D> near;
        BSPTree<Euclidean3D> far;
        SubHyperplane<Euclidean3D> facet;
        BSPTree<Euclidean3D> minus = node.getMinus();
        BSPTree<Euclidean3D> plus = node.getPlus();
        Plane plane = (Plane) cut.getHyperplane();
        double offset = plane.getOffset((Point) point);
        boolean in = FastMath.abs(offset) < getTolerance();
        if (offset < 0.0d) {
            near = minus;
            far = plus;
        } else {
            near = plus;
            far = minus;
        }
        if (in) {
            facet = boundaryFacet(point, node);
            if (facet != null) {
                return facet;
            }
        }
        SubHyperplane<Euclidean3D> crossed = recurseFirstIntersection(near, point, line);
        if (crossed != null) {
            return crossed;
        }
        if (!in) {
            Vector3D hit3D = plane.intersection(line);
            if (hit3D != null && line.getAbscissa(hit3D) > line.getAbscissa(point)) {
                facet = boundaryFacet(hit3D, node);
                if (facet != null) {
                    return facet;
                }
            }
        }
        return recurseFirstIntersection(far, point, line);
    }

    private SubHyperplane<Euclidean3D> boundaryFacet(Vector3D point, BSPTree<Euclidean3D> node) {
        Vector2D point2D = ((Plane) node.getCut().getHyperplane()).toSubSpace((Point) point);
        BoundaryAttribute<Euclidean3D> attribute = (BoundaryAttribute) node.getAttribute();
        if (attribute.getPlusOutside() != null && ((SubPlane) attribute.getPlusOutside()).getRemainingRegion().checkPoint(point2D) == Location.INSIDE) {
            return attribute.getPlusOutside();
        }
        if (attribute.getPlusInside() == null || ((SubPlane) attribute.getPlusInside()).getRemainingRegion().checkPoint(point2D) != Location.INSIDE) {
            return null;
        }
        return attribute.getPlusInside();
    }

    public PolyhedronsSet rotate(Vector3D center, Rotation rotation) {
        return (PolyhedronsSet) applyTransform(new RotationTransform(center, rotation));
    }

    public PolyhedronsSet translate(Vector3D translation) {
        return (PolyhedronsSet) applyTransform(new TranslationTransform(translation));
    }
}
