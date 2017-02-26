package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.ode.sampling.StepInterpolator;

class ClassicalRungeKuttaStepInterpolator extends RungeKuttaStepInterpolator {
    private static final long serialVersionUID = 20111120;

    public ClassicalRungeKuttaStepInterpolator(ClassicalRungeKuttaStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new ClassicalRungeKuttaStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        double oneMinusTheta = 1.0d - theta;
        double oneMinus2Theta = 1.0d - (2.0d * theta);
        double coeffDot1 = oneMinusTheta * oneMinus2Theta;
        double coeffDot23 = (2.0d * theta) * oneMinusTheta;
        double coeffDot4 = (-theta) * oneMinus2Theta;
        double coeff1;
        double coeff23;
        double coeff4;
        int i;
        if (this.previousState == null || theta > 0.5d) {
            double fourTheta = 4.0d * theta;
            double s = oneMinusThetaH / 6.0d;
            coeff1 = s * ((((-fourTheta) + 5.0d) * theta) - 1.0d);
            coeff23 = s * (((fourTheta - 2.0d) * theta) - 2.0d);
            coeff4 = s * ((((-fourTheta) - 1.0d) * theta) - 1.0d);
            for (i = 0; i < this.interpolatedState.length; i++) {
                double yDot1 = this.yDotK[0][i];
                double yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
                double yDot4 = this.yDotK[3][i];
                this.interpolatedState[i] = ((this.currentState[i] + (coeff1 * yDot1)) + (coeff23 * yDot23)) + (coeff4 * yDot4);
                this.interpolatedDerivatives[i] = ((coeffDot1 * yDot1) + (coeffDot23 * yDot23)) + (coeffDot4 * yDot4);
            }
            return;
        }
        double fourTheta2 = (4.0d * theta) * theta;
        s = (this.h * theta) / 6.0d;
        coeff1 = s * ((6.0d - (9.0d * theta)) + fourTheta2);
        coeff23 = s * ((6.0d * theta) - fourTheta2);
        coeff4 = s * ((-3.0d * theta) + fourTheta2);
        for (i = 0; i < this.interpolatedState.length; i++) {
            yDot1 = this.yDotK[0][i];
            yDot23 = this.yDotK[1][i] + this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            this.interpolatedState[i] = ((this.previousState[i] + (coeff1 * yDot1)) + (coeff23 * yDot23)) + (coeff4 * yDot4);
            this.interpolatedDerivatives[i] = ((coeffDot1 * yDot1) + (coeffDot23 * yDot23)) + (coeffDot4 * yDot4);
        }
    }
}
