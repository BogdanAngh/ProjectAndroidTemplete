package org.apache.commons.math4.stat.descriptive.summary;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class SumOfSquares extends AbstractStorelessUnivariateStatistic implements Serializable {
    private static final long serialVersionUID = 20150412;
    private long n;
    private double value;

    public SumOfSquares() {
        this.n = 0;
        this.value = 0.0d;
    }

    public SumOfSquares(SumOfSquares original) throws NullArgumentException {
        copy(original, this);
    }

    public void increment(double d) {
        this.value += d * d;
        this.n++;
    }

    public double getResult() {
        return this.value;
    }

    public long getN() {
        return this.n;
    }

    public void clear() {
        this.value = 0.0d;
        this.n = 0;
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        double sumSq = Double.NaN;
        if (MathArrays.verifyValues(values, begin, length, true)) {
            sumSq = 0.0d;
            for (int i = begin; i < begin + length; i++) {
                sumSq += values[i] * values[i];
            }
        }
        return sumSq;
    }

    public SumOfSquares copy() {
        SumOfSquares result = new SumOfSquares();
        copy(this, result);
        return result;
    }

    public static void copy(SumOfSquares source, SumOfSquares dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.n = source.n;
        dest.value = source.value;
    }
}
