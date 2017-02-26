package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.ExpandableStatefulODE;
import org.apache.commons.math4.ode.sampling.NordsieckStepInterpolator;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;

public class AdamsBashforthIntegrator extends AdamsIntegrator {
    private static final String METHOD_NAME = "Adams-Bashforth";

    public AdamsBashforthIntegrator(int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
        super(METHOD_NAME, nSteps, nSteps, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    public AdamsBashforthIntegrator(int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
        super(METHOD_NAME, nSteps, nSteps, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
    }

    public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
        sanityChecks(equations, t);
        setEquations(equations);
        boolean forward = t > equations.getTime();
        double[] y0 = equations.getCompleteState();
        double[] y = (double[]) y0.clone();
        double[] yDot = new double[y.length];
        NordsieckStepInterpolator interpolator = new NordsieckStepInterpolator();
        interpolator.reinitialize(y, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
        initIntegration(equations.getTime(), y0, t);
        start(equations.getTime(), y, t);
        interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
        interpolator.storeTime(this.stepStart);
        int lastRow = this.nordsieck.getRowDimension() - 1;
        double hNew = this.stepSize;
        interpolator.rescale(hNew);
        this.isLastStep = false;
        do {
            double error = BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
            while (error >= 1.0d) {
                this.stepSize = hNew;
                error = 0.0d;
                for (int i = 0; i < this.mainSetDimension; i++) {
                    double tol;
                    double yScale = FastMath.abs(y[i]);
                    if (this.vecAbsoluteTolerance == null) {
                        tol = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * yScale);
                    } else {
                        tol = this.vecAbsoluteTolerance[i] + (this.vecRelativeTolerance[i] * yScale);
                    }
                    double ratio = this.nordsieck.getEntry(lastRow, i) / tol;
                    error += ratio * ratio;
                }
                error = FastMath.sqrt(error / ((double) this.mainSetDimension));
                if (error >= 1.0d) {
                    hNew = filterStep(this.stepSize * computeStepGrowShrinkFactor(error), forward, false);
                    interpolator.rescale(hNew);
                }
            }
            double stepEnd = this.stepStart + this.stepSize;
            interpolator.shift();
            interpolator.setInterpolatedTime(stepEnd);
            ExpandableStatefulODE expandable = getExpandable();
            expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), y);
            int index = 0;
            for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
                secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), y);
                index++;
            }
            computeDerivatives(stepEnd, y, yDot);
            double[] predictedScaled = new double[y0.length];
            for (int j = 0; j < y0.length; j++) {
                predictedScaled[j] = this.stepSize * yDot[j];
            }
            Array2DRowRealMatrix nordsieckTmp = updateHighOrderDerivativesPhase1(this.nordsieck);
            updateHighOrderDerivativesPhase2(this.scaled, predictedScaled, nordsieckTmp);
            interpolator.reinitialize(stepEnd, this.stepSize, predictedScaled, nordsieckTmp);
            interpolator.storeTime(stepEnd);
            this.stepStart = acceptStep(interpolator, y, yDot, t);
            this.scaled = predictedScaled;
            this.nordsieck = nordsieckTmp;
            interpolator.reinitialize(stepEnd, this.stepSize, this.scaled, this.nordsieck);
            if (!this.isLastStep) {
                interpolator.storeTime(this.stepStart);
                if (this.resetOccurred) {
                    start(this.stepStart, y, t);
                    interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
                }
                double scaledH = this.stepSize * computeStepGrowShrinkFactor(error);
                double nextT = this.stepStart + scaledH;
                boolean nextIsLast = forward ? nextT >= t : nextT <= t;
                hNew = filterStep(scaledH, forward, nextIsLast);
                double filteredNextT = this.stepStart + hNew;
                boolean filteredNextIsLast = forward ? filteredNextT >= t : filteredNextT <= t;
                if (filteredNextIsLast) {
                    hNew = t - this.stepStart;
                }
                interpolator.rescale(hNew);
            }
        } while (!this.isLastStep);
        equations.setTime(this.stepStart);
        equations.setCompleteState(y);
        resetInternalState();
    }
}
