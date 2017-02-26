package edu.jas.arith;

import java.math.BigInteger;
import java.util.Iterator;

/* compiled from: BigInteger */
class BigIntegerIterator implements Iterator<BigInteger> {
    BigInteger curr;
    final boolean nonNegative;

    public BigIntegerIterator() {
        this(false);
    }

    public BigIntegerIterator(boolean nn) {
        this.curr = BigInteger.ZERO;
        this.nonNegative = nn;
    }

    public boolean hasNext() {
        return true;
    }

    public synchronized BigInteger next() {
        BigInteger i;
        i = new BigInteger(this.curr);
        if (this.nonNegative) {
            this.curr = this.curr.add(BigInteger.ONE);
        } else if (this.curr.signum() <= 0 || this.nonNegative) {
            this.curr = this.curr.negate().add(BigInteger.ONE);
        } else {
            this.curr = this.curr.negate();
        }
        return i;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
