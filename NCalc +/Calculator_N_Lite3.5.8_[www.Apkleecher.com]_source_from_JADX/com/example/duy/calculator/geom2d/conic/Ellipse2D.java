package com.example.duy.calculator.geom2d.conic;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.conic.Conic2D.Type;
import com.example.duy.calculator.geom2d.curve.AbstractSmoothCurve2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;
import com.example.duy.calculator.geom2d.util.AffineTransform2D;
import com.example.duy.calculator.geom2d.util.Angle2D;
import com.example.duy.calculator.geom2d.util.EqualUtils;
import com.example.duy.calculator.math_eval.Constants;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public class Ellipse2D extends AbstractSmoothCurve2D implements Cloneable {
    protected boolean direct;
    protected double r1;
    protected double r2;
    protected double theta;
    protected double xc;
    protected double yc;

    public Ellipse2D() {
        this(0.0d, 0.0d, 1.0d, 1.0d, 0.0d, true);
    }

    public Ellipse2D(Point2D center, double l1, double l2) {
        this(center.x(), center.y(), l1, l2, 0.0d, true);
    }

    public Ellipse2D(double xc, double yc, double l1, double l2) {
        this(xc, yc, l1, l2, 0.0d, true);
    }

    public Ellipse2D(Point2D center, double l1, double l2, double theta) {
        this(center.x(), center.y(), l1, l2, theta, true);
    }

    public Ellipse2D(double xc, double yc, double l1, double l2, double theta) {
        this(xc, yc, l1, l2, theta, true);
    }

    public Ellipse2D(double xc, double yc, double l1, double l2, double theta, boolean direct) {
        this.theta = 0.0d;
        this.direct = true;
        this.xc = xc;
        this.yc = yc;
        this.r1 = l1;
        this.r2 = l2;
        this.theta = theta;
        this.direct = direct;
    }

    public static Ellipse2D create(Point2D focus1, Point2D focus2, double chord) {
        double x1 = focus1.x();
        double y1 = focus1.y();
        double x2 = focus2.x();
        double y2 = focus2.y();
        double xc = (x1 + x2) / 2.0d;
        double yc = (y1 + y2) / 2.0d;
        double theta = Angle2D.horizontalAngle(x1, y1, x2, y2);
        double dist = focus1.distance(focus2);
        return new Ellipse2D(xc, yc, chord / 2.0d, Math.sqrt((chord * chord) - (dist * dist)) / 2.0d, theta);
    }

    @Deprecated
    public static Ellipse2D create(Point2D center, double l1, double l2) {
        return new Ellipse2D(center.x(), center.y(), l1, l2, 0.0d, true);
    }

    @Deprecated
    public static Ellipse2D create(Point2D center, double l1, double l2, double theta) {
        return new Ellipse2D(center.x(), center.y(), l1, l2, theta, true);
    }

    public static Ellipse2D create(Point2D center, double l1, double l2, double theta, boolean direct) {
        return new Ellipse2D(center.x(), center.y(), l1, l2, theta, direct);
    }

    public static final Ellipse2D inertiaEllipse(Collection<Point2D> points) {
        double xc = 0.0d;
        double yc = 0.0d;
        for (Point2D p : points) {
            xc += p.getX();
            yc += p.getY();
        }
        int np = points.size();
        xc /= (double) np;
        yc /= (double) np;
        double Ixx = 0.0d;
        double Iyy = 0.0d;
        double Ixy = 0.0d;
        for (Point2D p2 : points) {
            double x = p2.getX() - xc;
            double y = p2.getY() - yc;
            Ixx += x * x;
            Iyy += y * y;
            Ixy += x * y;
        }
        Ixx /= (double) np;
        Ixy /= (double) np;
        Iyy /= (double) np;
        double diff = Ixx - Iyy;
        double common = Math.sqrt((diff * diff) + ((4.0d * Ixy) * Ixy));
        return new Ellipse2D(xc, yc, Math.sqrt(2.0d) * Math.sqrt((Ixx + Iyy) + common), Math.sqrt(2.0d) * Math.sqrt((Ixx + Iyy) - common), Math.atan2(2.0d * Ixy, Ixx - Iyy) / 2.0d);
    }

    public double getRho(double angle) {
        return (this.r1 * this.r2) / Math.hypot(this.r2 * Math.cos(angle - this.theta), this.r1 * Math.cos(angle - this.theta));
    }

    public Point2D projectedPoint(Point2D point) {
        Vector2D polar = projectedVector(point, OpenMapRealVector.DEFAULT_ZERO_TOLERANCE);
        return new Point2D(point.x() + polar.x(), point.y() + polar.y());
    }

    public Vector2D projectedVector(Point2D point, double eMax) {
        double la;
        double lb;
        double theta;
        double x = point.x() - this.xc;
        double y = point.y() - this.yc;
        if (this.r1 >= this.r2) {
            la = this.r1;
            lb = this.r2;
            theta = this.theta;
        } else {
            la = this.r2;
            lb = this.r1;
            theta = this.theta + Angle2D.M_PI_2;
            double tmp = x;
            x = -y;
            y = tmp;
        }
        double cot = Math.cos(theta);
        double sit = Math.sin(theta);
        double tmpx = x;
        double tmpy = y;
        x = (tmpx * cot) - (tmpy * sit);
        y = (tmpx * sit) + (tmpy * cot);
        double ae = la;
        double f = 1.0d - (lb / la);
        double e2 = f * (2.0d - f);
        double g = 1.0d - f;
        double g2 = g * g;
        double z = y;
        double z2 = y * y;
        double r = x;
        double r2 = x * x;
        double g2r2ma2pz2 = (g2 * (r2 - (ae * ae))) + z2;
        double dist = Math.sqrt(r2 + z2);
        boolean inside = g2r2ma2pz2 <= 0.0d;
        if (dist < CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD * ae) {
            System.out.println("point at the center");
            return Vector2D.createPolar(r, 0.0d);
        }
        double cz = r / dist;
        double sz = z / dist;
        double t = z / (dist + r);
        double b = ((g2 * r) * cz) + (z * sz);
        double c = g2r2ma2pz2;
        double k = c / (Math.sqrt((b * b) - ((1.0d - ((e2 * cz) * cz)) * c)) + b);
        double phi = Math.atan2(z - (k * sz), (r - (k * cz)) * g2);
        if (Math.abs(k) < CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD * dist) {
            return Vector2D.createPolar(k, phi);
        }
        for (int iterations = 0; iterations < 100; iterations++) {
            double tildePhi;
            double a = g2r2ma2pz2 + ((((2.0d * r) + k) * g2) * k);
            b = ((-4.0d * k) * z) / a;
            double d = b;
            b += t;
            c = ((2.0d * ((((1.0d + e2) * k) * k) + g2r2ma2pz2)) / a) + (t * b);
            double b2 = b * b;
            double Q = ((3.0d * c) - b2) / 9.0d;
            double R = ((((9.0d * c) - (2.0d * b2)) * b) - (27.0d * (d + (t * c)))) / 54.0d;
            double D = ((Q * Q) * Q) + (R * R);
            double tildeT;
            double tildeT2;
            double tildeT2P1;
            if (D >= 0.0d) {
                double pow;
                double rootD = Math.sqrt(D);
                double rMr = R - rootD;
                double rPr = R + rootD;
                if (rPr > 0.0d) {
                    pow = Math.pow(rPr, 0.3333333333333333d);
                } else {
                    double d2 = -Math.pow(-rPr, 0.3333333333333333d);
                }
                tildeT = ((rMr > 0.0d ? Math.pow(rMr, 0.3333333333333333d) : -Math.pow(-rMr, 0.3333333333333333d)) + pow) - (b * 0.3333333333333333d);
                tildeT2 = tildeT * tildeT;
                tildeT2P1 = 1.0d + tildeT2;
                tildePhi = Math.atan2((z * tildeT2P1) - ((2.0d * k) * tildeT), ((r * tildeT2P1) - ((1.0d - tildeT2) * k)) * g2);
            } else {
                Q = -Q;
                double qRoot = Math.sqrt(Q);
                double alpha = Math.acos(R / (Q * qRoot));
                tildeT = ((2.0d * qRoot) * Math.cos(alpha * 0.3333333333333333d)) - (b * 0.3333333333333333d);
                tildeT2 = tildeT * tildeT;
                tildeT2P1 = 1.0d + tildeT2;
                tildePhi = Math.atan2((z * tildeT2P1) - ((2.0d * k) * tildeT), ((r * tildeT2P1) - ((1.0d - tildeT2) * k)) * g2);
                if (tildePhi * phi < 0.0d) {
                    tildeT = ((2.0d * qRoot) * Math.cos((Angle2D.M_2PI + alpha) * 0.3333333333333333d)) - (b * 0.3333333333333333d);
                    tildeT2 = tildeT * tildeT;
                    tildeT2P1 = 1.0d + tildeT2;
                    tildePhi = Math.atan2((z * tildeT2P1) - ((2.0d * k) * tildeT), ((r * tildeT2P1) - ((1.0d - tildeT2) * k)) * g2);
                    if (tildePhi * phi < 0.0d) {
                        tildeT = ((2.0d * qRoot) * Math.cos((12.566370614359172d + alpha) * 0.3333333333333333d)) - (b * 0.3333333333333333d);
                        tildeT2 = tildeT * tildeT;
                        tildeT2P1 = 1.0d + tildeT2;
                        tildePhi = Math.atan2((z * tildeT2P1) - ((2.0d * k) * tildeT), ((r * tildeT2P1) - ((1.0d - tildeT2) * k)) * g2);
                    }
                }
            }
            double dPhi = Math.abs(0.5d * (tildePhi - phi));
            phi = 0.5d * (phi + tildePhi);
            double cPhi = Math.cos(phi);
            double sPhi = Math.sin(phi);
            b = ae / Math.sqrt(1.0d - ((e2 * sPhi) * sPhi));
            double dR = r - (cPhi * b);
            double dZ = z - ((sPhi * b) * g2);
            k = Math.hypot(dR, dZ);
            if (inside) {
                k = -k;
            }
            t = dZ / (k + dR);
            if (dPhi < 1.0E-14d) {
                if (this.r1 >= this.r2) {
                    return Vector2D.createPolar(-k, phi + theta);
                }
                return Vector2D.createPolar(-k, (phi + theta) - Angle2D.M_PI_2);
            }
        }
        System.out.println("Ellipse.getProjectedVector() did not converge");
        return Vector2D.createPolar(k, phi);
    }

    public Ellipse2D parallel(double d) {
        return new Ellipse2D(this.xc, this.yc, Math.abs(this.r1 + d), Math.abs(this.r2 + d), this.theta, this.direct);
    }

    public boolean isDirect() {
        return this.direct;
    }

    public boolean isCircle() {
        return Math.abs(this.r1 - this.r2) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public double semiMajorAxisLength() {
        return this.r1;
    }

    public double semiMinorAxisLength() {
        return this.r2;
    }

    public Point2D center() {
        return new Point2D(this.xc, this.yc);
    }

    public Point2D focus1() {
        double a;
        double b;
        double theta;
        if (this.r1 > this.r2) {
            a = this.r1;
            b = this.r2;
            theta = this.theta;
        } else {
            a = this.r2;
            b = this.r1;
            theta = this.theta + Angle2D.M_PI_2;
        }
        return Point2D.createPolar(this.xc, this.yc, Math.sqrt((a * a) - (b * b)), FastMath.PI + theta);
    }

    public Point2D focus2() {
        double a;
        double b;
        double theta;
        if (this.r1 > this.r2) {
            a = this.r1;
            b = this.r2;
            theta = this.theta;
        } else {
            a = this.r2;
            b = this.r1;
            theta = this.theta + Angle2D.M_PI_2;
        }
        return Point2D.createPolar(this.xc, this.yc, Math.sqrt((a * a) - (b * b)), theta);
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

    public Type conicType() {
        if (Math.abs(this.r1 - this.r2) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return Type.CIRCLE;
        }
        return Type.ELLIPSE;
    }

    public double[] conicCoefficients() {
        double r1Sq = this.r1 * this.r1;
        double r2Sq = this.r2 * this.r2;
        double sint = Math.sin(this.theta);
        double cost = Math.cos(this.theta);
        double sin2t = (2.0d * sint) * cost;
        double sintSq = sint * sint;
        double costSq = cost * cost;
        double xcSq = this.xc * this.xc;
        double ycSq = this.yc * this.yc;
        double r1SqInv = 1.0d / r1Sq;
        double r2SqInv = 1.0d / r2Sq;
        double a = (costSq / r1Sq) + (sintSq / r2Sq);
        double b = ((r2Sq - r1Sq) * sin2t) / (r1Sq * r2Sq);
        double c = (costSq / r2Sq) + (sintSq / r1Sq);
        double d = ((-this.yc) * b) - ((2.0d * this.xc) * a);
        double e = ((-this.xc) * b) - ((2.0d * this.yc) * c);
        double f = ((-1.0d + (((xcSq + ycSq) * (r1SqInv + r2SqInv)) / 2.0d)) + ((((costSq - sintSq) * (xcSq - ycSq)) * (r1SqInv - r2SqInv)) / 2.0d)) + (((this.xc * this.yc) * (r1SqInv - r2SqInv)) * sin2t);
        return new double[]{a, b, c, d, e, f};
    }

    public double eccentricity() {
        double r = Math.min(this.r1, this.r2) / Math.max(this.r1, this.r2);
        return Math.sqrt(1.0d - (r * r));
    }

    public double windingAngle(Point2D point) {
        if (signedDistance(point) > 0.0d) {
            return 0.0d;
        }
        return this.direct ? Angle2D.M_2PI : -6.283185307179586d;
    }

    public boolean isInside(Point2D point) {
        int i = 1;
        Point2D pt = AffineTransform2D.createRotation(this.xc, this.yc, -this.theta).transform(point);
        double xp = (pt.x() - this.xc) / this.r1;
        double yp = (pt.y() - this.yc) / this.r2;
        int i2 = (xp * xp) + (yp * yp) < 1.0d ? 1 : 0;
        if (this.direct) {
            i = 0;
        }
        return i2 ^ i;
    }

    public double signedDistance(Point2D point) {
        double dist = asPolyline(180).distance(point);
        return isInside(point) ? -dist : dist;
    }

    public double signedDistance(double x, double y) {
        return signedDistance(new Point2D(x, y));
    }

    public Vector2D tangent(double t) {
        if (!this.direct) {
            t = -t;
        }
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        if (this.direct) {
            return new Vector2D((((-this.r1) * Math.sin(t)) * cot) - ((this.r2 * Math.cos(t)) * sit), (((-this.r1) * Math.sin(t)) * sit) + ((this.r2 * Math.cos(t)) * cot));
        }
        return new Vector2D(((this.r1 * Math.sin(t)) * cot) + ((this.r2 * Math.cos(t)) * sit), ((this.r1 * Math.sin(t)) * sit) - ((this.r2 * Math.cos(t)) * cot));
    }

    public double curvature(double t) {
        if (!this.direct) {
            t = -t;
        }
        double k = (this.r1 * this.r2) / Math.pow(Math.hypot(this.r2 * Math.cos(t), this.r1 * Math.sin(t)), 3.0d);
        return this.direct ? k : -k;
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

    public LinearCurve2D asPolyline(int n) {
        return null;
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
        if (!this.direct) {
            t = -t;
        }
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        return new Point2D((this.xc + ((this.r1 * Math.cos(t)) * cot)) - ((this.r2 * Math.sin(t)) * sit), (this.yc + ((this.r1 * Math.cos(t)) * sit)) + ((this.r2 * Math.sin(t)) * cot));
    }

    public Point2D firstPoint() {
        return new Point2D(this.xc + (this.r1 * Math.cos(this.theta)), this.yc + (this.r1 * Math.sin(this.theta)));
    }

    public Point2D lastPoint() {
        return new Point2D(this.xc + (this.r1 * Math.cos(this.theta)), this.yc + (this.r1 * Math.sin(this.theta)));
    }

    private Point2D toUnitCircle(Point2D point) {
        double xp = point.x();
        xp -= this.xc;
        double yp = point.y() - this.yc;
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        double xp1 = (xp * cot) + (yp * sit);
        yp = ((-xp) * sit) + (yp * cot);
        xp = xp1 / this.r1;
        yp /= this.r2;
        if (!this.direct) {
            yp = -yp;
        }
        return new Point2D(xp, yp);
    }

    public double position(Point2D point) {
        Point2D p2 = toUnitCircle(point);
        double xp = p2.x();
        double yp = p2.y();
        return Math.abs(Math.hypot(xp, yp) - 1.0d) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE ? Angle2D.horizontalAngle(xp, yp) : Double.NaN;
    }

    public double project(Point2D point) {
        Point2D p2 = toUnitCircle(point);
        return Angle2D.horizontalAngle(p2.x(), p2.y());
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        return null;
    }

    public Ellipse2D reverse() {
        return new Ellipse2D(this.xc, this.yc, this.r1, this.r2, this.theta, !this.direct);
    }

    public EllipseArc2D subCurve(double t0, double t1) {
        double startAngle;
        double extent;
        if (this.direct) {
            startAngle = t0;
            extent = Angle2D.formatAngle(t1 - t0);
        } else {
            extent = -Angle2D.formatAngle(t1 - t0);
            startAngle = Angle2D.formatAngle(-t0);
        }
        return new EllipseArc2D(this, startAngle, extent);
    }

    public double distance(Point2D point) {
        return asPolyline(180).distance(point);
    }

    public double distance(double x, double y) {
        return distance(new Point2D(x, y));
    }

    public Collection<Point2D> intersections(LineSegment2D line) {
        LinearShape2D line2 = line;
        Circle2D circle = new Circle2D(0.0d, 0.0d, 1.0d);
        Collection<Point2D> points = circle.intersections(line2);
        if (points.size() == 0) {
            return points;
        }
        ArrayList<Point2D> res = new ArrayList(points.size());
        for (Point2D point : points) {
            res.add(point(circle.position(point)));
        }
        return res;
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(double x, double y) {
        return distance(x, y) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public Path getGeneralPath() {
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        Path path = new Path();
        path.moveTo((float) (this.xc + (this.r1 * cot)), (float) (this.yc + (this.r1 * sit)));
        return appendPath(path);
    }

    public Path appendPath(Path path) {
        double cot = Math.cos(this.theta);
        double sit = Math.sin(this.theta);
        double t;
        if (this.direct) {
            for (t = 0.1d; t <= Angle2D.M_2PI; t += 0.1d) {
                path.lineTo((float) ((this.xc + ((this.r1 * Math.cos(t)) * cot)) - ((this.r2 * Math.sin(t)) * sit)), (float) ((this.yc + ((this.r2 * Math.sin(t)) * cot)) + ((this.r1 * Math.cos(t)) * sit)));
            }
        } else {
            for (t = 0.1d; t <= Angle2D.M_2PI; t += 0.1d) {
                path.lineTo((float) ((this.xc + ((this.r1 * Math.cos(t)) * cot)) + ((this.r2 * Math.sin(t)) * sit)), (float) ((this.yc - ((this.r2 * Math.sin(t)) * cot)) + ((this.r1 * Math.cos(t)) * sit)));
            }
        }
        path.lineTo((float) (this.xc + (this.r1 * cot)), (float) (this.yc + (this.r1 * sit)));
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ellipse2D)) {
            return false;
        }
        Ellipse2D ell = (Ellipse2D) obj;
        if (!ell.center().almostEquals(center(), eps)) {
            return false;
        }
        if (Math.abs(ell.r1 - this.r1) > eps) {
            return false;
        }
        if (Math.abs(ell.r2 - this.r2) > eps) {
            return false;
        }
        if (!Angle2D.almostEquals(ell.angle(), angle(), eps)) {
            return false;
        }
        if (ell.isDirect() != isDirect()) {
            return false;
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ellipse2D)) {
            return false;
        }
        Ellipse2D that = (Ellipse2D) obj;
        if (!EqualUtils.areEqual(this.xc, that.xc)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.yc, that.yc)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.r1, that.r1)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.r2, that.r2)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.theta, that.theta)) {
            return false;
        }
        if (this.direct != that.direct) {
            return false;
        }
        return true;
    }

    @Deprecated
    public Ellipse2D clone() {
        return new Ellipse2D(this.xc, this.yc, this.r1, this.r2, this.theta, this.direct);
    }

    public String toString() {
        String str = "Ellipse2D(%f,%f,%f,%f,%f,%s)";
        Object[] objArr = new Object[6];
        objArr[0] = Double.valueOf(this.xc);
        objArr[1] = Double.valueOf(this.yc);
        objArr[2] = Double.valueOf(this.r1);
        objArr[3] = Double.valueOf(this.r2);
        objArr[4] = Double.valueOf(this.theta);
        objArr[5] = this.direct ? Constants.TRUE : Constants.FALSE;
        return String.format(str, objArr);
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
