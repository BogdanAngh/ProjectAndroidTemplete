package com.example.duy.calculator.geom2d.line;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.Curve2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import com.example.duy.calculator.geom2d.util.UnboundedShape2DException;
import java.util.ArrayList;
import java.util.Collection;

public class StraightLine2D extends AbstractLine2D implements Cloneable, Curve2D {
    public static final double ACCURACY = 1.0E-12d;

    public StraightLine2D() {
        this(0.0d, 0.0d, 1.0d, 0.0d);
    }

    public StraightLine2D(Point2D point1, Point2D point2) {
        this(point1, new Vector2D(point1, point2));
    }

    public StraightLine2D(Point2D point, Vector2D direction) {
        this(point.x(), point.y(), direction.x(), direction.y());
    }

    public StraightLine2D(Point2D point, double dx, double dy) {
        this(point.x(), point.y(), dx, dy);
    }

    public StraightLine2D(Point2D point, double angle) {
        this(point.x(), point.y(), Math.cos(angle), Math.sin(angle));
    }

    public StraightLine2D(double xp, double yp, double dx, double dy) {
        super(xp, yp, dx, dy);
    }

    public StraightLine2D(LinearShape2D line) {
        this(line.origin(), line.direction());
    }

    public StraightLine2D(LinearShape2D line, Point2D point) {
        this(point, line.direction());
    }

    public StraightLine2D(double a, double b, double c) {
        this(0.0d, 0.0d, 1.0d, 0.0d);
        double d = (a * a) + (b * b);
        this.x0 = ((-a) * c) / d;
        this.y0 = ((-b) * c) / d;
        double theta = Math.atan2(-a, b);
        this.dx = Math.cos(theta);
        this.dy = Math.sin(theta);
    }

    public static Point2D getMidPoint(Line2D line2D) {
        return Point2D.midPoint(line2D.p1, line2D.p2);
    }

    @Deprecated
    public static StraightLine2D create(Point2D point, double angle) {
        return new StraightLine2D(point.x(), point.y(), Math.cos(angle), Math.sin(angle));
    }

    @Deprecated
    public static StraightLine2D create(Point2D p1, Point2D p2) {
        return new StraightLine2D(p1, p2);
    }

    @Deprecated
    public static StraightLine2D create(Point2D origin, Vector2D direction) {
        return new StraightLine2D(origin, direction);
    }

    public static StraightLine2D createHorizontal(Point2D origin) {
        return new StraightLine2D(origin, new Vector2D(1.0d, 0.0d));
    }

    public static StraightLine2D createVertical(Point2D origin) {
        return new StraightLine2D(origin, new Vector2D(0.0d, 1.0d));
    }

    public static StraightLine2D createMedian(Point2D p1, Point2D p2) {
        return createPerpendicular(create(p1, p2), Point2D.midPoint(p1, p2));
    }

    public static StraightLine2D createParallel(LinearShape2D line, Point2D point) {
        return new StraightLine2D(line, point);
    }

    public static StraightLine2D createParallel(LinearShape2D linear, double d) {
        StraightLine2D line = linear.supportingLine();
        double d2 = d / Math.hypot(line.dx, line.dy);
        return new StraightLine2D(line.x0 + (line.dy * d2), line.y0 - (line.dx * d2), line.dx, line.dy);
    }

    public static StraightLine2D createPerpendicular(LinearShape2D linear, Point2D point) {
        StraightLine2D line = linear.supportingLine();
        return new StraightLine2D(point, -line.dy, line.dx);
    }

    public static StraightLine2D createCartesian(double a, double b, double c) {
        return new StraightLine2D(a, b, c);
    }

    public static Point2D getIntersection(Point2D p1, Point2D p2, Point2D p3, Point2D p4) {
        return new StraightLine2D(p1, p2).intersection(new StraightLine2D(p3, p4));
    }

    public StraightLine2D parallel(Point2D point) {
        return new StraightLine2D(point, this.dx, this.dy);
    }

    public StraightLine2D parallel(double d) {
        double d2 = Math.hypot(this.dx, this.dy);
        if (Math.abs(d2) < ACCURACY) {
            throw new DegeneratedLine2DException("Can not compute parallel of degenerated line", this);
        }
        d2 = d / d2;
        return new StraightLine2D(this.x0 + (this.dy * d2), this.y0 - (this.dx * d2), this.dx, this.dy);
    }

    public StraightLine2D perpendicular(Point2D point) {
        return new StraightLine2D(point, -this.dy, this.dx);
    }

    public double windingAngle(Point2D point) {
        double angle1 = Angle2D.horizontalAngle(-this.dx, -this.dy);
        double angle2 = Angle2D.horizontalAngle(this.dx, this.dy);
        if (isInside(point)) {
            if (angle2 > angle1) {
                return angle2 - angle1;
            }
            return (Angle2D.M_2PI - angle1) + angle2;
        } else if (angle2 > angle1) {
            return (angle2 - angle1) - Angle2D.M_2PI;
        } else {
            return angle2 - angle1;
        }
    }

    public Vector2D leftTangent(double t) {
        return null;
    }

    public Vector2D rightTangent(double t) {
        return null;
    }

    public LinearCurve2D asPolyline(int n) {
        throw new UnboundedShape2DException(this);
    }

    public Point2D firstPoint() {
        throw new UnboundedShape2DException(this);
    }

    public Point2D lastPoint() {
        throw new UnboundedShape2DException(this);
    }

    public Collection<Point2D> singularPoints() {
        return new ArrayList(0);
    }

    public boolean isSingular(double pos) {
        return false;
    }

    public Curve2D clone() {
        return null;
    }

    public double t0() {
        return Double.NEGATIVE_INFINITY;
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

    public Point2D point(double t) {
        return new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
    }

    public StraightLine2D reverse() {
        return new StraightLine2D(this.x0, this.y0, -this.dx, -this.dy);
    }

    public Path appendPath(Path path) {
        throw new UnboundedShape2DException(this);
    }

    public boolean isBounded() {
        return false;
    }

    public double distance(double x, double y) {
        return super.projectedPoint(x, y).distance(x, y);
    }

    public boolean contains(double x, double y) {
        return super.supportContains(x, y);
    }

    public boolean contains(Point2D p) {
        return super.supportContains(p.x(), p.y());
    }

    public Path getPath() {
        throw new UnboundedShape2DException(this);
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StraightLine2D)) {
            return false;
        }
        StraightLine2D line = (StraightLine2D) obj;
        if (Math.abs(this.x0 - line.x0) > eps) {
            return false;
        }
        if (Math.abs(this.y0 - line.y0) > eps) {
            return false;
        }
        if (Math.abs(this.dx - line.dx) > eps) {
            return false;
        }
        if (Math.abs(this.dy - line.dy) > eps) {
            return false;
        }
        return true;
    }

    public String toString() {
        return new String("StraightLine2D(" + this.x0 + "," + this.y0 + "," + this.dx + "," + this.dy + ")");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StraightLine2D)) {
            return false;
        }
        StraightLine2D that = (StraightLine2D) obj;
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

    public boolean isInside(Point2D pt) {
        return false;
    }
}
