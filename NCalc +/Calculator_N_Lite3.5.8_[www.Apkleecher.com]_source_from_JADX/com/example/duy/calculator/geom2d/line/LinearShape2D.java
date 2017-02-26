package com.example.duy.calculator.geom2d.line;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;

public interface LinearShape2D {
    boolean containsProjection(Point2D point2D);

    Vector2D direction();

    double horizontalAngle();

    Point2D origin();

    StraightLine2D supportingLine();
}
