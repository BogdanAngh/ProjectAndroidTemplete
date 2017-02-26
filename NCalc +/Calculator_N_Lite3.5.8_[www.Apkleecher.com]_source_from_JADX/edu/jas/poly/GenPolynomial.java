package edu.jas.poly;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.kern.PreemptingException;
import edu.jas.kern.PrettyPrint;
import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.RingElem;
import edu.jas.structure.UnaryFunctor;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class GenPolynomial<C extends RingElem<C>> implements RingElem<GenPolynomial<C>>, Iterable<Monomial<C>> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final GenPolynomialRing<C> ring;
    protected final SortedMap<ExpVector, C> val;

    static {
        boolean z;
        if (GenPolynomial.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        logger = Logger.getLogger(GenPolynomial.class);
    }

    private GenPolynomial(GenPolynomialRing<C> r, TreeMap<ExpVector, C> t) {
        this.debug = logger.isDebugEnabled();
        this.ring = r;
        this.val = t;
        if (this.ring.checkPreempt && Thread.currentThread().isInterrupted()) {
            logger.debug("throw PreemptingException");
            throw new PreemptingException();
        }
    }

    public GenPolynomial(GenPolynomialRing<C> r) {
        this((GenPolynomialRing) r, new TreeMap(r.tord.getDescendComparator()));
    }

    public GenPolynomial(GenPolynomialRing<C> r, C c, ExpVector e) {
        this(r);
        if (!c.isZERO()) {
            this.val.put(e, c);
        }
    }

    public GenPolynomial(GenPolynomialRing<C> r, C c) {
        this(r, c, r.evzero);
    }

    public GenPolynomial(GenPolynomialRing<C> r, ExpVector e) {
        this(r, (RingElem) r.coFac.getONE(), e);
    }

    protected GenPolynomial(GenPolynomialRing<C> r, SortedMap<ExpVector, C> v) {
        this(r);
        if (v.size() > 0) {
            GenPolynomialRing.creations++;
            this.val.putAll(v);
        }
    }

    public GenPolynomialRing<C> factory() {
        return this.ring;
    }

    public GenPolynomial<C> copy() {
        return new GenPolynomial(this.ring, this.val);
    }

    public int length() {
        return this.val.size();
    }

    public SortedMap<ExpVector, C> getMap() {
        return Collections.unmodifiableSortedMap(this.val);
    }

    public void doPutToMap(ExpVector e, C c) {
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

    public void doRemoveFromMap(ExpVector e, C c) {
        RingElem b = (RingElem) this.val.remove(e);
        if (this.debug && c != null && !c.equals(b)) {
            logger.error("map entry wrong " + e + " to " + c + " old " + b);
        }
    }

    public void doPutToMap(SortedMap<ExpVector, C> vals) {
        for (Entry<ExpVector, C> me : vals.entrySet()) {
            ExpVector e = (ExpVector) me.getKey();
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
        if (this.ring.vars != null) {
            return toString(this.ring.vars);
        }
        StringBuffer s = new StringBuffer();
        s.append(getClass().getSimpleName() + ":");
        s.append(this.ring.coFac.getClass().getSimpleName());
        if (this.ring.coFac.characteristic().signum() != 0) {
            s.append("(" + this.ring.coFac.characteristic() + ")");
        }
        s.append("[ ");
        boolean first = true;
        for (Entry<ExpVector, C> m : this.val.entrySet()) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(((RingElem) m.getValue()).toString());
            s.append(" ");
            s.append(((ExpVector) m.getKey()).toString());
        }
        s.append(" ] ");
        return s.toString();
    }

    public String toString(String[] v) {
        StringBuffer s = new StringBuffer();
        boolean first;
        C c;
        RingElem c2;
        ExpVector e;
        if (!PrettyPrint.isTrue()) {
            s.append(getClass().getSimpleName() + "[ ");
            if (this.val.size() == 0) {
                s.append(Constants.ZERO);
            } else {
                first = true;
                for (Entry<ExpVector, C> m : this.val.entrySet()) {
                    c = (RingElem) m.getValue();
                    if (first) {
                        first = false;
                    } else if (c.signum() < 0) {
                        s.append(" - ");
                        c2 = (RingElem) c.negate();
                    } else {
                        s.append(" + ");
                    }
                    e = (ExpVector) m.getKey();
                    if (!c.isONE() || e.isZERO()) {
                        s.append(c.toString());
                        s.append(" ");
                    }
                    s.append(e.toString(v));
                }
            }
            s.append(" ] ");
        } else if (this.val.size() == 0) {
            s.append(Constants.ZERO);
        } else {
            first = true;
            for (Entry<ExpVector, C> m2 : this.val.entrySet()) {
                c = (RingElem) m2.getValue();
                if (first) {
                    first = false;
                } else if (c.signum() < 0) {
                    s.append(" - ");
                    c2 = (RingElem) c.negate();
                } else {
                    s.append(" + ");
                }
                e = (ExpVector) m2.getKey();
                if (!c.isONE() || e.isZERO()) {
                    String cs = c.toString();
                    if (cs.indexOf("-") >= 0 || cs.indexOf("+") >= 0) {
                        s.append("( ");
                        s.append(cs);
                        s.append(" )");
                    } else {
                        s.append(cs);
                    }
                    s.append(" ");
                }
                if (e == null || v == null) {
                    s.append(e);
                } else {
                    s.append(e.toString(v));
                }
            }
        }
        return s.toString();
    }

    public String toScript() {
        if (isZERO()) {
            return Constants.ZERO;
        }
        StringBuffer s = new StringBuffer();
        if (this.val.size() > 1) {
            s.append("( ");
        }
        String[] v = this.ring.vars;
        if (v == null) {
            v = GenPolynomialRing.newVars(UnivPowerSeriesRing.DEFAULT_NAME, this.ring.nvar);
        }
        boolean first = true;
        for (Entry<ExpVector, C> m : this.val.entrySet()) {
            C c = (RingElem) m.getValue();
            if (first) {
                first = false;
            } else if (c.signum() < 0) {
                s.append(" - ");
                RingElem c2 = (RingElem) c.negate();
            } else {
                s.append(" + ");
            }
            ExpVector e = (ExpVector) m.getKey();
            String cs = c.toScript();
            boolean parenthesis = cs.indexOf("-") >= 0 || cs.indexOf("+") >= 0;
            if (!c.isONE() || e.isZERO()) {
                if (parenthesis) {
                    s.append("( ");
                }
                s.append(cs);
                if (parenthesis) {
                    s.append(" )");
                }
                if (!e.isZERO()) {
                    s.append(" * ");
                }
            }
            s.append(e.toScript(v));
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
        RingElem c = (RingElem) this.val.get(this.ring.evzero);
        if (c != null) {
            return c.isONE();
        }
        return false;
    }

    public boolean isUnit() {
        if (this.val.size() != 1) {
            return false;
        }
        RingElem c = (RingElem) this.val.get(this.ring.evzero);
        if (c != null) {
            return c.isUnit();
        }
        return false;
    }

    public boolean isConstant() {
        if (this.val.size() == 1 && ((RingElem) this.val.get(this.ring.evzero)) != null) {
            return true;
        }
        return false;
    }

    public boolean isHomogeneous() {
        if (this.val.size() <= 1) {
            return true;
        }
        long deg = -1;
        for (ExpVector e : this.val.keySet()) {
            if (deg < 0) {
                deg = e.totalDeg();
            } else if (deg != e.totalDeg()) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object B) {
        if (B != null && (B instanceof GenPolynomial) && compareTo((GenPolynomial) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() << 27) + this.val.hashCode();
    }

    public int compareTo(GenPolynomial<C> b) {
        if (b == null) {
            return 1;
        }
        SortedMap<ExpVector, C> av = this.val;
        SortedMap<ExpVector, C> bv = b.val;
        Iterator<Entry<ExpVector, C>> ai = av.entrySet().iterator();
        Iterator<Entry<ExpVector, C>> bi = bv.entrySet().iterator();
        int c = 0;
        while (ai.hasNext() && bi.hasNext()) {
            Entry<ExpVector, C> aie = (Entry) ai.next();
            Entry<ExpVector, C> bie = (Entry) bi.next();
            int s = ((ExpVector) aie.getKey()).compareTo((ExpVector) bie.getKey());
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
        return ((RingElem) this.val.get((ExpVector) this.val.firstKey())).signum();
    }

    public int numberOfVariables() {
        return this.ring.nvar;
    }

    public Entry<ExpVector, C> leadingMonomial() {
        if (this.val.size() == 0) {
            return null;
        }
        return (Entry) this.val.entrySet().iterator().next();
    }

    public ExpVector leadingExpVector() {
        if (this.val.size() == 0) {
            return null;
        }
        return (ExpVector) this.val.firstKey();
    }

    public ExpVector trailingExpVector() {
        if (this.val.size() == 0) {
            return this.ring.evzero;
        }
        return (ExpVector) this.val.lastKey();
    }

    public C leadingBaseCoefficient() {
        if (this.val.size() == 0) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return (RingElem) this.val.get(this.val.firstKey());
    }

    public C trailingBaseCoefficient() {
        C c = (RingElem) this.val.get(this.ring.evzero);
        if (c == null) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return c;
    }

    public C coefficient(ExpVector e) {
        RingElem c = (RingElem) this.val.get(e);
        if (c == null) {
            return (RingElem) this.ring.coFac.getZERO();
        }
        return c;
    }

    public GenPolynomial<C> reductum() {
        if (this.val.size() <= 1) {
            return this.ring.getZERO();
        }
        Iterator<ExpVector> ai = this.val.keySet().iterator();
        ExpVector expVector = (ExpVector) ai.next();
        SortedMap<ExpVector, C> red = this.val.tailMap((ExpVector) ai.next());
        GenPolynomial<C> r = this.ring.getZERO().copy();
        r.doPutToMap(red);
        return r;
    }

    public long degree(int i) {
        if (this.val.size() == 0) {
            return 0;
        }
        int j;
        if (i >= 0) {
            j = (this.ring.nvar - 1) - i;
        } else {
            j = this.ring.nvar + i;
        }
        long deg = 0;
        for (ExpVector e : this.val.keySet()) {
            long d = e.getVal(j);
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public long degree() {
        if (this.val.size() == 0) {
            return 0;
        }
        long deg = 0;
        for (ExpVector e : this.val.keySet()) {
            long d = e.maxDeg();
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public long totalDegree() {
        if (this.val.size() == 0) {
            return 0;
        }
        long deg = 0;
        for (ExpVector e : this.val.keySet()) {
            long d = e.totalDeg();
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public ExpVector degreeVector() {
        ExpVector deg = this.ring.evzero;
        if (this.val.size() == 0) {
            return deg;
        }
        for (ExpVector e : this.val.keySet()) {
            deg = deg.lcm(e);
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

    public GenPolynomial<C> sum(GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector e = (ExpVector) me.getKey();
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

    public GenPolynomial<C> sum(C a, ExpVector e) {
        if (a == null || a.isZERO()) {
            return this;
        }
        GenPolynomial<C> n = copy();
        SortedMap<ExpVector, C> nv = n.val;
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

    public GenPolynomial<C> sum(C a) {
        return sum(a, this.ring.evzero);
    }

    public void doAddTo(GenPolynomial<C> S) {
        if (S != null && !S.isZERO()) {
            if (isZERO()) {
                this.val.putAll(S.val);
            } else if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                SortedMap<ExpVector, C> nv = this.val;
                for (Entry<ExpVector, C> me : S.val.entrySet()) {
                    ExpVector e = (ExpVector) me.getKey();
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
            } else {
                throw new AssertionError();
            }
        }
    }

    public void doAddTo(C a, ExpVector e) {
        if (a != null && !a.isZERO()) {
            SortedMap<ExpVector, C> nv = this.val;
            RingElem x = (RingElem) nv.get(e);
            if (x != null) {
                x = (RingElem) x.sum(a);
                if (x.isZERO()) {
                    nv.remove(e);
                    return;
                } else {
                    nv.put(e, x);
                    return;
                }
            }
            nv.put(e, a);
        }
    }

    public void doAddTo(C a) {
        doAddTo(a, this.ring.evzero);
    }

    public GenPolynomial<C> subtract(GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.negate();
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector e = (ExpVector) me.getKey();
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

    public GenPolynomial<C> subtract(C a, ExpVector e) {
        if (a == null || a.isZERO()) {
            return this;
        }
        GenPolynomial<C> n = copy();
        SortedMap<ExpVector, C> nv = n.val;
        RingElem x = (RingElem) nv.get(e);
        if (x != null) {
            x = (RingElem) x.subtract(a);
            if (x.isZERO()) {
                nv.remove(e);
                return n;
            }
            nv.put(e, x);
            return n;
        }
        nv.put(e, a.negate());
        return n;
    }

    public GenPolynomial<C> subtract(C a) {
        return subtract(a, this.ring.evzero);
    }

    public GenPolynomial<C> subtractMultiple(C a, GenPolynomial<C> S) {
        if (a == null || a.isZERO() || S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.multiply((RingElem) a.negate());
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenPolynomial<C> subtractMultiple(C a, ExpVector e, GenPolynomial<C> S) {
        if (a == null || a.isZERO() || S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S.multiply((RingElem) a.negate(), e);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = copy();
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = e.sum((ExpVector) me.getKey());
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenPolynomial<C> scaleSubtractMultiple(C b, C a, GenPolynomial<C> S) {
        if (a == null || S == null) {
            return multiply((RingElem) b);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiply((RingElem) b);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiply((RingElem) a.negate());
        }
        if (b.isONE()) {
            return subtractMultiple(a, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = multiply((RingElem) b);
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = (ExpVector) me.getKey();
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenPolynomial<C> scaleSubtractMultiple(C b, C a, ExpVector e, GenPolynomial<C> S) {
        if (a == null || S == null) {
            return multiply((RingElem) b);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiply((RingElem) b);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiply((RingElem) a.negate(), e);
        }
        if (b.isONE()) {
            return subtractMultiple(a, e, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = multiply((RingElem) b);
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = e.sum((ExpVector) me.getKey());
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenPolynomial<C> scaleSubtractMultiple(C b, ExpVector g, C a, ExpVector e, GenPolynomial<C> S) {
        if (a == null || S == null) {
            return multiply(b, g);
        }
        if (a.isZERO() || S.isZERO()) {
            return multiply(b, g);
        }
        if (isZERO() || b == null || b.isZERO()) {
            return S.multiply((RingElem) a.negate(), e);
        }
        if (b.isONE() && g.isZERO()) {
            return subtractMultiple(a, e, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            GenPolynomial<C> n = multiply(b, g);
            SortedMap<ExpVector, C> nv = n.val;
            for (Entry<ExpVector, C> me : S.val.entrySet()) {
                ExpVector f = e.sum((ExpVector) me.getKey());
                RingElem y = (RingElem) a.multiply((RingElem) me.getValue());
                RingElem x = (RingElem) nv.get(f);
                if (x != null) {
                    x = (RingElem) x.subtract(y);
                    if (x.isZERO()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZERO()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public GenPolynomial<C> negate() {
        GenPolynomial<C> n = this.ring.getZERO().copy();
        SortedMap<ExpVector, C> v = n.val;
        for (Entry<ExpVector, C> m : this.val.entrySet()) {
            v.put(m.getKey(), ((RingElem) m.getValue()).negate());
        }
        return n;
    }

    public GenPolynomial<C> abs() {
        if (leadingBaseCoefficient().signum() < 0) {
            return negate();
        }
        return this;
    }

    public GenPolynomial<C> multiply(GenPolynomial<C> S) {
        if (S == null) {
            return this.ring.getZERO();
        } else if (S.isZERO()) {
            return this.ring.getZERO();
        } else if (isZERO()) {
            return this;
        } else {
            if (!$assertionsDisabled && this.ring.nvar != S.ring.nvar) {
                throw new AssertionError();
            } else if ((this instanceof GenSolvablePolynomial) && (S instanceof GenSolvablePolynomial)) {
                logger.debug("warn: wrong method dispatch in JRE multiply(S) - trying to fix");
                return ((GenSolvablePolynomial) this).multiply((GenSolvablePolynomial) S);
            } else {
                GenPolynomial<C> p = this.ring.getZERO().copy();
                SortedMap<ExpVector, C> pv = p.val;
                for (Entry<ExpVector, C> m1 : this.val.entrySet()) {
                    RingElem c1 = (RingElem) m1.getValue();
                    ExpVector e1 = (ExpVector) m1.getKey();
                    for (Entry<ExpVector, C> m2 : S.val.entrySet()) {
                        ExpVector e2 = (ExpVector) m2.getKey();
                        RingElem c = (RingElem) c1.multiply((RingElem) m2.getValue());
                        if (!c.isZERO()) {
                            ExpVector e = e1.sum(e2);
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
        }
    }

    public GenPolynomial<C> multiply(C s) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        if (this instanceof GenSolvablePolynomial) {
            logger.debug("warn: wrong method dispatch in JRE multiply(s) - trying to fix");
            return ((GenSolvablePolynomial) this).multiply((RingElem) s);
        }
        GenPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<ExpVector, C> pv = p.val;
        for (Entry<ExpVector, C> m1 : this.val.entrySet()) {
            ExpVector e1 = (ExpVector) m1.getKey();
            RingElem c = (RingElem) ((RingElem) m1.getValue()).multiply(s);
            if (!c.isZERO()) {
                pv.put(e1, c);
            }
        }
        return p;
    }

    public GenPolynomial<C> monic() {
        if (isZERO()) {
            return this;
        }
        C lc = leadingBaseCoefficient();
        return lc.isUnit() ? multiply((RingElem) lc.inverse()) : this;
    }

    public GenPolynomial<C> multiply(C s, ExpVector e) {
        if (s == null) {
            return this.ring.getZERO();
        }
        if (s.isZERO()) {
            return this.ring.getZERO();
        }
        if (isZERO()) {
            return this;
        }
        if (this instanceof GenSolvablePolynomial) {
            logger.debug("warn: wrong method dispatch in JRE multiply(s,e) - trying to fix");
            return ((GenSolvablePolynomial) this).multiply((RingElem) s, e);
        }
        GenPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<ExpVector, C> pv = p.val;
        for (Entry<ExpVector, C> m1 : this.val.entrySet()) {
            ExpVector e1 = (ExpVector) m1.getKey();
            RingElem c = (RingElem) ((RingElem) m1.getValue()).multiply(s);
            if (!c.isZERO()) {
                pv.put(e1.sum(e), c);
            }
        }
        return p;
    }

    public GenPolynomial<C> multiply(ExpVector e) {
        if (isZERO()) {
            return this;
        }
        if (this instanceof GenSolvablePolynomial) {
            logger.debug("warn: wrong method dispatch in JRE multiply(e) - trying to fix");
            return ((GenSolvablePolynomial) this).multiply(e);
        }
        GenPolynomial<C> p = this.ring.getZERO().copy();
        SortedMap<ExpVector, C> pv = p.val;
        for (Entry<ExpVector, C> m1 : this.val.entrySet()) {
            pv.put(((ExpVector) m1.getKey()).sum(e), (RingElem) m1.getValue());
        }
        return p;
    }

    public GenPolynomial<C> multiply(Entry<ExpVector, C> m) {
        if (m == null) {
            return this.ring.getZERO();
        }
        return multiply((RingElem) m.getValue(), (ExpVector) m.getKey());
    }

    public GenPolynomial<C> divide(C s) {
        if (s == null || s.isZERO()) {
            throw new ArithmeticException("division by zero");
        } else if (isZERO()) {
            return this;
        } else {
            GenPolynomial<C> p = this.ring.getZERO().copy();
            SortedMap<ExpVector, C> pv = p.val;
            for (Entry<ExpVector, C> m : this.val.entrySet()) {
                ExpVector e = (ExpVector) m.getKey();
                RingElem c1 = (RingElem) m.getValue();
                RingElem c = (RingElem) c1.divide(s);
                if (this.debug) {
                    RingElem x = (RingElem) c1.remainder(s);
                    if (!x.isZERO()) {
                        logger.info("divide x = " + x);
                        throw new ArithmeticException("no exact division: " + c1 + "/" + s);
                    }
                }
                if (c.isZERO()) {
                    throw new ArithmeticException("no exact division: " + c1 + "/" + s + ", in " + this);
                }
                pv.put(e, c);
            }
            return p;
        }
    }

    public GenPolynomial<C>[] quotientRemainder(GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        }
        C c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            RingElem ci = (RingElem) c.inverse();
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                ExpVector e = S.leadingExpVector();
                GenPolynomial<C> q = this.ring.getZERO().copy();
                GenPolynomial<C> r = copy();
                while (!r.isZERO()) {
                    ExpVector f = r.leadingExpVector();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    C a = r.leadingBaseCoefficient();
                    f = f.subtract(e);
                    RingElem a2 = (RingElem) a.multiply(ci);
                    q = q.sum(a2, f);
                    r = r.subtract(S.multiply(a2, f));
                }
                return new GenPolynomial[]{q, r};
            }
            throw new AssertionError();
        }
        throw new ArithmeticException("lbcf not invertible " + c);
    }

    @Deprecated
    public GenPolynomial<C>[] divideAndRemainder(GenPolynomial<C> S) {
        return quotientRemainder(S);
    }

    public GenPolynomial<C> divide(GenPolynomial<C> S) {
        if ((this instanceof GenSolvablePolynomial) || (S instanceof GenSolvablePolynomial)) {
            return ((GenSolvablePolynomial) this).quotientRemainder((GenSolvablePolynomial) S)[0];
        }
        return quotientRemainder(S)[0];
    }

    public GenPolynomial<C> remainder(GenPolynomial<C> S) {
        if ((this instanceof GenSolvablePolynomial) || (S instanceof GenSolvablePolynomial)) {
            return ((GenSolvablePolynomial) this).quotientRemainder((GenSolvablePolynomial) S)[1];
        }
        if (S == null || S.isZERO()) {
            throw new ArithmeticException("division by zero");
        }
        C c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            RingElem ci = (RingElem) c.inverse();
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                ExpVector e = S.leadingExpVector();
                GenPolynomial<C> r = copy();
                while (!r.isZERO()) {
                    ExpVector f = r.leadingExpVector();
                    if (!f.multipleOf(e)) {
                        return r;
                    }
                    C a = r.leadingBaseCoefficient();
                    r = r.subtract(S.multiply((RingElem) a.multiply(ci), f.subtract(e)));
                }
                return r;
            }
            throw new AssertionError();
        }
        throw new ArithmeticException("lbc not invertible " + c);
    }

    public GenPolynomial<C> gcd(GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return S;
        }
        if (this.ring.nvar != 1) {
            throw new IllegalArgumentException("not univariate polynomials" + this.ring);
        }
        GenPolynomial<C> q = this;
        GenPolynomial<C> r = S;
        while (!r.isZERO()) {
            GenPolynomial<C> x = q.remainder((GenPolynomial) r);
            q = r;
            r = x;
        }
        return q.monic();
    }

    public GenPolynomial<C>[] egcd(GenPolynomial<C> S) {
        GenPolynomial<C>[] ret = new GenPolynomial[]{null, null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
            ret[1] = this.ring.getONE();
            ret[2] = this.ring.getZERO();
        } else if (isZERO()) {
            ret[0] = S;
            ret[1] = this.ring.getZERO();
            ret[2] = this.ring.getONE();
        } else {
            int i = this.ring.nvar;
            if (r0 != 1) {
                throw new IllegalArgumentException(getClass().getName() + " not univariate polynomials" + this.ring);
            } else if (isConstant() && S.isConstant()) {
                C[] gg = leadingBaseCoefficient().egcd(S.leadingBaseCoefficient());
                GenPolynomial<C> z = this.ring.getZERO();
                ret[0] = z.sum(gg[0]);
                ret[1] = z.sum(gg[1]);
                ret[2] = z.sum(gg[2]);
            } else {
                GenPolynomial<C> q = this;
                GenPolynomial<C> r = S;
                GenPolynomial<C> c1 = this.ring.getONE().copy();
                GenPolynomial<C> d1 = this.ring.getZERO().copy();
                GenPolynomial<C> c2 = this.ring.getZERO().copy();
                GenPolynomial<C> d2 = this.ring.getONE().copy();
                while (!r.isZERO()) {
                    GenPolynomial<C>[] qr = q.quotientRemainder(r);
                    q = qr[0];
                    GenPolynomial<C> x1 = c1.subtract(q.multiply((GenPolynomial) d1));
                    GenPolynomial<C> x2 = c2.subtract(q.multiply((GenPolynomial) d2));
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

    public GenPolynomial<C>[] hegcd(GenPolynomial<C> S) {
        GenPolynomial<C>[] ret = new GenPolynomial[]{null, null};
        if (S == null || S.isZERO()) {
            ret[0] = this;
            ret[1] = this.ring.getONE();
        } else if (isZERO()) {
            ret[0] = S;
        } else if (this.ring.nvar != 1) {
            throw new IllegalArgumentException(getClass().getName() + " not univariate polynomials" + this.ring);
        } else {
            GenPolynomial<C> q = this;
            GenPolynomial<C> r = S;
            GenPolynomial<C> c1 = this.ring.getONE().copy();
            GenPolynomial<C> d1 = this.ring.getZERO().copy();
            while (!r.isZERO()) {
                GenPolynomial<C>[] qr = q.quotientRemainder(r);
                GenPolynomial<C> x1 = c1.subtract(qr[0].multiply((GenPolynomial) d1));
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

    public GenPolynomial<C> inverse() {
        if (isUnit()) {
            return this.ring.getONE().multiply((RingElem) leadingBaseCoefficient().inverse());
        }
        throw new NotInvertibleException("element not invertible " + this + " :: " + this.ring);
    }

    public GenPolynomial<C> modInverse(GenPolynomial<C> m) {
        if (isZERO()) {
            throw new NotInvertibleException("zero is not invertible");
        }
        GenPolynomial<C>[] hegcd = hegcd(m);
        GenPolynomial a = hegcd[0];
        if (a.isUnit()) {
            GenPolynomial<C> b = hegcd[1];
            if (!b.isZERO()) {
                return b;
            }
            throw new NotInvertibleException("element not invertible, divisible by modul");
        }
        throw new AlgebraicNotInvertibleException("element not invertible, gcd != 1", (GenPolynomial) m, a, m.divide(a));
    }

    public GenPolynomial<C> extend(GenPolynomialRing<C> pfac, int j, long k) {
        if (this.ring.equals(pfac)) {
            return this;
        }
        GenPolynomial<C> Cp = pfac.getZERO().copy();
        if (isZERO()) {
            return Cp;
        }
        int i = pfac.nvar - this.ring.nvar;
        Map<ExpVector, C> C = Cp.val;
        for (Entry<ExpVector, C> y : this.val.entrySet()) {
            RingElem a = (RingElem) y.getValue();
            C.put(((ExpVector) y.getKey()).extend(i, j, k), a);
        }
        return Cp;
    }

    public GenPolynomial<C> extendLower(GenPolynomialRing<C> pfac, int j, long k) {
        if (this.ring.equals(pfac)) {
            return this;
        }
        GenPolynomial<C> Cp = pfac.getZERO().copy();
        if (isZERO()) {
            return Cp;
        }
        int i = pfac.nvar - this.ring.nvar;
        Map<ExpVector, C> C = Cp.val;
        for (Entry<ExpVector, C> y : this.val.entrySet()) {
            RingElem a = (RingElem) y.getValue();
            C.put(((ExpVector) y.getKey()).extendLower(i, j, k), a);
        }
        return Cp;
    }

    public Map<ExpVector, GenPolynomial<C>> contract(GenPolynomialRing<C> pfac) {
        GenPolynomial<C> zero = pfac.getZERO();
        Map<ExpVector, GenPolynomial<C>> B = new TreeMap(new TermOrder(2).getAscendComparator());
        if (!isZERO()) {
            int i = this.ring.nvar - pfac.nvar;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                RingElem a = (RingElem) y.getValue();
                ExpVector f = e.contract(0, i);
                ExpVector g = e.contract(i, e.length() - i);
                GenPolynomial<C> p = (GenPolynomial) B.get(f);
                if (p == null) {
                    p = zero;
                }
                B.put(f, p.sum(a, g));
            }
        }
        return B;
    }

    public GenPolynomial<C> contractCoeff(GenPolynomialRing<C> pfac) {
        Map<ExpVector, GenPolynomial<C>> ms = contract(pfac);
        GenPolynomial<C> c = pfac.getZERO();
        for (Entry<ExpVector, GenPolynomial<C>> m : ms.entrySet()) {
            if (((ExpVector) m.getKey()).isZERO()) {
                c = (GenPolynomial) m.getValue();
            } else {
                throw new RuntimeException("wrong coefficient contraction " + m + ", pol =  " + c);
            }
        }
        return c;
    }

    public GenPolynomial<C> extendUnivariate(GenPolynomialRing<C> pfac, int i) {
        if (i < 0 || pfac.nvar < i) {
            throw new IllegalArgumentException("index " + i + "out of range " + pfac.nvar);
        } else if (this.ring.nvar != 1) {
            throw new IllegalArgumentException("polynomial not univariate " + this.ring.nvar);
        } else if (isONE()) {
            return pfac.getONE();
        } else {
            int j = (pfac.nvar - 1) - i;
            GenPolynomial<C> Cp = pfac.getZERO().copy();
            if (isZERO()) {
                return Cp;
            }
            Map<ExpVector, C> C = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                RingElem a = (RingElem) y.getValue();
                C.put(ExpVector.create(pfac.nvar, j, ((ExpVector) y.getKey()).getVal(0)), a);
            }
            return Cp;
        }
    }

    public GenPolynomial<C> homogenize(GenPolynomialRing<C> pfac) {
        if (this.ring.equals(pfac)) {
            throw new UnsupportedOperationException("case with same ring not implemented");
        }
        GenPolynomial<C> Cp = pfac.getZERO().copy();
        if (!isZERO()) {
            long deg = totalDegree();
            Map<ExpVector, C> C = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                C.put(e.extend(1, 0, deg - e.totalDeg()), (RingElem) y.getValue());
            }
        }
        return Cp;
    }

    public GenPolynomial<C> deHomogenize(GenPolynomialRing<C> pfac) {
        if (this.ring.equals(pfac)) {
            throw new UnsupportedOperationException("case with same ring not implemented");
        }
        GenPolynomial<C> Cp = pfac.getZERO().copy();
        if (!isZERO()) {
            Map<ExpVector, C> C = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                RingElem a = (RingElem) y.getValue();
                C.put(((ExpVector) y.getKey()).contract(1, pfac.nvar), a);
            }
        }
        return Cp;
    }

    public GenPolynomial<C> reverse(GenPolynomialRing<C> oring) {
        GenPolynomial<C> Cp = oring.getZERO().copy();
        if (!isZERO()) {
            int k = -1;
            if (oring.tord.getEvord2() != 0 && oring.partial) {
                k = oring.tord.getSplit();
            }
            Map<ExpVector, C> C = Cp.val;
            for (Entry<ExpVector, C> y : this.val.entrySet()) {
                ExpVector f;
                ExpVector e = (ExpVector) y.getKey();
                if (k >= 0) {
                    f = e.reverse(k);
                } else {
                    f = e.reverse();
                }
                C.put(f, (RingElem) y.getValue());
            }
        }
        return Cp;
    }

    public Iterator<C> coefficientIterator() {
        return this.val.values().iterator();
    }

    public Iterator<ExpVector> exponentIterator() {
        return this.val.keySet().iterator();
    }

    public Iterator<Monomial<C>> iterator() {
        return new PolyIterator(this.val);
    }

    public GenPolynomial<C> map(UnaryFunctor<? super C, C> f) {
        GenPolynomial<C> n = this.ring.getZERO().copy();
        SortedMap<ExpVector, C> nv = n.val;
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            RingElem c = (RingElem) f.eval(m.c);
            if (!(c == null || c.isZERO())) {
                nv.put(m.e, c);
            }
        }
        return n;
    }
}
