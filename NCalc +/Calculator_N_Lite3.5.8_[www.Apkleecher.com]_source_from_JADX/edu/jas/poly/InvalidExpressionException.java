package edu.jas.poly;

public class InvalidExpressionException extends RuntimeException {
    public InvalidExpressionException() {
        super("InvalidExpressionException");
    }

    public InvalidExpressionException(String c) {
        super(c);
    }

    public InvalidExpressionException(String c, Throwable t) {
        super(c, t);
    }

    public InvalidExpressionException(Throwable t) {
        super("InvalidExpressionException", t);
    }
}
