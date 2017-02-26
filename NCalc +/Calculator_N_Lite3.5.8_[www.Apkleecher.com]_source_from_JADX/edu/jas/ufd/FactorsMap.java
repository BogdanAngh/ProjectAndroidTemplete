package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.SortedMap;

public class FactorsMap<C extends GcdRingElem<C>> implements Serializable {
    public final SortedMap<Factors<C>, Long> afactors;
    public final SortedMap<GenPolynomial<C>, Long> factors;
    public final GenPolynomial<C> poly;

    public FactorsMap(GenPolynomial<C> p, SortedMap<GenPolynomial<C>, Long> map) {
        this(p, map, null);
    }

    public FactorsMap(GenPolynomial<C> p, SortedMap<GenPolynomial<C>, Long> map, SortedMap<Factors<C>, Long> amap) {
        this.poly = p;
        this.factors = map;
        this.afactors = amap;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.poly.toString());
        sb.append(" =\n");
        boolean first = true;
        for (GenPolynomial<C> p : this.factors.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n ");
            }
            sb.append(p.toString());
            long e = ((Long) this.factors.get(p)).longValue();
            if (e > 1) {
                sb.append("**" + e);
            }
        }
        if (this.afactors == null) {
            return sb.toString();
        }
        for (Factors<C> f : this.afactors.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append(",\n ");
            }
            sb.append(f.toString());
            Long e2 = (Long) this.afactors.get(f);
            if (e2 != null && e2.longValue() > 1) {
                sb.append("**" + e2);
            }
        }
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer();
        boolean first = true;
        for (GenPolynomial<C> p : this.factors.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            sb.append(p.toScript());
            long e = ((Long) this.factors.get(p)).longValue();
            if (e > 1) {
                sb.append("**" + e);
            }
        }
        if (this.afactors == null) {
            return sb.toString();
        }
        for (Factors<C> f : this.afactors.keySet()) {
            if (first) {
                first = false;
            } else {
                sb.append("\n * ");
            }
            Long e2 = (Long) this.afactors.get(f);
            if (e2 == null) {
                System.out.println("f = " + f);
                System.out.println("afactors = " + this.afactors);
                throw new RuntimeException("this should not happen");
            } else if (e2.longValue() == 1) {
                sb.append(f.toScript());
            } else {
                sb.append("(\n");
                sb.append(f.toScript());
                sb.append("\n)**" + e2);
            }
        }
        return sb.toString();
    }

    public int length() {
        int i = this.factors.keySet().size();
        if (this.afactors == null) {
            return i;
        }
        for (Factors<C> f : this.afactors.keySet()) {
            i += f.length();
        }
        return i;
    }

    public AlgebraicNumberRing<C> findExtensionField() {
        if (this.afactors == null) {
            return null;
        }
        AlgebraicNumberRing<C> ar = null;
        int depth = 0;
        for (Factors<C> f : this.afactors.keySet()) {
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
