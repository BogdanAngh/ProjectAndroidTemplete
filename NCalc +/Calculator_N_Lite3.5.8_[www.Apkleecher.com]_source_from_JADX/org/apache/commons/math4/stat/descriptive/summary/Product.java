package org.apache.commons.math4.stat.descriptive.summary;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.stat.descriptive.WeightedEvaluation;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class Product extends AbstractStorelessUnivariateStatistic implements Serializable, WeightedEvaluation {
    private static final long serialVersionUID = 20150412;
    private long n;
    private double value;

    public Product() {
        this.n = 0;
        this.value = 1.0d;
    }

    public Product(Product original) throws NullArgumentException {
        copy(original, this);
    }

    public void increment(double d) {
        this.value *= d;
        this.n++;
    }

    public double getResult() {
        return this.value;
    }

    public long getN() {
        return this.n;
    }

    public void clear() {
        this.value = 1.0d;
        this.n = 0;
    }

    public double evaluate(double[] values, int begin, int length) throws MathIllegalArgumentException {
        double product = Double.NaN;
        if (MathArrays.verifyValues(values, begin, length, true)) {
            product = 1.0d;
            for (int i = begin; i < begin + length; i++) {
                product *= values[i];
            }
        }
        return product;
    }

    public double evaluate(double[] values, double[] weights, int begin, int length) throws MathIllegalArgumentException {
        double product = Double.NaN;
        if (MathArrays.verifyValues(values, weights, begin, length, true)) {
            product = 1.0d;
            for (int i = begin; i < begin + length; i++) {
                product *= FastMath.pow(values[i], weights[i]);
            }
        }
        return product;
    }

    public double evaluate(double[] values, double[] weights) throws MathIllegalArgumentException {
        return evaluate(values, weights, 0, values.length);
    }

    public Product copy() {
        Product result = new Product();
        copy(this, result);
        return result;
    }

    public static void copy(Product source, Product dest) throws NullArgumentException {
        MathUtils.checkNotNull(source);
        MathUtils.checkNotNull(dest);
        dest.n = source.n;
        dest.value = source.value;
    }
}
