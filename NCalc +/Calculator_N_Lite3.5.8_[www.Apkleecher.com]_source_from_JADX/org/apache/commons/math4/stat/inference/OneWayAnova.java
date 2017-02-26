package org.apache.commons.math4.stat.inference;

import java.util.ArrayList;
import java.util.Collection;
import org.apache.commons.math4.distribution.FDistribution;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.stat.descriptive.SummaryStatistics;
import org.apache.commons.math4.util.MathUtils;

public class OneWayAnova {

    private static class AnovaStats {
        private final double F;
        private final int dfbg;
        private final int dfwg;

        private AnovaStats(int dfbg, int dfwg, double F) {
            this.dfbg = dfbg;
            this.dfwg = dfwg;
            this.F = F;
        }
    }

    public double anovaFValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
        return anovaStats(categoryData).F;
    }

    public double anovaPValue(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
        AnovaStats a = anovaStats(categoryData);
        return 1.0d - new FDistribution(null, (double) a.dfbg, (double) a.dfwg).cumulativeProbability(a.F);
    }

    public double anovaPValue(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException, ConvergenceException, MaxCountExceededException {
        AnovaStats a = anovaStats(categoryData, allowOneElementData);
        return 1.0d - new FDistribution(null, (double) a.dfbg, (double) a.dfwg).cumulativeProbability(a.F);
    }

    private AnovaStats anovaStats(Collection<double[]> categoryData) throws NullArgumentException, DimensionMismatchException {
        MathUtils.checkNotNull(categoryData);
        Collection<SummaryStatistics> categoryDataSummaryStatistics = new ArrayList(categoryData.size());
        for (double[] data : categoryData) {
            SummaryStatistics dataSummaryStatistics = new SummaryStatistics();
            categoryDataSummaryStatistics.add(dataSummaryStatistics);
            for (double val : data) {
                dataSummaryStatistics.addValue(val);
            }
        }
        return anovaStats(categoryDataSummaryStatistics, false);
    }

    public boolean anovaTest(Collection<double[]> categoryData, double alpha) throws NullArgumentException, DimensionMismatchException, OutOfRangeException, ConvergenceException, MaxCountExceededException {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (anovaPValue(categoryData) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    private AnovaStats anovaStats(Collection<SummaryStatistics> categoryData, boolean allowOneElementData) throws NullArgumentException, DimensionMismatchException {
        MathUtils.checkNotNull(categoryData);
        if (!allowOneElementData) {
            if (categoryData.size() < 2) {
                throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_CATEGORIES_REQUIRED, categoryData.size(), 2);
            }
            for (SummaryStatistics array : categoryData) {
                if (array.getN() <= 1) {
                    throw new DimensionMismatchException(LocalizedFormats.TWO_OR_MORE_VALUES_IN_CATEGORY_REQUIRED, (int) array.getN(), 2);
                }
            }
        }
        int dfwg = 0;
        double sswg = 0.0d;
        double totsum = 0.0d;
        double totsumsq = 0.0d;
        int totnum = 0;
        for (SummaryStatistics data : categoryData) {
            double sum = data.getSum();
            double sumsq = data.getSumsq();
            int num = (int) data.getN();
            totnum += num;
            totsum += sum;
            totsumsq += sumsq;
            dfwg += num - 1;
            sswg += sumsq - ((sum * sum) / ((double) num));
        }
        int dfbg = categoryData.size() - 1;
        return new AnovaStats(dfwg, (((totsumsq - ((totsum * totsum) / ((double) totnum))) - sswg) / ((double) dfbg)) / (sswg / ((double) dfwg)), null);
    }
}
