package org.apache.commons.math4.filter;

import org.apache.commons.math4.linear.RealMatrix;

public interface MeasurementModel {
    RealMatrix getMeasurementMatrix();

    RealMatrix getMeasurementNoise();
}
