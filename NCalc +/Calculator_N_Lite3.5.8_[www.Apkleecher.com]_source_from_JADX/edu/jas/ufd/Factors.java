package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.GcdRingElem;
import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Factors<C extends GcdRingElem<C>> implements Comparable<Factors<C>>, Serializable {
    public final AlgebraicNumberRing<C> afac;
    public final List<GenPolynomial<AlgebraicNumber<C>>> afactors;
    public final GenPolynomial<AlgebraicNumber<C>> apoly;
    public final List<Factors<AlgebraicNumber<C>>> arfactors;
    public final GenPolynomial<C> poly;

    public Factors(GenPolynomial<C> p) {
        this(p, null, null, null, null);
    }

    public Factors(GenPolynomial<C> p, AlgebraicNumberRing<C> af, GenPolynomial<AlgebraicNumber<C>> ap, List<GenPolynomial<AlgebraicNumber<C>>> afact) {
        this(p, af, ap, afact, null);
    }

    public Factors(GenPolynomial<C> p, AlgebraicNumberRing<C> af, GenPolynomial<AlgebraicNumber<C>> ap, List<GenPolynomial<AlgebraicNumber<C>>> afact, List<Factors<AlgebraicNumber<C>>> arfact) {
        this.poly = p;
        this.afac = af;
        this.apoly = ap;
        this.afactors = afact;
        this.arfactors = arfact;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.poly.toString());
        if (this.afac == null) {
            return sb.toString();
        }
        sb.append(" = ");
        boolean first = true;
        for (GenPolynomial<AlgebraicNumber<C>> ap : this.afactors) {
            if (first) {
                first = false;
            } else {
                sb.append(", ");
            }
            sb.append(ap.toString());
        }
        sb.append("\n  ## over " + this.afac.toString() + "\n");
        if (this.arfactors == null) {
            return sb.toString();
        }
        first = true;
        for (Factors<AlgebraicNumber<C>> arp : this.arfactors) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n");
            }
            sb.append(arp.toString());
        }
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer();
        if (this.afac == null) {
            return sb.toString();
        }
        boolean first = true;
        for (GenPolynomial<AlgebraicNumber<C>> ap : this.afactors) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            sb.append(ap.toScript());
        }
        sb.append("   #:: " + this.afac.toScript() + BuildConfig.FLAVOR);
        if (this.arfactors == null) {
            return sb.toString();
        }
        for (Factors<AlgebraicNumber<C>> arp : this.arfactors) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            sb.append(arp.toScript());
        }
        return sb.toString();
    }

    public int length() {
        if (this.afac == null) {
            return 0;
        }
        int i = 0 + this.afactors.size();
        if (this.arfactors == null) {
            return i;
        }
        for (Factors<AlgebraicNumber<C>> f : this.arfactors) {
            i += f.length();
        }
        return i;
    }

    public int hashCode() {
        int h = this.poly.hashCode();
        if (this.afac == null) {
            return h;
        }
        h = (h << 27) + this.afac.hashCode();
        if (this.afactors != null) {
            h = (h << 27) + this.afactors.hashCode();
        }
        if (this.arfactors != null) {
            h = (h << 27) + this.arfactors.hashCode();
        }
        return h;
    }

    public boolean equals(Object B) {
        if (B != null && (B instanceof Factors) && compareTo((Factors) B) == 0) {
            return true;
        }
        return false;
    }

    public int compareTo(Factors<C> facs) {
        int s = this.poly.compareTo(facs.poly);
        if (s != 0) {
            return s;
        }
        if (this.afac == null) {
            return -1;
        }
        if (facs.afac == null) {
            return 1;
        }
        s = this.afac.modul.compareTo(facs.afac.modul);
        if (s != 0) {
            return s;
        }
        s = new PolynomialList(((GenPolynomial) this.afactors.get(0)).ring, this.afactors).compareTo(new PolynomialList(((GenPolynomial) facs.afactors.get(0)).ring, facs.afactors));
        if (s != 0) {
            return s;
        }
        if (this.arfactors == null && facs.arfactors == null) {
            return 0;
        }
        if (this.arfactors == null) {
            return -1;
        }
        if (facs.arfactors == null) {
            return 1;
        }
        int i = 0;
        for (Factors<AlgebraicNumber<C>> arp : this.arfactors) {
            if (i >= facs.arfactors.size()) {
                return 1;
            }
            s = arp.compareTo((Factors) facs.arfactors.get(i));
            if (s != 0) {
                return s;
            }
            i++;
        }
        if (i < facs.arfactors.size()) {
            return -1;
        }
        return 0;
    }

    public AlgebraicNumberRing<C> findExtensionField() {
        if (this.afac == null) {
            return null;
        }
        if (this.arfactors == null) {
            return this.afac;
        }
        AlgebraicNumberRing<C> arr = this.afac;
        int depth = 1;
        for (Factors<AlgebraicNumber<C>> af : this.arfactors) {
            AlgebraicNumberRing<AlgebraicNumber<C>> aring = af.findExtensionField();
            if (aring != null) {
                int d = aring.depth();
                if (d > depth) {
                    depth = d;
                    arr = aring;
                }
            }
        }
        return arr;
    }

    public List<GenPolynomial<AlgebraicNumber<C>>> getFactors() {
        List<GenPolynomial<AlgebraicNumber<C>>> af = new ArrayList();
        if (this.afac != null) {
            af.addAll(this.afactors);
            if (this.arfactors != null) {
                for (Factors<AlgebraicNumber<C>> arp : this.arfactors) {
                    af.add(arp.poly);
                }
            }
        }
        return af;
    }

    public Factors<AlgebraicNumber<C>> getFactor(GenPolynomial<AlgebraicNumber<C>> p) {
        if (this.afac == null) {
            return null;
        }
        for (Factors<AlgebraicNumber<C>> arp : this.arfactors) {
            if (p.equals(arp.poly)) {
                return arp;
            }
        }
        return null;
    }
}
