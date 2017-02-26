package com.example.duy.calculator.geom2d.line;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import java.util.Collection;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class LineSegment2D extends AbstractLine2D implements Cloneable {
    public LineSegment2D(Point2D point1, Point2D point2) {
        this(point1.x(), point1.y(), point2.x(), point2.y());
    }

    public LineSegment2D(double x1, double y1, double x2, double y2) {
        super(x1, y1, x2 - x1, y2 - y1);
    }

    @Deprecated
    public static LineSegment2D create(Point2D p1, Point2D p2) {
        return new LineSegment2D(p1, p2);
    }

    public static StraightLine2D getMedian(LineSegment2D edge) {
        return new StraightLine2D(edge.x0 + (edge.dx * 0.5d), edge.y0 + (edge.dy * 0.5d), -edge.dy, edge.dx);
    }

    public static double getEdgeAngle(LineSegment2D edge1, LineSegment2D edge2) {
        if (Math.abs(edge1.x0 - edge2.x0) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            if (Math.abs(edge1.y0 - edge2.y0) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                double x0 = edge1.x0;
                double y0 = edge1.y0;
                double x1 = edge1.x0 + edge1.dx;
                double y1 = edge1.y0 + edge1.dy;
                double x2 = edge2.x0 + edge2.dx;
                double y2 = edge2.y0 + edge2.dy;
                return Angle2D.angle(new Vector2D(x1 - x0, y1 - y0), new Vector2D(x2 - x0, y2 - y0));
            }
        }
        double d = edge1.x0;
        double d2 = edge1.dx;
        if (Math.abs((r0 + r0) - edge2.x0) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            d = edge1.y0;
            d2 = edge1.dy;
            if (Math.abs((r0 + r0) - edge2.y0) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                x0 = edge1.x0 + edge1.dx;
                y0 = edge1.y0 + edge1.dy;
                x1 = edge1.x0;
                y1 = edge1.y0;
                x2 = edge2.x0 + edge2.dx;
                y2 = edge2.y0 + edge2.dy;
                return Angle2D.angle(new Vector2D(x1 - x0, y1 - y0), new Vector2D(x2 - x0, y2 - y0));
            }
        }
        d = edge1.x0;
        d2 = edge1.dx;
        d2 = edge2.x0;
        if (Math.abs(((r0 + r0) - r0) - edge2.dx) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            d = edge1.y0;
            d2 = edge1.dy;
            d2 = edge2.y0;
            if (Math.abs(((r0 + r0) - r0) - edge2.dy) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                x0 = edge1.x0 + edge1.dx;
                y0 = edge1.y0 + edge1.dy;
                x1 = edge1.x0;
                y1 = edge1.y0;
                x2 = edge2.x0;
                y2 = edge2.y0;
                return Angle2D.angle(new Vector2D(x1 - x0, y1 - y0), new Vector2D(x2 - x0, y2 - y0));
            }
        }
        d = edge1.x0;
        d2 = edge2.x0;
        if (Math.abs((r0 - r0) - edge2.dx) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            d = edge1.y0;
            d2 = edge2.y0;
            if (Math.abs((r0 - r0) - edge2.dy) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                x0 = edge1.x0;
                y0 = edge1.y0;
                x1 = edge1.x0 + edge1.dx;
                y1 = edge1.y0 + edge1.dy;
                x2 = edge2.x0;
                y2 = edge2.y0;
                return Angle2D.angle(new Vector2D(x1 - x0, y1 - y0), new Vector2D(x2 - x0, y2 - y0));
            }
        }
        return Double.NaN;
    }

    public static boolean intersects(LineSegment2D edge1, LineSegment2D edge2) {
        boolean b1;
        Point2D e1p1 = edge1.firstPoint();
        Point2D e1p2 = edge1.lastPoint();
        Point2D e2p1 = edge2.firstPoint();
        Point2D e2p2 = edge2.lastPoint();
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

    public Point2D opposite(Point2D point) {
        if (point.equals(new Point2D(this.x0, this.y0))) {
            return new Point2D(this.x0 + this.dx, this.y0 + this.dy);
        }
        if (point.equals(new Point2D(this.x0 + this.dx, this.y0 + this.dy))) {
            return new Point2D(this.x0, this.y0);
        }
        return null;
    }

    public StraightLine2D getMedian() {
        return new StraightLine2D(this.x0 + (this.dx * 0.5d), this.y0 + (this.dy * 0.5d), -this.dy, this.dx);
    }

    public double length() {
        return Math.hypot(this.dx, this.dy);
    }

    public LineSegment2D parallel(double d) {
        double d2 = Math.hypot(this.dx, this.dy);
        if (Math.abs(d2) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            throw new DegeneratedLine2DException("Can not compute parallel of degnerated edge", this);
        }
        d2 = d / d2;
        return new LineSegment2D(this.x0 + (this.dy * d2), this.y0 - (this.dx * d2), (this.x0 + this.dx) + (this.dy * d2), (this.y0 + this.dy) - (this.dx * d2));
    }

    public double windingAngle(Point2D point) {
        return 0.0d;
    }

    public double signedDistance(Point2D point) {
        return 0.0d;
    }

    public double signedDistance(double x, double y) {
        if (contains(super.projectedPoint(x, y))) {
            return super.signedDistance(x, y);
        }
        double d = distance(x, y);
        return super.signedDistance(x, y) <= 0.0d ? -d : d;
    }

    public boolean isInside(Point2D pt) {
        return false;
    }

    public Point2D firstPoint() {
        return new Point2D(this.x0, this.y0);
    }

    public Point2D lastPoint() {
        return new Point2D(this.x0 + this.dx, this.y0 + this.dy);
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return null;
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
        return new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
    }

    public LineSegment2D reverse() {
        return new LineSegment2D(this.x0 + this.dx, this.y0 + this.dy, this.x0, this.y0);
    }

    public boolean isBounded() {
        return true;
    }

    public boolean contains(double xp, double yp) {
        if (!super.supportContains(xp, yp)) {
            return false;
        }
        double t = positionOnLine(xp, yp);
        if (t < -1.0E-12d || t - 1.0d > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    public double distance(double x, double y) {
        StraightLine2D line = supportingLine();
        return line.point(Math.min(Math.max(Math.min(line.positionOnLine(x, y), 1.0d), 0.0d), 1.0d)).distance(x, y);
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
        path.lineTo((float) (this.x0 + this.dx), (float) (this.y0 + this.dy));
        return path;
    }

    public Path getGeneralPath() {
        Path path = new Path();
        path.moveTo((float) this.x0, (float) this.y0);
        path.lineTo((float) (this.x0 + this.dx), (float) (this.y0 + this.dy));
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineSegment2D)) {
            return false;
        }
        LineSegment2D edge = (LineSegment2D) obj;
        if (Math.abs(this.x0 - edge.x0) > eps) {
            return false;
        }
        if (Math.abs(this.y0 - edge.y0) > eps) {
            return false;
        }
        if (Math.abs(this.dx - edge.dx) > eps) {
            return false;
        }
        if (Math.abs(this.dy - edge.dy) > eps) {
            return false;
        }
        return true;
    }

    public String toString() {
        return new String("LineSegment2D[(" + this.x0 + "," + this.y0 + ")-(" + (this.x0 + this.dx) + "," + (this.y0 + this.dy) + ")]");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineSegment2D)) {
            return false;
        }
        LineSegment2D that = (LineSegment2D) obj;
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

    public LineSegment2D clone() {
        return new LineSegment2D(this.x0, this.y0, this.x0 + this.dx, this.y0 + this.dy);
    }
}
