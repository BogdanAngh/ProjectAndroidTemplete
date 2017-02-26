package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.CartesianProduct;
import edu.jas.util.CartesianProductInfinite;
import edu.jas.util.LongIterable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* compiled from: GenPolynomialRing */
class GenPolynomialIterator<C extends RingElem<C>> implements Iterator<GenPolynomial<C>> {
    final List<Iterable<C>> coeffiter;
    GenPolynomial<C> current;
    final Iterator<List<Long>> eviter;
    Iterator<List<C>> itercoeff;
    final List<ExpVector> powers;
    final GenPolynomialRing<C> ring;

    public GenPolynomialIterator(GenPolynomialRing<C> fac) {
        this.ring = fac;
        LongIterable li = new LongIterable();
        li.setNonNegativeIterator();
        List<Iterable<Long>> tlist = new ArrayList(this.ring.nvar);
        for (int i = 0; i < this.ring.nvar; i++) {
            tlist.add(li);
        }
        this.eviter = new CartesianProductInfinite(tlist).iterator();
        RingFactory<C> cf = this.ring.coFac;
        this.coeffiter = new ArrayList();
        if ((cf instanceof Iterable) && cf.isFinite()) {
            this.coeffiter.add((Iterable) cf);
            this.itercoeff = new CartesianProduct(this.coeffiter).iterator();
            this.powers = new ArrayList();
            ExpVector e = ExpVector.create((Collection) this.eviter.next());
            this.powers.add(e);
            this.current = new GenPolynomial(this.ring, (RingElem) ((List) this.itercoeff.next()).get(0), e);
            return;
        }
        throw new IllegalArgumentException("only for finite iterable coefficients implemented");
    }

    public boolean hasNext() {
        return true;
    }

    public synchronized GenPolynomial<C> next() {
        GenPolynomial<C> res;
        res = this.current;
        if (!this.itercoeff.hasNext()) {
            this.powers.add(0, ExpVector.create((Collection) this.eviter.next()));
            if (this.coeffiter.size() == 1) {
                this.coeffiter.add(this.coeffiter.get(0));
                Iterable<C> it = (Iterable) this.coeffiter.get(0);
                List<C> elms = new ArrayList();
                for (C elm : it) {
                    elms.add(elm);
                }
                elms.remove(0);
                this.coeffiter.set(0, elms);
            } else {
                this.coeffiter.add(this.coeffiter.get(1));
            }
            this.itercoeff = new CartesianProduct(this.coeffiter).iterator();
        }
        List<C> coeffs = (List) this.itercoeff.next();
        GenPolynomial<C> pol = this.ring.getZERO().copy();
        int i = 0;
        for (ExpVector f : this.powers) {
            int i2 = i + 1;
            RingElem c = (RingElem) coeffs.get(i);
            if (c.isZERO()) {
                i = i2;
            } else if (pol.val.get(f) != null) {
                System.out.println("error f in pol = " + f + ", " + pol.getMap().get(f));
                throw new RuntimeException("error in iterator");
            } else {
                pol.doPutToMap(f, c);
                i = i2;
            }
        }
        this.current = pol;
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
