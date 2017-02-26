package org.apache.commons.math4.geometry.spherical.twod;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor;
import org.apache.commons.math4.geometry.partitioning.BSPTreeVisitor.Order;
import org.apache.commons.math4.util.FastMath;

class PropertiesComputer implements BSPTreeVisitor<Sphere2D> {
    private final List<Vector3D> convexCellsInsidePoints;
    private double summedArea;
    private Vector3D summedBarycenter;
    private final double tolerance;

    public PropertiesComputer(double tolerance) {
        this.tolerance = tolerance;
        this.summedArea = 0.0d;
        this.summedBarycenter = Vector3D.ZERO;
        this.convexCellsInsidePoints = new ArrayList();
    }

    public Order visitOrder(BSPTree<Sphere2D> bSPTree) {
        return Order.MINUS_SUB_PLUS;
    }

    public void visitInternalNode(BSPTree<Sphere2D> bSPTree) {
    }

    public void visitLeafNode(BSPTree<Sphere2D> node) {
        if (((Boolean) node.getAttribute()).booleanValue()) {
            List<Vertex> boundary = new SphericalPolygonsSet(node.pruneAroundConvexCell(Boolean.TRUE, Boolean.FALSE, null), this.tolerance).getBoundaryLoops();
            if (boundary.size() != 1) {
                throw new MathInternalError();
            }
            double area = convexCellArea((Vertex) boundary.get(0));
            Vector3D barycenter = convexCellBarycenter((Vertex) boundary.get(0));
            this.convexCellsInsidePoints.add(barycenter);
            this.summedArea += area;
            this.summedBarycenter = new Vector3D(1.0d, this.summedBarycenter, area, barycenter);
        }
    }

    private double convexCellArea(Vertex start) {
        int n = 0;
        double sum = 0.0d;
        Edge e = start.getOutgoing();
        while (true) {
            if (n != 0 && e.getStart() == start) {
                return sum - (((double) (n - 2)) * FastMath.PI);
            }
            Vector3D previousPole = e.getCircle().getPole();
            Vector3D nextPole = e.getEnd().getOutgoing().getCircle().getPole();
            double alpha = FastMath.atan2(Vector3D.dotProduct(nextPole, Vector3D.crossProduct(e.getEnd().getLocation().getVector(), previousPole)), -Vector3D.dotProduct(nextPole, previousPole));
            if (alpha < 0.0d) {
                alpha += Angle2D.M_2PI;
            }
            sum += alpha;
            n++;
            e = e.getEnd().getOutgoing();
        }
    }

    private Vector3D convexCellBarycenter(Vertex start) {
        int n = 0;
        Vector3D sumB = Vector3D.ZERO;
        Edge e = start.getOutgoing();
        Vector3D sumB2 = sumB;
        while (true) {
            if (n != 0 && e.getStart() == start) {
                return sumB2.normalize();
            }
            sumB = new Vector3D(1.0d, sumB2, e.getLength(), e.getCircle().getPole());
            n++;
            e = e.getEnd().getOutgoing();
            sumB2 = sumB;
        }
    }

    public double getArea() {
        return this.summedArea;
    }

    public S2Point getBarycenter() {
        if (this.summedBarycenter.getNormSq() == 0.0d) {
            return S2Point.NaN;
        }
        return new S2Point(this.summedBarycenter);
    }

    public List<Vector3D> getConvexCellsInsidePoints() {
        return this.convexCellsInsidePoints;
    }
}
