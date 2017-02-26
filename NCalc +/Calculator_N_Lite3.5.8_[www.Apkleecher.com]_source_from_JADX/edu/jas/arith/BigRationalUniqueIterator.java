package edu.jas.arith;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* compiled from: BigRational */
class BigRationalUniqueIterator implements Iterator<BigRational> {
    final Iterator<BigRational> ratit;
    final Set<BigRational> unique;

    public BigRationalUniqueIterator() {
        this(BigRational.ONE.iterator());
    }

    public BigRationalUniqueIterator(Iterator<BigRational> rit) {
        this.ratit = rit;
        this.unique = new HashSet();
    }

    public synchronized boolean hasNext() {
        return this.ratit.hasNext();
    }

    public synchronized BigRational next() {
        BigRational r;
        r = (BigRational) this.ratit.next();
        while (this.unique.contains(r)) {
            r = (BigRational) this.ratit.next();
        }
        this.unique.add(r);
        return r;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
