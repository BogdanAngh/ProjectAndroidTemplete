package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.io.Serializable;

public abstract class AbstractPair<C extends RingElem<C>> implements Serializable {
    public final ExpVector e;
    public final int i;
    public final int j;
    public final GenPolynomial<C> pi;
    public final GenPolynomial<C> pj;
    protected int s;

    public AbstractPair(GenPolynomial<C> a, GenPolynomial<C> b, int i, int j) {
        this(a.leadingExpVector().lcm(b.leadingExpVector()), (GenPolynomial) a, (GenPolynomial) b, i, j);
    }

    public AbstractPair(GenPolynomial<C> a, GenPolynomial<C> b, int i, int j, int s) {
        this(a.leadingExpVector().lcm(b.leadingExpVector()), a, b, i, j, s);
    }

    public AbstractPair(ExpVector lcm, GenPolynomial<C> a, GenPolynomial<C> b, int i, int j) {
        this(lcm, a, b, i, j, 0);
    }

    public AbstractPair(ExpVector lcm, GenPolynomial<C> a, GenPolynomial<C> b, int i, int j, int s) {
        this.e = lcm;
        this.pi = a;
        this.pj = b;
        this.i = i;
        this.j = j;
        this.s = Math.max(j, Math.max(i, s));
    }

    public void maxIndex(int s) {
        this.s = Math.max(this.j, Math.max(this.i, s));
    }

    public String toString() {
        return "pair(" + this.i + "," + this.j + "," + this.s + ",{" + this.pi.length() + "," + this.pj.length() + "}," + this.e + ")";
    }
}
