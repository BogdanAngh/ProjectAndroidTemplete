package edu.jas.kern;

public class PreemptingException extends RuntimeException {
    public PreemptingException() {
        super("PreemptingException");
    }

    public PreemptingException(String c) {
        super(c);
    }

    public PreemptingException(String c, Throwable t) {
        super(c, t);
    }

    public PreemptingException(Throwable t) {
        super("PreemptingException", t);
    }
}
