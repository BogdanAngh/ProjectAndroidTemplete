package org.apache.commons.math4.geometry.euclidean.twod;

import java.awt.geom.AffineTransform;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.OrientedPoint;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.partitioning.Embedding;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Transform;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Line implements Hyperplane<Euclidean2D>, Embedding<Euclidean2D, Euclidean1D> {
    private double angle;
    private double cos;
    private double originOffset;
    private Line reverse;
    private double sin;
    private final double tolerance;

    private static class LineTransform implements Transform<Euclidean2D, Euclidean1D> {
        private final double c11;
        private final double c1X;
        private final double c1Y;
        private final double cX1;
        private final double cXX;
        private final double cXY;
        private final double cY1;
        private final double cYX;
        private final double cYY;

        public LineTransform(AffineTransform transform) throws MathIllegalArgumentException {
            double[] m = new double[6];
            transform.getMatrix(m);
            this.cXX = m[0];
            this.cXY = m[2];
            this.cX1 = m[4];
            this.cYX = m[1];
            this.cYY = m[3];
            this.cY1 = m[5];
            this.c1Y = MathArrays.linearCombination(this.cXY, this.cY1, -this.cYY, this.cX1);
            this.c1X = MathArrays.linearCombination(this.cXX, this.cY1, -this.cYX, this.cX1);
            this.c11 = MathArrays.linearCombination(this.cXX, this.cYY, -this.cYX, this.cXY);
            if (FastMath.abs(this.c11) < 1.0E-20d) {
                throw new MathIllegalArgumentException(LocalizedFormats.NON_INVERTIBLE_TRANSFORM, new Object[0]);
            }
        }

        public Vector2D apply(Point<Euclidean2D> point) {
            Vector2D p2D = (Vector2D) point;
            double x = p2D.getX();
            double y = p2D.getY();
            return new Vector2D(MathArrays.linearCombination(this.cXX, x, this.cXY, y, this.cX1, 1.0d), MathArrays.linearCombination(this.cYX, x, this.cYY, y, this.cY1, 1.0d));
        }

        public Line apply(Hyperplane<Euclidean2D> hyperplane) {
            Line line = (Line) hyperplane;
            double rOffset = MathArrays.linearCombination(this.c1X, line.cos, this.c1Y, line.sin, this.c11, line.originOffset);
            double rCos = MathArrays.linearCombination(this.cXX, line.cos, this.cXY, line.sin);
            double rSin = MathArrays.linearCombination(this.cYX, line.cos, this.cYY, line.sin);
            double inv = 1.0d / FastMath.sqrt((rSin * rSin) + (rCos * rCos));
            return new Line(inv * rCos, inv * rSin, inv * rOffset, line.tolerance, null);
        }

        public SubHyperplane<Euclidean1D> apply(SubHyperplane<Euclidean1D> sub, Hyperplane<Euclidean2D> original, Hyperplane<Euclidean2D> transformed) {
            OrientedPoint op = (OrientedPoint) sub.getHyperplane();
            Line originalLine = (Line) original;
            return new OrientedPoint(((Line) transformed).toSubSpace(apply(originalLine.toSpace(op.getLocation()))), op.isDirect(), originalLine.tolerance).wholeHyperplane();
        }
    }

    public Line(Vector2D p1, Vector2D p2, double tolerance) {
        reset(p1, p2);
        this.tolerance = tolerance;
    }

    public Line(Vector2D p, double angle, double tolerance) {
        reset(p, angle);
        this.tolerance = tolerance;
    }

    private Line(double angle, double cos, double sin, double originOffset, double tolerance) {
        this.angle = angle;
        this.cos = cos;
        this.sin = sin;
        this.originOffset = originOffset;
        this.tolerance = tolerance;
        this.reverse = null;
    }

    public Line(Line line) {
        this.angle = MathUtils.normalizeAngle(line.angle, FastMath.PI);
        this.cos = line.cos;
        this.sin = line.sin;
        this.originOffset = line.originOffset;
        this.tolerance = line.tolerance;
        this.reverse = null;
    }

    public Line copySelf() {
        return new Line(this);
    }

    public void reset(Vector2D p1, Vector2D p2) {
        unlinkReverse();
        double dx = p2.getX() - p1.getX();
        double dy = p2.getY() - p1.getY();
        double d = FastMath.hypot(dx, dy);
        if (d == 0.0d) {
            this.angle = 0.0d;
            this.cos = 1.0d;
            this.sin = 0.0d;
            this.originOffset = p1.getY();
            return;
        }
        this.angle = FastMath.PI + FastMath.atan2(-dy, -dx);
        this.cos = dx / d;
        this.sin = dy / d;
        this.originOffset = MathArrays.linearCombination(p2.getX(), p1.getY(), -p1.getX(), p2.getY()) / d;
    }

    public void reset(Vector2D p, double alpha) {
        unlinkReverse();
        this.angle = MathUtils.normalizeAngle(alpha, FastMath.PI);
        this.cos = FastMath.cos(this.angle);
        this.sin = FastMath.sin(this.angle);
        this.originOffset = MathArrays.linearCombination(this.cos, p.getY(), -this.sin, p.getX());
    }

    public void revertSelf() {
        unlinkReverse();
        if (this.angle < FastMath.PI) {
            this.angle += FastMath.PI;
        } else {
            this.angle -= FastMath.PI;
        }
        this.cos = -this.cos;
        this.sin = -this.sin;
        this.originOffset = -this.originOffset;
    }

    private void unlinkReverse() {
        if (this.reverse != null) {
            this.reverse.reverse = null;
        }
        this.reverse = null;
    }

    public Line getReverse() {
        if (this.reverse == null) {
            this.reverse = new Line(this.angle < FastMath.PI ? this.angle + FastMath.PI : this.angle - FastMath.PI, -this.cos, -this.sin, -this.originOffset, this.tolerance);
            this.reverse.reverse = this;
        }
        return this.reverse;
    }

    public Vector1D toSubSpace(Vector<Euclidean2D> vector) {
        return toSubSpace((Point) vector);
    }

    public Vector2D toSpace(Vector<Euclidean1D> vector) {
        return toSpace((Point) vector);
    }

    public Vector1D toSubSpace(Point<Euclidean2D> point) {
        Vector2D p2 = (Vector2D) point;
        return new Vector1D(MathArrays.linearCombination(this.cos, p2.getX(), this.sin, p2.getY()));
    }

    public Vector2D toSpace(Point<Euclidean1D> point) {
        double abscissa = ((Vector1D) point).getX();
        return new Vector2D(MathArrays.linearCombination(abscissa, this.cos, -this.originOffset, this.sin), MathArrays.linearCombination(abscissa, this.sin, this.originOffset, this.cos));
    }

    public Vector2D intersection(Line other) {
        double d = MathArrays.linearCombination(this.sin, other.cos, -other.sin, this.cos);
        if (FastMath.abs(d) < this.tolerance) {
            return null;
        }
        return new Vector2D(MathArrays.linearCombination(this.cos, other.originOffset, -other.cos, this.originOffset) / d, MathArrays.linearCombination(this.sin, other.originOffset, -other.sin, this.originOffset) / d);
    }

    public Point<Euclidean2D> project(Point<Euclidean2D> point) {
        return toSpace(toSubSpace((Point) point));
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public SubLine wholeHyperplane() {
        return new SubLine(this, new IntervalsSet(this.tolerance));
    }

    public PolygonsSet wholeSpace() {
        return new PolygonsSet(this.tolerance);
    }

    public double getOffset(Line line) {
        return (MathArrays.linearCombination(this.cos, line.cos, this.sin, line.sin) > 0.0d ? -line.originOffset : line.originOffset) + this.originOffset;
    }

    public double getOffset(Vector<Euclidean2D> vector) {
        return getOffset((Point) vector);
    }

    public double getOffset(Point<Euclidean2D> point) {
        Vector2D p2 = (Vector2D) point;
        return MathArrays.linearCombination(this.sin, p2.getX(), -this.cos, p2.getY(), 1.0d, this.originOffset);
    }

    public boolean sameOrientationAs(Hyperplane<Euclidean2D> other) {
        Line otherL = (Line) other;
        return MathArrays.linearCombination(this.sin, otherL.sin, this.cos, otherL.cos) >= 0.0d;
    }

    public Vector2D getPointAt(Vector1D abscissa, double offset) {
        double x = abscissa.getX();
        double dOffset = offset - this.originOffset;
        return new Vector2D(MathArrays.linearCombination(x, this.cos, dOffset, this.sin), MathArrays.linearCombination(x, this.sin, -dOffset, this.cos));
    }

    public boolean contains(Vector2D p) {
        return FastMath.abs(getOffset((Vector) p)) < this.tolerance;
    }

    public double distance(Vector2D p) {
        return FastMath.abs(getOffset((Vector) p));
    }

    public boolean isParallelTo(Line line) {
        return FastMath.abs(MathArrays.linearCombination(this.sin, line.cos, -this.cos, line.sin)) < this.tolerance;
    }

    public void translateToPoint(Vector2D p) {
        this.originOffset = MathArrays.linearCombination(this.cos, p.getY(), -this.sin, p.getX());
    }

    public double getAngle() {
        return MathUtils.normalizeAngle(this.angle, FastMath.PI);
    }

    public void setAngle(double angle) {
        unlinkReverse();
        this.angle = MathUtils.normalizeAngle(angle, FastMath.PI);
        this.cos = FastMath.cos(this.angle);
        this.sin = FastMath.sin(this.angle);
    }

    public double getOriginOffset() {
        return this.originOffset;
    }

    public void setOriginOffset(double offset) {
        unlinkReverse();
        this.originOffset = offset;
    }

    public static Transform<Euclidean2D, Euclidean1D> getTransform(AffineTransform transform) throws MathIllegalArgumentException {
        return new LineTransform(transform);
    }
}
