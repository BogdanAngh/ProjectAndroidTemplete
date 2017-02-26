package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

class DenseWeightedEvaluation extends AbstractEvaluation {
    private final Evaluation unweighted;
    private final RealMatrix weightSqrt;

    DenseWeightedEvaluation(Evaluation unweighted, RealMatrix weightSqrt) {
        super(weightSqrt.getColumnDimension());
        this.unweighted = unweighted;
        this.weightSqrt = weightSqrt;
    }

    public RealMatrix getJacobian() {
        return this.weightSqrt.multiply(this.unweighted.getJacobian());
    }

    public RealVector getResiduals() {
        return this.weightSqrt.operate(this.unweighted.getResiduals());
    }

    public RealVector getPoint() {
        return this.unweighted.getPoint();
    }
}
