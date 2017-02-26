package edu.jas.application;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class ColorPolynomial<C extends RingElem<C>> implements Serializable {
    private static final Logger logger;
    public final GenPolynomial<GenPolynomial<C>> green;
    public final GenPolynomial<GenPolynomial<C>> red;
    public final GenPolynomial<GenPolynomial<C>> white;

    static {
        logger = Logger.getLogger(ColorPolynomial.class);
    }

    public ColorPolynomial(GenPolynomial<GenPolynomial<C>> g, GenPolynomial<GenPolynomial<C>> r, GenPolynomial<GenPolynomial<C>> w) {
        if (g == null || r == null || w == null) {
            throw new IllegalArgumentException("g,r,w may not be null");
        }
        this.green = g;
        this.red = r;
        this.white = w;
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append(":green: ");
        s.append(this.green.toString());
        s.append(" :red: ");
        s.append(this.red.toString());
        s.append(" :white: ");
        s.append(this.white.toString());
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        s.append(":green: ");
        s.append(this.green.toScript());
        s.append(" :red: ");
        s.append(this.red.toScript());
        s.append(" :white: ");
        s.append(this.white.toScript());
        return s.toString();
    }

    public boolean isZERO() {
        return this.red.isZERO() && this.white.isZERO();
    }

    public boolean isONE() {
        return (this.red.isZERO() && this.white.isONE()) || (this.red.isONE() && this.white.isZERO());
    }

    public boolean equals(Object p) {
        try {
            ColorPolynomial<C> cp = (ColorPolynomial) p;
            if (cp != null && this.green.equals(cp.green) && this.red.equals(cp.red) && this.white.equals(cp.white)) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (((this.green.hashCode() << 11) + this.red.hashCode()) << 11) + this.white.hashCode();
    }

    public boolean isDetermined() {
        return !this.red.isZERO() || this.white.isZERO();
    }

    public boolean checkInvariant() {
        boolean t = true;
        if (this.green.isZERO() && this.red.isZERO() && this.white.isZERO()) {
            return true;
        }
        if (this.green.isZERO() && this.red.isZERO()) {
            return true;
        }
        if (this.red.isZERO() && this.white.isZERO()) {
            return true;
        }
        if (!(this.green.isZERO() || this.red.isZERO())) {
            t = 1 != null && this.green.ring.tord.getDescendComparator().compare(this.green.trailingExpVector(), this.red.leadingExpVector()) < 0;
        }
        if (!(this.red.isZERO() || this.white.isZERO())) {
            ExpVector ttr = this.red.trailingExpVector();
            ExpVector ltw = this.white.leadingExpVector();
            Comparator<ExpVector> cmp = this.white.ring.tord.getDescendComparator();
            if (!t || cmp.compare(ttr, ltw) >= 0) {
                t = false;
            } else {
                t = true;
            }
        }
        if (!(!this.red.isZERO() || this.green.isZERO() || this.white.isZERO())) {
            ExpVector ttg = this.green.trailingExpVector();
            ltw = this.white.leadingExpVector();
            cmp = this.white.ring.tord.getDescendComparator();
            if (!t || cmp.compare(ttg, ltw) >= 0) {
                t = false;
            } else {
                t = true;
            }
        }
        if (!t) {
            System.out.println("not invariant " + this);
        }
        return t;
    }

    public List<GenPolynomial<C>> getGreenCoefficients() {
        return new ArrayList(this.green.getMap().values());
    }

    public List<GenPolynomial<C>> getRedCoefficients() {
        return new ArrayList(this.red.getMap().values());
    }

    public GenPolynomial<GenPolynomial<C>> getPolynomial() {
        GenPolynomial<GenPolynomial<C>> f = this.green.sum(this.red).sum(this.white);
        int s = (this.green.length() + this.red.length()) + this.white.length();
        int t = f.length();
        if (t == s) {
            return f;
        }
        throw new RuntimeException("illegal coloring state " + s + " != " + t);
    }

    public GenPolynomial<GenPolynomial<C>> getEssentialPolynomial() {
        GenPolynomial<GenPolynomial<C>> f = this.red.sum(this.white);
        int s = this.red.length() + this.white.length();
        int t = f.length();
        if (t != s) {
            logger.warn("illegal coloring state " + s + " != " + t);
            logger.info("f = " + f + ", red = " + this.red + ", white = " + this.white);
        }
        return f;
    }

    public int length() {
        return this.red.length() + this.white.length();
    }

    public ExpVector leadingExpVector() {
        if (this.red.isZERO()) {
            return this.white.leadingExpVector();
        }
        return this.red.leadingExpVector();
    }

    public Entry<ExpVector, GenPolynomial<C>> leadingMonomial() {
        if (this.red.isZERO()) {
            return this.white.leadingMonomial();
        }
        return this.red.leadingMonomial();
    }

    public ColorPolynomial<C> abs() {
        int s = this.green.signum();
        if (s > 0) {
            return this;
        }
        if (s < 0) {
            this(this.green.negate(), this.red.negate(), this.white.negate());
            return this;
        }
        GenPolynomial<GenPolynomial<C>> g = this.green;
        s = this.red.signum();
        if (s > 0) {
            return this;
        }
        if (s < 0) {
            this(g, this.red.negate(), this.white.negate());
            return this;
        }
        GenPolynomial<GenPolynomial<C>> r = this.red;
        s = this.white.signum();
        if (s > 0) {
            return this;
        }
        if (s < 0) {
            this(g, r, this.white.negate());
            return this;
        }
        this(g, r, this.white);
        return this;
    }

    public ColorPolynomial<C> sum(ColorPolynomial<C> S) {
        return new ColorPolynomial(this.green.sum(S.green), this.red.ring.getZERO(), getEssentialPolynomial().sum(S.getEssentialPolynomial()));
    }

    public ColorPolynomial<C> sum(GenPolynomial<C> s, ExpVector e) {
        GenPolynomial<GenPolynomial<C>> g = this.green;
        GenPolynomial<GenPolynomial<C>> r = this.red;
        GenPolynomial<GenPolynomial<C>> w = this.white;
        if (this.green.getMap().keySet().contains(e)) {
            g = this.green.sum(s, e);
        } else if (this.red.getMap().keySet().contains(e)) {
            r = this.red.sum(s, e);
        } else {
            w = this.white.sum(s, e);
        }
        return new ColorPolynomial(g, r, w);
    }

    public ColorPolynomial<C> subtract(ColorPolynomial<C> S) {
        return new ColorPolynomial(this.green.subtract(S.green), this.red.ring.getZERO(), getEssentialPolynomial().subtract(S.getEssentialPolynomial()));
    }

    public ColorPolynomial<C> subtract(GenPolynomial<C> s, ExpVector e) {
        GenPolynomial<GenPolynomial<C>> g = this.green;
        GenPolynomial<GenPolynomial<C>> r = this.red;
        GenPolynomial<GenPolynomial<C>> w = this.white;
        if (this.green.getMap().keySet().contains(e)) {
            g = this.green.subtract(s, e);
        } else if (this.red.getMap().keySet().contains(e)) {
            r = this.red.subtract(s, e);
        } else {
            w = this.white.subtract(s, e);
        }
        return new ColorPolynomial(g, r, w);
    }

    public ColorPolynomial<C> multiply(GenPolynomial<C> s, ExpVector e) {
        GenPolynomial<GenPolynomial<C>> g;
        GenPolynomial<GenPolynomial<C>> r;
        GenPolynomial<GenPolynomial<C>> w;
        if (this.green instanceof GenSolvablePolynomial) {
            logger.info("use left multiplication");
            GenSolvablePolynomial<GenPolynomial<C>> rs = this.red;
            GenSolvablePolynomial<GenPolynomial<C>> ws = this.white;
            g = this.green.multiplyLeft(s, e);
            r = rs.multiplyLeft(s, e);
            w = ws.multiplyLeft(s, e);
        } else {
            g = this.green.multiply(s, e);
            r = this.red.multiply(s, e);
            w = this.white.multiply(s, e);
        }
        return new ColorPolynomial(g, r, w);
    }

    public ColorPolynomial<C> multiply(GenPolynomial<C> s) {
        return new ColorPolynomial(this.green.multiply((RingElem) s), this.red.multiply((RingElem) s), this.white.multiply((RingElem) s));
    }

    public ColorPolynomial<C> divide(GenPolynomial<C> s) {
        return new ColorPolynomial(this.green.divide((RingElem) s), this.red.divide((RingElem) s), this.white.divide((RingElem) s));
    }
}
