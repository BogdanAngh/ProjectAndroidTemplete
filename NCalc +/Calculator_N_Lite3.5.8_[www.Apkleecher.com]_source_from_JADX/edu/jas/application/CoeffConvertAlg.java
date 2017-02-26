package edu.jas.application;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class CoeffConvertAlg<C extends GcdRingElem<C>> implements UnaryFunctor<AlgebraicNumber<C>, AlgebraicNumber<C>> {
    protected final AlgebraicNumber<C> A;
    protected final AlgebraicNumberRing<C> afac;

    public CoeffConvertAlg(AlgebraicNumberRing<C> fac, AlgebraicNumber<C> a) {
        if (fac == null || a == null) {
            throw new IllegalArgumentException("fac and a must not be null");
        }
        this.afac = fac;
        this.A = a;
    }

    public AlgebraicNumber<C> eval(AlgebraicNumber<C> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return PolyUtilApp.convertToPrimitiveElem(this.afac, this.A, (AlgebraicNumber) c);
    }
}
