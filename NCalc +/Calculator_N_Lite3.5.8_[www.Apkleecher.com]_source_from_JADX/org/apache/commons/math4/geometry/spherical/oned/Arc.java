package org.apache.commons.math4.geometry.spherical.oned;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.partitioning.Region.Location;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public class Arc {
    private final double lower;
    private final double middle;
    private final double tolerance;
    private final double upper;

    public Arc(double lower, double upper, double tolerance) throws NumberIsTooLargeException {
        this.tolerance = tolerance;
        if (Precision.equals(lower, upper, 0) || upper - lower >= Angle2D.M_2PI) {
            this.lower = 0.0d;
            this.upper = Angle2D.M_2PI;
            this.middle = FastMath.PI;
        } else if (lower <= upper) {
            this.lower = MathUtils.normalizeAngle(lower, FastMath.PI);
            this.upper = this.lower + (upper - lower);
            this.middle = 0.5d * (this.lower + this.upper);
        } else {
            throw new NumberIsTooLargeException(LocalizedFormats.ENDPOINTS_NOT_AN_INTERVAL, Double.valueOf(lower), Double.valueOf(upper), true);
        }
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
        return this.middle;
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public Location checkPoint(double point) {
        double normalizedPoint = MathUtils.normalizeAngle(point, this.middle);
        if (normalizedPoint < this.lower - this.tolerance || normalizedPoint > this.upper + this.tolerance) {
            return Location.OUTSIDE;
        }
        if (normalizedPoint <= this.lower + this.tolerance || normalizedPoint >= this.upper - this.tolerance) {
            return getSize() >= Angle2D.M_2PI - this.tolerance ? Location.INSIDE : Location.BOUNDARY;
        } else {
            return Location.INSIDE;
        }
    }
}
