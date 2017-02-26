package edu.jas.integrate;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class QuotIntegral<C extends GcdRingElem<C>> implements Serializable {
    public final List<LogIntegral<C>> logarithm;
    public final Quotient<C> quot;
    public final List<Quotient<C>> rational;

    public QuotIntegral(Integral<C> ri) {
        this(new QuotientRing(ri.den.ring), ri);
    }

    public QuotIntegral(QuotientRing<C> r, Integral<C> ri) {
        this(new Quotient(r, ri.num, ri.den), ri.pol, ri.rational, ri.logarithm);
    }

    public QuotIntegral(Quotient<C> r, GenPolynomial<C> p, List<GenPolynomial<C>> rat) {
        this(r, p, rat, new ArrayList());
    }

    public QuotIntegral(Quotient<C> r, GenPolynomial<C> p, List<GenPolynomial<C>> rat, List<LogIntegral<C>> log) {
        this.quot = r;
        QuotientRing<C> qr = r.ring;
        this.rational = new ArrayList();
        if (!p.isZERO()) {
            this.rational.add(new Quotient(qr, p));
        }
        int i = 0;
        while (i < rat.size()) {
            int i2 = i + 1;
            this.rational.add(new Quotient(qr, (GenPolynomial) rat.get(i), (GenPolynomial) rat.get(i2)));
            i = i2 + 1;
        }
        this.logarithm = log;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("integral( " + this.quot.toString() + " )");
        sb.append(" =\n");
        boolean first = true;
        if (this.rational.size() != 0) {
            for (int i = 0; i < this.rational.size(); i++) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" + ");
                }
                sb.append("(" + this.rational.get(i) + ")");
            }
        }
        if (this.logarithm.size() != 0) {
            if (this.rational.size() != 0) {
                sb.append(" + ");
            }
            first = true;
            for (LogIntegral<C> pf : this.logarithm) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" + ");
                }
                sb.append(pf);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public int hashCode() {
        return (((this.quot.hashCode() * 37) + this.rational.hashCode()) * 37) + this.logarithm.hashCode();
    }

    public boolean equals(Object B) {
        QuotIntegral<C> b = null;
        try {
            b = (QuotIntegral) B;
        } catch (ClassCastException e) {
        }
        if (b != null && this.quot.equals(b.quot) && this.rational.equals(b.rational) && this.logarithm.equals(b.logarithm)) {
            return true;
        }
        return false;
    }
}
