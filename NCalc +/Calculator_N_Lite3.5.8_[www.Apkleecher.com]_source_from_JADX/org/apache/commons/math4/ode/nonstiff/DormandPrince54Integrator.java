package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math4.util.FastMath;

public class DormandPrince54Integrator extends EmbeddedRungeKuttaIntegrator {
    private static final double E1 = 0.0012326388888888888d;
    private static final double E3 = -0.0042527702905061394d;
    private static final double E4 = 0.03697916666666667d;
    private static final double E5 = -0.05086379716981132d;
    private static final double E6 = 0.0419047619047619d;
    private static final double E7 = -0.025d;
    private static final String METHOD_NAME = "Dormand-Prince 5(4)";
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        STATIC_C = new double[]{0.2d, LoessInterpolator.DEFAULT_BANDWIDTH, 0.8d, 0.8888888888888888d, 1.0d, 1.0d};
        r0 = new double[6][];
        r0[0] = new double[]{0.2d};
        r0[1] = new double[]{0.075d, 0.225d};
        r0[2] = new double[]{0.9777777777777777d, -3.7333333333333334d, 3.5555555555555554d};
        r0[3] = new double[]{2.9525986892242035d, -11.595793324188385d, 9.822892851699436d, -0.2908093278463649d};
        r0[4] = new double[]{2.8462752525252526d, -10.757575757575758d, 8.906422717743473d, 0.2784090909090909d, -0.2735313036020583d};
        r0[5] = new double[]{0.09114583333333333d, 0.0d, 0.44923629829290207d, 0.6510416666666666d, -0.322376179245283d, 0.13095238095238096d};
        STATIC_A = r0;
        STATIC_B = new double[]{0.09114583333333333d, 0.0d, 0.44923629829290207d, 0.6510416666666666d, -0.322376179245283d, 0.13095238095238096d, 0.0d};
    }

    public DormandPrince54Integrator(double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
        super(METHOD_NAME, true, STATIC_C, STATIC_A, STATIC_B, new DormandPrince54StepInterpolator(), minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    public DormandPrince54Integrator(double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(METHOD_NAME, true, STATIC_C, STATIC_A, STATIC_B, new DormandPrince54StepInterpolator(), minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
    }

    public int getOrder() {
        return 5;
    }

    protected double estimateError(double[][] yDotK, double[] y0, double[] y1, double h) {
        double error = 0.0d;
        for (int j = 0; j < this.mainSetDimension; j++) {
            double tol;
            double errSum = (((((E1 * yDotK[0][j]) + (E3 * yDotK[2][j])) + (E4 * yDotK[3][j])) + (E5 * yDotK[4][j])) + (E6 * yDotK[5][j])) + (E7 * yDotK[6][j]);
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
