package org.apache.commons.math4.ml.distance;

import java.io.Serializable;

public interface DistanceMeasure extends Serializable {
    double compute(double[] dArr, double[] dArr2);
}
