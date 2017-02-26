package org.apache.commons.math4.ode;

public class FirstOrderConverter implements FirstOrderDifferentialEquations {
    private final int dimension;
    private final SecondOrderDifferentialEquations equations;
    private final double[] z;
    private final double[] zDDot;
    private final double[] zDot;

    public FirstOrderConverter(SecondOrderDifferentialEquations equations) {
        this.equations = equations;
        this.dimension = equations.getDimension();
        this.z = new double[this.dimension];
        this.zDot = new double[this.dimension];
        this.zDDot = new double[this.dimension];
    }

    public int getDimension() {
        return this.dimension * 2;
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) {
        System.arraycopy(y, 0, this.z, 0, this.dimension);
        System.arraycopy(y, this.dimension, this.zDot, 0, this.dimension);
        this.equations.computeSecondDerivatives(t, this.z, this.zDot, this.zDDot);
        System.arraycopy(this.zDot, 0, yDot, 0, this.dimension);
        System.arraycopy(this.zDDot, 0, yDot, this.dimension, this.dimension);
    }
}
