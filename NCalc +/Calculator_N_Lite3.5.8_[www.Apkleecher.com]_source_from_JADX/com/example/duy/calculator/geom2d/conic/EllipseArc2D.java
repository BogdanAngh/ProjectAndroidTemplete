package com.example.duy.calculator.geom2d.conic;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.line.Ray2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.polygon.Polyline2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public class EllipseArc2D extends AbstractSmoothCurve2D {
    protected double angleExtent;
    protected Ellipse2D ellipse;
    protected double startAngle;

    public EllipseArc2D() {
        this(0.0d, 0.0d, 1.0d, 1.0d, 0.0d, 0.0d, Angle2D.M_PI_2);
    }

    public EllipseArc2D(Ellipse2D ell, double start, double extent) {
        this(ell.xc, ell.yc, ell.r1, ell.r2, ell.theta, start, extent);
    }

    public EllipseArc2D(Ellipse2D ell, double start, double end, boolean direct) {
        this(ell.xc, ell.yc, ell.r1, ell.r2, ell.theta, start, end, direct);
    }

    public EllipseArc2D(double xc, double yc, double a, double b, double theta, double start, double extent) {
        this.startAngle = 0.0d;
        this.angleExtent = FastMath.PI;
        this.ellipse = new Ellipse2D(xc, yc, a, b, theta);
        this.startAngle = start;
        this.angleExtent = extent;
    }

    public EllipseArc2D(double xc, double yc, double a, double b, double theta, double start, double end, boolean direct) {
        this.startAngle = 0.0d;
        this.angleExtent = FastMath.PI;
        this.ellipse = new Ellipse2D(xc, yc, a, b, theta);
        this.startAngle = start;
        this.angleExtent = Angle2D.formatAngle(end - start);
        if (!direct) {
            this.angleExtent -= Angle2D.M_2PI;
        }
    }

    @Deprecated
    public static EllipseArc2D create(Ellipse2D ell, double start, double extent) {
        return new EllipseArc2D(ell.xc, ell.yc, ell.r1, ell.r2, ell.theta, start, extent);
    }

    @Deprecated
    public static EllipseArc2D create(Ellipse2D ell, double start, double end, boolean direct) {
        return new EllipseArc2D(ell.xc, ell.yc, ell.r1, ell.r2, ell.theta, start, end, direct);
    }

    private static double btan(double increment) {
        increment /= 2.0d;
        return (1.3333333333333333d * Math.sin(increment)) / (1.0d + Math.cos(increment));
    }

    public Ellipse2D getSupportingEllipse() {
        return this.ellipse;
    }

    public double getStartAngle() {
        return this.startAngle;
    }

    public double getAngleExtent() {
        return this.angleExtent;
    }

    public boolean isDirect() {
        return this.angleExtent >= 0.0d;
    }

    public boolean containsAngle(double angle) {
        return Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, angle, this.angleExtent > 0.0d);
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

    public double windingAngle(Point2D point) {
        Point2D p1 = point(0.0d);
        Point2D p2 = point(Math.abs(this.angleExtent));
        double angle1 = Angle2D.horizontalAngle(point, p1);
        double angle2 = Angle2D.horizontalAngle(point, p2);
        boolean b1 = new StraightLine2D(p1, p2).isInside(point);
        boolean b2 = this.ellipse.isInside(point);
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

    public boolean isInside(Point2D p) {
        return signedDistance(p.x(), p.y()) < 0.0d;
    }

    public double signedDistance(Point2D p) {
        return signedDistance(p.x(), p.y());
    }

    public double signedDistance(double x, double y) {
        boolean direct = this.angleExtent >= 0.0d;
        double dist = distance(x, y);
        Point2D point2D = new Point2D(x, y);
        if (this.ellipse.isInside(point2D)) {
            return this.angleExtent > 0.0d ? -dist : dist;
        } else {
            Point2D p1 = point(this.startAngle);
            double endAngle = this.startAngle + this.angleExtent;
            Point2D p2 = point(endAngle);
            boolean onLeft = new StraightLine2D(p1, p2).isInside(point2D);
            if (direct && !onLeft) {
                return dist;
            }
            if (!direct && onLeft) {
                return -dist;
            }
            boolean left1 = new Ray2D(p1, -Math.sin(this.startAngle), Math.cos(this.startAngle)).isInside(point2D);
            if (direct && !left1) {
                return dist;
            }
            if (!direct && left1) {
                return -dist;
            }
            boolean left2 = new Ray2D(p2, -Math.sin(endAngle), Math.cos(endAngle)).isInside(point2D);
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
        t = Math.min(Math.max(0.0d, t), Math.abs(this.angleExtent));
        if (this.angleExtent < 0.0d) {
            return this.ellipse.tangent(this.startAngle - t).times(-1.0d);
        }
        return this.ellipse.tangent(this.startAngle + t);
    }

    public double curvature(double t) {
        if (this.angleExtent < 0.0d) {
            t = this.startAngle - t;
        } else {
            t += this.startAngle;
        }
        double kappa = this.ellipse.curvature(t);
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

    public Polyline2D asPolyline(int n) {
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
        return t0();
    }

    public double t1() {
        return Math.abs(this.angleExtent);
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D point(double t) {
        t = Math.min(Math.max(t, 0.0d), Math.abs(this.angleExtent));
        if (this.angleExtent < 0.0d) {
            t = this.startAngle - t;
        } else {
            t += this.startAngle;
        }
        return this.ellipse.point(t);
    }

    public Point2D firstPoint() {
        return null;
    }

    public Point2D lastPoint() {
        return null;
    }

    public double position(Point2D point) {
        double angle = Angle2D.horizontalAngle(this.ellipse.center(), point);
        if (!containsAngle(angle)) {
            return Double.NaN;
        }
        if (this.angleExtent > 0.0d) {
            return Angle2D.formatAngle(angle - this.startAngle);
        }
        return Angle2D.formatAngle(this.startAngle - angle);
    }

    public double project(Point2D point) {
        double angle = this.ellipse.project(point);
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

    public Collection<Point2D> intersections(LinearShape2D line) {
        ArrayList<Point2D> array = new ArrayList();
        for (Point2D point : this.ellipse.intersections(line)) {
            if (contains(point)) {
                array.add(point);
            }
        }
        return array;
    }

    public EllipseArc2D reverse() {
        return new EllipseArc2D(this.ellipse, Angle2D.formatAngle(this.startAngle + this.angleExtent), -this.angleExtent);
    }

    public EllipseArc2D subCurve(double t0, double t1) {
        boolean z;
        t0 = Angle2D.formatAngle(this.startAngle + t0);
        t1 = Angle2D.formatAngle(this.startAngle + t1);
        if (!Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, t0, this.angleExtent > 0.0d)) {
            t0 = this.startAngle;
        }
        if (!Angle2D.containsAngle(this.startAngle, this.startAngle + this.angleExtent, t1, this.angleExtent > 0.0d)) {
            t1 = this.angleExtent;
        }
        Ellipse2D ellipse2D = this.ellipse;
        if (this.angleExtent > 0.0d) {
            z = true;
        } else {
            z = false;
        }
        return new EllipseArc2D(ellipse2D, t0, t1, z);
    }

    public double distance(Point2D point) {
        return distance(point.x(), point.y());
    }

    public double distance(double x, double y) {
        return point(project(new Point2D(x, y))).distance(x, y);
    }

    public boolean isBounded() {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(double x, double y) {
        return distance(x, y) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public boolean contains(Point2D point) {
        return contains(point.x(), point.y());
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
        if (!(obj instanceof EllipseArc2D)) {
            return false;
        }
        EllipseArc2D arc = (EllipseArc2D) obj;
        if (Math.abs(this.ellipse.xc - arc.ellipse.xc) > eps) {
            return false;
        }
        if (Math.abs(this.ellipse.yc - arc.ellipse.yc) > eps) {
            return false;
        }
        if (Math.abs(this.ellipse.r1 - arc.ellipse.r1) > eps) {
            return false;
        }
        if (Math.abs(this.ellipse.r2 - arc.ellipse.r2) > eps) {
            return false;
        }
        if (Math.abs(this.ellipse.theta - arc.ellipse.theta) > eps) {
            return false;
        }
        if (!Angle2D.equals(this.startAngle, arc.startAngle)) {
            return false;
        }
        if (Angle2D.equals(this.angleExtent, arc.angleExtent)) {
            return true;
        }
        return false;
    }

    public String toString() {
        Point2D center = this.ellipse.center();
        return String.format(Locale.US, "EllipseArc2D(%7.2f,%7.2f,%7.2f,%7.2f,%7.5f,%7.5f,%7.5f)", new Object[]{Double.valueOf(center.x()), Double.valueOf(center.y()), Double.valueOf(this.ellipse.r1), Double.valueOf(this.ellipse.r2), Double.valueOf(this.ellipse.theta), Double.valueOf(this.startAngle), Double.valueOf(this.angleExtent)});
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof EllipseArc2D)) {
            return false;
        }
        EllipseArc2D that = (EllipseArc2D) obj;
        if (!this.ellipse.equals(that.ellipse)) {
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

    public EllipseArc2D clone() {
        return new EllipseArc2D(this.ellipse, this.startAngle, this.angleExtent);
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
