package edu.jas.application;

import edu.jas.kern.StringUtil;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPairFactory;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.log4j.Logger;

public class LocalRing<C extends GcdRingElem<C>> implements RingFactory<Local<C>>, QuotPairFactory<GenPolynomial<C>, Local<C>> {
    private static final Logger logger;
    protected final GreatestCommonDivisor<C> engine;
    public final Ideal<C> ideal;
    protected int isField;
    public final GenPolynomialRing<C> ring;

    static {
        logger = Logger.getLogger(LocalRing.class);
    }

    public LocalRing(Ideal<C> i) {
        this.isField = -1;
        if (i == null) {
            throw new IllegalArgumentException("ideal may not be null");
        }
        this.ideal = i.GB();
        if (this.ideal.isONE()) {
            throw new IllegalArgumentException("ideal may not be 1");
        }
        if (this.ideal.isMaximal()) {
            this.isField = 1;
        } else {
            this.isField = 0;
            logger.warn("ideal not maximal");
        }
        this.ring = this.ideal.list.ring;
        this.engine = GCDFactory.getProxy(this.ring.coFac);
    }

    public GenPolynomialRing<C> pairFactory() {
        return this.ring;
    }

    public Local<C> create(GenPolynomial<C> n) {
        return new Local(this, n);
    }

    public Local<C> create(GenPolynomial<C> n, GenPolynomial<C> d) {
        return new Local(this, n, d);
    }

    public boolean isFinite() {
        return this.ring.isFinite() && this.ideal.bb.commonZeroTest(this.ideal.getList()) <= 0;
    }

    public Local<C> copy(Local<C> c) {
        return new Local(c.ring, c.num, c.den, true);
    }

    public Local<C> getZERO() {
        return new Local(this, this.ring.getZERO());
    }

    public Local<C> getONE() {
        return new Local(this, this.ring.getONE());
    }

    public List<Local<C>> generators() {
        List<GenPolynomial<C>> pgens = this.ring.generators();
        List<Local<C>> gens = new ArrayList(pgens.size());
        GenPolynomial<C> one = this.ring.getONE();
        for (GenPolynomial p : pgens) {
            gens.add(new Local(this, p));
            if (!(p.isONE() || this.ideal.contains(p))) {
                gens.add(new Local(this, one, p));
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
        return new Local(this, this.ring.fromInteger(a));
    }

    public Local<C> fromInteger(long a) {
        return new Local(this, this.ring.fromInteger(a));
    }

    public String toString() {
        return "LocalRing[ " + this.ideal.toString() + " ]";
    }

    public String toScript() {
        return "LC(" + this.ideal.list.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (!(b instanceof LocalRing)) {
            return false;
        }
        LocalRing<C> a = null;
        try {
            a = (LocalRing) b;
        } catch (ClassCastException e) {
        }
        if (a == null || !this.ring.equals(a.ring)) {
            return false;
        }
        return this.ideal.equals(a.ideal);
    }

    public int hashCode() {
        return this.ideal.hashCode();
    }

    public Local<C> random(int n) {
        GenPolynomial<C> s;
        GenPolynomial<C> r = this.ideal.normalform(this.ring.random(n).monic());
        do {
            s = this.ideal.normalform(this.ring.random(n).monic());
        } while (s.isZERO());
        return new Local(this, r, s, false);
    }

    public Local<C> random(int k, int l, int d, float q) {
        GenPolynomial<C> s;
        GenPolynomial<C> r = this.ideal.normalform(this.ring.random(k, l, d, q).monic());
        do {
            s = this.ideal.normalform(this.ring.random(k, l, d, q).monic());
        } while (s.isZERO());
        return new Local(this, r, s, false);
    }

    public Local<C> random(int n, Random rnd) {
        GenPolynomial<C> s;
        GenPolynomial<C> r = this.ideal.normalform(this.ring.random(n, rnd).monic());
        do {
            s = this.ideal.normalform(this.ring.random(n).monic());
        } while (s.isZERO());
        return new Local(this, r, s, false);
    }

    public Local<C> parse(String s) {
        int i = s.indexOf(VectorFormat.DEFAULT_PREFIX);
        if (i >= 0) {
            s = s.substring(i + 1);
        }
        i = s.lastIndexOf(VectorFormat.DEFAULT_SUFFIX);
        if (i >= 0) {
            s = s.substring(0, i);
        }
        i = s.indexOf("|");
        if (i < 0) {
            return new Local(this, this.ring.parse(s));
        }
        return new Local(this, this.ring.parse(s.substring(0, i)), this.ring.parse(s.substring(i + 1)));
    }

    public Local<C> parse(Reader r) {
        return parse(StringUtil.nextPairedString(r, '{', '}'));
    }
}
