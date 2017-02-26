package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;
import org.apache.commons.math4.geometry.VectorFormat;

public class PartialFraction<C extends GcdRingElem<C>> implements Serializable {
    public final List<GenPolynomial<AlgebraicNumber<C>>> adenom;
    public final List<AlgebraicNumber<C>> afactors;
    public final List<GenPolynomial<C>> cdenom;
    public final List<C> cfactors;
    public final GenPolynomial<C> den;
    public final GenPolynomial<C> num;

    public PartialFraction(GenPolynomial<C> n, GenPolynomial<C> d, List<C> cf, List<GenPolynomial<C>> cd, List<AlgebraicNumber<C>> af, List<GenPolynomial<AlgebraicNumber<C>>> ad) {
        this.num = n;
        this.den = d;
        this.cfactors = cf;
        this.cdenom = cd;
        this.afactors = af;
        this.adenom = ad;
    }

    public String toString() {
        int i;
        StringBuffer sb = new StringBuffer();
        sb.append("(" + this.num.toString() + ")");
        sb.append(" / ");
        sb.append("(" + this.den.toString() + ")");
        sb.append(" =\n");
        boolean first = true;
        for (i = 0; i < this.cfactors.size(); i++) {
            GcdRingElem cp = (GcdRingElem) this.cfactors.get(i);
            if (first) {
                first = false;
            } else {
                sb.append(" + ");
            }
            sb.append("(" + cp.toString() + ")");
            sb.append(" log( " + ((GenPolynomial) this.cdenom.get(i)).toString() + ")");
        }
        if (!first && this.afactors.size() > 0) {
            sb.append(" + ");
        }
        first = true;
        for (i = 0; i < this.afactors.size(); i++) {
            if (first) {
                first = false;
            } else {
                sb.append(" + ");
            }
            AlgebraicNumber<C> ap = (AlgebraicNumber) this.afactors.get(i);
            AlgebraicNumberRing<C> ar = ap.factory();
            GenPolynomial<AlgebraicNumber<C>> p = (GenPolynomial) this.adenom.get(i);
            if (p.degree(0) < ar.modul.degree(0) && ar.modul.degree(0) > 2) {
                sb.append("sum_(" + ar.getGenerator() + " in ");
                sb.append("rootOf(" + ar.modul + ") ) ");
            }
            sb.append("(" + ap.toString() + ")");
            sb.append(" log( " + p.toString() + ")");
        }
        return sb.toString();
    }

    public String toStringX() {
        StringBuffer sb = new StringBuffer();
        sb.append("(" + this.num.toString() + ")");
        sb.append(" / ");
        sb.append("(" + this.den.toString() + ")");
        sb.append(" =\n");
        boolean first = true;
        for (GcdRingElem cp : this.cfactors) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(cp.toString());
        }
        if (!first) {
            sb.append(" linear denominators: ");
        }
        first = true;
        for (GenPolynomial<C> cp2 : this.cdenom) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(cp2.toString());
        }
        if (!first) {
            sb.append(VectorFormat.DEFAULT_SEPARATOR);
        }
        first = true;
        for (AlgebraicNumber<C> ap : this.afactors) {
            if (first) {
                first = false;
            }
            sb.append(ap.toString());
            sb.append(" ## over " + ap.factory() + "\n");
        }
        if (!first) {
            sb.append(" denominators: ");
        }
        first = true;
        for (GenPolynomial<AlgebraicNumber<C>> ap2 : this.adenom) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(ap2.toString());
        }
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.num.toScript());
        sb.append(" / ");
        sb.append(this.den.toScript());
        sb.append(" = ");
        boolean first = true;
        for (GcdRingElem cp : this.cfactors) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(cp.toScript());
        }
        if (!first) {
            sb.append(" linear denominators: ");
        }
        first = true;
        for (GenPolynomial<C> cp2 : this.cdenom) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(cp2.toScript());
        }
        if (!first) {
            sb.append(", ");
        }
        first = true;
        for (AlgebraicNumber<C> ap : this.afactors) {
            if (first) {
                first = false;
            }
            sb.append(ap.toScript());
            sb.append(" ## over " + ap.toScriptFactory() + "\n");
        }
        sb.append(" denominators: ");
        first = true;
        for (GenPolynomial<AlgebraicNumber<C>> ap2 : this.adenom) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(ap2.toScript());
        }
        return sb.toString();
    }

    public int hashCode() {
        return (((((((((this.num.hashCode() * 37) + this.den.hashCode()) * 37) + this.cfactors.hashCode()) * 37) + this.cdenom.hashCode()) * 37) + this.afactors.hashCode()) * 37) + this.adenom.hashCode();
    }

    public boolean equals(Object B) {
        boolean t = false;
        if (B == null || !(B instanceof PartialFraction)) {
            return false;
        }
        PartialFraction<C> a = (PartialFraction) B;
        if (this.num.equals(a.num) && this.den.equals(a.den)) {
            t = true;
        }
        if (!t) {
            return t;
        }
        t = this.cfactors.equals(a.cfactors);
        if (!t) {
            return t;
        }
        t = this.cdenom.equals(a.cdenom);
        if (!t) {
            return t;
        }
        t = this.afactors.equals(a.afactors);
        if (t) {
            return this.adenom.equals(a.adenom);
        }
        return t;
    }
}
