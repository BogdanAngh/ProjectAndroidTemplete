package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ComplexAlgebraicRing<C extends GcdRingElem<C> & Rational> implements RingFactory<ComplexAlgebraicNumber<C>> {
    public static final int PRECISION = 9;
    public final AlgebraicNumberRing<Complex<C>> algebraic;
    public final ComplexRootsSturm<C> engine;
    protected C eps;
    Rectangle<C> root;

    public ComplexAlgebraicRing(GenPolynomial<Complex<C>> m, Rectangle<C> root) {
        this.algebraic = new AlgebraicNumberRing(m);
        this.root = root;
        this.engine = new ComplexRootsSturm(m.ring.coFac);
        if (m.ring.characteristic().signum() > 0) {
            throw new IllegalArgumentException("characteristic not zero");
        }
        this.eps = (GcdRingElem) Power.positivePower((GcdRingElem) ((GcdRingElem) ((Complex) m.ring.coFac.fromInteger(10)).getRe()).inverse(), 9);
    }

    public ComplexAlgebraicRing(GenPolynomial<Complex<C>> m, Rectangle<C> root, boolean isField) {
        this.algebraic = new AlgebraicNumberRing(m, isField);
        this.root = root;
        this.engine = new ComplexRootsSturm(m.ring.coFac);
        if (m.ring.characteristic().signum() > 0) {
            throw new IllegalArgumentException("characteristic not zero");
        }
        this.eps = (GcdRingElem) Power.positivePower((GcdRingElem) ((GcdRingElem) ((Complex) m.ring.coFac.fromInteger(10)).getRe()).inverse(), 9);
    }

    public synchronized void setRoot(Rectangle<C> v) {
        this.root = v;
    }

    public synchronized Rectangle<C> getRoot() {
        return this.root;
    }

    public synchronized C getEps() {
        return this.eps;
    }

    public synchronized void setEps(C e) {
        this.eps = e;
    }

    public synchronized void setEps(BigRational e) {
        this.eps = (GcdRingElem) ((Complex) this.algebraic.ring.coFac.parse(e.toString())).getRe();
    }

    public boolean isFinite() {
        return this.algebraic.isFinite();
    }

    public ComplexAlgebraicNumber<C> copy(ComplexAlgebraicNumber<C> c) {
        return new ComplexAlgebraicNumber(this, c.number);
    }

    public ComplexAlgebraicNumber<C> getZERO() {
        return new ComplexAlgebraicNumber(this, this.algebraic.getZERO());
    }

    public ComplexAlgebraicNumber<C> getONE() {
        return new ComplexAlgebraicNumber(this, this.algebraic.getONE());
    }

    public ComplexAlgebraicNumber<C> getIMAG() {
        return new ComplexAlgebraicNumber(this, this.algebraic.getZERO().sum(this.algebraic.ring.coFac.getIMAG()));
    }

    public ComplexAlgebraicNumber<C> getGenerator() {
        return new ComplexAlgebraicNumber(this, this.algebraic.getGenerator());
    }

    public List<ComplexAlgebraicNumber<C>> generators() {
        List<AlgebraicNumber<Complex<C>>> agens = this.algebraic.generators();
        List<ComplexAlgebraicNumber<C>> gens = new ArrayList(agens.size());
        for (AlgebraicNumber<Complex<C>> a : agens) {
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

    public BigInteger characteristic() {
        return this.algebraic.characteristic();
    }

    public ComplexAlgebraicNumber<C> fromInteger(BigInteger a) {
        return new ComplexAlgebraicNumber(this, this.algebraic.fromInteger(a));
    }

    public ComplexAlgebraicNumber<C> fromInteger(long a) {
        return new ComplexAlgebraicNumber(this, this.algebraic.fromInteger(a));
    }

    public String toString() {
        return "ComplexAlgebraicRing[ " + this.algebraic.modul.toString() + " in " + this.root + " | isField=" + this.algebraic.isField() + " :: " + this.algebraic.ring.toString() + " ]";
    }

    public String toScript() {
        return "ComplexN( " + this.algebraic.modul.toScript() + ", " + this.root.toScript() + " )";
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof ComplexAlgebraicRing)) {
            return false;
        }
        ComplexAlgebraicRing<C> a = (ComplexAlgebraicRing) b;
        if (this.algebraic.equals(a.algebraic) && this.root.equals(a.root)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.algebraic.hashCode() * 37) + this.root.hashCode();
    }

    public ComplexAlgebraicNumber<C> random(int n) {
        return new ComplexAlgebraicNumber(this, this.algebraic.random(n));
    }

    public ComplexAlgebraicNumber<C> random(int n, Random rnd) {
        return new ComplexAlgebraicNumber(this, this.algebraic.random(n, rnd));
    }

    public ComplexAlgebraicNumber<C> parse(String s) {
        return new ComplexAlgebraicNumber(this, this.algebraic.parse(s));
    }

    public ComplexAlgebraicNumber<C> parse(Reader r) {
        return new ComplexAlgebraicNumber(this, this.algebraic.parse(r));
    }
}
