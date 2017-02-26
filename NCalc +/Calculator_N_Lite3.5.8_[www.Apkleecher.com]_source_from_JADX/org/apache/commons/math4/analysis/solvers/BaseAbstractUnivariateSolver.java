package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.MathUtils;

public abstract class BaseAbstractUnivariateSolver<FUNC extends UnivariateFunction> implements BaseUnivariateSolver<FUNC> {
    private static final double DEFAULT_FUNCTION_VALUE_ACCURACY = 1.0E-15d;
    private static final double DEFAULT_RELATIVE_ACCURACY = 1.0E-14d;
    private final double absoluteAccuracy;
    private final Incrementor evaluations;
    private FUNC function;
    private final double functionValueAccuracy;
    private final double relativeAccuracy;
    private double searchMax;
    private double searchMin;
    private double searchStart;

    protected abstract double doSolve() throws TooManyEvaluationsException, NoBracketingException;

    protected BaseAbstractUnivariateSolver(double absoluteAccuracy) {
        this(DEFAULT_RELATIVE_ACCURACY, absoluteAccuracy, DEFAULT_FUNCTION_VALUE_ACCURACY);
    }

    protected BaseAbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy) {
        this(relativeAccuracy, absoluteAccuracy, DEFAULT_FUNCTION_VALUE_ACCURACY);
    }

    protected BaseAbstractUnivariateSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        this.evaluations = new Incrementor();
        this.absoluteAccuracy = absoluteAccuracy;
        this.relativeAccuracy = relativeAccuracy;
        this.functionValueAccuracy = functionValueAccuracy;
    }

    public int getMaxEvaluations() {
        return this.evaluations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    public double getMin() {
        return this.searchMin;
    }

    public double getMax() {
        return this.searchMax;
    }

    public double getStartValue() {
        return this.searchStart;
    }

    public double getAbsoluteAccuracy() {
        return this.absoluteAccuracy;
    }

    public double getRelativeAccuracy() {
        return this.relativeAccuracy;
    }

    public double getFunctionValueAccuracy() {
        return this.functionValueAccuracy;
    }

    protected double computeObjectiveValue(double point) throws TooManyEvaluationsException {
        incrementEvaluationCount();
        return this.function.value(point);
    }

    protected void setup(int maxEval, FUNC f, double min, double max, double startValue) throws NullArgumentException {
        MathUtils.checkNotNull(f);
        this.searchMin = min;
        this.searchMax = max;
        this.searchStart = startValue;
        this.function = f;
        this.evaluations.setMaximalCount(maxEval);
        this.evaluations.resetCount();
    }

    public double solve(int maxEval, FUNC f, double min, double max, double startValue) throws TooManyEvaluationsException, NoBracketingException {
        setup(maxEval, f, min, max, startValue);
        return doSolve();
    }

    public double solve(int maxEval, FUNC f, double min, double max) {
        return solve(maxEval, f, min, max, min + (0.5d * (max - min)));
    }

    public double solve(int maxEval, FUNC f, double startValue) throws TooManyEvaluationsException, NoBracketingException {
        return solve(maxEval, f, Double.NaN, Double.NaN, startValue);
    }

    protected boolean isBracketing(double lower, double upper) {
        return UnivariateSolverUtils.isBracketing(this.function, lower, upper);
    }

    protected boolean isSequence(double start, double mid, double end) {
        return UnivariateSolverUtils.isSequence(start, mid, end);
    }

    protected void verifyInterval(double lower, double upper) throws NumberIsTooLargeException {
        UnivariateSolverUtils.verifyInterval(lower, upper);
    }

    protected void verifySequence(double lower, double initial, double upper) throws NumberIsTooLargeException {
        UnivariateSolverUtils.verifySequence(lower, initial, upper);
    }

    protected void verifyBracketing(double lower, double upper) throws NullArgumentException, NoBracketingException {
        UnivariateSolverUtils.verifyBracketing(this.function, lower, upper);
    }

    protected void incrementEvaluationCount() throws TooManyEvaluationsException {
        try {
            this.evaluations.incrementCount();
        } catch (MaxCountExceededException e) {
            throw new TooManyEvaluationsException(e.getMax());
        }
    }
}
