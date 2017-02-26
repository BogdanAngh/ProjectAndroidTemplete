package org.apache.commons.math4.stat.descriptive;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;

public abstract class AbstractUnivariateStatistic implements UnivariateStatistic {
    private double[] storedData;

    public abstract UnivariateStatistic copy();

    public abstract double evaluate(double[] dArr, int i, int i2) throws MathIllegalArgumentException;

    public double evaluate(double[] values) throws MathIllegalArgumentException {
        MathArrays.verifyValues(values, 0, 0);
        return evaluate(values, 0, values.length);
    }

    public void setData(double[] values) {
        this.storedData = values == null ? null : (double[]) values.clone();
    }

    public double[] getData() {
        return this.storedData == null ? null : (double[]) this.storedData.clone();
    }

    protected double[] getDataRef() {
        return this.storedData;
    }

    public void setData(double[] values, int begin, int length) throws MathIllegalArgumentException {
        if (values == null) {
            throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
        } else if (begin < 0) {
            throw new NotPositiveException(LocalizedFormats.START_POSITION, Integer.valueOf(begin));
        } else if (length < 0) {
            throw new NotPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(length));
        } else if (begin + length > values.length) {
            throw new NumberIsTooLargeException(LocalizedFormats.SUBARRAY_ENDS_AFTER_ARRAY_END, Integer.valueOf(begin + length), Integer.valueOf(values.length), true);
        } else {
            this.storedData = new double[length];
            System.arraycopy(values, begin, this.storedData, 0, length);
        }
    }

    public double evaluate() throws MathIllegalArgumentException {
        return evaluate(this.storedData);
    }
}
