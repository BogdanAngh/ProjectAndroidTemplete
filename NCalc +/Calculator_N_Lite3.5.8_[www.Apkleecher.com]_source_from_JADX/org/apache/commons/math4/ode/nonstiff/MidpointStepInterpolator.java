package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.ode.sampling.StepInterpolator;

class MidpointStepInterpolator extends RungeKuttaStepInterpolator {
    private static final long serialVersionUID = 20111120;

    public MidpointStepInterpolator(MidpointStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new MidpointStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        double coeffDot2 = 2.0d * theta;
        double coeffDot1 = 1.0d - coeffDot2;
        double coeff1;
        double coeff2;
        int i;
        if (this.previousState == null || theta > 0.5d) {
            coeff1 = oneMinusThetaH * theta;
            coeff2 = oneMinusThetaH * (1.0d + theta);
            for (i = 0; i < this.interpolatedState.length; i++) {
                double yDot1 = this.yDotK[0][i];
                double yDot2 = this.yDotK[1][i];
                this.interpolatedState[i] = (this.currentState[i] + (coeff1 * yDot1)) - (coeff2 * yDot2);
                this.interpolatedDerivatives[i] = (coeffDot1 * yDot1) + (coeffDot2 * yDot2);
            }
            return;
        }
        coeff1 = theta * oneMinusThetaH;
        coeff2 = (theta * theta) * this.h;
        for (i = 0; i < this.interpolatedState.length; i++) {
            yDot1 = this.yDotK[0][i];
            yDot2 = this.yDotK[1][i];
            this.interpolatedState[i] = (this.previousState[i] + (coeff1 * yDot1)) + (coeff2 * yDot2);
            this.interpolatedDerivatives[i] = (coeffDot1 * yDot1) + (coeffDot2 * yDot2);
        }
    }
}
