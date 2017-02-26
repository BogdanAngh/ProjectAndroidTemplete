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

public class OrderedSyzPairlist<C extends RingElem<C>> extends OrderedPairlist<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(OrderedSyzPairlist.class);
    }

    public OrderedSyzPairlist(GenPolynomialRing<C> r) {
        this(0, r);
    }

    public OrderedSyzPairlist(int m, GenPolynomialRing<C> r) {
        super(m, r);
    }

    public PairList<C> create(GenPolynomialRing<C> r) {
        return new OrderedSyzPairlist(r);
    }

    public PairList<C> create(int m, GenPolynomialRing<C> r) {
        return new OrderedSyzPairlist(m, r);
    }

    public synchronized int put(GenPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else {
            ExpVector g;
            LinkedList<Pair<C>> ll;
            Iterator i$;
            Pair<C> pair;
            List list;
            int i;
            ExpVector ei;
            LinkedList linkedList;
            LinkedList<Pair<C>> exl;
            ExpVector e = p.leadingExpVector();
            int ps = this.P.size();
            BitSet redi = new BitSet();
            this.red.add(redi);
            this.P.add(p);
            List<ExpVector> es = new ArrayList();
            for (Entry<ExpVector, LinkedList<Pair<C>>> me : this.pairlist.entrySet()) {
                g = (ExpVector) me.getKey();
                if (this.moduleVars > 0) {
                    if (!this.reduction.moduleCriterion(this.moduleVars, e, g)) {
                        continue;
                    }
                }
                ll = (LinkedList) me.getValue();
                if (g.compareTo(g.lcm(e)) == 0) {
                    LinkedList<Pair<C>> lle = new LinkedList();
                    i$ = ll.iterator();
                    while (i$.hasNext()) {
                        pair = (Pair) i$.next();
                        if (g.compareTo(pair.pi.leadingExpVector().lcm(e)) != 0) {
                            if (g.compareTo(pair.pj.leadingExpVector().lcm(e)) != 0) {
                                list = this.red;
                                i = pair.j;
                                ((BitSet) r0.get(r0)).clear(pair.i);
                                lle.add(pair);
                            }
                        }
                    }
                    if (lle.size() > 0) {
                        i$ = lle.iterator();
                        while (i$.hasNext()) {
                            ll.remove((Pair) i$.next());
                        }
                        if (!es.contains(g)) {
                            es.add(g);
                        }
                    }
                } else {
                    continue;
                }
            }
            for (ExpVector ei2 : es) {
                ll = (LinkedList) this.pairlist.get(ei2);
                if (ll != null && ll.size() == 0) {
                    linkedList = (LinkedList) this.pairlist.remove(ei2);
                }
            }
            SortedMap<ExpVector, LinkedList<Pair<C>>> treeMap = new TreeMap(this.ring.tord.getAscendComparator());
            for (int j = 0; j < ps; j++) {
                GenPolynomial<C> pj = (GenPolynomial) this.P.get(j);
                ExpVector f = pj.leadingExpVector();
                if (this.moduleVars > 0) {
                    if (!this.reduction.moduleCriterion(this.moduleVars, e, f)) {
                    }
                }
                g = e.lcm(f);
                Pair<C> pair2 = new Pair((GenPolynomial) pj, (GenPolynomial) p, j, ps);
                LinkedList<Pair<C>> xl = (LinkedList) treeMap.get(g);
                if (xl == null) {
                    xl = new LinkedList();
                }
                xl.addFirst(pair2);
                treeMap.put(g, xl);
            }
            es = new ArrayList(treeMap.size());
            for (ExpVector eil : treeMap.keySet()) {
                for (ExpVector ejl : treeMap.keySet()) {
                    if (!(eil.compareTo(ejl) == 0 || !eil.multipleOf(ejl) || es.contains(eil))) {
                        es.add(eil);
                    }
                }
            }
            for (ExpVector ei22 : es) {
                linkedList = (LinkedList) treeMap.remove(ei22);
            }
            if (this.useCriterion4) {
                es = new ArrayList(treeMap.size());
                for (Entry<ExpVector, LinkedList<Pair<C>>> me2 : treeMap.entrySet()) {
                    ei22 = (ExpVector) me2.getKey();
                    exl = (LinkedList) me2.getValue();
                    boolean c = true;
                    i$ = exl.iterator();
                    while (i$.hasNext()) {
                        pair = (Pair) i$.next();
                        if (c) {
                            if (this.reduction.criterion4(pair.pi, pair.pj, pair.e)) {
                                c = true;
                            }
                        }
                        c = false;
                    }
                    if (c) {
                        if (exl.size() > 1) {
                            pair = (Pair) exl.getFirst();
                            exl.clear();
                            exl.add(pair);
                        }
                    } else if (!es.contains(ei22)) {
                        es.add(ei22);
                    }
                }
                for (ExpVector ei222 : es) {
                    linkedList = (LinkedList) treeMap.remove(ei222);
                }
            }
            for (Entry<ExpVector, LinkedList<Pair<C>>> me22 : treeMap.entrySet()) {
                ei222 = (ExpVector) me22.getKey();
                exl = (LinkedList) me22.getValue();
                i$ = exl.iterator();
                while (i$.hasNext()) {
                    pair = (Pair) i$.next();
                    list = this.red;
                    i = pair.j;
                    ((BitSet) r0.get(r0)).set(pair.i);
                }
                LinkedList<Pair<C>> ex = (LinkedList) this.pairlist.get(ei222);
                if (ex != null) {
                    exl.addAll(ex);
                    ex = exl;
                } else {
                    ex = exl;
                }
                this.pairlist.put(ei222, ex);
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
            while (ip.hasNext()) {
                Entry<ExpVector, LinkedList<Pair<C>>> me = (Entry) ip.next();
                ExpVector g = (ExpVector) me.getKey();
                LinkedList<Pair<C>> xl = (LinkedList) me.getValue();
                if (logger.isInfoEnabled()) {
                    logger.info("g  = " + g);
                }
                pair = null;
                while (xl.size() > 0) {
                    pair = (Pair) xl.removeFirst();
                    int i = pair.i;
                    int j = pair.j;
                    if (((BitSet) this.red.get(j)).get(i)) {
                        ((BitSet) this.red.get(j)).clear(i);
                        break;
                    }
                    System.out.println("c_red.get(" + j + ").get(" + i + ") = " + g);
                    pair = null;
                }
                if (xl.size() == 0) {
                    ip.remove();
                    continue;
                }
                if (pair != null) {
                    break;
                }
            }
            if (pair != null) {
                pair.maxIndex(this.P.size() - 1);
                this.remCount++;
                if (logger.isDebugEnabled()) {
                    logger.info("pair(" + pair.j + "," + pair.i + ")");
                }
            }
        }
        return pair;
    }

    public boolean criterion3(int i, int j, ExpVector eij) {
        throw new UnsupportedOperationException("not used in " + getClass().getName());
    }
}
