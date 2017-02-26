package edu.jas.gbmod;

import edu.jas.kern.StringUtil;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.PolynomialList;
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

public class SolvableQuotientRing<C extends GcdRingElem<C>> implements RingFactory<SolvableQuotient<C>>, QuotPairFactory<GenPolynomial<C>, SolvableQuotient<C>> {
    private static final Logger logger;
    public final SolvableSyzygyAbstract<C> engine;
    public final GenSolvablePolynomialRing<C> ring;

    static {
        logger = Logger.getLogger(SolvableQuotientRing.class);
    }

    public SolvableQuotientRing(GenSolvablePolynomialRing<C> r) {
        this.ring = r;
        this.engine = new SolvableSyzygySeq(this.ring.coFac);
        logger.debug("quotient ring constructed");
    }

    public GenSolvablePolynomialRing<C> pairFactory() {
        return this.ring;
    }

    public SolvableQuotient<C> create(GenPolynomial<C> n) {
        return new SolvableQuotient(this, (GenSolvablePolynomial) n);
    }

    public SolvableQuotient<C> create(GenPolynomial<C> n, GenPolynomial<C> d) {
        return new SolvableQuotient(this, (GenSolvablePolynomial) n, (GenSolvablePolynomial) d);
    }

    public boolean isFinite() {
        return this.ring.isFinite();
    }

    public SolvableQuotient<C> copy(SolvableQuotient<C> c) {
        return new SolvableQuotient(c.ring, c.num, c.den, true);
    }

    public SolvableQuotient<C> getZERO() {
        return new SolvableQuotient(this, this.ring.getZERO());
    }

    public SolvableQuotient<C> getONE() {
        return new SolvableQuotient(this, this.ring.getONE());
    }

    public List<SolvableQuotient<C>> generators() {
        List<GenSolvablePolynomial<C>> pgens = PolynomialList.castToSolvableList(this.ring.generators());
        List<SolvableQuotient<C>> gens = new ArrayList((pgens.size() * 2) - 1);
        GenSolvablePolynomial<C> one = this.ring.getONE();
        for (GenSolvablePolynomial<C> p : pgens) {
            gens.add(new SolvableQuotient(this, p));
            if (!p.isONE()) {
                gens.add(new SolvableQuotient(this, one, p));
            }
        }
        return gens;
    }

    public boolean isCommutative() {
        return this.ring.isCommutative();
    }

    public boolean isAssociative() {
        if (!this.ring.isAssociative()) {
            return false;
        }
        List<SolvableQuotient<C>> gens = generators();
        int ngen = gens.size();
        for (int i = 0; i < ngen; i++) {
            SolvableQuotient Xi = (SolvableQuotient) gens.get(i);
            for (int j = i + 1; j < ngen; j++) {
                SolvableQuotient Xj = (SolvableQuotient) gens.get(j);
                int k = j + 1;
                while (k < ngen) {
                    SolvableQuotient<C> Xk = (SolvableQuotient) gens.get(k);
                    SolvableQuotient<C> p = Xk.multiply(Xj).multiply(Xi);
                    SolvableQuotient<C> q = Xk.multiply(Xj.multiply(Xi));
                    if (p.equals(q)) {
                        k++;
                    } else if (!logger.isInfoEnabled()) {
                        return false;
                    } else {
                        logger.info("Xk = " + Xk + ", Xj = " + Xj + ", Xi = " + Xi);
                        logger.info("p = ( Xk * Xj ) * Xi = " + p);
                        logger.info("q = Xk * ( Xj * Xi ) = " + q);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isField() {
        return true;
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public SolvableQuotient<C> fromInteger(BigInteger a) {
        return new SolvableQuotient(this, this.ring.fromInteger(a));
    }

    public SolvableQuotient<C> fromInteger(long a) {
        return new SolvableQuotient(this, this.ring.fromInteger(a));
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
        return "SRF(" + this.ring.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof SolvableQuotientRing)) {
            return false;
        }
        return this.ring.equals(((SolvableQuotientRing) b).ring);
    }

    public int hashCode() {
        return this.ring.hashCode();
    }

    public SolvableQuotient<C> random(int n) {
        GenSolvablePolynomial<C> s;
        GenSolvablePolynomial<C> r = this.ring.random(n).monic();
        do {
            s = this.ring.random(n).monic();
        } while (s.isZERO());
        return new SolvableQuotient(this, r, s, false);
    }

    public SolvableQuotient<C> random(int k, int l, int d, float q) {
        GenSolvablePolynomial<C> r = this.ring.random(k, l, d, q).monic();
        GenSolvablePolynomial<C> s = this.ring.random(k, l, d, q).monic();
        do {
            s = this.ring.random(k, l, d, q).monic();
        } while (s.isZERO());
        return new SolvableQuotient(this, r, s, false);
    }

    public SolvableQuotient<C> random(int n, Random rnd) {
        GenSolvablePolynomial<C> r = this.ring.random(n, rnd).monic();
        GenSolvablePolynomial<C> s = this.ring.random(n, rnd).monic();
        do {
            s = this.ring.random(n, rnd).monic();
        } while (s.isZERO());
        return new SolvableQuotient(this, r, s, false);
    }

    public SolvableQuotient<C> parse(String s) {
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
            return new SolvableQuotient(this, this.ring.parse(s));
        }
        return new SolvableQuotient(this, this.ring.parse(s.substring(0, i)), this.ring.parse(s.substring(i + 1)));
    }

    public SolvableQuotient<C> parse(Reader r) {
        return parse(StringUtil.nextPairedString(r, '{', '}'));
    }
}
