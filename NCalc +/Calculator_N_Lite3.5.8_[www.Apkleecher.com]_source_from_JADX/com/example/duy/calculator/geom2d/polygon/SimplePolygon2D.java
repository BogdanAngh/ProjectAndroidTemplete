package com.example.duy.calculator.geom2d.polygon;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.util.AffineTransform2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class SimplePolygon2D implements Polygon2D {
    protected ArrayList<Point2D> vertices;

    public SimplePolygon2D() {
        this.vertices = new ArrayList();
    }

    public SimplePolygon2D(Point2D... vertices) {
        this.vertices = new ArrayList(vertices.length);
        for (Point2D vertex : vertices) {
            this.vertices.add(vertex);
        }
    }

    public SimplePolygon2D(double[] xcoords, double[] ycoords) {
        this.vertices = new ArrayList(xcoords.length);
        for (int i = 0; i < xcoords.length; i++) {
            this.vertices.add(new Point2D(xcoords[i], ycoords[i]));
        }
    }

    public SimplePolygon2D(Collection<? extends Point2D> points) {
        this.vertices = new ArrayList(points.size());
        this.vertices.addAll(points);
    }

    public SimplePolygon2D(int nVertices) {
        this.vertices = new ArrayList(nVertices);
    }

    public SimplePolygon2D(LinearRing2D ring) {
        this.vertices = new ArrayList(ring.vertexNumber());
        this.vertices.addAll(ring.vertices());
    }

    public SimplePolygon2D(SimplePolygon2D poly) {
        this.vertices = new ArrayList(poly.vertexNumber());
        this.vertices.addAll(poly.vertices);
    }

    public static SimplePolygon2D create(Collection<? extends Point2D> points) {
        return new SimplePolygon2D((Collection) points);
    }

    public static SimplePolygon2D create(Point2D... points) {
        return new SimplePolygon2D(points);
    }

    public int getWindingNumber(double x, double y) {
        return Polygons2D.windingNumber(this.vertices, new Point2D(x, y));
    }

    public SimplePolygon2D simplify(double distMax) {
        return new SimplePolygon2D(Polylines2D.simplifyClosedPolyline(this.vertices, distMax));
    }

    public LinearRing2D getRing() {
        return new LinearRing2D(this.vertices);
    }

    public void addVertex(Point2D point) {
        this.vertices.add(point);
    }

    public void insertVertex(int index, Point2D point) {
        this.vertices.add(index, point);
    }

    public void setVertex(int index, Point2D position) {
        this.vertices.set(index, position);
    }

    public boolean removeVertex(Point2D point) {
        return this.vertices.remove(point);
    }

    public void removeVertex(int index) {
        this.vertices.remove(index);
    }

    public void clearVertices() {
        this.vertices.clear();
    }

    public int closestVertexIndex(Point2D point) {
        double minDist = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int i = 0; i < this.vertices.size(); i++) {
            double dist = ((Point2D) this.vertices.get(i)).distance(point);
            if (dist < minDist) {
                index = i;
                minDist = dist;
            }
        }
        return index;
    }

    public double area() {
        return Polygons2D.computeArea((Polygon2D) this);
    }

    public Point2D centroid() {
        return Polygons2D.computeCentroid((Polygon2D) this);
    }

    public Collection<Point2D> vertices() {
        return this.vertices;
    }

    public Point2D vertex(int i) {
        return (Point2D) this.vertices.get(i);
    }

    public int vertexNumber() {
        return this.vertices.size();
    }

    public Collection<LineSegment2D> edges() {
        int nPoints = this.vertices.size();
        ArrayList<LineSegment2D> edges = new ArrayList(nPoints);
        if (nPoints != 0) {
            for (int i = 0; i < nPoints - 1; i++) {
                edges.add(new LineSegment2D((Point2D) this.vertices.get(i), (Point2D) this.vertices.get(i + 1)));
            }
            edges.add(new LineSegment2D((Point2D) this.vertices.get(nPoints - 1), (Point2D) this.vertices.get(0)));
        }
        return edges;
    }

    public int edgeNumber() {
        return this.vertices.size();
    }

    public Polygon2D asPolygon(int n) {
        return this;
    }

    public Collection<LinearRing2D> contours() {
        ArrayList<LinearRing2D> rings = new ArrayList(1);
        rings.add(new LinearRing2D(this.vertices));
        return rings;
    }

    public SimplePolygon2D complement() {
        int nPoints = this.vertices.size();
        Point2D[] res = new Point2D[nPoints];
        if (nPoints > 0) {
            res[0] = (Point2D) this.vertices.get(0);
        }
        for (int i = 1; i < nPoints; i++) {
            res[i] = (Point2D) this.vertices.get(nPoints - i);
        }
        return new SimplePolygon2D(res);
    }

    public boolean isBounded() {
        return area() > 0.0d;
    }

    public boolean isEmpty() {
        return this.vertices.size() == 0;
    }

    public SimplePolygon2D transform(AffineTransform2D trans) {
        int nPoints = this.vertices.size();
        Point2D[] array = new Point2D[nPoints];
        Point2D[] res = new Point2D[nPoints];
        for (int i = 0; i < nPoints; i++) {
            array[i] = (Point2D) this.vertices.get(i);
            res[i] = new Point2D();
        }
        trans.transform(array, res);
        SimplePolygon2D poly = new SimplePolygon2D(res);
        if (trans.isDirect()) {
            return poly;
        }
        return poly.complement();
    }

    public boolean contains(Point2D p) {
        return contains(p.x(), p.y());
    }

    public boolean contains(double x, double y) {
        double area = area();
        int winding = getWindingNumber(x, y);
        if (area > 0.0d) {
            if (winding == 1) {
                return true;
            }
            return false;
        } else if (winding != 0) {
            return false;
        } else {
            return true;
        }
    }

    public Path getGeneralPath() {
        Path path = new Path();
        if (this.vertices.size() >= 2) {
            Point2D point = (Point2D) this.vertices.get(0);
            path.moveTo((float) point.x(), (float) point.y());
            for (int i = 0; i < this.vertices.size(); i++) {
                point = (Point2D) this.vertices.get(i);
                path.lineTo((float) point.x(), (float) point.y());
            }
            point = (Point2D) this.vertices.get(0);
            path.lineTo((float) point.x(), (float) point.y());
            path.close();
        }
        return path;
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimplePolygon2D)) {
            return false;
        }
        SimplePolygon2D polygon = (SimplePolygon2D) obj;
        int nv = vertexNumber();
        if (polygon.vertexNumber() != nv) {
            return false;
        }
        for (int i = 0; i < nv; i++) {
            if (!vertex(i).almostEquals(polygon.vertex(i), eps)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SimplePolygon2D)) {
            return false;
        }
        SimplePolygon2D polygon = (SimplePolygon2D) obj;
        int nv = vertexNumber();
        if (polygon.vertexNumber() != nv) {
            return false;
        }
        for (int i = 0; i < nv; i++) {
            if (!vertex(i).equals(polygon.vertex(i))) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public SimplePolygon2D clone() {
        Collection array = new ArrayList(this.vertices.size());
        Iterator it = this.vertices.iterator();
        while (it.hasNext()) {
            array.add((Point2D) it.next());
        }
        return new SimplePolygon2D(array);
    }
}
