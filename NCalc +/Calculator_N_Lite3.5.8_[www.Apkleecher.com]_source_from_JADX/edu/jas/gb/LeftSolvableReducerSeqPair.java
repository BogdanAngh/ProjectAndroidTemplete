package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: SolvableGroebnerBaseSeqPairParallel */
class LeftSolvableReducerSeqPair<C extends RingElem<C>> implements Runnable {
    private static final boolean debug;
    private static final Logger logger;
    private final List<GenSolvablePolynomial<C>> G;
    private final CriticalPairList<C> pairlist;
    private final Terminator pool;
    private final SolvableReductionPar<C> sred;

    static {
        logger = Logger.getLogger(LeftSolvableReducerSeqPair.class);
        debug = logger.isDebugEnabled();
    }

    LeftSolvableReducerSeqPair(Terminator fin, List<GenSolvablePolynomial<C>> G, CriticalPairList<C> L) {
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
        if (r7 == 0) goto L_0x0050;
    L_0x0013:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x0040;
    L_0x001b:
        r7 = r10.pairlist;
        r7.update();
        r7 = r10.pool;
        r7.beIdle();
        r5 = 1;
        r6 = r6 + 1;
        r7 = r6 % 10;
        if (r7 != 0) goto L_0x006f;
    L_0x002c:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0077 }
        r8 = " reducer is sleeping";
        r7.info(r8);	 Catch:{ InterruptedException -> 0x0077 }
    L_0x0033:
        r8 = 100;
        java.lang.Thread.sleep(r8);	 Catch:{ InterruptedException -> 0x0077 }
        r7 = r10.pool;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x0013;
    L_0x0040:
        r7 = r10.pairlist;
        r7 = r7.hasNext();
        if (r7 != 0) goto L_0x0079;
    L_0x0048:
        r7 = r10.pool;
        r7 = r7.hasJobs();
        if (r7 != 0) goto L_0x0079;
    L_0x0050:
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
    L_0x006e:
        return;
    L_0x006f:
        r7 = logger;	 Catch:{ InterruptedException -> 0x0077 }
        r8 = "r";
        r7.debug(r8);	 Catch:{ InterruptedException -> 0x0077 }
        goto L_0x0033;
    L_0x0077:
        r2 = move-exception;
        goto L_0x0040;
    L_0x0079:
        if (r5 == 0) goto L_0x0081;
    L_0x007b:
        r7 = r10.pool;
        r7.notIdle();
        r5 = 0;
    L_0x0081:
        r7 = r10.pairlist;
        r3 = r7.getNext();
        if (r3 != 0) goto L_0x0090;
    L_0x0089:
        r7 = r10.pairlist;
        r7.update();
        goto L_0x0003;
    L_0x0090:
        r7 = debug;
        if (r7 == 0) goto L_0x00c8;
    L_0x0094:
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
    L_0x00c8:
        r9 = r10.sred;
        r7 = r3.pi;
        r7 = (edu.jas.poly.GenSolvablePolynomial) r7;
        r8 = r3.pj;
        r8 = (edu.jas.poly.GenSolvablePolynomial) r8;
        r1 = r9.leftSPolynomial(r7, r8);
        r7 = r1.isZERO();
        if (r7 == 0) goto L_0x00e3;
    L_0x00dc:
        r7 = r10.pairlist;
        r7.record(r3, r1);
        goto L_0x0003;
    L_0x00e3:
        r7 = debug;
        if (r7 == 0) goto L_0x0103;
    L_0x00e7:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(S) = ";
        r8 = r8.append(r9);
        r9 = r1.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x0103:
        r7 = r10.sred;
        r8 = r10.G;
        r0 = r7.leftNormalform(r8, r1);
        r4 = r4 + 1;
        r7 = r0.isZERO();
        if (r7 == 0) goto L_0x011a;
    L_0x0113:
        r7 = r10.pairlist;
        r7.record(r3, r0);
        goto L_0x0003;
    L_0x011a:
        r7 = debug;
        if (r7 == 0) goto L_0x013a;
    L_0x011e:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "ht(H) = ";
        r8 = r8.append(r9);
        r9 = r0.leadingExpVector();
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x013a:
        r0 = r0.monic();
        r7 = r0.isONE();
        if (r7 == 0) goto L_0x0161;
    L_0x0144:
        r7 = r10.pairlist;
        r7.putOne();
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x015e }
        r7.clear();	 Catch:{ all -> 0x015e }
        r7 = r10.G;	 Catch:{ all -> 0x015e }
        r7.add(r0);	 Catch:{ all -> 0x015e }
        monitor-exit(r8);	 Catch:{ all -> 0x015e }
        r7 = r10.pool;
        r7.allIdle();
        goto L_0x006e;
    L_0x015e:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x015e }
        throw r7;
    L_0x0161:
        r7 = debug;
        if (r7 == 0) goto L_0x017d;
    L_0x0165:
        r7 = logger;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "H = ";
        r8 = r8.append(r9);
        r8 = r8.append(r0);
        r8 = r8.toString();
        r7.debug(r8);
    L_0x017d:
        r8 = r10.G;
        monitor-enter(r8);
        r7 = r10.G;	 Catch:{ all -> 0x018d }
        r7.add(r0);	 Catch:{ all -> 0x018d }
        monitor-exit(r8);	 Catch:{ all -> 0x018d }
        r7 = r10.pairlist;
        r7.update(r3, r0);
        goto L_0x0003;
    L_0x018d:
        r7 = move-exception;
        monitor-exit(r8);	 Catch:{ all -> 0x018d }
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.LeftSolvableReducerSeqPair.run():void");
    }
}
