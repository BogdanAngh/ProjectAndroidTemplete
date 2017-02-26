package org.apache.commons.math4.ode.sampling;

import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class StepNormalizer implements StepHandler {
    private final StepNormalizerBounds bounds;
    private double firstTime;
    private boolean forward;
    private double h;
    private final FixedStepHandler handler;
    private double[] lastDerivatives;
    private double[] lastState;
    private double lastTime;
    private final StepNormalizerMode mode;

    public StepNormalizer(double h, FixedStepHandler handler) {
        this(h, handler, StepNormalizerMode.INCREMENT, StepNormalizerBounds.FIRST);
    }

    public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerMode mode) {
        this(h, handler, mode, StepNormalizerBounds.FIRST);
    }

    public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerBounds bounds) {
        this(h, handler, StepNormalizerMode.INCREMENT, bounds);
    }

    public StepNormalizer(double h, FixedStepHandler handler, StepNormalizerMode mode, StepNormalizerBounds bounds) {
        this.h = FastMath.abs(h);
        this.handler = handler;
        this.mode = mode;
        this.bounds = bounds;
        this.firstTime = Double.NaN;
        this.lastTime = Double.NaN;
        this.lastState = null;
        this.lastDerivatives = null;
        this.forward = true;
    }

    public void init(double t0, double[] y0, double t) {
        this.firstTime = Double.NaN;
        this.lastTime = Double.NaN;
        this.lastState = null;
        this.lastDerivatives = null;
        this.forward = true;
        this.handler.init(t0, y0, t);
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
        double nextTime;
        boolean z = false;
        if (this.lastState == null) {
            boolean z2;
            this.firstTime = interpolator.getPreviousTime();
            this.lastTime = interpolator.getPreviousTime();
            interpolator.setInterpolatedTime(this.lastTime);
            this.lastState = (double[]) interpolator.getInterpolatedState().clone();
            this.lastDerivatives = (double[]) interpolator.getInterpolatedDerivatives().clone();
            if (interpolator.getCurrentTime() >= this.lastTime) {
                z2 = true;
            } else {
                z2 = false;
            }
            this.forward = z2;
            if (!this.forward) {
                this.h = -this.h;
            }
        }
        if (this.mode == StepNormalizerMode.INCREMENT) {
            nextTime = this.lastTime + this.h;
        } else {
            nextTime = (FastMath.floor(this.lastTime / this.h) + 1.0d) * this.h;
        }
        if (this.mode == StepNormalizerMode.MULTIPLES && Precision.equals(nextTime, this.lastTime, 1)) {
            nextTime += this.h;
        }
        boolean nextInStep = isNextInStep(nextTime, interpolator);
        while (nextInStep) {
            doNormalizedStep(false);
            storeStep(interpolator, nextTime);
            nextTime += this.h;
            nextInStep = isNextInStep(nextTime, interpolator);
        }
        if (isLast) {
            boolean addLast;
            if (!this.bounds.lastIncluded() || this.lastTime == interpolator.getCurrentTime()) {
                addLast = false;
            } else {
                addLast = true;
            }
            if (!addLast) {
                z = true;
            }
            doNormalizedStep(z);
            if (addLast) {
                storeStep(interpolator, interpolator.getCurrentTime());
                doNormalizedStep(true);
            }
        }
    }

    private boolean isNextInStep(double nextTime, StepInterpolator interpolator) {
        if (this.forward) {
            return nextTime <= interpolator.getCurrentTime();
        } else {
            if (nextTime < interpolator.getCurrentTime()) {
                return false;
            }
            return true;
        }
    }

    private void doNormalizedStep(boolean isLast) {
        if (this.bounds.firstIncluded() || this.firstTime != this.lastTime) {
            this.handler.handleStep(this.lastTime, this.lastState, this.lastDerivatives, isLast);
        }
    }

    private void storeStep(StepInterpolator interpolator, double t) throws MaxCountExceededException {
        this.lastTime = t;
        interpolator.setInterpolatedTime(this.lastTime);
        System.arraycopy(interpolator.getInterpolatedState(), 0, this.lastState, 0, this.lastState.length);
        System.arraycopy(interpolator.getInterpolatedDerivatives(), 0, this.lastDerivatives, 0, this.lastDerivatives.length);
    }
}
