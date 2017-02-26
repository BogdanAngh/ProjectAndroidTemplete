package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.GenWordPolynomialRing;
import edu.jas.poly.Word;
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

public class OrderedWordPairlist<C extends RingElem<C>> implements WordPairList<C> {
    private static final Logger logger;
    protected final List<GenWordPolynomial<C>> P;
    protected boolean oneInGB;
    protected final SortedMap<Word, LinkedList<WordPair<C>>> pairlist;
    protected int putCount;
    protected final List<BitSet> red;
    protected final WordReduction<C> reduction;
    protected int remCount;
    protected final GenWordPolynomialRing<C> ring;

    static {
        logger = Logger.getLogger(OrderedWordPairlist.class);
    }

    public OrderedWordPairlist() {
        this.oneInGB = false;
        this.ring = null;
        this.P = null;
        this.pairlist = null;
        this.red = null;
        this.reduction = null;
        this.putCount = 0;
        this.remCount = 0;
    }

    public OrderedWordPairlist(GenWordPolynomialRing<C> r) {
        this.oneInGB = false;
        this.ring = r;
        this.P = new ArrayList();
        this.pairlist = new TreeMap(this.ring.alphabet.getAscendComparator());
        this.red = new ArrayList();
        this.putCount = 0;
        this.remCount = 0;
        this.reduction = new WordReductionSeq();
    }

    public WordPairList<C> create(GenWordPolynomialRing<C> r) {
        return new OrderedWordPairlist(r);
    }

    public String toString() {
        StringBuffer s = new StringBuffer(getClass().getSimpleName() + "(");
        s.append("#put=" + this.putCount);
        s.append(", #rem=" + this.remCount);
        if (!(this.pairlist == null || this.pairlist.size() == 0)) {
            s.append(", size=" + this.pairlist.size());
        }
        s.append(")");
        return s.toString();
    }

    public synchronized int put(GenWordPolynomial<C> p) {
        int size;
        this.putCount++;
        if (this.oneInGB) {
            size = this.P.size() - 1;
        } else if (p.isONE()) {
            size = putOne();
        } else {
            Word e = p.leadingWord();
            size = this.P.size();
            BitSet redi = new BitSet();
            for (int j = 0; j < size; j++) {
                WordPair<C> pair;
                LinkedList<WordPair<C>> xl;
                GenWordPolynomial<C> pj = (GenWordPolynomial) this.P.get(j);
                Word f = pj.leadingWord();
                Word g = f.lcm(e);
                if (g != null) {
                    pair = new WordPair(pj, p, j, size);
                    xl = (LinkedList) this.pairlist.get(g);
                    if (xl == null) {
                        xl = new LinkedList();
                    }
                    xl.addFirst(pair);
                    this.pairlist.put(g, xl);
                    redi.set(j);
                }
                g = e.lcm(f);
                if (g != null) {
                    pair = new WordPair(p, pj, size, j);
                    xl = (LinkedList) this.pairlist.get(g);
                    if (xl == null) {
                        xl = new LinkedList();
                    }
                    xl.addFirst(pair);
                    this.pairlist.put(g, xl);
                    ((BitSet) this.red.get(j)).set(size);
                }
            }
            this.red.add(redi);
            this.P.add(p);
        }
        return size;
    }

    public int put(List<GenWordPolynomial<C>> F) {
        int i = 0;
        for (GenWordPolynomial p : F) {
            i = put(p);
        }
        return i;
    }

    public synchronized WordPair<C> removeNext() {
        WordPair<C> wordPair;
        if (this.oneInGB) {
            wordPair = null;
        } else {
            Iterator<Entry<Word, LinkedList<WordPair<C>>>> ip = this.pairlist.entrySet().iterator();
            wordPair = null;
            if (ip.hasNext()) {
                Entry<Word, LinkedList<WordPair<C>>> me = (Entry) ip.next();
                Word g = (Word) me.getKey();
                LinkedList<WordPair<C>> xl = (LinkedList) me.getValue();
                if (logger.isInfoEnabled()) {
                    logger.info("g  = " + g);
                }
                wordPair = null;
                if (xl.size() > 0) {
                    wordPair = (WordPair) xl.removeFirst();
                    ((BitSet) this.red.get(wordPair.j)).clear(wordPair.i);
                }
                if (xl.size() == 0) {
                    ip.remove();
                }
            }
            this.remCount++;
        }
        return wordPair;
    }

    public synchronized boolean hasNext() {
        return this.pairlist.size() > 0;
    }

    public List<GenWordPolynomial<C>> getList() {
        return this.P;
    }

    public synchronized int putCount() {
        return this.putCount;
    }

    public synchronized int remCount() {
        return this.remCount;
    }

    public synchronized int putOne(GenWordPolynomial<C> one) {
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
        return this.P.size() - 1;
    }

    public boolean criterion3(int i, int j, Word eij) {
        boolean s = ((BitSet) this.red.get(j)).get(i);
        int k = 0;
        while (k < this.P.size()) {
            if (!(i == k || j == k || !eij.multipleOf(((GenWordPolynomial) this.P.get(k)).leadingWord()))) {
                if (i < j) {
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
                } else if (k < j) {
                    s = ((BitSet) this.red.get(k)).get(j) || ((BitSet) this.red.get(k)).get(i);
                } else if (j < k && k < i) {
                    s = ((BitSet) this.red.get(j)).get(k) || ((BitSet) this.red.get(k)).get(i);
                } else if (i < k) {
                    s = ((BitSet) this.red.get(j)).get(k) || ((BitSet) this.red.get(i)).get(k);
                }
                if (!s) {
                    return s;
                }
            }
            k++;
        }
        return true;
    }
}
