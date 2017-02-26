package org.apache.commons.math4.optim;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public abstract class BaseMultivariateOptimizer<PAIR> extends BaseOptimizer<PAIR> {
    private double[] lowerBound;
    private double[] start;
    private double[] upperBound;

    protected BaseMultivariateOptimizer(ConvergenceChecker<PAIR> checker) {
        super(checker);
    }

    public PAIR optimize(OptimizationData... optData) {
        return super.optimize(optData);
    }

    protected void parseOptimizationData(OptimizationData... optData) {
        super.parseOptimizationData(optData);
        for (OptimizationData data : optData) {
            if (data instanceof InitialGuess) {
                this.start = ((InitialGuess) data).getInitialGuess();
            } else if (data instanceof SimpleBounds) {
                SimpleBounds bounds = (SimpleBounds) data;
                this.lowerBound = bounds.getLower();
                this.upperBound = bounds.getUpper();
            }
        }
        checkParameters();
    }

    public double[] getStartPoint() {
        return this.start == null ? null : (double[]) this.start.clone();
    }

    public double[] getLowerBound() {
        return this.lowerBound == null ? null : (double[]) this.lowerBound.clone();
    }

    public double[] getUpperBound() {
        return this.upperBound == null ? null : (double[]) this.upperBound.clone();
    }

    private void checkParameters() {
        if (this.start != null) {
            int i;
            double v;
            int dim = this.start.length;
            if (this.lowerBound != null) {
                if (this.lowerBound.length != dim) {
                    throw new DimensionMismatchException(this.lowerBound.length, dim);
                }
                for (i = 0; i < dim; i++) {
                    v = this.start[i];
                    double lo = this.lowerBound[i];
                    if (v < lo) {
                        throw new NumberIsTooSmallException(Double.valueOf(v), Double.valueOf(lo), true);
                    }
                }
            }
            if (this.upperBound == null) {
                return;
            }
            if (this.upperBound.length != dim) {
                throw new DimensionMismatchException(this.upperBound.length, dim);
            }
            for (i = 0; i < dim; i++) {
                v = this.start[i];
                double hi = this.upperBound[i];
                if (v > hi) {
                    throw new NumberIsTooLargeException(Double.valueOf(v), Double.valueOf(hi), true);
                }
            }
        }
    }
}
