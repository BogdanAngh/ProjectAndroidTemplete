package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.List;

public class IdealWithRealRoots<C extends GcdRingElem<C>> extends IdealWithUniv<C> {
    public final List<List<BigDecimal>> rroots;

    protected IdealWithRealRoots() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    public IdealWithRealRoots(Ideal<C> id, List<GenPolynomial<C>> up, List<List<BigDecimal>> rr) {
        super(id, up);
        this.rroots = rr;
    }

    public IdealWithRealRoots(IdealWithUniv<C> iu, List<List<BigDecimal>> rr) {
        super(iu.ideal, iu.upolys);
        this.rroots = rr;
    }

    public String toString() {
        return super.toString() + "\nreal roots: " + this.rroots.toString();
    }

    public String toScript() {
        return super.toScript() + ",  " + this.rroots.toString();
    }
}
