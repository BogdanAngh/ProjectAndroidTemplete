package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Serializable;

public class Interval<C extends RingElem<C> & Rational> implements Serializable {
    public final C left;
    public final C right;

    public Interval(C left, C right) {
        this.left = left;
        this.right = right;
    }

    public Interval(C mid) {
        this(mid, mid);
    }

    public String toString() {
        return "[" + this.left + ", " + this.right + "]";
    }

    public String toScript() {
        return "[ " + this.left.toScript() + ", " + this.right.toScript() + " ]";
    }

    public Interval<C> copy() {
        return new Interval(this.left, this.right);
    }

    public boolean equals(Object b) {
        if (!(b instanceof Interval)) {
            return false;
        }
        try {
            Interval<C> a = (Interval) b;
            if (this.left.equals(a.left) && this.right.equals(a.right)) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (this.left.hashCode() * 37) + this.right.hashCode();
    }

    public boolean contains(C c) {
        return this.left.compareTo(c) <= 0 && c.compareTo(this.right) <= 0;
    }

    public boolean contains(Interval<C> vc) {
        return contains(vc.left) && contains(vc.right);
    }

    public C length() {
        return (RingElem) ((RingElem) this.right.subtract(this.left)).abs();
    }

    public BigRational rationalLength() {
        return ((Rational) length()).getRational();
    }

    public BigDecimal toDecimal() {
        BigDecimal l = new BigDecimal(((Rational) this.left).getRational());
        BigDecimal r = new BigDecimal(((Rational) this.right).getRational());
        return l.sum(r).divide(new BigDecimal(2));
    }

    public BigRational rationalMiddle() {
        return ((Rational) this.left).getRational().sum(((Rational) this.right).getRational()).multiply(new BigRational(1, 2));
    }

    public C middle() {
        return (RingElem) ((RingElem) this.left.sum(this.right)).multiply((RingElem) this.left.factory().parse("1/2"));
    }

    public C randomPoint() {
        RingElem dr = (RingElem) this.right.subtract(this.left);
        RingFactory<C> fac = (RingFactory) dr.factory();
        C r = (RingElem) ((RingElem) fac.random(13)).abs();
        if (!r.isZERO() && r.compareTo(fac.getONE()) > 0) {
            r = (RingElem) r.inverse();
        }
        return (RingElem) this.left.sum((RingElem) dr.multiply(r));
    }
}
