package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.ode.sampling.StepInterpolator;

class EulerStepInterpolator extends RungeKuttaStepInterpolator {
    private static final long serialVersionUID = 20111120;

    public EulerStepInterpolator(EulerStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new EulerStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        int i;
        if (this.previousState == null || theta > 0.5d) {
            for (i = 0; i < this.interpolatedState.length; i++) {
                this.interpolatedState[i] = this.currentState[i] - (this.yDotK[0][i] * oneMinusThetaH);
            }
            System.arraycopy(this.yDotK[0], 0, this.interpolatedDerivatives, 0, this.interpolatedDerivatives.length);
            return;
        }
        for (i = 0; i < this.interpolatedState.length; i++) {
            this.interpolatedState[i] = this.previousState[i] + ((this.h * theta) * this.yDotK[0][i]);
        }
        System.arraycopy(this.yDotK[0], 0, this.interpolatedDerivatives, 0, this.interpolatedDerivatives.length);
    }
}
