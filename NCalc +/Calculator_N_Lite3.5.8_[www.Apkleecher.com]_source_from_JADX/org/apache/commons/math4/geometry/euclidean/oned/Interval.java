package org.apache.commons.math4.geometry.euclidean.oned;

import org.apache.commons.math4.geometry.partitioning.Region.Location;

public class Interval {
    private final double lower;
    private final double upper;

    public Interval(double lower, double upper) {
        this.lower = lower;
        this.upper = upper;
    }

    public double getInf() {
        return this.lower;
    }

    public double getSup() {
        return this.upper;
    }

    public double getSize() {
        return this.upper - this.lower;
    }

    public double getBarycenter() {
        return 0.5d * (this.lower + this.upper);
    }

    public Location checkPoint(double point, double tolerance) {
        if (point < this.lower - tolerance || point > this.upper + tolerance) {
            return Location.OUTSIDE;
        }
        if (point <= this.lower + tolerance || point >= this.upper - tolerance) {
            return Location.BOUNDARY;
        }
        return Location.INSIDE;
    }
}
