package com.example.duy.calculator.geom2d.util;

public class UnboundedShape2DException extends RuntimeException {
    private static final long serialVersionUID = 1;
    private Shape2D shape;

    public UnboundedShape2DException(Shape2D shape) {
        this.shape = shape;
    }

    public Shape2D getShape() {
        return this.shape;
    }
}
