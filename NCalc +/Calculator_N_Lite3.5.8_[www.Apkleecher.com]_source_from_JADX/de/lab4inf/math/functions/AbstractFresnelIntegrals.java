package de.lab4inf.math.functions;

import de.lab4inf.math.util.ChebyshevExpansion;

abstract class AbstractFresnelIntegrals extends L4MFunction {
    protected static final double AK = 3.0d;
    protected static final double EPS = 1.0E-16d;
    private static final double[] F;
    private static final double[] G;
    protected static final double PIH = 1.5707963267948966d;

    static {
        F = new double[]{0.635461098412986d, -5.73621372272E-4d, 5.571891859E-6d, -1.37398906E-7d, 5.908966E-9d, -3.7099E-10d, 3.0716E-11d, -3.145E-12d, 3.81E-13d, -5.3E-14d, 8.0E-15d, -1.0E-15d};
        G = new double[]{0.022315579858535d, -9.8395902454E-5d, 1.663169852E-6d, -5.6963997E-8d, 3.074598E-9d, -2.28879E-10d, 2.1678E-11d, -2.478E-12d, 3.3E-13d, -5.0E-14d, 8.0E-15d, -2.0E-15d};
        double[] dArr = F;
        dArr[0] = dArr[0] / 2.0d;
        dArr = G;
        dArr[0] = dArr[0] / 2.0d;
    }

    protected AbstractFresnelIntegrals() {
    }

    protected static boolean hasRelativeConverged(double x, double y) {
        double dx = Math.abs(x - y);
        double sx = Math.abs(x + y) / 2.0d;
        if (sx != 0.0d) {
            if (dx / sx < EPS) {
                return true;
            }
            return false;
        } else if (dx >= EPS) {
            return false;
        } else {
            return true;
        }
    }

    public static double gSimple(double x) {
        double z = Math.abs(x);
        return 1.0d / (((2.0d + (4.142d * z)) + ((3.492d * z) * z)) + (((6.67d * z) * z) * z));
    }

    public static double fSimple(double x) {
        double z = Math.abs(x);
        return (1.0d + (0.926d * z)) / ((2.0d + (1.792d * z)) + ((3.104d * z) * z));
    }

    public static double gaux(double x) {
        if (x < AK) {
            return gSimple(x);
        }
        double z = 9.0d / (x * x);
        return (ChebyshevExpansion.cheby(((2.0d * z) * z) - 1.0d, G) * z) / x;
    }

    public static double faux(double x) {
        if (x < AK) {
            return fSimple(x);
        }
        double z = 9.0d / (x * x);
        return ChebyshevExpansion.cheby(((2.0d * z) * z) - 1.0d, F) / x;
    }
}
