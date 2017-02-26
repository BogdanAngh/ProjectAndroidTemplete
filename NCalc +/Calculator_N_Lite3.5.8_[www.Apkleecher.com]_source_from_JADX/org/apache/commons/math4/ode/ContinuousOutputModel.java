package org.apache.commons.math4.ode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.ode.sampling.StepHandler;
import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

public class ContinuousOutputModel implements StepHandler, Serializable {
    private static final long serialVersionUID = -1417964919405031606L;
    private double finalTime;
    private boolean forward;
    private int index;
    private double initialTime;
    private List<StepInterpolator> steps;

    public ContinuousOutputModel() {
        this.steps = new ArrayList();
        this.initialTime = Double.NaN;
        this.finalTime = Double.NaN;
        this.forward = true;
        this.index = 0;
    }

    public void append(ContinuousOutputModel model) throws MathIllegalArgumentException, MaxCountExceededException {
        if (model.steps.size() != 0) {
            if (this.steps.size() == 0) {
                this.initialTime = model.initialTime;
                this.forward = model.forward;
            } else if (getInterpolatedState().length != model.getInterpolatedState().length) {
                throw new DimensionMismatchException(model.getInterpolatedState().length, getInterpolatedState().length);
            } else if ((this.forward ^ model.forward) != 0) {
                throw new MathIllegalArgumentException(LocalizedFormats.PROPAGATION_DIRECTION_MISMATCH, new Object[0]);
            } else {
                StepInterpolator lastInterpolator = (StepInterpolator) this.steps.get(this.index);
                double current = lastInterpolator.getCurrentTime();
                if (FastMath.abs(model.getInitialTime() - current) > 0.001d * FastMath.abs(current - lastInterpolator.getPreviousTime())) {
                    throw new MathIllegalArgumentException(LocalizedFormats.HOLE_BETWEEN_MODELS_TIME_RANGES, Double.valueOf(FastMath.abs(gap)));
                }
            }
            for (StepInterpolator interpolator : model.steps) {
                this.steps.add(interpolator.copy());
            }
            this.index = this.steps.size() - 1;
            this.finalTime = ((StepInterpolator) this.steps.get(this.index)).getCurrentTime();
        }
    }

    public void init(double t0, double[] y0, double t) {
        this.initialTime = Double.NaN;
        this.finalTime = Double.NaN;
        this.forward = true;
        this.index = 0;
        this.steps.clear();
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
        if (this.steps.size() == 0) {
            this.initialTime = interpolator.getPreviousTime();
            this.forward = interpolator.isForward();
        }
        this.steps.add(interpolator.copy());
        if (isLast) {
            this.finalTime = interpolator.getCurrentTime();
            this.index = this.steps.size() - 1;
        }
    }

    public double getInitialTime() {
        return this.initialTime;
    }

    public double getFinalTime() {
        return this.finalTime;
    }

    public double getInterpolatedTime() {
        return ((StepInterpolator) this.steps.get(this.index)).getInterpolatedTime();
    }

    public void setInterpolatedTime(double time) {
        int iMin = 0;
        StepInterpolator sMin = (StepInterpolator) this.steps.get(0);
        double tMin = 0.5d * (sMin.getPreviousTime() + sMin.getCurrentTime());
        int iMax = this.steps.size() - 1;
        StepInterpolator sMax = (StepInterpolator) this.steps.get(iMax);
        double tMax = 0.5d * (sMax.getPreviousTime() + sMax.getCurrentTime());
        if (locatePoint(time, sMin) <= 0) {
            this.index = 0;
            sMin.setInterpolatedTime(time);
        } else if (locatePoint(time, sMax) >= 0) {
            this.index = iMax;
            sMax.setInterpolatedTime(time);
        } else {
            int i;
            while (iMax - iMin > 5) {
                StepInterpolator si = (StepInterpolator) this.steps.get(this.index);
                int location = locatePoint(time, si);
                if (location < 0) {
                    iMax = this.index;
                    tMax = 0.5d * (si.getPreviousTime() + si.getCurrentTime());
                } else if (location > 0) {
                    iMin = this.index;
                    tMin = 0.5d * (si.getPreviousTime() + si.getCurrentTime());
                } else {
                    si.setInterpolatedTime(time);
                    return;
                }
                int iMed = (iMin + iMax) / 2;
                StepInterpolator sMed = (StepInterpolator) this.steps.get(iMed);
                double tMed = 0.5d * (sMed.getPreviousTime() + sMed.getCurrentTime());
                if (FastMath.abs(tMed - tMin) < AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY || FastMath.abs(tMax - tMed) < AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY) {
                    this.index = iMed;
                } else {
                    double d12 = tMax - tMed;
                    double d23 = tMed - tMin;
                    double d13 = tMax - tMin;
                    double dt1 = time - tMax;
                    double dt2 = time - tMed;
                    double dt3 = time - tMin;
                    this.index = (int) FastMath.rint((((((dt2 * dt3) * d23) * ((double) iMax)) - (((dt1 * dt3) * d13) * ((double) iMed))) + (((dt1 * dt2) * d12) * ((double) iMin))) / ((d12 * d23) * d13));
                }
                int low = FastMath.max(iMin + 1, ((iMin * 9) + iMax) / 10);
                int high = FastMath.min(iMax - 1, ((iMax * 9) + iMin) / 10);
                i = this.index;
                if (r0 < low) {
                    this.index = low;
                } else {
                    i = this.index;
                    if (r0 > high) {
                        this.index = high;
                    }
                }
            }
            this.index = iMin;
            while (true) {
                i = this.index;
                if (r0 > iMax) {
                    break;
                }
                if (locatePoint(time, (StepInterpolator) this.steps.get(this.index)) <= 0) {
                    break;
                }
                this.index++;
            }
            ((StepInterpolator) this.steps.get(this.index)).setInterpolatedTime(time);
        }
    }

    public double[] getInterpolatedState() throws MaxCountExceededException {
        return ((StepInterpolator) this.steps.get(this.index)).getInterpolatedState();
    }

    public double[] getInterpolatedDerivatives() throws MaxCountExceededException {
        return ((StepInterpolator) this.steps.get(this.index)).getInterpolatedDerivatives();
    }

    public double[] getInterpolatedSecondaryState(int secondaryStateIndex) throws MaxCountExceededException {
        return ((StepInterpolator) this.steps.get(this.index)).getInterpolatedSecondaryState(secondaryStateIndex);
    }

    public double[] getInterpolatedSecondaryDerivatives(int secondaryStateIndex) throws MaxCountExceededException {
        return ((StepInterpolator) this.steps.get(this.index)).getInterpolatedSecondaryDerivatives(secondaryStateIndex);
    }

    private int locatePoint(double time, StepInterpolator interval) {
        if (this.forward) {
            if (time < interval.getPreviousTime()) {
                return -1;
            }
            if (time > interval.getCurrentTime()) {
                return 1;
            }
            return 0;
        } else if (time > interval.getPreviousTime()) {
            return -1;
        } else {
            if (time < interval.getCurrentTime()) {
                return 1;
            }
            return 0;
        }
    }
}
