package org.apache.commons.math4.ode;

import java.lang.reflect.Array;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.ode.nonstiff.AdaptiveStepsizeIntegrator;
import org.apache.commons.math4.ode.nonstiff.DormandPrince853Integrator;
import org.apache.commons.math4.ode.sampling.StepHandler;
import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

public abstract class MultistepIntegrator extends AdaptiveStepsizeIntegrator {
    private double exp;
    private double maxGrowth;
    private double minReduction;
    private final int nSteps;
    protected Array2DRowRealMatrix nordsieck;
    private double safety;
    protected double[] scaled;
    private FirstOrderIntegrator starter;

    class 1 implements FirstOrderDifferentialEquations {
        1() {
        }

        public int getDimension() {
            return MultistepIntegrator.this.getExpandable().getTotalDimension();
        }

        public void computeDerivatives(double t, double[] y, double[] yDot) {
            MultistepIntegrator.this.getExpandable().computeDerivatives(t, y, yDot);
        }
    }

    private static class InitializationCompletedMarkerException extends RuntimeException {
        private static final long serialVersionUID = -1914085471038046418L;

        public InitializationCompletedMarkerException() {
            super(null);
        }
    }

    private class NordsieckInitializer implements StepHandler {
        private int count;
        private final double[] t;
        private final double[][] y;
        private final double[][] yDot;

        public NordsieckInitializer(int nSteps, int n) {
            this.count = 0;
            this.t = new double[nSteps];
            this.y = (double[][]) Array.newInstance(Double.TYPE, new int[]{nSteps, n});
            this.yDot = (double[][]) Array.newInstance(Double.TYPE, new int[]{nSteps, n});
        }

        public void handleStep(StepInterpolator interpolator, boolean isLast) throws MaxCountExceededException {
            ExpandableStatefulODE expandable;
            EquationsMapper primary;
            int index;
            double prev = interpolator.getPreviousTime();
            double curr = interpolator.getCurrentTime();
            if (this.count == 0) {
                interpolator.setInterpolatedTime(prev);
                this.t[0] = prev;
                expandable = MultistepIntegrator.this.getExpandable();
                primary = expandable.getPrimaryMapper();
                primary.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
                primary.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
                index = 0;
                for (EquationsMapper secondary : expandable.getSecondaryMappers()) {
                    secondary.insertEquationData(interpolator.getInterpolatedSecondaryState(index), this.y[this.count]);
                    secondary.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(index), this.yDot[this.count]);
                    index++;
                }
            }
            this.count++;
            interpolator.setInterpolatedTime(curr);
            this.t[this.count] = curr;
            expandable = MultistepIntegrator.this.getExpandable();
            primary = expandable.getPrimaryMapper();
            primary.insertEquationData(interpolator.getInterpolatedState(), this.y[this.count]);
            primary.insertEquationData(interpolator.getInterpolatedDerivatives(), this.yDot[this.count]);
            index = 0;
            for (EquationsMapper secondary2 : expandable.getSecondaryMappers()) {
                secondary2.insertEquationData(interpolator.getInterpolatedSecondaryState(index), this.y[this.count]);
                secondary2.insertEquationData(interpolator.getInterpolatedSecondaryDerivatives(index), this.yDot[this.count]);
                index++;
            }
            if (this.count == this.t.length - 1) {
                MultistepIntegrator.this.stepStart = this.t[0];
                MultistepIntegrator.this.stepSize = (this.t[this.t.length - 1] - this.t[0]) / ((double) (this.t.length - 1));
                MultistepIntegrator.this.scaled = (double[]) this.yDot[0].clone();
                for (int j = 0; j < MultistepIntegrator.this.scaled.length; j++) {
                    double[] dArr = MultistepIntegrator.this.scaled;
                    dArr[j] = dArr[j] * MultistepIntegrator.this.stepSize;
                }
                MultistepIntegrator.this.nordsieck = MultistepIntegrator.this.initializeHighOrderDerivatives(MultistepIntegrator.this.stepSize, this.t, this.y, this.yDot);
                throw new InitializationCompletedMarkerException();
            }
        }

        public void init(double t0, double[] y0, double time) {
        }
    }

    public interface NordsieckTransformer {
        Array2DRowRealMatrix initializeHighOrderDerivatives(double d, double[] dArr, double[][] dArr2, double[][] dArr3);
    }

    protected abstract Array2DRowRealMatrix initializeHighOrderDerivatives(double d, double[] dArr, double[][] dArr2, double[][] dArr3);

    protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) throws NumberIsTooSmallException {
        super(name, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        if (nSteps < 2) {
            throw new NumberIsTooSmallException(LocalizedFormats.INTEGRATION_METHOD_NEEDS_AT_LEAST_TWO_PREVIOUS_POINTS, Integer.valueOf(nSteps), Integer.valueOf(2), true);
        }
        this.starter = new DormandPrince853Integrator(minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        this.nSteps = nSteps;
        this.exp = -1.0d / ((double) order);
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(FastMath.pow(2.0d, -this.exp));
    }

    protected MultistepIntegrator(String name, int nSteps, int order, double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(name, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        this.starter = new DormandPrince853Integrator(minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        this.nSteps = nSteps;
        this.exp = -1.0d / ((double) order);
        setSafety(0.9d);
        setMinReduction(0.2d);
        setMaxGrowth(FastMath.pow(2.0d, -this.exp));
    }

    public ODEIntegrator getStarterIntegrator() {
        return this.starter;
    }

    public void setStarterIntegrator(FirstOrderIntegrator starterIntegrator) {
        this.starter = starterIntegrator;
    }

    protected void start(double t0, double[] y0, double t) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
        this.starter.clearEventHandlers();
        this.starter.clearStepHandlers();
        this.starter.addStepHandler(new NordsieckInitializer(this.nSteps, y0.length));
        try {
            if (this.starter instanceof AbstractIntegrator) {
                ((AbstractIntegrator) this.starter).integrate(getExpandable(), t);
            } else {
                this.starter.integrate(new 1(), t0, y0, t, new double[y0.length]);
            }
        } catch (InitializationCompletedMarkerException e) {
            getEvaluationsCounter().incrementCount(this.starter.getEvaluations());
        }
        this.starter.clearStepHandlers();
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

    public double getSafety() {
        return this.safety;
    }

    public void setSafety(double safety) {
        this.safety = safety;
    }

    protected double computeStepGrowShrinkFactor(double error) {
        return FastMath.min(this.maxGrowth, FastMath.max(this.minReduction, this.safety * FastMath.pow(error, this.exp)));
    }
}
