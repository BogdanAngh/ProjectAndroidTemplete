package org.apache.commons.math4.geometry.spherical.twod;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.geometry.enclosing.EnclosingBall;
import org.apache.commons.math4.geometry.enclosing.WelzlEncloser;
import org.apache.commons.math4.geometry.euclidean.threed.Euclidean3D;
import org.apache.commons.math4.geometry.euclidean.threed.Rotation;
import org.apache.commons.math4.geometry.euclidean.threed.SphereGenerator;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.partitioning.AbstractRegion;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BoundaryProjection;
import org.apache.commons.math4.geometry.partitioning.RegionFactory;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.spherical.oned.Sphere1D;
import org.apache.commons.math4.util.FastMath;

public class SphericalPolygonsSet extends AbstractRegion<Sphere2D, Sphere1D> {
    private List<Vertex> loops;

    public SphericalPolygonsSet(double tolerance) {
        super(tolerance);
    }

    public SphericalPolygonsSet(Vector3D pole, double tolerance) {
        super(new BSPTree(new Circle(pole, tolerance).wholeHyperplane(), new BSPTree(Boolean.FALSE), new BSPTree(Boolean.TRUE), null), tolerance);
    }

    public SphericalPolygonsSet(Vector3D center, Vector3D meridian, double outsideRadius, int n, double tolerance) {
        this(tolerance, createRegularPolygonVertices(center, meridian, outsideRadius, n));
    }

    public SphericalPolygonsSet(BSPTree<Sphere2D> tree, double tolerance) {
        super((BSPTree) tree, tolerance);
    }

    public SphericalPolygonsSet(Collection<SubHyperplane<Sphere2D>> boundary, double tolerance) {
        super((Collection) boundary, tolerance);
    }

    public SphericalPolygonsSet(double hyperplaneThickness, S2Point... vertices) {
        super(verticesToTree(hyperplaneThickness, vertices), hyperplaneThickness);
    }

    private static S2Point[] createRegularPolygonVertices(Vector3D center, Vector3D meridian, double outsideRadius, int n) {
        S2Point[] array = new S2Point[n];
        array[0] = new S2Point(new Rotation(Vector3D.crossProduct(center, meridian), outsideRadius).applyTo(center));
        Rotation r = new Rotation(center, Angle2D.M_2PI / ((double) n));
        for (int i = 1; i < n; i++) {
            array[i] = new S2Point(r.applyTo(array[i - 1].getVector()));
        }
        return array;
    }

    private static BSPTree<Sphere2D> verticesToTree(double hyperplaneThickness, S2Point... vertices) {
        int n = vertices.length;
        if (n == 0) {
            return new BSPTree(Boolean.TRUE);
        }
        int i;
        Vertex[] vArray = new Vertex[n];
        for (i = 0; i < n; i++) {
            vArray[i] = new Vertex(vertices[i]);
        }
        List<Edge> edges = new ArrayList(n);
        Vertex end = vArray[n - 1];
        for (i = 0; i < n; i++) {
            Vertex start = end;
            end = vArray[i];
            Circle circle = start.sharedCircleWith(end);
            if (circle == null) {
                circle = new Circle(start.getLocation(), end.getLocation(), hyperplaneThickness);
            }
            edges.add(new Edge(start, end, Vector3D.angle(start.getLocation().getVector(), end.getLocation().getVector()), circle));
            for (Vertex vertex : vArray) {
                if (!(vertex == start || vertex == end || FastMath.abs(circle.getOffset(vertex.getLocation())) > hyperplaneThickness)) {
                    vertex.bindWith(circle);
                }
            }
        }
        BSPTree<Sphere2D> tree = new BSPTree();
        insertEdges(hyperplaneThickness, tree, edges);
        return tree;
    }

    private static void insertEdges(double hyperplaneThickness, BSPTree<Sphere2D> node, List<Edge> edges) {
        Edge inserted = null;
        int index = 0;
        while (inserted == null && index < edges.size()) {
            int index2 = index + 1;
            inserted = (Edge) edges.get(index);
            if (node.insertCut(inserted.getCircle())) {
                index = index2;
            } else {
                inserted = null;
                index = index2;
            }
        }
        if (inserted == null) {
            BSPTree<Sphere2D> parent = node.getParent();
            if (parent == null || node == parent.getMinus()) {
                node.setAttribute(Boolean.TRUE);
                return;
            } else {
                node.setAttribute(Boolean.FALSE);
                return;
            }
        }
        List<Edge> outsideList = new ArrayList();
        List<Edge> insideList = new ArrayList();
        for (Edge edge : edges) {
            if (edge != inserted) {
                edge.split(inserted.getCircle(), outsideList, insideList);
            }
        }
        if (outsideList.isEmpty()) {
            node.getPlus().setAttribute(Boolean.FALSE);
        } else {
            insertEdges(hyperplaneThickness, node.getPlus(), outsideList);
        }
        if (insideList.isEmpty()) {
            node.getMinus().setAttribute(Boolean.TRUE);
        } else {
            insertEdges(hyperplaneThickness, node.getMinus(), insideList);
        }
    }

    public SphericalPolygonsSet buildNew(BSPTree<Sphere2D> tree) {
        return new SphericalPolygonsSet((BSPTree) tree, getTolerance());
    }

    protected void computeGeometricalProperties() throws MathIllegalStateException {
        BSPTree<Sphere2D> tree = getTree(true);
        if (tree.getCut() != null) {
            PropertiesComputer pc = new PropertiesComputer(getTolerance());
            tree.visit(pc);
            setSize(pc.getArea());
            setBarycenter(pc.getBarycenter());
        } else if (tree.getCut() == null && ((Boolean) tree.getAttribute()).booleanValue()) {
            setSize(12.566370614359172d);
            setBarycenter(new S2Point(0.0d, 0.0d));
        } else {
            setSize(0.0d);
            setBarycenter(S2Point.NaN);
        }
    }

    public List<Vertex> getBoundaryLoops() throws MathIllegalStateException {
        if (this.loops == null) {
            if (getTree(false).getCut() == null) {
                this.loops = Collections.emptyList();
            } else {
                BSPTree<Sphere2D> root = getTree(true);
                EdgesBuilder visitor = new EdgesBuilder(root, getTolerance());
                root.visit(visitor);
                List<Edge> edges = visitor.getEdges();
                this.loops = new ArrayList();
                while (!edges.isEmpty()) {
                    Edge edge = (Edge) edges.get(0);
                    Vertex startVertex = edge.getStart();
                    this.loops.add(startVertex);
                    do {
                        Iterator<Edge> iterator = edges.iterator();
                        while (iterator.hasNext()) {
                            if (iterator.next() == edge) {
                                iterator.remove();
                                break;
                            }
                        }
                        edge = edge.getEnd().getOutgoing();
                    } while (edge.getStart() != startVertex);
                }
            }
        }
        return Collections.unmodifiableList(this.loops);
    }

    public EnclosingBall<Sphere2D, S2Point> getEnclosingCap() {
        S2Point[] s2PointArr;
        if (isEmpty()) {
            s2PointArr = new S2Point[0];
            return new EnclosingBall(S2Point.PLUS_K, Double.NEGATIVE_INFINITY, s2PointArr);
        } else if (isFull()) {
            s2PointArr = new S2Point[0];
            return new EnclosingBall(S2Point.PLUS_K, Double.POSITIVE_INFINITY, s2PointArr);
        } else {
            BSPTree<Sphere2D> root = getTree(false);
            if (isEmpty(root.getMinus())) {
                if (isFull(root.getPlus())) {
                    s2PointArr = new S2Point[0];
                    return new EnclosingBall(new S2Point(((Circle) root.getCut().getHyperplane()).getPole()).negate(), Angle2D.M_PI_2, s2PointArr);
                }
            }
            if (isFull(root.getMinus())) {
                if (isEmpty(root.getPlus())) {
                    s2PointArr = new S2Point[0];
                    return new EnclosingBall(new S2Point(((Circle) root.getCut().getHyperplane()).getPole()), Angle2D.M_PI_2, s2PointArr);
                }
            }
            List<Vector3D> points = getInsidePoints();
            for (Vertex loopStart : getBoundaryLoops()) {
                int count = 0;
                Vertex v = loopStart;
                while (true) {
                    if (count == 0 || v != loopStart) {
                        count++;
                        points.add(v.getLocation().getVector());
                        v = v.getOutgoing().getEnd();
                    }
                }
            }
            SphereGenerator generator = new SphereGenerator();
            EnclosingBall<Euclidean3D, Vector3D> enclosing3D = new WelzlEncloser(getTolerance(), generator).enclose(points);
            Vector3D[] support3D = (Vector3D[]) enclosing3D.getSupport();
            double r = enclosing3D.getRadius();
            double h = ((Vector3D) enclosing3D.getCenter()).getNorm();
            if (h < getTolerance()) {
                s2PointArr = new S2Point[0];
                EnclosingBall<Sphere2D, S2Point> enclosingS2 = new EnclosingBall(S2Point.PLUS_K, Double.POSITIVE_INFINITY, s2PointArr);
                for (Vector3D outsidePoint : getOutsidePoints()) {
                    S2Point s2Point = new S2Point(outsidePoint);
                    BoundaryProjection<Sphere2D> projection = projectToBoundary(s2Point);
                    if (FastMath.PI - projection.getOffset() < enclosingS2.getRadius()) {
                        enclosingS2 = new EnclosingBall(s2Point.negate(), FastMath.PI - projection.getOffset(), (S2Point) projection.getProjected());
                    }
                }
                return enclosingS2;
            }
            S2Point[] support = new S2Point[support3D.length];
            int i = 0;
            while (true) {
                int length = support3D.length;
                if (i >= r0) {
                    return new EnclosingBall(new S2Point((Vector3D) enclosing3D.getCenter()), FastMath.acos(((1.0d + (h * h)) - (r * r)) / (2.0d * h)), support);
                }
                support[i] = new S2Point(support3D[i]);
                i++;
            }
        }
    }

    private List<Vector3D> getInsidePoints() {
        PropertiesComputer pc = new PropertiesComputer(getTolerance());
        getTree(true).visit(pc);
        return pc.getConvexCellsInsidePoints();
    }

    private List<Vector3D> getOutsidePoints() {
        SphericalPolygonsSet complement = (SphericalPolygonsSet) new RegionFactory().getComplement(this);
        PropertiesComputer pc = new PropertiesComputer(getTolerance());
        complement.getTree(true).visit(pc);
        return pc.getConvexCellsInsidePoints();
    }
}
