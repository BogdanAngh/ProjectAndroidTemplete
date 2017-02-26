package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Serializable;

public class Rectangle<C extends RingElem<C> & Rational> implements Serializable {
    public final Complex<C>[] corners;

    Rectangle(Complex<C>[] c) {
        if (c.length < 5) {
            this.corners = new Complex[5];
            for (int i = 0; i < 4; i++) {
                this.corners[i] = c[i];
            }
        } else {
            this.corners = c;
        }
        if (this.corners[4] == null) {
            this.corners[4] = this.corners[0];
        }
    }

    public Rectangle(Complex<C> mid) {
        this(mid, mid);
    }

    public Rectangle(Complex<C> sw, Complex<C> ne) {
        this(new Complex(sw.ring, sw.getRe(), ne.getIm()), sw, new Complex(sw.ring, ne.getRe(), sw.getIm()), ne);
    }

    public Rectangle(Complex<C> nw, Complex<C> sw, Complex<C> se, Complex<C> ne) {
        this(new Complex[]{nw, sw, se, ne});
    }

    public String toString() {
        return "[" + this.corners[1] + ", " + this.corners[3] + "]";
    }

    public String toScript() {
        return "(" + this.corners[1].toScript() + ", " + this.corners[3].toScript() + ")";
    }

    public Complex<C> getNW() {
        return this.corners[0];
    }

    public Complex<C> getSW() {
        return this.corners[1];
    }

    public Complex<C> getSE() {
        return this.corners[2];
    }

    public Complex<C> getNE() {
        return this.corners[3];
    }

    public Rectangle<C> exchangeNW(Complex<C> c) {
        Complex<C> d = getSE();
        return new Rectangle(c, new Complex(c.factory(), c.getRe(), d.getIm()), d, new Complex(c.factory(), d.getRe(), c.getIm()));
    }

    public Rectangle<C> exchangeSW(Complex<C> c) {
        Complex<C> d = getNE();
        return new Rectangle(new Complex(c.factory(), c.getRe(), d.getIm()), c, new Complex(c.factory(), d.getRe(), c.getIm()), d);
    }

    public Rectangle<C> exchangeSE(Complex<C> c) {
        Complex<C> d = getNW();
        return new Rectangle(d, new Complex(c.factory(), d.getRe(), c.getIm()), c, new Complex(c.factory(), c.getRe(), d.getIm()));
    }

    public Rectangle<C> exchangeNE(Complex<C> c) {
        Complex<C> d = getSW();
        return new Rectangle(new Complex(c.factory(), d.getRe(), c.getIm()), d, new Complex(c.factory(), c.getRe(), d.getIm()), c);
    }

    public boolean contains(Complex<C> c) {
        Complex<C> ll = getSW();
        Complex<C> ur = getNE();
        C cre = c.getRe();
        C cim = c.getIm();
        return cre.compareTo(ll.getRe()) >= 0 && cim.compareTo(ll.getIm()) >= 0 && cre.compareTo(ur.getRe()) <= 0 && cim.compareTo(ur.getIm()) <= 0;
    }

    public boolean contains(Rectangle<C> r) {
        return contains(r.getSW()) && contains(r.getNE());
    }

    public Complex<C> randomPoint() {
        Complex<C> sw = getSW();
        Complex<C> se = getSE();
        Complex<C> nw = getNW();
        Complex<C> r = sw.factory().random(13);
        RingElem dr = (RingElem) se.getRe().subtract(sw.getRe());
        RingElem di = (RingElem) nw.getIm().subtract(sw.getIm());
        C rr = (RingElem) r.getRe().abs();
        C ri = (RingElem) r.getIm().abs();
        RingElem one = (RingElem) ((RingFactory) dr.factory()).getONE();
        if (!rr.isZERO() && rr.compareTo(one) > 0) {
            rr = (RingElem) rr.inverse();
        }
        if (!ri.isZERO() && ri.compareTo(one) > 0) {
            ri = (RingElem) ri.inverse();
        }
        return sw.sum(new Complex(sw.factory(), (RingElem) rr.multiply(dr), (RingElem) ri.multiply(di)));
    }

    public Rectangle<C> copy() {
        return new Rectangle(this.corners);
    }

    public boolean equals(Object b) {
        if (!(b instanceof Rectangle)) {
            return false;
        }
        Rectangle<C> a = null;
        try {
            a = (Rectangle) b;
        } catch (ClassCastException e) {
        }
        for (int i = 0; i < 4; i++) {
            if (!this.corners[i].equals(a.corners[i])) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int hc = 0;
        for (int i = 0; i < 3; i++) {
            hc += this.corners[i].hashCode() * 37;
        }
        return (hc * 37) + this.corners[3].hashCode();
    }

    public Complex<C> getCenter() {
        RingElem r = (RingElem) this.corners[2].getRe().subtract(this.corners[1].getRe());
        RingElem two = (RingElem) r.factory().fromInteger(2);
        return new Complex(this.corners[0].factory(), (RingElem) this.corners[1].getRe().sum((RingElem) r.divide(two)), (RingElem) this.corners[1].getIm().sum((RingElem) ((RingElem) this.corners[0].getIm().subtract(this.corners[1].getIm())).divide(two)));
    }

    public Complex<BigRational> getRationalCenter() {
        Complex<C> cm = getCenter();
        BigRational rs = ((Rational) cm.getRe()).getRational();
        return new Complex(new ComplexRing(rs.factory()), rs, ((Rational) cm.getIm()).getRational());
    }

    public Complex<BigDecimal> getDecimalCenter() {
        Complex<BigRational> rc = getRationalCenter();
        BigDecimal rd = new BigDecimal((BigRational) rc.getRe());
        return new Complex(new ComplexRing(rd.factory()), rd, new BigDecimal((BigRational) rc.getIm()));
    }

    public String centerApprox() {
        Complex<BigDecimal> c = getDecimalCenter();
        StringBuffer s = new StringBuffer();
        s.append("[ ");
        s.append(((BigDecimal) c.getRe()).toString());
        s.append(" i ");
        s.append(((BigDecimal) c.getIm()).toString());
        s.append(" ]");
        return s.toString();
    }

    public C length() {
        return this.corners[3].subtract(this.corners[1]).norm().getRe();
    }

    public BigRational rationalLength() {
        return ((Rational) length()).getRational();
    }

    public C lengthReal() {
        return (RingElem) ((RingElem) this.corners[3].getRe().subtract(this.corners[1].getRe())).abs();
    }

    public C lengthImag() {
        return (RingElem) ((RingElem) this.corners[3].getIm().subtract(this.corners[1].getIm())).abs();
    }
}
