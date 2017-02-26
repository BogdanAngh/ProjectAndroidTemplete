package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.ode.AbstractIntegrator;
import org.apache.commons.math4.ode.ExpandableStatefulODE;
import org.apache.commons.math4.util.FastMath;

public abstract class AdaptiveStepsizeIntegrator extends AbstractIntegrator {
    private double initialStep;
    protected int mainSetDimension;
    private double maxStep;
    private double minStep;
    protected double scalAbsoluteTolerance;
    protected double scalRelativeTolerance;
    protected double[] vecAbsoluteTolerance;
    protected double[] vecRelativeTolerance;

    public abstract void integrate(ExpandableStatefulODE expandableStatefulODE, double d) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;

    public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
        super(name);
        setStepSizeControl(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        resetInternalState();
    }

    public AdaptiveStepsizeIntegrator(String name, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(name);
        setStepSizeControl(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        resetInternalState();
    }

    public void setStepSizeControl(double minimalStep, double maximalStep, double absoluteTolerance, double relativeTolerance) {
        this.minStep = FastMath.abs(minimalStep);
        this.maxStep = FastMath.abs(maximalStep);
        this.initialStep = -1.0d;
        this.scalAbsoluteTolerance = absoluteTolerance;
        this.scalRelativeTolerance = relativeTolerance;
        this.vecAbsoluteTolerance = null;
        this.vecRelativeTolerance = null;
    }

    public void setStepSizeControl(double minimalStep, double maximalStep, double[] absoluteTolerance, double[] relativeTolerance) {
        this.minStep = FastMath.abs(minimalStep);
        this.maxStep = FastMath.abs(maximalStep);
        this.initialStep = -1.0d;
        this.scalAbsoluteTolerance = 0.0d;
        this.scalRelativeTolerance = 0.0d;
        this.vecAbsoluteTolerance = (double[]) absoluteTolerance.clone();
        this.vecRelativeTolerance = (double[]) relativeTolerance.clone();
    }

    public void setInitialStepSize(double initialStepSize) {
        if (initialStepSize < this.minStep || initialStepSize > this.maxStep) {
            this.initialStep = -1.0d;
        } else {
            this.initialStep = initialStepSize;
        }
    }

    protected void sanityChecks(ExpandableStatefulODE equations, double t) throws DimensionMismatchException, NumberIsTooSmallException {
        super.sanityChecks(equations, t);
        this.mainSetDimension = equations.getPrimaryMapper().getDimension();
        if (this.vecAbsoluteTolerance != null && this.vecAbsoluteTolerance.length != this.mainSetDimension) {
            throw new DimensionMismatchException(this.mainSetDimension, this.vecAbsoluteTolerance.length);
        } else if (this.vecRelativeTolerance != null && this.vecRelativeTolerance.length != this.mainSetDimension) {
            throw new DimensionMismatchException(this.mainSetDimension, this.vecRelativeTolerance.length);
        }
    }

    public double initializeStep(boolean forward, int order, double[] scale, double t0, double[] y0, double[] yDot0, double[] y1, double[] yDot1) throws MaxCountExceededException, DimensionMismatchException {
        if (this.initialStep <= 0.0d) {
            int j;
            double ratio;
            double h1;
            double yOnScale2 = 0.0d;
            double yDotOnScale2 = 0.0d;
            for (j = 0; j < scale.length; j++) {
                ratio = y0[j] / scale[j];
                yOnScale2 += ratio * ratio;
                ratio = yDot0[j] / scale[j];
                yDotOnScale2 += ratio * ratio;
            }
            double h = (yOnScale2 < CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD || yDotOnScale2 < CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD) ? AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY : 0.01d * FastMath.sqrt(yOnScale2 / yDotOnScale2);
            if (!forward) {
                h = -h;
            }
            for (j = 0; j < y0.length; j++) {
                y1[j] = y0[j] + (yDot0[j] * h);
            }
            computeDerivatives(t0 + h, y1, yDot1);
            double yDDotOnScale = 0.0d;
            for (j = 0; j < scale.length; j++) {
                ratio = (yDot1[j] - yDot0[j]) / scale[j];
                yDDotOnScale += ratio * ratio;
            }
            double maxInv2 = FastMath.max(FastMath.sqrt(yDotOnScale2), FastMath.sqrt(yDDotOnScale) / h);
            if (maxInv2 < CholeskyDecomposition.DEFAULT_RELATIVE_SYMMETRY_THRESHOLD) {
                h1 = FastMath.max((double) AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY, 0.001d * FastMath.abs(h));
            } else {
                h1 = FastMath.pow(0.01d / maxInv2, 1.0d / ((double) order));
            }
            h = FastMath.max(FastMath.min(100.0d * FastMath.abs(h), h1), OpenMapRealVector.DEFAULT_ZERO_TOLERANCE * FastMath.abs(t0));
            if (h < getMinStep()) {
                h = getMinStep();
            }
            if (h > getMaxStep()) {
                h = getMaxStep();
            }
            if (!forward) {
                h = -h;
            }
            return h;
        } else if (forward) {
            return this.initialStep;
        } else {
            return -this.initialStep;
        }
    }

    protected double filterStep(double h, boolean forward, boolean acceptSmall) throws NumberIsTooSmallException {
        double filteredH = h;
        if (FastMath.abs(h) < this.minStep) {
            if (acceptSmall) {
                filteredH = forward ? this.minStep : -this.minStep;
            } else {
                throw new NumberIsTooSmallException(LocalizedFormats.MINIMAL_STEPSIZE_REACHED_DURING_INTEGRATION, Double.valueOf(FastMath.abs(h)), Double.valueOf(this.minStep), true);
            }
        }
        if (filteredH > this.maxStep) {
            return this.maxStep;
        }
        if (filteredH < (-this.maxStep)) {
            return -this.maxStep;
        }
        return filteredH;
    }

    public double getCurrentStepStart() {
        return this.stepStart;
    }

    protected void resetInternalState() {
        this.stepStart = Double.NaN;
        this.stepSize = FastMath.sqrt(this.minStep * this.maxStep);
    }

    public double getMinStep() {
        return this.minStep;
    }

    public double getMaxStep() {
        return this.maxStep;
    }
}
