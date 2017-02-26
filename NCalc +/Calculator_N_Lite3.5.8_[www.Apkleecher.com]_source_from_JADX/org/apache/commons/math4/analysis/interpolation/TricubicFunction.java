package org.apache.commons.math4.analysis.interpolation;

import java.lang.reflect.Array;
import org.apache.commons.math4.analysis.TrivariateFunction;
import org.apache.commons.math4.exception.OutOfRangeException;

/* compiled from: TricubicInterpolatingFunction */
class TricubicFunction implements TrivariateFunction {
    private static final short N = (short) 4;
    private final double[][][] a;

    public TricubicFunction(double[] aV) {
        this.a = (double[][][]) Array.newInstance(Double.TYPE, new int[]{4, 4, 4});
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {
                    this.a[i][j][k] = aV[(((k * 4) + j) * 4) + i];
                }
            }
        }
    }

    public double value(double x, double y, double z) throws OutOfRangeException {
        if (x < 0.0d || x > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(x), Integer.valueOf(0), Integer.valueOf(1));
        } else if (y < 0.0d || y > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(y), Integer.valueOf(0), Integer.valueOf(1));
        } else if (z < 0.0d || z > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(z), Integer.valueOf(0), Integer.valueOf(1));
        } else {
            double x3 = (x * x) * x;
            double[] pX = new double[]{1.0d, x, x * x, x3};
            double y3 = (y * y) * y;
            double[] pY = new double[]{1.0d, y, y * y, y3};
            double z3 = (z * z) * z;
            double[] pZ = new double[]{1.0d, z, z * z, z3};
            double result = 0.0d;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    for (int k = 0; k < 4; k++) {
                        result += ((this.a[i][j][k] * pX[i]) * pY[j]) * pZ[k];
                    }
                }
            }
            return result;
        }
    }
}
