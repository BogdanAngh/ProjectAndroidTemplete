package edu.jas.structure;

public class NotInvertibleException extends RuntimeException {
    public NotInvertibleException() {
        super("NotInvertibleException");
    }

    public NotInvertibleException(String c) {
        super(c);
    }

    public NotInvertibleException(String c, Throwable t) {
        super(c, t);
    }

    public NotInvertibleException(Throwable t) {
        super("NotInvertibleException", t);
    }
}
