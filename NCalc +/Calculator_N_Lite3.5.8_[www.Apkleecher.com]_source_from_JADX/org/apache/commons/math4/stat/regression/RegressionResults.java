package org.apache.commons.math4.stat.regression;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class RegressionResults implements Serializable {
    private static final int ADJRSQ_IDX = 4;
    private static final int MSE_IDX = 3;
    private static final int RSQ_IDX = 2;
    private static final int SSE_IDX = 0;
    private static final int SST_IDX = 1;
    private static final long serialVersionUID = 1;
    private final boolean containsConstant;
    private final double[] globalFitInfo;
    private final boolean isSymmetricVCD;
    private final long nobs;
    private final double[] parameters;
    private final int rank;
    private final double[][] varCovData;

    private RegressionResults() {
        this.parameters = null;
        this.varCovData = null;
        this.rank = -1;
        this.nobs = -1;
        this.containsConstant = false;
        this.isSymmetricVCD = false;
        this.globalFitInfo = null;
    }

    public RegressionResults(double[] parameters, double[][] varcov, boolean isSymmetricCompressed, long nobs, int rank, double sumy, double sumysq, double sse, boolean containsConstant, boolean copyData) {
        if (copyData) {
            this.parameters = MathArrays.copyOf(parameters);
            this.varCovData = new double[varcov.length][];
            for (int i = SSE_IDX; i < varcov.length; i += SST_IDX) {
                this.varCovData[i] = MathArrays.copyOf(varcov[i]);
            }
        } else {
            this.parameters = parameters;
            this.varCovData = varcov;
        }
        this.isSymmetricVCD = isSymmetricCompressed;
        this.nobs = nobs;
        this.rank = rank;
        this.containsConstant = containsConstant;
        this.globalFitInfo = new double[5];
        Arrays.fill(this.globalFitInfo, Double.NaN);
        if (rank > 0) {
            double[] dArr = this.globalFitInfo;
            if (containsConstant) {
                sumysq -= (sumy * sumy) / ((double) nobs);
            }
            dArr[SST_IDX] = sumysq;
        }
        this.globalFitInfo[SSE_IDX] = sse;
        this.globalFitInfo[MSE_IDX] = this.globalFitInfo[SSE_IDX] / ((double) (nobs - ((long) rank)));
        this.globalFitInfo[RSQ_IDX] = 1.0d - (this.globalFitInfo[SSE_IDX] / this.globalFitInfo[SST_IDX]);
        if (containsConstant) {
            this.globalFitInfo[ADJRSQ_IDX] = 1.0d - (((((double) nobs) - 1.0d) * sse) / (this.globalFitInfo[SST_IDX] * ((double) (nobs - ((long) rank)))));
        } else {
            this.globalFitInfo[ADJRSQ_IDX] = 1.0d - ((1.0d - this.globalFitInfo[RSQ_IDX]) * (((double) nobs) / ((double) (nobs - ((long) rank)))));
        }
    }

    public double getParameterEstimate(int index) throws OutOfRangeException {
        if (this.parameters == null) {
            return Double.NaN;
        }
        if (index >= 0 && index < this.parameters.length) {
            return this.parameters[index];
        }
        throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(SSE_IDX), Integer.valueOf(this.parameters.length - 1));
    }

    public double[] getParameterEstimates() {
        if (this.parameters == null) {
            return null;
        }
        return MathArrays.copyOf(this.parameters);
    }

    public double getStdErrorOfEstimate(int index) throws OutOfRangeException {
        if (this.parameters == null) {
            return Double.NaN;
        }
        if (index < 0 || index >= this.parameters.length) {
            throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(SSE_IDX), Integer.valueOf(this.parameters.length - 1));
        }
        double var = getVcvElement(index, index);
        if (Double.isNaN(var) || var <= Double.MIN_VALUE) {
            return Double.NaN;
        }
        return FastMath.sqrt(var);
    }

    public double[] getStdErrorOfEstimates() {
        if (this.parameters == null) {
            return null;
        }
        double[] se = new double[this.parameters.length];
        for (int i = SSE_IDX; i < this.parameters.length; i += SST_IDX) {
            double var = getVcvElement(i, i);
            if (Double.isNaN(var) || var <= Double.MIN_VALUE) {
                se[i] = Double.NaN;
            } else {
                se[i] = FastMath.sqrt(var);
            }
        }
        return se;
    }

    public double getCovarianceOfParameters(int i, int j) throws OutOfRangeException {
        if (this.parameters == null) {
            return Double.NaN;
        }
        if (i < 0 || i >= this.parameters.length) {
            throw new OutOfRangeException(Integer.valueOf(i), Integer.valueOf(SSE_IDX), Integer.valueOf(this.parameters.length - 1));
        } else if (j >= 0 && j < this.parameters.length) {
            return getVcvElement(i, j);
        } else {
            throw new OutOfRangeException(Integer.valueOf(j), Integer.valueOf(SSE_IDX), Integer.valueOf(this.parameters.length - 1));
        }
    }

    public int getNumberOfParameters() {
        if (this.parameters == null) {
            return -1;
        }
        return this.parameters.length;
    }

    public long getN() {
        return this.nobs;
    }

    public double getTotalSumSquares() {
        return this.globalFitInfo[SST_IDX];
    }

    public double getRegressionSumSquares() {
        return this.globalFitInfo[SST_IDX] - this.globalFitInfo[SSE_IDX];
    }

    public double getErrorSumSquares() {
        return this.globalFitInfo[SSE_IDX];
    }

    public double getMeanSquareError() {
        return this.globalFitInfo[MSE_IDX];
    }

    public double getRSquared() {
        return this.globalFitInfo[RSQ_IDX];
    }

    public double getAdjustedRSquared() {
        return this.globalFitInfo[ADJRSQ_IDX];
    }

    public boolean hasIntercept() {
        return this.containsConstant;
    }

    private double getVcvElement(int i, int j) {
        if (!this.isSymmetricVCD) {
            return this.varCovData[i][j];
        }
        if (this.varCovData.length > SST_IDX) {
            if (i == j) {
                return this.varCovData[i][i];
            }
            if (i >= this.varCovData[j].length) {
                return this.varCovData[i][j];
            }
            return this.varCovData[j][i];
        } else if (i > j) {
            return this.varCovData[SSE_IDX][(((i + SST_IDX) * i) / RSQ_IDX) + j];
        } else {
            return this.varCovData[SSE_IDX][(((j + SST_IDX) * j) / RSQ_IDX) + i];
        }
    }
}
