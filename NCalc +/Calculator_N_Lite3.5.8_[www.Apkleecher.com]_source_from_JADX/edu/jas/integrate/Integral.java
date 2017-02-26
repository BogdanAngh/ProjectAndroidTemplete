package edu.jas.integrate;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Integral<C extends GcdRingElem<C>> implements Serializable {
    public final GenPolynomial<C> den;
    public final List<LogIntegral<C>> logarithm;
    public final GenPolynomial<C> num;
    public final GenPolynomial<C> pol;
    public final List<GenPolynomial<C>> rational;

    public Integral(GenPolynomial<C> n, GenPolynomial<C> d, GenPolynomial<C> p) {
        this(n, d, p, new ArrayList());
    }

    public Integral(GenPolynomial<C> n, GenPolynomial<C> d, GenPolynomial<C> p, List<GenPolynomial<C>> rat) {
        this(n, d, p, rat, new ArrayList());
    }

    public Integral(GenPolynomial<C> n, GenPolynomial<C> d, GenPolynomial<C> p, List<GenPolynomial<C>> rat, List<LogIntegral<C>> log) {
        this.num = n;
        this.den = d;
        this.pol = p;
        this.rational = rat;
        this.logarithm = log;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("integral( (" + this.num.toString());
        sb.append(") / (");
        sb.append(this.den.toString() + ") )");
        sb.append(" =\n");
        if (!this.pol.isZERO()) {
            sb.append(this.pol.toString());
        }
        boolean first = true;
        if (this.rational.size() != 0) {
            if (!this.pol.isZERO()) {
                sb.append(" + ");
            }
            int i = 0;
            while (i < this.rational.size()) {
                if (first) {
                    first = false;
                } else {
                    sb.append(" + ");
                }
                int i2 = i + 1;
                sb.append("(" + this.rational.get(i) + ")/(");
                sb.append(this.rational.get(i2) + ")");
                i = i2 + 1;
            }
        }
        if (this.logarithm.size() != 0) {
            if (!(this.pol.isZERO() && this.rational.size() == 0)) {
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
        return (((((((this.num.hashCode() * 37) + this.den.hashCode()) * 37) + this.pol.hashCode()) * 37) + this.rational.hashCode()) * 37) + this.logarithm.hashCode();
    }

    public boolean equals(Object B) {
        Integral<C> b = null;
        try {
            b = (Integral) B;
        } catch (ClassCastException e) {
        }
        if (b != null && this.num.equals(b.num) && this.den.equals(b.den) && this.pol.equals(b.pol) && this.rational.equals(b.rational) && this.logarithm.equals(b.logarithm)) {
            return true;
        }
        return false;
    }
}
