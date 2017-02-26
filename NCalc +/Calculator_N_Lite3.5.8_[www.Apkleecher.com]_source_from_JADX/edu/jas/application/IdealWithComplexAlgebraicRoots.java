package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.List;

public class IdealWithComplexAlgebraicRoots<D extends GcdRingElem<D> & Rational> extends IdealWithUniv<D> {
    public final List<List<Complex<RealAlgebraicNumber<D>>>> can;
    protected List<List<Complex<BigDecimal>>> droots;

    protected IdealWithComplexAlgebraicRoots() {
        this.droots = null;
        throw new IllegalArgumentException("do not use this constructor");
    }

    public IdealWithComplexAlgebraicRoots(Ideal<D> id, List<GenPolynomial<D>> up, List<List<Complex<RealAlgebraicNumber<D>>>> cr) {
        super(id, up);
        this.droots = null;
        this.can = cr;
    }

    public IdealWithComplexAlgebraicRoots(IdealWithUniv<D> iu, List<List<Complex<RealAlgebraicNumber<D>>>> cr) {
        super(iu.ideal, iu.upolys);
        this.droots = null;
        this.can = cr;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString() + "\ncomplex roots:\n");
        sb.append("[");
        boolean f1 = true;
        for (List<Complex<RealAlgebraicNumber<D>>> lr : this.can) {
            if (f1) {
                f1 = false;
            } else {
                sb.append(", ");
            }
            sb.append("[");
            boolean f2 = true;
            for (Complex<RealAlgebraicNumber<D>> rr : lr) {
                if (f2) {
                    f2 = false;
                } else {
                    sb.append(", ");
                }
                sb.append(rr.ring.toScript());
            }
            sb.append("]");
        }
        sb.append("]");
        if (this.droots != null) {
            sb.append("\ndecimal complex root approximation:\n");
            for (List<Complex<BigDecimal>> d : this.droots) {
                sb.append(d.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String toScript() {
        return super.toScript() + ",  " + this.can.toString();
    }

    public synchronized List<List<Complex<BigDecimal>>> decimalApproximation() {
        List<List<Complex<BigDecimal>>> list;
        if (this.droots != null) {
            list = this.droots;
        } else {
            list = new ArrayList();
            ComplexRing<BigDecimal> cfac = new ComplexRing(new BigDecimal());
            for (List<Complex<RealAlgebraicNumber<D>>> rri : this.can) {
                List<Complex<BigDecimal>> r = new ArrayList();
                for (Complex<RealAlgebraicNumber<D>> rr : rri) {
                    r.add(new Complex(cfac, new BigDecimal(((RealAlgebraicNumber) rr.getRe()).magnitude()), new BigDecimal(((RealAlgebraicNumber) rr.getIm()).magnitude())));
                }
                list.add(r);
            }
            this.droots = list;
        }
        return list;
    }

    public void doDecimalApproximation() {
        if (decimalApproximation().isEmpty()) {
            System.out.println("unused is empty");
        }
    }
}
