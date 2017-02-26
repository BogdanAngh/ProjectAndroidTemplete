package edu.jas.ufd;

public class NoLiftingException extends Exception {
    public NoLiftingException() {
        super("NoLiftingException");
    }

    public NoLiftingException(String c) {
        super(c);
    }

    public NoLiftingException(String c, Throwable t) {
        super(c, t);
    }

    public NoLiftingException(Throwable t) {
        super("NoLiftingException", t);
    }
}
