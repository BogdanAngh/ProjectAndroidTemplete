package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.ExceptionContext;
import org.apache.commons.math4.util.IterationManager;

public class ConjugateGradient extends PreconditionedIterativeLinearSolver {
    public static final String OPERATOR = "operator";
    public static final String VECTOR = "vector";
    private boolean check;
    private final double delta;

    public ConjugateGradient(int maxIterations, double delta, boolean check) {
        super(maxIterations);
        this.delta = delta;
        this.check = check;
    }

    public ConjugateGradient(IterationManager manager, double delta, boolean check) throws NullArgumentException {
        super(manager);
        this.delta = delta;
        this.check = check;
    }

    public final boolean getCheck() {
        return this.check;
    }

    public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x0) throws NullArgumentException, NonPositiveDefiniteOperatorException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        RealVector z;
        PreconditionedIterativeLinearSolver.checkParameters(a, m, b, x0);
        IterationManager manager = getIterationManager();
        manager.resetIterationCount();
        double rmax = this.delta * b.getNorm();
        RealVector bro = RealVector.unmodifiableRealVector(b);
        manager.incrementIterationCount();
        RealVector x = x0;
        RealVector xro = RealVector.unmodifiableRealVector(x);
        RealVector p = x.copy();
        RealVector r = b.combine(1.0d, -1.0d, a.operate(p));
        RealVector rro = RealVector.unmodifiableRealVector(r);
        double rnorm = r.getNorm();
        if (m == null) {
            z = r;
        } else {
            z = null;
        }
        IterativeLinearSolverEvent evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
        manager.fireInitializationEvent(evt);
        if (rnorm <= rmax) {
            manager.fireTerminationEvent(evt);
        } else {
            double rhoPrev = 0.0d;
            do {
                manager.incrementIterationCount();
                manager.fireIterationStartedEvent(new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm));
                if (m != null) {
                    z = m.operate(r);
                }
                double rhoNext = r.dotProduct(z);
                NonPositiveDefiniteOperatorException e;
                ExceptionContext context;
                if (!this.check || rhoNext > 0.0d) {
                    if (manager.getIterations() == 2) {
                        p.setSubVector(0, z);
                    } else {
                        p.combineToSelf(rhoNext / rhoPrev, 1.0d, z);
                    }
                    RealVector q = a.operate(p);
                    double pq = p.dotProduct(q);
                    if (!this.check || pq > 0.0d) {
                        double alpha = rhoNext / pq;
                        x.combineToSelf(1.0d, alpha, p);
                        r.combineToSelf(1.0d, -alpha, q);
                        rhoPrev = rhoNext;
                        rnorm = r.getNorm();
                        evt = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), xro, bro, rro, rnorm);
                        manager.fireIterationPerformedEvent(evt);
                    } else {
                        e = new NonPositiveDefiniteOperatorException();
                        context = e.getContext();
                        context.setValue(OPERATOR, a);
                        context.setValue(VECTOR, p);
                        throw e;
                    }
                }
                e = new NonPositiveDefiniteOperatorException();
                context = e.getContext();
                context.setValue(OPERATOR, m);
                context.setValue(VECTOR, r);
                throw e;
            } while (rnorm > rmax);
            manager.fireTerminationEvent(evt);
        }
        return x;
    }
}
