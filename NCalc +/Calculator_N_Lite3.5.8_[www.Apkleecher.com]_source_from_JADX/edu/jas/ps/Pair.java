package edu.jas.ps;

import edu.jas.structure.RingElem;
import java.io.Serializable;

public class Pair<C extends RingElem<C>> implements Serializable, Comparable<Pair> {
    public final int i;
    public final int j;
    protected int n;
    public final MultiVarPowerSeries<C> pi;
    public final MultiVarPowerSeries<C> pj;
    protected boolean toZero;
    protected boolean useCriterion3;
    protected boolean useCriterion4;

    public Pair(MultiVarPowerSeries<C> a, MultiVarPowerSeries<C> b, int i, int j) {
        this.toZero = false;
        this.useCriterion4 = false;
        this.useCriterion3 = false;
        this.pi = a;
        this.pj = b;
        this.i = i;
        this.j = j;
        this.n = 0;
        this.toZero = false;
    }

    public String toString() {
        return "pair[" + this.n + "](" + this.i + this.j + ", r0=" + this.toZero + ", c4=" + this.useCriterion4 + ", c3=" + this.useCriterion3 + ")";
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
