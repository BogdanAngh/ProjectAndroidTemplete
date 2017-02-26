package edu.jas.util;

import java.util.Iterator;

public class LongIterable implements Iterable<Long> {
    private boolean nonNegative;
    private long upperBound;

    public LongIterable() {
        this.nonNegative = true;
        this.upperBound = Long.MAX_VALUE;
    }

    public LongIterable(long ub) {
        this.nonNegative = true;
        this.upperBound = Long.MAX_VALUE;
        this.upperBound = ub;
    }

    public void setUpperBound(long ub) {
        this.upperBound = ub;
    }

    public long getUpperBound() {
        return this.upperBound;
    }

    public void setAllIterator() {
        this.nonNegative = false;
    }

    public void setNonNegativeIterator() {
        this.nonNegative = true;
    }

    public Iterator<Long> iterator() {
        return new LongIterator(this.nonNegative, this.upperBound);
    }
}
