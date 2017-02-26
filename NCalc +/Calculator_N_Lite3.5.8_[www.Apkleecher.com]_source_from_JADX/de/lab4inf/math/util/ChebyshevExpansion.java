package de.lab4inf.math.util;

import de.lab4inf.math.Function;
import org.apache.commons.math4.util.FastMath;

public class ChebyshevExpansion implements Function {
    private final double[] a;

    private static class ChebyshevTrafo implements Function {
        private final double a;
        private final double b;
        private final Function fct;

        public ChebyshevTrafo(double a, double b, Function f) {
            this.fct = f;
            this.a = 2.0d / (b - a);
            this.b = (a + b) / (a - b);
        }

        public double f(double... x) {
            double y = (x[0] - this.b) / this.a;
            return this.fct.f(y);
        }
    }

    public ChebyshevExpansion(double[] a) {
        this.a = (double[]) a.clone();
    }

    public ChebyshevExpansion(int n, Function fct) {
        this(coeff(n, fct));
    }

    public ChebyshevExpansion(int n, double a, double b, Function fct) {
        this(coeff(n, a, b, fct));
    }

    public double f(double... x) {
        return cheby(x[0], this.a);
    }

    double[] getCoeff() {
        return this.a;
    }

    public static double cheby(double x, double[] a) {
        double bp = 0.0d;
        double bpp = 0.0d;
        for (int n = a.length - 1; n > 0; n--) {
            bpp = bp;
            bp = (((2.0d * x) * bp) - bpp) + a[n];
        }
        return ((x * bp) - bpp) + a[0];
    }

    public static double[] coeff(int n, Function fct) {
        int k;
        double[] a = new double[n];
        double[] x = new double[n];
        double[] y = new double[n];
        for (k = 1; k <= n; k++) {
            double d = (double) n;
            x[k - 1] = Math.cos((FastMath.PI * (((double) k) - 0.5d)) / r0);
            y[k - 1] = fct.f(x[k - 1]);
        }
        for (k = 0; k < n; k++) {
            double t0 = 2.0d / ((double) n);
            double t1 = (2.0d * x[k]) / ((double) n);
            a[0] = a[0] + (y[k] / ((double) n));
            a[1] = a[1] + (y[k] * t1);
            for (int j = 0; j < n - 2; j++) {
                double t2 = ((2.0d * x[k]) * t1) - t0;
                int i = j + 2;
                a[i] = a[i] + (y[k] * t2);
                t0 = t1;
                t1 = t2;
            }
        }
        return a;
    }

    public static double[] coeff(int n, double a, double b, Function fct) {
        return coeff(n, new ChebyshevTrafo(a, b, fct));
    }
}
