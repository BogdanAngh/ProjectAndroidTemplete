package edu.jas.application;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.root.RealAlgebraicNumber;
import edu.jas.structure.GcdRingElem;
import java.util.ArrayList;
import java.util.List;

public class IdealWithRealAlgebraicRoots<D extends GcdRingElem<D> & Rational> extends IdealWithUniv<D> {
    protected List<List<BigDecimal>> droots;
    public final List<List<RealAlgebraicNumber<D>>> ran;

    protected IdealWithRealAlgebraicRoots() {
        this.droots = null;
        throw new IllegalArgumentException("do not use this constructor");
    }

    public IdealWithRealAlgebraicRoots(Ideal<D> id, List<GenPolynomial<D>> up, List<List<RealAlgebraicNumber<D>>> rr) {
        super(id, up);
        this.droots = null;
        this.ran = rr;
    }

    public IdealWithRealAlgebraicRoots(IdealWithUniv<D> iu, List<List<RealAlgebraicNumber<D>>> rr) {
        super(iu.ideal, iu.upolys);
        this.droots = null;
        this.ran = rr;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(super.toString() + "\nreal roots:\n");
        sb.append("[");
        boolean f1 = true;
        for (List<RealAlgebraicNumber<D>> lr : this.ran) {
            if (f1) {
                f1 = false;
            } else {
                sb.append(", ");
            }
            sb.append("[");
            boolean f2 = true;
            for (RealAlgebraicNumber<D> rr : lr) {
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
            sb.append("\ndecimal real root approximation:\n");
            for (List<BigDecimal> d : this.droots) {
                sb.append(d.toString());
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public String toScript() {
        return super.toScript() + ",  " + this.ran.toString();
    }

    public synchronized List<List<BigDecimal>> decimalApproximation() {
        List<List<BigDecimal>> list;
        if (this.droots != null) {
            list = this.droots;
        } else {
            list = new ArrayList();
            for (List<RealAlgebraicNumber<D>> rri : this.ran) {
                List<BigDecimal> r = new ArrayList();
                for (RealAlgebraicNumber<D> rr : rri) {
                    r.add(new BigDecimal(rr.magnitude()));
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
