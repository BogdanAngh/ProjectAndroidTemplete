package org.apache.commons.math4.stat.descriptive;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math4.stat.descriptive.moment.Mean;
import org.apache.commons.math4.stat.descriptive.moment.VectorialCovariance;
import org.apache.commons.math4.stat.descriptive.rank.Max;
import org.apache.commons.math4.stat.descriptive.rank.Min;
import org.apache.commons.math4.stat.descriptive.summary.Sum;
import org.apache.commons.math4.stat.descriptive.summary.SumOfLogs;
import org.apache.commons.math4.stat.descriptive.summary.SumOfSquares;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public class MultivariateSummaryStatistics implements StatisticalMultivariateSummary, Serializable {
    private static final long serialVersionUID = 2271900808994826718L;
    private final VectorialCovariance covarianceImpl;
    private final StorelessUnivariateStatistic[] geoMeanImpl;
    private final int k;
    private final StorelessUnivariateStatistic[] maxImpl;
    private final StorelessUnivariateStatistic[] meanImpl;
    private final StorelessUnivariateStatistic[] minImpl;
    private long n;
    private final StorelessUnivariateStatistic[] sumImpl;
    private final StorelessUnivariateStatistic[] sumLogImpl;
    private final StorelessUnivariateStatistic[] sumSqImpl;

    public MultivariateSummaryStatistics(int k, boolean isCovarianceBiasCorrected) {
        this.n = 0;
        this.k = k;
        this.sumImpl = new StorelessUnivariateStatistic[k];
        this.sumSqImpl = new StorelessUnivariateStatistic[k];
        this.minImpl = new StorelessUnivariateStatistic[k];
        this.maxImpl = new StorelessUnivariateStatistic[k];
        this.sumLogImpl = new StorelessUnivariateStatistic[k];
        this.geoMeanImpl = new StorelessUnivariateStatistic[k];
        this.meanImpl = new StorelessUnivariateStatistic[k];
        for (int i = 0; i < k; i++) {
            this.sumImpl[i] = new Sum();
            this.sumSqImpl[i] = new SumOfSquares();
            this.minImpl[i] = new Min();
            this.maxImpl[i] = new Max();
            this.sumLogImpl[i] = new SumOfLogs();
            this.geoMeanImpl[i] = new GeometricMean();
            this.meanImpl[i] = new Mean();
        }
        this.covarianceImpl = new VectorialCovariance(k, isCovarianceBiasCorrected);
    }

    public void addValue(double[] value) throws DimensionMismatchException {
        checkDimension(value.length);
        for (int i = 0; i < this.k; i++) {
            double v = value[i];
            this.sumImpl[i].increment(v);
            this.sumSqImpl[i].increment(v);
            this.minImpl[i].increment(v);
            this.maxImpl[i].increment(v);
            this.sumLogImpl[i].increment(v);
            this.geoMeanImpl[i].increment(v);
            this.meanImpl[i].increment(v);
        }
        this.covarianceImpl.increment(value);
        this.n++;
    }

    public int getDimension() {
        return this.k;
    }

    public long getN() {
        return this.n;
    }

    private double[] getResults(StorelessUnivariateStatistic[] stats) {
        double[] results = new double[stats.length];
        for (int i = 0; i < results.length; i++) {
            results[i] = stats[i].getResult();
        }
        return results;
    }

    public double[] getSum() {
        return getResults(this.sumImpl);
    }

    public double[] getSumSq() {
        return getResults(this.sumSqImpl);
    }

    public double[] getSumLog() {
        return getResults(this.sumLogImpl);
    }

    public double[] getMean() {
        return getResults(this.meanImpl);
    }

    public double[] getStandardDeviation() {
        double[] stdDev = new double[this.k];
        if (getN() < 1) {
            Arrays.fill(stdDev, Double.NaN);
        } else if (getN() < 2) {
            Arrays.fill(stdDev, 0.0d);
        } else {
            RealMatrix matrix = this.covarianceImpl.getResult();
            for (int i = 0; i < this.k; i++) {
                stdDev[i] = FastMath.sqrt(matrix.getEntry(i, i));
            }
        }
        return stdDev;
    }

    public RealMatrix getCovariance() {
        return this.covarianceImpl.getResult();
    }

    public double[] getMax() {
        return getResults(this.maxImpl);
    }

    public double[] getMin() {
        return getResults(this.minImpl);
    }

    public double[] getGeometricMean() {
        return getResults(this.geoMeanImpl);
    }

    public String toString() {
        String separator = ", ";
        String suffix = System.getProperty("line.separator");
        StringBuilder outBuffer = new StringBuilder();
        outBuffer.append("MultivariateSummaryStatistics:" + suffix);
        outBuffer.append("n: " + getN() + suffix);
        append(outBuffer, getMin(), "min: ", ", ", suffix);
        append(outBuffer, getMax(), "max: ", ", ", suffix);
        append(outBuffer, getMean(), "mean: ", ", ", suffix);
        append(outBuffer, getGeometricMean(), "geometric mean: ", ", ", suffix);
        append(outBuffer, getSumSq(), "sum of squares: ", ", ", suffix);
        append(outBuffer, getSumLog(), "sum of logarithms: ", ", ", suffix);
        append(outBuffer, getStandardDeviation(), "standard deviation: ", ", ", suffix);
        outBuffer.append("covariance: " + getCovariance().toString() + suffix);
        return outBuffer.toString();
    }

    private void append(StringBuilder buffer, double[] data, String prefix, String separator, String suffix) {
        buffer.append(prefix);
        for (int i = 0; i < data.length; i++) {
            if (i > 0) {
                buffer.append(separator);
            }
            buffer.append(data[i]);
        }
        buffer.append(suffix);
    }

    public void clear() {
        this.n = 0;
        for (int i = 0; i < this.k; i++) {
            this.minImpl[i].clear();
            this.maxImpl[i].clear();
            this.sumImpl[i].clear();
            this.sumLogImpl[i].clear();
            this.sumSqImpl[i].clear();
            this.geoMeanImpl[i].clear();
            this.meanImpl[i].clear();
        }
        this.covarianceImpl.clear();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof MultivariateSummaryStatistics)) {
            return false;
        }
        MultivariateSummaryStatistics stat = (MultivariateSummaryStatistics) object;
        if (MathArrays.equalsIncludingNaN(stat.getGeometricMean(), getGeometricMean()) && MathArrays.equalsIncludingNaN(stat.getMax(), getMax()) && MathArrays.equalsIncludingNaN(stat.getMean(), getMean()) && MathArrays.equalsIncludingNaN(stat.getMin(), getMin()) && Precision.equalsIncludingNaN((float) stat.getN(), (float) getN()) && MathArrays.equalsIncludingNaN(stat.getSum(), getSum()) && MathArrays.equalsIncludingNaN(stat.getSumSq(), getSumSq()) && MathArrays.equalsIncludingNaN(stat.getSumLog(), getSumLog()) && stat.getCovariance().equals(getCovariance())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((((((((((((((((((MathUtils.hash(getGeometricMean()) + 31) * 31) + MathUtils.hash(getGeometricMean())) * 31) + MathUtils.hash(getMax())) * 31) + MathUtils.hash(getMean())) * 31) + MathUtils.hash(getMin())) * 31) + MathUtils.hash((double) getN())) * 31) + MathUtils.hash(getSum())) * 31) + MathUtils.hash(getSumSq())) * 31) + MathUtils.hash(getSumLog())) * 31) + getCovariance().hashCode();
    }

    private void setImpl(StorelessUnivariateStatistic[] newImpl, StorelessUnivariateStatistic[] oldImpl) throws MathIllegalStateException, DimensionMismatchException {
        checkEmpty();
        checkDimension(newImpl.length);
        System.arraycopy(newImpl, 0, oldImpl, 0, newImpl.length);
    }

    public StorelessUnivariateStatistic[] getSumImpl() {
        return (StorelessUnivariateStatistic[]) this.sumImpl.clone();
    }

    public void setSumImpl(StorelessUnivariateStatistic[] sumImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(sumImpl, this.sumImpl);
    }

    public StorelessUnivariateStatistic[] getSumsqImpl() {
        return (StorelessUnivariateStatistic[]) this.sumSqImpl.clone();
    }

    public void setSumsqImpl(StorelessUnivariateStatistic[] sumsqImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(sumsqImpl, this.sumSqImpl);
    }

    public StorelessUnivariateStatistic[] getMinImpl() {
        return (StorelessUnivariateStatistic[]) this.minImpl.clone();
    }

    public void setMinImpl(StorelessUnivariateStatistic[] minImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(minImpl, this.minImpl);
    }

    public StorelessUnivariateStatistic[] getMaxImpl() {
        return (StorelessUnivariateStatistic[]) this.maxImpl.clone();
    }

    public void setMaxImpl(StorelessUnivariateStatistic[] maxImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(maxImpl, this.maxImpl);
    }

    public StorelessUnivariateStatistic[] getSumLogImpl() {
        return (StorelessUnivariateStatistic[]) this.sumLogImpl.clone();
    }

    public void setSumLogImpl(StorelessUnivariateStatistic[] sumLogImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(sumLogImpl, this.sumLogImpl);
    }

    public StorelessUnivariateStatistic[] getGeoMeanImpl() {
        return (StorelessUnivariateStatistic[]) this.geoMeanImpl.clone();
    }

    public void setGeoMeanImpl(StorelessUnivariateStatistic[] geoMeanImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(geoMeanImpl, this.geoMeanImpl);
    }

    public StorelessUnivariateStatistic[] getMeanImpl() {
        return (StorelessUnivariateStatistic[]) this.meanImpl.clone();
    }

    public void setMeanImpl(StorelessUnivariateStatistic[] meanImpl) throws MathIllegalStateException, DimensionMismatchException {
        setImpl(meanImpl, this.meanImpl);
    }

    private void checkEmpty() throws MathIllegalStateException {
        if (this.n > 0) {
            throw new MathIllegalStateException(LocalizedFormats.VALUES_ADDED_BEFORE_CONFIGURING_STATISTIC, Long.valueOf(this.n));
        }
    }

    private void checkDimension(int dimension) throws DimensionMismatchException {
        if (dimension != this.k) {
            throw new DimensionMismatchException(dimension, this.k);
        }
    }
}
