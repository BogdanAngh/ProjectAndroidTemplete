package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RingElem;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class OrderedMinPairlist<C extends RingElem<C>> extends OrderedPairlist<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(OrderedMinPairlist.class);
    }

    public OrderedMinPairlist(GenPolynomialRing<C> r) {
        this(0, r);
    }

    public OrderedMinPairlist(int m, GenPolynomialRing<C> r) {
        super(m, r);
    }

    public PairList<C> create(GenPolynomialRing<C> r) {
        return new OrderedMinPairlist(r);
    }

    public PairList<C> create(int m, GenPolynomialRing<C> r) {
        return new OrderedMinPairlist(m, r);
    }

    public synchronized int put(GenPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else {
            ExpVector e = p.leadingExpVector();
            int l = this.P.size();
            BitSet redi = new BitSet();
            redi.set(0, l);
            this.red.add(redi);
            this.P.add(p);
            for (int j = 0; j < l; j++) {
                GenPolynomial pj = (GenPolynomial) this.P.get(j);
                ExpVector f = pj.leadingExpVector();
                if (this.moduleVars <= 0 || this.reduction.moduleCriterion(this.moduleVars, e, f)) {
                    ExpVector g = e.lcm(f);
                    Pair<C> pair = new Pair(pj, (GenPolynomial) p, j, l);
                    boolean c = true;
                    if (this.useCriterion4) {
                        c = this.reduction.criterion4(pair.pi, pair.pj, g);
                    }
                    if (c) {
                        c = criterion3(j, l, g);
                    }
                    if (c) {
                        LinkedList<Pair<C>> xl = (LinkedList) this.pairlist.get(g);
                        if (xl == null) {
                            xl = new LinkedList();
                        }
                        xl.addFirst(pair);
                        this.pairlist.put(g, xl);
                    } else {
                        ((BitSet) this.red.get(j)).clear(l);
                    }
                } else {
                    ((BitSet) this.red.get(j)).clear(l);
                }
            }
            size = this.P.size() - 1;
        }
        return size;
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
                    if (((BitSet) this.red.get(j)).get(i)) {
                        c = true;
                        if (this.useCriterion4) {
                            c = this.reduction.criterion4(pair.pi, pair.pj, g);
                        }
                        if (c) {
                            c = criterion3(i, j, g);
                        }
                        if (c) {
                            ((BitSet) this.red.get(j)).clear(i);
                        } else {
                            ((BitSet) this.red.get(j)).clear(i);
                        }
                    } else {
                        System.out.println("c_y = " + g);
                    }
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

    public boolean criterion3(int i, int j, ExpVector eij) {
        boolean s = ((BitSet) this.red.get(j)).get(i);
        if (s) {
            s = true;
            int k = 0;
            while (k < this.P.size()) {
                boolean m;
                ExpVector ek = ((GenPolynomial) this.P.get(k)).leadingExpVector();
                if (!eij.multipleOf(ek) || eij.compareTo(ek) == 0) {
                    m = false;
                } else {
                    m = true;
                }
                if (m) {
                    if (k < i) {
                        if (((BitSet) this.red.get(i)).get(k) || ((BitSet) this.red.get(j)).get(k)) {
                            s = true;
                        } else {
                            s = false;
                        }
                    }
                    if (i < k && k < j) {
                        if (((BitSet) this.red.get(k)).get(i) || ((BitSet) this.red.get(j)).get(k)) {
                            s = true;
                        } else {
                            s = false;
                        }
                    }
                    if (j < k) {
                        if (((BitSet) this.red.get(k)).get(i) || ((BitSet) this.red.get(k)).get(j)) {
                            s = true;
                        } else {
                            s = false;
                        }
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
