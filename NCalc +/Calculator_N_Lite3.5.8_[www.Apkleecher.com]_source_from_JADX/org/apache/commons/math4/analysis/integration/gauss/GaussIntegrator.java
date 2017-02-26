package org.apache.commons.math4.analysis.integration.gauss;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathArrays.OrderDirection;
import org.apache.commons.math4.util.Pair;

public class GaussIntegrator {
    private final double[] points;
    private final double[] weights;

    public GaussIntegrator(double[] points, double[] weights) throws NonMonotonicSequenceException, DimensionMismatchException {
        if (points.length != weights.length) {
            throw new DimensionMismatchException(points.length, weights.length);
        }
        MathArrays.checkOrder(points, OrderDirection.INCREASING, true, true);
        this.points = (double[]) points.clone();
        this.weights = (double[]) weights.clone();
    }

    public GaussIntegrator(Pair<double[], double[]> pointsAndWeights) throws NonMonotonicSequenceException {
        this((double[]) pointsAndWeights.getFirst(), (double[]) pointsAndWeights.getSecond());
    }

    public double integrate(UnivariateFunction f) {
        double s = 0.0d;
        double c = 0.0d;
        for (int i = 0; i < this.points.length; i++) {
            double x = this.points[i];
            double y = (f.value(x) * this.weights[i]) - c;
            double t = s + y;
            c = (t - s) - y;
            s = t;
        }
        return s;
    }

    public int getNumberOfPoints() {
        return this.points.length;
    }

    public double getPoint(int index) {
        return this.points[index];
    }

    public double getWeight(int index) {
        return this.weights[index];
    }
}
