package org.apache.commons.math4.fitting;

import java.util.Collection;
import org.apache.commons.math4.analysis.MultivariateMatrixFunction;
import org.apache.commons.math4.analysis.MultivariateVectorFunction;
import org.apache.commons.math4.analysis.ParametricUnivariateFunction;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math4.fitting.leastsquares.LevenbergMarquardtOptimizer;

public abstract class AbstractCurveFitter {

    protected static class TheoreticalValuesFunction {
        private final ParametricUnivariateFunction f;
        private final double[] points;

        class 1 implements MultivariateVectorFunction {
            1() {
            }

            public double[] value(double[] p) {
                int len = TheoreticalValuesFunction.this.points.length;
                double[] values = new double[len];
                for (int i = 0; i < len; i++) {
                    values[i] = TheoreticalValuesFunction.this.f.value(TheoreticalValuesFunction.this.points[i], p);
                }
                return values;
            }
        }

        class 2 implements MultivariateMatrixFunction {
            2() {
            }

            public double[][] value(double[] p) {
                int len = TheoreticalValuesFunction.this.points.length;
                double[][] jacobian = new double[len][];
                for (int i = 0; i < len; i++) {
                    jacobian[i] = TheoreticalValuesFunction.this.f.gradient(TheoreticalValuesFunction.this.points[i], p);
                }
                return jacobian;
            }
        }

        public TheoreticalValuesFunction(ParametricUnivariateFunction f, Collection<WeightedObservedPoint> observations) {
            this.f = f;
            this.points = new double[observations.size()];
            int i = 0;
            for (WeightedObservedPoint obs : observations) {
                int i2 = i + 1;
                this.points[i] = obs.getX();
                i = i2;
            }
        }

        public MultivariateVectorFunction getModelFunction() {
            return new 1();
        }

        public MultivariateMatrixFunction getModelFunctionJacobian() {
            return new 2();
        }
    }

    protected abstract LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> collection);

    public double[] fit(Collection<WeightedObservedPoint> points) {
        return getOptimizer().optimize(getProblem(points)).getPoint().toArray();
    }

    protected LeastSquaresOptimizer getOptimizer() {
        return new LevenbergMarquardtOptimizer();
    }
}
