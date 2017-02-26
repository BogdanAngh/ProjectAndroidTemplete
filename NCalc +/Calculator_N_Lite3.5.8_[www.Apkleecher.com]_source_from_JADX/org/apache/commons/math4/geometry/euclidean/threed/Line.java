package org.apache.commons.math4.geometry.euclidean.threed;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.Vector;
import org.apache.commons.math4.geometry.euclidean.oned.Euclidean1D;
import org.apache.commons.math4.geometry.euclidean.oned.IntervalsSet;
import org.apache.commons.math4.geometry.euclidean.oned.Vector1D;
import org.apache.commons.math4.geometry.partitioning.Embedding;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class Line implements Embedding<Euclidean3D, Euclidean1D> {
    private Vector3D direction;
    private final double tolerance;
    private Vector3D zero;

    public Line(Vector3D p1, Vector3D p2, double tolerance) throws MathIllegalArgumentException {
        reset(p1, p2);
        this.tolerance = tolerance;
    }

    public Line(Line line) {
        this.direction = line.direction;
        this.zero = line.zero;
        this.tolerance = line.tolerance;
    }

    public void reset(Vector3D p1, Vector3D p2) throws MathIllegalArgumentException {
        Vector3D delta = p2.subtract((Vector) p1);
        double norm2 = delta.getNormSq();
        if (norm2 == 0.0d) {
            throw new MathIllegalArgumentException(LocalizedFormats.ZERO_NORM, new Object[0]);
        }
        this.direction = new Vector3D(1.0d / FastMath.sqrt(norm2), delta);
        this.zero = new Vector3D(1.0d, p1, (-p1.dotProduct(delta)) / norm2, delta);
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public Line revert() {
        Line reverted = new Line(this);
        reverted.direction = reverted.direction.negate();
        return reverted;
    }

    public Vector3D getDirection() {
        return this.direction;
    }

    public Vector3D getOrigin() {
        return this.zero;
    }

    public double getAbscissa(Vector3D point) {
        return point.subtract(this.zero).dotProduct(this.direction);
    }

    public Vector3D pointAt(double abscissa) {
        return new Vector3D(1.0d, this.zero, abscissa, this.direction);
    }

    public Vector1D toSubSpace(Vector<Euclidean3D> vector) {
        return toSubSpace((Point) vector);
    }

    public Vector3D toSpace(Vector<Euclidean1D> vector) {
        return toSpace((Point) vector);
    }

    public Vector1D toSubSpace(Point<Euclidean3D> point) {
        return new Vector1D(getAbscissa((Vector3D) point));
    }

    public Vector3D toSpace(Point<Euclidean1D> point) {
        return pointAt(((Vector1D) point).getX());
    }

    public boolean isSimilarTo(Line line) {
        double angle = Vector3D.angle(this.direction, line.direction);
        return (angle < this.tolerance || angle > FastMath.PI - this.tolerance) && contains(line.zero);
    }

    public boolean contains(Vector3D p) {
        return distance(p) < this.tolerance;
    }

    public double distance(Vector3D p) {
        Vector3D d = p.subtract(this.zero);
        return new Vector3D(1.0d, d, -d.dotProduct(this.direction), this.direction).getNorm();
    }

    public double distance(Line line) {
        Vector3D normal = Vector3D.crossProduct(this.direction, line.direction);
        double n = normal.getNorm();
        if (n < Precision.SAFE_MIN) {
            return distance(line.zero);
        }
        return FastMath.abs(line.zero.subtract(this.zero).dotProduct(normal) / n);
    }

    public Vector3D closestPoint(Line line) {
        double cos = this.direction.dotProduct(line.direction);
        double n = 1.0d - (cos * cos);
        if (n < Precision.EPSILON) {
            return this.zero;
        }
        Vector3D delta0 = line.zero.subtract(this.zero);
        return new Vector3D(1.0d, this.zero, (delta0.dotProduct(this.direction) - (delta0.dotProduct(line.direction) * cos)) / n, this.direction);
    }

    public Vector3D intersection(Line line) {
        Vector3D closest = closestPoint(line);
        return line.contains(closest) ? closest : null;
    }

    public SubLine wholeLine() {
        return new SubLine(this, new IntervalsSet(this.tolerance));
    }
}
