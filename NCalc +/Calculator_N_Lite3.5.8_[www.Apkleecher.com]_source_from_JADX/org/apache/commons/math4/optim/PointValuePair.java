package org.apache.commons.math4.optim;

import java.io.Serializable;
import org.apache.commons.math4.util.Pair;

public class PointValuePair extends Pair<double[], Double> implements Serializable {
    private static final long serialVersionUID = 20120513;

    private static class DataTransferObject implements Serializable {
        private static final long serialVersionUID = 20120513;
        private final double[] point;
        private final double value;

        public DataTransferObject(double[] point, double value) {
            this.point = (double[]) point.clone();
            this.value = value;
        }

        private Object readResolve() {
            return new PointValuePair(this.point, this.value, false);
        }
    }

    public PointValuePair(double[] point, double value) {
        this(point, value, true);
    }

    public PointValuePair(double[] point, double value, boolean copyArray) {
        Object obj;
        if (!copyArray) {
            obj = point;
        } else if (point == null) {
            obj = null;
        } else {
            double[] dArr = (double[]) point.clone();
        }
        super(obj, Double.valueOf(value));
    }

    public double[] getPoint() {
        double[] p = (double[]) getKey();
        return p == null ? null : (double[]) p.clone();
    }

    public double[] getPointRef() {
        return (double[]) getKey();
    }

    private Object writeReplace() {
        return new DataTransferObject((double[]) getKey(), ((Double) getValue()).doubleValue());
    }
}
