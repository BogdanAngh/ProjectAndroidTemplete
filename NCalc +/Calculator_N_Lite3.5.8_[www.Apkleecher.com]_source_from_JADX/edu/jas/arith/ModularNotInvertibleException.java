package edu.jas.arith;

import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;

public class ModularNotInvertibleException extends NotInvertibleException {
    public final GcdRingElem f;
    public final GcdRingElem f1;
    public final GcdRingElem f2;

    public ModularNotInvertibleException() {
        this(null, null, null);
    }

    public ModularNotInvertibleException(String c) {
        this(c, null, null, null);
    }

    public ModularNotInvertibleException(String c, Throwable t) {
        this(c, t, null, null, null);
    }

    public ModularNotInvertibleException(Throwable t) {
        this(t, null, null, null);
    }

    public ModularNotInvertibleException(GcdRingElem f, GcdRingElem f1, GcdRingElem f2) {
        super("ModularNotInvertibleException");
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public ModularNotInvertibleException(String c, GcdRingElem f, GcdRingElem f1, GcdRingElem f2) {
        super(c);
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public ModularNotInvertibleException(String c, Throwable t, GcdRingElem f, GcdRingElem f1, GcdRingElem f2) {
        super(c, t);
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public ModularNotInvertibleException(Throwable t, GcdRingElem f, GcdRingElem f1, GcdRingElem f2) {
        super("ModularNotInvertibleException", t);
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public String toString() {
        String s = super.toString();
        if (this.f == null && this.f1 == null && this.f2 == null) {
            return s;
        }
        return s + ", f = " + this.f + ", f1 = " + this.f1 + ", f2 = " + this.f2;
    }
}
