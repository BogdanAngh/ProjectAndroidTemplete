package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.distribution.AbstractRealDistribution;

public class IllinoisSolver extends BaseSecantSolver {
    public IllinoisSolver() {
        super(AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY, Method.ILLINOIS);
    }

    public IllinoisSolver(double absoluteAccuracy) {
        super(absoluteAccuracy, Method.ILLINOIS);
    }

    public IllinoisSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, Method.ILLINOIS);
    }

    public IllinoisSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, Method.PEGASUS);
    }
}
