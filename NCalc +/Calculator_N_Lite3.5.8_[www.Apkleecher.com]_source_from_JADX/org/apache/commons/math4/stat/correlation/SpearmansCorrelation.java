package org.apache.commons.math4.stat.correlation;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.BlockRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.stat.ranking.NaNStrategy;
import org.apache.commons.math4.stat.ranking.NaturalRanking;
import org.apache.commons.math4.stat.ranking.RankingAlgorithm;

public class SpearmansCorrelation {
    private final RealMatrix data;
    private final PearsonsCorrelation rankCorrelation;
    private final RankingAlgorithm rankingAlgorithm;

    public SpearmansCorrelation() {
        this(new NaturalRanking());
    }

    public SpearmansCorrelation(RankingAlgorithm rankingAlgorithm) throws MathIllegalArgumentException {
        if ((rankingAlgorithm instanceof NaturalRanking) && NaNStrategy.REMOVED == ((NaturalRanking) rankingAlgorithm).getNanStrategy()) {
            throw new MathIllegalArgumentException(LocalizedFormats.NOT_SUPPORTED_NAN_STRATEGY, NaNStrategy.REMOVED);
        }
        this.data = null;
        this.rankingAlgorithm = rankingAlgorithm;
        this.rankCorrelation = null;
    }

    public SpearmansCorrelation(RealMatrix dataMatrix) {
        this(dataMatrix, new NaturalRanking());
    }

    public SpearmansCorrelation(RealMatrix dataMatrix, RankingAlgorithm rankingAlgorithm) throws MathIllegalArgumentException {
        if ((rankingAlgorithm instanceof NaturalRanking) && NaNStrategy.REMOVED == ((NaturalRanking) rankingAlgorithm).getNanStrategy()) {
            throw new MathIllegalArgumentException(LocalizedFormats.NOT_SUPPORTED_NAN_STRATEGY, NaNStrategy.REMOVED);
        }
        this.rankingAlgorithm = rankingAlgorithm;
        this.data = rankTransform(dataMatrix);
        this.rankCorrelation = new PearsonsCorrelation(this.data);
    }

    public RealMatrix getCorrelationMatrix() {
        return this.rankCorrelation.getCorrelationMatrix();
    }

    public PearsonsCorrelation getRankCorrelation() {
        return this.rankCorrelation;
    }

    public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
        return new PearsonsCorrelation().computeCorrelationMatrix(rankTransform(matrix));
    }

    public RealMatrix computeCorrelationMatrix(double[][] matrix) {
        return computeCorrelationMatrix(new BlockRealMatrix(matrix));
    }

    public double correlation(double[] xArray, double[] yArray) {
        if (xArray.length != yArray.length) {
            throw new DimensionMismatchException(xArray.length, yArray.length);
        } else if (xArray.length >= 2) {
            return new PearsonsCorrelation().correlation(this.rankingAlgorithm.rank(xArray), this.rankingAlgorithm.rank(yArray));
        } else {
            throw new MathIllegalArgumentException(LocalizedFormats.INSUFFICIENT_DIMENSION, Integer.valueOf(xArray.length), Integer.valueOf(2));
        }
    }

    private RealMatrix rankTransform(RealMatrix matrix) {
        RealMatrix transformed = matrix.copy();
        for (int i = 0; i < transformed.getColumnDimension(); i++) {
            transformed.setColumn(i, this.rankingAlgorithm.rank(transformed.getColumn(i)));
        }
        return transformed;
    }
}
