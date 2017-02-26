package edu.jas.poly;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.UnaryFunctor;

/* compiled from: PolyUtil */
class ComplToAlgeb<C extends GcdRingElem<C>> implements UnaryFunctor<Complex<C>, AlgebraicNumber<C>> {
    protected final AlgebraicNumber<C> I;
    protected final AlgebraicNumberRing<C> afac;

    public ComplToAlgeb(AlgebraicNumberRing<C> fac) {
        if (fac == null) {
            throw new IllegalArgumentException("fac must not be null");
        }
        this.afac = fac;
        this.I = this.afac.getGenerator();
    }

    public AlgebraicNumber<C> eval(Complex<C> c) {
        if (c == null || c.isZERO()) {
            return this.afac.getZERO();
        }
        if (c.isONE()) {
            return this.afac.getONE();
        }
        if (c.isIMAG()) {
            return this.I;
        }
        return this.I.multiply(c.getIm()).sum(c.getRe());
    }
}
