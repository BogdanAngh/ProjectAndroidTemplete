package org.apache.commons.math4.optim.nonlinear.scalar;

import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class MultivariateFunctionPenaltyAdapter implements MultivariateFunction {
    private final MultivariateFunction bounded;
    private final double[] lower;
    private final double offset;
    private final double[] scale;
    private final double[] upper;

    public MultivariateFunctionPenaltyAdapter(MultivariateFunction bounded, double[] lower, double[] upper, double offset, double[] scale) {
        MathUtils.checkNotNull(lower);
        MathUtils.checkNotNull(upper);
        MathUtils.checkNotNull(scale);
        if (lower.length != upper.length) {
            throw new DimensionMismatchException(lower.length, upper.length);
        } else if (lower.length != scale.length) {
            throw new DimensionMismatchException(lower.length, scale.length);
        } else {
            for (int i = 0; i < lower.length; i++) {
                if (upper[i] < lower[i]) {
                    throw new NumberIsTooSmallException(Double.valueOf(upper[i]), Double.valueOf(lower[i]), true);
                }
            }
            this.bounded = bounded;
            this.lower = (double[]) lower.clone();
            this.upper = (double[]) upper.clone();
            this.offset = offset;
            this.scale = (double[]) scale.clone();
        }
    }

    public double value(double[] point) {
        int i = 0;
        while (i < this.scale.length) {
            if (point[i] < this.lower[i] || point[i] > this.upper[i]) {
                double sum = 0.0d;
                for (int j = i; j < this.scale.length; j++) {
                    double overshoot;
                    if (point[j] < this.lower[j]) {
                        overshoot = this.scale[j] * (this.lower[j] - point[j]);
                    } else if (point[j] > this.upper[j]) {
                        overshoot = this.scale[j] * (point[j] - this.upper[j]);
                    } else {
                        overshoot = 0.0d;
                    }
                    sum += FastMath.sqrt(overshoot);
                }
                return this.offset + sum;
            }
            i++;
        }
        return this.bounded.value(point);
    }
}
