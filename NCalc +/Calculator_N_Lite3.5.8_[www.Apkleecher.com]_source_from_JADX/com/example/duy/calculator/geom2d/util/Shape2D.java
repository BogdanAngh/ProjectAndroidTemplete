package com.example.duy.calculator.geom2d.util;

import com.example.duy.calculator.geom2d.GeometricObject2D;
import com.example.duy.calculator.geom2d.Point2D;

public interface Shape2D extends GeometricObject2D {
    public static final double ACCURACY = 1.0E-12d;

    boolean contains(double d, double d2);

    boolean contains(Point2D point2D);

    double distance(double d, double d2);

    double distance(Point2D point2D);

    boolean isBounded();

    boolean isEmpty();
}
