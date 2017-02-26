package org.apache.commons.math4.analysis.polynomials;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math4.fraction.BigFraction;
import org.apache.commons.math4.util.CombinatoricsUtils;
import org.apache.commons.math4.util.FastMath;

public class PolynomialsUtils {
    private static final List<BigFraction> CHEBYSHEV_COEFFICIENTS;
    private static final List<BigFraction> HERMITE_COEFFICIENTS;
    private static final Map<JacobiKey, List<BigFraction>> JACOBI_COEFFICIENTS;
    private static final List<BigFraction> LAGUERRE_COEFFICIENTS;
    private static final List<BigFraction> LEGENDRE_COEFFICIENTS;

    private interface RecurrenceCoefficientsGenerator {
        BigFraction[] generate(int i);
    }

    class 1 implements RecurrenceCoefficientsGenerator {
        private final BigFraction[] coeffs;

        1() {
            this.coeffs = new BigFraction[]{BigFraction.ZERO, BigFraction.TWO, BigFraction.ONE};
        }

        public BigFraction[] generate(int k) {
            return this.coeffs;
        }
    }

    class 2 implements RecurrenceCoefficientsGenerator {
        2() {
        }

        public BigFraction[] generate(int k) {
            return new BigFraction[]{BigFraction.ZERO, BigFraction.TWO, new BigFraction(k * 2)};
        }
    }

    class 3 implements RecurrenceCoefficientsGenerator {
        3() {
        }

        public BigFraction[] generate(int k) {
            int kP1 = k + 1;
            return new BigFraction[]{new BigFraction((k * 2) + 1, kP1), new BigFraction(-1, kP1), new BigFraction(k, kP1)};
        }
    }

    class 4 implements RecurrenceCoefficientsGenerator {
        4() {
        }

        public BigFraction[] generate(int k) {
            int kP1 = k + 1;
            return new BigFraction[]{BigFraction.ZERO, new BigFraction(k + kP1, kP1), new BigFraction(k, kP1)};
        }
    }

    class 5 implements RecurrenceCoefficientsGenerator {
        private final /* synthetic */ int val$v;
        private final /* synthetic */ int val$w;

        5(int i, int i2) {
            this.val$v = i;
            this.val$w = i2;
        }

        public BigFraction[] generate(int k) {
            k++;
            int kvw = (this.val$v + k) + this.val$w;
            int twoKvw = kvw + k;
            int twoKvwM1 = twoKvw - 1;
            int den = ((k * 2) * kvw) * (twoKvw - 2);
            return new BigFraction[]{new BigFraction(((this.val$v * this.val$v) - (this.val$w * this.val$w)) * twoKvwM1, den), new BigFraction((twoKvwM1 * twoKvw) * (twoKvw - 2), den), new BigFraction(((((this.val$v + k) - 1) * 2) * ((this.val$w + k) - 1)) * twoKvw, den)};
        }
    }

    private static class JacobiKey {
        private final int v;
        private final int w;

        public JacobiKey(int v, int w) {
            this.v = v;
            this.w = w;
        }

        public int hashCode() {
            return (this.v << 16) ^ this.w;
        }

        public boolean equals(Object key) {
            if (key == null || !(key instanceof JacobiKey)) {
                return false;
            }
            JacobiKey otherK = (JacobiKey) key;
            if (this.v == otherK.v && this.w == otherK.w) {
                return true;
            }
            return false;
        }
    }

    static {
        CHEBYSHEV_COEFFICIENTS = new ArrayList();
        CHEBYSHEV_COEFFICIENTS.add(BigFraction.ONE);
        CHEBYSHEV_COEFFICIENTS.add(BigFraction.ZERO);
        CHEBYSHEV_COEFFICIENTS.add(BigFraction.ONE);
        HERMITE_COEFFICIENTS = new ArrayList();
        HERMITE_COEFFICIENTS.add(BigFraction.ONE);
        HERMITE_COEFFICIENTS.add(BigFraction.ZERO);
        HERMITE_COEFFICIENTS.add(BigFraction.TWO);
        LAGUERRE_COEFFICIENTS = new ArrayList();
        LAGUERRE_COEFFICIENTS.add(BigFraction.ONE);
        LAGUERRE_COEFFICIENTS.add(BigFraction.ONE);
        LAGUERRE_COEFFICIENTS.add(BigFraction.MINUS_ONE);
        LEGENDRE_COEFFICIENTS = new ArrayList();
        LEGENDRE_COEFFICIENTS.add(BigFraction.ONE);
        LEGENDRE_COEFFICIENTS.add(BigFraction.ZERO);
        LEGENDRE_COEFFICIENTS.add(BigFraction.ONE);
        JACOBI_COEFFICIENTS = new HashMap();
    }

    private PolynomialsUtils() {
    }

    public static PolynomialFunction createChebyshevPolynomial(int degree) {
        return buildPolynomial(degree, CHEBYSHEV_COEFFICIENTS, new 1());
    }

    public static PolynomialFunction createHermitePolynomial(int degree) {
        return buildPolynomial(degree, HERMITE_COEFFICIENTS, new 2());
    }

    public static PolynomialFunction createLaguerrePolynomial(int degree) {
        return buildPolynomial(degree, LAGUERRE_COEFFICIENTS, new 3());
    }

    public static PolynomialFunction createLegendrePolynomial(int degree) {
        return buildPolynomial(degree, LEGENDRE_COEFFICIENTS, new 4());
    }

    public static PolynomialFunction createJacobiPolynomial(int degree, int v, int w) {
        JacobiKey key = new JacobiKey(v, w);
        if (!JACOBI_COEFFICIENTS.containsKey(key)) {
            List<BigFraction> list = new ArrayList();
            JACOBI_COEFFICIENTS.put(key, list);
            list.add(BigFraction.ONE);
            list.add(new BigFraction(v - w, 2));
            list.add(new BigFraction((v + 2) + w, 2));
        }
        return buildPolynomial(degree, (List) JACOBI_COEFFICIENTS.get(key), new 5(v, w));
    }

    public static double[] shift(double[] coefficients, double shift) {
        int i;
        int j;
        int dp1 = coefficients.length;
        double[] newCoefficients = new double[dp1];
        int[][] coeff = (int[][]) Array.newInstance(Integer.TYPE, new int[]{dp1, dp1});
        for (i = 0; i < dp1; i++) {
            for (j = 0; j <= i; j++) {
                coeff[i][j] = (int) CombinatoricsUtils.binomialCoefficient(i, j);
            }
        }
        for (i = 0; i < dp1; i++) {
            newCoefficients[0] = newCoefficients[0] + (coefficients[i] * FastMath.pow(shift, i));
        }
        int d = dp1 - 1;
        for (i = 0; i < d; i++) {
            for (j = i; j < d; j++) {
                int i2 = i + 1;
                newCoefficients[i2] = newCoefficients[i2] + ((((double) coeff[j + 1][j - i]) * coefficients[j + 1]) * FastMath.pow(shift, j - i));
            }
        }
        return newCoefficients;
    }

    private static PolynomialFunction buildPolynomial(int degree, List<BigFraction> coefficients, RecurrenceCoefficientsGenerator generator) {
        int maxDegree = ((int) FastMath.floor(FastMath.sqrt((double) (coefficients.size() * 2)))) - 1;
        synchronized (PolynomialsUtils.class) {
            if (degree > maxDegree) {
                computeUpToDegree(degree, maxDegree, generator, coefficients);
            }
        }
        int start = ((degree + 1) * degree) / 2;
        double[] a = new double[(degree + 1)];
        for (int i = 0; i <= degree; i++) {
            a[i] = ((BigFraction) coefficients.get(start + i)).doubleValue();
        }
        return new PolynomialFunction(a);
    }

    private static void computeUpToDegree(int degree, int maxDegree, RecurrenceCoefficientsGenerator generator, List<BigFraction> coefficients) {
        int startK = ((maxDegree - 1) * maxDegree) / 2;
        for (int k = maxDegree; k < degree; k++) {
            BigFraction ckPrev;
            int startKm1 = startK;
            startK += k;
            BigFraction[] ai = generator.generate(k);
            BigFraction ck = (BigFraction) coefficients.get(startK);
            coefficients.add(ck.multiply(ai[0]).subtract(((BigFraction) coefficients.get(startKm1)).multiply(ai[2])));
            for (int i = 1; i < k; i++) {
                ckPrev = ck;
                ck = (BigFraction) coefficients.get(startK + i);
                coefficients.add(ck.multiply(ai[0]).add(ckPrev.multiply(ai[1])).subtract(((BigFraction) coefficients.get(startKm1 + i)).multiply(ai[2])));
            }
            ckPrev = ck;
            ck = (BigFraction) coefficients.get(startK + k);
            coefficients.add(ck.multiply(ai[0]).add(ckPrev.multiply(ai[1])));
            coefficients.add(ck.multiply(ai[1]));
        }
    }
}
