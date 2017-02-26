package org.apache.commons.math4.stat.descriptive;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public abstract class AbstractStorelessUnivariateStatistic implements StorelessUnivariateStatistic {
    public abstract void clear();

    public abstract StorelessUnivariateStatistic copy();

    public abstract double getResult();

    public abstract void increment(double d);

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
        StorelessUnivariateStatistic stat = copy();
        stat.clear();
        stat.incrementAll(values, begin, length);
        return stat.getResult();
    }

    public void incrementAll(double[] values) throws MathIllegalArgumentException {
        if (values == null) {
            throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
        }
        incrementAll(values, 0, values.length);
    }

    public void incrementAll(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (MathArrays.verifyValues(values, begin, length)) {
            int k = begin + length;
            for (int i = begin; i < k; i++) {
                increment(values[i]);
            }
        }
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || object.getClass() != getClass()) {
            return false;
        }
        StorelessUnivariateStatistic stat = (StorelessUnivariateStatistic) object;
        if (Precision.equalsIncludingNaN(stat.getResult(), getResult()) && Precision.equalsIncludingNaN((float) stat.getN(), (float) getN())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((MathUtils.hash(getResult()) + 31) * 31) + MathUtils.hash((double) getN());
    }
}
