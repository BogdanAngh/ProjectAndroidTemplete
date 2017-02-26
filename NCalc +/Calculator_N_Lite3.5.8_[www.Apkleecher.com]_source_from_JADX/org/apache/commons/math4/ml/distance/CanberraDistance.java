package org.apache.commons.math4.ml.distance;

import org.apache.commons.math4.util.FastMath;

public class CanberraDistance implements DistanceMeasure {
    private static final long serialVersionUID = -6972277381587032228L;

    public double compute(double[] a, double[] b) {
        double sum = 0.0d;
        for (int i = 0; i < a.length; i++) {
            double num = FastMath.abs(a[i] - b[i]);
            double denom = FastMath.abs(a[i]) + FastMath.abs(b[i]);
            double d = (num == 0.0d && denom == 0.0d) ? 0.0d : num / denom;
            sum += d;
        }
        return sum;
    }
}
