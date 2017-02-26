package edu.jas.root;

public class InvalidBoundaryException extends Exception {
    public InvalidBoundaryException() {
        super("InvalidBoundaryException");
    }

    public InvalidBoundaryException(String c) {
        super(c);
    }

    public InvalidBoundaryException(String c, Throwable t) {
        super(c, t);
    }

    public InvalidBoundaryException(Throwable t) {
        super("InvalidBoundaryException", t);
    }
}
