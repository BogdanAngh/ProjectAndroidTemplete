package edu.jas.poly;

import edu.jas.kern.StringUtil;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.log4j.Logger;

public class ComplexRing<C extends RingElem<C>> implements RingFactory<Complex<C>> {
    private static final Logger logger;
    private static final Random random;
    public final RingFactory<C> ring;

    static {
        random = new Random();
        logger = Logger.getLogger(ComplexRing.class);
    }

    public ComplexRing(RingFactory<C> ring) {
        this.ring = ring;
    }

    public List<Complex<C>> generators() {
        List<C> gens = this.ring.generators();
        List<Complex<C>> g = new ArrayList(gens.size() + 1);
        for (C x : gens) {
            g.add(new Complex(this, (RingElem) x));
        }
        g.add(getIMAG());
        return g;
    }

    public AlgebraicNumberRing<C> algebraicRing() {
        GenPolynomialRing<C> pfac = new GenPolynomialRing(this.ring, 1, new TermOrder(2), new String[]{"I"});
        return new AlgebraicNumberRing(pfac.univariate(0, 2).sum(pfac.getONE()), this.ring.isField());
    }

    public boolean isFinite() {
        return this.ring.isFinite();
    }

    public Complex<C> copy(Complex<C> c) {
        return new Complex(this, c.re, c.im);
    }

    public Complex<C> getZERO() {
        return new Complex(this);
    }

    public Complex<C> getONE() {
        return new Complex(this, (RingElem) this.ring.getONE());
    }

    public Complex<C> getIMAG() {
        return new Complex(this, (RingElem) this.ring.getZERO(), (RingElem) this.ring.getONE());
    }

    public boolean isCommutative() {
        return this.ring.isCommutative();
    }

    public boolean isAssociative() {
        return this.ring.isAssociative();
    }

    public boolean isField() {
        return this.ring.isField();
    }

    public BigInteger characteristic() {
        return this.ring.characteristic();
    }

    public Complex<C> fromInteger(BigInteger a) {
        return new Complex(this, (RingElem) this.ring.fromInteger(a));
    }

    public Complex<C> fromInteger(long a) {
        return new Complex(this, (RingElem) this.ring.fromInteger(a));
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("Complex[");
        if (this.ring instanceof RingElem) {
            sb.append(this.ring.toScriptFactory());
        } else {
            sb.append(this.ring.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        s.append("CR(");
        if (this.ring instanceof RingElem) {
            s.append(this.ring.toScriptFactory());
        } else {
            s.append(this.ring.toScript());
        }
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object b) {
        if (b == null || !(b instanceof ComplexRing)) {
            return false;
        }
        if (this.ring.equals(((ComplexRing) b).ring)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return this.ring.hashCode();
    }

    public Complex<C> random(int n) {
        return random(n, random);
    }

    public Complex<C> random(int n, Random rnd) {
        return new Complex(this, (RingElem) this.ring.random(n, rnd), (RingElem) this.ring.random(n, rnd));
    }

    public Complex<C> parse(String s) {
        return new Complex(this, s);
    }

    public Complex<C> parse(Reader r) {
        return parse(StringUtil.nextString(r));
    }
}
