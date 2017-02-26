package com.example.duy.calculator.geom2d.line;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import com.example.duy.calculator.geom2d.util.UnboundedShape2DException;

public class Ray2D extends AbstractLine2D {
    public Ray2D() {
        this(0.0d, 0.0d, 1.0d, 0.0d);
    }

    public Ray2D(Point2D point1, Point2D point2) {
        this(point1.x(), point1.y(), point2.x() - point1.x(), point2.y() - point1.y());
    }

    public Ray2D(double x1, double y1, double dx, double dy) {
        super(x1, y1, dx, dy);
    }

    public Ray2D(Point2D point, double dx, double dy) {
        this(point.x(), point.y(), dx, dy);
    }

    public Ray2D(Point2D point, Vector2D vector) {
        this(point.x(), point.y(), vector.x(), vector.y());
    }

    public Ray2D(Point2D point, double angle) {
        this(point.x(), point.y(), Math.cos(angle), Math.sin(angle));
    }

    public Ray2D(double x, double y, double angle) {
        this(x, y, Math.cos(angle), Math.sin(angle));
    }

    public Ray2D(LinearShape2D line) {
        super(line.origin(), line.direction());
    }

    @Deprecated
    public static Ray2D create(Point2D origin, Vector2D direction) {
        return new Ray2D(origin, direction);
    }

    @Deprecated
    public static Ray2D create(Point2D origin, Point2D target) {
        return new Ray2D(origin, target);
    }

    public Ray2D parallel(double d) {
        double dd = Math.hypot(this.dx, this.dy);
        return new Ray2D(this.x0 + ((this.dy * d) / dd), this.y0 - ((this.dx * d) / dd), this.dx, this.dy);
    }

    public Path getPath() {
        throw new UnboundedShape2DException(this);
    }

    public Point2D firstPoint() {
        return new Point2D(this.x0, this.y0);
    }

    public Point2D lastPoint() {
        return null;
    }

    public Point2D point(double t) {
        t = Math.max(t, 0.0d);
        return new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
    }

    public double t0() {
        return 0.0d;
    }

    @Deprecated
    public double getT0() {
        return t0();
    }

    public double t1() {
        return Double.POSITIVE_INFINITY;
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public InvertedRay2D reverse() {
        return new InvertedRay2D(this.x0, this.y0, -this.dx, -this.dy);
    }

    public boolean isBounded() {
        return false;
    }

    public boolean contains(double x, double y) {
        if (supportContains(x, y) && positionOnLine(x, y) > -1.0E-12d) {
            return true;
        }
        return false;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ray2D)) {
            return false;
        }
        Ray2D ray = (Ray2D) obj;
        if (Math.abs(this.x0 - ray.x0) > eps) {
            return false;
        }
        if (Math.abs(this.y0 - ray.y0) > eps) {
            return false;
        }
        if (Math.abs(this.dx - ray.dx) > eps) {
            return false;
        }
        if (Math.abs(this.dy - ray.dy) > eps) {
            return false;
        }
        return true;
    }

    public String toString() {
        return new String("Ray2D(" + this.x0 + "," + this.y0 + "," + this.dx + "," + this.dy + ")");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ray2D)) {
            return false;
        }
        Ray2D that = (Ray2D) obj;
        if (!EqualUtils.areEqual(this.x0, that.x0)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.y0, that.y0)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.dx, that.dx)) {
            return false;
        }
        if (EqualUtils.areEqual(this.dy, that.dy)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((Double.valueOf(this.x0).hashCode() + 31) * 31) + Double.valueOf(this.y0).hashCode()) * 31) + Double.valueOf(this.dx).hashCode()) * 31) + Double.valueOf(this.dy).hashCode();
    }

    @Deprecated
    public Ray2D clone() {
        return new Ray2D(this.x0, this.y0, this.dx, this.dy);
    }

    public Vector2D leftTangent(double t) {
        return null;
    }

    public Vector2D rightTangent(double t) {
        return null;
    }

    public LinearCurve2D asPolyline(int n) {
        return null;
    }

    public Path appendPath(Path path) {
        return path;
    }
}
