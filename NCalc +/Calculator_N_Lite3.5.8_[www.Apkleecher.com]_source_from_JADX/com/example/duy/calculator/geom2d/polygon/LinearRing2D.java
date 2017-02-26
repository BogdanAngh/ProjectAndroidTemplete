package com.example.duy.calculator.geom2d.polygon;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.FastMath;

public class LinearRing2D extends LinearCurve2D {
    public LinearRing2D(int n) {
        super(n);
    }

    public LinearRing2D(Point2D... vertices) {
        super(vertices);
    }

    public LinearRing2D(double[] xcoords, double[] ycoords) {
        super(xcoords, ycoords);
    }

    public LinearRing2D(Collection<? extends Point2D> points) {
        super((Collection) points);
    }

    public LinearRing2D(LinearCurve2D lineString) {
        super(lineString.vertices);
    }

    public static LinearRing2D create(Collection<? extends Point2D> points) {
        return new LinearRing2D((Collection) points);
    }

    public static LinearRing2D create(Point2D... vertices) {
        return new LinearRing2D(vertices);
    }

    public double area() {
        Point2D prev = (Point2D) this.vertices.get(this.vertices.size() - 1);
        double area = 0.0d;
        Iterator it = this.vertices.iterator();
        while (it.hasNext()) {
            Point2D point = (Point2D) it.next();
            area += (prev.x() * point.y()) - (prev.y() * point.x());
            prev = point;
        }
        return area / 2.0d;
    }

    public LinearRing2D simplify(double distMax) {
        return new LinearRing2D(Polylines2D.simplifyClosedPolyline(this.vertices, distMax));
    }

    public Collection<LineSegment2D> edges() {
        int n = this.vertices.size();
        ArrayList<LineSegment2D> edges = new ArrayList(n);
        if (n >= 2) {
            for (int i = 0; i < n - 1; i++) {
                edges.add(new LineSegment2D((Point2D) this.vertices.get(i), (Point2D) this.vertices.get(i + 1)));
            }
            Point2D p0 = (Point2D) this.vertices.get(0);
            Point2D pn = (Point2D) this.vertices.get(n - 1);
            if (pn.distance(p0) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
                edges.add(new LineSegment2D(pn, p0));
            }
        }
        return edges;
    }

    public int edgeNumber() {
        int n = this.vertices.size();
        return n > 1 ? n : 0;
    }

    public LineSegment2D edge(int index) {
        return new LineSegment2D((Point2D) this.vertices.get(index), (Point2D) this.vertices.get((index + 1) % this.vertices.size()));
    }

    public LineSegment2D lastEdge() {
        int n = this.vertices.size();
        if (n < 2) {
            return null;
        }
        return new LineSegment2D((Point2D) this.vertices.get(n - 1), (Point2D) this.vertices.get(0));
    }

    public double windingAngle(Point2D point) {
        return ((double) (Polygons2D.windingNumber(this.vertices, point) * 2)) * FastMath.PI;
    }

    public boolean isInside(double x, double y) {
        return isInside(new Point2D(x, y));
    }

    public boolean isInside(Point2D point) {
        if (contains(point)) {
            return true;
        }
        double area = area();
        int winding = Polygons2D.windingNumber(this.vertices, point);
        if (area > 0.0d) {
            if (winding != 1) {
                return false;
            }
            return true;
        } else if (winding != 0) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isClosed() {
        return true;
    }

    public LinearCurve2D asPolyline(int n) {
        return null;
    }

    public Point2D point(double t) {
        double t0 = t0();
        t = Math.max(Math.min(t, t1()), t0);
        int n = this.vertices.size();
        int ind0 = (int) Math.floor(OpenMapRealVector.DEFAULT_ZERO_TOLERANCE + t);
        double tl = t - ((double) ind0);
        if (ind0 == n) {
            ind0 = 0;
        }
        Point2D p0 = (Point2D) this.vertices.get(ind0);
        if (Math.abs(t - ((double) ind0)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return p0;
        }
        int ind1 = ind0 + 1;
        if (ind1 == n) {
            ind1 = 0;
        }
        Point2D p1 = (Point2D) this.vertices.get(ind1);
        double x0 = p0.x();
        double y0 = p0.y();
        return new Point2D((tl * (p1.x() - x0)) + x0, (tl * (p1.y() - y0)) + y0);
    }

    public double t1() {
        return (double) this.vertices.size();
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D lastPoint() {
        if (this.vertices.size() == 0) {
            return null;
        }
        return (Point2D) this.vertices.get(0);
    }

    public LinearRing2D reverse() {
        Point2D[] points2 = new Point2D[this.vertices.size()];
        int n = this.vertices.size();
        if (n > 0) {
            points2[0] = (Point2D) this.vertices.get(0);
        }
        for (int i = 1; i < n; i++) {
            points2[i] = (Point2D) this.vertices.get(n - i);
        }
        return new LinearRing2D(points2);
    }

    public Polyline2D subCurve(double t0, double t1) {
        Polyline2D res = new Polyline2D();
        int indMax = vertexNumber();
        t0 = Math.min(Math.max(t0, 0.0d), (double) indMax);
        t1 = Math.min(Math.max(t1, 0.0d), (double) indMax);
        int ind0 = (int) Math.floor(OpenMapRealVector.DEFAULT_ZERO_TOLERANCE + t0);
        int ind1 = (int) Math.floor(OpenMapRealVector.DEFAULT_ZERO_TOLERANCE + t1);
        if (ind0 != ind1 || t0 >= t1) {
            res.addVertex(point(t0));
            int n;
            if (ind1 > ind0) {
                for (n = ind0 + 1; n <= ind1; n++) {
                    res.addVertex((Point2D) this.vertices.get(n));
                }
            } else {
                for (n = ind0 + 1; n < indMax; n++) {
                    res.addVertex((Point2D) this.vertices.get(n));
                }
                for (n = 0; n <= ind1; n++) {
                    res.addVertex((Point2D) this.vertices.get(n));
                }
            }
            res.addVertex(point(t1));
        } else {
            res.addVertex(point(t0));
            res.addVertex(point(t1));
        }
        return res;
    }

    public Path appendPath(Path path) {
        if (this.vertices.size() >= 2) {
            Point2D p0 = lastPoint();
            path.moveTo((float) p0.x(), (float) p0.y());
            Iterator it = this.vertices.iterator();
            while (it.hasNext()) {
                Point2D point = (Point2D) it.next();
                path.lineTo((float) point.x(), (float) point.y());
            }
            path.close();
        }
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof LinearRing2D)) {
            return false;
        }
        LinearRing2D ring = (LinearRing2D) obj;
        if (this.vertices.size() != ring.vertices.size()) {
            return false;
        }
        for (int i = 0; i < this.vertices.size(); i++) {
            if (!((Point2D) this.vertices.get(i)).almostEquals((GeometricObject2D) ring.vertices.get(i), eps)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        if (!(object instanceof LinearRing2D)) {
            return false;
        }
        LinearRing2D ring = (LinearRing2D) object;
        if (this.vertices.size() != ring.vertices.size()) {
            return false;
        }
        for (int i = 0; i < this.vertices.size(); i++) {
            if (!((Point2D) this.vertices.get(i)).equals(ring.vertices.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public LinearRing2D clone() {
        Collection array = new ArrayList(this.vertices.size());
        Iterator it = this.vertices.iterator();
        while (it.hasNext()) {
            array.add((Point2D) it.next());
        }
        return new LinearRing2D(array);
    }
}
