package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.util.FastMath;

public class HighamHall54Integrator extends EmbeddedRungeKuttaIntegrator {
    private static final String METHOD_NAME = "Higham-Hall 5(4)";
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;
    private static final double[] STATIC_E;

    static {
        STATIC_C = new double[]{0.2222222222222222d, 0.3333333333333333d, 0.5d, 0.6d, 1.0d, 1.0d};
        r0 = new double[6][];
        r0[0] = new double[]{0.2222222222222222d};
        r0[1] = new double[]{0.08333333333333333d, 0.25d};
        r0[2] = new double[]{0.125d, 0.0d, 0.375d};
        r0[3] = new double[]{0.182d, -0.27d, 0.624d, 0.064d};
        r0[4] = new double[]{-0.55d, 1.35d, 2.4d, -7.2d, 5.0d};
        r0[5] = new double[]{0.08333333333333333d, 0.0d, 0.84375d, -1.3333333333333333d, 1.3020833333333333d, 0.10416666666666667d};
        STATIC_A = r0;
        STATIC_B = new double[]{0.08333333333333333d, 0.0d, 0.84375d, -1.3333333333333333d, 1.3020833333333333d, 0.10416666666666667d, 0.0d};
        STATIC_E = new double[]{-0.05d, 0.0d, 0.50625d, -1.2d, 0.78125d, 0.0625d, -0.1d};
    }

    public HighamHall54Integrator(double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
        super(METHOD_NAME, false, STATIC_C, STATIC_A, STATIC_B, new HighamHall54StepInterpolator(), minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    public HighamHall54Integrator(double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(METHOD_NAME, false, STATIC_C, STATIC_A, STATIC_B, new HighamHall54StepInterpolator(), minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
    }

    public int getOrder() {
        return 5;
    }

    protected double estimateError(double[][] yDotK, double[] y0, double[] y1, double h) {
        double error = 0.0d;
        for (int j = 0; j < this.mainSetDimension; j++) {
            double tol;
            double errSum = STATIC_E[0] * yDotK[0][j];
            for (int l = 1; l < STATIC_E.length; l++) {
                errSum += STATIC_E[l] * yDotK[l][j];
            }
            double yScale = FastMath.max(FastMath.abs(y0[j]), FastMath.abs(y1[j]));
            if (this.vecAbsoluteTolerance == null) {
                tol = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * yScale);
            } else {
                tol = this.vecAbsoluteTolerance[j] + (this.vecRelativeTolerance[j] * yScale);
            }
            double ratio = (h * errSum) / tol;
            error += ratio * ratio;
        }
        return FastMath.sqrt(error / ((double) this.mainSetDimension));
    }
}
