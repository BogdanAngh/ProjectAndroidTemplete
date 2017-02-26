package edu.jas.arith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* compiled from: BigRational */
class BigRationalIterator implements Iterator<BigRational> {
    BigRational curr;
    BigInteger den;
    Iterator<BigInteger> denit;
    List<BigInteger> denlist;
    Iterator<BigInteger> denlistit;
    protected long level;
    final boolean nonNegative;
    BigInteger num;
    Iterator<BigInteger> numit;
    List<BigInteger> numlist;
    Iterator<BigInteger> numlistit;

    public BigRationalIterator() {
        this(false);
    }

    public BigRationalIterator(boolean nn) {
        this.nonNegative = nn;
        this.curr = BigRational.ZERO;
        this.level = 0;
        this.den = new BigInteger();
        this.num = BigInteger.ONE.copy();
        if (this.nonNegative) {
            this.den.setNonNegativeIterator();
        } else {
            this.den.setAllIterator();
        }
        this.num.setNonNegativeIterator();
        this.denit = this.den.iterator();
        this.numit = this.num.iterator();
        this.denlist = new ArrayList();
        this.numlist = new ArrayList();
        BigInteger unused = (BigInteger) this.denit.next();
        if (((BigInteger) this.numit.next()) == null) {
            System.out.println("unused is null");
        }
        this.denlist.add(this.denit.next());
        this.numlist.add(this.numit.next());
        this.denlistit = this.denlist.iterator();
        this.numlistit = this.numlist.iterator();
    }

    public boolean hasNext() {
        return true;
    }

    public synchronized BigRational next() {
        BigRational r;
        r = this.curr;
        if (this.denlistit.hasNext() && this.numlistit.hasNext()) {
            this.curr = BigRational.reduction(((BigInteger) this.denlistit.next()).val, ((BigInteger) this.numlistit.next()).val);
        } else {
            this.level++;
            if (this.level % 2 == 1) {
                Collections.reverse(this.denlist);
            } else {
                Collections.reverse(this.numlist);
            }
            this.denlist.add(this.denit.next());
            this.numlist.add(this.numit.next());
            if (this.level % 2 == 0) {
                Collections.reverse(this.denlist);
            } else {
                Collections.reverse(this.numlist);
            }
            this.denlistit = this.denlist.iterator();
            this.numlistit = this.numlist.iterator();
            this.curr = BigRational.reduction(((BigInteger) this.denlistit.next()).val, ((BigInteger) this.numlistit.next()).val);
        }
        return r;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
