package org.apache.commons.math4.primes;

import java.util.List;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class Primes {
    private Primes() {
    }

    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        int[] iArr = SmallPrimes.PRIMES;
        int length = iArr.length;
        int i = 0;
        while (i < length) {
            int p = iArr[i];
            if (n % p != 0) {
                i++;
            } else if (n == p) {
                return true;
            } else {
                return false;
            }
        }
        return SmallPrimes.millerRabinPrimeTest(n);
    }

    public static int nextPrime(int n) {
        if (n < 0) {
            throw new MathIllegalArgumentException(LocalizedFormats.NUMBER_TOO_SMALL, Integer.valueOf(n), Integer.valueOf(0));
        } else if (n == 2) {
            return 2;
        } else {
            n |= 1;
            if (n == 1) {
                return 2;
            }
            if (isPrime(n)) {
                return n;
            }
            int rem = n % 3;
            if (rem == 0) {
                n += 2;
            } else if (1 == rem) {
                n += 4;
            }
            while (!isPrime(n)) {
                n += 2;
                if (isPrime(n)) {
                    return n;
                }
                n += 4;
            }
            return n;
        }
    }

    public static List<Integer> primeFactors(int n) {
        if (n >= 2) {
            return SmallPrimes.trialDivision(n);
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NUMBER_TOO_SMALL, Integer.valueOf(n), Integer.valueOf(2));
    }
}
