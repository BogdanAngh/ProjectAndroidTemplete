package edu.jas.util;

public final class StrategyEnumeration {
    public static final StrategyEnumeration FIFO;
    public static final StrategyEnumeration LIFO;

    static {
        FIFO = new StrategyEnumeration();
        LIFO = new StrategyEnumeration();
    }

    private StrategyEnumeration() {
    }

    public String toString() {
        if (this == FIFO) {
            return "FIFO strategy";
        }
        return "LIFO strategy";
    }
}
