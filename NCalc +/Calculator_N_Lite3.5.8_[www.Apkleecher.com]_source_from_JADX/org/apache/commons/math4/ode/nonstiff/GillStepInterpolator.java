package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

class GillStepInterpolator extends RungeKuttaStepInterpolator {
    private static final double ONE_MINUS_INV_SQRT_2;
    private static final double ONE_PLUS_INV_SQRT_2;
    private static final long serialVersionUID = 20111120;

    static {
        ONE_MINUS_INV_SQRT_2 = 1.0d - FastMath.sqrt(0.5d);
        ONE_PLUS_INV_SQRT_2 = FastMath.sqrt(0.5d) + 1.0d;
    }

    public GillStepInterpolator(GillStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new GillStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        double twoTheta = 2.0d * theta;
        double fourTheta2 = twoTheta * twoTheta;
        double coeffDot1 = ((twoTheta - 3.0d) * theta) + 1.0d;
        double cDot23 = twoTheta * (1.0d - theta);
        double coeffDot2 = cDot23 * ONE_MINUS_INV_SQRT_2;
        double coeffDot3 = cDot23 * ONE_PLUS_INV_SQRT_2;
        double coeffDot4 = theta * (twoTheta - 1.0d);
        double coeff1;
        double coeff2;
        double coeff3;
        double coeff4;
        int i;
        if (this.previousState == null || theta > 0.5d) {
            double s = oneMinusThetaH / 6.0d;
            double c23 = s * ((2.0d + twoTheta) - fourTheta2);
            coeff1 = s * ((1.0d - (5.0d * theta)) + fourTheta2);
            coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
            coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
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
        s = (this.h * theta) / 6.0d;
        c23 = s * ((6.0d * theta) - fourTheta2);
        coeff1 = s * ((6.0d - (9.0d * theta)) + fourTheta2);
        coeff2 = c23 * ONE_MINUS_INV_SQRT_2;
        coeff3 = c23 * ONE_PLUS_INV_SQRT_2;
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
