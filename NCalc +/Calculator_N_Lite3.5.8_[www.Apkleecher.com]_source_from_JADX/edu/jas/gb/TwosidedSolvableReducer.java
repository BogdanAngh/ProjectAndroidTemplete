package edu.jas.gb;

import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: SolvableGroebnerBaseParallel */
class TwosidedSolvableReducer<C extends RingElem<C>> implements Runnable {
    private static final boolean debug;
    private static final Logger logger;
    private final List<GenSolvablePolynomial<C>> G;
    private final List<GenSolvablePolynomial<C>> X;
    private final int modv;
    private final PairList<C> pairlist;
    private final Terminator pool;
    private final SolvableReductionPar<C> sred;

    static {
        logger = Logger.getLogger(TwosidedSolvableReducer.class);
        debug = logger.isDebugEnabled();
    }

    TwosidedSolvableReducer(Terminator fin, int modv, List<GenSolvablePolynomial<C>> X, List<GenSolvablePolynomial<C>> G, PairList<C> L) {
        this.pool = fin;
        this.modv = modv;
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
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "modv = ";
        r11 = r11.append(r12);
        r12 = r13.modv;
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x001d:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x002d;
    L_0x0025:
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 == 0) goto L_0x0065;
    L_0x002d:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x0055;
    L_0x0035:
        r10 = r13.pool;
        r10.beIdle();
        r7 = 1;
        r8 = r8 + 1;
        r10 = r8 % 10;
        if (r10 != 0) goto L_0x0084;
    L_0x0041:
        r10 = logger;	 Catch:{ InterruptedException -> 0x008c }
        r11 = " reducer is sleeping";
        r10.info(r11);	 Catch:{ InterruptedException -> 0x008c }
    L_0x0048:
        r10 = 50;
        java.lang.Thread.sleep(r10);	 Catch:{ InterruptedException -> 0x008c }
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 != 0) goto L_0x002d;
    L_0x0055:
        r10 = r13.pairlist;
        r10 = r10.hasNext();
        if (r10 != 0) goto L_0x008e;
    L_0x005d:
        r10 = r13.pool;
        r10 = r10.hasJobs();
        if (r10 != 0) goto L_0x008e;
    L_0x0065:
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
    L_0x0083:
        return;
    L_0x0084:
        r10 = logger;	 Catch:{ InterruptedException -> 0x008c }
        r11 = "r";
        r10.debug(r11);	 Catch:{ InterruptedException -> 0x008c }
        goto L_0x0048;
    L_0x008c:
        r2 = move-exception;
        goto L_0x0055;
    L_0x008e:
        if (r7 == 0) goto L_0x0096;
    L_0x0090:
        r10 = r13.pool;
        r10.notIdle();
        r7 = 0;
    L_0x0096:
        r10 = r13.pairlist;
        r5 = r10.removeNext();
        if (r5 == 0) goto L_0x001d;
    L_0x009e:
        r10 = debug;
        if (r10 == 0) goto L_0x00d6;
    L_0x00a2:
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
    L_0x00d6:
        r12 = r13.sred;
        r10 = r5.pi;
        r10 = (edu.jas.poly.GenSolvablePolynomial) r10;
        r11 = r5.pj;
        r11 = (edu.jas.poly.GenSolvablePolynomial) r11;
        r1 = r12.leftSPolynomial(r10, r11);
        r10 = r1.isZERO();
        if (r10 != 0) goto L_0x001d;
    L_0x00ea:
        r10 = debug;
        if (r10 == 0) goto L_0x010a;
    L_0x00ee:
        r10 = logger;
        r11 = new java.lang.StringBuilder;
        r11.<init>();
        r12 = "ht(S) = ";
        r11 = r11.append(r12);
        r12 = r1.leadingExpVector();
        r11 = r11.append(r12);
        r11 = r11.toString();
        r10.debug(r11);
    L_0x010a:
        r10 = r13.sred;
        r11 = r13.G;
        r0 = r10.leftNormalform(r11, r1);
        r6 = r6 + 1;
        r10 = r0.isZERO();
        if (r10 != 0) goto L_0x001d;
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
        goto L_0x0083;
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
        r10 = r13.G;	 Catch:{ all -> 0x01cd }
        r10.add(r0);	 Catch:{ all -> 0x01cd }
        monitor-exit(r11);	 Catch:{ all -> 0x01cd }
        r10 = r13.pairlist;
        r10.put(r0);
        r3 = 0;
    L_0x018c:
        r10 = r13.X;
        r10 = r10.size();
        if (r3 >= r10) goto L_0x001d;
    L_0x0194:
        r10 = r13.X;
        r9 = r10.get(r3);
        r9 = (edu.jas.poly.GenSolvablePolynomial) r9;
        r4 = r0.multiply(r9);
        r10 = r13.sred;
        r11 = r13.G;
        r4 = r10.leftNormalform(r11, r4);
        r10 = r4.isZERO();
        if (r10 != 0) goto L_0x01e1;
    L_0x01ae:
        r4 = r4.monic();
        r10 = r4.isONE();
        if (r10 == 0) goto L_0x01d3;
    L_0x01b8:
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x01d0 }
        r10.clear();	 Catch:{ all -> 0x01d0 }
        r10 = r13.G;	 Catch:{ all -> 0x01d0 }
        r10.add(r4);	 Catch:{ all -> 0x01d0 }
        monitor-exit(r11);	 Catch:{ all -> 0x01d0 }
        r10 = r13.pool;
        r10.allIdle();
        goto L_0x0083;
    L_0x01cd:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01cd }
        throw r10;
    L_0x01d0:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01d0 }
        throw r10;
    L_0x01d3:
        r11 = r13.G;
        monitor-enter(r11);
        r10 = r13.G;	 Catch:{ all -> 0x01e4 }
        r10.add(r4);	 Catch:{ all -> 0x01e4 }
        monitor-exit(r11);	 Catch:{ all -> 0x01e4 }
        r10 = r13.pairlist;
        r10.put(r4);
    L_0x01e1:
        r3 = r3 + 1;
        goto L_0x018c;
    L_0x01e4:
        r10 = move-exception;
        monitor-exit(r11);	 Catch:{ all -> 0x01e4 }
        throw r10;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.TwosidedSolvableReducer.run():void");
    }
}
