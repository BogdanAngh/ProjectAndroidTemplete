package com.example.duy.calculator.geom2d.spline;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.curve.SmoothCurve2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.polygon.Polyline2D;
import java.lang.reflect.Array;
import java.util.Collection;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class CubicBezierCurve2D extends AbstractSmoothCurve2D implements SmoothCurve2D {
    protected double ctrlx1;
    protected double ctrlx2;
    protected double ctrly1;
    protected double ctrly2;
    protected double x1;
    protected double x2;
    protected double y1;
    protected double y2;

    public CubicBezierCurve2D() {
        this(0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 0.0d);
    }

    public CubicBezierCurve2D(double[][] coefs) {
        this(coefs[0][0], coefs[1][0], (coefs[0][1] / 3.0d) + coefs[0][0], (coefs[1][1] / 3.0d) + coefs[1][0], (coefs[0][2] / 3.0d) + (coefs[0][0] + ((2.0d * coefs[0][1]) / 3.0d)), (coefs[1][2] / 3.0d) + (coefs[1][0] + ((2.0d * coefs[1][1]) / 3.0d)), coefs[0][3] + ((coefs[0][0] + coefs[0][1]) + coefs[0][2]), coefs[1][3] + ((coefs[1][0] + coefs[1][1]) + coefs[1][2]));
    }

    public CubicBezierCurve2D(Point2D p1, Point2D ctrl1, Point2D ctrl2, Point2D p2) {
        this(p1.x(), p1.y(), ctrl1.x(), ctrl1.y(), ctrl2.x(), ctrl2.y(), p2.x(), p2.y());
    }

    public CubicBezierCurve2D(Point2D p1, Vector2D v1, Point2D p2, Vector2D v2) {
        this(p1.x(), p1.y(), (v1.x() / 3.0d) + p1.x(), (v1.y() / 3.0d) + p1.y(), p2.x() - (v2.x() / 3.0d), p2.y() - (v2.y() / 3.0d), p2.x(), p2.y());
    }

    public CubicBezierCurve2D(double x1, double y1, double xctrl1, double yctrl1, double xctrl2, double yctrl2, double x2, double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.ctrlx1 = xctrl1;
        this.ctrly1 = yctrl1;
        this.ctrlx2 = xctrl2;
        this.ctrly2 = yctrl2;
        this.x2 = x2;
        this.y2 = y2;
    }

    @Deprecated
    public static final CubicBezierCurve2D create(Point2D p1, Point2D c1, Point2D c2, Point2D p2) {
        return new CubicBezierCurve2D(p1, c1, c2, p2);
    }

    @Deprecated
    public static final CubicBezierCurve2D create(Point2D p1, Vector2D v1, Point2D p2, Vector2D v2) {
        return new CubicBezierCurve2D(p1, v1, p2, v2);
    }

    public Point2D getControl1() {
        return new Point2D(this.ctrlx1, this.ctrly1);
    }

    public Point2D getControl2() {
        return new Point2D(this.ctrlx2, this.ctrly2);
    }

    public Point2D getP1() {
        return firstPoint();
    }

    public Point2D getP2() {
        return lastPoint();
    }

    public Point2D getCtrlP1() {
        return getControl1();
    }

    public Point2D getCtrlP2() {
        return getControl2();
    }

    public double[][] getParametric() {
        double[][] tab = (double[][]) Array.newInstance(Double.TYPE, new int[]{2, 4});
        tab[0][0] = this.x1;
        tab[0][1] = (3.0d * this.ctrlx1) - (3.0d * this.x1);
        tab[0][2] = ((3.0d * this.x1) - (6.0d * this.ctrlx1)) + (3.0d * this.ctrlx2);
        tab[0][3] = ((this.x2 - (3.0d * this.ctrlx2)) + (3.0d * this.ctrlx1)) - this.x1;
        tab[1][0] = this.y1;
        tab[1][1] = (3.0d * this.ctrly1) - (3.0d * this.y1);
        tab[1][2] = ((3.0d * this.y1) - (6.0d * this.ctrly1)) + (3.0d * this.ctrly2);
        tab[1][3] = ((this.y2 - (3.0d * this.ctrly2)) + (3.0d * this.ctrly1)) - this.y1;
        return tab;
    }

    public double windingAngle(Point2D point) {
        return asPolyline(100).windingAngle(point);
    }

    public boolean isInside(Point2D pt) {
        return asPolyline(100).isInside(pt);
    }

    public double signedDistance(Point2D point) {
        if (isInside(point)) {
            return -distance(point.x(), point.y());
        }
        return distance(point.x(), point.y());
    }

    public double signedDistance(double x, double y) {
        if (isInside(new Point2D(x, y))) {
            return -distance(x, y);
        }
        return distance(x, y);
    }

    public Vector2D tangent(double t) {
        double[][] c = getParametric();
        return new Vector2D(c[0][1] + (((2.0d * c[0][2]) + ((3.0d * c[0][3]) * t)) * t), c[1][1] + (((2.0d * c[1][2]) + ((3.0d * c[1][3]) * t)) * t));
    }

    public Vector2D normal(double t) {
        return null;
    }

    public double curvature(double t) {
        double[][] c = getParametric();
        double xp = c[0][1] + (((2.0d * c[0][2]) + ((3.0d * c[0][3]) * t)) * t);
        double yp = c[1][1] + (((2.0d * c[1][2]) + ((3.0d * c[1][3]) * t)) * t);
        return ((xp * ((2.0d * c[1][2]) + ((6.0d * c[1][3]) * t))) - (yp * ((2.0d * c[0][2]) + ((6.0d * c[0][3]) * t)))) / Math.pow(Math.hypot(xp, yp), 3.0d);
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
        double dt = 1.0d / ((double) n);
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
        return 1.0d;
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return asPolyline(100).intersections(line);
    }

    public Point2D point(double t) {
        t = Math.min(Math.max(t, 0.0d), 1.0d);
        double[][] c = getParametric();
        return new Point2D(c[0][0] + ((c[0][1] + ((c[0][2] + (c[0][3] * t)) * t)) * t), c[1][0] + ((c[1][1] + ((c[1][2] + (c[1][3] * t)) * t)) * t));
    }

    public Point2D firstPoint() {
        return new Point2D(this.x1, this.y1);
    }

    public Point2D lastPoint() {
        return new Point2D(this.x2, this.y2);
    }

    public double position(Point2D point) {
        return asPolyline(100).position(point) / ((double) 100);
    }

    public double project(Point2D point) {
        return asPolyline(100).project(point) / ((double) 100);
    }

    public CubicBezierCurve2D reverse() {
        return new CubicBezierCurve2D(lastPoint(), getControl1(), getControl2(), firstPoint());
    }

    public CubicBezierCurve2D subCurve(double t0, double t1) {
        t0 = Math.max(t0, 0.0d);
        t1 = Math.min(t1, 1.0d);
        if (t0 > t1) {
            return null;
        }
        double dt = t1 - t0;
        return new CubicBezierCurve2D(point(t0), tangent(t0).times(dt), point(t1), tangent(t1).times(dt));
    }

    public boolean contains(double x, double y) {
        return asPolyline(180).contains(x, y);
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public double distance(Point2D p) {
        return distance(p.x(), p.y());
    }

    public double distance(double x, double y) {
        return asPolyline(100).distance(x, y);
    }

    public boolean isBounded() {
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public Path appendPath(Path path) {
        path.moveTo((float) this.x1, (float) this.y1);
        path.rCubicTo((float) this.ctrlx1, (float) this.ctrly1, (float) this.ctrlx2, (float) this.ctrly2, (float) this.x2, (float) this.y2);
        return path;
    }

    public Path getGeneralPath() {
        Path path = new Path();
        path.moveTo((float) this.x1, (float) this.y1);
        path.rCubicTo((float) this.ctrlx1, (float) this.ctrly1, (float) this.ctrlx2, (float) this.ctrly2, (float) this.x2, (float) this.y2);
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CubicBezierCurve2D)) {
            return false;
        }
        CubicBezierCurve2D bezier = (CubicBezierCurve2D) obj;
        if (Math.abs(this.x1 - bezier.x1) > eps) {
            return false;
        }
        if (Math.abs(this.y1 - bezier.y1) > eps) {
            return false;
        }
        if (Math.abs(this.ctrlx1 - bezier.ctrlx1) > eps) {
            return false;
        }
        if (Math.abs(this.ctrly1 - bezier.ctrly1) > eps) {
            return false;
        }
        if (Math.abs(this.ctrlx2 - bezier.ctrlx2) > eps) {
            return false;
        }
        if (Math.abs(this.ctrly2 - bezier.ctrly2) > eps) {
            return false;
        }
        if (Math.abs(this.x2 - bezier.x2) > eps) {
            return false;
        }
        if (Math.abs(this.y2 - bezier.y2) > eps) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CubicBezierCurve2D)) {
            return false;
        }
        CubicBezierCurve2D bezier = (CubicBezierCurve2D) obj;
        if (Math.abs(this.x1 - bezier.x1) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.y1 - bezier.y1) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.ctrlx1 - bezier.ctrlx1) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.ctrly1 - bezier.ctrly1) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.ctrlx2 - bezier.ctrlx2) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.ctrly2 - bezier.ctrly2) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.x2 - bezier.x2) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        if (Math.abs(this.y2 - bezier.y2) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return false;
        }
        return true;
    }

    @Deprecated
    public CubicBezierCurve2D clone() {
        return new CubicBezierCurve2D(this.x1, this.y1, this.ctrlx1, this.ctrly1, this.ctrlx2, this.ctrly2, this.x2, this.y2);
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
