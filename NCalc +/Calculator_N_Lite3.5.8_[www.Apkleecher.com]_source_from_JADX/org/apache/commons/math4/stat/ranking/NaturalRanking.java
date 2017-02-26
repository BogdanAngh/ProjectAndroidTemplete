package org.apache.commons.math4.stat.ranking;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NotANumberException;
import org.apache.commons.math4.random.RandomDataGenerator;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public class NaturalRanking implements RankingAlgorithm {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$TiesStrategy;
    public static final NaNStrategy DEFAULT_NAN_STRATEGY;
    public static final TiesStrategy DEFAULT_TIES_STRATEGY;
    private final NaNStrategy nanStrategy;
    private final RandomDataGenerator randomData;
    private final TiesStrategy tiesStrategy;

    private static class IntDoublePair implements Comparable<IntDoublePair> {
        private final int position;
        private final double value;

        public IntDoublePair(double value, int position) {
            this.value = value;
            this.position = position;
        }

        public int compareTo(IntDoublePair other) {
            return Double.compare(this.value, other.value);
        }

        public double getValue() {
            return this.value;
        }

        public int getPosition() {
            return this.position;
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

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$TiesStrategy() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$TiesStrategy;
        if (iArr == null) {
            iArr = new int[TiesStrategy.values().length];
            try {
                iArr[TiesStrategy.AVERAGE.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[TiesStrategy.MAXIMUM.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[TiesStrategy.MINIMUM.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[TiesStrategy.RANDOM.ordinal()] = 5;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[TiesStrategy.SEQUENTIAL.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$stat$ranking$TiesStrategy = iArr;
        }
        return iArr;
    }

    static {
        DEFAULT_NAN_STRATEGY = NaNStrategy.FAILED;
        DEFAULT_TIES_STRATEGY = TiesStrategy.AVERAGE;
    }

    public NaturalRanking() {
        this.tiesStrategy = DEFAULT_TIES_STRATEGY;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = null;
    }

    public NaturalRanking(TiesStrategy tiesStrategy) {
        this.tiesStrategy = tiesStrategy;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = new RandomDataGenerator();
    }

    public NaturalRanking(NaNStrategy nanStrategy) {
        this.nanStrategy = nanStrategy;
        this.tiesStrategy = DEFAULT_TIES_STRATEGY;
        this.randomData = null;
    }

    public NaturalRanking(NaNStrategy nanStrategy, TiesStrategy tiesStrategy) {
        this.nanStrategy = nanStrategy;
        this.tiesStrategy = tiesStrategy;
        this.randomData = new RandomDataGenerator();
    }

    public NaturalRanking(RandomGenerator randomGenerator) {
        this.tiesStrategy = TiesStrategy.RANDOM;
        this.nanStrategy = DEFAULT_NAN_STRATEGY;
        this.randomData = new RandomDataGenerator(randomGenerator);
    }

    public NaturalRanking(NaNStrategy nanStrategy, RandomGenerator randomGenerator) {
        this.nanStrategy = nanStrategy;
        this.tiesStrategy = TiesStrategy.RANDOM;
        this.randomData = new RandomDataGenerator(randomGenerator);
    }

    public NaNStrategy getNanStrategy() {
        return this.nanStrategy;
    }

    public TiesStrategy getTiesStrategy() {
        return this.tiesStrategy;
    }

    public double[] rank(double[] data) {
        int i;
        IntDoublePair[] ranks = new IntDoublePair[data.length];
        for (i = 0; i < data.length; i++) {
            ranks[i] = new IntDoublePair(data[i], i);
        }
        List<Integer> nanPositions = null;
        switch ($SWITCH_TABLE$org$apache$commons$math4$stat$ranking$NaNStrategy()[this.nanStrategy.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                recodeNaNs(ranks, Double.NEGATIVE_INFINITY);
                break;
            case IExpr.DOUBLEID /*2*/:
                recodeNaNs(ranks, Double.POSITIVE_INFINITY);
                break;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                ranks = removeNaNs(ranks);
                break;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                nanPositions = getNanPositions(ranks);
                break;
            case ValueServer.CONSTANT_MODE /*5*/:
                nanPositions = getNanPositions(ranks);
                if (nanPositions.size() > 0) {
                    throw new NotANumberException();
                }
                break;
            default:
                throw new MathInternalError();
        }
        Arrays.sort(ranks);
        double[] out = new double[ranks.length];
        int pos = 1;
        out[ranks[0].getPosition()] = (double) 1;
        List<Integer> tiesTrace = new ArrayList();
        tiesTrace.add(Integer.valueOf(ranks[0].getPosition()));
        for (i = 1; i < ranks.length; i++) {
            if (Double.compare(ranks[i].getValue(), ranks[i - 1].getValue()) > 0) {
                pos = i + 1;
                if (tiesTrace.size() > 1) {
                    resolveTie(out, tiesTrace);
                }
                tiesTrace = new ArrayList();
                tiesTrace.add(Integer.valueOf(ranks[i].getPosition()));
            } else {
                tiesTrace.add(Integer.valueOf(ranks[i].getPosition()));
            }
            out[ranks[i].getPosition()] = (double) pos;
        }
        if (tiesTrace.size() > 1) {
            resolveTie(out, tiesTrace);
        }
        if (this.nanStrategy == NaNStrategy.FIXED) {
            restoreNaNs(out, nanPositions);
        }
        return out;
    }

    private IntDoublePair[] removeNaNs(IntDoublePair[] ranks) {
        if (!containsNaNs(ranks)) {
            return ranks;
        }
        IntDoublePair[] outRanks = new IntDoublePair[ranks.length];
        int j = 0;
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                for (int k = i + 1; k < ranks.length; k++) {
                    ranks[k] = new IntDoublePair(ranks[k].getValue(), ranks[k].getPosition() - 1);
                }
            } else {
                outRanks[j] = new IntDoublePair(ranks[i].getValue(), ranks[i].getPosition());
                j++;
            }
        }
        IntDoublePair[] returnRanks = new IntDoublePair[j];
        System.arraycopy(outRanks, 0, returnRanks, 0, j);
        return returnRanks;
    }

    private void recodeNaNs(IntDoublePair[] ranks, double value) {
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                ranks[i] = new IntDoublePair(value, ranks[i].getPosition());
            }
        }
    }

    private boolean containsNaNs(IntDoublePair[] ranks) {
        for (IntDoublePair value : ranks) {
            if (Double.isNaN(value.getValue())) {
                return true;
            }
        }
        return false;
    }

    private void resolveTie(double[] ranks, List<Integer> tiesTrace) {
        double c = ranks[((Integer) tiesTrace.get(0)).intValue()];
        int length = tiesTrace.size();
        long f;
        switch ($SWITCH_TABLE$org$apache$commons$math4$stat$ranking$TiesStrategy()[this.tiesStrategy.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                f = FastMath.round(c);
                int i = 0;
                for (Integer intValue : tiesTrace) {
                    int i2 = i + 1;
                    ranks[intValue.intValue()] = (double) (((long) i) + f);
                    i = i2;
                }
            case IExpr.DOUBLEID /*2*/:
                fill(ranks, tiesTrace, c);
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                fill(ranks, tiesTrace, (((double) length) + c) - 1.0d);
            case IExpr.DOUBLECOMPLEXID /*4*/:
                fill(ranks, tiesTrace, (((2.0d * c) + ((double) length)) - 1.0d) / 2.0d);
            case ValueServer.CONSTANT_MODE /*5*/:
                f = FastMath.round(c);
                for (Integer intValue2 : tiesTrace) {
                    ranks[intValue2.intValue()] = (double) this.randomData.nextLong(f, (((long) length) + f) - 1);
                }
            default:
                throw new MathInternalError();
        }
    }

    private void fill(double[] data, List<Integer> tiesTrace, double value) {
        for (Integer intValue : tiesTrace) {
            data[intValue.intValue()] = value;
        }
    }

    private void restoreNaNs(double[] ranks, List<Integer> nanPositions) {
        if (nanPositions.size() != 0) {
            for (Integer intValue : nanPositions) {
                ranks[intValue.intValue()] = Double.NaN;
            }
        }
    }

    private List<Integer> getNanPositions(IntDoublePair[] ranks) {
        ArrayList<Integer> out = new ArrayList();
        for (int i = 0; i < ranks.length; i++) {
            if (Double.isNaN(ranks[i].getValue())) {
                out.add(Integer.valueOf(i));
            }
        }
        return out;
    }
}
