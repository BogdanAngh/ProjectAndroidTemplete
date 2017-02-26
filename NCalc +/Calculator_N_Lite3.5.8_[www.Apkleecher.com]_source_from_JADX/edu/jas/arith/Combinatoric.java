package edu.jas.arith;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;

public class Combinatoric {
    public static BigInteger binCoeffInduction(BigInteger A, long n, long k) {
        BigInteger np = new BigInteger(n - k);
        return A.multiply(np).divide(new BigInteger(1 + k));
    }

    public static BigInteger binCoeff(int n, int k) {
        BigInteger A = BigInteger.ONE;
        int kp = k < n - k ? k : n - k;
        for (int j = 0; j < kp; j++) {
            A = binCoeffInduction(A, (long) n, (long) j);
        }
        return A;
    }

    public static BigInteger binCoeffSum(int n, int k) {
        BigInteger S = BigInteger.ONE;
        BigInteger B = BigInteger.ONE;
        for (int j = 0; j < k; j++) {
            B = binCoeffInduction(B, (long) n, (long) j);
            S = S.sum(B);
        }
        return S;
    }

    public static BigInteger factorial(long n) {
        if (n <= 1) {
            return BigInteger.ONE;
        }
        BigInteger f = BigInteger.ONE;
        if (n >= 2147483647L) {
            throw new UnsupportedOperationException(n + " >= Integer.MAX_VALUE = " + BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
        }
        for (int i = 2; ((long) i) <= n; i++) {
            f = f.multiply(new BigInteger((long) i));
        }
        return f;
    }
}
