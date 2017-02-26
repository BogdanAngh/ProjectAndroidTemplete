package org.apache.commons.math4.ode.nonstiff;

public class MidpointIntegrator extends RungeKuttaIntegrator {
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        STATIC_C = new double[]{0.5d};
        double[][] dArr = new double[1][];
        dArr[0] = new double[]{0.5d};
        STATIC_A = dArr;
        STATIC_B = new double[]{0.0d, 1.0d};
    }

    public MidpointIntegrator(double step) {
        super("midpoint", STATIC_C, STATIC_A, STATIC_B, new MidpointStepInterpolator(), step);
    }
}
