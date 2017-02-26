package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.ode.sampling.StepInterpolator;

class ThreeEighthesStepInterpolator extends RungeKuttaStepInterpolator {
    private static final long serialVersionUID = 20111120;

    public ThreeEighthesStepInterpolator(ThreeEighthesStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new ThreeEighthesStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        double coeffDot3 = 0.75d * theta;
        double coeffDot1 = (((4.0d * theta) - 5.0d) * coeffDot3) + 1.0d;
        double coeffDot2 = coeffDot3 * (5.0d - (6.0d * theta));
        double coeffDot4 = coeffDot3 * ((2.0d * theta) - 1.0d);
        double coeff1;
        double coeff2;
        double coeff3;
        double coeff4;
        int i;
        if (this.previousState == null || theta > 0.5d) {
            double s = oneMinusThetaH / 8.0d;
            double fourTheta2 = (4.0d * theta) * theta;
            coeff1 = s * ((1.0d - (7.0d * theta)) + (2.0d * fourTheta2));
            coeff2 = (3.0d * s) * ((1.0d + theta) - fourTheta2);
            coeff3 = (3.0d * s) * (1.0d + theta);
            coeff4 = s * ((1.0d + theta) + fourTheta2);
            for (i = 0; i < this.interpolatedState.length; i++) {
                double yDot1 = this.yDotK[0][i];
                double yDot2 = this.yDotK[1][i];
                double yDot3 = this.yDotK[2][i];
                double yDot4 = this.yDotK[3][i];
                this.interpolatedState[i] = (((this.currentState[i] - (coeff1 * yDot1)) - (coeff2 * yDot2)) - (coeff3 * yDot3)) - (coeff4 * yDot4);
                this.interpolatedDerivatives[i] = (((coeffDot1 * yDot1) + (coeffDot2 * yDot2)) + (coeffDot3 * yDot3)) + (coeffDot4 * yDot4);
            }
            return;
        }
        s = (this.h * theta) / 8.0d;
        fourTheta2 = (4.0d * theta) * theta;
        coeff1 = s * ((8.0d - (15.0d * theta)) + (2.0d * fourTheta2));
        coeff2 = (3.0d * s) * ((5.0d * theta) - fourTheta2);
        coeff3 = (3.0d * s) * theta;
        coeff4 = s * ((-3.0d * theta) + fourTheta2);
        for (i = 0; i < this.interpolatedState.length; i++) {
            yDot1 = this.yDotK[0][i];
            yDot2 = this.yDotK[1][i];
            yDot3 = this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = (((this.previousState[i] + (coeff1 * yDot1)) + (coeff2 * yDot2)) + (coeff3 * yDot3)) + (coeff4 * yDot4);
            this.interpolatedDerivatives[i] = (((coeffDot1 * yDot1) + (coeffDot2 * yDot2)) + (coeffDot3 * yDot3)) + (coeffDot4 * yDot4);
        }
    }
}
