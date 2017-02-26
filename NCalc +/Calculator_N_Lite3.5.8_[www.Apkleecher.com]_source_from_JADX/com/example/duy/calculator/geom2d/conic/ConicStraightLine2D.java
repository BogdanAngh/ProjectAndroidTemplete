package com.example.duy.calculator.geom2d.conic;

import com.example.duy.calculator.geom2d.conic.Conic2D.Type;
import com.example.duy.calculator.geom2d.line.StraightLine2D;

public class ConicStraightLine2D extends StraightLine2D implements Conic2D {
    double[] coefs;

    public ConicStraightLine2D(StraightLine2D line) {
        super(line);
        this.coefs = new double[]{0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d};
        this.coefs = new double[]{0.0d, 0.0d, 0.0d, this.dy, -this.dx, (this.dx * this.y0) - (this.dy * this.x0)};
    }

    public ConicStraightLine2D(double a, double b, double c) {
        super(StraightLine2D.createCartesian(a, b, c));
        this.coefs = new double[]{0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d};
        this.coefs = new double[]{0.0d, 0.0d, 0.0d, a, b, c};
    }

    public double[] conicCoefficients() {
        return this.coefs;
    }

    public Type conicType() {
        return Type.STRAIGHT_LINE;
    }

    public double eccentricity() {
        return Double.NaN;
    }
}
