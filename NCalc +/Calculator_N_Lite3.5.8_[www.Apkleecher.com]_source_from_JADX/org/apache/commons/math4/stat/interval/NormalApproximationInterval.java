package org.apache.commons.math4.stat.interval;

import org.apache.commons.math4.distribution.NormalDistribution;
import org.apache.commons.math4.util.FastMath;

public class NormalApproximationInterval implements BinomialConfidenceInterval {
    public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
        double mean = ((double) numberOfSuccesses) / ((double) numberOfTrials);
        double difference = new NormalDistribution().inverseCumulativeProbability(1.0d - ((1.0d - confidenceLevel) / 2.0d)) * FastMath.sqrt(((1.0d / ((double) numberOfTrials)) * mean) * (1.0d - mean));
        return new ConfidenceInterval(mean - difference, mean + difference, confidenceLevel);
    }
}
