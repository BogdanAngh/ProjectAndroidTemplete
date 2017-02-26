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

public class FastCosineTransformer implements RealTransformer, Serializable {
    static final long serialVersionUID = 20120212;
    private final DctNormalization normalization;

    public FastCosineTransformer(DctNormalization normalization) {
        this.normalization = normalization;
    }

    public double[] transform(double[] f, TransformType type) throws MathIllegalArgumentException {
        if (type != TransformType.FORWARD) {
            double s1;
            double s2 = 2.0d / ((double) (f.length - 1));
            if (this.normalization == DctNormalization.ORTHOGONAL_DCT_I) {
                s1 = FastMath.sqrt(s2);
            } else {
                s1 = s2;
            }
            return TransformUtils.scaleArray(fct(f), s1);
        } else if (this.normalization != DctNormalization.ORTHOGONAL_DCT_I) {
            return fct(f);
        } else {
            return TransformUtils.scaleArray(fct(f), FastMath.sqrt(2.0d / ((double) (f.length - 1))));
        }
    }

    public double[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) throws MathIllegalArgumentException {
        return transform(FunctionUtils.sample(f, min, max, n), type);
    }

    protected double[] fct(double[] f) throws MathIllegalArgumentException {
        double[] transformed = new double[f.length];
        int n = f.length - 1;
        if (ArithmeticUtils.isPowerOfTwo((long) n)) {
            if (n == 1) {
                transformed[0] = 0.5d * (f[0] + f[1]);
                transformed[1] = 0.5d * (f[0] - f[1]);
            } else {
                int i;
                double[] x = new double[n];
                x[0] = 0.5d * (f[0] + f[n]);
                x[n >> 1] = f[n >> 1];
                double t1 = 0.5d * (f[0] - f[n]);
                for (i = 1; i < (n >> 1); i++) {
                    double a = 0.5d * (f[i] + f[n - i]);
                    double d = (double) n;
                    double b = FastMath.sin((((double) i) * FastMath.PI) / r0) * (f[i] - f[n - i]);
                    d = (double) n;
                    double c = FastMath.cos((((double) i) * FastMath.PI) / r0) * (f[i] - f[n - i]);
                    x[i] = a - b;
                    x[n - i] = a + b;
                    t1 += c;
                }
                DftNormalization dftNormalization = DftNormalization.STANDARD;
                Complex[] y = new FastFourierTransformer(r16).transform(x, TransformType.FORWARD);
                transformed[0] = y[0].getReal();
                transformed[1] = t1;
                for (i = 1; i < (n >> 1); i++) {
                    transformed[i * 2] = y[i].getReal();
                    transformed[(i * 2) + 1] = transformed[(i * 2) - 1] - y[i].getImaginary();
                }
                transformed[n] = y[n >> 1].getReal();
            }
            return transformed;
        }
        Localizable localizable = LocalizedFormats.NOT_POWER_OF_TWO_PLUS_ONE;
        Object[] objArr = new Object[1];
        objArr[0] = Integer.valueOf(f.length);
        throw new MathIllegalArgumentException(localizable, objArr);
    }
}
