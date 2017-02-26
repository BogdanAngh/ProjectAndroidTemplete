package org.apache.commons.math4.primes;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.util.FastMath;

class PollardRho {
    private PollardRho() {
    }

    public static List<Integer> primeFactors(int n) {
        List<Integer> factors = new ArrayList();
        n = SmallPrimes.smallTrialDivision(n, factors);
        if (1 != n) {
            if (SmallPrimes.millerRabinPrimeTest(n)) {
                factors.add(Integer.valueOf(n));
            } else {
                int divisor = rhoBrent(n);
                factors.add(Integer.valueOf(divisor));
                factors.add(Integer.valueOf(n / divisor));
            }
        }
        return factors;
    }

    static int rhoBrent(int n) {
        int cst = SmallPrimes.PRIMES_LAST;
        int y = 2;
        int r = 1;
        while (true) {
            int i;
            int x = y;
            for (i = 0; i < r; i++) {
                long j = (long) cst;
                long j2 = (long) n;
                y = (int) ((r0 + (((long) y) * ((long) y))) % r0);
            }
            int k = 0;
            do {
                int bound = FastMath.min(25, r - k);
                int q = 1;
                for (i = -3; i < bound; i++) {
                    j = (long) cst;
                    j2 = (long) n;
                    y = (int) ((r0 + (((long) y) * ((long) y))) % r0);
                    long divisor = (long) FastMath.abs(x - y);
                    if (0 == divisor) {
                        cst += SmallPrimes.PRIMES_LAST;
                        k = -25;
                        y = 2;
                        r = 1;
                        break;
                    }
                    j = (long) n;
                    q = (int) ((divisor * ((long) q)) % r0);
                    if (q == 0) {
                        return gcdPositive(FastMath.abs((int) divisor), n);
                    }
                }
                int out = gcdPositive(FastMath.abs(q), n);
                if (1 != out) {
                    return out;
                }
                k += 25;
            } while (k < r);
            r *= 2;
        }
    }

    static int gcdPositive(int a, int b) {
        if (a == 0) {
            return b;
        }
        if (b == 0) {
            return a;
        }
        int aTwos = Integer.numberOfTrailingZeros(a);
        a >>= aTwos;
        int bTwos = Integer.numberOfTrailingZeros(b);
        b >>= bTwos;
        int shift = FastMath.min(aTwos, bTwos);
        while (a != b) {
            int delta = a - b;
            b = FastMath.min(a, b);
            a = FastMath.abs(delta);
            a >>= Integer.numberOfTrailingZeros(a);
        }
        return a << shift;
    }
}
