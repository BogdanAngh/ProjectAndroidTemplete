package edu.jas.gb;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import java.util.List;

public class ExtendedGB<C extends RingElem<C>> {
    public final List<GenPolynomial<C>> F;
    public final List<List<GenPolynomial<C>>> F2G;
    public final List<GenPolynomial<C>> G;
    public final List<List<GenPolynomial<C>>> G2F;
    public final GenPolynomialRing<C> ring;

    public ExtendedGB(List<GenPolynomial<C>> F, List<GenPolynomial<C>> G, List<List<GenPolynomial<C>>> F2G, List<List<GenPolynomial<C>>> G2F) {
        this.F = F;
        this.G = G;
        this.F2G = F2G;
        this.G2F = G2F;
        GenPolynomialRing<C> r = null;
        if (G != null) {
            for (GenPolynomial<C> p : G) {
                if (p != null) {
                    r = p.ring;
                    break;
                }
            }
            if (r != null && r.getVars() == null) {
                r.setVars(r.newVars(Constants.Y));
            }
        }
        this.ring = r;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("ExtendedGB: \n\n");
        s.append("F = " + new PolynomialList(this.ring, this.F) + "\n\n");
        s.append("G = " + new PolynomialList(this.ring, this.G) + "\n\n");
        s.append("F2G = " + new ModuleList(this.ring, this.F2G) + "\n\n");
        s.append("G2F = " + new ModuleList(this.ring, this.G2F) + "\n");
        return s.toString();
    }
}
