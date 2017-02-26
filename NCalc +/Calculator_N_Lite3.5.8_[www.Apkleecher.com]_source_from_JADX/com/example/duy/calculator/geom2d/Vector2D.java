package com.example.duy.calculator.geom2d;

import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class Vector2D implements GeometricObject2D, Cloneable {
    public double x;
    public double y;

    public Vector2D() {
        this(1.0d, 0.0d);
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Point2D point) {
        this(point.x, point.y);
    }

    public Vector2D(Point2D point1, Point2D point2) {
        this(point2.x - point1.x, point2.y - point1.y);
    }

    public static Vector2D createPolar(double rho, double theta) {
        return new Vector2D(Math.cos(theta) * rho, Math.sin(theta) * rho);
    }

    public static double dot(Vector2D v1, Vector2D v2) {
        return (v1.x * v2.x) + (v1.y * v2.y);
    }

    public static double cross(Vector2D v1, Vector2D v2) {
        return (v1.x * v2.y) - (v2.x * v1.y);
    }

    public static boolean isColinear(Vector2D v1, Vector2D v2) {
        v1 = v1.normalize();
        v2 = v2.normalize();
        return Math.abs((v1.x * v2.y) - (v1.y * v2.x)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public static boolean isOrthogonal(Vector2D v1, Vector2D v2) {
        v1 = v1.normalize();
        v2 = v2.normalize();
        return Math.abs((v1.x * v2.x) + (v1.y * v2.y)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public double x() {
        return this.x;
    }

    public double getX() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double getY() {
        return this.y;
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x, -this.y);
    }

    public double norm() {
        return Math.hypot(this.x, this.y);
    }

    public double angle() {
        return Angle2D.horizontalAngle(this);
    }

    public Vector2D normalize() {
        double r = Math.hypot(this.x, this.y);
        return new Vector2D(this.x / r, this.y / r);
    }

    public boolean isColinear(Vector2D v) {
        return isColinear(this, v);
    }

    public boolean isOrthogonal(Vector2D v) {
        return isOrthogonal(this, v);
    }

    public double dot(Vector2D v) {
        return (this.x * v.x) + (this.y * v.y);
    }

    public double cross(Vector2D v) {
        return (this.x * v.y) - (v.x * this.y);
    }

    public Vector2D plus(Vector2D v) {
        return new Vector2D(this.x + v.x, this.y + v.y);
    }

    public Vector2D minus(Vector2D v) {
        return new Vector2D(this.x - v.x, this.y - v.y);
    }

    public Vector2D times(double k) {
        return new Vector2D(this.x * k, this.y * k);
    }

    public Vector2D rotate(double theta) {
        double cot = Math.cos(theta);
        double sit = Math.sin(theta);
        return new Vector2D((this.x * cot) - (this.y * sit), (this.x * sit) + (this.y * cot));
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector2D)) {
            return false;
        }
        Vector2D v = (Vector2D) obj;
        if (Math.abs(this.x - v.x) > eps) {
            return false;
        }
        if (Math.abs(this.y - v.y) > eps) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Vector2D)) {
            return false;
        }
        Vector2D that = (Vector2D) obj;
        if (!EqualUtils.areEqual(this.x, that.x)) {
            return false;
        }
        if (EqualUtils.areEqual(this.y, that.y)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((Double.valueOf(this.x).hashCode() + 31) * 31) + Double.valueOf(this.y).hashCode();
    }

    public String toString() {
        return "Vector(" + this.x + VectorFormat.DEFAULT_SEPARATOR + this.y + ")";
    }

    public Vector2D getOrthogonal() {
        return new Vector2D(-this.y, this.x);
    }

    public double getScalar(Vector2D vector) {
        return (Math.sqrt((this.x * this.x) + (this.y * this.y)) * Math.sqrt(Math.pow(vector.getX(), 2.0d) + Math.pow(vector.getY(), 2.0d))) * Math.cos(Angle2D.angle(this, vector));
    }
}
