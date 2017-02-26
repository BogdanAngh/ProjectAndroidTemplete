package edu.jas.poly;

import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.io.Serializable;

public class TableRelation<C extends RingElem<C>> implements Serializable {
    public final ExpVector e;
    public final ExpVector f;
    public final GenSolvablePolynomial<C> p;

    public TableRelation(ExpVector e, ExpVector f, GenSolvablePolynomial<C> p) {
        this.e = e;
        this.f = f;
        this.p = p;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("TableRelation[");
        s.append(BuildConfig.FLAVOR + this.e);
        s.append(" .*. ");
        s.append(BuildConfig.FLAVOR + this.f);
        s.append(" = ");
        s.append(BuildConfig.FLAVOR + this.p);
        s.append("]");
        return s.toString();
    }
}
