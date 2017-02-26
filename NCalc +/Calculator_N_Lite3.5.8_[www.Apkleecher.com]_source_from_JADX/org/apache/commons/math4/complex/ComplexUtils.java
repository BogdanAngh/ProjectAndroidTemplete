package org.apache.commons.math4.complex;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public class ComplexUtils {
    private ComplexUtils() {
    }

    public static Complex polar2Complex(double r, double theta) throws MathIllegalArgumentException {
        if (r >= 0.0d) {
            return new Complex(FastMath.cos(theta) * r, FastMath.sin(theta) * r);
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NEGATIVE_COMPLEX_MODULE, Double.valueOf(r));
    }

    public static Complex[] convertToComplex(double[] real) {
        Complex[] c = new Complex[real.length];
        for (int i = 0; i < real.length; i++) {
            c[i] = new Complex(real[i], 0.0d);
        }
        return c;
    }
}
