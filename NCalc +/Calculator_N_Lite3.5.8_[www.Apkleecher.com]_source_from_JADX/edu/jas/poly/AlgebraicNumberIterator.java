package edu.jas.poly;

import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.util.CartesianProduct;
import edu.jas.util.CartesianProductInfinite;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: AlgebraicNumberRing */
class AlgebraicNumberIterator<C extends RingElem<C>> implements Iterator<AlgebraicNumber<C>> {
    private static final Logger logger;
    final AlgebraicNumberRing<C> aring;
    final Iterator<List<C>> iter;
    final List<GenPolynomial<C>> powers;

    static {
        logger = Logger.getLogger(AlgebraicNumberIterator.class);
    }

    public AlgebraicNumberIterator(AlgebraicNumberRing<C> aring) {
        long j;
        RingFactory<C> cf = aring.ring.coFac;
        this.aring = aring;
        long d = aring.modul.degree(0);
        this.powers = new ArrayList((int) d);
        for (j = d - 1; j >= 0; j--) {
            this.powers.add(aring.ring.univariate(0, j));
        }
        if (cf instanceof Iterable) {
            List<Iterable<C>> comps = new ArrayList((int) d);
            Iterable<C> cfi = (Iterable) cf;
            for (j = 0; j < d; j++) {
                comps.add(cfi);
            }
            if (cf.isFinite()) {
                this.iter = new CartesianProduct(comps).iterator();
            } else {
                this.iter = new CartesianProductInfinite(comps).iterator();
            }
            if (logger.isInfoEnabled()) {
                logger.info("iterator for degree " + d + ", finite = " + cf.isFinite());
                return;
            }
            return;
        }
        throw new IllegalArgumentException("only for iterable coefficients implemented");
    }

    public boolean hasNext() {
        return this.iter.hasNext();
    }

    public AlgebraicNumber<C> next() {
        List<C> coeffs = (List) this.iter.next();
        GenPolynomial<C> pol = this.aring.ring.getZERO();
        int i = 0;
        for (GenPolynomial<C> f : this.powers) {
            int i2 = i + 1;
            RingElem c = (RingElem) coeffs.get(i);
            if (c.isZERO()) {
                i = i2;
            } else {
                pol = pol.sum(f.multiply(c));
                i = i2;
            }
        }
        return new AlgebraicNumber(this.aring, pol);
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove tuples");
    }
}
