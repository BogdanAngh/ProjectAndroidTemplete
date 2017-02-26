package org.apache.commons.math4.fitting;

import java.util.Collection;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunction.Parametric;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math4.linear.DiagonalMatrix;

public class PolynomialCurveFitter extends AbstractCurveFitter {
    private static final Parametric FUNCTION;
    private final double[] initialGuess;
    private final int maxIter;

    static {
        FUNCTION = new Parametric();
    }

    private PolynomialCurveFitter(double[] initialGuess, int maxIter) {
        this.initialGuess = initialGuess;
        this.maxIter = maxIter;
    }

    public static PolynomialCurveFitter create(int degree) {
        return new PolynomialCurveFitter(new double[(degree + 1)], BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public PolynomialCurveFitter withStartPoint(double[] newStart) {
        return new PolynomialCurveFitter((double[]) newStart.clone(), this.maxIter);
    }

    public PolynomialCurveFitter withMaxIterations(int newMaxIter) {
        return new PolynomialCurveFitter(this.initialGuess, newMaxIter);
    }

    protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> observations) {
        int len = observations.size();
        double[] target = new double[len];
        double[] weights = new double[len];
        int i = 0;
        for (WeightedObservedPoint obs : observations) {
            target[i] = obs.getY();
            weights[i] = obs.getWeight();
            i++;
        }
        TheoreticalValuesFunction model = new TheoreticalValuesFunction(FUNCTION, observations);
        if (this.initialGuess != null) {
            return new LeastSquaresBuilder().maxEvaluations(BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT).maxIterations(this.maxIter).start(this.initialGuess).target(target).weight(new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
        }
        throw new MathInternalError();
    }
}
