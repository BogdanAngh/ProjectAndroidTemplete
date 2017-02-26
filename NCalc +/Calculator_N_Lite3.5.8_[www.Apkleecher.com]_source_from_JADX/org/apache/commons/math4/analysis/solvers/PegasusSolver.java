package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.distribution.AbstractRealDistribution;

public class PegasusSolver extends BaseSecantSolver {
    public PegasusSolver() {
        super(AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY, Method.PEGASUS);
    }

    public PegasusSolver(double absoluteAccuracy) {
        super(absoluteAccuracy, Method.PEGASUS);
    }

    public PegasusSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, Method.PEGASUS);
    }

    public PegasusSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, Method.PEGASUS);
    }
}
