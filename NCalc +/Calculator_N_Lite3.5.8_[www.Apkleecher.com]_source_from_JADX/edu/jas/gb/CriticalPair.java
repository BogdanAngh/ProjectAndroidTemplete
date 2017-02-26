package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;

public class CriticalPair<C extends RingElem<C>> extends AbstractPair<C> {
    protected volatile boolean inReduction;
    protected volatile GenPolynomial<C> reductum;

    public CriticalPair(ExpVector e, GenPolynomial<C> pi, GenPolynomial<C> pj, int i, int j) {
        super(e, (GenPolynomial) pi, (GenPolynomial) pj, i, j);
        this.inReduction = false;
        this.reductum = null;
    }

    public String toString() {
        StringBuffer s = new StringBuffer(super.toString() + "[ ");
        if (this.inReduction) {
            s.append("," + this.inReduction);
        }
        if (this.reductum != null) {
            s.append("," + this.reductum.leadingExpVector());
        }
        s.append(" ]");
        return s.toString();
    }

    public void setInReduction() {
        if (this.inReduction) {
            throw new IllegalStateException("already in reduction " + this);
        }
        this.inReduction = true;
    }

    public boolean getInReduction() {
        return this.inReduction;
    }

    public GenPolynomial<C> getReductum() {
        return this.reductum;
    }

    public void setReductum(GenPolynomial<C> r) {
        if (r == null) {
            throw new IllegalArgumentException("reduction null not allowed " + this);
        }
        this.inReduction = false;
        this.reductum = r;
    }

    public boolean isZERO() {
        if (this.reductum == null) {
            return false;
        }
        return this.reductum.isZERO();
    }

    public boolean isONE() {
        if (this.reductum == null) {
            return false;
        }
        return this.reductum.isONE();
    }
}
