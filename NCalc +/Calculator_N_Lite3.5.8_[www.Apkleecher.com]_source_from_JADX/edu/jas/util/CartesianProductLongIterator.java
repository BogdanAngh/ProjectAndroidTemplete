package edu.jas.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CartesianProductLong */
class CartesianProductLongIterator implements Iterator<List<Long>> {
    final List<LongIterator> compit;
    final List<LongIterable> comps;
    List<Long> current;
    boolean empty;
    public final long upperBound;

    public CartesianProductLongIterator(List<LongIterable> comps, long ub) {
        if (comps == null) {
            throw new IllegalArgumentException("null comps not allowed");
        }
        LongIterator it;
        this.comps = comps;
        this.upperBound = ub;
        this.current = new ArrayList(comps.size());
        this.compit = new ArrayList(comps.size());
        this.empty = false;
        for (LongIterable ci : comps) {
            it = (LongIterator) ci.iterator();
            if (it.getUpperBound() < this.upperBound) {
                throw new IllegalArgumentException("each iterator (" + it.getUpperBound() + ") must be able to reach total upper bound " + this.upperBound);
            } else if (it.hasNext()) {
                this.current.add(it.next());
                this.compit.add(it);
            } else {
                this.empty = true;
                this.current.clear();
                return;
            }
        }
        it = (LongIterator) this.compit.get(this.compit.size() - 1);
        long d = -1;
        while (it.hasNext()) {
            d = it.next().longValue();
            if (d >= this.upperBound) {
                break;
            }
        }
        if (d >= 0) {
            this.current.set(this.current.size() - 1, Long.valueOf(d));
        }
        if (totalDegree(this.current) != this.upperBound) {
            this.empty = true;
            this.current.clear();
        }
    }

    public synchronized boolean hasNext() {
        return !this.empty;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<java.lang.Long> next() {
        /*
        r20 = this;
        monitor-enter(r20);
        r0 = r20;
        r0 = r0.empty;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        if (r16 == 0) goto L_0x0014;
    L_0x0009:
        r16 = new java.util.NoSuchElementException;	 Catch:{ all -> 0x0011 }
        r17 = "invalid call of next()";
        r16.<init>(r17);	 Catch:{ all -> 0x0011 }
        throw r16;	 Catch:{ all -> 0x0011 }
    L_0x0011:
        r16 = move-exception;
        monitor-exit(r20);
        throw r16;
    L_0x0014:
        r9 = new java.util.ArrayList;	 Catch:{ all -> 0x0011 }
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r9.<init>(r0);	 Catch:{ all -> 0x0011 }
    L_0x0021:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = r16.size();	 Catch:{ all -> 0x0011 }
        r5 = r16 + -1;
    L_0x002d:
        if (r5 < 0) goto L_0x0043;
    L_0x002f:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r7 = r0.get(r5);	 Catch:{ all -> 0x0011 }
        r7 = (edu.jas.util.LongIterator) r7;	 Catch:{ all -> 0x0011 }
        r16 = r7.hasNext();	 Catch:{ all -> 0x0011 }
        if (r16 == 0) goto L_0x004f;
    L_0x0043:
        if (r5 >= 0) goto L_0x0052;
    L_0x0045:
        r16 = 1;
        r0 = r16;
        r1 = r20;
        r1.empty = r0;	 Catch:{ all -> 0x0011 }
    L_0x004d:
        monitor-exit(r20);
        return r9;
    L_0x004f:
        r5 = r5 + -1;
        goto L_0x002d;
    L_0x0052:
        r10 = 0;
        r8 = 0;
    L_0x0055:
        if (r8 >= r5) goto L_0x006e;
    L_0x0057:
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r16 = r0.get(r8);	 Catch:{ all -> 0x0011 }
        r16 = (java.lang.Long) r16;	 Catch:{ all -> 0x0011 }
        r16 = r16.longValue();	 Catch:{ all -> 0x0011 }
        r10 = r10 + r16;
        r8 = r8 + 1;
        goto L_0x0055;
    L_0x006e:
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = (r10 > r16 ? 1 : (r10 == r16 ? 0 : -1));
        if (r16 < 0) goto L_0x00a1;
    L_0x0078:
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r17 = 0;
        r16 = r16.get(r17);	 Catch:{ all -> 0x0011 }
        r16 = (java.lang.Long) r16;	 Catch:{ all -> 0x0011 }
        r16 = r16.longValue();	 Catch:{ all -> 0x0011 }
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r18 = r0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 != 0) goto L_0x009d;
    L_0x0094:
        r16 = 1;
        r0 = r16;
        r1 = r20;
        r1.empty = r0;	 Catch:{ all -> 0x0011 }
        goto L_0x004d;
    L_0x009d:
        r0 = r20;
        r10 = r0.upperBound;	 Catch:{ all -> 0x0011 }
    L_0x00a1:
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r12 = r16 - r10;
        r8 = r5 + 1;
    L_0x00ab:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = r16.size();	 Catch:{ all -> 0x0011 }
        r0 = r16;
        if (r8 >= r0) goto L_0x00de;
    L_0x00b9:
        r0 = r20;
        r0 = r0.comps;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r16 = r0.get(r8);	 Catch:{ all -> 0x0011 }
        r16 = (edu.jas.util.LongIterable) r16;	 Catch:{ all -> 0x0011 }
        r7 = r16.iterator();	 Catch:{ all -> 0x0011 }
        r7 = (edu.jas.util.LongIterator) r7;	 Catch:{ all -> 0x0011 }
        r7.setUpperBound(r12);	 Catch:{ all -> 0x0011 }
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r0.set(r8, r7);	 Catch:{ all -> 0x0011 }
        r8 = r8 + 1;
        goto L_0x00ab;
    L_0x00de:
        r8 = r5;
    L_0x00df:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = r16.size();	 Catch:{ all -> 0x0011 }
        r0 = r16;
        if (r8 >= r0) goto L_0x010d;
    L_0x00ed:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r7 = r0.get(r8);	 Catch:{ all -> 0x0011 }
        r7 = (edu.jas.util.LongIterator) r7;	 Catch:{ all -> 0x0011 }
        r4 = r7.next();	 Catch:{ all -> 0x0011 }
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r16;
        r0.set(r8, r4);	 Catch:{ all -> 0x0011 }
        r8 = r8 + 1;
        goto L_0x00df;
    L_0x010d:
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r20;
        r1 = r16;
        r14 = r0.totalDegree(r1);	 Catch:{ all -> 0x0011 }
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 == 0) goto L_0x004d;
    L_0x0125:
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 > 0) goto L_0x0021;
    L_0x012f:
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r20;
        r0 = r0.compit;	 Catch:{ all -> 0x0011 }
        r17 = r0;
        r17 = r17.size();	 Catch:{ all -> 0x0011 }
        r17 = r17 + -1;
        r6 = r16.get(r17);	 Catch:{ all -> 0x0011 }
        r6 = (edu.jas.util.LongIterator) r6;	 Catch:{ all -> 0x0011 }
        r2 = -1;
    L_0x0149:
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 >= 0) goto L_0x0166;
    L_0x0153:
        r16 = r6.hasNext();	 Catch:{ all -> 0x0011 }
        if (r16 == 0) goto L_0x0166;
    L_0x0159:
        r16 = 1;
        r14 = r14 + r16;
        r16 = r6.next();	 Catch:{ all -> 0x0011 }
        r2 = r16.longValue();	 Catch:{ all -> 0x0011 }
        goto L_0x0149;
    L_0x0166:
        r16 = 0;
        r16 = (r2 > r16 ? 1 : (r2 == r16 ? 0 : -1));
        if (r16 < 0) goto L_0x0185;
    L_0x016c:
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r0 = r20;
        r0 = r0.current;	 Catch:{ all -> 0x0011 }
        r17 = r0;
        r17 = r17.size();	 Catch:{ all -> 0x0011 }
        r17 = r17 + -1;
        r18 = java.lang.Long.valueOf(r2);	 Catch:{ all -> 0x0011 }
        r16.set(r17, r18);	 Catch:{ all -> 0x0011 }
    L_0x0185:
        r0 = r20;
        r0 = r0.upperBound;	 Catch:{ all -> 0x0011 }
        r16 = r0;
        r16 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r16 != 0) goto L_0x0021;
    L_0x018f:
        goto L_0x004d;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.util.CartesianProductLongIterator.next():java.util.List<java.lang.Long>");
    }

    public long totalDegree(List<Long> e) {
        long d = 0;
        for (Long i : e) {
            d += i.longValue();
        }
        return d;
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove tuples");
    }
}
