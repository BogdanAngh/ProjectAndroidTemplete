package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.optim.OptimizationProblem;

public interface LeastSquaresProblem extends OptimizationProblem<Evaluation> {

    public interface Evaluation {
        double getChiSquare();

        double getCost();

        RealMatrix getCovariances(double d);

        RealMatrix getJacobian();

        RealVector getPoint();

        double getRMS();

        double getReducedChiSquare(int i);

        RealVector getResiduals();

        RealVector getSigma(double d);
    }

    Evaluation evaluate(RealVector realVector);

    int getObservationSize();

    int getParameterSize();

    RealVector getStart();
}
