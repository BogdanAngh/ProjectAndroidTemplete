package edu.jas.gbufd;

import edu.jas.gb.OrderedPairlist;
import edu.jas.gb.Pair;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.RegularRingElem;
import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class OrderedRPairlist<C extends RegularRingElem<C>> extends OrderedPairlist<C> {
    private static final Logger logger;
    protected final RReduction<C> rreduction;

    static {
        logger = Logger.getLogger(OrderedRPairlist.class);
    }

    public OrderedRPairlist(GenPolynomialRing<C> r) {
        this(0, r);
    }

    public OrderedRPairlist(int m, GenPolynomialRing<C> r) {
        super(m, r);
        this.rreduction = new RReductionSeq();
    }

    public synchronized Pair<C> removeNext() {
        Pair<C> pair;
        if (this.oneInGB) {
            pair = null;
        } else {
            Iterator<Entry<ExpVector, LinkedList<Pair<C>>>> ip = this.pairlist.entrySet().iterator();
            pair = null;
            if (ip.hasNext()) {
                Entry<ExpVector, LinkedList<Pair<C>>> me = (Entry) ip.next();
                ExpVector g = (ExpVector) me.getKey();
                LinkedList<Pair<C>> xl = (LinkedList) me.getValue();
                if (logger.isInfoEnabled()) {
                    logger.info("g  = " + g);
                }
                pair = null;
                if (xl.size() > 0) {
                    boolean c;
                    pair = (Pair) xl.removeFirst();
                    int i = pair.i;
                    int j = pair.j;
                    if (this.useCriterion4) {
                        c = this.rreduction.criterion4(pair.pi, pair.pj, g);
                    } else {
                        c = true;
                    }
                    pair.setUseCriterion4(c);
                    if (c) {
                        pair.setUseCriterion3(criterion3(i, j, g));
                    }
                    ((BitSet) this.red.get(j)).clear(i);
                }
                if (xl.size() == 0) {
                    ip.remove();
                }
            }
            this.remCount++;
        }
        return pair;
    }

    public boolean criterion3(int i, int j, ExpVector eij) {
        boolean s = ((BitSet) this.red.get(j)).get(i);
        if (s) {
            s = true;
            RegularRingElem c = (RegularRingElem) ((RegularRingElem) ((GenPolynomial) this.P.get(i)).leadingBaseCoefficient()).multiply((RegularRingElem) ((GenPolynomial) this.P.get(j)).leadingBaseCoefficient());
            int k = 0;
            while (k < this.P.size()) {
                GenPolynomial<C> A = (GenPolynomial) this.P.get(k);
                boolean m = eij.multipleOf(A.leadingExpVector());
                if (m) {
                    m = ((RegularRingElem) c.multiply((RegularRingElem) A.leadingBaseCoefficient())).isZERO();
                }
                if (m) {
                    if (k < i) {
                        s = ((BitSet) this.red.get(i)).get(k) || ((BitSet) this.red.get(j)).get(k);
                    }
                    if (i < k && k < j) {
                        s = ((BitSet) this.red.get(k)).get(i) || ((BitSet) this.red.get(j)).get(k);
                    }
                    if (j < k) {
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
