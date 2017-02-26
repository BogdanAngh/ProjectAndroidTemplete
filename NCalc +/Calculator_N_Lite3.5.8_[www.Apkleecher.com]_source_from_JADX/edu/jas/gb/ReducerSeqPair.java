package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: GroebnerBaseSeqPairParallel */
class ReducerSeqPair<C extends RingElem<C>> implements Runnable {
    private static final Logger logger;
    private final List<GenPolynomial<C>> G;
    private final Terminator fin;
    private final CriticalPairList<C> pairlist;
    private final ReductionPar<C> red;

    static {
        logger = Logger.getLogger(ReducerSeqPair.class);
    }

    ReducerSeqPair(Terminator fin, List<GenPolynomial<C>> G, CriticalPairList<C> L) {
        this.fin = fin;
        this.G = G;
        this.pairlist = L;
        this.red = new ReductionPar();
    }

    public String toString() {
        return "ReducerSeqPair";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r10 = this;
        r5 = 0;
        r4 = 0;
        r6 = 0;
    L_0x0003:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x0013;
    L_0x000b:
        r7 = r10.fin;
        r7 = r7.hasJobs();
        if (r7 == 0) goto L_0x0071;
    L_0x0013:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x0061;
    L_0x001b:
        r7 = r10.pairlist;
        r7.update();
        r7 = r10.fin;
        r7.beIdle();
        r5 = 1;
        r6 = r6 + 1;
        r7 = r6 % 10;
        if (r7 != 0) goto L_0x0090;
    L_0x002c:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0098 }
        r8 = " reducer is sleeping";
        r7.info(r8);	 Catch:{ InterruptedException -> 0x0098 }
    L_0x0033:
        r8 = 100;
        java.lang.Thread.sleep(r8);	 Catch:{ InterruptedException -> 0x0098 }
        r7 = java.lang.Thread.currentThread();
        r7 = r7.isInterrupted();
        if (r7 == 0) goto L_0x00c3;
    L_0x0042:
        r7 = r10.fin;
        r7.allIdle();
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "shutdown after .isInterrupted(): ";
        r8 = r8.append(r9);
        r9 = r10.fin;
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.info(r8);
    L_0x0061:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x00cc;
    L_0x0069:
        r7 = r10.fin;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x00cc;
    L_0x0071:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "terminated, done ";
        r8 = r8.append(r9);
        r8 = r8.append(r4);
        r9 = " reductions";
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.info(r8);
    L_0x008f:
        return;
    L_0x0090:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0098 }
        r8 = "r";
        r7.debug(r8);	 Catch:{ InterruptedException -> 0x0098 }
        goto L_0x0033;
    L_0x0098:
        r2 = move-exception;
        r7 = r10.fin;
        r7.allIdle();
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "shutdown ";
        r8 = r8.append(r9);
        r9 = r10.fin;
        r8 = r8.append(r9);
        r9 = " after: ";
        r8 = r8.append(r9);
        r8 = r8.append(r2);
        r8 = r8.toString();
        r7.info(r8);
        goto L_0x0061;
    L_0x00c3:
        r7 = r10.fin;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x0013;
    L_0x00cb:
        goto L_0x0061;
    L_0x00cc:
        if (r5 == 0) goto L_0x00d4;
    L_0x00ce:
        r7 = r10.fin;
        r7.notIdle();
        r5 = 0;
    L_0x00d4:
        r7 = r10.pairlist;
        r3 = r7.getNext();
        r7 = java.lang.Thread.currentThread();
        r7 = r7.isInterrupted();
        if (r7 == 0) goto L_0x00ec;
    L_0x00e4:
        r7 = new java.lang.RuntimeException;
        r8 = "interrupt after getNext";
        r7.<init>(r8);
        throw r7;
    L_0x00ec:
        if (r3 != 0) goto L_0x00f5;
    L_0x00ee:
        r7 = r10.pairlist;
        r7.update();
        goto L_0x0003;
    L_0x00f5:
        r7 = logger;
        r7 = r7.isDebugEnabled();
        if (r7 == 0) goto L_0x0131;
    L_0x00fd:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "pi = ";
        r8 = r8.append(r9);
        r9 = r3.pi;
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "pj = ";
        r8 = r8.append(r9);
        r9 = r3.pj;
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x0131:
        r7 = r10.red;
        r8 = r3.pi;
        r9 = r3.pj;
        r1 = r7.SPolynomial(r8, r9);
        r7 = r1.isZERO();
        if (r7 == 0) goto L_0x0148;
    L_0x0141:
        r7 = r10.pairlist;
        r7.record(r3, r1);
        goto L_0x0003;
    L_0x0148:
        r7 = logger;
        r7 = r7.isDebugEnabled();
        if (r7 == 0) goto L_0x016c;
    L_0x0150:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(S) = ";
        r8 = r8.append(r9);
        r9 = r1.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x016c:
        r7 = r10.red;
        r8 = r10.G;
        r0 = r7.normalform(r8, r1);
        r4 = r4 + 1;
        r7 = r0.isZERO();
        if (r7 == 0) goto L_0x0183;
    L_0x017c:
        r7 = r10.pairlist;
        r7.record(r3, r0);
        goto L_0x0003;
    L_0x0183:
        r7 = logger;
        r7 = r7.isDebugEnabled();
        if (r7 == 0) goto L_0x01a7;
    L_0x018b:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(H) = ";
        r8 = r8.append(r9);
        r9 = r0.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x01a7:
        r0 = r0.monic();
        r7 = r0.isONE();
        if (r7 == 0) goto L_0x01ce;
    L_0x01b1:
        r7 = r10.pairlist;
        r7.putOne();
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x01cb }
        r7.clear();	 Catch:{ all -> 0x01cb }
        r7 = r10.G;	 Catch:{ all -> 0x01cb }
        r7.add(r0);	 Catch:{ all -> 0x01cb }
        monitor-exit(r8);	 Catch:{ all -> 0x01cb }
        r7 = r10.fin;
        r7.allIdle();
        goto L_0x008f;
    L_0x01cb:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x01cb }
        throw r7;
    L_0x01ce:
        r7 = logger;
        r7 = r7.isDebugEnabled();
        if (r7 == 0) goto L_0x01ee;
    L_0x01d6:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "H = ";
        r8 = r8.append(r9);
        r8 = r8.append(r0);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x01ee:
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x01fe }
        r7.add(r0);	 Catch:{ all -> 0x01fe }
        monitor-exit(r8);	 Catch:{ all -> 0x01fe }
        r7 = r10.pairlist;
        r7.update(r3, r0);
        goto L_0x0003;
    L_0x01fe:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x01fe }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.ReducerSeqPair.run():void");
    }
}
