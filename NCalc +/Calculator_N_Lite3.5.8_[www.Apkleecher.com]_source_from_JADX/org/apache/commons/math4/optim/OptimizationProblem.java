package org.apache.commons.math4.optim;

import org.apache.commons.math4.util.Incrementor;

public interface OptimizationProblem<PAIR> {
    ConvergenceChecker<PAIR> getConvergenceChecker();

    Incrementor getEvaluationCounter();

    Incrementor getIterationCounter();
}
