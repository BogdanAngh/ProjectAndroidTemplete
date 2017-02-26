package edu.jas.poly;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.kern.PreemptingException;
import edu.jas.poly.WordFactory.WordComparator;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public final class GenWordPolynomial<C extends RingElem<C>> implements RingElem<GenWordPolynomial<C>>, Iterable<WordMonomial<C>> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final GenWordPolynomialRing<C> ring;
    final SortedMap<Word, C> val;

    static {
        boolean z;
        if (GenWordPolynomial.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        logger = Logger.getLogger(GenWordPolynomial.class);
    }

    private GenWordPolynomial(GenWordPolynomialRing<C> r, TreeMap<Word, C> t) {
        this.debug = logger.isDebugEnabled();
        this.ring = r;
        this.val = t;
        if (this.ring.checkPreempt && Thread.currentThread().isInterrupted()) {
            logger.debug("throw PreemptingException");
            throw new PreemptingException();
        }
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r) {
        this((GenWordPolynomialRing) r, new TreeMap(r.alphabet.getDescendComparator()));
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r, C c, Word e) {
        this(r);
        if (!c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r, C c) {
        this((GenWordPolynomialRing) r, (RingElem) c, r.wone);
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r, Word e) {
        this((GenWordPolynomialRing) r, (RingElem) r.coFac.getONE(), e);
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r, ExpVector e) {
        this((GenWordPolynomialRing) r, (RingElem) r.coFac.getONE(), r.alphabet.valueOf(e));
    }

    public GenWordPolynomial(GenWordPolynomialRing<C> r, C c, ExpVector e) {
        this((GenWordPolynomialRing) r, (RingElem) c, r.alphabet.valueOf(e));
    }

    protected GenWordPolynomial(GenWordPolynomialRing<C> r, SortedMap<Word, C> v) {
        this(r);
        this.val.putAll(v);
    }

    public GenWordPolynomialRing<C> factory() {
        return this.ring;
    }

    public GenWordPolynomial<C> copy() {
        return new GenWordPolynomial(this.ring, this.val);
    }

    public int length() {
        return this.val.size();
    }

    public SortedMap<Word, C> getMap() {
        return Collections.unmodifiableSortedMap(this.val);
    }

    public void doPutToMap(Word e, C c) {
        if (this.debug) {
            RingElem a = (RingElem) this.val.get(e);
            if (a != null) {
                logger.error("map entry exists " + e + " to " + a + " new " + c);
            }
        }
        if (!c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public void doRemoveFromMap(Word e, C c) {
        RingElem b = (RingElem) this.val.remove(e);
        if (this.debug && c != null && !c.equals(b)) {
            logger.error("map entry wrong " + e + " to " + c + " old " + b);
        }
    }

    public void doPutToMap(SortedMap<Word, C> vals) {
        for (Entry<Word, C> me : vals.entrySet()) {
            Word e = (Word) me.getKey();
            if (this.debug) {
                RingElem a = (RingElem) this.val.get(e);
                if (a != null) {
                    logger.error("map entry exists " + e + " to " + a + " new " + me.getValue());
                }
            }
            RingElem c = (RingElem) me.getValue();
            if (!c.isZERO()) {
                this.val.put(e, c);
            }
        }
    }

    public String toString() {
        if (isZERO()) {
            return Constants.ZERO;
        }
        if (isONE()) {
            return "1";
        }
        StringBuffer s = new StringBuffer();
        if (this.val.size() > 1) {
            s.append("( ");
        }
        boolean first = true;
        for (Entry<Word, C> m : this.val.entrySet()) {
            C c = (RingElem) m.getValue();
            if (first) {
                first = false;
            } else if (c.signum() < 0) {
                s.append(" - ");
                RingElem c2 = (RingElem) c.negate();
            } else {
                s.append(" + ");
            }
            Word e = (Word) m.getKey();
            if (!c.isONE() || e.isONE()) {
                if (null != null) {
                    s.append("( ");
                }
                String cs = c.toString();
                if (cs.indexOf("+") >= 0 || cs.indexOf("-") >= 0) {
                    s.append("( " + cs + " )");
                } else {
                    s.append(cs);
                }
                if (null != null) {
                    s.append(" )");
                }
                if (!e.isONE()) {
                    s.append(" ");
                }
            }
            s.append(e.toString());
        }
        if (this.val.size() > 1) {
            s.append(" )");
        }
        return s.toString();
    }

    public String toScript() {
        if (isZERO()) {
            return Constants.ZERO;
        }
        if (isONE()) {
            return "1";
        }
        StringBuffer s = new StringBuffer();
        if (this.val.size() > 1) {
            s.append("( ");
        }
        boolean first = true;
        for (Entry<Word, C> m : this.val.entrySet()) {
            C c = (RingElem) m.getValue();
            if (first) {
                first = false;
            } else if (c.signum() < 0) {
                s.append(" - ");
                RingElem c2 = (RingElem) c.negate();
            } else {
                s.append(" + ");
            }
            Word e = (Word) m.getKey();
            if (!c.isONE() || e.isONE()) {
                String cs = c.toScript();
                if (cs.indexOf("+") >= 0 || cs.indexOf("-") >= 0) {
                    s.append("( " + cs + " )");
                } else {
                    s.append(cs);
                }
                if (!e.isONE()) {
                    s.append(" * ");
                }
            }
            s.append(e.toScript());
        }
        if (this.val.size() > 1) {
            s.append(" )");
        }
        return s.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public boolean isZERO() {
        return this.val.size() == 0;
    }

    public boolean isONE() {
        if (this.val.size() != 1) {
            return false;
        }
        RingElem c = (RingElem) this.val.get(this.ring.wone);
        if (c != null) {
            return c.isONE();
        }
        return false;
    }

    public boolean isUnit() {
        if (this.val.size() != 1) {
            return false;
        }
        RingElem c = (RingElem) this.val.get(this.ring.wone);
        if (c != null) {
            return c.isUnit();
        }
        return false;
    }

    public boolean isConstant() {
        if (this.val.size() == 1 && ((RingElem) this.val.get(this.ring.wone)) != null) {
            return true;
        }
        return false;
    }

    public boolean isHomogeneous() {
        if (this.val.size() <= 1) {
            return true;
        }
        long deg = -1;
        for (Word e : this.val.keySet()) {
            if (deg < 0) {
                deg = e.degree();
            } else if (deg != e.degree()) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object B) {
        if (B != null && (B instanceof GenWordPolynomial) && compareTo((GenWordPolynomial) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() << 27) + this.val.hashCode();
    }

    public int compareTo(GenWordPolynomial<C> b) {
        if (b == null) {
            return 1;
        }
        SortedMap<Word, C> av = this.val;
        SortedMap<Word, C> bv = b.val;
        Iterator<Entry<Word, C>> ai = av.entrySet().iterator();
        Iterator<Entry<Word, C>> bi = bv.entrySet().iterator();
        int c = 0;
        while (ai.hasNext() && bi.hasNext()) {
            Entry<Word, C> aie = (Entry) ai.next();
            Entry<Word, C> bie = (Entry) bi.next();
            int s = ((Word) aie.getKey()).compareTo((Word) bie.getKey());
            if (s != 0) {
                return s;
            }
            if (c == 0) {
                c = ((RingElem) aie.getValue()).compareTo((RingElem) bie.getValue());
            }
        }
        if (ai.hasNext()) {
            return 1;
        }
        return bi.hasNext() ? -1 : c;
    }

    public int signum() {
        if (isZERO()) {
            return 0;
        }
        return ((RingElem) this.val.get((Word) this.val.firstKey())).signum();
    }

    public int numberOfVariables() {
        return this.ring.alphabet.length();
    }

    public Entry<Word, C> leadingMonomial() {
        if (this.val.size() == 0) {
            return null;
        }
        return (Entry) this.val.entrySet().iterator().next();
    }

    public Word leadingWord() {
        if (this.val.size() == 0) {
            return this.ring.wone;
        }
        return (Word) this.val.firstKey();
    }

    public Word trailingWord() {
        if (this.val.size() == 0) {
            return this.ring.wone;
        }
        return (Word) this.val.lastKey();
    }

    public C leadingBaseCoefficient() {
        if (this.val.size() == 0) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return (RingElem) this.val.get(this.val.firstKey());
    }

    public C trailingBaseCoefficient() {
        C c = (RingElem) this.val.get(this.ring.wone);
        if (c == null) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return c;
    }

    public C coefficient(Word e) {
        RingElem c = (RingElem) this.val.get(e);
        if (c == null) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return c;
    }

    public GenWordPolynomial<C> reductum() {
        if (this.val.size() <= 1) {
            return this.ring.getZERO();
        }
        Iterator<Word> ai = this.val.keySet().iterator();
        Word word = (Word) ai.next();
        return new GenWordPolynomial(this.ring, this.val.tailMap((Word) ai.next()));
    }

    public long degree() {
        if (this.val.size() == 0) {
            return 0;
        }
        long deg = 0;
        for (Word e : this.val.keySet()) {
            long d = e.degree();
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public C maxNorm() {
        C n = this.ring.getZEROCoefficient();
        for (RingElem c : this.val.values()) {
            C x = (RingElem) c.abs();
            if (n.compareTo(x) < 0) {
                n = x;
            }
        }
        return n;
    }

    public C sumNorm() {
        C n = this.ring.getZEROCoefficient();
        for (RingElem c : this.val.values()) {
            RingElem n2 = (RingElem) n.sum((RingElem) c.abs());
        }
        return n;
    }

    public GenWordPolynomial<C> sum(GenWordPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if ($assertionsDisabled || this.ring.alphabet == S.ring.alphabet) {
            GenWordPolynomial<C> n = copy();
            SortedMap<Word, C> nv = n.val;
            for (Entry<Word, C> me : S.val.entrySet()) {
                Word e = (Word) me.getKey();
                RingElem y = (RingElem) me.getValue();
                RingElem x = (RingElem) nv.get(e);
                if (x != null) {
                    x = (RingElem) x.sum(y);
                    if (x.isZERO()) {
                        nv.remove(e);
                    } else {
                        nv.put(e, x);
                    }
                } else {
                    nv.put(e, y);
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenWordPolynomial<C> sum(C a, Word e) {
        if (a == null || a.isZERO()) {
            return this;
        }
        GenWordPolynomial<C> n = copy();
        SortedMap<Word, C> nv = n.val;
        RingElem x = (RingElem) nv.get(e);
        if (x != null) {
            x = (RingElem) x.sum(a);
            if (x.isZERO()) {
                nv.remove(e);
            } else {
                nv.put(e, x);
            }
        } else {
            nv.put(e, a);
        }
        return n;
    }

    public GenWordPolynomial<C> sum(C a) {
        return sum(a, this.ring.wone);
    }

    public GenWordPolynomial<C> subtract(GenWordPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.negate();
        }
        if ($assertionsDisabled || this.ring.alphabet == S.ring.alphabet) {
            GenWordPolynomial<C> n = copy();
            SortedMap<Word, C> nv = n.val;
            for (Entry<Word, C> me : S.val.entrySet()) {
                Word e = (Word) me.getKey();
                RingElem y = (RingElem) me.getValue();
                RingElem x = (RingElem) nv.get(e);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(e);
                    } else {
                        nv.put(e, x);
                    }
                } else {
                    nv.put(e, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenWordPolynomial<C> subtract(C a, Word e) {
        if (a == null || a.isZERO()) {
            return this;
        }
        GenWordPolynomial<C> n = copy();
        SortedMap<Word, C> nv = n.val;
        RingElem x = (RingElem) nv.get(e);
        if (x != null) {
            x = (RingElem) x.subtract(a);
            if (x.isZERO()) {
                nv.remove(e);
            } else {
                nv.put(e, x);
            }
        } else {
            nv.put(e, a.negate());
        }
        return n;
    }

    public GenWordPolynomial<C> subtract(C a) {
        return subtract(a, this.ring.wone);
    }

    public GenWordPolynomial<C> negate() {
        GenWordPolynomial<C> n = this.ring.getZERO().copy();
        SortedMap<Word, C> v = n.val;
        for (Entry<Word, C> m : this.val.entrySet()) {
            v.put(m.getKey(), ((RingElem) m.getValue()).negate());
        }
        return n;
    }

    public GenWordPolynomial<C> abs() {
        if (leadingBaseCoefficient().signum() < 0) {
            return negate();
        }
        return this;
    }

    public GenWordPolynomial<C> multiply(GenWordPolynomial<C> S) {
        if (S == null) {
            return this.ring.getZERO();
        }
        if (S.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        if ($assertionsDisabled || this.ring.alphabet == S.ring.alphabet) {
            GenWordPolynomial<C> p = this.ring.getZERO().copy();
            SortedMap<Word, C> pv = p.val;
            for (Entry<Word, C> m1 : this.val.entrySet()) {
                RingElem c1 = (RingElem) m1.getValue();
                Word e1 = (Word) m1.getKey();
                for (Entry<Word, C> m2 : S.val.entrySet()) {
                    Word e2 = (Word) m2.getKey();
                    RingElem c = (RingElem) c1.multiply((RingElem) m2.getValue());
                    if (!c.isZERO()) {
                        Word e = e1.multiply(e2);
                        RingElem c0 = (RingElem) pv.get(e);
                        if (c0 == null) {
                            pv.put(e, c);
                        } else {
                            c0 = (RingElem) c0.sum(c);
                            if (c0.isZERO()) {
                                pv.remove(e);
                            } else {
                                pv.put(e, c0);
                            }
                        }
                    }
                }
            }
            return p;
        }
        throw new AssertionError();
    }

    public GenWordPolynomial<C> multiply(GenWordPolynomial<C> S, GenWordPolynomial<C> T) {
        if (S.isZERO() || T.isZERO()) {
            return this.ring.getZERO();
        }
        if (S.isONE()) {
            return multiply((GenWordPolynomial) T);
        }
        if (T.isONE()) {
            return S.multiply(this);
        }
        return S.multiply(this).multiply((GenWordPolynomial) T);
    }

    public GenWordPolynomial<C> multiply(C s) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            Word e1 = (Word) m1.getKey();
            RingElem c = (RingElem) ((RingElem) m1.getValue()).multiply(s);
            if (!c.isZERO()) {
                pv.put(e1, c);
            }
        }
        return p;
    }

    public GenWordPolynomial<C> multiply(C s, C t) {
        if (s == null || t == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO() || t.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            Word e = (Word) m1.getKey();
            RingElem c = (RingElem) ((RingElem) s.multiply((RingElem) m1.getValue())).multiply(t);
            if (!c.isZERO()) {
                pv.put(e, c);
            }
        }
        return p;
    }

    public GenWordPolynomial<C> monic() {
        if (isZERO()) {
            return this;
        }
        C lc = leadingBaseCoefficient();
        return lc.isUnit() ? multiply((RingElem) lc.inverse()) : this;
    }

    public GenWordPolynomial<C> multiply(C s, Word e) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            Word e1 = (Word) m1.getKey();
            RingElem c = (RingElem) ((RingElem) m1.getValue()).multiply(s);
            if (!c.isZERO()) {
                pv.put(e1.multiply(e), c);
            }
        }
        return p;
    }

    public GenWordPolynomial<C> multiply(Word e, Word f) {
        if (isZERO()) {
            return this;
        }
        if (e.isONE()) {
            return multiply(f);
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            pv.put(e.multiply(((Word) m1.getKey()).multiply(f)), (RingElem) m1.getValue());
        }
        return p;
    }

    public GenWordPolynomial<C> multiply(C s, Word e, Word f) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        if (e.isONE()) {
            return multiply((RingElem) s, f);
        }
        return multiply((RingElem) this.ring.coFac.getONE(), e, s, f);
    }

    public GenWordPolynomial<C> multiply(C s, Word e, C t, Word f) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            RingElem c = (RingElem) ((RingElem) s.multiply((RingElem) m1.getValue())).multiply(t);
            if (!c.isZERO()) {
                pv.put(e.multiply((Word) m1.getKey()).multiply(f), c);
            }
        }
        return p;
    }

    public GenWordPolynomial<C> multiply(Word e) {
        if (isZERO()) {
            return this;
        }
        GenWordPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<Word, C> pv = p.val;
        for (Entry<Word, C> m1 : this.val.entrySet()) {
            pv.put(((Word) m1.getKey()).multiply(e), (RingElem) m1.getValue());
        }
        return p;
    }

    public GenWordPolynomial<C> multiply(Entry<Word, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((RingElem) m.getValue(), (Word) m.getKey());
    }

    public GenWordPolynomial<C> divide(C s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException(getClass().getName() + " division by zero");
        } else if (isZERO()) {
            return this;
        } else {
            GenWordPolynomial<C> p = this.ring.getZERO().copy();
            SortedMap<Word, C> pv = p.val;
            for (Entry<Word, C> m : this.val.entrySet()) {
                Word e = (Word) m.getKey();
                RingElem c1 = (RingElem) m.getValue();
                RingElem c = (RingElem) c1.divide(s);
                if (this.debug) {
                    RingElem x = (RingElem) c1.remainder(s);
                    if (!x.isZERO()) {
                        logger.info("divide x = " + x);
                        throw new ArithmeticException(getClass().getName() + " no exact division: " + c1 + "/" + s);
                    }
                }
                if (c.isZERO()) {
                    throw new ArithmeticException(getClass().getName() + " no exact division: " + c1 + "/" + s + ", in " + this);
                }
                pv.put(e, c);
            }
            return p;
        }
    }

    public GenWordPolynomial<C>[] quotientRemainder(GenWordPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(getClass().getName() + " division by zero");
        }
        C c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            RingElem ci = (RingElem) c.inverse();
            RingElem one = (RingElem) this.ring.coFac.getONE();
            if ($assertionsDisabled || this.ring.alphabet == S.ring.alphabet) {
                WordComparator cmp = this.ring.alphabet.getDescendComparator();
                Word e = S.leadingWord();
                GenWordPolynomial<C> q = this.ring.getZERO().copy();
                GenWordPolynomial<C> r = copy();
                while (!r.isZERO()) {
                    Word f = r.leadingWord();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    C a = r.leadingBaseCoefficient();
                    Word[] g = f.divideWord(e);
                    RingElem a2 = (RingElem) a.multiply(ci);
                    q = q.sum(a2, g[0].multiply(g[1]));
                    r = r.subtract(S.multiply(a2, g[0], one, g[1]));
                    Word fr = r.leadingWord();
                    if (cmp.compare(f, fr) > 0) {
                        throw new RuntimeException("possible infinite loop: f = " + f + ", fr = " + fr);
                    }
                }
                return new GenWordPolynomial[]{q, r};
            }
            throw new AssertionError();
        }
        throw new ArithmeticException(getClass().getName() + " lbcf not invertible " + c);
    }

    public GenWordPolynomial<C> divide(GenWordPolynomial<C> S) {
        return quotientRemainder(S)[0];
    }

    public GenWordPolynomial<C> remainder(GenWordPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException(getClass().getName() + " division by zero");
        }
        C c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            RingElem ci = (RingElem) c.inverse();
            RingElem one = (RingElem) this.ring.coFac.getONE();
            if ($assertionsDisabled || this.ring.alphabet == S.ring.alphabet) {
                WordComparator cmp = this.ring.alphabet.getDescendComparator();
                Word e = S.leadingWord();
                GenWordPolynomial<C> r = copy();
                while (!r.isZERO()) {
                    Word f = r.leadingWord();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    C a = r.leadingBaseCoefficient();
                    Word[] g = f.divideWord(e);
                    r = r.subtract(S.multiply((RingElem) a.multiply(ci), g[0], one, g[1]));
                    Word fr = r.leadingWord();
                    if (cmp.compare(f, fr) > 0) {
                        throw new RuntimeException("possible infinite loop: f = " + f + ", fr = " + fr);
                    }
                }
                return r;
            }
            throw new AssertionError();
        }
        throw new ArithmeticException(getClass().getName() + " lbc not invertible " + c);
    }

    public GenWordPolynomial<C> gcd(GenWordPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (this.ring.alphabet.length() != 1) {
            throw new IllegalArgumentException("no univariate polynomial " + this.ring);
        }
        GenWordPolynomial<C> q = this;
        GenWordPolynomial<C> r = S;
        while (!r.isZERO()) {
            GenWordPolynomial<C> x = q.remainder((GenWordPolynomial) r);
            q = r;
            r = x;
        }
        return q.monic();
    }

    public GenWordPolynomial<C>[] egcd(GenWordPolynomial<C> S) {
        GenWordPolynomial<C>[] ret = new GenWordPolynomial[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
            ret[1] = this.ring.getONE();
            ret[2] = this.ring.getZERO();
        } else if (isZERO()) {
            ret[0] = S;
            ret[1] = this.ring.getZERO();
            ret[2] = this.ring.getONE();
        } else {
            if (this.ring.alphabet.length() != 1) {
                throw new IllegalArgumentException("no univariate polynomial " + this.ring);
            } else if (isConstant() && S.isConstant()) {
                C[] gg = leadingBaseCoefficient().egcd(S.leadingBaseCoefficient());
                GenWordPolynomial<C> z = this.ring.getZERO();
                ret[0] = z.sum(gg[0]);
                ret[1] = z.sum(gg[1]);
                ret[2] = z.sum(gg[2]);
            } else {
                GenWordPolynomial<C> q = this;
                GenWordPolynomial<C> r = S;
                GenWordPolynomial<C> c1 = this.ring.getONE().copy();
                GenWordPolynomial<C> d1 = this.ring.getZERO().copy();
                GenWordPolynomial<C> c2 = this.ring.getZERO().copy();
                GenWordPolynomial<C> d2 = this.ring.getONE().copy();
                while (!r.isZERO()) {
                    GenWordPolynomial<C>[] qr = q.quotientRemainder(r);
                    q = qr[0];
                    GenWordPolynomial<C> x1 = c1.subtract(q.multiply((GenWordPolynomial) d1));
                    GenWordPolynomial<C> x2 = c2.subtract(q.multiply((GenWordPolynomial) d2));
                    c1 = d1;
                    c2 = d2;
                    d1 = x1;
                    d2 = x2;
                    q = r;
                    r = qr[1];
                }
                C g = q.leadingBaseCoefficient();
                if (g.isUnit()) {
                    RingElem h = (RingElem) g.inverse();
                    q = q.multiply(h);
                    c1 = c1.multiply(h);
                    c2 = c2.multiply(h);
                }
                ret[0] = q;
                ret[1] = c1;
                ret[2] = c2;
            }
        }
        return ret;
    }

    public GenWordPolynomial<C>[] hegcd(GenWordPolynomial<C> S) {
        GenWordPolynomial<C>[] ret = new GenWordPolynomial[]{null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
            ret[1] = this.ring.getONE();
        } else if (isZERO()) {
            ret[0] = S;
        } else if (this.ring.alphabet.length() != 1) {
            throw new IllegalArgumentException("no univariate polynomial " + this.ring);
        } else {
            GenWordPolynomial<C> q = this;
            GenWordPolynomial<C> r = S;
            GenWordPolynomial<C> c1 = this.ring.getONE().copy();
            GenWordPolynomial<C> d1 = this.ring.getZERO().copy();
            while (!r.isZERO()) {
                GenWordPolynomial<C>[] qr = q.quotientRemainder(r);
                GenWordPolynomial<C> x1 = c1.subtract(qr[0].multiply((GenWordPolynomial) d1));
                c1 = d1;
                d1 = x1;
                q = r;
                r = qr[1];
            }
            C g = q.leadingBaseCoefficient();
            if (g.isUnit()) {
                RingElem h = (RingElem) g.inverse();
                q = q.multiply(h);
                c1 = c1.multiply(h);
            }
            ret[0] = q;
            ret[1] = c1;
        }
        return ret;
    }

    public GenWordPolynomial<C> inverse() {
        if (isUnit()) {
            return this.ring.getONE().multiply((RingElem) leadingBaseCoefficient().inverse());
        }
        throw new NotInvertibleException("element not invertible " + this + " :: " + this.ring);
    }

    public GenWordPolynomial<C> modInverse(GenWordPolynomial<C> m) {
        if (isZERO()) {
            throw new NotInvertibleException("zero is not invertible");
        }
        GenWordPolynomial<C>[] hegcd = hegcd(m);
        if (hegcd[0].isUnit()) {
            GenWordPolynomial<C> b = hegcd[1];
            if (!b.isZERO()) {
                return b;
            }
            throw new NotInvertibleException("element not invertible, divisible by modul");
        }
        throw new NotInvertibleException("element not invertible, gcd != 1");
    }

    public Iterator<C> coefficientIterator() {
        return this.val.values().iterator();
    }

    public Iterator<Word> wordIterator() {
        return this.val.keySet().iterator();
    }

    public Iterator<WordMonomial<C>> iterator() {
        return new WordPolyIterator(this.val);
    }

    public GenWordPolynomial<C> map(UnaryFunctor<? super C, C> f) {
        GenWordPolynomial<C> n = this.ring.getZERO().copy();
        SortedMap<Word, C> nv = n.val;
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            WordMonomial<C> m = (WordMonomial) i$.next();
            RingElem c = (RingElem) f.eval(m.c);
            if (!(c == null || c.isZERO())) {
                nv.put(m.e, c);
            }
        }
        return n;
    }
}
