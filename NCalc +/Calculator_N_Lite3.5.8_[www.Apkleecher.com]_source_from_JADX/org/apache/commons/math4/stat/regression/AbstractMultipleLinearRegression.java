package org.apache.commons.math4.stat.regression;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.NonSquareMatrixException;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.stat.descriptive.moment.Variance;
import org.apache.commons.math4.util.FastMath;

public abstract class AbstractMultipleLinearRegression implements MultipleLinearRegression {
    private boolean noIntercept;
    private RealMatrix xMatrix;
    private RealVector yVector;

    protected abstract RealVector calculateBeta();

    protected abstract RealMatrix calculateBetaVariance();

    public AbstractMultipleLinearRegression() {
        this.noIntercept = false;
    }

    protected RealMatrix getX() {
        return this.xMatrix;
    }

    protected RealVector getY() {
        return this.yVector;
    }

    public boolean isNoIntercept() {
        return this.noIntercept;
    }

    public void setNoIntercept(boolean noIntercept) {
        this.noIntercept = noIntercept;
    }

    public void newSampleData(double[] data, int nobs, int nvars) {
        if (data == null) {
            throw new NullArgumentException();
        } else if (data.length != (nvars + 1) * nobs) {
            throw new DimensionMismatchException(data.length, (nvars + 1) * nobs);
        } else if (nobs <= nvars) {
            throw new InsufficientDataException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(nobs), Integer.valueOf(nvars + 1));
        } else {
            double[] y = new double[nobs];
            int cols = this.noIntercept ? nvars : nvars + 1;
            double[][] x = (double[][]) Array.newInstance(Double.TYPE, new int[]{nobs, cols});
            int pointer = 0;
            for (int i = 0; i < nobs; i++) {
                int pointer2 = pointer + 1;
                y[i] = data[pointer];
                if (!this.noIntercept) {
                    x[i][0] = 1.0d;
                }
                int j = this.noIntercept ? 0 : 1;
                pointer = pointer2;
                while (j < cols) {
                    pointer2 = pointer + 1;
                    x[i][j] = data[pointer];
                    j++;
                    pointer = pointer2;
                }
            }
            this.xMatrix = new Array2DRowRealMatrix(x);
            this.yVector = new ArrayRealVector(y);
        }
    }

    protected void newYSampleData(double[] y) {
        if (y == null) {
            throw new NullArgumentException();
        } else if (y.length == 0) {
            throw new NoDataException();
        } else {
            this.yVector = new ArrayRealVector(y);
        }
    }

    protected void newXSampleData(double[][] x) {
        if (x == null) {
            throw new NullArgumentException();
        } else if (x.length == 0) {
            throw new NoDataException();
        } else if (this.noIntercept) {
            this.xMatrix = new Array2DRowRealMatrix(x, true);
        } else {
            int nVars = x[0].length;
            double[][] xAug = (double[][]) Array.newInstance(Double.TYPE, new int[]{x.length, nVars + 1});
            for (int i = 0; i < x.length; i++) {
                if (x[i].length != nVars) {
                    throw new DimensionMismatchException(x[i].length, nVars);
                }
                xAug[i][0] = 1.0d;
                System.arraycopy(x[i], 0, xAug[i], 1, nVars);
            }
            this.xMatrix = new Array2DRowRealMatrix(xAug, false);
        }
    }

    protected void validateSampleData(double[][] x, double[] y) throws MathIllegalArgumentException {
        if (x == null || y == null) {
            throw new NullArgumentException();
        } else if (x.length != y.length) {
            throw new DimensionMismatchException(y.length, x.length);
        } else if (x.length == 0) {
            throw new NoDataException();
        } else if (x[0].length + 1 > x.length) {
            throw new MathIllegalArgumentException(LocalizedFormats.NOT_ENOUGH_DATA_FOR_NUMBER_OF_PREDICTORS, Integer.valueOf(x.length), Integer.valueOf(x[0].length));
        }
    }

    protected void validateCovarianceData(double[][] x, double[][] covariance) {
        if (x.length != covariance.length) {
            throw new DimensionMismatchException(x.length, covariance.length);
        } else if (covariance.length > 0 && covariance.length != covariance[0].length) {
            throw new NonSquareMatrixException(covariance.length, covariance[0].length);
        }
    }

    public double[] estimateRegressionParameters() {
        return calculateBeta().toArray();
    }

    public double[] estimateResiduals() {
        return this.yVector.subtract(this.xMatrix.operate(calculateBeta())).toArray();
    }

    public double[][] estimateRegressionParametersVariance() {
        return calculateBetaVariance().getData();
    }

    public double[] estimateRegressionParametersStandardErrors() {
        double[][] betaVariance = estimateRegressionParametersVariance();
        double sigma = calculateErrorVariance();
        int length = betaVariance[0].length;
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = FastMath.sqrt(betaVariance[i][i] * sigma);
        }
        return result;
    }

    public double estimateRegressandVariance() {
        return calculateYVariance();
    }

    public double estimateErrorVariance() {
        return calculateErrorVariance();
    }

    public double estimateRegressionStandardError() {
        return FastMath.sqrt(estimateErrorVariance());
    }

    protected double calculateYVariance() {
        return new Variance().evaluate(this.yVector.toArray());
    }

    protected double calculateErrorVariance() {
        RealVector residuals = calculateResiduals();
        return residuals.dotProduct(residuals) / ((double) (this.xMatrix.getRowDimension() - this.xMatrix.getColumnDimension()));
    }

    protected RealVector calculateResiduals() {
        return this.yVector.subtract(this.xMatrix.operate(calculateBeta()));
    }
}
