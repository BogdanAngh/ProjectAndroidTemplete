package edu.jas.poly;

import edu.jas.structure.QuotPairFactory;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class QuotientRing<C extends RingElem<C>> implements RingFactory<Quotient<C>>, QuotPairFactory<C, Quotient<C>> {
    private static final Logger logger;
    private final boolean debug;
    public final RingFactory<C> ring;

    static {
        logger = Logger.getLogger(QuotientRing.class);
    }

    public QuotientRing(RingFactory<C> r) {
        this.debug = logger.isDebugEnabled();
        this.ring = r;
    }

    public RingFactory<C> pairFactory() {
        return this.ring;
    }

    public Quotient<C> create(C n) {
        return new Quotient(this, n);
    }

    public Quotient<C> create(C n, C d) {
        return new Quotient(this, n, d);
    }

    public boolean isFinite() {
        return this.ring.isFinite();
    }

    public Quotient<C> copy(Quotient<C> c) {
        return new Quotient(c.ring, c.num, c.den, true);
    }

    public Quotient<C> getZERO() {
        return new Quotient(this, (RingElem) this.ring.getZERO());
    }

    public Quotient<C> getONE() {
        return new Quotient(this, (RingElem) this.ring.getONE());
    }

    public List<Quotient<C>> generators() {
        List<? extends C> rgens = this.ring.generators();
        List<Quotient<C>> gens = new ArrayList(rgens.size());
        Iterator i$ = rgens.iterator();
        while (i$.hasNext()) {
            gens.add(new Quotient(this, (RingElem) i$.next()));
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
        return true;
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public Quotient<C> fromInteger(BigInteger a) {
        return new Quotient(this, (RingElem) this.ring.fromInteger(a));
    }

    public Quotient<C> fromInteger(long a) {
        return new Quotient(this, (RingElem) this.ring.fromInteger(a));
    }

    public String toString() {
        return "Quotient[ " + this.ring.toString() + " ]";
    }

    public String toScript() {
        return "QuotientRing(" + this.ring.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof QuotientRing)) {
            return false;
        }
        return this.ring.equals(((QuotientRing) b).ring);
    }

    public int hashCode() {
        return this.ring.hashCode();
    }

    public Quotient<C> random(int n) {
        RingElem r = (RingElem) this.ring.random(n);
        C s = (RingElem) this.ring.random(n);
        while (s.isZERO()) {
            RingElem s2 = (RingElem) this.ring.random(n);
        }
        return new Quotient(this, r, s, false);
    }

    public Quotient<C> random(int n, Random rnd) {
        RingElem r = (RingElem) this.ring.random(n, rnd);
        C s = (RingElem) this.ring.random(n, rnd);
        while (s.isZERO()) {
            RingElem s2 = (RingElem) this.ring.random(n, rnd);
        }
        return new Quotient(this, r, s, false);
    }

    public Quotient<C> parse(String s) {
        return new Quotient(this, (RingElem) this.ring.parse(s));
    }

    public Quotient<C> parse(Reader r) {
        RingElem x = (RingElem) this.ring.parse(r);
        if (this.debug) {
            logger.debug("x = " + x);
        }
        return new Quotient(this, x);
    }
}
