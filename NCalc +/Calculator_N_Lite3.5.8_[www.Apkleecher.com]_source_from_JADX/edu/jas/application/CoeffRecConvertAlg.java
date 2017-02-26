package edu.jas.application;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtilApp */
class CoeffRecConvertAlg<C extends GcdRingElem<C>> implements UnaryFunctor<AlgebraicNumber<AlgebraicNumber<C>>, AlgebraicNumber<C>> {
    protected final AlgebraicNumber<C> A;
    protected final AlgebraicNumber<C> B;
    protected final AlgebraicNumberRing<C> afac;

    public CoeffRecConvertAlg(AlgebraicNumberRing<C> fac, AlgebraicNumber<C> a, AlgebraicNumber<C> b) {
        if (fac == null || a == null || b == null) {
            throw new IllegalArgumentException("fac, a and b must not be null");
        }
        this.afac = fac;
        this.A = a;
        this.B = b;
    }

    public AlgebraicNumber<C> eval(AlgebraicNumber<AlgebraicNumber<C>> c) {
        if (c == null) {
            return this.afac.getZERO();
        }
        return PolyUtilApp.convertToPrimitiveElem(this.afac, this.A, this.B, (AlgebraicNumber) c);
    }
}
