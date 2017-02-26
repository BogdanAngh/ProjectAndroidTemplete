package edu.jas.application;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class ResidueRing<C extends GcdRingElem<C>> implements RingFactory<Residue<C>> {
    private static final Logger logger;
    protected final GreatestCommonDivisor<C> engine;
    public final Ideal<C> ideal;
    protected int isField;
    public final GenPolynomialRing<C> ring;

    static {
        logger = Logger.getLogger(ResidueRing.class);
    }

    public ResidueRing(Ideal<C> i) {
        this(i, false);
    }

    public ResidueRing(Ideal<C> i, boolean isMaximal) {
        this.isField = -1;
        this.ideal = i.GB();
        this.ring = this.ideal.list.ring;
        this.engine = GCDFactory.getProxy(this.ring.coFac);
        if (isMaximal) {
            this.isField = 1;
        } else if (this.ideal.isONE()) {
            logger.warn("ideal is one, so all residues are 0");
        }
    }

    public boolean isFinite() {
        return this.ideal.commonZeroTest() <= 0 && this.ring.coFac.isFinite();
    }

    public Residue<C> copy(Residue<C> c) {
        if (c == null) {
            return getZERO();
        }
        return new Residue(this, c.val);
    }

    public Residue<C> getZERO() {
        return new Residue(this, this.ring.getZERO());
    }

    public Residue<C> getONE() {
        Residue<C> one = new Residue(this, this.ring.getONE());
        if (one.isZERO()) {
            logger.warn("ideal is one, so all residues are 0");
        }
        return one;
    }

    public List<Residue<C>> generators() {
        List<GenPolynomial<C>> pgens = this.ring.generators();
        List<Residue<C>> gens = new ArrayList(pgens.size());
        SortedSet<Residue<C>> sgens = new TreeSet();
        List rgens = new ArrayList(pgens.size());
        ResidueRing gr = new ResidueRing(new Ideal(this.ring, rgens));
        for (GenPolynomial<C> p : pgens) {
            Residue<C> r = new Residue(this, p);
            if (!r.isZERO() && (r.isONE() || !r.val.isConstant())) {
                Residue<C> x = new Residue(gr, r.val);
                if (!x.isZERO() && (x.isONE() || !x.val.isConstant())) {
                    r = new Residue(this, x.val);
                    if (!r.isZERO()) {
                        r = r.monic();
                        if (!(r.isONE() || r.val.isConstant())) {
                            rgens.add(r.val);
                            gr = new ResidueRing(new Ideal(this.ring, rgens));
                        }
                        sgens.add(r);
                    }
                }
            }
        }
        gens.addAll(sgens);
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
        if (this.isField == 0) {
            return false;
        }
        if (!this.ideal.isMaximal()) {
            return false;
        }
        this.isField = 1;
        return true;
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public Residue<C> fromInteger(BigInteger a) {
        return new Residue(this, this.ring.fromInteger(a));
    }

    public Residue<C> fromInteger(long a) {
        return new Residue(this, this.ring.fromInteger(a));
    }

    public String toString() {
        return "ResidueRing[ " + this.ideal.toString() + " ]";
    }

    public String toScript() {
        return "RC(" + this.ideal.list.toScript() + ")";
    }

    public boolean equals(Object b) {
        if (!(b instanceof ResidueRing)) {
            return false;
        }
        ResidueRing<C> a = null;
        try {
            a = (ResidueRing) b;
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

    public Residue<C> random(int n) {
        return new Residue(this, this.ring.random(n).monic());
    }

    public Residue<C> random(int k, int l, int d, float q) {
        return new Residue(this, this.ring.random(k, l, d, q).monic());
    }

    public Residue<C> random(int n, Random rnd) {
        return new Residue(this, this.ring.random(n, rnd).monic());
    }

    public Residue<C> parse(String s) {
        return new Residue(this, this.ring.parse(s));
    }

    public Residue<C> parse(Reader r) {
        return new Residue(this, this.ring.parse(r));
    }
}
