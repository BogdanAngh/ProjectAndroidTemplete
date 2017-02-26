package com.example.duy.calculator.geom2d.conic;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.line.Ray2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.polygon.Polyline2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import java.util.Collection;
import java.util.Locale;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public class CircleArc2D extends AbstractSmoothCurve2D implements CircularShape2D, Cloneable {
    protected double angleExtent;
    protected Circle2D circle;
    protected double startAngle;

    public CircleArc2D() {
        this(0.0d, 0.0d, 1.0d, 0.0d, (double) Angle2D.M_PI_2);
    }

    public CircleArc2D(Circle2D circle, double startAngle, double angleExtent) {
        this(circle.xc, circle.yc, circle.r, startAngle, angleExtent);
    }

    public CircleArc2D(Circle2D circle, double startAngle, double endAngle, boolean direct) {
        this(circle.xc, circle.yc, circle.r, startAngle, endAngle, direct);
    }

    public CircleArc2D(Point2D center, double radius, double startAngle, double angleExtent) {
        this(center.x(), center.y(), radius, startAngle, angleExtent);
    }

    public CircleArc2D(Point2D center, double radius, double start, double end, boolean direct) {
        this(center.x(), center.y(), radius, start, end, direct);
    }

    public CircleArc2D(double xc, double yc, double r, double startAngle, double endAngle, boolean direct) {
        this.startAngle = 0.0d;
        this.angleExtent = FastMath.PI;
        this.circle = new Circle2D(xc, yc, r);
        this.startAngle = startAngle;
        this.angleExtent = endAngle;
        this.angleExtent = Angle2D.formatAngle(endAngle - startAngle);
        if (!direct) {
            this.angleExtent -= Angle2D.M_2PI;
        }
    }

    public CircleArc2D(double xc, double yc, double r, double start, double extent) {
        this.startAngle = 0.0d;
        this.angleExtent = FastMath.PI;
        this.circle = new Circle2D(xc, yc, r);
        this.startAngle = start;
        this.angleExtent = extent;
    }

    @Deprecated
    public static CircleArc2D create(Circle2D support, double startAngle, double angleExtent) {
        return new CircleArc2D(support, startAngle, angleExtent);
    }

    @Deprecated
    public static CircleArc2D create(Circle2D support, double startAngle, double endAngle, boolean direct) {
        return new CircleArc2D(support, startAngle, endAngle, direct);
    }

    @Deprecated
    public static CircleArc2D create(Point2D center, double radius, double startAngle, double angleExtent) {
        return new CircleArc2D(center, radius, startAngle, angleExtent);
    }

    @Deprecated
    public static CircleArc2D create(Point2D center, double radius, double startAngle, double endAngle, boolean direct) {
        return new CircleArc2D(center, radius, startAngle, endAngle, direct);
    }

    private static double btan(double increment) {
        increment /= 2.0d;
        return (1.3333333333333333d * Math.sin(increment)) / (1.0d + Math.cos(increment));
    }

    public boolean isDirect() {
        return this.angleExtent >= 0.0d;
    }

    public double getStartAngle() {
        return this.startAngle;
    }

    public double getAngleExtent() {
        return this.angleExtent;
    }

    public double getArea() {
        return (FastMath.PI * Math.pow(this.circle.radius(), 2.0d)) / Math.abs(12.566370614359172d / this.angleExtent);
    }

    public double getChordArea() {
        if (Angle2D.M_2PI == this.angleExtent) {
            return getArea();
        }
        return ((this.circle.r * this.circle.r) * (this.angleExtent - Math.sin(this.angleExtent))) / 2.0d;
    }

    public boolean containsAngle(double angle) {
        return Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, angle, this.angleExtent >= 0.0d);
    }

    public double getAngle(double position) {
        if (position < 0.0d) {
            position = 0.0d;
        }
        if (position > Math.abs(this.angleExtent)) {
            position = Math.abs(this.angleExtent);
        }
        if (this.angleExtent < 0.0d) {
            position = -position;
        }
        return Angle2D.formatAngle(this.startAngle + position);
    }

    private double positionToAngle(double t) {
        if (t > Math.abs(this.angleExtent)) {
            t = Math.abs(this.angleExtent);
        }
        if (t < 0.0d) {
            t = 0.0d;
        }
        if (this.angleExtent < 0.0d) {
            t = -t;
        }
        return t + this.startAngle;
    }

    public Circle2D supportingCircle() {
        return this.circle;
    }

    public CircleArc2D parallel(double dist) {
        double r = this.circle.radius();
        return new CircleArc2D(this.circle.center(), Math.max(this.angleExtent > 0.0d ? r + dist : r - dist, 0.0d), this.startAngle, this.angleExtent);
    }

    public double length() {
        return this.circle.radius() * Math.abs(this.angleExtent);
    }

    public double length(double pos) {
        return this.circle.radius() * pos;
    }

    public double position(double length) {
        return length / this.circle.radius();
    }

    public double windingAngle(Point2D point) {
        Point2D p1 = firstPoint();
        Point2D p2 = lastPoint();
        double angle1 = Angle2D.horizontalAngle(point, p1);
        double angle2 = Angle2D.horizontalAngle(point, p2);
        boolean b1 = new StraightLine2D(p1, p2).isInside(point);
        boolean b2 = this.circle.isInside(point);
        if (this.angleExtent > 0.0d) {
            if (b1 || b2) {
                if (angle2 > angle1) {
                    return angle2 - angle1;
                }
                return (Angle2D.M_2PI - angle1) + angle2;
            } else if (angle2 > angle1) {
                return (angle2 - angle1) - Angle2D.M_2PI;
            } else {
                return angle2 - angle1;
            }
        } else if (!b1 || b2) {
            if (angle1 > angle2) {
                return angle2 - angle1;
            }
            return (angle2 - angle1) - Angle2D.M_2PI;
        } else if (angle1 > angle2) {
            return (angle2 - angle1) + Angle2D.M_2PI;
        } else {
            return angle2 - angle1;
        }
    }

    public boolean isInside(Point2D point) {
        return signedDistance(point.x(), point.y()) < 0.0d;
    }

    public double signedDistance(Point2D p) {
        return signedDistance(p.x(), p.y());
    }

    public double signedDistance(double x, double y) {
        double dist = distance(x, y);
        Point2D point = new Point2D(x, y);
        boolean direct = this.angleExtent > 0.0d;
        if (this.circle.isInside(point)) {
            return direct ? -dist : dist;
        } else {
            Point2D p1 = this.circle.point(this.startAngle);
            Point2D p2 = this.circle.point(this.startAngle + this.angleExtent);
            boolean onLeft = new StraightLine2D(p1, p2).isInside(point);
            if (direct && !onLeft) {
                return dist;
            }
            if (!direct && onLeft) {
                return -dist;
            }
            boolean left1 = new Ray2D(p1, this.circle.tangent(this.startAngle)).isInside(point);
            if (direct && !left1) {
                return dist;
            }
            if (!direct && left1) {
                return -dist;
            }
            boolean left2 = new Ray2D(p2, this.circle.tangent(this.startAngle + this.angleExtent)).isInside(point);
            if (direct && !left2) {
                return dist;
            }
            if (!direct && left2) {
                return -dist;
            }
            if (direct) {
                return -dist;
            }
            return dist;
        }
    }

    public Vector2D tangent(double t) {
        t = positionToAngle(t);
        double r = this.circle.radius();
        if (this.angleExtent > 0.0d) {
            return new Vector2D((-r) * Math.sin(t), Math.cos(t) * r);
        }
        return new Vector2D(Math.sin(t) * r, (-r) * Math.cos(t));
    }

    public double curvature(double t) {
        double kappa = this.circle.curvature(t);
        return isDirect() ? kappa : -kappa;
    }

    public boolean isClosed() {
        return false;
    }

    public Vector2D leftTangent(double t) {
        return null;
    }

    public Vector2D rightTangent(double t) {
        return null;
    }

    public LinearCurve2D asPolyline(int n) {
        double dt = Math.abs(this.angleExtent) / ((double) n);
        Point2D[] points = new Point2D[(n + 1)];
        for (int i = 0; i < n + 1; i++) {
            points[i] = point(((double) i) * dt);
        }
        return new Polyline2D(points);
    }

    public double t0() {
        return 0.0d;
    }

    @Deprecated
    public double getT0() {
        return 0.0d;
    }

    public double t1() {
        return Math.abs(this.angleExtent);
    }

    @Deprecated
    public double getT1() {
        return Math.abs(this.angleExtent);
    }

    public Point2D point(double t) {
        return this.circle.point(positionToAngle(t));
    }

    public Point2D firstPoint() {
        return null;
    }

    public Point2D lastPoint() {
        return null;
    }

    public double position(Point2D point) {
        double angle = Angle2D.horizontalAngle(this.circle.center(), point);
        if (containsAngle(angle)) {
            if (this.angleExtent > 0.0d) {
                return Angle2D.formatAngle(angle - this.startAngle);
            }
            return Angle2D.formatAngle(this.startAngle - angle);
        } else if (firstPoint().distance(point) >= lastPoint().distance(point)) {
            return Math.abs(this.angleExtent);
        } else {
            return 0.0d;
        }
    }

    public Collection<Point2D> intersections(LineSegment2D line) {
        return Circle2D.lineCircleIntersections(line, this);
    }

    public double project(Point2D point) {
        double angle = this.circle.project(point);
        if (!Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, angle, this.angleExtent > 0.0d)) {
            if (firstPoint().distance(point) < lastPoint().distance(point)) {
                return 0.0d;
            }
            return Math.abs(this.angleExtent);
        } else if (this.angleExtent > 0.0d) {
            return Angle2D.formatAngle(angle - this.startAngle);
        } else {
            return Angle2D.formatAngle(this.startAngle - angle);
        }
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return null;
    }

    public CircleArc2D subCurve(double t0, double t1) {
        boolean z;
        if (this.angleExtent > 0.0d) {
            t0 = Angle2D.formatAngle(this.startAngle + t0);
            t1 = Angle2D.formatAngle(this.startAngle + t1);
        } else {
            t0 = Angle2D.formatAngle(this.startAngle - t0);
            t1 = Angle2D.formatAngle(this.startAngle - t1);
        }
        if (!Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, t0, this.angleExtent > 0.0d)) {
            t0 = this.startAngle;
        }
        if (!Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, t1, this.angleExtent > 0.0d)) {
            t1 = Angle2D.formatAngle(this.startAngle + this.angleExtent);
        }
        Circle2D circle2D = this.circle;
        if (this.angleExtent > 0.0d) {
            z = true;
        } else {
            z = false;
        }
        return new CircleArc2D(circle2D, t0, t1, z);
    }

    public CircleArc2D reverse() {
        return new CircleArc2D(this.circle, Angle2D.formatAngle(this.startAngle + this.angleExtent), -this.angleExtent);
    }

    public double distance(Point2D p) {
        return distance(p.x(), p.y());
    }

    public double distance(double x, double y) {
        if (containsAngle(Angle2D.horizontalAngle(this.circle.xc, this.circle.yc, x, y))) {
            return Math.abs(Point2D.distance(this.circle.xc, this.circle.yc, x, y) - this.circle.r);
        }
        return Math.min(firstPoint().distance(x, y), lastPoint().distance(x, y));
    }

    public boolean isBounded() {
        return true;
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(double x, double y) {
        if (Math.abs(Point2D.distance(this.circle.xc, this.circle.yc, x, y) - this.circle.radius()) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return containsAngle(Angle2D.horizontalAngle(this.circle.xc, this.circle.yc, x, y));
    }

    public boolean isEmpty() {
        return false;
    }

    public Path appendPath(Path path) {
        int nSeg = Math.min((int) Math.ceil(Math.abs(this.angleExtent) / Angle2D.M_PI_2), 4);
        double ext = this.angleExtent / ((double) nSeg);
        double k = btan(Math.abs(ext));
        for (int i = 0; i < nSeg; i++) {
            double ti0 = Math.abs(((double) i) * ext);
            double ti1 = Math.abs(((double) (i + 1)) * ext);
            Point2D p1 = point(ti0);
            Point2D p2 = point(ti1);
            Vector2D v1 = tangent(ti0).times(k);
            Vector2D v2 = tangent(ti1).times(k);
            Path path2 = path;
            path2.rCubicTo((float) (p1.x() + v1.x()), (float) (p1.y() + v1.y()), (float) (p2.x() - v2.x()), (float) (p2.y() - v2.y()), (float) p2.x(), (float) p2.y());
        }
        return path;
    }

    public Path getGeneralPath() {
        Path path = new Path();
        Point2D point = firstPoint();
        path.moveTo((float) point.x(), (float) point.y());
        return appendPath(path);
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CircleArc2D)) {
            return super.equals(obj);
        }
        CircleArc2D arc = (CircleArc2D) obj;
        if (Math.abs(this.circle.xc - arc.circle.xc) > eps) {
            return false;
        }
        if (Math.abs(this.circle.yc - arc.circle.yc) > eps) {
            return false;
        }
        if (Math.abs(this.circle.r - arc.circle.r) > eps) {
            return false;
        }
        if (Math.abs(this.circle.theta - arc.circle.theta) > eps) {
            return false;
        }
        if (Math.abs(Angle2D.formatAngle(this.startAngle) - Angle2D.formatAngle(arc.startAngle)) > eps) {
            return false;
        }
        if (Math.abs(Angle2D.formatAngle(this.angleExtent) - Angle2D.formatAngle(arc.angleExtent)) > eps) {
            return false;
        }
        return true;
    }

    public String toString() {
        Point2D center = this.circle.center();
        return String.format(Locale.US, "CircleArc2D(%7.2f,%7.2f,%7.2f,%7.5f,%7.5f)", new Object[]{Double.valueOf(center.x()), Double.valueOf(center.y()), Double.valueOf(this.circle.radius()), Double.valueOf(getStartAngle()), Double.valueOf(getAngleExtent())});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CircleArc2D)) {
            return false;
        }
        CircleArc2D that = (CircleArc2D) obj;
        if (!this.circle.equals(that.circle)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.startAngle, that.startAngle)) {
            return false;
        }
        if (EqualUtils.areEqual(this.angleExtent, that.angleExtent)) {
            return true;
        }
        return false;
    }

    public StraightLine2D supportingLine() {
        return null;
    }

    public double horizontalAngle() {
        return 0.0d;
    }

    public Point2D origin() {
        return null;
    }

    public Vector2D direction() {
        return null;
    }

    public boolean containsProjection(Point2D point) {
        return false;
    }
}
