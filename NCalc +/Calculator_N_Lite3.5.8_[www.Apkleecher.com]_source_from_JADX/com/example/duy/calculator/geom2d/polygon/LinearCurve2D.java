package com.example.duy.calculator.geom2d.polygon;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.curve.ContinuousCurve2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.apache.commons.math4.linear.OpenMapRealVector;

public abstract class LinearCurve2D implements ContinuousCurve2D {
    public ArrayList<Point2D> vertices;

    public abstract LineSegment2D edge(int i);

    public abstract int edgeNumber();

    public abstract Collection<LineSegment2D> edges();

    public abstract LineSegment2D lastEdge();

    public abstract LinearCurve2D simplify(double d);

    public LinearCurve2D() {
        this.vertices = new ArrayList();
    }

    public LinearCurve2D(int nVertices) {
        this.vertices = new ArrayList(nVertices);
    }

    public LinearCurve2D(Point2D... vertices) {
        this.vertices = new ArrayList(vertices.length);
        for (Point2D vertex : vertices) {
            this.vertices.add(vertex);
        }
    }

    public LinearCurve2D(Collection<? extends Point2D> vertices) {
        this.vertices = new ArrayList(vertices.size());
        this.vertices.addAll(vertices);
    }

    public LinearCurve2D(double[] xcoords, double[] ycoords) {
        this.vertices = new ArrayList(xcoords.length);
        int n = xcoords.length;
        this.vertices.ensureCapacity(n);
        for (int i = 0; i < n; i++) {
            this.vertices.add(new Point2D(xcoords[i], ycoords[i]));
        }
    }

    public Iterator<Point2D> vertexIterator() {
        return this.vertices.iterator();
    }

    public Point2D[] vertexArray() {
        return (Point2D[]) this.vertices.toArray(new Point2D[0]);
    }

    public boolean addVertex(Point2D vertex) {
        return this.vertices.add(vertex);
    }

    public void insertVertex(int index, Point2D vertex) {
        this.vertices.add(index, vertex);
    }

    public boolean removeVertex(Point2D vertex) {
        return this.vertices.remove(vertex);
    }

    public Point2D removeVertex(int index) {
        return (Point2D) this.vertices.remove(index);
    }

    public void setVertex(int index, Point2D position) {
        this.vertices.set(index, position);
    }

    public void clearVertices() {
        this.vertices.clear();
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

    public LineSegment2D firstEdge() {
        if (this.vertices.size() < 2) {
            return null;
        }
        return new LineSegment2D((Point2D) this.vertices.get(0), (Point2D) this.vertices.get(1));
    }

    public double length() {
        double sum = 0.0d;
        for (LineSegment2D edge : edges()) {
            sum += edge.length();
        }
        return sum;
    }

    public double length(double pos) {
        double length = 0.0d;
        int index = (int) Math.floor(pos);
        for (int i = 0; i < index; i++) {
            length += edge(i).length();
        }
        if (index >= this.vertices.size() - 1) {
            return length;
        }
        return length + edge(index).length(pos - ((double) index));
    }

    public double position(double length) {
        int index = 0;
        double cumLength = length(t0());
        for (LineSegment2D edge : edges()) {
            double edgeLength = edge.length();
            if (cumLength + edgeLength < length) {
                cumLength += edgeLength;
                index++;
            } else {
                return ((double) index) + edge.position(length - cumLength);
            }
        }
        return 0.0d;
    }

    public double signedDistance(Point2D point) {
        double dist = distance(point.x(), point.y());
        if (isInside(point)) {
            return -dist;
        }
        return dist;
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

    public double signedDistance(double x, double y) {
        double dist = distance(x, y);
        if (isInside(new Point2D(x, y))) {
            return -dist;
        }
        return dist;
    }

    public Vector2D leftTangent(double t) {
        int index = (int) Math.floor(t);
        if (Math.abs(t - ((double) index)) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            index--;
        }
        return edge(index).tangent(0.0d);
    }

    public Vector2D rightTangent(double t) {
        return edge((int) Math.ceil(t)).tangent(0.0d);
    }

    public double curvature(double t) {
        if (Math.abs(((double) Math.round(t)) - t) > OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return 0.0d;
        }
        return Double.POSITIVE_INFINITY;
    }

    public Collection<? extends LineSegment2D> smoothPieces() {
        return edges();
    }

    public double t0() {
        return 0.0d;
    }

    @Deprecated
    public double getT0() {
        return t0();
    }

    public Point2D firstPoint() {
        if (this.vertices.size() == 0) {
            return null;
        }
        return (Point2D) this.vertices.get(0);
    }

    public Collection<Point2D> singularPoints() {
        return this.vertices;
    }

    public boolean isSingular(double pos) {
        if (Math.abs(pos - ((double) Math.round(pos))) < OpenMapRealVector.DEFAULT_ZERO_TOLERANCE) {
            return true;
        }
        return false;
    }

    public double position(Point2D point) {
        int ind = 0;
        double minDist = Double.POSITIVE_INFINITY;
        double x = point.x();
        double y = point.y();
        int i = 0;
        LineSegment2D closest = null;
        for (LineSegment2D edge : edges()) {
            double dist = edge.distance(x, y);
            if (dist < minDist) {
                minDist = dist;
                ind = i;
                closest = edge;
            }
            i++;
        }
        return closest.position(point) + ((double) ind);
    }

    public Collection<Point2D> intersections(LinearShape2D line) {
        ArrayList<Point2D> list = new ArrayList();
        for (LineSegment2D edge : edges()) {
            if (!edge.isParallel(line)) {
                Point2D point = edge.intersection(line);
                if (!(point == null || list.contains(point))) {
                    list.add(point);
                }
            }
        }
        return list;
    }

    public double project(Point2D point) {
        double minDist = Double.POSITIVE_INFINITY;
        double x = point.x();
        double y = point.y();
        double pos = Double.NaN;
        int i = 0;
        for (LineSegment2D edge : edges()) {
            double dist = edge.distance(x, y);
            if (dist < minDist) {
                minDist = dist;
                pos = edge.project(point) + ((double) i);
            }
            i++;
        }
        return pos;
    }

    public double distance(double x, double y) {
        double dist = Double.MAX_VALUE;
        for (LineSegment2D edge : edges()) {
            if (edge.length() != 0.0d) {
                dist = Math.min(dist, edge.distance(x, y));
            }
        }
        return dist;
    }

    public double distance(Point2D point) {
        return distance(point.x(), point.y());
    }

    public boolean isEmpty() {
        return this.vertices.size() == 0;
    }

    public boolean isBounded() {
        return true;
    }

    public boolean contains(double x, double y) {
        for (LineSegment2D edge : edges()) {
            if (edge.length() != 0.0d && edge.contains(x, y)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Point2D point) {
        return contains(point.x(), point.y());
    }

    public Path asGeneralPath() {
        Path path = new Path();
        return this.vertices.size() < 2 ? path : appendPath(path);
    }
}
