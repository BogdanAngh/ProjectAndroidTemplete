package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;

public class Pair<C extends RingElem<C>> extends AbstractPair<C> implements Comparable<Pair> {
    protected int n;
    protected boolean toZero;
    protected boolean useCriterion3;
    protected boolean useCriterion4;

    @Deprecated
    public Pair(Object a, GenPolynomial<C> b, int i, int j) {
        this((GenPolynomial) a, (GenPolynomial) b, i, j);
    }

    public Pair(GenPolynomial<C> a, GenPolynomial<C> b, int i, int j) {
        this((GenPolynomial) a, (GenPolynomial) b, i, j, 0);
    }

    public Pair(GenPolynomial<C> a, GenPolynomial<C> b, int i, int j, int s) {
        this(a.leadingExpVector().lcm(b.leadingExpVector()), a, b, i, j, s);
    }

    public Pair(ExpVector lcm, GenPolynomial<C> a, GenPolynomial<C> b, int i, int j) {
        this(lcm, a, b, i, j, 0);
    }

    public Pair(ExpVector lcm, GenPolynomial<C> a, GenPolynomial<C> b, int i, int j, int s) {
        super(lcm, a, b, i, j, s);
        this.toZero = false;
        this.useCriterion4 = true;
        this.useCriterion3 = true;
        this.n = 0;
        this.toZero = false;
    }

    public String toString() {
        return super.toString() + "[" + this.n + ", r0=" + this.toZero + ", c4=" + this.useCriterion4 + ", c3=" + this.useCriterion3 + "]";
    }

    public void pairNumber(int n) {
        this.n = n;
    }

    public int getPairNumber() {
        return this.n;
    }

    public void setZero() {
        this.toZero = true;
    }

    public boolean isZero() {
        return this.toZero;
    }

    public boolean equals(Object ob) {
        if ((ob instanceof Pair) && compareTo((Pair) ob) == 0) {
            return true;
        }
        return false;
    }

    public int compareTo(Pair p) {
        int x = p.getPairNumber();
        if (this.n > x) {
            return 1;
        }
        if (this.n < x) {
            return -1;
        }
        return 0;
    }

    public int hashCode() {
        return (this.i << 16) + this.j;
    }

    public void setUseCriterion4(boolean c) {
        this.useCriterion4 = c;
    }

    public boolean getUseCriterion4() {
        return this.useCriterion4;
    }

    public void setUseCriterion3(boolean c) {
        this.useCriterion3 = c;
    }

    public boolean getUseCriterion3() {
        return this.useCriterion3;
    }
}
