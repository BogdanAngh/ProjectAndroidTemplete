package edu.jas.application;

import edu.jas.structure.RingElem;
import java.io.Serializable;
import org.apache.commons.math4.geometry.VectorFormat;

public class CPair<C extends RingElem<C>> implements Serializable, Comparable<CPair<C>> {
    public final int i;
    public final int j;
    protected int n;
    public final ColorPolynomial<C> pi;
    public final ColorPolynomial<C> pj;
    protected boolean toZero;
    protected boolean useCriterion3;
    protected boolean useCriterion4;

    public CPair(ColorPolynomial<C> a, ColorPolynomial<C> b, int i, int j) {
        this.toZero = false;
        this.useCriterion4 = true;
        this.useCriterion3 = true;
        this.pi = a;
        this.pj = b;
        this.i = i;
        this.j = j;
        this.n = 0;
        this.toZero = false;
    }

    public String toString() {
        return "pair[" + this.n + "](" + this.i + VectorFormat.DEFAULT_PREFIX + this.pi.length() + "}," + this.j + VectorFormat.DEFAULT_PREFIX + this.pj.length() + VectorFormat.DEFAULT_SUFFIX + ", r0=" + this.toZero + ", c4=" + this.useCriterion4 + ", c3=" + this.useCriterion3 + ")";
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
        try {
            CPair cp = (CPair) ob;
            if (cp != null && compareTo(cp) == 0) {
                return true;
            }
            return false;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int compareTo(CPair<C> p) {
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
        return getPairNumber();
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
