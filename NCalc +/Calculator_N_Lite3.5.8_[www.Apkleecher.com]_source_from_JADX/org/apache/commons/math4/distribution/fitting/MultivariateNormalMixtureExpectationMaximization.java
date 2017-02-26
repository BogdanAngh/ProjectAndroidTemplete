package org.apache.commons.math4.distribution.fitting;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.distribution.MixtureMultivariateNormalDistribution;
import org.apache.commons.math4.distribution.MultivariateNormalDistribution;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.SingularMatrixException;
import org.apache.commons.math4.stat.correlation.Covariance;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.Pair;

public class MultivariateNormalMixtureExpectationMaximization {
    private static final int DEFAULT_MAX_ITERATIONS = 1000;
    private static final double DEFAULT_THRESHOLD = 1.0E-5d;
    private final double[][] data;
    private MixtureMultivariateNormalDistribution fittedModel;
    private double logLikelihood;

    private static class DataRow implements Comparable<DataRow> {
        private Double mean;
        private final double[] row;

        DataRow(double[] data) {
            this.row = data;
            this.mean = Double.valueOf(0.0d);
            for (double doubleValue : data) {
                this.mean = Double.valueOf(this.mean.doubleValue() + doubleValue);
            }
            this.mean = Double.valueOf(this.mean.doubleValue() / ((double) data.length));
        }

        public int compareTo(DataRow other) {
            return this.mean.compareTo(other.mean);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other instanceof DataRow) {
                return MathArrays.equals(this.row, ((DataRow) other).row);
            }
            return false;
        }

        public int hashCode() {
            return Arrays.hashCode(this.row);
        }

        public double[] getRow() {
            return this.row;
        }
    }

    public MultivariateNormalMixtureExpectationMaximization(double[][] data) throws NotStrictlyPositiveException, DimensionMismatchException, NumberIsTooSmallException {
        this.logLikelihood = 0.0d;
        if (data.length < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(data.length));
        }
        this.data = (double[][]) Array.newInstance(Double.TYPE, new int[]{data.length, data[0].length});
        int i = 0;
        while (i < data.length) {
            if (data[i].length != data[0].length) {
                throw new DimensionMismatchException(data[i].length, data[0].length);
            } else if (data[i].length < 2) {
                throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_TOO_SMALL, Integer.valueOf(data[i].length), Integer.valueOf(2), true);
            } else {
                this.data[i] = MathArrays.copyOf(data[i], data[i].length);
                i++;
            }
        }
    }

    public void fit(MixtureMultivariateNormalDistribution initialMixture, int maxIterations, double threshold) throws SingularMatrixException, NotStrictlyPositiveException, DimensionMismatchException {
        if (maxIterations < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(maxIterations));
        } else if (threshold < Double.MIN_VALUE) {
            throw new NotStrictlyPositiveException(Double.valueOf(threshold));
        } else {
            int n = this.data.length;
            int numCols = this.data[0].length;
            int k = initialMixture.getComponents().size();
            int numMeanColumns = ((MultivariateNormalDistribution) ((Pair) initialMixture.getComponents().get(0)).getSecond()).getMeans().length;
            if (numMeanColumns != numCols) {
                throw new DimensionMismatchException(numMeanColumns, numCols);
            }
            double previousLogLikelihood = 0.0d;
            this.logLikelihood = Double.NEGATIVE_INFINITY;
            this.fittedModel = new MixtureMultivariateNormalDistribution(initialMixture.getComponents());
            int numIterations = 0;
            while (true) {
                int numIterations2 = numIterations + 1;
                if (numIterations > maxIterations) {
                    break;
                }
                if (FastMath.abs(previousLogLikelihood - this.logLikelihood) <= threshold) {
                    break;
                }
                int j;
                int i;
                int col;
                previousLogLikelihood = this.logLikelihood;
                double sumLogLikelihood = 0.0d;
                List<Pair<Double, MultivariateNormalDistribution>> components = this.fittedModel.getComponents();
                double[] weights = new double[k];
                MultivariateNormalDistribution[] mvns = new MultivariateNormalDistribution[k];
                for (j = 0; j < k; j++) {
                    weights[j] = ((Double) ((Pair) components.get(j)).getFirst()).doubleValue();
                    mvns[j] = (MultivariateNormalDistribution) ((Pair) components.get(j)).getSecond();
                }
                int[] iArr = new int[]{n, k};
                double[][] gamma = (double[][]) Array.newInstance(Double.TYPE, iArr);
                double[] gammaSums = new double[k];
                iArr = new int[]{k, numCols};
                double[][] gammaDataProdSums = (double[][]) Array.newInstance(Double.TYPE, iArr);
                for (i = 0; i < n; i++) {
                    double rowDensity = this.fittedModel.density(this.data[i]);
                    sumLogLikelihood += FastMath.log(rowDensity);
                    for (j = 0; j < k; j++) {
                        gamma[i][j] = (weights[j] * mvns[j].density(this.data[i])) / rowDensity;
                        gammaSums[j] = gammaSums[j] + gamma[i][j];
                        for (col = 0; col < numCols; col++) {
                            double[] dArr = gammaDataProdSums[j];
                            dArr[col] = dArr[col] + (gamma[i][j] * this.data[i][col]);
                        }
                    }
                }
                this.logLikelihood = sumLogLikelihood / ((double) n);
                double[] newWeights = new double[k];
                iArr = new int[]{k, numCols};
                double[][] newMeans = (double[][]) Array.newInstance(Double.TYPE, iArr);
                for (j = 0; j < k; j++) {
                    newWeights[j] = gammaSums[j] / ((double) n);
                    for (col = 0; col < numCols; col++) {
                        newMeans[j][col] = gammaDataProdSums[j][col] / gammaSums[j];
                    }
                }
                RealMatrix[] newCovMats = new RealMatrix[k];
                for (j = 0; j < k; j++) {
                    newCovMats[j] = new Array2DRowRealMatrix(numCols, numCols);
                }
                for (i = 0; i < n; i++) {
                    for (j = 0; j < k; j++) {
                        RealMatrix array2DRowRealMatrix = new Array2DRowRealMatrix(MathArrays.ebeSubtract(this.data[i], newMeans[j]));
                        RealMatrix dataCov = array2DRowRealMatrix.multiply(array2DRowRealMatrix.transpose()).scalarMultiply(gamma[i][j]);
                        newCovMats[j] = newCovMats[j].add(dataCov);
                    }
                }
                iArr = new int[]{k, numCols, numCols};
                double[][][] newCovMatArrays = (double[][][]) Array.newInstance(Double.TYPE, iArr);
                for (j = 0; j < k; j++) {
                    newCovMats[j] = newCovMats[j].scalarMultiply(1.0d / gammaSums[j]);
                    newCovMatArrays[j] = newCovMats[j].getData();
                }
                this.fittedModel = new MixtureMultivariateNormalDistribution(newWeights, newMeans, newCovMatArrays);
                numIterations = numIterations2;
            }
            if (FastMath.abs(previousLogLikelihood - this.logLikelihood) > threshold) {
                throw new ConvergenceException();
            }
        }
    }

    public void fit(MixtureMultivariateNormalDistribution initialMixture) throws SingularMatrixException, NotStrictlyPositiveException {
        fit(initialMixture, DEFAULT_MAX_ITERATIONS, DEFAULT_THRESHOLD);
    }

    public static MixtureMultivariateNormalDistribution estimate(double[][] data, int numComponents) throws NotStrictlyPositiveException, DimensionMismatchException {
        int length = data.length;
        if (r0 < 2) {
            throw new NotStrictlyPositiveException(Integer.valueOf(data.length));
        } else if (numComponents < 2) {
            throw new NumberIsTooSmallException(Integer.valueOf(numComponents), Integer.valueOf(2), true);
        } else {
            if (numComponents > data.length) {
                throw new NumberIsTooLargeException(Integer.valueOf(numComponents), Integer.valueOf(data.length), true);
            }
            int i;
            int numRows = data.length;
            int numCols = data[0].length;
            DataRow[] sortedData = new DataRow[numRows];
            for (i = 0; i < numRows; i++) {
                sortedData[i] = new DataRow(data[i]);
            }
            Arrays.sort(sortedData);
            double weight = 1.0d / ((double) numComponents);
            List<Pair<Double, MultivariateNormalDistribution>> components = new ArrayList(numComponents);
            for (int binIndex = 0; binIndex < numComponents; binIndex++) {
                int minIndex = (binIndex * numRows) / numComponents;
                int maxIndex = ((binIndex + 1) * numRows) / numComponents;
                int numBinRows = maxIndex - minIndex;
                int[] iArr = new int[]{numBinRows, numCols};
                double[][] binData = (double[][]) Array.newInstance(Double.TYPE, iArr);
                double[] columnMeans = new double[numCols];
                i = minIndex;
                int iBin = 0;
                while (i < maxIndex) {
                    for (int j = 0; j < numCols; j++) {
                        double val = sortedData[i].getRow()[j];
                        columnMeans[j] = columnMeans[j] + val;
                        binData[iBin][j] = val;
                    }
                    i++;
                    iBin++;
                }
                MathArrays.scaleInPlace(1.0d / ((double) numBinRows), columnMeans);
                MultivariateNormalDistribution mvn = new MultivariateNormalDistribution(columnMeans, new Covariance(binData).getCovarianceMatrix().getData());
                components.add(new Pair(Double.valueOf(weight), mvn));
            }
            return new MixtureMultivariateNormalDistribution(components);
        }
    }

    public double getLogLikelihood() {
        return this.logLikelihood;
    }

    public MixtureMultivariateNormalDistribution getFittedModel() {
        return new MixtureMultivariateNormalDistribution(this.fittedModel.getComponents());
    }
}
