package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.Overlap;
import edu.jas.poly.OverlapList;
import edu.jas.poly.Word;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public abstract class WordReductionAbstract<C extends RingElem<C>> implements WordReduction<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(WordReductionAbstract.class);
    }

    public List<GenWordPolynomial<C>> SPolynomials(GenWordPolynomial<C> Ap, GenWordPolynomial<C> Bp) {
        List<GenWordPolynomial<C>> sp = new ArrayList();
        if (Bp == null || Bp.isZERO()) {
            if (Ap == null) {
                sp.add(Bp);
            } else {
                sp.add(Ap.ring.getZERO());
            }
        } else if (Ap == null || Ap.isZERO()) {
            sp.add(Bp.ring.getZERO());
        } else {
            Entry<Word, C> ma = Ap.leadingMonomial();
            Entry<Word, C> mb = Bp.leadingMonomial();
            RingElem a = (RingElem) ma.getValue();
            RingElem b = (RingElem) mb.getValue();
            OverlapList oll = ((Word) ma.getKey()).overlap((Word) mb.getKey());
            if (!oll.ols.isEmpty()) {
                for (Overlap ol : oll.ols) {
                    sp.add(SPolynomial(ol, b, Ap, a, Bp));
                }
            }
        }
        return sp;
    }

    public GenWordPolynomial<C> SPolynomial(C a, Word l1, GenWordPolynomial<C> A, Word r1, C b, Word l2, GenWordPolynomial<C> B, Word r2) {
        RingElem one = (RingElem) A.ring.coFac.getONE();
        return A.multiply(a, l1, one, r1).subtract(B.multiply(b, l2, one, r2));
    }

    public GenWordPolynomial<C> SPolynomial(Overlap ol, C a, GenWordPolynomial<C> A, C b, GenWordPolynomial<C> B) {
        RingElem one = (RingElem) A.ring.coFac.getONE();
        return A.multiply(a, ol.l1, one, ol.r1).subtract(B.multiply(b, ol.l2, one, ol.r2));
    }

    public List<GenWordPolynomial<C>> normalform(List<GenWordPolynomial<C>> Pp, List<GenWordPolynomial<C>> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isEmpty()) {
            return Ap;
        }
        ArrayList<GenWordPolynomial<C>> red = new ArrayList();
        for (GenWordPolynomial<C> A : Ap) {
            red.add(normalform((List) Pp, (GenWordPolynomial) A));
        }
        return red;
    }

    public boolean isTopReducible(List<GenWordPolynomial<C>> P, GenWordPolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        Word e = A.leadingWord();
        for (GenWordPolynomial<C> p : P) {
            if (e.multipleOf(p.leadingWord())) {
                return true;
            }
        }
        return false;
    }

    public boolean isReducible(List<GenWordPolynomial<C>> Pp, GenWordPolynomial<C> Ap) {
        return !isNormalform(Pp, Ap);
    }

    public boolean isNormalform(List<GenWordPolynomial<C>> Pp, GenWordPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        if (Ap == null || Ap.isZERO()) {
            return true;
        }
        int l;
        synchronized (Pp) {
            int i;
            l = Pp.size();
            GenWordPolynomial<C>[] P = new GenWordPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (GenWordPolynomial) Pp.get(i);
            }
        }
        Word[] htl = new Word[l];
        GenWordPolynomial<C>[] p = new GenWordPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<Word, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (Word) m.getKey();
                j++;
            }
        }
        l = j;
        for (Word e : Ap.getMap().keySet()) {
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean isNormalform(List<GenWordPolynomial<C>> Pp) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        List<GenWordPolynomial<C>> P = new LinkedList(Pp);
        int s = P.size();
        for (int i = 0; i < s; i++) {
            GenWordPolynomial<C> Ap = (GenWordPolynomial) P.remove(i);
            if (!isNormalform(P, Ap)) {
                return false;
            }
            P.add(Ap);
        }
        return true;
    }

    public List<GenWordPolynomial<C>> irreducibleSet(List<GenWordPolynomial<C>> Pp) {
        ArrayList<GenWordPolynomial<C>> P = new ArrayList();
        for (GenWordPolynomial<C> a : Pp) {
            GenWordPolynomial<C> a2;
            if (a2.length() != 0) {
                a2 = a2.monic();
                if (a2.isONE()) {
                    P.clear();
                    P.add(a2);
                    return P;
                }
                P.add(a2);
            }
        }
        int l = P.size();
        if (l <= 1) {
            return P;
        }
        int irr = 0;
        logger.debug("irr = ");
        while (irr != l) {
            a2 = (GenWordPolynomial) P.remove(0);
            Word e = a2.leadingWord();
            a2 = normalform((List) P, (GenWordPolynomial) a2);
            logger.debug(String.valueOf(irr));
            if (a2.length() == 0) {
                l--;
                if (l <= 1) {
                    return P;
                }
            } else {
                Word f = a2.leadingWord();
                if (f.signum() == 0) {
                    P = new ArrayList();
                    P.add(a2.monic());
                    return P;
                }
                if (e.equals(f)) {
                    irr++;
                } else {
                    irr = 0;
                    a2 = a2.monic();
                }
                P.add(a2);
            }
        }
        return P;
    }

    public boolean isReductionNF(List<GenWordPolynomial<C>> lrow, List<GenWordPolynomial<C>> rrow, List<GenWordPolynomial<C>> Pp, GenWordPolynomial<C> Ap, GenWordPolynomial<C> Np) {
        if (lrow == null && rrow == null && Pp == null) {
            if (Ap != null) {
                return Ap.equals(Np);
            }
            if (Np == null) {
                return true;
            }
            return false;
        } else if (lrow == null || rrow == null || Pp == null) {
            return false;
        } else {
            if (lrow.size() != Pp.size() || rrow.size() != Pp.size()) {
                return false;
            }
            GenWordPolynomial<C> t = Np;
            for (int m = 0; m < Pp.size(); m++) {
                GenWordPolynomial rl = (GenWordPolynomial) lrow.get(m);
                GenWordPolynomial rr = (GenWordPolynomial) rrow.get(m);
                GenWordPolynomial<C> p = (GenWordPolynomial) Pp.get(m);
                if (!(rl == null || rr == null || p == null)) {
                    if (t == null) {
                        t = p.multiply(rl, rr);
                    } else {
                        t = t.sum(p.multiply(rl, rr));
                    }
                }
            }
            if (t != null) {
                GenWordPolynomial<C> r = t.subtract((GenWordPolynomial) Ap);
                boolean z = r.isZERO();
                if (!z) {
                    logger.info("t = " + t);
                    logger.info("a = " + Ap);
                    logger.info("t-a = " + r);
                }
                return z;
            } else if (Ap == null) {
                return true;
            } else {
                return Ap.isZERO();
            }
        }
    }
}
