package org.apache.commons.math4.distribution;

import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;

public interface RealDistribution {
    double cumulativeProbability(double d);

    double density(double d);

    double getNumericalMean();

    double getNumericalVariance();

    double getSupportLowerBound();

    double getSupportUpperBound();

    double inverseCumulativeProbability(double d) throws OutOfRangeException;

    boolean isSupportConnected();

    double logDensity(double d);

    double probability(double d);

    double probability(double d, double d2) throws NumberIsTooLargeException;

    void reseedRandomGenerator(long j);

    double sample();

    double[] sample(int i);
}
