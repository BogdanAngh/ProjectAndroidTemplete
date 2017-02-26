package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

class OptimumImpl implements Optimum {
    private final int evaluations;
    private final int iterations;
    private final Evaluation value;

    OptimumImpl(Evaluation value, int evaluations, int iterations) {
        this.value = value;
        this.evaluations = evaluations;
        this.iterations = iterations;
    }

    public int getEvaluations() {
        return this.evaluations;
    }

    public int getIterations() {
        return this.iterations;
    }

    public RealMatrix getCovariances(double threshold) {
        return this.value.getCovariances(threshold);
    }

    public RealVector getSigma(double covarianceSingularityThreshold) {
        return this.value.getSigma(covarianceSingularityThreshold);
    }

    public double getRMS() {
        return this.value.getRMS();
    }

    public RealMatrix getJacobian() {
        return this.value.getJacobian();
    }

    public double getCost() {
        return this.value.getCost();
    }

    public double getChiSquare() {
        return this.value.getChiSquare();
    }

    public double getReducedChiSquare(int n) {
        return this.value.getReducedChiSquare(n);
    }

    public RealVector getResiduals() {
        return this.value.getResiduals();
    }

    public RealVector getPoint() {
        return this.value.getPoint();
    }
}
