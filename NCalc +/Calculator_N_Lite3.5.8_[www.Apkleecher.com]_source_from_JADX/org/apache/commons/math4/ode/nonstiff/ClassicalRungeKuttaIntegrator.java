package org.apache.commons.math4.ode.nonstiff;

public class ClassicalRungeKuttaIntegrator extends RungeKuttaIntegrator {
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        STATIC_C = new double[]{0.5d, 0.5d, 1.0d};
        r0 = new double[3][];
        r0[0] = new double[]{0.5d};
        r0[1] = new double[]{0.0d, 0.5d};
        r0[2] = new double[]{0.0d, 0.0d, 1.0d};
        STATIC_A = r0;
        STATIC_B = new double[]{0.16666666666666666d, 0.3333333333333333d, 0.3333333333333333d, 0.16666666666666666d};
    }

    public ClassicalRungeKuttaIntegrator(double step) {
        super("classical Runge-Kutta", STATIC_C, STATIC_A, STATIC_B, new ClassicalRungeKuttaStepInterpolator(), step);
    }
}
