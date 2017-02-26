package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.GenWordPolynomialRing;
import edu.jas.poly.Word;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public abstract class WordGroebnerBaseAbstract<C extends RingElem<C>> implements WordGroebnerBase<C> {
    private static final Logger logger;
    private final boolean debug;
    public final WordReduction<C> red;
    public final WordPairList<C> strategy;

    public abstract List<GenWordPolynomial<C>> GB(List<GenWordPolynomial<C>> list);

    static {
        logger = Logger.getLogger(WordGroebnerBaseAbstract.class);
    }

    public WordGroebnerBaseAbstract() {
        this(new WordReductionSeq());
    }

    public WordGroebnerBaseAbstract(WordReduction<C> red) {
        this(red, new OrderedWordPairlist());
    }

    public WordGroebnerBaseAbstract(WordReduction<C> red, WordPairList<C> pl) {
        this.debug = logger.isDebugEnabled();
        this.red = red;
        this.strategy = pl;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    public List<GenWordPolynomial<C>> normalizeZerosOnes(List<GenWordPolynomial<C>> A) {
        List<GenWordPolynomial<C>> N = new ArrayList(A.size());
        if (!(A == null || A.isEmpty())) {
            for (GenWordPolynomial<C> p : A) {
                if (!(p == null || p.isZERO())) {
                    if (p.isUnit()) {
                        N.clear();
                        N.add(p.ring.getONE());
                        break;
                    }
                    N.add(p.abs());
                }
            }
        }
        return N;
    }

    public int commonZeroTest(List<GenWordPolynomial<C>> F) {
        if (F == null || F.isEmpty()) {
            return 1;
        }
        GenWordPolynomialRing<C> pfac = ((GenWordPolynomial) F.get(0)).ring;
        if (pfac.alphabet.length() <= 0) {
            return -1;
        }
        Set<String> v = new HashSet();
        for (GenWordPolynomial<C> p : F) {
            if (!p.isZERO()) {
                if (p.isConstant()) {
                    return -1;
                }
                Word e = p.leadingWord();
                if (e != null) {
                    SortedMap<String, Integer> u = e.dependencyOnVariables();
                    if (u != null && u.size() == 1) {
                        v.add(u.firstKey());
                    }
                }
            }
        }
        if (pfac.alphabet.length() == v.size()) {
            return 0;
        }
        return 1;
    }

    public List<Long> univariateDegrees(List<GenWordPolynomial<C>> A) {
        List<Long> ud = new ArrayList();
        if (!(A == null || A.size() == 0 || ((GenWordPolynomial) A.get(0)).ring.alphabet.length() <= 0)) {
            SortedMap<String, Long> v = new TreeMap();
            for (GenWordPolynomial<C> p : A) {
                Word e = p.leadingWord();
                if (e != null) {
                    SortedMap<String, Integer> u = e.dependencyOnVariables();
                    if (u != null && u.size() == 1 && ((Long) v.get(u.firstKey())) == null) {
                        v.put(u.firstKey(), Long.valueOf((long) ((Integer) u.get(u.firstKey())).intValue()));
                    }
                }
            }
            ud.addAll(v.values());
        }
        return ud;
    }

    public boolean isGB(List<GenWordPolynomial<C>> F) {
        if (F == null || F.size() <= 1) {
            return true;
        }
        for (int i = 0; i < F.size(); i++) {
            GenWordPolynomial<C> pi = (GenWordPolynomial) F.get(i);
            for (int j = i + 1; j < F.size(); j++) {
                GenWordPolynomial<C> pj = (GenWordPolynomial) F.get(j);
                for (GenWordPolynomial s : this.red.SPolynomials(pi, pj)) {
                    GenWordPolynomial<C> h = this.red.normalform((List) F, s);
                    if (!h.isZERO()) {
                        logger.info("no GB: pi = " + pi + ", pj = " + pj);
                        logger.info("s  = " + s + ", h = " + h);
                        return false;
                    }
                }
                for (GenWordPolynomial s2 : this.red.SPolynomials(pj, pi)) {
                    h = this.red.normalform((List) F, s2);
                    if (!h.isZERO()) {
                        logger.info("no GB: pj = " + pj + ", pi = " + pi);
                        logger.info("s  = " + s2 + ", h = " + h);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public List<GenWordPolynomial<C>> minimalGB(List<GenWordPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() <= 1) {
            return Gp;
        }
        List<GenWordPolynomial<C>> G = new ArrayList(Gp.size());
        for (GenWordPolynomial<C> a : Gp) {
            GenWordPolynomial<C> a2;
            if (!(a2 == null || a2.isZERO())) {
                G.add(a2);
            }
        }
        if (G.size() <= 1) {
            return G;
        }
        List<GenWordPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            GenWordPolynomial a3 = (GenWordPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a3) && !this.red.isTopReducible(F, a3)) {
                F.add(a3);
            } else if (this.debug) {
                System.out.println("dropped " + a3);
                List ff = new ArrayList(G);
                ff.addAll(F);
                a2 = this.red.normalform(ff, a3);
                if (!a2.isZERO()) {
                    System.out.println("error, nf(a) " + a2);
                }
            }
        }
        List<GenWordPolynomial<C>> G2 = F;
        if (G2.size() <= 1) {
            return G2;
        }
        Collections.reverse(G2);
        int len = G2.size();
        if (this.debug) {
            System.out.println("#G " + len);
            for (GenWordPolynomial<C> aa : G2) {
                System.out.println("aa = " + aa.length() + ", lt = " + aa.getMap().keySet());
            }
        }
        for (int i = 0; i < len; i++) {
            a3 = (GenWordPolynomial) G2.remove(0);
            if (this.debug) {
                System.out.println("doing " + a3.length() + ", lt = " + a3.leadingWord());
            }
            G2.add(this.red.normalform((List) G2, a3));
        }
        return G2;
    }

    public boolean isMinimalGB(List<GenWordPolynomial<C>> Gp) {
        if (Gp == null || Gp.size() == 0) {
            return true;
        }
        GenWordPolynomial<C> a;
        for (GenWordPolynomial<C> a2 : Gp) {
            if (a2 != null) {
                if (a2.isZERO()) {
                }
            }
            if (!this.debug) {
                return false;
            }
            logger.debug("zero polynomial " + a2);
            return false;
        }
        List<GenWordPolynomial<C>> G = new ArrayList(Gp);
        List<GenWordPolynomial<C>> F = new ArrayList(G.size());
        while (G.size() > 0) {
            a2 = (GenWordPolynomial) G.remove(0);
            if (!this.red.isTopReducible(G, a2) && !this.red.isTopReducible(F, a2)) {
                F.add(a2);
            } else if (!this.debug) {
                return false;
            } else {
                logger.debug("top reducible polynomial " + a2);
                return false;
            }
        }
        G = F;
        if (G.size() <= 1) {
            return true;
        }
        int len = G.size();
        int i = 0;
        while (i < len) {
            a2 = (GenWordPolynomial) G.remove(0);
            if (this.red.isNormalform(G, a2)) {
                G.add(a2);
                i++;
            } else if (!this.debug) {
                return false;
            } else {
                logger.debug("reducible polynomial " + a2);
                return false;
            }
        }
        return true;
    }

    public void terminate() {
        logger.info("terminate not implemented");
    }

    public int cancel() {
        logger.info("cancel not implemented");
        return 0;
    }
}
