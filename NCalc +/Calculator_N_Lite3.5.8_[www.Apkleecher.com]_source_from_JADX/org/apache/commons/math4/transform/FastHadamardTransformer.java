package org.apache.commons.math4.transform;

import java.io.Serializable;
import org.apache.commons.math4.analysis.FunctionUtils;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.ArithmeticUtils;

public class FastHadamardTransformer implements RealTransformer, Serializable {
    static final long serialVersionUID = 20120211;

    public double[] transform(double[] f, TransformType type) {
        if (type == TransformType.FORWARD) {
            return fht(f);
        }
        return TransformUtils.scaleArray(fht(f), 1.0d / ((double) f.length));
    }

    public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
        return transform(FunctionUtils.sample(f, min, max, n), type);
    }

    public int[] transform(int[] f) {
        return fht(f);
    }

    protected double[] fht(double[] x) throws MathIllegalArgumentException {
        int n = x.length;
        int halfN = n / 2;
        if (ArithmeticUtils.isPowerOfTwo((long) n)) {
            double[] yPrevious = new double[n];
            double[] yCurrent = (double[]) x.clone();
            for (int j = 1; j < n; j <<= 1) {
                int i;
                int twoI;
                double[] yTmp = yCurrent;
                yCurrent = yPrevious;
                yPrevious = yTmp;
                for (i = 0; i < halfN; i++) {
                    twoI = i * 2;
                    yCurrent[i] = yPrevious[twoI] + yPrevious[twoI + 1];
                }
                for (i = halfN; i < n; i++) {
                    twoI = i * 2;
                    yCurrent[i] = yPrevious[twoI - n] - yPrevious[(twoI - n) + 1];
                }
            }
            return yCurrent;
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO, Integer.valueOf(n));
    }

    protected int[] fht(int[] x) throws MathIllegalArgumentException {
        int n = x.length;
        int halfN = n / 2;
        if (ArithmeticUtils.isPowerOfTwo((long) n)) {
            int[] yPrevious = new int[n];
            int[] yCurrent = (int[]) x.clone();
            for (int j = 1; j < n; j <<= 1) {
                int i;
                int twoI;
                int[] yTmp = yCurrent;
                yCurrent = yPrevious;
                yPrevious = yTmp;
                for (i = 0; i < halfN; i++) {
                    twoI = i * 2;
                    yCurrent[i] = yPrevious[twoI] + yPrevious[twoI + 1];
                }
                for (i = halfN; i < n; i++) {
                    twoI = i * 2;
                    yCurrent[i] = yPrevious[twoI - n] - yPrevious[(twoI - n) + 1];
                }
            }
            return yCurrent;
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO, Integer.valueOf(n));
    }
}
