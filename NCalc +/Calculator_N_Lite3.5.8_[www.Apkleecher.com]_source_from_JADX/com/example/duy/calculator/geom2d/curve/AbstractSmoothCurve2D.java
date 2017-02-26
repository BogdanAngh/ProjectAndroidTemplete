package com.example.duy.calculator.geom2d.curve;

import com.example.duy.calculator.geom2d.Point2D;
import com.example.duy.calculator.geom2d.line.LinearShape2D;
import java.util.ArrayList;
import java.util.Collection;

public abstract class AbstractSmoothCurve2D implements LinearShape2D, ContinuousCurve2D, Cloneable {
    public Collection<Point2D> singularPoints() {
        return new ArrayList(0);
    }

    public Collection<Point2D> vertices() {
        ArrayList<Point2D> array = new ArrayList(2);
        if (!Double.isInfinite(t0())) {
            array.add(firstPoint());
        }
        if (!Double.isInfinite(t1())) {
            array.add(lastPoint());
        }
        return array;
    }

    public boolean isSingular(double pos) {
        return false;
    }
}
