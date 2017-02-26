package edu.jas.application;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.root.Interval;
import edu.jas.root.PolyUtilRoot;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.root.RealRootTuple;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class RealAlgebraicRing<C extends GcdRingElem<C> & Rational> implements RingFactory<RealAlgebraicNumber<C>> {
    private static final Logger logger;
    public final int PRECISION;
    final ResidueRing<C> algebraic;
    protected C eps;
    public final edu.jas.root.RealAlgebraicRing<RealAlgebraicNumber<C>> realRing;
    RealRootTuple<C> root;
    final IdealWithUniv<C> univs;

    static {
        logger = Logger.getLogger(RealAlgebraicRing.class);
    }

    public RealAlgebraicRing(IdealWithUniv<C> m, ResidueRing<C> a, RealRootTuple<C> r) {
        this.PRECISION = 9;
        this.univs = m;
        this.algebraic = a;
        this.root = r;
        if (this.algebraic.characteristic().signum() > 0) {
            throw new IllegalArgumentException("characteristic not zero");
        }
        this.eps = (GcdRingElem) Power.positivePower((GcdRingElem) ((GcdRingElem) m.ideal.list.ring.coFac.fromInteger(10)).inverse(), 9);
        RingFactory rfac1 = ((RealAlgebraicNumber) this.root.tuple.get(0)).factory();
        edu.jas.root.RealAlgebraicRing<C> rfac2 = ((RealAlgebraicNumber) this.root.tuple.get(1)).factory();
        GenPolynomial p0 = PolyUtil.selectWithVariable(this.univs.ideal.list.list, 0);
        if (p0 == null) {
            throw new RuntimeException("no polynomial found in 0 of  " + this.univs.ideal);
        }
        GenPolynomialRing prfac = p0.ring.recursive(1);
        GenPolynomial<RealAlgebraicNumber<C>> p0ar = PolyUtilRoot.convertRecursiveToAlgebraicCoefficients(new GenPolynomialRing(rfac1, prfac), PolyUtil.recursive(prfac, p0));
        Interval<C> r2 = rfac2.getRoot();
        edu.jas.root.RealAlgebraicRing<RealAlgebraicNumber<C>> realAlgebraicRing = new edu.jas.root.RealAlgebraicRing(p0ar, new Interval(rfac1.getZERO().sum((GcdRingElem) r2.left), rfac1.getZERO().sum((GcdRingElem) r2.right)));
        logger.info("realRing = " + realAlgebraicRing);
        this.realRing = realAlgebraicRing;
    }

    public RealAlgebraicRing(IdealWithUniv<C> m, RealRootTuple<C> root) {
        this((IdealWithUniv) m, new ResidueRing(m.ideal), (RealRootTuple) root);
    }

    public RealAlgebraicRing(IdealWithUniv<C> m, RealRootTuple<C> root, boolean isField) {
        this((IdealWithUniv) m, new ResidueRing(m.ideal, isField), (RealRootTuple) root);
    }

    public synchronized void setRoot(RealRootTuple<C> v) {
        this.root = v;
    }

    public synchronized RealRootTuple<C> getRoot() {
        return this.root;
    }

    public synchronized C getEps() {
        return this.eps;
    }

    public synchronized void setEps(C e) {
        this.eps = e;
    }

    public synchronized void setEps(BigRational e) {
        this.eps = (GcdRingElem) this.realRing.algebraic.ring.coFac.algebraic.ring.coFac.parse(e.toString());
    }

    public boolean isFinite() {
        return this.realRing.isFinite();
    }

    public RealAlgebraicNumber<C> copy(RealAlgebraicNumber<C> c) {
        return new RealAlgebraicNumber(this, c.number);
    }

    public RealAlgebraicNumber<C> getZERO() {
        return new RealAlgebraicNumber(this, this.realRing.getZERO());
    }

    public RealAlgebraicNumber<C> getONE() {
        return new RealAlgebraicNumber(this, this.realRing.getONE());
    }

    public List<RealAlgebraicNumber<C>> generators() {
        List<RealAlgebraicNumber<RealAlgebraicNumber<C>>> agens = this.realRing.generators();
        List<RealAlgebraicNumber<C>> gens = new ArrayList(agens.size());
        for (RealAlgebraicNumber a : agens) {
            gens.add(getZERO().sum(a));
        }
        return gens;
    }

    public boolean isCommutative() {
        return this.realRing.isCommutative();
    }

    public boolean isAssociative() {
        return this.realRing.isAssociative();
    }

    public boolean isField() {
        return this.realRing.isField();
    }

    public void setField(boolean isField) {
        this.realRing.setField(isField);
    }

    public BigInteger characteristic() {
        return this.realRing.characteristic();
    }

    public RealAlgebraicNumber<C> fromInteger(BigInteger a) {
        return new RealAlgebraicNumber(this, this.realRing.fromInteger(a));
    }

    public RealAlgebraicNumber<C> fromInteger(long a) {
        return new RealAlgebraicNumber(this, this.realRing.fromInteger(a));
    }

    public String toString() {
        return "RealAlgebraicRing[ " + this.realRing.toString() + " in " + this.root + " | isField=" + this.realRing.isField() + ", algebraic.ideal=" + this.algebraic.ideal.toString() + " ]";
    }

    public String toScript() {
        return "RealRecN( " + this.realRing.toScript() + ", " + this.root.toScript() + " )";
    }

    public boolean equals(Object b) {
        if (!(b instanceof RealAlgebraicRing)) {
            return false;
        }
        RealAlgebraicRing<C> a = null;
        try {
            a = (RealAlgebraicRing) b;
        } catch (ClassCastException e) {
        }
        if (a != null && this.realRing.equals(a.realRing) && this.root.equals(a.root)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.realRing.hashCode() * 37) + this.root.hashCode();
    }

    public RealAlgebraicNumber<C> random(int n) {
        return new RealAlgebraicNumber(this, this.realRing.random(n));
    }

    public RealAlgebraicNumber<C> random(int n, Random rnd) {
        return new RealAlgebraicNumber(this, this.realRing.random(n, rnd));
    }

    public RealAlgebraicNumber<C> parse(String s) {
        return new RealAlgebraicNumber(this, this.realRing.parse(s));
    }

    public RealAlgebraicNumber<C> parse(Reader r) {
        return new RealAlgebraicNumber(this, this.realRing.parse(r));
    }
}
