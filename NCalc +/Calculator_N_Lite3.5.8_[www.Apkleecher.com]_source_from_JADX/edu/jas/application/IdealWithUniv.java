package edu.jas.application;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.List;

public class IdealWithUniv<C extends GcdRingElem<C>> implements Serializable {
    public final Ideal<C> ideal;
    public final List<GenPolynomial<C>> others;
    public final List<GenPolynomial<C>> upolys;

    protected IdealWithUniv() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    protected IdealWithUniv(Ideal<C> id, List<GenPolynomial<C>> up) {
        this(id, up, null);
    }

    protected IdealWithUniv(Ideal<C> id, List<GenPolynomial<C>> up, List<GenPolynomial<C>> og) {
        this.ideal = id;
        this.upolys = up;
        this.others = og;
    }

    public String toString() {
        String s = this.ideal.toString();
        if (this.upolys != null) {
            s = s + "\nunivariate polynomials:\n" + this.upolys.toString();
        }
        return this.others == null ? s : s + "\nother polynomials:\n" + this.others.toString();
    }

    public String toScript() {
        String s = this.ideal.toScript();
        if (this.upolys != null) {
            s = s + ", upolys=" + this.upolys.toString();
        }
        return this.others == null ? s : s + ", others=" + this.others.toString();
    }
}
