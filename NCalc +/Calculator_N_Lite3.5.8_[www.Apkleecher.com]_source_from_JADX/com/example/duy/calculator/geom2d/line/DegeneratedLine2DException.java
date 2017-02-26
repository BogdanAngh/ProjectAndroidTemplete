package com.example.duy.calculator.geom2d.line;

public class DegeneratedLine2DException extends RuntimeException {
    private static final long serialVersionUID = 1;
    protected LinearShape2D line;

    public DegeneratedLine2DException(String msg, LinearShape2D line) {
        super(msg);
        this.line = line;
    }

    public DegeneratedLine2DException(LinearShape2D line) {
        this.line = line;
    }

    public LinearShape2D getLine() {
        return this.line;
    }
}
