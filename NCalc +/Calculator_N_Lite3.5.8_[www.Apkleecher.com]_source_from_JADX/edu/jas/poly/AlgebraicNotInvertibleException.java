package edu.jas.poly;

import edu.jas.structure.NotInvertibleException;

public class AlgebraicNotInvertibleException extends NotInvertibleException {
    public final GenPolynomial f;
    public final GenPolynomial f1;
    public final GenPolynomial f2;

    public AlgebraicNotInvertibleException() {
        this(null, null, null);
    }

    public AlgebraicNotInvertibleException(String c) {
        this(c, null, null, null);
    }

    public AlgebraicNotInvertibleException(String c, Throwable t) {
        this(c, t, null, null, null);
    }

    public AlgebraicNotInvertibleException(Throwable t) {
        this(t, null, null, null);
    }

    public AlgebraicNotInvertibleException(GenPolynomial f, GenPolynomial f1, GenPolynomial f2) {
        super("AlgebraicNotInvertibleException");
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public AlgebraicNotInvertibleException(String c, GenPolynomial f, GenPolynomial f1, GenPolynomial f2) {
        super(c);
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public AlgebraicNotInvertibleException(String c, Throwable t, GenPolynomial f, GenPolynomial f1, GenPolynomial f2) {
        super(c, t);
        this.f = f;
        this.f1 = f1;
        this.f2 = f2;
    }

    public AlgebraicNotInvertibleException(Throwable t, GenPolynomial f, GenPolynomial f1, GenPolynomial f2) {
        super("AlgebraicNotInvertibleException", t);
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
