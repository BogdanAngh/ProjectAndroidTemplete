package org.apache.commons.math4.optim;

import java.io.Serializable;
import org.apache.commons.math4.util.Pair;

public class PointVectorValuePair extends Pair<double[], double[]> implements Serializable {
    private static final long serialVersionUID = 20120513;

    private static class DataTransferObject implements Serializable {
        private static final long serialVersionUID = 20120513;
        private final double[] point;
        private final double[] value;

        public DataTransferObject(double[] point, double[] value) {
            this.point = (double[]) point.clone();
            this.value = (double[]) value.clone();
        }

        private Object readResolve() {
            return new PointVectorValuePair(this.point, this.value, false);
        }
    }

    public PointVectorValuePair(double[] point, double[] value) {
        this(point, value, true);
    }

    public PointVectorValuePair(double[] point, double[] value, boolean copyArray) {
        Object point2;
        Object obj;
        if (copyArray) {
            if (point == null) {
                point2 = null;
            } else {
                point2 = (double[]) point.clone();
            }
        }
        if (!copyArray) {
            obj = value;
        } else if (value == null) {
            obj = null;
        } else {
            double[] dArr = (double[]) value.clone();
        }
        super(point2, obj);
    }

    public double[] getPoint() {
        double[] p = (double[]) getKey();
        return p == null ? null : (double[]) p.clone();
    }

    public double[] getPointRef() {
        return (double[]) getKey();
    }

    public double[] getValue() {
        double[] v = (double[]) super.getValue();
        return v == null ? null : (double[]) v.clone();
    }

    public double[] getValueRef() {
        return (double[]) super.getValue();
    }

    private Object writeReplace() {
        return new DataTransferObject((double[]) getKey(), getValue());
    }
}
