package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.util.FastMath;

public class LutherIntegrator extends RungeKuttaIntegrator {
    private static final double Q;
    private static final double[][] STATIC_A;
    private static final double[] STATIC_B;
    private static final double[] STATIC_C;

    static {
        Q = FastMath.sqrt(21.0d);
        STATIC_C = new double[]{1.0d, 0.5d, 0.6666666666666666d, (7.0d - Q) / 14.0d, (7.0d + Q) / 14.0d, 1.0d};
        double[][] dArr = new double[6][];
        dArr[0] = new double[]{1.0d};
        dArr[1] = new double[]{0.375d, 0.125d};
        dArr[2] = new double[]{0.2962962962962963d, 0.07407407407407407d, 0.2962962962962963d};
        dArr[3] = new double[]{(-21.0d + (9.0d * Q)) / 392.0d, (-56.0d + (8.0d * Q)) / 392.0d, (336.0d - (48.0d * Q)) / 392.0d, (-63.0d + (3.0d * Q)) / 392.0d};
        dArr[4] = new double[]{(-1155.0d - (255.0d * Q)) / 1960.0d, (-280.0d - (40.0d * Q)) / 1960.0d, (0.0d - (320.0d * Q)) / 1960.0d, (63.0d + (363.0d * Q)) / 1960.0d, (2352.0d + (392.0d * Q)) / 1960.0d};
        dArr[5] = new double[]{(330.0d + (105.0d * Q)) / 180.0d, (120.0d + (0.0d * Q)) / 180.0d, (-200.0d + (280.0d * Q)) / 180.0d, (126.0d - (189.0d * Q)) / 180.0d, (-686.0d - (126.0d * Q)) / 180.0d, (490.0d - (70.0d * Q)) / 180.0d};
        STATIC_A = dArr;
        STATIC_B = new double[]{0.05d, 0.0d, 0.35555555555555557d, 0.0d, 0.2722222222222222d, 0.2722222222222222d, 0.05d};
    }

    public LutherIntegrator(double step) {
        super("Luther", STATIC_C, STATIC_A, STATIC_B, new LutherStepInterpolator(), step);
    }
}
