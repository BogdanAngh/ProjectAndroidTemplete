package org.apache.commons.math4.optim;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.exception.TooManyIterationsException;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.Incrementor.MaxCountExceededCallback;

public abstract class BaseOptimizer<PAIR> {
    private final ConvergenceChecker<PAIR> checker;
    protected final Incrementor evaluations;
    protected final Incrementor iterations;

    private static class MaxEvalCallback implements MaxCountExceededCallback {
        private MaxEvalCallback() {
        }

        public void trigger(int max) {
            throw new TooManyEvaluationsException(Integer.valueOf(max));
        }
    }

    private static class MaxIterCallback implements MaxCountExceededCallback {
        private MaxIterCallback() {
        }

        public void trigger(int max) {
            throw new TooManyIterationsException(Integer.valueOf(max));
        }
    }

    protected abstract PAIR doOptimize();

    protected BaseOptimizer(ConvergenceChecker<PAIR> checker) {
        this(checker, 0, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    protected BaseOptimizer(ConvergenceChecker<PAIR> checker, int maxEval, int maxIter) {
        this.checker = checker;
        this.evaluations = new Incrementor(maxEval, new MaxEvalCallback());
        this.iterations = new Incrementor(maxIter, new MaxIterCallback());
    }

    public int getMaxEvaluations() {
        return this.evaluations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    public int getMaxIterations() {
        return this.iterations.getMaximalCount();
    }

    public int getIterations() {
        return this.iterations.getCount();
    }

    public ConvergenceChecker<PAIR> getConvergenceChecker() {
        return this.checker;
    }

    public PAIR optimize(OptimizationData... optData) throws TooManyEvaluationsException, TooManyIterationsException {
        parseOptimizationData(optData);
        this.evaluations.resetCount();
        this.iterations.resetCount();
        return doOptimize();
    }

    public PAIR optimize() throws TooManyEvaluationsException, TooManyIterationsException {
        this.evaluations.resetCount();
        this.iterations.resetCount();
        return doOptimize();
    }

    protected void incrementEvaluationCount() throws TooManyEvaluationsException {
        this.evaluations.incrementCount();
    }

    protected void incrementIterationCount() throws TooManyIterationsException {
        this.iterations.incrementCount();
    }

    protected void parseOptimizationData(OptimizationData... optData) {
        for (OptimizationData data : optData) {
            if (data instanceof MaxEval) {
                this.evaluations.setMaximalCount(((MaxEval) data).getMaxEval());
            } else if (data instanceof MaxIter) {
                this.iterations.setMaximalCount(((MaxIter) data).getMaxIter());
            }
        }
    }
}
