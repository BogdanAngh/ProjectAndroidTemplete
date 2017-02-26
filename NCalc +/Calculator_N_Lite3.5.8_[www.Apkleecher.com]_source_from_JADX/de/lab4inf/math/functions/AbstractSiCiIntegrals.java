package de.lab4inf.math.functions;

import de.lab4inf.math.util.ChebyshevExpansion;

abstract class AbstractSiCiIntegrals extends L4MFunction {
    protected static final double AK = 16.0d;
    private static final double[] F;
    private static final double[] G;
    protected static final double PIH = 1.5707963267948966d;

    static {
        F = new double[]{0.124527458057854d, -2.33756041393E-4d, 2.453755677E-6d, -5.8670317E-8d, 2.356196E-9d, -1.36096E-10d, 1.0308E-11d, -9.64E-13d, 1.07E-13d, -1.4E-14d, 2.0E-15d};
        G = new double[]{0.007725712193407d, -4.2644182622E-5d, 7.2499595E-7d, -2.3468225E-8d, 1.169202E-9d, -7.9604E-11d, 6.875E-12d, -7.17E-13d, 8.7E-14d, -1.2E-14d, 2.0E-15d};
        double[] dArr = F;
        dArr[0] = dArr[0] / 2.0d;
        dArr = G;
        dArr[0] = dArr[0] / 2.0d;
    }

    protected AbstractSiCiIntegrals() {
    }

    private static double fSimple(double x) {
        double[] a = new double[]{38.027264d, 265.187033d, 335.67732d, 38.102495d};
        double[] b = new double[]{40.021433d, 322.624911d, 570.23628d, 157.105423d};
        double z = x * x;
        double u = 1.0d;
        double d = 1.0d;
        for (int i = 0; i < a.length; i++) {
            u = a[i] + (u * z);
            d = b[i] + (d * z);
        }
        return u / (x * d);
    }

    private static double gSimple(double x) {
        double[] a = new double[]{42.242855d, 302.757865d, 352.018498d, 21.821899d};
        double[] b = new double[]{48.196927d, 482.485984d, 1114.978885d, 449.690326d};
        double z = x * x;
        double u = 1.0d;
        double d = 1.0d;
        for (int i = 0; i < a.length; i++) {
            u = a[i] + (u * z);
            d = b[i] + (d * z);
        }
        return u / ((x * x) * d);
    }

    public static double auxg(double x) {
        if (x >= AK) {
            double z = AK / x;
            double g = z * z;
            return ChebyshevExpansion.cheby((2.0d * g) - 1.0d, G) * g;
        } else if (x < 1.0d) {
            return ((-CosineIntegral.ci(x)) * Math.cos(x)) - ((SineIntegral.si(x) - PIH) * Math.sin(x));
        } else {
            return gSimple(x);
        }
    }

    public static double auxf(double x) {
        if (x >= AK) {
            double z = AK / x;
            return ChebyshevExpansion.cheby((2.0d * (z * z)) - 1.0d, F) * z;
        } else if (x < 1.0d) {
            return (CosineIntegral.ci(x) * Math.sin(x)) - ((SineIntegral.si(x) - PIH) * Math.cos(x));
        } else {
            return fSimple(x);
        }
    }
}
