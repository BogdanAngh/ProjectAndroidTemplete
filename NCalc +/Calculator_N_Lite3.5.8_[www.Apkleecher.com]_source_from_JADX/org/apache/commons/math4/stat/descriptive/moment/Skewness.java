package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Skewness extends AbstractStorelessUnivariateStatistic implements Serializable {
    private static final long serialVersionUID = 20150412;
    protected boolean incMoment;
    protected ThirdMoment moment;

    public Skewness() {
        this.moment = null;
        this.incMoment = true;
        this.moment = new ThirdMoment();
    }

    public Skewness(ThirdMoment m3) {
        this.moment = null;
        this.incMoment = false;
        this.moment = m3;
    }

    public Skewness(Skewness original) throws NullArgumentException {
        this.moment = null;
        copy(original, this);
    }

    public void increment(double d) {
        if (this.incMoment) {
            this.moment.increment(d);
        }
    }

    public double getResult() {
        if (this.moment.n < 3) {
            return Double.NaN;
        }
        double variance = this.moment.m2 / ((double) (this.moment.n - 1));
        if (variance < 1.0E-19d) {
            return 0.0d;
        }
        double n0 = (double) this.moment.getN();
        return (this.moment.m3 * n0) / ((((n0 - 1.0d) * (n0 - 2.0d)) * FastMath.sqrt(variance)) * variance);
    }

    public long getN() {
        return this.moment.getN();
    }

    public void clear() {
        if (this.incMoment) {
            this.moment.clear();
        }
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (!MathArrays.verifyValues(values, begin, length) || length <= 2) {
            return Double.NaN;
        }
        int i;
        double m = new Mean().evaluate(values, begin, length);
        double accum = 0.0d;
        double accum2 = 0.0d;
        for (i = begin; i < begin + length; i++) {
            double d = values[i] - m;
            accum += d * d;
            accum2 += d;
        }
        double d2 = (double) (length - 1);
        double variance = (accum - ((accum2 * accum2) / ((double) length))) / r0;
        double accum3 = 0.0d;
        for (i = begin; i < begin + length; i++) {
            d = values[i] - m;
            accum3 += (d * d) * d;
        }
        double n0 = (double) length;
        return (n0 / ((n0 - 1.0d) * (n0 - 2.0d))) * (accum3 / (FastMath.sqrt(variance) * variance));
    }

    public Skewness copy() {
        Skewness result = new Skewness();
        copy(this, result);
        return result;
    }

    public static void copy(Skewness source, Skewness dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.moment = new ThirdMoment(source.moment.copy());
        dest.incMoment = source.incMoment;
    }
}
