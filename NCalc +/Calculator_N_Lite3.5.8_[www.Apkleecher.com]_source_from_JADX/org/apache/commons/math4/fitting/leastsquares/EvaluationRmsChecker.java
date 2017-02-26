package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.util.Precision;

public class EvaluationRmsChecker implements ConvergenceChecker<Evaluation> {
    private final double absTol;
    private final double relTol;

    public EvaluationRmsChecker(double tol) {
        this(tol, tol);
    }

    public EvaluationRmsChecker(double relTol, double absTol) {
        this.relTol = relTol;
        this.absTol = absTol;
    }

    public boolean converged(int iteration, Evaluation previous, Evaluation current) {
        double prevRms = previous.getRMS();
        double currRms = current.getRMS();
        return Precision.equals(prevRms, currRms, this.absTol) || Precision.equalsWithRelativeTolerance(prevRms, currRms, this.relTol);
    }
}
