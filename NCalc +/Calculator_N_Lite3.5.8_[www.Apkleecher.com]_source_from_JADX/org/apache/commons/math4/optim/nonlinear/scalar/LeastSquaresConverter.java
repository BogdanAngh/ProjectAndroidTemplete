package org.apache.commons.math4.optim.nonlinear.scalar;

import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.analysis.MultivariateVectorFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.linear.RealMatrix;

public class LeastSquaresConverter implements MultivariateFunction {
    private final MultivariateVectorFunction function;
    private final double[] observations;
    private final RealMatrix scale;
    private final double[] weights;

    public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations) {
        this.function = function;
        this.observations = (double[]) observations.clone();
        this.weights = null;
        this.scale = null;
    }

    public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, double[] weights) {
        if (observations.length != weights.length) {
            throw new DimensionMismatchException(observations.length, weights.length);
        }
        this.function = function;
        this.observations = (double[]) observations.clone();
        this.weights = (double[]) weights.clone();
        this.scale = null;
    }

    public LeastSquaresConverter(MultivariateVectorFunction function, double[] observations, RealMatrix scale) {
        if (observations.length != scale.getColumnDimension()) {
            throw new DimensionMismatchException(observations.length, scale.getColumnDimension());
        }
        this.function = function;
        this.observations = (double[]) observations.clone();
        this.weights = null;
        this.scale = scale.copy();
    }

    public double value(double[] point) {
        int i = 0;
        double[] residuals = this.function.value(point);
        if (residuals.length != this.observations.length) {
            throw new DimensionMismatchException(residuals.length, this.observations.length);
        }
        int i2;
        for (i2 = 0; i2 < residuals.length; i2++) {
            residuals[i2] = residuals[i2] - this.observations[i2];
        }
        double sumSquares = 0.0d;
        double ri;
        if (this.weights != null) {
            for (i2 = 0; i2 < residuals.length; i2++) {
                ri = residuals[i2];
                sumSquares += (this.weights[i2] * ri) * ri;
            }
        } else if (this.scale != null) {
            double[] operate = this.scale.operate(residuals);
            int length = operate.length;
            while (i < length) {
                double yi = operate[i];
                sumSquares += yi * yi;
                i++;
            }
        } else {
            int length2 = residuals.length;
            while (i < length2) {
                ri = residuals[i];
                sumSquares += ri * ri;
                i++;
            }
        }
        return sumSquares;
    }
}
