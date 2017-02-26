package edu.jas.application;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.structure.GcdRingElem;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class OrderedCPairlist<C extends GcdRingElem<C>> implements Serializable {
    private static final Logger logger;
    protected final List<ColorPolynomial<C>> P;
    protected final int moduleVars;
    protected boolean oneInGB;
    protected final SortedMap<ExpVector, LinkedList<CPair<C>>> pairlist;
    protected int putCount;
    protected final List<BitSet> red;
    protected final CReductionSeq<C> reduction;
    protected int remCount;
    protected final GenPolynomialRing<GenPolynomial<C>> ring;
    protected boolean useCriterion4;

    static {
        logger = Logger.getLogger(OrderedCPairlist.class);
    }

    public OrderedCPairlist(GenPolynomialRing<GenPolynomial<C>> r) {
        this(0, r);
    }

    public OrderedCPairlist(int m, GenPolynomialRing<GenPolynomial<C>> r) {
        this.oneInGB = false;
        this.useCriterion4 = false;
        this.moduleVars = m;
        this.ring = r;
        this.P = new ArrayList();
        this.pairlist = new TreeMap(this.ring.tord.getAscendComparator());
        this.red = new ArrayList();
        this.putCount = 0;
        this.remCount = 0;
        if (this.ring instanceof GenSolvablePolynomialRing) {
            this.useCriterion4 = false;
        }
        this.reduction = new CReductionSeq(((GenPolynomialRing) this.ring.coFac).coFac);
    }

    private OrderedCPairlist(int m, GenPolynomialRing<GenPolynomial<C>> ring, List<ColorPolynomial<C>> P, SortedMap<ExpVector, LinkedList<CPair<C>>> pl, List<BitSet> red, CReductionSeq<C> cred, int pc, int rc) {
        this.oneInGB = false;
        this.useCriterion4 = false;
        this.moduleVars = m;
        this.ring = ring;
        this.P = P;
        this.pairlist = pl;
        this.red = red;
        this.reduction = cred;
        this.putCount = pc;
        this.remCount = rc;
    }

    public synchronized OrderedCPairlist<C> copy() {
        return new OrderedCPairlist(this.moduleVars, this.ring, new ArrayList(this.P), clonePairlist(), cloneBitSet(), this.reduction, this.putCount, this.remCount);
    }

    private SortedMap<ExpVector, LinkedList<CPair<C>>> clonePairlist() {
        SortedMap<ExpVector, LinkedList<CPair<C>>> pl = new TreeMap(this.ring.tord.getAscendComparator());
        for (Entry<ExpVector, LinkedList<CPair<C>>> m : this.pairlist.entrySet()) {
            pl.put((ExpVector) m.getKey(), new LinkedList((LinkedList) m.getValue()));
        }
        return pl;
    }

    public int pairCount() {
        int c = 0;
        for (Entry<ExpVector, LinkedList<CPair<C>>> m : this.pairlist.entrySet()) {
            c += ((LinkedList) m.getValue()).size();
        }
        return c;
    }

    private List<BitSet> cloneBitSet() {
        List<BitSet> r = new ArrayList(this.red.size());
        for (BitSet b : this.red) {
            r.add((BitSet) b.clone());
        }
        return r;
    }

    public int bitCount() {
        int c = 0;
        for (BitSet b : this.red) {
            c += b.cardinality();
        }
        return c;
    }

    public String toString() {
        int p = pairCount();
        int b = bitCount();
        if (p != b) {
            return "OrderedCPairlist( pairCount=" + p + ", bitCount=" + b + ", putCount=" + this.putCount + ", remCount=" + this.remCount + " )";
        }
        return "OrderedCPairlist( pairCount=" + p + ", putCount=" + this.putCount + ", remCount=" + this.remCount + " )";
    }

    public boolean equals(Object ob) {
        try {
            OrderedCPairlist<C> c = (OrderedCPairlist) ob;
            if (c == null) {
                return false;
            }
            boolean t = getList().equals(c.getList());
            if (!t) {
                return t;
            }
            if (pairCount() == c.pairCount()) {
                t = true;
            } else {
                t = false;
            }
            if (t) {
                return true;
            }
            return t;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        return (getList().hashCode() << 7) + pairCount();
    }

    public synchronized int put(ColorPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else {
            ExpVector e = p.leadingExpVector();
            int l = this.P.size();
            for (int j = 0; j < l; j++) {
                ColorPolynomial<C> pj = (ColorPolynomial) this.P.get(j);
                ExpVector f = pj.leadingExpVector();
                if (this.moduleVars <= 0 || e.invLexCompareTo(f, 0, this.moduleVars) == 0) {
                    ExpVector g = e.lcm(f);
                    CPair<C> pair = new CPair(pj, p, j, l);
                    LinkedList<CPair<C>> xl = (LinkedList) this.pairlist.get(g);
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

    public synchronized CPair<C> removeNext() {
        CPair<C> cPair;
        if (this.oneInGB) {
            cPair = null;
        } else {
            Iterator<Entry<ExpVector, LinkedList<CPair<C>>>> ip = this.pairlist.entrySet().iterator();
            cPair = null;
            boolean c = false;
            while (!c && ip.hasNext()) {
                Entry<ExpVector, LinkedList<CPair<C>>> me = (Entry) ip.next();
                ExpVector g = (ExpVector) me.getKey();
                LinkedList<CPair<C>> xl = (LinkedList) me.getValue();
                if (logger.isInfoEnabled()) {
                    logger.info("g  = " + g);
                }
                cPair = null;
                while (!c && xl.size() > 0) {
                    cPair = (CPair) xl.removeFirst();
                    c = true;
                    ((BitSet) this.red.get(cPair.j)).clear(cPair.i);
                }
                if (xl.size() == 0) {
                    ip.remove();
                }
            }
            if (c) {
                this.remCount++;
            } else {
                cPair = null;
            }
        }
        return cPair;
    }

    public synchronized boolean hasNext() {
        return this.pairlist.size() > 0;
    }

    public List<ColorPolynomial<C>> getList() {
        return this.P;
    }

    public synchronized int putCount() {
        return this.putCount;
    }

    public synchronized int remCount() {
        return this.remCount;
    }

    public synchronized int putOne(ColorPolynomial<C> one) {
        int size;
        this.putCount++;
        if (one == null) {
            size = this.P.size() - 1;
        } else if (one.isONE()) {
            this.oneInGB = true;
            this.pairlist.clear();
            this.P.clear();
            this.P.add(one);
            this.red.clear();
            size = this.P.size() - 1;
        } else {
            size = this.P.size() - 1;
        }
        return size;
    }

    public boolean criterion3(int i, int j, ExpVector eij) {
        boolean s = ((BitSet) this.red.get(j)).get(i);
        if (s) {
            int k = 0;
            while (k < this.P.size()) {
                if (!(i == k || j == k || !eij.multipleOf(((ColorPolynomial) this.P.get(k)).leadingExpVector()))) {
                    if (k < i) {
                        s = ((BitSet) this.red.get(i)).get(k) || ((BitSet) this.red.get(j)).get(k);
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
