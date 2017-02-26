package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import java.util.ArrayList;
import java.util.List;

/* compiled from: PolyUtil */
class CoeffToRecAlg<C extends GcdRingElem<C>> implements UnaryFunctor<C, AlgebraicNumber<C>> {
    final int depth;
    protected final List<AlgebraicNumberRing<C>> lfac;

    public CoeffToRecAlg(int depth, AlgebraicNumberRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        AlgebraicNumberRing<C> afac = fac;
        this.depth = depth;
        this.lfac = new ArrayList(this.depth);
        this.lfac.add(fac);
        int i = 1;
        while (i < this.depth) {
            RingFactory<C> rf = afac.ring.coFac;
            if (rf instanceof AlgebraicNumberRing) {
                afac = (AlgebraicNumberRing) rf;
                this.lfac.add(afac);
                i++;
            } else {
                throw new IllegalArgumentException("fac depth to low");
            }
        }
    }

    public AlgebraicNumber<C> eval(C c) {
        if (c == null) {
            return ((AlgebraicNumberRing) this.lfac.get(0)).getZERO();
        }
        AlgebraicNumberRing<C> af = (AlgebraicNumberRing) this.lfac.get(this.lfac.size() - 1);
        C an = new AlgebraicNumber(af, af.ring.getZERO().sum((RingElem) c));
        for (int i = this.lfac.size() - 2; i >= 0; i--) {
            af = (AlgebraicNumberRing) this.lfac.get(i);
            an = new AlgebraicNumber(af, af.ring.getZERO().sum((GcdRingElem) an));
        }
        return an;
    }
}
