package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.Modular;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;

public class HenselApprox<MOD extends GcdRingElem<MOD> & Modular> implements Serializable {
    public final GenPolynomial<BigInteger> A;
    public final GenPolynomial<MOD> Am;
    public final GenPolynomial<BigInteger> B;
    public final GenPolynomial<MOD> Bm;

    public HenselApprox(GenPolynomial<BigInteger> A, GenPolynomial<BigInteger> B, GenPolynomial<MOD> Am, GenPolynomial<MOD> Bm) {
        this.A = A;
        this.B = B;
        this.Am = Am;
        this.Bm = Bm;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.A.toString());
        sb.append(",");
        sb.append(this.B.toString());
        sb.append(",");
        sb.append(this.Am.toString());
        sb.append(",");
        sb.append(this.Bm.toString());
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.A.toScript());
        sb.append(",");
        sb.append(this.B.toScript());
        sb.append(",");
        sb.append(this.Am.toScript());
        sb.append(",");
        sb.append(this.Bm.toScript());
        return sb.toString();
    }

    public int hashCode() {
        return (((((this.A.hashCode() * 37) + this.B.hashCode()) * 37) + this.Am.hashCode()) * 37) + this.Bm.hashCode();
    }

    public boolean equals(Object B) {
        if (B == null || !(B instanceof HenselApprox)) {
            return false;
        }
        HenselApprox<MOD> a = (HenselApprox) B;
        if (this.A.equals(a.A) && B.equals(a.B) && this.Am.equals(a.Am) && this.Bm.equals(a.Bm)) {
            return true;
        }
        return false;
    }

    public BigInteger approximationSize() {
        return this.Am.ring.coFac.getIntegerModul();
    }
}
