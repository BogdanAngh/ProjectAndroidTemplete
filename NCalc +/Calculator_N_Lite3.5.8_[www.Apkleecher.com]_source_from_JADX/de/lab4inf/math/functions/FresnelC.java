package de.lab4inf.math.functions;

import com.example.duy.calculator.geom2d.util.Angle2D;
import de.lab4inf.math.util.ChebyshevExpansion;

public class FresnelC extends AbstractFresnelIntegrals {
    private static final double[] A;

    static {
        A = new double[]{0.566094879476909d, -0.174163078153421d, 0.147534155215236d, -0.092641852979503d, 0.097246391833287d, -0.105139484620109d, 0.066060370528389d, -0.025736708168279d, 0.006861115379812d, -0.001341081352431d, 2.01616882443E-4d, -2.4136195791E-5d, 2.361906788E-6d, -1.9285009E-7d, 1.3357248E-8d, -7.9555E-10d, 4.1213E-11d, -1.875E-12d, 7.6E-14d, -3.0E-15d};
        double[] dArr = A;
        dArr[0] = dArr[0] / 2.0d;
    }

    public double f(double... x) {
        return fresnelC(x[0]);
    }

    public static double fresnelC(double x) {
        double y;
        double z = Math.abs(x);
        double w = z * z;
        if (z < 3.0d) {
            double u = w / 9.0d;
            y = ChebyshevExpansion.cheby(((2.0d * u) * u) - 1.0d, A) * z;
        } else {
            w *= Angle2D.M_PI_2;
            y = (0.5d + (AbstractFresnelIntegrals.faux(z) * Math.sin(w))) - (AbstractFresnelIntegrals.gaux(z) * Math.cos(w));
        }
        if (x < 0.0d) {
            return -y;
        }
        return y;
    }

    public static double fresnelCSeries(double x) {
        double yn = 1.0d;
        double fac = 1.0d;
        double x2 = (Angle2D.M_PI_2 * x) * x;
        double x4 = (-x2) * x2;
        double z4 = 1.0d;
        int k = 0;
        double yo;
        do {
            k++;
            fac = (fac * ((double) ((k * 2) - 1))) * ((double) (k * 2));
            yo = yn;
            z4 *= x4;
            yn += z4 / (((double) ((k * 4) + 1)) * fac);
        } while (!AbstractFresnelIntegrals.hasRelativeConverged(yn, yo));
        return yn * x;
    }
}
