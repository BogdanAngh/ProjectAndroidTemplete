package edu.jas.application;

import edu.jas.kern.PrettyPrint;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;

public class Residue<C extends GcdRingElem<C>> implements GcdRingElem<Residue<C>> {
    protected int isunit;
    public final ResidueRing<C> ring;
    public final GenPolynomial<C> val;

    public Residue(ResidueRing<C> r) {
        this(r, r.ring.getZERO(), 0);
    }

    public Residue(ResidueRing<C> r, GenPolynomial<C> a) {
        this(r, a, -1);
    }

    public Residue(ResidueRing<C> r, GenPolynomial<C> a, int u) {
        this.isunit = -1;
        this.ring = r;
        this.val = this.ring.ideal.normalform((GenPolynomial) a);
        if (u == 0 || u == 1) {
            this.isunit = u;
        } else if (this.val.isZERO()) {
            this.isunit = 0;
        } else if (this.ring.isField()) {
            this.isunit = 1;
        } else {
            if (this.val.isUnit()) {
                this.isunit = 1;
            }
            this.isunit = -1;
        }
    }

    public ResidueRing<C> factory() {
        return this.ring;
    }

    public Residue<C> copy() {
        return new Residue(this.ring, this.val, this.isunit);
    }

    public boolean isZERO() {
        return this.val.isZERO();
    }

    public boolean isONE() {
        return this.val.isONE();
    }

    public boolean isUnit() {
        if (this.isunit > 0) {
            return true;
        }
        if (this.isunit == 0) {
            return false;
        }
        boolean u = this.ring.ideal.isUnit(this.val);
        if (u) {
            this.isunit = 1;
            return u;
        }
        this.isunit = 0;
        return u;
    }

    public boolean isConstant() {
        return this.val.isConstant();
    }

    public String toString() {
        if (PrettyPrint.isTrue()) {
            return this.val.toString(this.ring.ring.getVars());
        }
        return "Residue[ " + this.val.toString() + " mod " + this.ring.toString() + " ]";
    }

    public String toScript() {
        return this.val.toScript();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Residue<C> b) {
        GenPolynomial v = b.val;
        if (!this.ring.equals(b.ring)) {
            v = this.ring.ideal.normalform(v);
        }
        return this.val.compareTo(v);
    }

    public boolean equals(Object b) {
        if (!(b instanceof Residue)) {
            return false;
        }
        Residue a = null;
        try {
            a = (Residue) b;
        } catch (ClassCastException e) {
        }
        if (a == null || compareTo(a) != 0) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + this.val.hashCode();
    }

    public Residue<C> abs() {
        return new Residue(this.ring, this.val.abs(), this.isunit);
    }

    public Residue<C> sum(Residue<C> S) {
        return new Residue(this.ring, this.val.sum(S.val));
    }

    public Residue<C> negate() {
        return new Residue(this.ring, this.val.negate(), this.isunit);
    }

    public int signum() {
        return this.val.signum();
    }

    public Residue<C> subtract(Residue<C> S) {
        return new Residue(this.ring, this.val.subtract(S.val));
    }

    public Residue<C> divide(Residue<C> S) {
        if (this.ring.isField()) {
            return multiply(S.inverse());
        }
        return new Residue(this.ring, PolyUtil.basePseudoDivide(this.val, S.val));
    }

    public Residue<C> inverse() {
        return new Residue(this.ring, this.ring.ideal.inverse(this.val), 1);
    }

    public Residue<C> remainder(Residue<C> S) {
        return new Residue(this.ring, PolyUtil.baseSparsePseudoRemainder(this.val, S.val));
    }

    public Residue<C> multiply(Residue<C> S) {
        GenPolynomial<C> x = this.val.multiply(S.val);
        int i = -1;
        if (this.isunit == 1 && S.isunit == 1) {
            i = 1;
        } else if (this.isunit == 0 || S.isunit == 0) {
            i = 0;
        }
        return new Residue(this.ring, x, i);
    }

    public Residue<C> monic() {
        return new Residue(this.ring, this.val.monic(), this.isunit);
    }

    public Residue<C> gcd(Residue<C> b) {
        GenPolynomial<C> x = this.ring.engine.gcd(this.val, b.val);
        int i = -1;
        if (x.isONE()) {
            i = 1;
        } else {
            System.out.println("Residue gcd = " + x);
        }
        if (this.isunit == 1 && b.isunit == 1) {
            i = 1;
        }
        return new Residue(this.ring, x, i);
    }

    public Residue<C>[] egcd(Residue<C> residue) {
        throw new UnsupportedOperationException("egcd not implemented");
    }
}
