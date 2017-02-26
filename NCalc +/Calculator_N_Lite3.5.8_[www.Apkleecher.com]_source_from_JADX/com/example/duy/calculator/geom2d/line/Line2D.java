package com.example.duy.calculator.geom2d.line;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.math_eval.Constants;
import edu.jas.ps.UnivPowerSeriesRing;
import java.util.Collection;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class Line2D extends AbstractSmoothCurve2D implements LinearShape2D, Cloneable {
    public Point2D p1;
    public Point2D p2;

    public Line2D(Point2D point1, Point2D point2) {
        this.p1 = point1;
        this.p2 = point2;
    }

    public Line2D(double x1, double y1, double x2, double y2) {
        this.p1 = new Point2D(x1, y1);
        this.p2 = new Point2D(x2, y2);
    }

    public Line2D(Line2D line) {
        this(line.getPoint1(), line.getPoint2());
    }

    public Line2D(StraightLine2D mLine) {
        this(mLine.x0, mLine.y0, mLine.dx, mLine.dx);
    }

    public static boolean intersects(Line2D line1, Line2D line2) {
        boolean b1;
        Point2D e1p1 = line1.firstPoint();
        Point2D e1p2 = line1.lastPoint();
        Point2D e2p1 = line2.firstPoint();
        Point2D e2p2 = line2.lastPoint();
        if (Point2D.ccw(e1p1, e1p2, e2p1) * Point2D.ccw(e1p1, e1p2, e2p2) <= 0) {
            b1 = true;
        } else {
            b1 = false;
        }
        boolean b2;
        if (Point2D.ccw(e2p1, e2p2, e1p1) * Point2D.ccw(e2p1, e2p2, e1p2) <= 0) {
            b2 = true;
        } else {
            b2 = false;
        }
        if (b1 && b2) {
            return true;
        }
        return false;
    }

    @Deprecated
    public static Line2D create(Point2D p1, Point2D p2) {
        return new Line2D(p1, p2);
    }

    public static Point2D getMidPoint(Line2D line2D) {
        return Point2D.midPoint(line2D.p1, line2D.p2);
    }

    public static String getEquationParameter(Line2D line2D) {
        Point2D point1 = line2D.getPoint1();
        Vector2D vecPhoenix = new Vector2D(point1, line2D.getPoint2());
        double u1 = vecPhoenix.getX();
        double u2 = vecPhoenix.getY();
        double x0 = point1.getX();
        double y0 = point1.getY();
        StringBuilder res = new StringBuilder();
        res.append("x = ");
        res.append(x0);
        res.append("+");
        res.append(u1);
        res.append("t");
        res.append("\n");
        res.append("y = ");
        res.append(y0);
        res.append("+");
        res.append(u2);
        res.append("t");
        return res.toString();
    }

    public static String getGeneralEquation(Line2D line2D) {
        Vector2D vectorTangent = new Vector2D(line2D.getPoint1(), line2D.getPoint2()).getOrthogonal();
        double a = vectorTangent.getX();
        double b = vectorTangent.getY();
        double x0 = line2D.p1.getX();
        double y0 = line2D.p1.getY();
        if (Math.pow(a, 2.0d) + Math.pow(b, 2.0d) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            throw new RuntimeException("do not exist general equation");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a);
        stringBuilder.append(UnivPowerSeriesRing.DEFAULT_NAME);
        stringBuilder.append("+");
        stringBuilder.append(b);
        stringBuilder.append(Constants.Y);
        stringBuilder.append("+");
        stringBuilder.append(((-a) * x0) - (b * y0));
        stringBuilder.append(" = 0");
        return stringBuilder.toString();
    }

    public String getEquationParameter() {
        Vector2D vecPhoenix = new Vector2D(this.p1, this.p2);
        double u1 = vecPhoenix.getX();
        double u2 = vecPhoenix.getY();
        double x0 = this.p1.getX();
        double y0 = this.p1.getY();
        StringBuilder res = new StringBuilder();
        res.append("x = ");
        res.append(x0);
        res.append("+");
        res.append(u1);
        res.append("t");
        res.append("\n");
        res.append("y = ");
        res.append(y0);
        res.append("+");
        res.append(u2);
        res.append("t");
        return res.toString();
    }

    public Point2D getPoint1() {
        return this.p1;
    }

    public void setPoint1(Point2D point) {
        this.p1 = point;
    }

    public Point2D getPoint2() {
        return this.p2;
    }

    public void setPoint2(Point2D point) {
        this.p2 = point;
    }

    public double getX1() {
        return this.p1.x();
    }

    public double getY1() {
        return this.p1.y();
    }

    public double getX2() {
        return this.p2.x();
    }

    public double getY2() {
        return this.p2.y();
    }

    public Point2D getOtherPoint(Point2D point) {
        if (point.equals(this.p1)) {
            return this.p2;
        }
        if (point.equals(this.p2)) {
            return this.p1;
        }
        return null;
    }

    public boolean isColinear(LinearShape2D line) {
        return new LineSegment2D(this.p1, this.p2).isColinear(line);
    }

    public boolean isParallel(LinearShape2D line) {
        return new LineSegment2D(this.p1, this.p2).isParallel(line);
    }

    public boolean containsProjection(Point2D point) {
        double pos = new LineSegment2D(this.p1, this.p2).project(point);
        return pos > -1.0E-12d && pos < 1.000000000001d;
    }

    public Line2D parallel(double d) {
        double x0 = getX1();
        double y0 = getY1();
        double dx = getX2() - x0;
        double dy = getY2() - y0;
        double d2 = d / Math.hypot(dx, dy);
        return new Line2D((dy * d2) + x0, y0 - (dx * d2), (x0 + dx) + (dy * d2), (y0 + dy) - (dx * d2));
    }

    public double length() {
        return this.p1.distance(this.p2);
    }

    public double length(double pos) {
        return Math.hypot(this.p2.x() - this.p1.x(), this.p2.y() - this.p1.y()) * pos;
    }

    public double position(double length) {
        return length / Math.hypot(this.p2.x() - this.p1.x(), this.p2.y() - this.p1.y());
    }

    public double[][] parametric() {
        return new LineSegment2D(this.p1, this.p2).parametric();
    }

    public double[] cartesianEquation() {
        return new LineSegment2D(this.p1, this.p2).cartesianEquation();
    }

    public double[] polarCoefficients() {
        return new LineSegment2D(this.p1, this.p2).polarCoefficients();
    }

    public double[] polarCoefficientsSigned() {
        return new LineSegment2D(this.p1, this.p2).polarCoefficientsSigned();
    }

    public double horizontalAngle() {
        return new LineSegment2D(this.p1, this.p2).horizontalAngle();
    }

    public Point2D intersection(LinearShape2D line) {
        return new LineSegment2D(this.p1, this.p2).intersection(line);
    }

    public Point2D origin() {
        return this.p1;
    }

    public StraightLine2D supportingLine() {
        return new StraightLine2D(this.p1, this.p2);
    }

    public Vector2D direction() {
        return new Vector2D(this.p1, this.p2);
    }

    public double signedDistance(Point2D p) {
        return signedDistance(p.x(), p.y());
    }

    public double signedDistance(double x, double y) {
        return new LineSegment2D(this.p1, this.p2).signedDistance(x, y);
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

    public double distance(Point2D p) {
        return distance(p.x(), p.y());
    }

    public double distance(double x, double y) {
        Point2D proj = new StraightLine2D(this.p1, this.p2).projectedPoint(x, y);
        if (contains(proj)) {
            return proj.distance(x, y);
        }
        return Math.min(Math.hypot(this.p1.x() - x, this.p1.y() - y), Math.hypot(this.p2.x() - x, this.p2.y() - y));
    }

    public StraightLine2D parallel(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).parallel(point);
    }

    public StraightLine2D perpendicular(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).perpendicular(point);
    }

    public Vector2D tangent(double t) {
        return new Vector2D(this.p1, this.p2);
    }

    public double curvature(double t) {
        return 0.0d;
    }

    public LinearCurve2D asPolyline(int n) {
        return null;
    }

    public double windingAngle(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).windingAngle(point);
    }

    public boolean isInside(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).signedDistance(point) < 0.0d;
    }

    public double t0() {
        return 0.0d;
    }

    @Deprecated
    public double getT0() {
        return t0();
    }

    public double t1() {
        return 1.0d;
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D point(double t) {
        t = Math.min(Math.max(t, 0.0d), 1.0d);
        return new Point2D((this.p1.x() * (1.0d - t)) + (this.p2.x() * t), (this.p1.y() * (1.0d - t)) + (this.p2.y() * t));
    }

    public Point2D firstPoint() {
        return this.p1;
    }

    public Point2D lastPoint() {
        return this.p2;
    }

    public double position(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).position(point);
    }

    public double project(Point2D point) {
        return new LineSegment2D(this.p1, this.p2).project(point);
    }

    public Line2D subCurve(double t0, double t1) {
        if (t0 > t1) {
            return null;
        }
        return new Line2D(point(Math.max(t0, t0())), point(Math.min(t1, t1())));
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return new LineSegment2D(this.p1, this.p2).intersections(line);
    }

    public boolean contains(double x, double y) {
        return new LineSegment2D(this.p1, this.p2).contains(x, y);
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean isBounded() {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public Path getGeneralPath() {
        Path path = new Path();
        path.moveTo((float) this.p1.x(), (float) this.p1.y());
        path.lineTo((float) this.p2.x(), (float) this.p2.y());
        return path;
    }

    public Path appendPath(Path path) {
        path.lineTo((float) this.p2.x(), (float) this.p2.y());
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Line2D)) {
            return false;
        }
        Line2D edge = (Line2D) obj;
        if (this.p1.almostEquals(edge.p1, eps) && this.p2.almostEquals(edge.p2, eps)) {
            return true;
        }
        return false;
    }

    public Point2D getMidPoint() {
        return Point2D.midPoint(this.p1, this.p2);
    }

    public String toString() {
        return "Line2D(" + this.p1 + ")-(" + this.p2 + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Line2D)) {
            return false;
        }
        Line2D edge = (Line2D) obj;
        if (this.p1.equals(edge.p1) && this.p2.equals(edge.p2)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.p1.hashCode() + 31) * 31) + this.p2.hashCode();
    }

    public Line2D clone() {
        return new Line2D(this.p1, this.p2);
    }

    public String getGeneralEquation() {
        Vector2D vectorTangent = new Vector2D(this.p1, this.p2).getOrthogonal();
        double a = vectorTangent.getX();
        double b = vectorTangent.getY();
        double x0 = this.p1.getX();
        double y0 = this.p1.getY();
        if (Math.pow(a, 2.0d) + Math.pow(b, 2.0d) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            throw new RuntimeException("do not exist general equation");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(a);
        stringBuilder.append(UnivPowerSeriesRing.DEFAULT_NAME);
        stringBuilder.append("+");
        stringBuilder.append(b);
        stringBuilder.append(Constants.Y);
        stringBuilder.append("+");
        stringBuilder.append(((-a) * x0) - (b * y0));
        stringBuilder.append(" = 0");
        return stringBuilder.toString();
    }
}
