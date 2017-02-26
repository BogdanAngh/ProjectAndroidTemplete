package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PolyUtilRoot */
class CoeffToRecReAlg<C extends GcdRingElem<C> & Rational> implements UnaryFunctor<C, RealAlgebraicNumber<C>> {
    final int depth;
    protected final List<RealAlgebraicRing<C>> lfac;

    public CoeffToRecReAlg(int depth, RealAlgebraicRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        RealAlgebraicRing<C> afac = fac;
        this.depth = depth;
        this.lfac = new ArrayList(this.depth);
        this.lfac.add(fac);
        int i = 1;
        while (i < this.depth) {
            RingFactory<C> rf = afac.algebraic.ring.coFac;
            if (rf instanceof RealAlgebraicRing) {
                afac = (RealAlgebraicRing) rf;
                this.lfac.add(afac);
                i++;
            } else {
                throw new IllegalArgumentException("fac depth to low");
            }
        }
    }

    public RealAlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return ((RealAlgebraicRing) this.lfac.get(0)).getZERO();
        }
        RealAlgebraicRing af = (RealAlgebraicRing) this.lfac.get(this.lfac.size() - 1);
        C an = new RealAlgebraicNumber(af, af.algebraic.ring.getZERO().sum((RingElem) c));
        for (int i = this.lfac.size() - 2; i >= 0; i--) {
            af = (RealAlgebraicRing) this.lfac.get(i);
            an = new RealAlgebraicNumber(af, af.algebraic.ring.getZERO().sum((GcdRingElem) an));
        }
        return an;
    }
}
