package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RealAlgebraicRing<C extends GcdRingElem<C> & Rational> implements RingFactory<RealAlgebraicNumber<C>> {
    public static final int PRECISION = 9;
    public final AlgebraicNumberRing<C> algebraic;
    public final RealRootsSturm<C> engine;
    protected C eps;
    Interval<C> root;

    public RealAlgebraicRing(GenPolynomial<C> m, Interval<C> root) {
        this.algebraic = new AlgebraicNumberRing(m);
        this.root = root;
        this.engine = new RealRootsSturm();
        if (m.ring.characteristic().signum() > 0) {
            throw new RuntimeException("characteristic not zero");
        }
        this.eps = (GcdRingElem) Power.positivePower((GcdRingElem) ((GcdRingElem) m.ring.coFac.fromInteger(10)).inverse(), 9);
    }

    public RealAlgebraicRing(GenPolynomial<C> m, Interval<C> root, boolean isField) {
        this.algebraic = new AlgebraicNumberRing(m, isField);
        this.root = root;
        this.engine = new RealRootsSturm();
        if (m.ring.characteristic().signum() > 0) {
            throw new RuntimeException("characteristic not zero");
        }
        this.eps = (GcdRingElem) Power.positivePower((GcdRingElem) ((GcdRingElem) m.ring.coFac.fromInteger(10)).inverse(), 9);
    }

    public synchronized Interval<C> getRoot() {
        return this.root;
    }

    public synchronized void setRoot(Interval<C> v) {
        this.root = v;
    }

    public synchronized C getEps() {
        return this.eps;
    }

    public synchronized void setEps(C e) {
        this.eps = e;
    }

    public synchronized void setEps(BigRational e) {
        this.eps = (GcdRingElem) this.algebraic.ring.coFac.parse(e.toString());
    }

    public void halfInterval() {
        setRoot(this.engine.halfInterval(this.root, this.algebraic.modul));
    }

    public boolean isFinite() {
        return this.algebraic.isFinite();
    }

    public RealAlgebraicNumber<C> copy(RealAlgebraicNumber<C> c) {
        return new RealAlgebraicNumber(this, c.number);
    }

    public RealAlgebraicNumber<C> getZERO() {
        return new RealAlgebraicNumber(this, this.algebraic.getZERO());
    }

    public RealAlgebraicNumber<C> getONE() {
        return new RealAlgebraicNumber(this, this.algebraic.getONE());
    }

    public RealAlgebraicNumber<C> getGenerator() {
        return new RealAlgebraicNumber(this, this.algebraic.getGenerator());
    }

    public List<RealAlgebraicNumber<C>> generators() {
        List<AlgebraicNumber<C>> agens = this.algebraic.generators();
        List<RealAlgebraicNumber<C>> gens = new ArrayList(agens.size());
        for (AlgebraicNumber<C> a : agens) {
            gens.add(getZERO().sum(a.getVal()));
        }
        return gens;
    }

    public boolean isCommutative() {
        return this.algebraic.isCommutative();
    }

    public boolean isAssociative() {
        return this.algebraic.isAssociative();
    }

    public boolean isField() {
        return this.algebraic.isField();
    }

    public void setField(boolean isField) {
        this.algebraic.setField(isField);
    }

    public BigInteger characteristic() {
        return this.algebraic.characteristic();
    }

    public RealAlgebraicNumber<C> fromInteger(BigInteger a) {
        return new RealAlgebraicNumber(this, this.algebraic.fromInteger(a));
    }

    public RealAlgebraicNumber<C> fromInteger(long a) {
        return new RealAlgebraicNumber(this, this.algebraic.fromInteger(a));
    }

    public String toString() {
        return "RealAlgebraicRing[ " + this.algebraic.modul.toString() + " in " + this.root + " | isField=" + this.algebraic.isField() + " :: " + this.algebraic.ring.toString() + " ]";
    }

    public String toScript() {
        return "RealN( " + this.algebraic.modul.toScript() + ", " + this.root.toScript() + " )";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof RealAlgebraicRing)) {
            return false;
        }
        RealAlgebraicRing<C> a = (RealAlgebraicRing) b;
        if (this.algebraic.equals(a.algebraic) && this.root.equals(a.root)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.algebraic.hashCode() * 37) + this.root.hashCode();
    }

    public RealAlgebraicNumber<C> random(int n) {
        return new RealAlgebraicNumber(this, this.algebraic.random(n));
    }

    public RealAlgebraicNumber<C> random(int n, Random rnd) {
        return new RealAlgebraicNumber(this, this.algebraic.random(n, rnd));
    }

    public RealAlgebraicNumber<C> parse(String s) {
        return new RealAlgebraicNumber(this, this.algebraic.parse(s));
    }

    public RealAlgebraicNumber<C> parse(Reader r) {
        return new RealAlgebraicNumber(this, this.algebraic.parse(r));
    }
}
