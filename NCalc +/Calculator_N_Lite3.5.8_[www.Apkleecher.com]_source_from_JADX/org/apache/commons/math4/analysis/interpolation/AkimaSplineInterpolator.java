package org.apache.commons.math4.analysis.interpolation;

import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.analysis.polynomials.PolynomialSplineFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.Precision;

public class AkimaSplineInterpolator implements UnivariateInterpolator {
    private static final int MINIMUM_NUMBER_POINTS = 5;

    public PolynomialSplineFunction interpolate(double[] xvals, double[] yvals) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        if (xvals == null || yvals == null) {
            throw new NullArgumentException();
        } else if (xvals.length != yvals.length) {
            throw new DimensionMismatchException(xvals.length, yvals.length);
        } else if (xvals.length < MINIMUM_NUMBER_POINTS) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(xvals.length), Integer.valueOf(MINIMUM_NUMBER_POINTS), true);
        } else {
            int i;
            MathArrays.checkOrder(xvals);
            int numberOfDiffAndWeightElements = xvals.length - 1;
            double[] differences = new double[numberOfDiffAndWeightElements];
            double[] weights = new double[numberOfDiffAndWeightElements];
            for (i = 0; i < differences.length; i++) {
                differences[i] = (yvals[i + 1] - yvals[i]) / (xvals[i + 1] - xvals[i]);
            }
            for (i = 1; i < weights.length; i++) {
                weights[i] = FastMath.abs(differences[i] - differences[i - 1]);
            }
            double[] firstDerivatives = new double[xvals.length];
            for (i = 2; i < firstDerivatives.length - 2; i++) {
                double wP = weights[i + 1];
                double wM = weights[i - 1];
                if (Precision.equals(wP, 0.0d) && Precision.equals(wM, 0.0d)) {
                    double xv = xvals[i];
                    double xvP = xvals[i + 1];
                    double xvM = xvals[i - 1];
                    firstDerivatives[i] = (((xvP - xv) * differences[i - 1]) + ((xv - xvM) * differences[i])) / (xvP - xvM);
                } else {
                    firstDerivatives[i] = ((differences[i - 1] * wP) + (differences[i] * wM)) / (wP + wM);
                }
            }
            firstDerivatives[0] = differentiateThreePoint(xvals, yvals, 0, 0, 1, 2);
            firstDerivatives[1] = differentiateThreePoint(xvals, yvals, 1, 0, 1, 2);
            firstDerivatives[xvals.length - 2] = differentiateThreePoint(xvals, yvals, xvals.length - 2, xvals.length - 3, xvals.length - 2, xvals.length - 1);
            firstDerivatives[xvals.length - 1] = differentiateThreePoint(xvals, yvals, xvals.length - 1, xvals.length - 3, xvals.length - 2, xvals.length - 1);
            return interpolateHermiteSorted(xvals, yvals, firstDerivatives);
        }
    }

    private double differentiateThreePoint(double[] xvals, double[] yvals, int indexOfDifferentiation, int indexOfFirstSample, int indexOfSecondsample, int indexOfThirdSample) {
        double x0 = yvals[indexOfFirstSample];
        double x1 = yvals[indexOfSecondsample];
        double t1 = xvals[indexOfSecondsample] - xvals[indexOfFirstSample];
        double t2 = xvals[indexOfThirdSample] - xvals[indexOfFirstSample];
        double a = ((yvals[indexOfThirdSample] - x0) - ((t2 / t1) * (x1 - x0))) / ((t2 * t2) - (t1 * t2));
        return ((2.0d * a) * (xvals[indexOfDifferentiation] - xvals[indexOfFirstSample])) + (((x1 - x0) - ((a * t1) * t1)) / t1);
    }

    private PolynomialSplineFunction interpolateHermiteSorted(double[] xvals, double[] yvals, double[] firstDerivatives) {
        int length = xvals.length;
        int length2 = yvals.length;
        if (length != r0) {
            throw new DimensionMismatchException(xvals.length, yvals.length);
        }
        length = xvals.length;
        length2 = firstDerivatives.length;
        if (length != r0) {
            throw new DimensionMismatchException(xvals.length, firstDerivatives.length);
        } else if (xvals.length < 2) {
            throw new NumberIsTooSmallException(LocalizedFormats.NUMBER_OF_POINTS, Integer.valueOf(xvals.length), Integer.valueOf(2), true);
        } else {
            PolynomialFunction[] polynomials = new PolynomialFunction[(xvals.length - 1)];
            double[] coefficients = new double[4];
            for (int i = 0; i < polynomials.length; i++) {
                double w = xvals[i + 1] - xvals[i];
                double w2 = w * w;
                double yv = yvals[i];
                double yvP = yvals[i + 1];
                double fd = firstDerivatives[i];
                double fdP = firstDerivatives[i + 1];
                coefficients[0] = yv;
                coefficients[1] = firstDerivatives[i];
                coefficients[2] = ((((3.0d * (yvP - yv)) / w) - (2.0d * fd)) - fdP) / w;
                coefficients[3] = ((((2.0d * (yv - yvP)) / w) + fd) + fdP) / w2;
                polynomials[i] = new PolynomialFunction(coefficients);
            }
            return new PolynomialSplineFunction(xvals, polynomials);
        }
    }
}
