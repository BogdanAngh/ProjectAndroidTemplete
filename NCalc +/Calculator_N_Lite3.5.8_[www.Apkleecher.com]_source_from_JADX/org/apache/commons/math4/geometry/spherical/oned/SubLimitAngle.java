package org.apache.commons.math4.geometry.spherical.oned;

import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.linear.CholeskyDecomposition;

public class SubLimitAngle extends AbstractSubHyperplane<Sphere1D, Sphere1D> {
    public SubLimitAngle(Hyperplane<Sphere1D> hyperplane, Region<Sphere1D> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    public double getSize() {
        return 0.0d;
    }

    public boolean isEmpty() {
        return false;
    }

    protected AbstractSubHyperplane<Sphere1D, Sphere1D> buildNew(Hyperplane<Sphere1D> hyperplane, Region<Sphere1D> remainingRegion) {
        return new SubLimitAngle(hyperplane, remainingRegion);
    }

    public Side side(Hyperplane<Sphere1D> hyperplane) {
        double global = hyperplane.getOffset(((LimitAngle) getHyperplane()).getLocation());
        if (global < -1.0E-10d) {
            return Side.MINUS;
        }
        return global > CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD ? Side.PLUS : Side.HYPER;
    }

    public SplitSubHyperplane<Sphere1D> split(Hyperplane<Sphere1D> hyperplane) {
        if (hyperplane.getOffset(((LimitAngle) getHyperplane()).getLocation()) < -1.0E-10d) {
            return new SplitSubHyperplane(null, this);
        }
        return new SplitSubHyperplane(this, null);
    }
}
