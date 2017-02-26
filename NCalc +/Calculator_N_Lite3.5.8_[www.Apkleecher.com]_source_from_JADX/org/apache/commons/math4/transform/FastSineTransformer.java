package org.apache.commons.math4.transform;

import java.io.Serializable;
import org.apache.commons.math4.analysis.FunctionUtils;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.complex.Complex;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.ArithmeticUtils;
import org.apache.commons.math4.util.FastMath;

public class FastSineTransformer implements RealTransformer, Serializable {
    static final long serialVersionUID = 20120211;
    private final DstNormalization normalization;

    public FastSineTransformer(DstNormalization normalization) {
        this.normalization = normalization;
    }

    public double[] transform(double[] f, TransformType type) {
        if (this.normalization == DstNormalization.ORTHOGONAL_DST_I) {
            return TransformUtils.scaleArray(fst(f), FastMath.sqrt(2.0d / ((double) f.length)));
        } else if (type == TransformType.FORWARD) {
            return fst(f);
        } else {
            return TransformUtils.scaleArray(fst(f), 2.0d / ((double) f.length));
        }
    }

    public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
        double[] data = FunctionUtils.sample(f, min, max, n);
        data[0] = 0.0d;
        return transform(data, type);
    }

    protected double[] fst(double[] f) throws MathIllegalArgumentException {
        double[] transformed = new double[f.length];
        if (!ArithmeticUtils.isPowerOfTwo((long) f.length)) {
            Localizable localizable = LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING;
            Object[] objArr = new Object[1];
            objArr[0] = Integer.valueOf(f.length);
            throw new MathIllegalArgumentException(localizable, objArr);
        } else if (f[0] != 0.0d) {
            throw new MathIllegalArgumentException(LocalizedFormats.FIRST_ELEMENT_NOT_ZERO, Double.valueOf(f[0]));
        } else {
            int n = f.length;
            if (n == 1) {
                transformed[0] = 0.0d;
            } else {
                int i;
                double[] x = new double[n];
                x[0] = 0.0d;
                x[n >> 1] = 2.0d * f[n >> 1];
                for (i = 1; i < (n >> 1); i++) {
                    double a = FastMath.sin((((double) i) * FastMath.PI) / ((double) n)) * (f[i] + f[n - i]);
                    double b = 0.5d * (f[i] - f[n - i]);
                    x[i] = a + b;
                    x[n - i] = a - b;
                }
                Complex[] y = new FastFourierTransformer(DftNormalization.STANDARD).transform(x, TransformType.FORWARD);
                transformed[0] = 0.0d;
                transformed[1] = 0.5d * y[0].getReal();
                for (i = 1; i < (n >> 1); i++) {
                    transformed[i * 2] = -y[i].getImaginary();
                    transformed[(i * 2) + 1] = y[i].getReal() + transformed[(i * 2) - 1];
                }
            }
            return transformed;
        }
    }
}
