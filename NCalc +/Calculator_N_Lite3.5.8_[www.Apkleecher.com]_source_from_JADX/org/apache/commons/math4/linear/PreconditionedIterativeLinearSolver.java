package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.util.IterationManager;
import org.apache.commons.math4.util.MathUtils;

public abstract class PreconditionedIterativeLinearSolver extends IterativeLinearSolver {
    public abstract RealVector solveInPlace(RealLinearOperator realLinearOperator, RealLinearOperator realLinearOperator2, RealVector realVector, RealVector realVector2) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException;

    public PreconditionedIterativeLinearSolver(int maxIterations) {
        super(maxIterations);
    }

    public PreconditionedIterativeLinearSolver(IterationManager manager) throws NullArgumentException {
        super(manager);
    }

    public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        MathUtils.checkNotNull(x0);
        return solveInPlace(a, m, b, x0.copy());
    }

    public RealVector solve(RealLinearOperator a, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        MathUtils.checkNotNull(a);
        RealVector x = new ArrayRealVector(a.getColumnDimension());
        x.set(0.0d);
        return solveInPlace(a, null, b, x);
    }

    public RealVector solve(RealLinearOperator a, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        MathUtils.checkNotNull(x0);
        return solveInPlace(a, null, b, x0.copy());
    }

    protected static void checkParameters(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException {
        IterativeLinearSolver.checkParameters(a, b, x0);
        if (m == null) {
            return;
        }
        if (m.getColumnDimension() != m.getRowDimension()) {
            throw new NonSquareOperatorException(m.getColumnDimension(), m.getRowDimension());
        } else if (m.getRowDimension() != a.getRowDimension()) {
            throw new DimensionMismatchException(m.getRowDimension(), a.getRowDimension());
        }
    }

    public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        MathUtils.checkNotNull(a);
        return solveInPlace(a, m, b, new ArrayRealVector(a.getColumnDimension()));
    }

    public RealVector solveInPlace(RealLinearOperator a, RealVector b, RealVector x0) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException {
        return solveInPlace(a, null, b, x0);
    }
}
