package org.apache.commons.math4.ode.nonstiff;

public class ThreeEighthesIntegrator extends RungeKuttaIntegrator {
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        STATIC_C = new double[]{0.3333333333333333d, 0.6666666666666666d, 1.0d};
        r0 = new double[3][];
        r0[0] = new double[]{0.3333333333333333d};
        r0[1] = new double[]{-0.3333333333333333d, 1.0d};
        r0[2] = new double[]{1.0d, -1.0d, 1.0d};
        STATIC_A = r0;
        STATIC_B = new double[]{0.125d, 0.375d, 0.375d, 0.125d};
    }

    public ThreeEighthesIntegrator(double step) {
        super("3/8", STATIC_C, STATIC_A, STATIC_B, new ThreeEighthesStepInterpolator(), step);
    }
}
