package de.lab4inf.math.functions;

import com.example.duy.calculator.geom2d.util.Angle2D;
import de.lab4inf.math.util.ChebyshevExpansion;

public class FresnelS extends AbstractFresnelIntegrals {
    private static final double[] A;

    static {
        A = new double[]{1.734174339031447d, -1.247697507291387d, 0.926493976989515d, -0.688881695298469d, 0.515461606559411d, -0.30039878687713d, 0.122191066602012d, -0.035248288029314d, 0.00751776347924d, -0.001232314420465d, 1.60243443651E-4d, -1.6954178157E-5d, 1.48971966E-6d, -1.10548467E-7d, 7.025677E-9d, -3.86931E-10d, 1.8654E-11d, -7.94E-13d, 3.0E-14d, -1.0E-15d};
        double[] dArr = A;
        dArr[0] = dArr[0] / 2.0d;
    }

    public double f(double... x) {
        return fresnelS(x[0]);
    }

    public static double fresnelS(double x) {
        double y;
        double z = Math.abs(x);
        double w = z * z;
        if (z <= 3.0d) {
            double u = w / 9.0d;
            y = ((ChebyshevExpansion.cheby(((2.0d * u) * u) - 1.0d, A) * z) * w) / 9.0d;
        } else {
            w *= Angle2D.M_PI_2;
            y = (0.5d - (AbstractFresnelIntegrals.faux(z) * Math.cos(w))) - (AbstractFresnelIntegrals.gaux(z) * Math.sin(w));
        }
        if (x < 0.0d) {
            return -y;
        }
        return y;
    }

    public static double fresnelSSeries(double x) {
        double yn = 0.3333333333333333d;
        double fac = 1.0d;
        double x2 = (Angle2D.M_PI_2 * x) * x;
        double x4 = (-x2) * x2;
        double z4 = 1.0d;
        int k = 0;
        double yo;
        do {
            k++;
            fac = (fac * ((double) (k * 2))) * ((double) ((k * 2) + 1));
            yo = yn;
            z4 *= x4;
            yn += z4 / (((double) ((k * 4) + 3)) * fac);
        } while (Math.abs(yn - yo) > Math.abs(yo) * 1.0E-16d);
        return yn * (x * x2);
    }
}
