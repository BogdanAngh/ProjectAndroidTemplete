package org.apache.commons.math4.optim.nonlinear.scalar.noderiv;

import java.util.Comparator;
import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.optim.PointValuePair;

public class NelderMeadSimplex extends AbstractSimplex {
    private static final double DEFAULT_GAMMA = 0.5d;
    private static final double DEFAULT_KHI = 2.0d;
    private static final double DEFAULT_RHO = 1.0d;
    private static final double DEFAULT_SIGMA = 0.5d;
    private final double gamma;
    private final double khi;
    private final double rho;
    private final double sigma;

    public NelderMeadSimplex(int n) {
        this(n, DEFAULT_RHO);
    }

    public NelderMeadSimplex(int n, double sideLength) {
        this(n, sideLength, DEFAULT_RHO, DEFAULT_KHI, DEFAULT_SIGMA, DEFAULT_SIGMA);
    }

    public NelderMeadSimplex(int n, double sideLength, double rho, double khi, double gamma, double sigma) {
        super(n, sideLength);
        this.rho = rho;
        this.khi = khi;
        this.gamma = gamma;
        this.sigma = sigma;
    }

    public NelderMeadSimplex(int n, double rho, double khi, double gamma, double sigma) {
        this(n, DEFAULT_RHO, rho, khi, gamma, sigma);
    }

    public NelderMeadSimplex(double[] steps) {
        this(steps, (double) DEFAULT_RHO, (double) DEFAULT_KHI, (double) DEFAULT_SIGMA, (double) DEFAULT_SIGMA);
    }

    public NelderMeadSimplex(double[] steps, double rho, double khi, double gamma, double sigma) {
        super(steps);
        this.rho = rho;
        this.khi = khi;
        this.gamma = gamma;
        this.sigma = sigma;
    }

    public NelderMeadSimplex(double[][] referenceSimplex) {
        this(referenceSimplex, (double) DEFAULT_RHO, (double) DEFAULT_KHI, (double) DEFAULT_SIGMA, (double) DEFAULT_SIGMA);
    }

    public NelderMeadSimplex(double[][] referenceSimplex, double rho, double khi, double gamma, double sigma) {
        super(referenceSimplex);
        this.rho = rho;
        this.khi = khi;
        this.gamma = gamma;
        this.sigma = sigma;
    }

    public void iterate(MultivariateFunction evaluationFunction, Comparator<PointValuePair> comparator) {
        int i;
        int j;
        int n = getDimension();
        PointValuePair best = getPoint(0);
        PointValuePair secondBest = getPoint(n - 1);
        PointValuePair worst = getPoint(n);
        double[] xWorst = worst.getPointRef();
        double[] centroid = new double[n];
        for (i = 0; i < n; i++) {
            double[] x = getPoint(i).getPointRef();
            for (j = 0; j < n; j++) {
                centroid[j] = centroid[j] + x[j];
            }
        }
        double scaling = DEFAULT_RHO / ((double) n);
        for (j = 0; j < n; j++) {
            centroid[j] = centroid[j] * scaling;
        }
        double[] xR = new double[n];
        for (j = 0; j < n; j++) {
            xR[j] = centroid[j] + (this.rho * (centroid[j] - xWorst[j]));
        }
        PointValuePair reflected = new PointValuePair(xR, evaluationFunction.value(xR), false);
        if (comparator.compare(best, reflected) <= 0 && comparator.compare(reflected, secondBest) < 0) {
            replaceWorstPoint(reflected, comparator);
        } else if (comparator.compare(reflected, best) < 0) {
            double[] xE = new double[n];
            for (j = 0; j < n; j++) {
                xE[j] = centroid[j] + (this.khi * (xR[j] - centroid[j]));
            }
            PointValuePair expanded = new PointValuePair(xE, evaluationFunction.value(xE), false);
            if (comparator.compare(expanded, reflected) < 0) {
                replaceWorstPoint(expanded, comparator);
            } else {
                replaceWorstPoint(reflected, comparator);
            }
        } else {
            double[] xC;
            if (comparator.compare(reflected, worst) < 0) {
                xC = new double[n];
                for (j = 0; j < n; j++) {
                    xC[j] = centroid[j] + (this.gamma * (xR[j] - centroid[j]));
                }
                PointValuePair outContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
                if (comparator.compare(outContracted, reflected) <= 0) {
                    replaceWorstPoint(outContracted, comparator);
                    return;
                }
            }
            xC = new double[n];
            for (j = 0; j < n; j++) {
                xC[j] = centroid[j] - (this.gamma * (centroid[j] - xWorst[j]));
            }
            PointValuePair inContracted = new PointValuePair(xC, evaluationFunction.value(xC), false);
            if (comparator.compare(inContracted, worst) < 0) {
                replaceWorstPoint(inContracted, comparator);
                return;
            }
            double[] xSmallest = getPoint(0).getPointRef();
            for (i = 1; i <= n; i++) {
                x = getPoint(i).getPoint();
                for (j = 0; j < n; j++) {
                    x[j] = xSmallest[j] + (this.sigma * (x[j] - xSmallest[j]));
                }
                setPoint(i, new PointValuePair(x, Double.NaN, false));
            }
            evaluate(evaluationFunction, comparator);
        }
    }
}
