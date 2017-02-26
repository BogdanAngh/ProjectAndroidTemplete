package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class OrderedPairlist<C extends RingElem<C>> implements PairList<C> {
    private static final Logger logger;
    protected final List<GenPolynomial<C>> P;
    protected final int moduleVars;
    protected boolean oneInGB;
    protected final SortedMap<ExpVector, LinkedList<Pair<C>>> pairlist;
    protected int putCount;
    protected final List<BitSet> red;
    protected final Reduction<C> reduction;
    protected int remCount;
    protected final GenPolynomialRing<C> ring;
    protected boolean useCriterion4;

    static {
        logger = Logger.getLogger(OrderedPairlist.class);
    }

    public OrderedPairlist() {
        this.oneInGB = false;
        this.useCriterion4 = true;
        this.moduleVars = 0;
        this.ring = null;
        this.P = null;
        this.pairlist = null;
        this.red = null;
        this.reduction = null;
        this.putCount = 0;
        this.remCount = 0;
    }

    public OrderedPairlist(GenPolynomialRing<C> r) {
        this(0, r);
    }

    public OrderedPairlist(int m, GenPolynomialRing<C> r) {
        this.oneInGB = false;
        this.useCriterion4 = true;
        this.moduleVars = m;
        this.ring = r;
        this.P = new ArrayList();
        this.pairlist = new TreeMap(this.ring.tord.getAscendComparator());
        this.red = new ArrayList();
        this.putCount = 0;
        this.remCount = 0;
        if (!this.ring.isCommutative()) {
            this.useCriterion4 = false;
        }
        this.reduction = new ReductionSeq();
    }

    public PairList<C> create(GenPolynomialRing<C> r) {
        return new OrderedPairlist(r);
    }

    public PairList<C> create(int m, GenPolynomialRing<C> r) {
        return new OrderedPairlist(m, r);
    }

    public String toString() {
        StringBuffer s = new StringBuffer(getClass().getSimpleName() + "(");
        s.append("#put=" + this.putCount);
        s.append(", #rem=" + this.remCount);
        if (!(this.pairlist == null || this.pairlist.size() == 0)) {
            s.append(", size=" + this.pairlist.size());
        }
        if (this.moduleVars > 0) {
            s.append(", modv=" + this.moduleVars);
        }
        s.append(")");
        return s.toString();
    }

    public synchronized int put(GenPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else {
            ExpVector e = p.leadingExpVector();
            int l = this.P.size();
            for (int j = 0; j < l; j++) {
                GenPolynomial pj = (GenPolynomial) this.P.get(j);
                ExpVector f = pj.leadingExpVector();
                if (this.moduleVars <= 0 || this.reduction.moduleCriterion(this.moduleVars, e, f)) {
                    ExpVector g = e.lcm(f);
                    Pair<C> pair = new Pair(pj, (GenPolynomial) p, j, l);
                    LinkedList<Pair<C>> xl = (LinkedList) this.pairlist.get(g);
                    if (xl == null) {
                        xl = new LinkedList();
                    }
                    xl.addFirst(pair);
                    this.pairlist.put(g, xl);
                }
            }
            this.P.add(p);
            BitSet redi = new BitSet();
            redi.set(0, l);
            this.red.add(redi);
            size = this.P.size() - 1;
        }
        return size;
    }

    public int put(List<GenPolynomial<C>> F) {
        int i = 0;
        for (GenPolynomial p : F) {
            i = put(p);
        }
        return i;
    }

    public synchronized Pair<C> removeNext() {
        Pair<C> pair;
        if (this.oneInGB) {
            pair = null;
        } else {
            Iterator<Entry<ExpVector, LinkedList<Pair<C>>>> ip = this.pairlist.entrySet().iterator();
            pair = null;
            boolean c = false;
            while (!c && ip.hasNext()) {
                Entry<ExpVector, LinkedList<Pair<C>>> me = (Entry) ip.next();
                ExpVector g = (ExpVector) me.getKey();
                LinkedList<Pair<C>> xl = (LinkedList) me.getValue();
                if (logger.isInfoEnabled()) {
                    logger.info("g  = " + g);
                }
                pair = null;
                while (!c && xl.size() > 0) {
                    pair = (Pair) xl.removeFirst();
                    int i = pair.i;
                    int j = pair.j;
                    if (this.useCriterion4) {
                        c = this.reduction.criterion4(pair.pi, pair.pj, g);
                    } else {
                        c = true;
                    }
                    if (c) {
                        c = criterion3(i, j, g);
                    }
                    ((BitSet) this.red.get(j)).clear(i);
                }
                if (xl.size() == 0) {
                    ip.remove();
                }
            }
            if (c) {
                pair.maxIndex(this.P.size() - 1);
                this.remCount++;
                if (logger.isDebugEnabled()) {
                    logger.info("pair(" + pair.j + "," + pair.i + ")");
                }
            } else {
                pair = null;
            }
        }
        return pair;
    }

    public synchronized boolean hasNext() {
        return this.pairlist.size() > 0;
    }

    public List<GenPolynomial<C>> getList() {
        return this.P;
    }

    public int size() {
        return this.P.size();
    }

    public synchronized int putCount() {
        return this.putCount;
    }

    public synchronized int remCount() {
        return this.remCount;
    }

    public synchronized int putOne(GenPolynomial<C> one) {
        int size;
        if (one == null) {
            size = this.P.size() - 1;
        } else if (one.isONE()) {
            size = putOne();
        } else {
            size = this.P.size() - 1;
        }
        return size;
    }

    public synchronized int putOne() {
        this.putCount++;
        this.oneInGB = true;
        this.pairlist.clear();
        this.P.clear();
        this.P.add(this.ring.getONE());
        this.red.clear();
        logger.info("outOne " + toString());
        return this.P.size() - 1;
    }

    public boolean criterion3(int i, int j, ExpVector eij) {
        boolean s = ((BitSet) this.red.get(j)).get(i);
        if (s) {
            int k = 0;
            while (k < this.P.size()) {
                if (!(i == k || j == k || !eij.multipleOf(((GenPolynomial) this.P.get(k)).leadingExpVector()))) {
                    if (k < i) {
                        if (((BitSet) this.red.get(i)).get(k) || ((BitSet) this.red.get(j)).get(k)) {
                            s = true;
                        } else {
                            s = false;
                        }
                    } else if (i < k && k < j) {
                        s = ((BitSet) this.red.get(k)).get(i) || ((BitSet) this.red.get(j)).get(k);
                    } else if (j < k) {
                        s = ((BitSet) this.red.get(k)).get(i) || ((BitSet) this.red.get(k)).get(j);
                    }
                    if (!s) {
                        return s;
                    }
                }
                k++;
            }
            return true;
        }
        logger.warn("c3.s false for " + j + " " + i);
        return s;
    }
}
