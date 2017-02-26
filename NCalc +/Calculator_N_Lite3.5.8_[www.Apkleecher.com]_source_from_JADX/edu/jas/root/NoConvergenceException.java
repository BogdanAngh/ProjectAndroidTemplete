package edu.jas.root;

public class NoConvergenceException extends Exception {
    public NoConvergenceException() {
        super("NoConvergenceException");
    }

    public NoConvergenceException(String c) {
        super(c);
    }

    public NoConvergenceException(String c, Throwable t) {
        super(c, t);
    }

    public NoConvergenceException(Throwable t) {
        super("NoConvergenceException", t);
    }
}
