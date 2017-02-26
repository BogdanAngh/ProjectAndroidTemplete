package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import java.util.List;

public class PolynomialTaylorFunction<C extends RingElem<C>> implements TaylorFunction<C> {
    final long facul;
    final GenPolynomial<C> pol;

    public PolynomialTaylorFunction(GenPolynomial<C> p) {
        this(p, 1);
    }

    public PolynomialTaylorFunction(GenPolynomial<C> p, long f) {
        this.pol = p;
        this.facul = f;
    }

    public String toString() {
        return this.pol.toString();
    }

    public long getFacul() {
        return this.facul;
    }

    public boolean isZERO() {
        return this.pol.isZERO();
    }

    public TaylorFunction<C> deriviative() {
        return new PolynomialTaylorFunction(PolyUtil.baseDeriviative(this.pol));
    }

    public TaylorFunction<C> deriviative(ExpVector i) {
        GenPolynomial<C> p = this.pol;
        long f = 1;
        if (i.signum() == 0 || this.pol.isZERO()) {
            return new PolynomialTaylorFunction(p, 1);
        }
        for (int j = 0; j < i.length(); j++) {
            long e = i.getVal(j);
            if (e != 0) {
                int jl = (i.length() - 1) - j;
                for (long k = 0; k < e; k++) {
                    p = PolyUtil.baseDeriviative(p, jl);
                    f *= k + 1;
                    if (p.isZERO()) {
                        return new PolynomialTaylorFunction(p, f);
                    }
                }
                continue;
            }
        }
        return new PolynomialTaylorFunction(p, f);
    }

    public C evaluate(C a) {
        return PolyUtil.evaluateMain(this.pol.ring.coFac, this.pol, (RingElem) a);
    }

    public C evaluate(List<C> a) {
        return PolyUtil.evaluateAll(this.pol.ring.coFac, this.pol.ring, this.pol, a);
    }
}
