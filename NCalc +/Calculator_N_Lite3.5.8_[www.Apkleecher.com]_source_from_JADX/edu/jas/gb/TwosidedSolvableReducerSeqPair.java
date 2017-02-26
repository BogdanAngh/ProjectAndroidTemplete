package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: SolvableGroebnerBaseSeqPairParallel */
class TwosidedSolvableReducerSeqPair<C extends RingElem<C>> implements Runnable {
    private static final boolean debug;
    private static final Logger logger;
    private final List<GenSolvablePolynomial<C>> G;
    private final List<GenSolvablePolynomial<C>> X;
    private final CriticalPairList<C> pairlist;
    private final Terminator pool;
    private final SolvableReductionPar<C> sred;

    static {
        logger = Logger.getLogger(TwosidedSolvableReducerSeqPair.class);
        debug = logger.isDebugEnabled();
    }

    TwosidedSolvableReducerSeqPair(Terminator fin, List<GenSolvablePolynomial<C>> X, List<GenSolvablePolynomial<C>> G, CriticalPairList<C> L) {
        this.pool = fin;
        this.X = X;
        this.G = G;
        this.pairlist = L;
        this.sred = new SolvableReductionPar();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r13 = this;
        r7 = 0;
        r6 = 0;
        r8 = 0;
    L_0x0003:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x0013;
    L_0x000b:
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 == 0) goto L_0x0050;
    L_0x0013:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x0040;
    L_0x001b:
        r10 = r13.pairlist;
        r10.update();
        r10 = r13.pool;
        r10.beIdle();
        r7 = 1;
        r8 = r8 + 1;
        r10 = r8 % 10;
        if (r10 != 0) goto L_0x006f;
    L_0x002c:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0077 }
        r11 = " reducer is sleeping";
        r10.info(r11);	 Catch:{ InterruptedException -> 0x0077 }
    L_0x0033:
        r10 = 50;
        java.lang.Thread.sleep(r10);	 Catch:{ InterruptedException -> 0x0077 }
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 != 0) goto L_0x0013;
    L_0x0040:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x0079;
    L_0x0048:
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 != 0) goto L_0x0079;
    L_0x0050:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "terminated, done ";
        r11 = r11.append(r12);
        r11 = r11.append(r6);
        r12 = " reductions";
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.info(r11);
    L_0x006e:
        return;
    L_0x006f:
        r10 = logger;	 Catch:{ InterruptedException -> 0x0077 }
        r11 = "r";
        r10.debug(r11);	 Catch:{ InterruptedException -> 0x0077 }
        goto L_0x0033;
    L_0x0077:
        r2 = move-exception;
        goto L_0x0040;
    L_0x0079:
        if (r7 == 0) goto L_0x0081;
    L_0x007b:
        r10 = r13.pool;
        r10.notIdle();
        r7 = 0;
    L_0x0081:
        r10 = r13.pairlist;
        r5 = r10.getNext();
        if (r5 != 0) goto L_0x0090;
    L_0x0089:
        r10 = r13.pairlist;
        r10.update();
        goto L_0x0003;
    L_0x0090:
        r10 = debug;
        if (r10 == 0) goto L_0x00c8;
    L_0x0094:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "pi = ";
        r11 = r11.append(r12);
        r12 = r5.pi;
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "pj = ";
        r11 = r11.append(r12);
        r12 = r5.pj;
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x00c8:
        r12 = r13.sred;
        r10 = r5.pi;
        r10 = (edu.jas.poly.GenSolvablePolynomial) r10;
        r11 = r5.pj;
        r11 = (edu.jas.poly.GenSolvablePolynomial) r11;
        r1 = r12.leftSPolynomial(r10, r11);
        r10 = r1.isZERO();
        if (r10 == 0) goto L_0x00e3;
    L_0x00dc:
        r10 = r13.pairlist;
        r10.record(r5, r1);
        goto L_0x0003;
    L_0x00e3:
        r10 = debug;
        if (r10 == 0) goto L_0x0103;
    L_0x00e7:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "ht(S) = ";
        r11 = r11.append(r12);
        r12 = r1.leadingExpVector();
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x0103:
        r10 = r13.sred;
        r11 = r13.G;
        r0 = r10.leftNormalform(r11, r1);
        r6 = r6 + 1;
        r10 = r0.isZERO();
        if (r10 == 0) goto L_0x011a;
    L_0x0113:
        r10 = r13.pairlist;
        r10.record(r5, r0);
        goto L_0x0003;
    L_0x011a:
        r10 = debug;
        if (r10 == 0) goto L_0x013a;
    L_0x011e:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "ht(H) = ";
        r11 = r11.append(r12);
        r12 = r0.leadingExpVector();
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x013a:
        r0 = r0.monic();
        r10 = r0.isONE();
        if (r10 == 0) goto L_0x0161;
    L_0x0144:
        r10 = r13.pairlist;
        r10.putOne();
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x015e }
        r10.clear();	 Catch:{ all -> 0x015e }
        r10 = r13.G;	 Catch:{ all -> 0x015e }
        r10.add(r0);	 Catch:{ all -> 0x015e }
        monitor-exit(r11);	 Catch:{ all -> 0x015e }
        r10 = r13.pool;
        r10.allIdle();
        goto L_0x006e;
    L_0x015e:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x015e }
        throw r10;
    L_0x0161:
        r10 = debug;
        if (r10 == 0) goto L_0x017d;
    L_0x0165:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "H = ";
        r11 = r11.append(r12);
        r11 = r11.append(r0);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x017d:
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x01a5 }
        r10.add(r0);	 Catch:{ all -> 0x01a5 }
        monitor-exit(r11);	 Catch:{ all -> 0x01a5 }
        r10 = r13.pairlist;
        r10.update(r5, r0);
        r3 = 0;
    L_0x018c:
        r10 = r13.X;
        r10 = r10.size();
        if (r3 >= r10) goto L_0x0003;
    L_0x0194:
        r10 = r13.X;
        r9 = r10.get(r3);
        r9 = (edu.jas.poly.GenSolvablePolynomial) r9;
        r10 = r9.isONE();
        if (r10 == 0) goto L_0x01a8;
    L_0x01a2:
        r3 = r3 + 1;
        goto L_0x018c;
    L_0x01a5:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01a5 }
        throw r10;
    L_0x01a8:
        r4 = r0.multiply(r9);
        r10 = r13.sred;
        r11 = r13.G;
        r4 = r10.leftNormalform(r11, r4);
        r10 = r4.isZERO();
        if (r10 != 0) goto L_0x01a2;
    L_0x01ba:
        r4 = r4.monic();
        r10 = r4.isONE();
        if (r10 == 0) goto L_0x01dc;
    L_0x01c4:
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x01d9 }
        r10.clear();	 Catch:{ all -> 0x01d9 }
        r10 = r13.G;	 Catch:{ all -> 0x01d9 }
        r10.add(r4);	 Catch:{ all -> 0x01d9 }
        monitor-exit(r11);	 Catch:{ all -> 0x01d9 }
        r10 = r13.pool;
        r10.allIdle();
        goto L_0x006e;
    L_0x01d9:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01d9 }
        throw r10;
    L_0x01dc:
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x01eb }
        r10.add(r4);	 Catch:{ all -> 0x01eb }
        monitor-exit(r11);	 Catch:{ all -> 0x01eb }
        r10 = r13.pairlist;
        r10.put(r4);
        goto L_0x01a2;
    L_0x01eb:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01eb }
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.TwosidedSolvableReducerSeqPair.run():void");
    }
}
