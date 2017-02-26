package com.example.duy.calculator.geom2d.curve;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import com.example.duy.calculator.geom2d.util.Shape2D;
import java.util.Collection;

public interface Curve2D extends Shape2D {
    Point2D firstPoint();

    @Deprecated
    double getT0();

    @Deprecated
    double getT1();

    Collection<Point2D> intersections(LinearShape2D linearShape2D);

    boolean isSingular(double d);

    Point2D lastPoint();

    Point2D point(double d);

    double position(Point2D point2D);

    double project(Point2D point2D);

    Collection<Point2D> singularPoints();

    double t0();

    double t1();

    Collection<Point2D> vertices();
}
