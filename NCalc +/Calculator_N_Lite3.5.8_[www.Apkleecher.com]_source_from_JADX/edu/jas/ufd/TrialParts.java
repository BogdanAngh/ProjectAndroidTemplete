package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.poly.GenPolynomial;
import java.util.List;

/* compiled from: FactorInteger */
class TrialParts {
    public final List<BigInteger> evalPoints;
    public final List<BigInteger> ldcfEval;
    public final List<GenPolynomial<BigInteger>> ldcfFactors;
    public final List<GenPolynomial<BigInteger>> univFactors;
    public final GenPolynomial<BigInteger> univPoly;

    public TrialParts(List<BigInteger> ev, GenPolynomial<BigInteger> up, List<GenPolynomial<BigInteger>> uf, List<BigInteger> le, List<GenPolynomial<BigInteger>> lf) {
        this.evalPoints = ev;
        this.univPoly = up;
        this.univFactors = uf;
        this.ldcfFactors = lf;
        this.ldcfEval = le;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TrialParts[");
        sb.append("evalPoints = " + this.evalPoints);
        sb.append(", univPoly = " + this.univPoly);
        sb.append(", univFactors = " + this.univFactors);
        sb.append(", ldcfEval = " + this.ldcfEval);
        sb.append(", ldcfFactors = " + this.ldcfFactors);
        sb.append("]");
        return sb.toString();
    }
}
