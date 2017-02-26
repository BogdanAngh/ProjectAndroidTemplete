package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import java.io.Serializable;

public class Boundary<C extends RingElem<C> & Rational> implements Serializable {
    public final GenPolynomial<Complex<C>> A;
    public final GenPolynomial<Complex<C>>[] polys;
    public final Rectangle<C> rect;
    GenPolynomialRing<C> rfac;

    public Boundary(Rectangle<C> r, GenPolynomial<Complex<C>> p) throws InvalidBoundaryException {
        if (p.isConstant() || p.isZERO()) {
            throw new InvalidBoundaryException("p is constant or 0 " + p);
        }
        this.rect = r;
        this.A = p;
        GreatestCommonDivisor<Complex<C>> ufd = GCDFactory.getImplementation(this.A.ring.coFac);
        this.polys = new GenPolynomial[5];
        Complex<C>[] corner = this.rect.corners;
        int i = 0;
        while (i < 4) {
            GenPolynomial<Complex<C>> pc = PolyUtil.substituteUnivariate(PolyUtil.seriesOfTaylor(this.A, corner[i]), this.A.ring.univariate(0, 1).multiply(corner[i + 1].subtract(corner[i])));
            if (ufd.gcd(this.A, pc).isONE()) {
                this.polys[i] = pc;
                i++;
            } else {
                throw new InvalidBoundaryException("A has a zero on rectangle " + this.rect + ", A = " + this.A);
            }
        }
        this.polys[4] = this.polys[0];
        this.rfac = new GenPolynomialRing(this.A.ring.coFac.ring, this.A.ring);
    }

    protected Boundary(Rectangle<C> r, GenPolynomial<Complex<C>> p, GenPolynomial<Complex<C>>[] b) {
        this.rect = r;
        this.A = p;
        this.polys = b;
        this.rfac = new GenPolynomialRing(this.A.ring.coFac.ring, this.A.ring);
    }

    public String toString() {
        return this.rect.toString();
    }

    public String toScript() {
        return this.rect.toScript();
    }

    public GenPolynomial<C> getRealPart(int i) {
        return PolyUtil.realPartFromComplex(this.rfac, this.polys[i]);
    }

    public GenPolynomial<C> getImagPart(int i) {
        return PolyUtil.imaginaryPartFromComplex(this.rfac, this.polys[i]);
    }

    public Boundary<C> copy() {
        return new Boundary(this.rect, this.A, this.polys);
    }

    public boolean equals(Object b) {
        Boundary<C> a = null;
        try {
            a = (Boundary) b;
        } catch (ClassCastException e) {
        }
        if (a != null && this.rect.equals(a.rect) && this.A.equals(a.A)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((0 + (this.rect.hashCode() * 37)) * 37) + this.A.hashCode();
    }
}
