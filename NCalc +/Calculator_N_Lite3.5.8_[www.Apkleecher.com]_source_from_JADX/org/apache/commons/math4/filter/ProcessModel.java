package org.apache.commons.math4.filter;

import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

public interface ProcessModel {
    RealMatrix getControlMatrix();

    RealMatrix getInitialErrorCovariance();

    RealVector getInitialStateEstimate();

    RealMatrix getProcessNoise();

    RealMatrix getStateTransitionMatrix();
}
