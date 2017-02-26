package org.apache.commons.math4.ode.nonstiff;

public class EulerIntegrator extends RungeKuttaIntegrator {
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        STATIC_C = new double[0];
        STATIC_A = new double[0][];
        STATIC_B = new double[]{1.0d};
    }

    public EulerIntegrator(double step) {
        super("Euler", STATIC_C, STATIC_A, STATIC_B, new EulerStepInterpolator(), step);
    }
}
