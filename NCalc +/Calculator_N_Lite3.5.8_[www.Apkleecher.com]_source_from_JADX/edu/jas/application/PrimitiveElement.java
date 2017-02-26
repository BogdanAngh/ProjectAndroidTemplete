package edu.jas.application;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;

public class PrimitiveElement<C extends GcdRingElem<C>> implements Serializable {
    public final AlgebraicNumber<C> A;
    public final AlgebraicNumberRing<C> Aring;
    public final AlgebraicNumber<C> B;
    public final AlgebraicNumberRing<C> Bring;
    public final AlgebraicNumberRing<C> primitiveElem;

    protected PrimitiveElement() {
        throw new IllegalArgumentException("do not use this constructor");
    }

    protected PrimitiveElement(AlgebraicNumberRing<C> pe, AlgebraicNumber<C> A, AlgebraicNumber<C> B) {
        this(pe, A, B, null, null);
    }

    protected PrimitiveElement(AlgebraicNumberRing<C> pe, AlgebraicNumber<C> A, AlgebraicNumber<C> B, AlgebraicNumberRing<C> ar, AlgebraicNumberRing<C> br) {
        this.primitiveElem = pe;
        this.A = A;
        this.B = B;
        this.Aring = ar;
        this.Bring = br;
    }

    public String toString() {
        StringBuffer s = new StringBuffer("[");
        s.append(this.primitiveElem.toString());
        s.append(", " + this.A.toString());
        s.append(", " + this.B.toString());
        if (this.Aring != null) {
            s.append(", " + this.Aring.toString());
        }
        if (this.Bring != null) {
            s.append(", " + this.Bring.toString());
        }
        return s + "]";
    }

    public String toScript() {
        StringBuffer s = new StringBuffer("(");
        s.append(this.primitiveElem.toScript());
        s.append(", " + this.A.toScript());
        s.append(", " + this.B.toScript());
        if (this.Aring != null) {
            s.append(", " + this.Aring.toScript());
        }
        if (this.Bring != null) {
            s.append(", " + this.Bring.toScript());
        }
        return s + ")";
    }
}
