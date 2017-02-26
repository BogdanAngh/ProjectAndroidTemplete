package org.apache.commons.math4.stat.interval;

import org.apache.commons.math4.distribution.NormalDistribution;
import org.apache.commons.math4.util.FastMath;

public class AgrestiCoullInterval implements BinomialConfidenceInterval {
    public ConfidenceInterval createInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        IntervalUtils.checkParameters(numberOfTrials, numberOfSuccesses, confidenceLevel);
        double z = new NormalDistribution().inverseCumulativeProbability(1.0d - ((1.0d - confidenceLevel) / 2.0d));
        double zSquared = FastMath.pow(z, 2);
        double modifiedNumberOfTrials = ((double) numberOfTrials) + zSquared;
        double modifiedSuccessesRatio = (1.0d / modifiedNumberOfTrials) * (((double) numberOfSuccesses) + (0.5d * zSquared));
        double difference = z * FastMath.sqrt(((1.0d / modifiedNumberOfTrials) * modifiedSuccessesRatio) * (1.0d - modifiedSuccessesRatio));
        return new ConfidenceInterval(modifiedSuccessesRatio - difference, modifiedSuccessesRatio + difference, confidenceLevel);
    }
}
