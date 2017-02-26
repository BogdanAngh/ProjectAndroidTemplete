package org.apache.commons.math4.geometry.euclidean.twod;

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

public class Vector2D implements Vector<Euclidean2D> {
    public static final Vector2D NEGATIVE_INFINITY;
    public static final Vector2D NaN;
    public static final Vector2D POSITIVE_INFINITY;
    public static final Vector2D ZERO;
    private static final long serialVersionUID = 266938651998679754L;
    private final double x;
    private final double y;

    static {
        ZERO = new Vector2D(0.0d, 0.0d);
        NaN = new Vector2D(Double.NaN, Double.NaN);
        POSITIVE_INFINITY = new Vector2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
        NEGATIVE_INFINITY = new Vector2D(Double.NEGATIVE_INFINITY, Double.NEGATIVE_INFINITY);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(double[] v) throws DimensionMismatchException {
        if (v.length != 2) {
            throw new DimensionMismatchException(v.length, 2);
        }
        this.x = v[0];
        this.y = v[1];
    }

    public Vector2D(double a, Vector2D u) {
        this.x = u.x * a;
        this.y = u.y * a;
    }

    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2) {
        this.x = (u1.x * a1) + (u2.x * a2);
        this.y = (u1.y * a1) + (u2.y * a2);
    }

    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3) {
        this.x = ((u1.x * a1) + (u2.x * a2)) + (u3.x * a3);
        this.y = ((u1.y * a1) + (u2.y * a2)) + (u3.y * a3);
    }

    public Vector2D(double a1, Vector2D u1, double a2, Vector2D u2, double a3, Vector2D u3, double a4, Vector2D u4) {
        this.x = (((u1.x * a1) + (u2.x * a2)) + (u3.x * a3)) + (u4.x * a4);
        this.y = (((u1.y * a1) + (u2.y * a2)) + (u3.y * a3)) + (u4.y * a4);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double[] toArray() {
        return new double[]{this.x, this.y};
    }

    public Space getSpace() {
        return Euclidean2D.getInstance();
    }

    public Vector2D getZero() {
        return ZERO;
    }

    public double getNorm1() {
        return FastMath.abs(this.x) + FastMath.abs(this.y);
    }

    public double getNorm() {
        return FastMath.sqrt((this.x * this.x) + (this.y * this.y));
    }

    public double getNormSq() {
        return (this.x * this.x) + (this.y * this.y);
    }

    public double getNormInf() {
        return FastMath.max(FastMath.abs(this.x), FastMath.abs(this.y));
    }

    public Vector2D add(Vector<Euclidean2D> v) {
        Vector2D v2 = (Vector2D) v;
        return new Vector2D(this.x + v2.getX(), this.y + v2.getY());
    }

    public Vector2D add(double factor, Vector<Euclidean2D> v) {
        Vector2D v2 = (Vector2D) v;
        return new Vector2D(this.x + (v2.getX() * factor), this.y + (v2.getY() * factor));
    }

    public Vector2D subtract(Vector<Euclidean2D> p) {
        Vector2D p3 = (Vector2D) p;
        return new Vector2D(this.x - p3.x, this.y - p3.y);
    }

    public Vector2D subtract(double factor, Vector<Euclidean2D> v) {
        Vector2D v2 = (Vector2D) v;
        return new Vector2D(this.x - (v2.getX() * factor), this.y - (v2.getY() * factor));
    }

    public Vector2D normalize() throws MathArithmeticException {
        double s = getNorm();
        if (s != 0.0d) {
            return scalarMultiply(1.0d / s);
        }
        throw new MathArithmeticException(LocalizedFormats.CANNOT_NORMALIZE_A_ZERO_NORM_VECTOR, new Object[0]);
    }

    public static double angle(Vector2D v1, Vector2D v2) throws MathArithmeticException {
        double normProduct = v1.getNorm() * v2.getNorm();
        if (normProduct == 0.0d) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
        }
        double dot = v1.dotProduct(v2);
        double threshold = normProduct * 0.9999d;
        if (dot >= (-threshold) && dot <= threshold) {
            return FastMath.acos(dot / normProduct);
        }
        double n = FastMath.abs(MathArrays.linearCombination(v1.x, v2.y, -v1.y, v2.x));
        if (dot >= 0.0d) {
            return FastMath.asin(n / normProduct);
        }
        return FastMath.PI - FastMath.asin(n / normProduct);
    }

    public Vector2D negate() {
        return new Vector2D(-this.x, -this.y);
    }

    public Vector2D scalarMultiply(double a) {
        return new Vector2D(this.x * a, this.y * a);
    }

    public boolean isNaN() {
        return Double.isNaN(this.x) || Double.isNaN(this.y);
    }

    public boolean isInfinite() {
        return !isNaN() && (Double.isInfinite(this.x) || Double.isInfinite(this.y));
    }

    public double distance1(Vector<Euclidean2D> p) {
        Vector2D p3 = (Vector2D) p;
        return FastMath.abs(p3.x - this.x) + FastMath.abs(p3.y - this.y);
    }

    public double distance(Point<Euclidean2D> p) {
        Vector2D p3 = (Vector2D) p;
        double dx = p3.x - this.x;
        double dy = p3.y - this.y;
        return FastMath.sqrt((dx * dx) + (dy * dy));
    }

    public double distanceInf(Vector<Euclidean2D> p) {
        Vector2D p3 = (Vector2D) p;
        return FastMath.max(FastMath.abs(p3.x - this.x), FastMath.abs(p3.y - this.y));
    }

    public double distanceSq(Vector<Euclidean2D> p) {
        Vector2D p3 = (Vector2D) p;
        double dx = p3.x - this.x;
        double dy = p3.y - this.y;
        return (dx * dx) + (dy * dy);
    }

    public double dotProduct(Vector<Euclidean2D> v) {
        Vector2D v2 = (Vector2D) v;
        return MathArrays.linearCombination(this.x, v2.x, this.y, v2.y);
    }

    public double crossProduct(Vector2D p1, Vector2D p2) {
        return MathArrays.linearCombination(p2.getX() - p1.getX(), getY() - p1.getY(), -(getX() - p1.getX()), p2.getY() - p1.getY());
    }

    public static double distance(Vector2D p1, Vector2D p2) {
        return p1.distance(p2);
    }

    public static double distanceInf(Vector2D p1, Vector2D p2) {
        return p1.distanceInf(p2);
    }

    public static double distanceSq(Vector2D p1, Vector2D p2) {
        return p1.distanceSq(p2);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Vector2D)) {
            return false;
        }
        Vector2D rhs = (Vector2D) other;
        if (rhs.isNaN()) {
            return isNaN();
        }
        if (this.x == rhs.x && this.y == rhs.y) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        if (isNaN()) {
            return 542;
        }
        return ((MathUtils.hash(this.x) * 76) + MathUtils.hash(this.y)) * 122;
    }

    public String toString() {
        return Vector2DFormat.getInstance().format(this);
    }

    public String toString(NumberFormat format) {
        return new Vector2DFormat(format).format(this);
    }
}
