package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.stat.descriptive.WeightedEvaluation;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Variance extends AbstractStorelessUnivariateStatistic implements Serializable, WeightedEvaluation {
    private static final long serialVersionUID = 20150412;
    protected boolean incMoment;
    private boolean isBiasCorrected;
    protected SecondMoment moment;

    public Variance() {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new SecondMoment();
    }

    public Variance(SecondMoment m2) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = m2;
    }

    public Variance(boolean isBiasCorrected) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.moment = new SecondMoment();
        this.isBiasCorrected = isBiasCorrected;
    }

    public Variance(boolean isBiasCorrected, SecondMoment m2) {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        this.incMoment = false;
        this.moment = m2;
        this.isBiasCorrected = isBiasCorrected;
    }

    public Variance(Variance original) throws NullArgumentException {
        this.moment = null;
        this.incMoment = true;
        this.isBiasCorrected = true;
        copy(original, this);
    }

    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    public double getResult() {
        if (this.moment.n == 0) {
            return Double.NaN;
        }
        if (this.moment.n == 1) {
            return 0.0d;
        }
        if (this.isBiasCorrected) {
            return this.moment.m2 / (((double) this.moment.n) - 1.0d);
        }
        return this.moment.m2 / ((double) this.moment.n);
    }

    public long getN() {
        return this.moment.getN();
    }

    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    public double evaluate(double[] values) throws MathIllegalArgumentException {
        if (values != null) {
            return evaluate(values, 0, values.length);
        }
        throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, begin, length)) {
            return Double.NaN;
        }
        if (length == 1) {
            return 0.0d;
        }
        if (length <= 1) {
            return Double.NaN;
        }
        return evaluate(values, new Mean().evaluate(values, begin, length), begin, length);
    }

    public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, weights, begin, length)) {
            return Double.NaN;
        }
        if (length == 1) {
            return 0.0d;
        }
        if (length <= 1) {
            return Double.NaN;
        }
        return evaluate(values, weights, new Mean().evaluate(values, weights, begin, length), begin, length);
    }

    public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
        return evaluate(values, weights, 0, values.length);
    }

    public double evaluate(double[] values, double mean, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, begin, length)) {
            return Double.NaN;
        }
        if (length == 1) {
            return 0.0d;
        }
        if (length <= 1) {
            return Double.NaN;
        }
        double accum = 0.0d;
        double accum2 = 0.0d;
        for (int i = begin; i < begin + length; i++) {
            double dev = values[i] - mean;
            accum += dev * dev;
            accum2 += dev;
        }
        double len = (double) length;
        if (this.isBiasCorrected) {
            return (accum - ((accum2 * accum2) / len)) / (len - 1.0d);
        }
        return (accum - ((accum2 * accum2) / len)) / len;
    }

    public double evaluate(double[] values, double mean) throws MathIllegalArgumentException {
        return evaluate(values, mean, 0, values.length);
    }

    public double evaluate(double[] values, double[] weights, double mean, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, weights, begin, length)) {
            return Double.NaN;
        }
        if (length == 1) {
            return 0.0d;
        }
        if (length <= 1) {
            return Double.NaN;
        }
        int i;
        double accum = 0.0d;
        double accum2 = 0.0d;
        for (i = begin; i < begin + length; i++) {
            double dev = values[i] - mean;
            accum += weights[i] * (dev * dev);
            accum2 += weights[i] * dev;
        }
        double sumWts = 0.0d;
        for (i = begin; i < begin + length; i++) {
            sumWts += weights[i];
        }
        if (this.isBiasCorrected) {
            return (accum - ((accum2 * accum2) / sumWts)) / (sumWts - 1.0d);
        }
        return (accum - ((accum2 * accum2) / sumWts)) / sumWts;
    }

    public double evaluate(double[] values, double[] weights, double mean) throws MathIllegalArgumentException {
        return evaluate(values, weights, mean, 0, values.length);
    }

    public boolean isBiasCorrected() {
        return this.isBiasCorrected;
    }

    public void setBiasCorrected(boolean biasCorrected) {
        this.isBiasCorrected = biasCorrected;
    }

    public Variance copy() {
        Variance result = new Variance();
        copy(this, result);
        return result;
    }

    public static void copy(Variance source, Variance dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.moment = source.moment.copy();
        dest.isBiasCorrected = source.isBiasCorrected;
        dest.incMoment = source.incMoment;
    }
}
