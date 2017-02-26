package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class Residue<C extends RingElem<C>> implements RingElem<Residue<C>> {
    private static final Logger logger;
    private final boolean debug;
    protected int isunit;
    protected final ResidueRing<C> ring;
    protected final C val;

    static {
        logger = Logger.getLogger(Residue.class);
    }

    public Residue(ResidueRing<C> r) {
        this(r, (RingElem) r.ring.getZERO(), 0);
    }

    public Residue(ResidueRing<C> r, C a) {
        this(r, a, -1);
    }

    public Residue(ResidueRing<C> r, C a, int u) {
        this.debug = logger.isDebugEnabled();
        this.isunit = -1;
        this.ring = r;
        C v = (RingElem) a.remainder(this.ring.modul);
        if (v.signum() < 0) {
            v = (RingElem) v.sum(this.ring.modul);
        }
        this.val = v;
        if (u == 0 || u == 1) {
            this.isunit = u;
        } else if (this.val.isZERO()) {
            this.isunit = 0;
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
        return new Residue(this.ring, this.val);
    }

    public boolean isZERO() {
        return this.val.equals(this.ring.ring.getZERO());
    }

    public boolean isONE() {
        return this.val.equals(this.ring.ring.getONE());
    }

    public boolean isUnit() {
        if (this.isunit == 1) {
            return true;
        }
        if (this.isunit == 0) {
            return false;
        }
        if (!(this.val instanceof GcdRingElem) || !(this.ring.modul instanceof GcdRingElem)) {
            return false;
        }
        C gcd = this.val.gcd(this.ring.modul);
        if (this.debug) {
            logger.info("gcd = " + gcd);
        }
        boolean u = gcd.isONE();
        if (u) {
            this.isunit = 1;
            return u;
        }
        this.isunit = 0;
        return u;
    }

    public String toString() {
        return "Residue[ " + this.val.toString() + " mod " + this.ring.toString() + " ]";
    }

    public String toScript() {
        return "Residue( " + this.val.toScript() + " , " + this.ring.toScript() + " )";
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public int compareTo(Residue<C> b) {
        C v = b.val;
        if (!this.ring.equals(b.ring)) {
            v = (RingElem) v.remainder(this.ring.modul);
        }
        return this.val.compareTo(v);
    }

    public boolean equals(Object b) {
        if (b != null && (b instanceof Residue) && compareTo((Residue) b) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + this.val.hashCode();
    }

    public Residue<C> abs() {
        return new Residue(this.ring, (RingElem) this.val.abs());
    }

    public Residue<C> sum(Residue<C> S) {
        return new Residue(this.ring, (RingElem) this.val.sum(S.val));
    }

    public Residue<C> negate() {
        return new Residue(this.ring, (RingElem) this.val.negate());
    }

    public int signum() {
        return this.val.signum();
    }

    public Residue<C> subtract(Residue<C> S) {
        return new Residue(this.ring, (RingElem) this.val.subtract(S.val));
    }

    public Residue<C> divide(Residue<C> S) {
        return multiply(S.inverse());
    }

    public Residue<C> inverse() {
        if (this.isunit == 0) {
            throw new NotInvertibleException("element not invertible (0) " + this);
        } else if ((this.val instanceof GcdRingElem) && (this.ring.modul instanceof GcdRingElem)) {
            RingElem[] egcd = this.val.egcd(this.ring.modul);
            if (this.debug) {
                logger.info("egcd = " + egcd[0] + ", f = " + egcd[1]);
            }
            if (egcd[0].isONE()) {
                this.isunit = 1;
                return new Residue(this.ring, egcd[1]);
            }
            this.isunit = 0;
            throw new NotInvertibleException("element not invertible (gcd)" + this);
        } else if (this.val.isUnit()) {
            return new Residue(this.ring, (RingElem) this.val.inverse());
        } else {
            System.out.println("isunit = " + this.isunit + ", isUnit() = " + isUnit());
            throw new NotInvertibleException("element not invertible (!gcd)" + this);
        }
    }

    public Residue<C> remainder(Residue<C> S) {
        return new Residue(this.ring, (RingElem) this.val.remainder(S.val));
    }

    public Residue<C>[] quotientRemainder(Residue<C> S) {
        return new Residue[]{divide((Residue) S), remainder((Residue) S)};
    }

    public Residue<C> multiply(Residue<C> S) {
        return new Residue(this.ring, (RingElem) this.val.multiply(S.val));
    }

    public Residue<C> gcd(Residue<C> residue) {
        throw new UnsupportedOperationException("gcd not implemented " + getClass().getName());
    }

    public Residue<C>[] egcd(Residue<C> residue) {
        throw new UnsupportedOperationException("egcd not implemented " + getClass().getName());
    }
}
