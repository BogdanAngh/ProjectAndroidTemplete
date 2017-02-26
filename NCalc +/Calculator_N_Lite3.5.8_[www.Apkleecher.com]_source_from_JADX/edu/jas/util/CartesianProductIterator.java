package edu.jas.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* compiled from: CartesianProduct */
class CartesianProductIterator<E> implements Iterator<List<E>> {
    final List<Iterator<E>> compit;
    final List<Iterable<E>> comps;
    List<E> current;
    boolean empty;

    public CartesianProductIterator(List<Iterable<E>> comps) {
        if (comps == null) {
            throw new IllegalArgumentException("null comps not allowed");
        }
        this.comps = comps;
        this.current = new ArrayList(comps.size());
        this.compit = new ArrayList(comps.size());
        this.empty = false;
        for (Iterable<E> ci : comps) {
            Iterator<E> it = ci.iterator();
            if (it.hasNext()) {
                this.current.add(it.next());
                this.compit.add(it);
            } else {
                this.empty = true;
                this.current.clear();
                return;
            }
        }
    }

    public synchronized boolean hasNext() {
        return !this.empty;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized java.util.List<E> next() {
        /*
        r7 = this;
        monitor-enter(r7);
        r5 = r7.empty;	 Catch:{ all -> 0x000d }
        if (r5 == 0) goto L_0x0010;
    L_0x0005:
        r5 = new java.util.NoSuchElementException;	 Catch:{ all -> 0x000d }
        r6 = "invalid call of next()";
        r5.<init>(r6);	 Catch:{ all -> 0x000d }
        throw r5;	 Catch:{ all -> 0x000d }
    L_0x000d:
        r5 = move-exception;
        monitor-exit(r7);
        throw r5;
    L_0x0010:
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x000d }
        r5 = r7.current;	 Catch:{ all -> 0x000d }
        r4.<init>(r5);	 Catch:{ all -> 0x000d }
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r5 = r5.size();	 Catch:{ all -> 0x000d }
        r1 = r5 + -1;
    L_0x001f:
        if (r1 < 0) goto L_0x002f;
    L_0x0021:
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r2 = r5.get(r1);	 Catch:{ all -> 0x000d }
        r2 = (java.util.Iterator) r2;	 Catch:{ all -> 0x000d }
        r5 = r2.hasNext();	 Catch:{ all -> 0x000d }
        if (r5 == 0) goto L_0x0036;
    L_0x002f:
        if (r1 >= 0) goto L_0x0039;
    L_0x0031:
        r5 = 1;
        r7.empty = r5;	 Catch:{ all -> 0x000d }
    L_0x0034:
        monitor-exit(r7);
        return r4;
    L_0x0036:
        r1 = r1 + -1;
        goto L_0x001f;
    L_0x0039:
        r3 = r1 + 1;
    L_0x003b:
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r5 = r5.size();	 Catch:{ all -> 0x000d }
        if (r3 >= r5) goto L_0x0057;
    L_0x0043:
        r5 = r7.comps;	 Catch:{ all -> 0x000d }
        r5 = r5.get(r3);	 Catch:{ all -> 0x000d }
        r5 = (java.lang.Iterable) r5;	 Catch:{ all -> 0x000d }
        r2 = r5.iterator();	 Catch:{ all -> 0x000d }
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r5.set(r3, r2);	 Catch:{ all -> 0x000d }
        r3 = r3 + 1;
        goto L_0x003b;
    L_0x0057:
        r3 = r1;
    L_0x0058:
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r5 = r5.size();	 Catch:{ all -> 0x000d }
        if (r3 >= r5) goto L_0x0034;
    L_0x0060:
        r5 = r7.compit;	 Catch:{ all -> 0x000d }
        r2 = r5.get(r3);	 Catch:{ all -> 0x000d }
        r2 = (java.util.Iterator) r2;	 Catch:{ all -> 0x000d }
        r0 = r2.next();	 Catch:{ all -> 0x000d }
        r5 = r7.current;	 Catch:{ all -> 0x000d }
        r5.set(r3, r0);	 Catch:{ all -> 0x000d }
        r3 = r3 + 1;
        goto L_0x0058;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.util.CartesianProductIterator.next():java.util.List<E>");
    }

    public void remove() {
        throw new UnsupportedOperationException("cannnot remove tuples");
    }
}
