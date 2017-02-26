package org.apache.commons.math4.geometry.euclidean.threed;

import java.io.Serializable;
import java.text.NumberFormat;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Space;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Vector3D implements Serializable, Vector<Euclidean3D> {
    public static final Vector3D MINUS_I;
    public static final Vector3D MINUS_J;
    public static final Vector3D MINUS_K;
    public static final Vector3D NEGATIVE_INFINITY;
    public static final Vector3D NaN;
    public static final Vector3D PLUS_I;
    public static final Vector3D PLUS_J;
    public static final Vector3D PLUS_K;
    public static final Vector3D POSITIVE_INFINITY;
    public static final Vector3D ZERO;
    private static final long serialVersionUID = 1313493323784566947L;
    private final double x;
    private final double y;
    private final double z;

    static {
        ZERO = new Vector3D(0.0d, 0.0d, 0.0d);
        PLUS_I = new Vector3D(1.0d, 0.0d, 0.0d);
        MINUS_I = new Vector3D(-1.0d, 0.0d, 0.0d);
        PLUS_J = new Vector3D(0.0d, 1.0d, 0.0d);
        MINUS_J = new Vector3D(0.0d, -1.0d, 0.0d);
        PLUS_K = new Vector3D(0.0d, 0.0d, 1.0d);
        MINUS_K = new Vector3D(0.0d, 0.0d, -1.0d);
        NaN = new Vector3D(Double.NaN, Double.NaN, Double.NaN);
        POSITIVE_INFINITY = new Vector3D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        NEGATIVE_INFINITY = new Vector3D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double[] v) throws DimensionMismatchException {
        if (v.length != 3) {
            throw new DimensionMismatchException(v.length, 3);
        }
        this.x = v[0];
        this.y = v[1];
        this.z = v[2];
    }

    public Vector3D(double alpha, double delta) {
        double cosDelta = FastMath.cos(delta);
        this.x = FastMath.cos(alpha) * cosDelta;
        this.y = FastMath.sin(alpha) * cosDelta;
        this.z = FastMath.sin(delta);
    }

    public Vector3D(double a, Vector3D u) {
        this.x = u.x * a;
        this.y = u.y * a;
        this.z = u.z * a;
    }

    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2) {
        this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x);
        this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y);
        this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z);
    }

    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3) {
        this.x = MathArrays.linearCombination(a1, u1.x, a2, u2.x, a3, u3.x);
        this.y = MathArrays.linearCombination(a1, u1.y, a2, u2.y, a3, u3.y);
        this.z = MathArrays.linearCombination(a1, u1.z, a2, u2.z, a3, u3.z);
    }

    public Vector3D(double a1, Vector3D u1, double a2, Vector3D u2, double a3, Vector3D u3, double a4, Vector3D u4) {
        double d = a1;
        double d2 = a2;
        double d3 = a3;
        double d4 = a4;
        this.x = MathArrays.linearCombination(d, u1.x, d2, u2.x, d3, u3.x, d4, u4.x);
        d = a1;
        d2 = a2;
        d3 = a3;
        d4 = a4;
        this.y = MathArrays.linearCombination(d, u1.y, d2, u2.y, d3, u3.y, d4, u4.y);
        d = a1;
        d2 = a2;
        d3 = a3;
        d4 = a4;
        this.z = MathArrays.linearCombination(d, u1.z, d2, u2.z, d3, u3.z, d4, u4.z);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public double[] toArray() {
        return new double[]{this.x, this.y, this.z};
    }

    public Space getSpace() {
        return Euclidean3D.getInstance();
    }

    public Vector3D getZero() {
        return ZERO;
    }

    public double getNorm1() {
        return (FastMath.abs(this.x) + FastMath.abs(this.y)) + FastMath.abs(this.z);
    }

    public double getNorm() {
        return FastMath.sqrt(((this.x * this.x) + (this.y * this.y)) + (this.z * this.z));
    }

    public double getNormSq() {
        return ((this.x * this.x) + (this.y * this.y)) + (this.z * this.z);
    }

    public double getNormInf() {
        return FastMath.max(FastMath.max(FastMath.abs(this.x), FastMath.abs(this.y)), FastMath.abs(this.z));
    }

    public double getAlpha() {
        return FastMath.atan2(this.y, this.x);
    }

    public double getDelta() {
        return FastMath.asin(this.z / getNorm());
    }

    public Vector3D add(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        return new Vector3D(this.x + v3.x, this.y + v3.y, this.z + v3.z);
    }

    public Vector3D add(double factor, Vector<Euclidean3D> v) {
        return new Vector3D(1.0d, this, factor, (Vector3D) v);
    }

    public Vector3D subtract(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        return new Vector3D(this.x - v3.x, this.y - v3.y, this.z - v3.z);
    }

    public Vector3D subtract(double factor, Vector<Euclidean3D> v) {
        return new Vector3D(1.0d, this, -factor, (Vector3D) v);
    }

    public Vector3D normalize() throws MathArithmeticException {
        double s = getNorm();
        if (s != 0.0d) {
            return scalarMultiply(1.0d / s);
        }
        throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
    }

    public Vector3D orthogonal() throws MathArithmeticException {
        double threshold = 0.6d * getNorm();
        if (threshold == 0.0d) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
        } else if (FastMath.abs(this.x) <= threshold) {
            inverse = 1.0d / FastMath.sqrt((this.y * this.y) + (this.z * this.z));
            return new Vector3D(0.0d, this.z * inverse, (-inverse) * this.y);
        } else if (FastMath.abs(this.y) <= threshold) {
            inverse = 1.0d / FastMath.sqrt((this.x * this.x) + (this.z * this.z));
            return new Vector3D((-inverse) * this.z, 0.0d, this.x * inverse);
        } else {
            inverse = 1.0d / FastMath.sqrt((this.x * this.x) + (this.y * this.y));
            return new Vector3D(this.y * inverse, (-inverse) * this.x, 0.0d);
        }
    }

    public static double angle(Vector3D v1, Vector3D v2) throws MathArithmeticException {
        double normProduct = v1.getNorm() * v2.getNorm();
        if (normProduct == 0.0d) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
        }
        double dot = v1.dotProduct(v2);
        double threshold = normProduct * 0.9999d;
        if (dot >= (-threshold) && dot <= threshold) {
            return FastMath.acos(dot / normProduct);
        }
        Vector3D v3 = crossProduct(v1, v2);
        if (dot >= 0.0d) {
            return FastMath.asin(v3.getNorm() / normProduct);
        }
        return FastMath.PI - FastMath.asin(v3.getNorm() / normProduct);
    }

    public Vector3D negate() {
        return new Vector3D(-this.x, -this.y, -this.z);
    }

    public Vector3D scalarMultiply(double a) {
        return new Vector3D(this.x * a, this.y * a, this.z * a);
    }

    public boolean isNaN() {
        return Double.isNaN(this.x) || Double.isNaN(this.y) || Double.isNaN(this.z);
    }

    public boolean isInfinite() {
        return !isNaN() && (Double.isInfinite(this.x) || Double.isInfinite(this.y) || Double.isInfinite(this.z));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector3D)) {
            return false;
        }
        Vector3D rhs = (Vector3D) other;
        if (rhs.isNaN()) {
            return isNaN();
        }
        if (this.x == rhs.x && this.y == rhs.y && this.z == rhs.z) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isNaN()) {
            return 642;
        }
        return (((MathUtils.hash(this.x) * 164) + (MathUtils.hash(this.y) * 3)) + MathUtils.hash(this.z)) * 643;
    }

    public double dotProduct(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        return MathArrays.linearCombination(this.x, v3.x, this.y, v3.y, this.z, v3.z);
    }

    public Vector3D crossProduct(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        return new Vector3D(MathArrays.linearCombination(this.y, v3.z, -this.z, v3.y), MathArrays.linearCombination(this.z, v3.x, -this.x, v3.z), MathArrays.linearCombination(this.x, v3.y, -this.y, v3.x));
    }

    public double distance1(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        double dx = FastMath.abs(v3.x - this.x);
        double dy = FastMath.abs(v3.y - this.y);
        return (dx + dy) + FastMath.abs(v3.z - this.z);
    }

    public double distance(Point<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        double dx = v3.x - this.x;
        double dy = v3.y - this.y;
        double dz = v3.z - this.z;
        return FastMath.sqrt(((dx * dx) + (dy * dy)) + (dz * dz));
    }

    public double distanceInf(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        double dx = FastMath.abs(v3.x - this.x);
        double dy = FastMath.abs(v3.y - this.y);
        return FastMath.max(FastMath.max(dx, dy), FastMath.abs(v3.z - this.z));
    }

    public double distanceSq(Vector<Euclidean3D> v) {
        Vector3D v3 = (Vector3D) v;
        double dx = v3.x - this.x;
        double dy = v3.y - this.y;
        double dz = v3.z - this.z;
        return ((dx * dx) + (dy * dy)) + (dz * dz);
    }

    public static double dotProduct(Vector3D v1, Vector3D v2) {
        return v1.dotProduct(v2);
    }

    public static Vector3D crossProduct(Vector3D v1, Vector3D v2) {
        return v1.crossProduct(v2);
    }

    public static double distance1(Vector3D v1, Vector3D v2) {
        return v1.distance1(v2);
    }

    public static double distance(Vector3D v1, Vector3D v2) {
        return v1.distance(v2);
    }

    public static double distanceInf(Vector3D v1, Vector3D v2) {
        return v1.distanceInf(v2);
    }

    public static double distanceSq(Vector3D v1, Vector3D v2) {
        return v1.distanceSq(v2);
    }

    public String toString() {
        return Vector3DFormat.getInstance().format(this);
    }

    public String toString(NumberFormat format) {
        return new Vector3DFormat(format).format(this);
    }
}
