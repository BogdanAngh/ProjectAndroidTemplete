package org.apache.commons.math4.analysis.interpolation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math4.analysis.differentiation.UnivariateDifferentiableVectorFunction;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.CombinatoricsUtils;

public class HermiteInterpolator implements UnivariateDifferentiableVectorFunction {
    private final List<Double> abscissae;
    private final List<double[]> bottomDiagonal;
    private final List<double[]> topDiagonal;

    public HermiteInterpolator() {
        this.abscissae = new ArrayList();
        this.topDiagonal = new ArrayList();
        this.bottomDiagonal = new ArrayList();
    }

    public void addSamplePoint(double x, double[]... value) throws ZeroException, MathArithmeticException {
        for (int i = 0; i < value.length; i++) {
            double inv;
            int j;
            double[] y = (double[]) value[i].clone();
            if (i > 1) {
                inv = 1.0d / ((double) CombinatoricsUtils.factorial(i));
                for (j = 0; j < y.length; j++) {
                    y[j] = y[j] * inv;
                }
            }
            int n = this.abscissae.size();
            this.bottomDiagonal.add(n - i, y);
            double[] bottom0 = y;
            for (j = i; j < n; j++) {
                double[] bottom1 = (double[]) this.bottomDiagonal.get(n - (j + 1));
                inv = 1.0d / (x - ((Double) this.abscissae.get(n - (j + 1))).doubleValue());
                if (Double.isInfinite(inv)) {
                    throw new ZeroException(LocalizedFormats.DUPLICATED_ABSCISSA_DIVISION_BY_ZERO, Double.valueOf(x));
                }
                for (int k = 0; k < y.length; k++) {
                    bottom1[k] = (bottom0[k] - bottom1[k]) * inv;
                }
                bottom0 = bottom1;
            }
            this.topDiagonal.add((double[]) bottom0.clone());
            this.abscissae.add(Double.valueOf(x));
        }
    }

    public PolynomialFunction[] getPolynomials() throws NoDataException {
        int i;
        checkInterpolation();
        PolynomialFunction zero = polynomial(0.0d);
        PolynomialFunction[] polynomials = new PolynomialFunction[((double[]) this.topDiagonal.get(0)).length];
        for (i = 0; i < polynomials.length; i++) {
            polynomials[i] = zero;
        }
        PolynomialFunction coeff = polynomial(1.0d);
        for (i = 0; i < this.topDiagonal.size(); i++) {
            double[] tdi = (double[]) this.topDiagonal.get(i);
            for (int k = 0; k < polynomials.length; k++) {
                polynomials[k] = polynomials[k].add(coeff.multiply(polynomial(tdi[k])));
            }
            coeff = coeff.multiply(polynomial(-((Double) this.abscissae.get(i)).doubleValue(), 1.0d));
        }
        return polynomials;
    }

    public double[] value(double x) throws NoDataException {
        checkInterpolation();
        double[] value = new double[((double[]) this.topDiagonal.get(0)).length];
        double valueCoeff = 1.0d;
        for (int i = 0; i < this.topDiagonal.size(); i++) {
            double[] dividedDifference = (double[]) this.topDiagonal.get(i);
            for (int k = 0; k < value.length; k++) {
                value[k] = value[k] + (dividedDifference[k] * valueCoeff);
            }
            valueCoeff *= x - ((Double) this.abscissae.get(i)).doubleValue();
        }
        return value;
    }

    public DerivativeStructure[] value(DerivativeStructure x) throws NoDataException {
        checkInterpolation();
        DerivativeStructure[] value = new DerivativeStructure[((double[]) this.topDiagonal.get(0)).length];
        Arrays.fill(value, x.getField().getZero());
        DerivativeStructure valueCoeff = (DerivativeStructure) x.getField().getOne();
        for (int i = 0; i < this.topDiagonal.size(); i++) {
            double[] dividedDifference = (double[]) this.topDiagonal.get(i);
            for (int k = 0; k < value.length; k++) {
                value[k] = value[k].add(valueCoeff.multiply(dividedDifference[k]));
            }
            valueCoeff = valueCoeff.multiply(x.subtract(((Double) this.abscissae.get(i)).doubleValue()));
        }
        return value;
    }

    private void checkInterpolation() throws NoDataException {
        if (this.abscissae.isEmpty()) {
            throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
        }
    }

    private PolynomialFunction polynomial(double... c) {
        return new PolynomialFunction(c);
    }
}
