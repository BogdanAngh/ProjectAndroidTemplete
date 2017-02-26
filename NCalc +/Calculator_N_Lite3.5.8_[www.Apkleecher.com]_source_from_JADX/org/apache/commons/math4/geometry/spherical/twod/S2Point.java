package org.apache.commons.math4.geometry.spherical.twod;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class S2Point implements Point<Sphere2D> {
    public static final S2Point MINUS_I;
    public static final S2Point MINUS_J;
    public static final S2Point MINUS_K;
    public static final S2Point NaN;
    public static final S2Point PLUS_I;
    public static final S2Point PLUS_J;
    public static final S2Point PLUS_K;
    private static final long serialVersionUID = 20131218;
    private final double phi;
    private final double theta;
    private final Vector3D vector;

    static {
        PLUS_I = new S2Point(0.0d, Angle2D.M_PI_2, Vector3D.PLUS_I);
        PLUS_J = new S2Point(Angle2D.M_PI_2, Angle2D.M_PI_2, Vector3D.PLUS_J);
        PLUS_K = new S2Point(0.0d, 0.0d, Vector3D.PLUS_K);
        MINUS_I = new S2Point(FastMath.PI, Angle2D.M_PI_2, Vector3D.MINUS_I);
        MINUS_J = new S2Point(Angle2D.M_3PI_2, Angle2D.M_PI_2, Vector3D.MINUS_J);
        MINUS_K = new S2Point(0.0d, FastMath.PI, Vector3D.MINUS_K);
        NaN = new S2Point(Double.NaN, Double.NaN, Vector3D.NaN);
    }

    public S2Point(double theta, double phi) throws OutOfRangeException {
        this(theta, phi, vector(theta, phi));
    }

    public S2Point(Vector3D vector) throws MathArithmeticException {
        this(FastMath.atan2(vector.getY(), vector.getX()), Vector3D.angle(Vector3D.PLUS_K, vector), vector.normalize());
    }

    private S2Point(double theta, double phi, Vector3D vector) {
        this.theta = theta;
        this.phi = phi;
        this.vector = vector;
    }

    private static Vector3D vector(double theta, double phi) throws OutOfRangeException {
        if (phi < 0.0d || phi > FastMath.PI) {
            throw new OutOfRangeException(Double.valueOf(phi), Integer.valueOf(0), Double.valueOf(FastMath.PI));
        }
        double cosTheta = FastMath.cos(theta);
        double sinTheta = FastMath.sin(theta);
        double cosPhi = FastMath.cos(phi);
        double sinPhi = FastMath.sin(phi);
        return new Vector3D(cosTheta * sinPhi, sinTheta * sinPhi, cosPhi);
    }

    public double getTheta() {
        return this.theta;
    }

    public double getPhi() {
        return this.phi;
    }

    public Vector3D getVector() {
        return this.vector;
    }

    public Space getSpace() {
        return Sphere2D.getInstance();
    }

    public boolean isNaN() {
        return Double.isNaN(this.theta) || Double.isNaN(this.phi);
    }

    public S2Point negate() {
        return new S2Point(-this.theta, FastMath.PI - this.phi, this.vector.negate());
    }

    public double distance(Point<Sphere2D> point) {
        return distance(this, (S2Point) point);
    }

    public static double distance(S2Point p1, S2Point p2) {
        return Vector3D.angle(p1.vector, p2.vector);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof S2Point)) {
            return false;
        }
        S2Point rhs = (S2Point) other;
        if (rhs.isNaN()) {
            return isNaN();
        }
        if (this.theta == rhs.theta && this.phi == rhs.phi) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isNaN()) {
            return 542;
        }
        return ((MathUtils.hash(this.theta) * 37) + MathUtils.hash(this.phi)) * 134;
    }
}
