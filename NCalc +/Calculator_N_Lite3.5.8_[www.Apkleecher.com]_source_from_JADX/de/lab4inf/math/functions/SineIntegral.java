package de.lab4inf.math.functions;

import com.example.duy.calculator.geom2d.util.Angle2D;
import de.lab4inf.math.util.ChebyshevExpansion;

public class SineIntegral extends AbstractSiCiIntegrals {
    private static final double[] A;
    private static final double EPS = 5.0E-13d;
    private static final double[] FACULTY;

    static {
        int i;
        FACULTY = new double[16];
        A = new double[]{8.105852955361245d, -4.063980844911986d, 2.778756381742663d, -1.926565091150656d, 1.389308771171888d, -0.968322236987086d, 0.530148847916522d, -0.211263780976555d, 0.062033679432003d, -0.013867445589417d, 0.002436221404749d, -3.45469155569E-4d, 4.0420271419E-5d, -3.972908746E-6d, 3.32988589E-7d, -2.4100076E-8d, 1.52237E-9d, -8.471E-11d, 4.185E-12d, -1.85E-13d, 7.0E-15d};
        int n = FACULTY.length;
        FACULTY[0] = 1.0d;
        for (i = 1; i < n; i++) {
            FACULTY[i] = ((double) i) * FACULTY[i - 1];
        }
        double[] dArr = A;
        dArr[0] = dArr[0] / 2.0d;
        double c1 = 0.0d;
        double c2 = 0.0d;
        for (i = 0; i < A.length; i++) {
            c1 += A[i];
            c2 += Math.abs(A[i]);
        }
    }

    public double f(double... x) {
        return si(x[0]);
    }

    public static double si(double x) {
        double y;
        double z = Math.abs(x);
        if (z < 16.0d) {
            double w = z / 16.0d;
            y = ChebyshevExpansion.cheby(((2.0d * w) * w) - 1.0d, A) * w;
        } else {
            y = (Angle2D.M_PI_2 - (AbstractSiCiIntegrals.auxf(z) * Math.cos(z))) - (AbstractSiCiIntegrals.auxg(z) * Math.sin(z));
        }
        if (x < 0.0d) {
            return -y;
        }
        return y;
    }

    private static double facul(int x) {
        int n = FACULTY.length - 1;
        if (x <= n) {
            return FACULTY[x];
        }
        double f = FACULTY[n];
        for (int i = n + 1; i <= x; i++) {
            f *= (double) i;
        }
        return f;
    }

    public static double expandSi(double x) {
        double sum = 0.0d;
        double xn = x;
        double x2 = x * x;
        int sign = 1;
        int n = 0;
        do {
            int nn = (n * 2) + 1;
            double delta = xn / (((double) nn) * facul(nn));
            sum += ((double) sign) * delta;
            sign = -sign;
            xn *= x2;
            n++;
            if (Math.abs(delta) <= EPS) {
                break;
            }
        } while (n < 13);
        return sum;
    }
}
