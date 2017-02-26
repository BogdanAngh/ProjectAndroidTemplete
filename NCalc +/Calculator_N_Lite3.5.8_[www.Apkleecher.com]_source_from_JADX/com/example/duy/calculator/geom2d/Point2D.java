package com.example.duy.calculator.geom2d;

import com.example.duy.calculator.geom2d.util.EqualUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class Point2D implements GeometricObject2D, Cloneable {
    public double x;
    public double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Deprecated
    public Point2D(Point2D point) {
        this(point.x, point.y);
    }

    @Deprecated
    public static Point2D create(double x, double y) {
        return new Point2D(x, y);
    }

    public static Point2D create(Point2D point) {
        return new Point2D(point.x, point.y);
    }

    public static Point2D createPolar(double rho, double theta) {
        return new Point2D(Math.cos(theta) * rho, Math.sin(theta) * rho);
    }

    public static Point2D createPolar(Point2D point, double rho, double theta) {
        return new Point2D(point.x + (Math.cos(theta) * rho), point.y + (Math.sin(theta) * rho));
    }

    public static Point2D createPolar(double x0, double y0, double rho, double theta) {
        return new Point2D((Math.cos(theta) * rho) + x0, (Math.sin(theta) * rho) + y0);
    }

    public static double distance(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    public static double distance(Point2D p1, Point2D p2) {
        return Math.hypot(p1.x - p2.x, p1.y - p2.y);
    }

    public static boolean isColinear(Point2D p1, Point2D p2, Point2D p3) {
        return Math.abs(((p2.x - p1.x) * (p3.y - p1.y)) - ((p2.y - p1.y) * (p3.x - p1.x))) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
    }

    public static int ccw(Point2D p0, Point2D p1, Point2D p2) {
        double x0 = p0.x;
        double y0 = p0.y;
        double dx1 = p1.x - x0;
        double dy1 = p1.y - y0;
        double dx2 = p2.x - x0;
        double dy2 = p2.y - y0;
        if (dx1 * dy2 > dy1 * dx2) {
            return 1;
        }
        if (dx1 * dy2 < dy1 * dx2) {
            return -1;
        }
        if (dx1 * dx2 < 0.0d || dy1 * dy2 < 0.0d) {
            return -1;
        }
        if (Math.hypot(dx1, dy1) < Math.hypot(dx2, dy2)) {
            return 1;
        }
        return 0;
    }

    public static Point2D midPoint(Point2D p1, Point2D p2) {
        return new Point2D((p1.x + p2.x) / 2.0d, (p1.y + p2.y) / 2.0d);
    }

    public static Point2D centroid(Point2D[] points) {
        int n = points.length;
        double sx = 0.0d;
        double sy = 0.0d;
        for (int i = 0; i < n; i++) {
            sx += points[i].x;
            sy += points[i].y;
        }
        return new Point2D(sx / ((double) n), sy / ((double) n));
    }

    public static Point2D centroid(Point2D[] points, double[] weights) {
        int n = points.length;
        if (n != weights.length) {
            throw new RuntimeException("Arrays must have the same size");
        }
        double sx = 0.0d;
        double sy = 0.0d;
        double sw = 0.0d;
        for (int i = 0; i < n; i++) {
            double w = weights[i];
            sx += points[i].x * w;
            sy += points[i].y * w;
            sw += w;
        }
        return new Point2D(sx / sw, sy / sw);
    }

    public static Point2D centroid(Collection<? extends Point2D> points) {
        int n = points.size();
        double sx = 0.0d;
        double sy = 0.0d;
        for (Point2D point : points) {
            sx += point.x;
            sy += point.y;
        }
        return new Point2D(sx / ((double) n), sy / ((double) n));
    }

    public static Point2D centroid(Point2D pt1, Point2D pt2, Point2D pt3) {
        return new Point2D(((pt1.x + pt2.x) + pt3.x) / 3.0d, ((pt1.y + pt2.y) + pt3.y) / 3.0d);
    }

    public Point2D plus(Point2D p) {
        return new Point2D(this.x + p.x, this.y + p.y);
    }

    public Point2D plus(Vector2D v) {
        return new Point2D(this.x + v.x, this.y + v.y);
    }

    public Point2D minus(Point2D p) {
        return new Point2D(this.x - p.x, this.y - p.y);
    }

    public Point2D minus(Vector2D v) {
        return new Point2D(this.x - v.x, this.y - v.y);
    }

    public Point2D translate(double tx, double ty) {
        return new Point2D(this.x + tx, this.y + ty);
    }

    public Point2D scale(double kx, double ky) {
        return new Point2D(this.x * kx, this.y * ky);
    }

    public Point2D scale(double k) {
        return new Point2D(this.x * k, this.y * k);
    }

    public Point2D rotate(double theta) {
        double cot = Math.cos(theta);
        double sit = Math.sin(theta);
        return new Point2D((this.x * cot) - (this.y * sit), (this.x * sit) + (this.y * cot));
    }

    public Point2D rotate(Point2D center, double theta) {
        double cx = center.x;
        double cy = center.y;
        double cot = Math.cos(theta);
        double sit = Math.sin(theta);
        return new Point2D((((this.x * cot) - (this.y * sit)) + ((1.0d - cot) * cx)) + (sit * cy), (((this.x * sit) + (this.y * cot)) + ((1.0d - cot) * cy)) - (sit * cx));
    }

    public double x() {
        return this.x;
    }

    public double getX() {
        return this.x;
    }

    public double y() {
        return this.y;
    }

    public double getY() {
        return this.y;
    }

    public int size() {
        return 1;
    }

    public Collection<Point2D> points() {
        ArrayList<Point2D> array = new ArrayList(1);
        array.add(this);
        return array;
    }

    public double distance(Point2D point) {
        return distance(point.x, point.y);
    }

    public double distance(double x, double y) {
        return Math.hypot(this.x - x, this.y - y);
    }

    public boolean isBounded() {
        if (Double.isInfinite(this.x) || Double.isInfinite(this.y) || Double.isNaN(this.x) || Double.isNaN(this.y)) {
            return false;
        }
        return true;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean contains(double x, double y) {
        return equals(new Point2D(x, y));
    }

    public boolean contains(Point2D p) {
        return equals(p);
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point2D)) {
            return false;
        }
        Point2D p = (Point2D) obj;
        if (Math.abs(this.x - p.x) > eps) {
            return false;
        }
        if (Math.abs(this.y - p.y) > eps) {
            return false;
        }
        return true;
    }

    public Iterator<Point2D> iterator() {
        return points().iterator();
    }

    public String toString() {
        return new String("Point2D(" + this.x + VectorFormat.DEFAULT_SEPARATOR + this.y + ")");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Point2D)) {
            return false;
        }
        Point2D that = (Point2D) obj;
        if (!EqualUtils.areEqual(this.x, that.x)) {
            return false;
        }
        if (EqualUtils.areEqual(this.y, that.y)) {
            return true;
        }
        return false;
    }

    public Point2D clone() {
        return new Point2D(this.x, this.y);
    }

    public int hashCode() {
        return ((Double.valueOf(this.x).hashCode() + 31) * 31) + Double.valueOf(this.y).hashCode();
    }
}
