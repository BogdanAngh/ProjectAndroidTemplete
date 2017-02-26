package org.apache.commons.math4.ode.nonstiff;

import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.RealMatrixPreservingVisitor;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.ExpandableStatefulODE;
import org.apache.commons.math4.ode.sampling.NordsieckStepInterpolator;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;

public class AdamsMoultonIntegrator extends AdamsIntegrator {
    private static final String METHOD_NAME = "Adams-Moulton";

    private class Corrector implements RealMatrixPreservingVisitor {
        private final double[] after;
        private final double[] before;
        private final double[] previous;
        private final double[] scaled;

        public Corrector(double[] previous, double[] scaled, double[] state) {
            this.previous = previous;
            this.scaled = scaled;
            this.after = state;
            this.before = (double[]) state.clone();
        }

        public void start(int rows, int columns, int startRow, int endRow, int startColumn, int endColumn) {
            Arrays.fill(this.after, 0.0d);
        }

        public void visit(int row, int column, double value) {
            if ((row & 1) == 0) {
                double[] dArr = this.after;
                dArr[column] = dArr[column] - value;
                return;
            }
            dArr = this.after;
            dArr[column] = dArr[column] + value;
        }

        public double end() {
            double error = 0.0d;
            for (int i = 0; i < this.after.length; i++) {
                double[] dArr = this.after;
                dArr[i] = dArr[i] + (this.previous[i] + this.scaled[i]);
                if (i < AdamsMoultonIntegrator.this.mainSetDimension) {
                    double tol;
                    double yScale = FastMath.max(FastMath.abs(this.previous[i]), FastMath.abs(this.after[i]));
                    if (AdamsMoultonIntegrator.this.vecAbsoluteTolerance == null) {
                        tol = AdamsMoultonIntegrator.this.scalAbsoluteTolerance + (AdamsMoultonIntegrator.this.scalRelativeTolerance * yScale);
                    } else {
                        tol = AdamsMoultonIntegrator.this.vecAbsoluteTolerance[i] + (AdamsMoultonIntegrator.this.vecRelativeTolerance[i] * yScale);
                    }
                    double ratio = (this.after[i] - this.before[i]) / tol;
                    error += ratio * ratio;
                }
            }
            return FastMath.sqrt(error / ((double) AdamsMoultonIntegrator.this.mainSetDimension));
        }
    }

    public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
        super(METHOD_NAME, nSteps, nSteps + 1, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
    }

    public AdamsMoultonIntegrator(int nSteps, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) throws IllegalArgumentException {
        super(METHOD_NAME, nSteps, nSteps + 1, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
    }

    public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
        sanityChecks(equations, t);
        setEquations(equations);
        boolean forward = t > equations.getTime();
        double[] y0 = equations.getCompleteState();
        double[] y = (double[]) y0.clone();
        double[] yDot = new double[y.length];
        Object yTmp = new double[y.length];
        double[] predictedScaled = new double[y.length];
        NordsieckStepInterpolator interpolator = new NordsieckStepInterpolator();
        interpolator.reinitialize(y, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
        initIntegration(equations.getTime(), y0, t);
        start(equations.getTime(), y, t);
        interpolator.reinitialize(this.stepStart, this.stepSize, this.scaled, this.nordsieck);
        interpolator.storeTime(this.stepStart);
        double hNew = this.stepSize;
        interpolator.rescale(hNew);
        this.isLastStep = false;
        Array2DRowRealMatrix nordsieckTmp = null;
        do {
            double stepEnd;
            double error = BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
            while (error >= 1.0d) {
                int j;
                this.stepSize = hNew;
                stepEnd = this.stepStart + this.stepSize;
                interpolator.setInterpolatedTime(stepEnd);
                ExpandableStatefulODE expandable = getExpandable();
                expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), yTmp);
                int index = 0;
                for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
                    secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), yTmp);
                    index++;
                }
                computeDerivatives(stepEnd, yTmp, yDot);
                for (j = 0; j < y0.length; j++) {
                    predictedScaled[j] = this.stepSize * yDot[j];
                }
                nordsieckTmp = updateHighOrderDerivativesPhase1(this.nordsieck);
                updateHighOrderDerivativesPhase2(this.scaled, predictedScaled, nordsieckTmp);
                error = nordsieckTmp.walkInOptimizedOrder(new Corrector(y, predictedScaled, yTmp));
                if (error >= 1.0d) {
                    hNew = filterStep(this.stepSize * computeStepGrowShrinkFactor(error), forward, false);
                    interpolator.rescale(hNew);
                }
            }
            stepEnd = this.stepStart + this.stepSize;
            computeDerivatives(stepEnd, yTmp, yDot);
            double[] correctedScaled = new double[y0.length];
            for (j = 0; j < y0.length; j++) {
                correctedScaled[j] = this.stepSize * yDot[j];
            }
            updateHighOrderDerivativesPhase2(predictedScaled, correctedScaled, nordsieckTmp);
            System.arraycopy(yTmp, 0, y, 0, y.length);
            interpolator.reinitialize(stepEnd, this.stepSize, correctedScaled, nordsieckTmp);
            interpolator.storeTime(this.stepStart);
            interpolator.shift();
            interpolator.storeTime(stepEnd);
            this.stepStart = acceptStep(interpolator, y, yDot, t);
            this.scaled = correctedScaled;
            this.nordsieck = nordsieckTmp;
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
