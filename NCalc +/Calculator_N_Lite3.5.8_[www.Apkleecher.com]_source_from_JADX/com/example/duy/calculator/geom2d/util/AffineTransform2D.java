package com.example.duy.calculator.geom2d.util;

import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class AffineTransform2D implements GeometricObject2D {
    public static final double ACCURACY = 1.0E-12d;
    protected double m00;
    protected double m01;
    protected double m02;
    protected double m10;
    protected double m11;
    protected double m12;

    public AffineTransform2D() {
        this.m11 = 1.0d;
        this.m00 = 1.0d;
        this.m10 = 0.0d;
        this.m01 = 0.0d;
        this.m12 = 0.0d;
        this.m02 = 0.0d;
    }

    @Deprecated
    public AffineTransform2D(AffineTransform2D trans) {
        this.m00 = trans.m00;
        this.m01 = trans.m01;
        this.m02 = trans.m02;
        this.m10 = trans.m10;
        this.m11 = trans.m11;
        this.m12 = trans.m12;
    }

    public AffineTransform2D(double[] coefs) {
        if (coefs.length == 4) {
            this.m00 = coefs[0];
            this.m01 = coefs[1];
            this.m10 = coefs[2];
            this.m11 = coefs[3];
            return;
        }
        this.m00 = coefs[0];
        this.m01 = coefs[1];
        this.m02 = coefs[2];
        this.m10 = coefs[3];
        this.m11 = coefs[4];
        this.m12 = coefs[5];
    }

    public AffineTransform2D(double xx, double yx, double tx, double xy, double yy, double ty) {
        this.m00 = xx;
        this.m01 = yx;
        this.m02 = tx;
        this.m10 = xy;
        this.m11 = yy;
        this.m12 = ty;
    }

    public static AffineTransform2D createIdentity() {
        return new AffineTransform2D(1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d);
    }

    public static AffineTransform2D create(AffineTransform2D trans) {
        return new AffineTransform2D(trans.m00, trans.m01, trans.m02, trans.m10, trans.m11, trans.m12);
    }

    public static AffineTransform2D create(double[] coefs) {
        if (coefs.length == 4) {
            return new AffineTransform2D(coefs[0], coefs[1], 0.0d, coefs[2], coefs[3], 0.0d);
        }
        if (coefs.length == 6) {
            return new AffineTransform2D(coefs[0], coefs[1], coefs[2], coefs[3], coefs[4], coefs[5]);
        }
        throw new IllegalArgumentException("Input array must have either 4 or 6 elements");
    }

    public static AffineTransform2D create(double xx, double yx, double tx, double xy, double yy, double ty) {
        return new AffineTransform2D(xx, yx, tx, xy, yy, ty);
    }

    public static AffineTransform2D createGlideReflection(LinearShape2D line, double distance) {
        Vector2D vector = line.direction().normalize();
        Point2D origin = line.origin();
        double dx = vector.x();
        double dy = vector.y();
        double delta = (dx * dx) + (dy * dy);
        double dx2 = dx * dx;
        double dy2 = dy * dy;
        double dxy = dx * dy;
        double dxy0 = dx * origin.y();
        double dyx0 = dy * origin.x();
        return new AffineTransform2D((dx2 - dy2) / delta, (2.0d * dxy) / delta, (((2.0d * dy) * (dyx0 - dxy0)) / delta) + (dx * distance), (2.0d * dxy) / delta, (dy2 - dx2) / delta, (((2.0d * dx) * (dxy0 - dyx0)) / delta) + (dy * distance));
    }

    @Deprecated
    public static AffineTransform2D createHomothecy(Point2D center, double k) {
        return createScaling(center, k, k);
    }

    public static AffineTransform2D createLineReflection(LinearShape2D line) {
        Point2D origin = line.origin();
        Vector2D vector = line.direction();
        double dx = vector.x();
        double dy = vector.y();
        double x0 = origin.x();
        double y0 = origin.y();
        double dx2 = dx * dx;
        double dy2 = dy * dy;
        double dxy = dx * dy;
        double delta = dx2 + dy2;
        return new AffineTransform2D((dx2 - dy2) / delta, (2.0d * dxy) / delta, (2.0d * ((dy2 * x0) - (dxy * y0))) / delta, (2.0d * dxy) / delta, (dy2 - dx2) / delta, (2.0d * ((dx2 * y0) - (dxy * x0))) / delta);
    }

    public static AffineTransform2D createPointReflection(Point2D center) {
        return createScaling(center, -1.0d, -1.0d);
    }

    public static AffineTransform2D createQuadrantRotation(int numQuadrant) {
        switch (((numQuadrant % 4) + 4) % 4) {
            case ValueServer.DIGEST_MODE /*0*/:
                return new AffineTransform2D(1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d);
            case ValueServer.REPLAY_MODE /*1*/:
                return new AffineTransform2D(0.0d, -1.0d, 0.0d, 1.0d, 0.0d, 0.0d);
            case IExpr.DOUBLEID /*2*/:
                return new AffineTransform2D(-1.0d, 0.0d, 0.0d, 0.0d, -1.0d, 0.0d);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return new AffineTransform2D(0.0d, 1.0d, 0.0d, -1.0d, 0.0d, 0.0d);
            default:
                throw new RuntimeException("Error in integer rounding...");
        }
    }

    public static AffineTransform2D createQuadrantRotation(Point2D center, int numQuadrant) {
        AffineTransform2D trans = createQuadrantRotation(numQuadrant);
        trans.recenter(center.x(), center.y());
        return trans;
    }

    public static AffineTransform2D createQuadrantRotation(double x0, double y0, int numQuadrant) {
        AffineTransform2D trans = createQuadrantRotation(numQuadrant);
        trans.recenter(x0, y0);
        return trans;
    }

    public static AffineTransform2D createRotation(double angle) {
        return createRotation(0.0d, 0.0d, angle);
    }

    public static AffineTransform2D createRotation(Point2D center, double angle) {
        return createRotation(center.x(), center.y(), angle);
    }

    public static AffineTransform2D createRotation(double cx, double cy, double angle) {
        angle = Angle2D.formatAngle(angle);
        int k = (int) Math.round((2.0d * angle) / FastMath.PI);
        if (Math.abs(((((double) k) * FastMath.PI) / 2.0d) - angle) < ACCURACY) {
            return createQuadrantRotation(cx, cy, k);
        }
        double cot = Math.cos(angle);
        double sit = Math.sin(angle);
        return new AffineTransform2D(cot, -sit, ((1.0d - cot) * cx) + (sit * cy), sit, cot, ((1.0d - cot) * cy) - (sit * cx));
    }

    public static AffineTransform2D createScaling(double sx, double sy) {
        return createScaling(new Point2D(0.0d, 0.0d), sx, sy);
    }

    public static AffineTransform2D createScaling(Point2D center, double sx, double sy) {
        return new AffineTransform2D(sx, 0.0d, center.x() * (1.0d - sx), 0.0d, sy, (1.0d - sy) * center.y());
    }

    public static AffineTransform2D createShear(double shx, double shy) {
        return new AffineTransform2D(1.0d, shx, 0.0d, shy, 1.0d, 0.0d);
    }

    public static AffineTransform2D createTranslation(Vector2D vect) {
        return new AffineTransform2D(1.0d, 0.0d, vect.x(), 0.0d, 1.0d, vect.y());
    }

    public static AffineTransform2D createTranslation(double dx, double dy) {
        return new AffineTransform2D(1.0d, 0.0d, dx, 0.0d, 1.0d, dy);
    }

    public static boolean isIdentity(AffineTransform2D trans) {
        if (Math.abs(trans.m00 - 1.0d) <= ACCURACY && Math.abs(trans.m01) <= ACCURACY && Math.abs(trans.m02) <= ACCURACY && Math.abs(trans.m10) <= ACCURACY && Math.abs(trans.m11 - 1.0d) <= ACCURACY && Math.abs(trans.m12) <= ACCURACY) {
            return true;
        }
        return false;
    }

    public static boolean isDirect(AffineTransform2D trans) {
        return (trans.m00 * trans.m11) - (trans.m01 * trans.m10) > 0.0d;
    }

    public static boolean isIsometry(AffineTransform2D trans) {
        double a = trans.m00;
        double b = trans.m01;
        double c = trans.m10;
        double d = trans.m11;
        if (Math.abs(((a * a) + (b * b)) - 1.0d) > ACCURACY) {
            return false;
        }
        if (Math.abs(((c * c) + (d * d)) - 1.0d) > ACCURACY) {
            return false;
        }
        if (Math.abs((a * b) + (c * d)) > ACCURACY) {
            return false;
        }
        return true;
    }

    public static boolean isMotion(AffineTransform2D trans) {
        return isIsometry(trans) && isDirect(trans);
    }

    public static boolean isSimilarity(AffineTransform2D trans) {
        double a = trans.m00;
        double b = trans.m01;
        double c = trans.m10;
        double d = trans.m11;
        double k2 = Math.abs((a * d) - (b * c));
        if (Math.abs(((a * a) + (b * b)) - k2) > ACCURACY) {
            return false;
        }
        if (Math.abs(((c * c) + (d * d)) - k2) > ACCURACY) {
            return false;
        }
        if (Math.abs(((a * a) + (c * c)) - k2) > ACCURACY) {
            return false;
        }
        if (Math.abs(((b * b) + (d * d)) - k2) > ACCURACY) {
            return false;
        }
        return true;
    }

    private void recenter(double x0, double y0) {
        this.m02 = ((1.0d - this.m00) * x0) - (this.m01 * y0);
        this.m12 = ((1.0d - this.m11) * y0) - (this.m10 * x0);
    }

    public double[] coefficients() {
        return new double[]{this.m00, this.m01, this.m02, this.m10, this.m11, this.m12};
    }

    public double[][] affineMatrix() {
        tab = new double[3][];
        tab[0] = new double[]{this.m00, this.m01, this.m02};
        tab[1] = new double[]{this.m10, this.m11, this.m12};
        tab[2] = new double[]{0.0d, 0.0d, 1.0d};
        return tab;
    }

    public AffineTransform2D concatenate(AffineTransform2D that) {
        double d = that.m00;
        d = this.m01;
        double d2 = that.m10;
        d = that.m01;
        d = this.m01;
        d2 = that.m11;
        d = that.m02;
        d = this.m01;
        d2 = that.m12;
        d = this.m02;
        d = that.m00;
        d = this.m11;
        d2 = that.m10;
        d = that.m01;
        d = this.m11;
        d2 = that.m11;
        d = that.m02;
        d = this.m11;
        d2 = that.m12;
        return new AffineTransform2D((this.m00 * r0) + (r0 * r0), (this.m00 * r0) + (r0 * r0), ((this.m00 * r0) + (r0 * r0)) + r0, (this.m10 * r0) + (r0 * r0), (this.m10 * r0) + (r0 * r0), ((this.m10 * r0) + (r0 * r0)) + this.m12);
    }

    public AffineTransform2D chain(AffineTransform2D that) {
        double d = this.m11;
        d = this.m02;
        d = that.m11;
        double d2 = this.m12;
        return new AffineTransform2D((that.m00 * this.m00) + (that.m01 * this.m10), (that.m00 * this.m01) + (that.m01 * this.m11), ((that.m00 * this.m02) + (that.m01 * this.m12)) + that.m02, (that.m10 * this.m00) + (that.m11 * this.m10), (that.m10 * this.m01) + (that.m11 * r0), ((that.m10 * r0) + (r0 * r0)) + that.m12);
    }

    public AffineTransform2D preConcatenate(AffineTransform2D that) {
        return chain(that);
    }

    public boolean isSimilarity() {
        return isSimilarity(this);
    }

    public boolean isMotion() {
        return isMotion(this);
    }

    public boolean isIsometry() {
        return isIsometry(this);
    }

    public boolean isDirect() {
        return isDirect(this);
    }

    public boolean isIdentity() {
        return isIdentity(this);
    }

    public Point2D transform(Point2D p) {
        return new Point2D(((p.x() * this.m00) + (p.y() * this.m01)) + this.m02, ((p.x() * this.m10) + (p.y() * this.m11)) + this.m12);
    }

    public Point2D[] transform(Point2D[] src, Point2D[] dst) {
        if (dst == null) {
            dst = new Point2D[src.length];
        }
        for (int i = 0; i < src.length; i++) {
            double x = src[i].x();
            double y = src[i].y();
            dst[i] = new Point2D(((this.m00 * x) + (this.m01 * y)) + this.m02, ((this.m10 * x) + (this.m11 * y)) + this.m12);
        }
        return dst;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AffineTransform2D)) {
            return false;
        }
        double[] tab1 = coefficients();
        double[] tab2 = ((AffineTransform2D) obj).coefficients();
        for (int i = 0; i < 6; i++) {
            if (Math.abs(tab1[i] - tab2[i]) > eps) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return new String("AffineTransform2D(" + this.m00 + "," + this.m01 + "," + this.m02 + "," + this.m10 + "," + this.m11 + "," + this.m12 + ",");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AffineTransform2D)) {
            return false;
        }
        AffineTransform2D that = (AffineTransform2D) obj;
        if (!EqualUtils.areEqual(this.m00, that.m00)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.m01, that.m01)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.m02, that.m02)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.m00, that.m00)) {
            return false;
        }
        if (!EqualUtils.areEqual(this.m01, that.m01)) {
            return false;
        }
        if (EqualUtils.areEqual(this.m02, that.m02)) {
            return true;
        }
        return false;
    }

    @Deprecated
    public AffineTransform2D clone() {
        return new AffineTransform2D(this.m00, this.m01, this.m02, this.m10, this.m11, this.m12);
    }
}
