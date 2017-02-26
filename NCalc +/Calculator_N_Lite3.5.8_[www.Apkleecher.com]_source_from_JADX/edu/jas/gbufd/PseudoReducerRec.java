package edu.jas.gbufd;

import edu.jas.gb.PairList;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.ufd.GreatestCommonDivisorAbstract;
import edu.jas.util.Terminator;
import java.util.List;
import org.apache.log4j.Logger;

/* compiled from: GroebnerBasePseudoRecParallel */
class PseudoReducerRec<C extends GcdRingElem<C>> implements Runnable {
    private static final Logger logger;
    private final List<GenPolynomial<GenPolynomial<C>>> G;
    private final GreatestCommonDivisorAbstract<C> engine;
    private final Terminator fin;
    private final PairList<GenPolynomial<C>> pairlist;
    private final PseudoReductionPar<GenPolynomial<C>> red;
    private final PseudoReductionPar<C> redRec;

    static {
        logger = Logger.getLogger(PseudoReducerRec.class);
    }

    PseudoReducerRec(Terminator fin, List<GenPolynomial<GenPolynomial<C>>> G, PairList<GenPolynomial<C>> L, GreatestCommonDivisorAbstract<C> engine) {
        this.fin = fin;
        this.G = G;
        this.pairlist = L;
        this.red = new PseudoReductionPar();
        this.redRec = new PseudoReductionPar();
        this.engine = engine;
        fin.initIdle(1);
    }

    public String toString() {
        return "PseudoReducer";
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
        if (r8 == 0) goto L_0x0045;
    L_0x0013:
        r8 = r12.pairlist;
        r8 = r8.hasNext();
        if (r8 != 0) goto L_0x0035;
    L_0x001b:
        r7 = r7 + 1;
        r8 = r7 % 10;
        if (r8 != 0) goto L_0x0069;
    L_0x0021:
        r8 = logger;	 Catch:{ InterruptedException -> 0x0071 }
        r9 = " reducer is sleeping";
        r8.info(r9);	 Catch:{ InterruptedException -> 0x0071 }
    L_0x0028:
        r8 = 100;
        java.lang.Thread.sleep(r8);	 Catch:{ InterruptedException -> 0x0071 }
        r8 = r12.fin;
        r8 = r8.hasJobs();
        if (r8 != 0) goto L_0x0013;
    L_0x0035:
        r8 = r12.pairlist;
        r8 = r8.hasNext();
        if (r8 != 0) goto L_0x0073;
    L_0x003d:
        r8 = r12.fin;
        r8 = r8.hasJobs();
        if (r8 != 0) goto L_0x0073;
    L_0x0045:
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
    L_0x0068:
        return;
    L_0x0069:
        r8 = logger;	 Catch:{ InterruptedException -> 0x0071 }
        r9 = "r";
        r8.debug(r9);	 Catch:{ InterruptedException -> 0x0071 }
        goto L_0x0028;
    L_0x0071:
        r2 = move-exception;
        goto L_0x0035;
    L_0x0073:
        r8 = r12.fin;
        r8.notIdle();
        r8 = r12.pairlist;
        r3 = r8.removeNext();
        r8 = java.lang.Thread.currentThread();
        r8 = r8.isInterrupted();
        if (r8 == 0) goto L_0x0095;
    L_0x0088:
        r8 = r12.fin;
        r8.initIdle(r11);
        r8 = new java.lang.RuntimeException;
        r9 = "interrupt after removeNext";
        r8.<init>(r9);
        throw r8;
    L_0x0095:
        if (r3 != 0) goto L_0x009e;
    L_0x0097:
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x009e:
        r4 = r3.pi;
        r5 = r3.pj;
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x00da;
    L_0x00aa:
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
    L_0x00da:
        r8 = r12.red;
        r1 = r8.SPolynomial(r4, r5);
        r8 = r1.isZERO();
        if (r8 == 0) goto L_0x00f0;
    L_0x00e6:
        r3.setZero();
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x00f0:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x0114;
    L_0x00f8:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "ht(S) = ";
        r9 = r9.append(r10);
        r10 = r1.leadingExpVector();
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.debug(r9);
    L_0x0114:
        r8 = r12.redRec;
        r9 = r12.G;
        r0 = r8.normalformRecursive(r9, r1);
        r6 = r6 + 1;
        r8 = r0.isZERO();
        if (r8 == 0) goto L_0x012e;
    L_0x0124:
        r3.setZero();
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x012e:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x0152;
    L_0x0136:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "ht(H) = ";
        r9 = r9.append(r10);
        r10 = r0.leadingExpVector();
        r9 = r9.append(r10);
        r9 = r9.toString();
        r8.info(r9);
    L_0x0152:
        r8 = r12.engine;
        r0 = r8.recursivePrimitivePart(r0);
        r0 = r0.abs();
        r8 = r0.isONE();
        if (r8 == 0) goto L_0x017f;
    L_0x0162:
        r8 = r12.pairlist;
        r8.put(r0);
        r9 = r12.G;
        monitor-enter(r9);
        r8 = r12.G;	 Catch:{ all -> 0x017c }
        r8.clear();	 Catch:{ all -> 0x017c }
        r8 = r12.G;	 Catch:{ all -> 0x017c }
        r8.add(r0);	 Catch:{ all -> 0x017c }
        monitor-exit(r9);	 Catch:{ all -> 0x017c }
        r8 = r12.fin;
        r8.allIdle();
        goto L_0x0068;
    L_0x017c:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x017c }
        throw r8;
    L_0x017f:
        r8 = logger;
        r8 = r8.isDebugEnabled();
        if (r8 == 0) goto L_0x019f;
    L_0x0187:
        r8 = logger;
        r9 = new java.lang.StringBuilder;
        r9.<init>();
        r10 = "H = ";
        r9 = r9.append(r10);
        r9 = r9.append(r0);
        r9 = r9.toString();
        r8.debug(r9);
    L_0x019f:
        r9 = r12.G;
        monitor-enter(r9);
        r8 = r12.G;	 Catch:{ all -> 0x01b4 }
        r8.add(r0);	 Catch:{ all -> 0x01b4 }
        monitor-exit(r9);	 Catch:{ all -> 0x01b4 }
        r8 = r12.pairlist;
        r8.put(r0);
        r8 = r12.fin;
        r8.initIdle(r11);
        goto L_0x0003;
    L_0x01b4:
        r8 = move-exception;
        monitor-exit(r9);	 Catch:{ all -> 0x01b4 }
        throw r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbufd.PseudoReducerRec.run():void");
    }
}
