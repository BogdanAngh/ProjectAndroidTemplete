package com.example.duy.calculator.geom2d.polygon;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.line.StraightLine2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math4.linear.OpenMapRealVector;

public class Polyline2D extends LinearCurve2D {
    public Polyline2D() {
        super(1);
    }

    public Polyline2D(int nVertices) {
        super(nVertices);
    }

    public Polyline2D(Point2D initialPoint) {
        this.vertices.add(initialPoint);
    }

    public Polyline2D(Point2D... vertices) {
        super(vertices);
    }

    public Polyline2D(Collection<? extends Point2D> vertices) {
        super((Collection) vertices);
    }

    public Polyline2D(double[] xcoords, double[] ycoords) {
        super(xcoords, ycoords);
    }

    public Polyline2D(LinearCurve2D lineString) {
        super(lineString.vertices);
        if (lineString.isClosed()) {
            this.vertices.add(lineString.firstPoint());
        }
    }

    public static Polyline2D create(Collection<? extends Point2D> points) {
        return new Polyline2D((Collection) points);
    }

    public static Polyline2D create(Point2D... points) {
        return new Polyline2D(points);
    }

    public Polyline2D simplify(double distMax) {
        return new Polyline2D(Polylines2D.simplifyPolyline(this.vertices, distMax));
    }

    public Collection<LineSegment2D> edges() {
        int n = this.vertices.size();
        ArrayList<LineSegment2D> edges = new ArrayList(n);
        if (n >= 2) {
            for (int i = 0; i < n - 1; i++) {
                edges.add(new LineSegment2D((Point2D) this.vertices.get(i), (Point2D) this.vertices.get(i + 1)));
            }
        }
        return edges;
    }

    public int edgeNumber() {
        int n = this.vertices.size();
        if (n > 1) {
            return n - 1;
        }
        return 0;
    }

    public LineSegment2D edge(int index) {
        return new LineSegment2D((Point2D) this.vertices.get(index), (Point2D) this.vertices.get(index + 1));
    }

    public LineSegment2D lastEdge() {
        int n = this.vertices.size();
        if (n < 2) {
            return null;
        }
        return new LineSegment2D((Point2D) this.vertices.get(n - 2), (Point2D) this.vertices.get(n - 1));
    }

    public double windingAngle(Point2D point) {
        double angle = 0.0d;
        for (int i = 0; i < this.vertices.size() - 1; i++) {
            angle += new LineSegment2D((Point2D) this.vertices.get(i), (Point2D) this.vertices.get(i + 1)).windingAngle(point);
        }
        return angle;
    }

    public boolean isInside(Point2D pt) {
        if (new LinearRing2D(vertexArray()).isInside(pt)) {
            return true;
        }
        if (this.vertices.size() < 3) {
            return false;
        }
        Point2D p0 = firstPoint();
        if (new StraightLine2D(vertex(1), p0).isInside(pt)) {
            return false;
        }
        Point2D p1 = lastPoint();
        if (new StraightLine2D(p1, vertex(vertexNumber() - 2)).isInside(pt)) {
            return false;
        }
        if (new StraightLine2D(p0, p1).isInside(pt)) {
            return true;
        }
        return false;
    }

    public boolean isClosed() {
        return false;
    }

    public LinearCurve2D asPolyline(int n) {
        return null;
    }

    public Point2D point(double t) {
        double t0 = t0();
        t = Math.max(Math.min(t, t1()), t0);
        int ind0 = (int) Math.floor(OpenMapRealVector.DEFAULT_ZERO_TOLERANCE + t);
        double tl = t - ((double) ind0);
        Point2D p0 = (Point2D) this.vertices.get(ind0);
        if (Math.abs(t - ((double) ind0)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return p0;
        }
        int ind1 = ind0 + 1;
        Point2D p1 = (Point2D) this.vertices.get(ind1);
        double x0 = p0.x();
        double y0 = p0.y();
        return new Point2D((tl * (p1.x() - x0)) + x0, (tl * (p1.y() - y0)) + y0);
    }

    public double t1() {
        return (double) (this.vertices.size() - 1);
    }

    @Deprecated
    public double getT1() {
        return t1();
    }

    public Point2D lastPoint() {
        if (this.vertices.size() == 0) {
            return null;
        }
        return (Point2D) this.vertices.get(this.vertices.size() - 1);
    }

    public Polyline2D reverse() {
        Point2D[] points2 = new Point2D[this.vertices.size()];
        int n = this.vertices.size();
        for (int i = 0; i < n; i++) {
            points2[i] = (Point2D) this.vertices.get((n - 1) - i);
        }
        return new Polyline2D(points2);
    }

    public Polyline2D subCurve(double t0, double t1) {
        Polyline2D res = new Polyline2D();
        if (t1 >= t0) {
            int indMax = (int) t1();
            t0 = Math.min(Math.max(t0, 0.0d), (double) indMax);
            t1 = Math.min(Math.max(t1, 0.0d), (double) indMax);
            int ind0 = (int) Math.floor(t0);
            int ind1 = (int) Math.floor(t1);
            if (ind0 == ind1) {
                res.addVertex(point(t0));
                res.addVertex(point(t1));
            } else {
                res.addVertex(point(t0));
                for (int n = ind0 + 1; n <= ind1; n++) {
                    res.addVertex((Point2D) this.vertices.get(n));
                }
                res.addVertex(point(t1));
            }
        }
        return res;
    }

    public Path appendPath(Path path) {
        if (this.vertices.size() >= 2) {
            Iterator<Point2D> iter = this.vertices.iterator();
            Point2D point = (Point2D) iter.next();
            while (iter.hasNext()) {
                point = (Point2D) iter.next();
                path.lineTo((float) point.x(), (float) point.y());
            }
        }
        return path;
    }

    public Path asGeneralPath() {
        Path path = new Path();
        if (this.vertices.size() >= 2) {
            Iterator<Point2D> iter = this.vertices.iterator();
            Point2D point = (Point2D) iter.next();
            path.moveTo((float) point.x(), (float) point.y());
            while (iter.hasNext()) {
                point = (Point2D) iter.next();
                path.lineTo((float) point.x(), (float) point.y());
            }
        }
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Polyline2D)) {
            return false;
        }
        Polyline2D polyline = (Polyline2D) obj;
        if (this.vertices.size() != polyline.vertices.size()) {
            return false;
        }
        for (int i = 0; i < this.vertices.size(); i++) {
            if (!((Point2D) this.vertices.get(i)).almostEquals((GeometricObject2D) polyline.vertices.get(i), eps)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Polyline2D)) {
            return false;
        }
        Polyline2D polyline = (Polyline2D) object;
        if (this.vertices.size() != polyline.vertices.size()) {
            return false;
        }
        for (int i = 0; i < this.vertices.size(); i++) {
            if (!((Point2D) this.vertices.get(i)).equals(polyline.vertices.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public Polyline2D clone() {
        Collection array = new ArrayList(this.vertices.size());
        Iterator it = this.vertices.iterator();
        while (it.hasNext()) {
            array.add((Point2D) it.next());
        }
        return new Polyline2D(array);
    }
}
