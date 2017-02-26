package org.apache.commons.math4.stat.inference;

import org.apache.commons.math4.distribution.BinomialDistribution;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public class BinomialTest {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$inference$AlternativeHypothesis;

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$stat$inference$AlternativeHypothesis() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$stat$inference$AlternativeHypothesis;
        if (iArr == null) {
            iArr = new int[AlternativeHypothesis.values().length];
            try {
                iArr[AlternativeHypothesis.GREATER_THAN.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AlternativeHypothesis.LESS_THAN.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[AlternativeHypothesis.TWO_SIDED.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$stat$inference$AlternativeHypothesis = iArr;
        }
        return iArr;
    }

    public boolean binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis, double alpha) {
        return binomialTest(numberOfTrials, numberOfSuccesses, probability, alternativeHypothesis) < alpha;
    }

    public double binomialTest(int numberOfTrials, int numberOfSuccesses, double probability, AlternativeHypothesis alternativeHypothesis) {
        if (numberOfTrials < 0) {
            throw new NotPositiveException(Integer.valueOf(numberOfTrials));
        } else if (numberOfSuccesses < 0) {
            throw new NotPositiveException(Integer.valueOf(numberOfSuccesses));
        } else if (probability < 0.0d || probability > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(probability), Integer.valueOf(0), Integer.valueOf(1));
        } else if (numberOfTrials < numberOfSuccesses) {
            throw new MathIllegalArgumentException(LocalizedFormats.BINOMIAL_INVALID_PARAMETERS_ORDER, Integer.valueOf(numberOfTrials), Integer.valueOf(numberOfSuccesses));
        } else if (alternativeHypothesis == null) {
            throw new NullArgumentException();
        } else {
            BinomialDistribution distribution = new BinomialDistribution(null, numberOfTrials, probability);
            switch ($SWITCH_TABLE$org$apache$commons$math4$stat$inference$AlternativeHypothesis()[alternativeHypothesis.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    int criticalValueLow = 0;
                    int criticalValueHigh = numberOfTrials;
                    double pTotal = 0.0d;
                    do {
                        double pLow = distribution.probability(criticalValueLow);
                        double pHigh = distribution.probability(criticalValueHigh);
                        if (pLow == pHigh) {
                            pTotal += 2.0d * pLow;
                            criticalValueLow++;
                            criticalValueHigh--;
                        } else if (pLow < pHigh) {
                            pTotal += pLow;
                            criticalValueLow++;
                        } else {
                            pTotal += pHigh;
                            criticalValueHigh--;
                        }
                        if (criticalValueLow > numberOfSuccesses) {
                            return pTotal;
                        }
                    } while (criticalValueHigh >= numberOfSuccesses);
                    return pTotal;
                case IExpr.DOUBLEID /*2*/:
                    return 1.0d - distribution.cumulativeProbability(numberOfSuccesses - 1);
                case ValueServer.EXPONENTIAL_MODE /*3*/:
                    return distribution.cumulativeProbability(numberOfSuccesses);
                default:
                    throw new MathInternalError(LocalizedFormats.OUT_OF_RANGE_SIMPLE, alternativeHypothesis, AlternativeHypothesis.TWO_SIDED, AlternativeHypothesis.LESS_THAN);
            }
        }
    }
}
