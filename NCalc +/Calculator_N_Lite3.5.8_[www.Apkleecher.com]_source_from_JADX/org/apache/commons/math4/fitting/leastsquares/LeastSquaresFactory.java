package org.apache.commons.math4.fitting.leastsquares;

import org.apache.commons.math4.analysis.MultivariateMatrixFunction;
import org.apache.commons.math4.analysis.MultivariateVectorFunction;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem.Evaluation;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.DiagonalMatrix;
import org.apache.commons.math4.linear.EigenDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.optim.AbstractOptimizationProblem;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.PointVectorValuePair;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.Pair;

public class LeastSquaresFactory {

    class 1 extends LeastSquaresAdapter {
        private final /* synthetic */ RealMatrix val$weightSquareRoot;

        1(LeastSquaresProblem $anonymous0, RealMatrix realMatrix) {
            this.val$weightSquareRoot = realMatrix;
            super($anonymous0);
        }

        public Evaluation evaluate(RealVector point) {
            return new DenseWeightedEvaluation(super.evaluate(point), this.val$weightSquareRoot);
        }
    }

    class 2 extends LeastSquaresAdapter {
        private final /* synthetic */ Incrementor val$counter;

        2(LeastSquaresProblem $anonymous0, Incrementor incrementor) {
            this.val$counter = incrementor;
            super($anonymous0);
        }

        public Evaluation evaluate(RealVector point) {
            this.val$counter.incrementCount();
            return super.evaluate(point);
        }
    }

    class 3 implements ConvergenceChecker<Evaluation> {
        private final /* synthetic */ ConvergenceChecker val$checker;

        3(ConvergenceChecker convergenceChecker) {
            this.val$checker = convergenceChecker;
        }

        public boolean converged(int iteration, Evaluation previous, Evaluation current) {
            return this.val$checker.converged(iteration, new PointVectorValuePair(previous.getPoint().toArray(), previous.getResiduals().toArray(), false), new PointVectorValuePair(current.getPoint().toArray(), current.getResiduals().toArray(), false));
        }
    }

    private static class LocalLeastSquaresProblem extends AbstractOptimizationProblem<Evaluation> implements LeastSquaresProblem {
        private final boolean lazyEvaluation;
        private final MultivariateJacobianFunction model;
        private final ParameterValidator paramValidator;
        private final RealVector start;
        private final RealVector target;

        private static class LazyUnweightedEvaluation extends AbstractEvaluation {
            private final ValueAndJacobianFunction model;
            private final RealVector point;
            private final RealVector target;

            private LazyUnweightedEvaluation(ValueAndJacobianFunction model, RealVector target, RealVector point) {
                super(target.getDimension());
                this.model = model;
                this.point = point;
                this.target = target;
            }

            public RealMatrix getJacobian() {
                return this.model.computeJacobian(this.point.toArray());
            }

            public RealVector getPoint() {
                return this.point;
            }

            public RealVector getResiduals() {
                return this.target.subtract(this.model.computeValue(this.point.toArray()));
            }
        }

        private static class UnweightedEvaluation extends AbstractEvaluation {
            private final RealMatrix jacobian;
            private final RealVector point;
            private final RealVector residuals;

            private UnweightedEvaluation(RealVector values, RealMatrix jacobian, RealVector target, RealVector point) {
                super(target.getDimension());
                this.jacobian = jacobian;
                this.point = point;
                this.residuals = target.subtract(values);
            }

            public RealMatrix getJacobian() {
                return this.jacobian;
            }

            public RealVector getPoint() {
                return this.point;
            }

            public RealVector getResiduals() {
                return this.residuals;
            }
        }

        LocalLeastSquaresProblem(MultivariateJacobianFunction model, RealVector target, RealVector start, ConvergenceChecker<Evaluation> checker, int maxEvaluations, int maxIterations, boolean lazyEvaluation, ParameterValidator paramValidator) {
            super(maxEvaluations, maxIterations, checker);
            this.target = target;
            this.model = model;
            this.start = start;
            this.lazyEvaluation = lazyEvaluation;
            this.paramValidator = paramValidator;
            if (lazyEvaluation && !(model instanceof ValueAndJacobianFunction)) {
                throw new MathIllegalStateException(LocalizedFormats.INVALID_IMPLEMENTATION, model.getClass().getName());
            }
        }

        public int getObservationSize() {
            return this.target.getDimension();
        }

        public int getParameterSize() {
            return this.start.getDimension();
        }

        public RealVector getStart() {
            return this.start == null ? null : this.start.copy();
        }

        public Evaluation evaluate(RealVector point) {
            RealVector p;
            if (this.paramValidator == null) {
                p = point.copy();
            } else {
                p = this.paramValidator.validate(point.copy());
            }
            if (this.lazyEvaluation) {
                return new LazyUnweightedEvaluation(this.target, p, null);
            }
            Pair<RealVector, RealMatrix> value = this.model.value(p);
            return new UnweightedEvaluation((RealMatrix) value.getSecond(), this.target, p, null);
        }
    }

    private static class LocalValueAndJacobianFunction implements ValueAndJacobianFunction {
        private final MultivariateMatrixFunction jacobian;
        private final MultivariateVectorFunction value;

        LocalValueAndJacobianFunction(MultivariateVectorFunction value, MultivariateMatrixFunction jacobian) {
            this.value = value;
            this.jacobian = jacobian;
        }

        public Pair<RealVector, RealMatrix> value(RealVector point) {
            double[] p = point.toArray();
            return new Pair(computeValue(p), computeJacobian(p));
        }

        public RealVector computeValue(double[] params) {
            return new ArrayRealVector(this.value.value(params), false);
        }

        public RealMatrix computeJacobian(double[] params) {
            return new Array2DRowRealMatrix(this.jacobian.value(params), false);
        }
    }

    private LeastSquaresFactory() {
    }

    public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, RealMatrix weight, ConvergenceChecker<Evaluation> checker, int maxEvaluations, int maxIterations, boolean lazyEvaluation, ParameterValidator paramValidator) {
        LeastSquaresProblem p = new LocalLeastSquaresProblem(model, observed, start, checker, maxEvaluations, maxIterations, lazyEvaluation, paramValidator);
        if (weight != null) {
            return weightMatrix(p, weight);
        }
        return p;
    }

    public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, ConvergenceChecker<Evaluation> checker, int maxEvaluations, int maxIterations) {
        return create(model, observed, start, null, checker, maxEvaluations, maxIterations, false, null);
    }

    public static LeastSquaresProblem create(MultivariateJacobianFunction model, RealVector observed, RealVector start, RealMatrix weight, ConvergenceChecker<Evaluation> checker, int maxEvaluations, int maxIterations) {
        return weightMatrix(create(model, observed, start, checker, maxEvaluations, maxIterations), weight);
    }

    public static LeastSquaresProblem create(MultivariateVectorFunction model, MultivariateMatrixFunction jacobian, double[] observed, double[] start, RealMatrix weight, ConvergenceChecker<Evaluation> checker, int maxEvaluations, int maxIterations) {
        return create(model(model, jacobian), new ArrayRealVector(observed, false), new ArrayRealVector(start, false), weight, checker, maxEvaluations, maxIterations);
    }

    public static LeastSquaresProblem weightMatrix(LeastSquaresProblem problem, RealMatrix weights) {
        return new 1(problem, squareRoot(weights));
    }

    public static LeastSquaresProblem weightDiagonal(LeastSquaresProblem problem, RealVector weights) {
        return weightMatrix(problem, new DiagonalMatrix(weights.toArray()));
    }

    public static LeastSquaresProblem countEvaluations(LeastSquaresProblem problem, Incrementor counter) {
        return new 2(problem, counter);
    }

    public static ConvergenceChecker<Evaluation> evaluationChecker(ConvergenceChecker<PointVectorValuePair> checker) {
        return new 3(checker);
    }

    private static RealMatrix squareRoot(RealMatrix m) {
        if (!(m instanceof DiagonalMatrix)) {
            return new EigenDecomposition(m).getSquareRoot();
        }
        int dim = m.getRowDimension();
        RealMatrix sqrtM = new DiagonalMatrix(dim);
        for (int i = 0; i < dim; i++) {
            sqrtM.setEntry(i, i, FastMath.sqrt(m.getEntry(i, i)));
        }
        return sqrtM;
    }

    public static MultivariateJacobianFunction model(MultivariateVectorFunction value, MultivariateMatrixFunction jacobian) {
        return new LocalValueAndJacobianFunction(value, jacobian);
    }
}
