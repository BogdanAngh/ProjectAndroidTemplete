package org.apache.commons.math4.stat.regression;

import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.LUDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

public class GLSMultipleLinearRegression extends AbstractMultipleLinearRegression {
    private RealMatrix Omega;
    private RealMatrix OmegaInverse;

    public void newSampleData(double[] y, double[][] x, double[][] covariance) {
        validateSampleData(x, y);
        newYSampleData(y);
        newXSampleData(x);
        validateCovarianceData(x, covariance);
        newCovarianceData(covariance);
    }

    protected void newCovarianceData(double[][] omega) {
        this.Omega = new Array2DRowRealMatrix(omega);
        this.OmegaInverse = null;
    }

    protected RealMatrix getOmegaInverse() {
        if (this.OmegaInverse == null) {
            this.OmegaInverse = new LUDecomposition(this.Omega).getSolver().getInverse();
        }
        return this.OmegaInverse;
    }

    protected RealVector calculateBeta() {
        RealMatrix OI = getOmegaInverse();
        RealMatrix XT = getX().transpose();
        return new LUDecomposition(XT.multiply(OI).multiply(getX())).getSolver().getInverse().multiply(XT).multiply(OI).operate(getY());
    }

    protected RealMatrix calculateBetaVariance() {
        return new LUDecomposition(getX().transpose().multiply(getOmegaInverse()).multiply(getX())).getSolver().getInverse();
    }

    protected double calculateErrorVariance() {
        RealVector residuals = calculateResiduals();
        return residuals.dotProduct(getOmegaInverse().operate(residuals)) / ((double) (getX().getRowDimension() - getX().getColumnDimension()));
    }
}
