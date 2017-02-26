package edu.jas.poly;

import edu.jas.structure.RingElem;
import java.util.Map.Entry;

public final class Monomial<C extends RingElem<C>> {
    public final C c;
    public final ExpVector e;

    public Monomial(Entry<ExpVector, C> me) {
        this((ExpVector) me.getKey(), (RingElem) me.getValue());
    }

    public Monomial(ExpVector e, C c) {
        this.e = e;
        this.c = c;
    }

    public ExpVector exponent() {
        return this.e;
    }

    public C coefficient() {
        return this.c;
    }

    public String toString() {
        return this.c.toString() + " " + this.e.toString();
    }
}
