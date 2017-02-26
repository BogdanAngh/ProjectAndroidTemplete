package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.stat.descriptive.WeightedEvaluation;
import org.apache.commons.math4.stat.descriptive.summary.Sum;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Mean extends AbstractStorelessUnivariateStatistic implements Serializable, WeightedEvaluation {
    private static final long serialVersionUID = 20150412;
    protected boolean incMoment;
    protected FirstMoment moment;

    public Mean() {
        this.incMoment = true;
        this.moment = new FirstMoment();
    }

    public Mean(FirstMoment m1) {
        this.moment = m1;
        this.incMoment = false;
    }

    public Mean(Mean original) throws NullArgumentException {
        copy(original, this);
    }

    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    public double getResult() {
        return this.moment.m1;
    }

    public long getN() {
        return this.moment.getN();
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, begin, length)) {
            return Double.NaN;
        }
        double sampleSize = (double) length;
        double xbar = new Sum().evaluate(values, begin, length) / sampleSize;
        double correction = 0.0d;
        for (int i = begin; i < begin + length; i++) {
            correction += values[i] - xbar;
        }
        return (correction / sampleSize) + xbar;
    }

    public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, weights, begin, length)) {
            return Double.NaN;
        }
        Sum sum = new Sum();
        double sumw = sum.evaluate(weights, begin, length);
        double xbarw = sum.evaluate(values, weights, begin, length) / sumw;
        double correction = 0.0d;
        for (int i = begin; i < begin + length; i++) {
            correction += weights[i] * (values[i] - xbarw);
        }
        return (correction / sumw) + xbarw;
    }

    public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
        return evaluate(values, weights, 0, values.length);
    }

    public Mean copy() {
        Mean result = new Mean();
        copy(this, result);
        return result;
    }

    public static void copy(Mean source, Mean dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.incMoment = source.incMoment;
        dest.moment = source.moment.copy();
    }
}
