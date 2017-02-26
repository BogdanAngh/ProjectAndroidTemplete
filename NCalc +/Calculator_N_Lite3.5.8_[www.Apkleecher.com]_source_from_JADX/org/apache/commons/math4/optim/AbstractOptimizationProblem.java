package org.apache.commons.math4.optim;

import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.exception.TooManyIterationsException;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.Incrementor.MaxCountExceededCallback;

public abstract class AbstractOptimizationProblem<PAIR> implements OptimizationProblem<PAIR> {
    private static final MaxEvalCallback MAX_EVAL_CALLBACK;
    private static final MaxIterCallback MAX_ITER_CALLBACK;
    private final ConvergenceChecker<PAIR> checker;
    private final int maxEvaluations;
    private final int maxIterations;

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

    static {
        MAX_EVAL_CALLBACK = new MaxEvalCallback();
        MAX_ITER_CALLBACK = new MaxIterCallback();
    }

    protected AbstractOptimizationProblem(int maxEvaluations, int maxIterations, ConvergenceChecker<PAIR> checker) {
        this.maxEvaluations = maxEvaluations;
        this.maxIterations = maxIterations;
        this.checker = checker;
    }

    public Incrementor getEvaluationCounter() {
        return new Incrementor(this.maxEvaluations, MAX_EVAL_CALLBACK);
    }

    public Incrementor getIterationCounter() {
        return new Incrementor(this.maxIterations, MAX_ITER_CALLBACK);
    }

    public ConvergenceChecker<PAIR> getConvergenceChecker() {
        return this.checker;
    }
}
