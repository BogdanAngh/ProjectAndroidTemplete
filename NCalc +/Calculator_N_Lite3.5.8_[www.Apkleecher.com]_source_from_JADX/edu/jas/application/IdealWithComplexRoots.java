package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.List;

class IdealWithComplexRoots<C extends GcdRingElem<C>> extends IdealWithUniv<C> {
    public final List<List<Complex<BigDecimal>>> croots;

    protected IdealWithComplexRoots() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    public IdealWithComplexRoots(Ideal<C> id, List<GenPolynomial<C>> up, List<List<Complex<BigDecimal>>> cr) {
        super(id, up);
        this.croots = cr;
    }

    public IdealWithComplexRoots(IdealWithUniv<C> iu, List<List<Complex<BigDecimal>>> cr) {
        super(iu.ideal, iu.upolys);
        this.croots = cr;
    }

    public String toString() {
        return super.toString() + "\ncomplex roots: " + this.croots.toString();
    }

    public String toScript() {
        return super.toScript() + ",  " + this.croots.toString();
    }
}
