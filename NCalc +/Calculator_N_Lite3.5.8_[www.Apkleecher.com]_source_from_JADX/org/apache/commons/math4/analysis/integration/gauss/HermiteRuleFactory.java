package org.apache.commons.math4.analysis.integration.gauss;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Pair;

public class HermiteRuleFactory extends BaseRuleFactory<Double> {
    private static final double H0 = 0.7511255444649425d;
    private static final double H1 = 1.0622519320271968d;
    private static final double SQRT_PI = 1.772453850905516d;

    protected Pair<Double[], Double[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
        if (numberOfPoints == 1) {
            return new Pair(new Double[]{Double.valueOf(0.0d)}, new Double[]{Double.valueOf(SQRT_PI)});
        }
        int lastNumPoints = numberOfPoints - 1;
        Double[] previousPoints = (Double[]) getRuleInternal(lastNumPoints).getFirst();
        Object points = new Double[numberOfPoints];
        Object weights = new Double[numberOfPoints];
        double sqrtTwoTimesLastNumPoints = FastMath.sqrt((double) (lastNumPoints * 2));
        double sqrtTwoTimesNumPoints = FastMath.sqrt((double) (numberOfPoints * 2));
        int iMax = numberOfPoints / 2;
        int i = 0;
        while (i < iMax) {
            double b;
            int j;
            double a = i == 0 ? -sqrtTwoTimesLastNumPoints : previousPoints[i - 1].doubleValue();
            if (iMax == 1) {
                b = -0.5d;
            } else {
                b = previousPoints[i].doubleValue();
            }
            double hma = H0;
            double ha = H1 * a;
            double hmb = H0;
            double hb = H1 * b;
            for (j = 1; j < numberOfPoints; j++) {
                double jp1 = (double) (j + 1);
                double s = FastMath.sqrt(2.0d / jp1);
                double sm = FastMath.sqrt(((double) j) / jp1);
                double hpa = ((s * a) * ha) - (sm * hma);
                hma = ha;
                ha = hpa;
                hmb = hb;
                hb = ((s * b) * hb) - (sm * hmb);
            }
            double c = 0.5d * (a + b);
            double hmc = H0;
            double hc = H1 * c;
            boolean done = false;
            while (!done) {
                done = b - a <= Math.ulp(c);
                hmc = H0;
                hc = H1 * c;
                for (j = 1; j < numberOfPoints; j++) {
                    jp1 = (double) (j + 1);
                    hmc = hc;
                    hc = ((FastMath.sqrt(2.0d / jp1) * c) * hc) - (FastMath.sqrt(((double) j) / jp1) * hmc);
                }
                if (!done) {
                    if (ha * hc < 0.0d) {
                        b = c;
                        hmb = hmc;
                        hb = hc;
                    } else {
                        a = c;
                        hma = hmc;
                        ha = hc;
                    }
                    c = 0.5d * (a + b);
                }
            }
            double d = sqrtTwoTimesNumPoints * hmc;
            double w = 2.0d / (d * d);
            points[i] = Double.valueOf(c);
            weights[i] = Double.valueOf(w);
            int idx = lastNumPoints - i;
            points[idx] = Double.valueOf(-c);
            weights[idx] = Double.valueOf(w);
            i++;
        }
        if (numberOfPoints % 2 != 0) {
            double hm = H0;
            for (j = 1; j < numberOfPoints; j += 2) {
                hm *= -FastMath.sqrt(((double) j) / ((double) (j + 1)));
            }
            d = sqrtTwoTimesNumPoints * hm;
            w = 2.0d / (d * d);
            points[iMax] = Double.valueOf(0.0d);
            weights[iMax] = Double.valueOf(w);
        }
        return new Pair(points, weights);
    }
}
