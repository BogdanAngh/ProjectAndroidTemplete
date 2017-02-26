package edu.jas.ps;

import edu.jas.poly.ExpVector;
import java.util.Iterator;

public class ExpVectorIterable implements Iterable<ExpVector> {
    final boolean infinite;
    final int nvar;
    protected long upperBound;

    public ExpVectorIterable(int nv) {
        this(nv, true, Long.MAX_VALUE);
    }

    public ExpVectorIterable(int nv, long ub) {
        this(nv, false, ub);
    }

    public ExpVectorIterable(int nv, boolean all, long ub) {
        this.upperBound = ub;
        this.infinite = all;
        this.nvar = nv;
    }

    public void setUpperBound(long ub) {
        this.upperBound = ub;
    }

    public long getUpperBound() {
        return this.upperBound;
    }

    public Iterator<ExpVector> iterator() {
        return new ExpVectorIterator(this.nvar, this.infinite, this.upperBound);
    }
}
