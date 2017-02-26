package org.apache.commons.math4.analysis.interpolation;

import java.lang.reflect.Array;
import org.apache.commons.math4.analysis.BivariateFunction;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.MathArrays;

/* compiled from: BicubicInterpolatingFunction */
class BicubicFunction implements BivariateFunction {
    private static final short N = (short) 4;
    private final double[][] a;

    public BicubicFunction(double[] coeff) {
        this.a = (double[][]) Array.newInstance(Double.TYPE, new int[]{4, 4});
        for (int j = 0; j < 4; j++) {
            double[] aJ = this.a[j];
            for (int i = 0; i < 4; i++) {
                aJ[i] = coeff[(i * 4) + j];
            }
        }
    }

    public double value(double x, double y) {
        if (x < 0.0d || x > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(x), Integer.valueOf(0), Integer.valueOf(1));
        } else if (y < 0.0d || y > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(y), Integer.valueOf(0), Integer.valueOf(1));
        } else {
            double x3 = (x * x) * x;
            double y3 = (y * y) * y;
            return apply(new double[]{1.0d, x, x * x, x3}, new double[]{1.0d, y, y * y, y3}, this.a);
        }
    }

    private double apply(double[] pX, double[] pY, double[][] coeff) {
        double result = 0.0d;
        for (int i = 0; i < 4; i++) {
            result += pX[i] * MathArrays.linearCombination(coeff[i], pY);
        }
        return result;
    }
}
