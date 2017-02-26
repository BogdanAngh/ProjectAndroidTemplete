package com.example.duy.calculator.geom2d.line;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.AffineTransform2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import com.example.duy.calculator.geom2d.util.UnboundedShape2DException;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class LineArc2D extends AbstractLine2D implements Cloneable {
    protected double t0;
    protected double t1;

    public LineArc2D(Point2D point1, Point2D point2, double t0, double t1) {
        this(point1.x(), point1.y(), point2.x() - point1.x(), point2.y() - point1.y(), t0, t1);
    }

    public LineArc2D(LinearShape2D line, double t0, double t1) {
        super(line.origin(), line.direction());
        this.t0 = 0.0d;
        this.t1 = 1.0d;
        this.t0 = t0;
        this.t1 = t1;
    }

    public LineArc2D(double x1, double y1, double dx, double dy, double t0, double t1) {
        super(x1, y1, dx, dy);
        this.t0 = 0.0d;
        this.t1 = 1.0d;
        this.t0 = t0;
        this.t1 = t1;
    }

    public static LineArc2D create(Point2D p1, Point2D p2, double t0, double t1) {
        return new LineArc2D(p1, p2, t0, t1);
    }

    private static boolean almostEquals(double d1, double d2, double eps) {
        if (d1 == Double.POSITIVE_INFINITY && d2 == Double.POSITIVE_INFINITY) {
            return true;
        }
        if ((d1 != Double.NEGATIVE_INFINITY || d2 != Double.NEGATIVE_INFINITY) && Math.abs(d1 - d2) >= eps) {
            return false;
        }
        return true;
    }

    public double length() {
        if (isBounded()) {
            return firstPoint().distance(lastPoint());
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getX1() {
        if (this.t0 != Double.NEGATIVE_INFINITY) {
            return this.x0 + (this.t0 * this.dx);
        }
        return Double.NEGATIVE_INFINITY;
    }

    public double getY1() {
        if (this.t0 != Double.NEGATIVE_INFINITY) {
            return this.y0 + (this.t0 * this.dy);
        }
        return Double.NEGATIVE_INFINITY;
    }

    public double getX2() {
        if (this.t1 != Double.POSITIVE_INFINITY) {
            return this.x0 + (this.t1 * this.dx);
        }
        return Double.POSITIVE_INFINITY;
    }

    public double getY2() {
        if (this.t1 != Double.POSITIVE_INFINITY) {
            return this.y0 + (this.t1 * this.dy);
        }
        return Double.POSITIVE_INFINITY;
    }

    public LineArc2D parallel(double d) {
        double d2 = d / Math.hypot(this.dx, this.dy);
        return new LineArc2D(this.x0 + (this.dy * d2), this.y0 - (this.dx * d2), this.dx, this.dy, this.t0, this.t1);
    }

    public double t0() {
        return this.t0;
    }

    @Deprecated
    public double getT0() {
        return t0();
    }

    public double t1() {
        return this.t1;
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D point(double t) {
        if (t < this.t0) {
            t = this.t0;
        }
        if (t > this.t1) {
            t = this.t1;
        }
        if (!Double.isInfinite(t)) {
            return new Point2D(this.x0 + (this.dx * t), this.y0 + (this.dy * t));
        }
        throw new UnboundedShape2DException(this);
    }

    public Point2D firstPoint() {
        if (!Double.isInfinite(this.t0)) {
            return new Point2D(this.x0 + (this.t0 * this.dx), this.y0 + (this.t0 * this.dy));
        }
        throw new UnboundedShape2DException(this);
    }

    public Point2D lastPoint() {
        if (!Double.isInfinite(this.t1)) {
            return new Point2D(this.x0 + (this.t1 * this.dx), this.y0 + (this.t1 * this.dy));
        }
        throw new UnboundedShape2DException(this);
    }

    public Collection<Point2D> singularPoints() {
        ArrayList<Point2D> list = new ArrayList(2);
        if (this.t0 != Double.NEGATIVE_INFINITY) {
            list.add(firstPoint());
        }
        if (this.t1 != Double.POSITIVE_INFINITY) {
            list.add(lastPoint());
        }
        return list;
    }

    public boolean isSingular(double pos) {
        if (Math.abs(pos - this.t0) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE && Math.abs(pos - this.t1) >= OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    public LineArc2D reverse() {
        return new LineArc2D(this.x0, this.y0, -this.dx, -this.dy, -this.t1, -this.t0);
    }

    public LineArc2D subCurve(double t0, double t1) {
        return new LineArc2D(this, Math.max(t0, t0()), Math.min(t1, t1()));
    }

    public boolean isBounded() {
        return (this.t0 == Double.NEGATIVE_INFINITY || this.t1 == Double.POSITIVE_INFINITY) ? false : true;
    }

    public boolean contains(Point2D pt) {
        return contains(pt.x(), pt.y());
    }

    public boolean contains(double xp, double yp) {
        if (!super.supportContains(xp, yp)) {
            return false;
        }
        double t = positionOnLine(xp, yp);
        if (t - this.t0 < -1.0E-12d || t - this.t1 > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    public Path getGeneralPath() {
        if (isBounded()) {
            Path path = new Path();
            path.moveTo((float) (this.x0 + (this.t0 * this.dx)), (float) (this.y0 + (this.t0 * this.dy)));
            path.lineTo((float) (this.x0 + (this.t1 * this.dx)), (float) (this.y0 + (this.t1 * this.dy)));
            return path;
        }
        throw new UnboundedShape2DException(this);
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
        if (isBounded()) {
            if (!(this.t0 == Double.NEGATIVE_INFINITY || this.t1 == Double.POSITIVE_INFINITY)) {
                path.lineTo((float) getX1(), (float) getY1());
                path.lineTo((float) getX2(), (float) getY2());
            }
            return path;
        }
        throw new UnboundedShape2DException(this);
    }

    public LineArc2D transform(AffineTransform2D trans) {
        double[] tab = trans.coefficients();
        return new LineArc2D(((this.x0 * tab[0]) + (this.y0 * tab[1])) + tab[2], ((this.x0 * tab[3]) + (this.y0 * tab[4])) + tab[5], (this.dx * tab[0]) + (this.dy * tab[1]), (this.dx * tab[3]) + (this.dy * tab[4]), this.t0, this.t1);
    }

    public String toString() {
        return new String("LineArc2D(" + this.x0 + "," + this.y0 + "," + this.dx + "," + this.dy + "," + this.t0 + "," + this.t1 + ")");
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineArc2D)) {
            return false;
        }
        LineArc2D that = (LineArc2D) obj;
        if (!almostEquals(this.x0, that.x0, eps)) {
            return false;
        }
        if (!almostEquals(this.y0, that.y0, eps)) {
            return false;
        }
        if (!almostEquals(this.dx, that.dx, eps)) {
            return false;
        }
        if (!almostEquals(this.dy, that.dy, eps)) {
            return false;
        }
        if (!almostEquals(this.t0, that.t0, eps)) {
            return false;
        }
        if (almostEquals(this.t1, that.t1, eps)) {
            return true;
        }
        return false;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LineArc2D)) {
            return false;
        }
        LineArc2D that = (LineArc2D) obj;
        if (!EqualUtils.areEqual(this.x0, that.x0)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.y0, that.y0)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.dx, that.dx)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.dy, that.dy)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.t0, that.t0)) {
            return false;
        }
        if (EqualUtils.areEqual(this.t1, that.t1)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((Double.valueOf(this.x0).hashCode() + 31) * 31) + Double.valueOf(this.y0).hashCode()) * 31) + Double.valueOf(this.dx).hashCode()) * 31) + Double.valueOf(this.dy).hashCode()) * 31) + Double.valueOf(this.t0).hashCode()) * 31) + Double.valueOf(this.t1).hashCode();
    }

    public LineArc2D clone() {
        return new LineArc2D(this.x0, this.y0, this.dx, this.dy, this.t0, this.t1);
    }
}
