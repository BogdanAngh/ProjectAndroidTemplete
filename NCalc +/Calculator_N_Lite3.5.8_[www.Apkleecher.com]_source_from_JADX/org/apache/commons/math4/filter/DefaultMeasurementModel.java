package org.apache.commons.math4.filter;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrix;

public class DefaultMeasurementModel implements MeasurementModel {
    private final RealMatrix measurementMatrix;
    private final RealMatrix measurementNoise;

    public DefaultMeasurementModel(double[][] measMatrix, double[][] measNoise) throws NullArgumentException, NoDataException, DimensionMismatchException {
        this(new Array2DRowRealMatrix(measMatrix), new Array2DRowRealMatrix(measNoise));
    }

    public DefaultMeasurementModel(RealMatrix measMatrix, RealMatrix measNoise) {
        this.measurementMatrix = measMatrix;
        this.measurementNoise = measNoise;
    }

    public RealMatrix getMeasurementMatrix() {
        return this.measurementMatrix;
    }

    public RealMatrix getMeasurementNoise() {
        return this.measurementNoise;
    }
}
