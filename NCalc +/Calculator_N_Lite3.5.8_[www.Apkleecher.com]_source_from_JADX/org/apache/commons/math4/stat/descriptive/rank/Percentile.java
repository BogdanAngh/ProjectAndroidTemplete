package org.apache.commons.math4.stat.descriptive.rank;

import java.io.Serializable;
import java.util.Arrays;
import java.util.BitSet;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.stat.descriptive.AbstractUnivariateStatistic;
import org.apache.commons.math4.stat.ranking.NaNStrategy;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.KthSelector;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.MedianOf3PivotingStrategy;
import org.apache.commons.math4.util.PivotingStrategyInterface;
import org.apache.commons.math4.util.Precision;
import org.matheclipse.core.interfaces.IExpr;

public class Percentile extends AbstractUnivariateStatistic implements Serializable {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy = null;
    private static final int MAX_CACHED_LEVELS = 10;
    private static final int PIVOTS_HEAP_LENGTH = 512;
    private static final long serialVersionUID = 20150412;
    private int[] cachedPivots;
    private final EstimationType estimationType;
    private final KthSelector kthSelector;
    private final NaNStrategy nanStrategy;
    private double quantile;

    public enum EstimationType {
        LEGACY("Legacy Apache Commons Math") {
            protected double index(double p, int length) {
                if (Double.compare(p, 0.0d) == 0) {
                    return 0.0d;
                }
                return Double.compare(p, 1.0d) == 0 ? (double) length : ((double) (length + 1)) * p;
            }
        },
        R_1("R-1") {
            protected double index(double p, int length) {
                if (Double.compare(p, 0.0d) == 0) {
                    return 0.0d;
                }
                return (((double) length) * p) + 0.5d;
            }

            protected double estimate(double[] values, int[] pivotsHeap, double pos, int length, KthSelector kthSelector) {
                return super.estimate(values, pivotsHeap, FastMath.ceil(pos - 0.5d), length, kthSelector);
            }
        },
        R_2("R-2") {
            protected double index(double p, int length) {
                if (Double.compare(p, 1.0d) == 0) {
                    return (double) length;
                }
                if (Double.compare(p, 0.0d) != 0) {
                    return (((double) length) * p) + 0.5d;
                }
                return 0.0d;
            }

            protected double estimate(double[] values, int[] pivotsHeap, double pos, int length, KthSelector kthSelector) {
                return (super.estimate(values, pivotsHeap, FastMath.ceil(pos - 0.5d), length, kthSelector) + super.estimate(values, pivotsHeap, FastMath.floor(0.5d + pos), length, kthSelector)) / 2.0d;
            }
        },
        R_3("R-3") {
            protected double index(double p, int length) {
                return Double.compare(p, 0.5d / ((double) length)) <= 0 ? 0.0d : FastMath.rint(((double) length) * p);
            }
        },
        R_4("R-4") {
            protected double index(double p, int length) {
                if (Double.compare(p, 1.0d / ((double) length)) < 0) {
                    return 0.0d;
                }
                return Double.compare(p, 1.0d) == 0 ? (double) length : ((double) length) * p;
            }
        },
        R_5("R-5") {
            protected double index(double p, int length) {
                double maxLimit = (((double) length) - 0.5d) / ((double) length);
                if (Double.compare(p, 0.5d / ((double) length)) < 0) {
                    return 0.0d;
                }
                return Double.compare(p, maxLimit) >= 0 ? (double) length : (((double) length) * p) + 0.5d;
            }
        },
        R_6("R-6") {
            protected double index(double p, int length) {
                double maxLimit = (((double) length) * 1.0d) / ((double) (length + 1));
                if (Double.compare(p, 1.0d / ((double) (length + 1))) < 0) {
                    return 0.0d;
                }
                return Double.compare(p, maxLimit) >= 0 ? (double) length : ((double) (length + 1)) * p;
            }
        },
        R_7("R-7") {
            protected double index(double p, int length) {
                if (Double.compare(p, 0.0d) == 0) {
                    return 0.0d;
                }
                return Double.compare(p, 1.0d) == 0 ? (double) length : (((double) (length - 1)) * p) + 1.0d;
            }
        },
        R_8("R-8") {
            protected double index(double p, int length) {
                double maxLimit = (((double) length) - 0.3333333333333333d) / (((double) length) + 0.3333333333333333d);
                if (Double.compare(p, 0.6666666666666666d / (((double) length) + 0.3333333333333333d)) < 0) {
                    return 0.0d;
                }
                if (Double.compare(p, maxLimit) >= 0) {
                    return (double) length;
                }
                return ((((double) length) + 0.3333333333333333d) * p) + 0.3333333333333333d;
            }
        },
        R_9("R-9") {
            protected double index(double p, int length) {
                double maxLimit = (((double) length) - 0.375d) / (((double) length) + 0.25d);
                if (Double.compare(p, 0.625d / (((double) length) + 0.25d)) < 0) {
                    return 0.0d;
                }
                if (Double.compare(p, maxLimit) >= 0) {
                    return (double) length;
                }
                return ((((double) length) + 0.25d) * p) + 0.375d;
            }
        };
        
        private final String name;

        protected abstract double index(double d, int i);

        private EstimationType(String type) {
            this.name = type;
        }

        protected double estimate(double[] work, int[] pivotsHeap, double pos, int length, KthSelector kthSelector) {
            double fpos = FastMath.floor(pos);
            int intPos = (int) fpos;
            double dif = pos - fpos;
            if (pos < 1.0d) {
                return kthSelector.select(work, pivotsHeap, 0);
            }
            if (pos >= ((double) length)) {
                return kthSelector.select(work, pivotsHeap, length - 1);
            }
            double lower = kthSelector.select(work, pivotsHeap, intPos - 1);
            return ((kthSelector.select(work, pivotsHeap, intPos) - lower) * dif) + lower;
        }

        protected double evaluate(double[] work, int[] pivotsHeap, double p, KthSelector kthSelector) {
            MathUtils.checkNotNull(work);
            if (p > 100.0d || p <= 0.0d) {
                throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
            }
            return estimate(work, pivotsHeap, index(p / 100.0d, work.length), work.length, kthSelector);
        }

        public double evaluate(double[] work, double p, KthSelector kthSelector) {
            return evaluate(work, null, p, kthSelector);
        }

        String getName() {
            return this.name;
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy;
        if (iArr == null) {
            iArr = new int[NaNStrategy.values().length];
            try {
                iArr[NaNStrategy.FAILED.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[NaNStrategy.FIXED.ordinal()] = 4;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[NaNStrategy.MAXIMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[NaNStrategy.MINIMAL.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[NaNStrategy.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy = iArr;
        }
        return iArr;
    }

    public Percentile() {
        this(50.0d);
    }

    public Percentile(double quantile) throws MathIllegalArgumentException {
        this(quantile, EstimationType.LEGACY, NaNStrategy.REMOVED, new KthSelector(new MedianOf3PivotingStrategy()));
    }

    public Percentile(Percentile original) throws NullArgumentException {
        MathUtils.checkNotNull(original);
        this.estimationType = original.getEstimationType();
        this.nanStrategy = original.getNaNStrategy();
        this.kthSelector = original.getKthSelector();
        setData(original.getDataRef());
        if (original.cachedPivots != null) {
            System.arraycopy(original.cachedPivots, 0, this.cachedPivots, 0, original.cachedPivots.length);
        }
        setQuantile(original.quantile);
    }

    protected Percentile(double quantile, EstimationType estimationType, NaNStrategy nanStrategy, KthSelector kthSelector) throws MathIllegalArgumentException {
        setQuantile(quantile);
        this.cachedPivots = null;
        MathUtils.checkNotNull(estimationType);
        MathUtils.checkNotNull(nanStrategy);
        MathUtils.checkNotNull(kthSelector);
        this.estimationType = estimationType;
        this.nanStrategy = nanStrategy;
        this.kthSelector = kthSelector;
    }

    public void setData(double[] values) {
        if (values == null) {
            this.cachedPivots = null;
        } else {
            this.cachedPivots = new int[PIVOTS_HEAP_LENGTH];
            Arrays.fill(this.cachedPivots, -1);
        }
        super.setData(values);
    }

    public void setData(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (values == null) {
            this.cachedPivots = null;
        } else {
            this.cachedPivots = new int[PIVOTS_HEAP_LENGTH];
            Arrays.fill(this.cachedPivots, -1);
        }
        super.setData(values, begin, length);
    }

    public double evaluate(double p) throws MathIllegalArgumentException {
        return evaluate(getDataRef(), p);
    }

    public double evaluate(double[] values, double p) throws MathIllegalArgumentException {
        MathArrays.verifyValues(values, 0, 0);
        return evaluate(values, 0, values.length, p);
    }

    public double evaluate(double[] values, int start, int length) throws MathIllegalArgumentException {
        return evaluate(values, start, length, this.quantile);
    }

    public double evaluate(double[] values, int begin, int length, double p) throws MathIllegalArgumentException {
        MathArrays.verifyValues(values, begin, length);
        if (p > 100.0d || p <= 0.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
        } else if (length == 0) {
            return Double.NaN;
        } else {
            if (length == 1) {
                return values[begin];
            }
            double[] work = getWorkArray(values, begin, length);
            int[] pivotsHeap = getPivots(values);
            if (work.length == 0) {
                return Double.NaN;
            }
            return this.estimationType.evaluate(work, pivotsHeap, p, this.kthSelector);
        }
    }

    public double getQuantile() {
        return this.quantile;
    }

    public void setQuantile(double p) throws MathIllegalArgumentException {
        if (p <= 0.0d || p > 100.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_QUANTILE_VALUE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
        }
        this.quantile = p;
    }

    public Percentile copy() {
        return new Percentile(this);
    }

    protected double[] getWorkArray(double[] values, int begin, int length) {
        if (values == getDataRef()) {
            return getDataRef();
        }
        switch ($SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy()[this.nanStrategy.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return replaceAndSlice(values, begin, length, Double.NaN, Double.NEGATIVE_INFINITY);
            case IExpr.DOUBLEID /*2*/:
                return replaceAndSlice(values, begin, length, Double.NaN, Double.POSITIVE_INFINITY);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return removeAndSlice(values, begin, length, Double.NaN);
            case ValueServer.CONSTANT_MODE /*5*/:
                double[] work = copyOf(values, begin, length);
                MathArrays.checkNotNaN(work);
                return work;
            default:
                return copyOf(values, begin, length);
        }
    }

    private static double[] copyOf(double[] values, int begin, int length) {
        MathArrays.verifyValues(values, begin, length);
        return MathArrays.copyOfRange(values, begin, begin + length);
    }

    private static double[] replaceAndSlice(double[] values, int begin, int length, double original, double replacement) {
        double[] temp = copyOf(values, begin, length);
        for (int i = 0; i < length; i++) {
            double d;
            if (Precision.equalsIncludingNaN(original, temp[i])) {
                d = replacement;
            } else {
                d = temp[i];
            }
            temp[i] = d;
        }
        return temp;
    }

    private static double[] removeAndSlice(double[] values, int begin, int length, double removedValue) {
        MathArrays.verifyValues(values, begin, length);
        BitSet bits = new BitSet(length);
        for (int i = begin; i < begin + length; i++) {
            if (Precision.equalsIncludingNaN(removedValue, values[i])) {
                bits.set(i - begin);
            }
        }
        if (bits.isEmpty()) {
            return copyOf(values, begin, length);
        }
        if (bits.cardinality() == length) {
            return new double[0];
        }
        double[] temp = new double[(length - bits.cardinality())];
        int start = begin;
        int dest = 0;
        int bitSetPtr = 0;
        while (true) {
            int nextOne = bits.nextSetBit(bitSetPtr);
            if (nextOne == -1) {
                break;
            }
            int lengthToCopy = nextOne - bitSetPtr;
            System.arraycopy(values, start, temp, dest, lengthToCopy);
            dest += lengthToCopy;
            bitSetPtr = bits.nextClearBit(nextOne);
            start = begin + bitSetPtr;
        }
        if (start >= begin + length) {
            return temp;
        }
        System.arraycopy(values, start, temp, dest, (begin + length) - start);
        return temp;
    }

    private int[] getPivots(double[] values) {
        if (values == getDataRef()) {
            return this.cachedPivots;
        }
        int[] pivotsHeap = new int[PIVOTS_HEAP_LENGTH];
        Arrays.fill(pivotsHeap, -1);
        return pivotsHeap;
    }

    public EstimationType getEstimationType() {
        return this.estimationType;
    }

    public Percentile withEstimationType(EstimationType newEstimationType) {
        return new Percentile(this.quantile, newEstimationType, this.nanStrategy, this.kthSelector);
    }

    public NaNStrategy getNaNStrategy() {
        return this.nanStrategy;
    }

    public Percentile withNaNStrategy(NaNStrategy newNaNStrategy) {
        return new Percentile(this.quantile, this.estimationType, newNaNStrategy, this.kthSelector);
    }

    public KthSelector getKthSelector() {
        return this.kthSelector;
    }

    public PivotingStrategyInterface getPivotingStrategy() {
        return this.kthSelector.getPivotingStrategy();
    }

    public Percentile withKthSelector(KthSelector newKthSelector) {
        return new Percentile(this.quantile, this.estimationType, this.nanStrategy, newKthSelector);
    }
}
