package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RealRootTuple<C extends GcdRingElem<C> & Rational> implements Serializable {
    public final List<RealAlgebraicNumber<C>> tuple;

    public RealRootTuple(List<RealAlgebraicNumber<C>> t) {
        if (t == null) {
            throw new IllegalArgumentException("null tuple not allowed");
        }
        this.tuple = t;
    }

    public String toString() {
        return this.tuple.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer("[");
        boolean first = true;
        for (RealAlgebraicNumber<C> r : this.tuple) {
            if (first) {
                first = false;
            } else {
                sb.append(",");
            }
            sb.append(r.toScript());
        }
        return sb.toString();
    }

    public RealRootTuple<C> copy() {
        return new RealRootTuple(new ArrayList(this.tuple));
    }

    public boolean equals(Object b) {
        RealRootTuple<C> a = null;
        try {
            a = (RealRootTuple) b;
        } catch (ClassCastException e) {
        }
        if (a == null) {
            return false;
        }
        return this.tuple.equals(a.tuple);
    }

    public int hashCode() {
        return this.tuple.hashCode();
    }

    public List<BigRational> getRational() {
        List<BigRational> center = new ArrayList(this.tuple.size());
        for (RealAlgebraicNumber<C> rr : this.tuple) {
            center.add(rr.getRational());
        }
        return center;
    }

    public List<BigDecimal> decimalMagnitude() {
        List<BigDecimal> center = new ArrayList(this.tuple.size());
        for (RealAlgebraicNumber<C> rr : this.tuple) {
            center.add(rr.decimalMagnitude());
        }
        return center;
    }

    public BigRational rationalLength() {
        BigRational len = new BigRational();
        for (RealAlgebraicNumber<C> rr : this.tuple) {
            BigRational r = rr.ring.root.rationalLength();
            if (len.compareTo(r) < 0) {
                len = r;
            }
        }
        return len;
    }

    public int signum() {
        int s = 0;
        for (RealAlgebraicNumber<C> rr : this.tuple) {
            int rs = rr.signum();
            if (rs != 0) {
                s = rs;
            }
        }
        return s;
    }
}
