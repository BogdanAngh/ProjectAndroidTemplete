package com.example.duy.calculator.geom2d.curve;

import android.graphics.Path;
import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.Vector2D;
import com.example.duy.calculator.geom2d.polygon.LinearCurve2D;

public interface ContinuousCurve2D extends Curve2D {
    Path appendPath(Path path);

    LinearCurve2D asPolyline(int i);

    boolean contains(Point2D point2D);

    double curvature(double d);

    boolean isClosed();

    Vector2D leftTangent(double d);

    Vector2D rightTangent(double d);
}
