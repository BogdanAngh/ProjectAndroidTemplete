package edu.jas.util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/* compiled from: LongIterable */
class LongIterator implements Iterator<Long> {
    long current;
    boolean empty;
    final boolean nonNegative;
    protected long upperBound;

    public void setUpperBound(long ub) {
        this.upperBound = ub;
    }

    public long getUpperBound() {
        return this.upperBound;
    }

    public LongIterator() {
        this(false, Long.MAX_VALUE);
    }

    public LongIterator(boolean nn, long ub) {
        this.current = 0;
        this.empty = false;
        this.nonNegative = nn;
        this.upperBound = ub;
    }

    public synchronized boolean hasNext() {
        return !this.empty;
    }

    public synchronized Long next() {
        Long res;
        if (this.empty) {
            throw new NoSuchElementException("invalid call of next()");
        }
        res = Long.valueOf(this.current);
        if (this.nonNegative) {
            this.current++;
        } else if (this.current > 0) {
            this.current = -this.current;
        } else {
            this.current = -this.current;
            this.current++;
        }
        if (this.current > this.upperBound) {
            this.empty = true;
        }
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
