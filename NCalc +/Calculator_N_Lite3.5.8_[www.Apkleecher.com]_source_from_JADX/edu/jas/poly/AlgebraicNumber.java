package edu.jas.poly;

import edu.jas.kern.PrettyPrint;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingElem;

public class AlgebraicNumber<C extends RingElem<C>> implements GcdRingElem<AlgebraicNumber<C>> {
    protected int isunit;
    public final AlgebraicNumberRing<C> ring;
    public final GenPolynomial<C> val;

    public AlgebraicNumber(AlgebraicNumberRing<C> r, GenPolynomial<C> a) {
        this.isunit = -1;
        this.ring = r;
        this.val = a.remainder(this.ring.modul);
        if (this.val.isZERO()) {
            this.isunit = 0;
        }
        if (this.ring.isField()) {
            this.isunit = 1;
        }
    }

    public AlgebraicNumber(AlgebraicNumberRing<C> r) {
        this(r, r.ring.getZERO());
    }

    public GenPolynomial<C> getVal() {
        return this.val;
    }

    public AlgebraicNumberRing<C> factory() {
        return this.ring;
    }

    public AlgebraicNumber<C> copy() {
        return new AlgebraicNumber(this.ring, this.val);
    }

    public boolean isZERO() {
        return this.val.equals(this.ring.ring.getZERO());
    }

    public boolean isONE() {
        return this.val.equals(this.ring.ring.getONE());
    }

    public boolean isUnit() {
        if (this.isunit > 0) {
            return true;
        }
        if (this.isunit == 0) {
            return false;
        }
        if (this.val.isZERO()) {
            this.isunit = 0;
            return false;
        } else if (this.ring.isField()) {
            this.isunit = 1;
            return true;
        } else {
            boolean u = this.val.gcd(this.ring.modul).isUnit();
            if (u) {
                this.isunit = 1;
                return u;
            }
            this.isunit = 0;
            return u;
        }
    }

    public String toString() {
        if (PrettyPrint.isTrue()) {
            return this.val.toString(this.ring.ring.vars);
        }
        return "AlgebraicNumber[ " + this.val.toString() + " ]";
    }

    public String toScript() {
        return this.val.toScript();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(AlgebraicNumber<C> b) {
        int s = 0;
        if (this.ring.modul != b.ring.modul) {
            s = this.ring.modul.compareTo(b.ring.modul);
        }
        return s != 0 ? s : this.val.compareTo(b.val);
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof AlgebraicNumber)) {
            return false;
        }
        AlgebraicNumber a = (AlgebraicNumber) b;
        if (this.ring.equals(a.ring) && compareTo(a) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.val.hashCode() * 37) + this.ring.hashCode();
    }

    public AlgebraicNumber<C> abs() {
        return new AlgebraicNumber(this.ring, this.val.abs());
    }

    public AlgebraicNumber<C> sum(AlgebraicNumber<C> S) {
        return new AlgebraicNumber(this.ring, this.val.sum(S.val));
    }

    public AlgebraicNumber<C> sum(GenPolynomial<C> c) {
        return new AlgebraicNumber(this.ring, this.val.sum((GenPolynomial) c));
    }

    public AlgebraicNumber<C> sum(C c) {
        return new AlgebraicNumber(this.ring, this.val.sum((RingElem) c));
    }

    public AlgebraicNumber<C> negate() {
        return new AlgebraicNumber(this.ring, this.val.negate());
    }

    public int signum() {
        return this.val.signum();
    }

    public AlgebraicNumber<C> subtract(AlgebraicNumber<C> S) {
        return new AlgebraicNumber(this.ring, this.val.subtract(S.val));
    }

    public AlgebraicNumber<C> divide(AlgebraicNumber<C> S) {
        return multiply(S.inverse());
    }

    public AlgebraicNumber<C> inverse() {
        try {
            return new AlgebraicNumber(this.ring, this.val.modInverse(this.ring.modul));
        } catch (AlgebraicNotInvertibleException e) {
            throw e;
        } catch (NotInvertibleException e2) {
            throw new AlgebraicNotInvertibleException(e2 + ", val = " + this.val + ", modul = " + this.ring.modul + ", gcd = " + this.val.gcd(this.ring.modul), e2);
        }
    }

    public AlgebraicNumber<C> remainder(AlgebraicNumber<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        } else if (S.isONE()) {
            return this.ring.getZERO();
        } else {
            if (S.isUnit()) {
                return this.ring.getZERO();
            }
            return new AlgebraicNumber(this.ring, this.val.remainder(S.val));
        }
    }

    public AlgebraicNumber<C>[] quotientRemainder(AlgebraicNumber<C> S) {
        return new AlgebraicNumber[]{divide((AlgebraicNumber) S), remainder((AlgebraicNumber) S)};
    }

    public AlgebraicNumber<C> multiply(AlgebraicNumber<C> S) {
        return new AlgebraicNumber(this.ring, this.val.multiply(S.val));
    }

    public AlgebraicNumber<C> multiply(C c) {
        return new AlgebraicNumber(this.ring, this.val.multiply((RingElem) c));
    }

    public AlgebraicNumber<C> multiply(GenPolynomial<C> c) {
        return new AlgebraicNumber(this.ring, this.val.multiply((GenPolynomial) c));
    }

    public AlgebraicNumber<C> monic() {
        return new AlgebraicNumber(this.ring, this.val.monic());
    }

    public AlgebraicNumber<C> gcd(AlgebraicNumber<C> S) {
        if (S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (isUnit() || S.isUnit()) {
            return this.ring.getONE();
        }
        return new AlgebraicNumber(this.ring, this.val.gcd(S.val));
    }

    public AlgebraicNumber<C>[] egcd(AlgebraicNumber<C> S) {
        AlgebraicNumber<C>[] ret = new AlgebraicNumber[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
        } else if (isZERO()) {
            ret[0] = S;
        } else if (isUnit() || S.isUnit()) {
            ret[0] = this.ring.getONE();
            if (isUnit() && S.isUnit()) {
                AlgebraicNumber half = this.ring.fromInteger(2).inverse();
                ret[1] = inverse().multiply(half);
                ret[2] = S.inverse().multiply(half);
            } else if (isUnit()) {
                ret[1] = inverse();
                ret[2] = this.ring.getZERO();
            } else {
                ret[1] = this.ring.getZERO();
                ret[2] = S.inverse();
            }
        } else {
            GenPolynomial<C> q = this.val;
            GenPolynomial<C> r = S.val;
            GenPolynomial<C> c1 = this.ring.ring.getONE();
            GenPolynomial<C> d1 = this.ring.ring.getZERO();
            GenPolynomial<C> c2 = this.ring.ring.getZERO();
            GenPolynomial<C> d2 = this.ring.ring.getONE();
            while (!r.isZERO()) {
                GenPolynomial<C>[] qr = q.quotientRemainder(r);
                q = qr[0];
                GenPolynomial<C> x1 = c1.subtract(q.multiply((GenPolynomial) d1));
                GenPolynomial<C> x2 = c2.subtract(q.multiply((GenPolynomial) d2));
                c1 = d1;
                c2 = d2;
                d1 = x1;
                d2 = x2;
                q = r;
                r = qr[1];
            }
            ret[0] = new AlgebraicNumber(this.ring, q);
            ret[1] = new AlgebraicNumber(this.ring, c1);
            ret[2] = new AlgebraicNumber(this.ring, c2);
        }
        return ret;
    }
}
