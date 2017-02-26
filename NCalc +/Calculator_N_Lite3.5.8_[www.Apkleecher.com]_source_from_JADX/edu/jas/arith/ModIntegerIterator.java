package edu.jas.arith;

import java.math.BigInteger;
import java.util.Iterator;

/* compiled from: ModIntegerRing */
class ModIntegerIterator implements Iterator<ModInteger> {
    BigInteger curr;
    final ModIntegerRing ring;

    public ModIntegerIterator(ModIntegerRing fac) {
        this.curr = BigInteger.ZERO;
        this.ring = fac;
    }

    public synchronized boolean hasNext() {
        return this.curr.compareTo(this.ring.modul) < 0;
    }

    public synchronized ModInteger next() {
        ModInteger i;
        i = new ModInteger(this.ring, this.curr);
        this.curr = this.curr.add(BigInteger.ONE);
        return i;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
