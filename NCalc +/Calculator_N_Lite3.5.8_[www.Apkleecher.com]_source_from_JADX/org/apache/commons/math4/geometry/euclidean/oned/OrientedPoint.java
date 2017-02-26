package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;

public class OrientedPoint implements Hyperplane<Euclidean1D> {
    private boolean direct;
    private final Vector1D location;
    private final double tolerance;

    public OrientedPoint(Vector1D location, boolean direct, double tolerance) {
        this.location = location;
        this.direct = direct;
        this.tolerance = tolerance;
    }

    public OrientedPoint copySelf() {
        return this;
    }

    public double getOffset(Vector<Euclidean1D> vector) {
        return getOffset((Point) vector);
    }

    public double getOffset(Point<Euclidean1D> point) {
        double delta = ((Vector1D) point).getX() - this.location.getX();
        return this.direct ? delta : -delta;
    }

    public SubOrientedPoint wholeHyperplane() {
        return new SubOrientedPoint(this, null);
    }

    public IntervalsSet wholeSpace() {
        return new IntervalsSet(this.tolerance);
    }

    public boolean sameOrientationAs(Hyperplane<Euclidean1D> other) {
        return (this.direct ^ ((OrientedPoint) other).direct) == 0;
    }

    public Point<Euclidean1D> project(Point<Euclidean1D> point) {
        return this.location;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public Vector1D getLocation() {
        return this.location;
    }

    public boolean isDirect() {
        return this.direct;
    }

    public void revertSelf() {
        this.direct = !this.direct;
    }
}
