package edu.jas.ufd;

import edu.jas.kern.StringUtil;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPairFactory;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.log4j.Logger;

public class QuotientRing<C extends GcdRingElem<C>> implements RingFactory<Quotient<C>>, QuotPairFactory<GenPolynomial<C>, Quotient<C>> {
    private static final Logger logger;
    public final GreatestCommonDivisor<C> engine;
    public final GenPolynomialRing<C> ring;
    public final boolean ufdGCD;

    static {
        logger = Logger.getLogger(QuotientRing.class);
    }

    public QuotientRing(GenPolynomialRing<C> r) {
        this(r, true);
    }

    public QuotientRing(GenPolynomialRing<C> r, boolean ufdGCD) {
        this.ring = r;
        this.ufdGCD = ufdGCD;
        this.engine = GCDFactory.getProxy(this.ring.coFac);
        logger.debug("quotient ring constructed");
    }

    public GenPolynomialRing<C> pairFactory() {
        return this.ring;
    }

    public Quotient<C> create(GenPolynomial<C> n) {
        return new Quotient(this, n);
    }

    public Quotient<C> create(GenPolynomial<C> n, GenPolynomial<C> d) {
        return new Quotient(this, n, d);
    }

    protected GenPolynomial<C> divide(GenPolynomial<C> n, GenPolynomial<C> d) {
        return PolyUtil.basePseudoDivide(n, d);
    }

    protected GenPolynomial<C> gcd(GenPolynomial<C> n, GenPolynomial<C> d) {
        if (this.ufdGCD) {
            return this.engine.gcd(n, d);
        }
        return this.engine.gcd(n, d);
    }

    public boolean isFinite() {
        return false;
    }

    public Quotient<C> copy(Quotient<C> c) {
        return new Quotient(c.ring, c.num, c.den, true);
    }

    public Quotient<C> getZERO() {
        return new Quotient(this, this.ring.getZERO());
    }

    public Quotient<C> getONE() {
        return new Quotient(this, this.ring.getONE());
    }

    public List<Quotient<C>> generators() {
        List<GenPolynomial<C>> pgens = this.ring.generators();
        List<Quotient<C>> gens = new ArrayList(pgens.size());
        for (GenPolynomial<C> p : pgens) {
            gens.add(new Quotient(this, p));
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
        return new Quotient(this, this.ring.fromInteger(a));
    }

    public Quotient<C> fromInteger(long a) {
        return new Quotient(this, this.ring.fromInteger(a));
    }

    public String toString() {
        String s;
        if (this.ring.coFac.characteristic().signum() == 0) {
            s = "RatFunc";
        } else {
            s = "ModFunc";
        }
        return s + "( " + this.ring.toString() + " )";
    }

    public String toScript() {
        return "RF(" + this.ring.toScript() + ")";
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
        GenPolynomial<C> r = this.ring.random(n).monic();
        GenPolynomial<C> s = this.ring.random(n).monic();
        while (s.isZERO()) {
            s = this.ring.random(n).monic();
        }
        return new Quotient(this, r, s, false);
    }

    public Quotient<C> random(int k, int l, int d, float q) {
        GenPolynomial<C> r = this.ring.random(k, l, d, q).monic();
        GenPolynomial<C> s = this.ring.random(k, l, d, q).monic();
        while (s.isZERO()) {
            s = this.ring.random(k, l, d, q).monic();
        }
        return new Quotient(this, r, s, false);
    }

    public Quotient<C> random(int n, Random rnd) {
        GenPolynomial<C> r = this.ring.random(n, rnd).monic();
        GenPolynomial<C> s = this.ring.random(n, rnd).monic();
        while (s.isZERO()) {
            s = this.ring.random(n, rnd).monic();
        }
        return new Quotient(this, r, s, false);
    }

    public Quotient<C> parse(String s) {
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
            return new Quotient(this, this.ring.parse(s));
        }
        return new Quotient(this, this.ring.parse(s.substring(0, i)), this.ring.parse(s.substring(i + 1)));
    }

    public Quotient<C> parse(Reader r) {
        return parse(StringUtil.nextPairedString(r, '{', '}'));
    }

    public long extensionDegree() {
        if (this.ring.nvar <= 0) {
            return 0;
        }
        return -1;
    }
}
