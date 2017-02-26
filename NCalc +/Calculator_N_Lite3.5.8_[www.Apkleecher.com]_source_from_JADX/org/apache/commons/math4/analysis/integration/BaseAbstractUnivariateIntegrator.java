package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.MathUtils;

public abstract class BaseAbstractUnivariateIntegrator implements UnivariateIntegrator {
    public static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-15d;
    public static final int DEFAULT_MAX_ITERATIONS_COUNT = Integer.MAX_VALUE;
    public static final int DEFAULT_MIN_ITERATIONS_COUNT = 3;
    public static final double DEFAULT_RELATIVE_ACCURACY = 1.0E-6d;
    private final double absoluteAccuracy;
    private final Incrementor evaluations;
    private UnivariateFunction function;
    protected final Incrementor iterations;
    private double max;
    private double min;
    private final int minimalIterationCount;
    private final double relativeAccuracy;

    protected abstract double doIntegrate() throws TooManyEvaluationsException, MaxCountExceededException;

    protected BaseAbstractUnivariateIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
        this.relativeAccuracy = relativeAccuracy;
        this.absoluteAccuracy = absoluteAccuracy;
        if (minimalIterationCount <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(minimalIterationCount));
        } else if (maximalIterationCount <= minimalIterationCount) {
            throw new NumberIsTooSmallException(Integer.valueOf(maximalIterationCount), Integer.valueOf(minimalIterationCount), false);
        } else {
            this.minimalIterationCount = minimalIterationCount;
            this.iterations = new Incrementor();
            this.iterations.setMaximalCount(maximalIterationCount);
            this.evaluations = new Incrementor();
        }
    }

    protected BaseAbstractUnivariateIntegrator(double relativeAccuracy, double absoluteAccuracy) {
        this(relativeAccuracy, absoluteAccuracy, DEFAULT_MIN_ITERATIONS_COUNT, DEFAULT_MAX_ITERATIONS_COUNT);
    }

    protected BaseAbstractUnivariateIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException {
        this(DEFAULT_RELATIVE_ACCURACY, DEFAULT_ABSOLUTE_ACCURACY, minimalIterationCount, maximalIterationCount);
    }

    public double getRelativeAccuracy() {
        return this.relativeAccuracy;
    }

    public double getAbsoluteAccuracy() {
        return this.absoluteAccuracy;
    }

    public int getMinimalIterationCount() {
        return this.minimalIterationCount;
    }

    public int getMaximalIterationCount() {
        return this.iterations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    public int getIterations() {
        return this.iterations.getCount();
    }

    protected double getMin() {
        return this.min;
    }

    protected double getMax() {
        return this.max;
    }

    protected double computeObjectiveValue(double point) throws TooManyEvaluationsException {
        try {
            this.evaluations.incrementCount();
            return this.function.value(point);
        } catch (MaxCountExceededException e) {
            throw new TooManyEvaluationsException(e.getMax());
        }
    }

    protected void setup(int maxEval, UnivariateFunction f, double lower, double upper) throws NullArgumentException, MathIllegalArgumentException {
        MathUtils.checkNotNull(f);
        UnivariateSolverUtils.verifyInterval(lower, upper);
        this.min = lower;
        this.max = upper;
        this.function = f;
        this.evaluations.setMaximalCount(maxEval);
        this.evaluations.resetCount();
        this.iterations.resetCount();
    }

    public double integrate(int maxEval, UnivariateFunction f, double lower, double upper) throws TooManyEvaluationsException, MaxCountExceededException, MathIllegalArgumentException, NullArgumentException {
        setup(maxEval, f, lower, upper);
        return doIntegrate();
    }
}
