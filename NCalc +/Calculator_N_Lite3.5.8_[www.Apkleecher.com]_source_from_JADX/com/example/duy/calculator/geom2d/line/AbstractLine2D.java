package com.example.duy.calculator.geom2d.line;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractLine2D extends AbstractSmoothCurve2D implements LinearShape2D {
    protected double dx;
    protected double dy;
    protected double x0;
    protected double y0;

    protected AbstractLine2D(double x0, double y0, double dx, double dy) {
        this.x0 = x0;
        this.y0 = y0;
        this.dx = dx;
        this.dy = dy;
    }

    protected AbstractLine2D(Point2D point, Vector2D vector) {
        this.x0 = point.x();
        this.y0 = point.y();
        this.dx = vector.x();
        this.dy = vector.y();
    }

    public static Point2D getIntersection(AbstractLine2D line1, AbstractLine2D line2) {
        double denom = (line1.dx * line2.dy) - (line1.dy * line2.dx);
        if (Math.abs(denom) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return null;
        }
        double t = (((line1.y0 - line2.y0) * line2.dx) - ((line1.x0 - line2.x0) * line2.dy)) / denom;
        return new Point2D(line1.x0 + (line1.dx * t), line1.y0 + (line1.dy * t));
    }

    public static boolean isColinear(AbstractLine2D line1, AbstractLine2D line2) {
        if (Math.abs((line1.dx * line2.dy) - (line1.dy * line2.dx)) <= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE && Math.abs(((line2.y0 - line1.y0) * line2.dx) - ((line2.x0 - line1.x0) * line2.dy)) / Math.hypot(line2.dx, line2.dy) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return true;
        }
        return false;
    }

    public static boolean isParallel(AbstractLine2D line1, AbstractLine2D line2) {
        return Math.abs((line1.dx * line2.dy) - (line1.dy * line2.dx)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public boolean isColinear(LinearShape2D linear) {
        if (!isParallel(linear)) {
            return false;
        }
        StraightLine2D line = linear.supportingLine();
        if (Math.abs(this.dx) > Math.abs(this.dy)) {
            if (Math.abs(((((line.x0 - this.x0) * this.dy) / this.dx) + this.y0) - line.y0) <= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                return true;
            }
            return false;
        } else if (Math.abs(((((line.y0 - this.y0) * this.dx) / this.dy) + this.x0) - line.x0) <= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isParallel(LinearShape2D line) {
        return Vector2D.isColinear(direction(), line.direction());
    }

    protected boolean supportContains(double x, double y) {
        double denom = Math.hypot(this.dx, this.dy);
        if (denom >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return Math.abs(((x - this.x0) * this.dy) - ((y - this.y0) * this.dx)) / denom < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
        } else {
            throw new DegeneratedLine2DException(this);
        }
    }

    public double[][] parametric() {
        double[][] tab = (double[][]) Array.newInstance(Double.TYPE, new int[]{2, 2});
        tab[0][0] = this.x0;
        tab[0][1] = this.dx;
        tab[1][0] = this.y0;
        tab[1][1] = this.dy;
        return tab;
    }

    public double[] cartesianEquation() {
        return new double[]{this.dy, -this.dx, (this.dx * this.y0) - (this.dy * this.x0)};
    }

    public double[] polarCoefficients() {
        tab = new double[2];
        double d = signedDistance(0.0d, 0.0d);
        tab[0] = Math.abs(d);
        if (d > 0.0d) {
            tab[1] = (horizontalAngle() + FastMath.PI) % Angle2D.M_2PI;
        } else {
            tab[1] = horizontalAngle();
        }
        return tab;
    }

    public double[] polarCoefficientsSigned() {
        return new double[]{signedDistance(0.0d, 0.0d), horizontalAngle()};
    }

    public double positionOnLine(Point2D point) {
        return positionOnLine(point.x(), point.y());
    }

    public double positionOnLine(double x, double y) {
        double denom = (this.dx * this.dx) + (this.dy * this.dy);
        if (Math.abs(denom) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return (((y - this.y0) * this.dy) + ((x - this.x0) * this.dx)) / denom;
        }
        throw new DegeneratedLine2DException(this);
    }

    public Point2D projectedPoint(Point2D p) {
        return projectedPoint(p.x(), p.y());
    }

    public Point2D projectedPoint(double x, double y) {
        if (contains(x, y)) {
            return new Point2D(x, y);
        }
        double t = positionOnLine(x, y);
        return new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
    }

    public Point2D getSymmetric(Point2D p) {
        return getSymmetric(p.x(), p.y());
    }

    public Point2D getSymmetric(double x, double y) {
        double t = 2.0d * positionOnLine(x, y);
        return new Point2D(((this.x0 * 2.0d) + (this.dx * t)) - x, ((this.y0 * 2.0d) + (this.dy * t)) - y);
    }

    public StraightLine2D parallel(Point2D point) {
        return new StraightLine2D(point, this.dx, this.dy);
    }

    public StraightLine2D perpendicular(Point2D point) {
        return new StraightLine2D(point, -this.dy, this.dx);
    }

    public Point2D origin() {
        return new Point2D(this.x0, this.y0);
    }

    public Vector2D direction() {
        return new Vector2D(this.dx, this.dy);
    }

    public double horizontalAngle() {
        return (Math.atan2(this.dy, this.dx) + Angle2D.M_2PI) % Angle2D.M_2PI;
    }

    public Point2D intersection(LinearShape2D line) {
        Vector2D vect = line.direction();
        double dx2 = vect.x();
        double dy2 = vect.y();
        double denom = (this.dx * dy2) - (this.dy * dx2);
        if (Math.abs(denom) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return null;
        }
        Point2D origin = line.origin();
        double x2 = origin.x();
        double t = (((this.y0 - origin.y()) * dx2) - ((this.x0 - x2) * dy2)) / denom;
        Point2D point = new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
        return (containsProjection(point) && line.containsProjection(point)) ? point : null;
    }

    public StraightLine2D supportingLine() {
        return new StraightLine2D(this);
    }

    public boolean containsProjection(Point2D point) {
        double pos = positionOnLine(point);
        return pos > t0() - OpenMapRealVector.DEFAULT_ZERO_TOLERANCE && pos < t1() + OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public double length(double pos) {
        return Math.hypot(this.dx, this.dy) * pos;
    }

    public double position(double distance) {
        double delta = Math.hypot(this.dx, this.dy);
        if (delta >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return distance / delta;
        }
        throw new DegeneratedLine2DException(this);
    }

    public double windingAngle(Point2D point) {
        double angle1;
        double angle2;
        double t0 = t0();
        double t1 = t1();
        if (t0 == Double.NEGATIVE_INFINITY) {
            angle1 = Angle2D.horizontalAngle(-this.dx, -this.dy);
        } else {
            angle1 = Angle2D.horizontalAngle(point.x(), point.y(), this.x0 + (this.dx * t0), this.y0 + (this.dy * t0));
        }
        if (t1 == Double.POSITIVE_INFINITY) {
            angle2 = Angle2D.horizontalAngle(this.dx, this.dy);
        } else {
            angle2 = Angle2D.horizontalAngle(point.x(), point.y(), this.x0 + (this.dx * t1), this.y0 + (this.dy * t1));
        }
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

    public double signedDistance(Point2D p) {
        return signedDistance(p.x(), p.y());
    }

    public double signedDistance(double x, double y) {
        double delta = Math.hypot(this.dx, this.dy);
        if (delta >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return (((x - this.x0) * this.dy) - ((y - this.y0) * this.dx)) / delta;
        }
        throw new DegeneratedLine2DException(this);
    }

    public boolean isInside(Point2D p) {
        return ((p.x() - this.x0) * this.dy) - ((p.y() - this.y0) * this.dx) < 0.0d;
    }

    public Vector2D tangent(double t) {
        return new Vector2D(this.dx, this.dy);
    }

    public double curvature(double t) {
        return 0.0d;
    }

    public boolean isClosed() {
        return false;
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        if (isParallel(line)) {
            return new ArrayList(0);
        }
        Collection<Point2D> points = new ArrayList(1);
        Point2D point = intersection(line);
        if (point == null) {
            return points;
        }
        points.add(point);
        return points;
    }

    public double position(Point2D point) {
        double pos = positionOnLine(point);
        double eps = Math.hypot(this.dx, this.dy) * OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
        if (pos < t0() - eps) {
            return Double.NaN;
        }
        if (pos > t1() + eps) {
            return Double.NaN;
        }
        return pos;
    }

    public double project(Point2D point) {
        return Math.min(Math.max(positionOnLine(point), t0()), t1());
    }

    public AbstractLine2D subCurve(double t0, double t1) {
        t0 = Math.max(t0, t0());
        t1 = Math.min(t1, t1());
        if (Double.isInfinite(t1)) {
            if (Double.isInfinite(t0)) {
                return new StraightLine2D(this);
            }
            return new Ray2D(point(t0), direction());
        } else if (Double.isInfinite(t0)) {
            return new InvertedRay2D(point(t1), direction());
        } else {
            return new LineSegment2D(point(t0), point(t1));
        }
    }

    public double distance(Point2D p) {
        return distance(p.x(), p.y());
    }

    public double distance(double x, double y) {
        Point2D proj = projectedPoint(x, y);
        if (contains(proj)) {
            return proj.distance(x, y);
        }
        double dist = Double.POSITIVE_INFINITY;
        if (!Double.isInfinite(t0())) {
            dist = firstPoint().distance(x, y);
        }
        if (Double.isInfinite(t1())) {
            return dist;
        }
        return Math.min(dist, lastPoint().distance(x, y));
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean isEmpty() {
        return Math.hypot(this.dx, this.dy) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }
}
