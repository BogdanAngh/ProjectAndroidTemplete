package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;

public interface IntegerDistribution {
    double cumulativeProbability(int i);

    double getNumericalMean();

    double getNumericalVariance();

    int getSupportLowerBound();

    int getSupportUpperBound();

    int inverseCumulativeProbability(double d) throws OutOfRangeException;

    boolean isSupportConnected();

    double logProbability(int i);

    double probability(int i);

    double probability(int i, int i2) throws NumberIsTooLargeException;

    void reseedRandomGenerator(long j);

    int sample();

    int[] sample(int i);
}
