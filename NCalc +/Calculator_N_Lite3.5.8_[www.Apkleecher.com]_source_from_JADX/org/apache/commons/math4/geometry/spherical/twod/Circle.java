package org.apache.commons.math4.geometry.spherical.twod;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.geometry.Point;
import org.apache.commons.math4.geometry.euclidean.threed.Rotation;
import org.apache.commons.math4.geometry.euclidean.threed.Vector3D;
import org.apache.commons.math4.geometry.partitioning.Embedding;
import org.apache.commons.math4.geometry.partitioning.Hyperplane;
import org.apache.commons.math4.geometry.partitioning.SubHyperplane;
import org.apache.commons.math4.geometry.partitioning.Transform;
import org.apache.commons.math4.geometry.spherical.oned.Arc;
import org.apache.commons.math4.geometry.spherical.oned.ArcsSet;
import org.apache.commons.math4.geometry.spherical.oned.S1Point;
import org.apache.commons.math4.geometry.spherical.oned.Sphere1D;
import org.apache.commons.math4.util.FastMath;

public class Circle implements Hyperplane<Sphere2D>, Embedding<Sphere2D, Sphere1D> {
    private Vector3D pole;
    private final double tolerance;
    private Vector3D x;
    private Vector3D y;

    private static class CircleTransform implements Transform<Sphere2D, Sphere1D> {
        private final Rotation rotation;

        public CircleTransform(Rotation rotation) {
            this.rotation = rotation;
        }

        public S2Point apply(Point<Sphere2D> point) {
            return new S2Point(this.rotation.applyTo(((S2Point) point).getVector()));
        }

        public Circle apply(Hyperplane<Sphere2D> hyperplane) {
            Circle circle = (Circle) hyperplane;
            return new Circle(this.rotation.applyTo(circle.x), this.rotation.applyTo(circle.y), circle.tolerance, null);
        }

        public SubHyperplane<Sphere1D> apply(SubHyperplane<Sphere1D> sub, Hyperplane<Sphere2D> hyperplane, Hyperplane<Sphere2D> hyperplane2) {
            return sub;
        }
    }

    public Circle(Vector3D pole, double tolerance) {
        reset(pole);
        this.tolerance = tolerance;
    }

    public Circle(S2Point first, S2Point second, double tolerance) {
        reset(first.getVector().crossProduct(second.getVector()));
        this.tolerance = tolerance;
    }

    private Circle(Vector3D pole, Vector3D x, Vector3D y, double tolerance) {
        this.pole = pole;
        this.x = x;
        this.y = y;
        this.tolerance = tolerance;
    }

    public Circle(Circle circle) {
        this(circle.pole, circle.x, circle.y, circle.tolerance);
    }

    public Circle copySelf() {
        return new Circle(this);
    }

    public void reset(Vector3D newPole) {
        this.pole = newPole.normalize();
        this.x = newPole.orthogonal();
        this.y = Vector3D.crossProduct(newPole, this.x).normalize();
    }

    public void revertSelf() {
        this.y = this.y.negate();
        this.pole = this.pole.negate();
    }

    public Circle getReverse() {
        return new Circle(this.pole.negate(), this.x, this.y.negate(), this.tolerance);
    }

    public Point<Sphere2D> project(Point<Sphere2D> point) {
        return toSpace(toSubSpace((Point) point));
    }

    public double getTolerance() {
        return this.tolerance;
    }

    public S1Point toSubSpace(Point<Sphere2D> point) {
        return new S1Point(getPhase(((S2Point) point).getVector()));
    }

    public double getPhase(Vector3D direction) {
        return FastMath.PI + FastMath.atan2(-direction.dotProduct(this.y), -direction.dotProduct(this.x));
    }

    public S2Point toSpace(Point<Sphere1D> point) {
        return new S2Point(getPointAt(((S1Point) point).getAlpha()));
    }

    public Vector3D getPointAt(double alpha) {
        return new Vector3D(FastMath.cos(alpha), this.x, FastMath.sin(alpha), this.y);
    }

    public Vector3D getXAxis() {
        return this.x;
    }

    public Vector3D getYAxis() {
        return this.y;
    }

    public Vector3D getPole() {
        return this.pole;
    }

    public Arc getInsideArc(Circle other) {
        double alpha = getPhase(other.pole);
        return new Arc(alpha - Angle2D.M_PI_2, Angle2D.M_PI_2 + alpha, this.tolerance);
    }

    public SubCircle wholeHyperplane() {
        return new SubCircle(this, new ArcsSet(this.tolerance));
    }

    public SphericalPolygonsSet wholeSpace() {
        return new SphericalPolygonsSet(this.tolerance);
    }

    public double getOffset(Point<Sphere2D> point) {
        return getOffset(((S2Point) point).getVector());
    }

    public double getOffset(Vector3D direction) {
        return Vector3D.angle(this.pole, direction) - Angle2D.M_PI_2;
    }

    public boolean sameOrientationAs(Hyperplane<Sphere2D> other) {
        return Vector3D.dotProduct(this.pole, ((Circle) other).pole) >= 0.0d;
    }

    public static Transform<Sphere2D, Sphere1D> getTransform(Rotation rotation) {
        return new CircleTransform(rotation);
    }
}
