package com.example.duy.calculator.geom2d.conic;

public interface Conic2D {

    public enum Type {
        NOT_A_CONIC,
        ELLIPSE,
        HYPERBOLA,
        PARABOLA,
        CIRCLE,
        STRAIGHT_LINE,
        TWO_LINES,
        POINT
    }

    double[] conicCoefficients();

    Type conicType();

    double eccentricity();
}
