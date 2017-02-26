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

public class LocalRing<C extends RingElem<C>> implements RingFactory<Local<C>>, QuotPairFactory<C, Local<C>> {
    private static final Logger logger;
    protected final C ideal;
    protected int isField;
    protected final RingFactory<C> ring;

    static {
        logger = Logger.getLogger(LocalRing.class);
    }

    public LocalRing(RingFactory<C> r, C i) {
        this.isField = -1;
        this.ring = r;
        if (i == null) {
            throw new IllegalArgumentException("ideal may not be null");
        }
        this.ideal = i;
        if (this.ideal.isONE()) {
            throw new IllegalArgumentException("ideal may not be 1");
        }
    }

    public RingFactory<C> pairFactory() {
        return this.ring;
    }

    public Local<C> create(C n) {
        return new Local(this, n);
    }

    public Local<C> create(C n, C d) {
        return new Local(this, n, d);
    }

    public boolean isFinite() {
        return this.ring.isFinite();
    }

    public Local<C> copy(Local<C> c) {
        return new Local(c.ring, c.num, c.den, true);
    }

    public Local<C> getZERO() {
        return new Local(this, (RingElem) this.ring.getZERO());
    }

    public Local<C> getONE() {
        return new Local(this, (RingElem) this.ring.getONE());
    }

    public List<Local<C>> generators() {
        List<? extends C> rgens = this.ring.generators();
        List<Local<C>> gens = new ArrayList(rgens.size() - 1);
        Iterator i$ = rgens.iterator();
        while (i$.hasNext()) {
            RingElem c = (RingElem) i$.next();
            if (!c.isONE()) {
                gens.add(new Local(this, c));
            }
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

    public Local<C> fromInteger(BigInteger a) {
        return new Local(this, (RingElem) this.ring.fromInteger(a));
    }

    public Local<C> fromInteger(long a) {
        return new Local(this, (RingElem) this.ring.fromInteger(a));
    }

    public String toString() {
        return "Local[ " + this.ideal.toString() + " ]";
    }

    public String toScript() {
        return "LocalRing(" + this.ideal.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof LocalRing)) {
            return false;
        }
        LocalRing<C> a = (LocalRing) b;
        if (this.ring.equals(a.ring)) {
            return this.ideal.equals(a.ideal);
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() * 37) + this.ideal.hashCode();
    }

    public Local<C> random(int n) {
        RingElem r = (RingElem) this.ring.random(n);
        C s = (RingElem) ((RingElem) this.ring.random(n)).remainder(this.ideal);
        while (s.isZERO()) {
            logger.debug("zero was in ideal");
            RingElem s2 = (RingElem) ((RingElem) this.ring.random(n)).remainder(this.ideal);
        }
        return new Local(this, r, s, false);
    }

    public Local<C> random(int n, Random rnd) {
        RingElem r = (RingElem) this.ring.random(n, rnd);
        C s = (RingElem) ((RingElem) this.ring.random(n, rnd)).remainder(this.ideal);
        while (s.isZERO()) {
            logger.debug("zero was in ideal");
            RingElem s2 = (RingElem) ((RingElem) this.ring.random(n, rnd)).remainder(this.ideal);
        }
        return new Local(this, r, s, false);
    }

    public Local<C> parse(String s) {
        return new Local(this, (RingElem) this.ring.parse(s));
    }

    public Local<C> parse(Reader r) {
        return new Local(this, (RingElem) this.ring.parse(r));
    }
}
