package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.log4j.Logger;

public class CriticalPairList<C extends RingElem<C>> extends OrderedPairlist<C> {
    private static final Logger logger;
    protected final SortedSet<CriticalPair<C>> pairlist;
    protected int recordCount;

    static {
        logger = Logger.getLogger(CriticalPairList.class);
    }

    public CriticalPairList() {
        this.pairlist = null;
    }

    public CriticalPairList(GenPolynomialRing<C> r) {
        this(0, r);
    }

    public CriticalPairList(int m, GenPolynomialRing<C> r) {
        super(m, r);
        this.pairlist = new TreeSet(new CriticalPairComparator(this.ring.tord));
        this.recordCount = 0;
    }

    public PairList<C> create(GenPolynomialRing<C> r) {
        return new CriticalPairList(r);
    }

    public PairList<C> create(int m, GenPolynomialRing<C> r) {
        return new CriticalPairList(m, r);
    }

    public synchronized int put(GenPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else {
            ExpVector e = p.leadingExpVector();
            size = this.P.size();
            for (int j = 0; j < size; j++) {
                GenPolynomial<C> pj = (GenPolynomial) this.P.get(j);
                ExpVector f = pj.leadingExpVector();
                if (this.moduleVars <= 0 || this.reduction.moduleCriterion(this.moduleVars, e, f)) {
                    this.pairlist.add(new CriticalPair(e.lcm(f), pj, p, j, size));
                }
            }
            this.P.add(p);
            BitSet redi = new BitSet();
            redi.set(0, size);
            this.red.add(redi);
            if (this.recordCount < size) {
                this.recordCount = size;
            }
        }
        return size;
    }

    public synchronized boolean hasNext() {
        return this.pairlist.size() > 0;
    }

    public Pair<C> removeNext() {
        CriticalPair<C> cp = getNext();
        if (cp == null) {
            return null;
        }
        return new Pair(cp.pi, cp.pj, cp.i, cp.j);
    }

    public synchronized CriticalPair<C> getNext() {
        CriticalPair<C> criticalPair;
        if (this.oneInGB) {
            criticalPair = null;
        } else {
            criticalPair = null;
            Iterator<CriticalPair<C>> ip = this.pairlist.iterator();
            boolean c = false;
            while (!c && ip.hasNext()) {
                criticalPair = (CriticalPair) ip.next();
                if (!criticalPair.getInReduction() && criticalPair.getReductum() == null) {
                    if (logger.isInfoEnabled()) {
                        logger.info(BuildConfig.FLAVOR + criticalPair);
                    }
                    if (this.useCriterion4) {
                        c = this.reduction.criterion4(criticalPair.pi, criticalPair.pj, criticalPair.e);
                    } else {
                        c = true;
                    }
                    if (c) {
                        c = criterion3(criticalPair.i, criticalPair.j, criticalPair.e);
                    }
                    ((BitSet) this.red.get(criticalPair.j)).clear(criticalPair.i);
                    if (!c) {
                        criticalPair.setReductum(this.ring.getZERO());
                    }
                }
            }
            if (c) {
                this.remCount++;
                criticalPair.setInReduction();
            } else {
                criticalPair = null;
            }
        }
        return criticalPair;
    }

    public int record(CriticalPair<C> pair, GenPolynomial<C> p) {
        if (p == null) {
            p = this.ring.getZERO();
        }
        pair.setReductum(p);
        if (p.isZERO() || p.isONE()) {
            return -1;
        }
        this.recordCount++;
        return this.recordCount;
    }

    public int update(CriticalPair<C> pair, GenPolynomial<C> p) {
        if (p == null) {
            p = this.ring.getZERO();
        }
        pair.setReductum(p);
        if (!(p.isZERO() || p.isONE())) {
            this.recordCount++;
        }
        if (update() < 0) {
            System.out.println("c < 0");
        }
        if (p.isZERO() || p.isONE()) {
            return -1;
        }
        return this.recordCount;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int update() {
        /*
        r5 = this;
        monitor-enter(r5);
        r0 = 0;
        r4 = r5.oneInGB;	 Catch:{ all -> 0x0036 }
        if (r4 == 0) goto L_0x0009;
    L_0x0006:
        r1 = r0;
    L_0x0007:
        monitor-exit(r5);
        return r1;
    L_0x0009:
        r4 = r5.pairlist;	 Catch:{ all -> 0x0036 }
        r4 = r4.size();	 Catch:{ all -> 0x0036 }
        if (r4 <= 0) goto L_0x003d;
    L_0x0011:
        r4 = r5.pairlist;	 Catch:{ all -> 0x0036 }
        r3 = r4.first();	 Catch:{ all -> 0x0036 }
        r3 = (edu.jas.gb.CriticalPair) r3;	 Catch:{ all -> 0x0036 }
        r2 = r3.getReductum();	 Catch:{ all -> 0x0036 }
        if (r2 == 0) goto L_0x003d;
    L_0x001f:
        r4 = r5.pairlist;	 Catch:{ all -> 0x0036 }
        r4.remove(r3);	 Catch:{ all -> 0x0036 }
        r0 = r0 + 1;
        r4 = r2.isZERO();	 Catch:{ all -> 0x0036 }
        if (r4 != 0) goto L_0x0009;
    L_0x002c:
        r4 = r2.isONE();	 Catch:{ all -> 0x0036 }
        if (r4 == 0) goto L_0x0039;
    L_0x0032:
        r5.putOne();	 Catch:{ all -> 0x0036 }
        goto L_0x0009;
    L_0x0036:
        r4 = move-exception;
        monitor-exit(r5);
        throw r4;
    L_0x0039:
        r5.put(r2);	 Catch:{ all -> 0x0036 }
        goto L_0x0009;
    L_0x003d:
        r1 = r0;
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.CriticalPairList.update():int");
    }

    public synchronized List<CriticalPair<C>> inWork() {
        List<CriticalPair<C>> iw;
        iw = new ArrayList();
        if (!this.oneInGB) {
            for (CriticalPair<C> pair : this.pairlist) {
                if (pair.getInReduction()) {
                    iw.add(pair);
                }
            }
        }
        return iw;
    }

    public synchronized int updateMany() {
        int num;
        int num2 = 0;
        if (this.oneInGB) {
            num = 0;
        } else {
            List<CriticalPair<C>> rem = new ArrayList();
            for (CriticalPair<C> pair : this.pairlist) {
                if (pair.getReductum() == null) {
                    break;
                }
                rem.add(pair);
                num2++;
            }
            for (CriticalPair<C> pair2 : rem) {
                this.pairlist.remove(pair2);
                GenPolynomial<C> p = pair2.getReductum();
                if (!p.isZERO()) {
                    if (p.isONE()) {
                        putOne();
                    } else {
                        put(p);
                    }
                }
            }
            num = num2;
        }
        return num;
    }

    public synchronized int putOne() {
        super.putOne();
        this.pairlist.clear();
        return 0;
    }
}
