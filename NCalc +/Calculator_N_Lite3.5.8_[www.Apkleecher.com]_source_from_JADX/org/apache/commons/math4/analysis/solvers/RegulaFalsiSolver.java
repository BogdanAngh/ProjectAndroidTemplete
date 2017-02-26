package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.distribution.AbstractRealDistribution;

public class RegulaFalsiSolver extends BaseSecantSolver {
    public RegulaFalsiSolver() {
        super(AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY, Method.REGULA_FALSI);
    }

    public RegulaFalsiSolver(double absoluteAccuracy) {
        super(absoluteAccuracy, Method.REGULA_FALSI);
    }

    public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, Method.REGULA_FALSI);
    }

    public RegulaFalsiSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy, Method.REGULA_FALSI);
    }
}
