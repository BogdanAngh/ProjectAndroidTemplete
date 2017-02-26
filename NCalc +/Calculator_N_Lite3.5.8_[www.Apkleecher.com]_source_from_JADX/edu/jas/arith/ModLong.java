package edu.jas.arith;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import java.math.BigInteger;

public final class ModLong implements GcdRingElem<ModLong>, Modular {
    public final ModLongRing ring;
    public final long val;

    public ModLong(ModLongRing m, BigInteger a) {
        this(m, a.mod(m.getModul()).longValue());
    }

    public ModLong(ModLongRing m, long a) {
        this.ring = m;
        long v = a % this.ring.modul;
        if (v < 0) {
            v += this.ring.modul;
        }
        this.val = v;
    }

    public ModLong(ModLongRing m, Long a) {
        this(m, a.longValue());
    }

    public ModLong(ModLongRing m, String s) {
        this(m, new Long(s.trim()));
    }

    public ModLong(ModLongRing m) {
        this(m, 0);
    }

    public long getVal() {
        return this.val;
    }

    public long getModul() {
        return this.ring.modul;
    }

    public ModLongRing factory() {
        return this.ring;
    }

    public long getSymmetricVal() {
        if (this.val + this.val > this.ring.modul) {
            return this.val - this.ring.modul;
        }
        return this.val;
    }

    public BigInteger getInteger() {
        return new BigInteger(this.val);
    }

    public BigInteger getSymmetricInteger() {
        long v = this.val;
        if (this.val + this.val > this.ring.modul) {
            v = this.val - this.ring.modul;
        }
        return new BigInteger(v);
    }

    public ModLong copy() {
        return new ModLong(this.ring, this.val);
    }

    public boolean isZERO() {
        return this.val == 0;
    }

    public boolean isONE() {
        return this.val == 1;
    }

    public boolean isUnit() {
        if (isZERO()) {
            return false;
        }
        if (this.ring.isField()) {
            return true;
        }
        long g = gcd(this.ring.modul, this.val);
        if (g == 1 || g == -1) {
            return true;
        }
        return false;
    }

    public String toString() {
        return Long.toString(this.val);
    }

    public String toScript() {
        return toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(ModLong b) {
        long v = b.val;
        if (this.ring != b.ring) {
            v %= this.ring.modul;
        }
        if (this.val > v) {
            return 1;
        }
        return this.val < v ? -1 : 0;
    }

    public boolean equals(Object b) {
        if ((b instanceof ModLong) && compareTo((ModLong) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (int) this.val;
    }

    public ModLong abs() {
        return new ModLong(this.ring, this.val < 0 ? -this.val : this.val);
    }

    public ModLong negate() {
        return new ModLong(this.ring, -this.val);
    }

    public int signum() {
        if (this.val > 0) {
            return 1;
        }
        return this.val < 0 ? -1 : 0;
    }

    public ModLong subtract(ModLong S) {
        return new ModLong(this.ring, this.val - S.val);
    }

    public ModLong divide(ModLong S) {
        ModLong multiply;
        try {
            multiply = multiply(S.inverse());
        } catch (NotInvertibleException e) {
            if (this.val % S.val == 0) {
                multiply = new ModLong(this.ring, this.val / S.val);
            } else {
                throw new NotInvertibleException(e.getCause());
            }
        } catch (ArithmeticException a) {
            throw new NotInvertibleException(a.getCause());
        }
        return multiply;
    }

    public ModLong inverse() {
        try {
            return new ModLong(this.ring, modInverse(this.val, this.ring.modul));
        } catch (Throwable e) {
            long g = gcd(this.val, this.ring.modul);
            throw new ModularNotInvertibleException(e, new BigInteger(this.ring.modul), new BigInteger(g), new BigInteger(this.ring.modul / g));
        }
    }

    public ModLong remainder(ModLong S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        } else if (S.isONE()) {
            return this.ring.getZERO();
        } else {
            if (S.isUnit()) {
                return this.ring.getZERO();
            }
            return new ModLong(this.ring, this.val % S.val);
        }
    }

    public ModLong multiply(ModLong S) {
        return new ModLong(this.ring, this.val * S.val);
    }

    public ModLong sum(ModLong S) {
        return new ModLong(this.ring, this.val + S.val);
    }

    public ModLong gcd(ModLong S) {
        if (S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (isUnit() || S.isUnit()) {
            return this.ring.getONE();
        }
        return new ModLong(this.ring, gcd(this.val, S.val));
    }

    public ModLong[] egcd(ModLong S) {
        ModLong[] ret = new ModLong[]{null, null, null};
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
            long q = this.val;
            long r = S.val;
            long c1 = 1;
            long d1 = 0;
            long c2 = 0;
            long d2 = 1;
            while (r != 0) {
                long b = q % r;
                q /= r;
                long x1 = c1 - (q * d1);
                long x2 = c2 - (q * d2);
                c1 = d1;
                c2 = d2;
                d1 = x1;
                d2 = x2;
                q = r;
                r = b;
            }
            ret[0] = new ModLong(this.ring, q);
            ret[1] = new ModLong(this.ring, c1);
            ret[2] = new ModLong(this.ring, c2);
        }
        return ret;
    }

    public long gcd(long T, long S) {
        if (S == 0) {
            return T;
        }
        if (T == 0) {
            return S;
        }
        long a = T;
        long b = S;
        while (b != 0) {
            long r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    public long[] hegcd(long T, long S) {
        long[] ret = new long[2];
        if (S == 0) {
            ret[0] = T;
            ret[1] = 1;
        } else if (T == 0) {
            ret[0] = S;
            ret[1] = 0;
        } else {
            long a = T;
            long b = S;
            long a1 = 1;
            long b1 = 0;
            while (b != 0) {
                long q = a / b;
                long r = a % b;
                a = b;
                b = r;
                long r1 = a1 - (q * b1);
                a1 = b1;
                b1 = r1;
            }
            if (a1 < 0) {
                a1 += S;
            }
            ret[0] = a;
            ret[1] = a1;
        }
        return ret;
    }

    public long modInverse(long T, long m) {
        if (T == 0) {
            throw new NotInvertibleException("zero is not invertible");
        }
        long[] hegcd = hegcd(T, m);
        long a = hegcd[0];
        if (a == 1 || a == -1) {
            long b = hegcd[1];
            if (b == 0) {
                throw new NotInvertibleException("element not invertible, divisible by modul");
            } else if (b < 0) {
                return b + m;
            } else {
                return b;
            }
        }
        throw new ModularNotInvertibleException("element not invertible, gcd != 1", new BigInteger(m), new BigInteger(a), new BigInteger(m / a));
    }
}
