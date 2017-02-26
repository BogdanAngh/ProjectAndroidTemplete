package org.apache.commons.math4.geometry.spherical.oned;

import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.euclidean.twod.Vector2D;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class S1Point implements Point<Sphere1D> {
    public static final S1Point NaN;
    private static final long serialVersionUID = 20131218;
    private final double alpha;
    private final Vector2D vector;

    static {
        NaN = new S1Point(Double.NaN, Vector2D.NaN);
    }

    public S1Point(double alpha) {
        this(MathUtils.normalizeAngle(alpha, FastMath.PI), new Vector2D(FastMath.cos(alpha), FastMath.sin(alpha)));
    }

    private S1Point(double alpha, Vector2D vector) {
        this.alpha = alpha;
        this.vector = vector;
    }

    public double getAlpha() {
        return this.alpha;
    }

    public Vector2D getVector() {
        return this.vector;
    }

    public Space getSpace() {
        return Sphere1D.getInstance();
    }

    public boolean isNaN() {
        return Double.isNaN(this.alpha);
    }

    public double distance(Point<Sphere1D> point) {
        return distance(this, (S1Point) point);
    }

    public static double distance(S1Point p1, S1Point p2) {
        return Vector2D.angle(p1.vector, p2.vector);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof S1Point)) {
            return false;
        }
        S1Point rhs = (S1Point) other;
        if (rhs.isNaN()) {
            return isNaN();
        }
        if (this.alpha != rhs.alpha) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        if (isNaN()) {
            return 542;
        }
        return MathUtils.hash(this.alpha) * 1759;
    }
}
