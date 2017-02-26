package edu.jas.arith;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import java.math.BigInteger;

public final class ModInteger implements GcdRingElem<ModInteger>, Modular {
    public final ModIntegerRing ring;
    public final BigInteger val;

    public ModInteger(ModIntegerRing m, BigInteger a) {
        this.ring = m;
        this.val = a.mod(this.ring.modul);
    }

    public ModInteger(ModIntegerRing m, long a) {
        this(m, new BigInteger(String.valueOf(a)));
    }

    public ModInteger(ModIntegerRing m, String s) {
        this(m, new BigInteger(s.trim()));
    }

    public ModInteger(ModIntegerRing m) {
        this(m, BigInteger.ZERO);
    }

    public BigInteger getVal() {
        return this.val;
    }

    public BigInteger getModul() {
        return this.ring.modul;
    }

    public ModIntegerRing factory() {
        return this.ring;
    }

    public BigInteger getSymmetricVal() {
        if (this.val.add(this.val).compareTo(this.ring.modul) > 0) {
            return this.val.subtract(this.ring.modul);
        }
        return this.val;
    }

    public BigInteger getInteger() {
        return new BigInteger(this.val);
    }

    public BigInteger getSymmetricInteger() {
        BigInteger v = this.val;
        if (this.val.add(this.val).compareTo(this.ring.modul) > 0) {
            v = this.val.subtract(this.ring.modul);
        }
        return new BigInteger(v);
    }

    public ModInteger copy() {
        return new ModInteger(this.ring, this.val);
    }

    public boolean isZERO() {
        return this.val.equals(BigInteger.ZERO);
    }

    public boolean isONE() {
        return this.val.equals(BigInteger.ONE);
    }

    public boolean isUnit() {
        if (isZERO()) {
            return false;
        }
        if (this.ring.isField()) {
            return true;
        }
        return this.ring.modul.gcd(this.val).abs().equals(BigInteger.ONE);
    }

    public String toString() {
        return this.val.toString();
    }

    public String toScript() {
        return toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(ModInteger b) {
        BigInteger v = b.val;
        if (this.ring != b.ring) {
            v = v.mod(this.ring.modul);
        }
        return this.val.compareTo(v);
    }

    public static int MICOMP(ModInteger A, ModInteger B) {
        if (A == null) {
            return -B.signum();
        }
        return A.compareTo(B);
    }

    public boolean equals(Object b) {
        if ((b instanceof ModInteger) && compareTo((ModInteger) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.val.hashCode();
    }

    public ModInteger abs() {
        return new ModInteger(this.ring, this.val.abs());
    }

    public static ModInteger MIABS(ModInteger A) {
        if (A == null) {
            return null;
        }
        return A.abs();
    }

    public ModInteger negate() {
        return new ModInteger(this.ring, this.val.negate());
    }

    public static ModInteger MINEG(ModInteger A) {
        if (A == null) {
            return null;
        }
        return A.negate();
    }

    public int signum() {
        return this.val.signum();
    }

    public static int MISIGN(ModInteger A) {
        if (A == null) {
            return 0;
        }
        return A.signum();
    }

    public ModInteger subtract(ModInteger S) {
        return new ModInteger(this.ring, this.val.subtract(S.val));
    }

    public static ModInteger MIDIF(ModInteger A, ModInteger B) {
        if (A == null) {
            return B.negate();
        }
        return A.subtract(B);
    }

    public ModInteger divide(ModInteger S) {
        ModInteger multiply;
        try {
            multiply = multiply(S.inverse());
        } catch (Throwable e) {
            if (this.val.remainder(S.val).equals(BigInteger.ZERO)) {
                multiply = new ModInteger(this.ring, this.val.divide(S.val));
            } else {
                throw new NotInvertibleException(e);
            }
        } catch (Throwable a) {
            throw new NotInvertibleException(a);
        }
        return multiply;
    }

    public static ModInteger MIQ(ModInteger A, ModInteger B) {
        if (A == null) {
            return null;
        }
        return A.divide(B);
    }

    public ModInteger inverse() {
        try {
            return new ModInteger(this.ring, this.val.modInverse(this.ring.modul));
        } catch (Throwable e) {
            BigInteger g = this.val.gcd(this.ring.modul);
            throw new ModularNotInvertibleException(e, new BigInteger(this.ring.modul), new BigInteger(g), new BigInteger(this.ring.modul.divide(g)));
        }
    }

    public static ModInteger MIINV(ModInteger A) {
        if (A == null) {
            return null;
        }
        return A.inverse();
    }

    public ModInteger remainder(ModInteger S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        } else if (S.isONE()) {
            return this.ring.getZERO();
        } else {
            if (S.isUnit()) {
                return this.ring.getZERO();
            }
            return new ModInteger(this.ring, this.val.remainder(S.val));
        }
    }

    public static ModInteger MIREM(ModInteger A, ModInteger B) {
        if (A == null) {
            return null;
        }
        return A.remainder(B);
    }

    public ModInteger[] quotientRemainder(ModInteger S) {
        return new ModInteger[]{divide(S), remainder(S)};
    }

    public ModInteger multiply(ModInteger S) {
        return new ModInteger(this.ring, this.val.multiply(S.val));
    }

    public static ModInteger MIPROD(ModInteger A, ModInteger B) {
        if (A == null) {
            return null;
        }
        return A.multiply(B);
    }

    public ModInteger sum(ModInteger S) {
        return new ModInteger(this.ring, this.val.add(S.val));
    }

    public static ModInteger MISUM(ModInteger A, ModInteger B) {
        if (A == null) {
            return null;
        }
        return A.sum(B);
    }

    public ModInteger gcd(ModInteger S) {
        if (S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (isUnit() || S.isUnit()) {
            return this.ring.getONE();
        }
        return new ModInteger(this.ring, this.val.gcd(S.val));
    }

    public ModInteger[] hegcd(ModInteger S) {
        ModInteger[] ret = new ModInteger[]{null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
            ret[1] = this.ring.getONE();
        } else if (isZERO()) {
            ret[0] = S;
        } else {
            BigInteger q = this.val;
            BigInteger r = S.val;
            BigInteger c1 = BigInteger.ONE.val;
            BigInteger d1 = BigInteger.ZERO.val;
            while (!r.equals(BigInteger.ZERO)) {
                BigInteger[] qr = q.divideAndRemainder(r);
                BigInteger x1 = c1.subtract(qr[0].multiply(d1));
                c1 = d1;
                d1 = x1;
                q = r;
                r = qr[1];
            }
            ret[0] = new ModInteger(this.ring, q);
            ret[1] = new ModInteger(this.ring, c1);
        }
        return ret;
    }

    public ModInteger[] egcd(ModInteger S) {
        ModInteger[] ret = new ModInteger[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else if (isUnit() || S.isUnit()) {
            ret[0] = this.ring.getONE();
            if (isUnit() && S.isUnit()) {
                ret[1] = this.ring.getONE();
                ret[2] = ret[0].subtract(ret[1].multiply(this)).divide(S);
            } else if (isUnit()) {
                ret[1] = inverse();
                ret[2] = this.ring.getZERO();
            } else {
                ret[1] = this.ring.getZERO();
                ret[2] = S.inverse();
            }
        } else {
            BigInteger q = this.val;
            BigInteger r = S.val;
            BigInteger c1 = BigInteger.ONE.val;
            BigInteger d1 = BigInteger.ZERO.val;
            BigInteger c2 = BigInteger.ZERO.val;
            BigInteger d2 = BigInteger.ONE.val;
            while (!r.equals(BigInteger.ZERO)) {
                BigInteger[] qr = q.divideAndRemainder(r);
                q = qr[0];
                BigInteger x1 = c1.subtract(q.multiply(d1));
                BigInteger x2 = c2.subtract(q.multiply(d2));
                c1 = d1;
                c2 = d2;
                d1 = x1;
                d2 = x2;
                q = r;
                r = qr[1];
            }
            ret[0] = new ModInteger(this.ring, q);
            ret[1] = new ModInteger(this.ring, c1);
            ret[2] = new ModInteger(this.ring, c2);
        }
        return ret;
    }
}
