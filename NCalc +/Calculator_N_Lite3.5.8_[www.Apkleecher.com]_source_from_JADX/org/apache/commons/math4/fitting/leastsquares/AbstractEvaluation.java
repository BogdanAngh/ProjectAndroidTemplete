package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.QRDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractEvaluation implements Evaluation {
    private final int observationSize;

    AbstractEvaluation(int observationSize) {
        this.observationSize = observationSize;
    }

    public RealMatrix getCovariances(double threshold) {
        RealMatrix j = getJacobian();
        return new QRDecomposition(j.transpose().multiply(j), threshold).getSolver().getInverse();
    }

    public RealVector getSigma(double covarianceSingularityThreshold) {
        RealMatrix cov = getCovariances(covarianceSingularityThreshold);
        int nC = cov.getColumnDimension();
        RealVector sig = new ArrayRealVector(nC);
        for (int i = 0; i < nC; i++) {
            sig.setEntry(i, FastMath.sqrt(cov.getEntry(i, i)));
        }
        return sig;
    }

    public double getRMS() {
        return FastMath.sqrt(getReducedChiSquare(1));
    }

    public double getCost() {
        return FastMath.sqrt(getChiSquare());
    }

    public double getChiSquare() {
        ArrayRealVector r = new ArrayRealVector(getResiduals());
        return r.dotProduct(r);
    }

    public double getReducedChiSquare(int numberOfFittedParameters) {
        return getChiSquare() / ((double) ((this.observationSize - numberOfFittedParameters) + 1));
    }
}
