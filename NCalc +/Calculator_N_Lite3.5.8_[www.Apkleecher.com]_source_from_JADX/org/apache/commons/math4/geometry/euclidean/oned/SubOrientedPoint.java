package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.linear.CholeskyDecomposition;

public class SubOrientedPoint extends AbstractSubHyperplane<Euclidean1D, Euclidean1D> {
    public SubOrientedPoint(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    public double getSize() {
        return 0.0d;
    }

    public boolean isEmpty() {
        return false;
    }

    protected AbstractSubHyperplane<Euclidean1D, Euclidean1D> buildNew(Hyperplane<Euclidean1D> hyperplane, Region<Euclidean1D> remainingRegion) {
        return new SubOrientedPoint(hyperplane, remainingRegion);
    }

    public Side side(Hyperplane<Euclidean1D> hyperplane) {
        double global = hyperplane.getOffset(((OrientedPoint) getHyperplane()).getLocation());
        if (global < -1.0E-10d) {
            return Side.MINUS;
        }
        return global > CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD ? Side.PLUS : Side.HYPER;
    }

    public SplitSubHyperplane<Euclidean1D> split(Hyperplane<Euclidean1D> hyperplane) {
        if (hyperplane.getOffset(((OrientedPoint) getHyperplane()).getLocation()) < -1.0E-10d) {
            return new SplitSubHyperplane(null, this);
        }
        return new SplitSubHyperplane(this, null);
    }
}
