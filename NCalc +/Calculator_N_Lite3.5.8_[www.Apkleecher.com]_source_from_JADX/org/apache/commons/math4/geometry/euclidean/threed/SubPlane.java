package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.euclidean.twod.Euclidean2D;
import org.apache.commons.math4.geometry.euclidean.twod.Line;
import org.apache.commons.math4.geometry.euclidean.twod.PolygonsSet;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.BSPTree;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.linear.CholeskyDecomposition;

public class SubPlane extends AbstractSubHyperplane<Euclidean3D, Euclidean2D> {
    public SubPlane(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    protected AbstractSubHyperplane<Euclidean3D, Euclidean2D> buildNew(Hyperplane<Euclidean3D> hyperplane, Region<Euclidean2D> remainingRegion) {
        return new SubPlane(hyperplane, remainingRegion);
    }

    public Side side(Hyperplane<Euclidean3D> hyperplane) {
        Plane otherPlane = (Plane) hyperplane;
        Plane thisPlane = (Plane) getHyperplane();
        Line inter = otherPlane.intersection(thisPlane);
        double tolerance = thisPlane.getTolerance();
        if (inter == null) {
            double global = otherPlane.getOffset(thisPlane);
            if (global < -1.0E-10d) {
                return Side.MINUS;
            }
            return global > CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD ? Side.PLUS : Side.HYPER;
        } else {
            Vector2D p = thisPlane.toSubSpace(inter.toSpace(Vector1D.ZERO));
            Vector2D q = thisPlane.toSubSpace(inter.toSpace(Vector1D.ONE));
            if (Vector3D.crossProduct(inter.getDirection(), thisPlane.getNormal()).dotProduct(otherPlane.getNormal()) < 0.0d) {
                Vector2D tmp = p;
                p = q;
                q = tmp;
            }
            return getRemainingRegion().side(new Line(p, q, tolerance));
        }
    }

    public SplitSubHyperplane<Euclidean3D> split(Hyperplane<Euclidean3D> hyperplane) {
        Plane otherPlane = (Plane) hyperplane;
        Plane thisPlane = (Plane) getHyperplane();
        Line inter = otherPlane.intersection(thisPlane);
        double tolerance = thisPlane.getTolerance();
        if (inter != null) {
            BSPTree plusTree;
            BSPTree minusTree;
            Vector2D p = thisPlane.toSubSpace((Point) inter.toSpace(Vector1D.ZERO));
            Vector2D q = thisPlane.toSubSpace((Point) inter.toSpace(Vector1D.ONE));
            if (Vector3D.crossProduct(inter.getDirection(), thisPlane.getNormal()).dotProduct(otherPlane.getNormal()) < 0.0d) {
                Vector2D tmp = p;
                p = q;
                q = tmp;
            }
            SubHyperplane<Euclidean2D> l2DMinus = new Line(p, q, tolerance).wholeHyperplane();
            SubHyperplane<Euclidean2D> l2DPlus = new Line(q, p, tolerance).wholeHyperplane();
            BSPTree<Euclidean2D> splitTree = getRemainingRegion().getTree(false).split(l2DMinus);
            if (getRemainingRegion().isEmpty(splitTree.getPlus())) {
                plusTree = new BSPTree(Boolean.FALSE);
            } else {
                plusTree = new BSPTree(l2DPlus, new BSPTree(Boolean.FALSE), splitTree.getPlus(), null);
            }
            if (getRemainingRegion().isEmpty(splitTree.getMinus())) {
                minusTree = new BSPTree(Boolean.FALSE);
            } else {
                minusTree = new BSPTree(l2DMinus, new BSPTree(Boolean.FALSE), splitTree.getMinus(), null);
            }
            return new SplitSubHyperplane(new SubPlane(thisPlane.copySelf(), new PolygonsSet(plusTree, tolerance)), new SubPlane(thisPlane.copySelf(), new PolygonsSet(minusTree, tolerance)));
        } else if (otherPlane.getOffset(thisPlane) < -1.0E-10d) {
            return new SplitSubHyperplane(null, this);
        } else {
            return new SplitSubHyperplane(this, null);
        }
    }
}
