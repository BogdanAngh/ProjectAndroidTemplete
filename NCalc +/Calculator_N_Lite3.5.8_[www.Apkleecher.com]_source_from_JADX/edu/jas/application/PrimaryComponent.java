package edu.jas.application;

import edu.jas.structure.GcdRingElem;
import java.io.Serializable;

public class PrimaryComponent<C extends GcdRingElem<C>> implements Serializable {
    protected int exponent;
    public final Ideal<C> primary;
    public final IdealWithUniv<C> prime;

    protected PrimaryComponent() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    protected PrimaryComponent(Ideal<C> q, IdealWithUniv<C> p) {
        this(q, p, -1);
    }

    protected PrimaryComponent(Ideal<C> q, IdealWithUniv<C> p, int e) {
        this.primary = q;
        this.prime = p;
        this.exponent = e;
    }

    public int getExponent() {
        return this.exponent;
    }

    public void setExponent(int e) {
        this.exponent = e;
    }

    public String toString() {
        String s = "\nprimary:\n" + this.primary.toString() + "\nprime:\n" + this.prime.toString();
        return this.exponent < 0 ? s : s + "\nexponent:\n" + this.exponent;
    }

    public String toScript() {
        String s = this.primary.toScript() + ",  " + this.prime.toString();
        return this.exponent < 0 ? s : s + ", " + this.exponent;
    }
}
