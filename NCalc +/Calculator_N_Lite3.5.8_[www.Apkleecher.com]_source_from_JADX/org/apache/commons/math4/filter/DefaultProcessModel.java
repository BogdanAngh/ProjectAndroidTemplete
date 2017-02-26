package org.apache.commons.math4.filter;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;

public class DefaultProcessModel implements ProcessModel {
    private final RealMatrix controlMatrix;
    private final RealMatrix initialErrorCovMatrix;
    private final RealVector initialStateEstimateVector;
    private final RealMatrix processNoiseCovMatrix;
    private final RealMatrix stateTransitionMatrix;

    public DefaultProcessModel(double[][] stateTransition, double[][] control, double[][] processNoise, double[] initialStateEstimate, double[][] initialErrorCovariance) throws NullArgumentException, NoDataException, DimensionMismatchException {
        this(new Array2DRowRealMatrix(stateTransition), new Array2DRowRealMatrix(control), new Array2DRowRealMatrix(processNoise), new ArrayRealVector(initialStateEstimate), new Array2DRowRealMatrix(initialErrorCovariance));
    }

    public DefaultProcessModel(double[][] stateTransition, double[][] control, double[][] processNoise) throws NullArgumentException, NoDataException, DimensionMismatchException {
        this(new Array2DRowRealMatrix(stateTransition), new Array2DRowRealMatrix(control), new Array2DRowRealMatrix(processNoise), null, null);
    }

    public DefaultProcessModel(RealMatrix stateTransition, RealMatrix control, RealMatrix processNoise, RealVector initialStateEstimate, RealMatrix initialErrorCovariance) {
        this.stateTransitionMatrix = stateTransition;
        this.controlMatrix = control;
        this.processNoiseCovMatrix = processNoise;
        this.initialStateEstimateVector = initialStateEstimate;
        this.initialErrorCovMatrix = initialErrorCovariance;
    }

    public RealMatrix getStateTransitionMatrix() {
        return this.stateTransitionMatrix;
    }

    public RealMatrix getControlMatrix() {
        return this.controlMatrix;
    }

    public RealMatrix getProcessNoise() {
        return this.processNoiseCovMatrix;
    }

    public RealVector getInitialStateEstimate() {
        return this.initialStateEstimateVector;
    }

    public RealMatrix getInitialErrorCovariance() {
        return this.initialErrorCovMatrix;
    }
}
