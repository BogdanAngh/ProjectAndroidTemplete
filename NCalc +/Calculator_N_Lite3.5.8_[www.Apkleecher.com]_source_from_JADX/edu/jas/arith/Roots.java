package edu.jas.arith;

import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import java.math.MathContext;

public class Roots {
    public static BigInteger root(BigInteger A, int n) {
        if (n == 1) {
            return A;
        }
        if (n == 2) {
            return sqrt(A);
        }
        if (n < 1) {
            throw new IllegalArgumentException("negative root not defined");
        } else if (A == null || A.isZERO() || A.isONE()) {
            return A;
        } else {
            RingElem R = new BigInteger(root(new BigDecimal(A.val, new MathContext(A.val.bitLength() + 2)), n).val.toBigInteger());
            while (A.compareTo((BigInteger) Power.positivePower(R, (long) n)) < 0) {
                R = R.subtract(BigInteger.ONE);
            }
            return R;
        }
    }

    public static BigInteger sqrt(BigInteger A) {
        if (A == null || A.isZERO() || A.isONE()) {
            return A;
        }
        BigInteger R = new BigInteger(sqrt(new BigDecimal(A.val, new MathContext(A.val.bitLength() + 2))).val.toBigInteger());
        while (A.compareTo(R.multiply(R)) < 0) {
            R = R.subtract(BigInteger.ONE);
        }
        return R;
    }

    public static BigInteger sqrtInt(BigInteger A) {
        if (A == null || A.isZERO() || A.isONE()) {
            return A;
        }
        int s = A.signum();
        if (s < 0) {
            throw new ArithmeticException("root of negative not defined");
        } else if (s == 0) {
            return A;
        } else {
            BigInteger R1;
            int log2 = A.val.bitLength();
            BigInteger R = new BigInteger(A.val.shiftRight(log2 - (log2 / 2)));
            BigInteger d = R;
            while (!d.isZERO()) {
                BigInteger d2 = new BigInteger(d.val.shiftRight(1));
                R1 = R.sum(d2);
                s = A.compareTo(R1.multiply(R1));
                if (s == 0) {
                    return R1;
                }
                if (s > 0) {
                    R = R1;
                    d = d2;
                } else {
                    d = d2;
                }
            }
            do {
                R1 = R.sum(BigInteger.ONE);
                s = A.compareTo(R1.multiply(R1));
                if (s == 0) {
                    return R1;
                }
                if (s > 0) {
                    R = R1;
                    continue;
                }
            } while (s >= 0);
            return R;
        }
    }

    public static BigDecimal sqrt(BigDecimal A) {
        if (A == null || A.isZERO() || A.isONE()) {
            return A;
        }
        if (A.signum() < 0) {
            throw new ArithmeticException("root of negative not defined");
        } else if (A.abs().val.compareTo(BigDecimal.ONE.val) < 0) {
            return sqrt(A.inverse()).inverse();
        } else {
            MathContext mc = A.context;
            BigDecimal eps = (BigDecimal) Power.positivePower(new BigDecimal("0.1"), (long) (Math.max(mc.getPrecision(), MathContext.DECIMAL64.getPrecision()) / 2));
            BigDecimal Ap = new BigDecimal(A.val, mc);
            BigDecimal ninv = new BigDecimal(0.5d, mc);
            BigDecimal R = Ap.multiply(ninv);
            int i = 0;
            while (true) {
                BigDecimal R1 = R.sum(Ap.divide(R)).multiply(ninv);
                R = R1;
                if (R.subtract(R1).abs().val.compareTo(eps.val) <= 0) {
                    return R;
                }
                int i2 = i + 1;
                if (i % 11 == 0) {
                    eps = eps.sum(eps);
                    i = i2;
                } else {
                    i = i2;
                }
            }
        }
    }

    public static BigDecimal root(BigDecimal A, int n) {
        if (n == 1) {
            return A;
        }
        if (n == 2) {
            return sqrt(A);
        }
        if (n < 1) {
            throw new IllegalArgumentException("negative root not defined");
        } else if (A == null || A.isZERO() || A.isONE()) {
            return A;
        } else {
            if (A.signum() < 0) {
                throw new ArithmeticException("root of negative not defined");
            }
            if (A.abs().val.compareTo(BigDecimal.ONE.val) < 0) {
                return root(A.inverse(), n).inverse();
            }
            MathContext mc = A.context;
            BigDecimal eps = (BigDecimal) Power.positivePower(new BigDecimal("0.1"), (long) ((Math.max(mc.getPrecision(), MathContext.DECIMAL64.getPrecision()) * 2) / 3));
            BigDecimal Ap = A;
            BigDecimal N = new BigDecimal((long) n, mc);
            BigDecimal ninv = new BigDecimal(1.0d / ((double) n), mc);
            BigDecimal nsub = new BigDecimal(1.0d, mc).subtract(ninv);
            RingElem R = Ap.multiply(ninv);
            int i = 0;
            while (true) {
                BigDecimal R1 = R.multiply(nsub).sum(Ap.divide(((BigDecimal) Power.positivePower(R, (long) (n - 1))).multiply(N)));
                BigDecimal R2 = R1;
                if (R.subtract(R1).abs().val.compareTo(eps.val) <= 0) {
                    return R2;
                }
                int i2 = i + 1;
                if (i % 11 == 0) {
                    eps = eps.sum(eps);
                    i = i2;
                } else {
                    i = i2;
                }
            }
        }
    }
}
