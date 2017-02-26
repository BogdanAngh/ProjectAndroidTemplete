package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class ResidueRing<C extends RingElem<C>> implements RingFactory<Residue<C>> {
    private static final Logger logger;
    protected int isField;
    protected final C modul;
    protected final RingFactory<C> ring;

    static {
        logger = Logger.getLogger(ResidueRing.class);
    }

    public ResidueRing(RingFactory<C> r, C m) {
        this.isField = -1;
        this.ring = r;
        if (m.isZERO()) {
            throw new IllegalArgumentException("modul may not be null");
        }
        if (m.isONE()) {
            logger.warn("modul is one");
        }
        if (m.signum() < 0) {
            m = (RingElem) m.negate();
        }
        this.modul = m;
    }

    public boolean isFinite() {
        throw new UnsupportedOperationException("not implemented");
    }

    public Residue<C> copy(Residue<C> c) {
        return new Residue(c.ring, c.val);
    }

    public Residue<C> getZERO() {
        return new Residue(this, (RingElem) this.ring.getZERO());
    }

    public Residue<C> getONE() {
        Residue<C> one = new Residue(this, (RingElem) this.ring.getONE());
        if (one.isZERO()) {
            logger.warn("one is zero, so all residues are 0");
        }
        return one;
    }

    public List<Residue<C>> generators() {
        List<? extends C> rgens = this.ring.generators();
        List<Residue<C>> gens = new ArrayList(rgens.size());
        Iterator i$ = rgens.iterator();
        while (i$.hasNext()) {
            gens.add(new Residue(this, (RingElem) i$.next()));
        }
        return gens;
    }

    public boolean isCommutative() {
        return this.ring.isCommutative();
    }

    public boolean isAssociative() {
        return this.ring.isAssociative();
    }

    public boolean isField() {
        if (this.isField > 0) {
            return true;
        }
        return this.isField == 0 ? false : false;
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public Residue<C> fromInteger(BigInteger a) {
        return new Residue(this, (RingElem) this.ring.fromInteger(a));
    }

    public Residue<C> fromInteger(long a) {
        return new Residue(this, (RingElem) this.ring.fromInteger(a));
    }

    public String toString() {
        return "Residue[ " + this.modul.toString() + " ]";
    }

    public String toScript() {
        return "ResidueRing(" + this.modul.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof ResidueRing)) {
            return false;
        }
        ResidueRing<C> a = (ResidueRing) b;
        if (this.ring.equals(a.ring)) {
            return this.modul.equals(a.modul);
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + this.modul.hashCode();
    }

    public Residue<C> random(int n) {
        return new Residue(this, (RingElem) this.ring.random(n));
    }

    public Residue<C> random(int n, Random rnd) {
        return new Residue(this, (RingElem) this.ring.random(n, rnd));
    }

    public Residue<C> parse(String s) {
        return new Residue(this, (RingElem) this.ring.parse(s));
    }

    public Residue<C> parse(Reader r) {
        return new Residue(this, (RingElem) this.ring.parse(r));
    }
}
