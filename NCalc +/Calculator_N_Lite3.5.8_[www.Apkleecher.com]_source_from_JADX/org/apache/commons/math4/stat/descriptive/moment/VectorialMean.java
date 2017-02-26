package org.apache.commons.math4.stat.descriptive.moment;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;

public class VectorialMean implements Serializable {
    private static final long serialVersionUID = 8223009086481006892L;
    private final Mean[] means;

    public VectorialMean(int dimension) {
        this.means = new Mean[dimension];
        for (int i = 0; i < dimension; i++) {
            this.means[i] = new Mean();
        }
    }

    public void increment(double[] v) throws DimensionMismatchException {
        if (v.length != this.means.length) {
            throw new DimensionMismatchException(v.length, this.means.length);
        }
        for (int i = 0; i < v.length; i++) {
            this.means[i].increment(v[i]);
        }
    }

    public double[] getResult() {
        double[] result = new double[this.means.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = this.means[i].getResult();
        }
        return result;
    }

    public long getN() {
        return this.means.length == 0 ? 0 : this.means[0].getN();
    }

    public int hashCode() {
        return Arrays.hashCode(this.means) + 31;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof VectorialMean)) {
            return false;
        }
        if (Arrays.equals(this.means, ((VectorialMean) obj).means)) {
            return true;
        }
        return false;
    }
}
