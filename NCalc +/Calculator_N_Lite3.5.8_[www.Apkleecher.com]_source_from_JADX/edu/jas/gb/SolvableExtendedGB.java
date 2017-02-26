package edu.jas.gb;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.ModuleList;
import edu.jas.poly.PolynomialList;
import edu.jas.structure.RingElem;
import java.util.List;

public class SolvableExtendedGB<C extends RingElem<C>> {
    public final List<GenSolvablePolynomial<C>> F;
    public final List<List<GenSolvablePolynomial<C>>> F2G;
    public final List<GenSolvablePolynomial<C>> G;
    public final List<List<GenSolvablePolynomial<C>>> G2F;
    public final GenSolvablePolynomialRing<C> ring;

    public SolvableExtendedGB(List<GenSolvablePolynomial<C>> F, List<GenSolvablePolynomial<C>> G, List<List<GenSolvablePolynomial<C>>> F2G, List<List<GenSolvablePolynomial<C>>> G2F) {
        this.F = F;
        this.G = G;
        this.F2G = F2G;
        this.G2F = G2F;
        GenSolvablePolynomialRing<C> r = null;
        if (G != null) {
            for (GenSolvablePolynomial<C> p : G) {
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
        StringBuffer s = new StringBuffer("SolvableExtendedGB: \n\n");
        s.append("F = " + new PolynomialList(this.ring, this.F) + "\n\n");
        s.append("G = " + new PolynomialList(this.ring, this.G) + "\n\n");
        s.append("F2G = " + new ModuleList(this.ring, this.F2G) + "\n\n");
        s.append("G2F = " + new ModuleList(this.ring, this.G2F) + "\n");
        return s.toString();
    }
}
