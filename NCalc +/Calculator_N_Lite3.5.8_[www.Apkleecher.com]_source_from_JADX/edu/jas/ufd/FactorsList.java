package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;

public class FactorsList<C extends GcdRingElem<C>> implements Serializable {
    public final List<Factors<C>> afactors;
    public final List<GenPolynomial<C>> factors;
    public final GenPolynomial<C> poly;

    public FactorsList(GenPolynomial<C> p, List<GenPolynomial<C>> list) {
        this(p, list, null);
    }

    public FactorsList(GenPolynomial<C> p, List<GenPolynomial<C>> list, List<Factors<C>> alist) {
        this.poly = p;
        this.factors = list;
        this.afactors = alist;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (GenPolynomial<C> p : this.factors) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n ");
            }
            sb.append(p.toString());
        }
        if (this.afactors == null) {
            return sb.toString();
        }
        for (Factors<C> f : this.afactors) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n ");
            }
            sb.append(f.toString());
        }
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.poly.toScript());
        sb.append(" =\n");
        boolean first = true;
        for (GenPolynomial<C> p : this.factors) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            sb.append(p.toScript());
        }
        if (this.afactors == null) {
            return sb.toString();
        }
        for (Factors<C> f : this.afactors) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            sb.append(f.toScript());
        }
        return sb.toString();
    }

    public AlgebraicNumberRing<C> findExtensionField() {
        if (this.afactors == null) {
            return null;
        }
        AlgebraicNumberRing<C> ar = null;
        int depth = 0;
        for (Factors<C> f : this.afactors) {
            AlgebraicNumberRing<C> aring = f.findExtensionField();
            if (aring != null) {
                int d = aring.depth();
                if (d > depth) {
                    depth = d;
                    ar = aring;
                }
            }
        }
        return ar;
    }
}
