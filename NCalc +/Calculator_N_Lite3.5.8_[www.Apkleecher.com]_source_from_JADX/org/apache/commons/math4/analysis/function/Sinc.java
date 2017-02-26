package org.apache.commons.math4.analysis.function;

import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.FastMath;

public class Sinc implements UnivariateDifferentiableFunction {
    private static final double SHORTCUT = 0.006d;
    private final boolean normalized;

    public Sinc() {
        this(false);
    }

    public Sinc(boolean normalized) {
        this.normalized = normalized;
    }

    public double value(double x) {
        double scaledX;
        if (this.normalized) {
            scaledX = FastMath.PI * x;
        } else {
            scaledX = x;
        }
        if (FastMath.abs(scaledX) > SHORTCUT) {
            return FastMath.sin(scaledX) / scaledX;
        }
        double scaledX2 = scaledX * scaledX;
        return (((scaledX2 - 20.0d) * scaledX2) + 120.0d) / 120.0d;
    }

    public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
        int length;
        int i;
        double scaledX = (this.normalized ? FastMath.PI : 1.0d) * t.getValue();
        double scaledX2 = scaledX * scaledX;
        double[] f = new double[(t.getOrder() + 1)];
        int k;
        if (FastMath.abs(scaledX) > SHORTCUT) {
            double inv = 1.0d / scaledX;
            double cos = FastMath.cos(scaledX);
            double sin = FastMath.sin(scaledX);
            f[0] = inv * sin;
            double[] sc = new double[f.length];
            sc[0] = 1.0d;
            double coeff = inv;
            int n = 1;
            while (true) {
                length = f.length;
                if (n >= r0) {
                    break;
                }
                int kStart;
                double s = 0.0d;
                double c = 0.0d;
                if ((n & 1) == 0) {
                    sc[n] = 0.0d;
                    kStart = n;
                } else {
                    sc[n] = sc[n - 1];
                    c = sc[n];
                    kStart = n - 1;
                }
                for (k = kStart; k > 1; k -= 2) {
                    sc[k] = (((double) (k - n)) * sc[k]) - sc[k - 1];
                    s = (s * scaledX2) + sc[k];
                    sc[k - 1] = (((double) ((k - 1) - n)) * sc[k - 1]) + sc[k - 2];
                    c = (c * scaledX2) + sc[k - 1];
                }
                sc[0] = sc[0] * ((double) (-n));
                coeff *= inv;
                f[n] = ((((s * scaledX2) + sc[0]) * sin) + ((c * scaledX) * cos)) * coeff;
                n++;
            }
        } else {
            i = 0;
            while (true) {
                length = f.length;
                if (i >= r0) {
                    break;
                }
                k = i / 2;
                if ((i & 1) == 0) {
                    f[i] = ((double) ((k & 1) == 0 ? 1 : -1)) * ((1.0d / ((double) (i + 1))) - (((1.0d / ((double) ((i * 2) + 6))) - (scaledX2 / ((double) ((i * 24) + 120)))) * scaledX2));
                } else {
                    f[i] = ((k & 1) == 0 ? -scaledX : scaledX) * ((1.0d / ((double) (i + 2))) - (((1.0d / ((double) ((i * 6) + 24))) - (scaledX2 / ((double) ((i * 120) + 720)))) * scaledX2));
                }
                i++;
            }
        }
        if (this.normalized) {
            double scale = FastMath.PI;
            i = 1;
            while (true) {
                length = f.length;
                if (i >= r0) {
                    break;
                }
                f[i] = f[i] * scale;
                scale *= FastMath.PI;
                i++;
            }
        }
        return t.compose(f);
    }
}
