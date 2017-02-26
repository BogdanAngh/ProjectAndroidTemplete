package edu.jas.gb;

import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: GroebnerBaseParallel */
class Reducer<C extends RingElem<C>> implements Runnable {
    private static final Logger logger;
    private final List<GenPolynomial<C>> G;
    private final Terminator fin;
    private final PairList<C> pairlist;
    private final ReductionPar<C> red;

    static {
        logger = Logger.getLogger(Reducer.class);
    }

    Reducer(Terminator fin, List<GenPolynomial<C>> G, PairList<C> L) {
        this.fin = fin;
        this.fin.initIdle(1);
        this.G = G;
        this.pairlist = L;
        this.red = new ReductionPar();
    }

    public String toString() {
        return "Reducer";
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r12 = this;
        r11 = 1;
        r6 = 0;
        r7 = 0;
    L_0x0003:
        r8 = r12.pairlist;
        r8 = r8.hasNext();
        if (r8 != 0) goto L_0x0013;
    L_0x000b:
        r8 = r12.fin;
        r8 = r8.hasJobs();
        if (r8 == 0) goto L_0x0066;
    L_0x0013:
        r8 = r12.pairlist;
        r8 = r8.hasNext();
        if (r8 != 0) goto L_0x0056;
    L_0x001b:
        r7 = r7 + 1;
        r8 = r7 % 10;
        if (r8 != 0) goto L_0x008a;
    L_0x0021:
        r8 = logger;	 Catch:{ InterruptedException -> 0x0092 }
        r9 = " reducer is sleeping";
        r8.info(r9);	 Catch:{ InterruptedException -> 0x0092 }
    L_0x0028:
        r8 = 100;
        java.lang.Thread.sleep(r8);	 Catch:{ InterruptedException -> 0x0092 }
        r8 = java.lang.Thread.currentThread();
        r8 = r8.isInterrupted();
        if (r8 == 0) goto L_0x00bd;
    L_0x0037:
        r8 = r12.fin;
        r8.allIdle();
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "shutdown after .isInterrupted(): ";
        r9 = r9.append(r10);
        r10 = r12.fin;
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.info(r9);
    L_0x0056:
        r8 = r12.pairlist;
        r8 = r8.hasNext();
        if (r8 != 0) goto L_0x00c6;
    L_0x005e:
        r8 = r12.fin;
        r8 = r8.hasJobs();
        if (r8 != 0) goto L_0x00c6;
    L_0x0066:
        r8 = r12.fin;
        r8.allIdle();
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "terminated, done ";
        r9 = r9.append(r10);
        r9 = r9.append(r6);
        r10 = " reductions";
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.info(r9);
    L_0x0089:
        return;
    L_0x008a:
        r8 = logger;	 Catch:{ InterruptedException -> 0x0092 }
        r9 = "r";
        r8.debug(r9);	 Catch:{ InterruptedException -> 0x0092 }
        goto L_0x0028;
    L_0x0092:
        r2 = move-exception;
        r8 = r12.fin;
        r8.allIdle();
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "shutdown ";
        r9 = r9.append(r10);
        r10 = r12.fin;
        r9 = r9.append(r10);
        r10 = " after: ";
        r9 = r9.append(r10);
        r9 = r9.append(r2);
        r9 = r9.toString();
        r8.info(r9);
        goto L_0x0056;
    L_0x00bd:
        r8 = r12.fin;
        r8 = r8.hasJobs();
        if (r8 != 0) goto L_0x0013;
    L_0x00c5:
        goto L_0x0056;
    L_0x00c6:
        r8 = r12.fin;
        r8.notIdle();
        r8 = r12.pairlist;
        r3 = r8.removeNext();
        r8 = java.lang.Thread.currentThread();
        r8 = r8.isInterrupted();
        if (r8 == 0) goto L_0x00e8;
    L_0x00db:
        r8 = r12.fin;
        r8.initIdle(r11);
        r8 = new java.lang.RuntimeException;
        r9 = "interrupt after removeNext";
        r8.<init>(r9);
        throw r8;
    L_0x00e8:
        if (r3 != 0) goto L_0x00f1;
    L_0x00ea:
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x00f1:
        r4 = r3.pi;
        r5 = r3.pj;
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x012d;
    L_0x00fd:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "pi    = ";
        r9 = r9.append(r10);
        r9 = r9.append(r4);
        r9 = r9.toString();
        r8.debug(r9);
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "pj    = ";
        r9 = r9.append(r10);
        r9 = r9.append(r5);
        r9 = r9.toString();
        r8.debug(r9);
    L_0x012d:
        r8 = r12.red;
        r1 = r8.SPolynomial(r4, r5);
        r8 = r1.isZERO();
        if (r8 == 0) goto L_0x0143;
    L_0x0139:
        r3.setZero();
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x0143:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x0167;
    L_0x014b:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "ht(S) = ";
        r9 = r9.append(r10);
        r10 = r1.leadingExpVector();
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.debug(r9);
    L_0x0167:
        r8 = r12.red;
        r9 = r12.G;
        r0 = r8.normalform(r9, r1);
        r6 = r6 + 1;
        r8 = r0.isZERO();
        if (r8 == 0) goto L_0x0181;
    L_0x0177:
        r3.setZero();
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x0181:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x01a5;
    L_0x0189:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "ht(H) = ";
        r9 = r9.append(r10);
        r10 = r0.leadingExpVector();
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.info(r9);
    L_0x01a5:
        r0 = r0.monic();
        r8 = r0.isONE();
        if (r8 == 0) goto L_0x01cc;
    L_0x01af:
        r8 = r12.pairlist;
        r8.put(r0);
        r9 = r12.G;
        monitor-enter(r9);
        r8 = r12.G;	 Catch:{ all -> 0x01c9 }
        r8.clear();	 Catch:{ all -> 0x01c9 }
        r8 = r12.G;	 Catch:{ all -> 0x01c9 }
        r8.add(r0);	 Catch:{ all -> 0x01c9 }
        monitor-exit(r9);	 Catch:{ all -> 0x01c9 }
        r8 = r12.fin;
        r8.allIdle();
        goto L_0x0089;
    L_0x01c9:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x01c9 }
        throw r8;
    L_0x01cc:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x01ec;
    L_0x01d4:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "H = ";
        r9 = r9.append(r10);
        r9 = r9.append(r0);
        r9 = r9.toString();
        r8.debug(r9);
    L_0x01ec:
        r9 = r12.G;
        monitor-enter(r9);
        r8 = r12.G;	 Catch:{ all -> 0x0201 }
        r8.add(r0);	 Catch:{ all -> 0x0201 }
        monitor-exit(r9);	 Catch:{ all -> 0x0201 }
        r8 = r12.pairlist;
        r8.put(r0);
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x0201:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x0201 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.Reducer.run():void");
    }
}
