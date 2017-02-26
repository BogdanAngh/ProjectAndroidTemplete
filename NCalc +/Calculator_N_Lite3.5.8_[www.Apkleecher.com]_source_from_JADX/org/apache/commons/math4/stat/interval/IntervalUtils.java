package org.apache.commons.math4.stat.interval;

import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public final class IntervalUtils {
    private static final BinomialConfidenceInterval AGRESTI_COULL;
    private static final BinomialConfidenceInterval CLOPPER_PEARSON;
    private static final BinomialConfidenceInterval NORMAL_APPROXIMATION;
    private static final BinomialConfidenceInterval WILSON_SCORE;

    static {
        AGRESTI_COULL = new AgrestiCoullInterval();
        CLOPPER_PEARSON = new ClopperPearsonInterval();
        NORMAL_APPROXIMATION = new NormalApproximationInterval();
        WILSON_SCORE = new WilsonScoreInterval();
    }

    private IntervalUtils() {
    }

    public static ConfidenceInterval getAgrestiCoullInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        return AGRESTI_COULL.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
    }

    public static ConfidenceInterval getClopperPearsonInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        return CLOPPER_PEARSON.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
    }

    public static ConfidenceInterval getNormalApproximationInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        return NORMAL_APPROXIMATION.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
    }

    public static ConfidenceInterval getWilsonScoreInterval(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        return WILSON_SCORE.createInterval(numberOfTrials, numberOfSuccesses, confidenceLevel);
    }

    static void checkParameters(int numberOfTrials, int numberOfSuccesses, double confidenceLevel) {
        if (numberOfTrials <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_TRIALS, Integer.valueOf(numberOfTrials));
        } else if (numberOfSuccesses < 0) {
            throw new NotPositiveException(LocalizedFormats.NEGATIVE_NUMBER_OF_SUCCESSES, Integer.valueOf(numberOfSuccesses));
        } else if (numberOfSuccesses > numberOfTrials) {
            throw new NumberIsTooLargeException(LocalizedFormats.NUMBER_OF_SUCCESS_LARGER_THAN_POPULATION_SIZE, Integer.valueOf(numberOfSuccesses), Integer.valueOf(numberOfTrials), true);
        } else if (confidenceLevel <= 0.0d || confidenceLevel >= 1.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUNDS_CONFIDENCE_LEVEL, Double.valueOf(confidenceLevel), Integer.valueOf(0), Integer.valueOf(1));
        }
    }
}
