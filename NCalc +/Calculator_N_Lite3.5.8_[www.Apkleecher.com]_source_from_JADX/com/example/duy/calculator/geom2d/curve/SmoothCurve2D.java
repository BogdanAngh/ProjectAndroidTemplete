package com.example.duy.calculator.geom2d.curve;

import com.example.duy.calculator.geom2d.Vector2D;

public interface SmoothCurve2D extends ContinuousCurve2D {
    Vector2D normal(double d);

    Vector2D tangent(double d);
}
