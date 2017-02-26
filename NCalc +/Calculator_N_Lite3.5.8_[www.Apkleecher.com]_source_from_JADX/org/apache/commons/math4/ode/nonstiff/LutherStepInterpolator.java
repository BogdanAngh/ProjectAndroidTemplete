package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

class LutherStepInterpolator extends RungeKuttaStepInterpolator {
    private static final double Q;
    private static final long serialVersionUID = 20140416;

    static {
        Q = FastMath.sqrt(21.0d);
    }

    public LutherStepInterpolator(LutherStepInterpolator interpolator) {
        super(interpolator);
    }

    protected StepInterpolator doCopy() {
        return new LutherStepInterpolator(this);
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        double coeffDot1 = 1.0d + ((-10.8d + ((36.0d + ((-47.0d + (21.0d * theta)) * theta)) * theta)) * theta);
        double coeffDot3 = theta * (-13.866666666666667d + ((106.66666666666667d + ((-202.66666666666666d + (112.0d * theta)) * theta)) * theta));
        double coeffDot4 = theta * (12.96d + ((-97.2d + ((194.4d + ((-567.0d * theta) / 5.0d)) * theta)) * theta));
        double coeffDot5 = theta * (((833.0d + (343.0d * Q)) / 150.0d) + ((((-637.0d - (357.0d * Q)) / 30.0d) + ((((392.0d + (287.0d * Q)) / 15.0d) + (((-49.0d - (49.0d * Q)) * theta) / 5.0d)) * theta)) * theta));
        double coeffDot6 = theta * (((833.0d - (343.0d * Q)) / 150.0d) + ((((-637.0d + (357.0d * Q)) / 30.0d) + ((((392.0d - (287.0d * Q)) / 15.0d) + (((-49.0d + (49.0d * Q)) * theta) / 5.0d)) * theta)) * theta));
        double coeffDot7 = theta * (0.6d + ((-3.0d + (3.0d * theta)) * theta));
        double coeff1;
        double coeff3;
        double coeff4;
        double coeff5;
        double coeff6;
        double coeff7;
        int i;
        if (this.previousState == null || theta > 0.5d) {
            coeff1 = -0.05d + ((0.95d + ((-4.45d + ((7.55d + ((-21.0d * theta) / 5.0d)) * theta)) * theta)) * theta);
            coeff3 = -0.35555555555555557d + ((-0.35555555555555557d + ((-7.288888888888889d + ((28.266666666666666d + ((-112.0d * theta) / 5.0d)) * theta)) * theta)) * theta);
            coeff4 = theta * ((6.48d + ((-25.92d + ((567.0d * theta) / 25.0d)) * theta)) * theta);
            coeff5 = -0.2722222222222222d + ((-0.2722222222222222d + ((((2254.0d + (1029.0d * Q)) / 900.0d) + ((((-1372.0d - (847.0d * Q)) / 300.0d) + (((49.0d + (49.0d * Q)) * theta) / 25.0d)) * theta)) * theta)) * theta);
            coeff6 = -0.2722222222222222d + ((-0.2722222222222222d + ((((2254.0d - (1029.0d * Q)) / 900.0d) + ((((-1372.0d + (847.0d * Q)) / 300.0d) + (((49.0d - (49.0d * Q)) * theta) / 25.0d)) * theta)) * theta)) * theta);
            coeff7 = -0.05d + ((-0.05d + ((0.25d + (-0.75d * theta)) * theta)) * theta);
            for (i = 0; i < this.interpolatedState.length; i++) {
                double yDot1 = this.yDotK[0][i];
                double yDot2 = this.yDotK[1][i];
                double yDot3 = this.yDotK[2][i];
                double yDot4 = this.yDotK[3][i];
                double yDot5 = this.yDotK[4][i];
                double yDot6 = this.yDotK[5][i];
                double yDot7 = this.yDotK[6][i];
                this.interpolatedState[i] = this.currentState[i] + ((((((((coeff1 * yDot1) + (Q * yDot2)) + (coeff3 * yDot3)) + (coeff4 * yDot4)) + (coeff5 * yDot5)) + (coeff6 * yDot6)) + (coeff7 * yDot7)) * oneMinusThetaH);
                this.interpolatedDerivatives[i] = ((((((coeffDot1 * yDot1) + (Q * yDot2)) + (coeffDot3 * yDot3)) + (coeffDot4 * yDot4)) + (coeffDot5 * yDot5)) + (coeffDot6 * yDot6)) + (coeffDot7 * yDot7);
            }
            return;
        }
        coeff1 = 1.0d + ((-5.4d + ((12.0d + ((-11.75d + ((21.0d * theta) / 5.0d)) * theta)) * theta)) * theta);
        coeff3 = theta * (-6.933333333333334d + ((35.55555555555556d + ((-50.666666666666664d + ((112.0d * theta) / 5.0d)) * theta)) * theta));
        coeff4 = theta * (6.48d + ((-32.4d + ((48.6d + ((-567.0d * theta) / 25.0d)) * theta)) * theta));
        coeff5 = theta * (((833.0d + (343.0d * Q)) / 300.0d) + ((((-637.0d - (357.0d * Q)) / 90.0d) + ((((392.0d + (287.0d * Q)) / 60.0d) + (((-49.0d - (49.0d * Q)) * theta) / 25.0d)) * theta)) * theta));
        coeff6 = theta * (((833.0d - (343.0d * Q)) / 300.0d) + ((((-637.0d + (357.0d * Q)) / 90.0d) + ((((392.0d - (287.0d * Q)) / 60.0d) + (((-49.0d + (49.0d * Q)) * theta) / 25.0d)) * theta)) * theta));
        coeff7 = theta * (LoessInterpolator.DEFAULT_BANDWIDTH + ((-1.0d + (0.75d * theta)) * theta));
        for (i = 0; i < this.interpolatedState.length; i++) {
            yDot1 = this.yDotK[0][i];
            yDot2 = this.yDotK[1][i];
            yDot3 = this.yDotK[2][i];
            yDot4 = this.yDotK[3][i];
            yDot5 = this.yDotK[4][i];
            yDot6 = this.yDotK[5][i];
            yDot7 = this.yDotK[6][i];
            this.interpolatedState[i] = this.previousState[i] + ((this.h * theta) * (((((((coeff1 * yDot1) + (Q * yDot2)) + (coeff3 * yDot3)) + (coeff4 * yDot4)) + (coeff5 * yDot5)) + (coeff6 * yDot6)) + (coeff7 * yDot7)));
            this.interpolatedDerivatives[i] = ((((((coeffDot1 * yDot1) + (Q * yDot2)) + (coeffDot3 * yDot3)) + (coeffDot4 * yDot4)) + (coeffDot5 * yDot5)) + (coeffDot6 * yDot6)) + (coeffDot7 * yDot7);
        }
    }
}
