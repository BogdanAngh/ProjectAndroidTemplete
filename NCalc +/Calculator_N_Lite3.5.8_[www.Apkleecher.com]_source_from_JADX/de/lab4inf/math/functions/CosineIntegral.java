package de.lab4inf.math.functions;

import de.lab4inf.math.util.ChebyshevExpansion;
import org.apache.commons.math4.special.Gamma;

public class CosineIntegral extends AbstractSiCiIntegrals {
    private static final double[] A;

    static {
        A = new double[]{29.985178735626818d, -19.38612409660777d, 12.74187086975807d, -8.10790397056253d, 4.862022348500627d, -2.497505088539025d, 1.00866078735811d, -0.312080924825428d, 0.074678255294576d, -0.014110865253535d, 0.002152046752074d, -2.70212331184E-4d, 2.8416945498E-5d, -2.540125611E-6d, 1.95437144E-7d, -1.308402E-8d, 7.69379E-10d, -4.0066E-11d, 1.861E-12d, -7.8E-14d, 3.0E-15d};
        double[] dArr = A;
        dArr[0] = dArr[0] / 2.0d;
    }

    public double f(double... x) {
        return ci(x[0]);
    }

    public static double ci(double x) {
        double y;
        double z = Math.abs(x);
        if (z < 16.0d) {
            double w = z / 16.0d;
            y = (Gamma.GAMMA + Math.log(z)) - ((ChebyshevExpansion.cheby(((2.0d * w) * w) - 1.0d, A) * w) * w);
        } else {
            y = (AbstractSiCiIntegrals.auxf(z) * Math.sin(z)) - (AbstractSiCiIntegrals.auxg(z) * Math.cos(z));
        }
        if (x < 0.0d) {
            return -y;
        }
        return y;
    }
}
