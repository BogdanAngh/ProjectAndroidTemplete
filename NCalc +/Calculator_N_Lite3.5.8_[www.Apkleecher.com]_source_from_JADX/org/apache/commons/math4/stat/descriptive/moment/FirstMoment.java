package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.util.MathUtils;

class FirstMoment extends AbstractStorelessUnivariateStatistic implements Serializable {
    private static final long serialVersionUID = 20150412;
    protected double dev;
    protected double m1;
    protected long n;
    protected double nDev;

    public FirstMoment() {
        this.n = 0;
        this.m1 = Double.NaN;
        this.dev = Double.NaN;
        this.nDev = Double.NaN;
    }

    public FirstMoment(FirstMoment original) throws NullArgumentException {
        copy(original, this);
    }

    public void increment(double d) {
        if (this.n == 0) {
            this.m1 = 0.0d;
        }
        this.n++;
        double n0 = (double) this.n;
        this.dev = d - this.m1;
        this.nDev = this.dev / n0;
        this.m1 += this.nDev;
    }

    public void clear() {
        this.m1 = Double.NaN;
        this.n = 0;
        this.dev = Double.NaN;
        this.nDev = Double.NaN;
    }

    public double getResult() {
        return this.m1;
    }

    public long getN() {
        return this.n;
    }

    public FirstMoment copy() {
        FirstMoment result = new FirstMoment();
        copy(this, result);
        return result;
    }

    public static void copy(FirstMoment source, FirstMoment dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.n = source.n;
        dest.m1 = source.m1;
        dest.dev = source.dev;
        dest.nDev = source.nDev;
    }
}
