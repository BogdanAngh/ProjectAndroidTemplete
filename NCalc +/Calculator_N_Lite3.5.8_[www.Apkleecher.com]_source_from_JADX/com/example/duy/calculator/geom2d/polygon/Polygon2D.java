package com.example.duy.calculator.geom2d.polygon;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LineSegment2D;
import java.util.Collection;

public interface Polygon2D {
    void addVertex(Point2D point2D);

    double area();

    Point2D centroid();

    int closestVertexIndex(Point2D point2D);

    Polygon2D complement();

    Collection<? extends LinearRing2D> contours();

    int edgeNumber();

    Collection<? extends LineSegment2D> edges();

    void insertVertex(int i, Point2D point2D);

    void removeVertex(int i);

    void setVertex(int i, Point2D point2D);

    Point2D vertex(int i);

    int vertexNumber();

    Collection<Point2D> vertices();
}
