package org.apache.commons.math4.ode.nonstiff;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.ode.ExpandableStatefulODE;
import org.apache.commons.math4.ode.sampling.AbstractStepInterpolator;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;
import org.apache.commons.math4.util.FastMath;

public abstract class EmbeddedRungeKuttaIntegrator extends AdaptiveStepsizeIntegrator {
    private final double[][] a;
    private final double[] b;
    private final double[] c;
    private final double exp;
    private final boolean fsal;
    private double maxGrowth;
    private double minReduction;
    private final RungeKuttaStepInterpolator prototype;
    private double safety;

    protected abstract double estimateError(double[][] dArr, double[] dArr2, double[] dArr3, double d);

    public abstract int getOrder();

    protected EmbeddedRungeKuttaIntegrator(String name, boolean fsal, double[] c, double[][] a, double[] b, RungeKuttaStepInterpolator prototype, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
        super(name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        this.fsal = fsal;
        this.c = c;
        this.a = a;
        this.b = b;
        this.prototype = prototype;
        this.exp = -1.0d / ((double) getOrder());
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS);
    }

    protected EmbeddedRungeKuttaIntegrator(String name, boolean fsal, double[] c, double[][] a, double[] b, RungeKuttaStepInterpolator prototype, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        this.fsal = fsal;
        this.c = c;
        this.a = a;
        this.b = b;
        this.prototype = prototype;
        this.exp = -1.0d / ((double) getOrder());
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS);
    }

    public double getSafety() {
        return this.safety;
    }

    public void setSafety(double safety) {
        this.safety = safety;
    }

    public void integrate(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException {
        sanityChecks(equations, t);
        setEquations(equations);
        boolean forward = t > equations.getTime();
        double[] y0 = equations.getCompleteState();
        Object y = (double[]) y0.clone();
        int stages = this.c.length + 1;
        double[][] yDotK = (double[][]) Array.newInstance(Double.TYPE, new int[]{stages, y.length});
        double[] yTmp = (double[]) y0.clone();
        Object yDotTmp = new double[y.length];
        AbstractStepInterpolator interpolator = (RungeKuttaStepInterpolator) this.prototype.copy();
        interpolator.reinitialize(this, yTmp, yDotK, forward, equations.getPrimaryMapper(), equations.getSecondaryMappers());
        interpolator.storeTime(equations.getTime());
        this.stepStart = equations.getTime();
        double hNew = 0.0d;
        boolean firstTime = true;
        initIntegration(equations.getTime(), y0, t);
        this.isLastStep = false;
        do {
            interpolator.shift();
            double error = BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
            while (error >= 1.0d) {
                int l;
                if (firstTime || !this.fsal) {
                    computeDerivatives(this.stepStart, y, yDotK[0]);
                }
                if (firstTime) {
                    double[] scale = new double[this.mainSetDimension];
                    int i;
                    if (this.vecAbsoluteTolerance == null) {
                        for (i = 0; i < scale.length; i++) {
                            scale[i] = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * FastMath.abs(y[i]));
                        }
                    } else {
                        for (i = 0; i < scale.length; i++) {
                            scale[i] = this.vecAbsoluteTolerance[i] + (this.vecRelativeTolerance[i] * FastMath.abs(y[i]));
                        }
                    }
                    hNew = initializeStep(forward, getOrder(), scale, this.stepStart, y, yDotK[0], yTmp, yDotK[1]);
                    firstTime = false;
                }
                this.stepSize = hNew;
                if (forward) {
                    if (this.stepStart + this.stepSize >= t) {
                        this.stepSize = t - this.stepStart;
                    }
                } else if (this.stepStart + this.stepSize <= t) {
                    this.stepSize = t - this.stepStart;
                }
                for (int k = 1; k < stages; k++) {
                    int j;
                    for (j = 0; j < y0.length; j++) {
                        double sum = this.a[k - 1][0] * yDotK[0][j];
                        for (l = 1; l < k; l++) {
                            sum += this.a[k - 1][l] * yDotK[l][j];
                        }
                        yTmp[j] = y[j] + (this.stepSize * sum);
                    }
                    computeDerivatives(this.stepStart + (this.c[k - 1] * this.stepSize), yTmp, yDotK[k]);
                }
                for (j = 0; j < y0.length; j++) {
                    sum = this.b[0] * yDotK[0][j];
                    for (l = 1; l < stages; l++) {
                        sum += this.b[l] * yDotK[l][j];
                    }
                    yTmp[j] = y[j] + (this.stepSize * sum);
                }
                error = estimateError(yDotK, y, yTmp, this.stepSize);
                if (error >= 1.0d) {
                    hNew = filterStep(this.stepSize * FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp))), forward, false);
                }
            }
            interpolator.storeTime(this.stepStart + this.stepSize);
            System.arraycopy(yTmp, 0, y, 0, y0.length);
            System.arraycopy(yDotK[stages - 1], 0, yDotTmp, 0, y0.length);
            this.stepStart = acceptStep(interpolator, y, yDotTmp, t);
            System.arraycopy(y, 0, yTmp, 0, y.length);
            if (!this.isLastStep) {
                interpolator.storeTime(this.stepStart);
                if (this.fsal) {
                    System.arraycopy(yDotTmp, 0, yDotK[0], 0, y0.length);
                }
                double scaledH = this.stepSize * FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
                double nextT = this.stepStart + scaledH;
                boolean nextIsLast = forward ? nextT >= t : nextT <= t;
                hNew = filterStep(scaledH, forward, nextIsLast);
                double filteredNextT = this.stepStart + hNew;
                boolean filteredNextIsLast = forward ? filteredNextT >= t : filteredNextT <= t;
                if (filteredNextIsLast) {
                    hNew = t - this.stepStart;
                }
            }
        } while (!this.isLastStep);
        equations.setTime(this.stepStart);
        equations.setCompleteState(y);
        resetInternalState();
    }

    public double getMinReduction() {
        return this.minReduction;
    }

    public void setMinReduction(double minReduction) {
        this.minReduction = minReduction;
    }

    public double getMaxGrowth() {
        return this.maxGrowth;
    }

    public void setMaxGrowth(double maxGrowth) {
        this.maxGrowth = maxGrowth;
    }
}
