package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealMatrix;

public class VectorialCovariance implements Serializable {
    private static final long serialVersionUID = 4118372414238930270L;
    private final boolean isBiasCorrected;
    private long n;
    private final double[] productsSums;
    private final double[] sums;

    public VectorialCovariance(int dimension, boolean isBiasCorrected) {
        this.sums = new double[dimension];
        this.productsSums = new double[(((dimension + 1) * dimension) / 2)];
        this.n = 0;
        this.isBiasCorrected = isBiasCorrected;
    }

    public void increment(double[] v) throws DimensionMismatchException {
        if (v.length != this.sums.length) {
            throw new DimensionMismatchException(v.length, this.sums.length);
        }
        int k = 0;
        int i = 0;
        while (i < v.length) {
            double[] dArr = this.sums;
            dArr[i] = dArr[i] + v[i];
            int j = 0;
            int k2 = k;
            while (j <= i) {
                dArr = this.productsSums;
                k = k2 + 1;
                dArr[k2] = dArr[k2] + (v[i] * v[j]);
                j++;
                k2 = k;
            }
            i++;
            k = k2;
        }
        this.n++;
    }

    public RealMatrix getResult() {
        int dimension = this.sums.length;
        RealMatrix result = MatrixUtils.createRealMatrix(dimension, dimension);
        if (this.n > 1) {
            double c = 1.0d / ((double) ((this.isBiasCorrected ? this.n - 1 : this.n) * this.n));
            int k = 0;
            int i = 0;
            while (i < dimension) {
                int j = 0;
                int k2 = k;
                while (j <= i) {
                    k = k2 + 1;
                    double e = c * ((((double) this.n) * this.productsSums[k2]) - (this.sums[i] * this.sums[j]));
                    result.setEntry(i, j, e);
                    result.setEntry(j, i, e);
                    j++;
                    k2 = k;
                }
                i++;
                k = k2;
            }
        }
        return result;
    }

    public long getN() {
        return this.n;
    }

    public void clear() {
        this.n = 0;
        Arrays.fill(this.sums, 0.0d);
        Arrays.fill(this.productsSums, 0.0d);
    }

    public int hashCode() {
        return (((((((this.isBiasCorrected ? 1231 : 1237) + 31) * 31) + ((int) (this.n ^ (this.n >>> 32)))) * 31) + Arrays.hashCode(this.productsSums)) * 31) + Arrays.hashCode(this.sums);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VectorialCovariance)) {
            return false;
        }
        VectorialCovariance other = (VectorialCovariance) obj;
        if (this.isBiasCorrected != other.isBiasCorrected) {
            return false;
        }
        if (this.n != other.n) {
            return false;
        }
        if (!Arrays.equals(this.productsSums, other.productsSums)) {
            return false;
        }
        if (Arrays.equals(this.sums, other.sums)) {
            return true;
        }
        return false;
    }
}
