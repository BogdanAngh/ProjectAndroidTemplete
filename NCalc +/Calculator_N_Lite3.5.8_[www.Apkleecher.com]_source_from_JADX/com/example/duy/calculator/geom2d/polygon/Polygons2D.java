package com.example.duy.calculator.geom2d.polygon;

import com.example.duy.calculator.geom2d.Point2D;
import java.util.Collection;

public final class Polygons2D {
    public static final SimplePolygon2D createRectangle(Point2D p1, Point2D p2) {
        return createRectangle(p1.getX(), p1.getY(), p2.getX(), p2.getY());
    }

    public static final SimplePolygon2D createRectangle(double x1, double y1, double x2, double y2) {
        double xmin = Math.min(x1, x2);
        double xmax = Math.max(x1, x2);
        double ymin = Math.min(y1, y2);
        double ymax = Math.max(y1, y2);
        return new SimplePolygon2D(new Point2D(xmin, ymin), new Point2D(xmax, ymin), new Point2D(xmax, ymax), new Point2D(xmin, ymax));
    }

    public static final SimplePolygon2D createCenteredRectangle(Point2D center, double length, double width) {
        double xc = center.x();
        double yc = center.y();
        double len = length / 2.0d;
        double wid = width / 2.0d;
        double x1 = xc - len;
        double y1 = yc - wid;
        double x2 = xc + len;
        double y2 = yc + wid;
        return new SimplePolygon2D(new Point2D(x1, y1), new Point2D(x2, y1), new Point2D(x2, y2), new Point2D(x1, y2));
    }

    public static final SimplePolygon2D createOrientedRectangle(Point2D center, double length, double width, double theta) {
        double xc = center.x();
        double yc = center.y();
        double len = length / 2.0d;
        double wid = width / 2.0d;
        double cot = Math.cos(theta);
        double sit = Math.sin(theta);
        r15 = new Point2D[4];
        r15[0] = new Point2D((((-len) * cot) + (wid * sit)) + xc, (((-len) * sit) - (wid * cot)) + yc);
        r15[1] = new Point2D(((len * cot) + (wid * sit)) + xc, ((len * sit) - (wid * cot)) + yc);
        r15[2] = new Point2D(((len * cot) - (wid * sit)) + xc, ((len * sit) + (wid * cot)) + yc);
        r15[3] = new Point2D((((-len) * cot) - (wid * sit)) + xc, (((-len) * sit) + (wid * cot)) + yc);
        return new SimplePolygon2D(r15);
    }

    public static final Point2D computeCentroid(Polygon2D polygon) {
        if (polygon instanceof SimplePolygon2D) {
            return computeCentroid(((SimplePolygon2D) polygon).getRing());
        }
        double xc = 0.0d;
        double yc = 0.0d;
        double cumArea = 0.0d;
        for (LinearRing2D ring : polygon.contours()) {
            double area = computeArea(ring);
            Point2D centroid = computeCentroid(ring);
            xc += centroid.x() * area;
            yc += centroid.y() * area;
            cumArea += area;
        }
        return new Point2D(xc / cumArea, yc / cumArea);
    }

    public static final Point2D computeCentroid(LinearRing2D ring) {
        double xc = 0.0d;
        double yc = 0.0d;
        Point2D prev = ring.vertex(ring.vertexNumber() - 1);
        double xp = prev.x();
        double yp = prev.y();
        for (Point2D point : ring.vertices()) {
            double x = point.x();
            double y = point.y();
            double tmp = (xp * y) - (yp * x);
            xc += (x + xp) * tmp;
            yc += (y + yp) * tmp;
            prev = point;
            xp = x;
            yp = y;
        }
        double denom = computeArea(ring) * 6.0d;
        return new Point2D(xc / denom, yc / denom);
    }

    public static final double computeArea(Polygon2D polygon) {
        double area = 0.0d;
        for (LinearRing2D ring : polygon.contours()) {
            area += computeArea(ring);
        }
        return area;
    }

    public static double computeArea(LinearRing2D ring) {
        double area = 0.0d;
        Point2D prev = ring.vertex(ring.vertexNumber() - 1);
        for (Point2D point : ring.vertices()) {
            area += (prev.x() * point.y()) - (prev.y() * point.x());
            prev = point;
        }
        return area / 2.0d;
    }

    public static int windingNumber(Collection<Point2D> vertices, Point2D point) {
        int wn = 0;
        Point2D previous = null;
        for (Point2D previous2 : vertices) {
        }
        double y1 = previous.y();
        double y = point.y();
        for (Point2D current : vertices) {
            double y2 = current.y();
            if (y1 <= y) {
                if (y2 > y && isLeft(previous, current, point) > 0) {
                    wn++;
                }
            } else if (y2 <= y && isLeft(previous, current, point) < 0) {
                wn--;
            }
            y1 = y2;
            previous = current;
        }
        return wn;
    }

    private static final int isLeft(Point2D p1, Point2D p2, Point2D pt) {
        double x = p1.x();
        double y = p1.y();
        return (int) Math.signum(((p2.x() - x) * (pt.y() - y)) - ((pt.x() - x) * (p2.y() - y)));
    }
}
