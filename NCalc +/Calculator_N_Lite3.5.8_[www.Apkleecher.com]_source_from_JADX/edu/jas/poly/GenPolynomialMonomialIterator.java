package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.CartesianProductInfinite;
import edu.jas.util.LongIterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: GenPolynomialRing */
class GenPolynomialMonomialIterator<C extends RingElem<C>> implements Iterator<GenPolynomial<C>> {
    GenPolynomial<C> current;
    final Iterator<List> iter;
    final GenPolynomialRing<C> ring;

    public GenPolynomialMonomialIterator(GenPolynomialRing<C> fac) {
        this.ring = fac;
        LongIterable li = new LongIterable();
        li.setNonNegativeIterator();
        List<Iterable<Long>> tlist = new ArrayList(this.ring.nvar);
        for (int i = 0; i < this.ring.nvar; i++) {
            tlist.add(li);
        }
        CartesianProductInfinite<Long> ei = new CartesianProductInfinite(tlist);
        RingFactory<C> cf = this.ring.coFac;
        if (!(cf instanceof Iterable) || cf.isFinite()) {
            throw new IllegalArgumentException("only for infinite iterable coefficients implemented");
        }
        Iterable<C> coeffiter = (Iterable) cf;
        List<Iterable> eci = new ArrayList(2);
        eci.add(ei);
        eci.add(coeffiter);
        this.iter = new CartesianProductInfinite(eci).iterator();
        List ec = (List) this.iter.next();
        RingElem c = (RingElem) ec.get(1);
        ExpVector e = ExpVector.create((List) ec.get(0));
        this.current = new GenPolynomial(this.ring, c, e);
    }

    public boolean hasNext() {
        return true;
    }

    public synchronized GenPolynomial<C> next() {
        GenPolynomial<C> res;
        res = this.current;
        List ec = (List) this.iter.next();
        C c = (RingElem) ec.get(1);
        while (c.isZERO()) {
            ec = (List) this.iter.next();
            RingElem c2 = (RingElem) ec.get(1);
        }
        this.current = new GenPolynomial(this.ring, c, ExpVector.create((List) ec.get(0)));
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
