package com.example.duy.calculator.geom2d.polygon;

import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import org.apache.commons.math4.util.FastMath;

public class MultiPolygon2D implements Polygon2D {
    ArrayList<LinearRing2D> rings;

    public MultiPolygon2D(int nRings) {
        this.rings = new ArrayList(1);
        this.rings.ensureCapacity(nRings);
    }

    public MultiPolygon2D(LinearRing2D... rings) {
        this.rings = new ArrayList(1);
        for (LinearRing2D ring : rings) {
            this.rings.add(ring);
        }
    }

    public MultiPolygon2D(Collection<LinearRing2D> lines) {
        this.rings = new ArrayList(1);
        this.rings.addAll(lines);
    }

    public static MultiPolygon2D create(Collection<LinearRing2D> rings) {
        return new MultiPolygon2D((Collection) rings);
    }

    public static MultiPolygon2D create(LinearRing2D... rings) {
        return new MultiPolygon2D(rings);
    }

    public void addRing(LinearRing2D ring) {
        this.rings.add(ring);
    }

    public void insertRing(int index, LinearRing2D ring) {
        this.rings.add(index, ring);
    }

    public void removeRing(LinearRing2D ring) {
        this.rings.remove(ring);
    }

    public void clearRings() {
        this.rings.clear();
    }

    public LinearRing2D getRing(int index) {
        return (LinearRing2D) this.rings.get(index);
    }

    public void setRing(int index, LinearRing2D ring) {
        this.rings.set(index, ring);
    }

    public int ringNumber() {
        return this.rings.size();
    }

    public double area() {
        return Polygons2D.computeArea((Polygon2D) this);
    }

    public Point2D centroid() {
        return Polygons2D.computeCentroid((Polygon2D) this);
    }

    public Collection<LineSegment2D> edges() {
        ArrayList<LineSegment2D> edges = new ArrayList(edgeNumber());
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            edges.addAll(((LinearRing2D) it.next()).edges());
        }
        return edges;
    }

    public int edgeNumber() {
        int count = 0;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            count += ((LinearRing2D) it.next()).vertexNumber();
        }
        return count;
    }

    public Collection<Point2D> vertices() {
        ArrayList<Point2D> points = new ArrayList(vertexNumber());
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            points.addAll(((LinearRing2D) it.next()).vertices());
        }
        return points;
    }

    public Point2D vertex(int i) {
        int count = 0;
        LinearRing2D boundary = null;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            LinearRing2D ring = (LinearRing2D) it.next();
            int nv = ring.vertexNumber();
            if (count + nv > i) {
                boundary = ring;
                break;
            }
            count += nv;
        }
        if (boundary != null) {
            return boundary.vertex(i - count);
        }
        throw new IndexOutOfBoundsException();
    }

    public void setVertex(int i, Point2D point) {
        int count = 0;
        LinearRing2D boundary = null;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            LinearRing2D ring = (LinearRing2D) it.next();
            int nv = ring.vertexNumber();
            if (count + nv > i) {
                boundary = ring;
                break;
            }
            count += nv;
        }
        if (boundary == null) {
            throw new IndexOutOfBoundsException();
        }
        boundary.setVertex(i - count, point);
    }

    public void addVertex(Point2D position) {
        if (this.rings.size() == 0) {
            throw new RuntimeException("Can not add a vertex to a multipolygon with no ring");
        }
        ((LinearRing2D) this.rings.get(this.rings.size() - 1)).addVertex(position);
    }

    public void insertVertex(int index, Point2D point) {
        if (this.rings.size() == 0) {
            throw new RuntimeException("Can not add a vertex to a multipolygon with no ring");
        }
        int nv = vertexNumber();
        if (nv <= index) {
            throw new IllegalArgumentException("Can not insert vertex at position " + index + " (max is " + nv + ")");
        }
        int count = 0;
        LinearRing2D boundary = null;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            LinearRing2D ring = (LinearRing2D) it.next();
            nv = ring.vertexNumber();
            if (count + nv > index) {
                boundary = ring;
                break;
            }
            count += nv;
        }
        if (boundary == null) {
            throw new IndexOutOfBoundsException();
        }
        boundary.insertVertex(index - count, point);
    }

    public void removeVertex(int i) {
        int count = 0;
        LinearRing2D boundary = null;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            LinearRing2D ring = (LinearRing2D) it.next();
            int nv = ring.vertexNumber();
            if (count + nv > i) {
                boundary = ring;
                break;
            }
            count += nv;
        }
        if (boundary == null) {
            throw new IndexOutOfBoundsException();
        }
        boundary.removeVertex(i - count);
    }

    public int vertexNumber() {
        int count = 0;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            count += ((LinearRing2D) it.next()).vertexNumber();
        }
        return count;
    }

    public int closestVertexIndex(Point2D point) {
        double minDist = Double.POSITIVE_INFINITY;
        int index = -1;
        int i = 0;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            for (Point2D vertex : ((LinearRing2D) it.next()).vertices()) {
                double dist = vertex.distance(point);
                if (dist < minDist) {
                    index = i;
                    minDist = dist;
                }
                i++;
            }
        }
        return index;
    }

    public Polygon2D asPolygon(int n) {
        return this;
    }

    public Collection<LinearRing2D> contours() {
        return Collections.unmodifiableList(this.rings);
    }

    public Polygon2D complement() {
        Collection reverseLines = new ArrayList(this.rings.size());
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            reverseLines.add(((LinearRing2D) it.next()).reverse());
        }
        return new MultiPolygon2D(reverseLines);
    }

    public boolean isEmpty() {
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            if (!((LinearRing2D) it.next()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public boolean contains(Point2D point) {
        double angle = 0.0d;
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            angle += ((LinearRing2D) it.next()).windingAngle(point);
        }
        if (area() > 0.0d) {
            if (angle > FastMath.PI) {
                return true;
            }
            return false;
        } else if (angle <= -3.141592653589793d) {
            return false;
        } else {
            return true;
        }
    }

    public boolean contains(double x, double y) {
        return contains(new Point2D(x, y));
    }

    public boolean almostEquals(GeometricObject2D obj, double eps) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultiPolygon2D)) {
            return false;
        }
        MultiPolygon2D polygon = (MultiPolygon2D) obj;
        if (polygon.rings.size() != this.rings.size()) {
            return false;
        }
        for (int i = 0; i < this.rings.size(); i++) {
            if (!((LinearRing2D) this.rings.get(i)).almostEquals((GeometricObject2D) polygon.rings.get(i), eps)) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MultiPolygon2D)) {
            return false;
        }
        MultiPolygon2D polygon = (MultiPolygon2D) obj;
        if (polygon.rings.size() != this.rings.size()) {
            return false;
        }
        for (int i = 0; i < this.rings.size(); i++) {
            if (!((LinearRing2D) this.rings.get(i)).equals(polygon.rings.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Deprecated
    public MultiPolygon2D clone() {
        Collection array = new ArrayList(this.rings.size());
        Iterator it = this.rings.iterator();
        while (it.hasNext()) {
            array.add(new LinearRing2D((LinearRing2D) it.next()));
        }
        return new MultiPolygon2D(array);
    }
}
