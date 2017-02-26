package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.util.CartesianProductLong;
import edu.jas.util.LongIterable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/* compiled from: ExpVectorIterable */
class ExpVectorIterator implements Iterator<ExpVector> {
    ExpVector current;
    protected boolean empty;
    final boolean infinite;
    Iterator<List<Long>> liter;
    final int nvar;
    protected int totalDegree;
    final long upperBound;

    public ExpVectorIterator(int nv) {
        this(nv, true, Long.MAX_VALUE);
    }

    public ExpVectorIterator(int nv, long ub) {
        this(nv, false, ub);
    }

    protected ExpVectorIterator(int nv, boolean inf, long ub) {
        boolean z = false;
        this.infinite = inf;
        this.upperBound = ub;
        if (this.upperBound < 0) {
            throw new IllegalArgumentException("negative upper bound not allowed");
        }
        this.totalDegree = 0;
        if (!this.infinite) {
            this.totalDegree = (int) this.upperBound;
        }
        LongIterable li = new LongIterable();
        li.setNonNegativeIterator();
        li.setUpperBound((long) this.totalDegree);
        List<LongIterable> tlist = new ArrayList(nv);
        for (int i = 0; i < nv; i++) {
            tlist.add(li);
        }
        this.liter = new CartesianProductLong(tlist, (long) this.totalDegree).iterator();
        if (((long) this.totalDegree) > this.upperBound || !this.liter.hasNext()) {
            z = true;
        }
        this.empty = z;
        this.current = ExpVector.create(nv);
        if (!this.empty) {
            this.current = ExpVector.create((List) this.liter.next());
        }
        this.nvar = nv;
    }

    public synchronized boolean hasNext() {
        return !this.empty;
    }

    public synchronized ExpVector next() {
        ExpVector res;
        res = this.current;
        if (this.liter.hasNext()) {
            this.current = ExpVector.create((List) this.liter.next());
        } else if (((long) this.totalDegree) >= this.upperBound) {
            this.empty = true;
        } else {
            this.totalDegree++;
            if (((long) this.totalDegree) < this.upperBound || this.infinite) {
                LongIterable li = new LongIterable();
                li.setNonNegativeIterator();
                li.setUpperBound((long) this.totalDegree);
                List<LongIterable> tlist = new ArrayList(this.nvar);
                for (int i = 0; i < this.nvar; i++) {
                    tlist.add(li);
                }
                this.liter = new CartesianProductLong(tlist, (long) this.totalDegree).iterator();
                this.current = ExpVector.create((List) this.liter.next());
            } else {
                throw new NoSuchElementException("invalid call of next()");
            }
        }
        return res;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove elements");
    }
}
