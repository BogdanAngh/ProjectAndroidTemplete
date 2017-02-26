package org.apache.commons.math4.analysis.integration.gauss;

import java.util.Map;
import java.util.TreeMap;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.Pair;

public abstract class BaseRuleFactory<T extends Number> {
    private final Map<Integer, Pair<T[], T[]>> pointsAndWeights;
    private final Map<Integer, Pair<double[], double[]>> pointsAndWeightsDouble;

    protected abstract Pair<T[], T[]> computeRule(int i) throws DimensionMismatchException;

    public BaseRuleFactory() {
        this.pointsAndWeights = new TreeMap();
        this.pointsAndWeightsDouble = new TreeMap();
    }

    public Pair<double[], double[]> getRule(int numberOfPoints) throws NotStrictlyPositiveException, DimensionMismatchException {
        if (numberOfPoints <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(numberOfPoints));
        }
        Pair<double[], double[]> cached = (Pair) this.pointsAndWeightsDouble.get(Integer.valueOf(numberOfPoints));
        if (cached == null) {
            cached = convertToDouble(getRuleInternal(numberOfPoints));
            this.pointsAndWeightsDouble.put(Integer.valueOf(numberOfPoints), cached);
        }
        return new Pair((double[]) ((double[]) cached.getFirst()).clone(), (double[]) ((double[]) cached.getSecond()).clone());
    }

    protected synchronized Pair<T[], T[]> getRuleInternal(int numberOfPoints) throws DimensionMismatchException {
        Pair<T[], T[]> rule;
        rule = (Pair) this.pointsAndWeights.get(Integer.valueOf(numberOfPoints));
        if (rule == null) {
            addRule(computeRule(numberOfPoints));
            rule = getRuleInternal(numberOfPoints);
        }
        return rule;
    }

    protected void addRule(Pair<T[], T[]> rule) throws DimensionMismatchException {
        if (((Number[]) rule.getFirst()).length != ((Number[]) rule.getSecond()).length) {
            throw new DimensionMismatchException(((Number[]) rule.getFirst()).length, ((Number[]) rule.getSecond()).length);
        }
        this.pointsAndWeights.put(Integer.valueOf(((Number[]) rule.getFirst()).length), rule);
    }

    private static <T extends Number> Pair<double[], double[]> convertToDouble(Pair<T[], T[]> rule) {
        Number[] pT = (Number[]) rule.getFirst();
        Number[] wT = (Number[]) rule.getSecond();
        int len = pT.length;
        double[] pD = new double[len];
        double[] wD = new double[len];
        for (int i = 0; i < len; i++) {
            pD[i] = pT[i].doubleValue();
            wD[i] = wT[i].doubleValue();
        }
        return new Pair(pD, wD);
    }
}
