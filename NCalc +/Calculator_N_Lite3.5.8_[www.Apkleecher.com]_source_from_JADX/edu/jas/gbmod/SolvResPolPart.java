package edu.jas.gbmod;

import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import java.io.Serializable;

/* compiled from: SolvableSyzygyAbstract */
class SolvResPolPart<C extends RingElem<C>> implements Serializable {
    public final PolynomialList<C> GB;
    public final PolynomialList<C> ideal;
    public final ModuleList<C> syzygy;

    public SolvResPolPart(PolynomialList<C> m, PolynomialList<C> g, ModuleList<C> z) {
        this.ideal = m;
        this.GB = g;
        this.syzygy = z;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("SolvResPolPart(\n");
        s.append("ideal = " + this.ideal);
        s.append("\n GB = " + this.GB);
        s.append("\n syzygy = " + this.syzygy);
        s.append(")");
        return s.toString();
    }
}
