package org.apache.commons.math4.analysis.integration.gauss;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.Pair;

public class LegendreRuleFactory extends BaseRuleFactory<Double> {
    protected Pair<Double[], Double[]> computeRule(int numberOfPoints) throws DimensionMismatchException {
        if (numberOfPoints == 1) {
            return new Pair(new Double[]{Double.valueOf(0.0d)}, new Double[]{Double.valueOf(2.0d)});
        }
        Double[] previousPoints = (Double[]) getRuleInternal(numberOfPoints - 1).getFirst();
        Object points = new Double[numberOfPoints];
        Object weights = new Double[numberOfPoints];
        int iMax = numberOfPoints / 2;
        int i = 0;
        while (i < iMax) {
            int j;
            double a = i == 0 ? -1.0d : previousPoints[i - 1].doubleValue();
            double b = iMax == 1 ? 1.0d : previousPoints[i].doubleValue();
            double pma = 1.0d;
            double pa = a;
            double pmb = 1.0d;
            double pb = b;
            for (j = 1; j < numberOfPoints; j++) {
                int two_j_p_1 = (j * 2) + 1;
                int j_p_1 = j + 1;
                double ppa = (((((double) two_j_p_1) * a) * pa) - (((double) j) * pma)) / ((double) j_p_1);
                pma = pa;
                pa = ppa;
                pmb = pb;
                pb = (((((double) two_j_p_1) * b) * pb) - (((double) j) * pmb)) / ((double) j_p_1);
            }
            double c = 0.5d * (a + b);
            double pmc = 1.0d;
            double pc = c;
            boolean done = false;
            while (!done) {
                done = b - a <= Math.ulp(c);
                pmc = 1.0d;
                pc = c;
                for (j = 1; j < numberOfPoints; j++) {
                    pmc = pc;
                    pc = (((((double) ((j * 2) + 1)) * c) * pc) - (((double) j) * pmc)) / ((double) (j + 1));
                }
                if (!done) {
                    if (pa * pc <= 0.0d) {
                        b = c;
                        pmb = pmc;
                        pb = pc;
                    } else {
                        a = c;
                        pma = pmc;
                        pa = pc;
                    }
                    c = 0.5d * (a + b);
                }
            }
            double d = ((double) numberOfPoints) * (pmc - (c * pc));
            double w = (2.0d * (1.0d - (c * c))) / (d * d);
            points[i] = Double.valueOf(c);
            weights[i] = Double.valueOf(w);
            int idx = (numberOfPoints - i) - 1;
            points[idx] = Double.valueOf(-c);
            weights[idx] = Double.valueOf(w);
            i++;
        }
        if (numberOfPoints % 2 != 0) {
            pmc = 1.0d;
            for (j = 1; j < numberOfPoints; j += 2) {
                pmc = (((double) (-j)) * pmc) / ((double) (j + 1));
            }
            d = ((double) numberOfPoints) * pmc;
            w = 2.0d / (d * d);
            points[iMax] = Double.valueOf(0.0d);
            weights[iMax] = Double.valueOf(w);
        }
        return new Pair(points, weights);
    }
}
