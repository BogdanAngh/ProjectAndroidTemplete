package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: SolvableGroebnerBaseParallel */
class LeftSolvableReducer<C extends RingElem<C>> implements Runnable {
    private static final boolean debug;
    private static final Logger logger;
    private final List<GenSolvablePolynomial<C>> G;
    private final PairList<C> pairlist;
    private final Terminator pool;
    private final SolvableReductionPar<C> sred;

    static {
        logger = Logger.getLogger(LeftSolvableReducer.class);
        debug = logger.isDebugEnabled();
    }

    LeftSolvableReducer(Terminator fin, List<GenSolvablePolynomial<C>> G, PairList<C> L) {
        this.pool = fin;
        this.G = G;
        this.pairlist = L;
        this.sred = new SolvableReductionPar();
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
        r7 = r10.pool;
        r7 = r7.hasJobs();
        if (r7 == 0) goto L_0x006c;
    L_0x0013:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x005c;
    L_0x001b:
        r7 = r10.pool;
        r7.beIdle();
        r5 = 1;
        r6 = r6 + 1;
        r7 = r6 % 10;
        if (r7 != 0) goto L_0x008b;
    L_0x0027:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0093 }
        r8 = " reducer is sleeping";
        r7.info(r8);	 Catch:{ InterruptedException -> 0x0093 }
    L_0x002e:
        r8 = 100;
        java.lang.Thread.sleep(r8);	 Catch:{ InterruptedException -> 0x0093 }
        r7 = java.lang.Thread.currentThread();
        r7 = r7.isInterrupted();
        if (r7 == 0) goto L_0x00be;
    L_0x003d:
        r7 = r10.pool;
        r7.allIdle();
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "shutdown after .isInterrupted(): ";
        r8 = r8.append(r9);
        r9 = r10.pool;
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.info(r8);
    L_0x005c:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x00c7;
    L_0x0064:
        r7 = r10.pool;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x00c7;
    L_0x006c:
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
    L_0x008a:
        return;
    L_0x008b:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0093 }
        r8 = "r";
        r7.debug(r8);	 Catch:{ InterruptedException -> 0x0093 }
        goto L_0x002e;
    L_0x0093:
        r2 = move-exception;
        r7 = r10.pool;
        r7.allIdle();
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "shutdown ";
        r8 = r8.append(r9);
        r9 = r10.pool;
        r8 = r8.append(r9);
        r9 = " after: ";
        r8 = r8.append(r9);
        r8 = r8.append(r2);
        r8 = r8.toString();
        r7.info(r8);
        goto L_0x005c;
    L_0x00be:
        r7 = r10.pool;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x0013;
    L_0x00c6:
        goto L_0x005c;
    L_0x00c7:
        if (r5 == 0) goto L_0x00cf;
    L_0x00c9:
        r7 = r10.pool;
        r7.notIdle();
        r5 = 0;
    L_0x00cf:
        r7 = r10.pairlist;
        r3 = r7.removeNext();
        if (r3 == 0) goto L_0x0003;
    L_0x00d7:
        r7 = debug;
        if (r7 == 0) goto L_0x010f;
    L_0x00db:
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
    L_0x010f:
        r9 = r10.sred;
        r7 = r3.pi;
        r7 = (edu.jas.poly.GenSolvablePolynomial) r7;
        r8 = r3.pj;
        r8 = (edu.jas.poly.GenSolvablePolynomial) r8;
        r1 = r9.leftSPolynomial(r7, r8);
        r7 = r1.isZERO();
        if (r7 != 0) goto L_0x0003;
    L_0x0123:
        r7 = debug;
        if (r7 == 0) goto L_0x0143;
    L_0x0127:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(S) = ";
        r8 = r8.append(r9);
        r9 = r1.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x0143:
        r7 = r10.sred;
        r8 = r10.G;
        r0 = r7.leftNormalform(r8, r1);
        r4 = r4 + 1;
        r7 = r0.isZERO();
        if (r7 != 0) goto L_0x0003;
    L_0x0153:
        r7 = debug;
        if (r7 == 0) goto L_0x0173;
    L_0x0157:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(H) = ";
        r8 = r8.append(r9);
        r9 = r0.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x0173:
        r0 = r0.monic();
        r7 = r0.isONE();
        if (r7 == 0) goto L_0x019a;
    L_0x017d:
        r7 = r10.pairlist;
        r7.putOne();
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x0197 }
        r7.clear();	 Catch:{ all -> 0x0197 }
        r7 = r10.G;	 Catch:{ all -> 0x0197 }
        r7.add(r0);	 Catch:{ all -> 0x0197 }
        monitor-exit(r8);	 Catch:{ all -> 0x0197 }
        r7 = r10.pool;
        r7.allIdle();
        goto L_0x008a;
    L_0x0197:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x0197 }
        throw r7;
    L_0x019a:
        r7 = debug;
        if (r7 == 0) goto L_0x01b6;
    L_0x019e:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "H = ";
        r8 = r8.append(r9);
        r8 = r8.append(r0);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x01b6:
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x01c6 }
        r7.add(r0);	 Catch:{ all -> 0x01c6 }
        monitor-exit(r8);	 Catch:{ all -> 0x01c6 }
        r7 = r10.pairlist;
        r7.put(r0);
        goto L_0x0003;
    L_0x01c6:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x01c6 }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.LeftSolvableReducer.run():void");
    }
}
