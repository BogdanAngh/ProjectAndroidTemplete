package org.apache.commons.math4.geometry.spherical.twod;

import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.partitioning.AbstractSubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.Region;
import org.apache.commons.math4.geometry.partitioning.Side;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane.SplitSubHyperplane;
import org.apache.commons.math4.geometry.spherical.oned.ArcsSet;
import org.apache.commons.math4.geometry.spherical.oned.ArcsSet.Split;
import org.apache.commons.math4.geometry.spherical.oned.Sphere1D;
import org.apache.commons.math4.util.FastMath;

public class SubCircle extends AbstractSubHyperplane<Sphere2D, Sphere1D> {
    public SubCircle(Hyperplane<Sphere2D> hyperplane, Region<Sphere1D> remainingRegion) {
        super(hyperplane, remainingRegion);
    }

    protected AbstractSubHyperplane<Sphere2D, Sphere1D> buildNew(Hyperplane<Sphere2D> hyperplane, Region<Sphere1D> remainingRegion) {
        return new SubCircle(hyperplane, remainingRegion);
    }

    public Side side(Hyperplane<Sphere2D> hyperplane) {
        Circle thisCircle = (Circle) getHyperplane();
        Circle otherCircle = (Circle) hyperplane;
        double angle = Vector3D.angle(thisCircle.getPole(), otherCircle.getPole());
        if (angle < thisCircle.getTolerance() || angle > FastMath.PI - thisCircle.getTolerance()) {
            return Side.HYPER;
        }
        return ((ArcsSet) getRemainingRegion()).side(thisCircle.getInsideArc(otherCircle));
    }

    public SplitSubHyperplane<Sphere2D> split(Hyperplane<Sphere2D> hyperplane) {
        Circle thisCircle = (Circle) getHyperplane();
        Circle otherCircle = (Circle) hyperplane;
        double angle = Vector3D.angle(thisCircle.getPole(), otherCircle.getPole());
        if (angle < thisCircle.getTolerance()) {
            return new SplitSubHyperplane(null, this);
        }
        if (angle > FastMath.PI - thisCircle.getTolerance()) {
            return new SplitSubHyperplane(this, null);
        }
        SubHyperplane subHyperplane;
        Split split = ((ArcsSet) getRemainingRegion()).split(thisCircle.getInsideArc(otherCircle));
        ArcsSet plus = split.getPlus();
        ArcsSet minus = split.getMinus();
        if (plus == null) {
            subHyperplane = null;
        } else {
            Object subCircle = new SubCircle(thisCircle.copySelf(), plus);
        }
        return new SplitSubHyperplane(subHyperplane, minus == null ? null : new SubCircle(thisCircle.copySelf(), minus));
    }
}
