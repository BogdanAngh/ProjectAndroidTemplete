package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresOptimizer.Optimum;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.linear.LUDecomposition;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.NonPositiveDefiniteMatrixException;
import org.apache.commons.math4.linear.QRDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.linear.SingularMatrixException;
import org.apache.commons.math4.linear.SingularValueDecomposition;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.Pair;

public class GaussNewtonOptimizer implements LeastSquaresOptimizer {
    private static final double SINGULARITY_THRESHOLD = 1.0E-11d;
    private final Decomposition decomposition;

    public enum Decomposition {
        LU {
            protected RealVector solve(RealMatrix jacobian, RealVector residuals) {
                try {
                    Pair<RealMatrix, RealVector> normalEquation = GaussNewtonOptimizer.computeNormalMatrix(jacobian, residuals);
                    return new LUDecomposition((RealMatrix) normalEquation.getFirst(), GaussNewtonOptimizer.SINGULARITY_THRESHOLD).getSolver().solve((RealVector) normalEquation.getSecond());
                } catch (SingularMatrixException e) {
                    throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, e);
                }
            }
        },
        QR {
            protected RealVector solve(RealMatrix jacobian, RealVector residuals) {
                try {
                    return new QRDecomposition(jacobian, GaussNewtonOptimizer.SINGULARITY_THRESHOLD).getSolver().solve(residuals);
                } catch (SingularMatrixException e) {
                    throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, e);
                }
            }
        },
        CHOLESKY {
            protected RealVector solve(RealMatrix jacobian, RealVector residuals) {
                try {
                    Pair<RealMatrix, RealVector> normalEquation = GaussNewtonOptimizer.computeNormalMatrix(jacobian, residuals);
                    return new CholeskyDecomposition((RealMatrix) normalEquation.getFirst(), GaussNewtonOptimizer.SINGULARITY_THRESHOLD, GaussNewtonOptimizer.SINGULARITY_THRESHOLD).getSolver().solve((RealVector) normalEquation.getSecond());
                } catch (NonPositiveDefiniteMatrixException e) {
                    throw new ConvergenceException(LocalizedFormats.UNABLE_TO_SOLVE_SINGULAR_PROBLEM, e);
                }
            }
        },
        SVD {
            protected RealVector solve(RealMatrix jacobian, RealVector residuals) {
                return new SingularValueDecomposition(jacobian).getSolver().solve(residuals);
            }
        };

        protected abstract RealVector solve(RealMatrix realMatrix, RealVector realVector);
    }

    public GaussNewtonOptimizer() {
        this(Decomposition.QR);
    }

    public GaussNewtonOptimizer(Decomposition decomposition) {
        this.decomposition = decomposition;
    }

    public Decomposition getDecomposition() {
        return this.decomposition;
    }

    public GaussNewtonOptimizer withDecomposition(Decomposition newDecomposition) {
        return new GaussNewtonOptimizer(newDecomposition);
    }

    public Optimum optimize(LeastSquaresProblem lsp) {
        Incrementor evaluationCounter = lsp.getEvaluationCounter();
        Incrementor iterationCounter = lsp.getIterationCounter();
        ConvergenceChecker<Evaluation> checker = lsp.getConvergenceChecker();
        if (checker == null) {
            throw new NullArgumentException();
        }
        RealVector currentPoint = lsp.getStart();
        Evaluation current = null;
        while (true) {
            iterationCounter.incrementCount();
            Evaluation previous = current;
            evaluationCounter.incrementCount();
            current = lsp.evaluate(currentPoint);
            RealVector currentResiduals = current.getResiduals();
            RealMatrix weightedJacobian = current.getJacobian();
            currentPoint = current.getPoint();
            if (previous != null && checker.converged(iterationCounter.getCount(), previous, current)) {
                return new OptimumImpl(current, evaluationCounter.getCount(), iterationCounter.getCount());
            }
            currentPoint = currentPoint.add(this.decomposition.solve(weightedJacobian, currentResiduals));
        }
    }

    public String toString() {
        return "GaussNewtonOptimizer{decomposition=" + this.decomposition + '}';
    }

    private static Pair<RealMatrix, RealVector> computeNormalMatrix(RealMatrix jacobian, RealVector residuals) {
        int i;
        int j;
        int nR = jacobian.getRowDimension();
        int nC = jacobian.getColumnDimension();
        RealMatrix normal = MatrixUtils.createRealMatrix(nC, nC);
        RealVector jTr = new ArrayRealVector(nC);
        for (i = 0; i < nR; i++) {
            for (j = 0; j < nC; j++) {
                jTr.setEntry(j, jTr.getEntry(j) + (residuals.getEntry(i) * jacobian.getEntry(i, j)));
            }
            for (int k = 0; k < nC; k++) {
                for (int l = k; l < nC; l++) {
                    normal.setEntry(k, l, normal.getEntry(k, l) + (jacobian.getEntry(i, k) * jacobian.getEntry(i, l)));
                }
            }
        }
        for (i = 0; i < nC; i++) {
            for (j = 0; j < i; j++) {
                normal.setEntry(i, j, normal.getEntry(j, i));
            }
        }
        return new Pair(normal, jTr);
    }
}
