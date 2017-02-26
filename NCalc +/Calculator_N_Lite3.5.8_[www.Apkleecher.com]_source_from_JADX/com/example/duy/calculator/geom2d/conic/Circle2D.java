package com.example.duy.calculator.geom2d.conic;

import android.graphics.Path;
import android.util.Log;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.conic.Conic2D.Type;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.line.AbstractLine2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.ColinearPoints2DException;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import com.example.duy.calculator.math_eval.Constants;
import edu.jas.ps.UnivPowerSeriesRing;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public class Circle2D extends AbstractSmoothCurve2D implements CircularShape2D {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static String TAG;
    protected boolean direct;
    protected double r;
    protected double theta;
    protected double xc;
    protected double yc;

    static {
        $assertionsDisabled = !Circle2D.class.desiredAssertionStatus();
        TAG = Circle2D.class.getName();
    }

    public Circle2D() {
        this(0.0d, 0.0d, 0.0d, true);
    }

    public Circle2D(Point2D center, double radius) {
        this(center.x(), center.y(), radius, true);
    }

    public Circle2D(Point2D center, double radius, boolean direct) {
        this(center.x(), center.y(), radius, direct);
    }

    public Circle2D(double xcenter, double ycenter, double radius) {
        this(xcenter, ycenter, radius, true);
    }

    public Circle2D(double xcenter, double ycenter, double radius, boolean direct) {
        this.r = 0.0d;
        this.direct = true;
        this.theta = 0.0d;
        this.xc = xcenter;
        this.yc = ycenter;
        this.r = radius;
        this.direct = direct;
    }

    @Deprecated
    public static Circle2D create(Point2D center, double radius) {
        return new Circle2D(center, radius);
    }

    @Deprecated
    public static Circle2D create(Point2D center, double radius, boolean direct) {
        return new Circle2D(center, radius, direct);
    }

    @Deprecated
    public static Circle2D create(Point2D p1, Point2D p2, Point2D p3) {
        if (Point2D.isColinear(p1, p2, p3)) {
            throw new ColinearPoints2DException(p1, p2, p3);
        }
        StraightLine2D line12 = StraightLine2D.createMedian(p1, p2);
        StraightLine2D line23 = StraightLine2D.createMedian(p2, p3);
        if ($assertionsDisabled || !AbstractLine2D.isParallel(line12, line23)) {
            Point2D center = AbstractLine2D.getIntersection(line12, line23);
            return new Circle2D(center, Point2D.distance(center, p2));
        }
        throw new AssertionError("If points are not colinear, medians should not be parallel");
    }

    @Deprecated
    public static Collection<Point2D> getIntersections(Circle2D circle1, Circle2D circle2) {
        ArrayList<Point2D> intersections = new ArrayList(2);
        Point2D center1 = circle1.center();
        Point2D center2 = circle2.center();
        double r1 = circle1.radius();
        double r2 = circle2.radius();
        double d = Point2D.distance(center1, center2);
        if (d >= Math.abs(r1 - r2) && d <= r1 + r2) {
            double angle = Angle2D.horizontalAngle(center1, center2);
            double d1 = (d / 2.0d) + (((r1 * r1) - (r2 * r2)) / (2.0d * d));
            Point2D tmp = Point2D.createPolar(center1, d1, angle);
            double h = Math.sqrt((r1 * r1) - (d1 * d1));
            intersections.add(Point2D.createPolar(tmp, h, Angle2D.M_PI_2 + angle));
            intersections.add(Point2D.createPolar(tmp, h, angle - Angle2D.M_PI_2));
        }
        return intersections;
    }

    @Deprecated
    public static Collection<Point2D> getIntersections(Circle2D circle, LineSegment2D line) {
        ArrayList<Point2D> intersections = new ArrayList(2);
        Circle2D parent = circle.supportingCircle();
        Point2D center = parent.center();
        double radius = parent.radius();
        Point2D inter = StraightLine2D.createPerpendicular(line, center).intersection(new StraightLine2D(line));
        if ($assertionsDisabled || inter != null) {
            double dist = inter.distance(center);
            if (Math.abs(dist - radius) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                double angle = line.horizontalAngle();
                double d2 = Math.sqrt((radius * radius) - (dist * dist));
                Point2D p1 = Point2D.createPolar(inter, d2, FastMath.PI + angle);
                Point2D p2 = Point2D.createPolar(inter, d2, angle);
                if (line.contains(p1) && circle.contains(p1)) {
                    intersections.add(p1);
                }
                if (line.contains(p2) && circle.contains(p2)) {
                    intersections.add(p2);
                }
            } else if (line.contains(inter) && circle.contains(inter)) {
                intersections.add(inter);
            }
            return intersections;
        }
        throw new AssertionError();
    }

    public static Circle2D circumCircle(Point2D p1, Point2D p2, Point2D p3) {
        Point2D center = circumCenter(p1, p2, p3);
        return new Circle2D(center, Point2D.distance(center, p2));
    }

    public static Point2D circumCenter(Point2D p1, Point2D p2, Point2D p3) {
        if (Point2D.isColinear(p1, p2, p3)) {
            throw new ColinearPoints2DException(p1, p2, p3);
        }
        StraightLine2D line12 = StraightLine2D.createMedian(p1, p2);
        StraightLine2D line23 = StraightLine2D.createMedian(p2, p3);
        if ($assertionsDisabled || !AbstractLine2D.isParallel(line12, line23)) {
            return AbstractLine2D.getIntersection(line12, line23);
        }
        throw new AssertionError("If points are not colinear, medians should not be parallel");
    }

    public static Collection<Point2D> circlesIntersections(Circle2D circle1, Circle2D circle2) {
        Point2D center1 = circle1.center();
        Point2D center2 = circle2.center();
        double r1 = circle1.radius();
        double r2 = circle2.radius();
        double d = Point2D.distance(center1, center2);
        if (d < Math.abs(r1 - r2) || d > r1 + r2) {
            return new ArrayList(0);
        }
        double angle = Angle2D.horizontalAngle(center1, center2);
        if (d == Math.abs(r1 - r2) || d == r1 + r2) {
            Collection<Point2D> arrayList = new ArrayList(1);
            arrayList.add(Point2D.createPolar(center1, r1, angle));
            return arrayList;
        }
        double d1 = (d / 2.0d) + (((r1 * r1) - (r2 * r2)) / (2.0d * d));
        Point2D tmp = Point2D.createPolar(center1, d1, angle);
        double h = Math.sqrt((r1 * r1) - (d1 * d1));
        Collection<Point2D> intersections = new ArrayList(2);
        intersections.add(Point2D.createPolar(tmp, h, Angle2D.M_PI_2 + angle));
        intersections.add(Point2D.createPolar(tmp, h, angle - Angle2D.M_PI_2));
        return intersections;
    }

    public static Collection<Point2D> lineCircleIntersections(LineSegment2D line, CircleArc2D circle) {
        ArrayList<Point2D> intersections = new ArrayList(2);
        Circle2D parent = circle.supportingCircle();
        Point2D center = parent.center();
        double radius = parent.radius();
        Point2D inter = StraightLine2D.createPerpendicular(line, center).intersection(new StraightLine2D(line));
        if (inter == null) {
            throw new RuntimeException("Could not compute intersection point when computing line-cicle intersection");
        }
        double dist = inter.distance(center);
        if (Math.abs(dist - radius) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            double angle = line.horizontalAngle();
            double d2 = Math.sqrt((radius * radius) - (dist * dist));
            Point2D p1 = Point2D.createPolar(inter, d2, FastMath.PI + angle);
            Point2D p2 = Point2D.createPolar(inter, d2, angle);
            if (line.contains(p1) && circle.contains(p1)) {
                intersections.add(p1);
            }
            if (line.contains(p2) && circle.contains(p2)) {
                intersections.add(p2);
            }
        } else if (line.contains(inter) && circle.contains(inter)) {
            intersections.add(inter);
        }
        return intersections;
    }

    public static Collection<Point2D> lineCircleIntersections(LineSegment2D line, Circle2D circle) {
        ArrayList<Point2D> intersections = new ArrayList(2);
        Circle2D parent = circle.supportingCircle();
        Point2D center = parent.center();
        double radius = parent.radius();
        Point2D inter = StraightLine2D.createPerpendicular(line, center).intersection(new StraightLine2D(line));
        if (inter == null) {
            throw new RuntimeException("Could not compute intersection point when computing line-circle intersection");
        }
        double dist = inter.distance(center);
        if (Math.abs(dist - radius) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            double angle = line.horizontalAngle();
            double d2 = Math.sqrt((radius * radius) - (dist * dist));
            Point2D p1 = Point2D.createPolar(inter, d2, FastMath.PI + angle);
            Point2D p2 = Point2D.createPolar(inter, d2, angle);
            if (line.contains(p1) && circle.contains(p1)) {
                intersections.add(p1);
            }
            if (line.contains(p2) && circle.contains(p2)) {
                intersections.add(p2);
            }
        } else if (line.contains(inter) && circle.contains(inter)) {
            intersections.add(inter);
        }
        return intersections;
    }

    public static StraightLine2D radicalAxis(Circle2D circle1, Circle2D circle2) {
        double r1 = circle1.radius();
        double r2 = circle2.radius();
        Point2D p1 = circle1.center();
        Point2D p2 = circle2.center();
        double angle = Angle2D.horizontalAngle(p1, p2);
        double dist = p1.distance(p2);
        if (dist < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            throw new IllegalArgumentException("Input circles must have distinct centers");
        }
        double d = ((((dist * dist) + (r1 * r1)) - (r2 * r2)) * 0.5d) / dist;
        double cot = Math.cos(angle);
        double sit = Math.sin(angle);
        return new StraightLine2D(p1.x() + (d * cot), p1.y() + (d * sit), -sit, cot);
    }

    public double radius() {
        return this.r;
    }

    public Collection<Point2D> intersections(Circle2D circle) {
        return circlesIntersections(this, circle);
    }

    public Circle2D supportingCircle() {
        return this;
    }

    public boolean isDirect() {
        return this.direct;
    }

    public Point2D center() {
        return new Point2D(this.xc, this.yc);
    }

    public Vector2D vector1() {
        return new Vector2D(Math.cos(this.theta), Math.sin(this.theta));
    }

    public Vector2D vector2() {
        if (this.direct) {
            return new Vector2D(-Math.sin(this.theta), Math.cos(this.theta));
        }
        return new Vector2D(Math.sin(this.theta), -Math.cos(this.theta));
    }

    public double angle() {
        return this.theta;
    }

    public Point2D focus1() {
        return new Point2D(this.xc, this.yc);
    }

    public Point2D focus2() {
        return new Point2D(this.xc, this.yc);
    }

    public boolean isCircle() {
        return true;
    }

    public Ellipse2D asEllipse() {
        return new Ellipse2D(this.xc, this.yc, this.r, this.r, this.theta, this.direct);
    }

    public Type conicType() {
        return Type.CIRCLE;
    }

    public double[] conicCoefficients() {
        return new double[]{1.0d, 0.0d, 1.0d, this.xc * -2.0d, this.yc * -2.0d, ((this.xc * this.xc) + (this.yc * this.yc)) - (this.r * this.r)};
    }

    public double eccentricity() {
        return 0.0d;
    }

    public Circle2D parallel(double d) {
        return new Circle2D(this.xc, this.yc, Math.max(this.direct ? this.r + d : this.r - d, 0.0d), this.direct);
    }

    public double length() {
        return Angle2D.M_2PI * this.r;
    }

    public double length(double pos) {
        return this.r * pos;
    }

    public double position(double length) {
        return length / this.r;
    }

    public double windingAngle(Point2D point) {
        if (signedDistance(point) > 0.0d) {
            return 0.0d;
        }
        return this.direct ? Angle2D.M_2PI : -6.283185307179586d;
    }

    public Vector2D tangent(double t) {
        if (!this.direct) {
            t = -t;
        }
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        double cost = Math.cos(t);
        double sint = Math.sin(t);
        if (this.direct) {
            return new Vector2D((((-this.r) * sint) * cot) - ((this.r * cost) * sit), (((-this.r) * sint) * sit) + ((this.r * cost) * cot));
        }
        return new Vector2D(((this.r * sint) * cot) + ((this.r * cost) * sit), ((this.r * sint) * sit) - ((this.r * cost) * cot));
    }

    public double curvature(double t) {
        double k = 1.0d / this.r;
        return this.direct ? k : -k;
    }

    public LinearCurve2D asPolyline(int n) {
        return null;
    }

    public boolean isClosed() {
        return true;
    }

    public Vector2D leftTangent(double t) {
        return null;
    }

    public Vector2D rightTangent(double t) {
        return null;
    }

    public boolean isInside(Point2D point) {
        int i = 1;
        double xp = (point.x() - this.xc) / this.r;
        double yp = (point.y() - this.yc) / this.r;
        int i2 = (xp * xp) + (yp * yp) < 1.0d ? 1 : 0;
        if (this.direct) {
            i = 0;
        }
        return i2 ^ i;
    }

    public double signedDistance(Point2D point) {
        return signedDistance(point.x(), point.y());
    }

    public double signedDistance(double x, double y) {
        if (this.direct) {
            return Point2D.distance(this.xc, this.yc, x, y) - this.r;
        }
        return this.r - Point2D.distance(this.xc, this.yc, x, y);
    }

    public boolean isBounded() {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public double t0() {
        return 0.0d;
    }

    @Deprecated
    public double getT0() {
        return t0();
    }

    public double t1() {
        return Angle2D.M_2PI;
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D point(double t) {
        double angle = this.theta + t;
        if (!this.direct) {
            angle = this.theta - t;
        }
        return new Point2D(this.xc + (this.r * Math.cos(angle)), this.yc + (this.r * Math.sin(angle)));
    }

    public Point2D firstPoint() {
        return new Point2D(this.xc + (this.r * Math.cos(this.theta)), this.yc + (this.r * Math.sin(this.theta)));
    }

    public Point2D lastPoint() {
        return new Point2D(this.xc + (this.r * Math.cos(this.theta)), this.yc + (this.r * Math.sin(this.theta)));
    }

    public double position(Point2D point) {
        double angle = Angle2D.horizontalAngle(this.xc, this.yc, point.x(), point.y());
        if (this.direct) {
            return Angle2D.formatAngle(angle - this.theta);
        }
        return Angle2D.formatAngle(this.theta - angle);
    }

    public double project(Point2D point) {
        return Angle2D.horizontalAngle(point.x() - this.xc, point.y() - this.yc);
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return null;
    }

    public Circle2D reverse() {
        return new Circle2D(this.xc, this.yc, this.r, !this.direct);
    }

    public CircleArc2D subCurve(double t0, double t1) {
        double startAngle;
        double extent;
        if (this.direct) {
            startAngle = t0;
            extent = Angle2D.formatAngle(t1 - t0);
        } else {
            extent = -Angle2D.formatAngle(t1 - t0);
            startAngle = Angle2D.formatAngle(-t0);
        }
        return new CircleArc2D(this, startAngle, extent);
    }

    public double distance(Point2D point) {
        return Math.abs(Point2D.distance(this.xc, this.yc, point.x(), point.y()) - this.r);
    }

    public double distance(double x, double y) {
        return Math.abs(Point2D.distance(this.xc, this.yc, x, y) - this.r);
    }

    public Collection<Point2D> intersections(LineSegment2D line) {
        return lineCircleIntersections(line, this);
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(double x, double y) {
        return Math.abs(distance(x, y)) <= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public Path appendPath(Path path) {
        Path path2;
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        double t;
        double cost;
        double sint;
        if (this.direct) {
            for (t = 0.1d; t < Angle2D.M_2PI; t += 0.1d) {
                cost = Math.cos(t);
                sint = Math.sin(t);
                path2 = path;
                path2.lineTo((float) ((this.xc + ((this.r * cost) * cot)) - ((this.r * sint) * sit)), (float) ((this.yc + ((this.r * cost) * sit)) + ((this.r * sint) * cot)));
            }
        } else {
            for (t = 0.1d; t < Angle2D.M_2PI; t += 0.1d) {
                cost = Math.cos(t);
                sint = Math.sin(t);
                path2 = path;
                path2.lineTo((float) ((this.xc + ((this.r * cost) * cot)) + ((this.r * sint) * sit)), (float) ((this.yc + ((this.r * cost) * sit)) - ((this.r * sint) * cot)));
            }
        }
        path2 = path;
        path2.lineTo((float) (this.xc + (this.r * cot)), (float) (this.yc + (this.r * sit)));
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (!(obj instanceof Circle2D)) {
            return false;
        }
        Circle2D circle = (Circle2D) obj;
        if (Math.abs(circle.xc - this.xc) > eps || Math.abs(circle.yc - this.yc) > eps || Math.abs(circle.r - this.r) > eps || circle.direct != this.direct) {
            return false;
        }
        return true;
    }

    public String toString() {
        Locale locale = Locale.US;
        String str = "Circle2D(%7.2f,%7.2f,%7.2f,%s)";
        Object[] objArr = new Object[4];
        objArr[0] = Double.valueOf(this.xc);
        objArr[1] = Double.valueOf(this.yc);
        objArr[2] = Double.valueOf(this.r);
        objArr[3] = this.direct ? Constants.TRUE : Constants.FALSE;
        return String.format(locale, str, objArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Circle2D)) {
            return super.equals(obj);
        }
        Circle2D that = (Circle2D) obj;
        if (!EqualUtils.areEqual(this.xc, that.xc)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.yc, that.yc)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.r, that.r)) {
            return false;
        }
        if (this.direct != that.direct) {
            return false;
        }
        return true;
    }

    public Circle2D clone() {
        return new Circle2D(this.xc, this.yc, this.r, this.direct);
    }

    public int hashCode() {
        return ((center().hashCode() + 17) * 31) + Double.valueOf(radius()).hashCode();
    }

    public double getArea() {
        return FastMath.PI * Math.pow(radius(), 2.0d);
    }

    public String getEquationCircle() {
        StringBuilder equation = new StringBuilder();
        equation.append("(x");
        if (this.xc > 0.0d) {
            equation.append("-");
            equation.append(this.xc);
        } else if (this.xc < 0.0d) {
            equation.append("+");
            equation.append(this.xc);
        }
        equation.append(")^2 + (y");
        if (this.yc > 0.0d) {
            equation.append("-");
            equation.append(this.yc);
        } else if (this.yc < 0.0d) {
            equation.append("+");
            equation.append(this.yc);
        }
        equation.append(")^2 = ");
        equation.append(Math.pow(this.r, 2.0d));
        return equation.toString();
    }

    public String getEquationTangent(Point2D point2D) {
        if (contains(point2D)) {
            double x0 = point2D.x();
            double y0 = point2D.y();
            StringBuilder equation = new StringBuilder();
            double mX = x0 - this.xc;
            if (mX > 0.0d || mX < 0.0d) {
                equation.append(mX);
                equation.append(UnivPowerSeriesRing.DEFAULT_NAME);
            }
            Log.d(TAG, String.valueOf(mX));
            double mY = y0 - this.yc;
            if (mY > 0.0d || mY < 0.0d) {
                equation.append(mY);
                equation.append(Constants.Y);
            }
            Log.d(TAG, String.valueOf(mY));
            double mC = ((-x0) * (x0 - this.xc)) - ((y0 - this.yc) * y0);
            if (mC > 0.0d) {
                equation.append(" +");
                equation.append(mC);
            } else if (mC < 0.0d) {
                equation.append(mC);
            }
            Log.d(TAG, String.valueOf(mC));
            equation.append(" = 0");
            return equation.toString();
        }
        throw new RuntimeException("point not contain in circle");
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
