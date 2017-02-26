package org.apache.commons.math4.stat.descriptive;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.stat.descriptive.moment.GeometricMean;
import org.apache.commons.math4.stat.descriptive.moment.Kurtosis;
import org.apache.commons.math4.stat.descriptive.moment.Mean;
import org.apache.commons.math4.stat.descriptive.moment.Skewness;
import org.apache.commons.math4.stat.descriptive.moment.Variance;
import org.apache.commons.math4.stat.descriptive.rank.Max;
import org.apache.commons.math4.stat.descriptive.rank.Min;
import org.apache.commons.math4.stat.descriptive.rank.Percentile;
import org.apache.commons.math4.stat.descriptive.summary.Sum;
import org.apache.commons.math4.stat.descriptive.summary.SumOfSquares;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.ResizableDoubleArray;

public class DescriptiveStatistics implements StatisticalSummary, Serializable {
    public static final int INFINITE_WINDOW = -1;
    private static final String SET_QUANTILE_METHOD_NAME = "setQuantile";
    private static final long serialVersionUID = 4133067267405273064L;
    private ResizableDoubleArray eDA;
    private UnivariateStatistic geometricMeanImpl;
    private UnivariateStatistic kurtosisImpl;
    private UnivariateStatistic maxImpl;
    private UnivariateStatistic meanImpl;
    private UnivariateStatistic minImpl;
    private UnivariateStatistic percentileImpl;
    private UnivariateStatistic skewnessImpl;
    private UnivariateStatistic sumImpl;
    private UnivariateStatistic sumsqImpl;
    private UnivariateStatistic varianceImpl;
    private int windowSize;

    public DescriptiveStatistics() {
        this.windowSize = INFINITE_WINDOW;
        this.eDA = new ResizableDoubleArray();
        this.meanImpl = new Mean();
        this.geometricMeanImpl = new GeometricMean();
        this.kurtosisImpl = new Kurtosis();
        this.maxImpl = new Max();
        this.minImpl = new Min();
        this.percentileImpl = new Percentile();
        this.skewnessImpl = new Skewness();
        this.varianceImpl = new Variance();
        this.sumsqImpl = new SumOfSquares();
        this.sumImpl = new Sum();
    }

    public DescriptiveStatistics(int window) throws MathIllegalArgumentException {
        this.windowSize = INFINITE_WINDOW;
        this.eDA = new ResizableDoubleArray();
        this.meanImpl = new Mean();
        this.geometricMeanImpl = new GeometricMean();
        this.kurtosisImpl = new Kurtosis();
        this.maxImpl = new Max();
        this.minImpl = new Min();
        this.percentileImpl = new Percentile();
        this.skewnessImpl = new Skewness();
        this.varianceImpl = new Variance();
        this.sumsqImpl = new SumOfSquares();
        this.sumImpl = new Sum();
        setWindowSize(window);
    }

    public DescriptiveStatistics(double[] initialDoubleArray) {
        this.windowSize = INFINITE_WINDOW;
        this.eDA = new ResizableDoubleArray();
        this.meanImpl = new Mean();
        this.geometricMeanImpl = new GeometricMean();
        this.kurtosisImpl = new Kurtosis();
        this.maxImpl = new Max();
        this.minImpl = new Min();
        this.percentileImpl = new Percentile();
        this.skewnessImpl = new Skewness();
        this.varianceImpl = new Variance();
        this.sumsqImpl = new SumOfSquares();
        this.sumImpl = new Sum();
        if (initialDoubleArray != null) {
            this.eDA = new ResizableDoubleArray(initialDoubleArray);
        }
    }

    public DescriptiveStatistics(DescriptiveStatistics original) throws NullArgumentException {
        this.windowSize = INFINITE_WINDOW;
        this.eDA = new ResizableDoubleArray();
        this.meanImpl = new Mean();
        this.geometricMeanImpl = new GeometricMean();
        this.kurtosisImpl = new Kurtosis();
        this.maxImpl = new Max();
        this.minImpl = new Min();
        this.percentileImpl = new Percentile();
        this.skewnessImpl = new Skewness();
        this.varianceImpl = new Variance();
        this.sumsqImpl = new SumOfSquares();
        this.sumImpl = new Sum();
        copy(original, this);
    }

    public void addValue(double v) {
        if (this.windowSize == INFINITE_WINDOW) {
            this.eDA.addElement(v);
        } else if (getN() == ((long) this.windowSize)) {
            this.eDA.addElementRolling(v);
        } else if (getN() < ((long) this.windowSize)) {
            this.eDA.addElement(v);
        }
    }

    public void removeMostRecentValue() throws MathIllegalStateException {
        try {
            this.eDA.discardMostRecentElements(1);
        } catch (MathIllegalArgumentException e) {
            throw new MathIllegalStateException(LocalizedFormats.NO_DATA, new Object[0]);
        }
    }

    public double replaceMostRecentValue(double v) throws MathIllegalStateException {
        return this.eDA.substituteMostRecentElement(v);
    }

    public double getMean() {
        return apply(this.meanImpl);
    }

    public double getGeometricMean() {
        return apply(this.geometricMeanImpl);
    }

    public double getVariance() {
        return apply(this.varianceImpl);
    }

    public double getPopulationVariance() {
        return apply(new Variance(false));
    }

    public double getStandardDeviation() {
        if (getN() <= 0) {
            return Double.NaN;
        }
        if (getN() > 1) {
            return FastMath.sqrt(getVariance());
        }
        return 0.0d;
    }

    public double getQuadraticMean() {
        long n = getN();
        return n > 0 ? FastMath.sqrt(getSumsq() / ((double) n)) : Double.NaN;
    }

    public double getSkewness() {
        return apply(this.skewnessImpl);
    }

    public double getKurtosis() {
        return apply(this.kurtosisImpl);
    }

    public double getMax() {
        return apply(this.maxImpl);
    }

    public double getMin() {
        return apply(this.minImpl);
    }

    public long getN() {
        return (long) this.eDA.getNumElements();
    }

    public double getSum() {
        return apply(this.sumImpl);
    }

    public double getSumsq() {
        return apply(this.sumsqImpl);
    }

    public void clear() {
        this.eDA.clear();
    }

    public int getWindowSize() {
        return this.windowSize;
    }

    public void setWindowSize(int windowSize) throws MathIllegalArgumentException {
        if (windowSize >= 1 || windowSize == INFINITE_WINDOW) {
            this.windowSize = windowSize;
            if (windowSize != INFINITE_WINDOW && windowSize < this.eDA.getNumElements()) {
                this.eDA.discardFrontElements(this.eDA.getNumElements() - windowSize);
                return;
            }
            return;
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NOT_POSITIVE_WINDOW_SIZE, Integer.valueOf(windowSize));
    }

    public double[] getValues() {
        return this.eDA.getElements();
    }

    public double[] getSortedValues() {
        double[] sort = getValues();
        Arrays.sort(sort);
        return sort;
    }

    public double getElement(int index) {
        return this.eDA.getElement(index);
    }

    public double getPercentile(double p) throws MathIllegalStateException, MathIllegalArgumentException {
        if (this.percentileImpl instanceof Percentile) {
            ((Percentile) this.percentileImpl).setQuantile(p);
        } else {
            try {
                this.percentileImpl.getClass().getMethod(SET_QUANTILE_METHOD_NAME, new Class[]{Double.TYPE}).invoke(this.percentileImpl, new Object[]{Double.valueOf(p)});
            } catch (NoSuchMethodException e) {
                throw new MathIllegalStateException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, this.percentileImpl.getClass().getName(), SET_QUANTILE_METHOD_NAME);
            } catch (IllegalAccessException e2) {
                throw new MathIllegalStateException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, SET_QUANTILE_METHOD_NAME, this.percentileImpl.getClass().getName());
            } catch (InvocationTargetException e3) {
                throw new IllegalStateException(e3.getCause());
            }
        }
        return apply(this.percentileImpl);
    }

    public String toString() {
        StringBuilder outBuffer = new StringBuilder();
        String endl = "\n";
        outBuffer.append("DescriptiveStatistics:").append(endl);
        outBuffer.append("n: ").append(getN()).append(endl);
        outBuffer.append("min: ").append(getMin()).append(endl);
        outBuffer.append("max: ").append(getMax()).append(endl);
        outBuffer.append("mean: ").append(getMean()).append(endl);
        outBuffer.append("std dev: ").append(getStandardDeviation()).append(endl);
        try {
            outBuffer.append("median: ").append(getPercentile(50.0d)).append(endl);
        } catch (MathIllegalStateException e) {
            outBuffer.append("median: unavailable").append(endl);
        }
        outBuffer.append("skewness: ").append(getSkewness()).append(endl);
        outBuffer.append("kurtosis: ").append(getKurtosis()).append(endl);
        return outBuffer.toString();
    }

    public double apply(UnivariateStatistic stat) {
        return this.eDA.compute(stat);
    }

    public synchronized UnivariateStatistic getMeanImpl() {
        return this.meanImpl;
    }

    public synchronized void setMeanImpl(UnivariateStatistic meanImpl) {
        this.meanImpl = meanImpl;
    }

    public synchronized UnivariateStatistic getGeometricMeanImpl() {
        return this.geometricMeanImpl;
    }

    public synchronized void setGeometricMeanImpl(UnivariateStatistic geometricMeanImpl) {
        this.geometricMeanImpl = geometricMeanImpl;
    }

    public synchronized UnivariateStatistic getKurtosisImpl() {
        return this.kurtosisImpl;
    }

    public synchronized void setKurtosisImpl(UnivariateStatistic kurtosisImpl) {
        this.kurtosisImpl = kurtosisImpl;
    }

    public synchronized UnivariateStatistic getMaxImpl() {
        return this.maxImpl;
    }

    public synchronized void setMaxImpl(UnivariateStatistic maxImpl) {
        this.maxImpl = maxImpl;
    }

    public synchronized UnivariateStatistic getMinImpl() {
        return this.minImpl;
    }

    public synchronized void setMinImpl(UnivariateStatistic minImpl) {
        this.minImpl = minImpl;
    }

    public synchronized UnivariateStatistic getPercentileImpl() {
        return this.percentileImpl;
    }

    public synchronized void setPercentileImpl(UnivariateStatistic percentileImpl) throws MathIllegalArgumentException {
        try {
            percentileImpl.getClass().getMethod(SET_QUANTILE_METHOD_NAME, new Class[]{Double.TYPE}).invoke(percentileImpl, new Object[]{Double.valueOf(50.0d)});
            this.percentileImpl = percentileImpl;
        } catch (NoSuchMethodException e) {
            throw new MathIllegalArgumentException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_UNSUPPORTED_METHOD, percentileImpl.getClass().getName(), SET_QUANTILE_METHOD_NAME);
        } catch (IllegalAccessException e2) {
            throw new MathIllegalArgumentException(LocalizedFormats.PERCENTILE_IMPLEMENTATION_CANNOT_ACCESS_METHOD, SET_QUANTILE_METHOD_NAME, percentileImpl.getClass().getName());
        } catch (InvocationTargetException e3) {
            throw new IllegalArgumentException(e3.getCause());
        }
    }

    public synchronized UnivariateStatistic getSkewnessImpl() {
        return this.skewnessImpl;
    }

    public synchronized void setSkewnessImpl(UnivariateStatistic skewnessImpl) {
        this.skewnessImpl = skewnessImpl;
    }

    public synchronized UnivariateStatistic getVarianceImpl() {
        return this.varianceImpl;
    }

    public synchronized void setVarianceImpl(UnivariateStatistic varianceImpl) {
        this.varianceImpl = varianceImpl;
    }

    public synchronized UnivariateStatistic getSumsqImpl() {
        return this.sumsqImpl;
    }

    public synchronized void setSumsqImpl(UnivariateStatistic sumsqImpl) {
        this.sumsqImpl = sumsqImpl;
    }

    public synchronized UnivariateStatistic getSumImpl() {
        return this.sumImpl;
    }

    public synchronized void setSumImpl(UnivariateStatistic sumImpl) {
        this.sumImpl = sumImpl;
    }

    public DescriptiveStatistics copy() {
        DescriptiveStatistics result = new DescriptiveStatistics();
        copy(this, result);
        return result;
    }

    public static void copy(DescriptiveStatistics source, DescriptiveStatistics dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.eDA = source.eDA.copy();
        dest.windowSize = source.windowSize;
        dest.maxImpl = source.maxImpl.copy();
        dest.meanImpl = source.meanImpl.copy();
        dest.minImpl = source.minImpl.copy();
        dest.sumImpl = source.sumImpl.copy();
        dest.varianceImpl = source.varianceImpl.copy();
        dest.sumsqImpl = source.sumsqImpl.copy();
        dest.geometricMeanImpl = source.geometricMeanImpl.copy();
        dest.kurtosisImpl = source.kurtosisImpl;
        dest.skewnessImpl = source.skewnessImpl;
        dest.percentileImpl = source.percentileImpl;
    }
}
