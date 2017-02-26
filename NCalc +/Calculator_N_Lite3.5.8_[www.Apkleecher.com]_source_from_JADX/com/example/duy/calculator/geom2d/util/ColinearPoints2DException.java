package com.example.duy.calculator.geom2d.util;

import com.example.duy.calculator.geom2d.Point2D;

public class ColinearPoints2DException extends RuntimeException {
    private static final long serialVersionUID = 1;
    protected Point2D p1;
    protected Point2D p2;
    protected Point2D p3;

    public ColinearPoints2DException(Point2D p1, Point2D p2, Point2D p3) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
    }

    public Point2D getP1() {
        return this.p1;
    }

    public Point2D getP2() {
        return this.p2;
    }

    public Point2D getP3() {
        return this.p3;
    }
}
