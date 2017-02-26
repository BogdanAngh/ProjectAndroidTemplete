package org.apache.commons.math4.stat.correlation;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.BlockRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.stat.descriptive.moment.Mean;
import org.apache.commons.math4.stat.descriptive.moment.Variance;

public class Covariance {
    private final RealMatrix covarianceMatrix;
    private final int n;

    public Covariance() {
        this.covarianceMatrix = null;
        this.n = 0;
    }

    public Covariance(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException, NotStrictlyPositiveException {
        this(new BlockRealMatrix(data), biasCorrected);
    }

    public Covariance(double[][] data) throws MathIllegalArgumentException, NotStrictlyPositiveException {
        this(data, true);
    }

    public Covariance(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
        checkSufficientData(matrix);
        this.n = matrix.getRowDimension();
        this.covarianceMatrix = computeCovarianceMatrix(matrix, biasCorrected);
    }

    public Covariance(RealMatrix matrix) throws MathIllegalArgumentException {
        this(matrix, true);
    }

    public RealMatrix getCovarianceMatrix() {
        return this.covarianceMatrix;
    }

    public int getN() {
        return this.n;
    }

    protected RealMatrix computeCovarianceMatrix(RealMatrix matrix, boolean biasCorrected) throws MathIllegalArgumentException {
        int dimension = matrix.getColumnDimension();
        Variance variance = new Variance(biasCorrected);
        RealMatrix outMatrix = new BlockRealMatrix(dimension, dimension);
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < i; j++) {
                double cov = covariance(matrix.getColumn(i), matrix.getColumn(j), biasCorrected);
                outMatrix.setEntry(i, j, cov);
                outMatrix.setEntry(j, i, cov);
            }
            outMatrix.setEntry(i, i, variance.evaluate(matrix.getColumn(i)));
        }
        return outMatrix;
    }

    protected RealMatrix computeCovarianceMatrix(RealMatrix matrix) throws MathIllegalArgumentException {
        return computeCovarianceMatrix(matrix, true);
    }

    protected RealMatrix computeCovarianceMatrix(double[][] data, boolean biasCorrected) throws MathIllegalArgumentException, NotStrictlyPositiveException {
        return computeCovarianceMatrix(new BlockRealMatrix(data), biasCorrected);
    }

    protected RealMatrix computeCovarianceMatrix(double[][] data) throws MathIllegalArgumentException, NotStrictlyPositiveException {
        return computeCovarianceMatrix(data, true);
    }

    public double covariance(double[] xArray, double[] yArray, boolean biasCorrected) throws MathIllegalArgumentException {
        Mean mean = new Mean();
        double result = 0.0d;
        int length = xArray.length;
        if (length != yArray.length) {
            LocalizedFormats localizedFormats = LocalizedFormats.DIMENSIONS_MISMATCH_SIMPLE;
            Integer[] numArr = new Object[2];
            numArr[0] = Integer.valueOf(length);
            numArr[1] = Integer.valueOf(yArray.length);
            throw new MathIllegalArgumentException(localizedFormats, numArr);
        } else if (length < 2) {
            throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(length), Integer.valueOf(2));
        } else {
            double xMean = mean.evaluate(xArray);
            double yMean = mean.evaluate(yArray);
            for (int i = 0; i < length; i++) {
                result += (((xArray[i] - xMean) * (yArray[i] - yMean)) - result) / ((double) (i + 1));
            }
            if (!biasCorrected) {
                return result;
            }
            return result * (((double) length) / ((double) (length - 1)));
        }
    }

    public double covariance(double[] xArray, double[] yArray) throws MathIllegalArgumentException {
        return covariance(xArray, yArray, true);
    }

    private void checkSufficientData(RealMatrix matrix) throws MathIllegalArgumentException {
        int nRows = matrix.getRowDimension();
        int nCols = matrix.getColumnDimension();
        if (nRows < 2 || nCols < 1) {
            throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_ROWS_AND_COLUMNS, Integer.valueOf(nRows), Integer.valueOf(nCols));
        }
    }
}
