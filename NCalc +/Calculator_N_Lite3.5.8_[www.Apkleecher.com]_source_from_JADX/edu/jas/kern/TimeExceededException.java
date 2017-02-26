package edu.jas.kern;

public class TimeExceededException extends RuntimeException {
    public TimeExceededException() {
        super("TimeExceededException");
    }

    public TimeExceededException(String c) {
        super(c);
    }

    public TimeExceededException(String c, Throwable t) {
        super(c, t);
    }

    public TimeExceededException(Throwable t) {
        super("TimeExceededException", t);
    }
}
