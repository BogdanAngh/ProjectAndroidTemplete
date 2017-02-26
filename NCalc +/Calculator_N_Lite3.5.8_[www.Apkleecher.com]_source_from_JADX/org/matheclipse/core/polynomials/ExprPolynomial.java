package org.matheclipse.core.polynomials;

import com.example.duy.calculator.math_eval.Constants;
import com.google.common.base.Function;
import edu.jas.kern.PreemptingException;
import edu.jas.kern.PrettyPrint;
import edu.jas.structure.NotInvertibleException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

public class ExprPolynomial implements Iterable<ExprMonomial> {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    private final boolean debug;
    public final ExprPolynomialRing ring;
    protected final SortedMap<ExpVectorLong, IExpr> val;

    static {
        boolean z;
        if (ExprPolynomial.class.desiredAssertionStatus()) {
            z = false;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        logger = Logger.getLogger(ExprPolynomial.class);
    }

    private ExprPolynomial(ExprPolynomialRing r, TreeMap<ExpVectorLong, IExpr> t) {
        this.debug = logger.isDebugEnabled();
        this.ring = r;
        this.val = t;
        if (this.ring.checkPreempt && Thread.currentThread().isInterrupted()) {
            logger.debug("throw PreemptingException");
            throw new PreemptingException();
        }
    }

    public ExprPolynomial(ExprPolynomialRing r) {
        this(r, new TreeMap(r.tord.getDescendComparator()));
    }

    public ExprPolynomial(ExprPolynomialRing r, IExpr c, ExpVectorLong e) {
        this(r);
        if (!c.isZero()) {
            this.val.put(e, c);
        }
    }

    public ExprPolynomial(ExprPolynomialRing r, IExpr c) {
        this(r, c, r.evzero);
    }

    public ExprPolynomial(ExprPolynomialRing r, ExpVectorLong e) {
        this(r, r.coFac.getONE(), e);
    }

    protected ExprPolynomial(ExprPolynomialRing r, SortedMap<ExpVectorLong, IExpr> v) {
        this(r);
        if (v.size() > 0) {
            ExprPolynomialRing.creations++;
            this.val.putAll(v);
        }
    }

    public ExprPolynomialRing factory() {
        return this.ring;
    }

    public ExprPolynomial copy() {
        return new ExprPolynomial(this.ring, this.val);
    }

    public int length() {
        return this.val.size();
    }

    public SortedMap<ExpVectorLong, IExpr> getMap() {
        return Collections.unmodifiableSortedMap(this.val);
    }

    public void doPutToMap(ExpVectorLong e, IExpr c) {
        if (this.debug) {
            IExpr a = (IExpr) this.val.get(e);
            if (a != null) {
                logger.error("map entry exists " + e + " to " + a + " new " + c);
            }
        }
        if (!c.isZero()) {
            this.val.put(e, c);
        }
    }

    public void doRemoveFromMap(ExpVectorLong e, IExpr c) {
        IExpr b = (IExpr) this.val.remove(e);
        if (this.debug && c != null && !c.equals(b)) {
            logger.error("map entry wrong " + e + " to " + c + " old " + b);
        }
    }

    public void doPutToMap(SortedMap<ExpVectorLong, IExpr> vals) {
        for (Entry<ExpVectorLong, IExpr> me : vals.entrySet()) {
            ExpVectorLong e = (ExpVectorLong) me.getKey();
            if (this.debug) {
                IExpr a = (IExpr) this.val.get(e);
                if (a != null) {
                    logger.error("map entry exists " + e + " to " + a + " new " + me.getValue());
                }
            }
            IExpr c = (IExpr) me.getValue();
            if (!c.isZero()) {
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
        for (Entry<ExpVectorLong, IExpr> m : this.val.entrySet()) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            s.append(((IExpr) m.getValue()).toString());
            s.append(" ");
            s.append(((ExpVectorLong) m.getKey()).toString());
        }
        s.append(" ] ");
        return s.toString();
    }

    public String toString(IAST v) {
        StringBuffer s = new StringBuffer();
        boolean first;
        IExpr c;
        ExpVectorLong e;
        if (!PrettyPrint.isTrue()) {
            s.append(getClass().getSimpleName() + "[ ");
            if (this.val.size() == 0) {
                s.append(Constants.ZERO);
            } else {
                first = true;
                for (Entry<ExpVectorLong, IExpr> m : this.val.entrySet()) {
                    c = (IExpr) m.getValue();
                    if (first) {
                        first = false;
                    } else if (c.signum() < 0) {
                        s.append(" - ");
                        c = (IExpr) c.negate();
                    } else {
                        s.append(" + ");
                    }
                    e = (ExpVectorLong) m.getKey();
                    if (!c.isOne() || e.isZero()) {
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
            for (Entry<ExpVectorLong, IExpr> m2 : this.val.entrySet()) {
                c = (IExpr) m2.getValue();
                if (first) {
                    first = false;
                } else if (c.signum() < 0) {
                    s.append(" - ");
                    c = (IExpr) c.negate();
                } else {
                    s.append(" + ");
                }
                e = (ExpVectorLong) m2.getKey();
                if (!c.isOne() || e.isZero()) {
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
        if (isZero()) {
            return Constants.ZERO;
        }
        StringBuffer s = new StringBuffer();
        if (this.val.size() > 1) {
            s.append("( ");
        }
        IAST v = this.ring.vars;
        boolean first = true;
        for (Entry<ExpVectorLong, IExpr> m : this.val.entrySet()) {
            IExpr c = (IExpr) m.getValue();
            if (first) {
                first = false;
            } else if (c.signum() < 0) {
                s.append(" - ");
                c = (IExpr) c.negate();
            } else {
                s.append(" + ");
            }
            ExpVectorLong e = (ExpVectorLong) m.getKey();
            String cs = c.toScript();
            boolean parenthesis = cs.indexOf("-") >= 0 || cs.indexOf("+") >= 0;
            if (!c.isOne() || e.isZero()) {
                if (parenthesis) {
                    s.append("( ");
                }
                s.append(cs);
                if (parenthesis) {
                    s.append(" )");
                }
                if (!e.isZero()) {
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

    public boolean isZero() {
        return this.val.size() == 0;
    }

    public boolean isOne() {
        if (this.val.size() != 1) {
            return false;
        }
        IExpr c = (IExpr) this.val.get(this.ring.evzero);
        if (c != null) {
            return c.isOne();
        }
        return false;
    }

    public boolean isUnit() {
        if (this.val.size() != 1) {
            return false;
        }
        IExpr c = (IExpr) this.val.get(this.ring.evzero);
        if (c != null) {
            return c.isUnit();
        }
        return false;
    }

    public boolean isConstant() {
        if (this.val.size() == 1 && ((IExpr) this.val.get(this.ring.evzero)) != null) {
            return true;
        }
        return false;
    }

    public boolean isHomogeneous() {
        if (this.val.size() <= 1) {
            return true;
        }
        long deg = -1;
        for (ExpVectorLong e : this.val.keySet()) {
            if (deg < 0) {
                deg = e.totalDeg();
            } else if (deg != e.totalDeg()) {
                return false;
            }
        }
        return true;
    }

    public boolean equals(Object B) {
        if (B != null && (B instanceof ExprPolynomial) && compareTo((ExprPolynomial) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.ring.hashCode() << 27) + this.val.hashCode();
    }

    public int compareTo(ExprPolynomial b) {
        if (b == null) {
            return 1;
        }
        SortedMap<ExpVectorLong, IExpr> av = this.val;
        SortedMap<ExpVectorLong, IExpr> bv = b.val;
        Iterator<Entry<ExpVectorLong, IExpr>> ai = av.entrySet().iterator();
        Iterator<Entry<ExpVectorLong, IExpr>> bi = bv.entrySet().iterator();
        int c = 0;
        while (ai.hasNext() && bi.hasNext()) {
            Entry<ExpVectorLong, IExpr> aie = (Entry) ai.next();
            Entry<ExpVectorLong, IExpr> bie = (Entry) bi.next();
            int s = ((ExpVectorLong) aie.getKey()).compareTo((ExpVectorLong) bie.getKey());
            if (s != 0) {
                return s;
            }
            if (c == 0) {
                c = ((IExpr) aie.getValue()).compareTo((IExpr) bie.getValue());
            }
        }
        if (ai.hasNext()) {
            return 1;
        }
        return bi.hasNext() ? -1 : c;
    }

    public int signum() {
        if (isZero()) {
            return 0;
        }
        return ((IExpr) this.val.get((ExpVectorLong) this.val.firstKey())).signum();
    }

    public int numberOfVariables() {
        return this.ring.nvar;
    }

    public Entry<ExpVectorLong, IExpr> leadingMonomial() {
        if (this.val.size() == 0) {
            return null;
        }
        return (Entry) this.val.entrySet().iterator().next();
    }

    public ExpVectorLong leadingExpVectorLong() {
        if (this.val.size() == 0) {
            return null;
        }
        if (this.ring.tord.getEvord() == 1) {
            return (ExpVectorLong) this.val.lastKey();
        }
        return (ExpVectorLong) this.val.firstKey();
    }

    public ExpVectorLong trailingExpVectorLong() {
        if (this.val.size() == 0) {
            return this.ring.evzero;
        }
        if (this.ring.tord.getEvord() == 1) {
            return (ExpVectorLong) this.val.firstKey();
        }
        return (ExpVectorLong) this.val.lastKey();
    }

    public IExpr leadingBaseCoefficient() {
        if (this.val.size() == 0) {
            return this.ring.coFac.getZERO();
        }
        if (this.ring.tord.getEvord() == 1) {
            return (IExpr) this.val.get(this.val.lastKey());
        }
        return (IExpr) this.val.get(this.val.firstKey());
    }

    public IExpr trailingBaseCoefficient() {
        IExpr c = (IExpr) this.val.get(this.ring.evzero);
        if (c == null) {
            return this.ring.coFac.getZERO();
        }
        return c;
    }

    public IExpr coefficient(ExpVectorLong e) {
        IExpr c = (IExpr) this.val.get(e);
        if (c == null) {
            return this.ring.coFac.getZERO();
        }
        return c;
    }

    public ExprPolynomial reductum() {
        if (this.val.size() <= 1) {
            return this.ring.getZero();
        }
        Iterator<ExpVectorLong> ai = this.val.keySet().iterator();
        ExpVectorLong expVectorLong = (ExpVectorLong) ai.next();
        SortedMap<ExpVectorLong, IExpr> red = this.val.tailMap((ExpVectorLong) ai.next());
        ExprPolynomial r = this.ring.getZero().copy();
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
        for (ExpVectorLong e : this.val.keySet()) {
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
        for (ExpVectorLong e : this.val.keySet()) {
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
        for (ExpVectorLong e : this.val.keySet()) {
            long d = e.totalDeg();
            if (d > deg) {
                deg = d;
            }
        }
        return deg;
    }

    public ExpVectorLong degreeVector() {
        ExpVectorLong deg = this.ring.evzero;
        if (this.val.size() == 0) {
            return deg;
        }
        for (ExpVectorLong e : this.val.keySet()) {
            deg = deg.lcm(e);
        }
        return deg;
    }

    public IExpr maxNorm() {
        IExpr n = this.ring.getZEROCoefficient();
        for (IExpr c : this.val.values()) {
            IExpr x = (IExpr) c.abs();
            if (n.compareTo(x) < 0) {
                n = x;
            }
        }
        return n;
    }

    public IExpr sumNorm() {
        IExpr n = this.ring.getZEROCoefficient();
        for (IExpr c : this.val.values()) {
            n = (IExpr) n.sum((IExpr) c.abs());
        }
        return n;
    }

    public ExprPolynomial sum(ExprPolynomial S) {
        if (S == null || S.isZero()) {
            return this;
        }
        if (isZero()) {
            return S;
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = copy();
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong e = (ExpVectorLong) me.getKey();
                IExpr y = (IExpr) me.getValue();
                IExpr x = (IExpr) nv.get(e);
                if (x != null) {
                    x = (IExpr) x.sum(y);
                    if (x.isZero()) {
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

    public ExprPolynomial sum(IExpr a, ExpVectorLong e) {
        if (a == null || a.isZero()) {
            return this;
        }
        ExprPolynomial n = copy();
        SortedMap<ExpVectorLong, IExpr> nv = n.val;
        IExpr x = (IExpr) nv.get(e);
        if (x != null) {
            x = (IExpr) x.sum(a);
            if (x.isZero()) {
                nv.remove(e);
            } else {
                nv.put(e, x);
            }
        } else {
            nv.put(e, a);
        }
        return n;
    }

    public ExprPolynomial sum(IExpr a) {
        return sum(a, this.ring.evzero);
    }

    public void doAddTo(ExprPolynomial S) {
        if (S != null && !S.isZero()) {
            if (isZero()) {
                this.val.putAll(S.val);
            } else if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                SortedMap<ExpVectorLong, IExpr> nv = this.val;
                for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                    ExpVectorLong e = (ExpVectorLong) me.getKey();
                    IExpr y = (IExpr) me.getValue();
                    IExpr x = (IExpr) nv.get(e);
                    if (x != null) {
                        x = (IExpr) x.sum(y);
                        if (x.isZero()) {
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

    public void doAddTo(IExpr a, ExpVectorLong e) {
        if (a != null && !a.isZero()) {
            SortedMap<ExpVectorLong, IExpr> nv = this.val;
            IExpr x = (IExpr) nv.get(e);
            if (x != null) {
                x = (IExpr) x.sum(a);
                if (x.isZero()) {
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

    public void doAddTo(IExpr a) {
        doAddTo(a, this.ring.evzero);
    }

    public ExprPolynomial subtract(ExprPolynomial S) {
        if (S == null || S.isZero()) {
            return this;
        }
        if (isZero()) {
            return S.negate();
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = copy();
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong e = (ExpVectorLong) me.getKey();
                IExpr y = (IExpr) me.getValue();
                IExpr x = (IExpr) nv.get(e);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
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

    public ExprPolynomial subtract(IExpr a, ExpVectorLong e) {
        if (a == null || a.isZero()) {
            return this;
        }
        ExprPolynomial n = copy();
        SortedMap<ExpVectorLong, IExpr> nv = n.val;
        IExpr x = (IExpr) nv.get(e);
        if (x != null) {
            x = (IExpr) x.subtract(a);
            if (x.isZero()) {
                nv.remove(e);
                return n;
            }
            nv.put(e, x);
            return n;
        }
        nv.put(e, a.negate());
        return n;
    }

    public ExprPolynomial subtract(IExpr a) {
        return subtract(a, this.ring.evzero);
    }

    public ExprPolynomial subtractMultiple(IExpr a, ExprPolynomial S) {
        if (a == null || a.isZero() || S == null || S.isZero()) {
            return this;
        }
        if (isZero()) {
            return S.multiply((IExpr) a.negate());
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = copy();
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong f = (ExpVectorLong) me.getKey();
                IExpr y = a.multiply((IExpr) me.getValue());
                IExpr x = (IExpr) nv.get(f);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZero()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public ExprPolynomial subtractMultiple(IExpr a, ExpVectorLong e, ExprPolynomial S) {
        if (a == null || a.isZero() || S == null || S.isZero()) {
            return this;
        }
        if (isZero()) {
            return S.multiply((IExpr) a.negate(), e);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = copy();
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong f = e.sum((ExpVectorLong) me.getKey());
                IExpr y = a.multiply((IExpr) me.getValue());
                IExpr x = (IExpr) nv.get(f);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZero()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public ExprPolynomial scaleSubtractMultiple(IExpr b, IExpr a, ExprPolynomial S) {
        if (a == null || S == null) {
            return multiply(b);
        }
        if (a.isZero() || S.isZero()) {
            return multiply(b);
        }
        if (isZero() || b == null || b.isZero()) {
            return S.multiply((IExpr) a.negate());
        }
        if (b.isOne()) {
            return subtractMultiple(a, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = multiply(b);
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong f = (ExpVectorLong) me.getKey();
                IExpr y = a.multiply((IExpr) me.getValue());
                IExpr x = (IExpr) nv.get(f);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZero()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public ExprPolynomial scaleSubtractMultiple(IExpr b, IExpr a, ExpVectorLong e, ExprPolynomial S) {
        if (a == null || S == null) {
            return multiply(b);
        }
        if (a.isZero() || S.isZero()) {
            return multiply(b);
        }
        if (isZero() || b == null || b.isZero()) {
            return S.multiply((IExpr) a.negate(), e);
        }
        if (b.isOne()) {
            return subtractMultiple(a, e, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = multiply(b);
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong f = e.sum((ExpVectorLong) me.getKey());
                IExpr y = a.multiply((IExpr) me.getValue());
                IExpr x = (IExpr) nv.get(f);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZero()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public ExprPolynomial scaleSubtractMultiple(IExpr b, ExpVectorLong g, IExpr a, ExpVectorLong e, ExprPolynomial S) {
        if (a == null || S == null) {
            return multiply(b, g);
        }
        if (a.isZero() || S.isZero()) {
            return multiply(b, g);
        }
        if (isZero() || b == null || b.isZero()) {
            return S.multiply((IExpr) a.negate(), e);
        }
        if (b.isOne() && g.isZero()) {
            return subtractMultiple(a, e, S);
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial n = multiply(b, g);
            SortedMap<ExpVectorLong, IExpr> nv = n.val;
            for (Entry<ExpVectorLong, IExpr> me : S.val.entrySet()) {
                ExpVectorLong f = e.sum((ExpVectorLong) me.getKey());
                IExpr y = a.multiply((IExpr) me.getValue());
                IExpr x = (IExpr) nv.get(f);
                if (x != null) {
                    x = (IExpr) x.subtract(y);
                    if (x.isZero()) {
                        nv.remove(f);
                    } else {
                        nv.put(f, x);
                    }
                } else if (!y.isZero()) {
                    nv.put(f, y.negate());
                }
            }
            return n;
        }
        throw new AssertionError();
    }

    public ExprPolynomial negate() {
        ExprPolynomial n = this.ring.getZero().copy();
        SortedMap<ExpVectorLong, IExpr> v = n.val;
        for (Entry<ExpVectorLong, IExpr> m : this.val.entrySet()) {
            v.put(m.getKey(), ((IExpr) m.getValue()).negate());
        }
        return n;
    }

    public ExprPolynomial abs() {
        if (leadingBaseCoefficient().signum() < 0) {
            return negate();
        }
        return this;
    }

    public ExprPolynomial multiply(ExprPolynomial S) {
        if (S == null) {
            return this.ring.getZero();
        }
        if (S.isZero()) {
            return this.ring.getZero();
        }
        if (isZero()) {
            return this;
        }
        if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
            ExprPolynomial p = this.ring.getZero().copy();
            SortedMap<ExpVectorLong, IExpr> pv = p.val;
            for (Entry<ExpVectorLong, IExpr> m1 : this.val.entrySet()) {
                IExpr c1 = (IExpr) m1.getValue();
                ExpVectorLong e1 = (ExpVectorLong) m1.getKey();
                for (Entry<ExpVectorLong, IExpr> m2 : S.val.entrySet()) {
                    ExpVectorLong e2 = (ExpVectorLong) m2.getKey();
                    IExpr c = c1.multiply((IExpr) m2.getValue());
                    if (!c.isZero()) {
                        ExpVectorLong e = e1.sum(e2);
                        IExpr c0 = (IExpr) pv.get(e);
                        if (c0 == null) {
                            pv.put(e, c);
                        } else {
                            c0 = (IExpr) c0.sum(c);
                            if (c0.isZero()) {
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

    public ExprPolynomial multiply(IExpr s) {
        if (s == null) {
            return this.ring.getZero();
        }
        if (s.isZero()) {
            return this.ring.getZero();
        }
        if (isZero()) {
            return this;
        }
        ExprPolynomial p = this.ring.getZero().copy();
        SortedMap<ExpVectorLong, IExpr> pv = p.val;
        for (Entry<ExpVectorLong, IExpr> m1 : this.val.entrySet()) {
            ExpVectorLong e1 = (ExpVectorLong) m1.getKey();
            IExpr c = ((IExpr) m1.getValue()).multiply(s);
            if (!c.isZero()) {
                pv.put(e1, c);
            }
        }
        return p;
    }

    public ExprPolynomial monic() {
        if (isZero()) {
            return this;
        }
        IExpr lc = leadingBaseCoefficient();
        return lc.isUnit() ? multiply(lc.inverse()) : this;
    }

    public ExprPolynomial multiply(IExpr s, ExpVectorLong e) {
        if (s == null) {
            return this.ring.getZero();
        }
        if (s.isZero()) {
            return this.ring.getZero();
        }
        if (isZero()) {
            return this;
        }
        ExprPolynomial p = this.ring.getZero().copy();
        SortedMap<ExpVectorLong, IExpr> pv = p.val;
        for (Entry<ExpVectorLong, IExpr> m1 : this.val.entrySet()) {
            ExpVectorLong e1 = (ExpVectorLong) m1.getKey();
            IExpr c = ((IExpr) m1.getValue()).multiply(s);
            if (!c.isZero()) {
                pv.put(e1.sum(e), c);
            }
        }
        return p;
    }

    public ExprPolynomial multiply(ExpVectorLong e) {
        if (isZero()) {
            return this;
        }
        ExprPolynomial p = this.ring.getZero().copy();
        SortedMap<ExpVectorLong, IExpr> pv = p.val;
        for (Entry<ExpVectorLong, IExpr> m1 : this.val.entrySet()) {
            pv.put(((ExpVectorLong) m1.getKey()).sum(e), (IExpr) m1.getValue());
        }
        return p;
    }

    public ExprPolynomial multiply(Entry<ExpVectorLong, IExpr> m) {
        if (m == null) {
            return this.ring.getZero();
        }
        return multiply((IExpr) m.getValue(), (ExpVectorLong) m.getKey());
    }

    public ExprPolynomial divide(IExpr s) {
        if (s == null || s.isZero()) {
            throw new ArithmeticException("division by zero");
        } else if (isZero()) {
            return this;
        } else {
            ExprPolynomial p = this.ring.getZero().copy();
            SortedMap<ExpVectorLong, IExpr> pv = p.val;
            for (Entry<ExpVectorLong, IExpr> m : this.val.entrySet()) {
                ExpVectorLong e = (ExpVectorLong) m.getKey();
                IExpr c1 = (IExpr) m.getValue();
                IExpr c = c1.divide(s);
                if (this.debug) {
                    IExpr x = (IExpr) c1.remainder(s);
                    if (!x.isZero()) {
                        logger.info("divide x = " + x);
                        throw new ArithmeticException("no exact division: " + c1 + "/" + s);
                    }
                }
                if (c.isZero()) {
                    throw new ArithmeticException("no exact division: " + c1 + "/" + s + ", in " + this);
                }
                pv.put(e, c);
            }
            return p;
        }
    }

    public ExprPolynomial[] quotientRemainder(ExprPolynomial S) {
        if (S == null || S.isZero()) {
            throw new ArithmeticException("division by zero");
        }
        IExpr c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            IExpr ci = c.inverse();
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                ExpVectorLong e = S.leadingExpVectorLong();
                ExprPolynomial q = this.ring.getZero().copy();
                ExprPolynomial r = copy();
                while (!r.isZero()) {
                    ExpVectorLong f = r.leadingExpVectorLong();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    IExpr a = r.leadingBaseCoefficient();
                    f = f.subtract(e);
                    a = a.multiply(ci);
                    q = q.sum(a, f);
                    r = r.subtract(S.multiply(a, f));
                }
                return new ExprPolynomial[]{q, r};
            }
            throw new AssertionError();
        }
        throw new ArithmeticException("lbcf not invertible " + c);
    }

    @Deprecated
    public ExprPolynomial[] divideAndRemainder(ExprPolynomial S) {
        return quotientRemainder(S);
    }

    public ExprPolynomial divide(ExprPolynomial S) {
        return quotientRemainder(S)[0];
    }

    public ExprPolynomial remainder(ExprPolynomial S) {
        if (S == null || S.isZero()) {
            throw new ArithmeticException("division by zero");
        }
        IExpr c = S.leadingBaseCoefficient();
        if (c.isUnit()) {
            IExpr ci = c.inverse();
            if ($assertionsDisabled || this.ring.nvar == S.ring.nvar) {
                ExpVectorLong e = S.leadingExpVectorLong();
                ExprPolynomial r = copy();
                while (!r.isZero()) {
                    ExpVectorLong f = r.leadingExpVectorLong();
                    if (!f.multipleOf(e)) {
                        break;
                    }
                    IExpr a = r.leadingBaseCoefficient();
                    r = r.subtract(S.multiply(a.multiply(ci), f.subtract(e)));
                }
                return r;
            }
            throw new AssertionError();
        }
        throw new ArithmeticException("lbc not invertible " + c);
    }

    public ExprPolynomial gcd(ExprPolynomial S) {
        if (S == null || S.isZero()) {
            return this;
        }
        if (isZero()) {
            return S;
        }
        if (this.ring.nvar != 1) {
            throw new IllegalArgumentException("not univariate polynomials" + this.ring);
        }
        ExprPolynomial q = this;
        ExprPolynomial r = S;
        while (!r.isZero()) {
            ExprPolynomial x = q.remainder(r);
            q = r;
            r = x;
        }
        return q.monic();
    }

    public ExprPolynomial[] egcd(ExprPolynomial S) {
        ExprPolynomial[] ret = new ExprPolynomial[]{null, null, null};
        if (S == null || S.isZero()) {
            ret[0] = this;
            ret[1] = this.ring.getOne();
            ret[2] = this.ring.getZero();
        } else if (isZero()) {
            ret[0] = S;
            ret[1] = this.ring.getZero();
            ret[2] = this.ring.getOne();
        } else {
            int i = this.ring.nvar;
            if (r0 != 1) {
                throw new IllegalArgumentException(getClass().getName() + " not univariate polynomials" + this.ring);
            } else if (isConstant() && S.isConstant()) {
                IExpr[] gg = (IExpr[]) leadingBaseCoefficient().egcd(S.leadingBaseCoefficient());
                ExprPolynomial z = this.ring.getZero();
                ret[0] = z.sum(gg[0]);
                ret[1] = z.sum(gg[1]);
                ret[2] = z.sum(gg[2]);
            } else {
                ExprPolynomial q = this;
                ExprPolynomial r = S;
                ExprPolynomial c1 = this.ring.getOne().copy();
                ExprPolynomial d1 = this.ring.getZero().copy();
                ExprPolynomial c2 = this.ring.getZero().copy();
                ExprPolynomial d2 = this.ring.getOne().copy();
                while (!r.isZero()) {
                    ExprPolynomial[] qr = q.quotientRemainder(r);
                    q = qr[0];
                    ExprPolynomial x1 = c1.subtract(q.multiply(d1));
                    ExprPolynomial x2 = c2.subtract(q.multiply(d2));
                    c1 = d1;
                    c2 = d2;
                    d1 = x1;
                    d2 = x2;
                    q = r;
                    r = qr[1];
                }
                IExpr g = q.leadingBaseCoefficient();
                if (g.isUnit()) {
                    IExpr h = g.inverse();
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

    public ExprPolynomial[] hegcd(ExprPolynomial S) {
        ExprPolynomial[] ret = new ExprPolynomial[]{null, null};
        if (S == null || S.isZero()) {
            ret[0] = this;
            ret[1] = this.ring.getOne();
        } else if (isZero()) {
            ret[0] = S;
        } else if (this.ring.nvar != 1) {
            throw new IllegalArgumentException(getClass().getName() + " not univariate polynomials" + this.ring);
        } else {
            ExprPolynomial q = this;
            ExprPolynomial r = S;
            ExprPolynomial c1 = this.ring.getOne().copy();
            ExprPolynomial d1 = this.ring.getZero().copy();
            while (!r.isZero()) {
                ExprPolynomial[] qr = q.quotientRemainder(r);
                ExprPolynomial x1 = c1.subtract(qr[0].multiply(d1));
                c1 = d1;
                d1 = x1;
                q = r;
                r = qr[1];
            }
            IExpr g = q.leadingBaseCoefficient();
            if (g.isUnit()) {
                IExpr h = g.inverse();
                q = q.multiply(h);
                c1 = c1.multiply(h);
            }
            ret[0] = q;
            ret[1] = c1;
        }
        return ret;
    }

    public ExprPolynomial inverse() {
        if (isUnit()) {
            return this.ring.getOne().multiply(leadingBaseCoefficient().inverse());
        }
        throw new NotInvertibleException("element not invertible " + this + " :: " + this.ring);
    }

    public ExprPolynomial modInverse(ExprPolynomial m) {
        if (isZero()) {
            throw new NotInvertibleException("zero is not invertible");
        }
        ExprPolynomial[] hegcd = hegcd(m);
        ExprPolynomial a = hegcd[0];
        if (a.isUnit()) {
            ExprPolynomial b = hegcd[1];
            if (!b.isZero()) {
                return b;
            }
            throw new NotInvertibleException("element not invertible, divisible by modul");
        }
        throw new AlgebraicNotInvertibleException("element not invertible, gcd != 1", m, a, m.divide(a));
    }

    public ExprPolynomial extend(ExprPolynomialRing pfac, int j, long k) {
        if (this.ring.equals(pfac)) {
            return this;
        }
        ExprPolynomial Cp = pfac.getZero().copy();
        if (isZero()) {
            return Cp;
        }
        int i = pfac.nvar - this.ring.nvar;
        Map<ExpVectorLong, IExpr> C = Cp.val;
        for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
            IExpr a = (IExpr) y.getValue();
            C.put(((ExpVectorLong) y.getKey()).extend(i, j, k), a);
        }
        return Cp;
    }

    public ExprPolynomial extendLower(ExprPolynomialRing pfac, int j, long k) {
        if (this.ring.equals(pfac)) {
            return this;
        }
        ExprPolynomial Cp = pfac.getZero().copy();
        if (isZero()) {
            return Cp;
        }
        int i = pfac.nvar - this.ring.nvar;
        Map<ExpVectorLong, IExpr> C = Cp.val;
        for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
            IExpr a = (IExpr) y.getValue();
            C.put(((ExpVectorLong) y.getKey()).extendLower(i, j, k), a);
        }
        return Cp;
    }

    public Map<ExpVectorLong, ExprPolynomial> contract(ExprPolynomialRing pfac) {
        ExprPolynomial zero = pfac.getZero();
        Map<ExpVectorLong, ExprPolynomial> B = new TreeMap(new ExprTermOrder(2).getAscendComparator());
        if (!isZero()) {
            int i = this.ring.nvar - pfac.nvar;
            for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
                ExpVectorLong e = (ExpVectorLong) y.getKey();
                IExpr a = (IExpr) y.getValue();
                ExpVectorLong f = e.contract(0, i);
                ExpVectorLong g = e.contract(i, e.length() - i);
                ExprPolynomial p = (ExprPolynomial) B.get(f);
                if (p == null) {
                    p = zero;
                }
                B.put(f, p.sum(a, g));
            }
        }
        return B;
    }

    public ExprPolynomial contractCoeff(ExprPolynomialRing pfac) {
        Map<ExpVectorLong, ExprPolynomial> ms = contract(pfac);
        ExprPolynomial c = pfac.getZero();
        for (Entry<ExpVectorLong, ExprPolynomial> m : ms.entrySet()) {
            if (((ExpVectorLong) m.getKey()).isZero()) {
                c = (ExprPolynomial) m.getValue();
            } else {
                throw new RuntimeException("wrong coefficient contraction " + m + ", pol =  " + c);
            }
        }
        return c;
    }

    public ExprPolynomial extendUnivariate(ExprPolynomialRing pfac, int i) {
        if (i < 0 || pfac.nvar < i) {
            throw new IllegalArgumentException("index " + i + "out of range " + pfac.nvar);
        } else if (this.ring.nvar != 1) {
            throw new IllegalArgumentException("polynomial not univariate " + this.ring.nvar);
        } else if (isOne()) {
            return pfac.getOne();
        } else {
            int j = (pfac.nvar - 1) - i;
            ExprPolynomial Cp = pfac.getZero().copy();
            if (isZero()) {
                return Cp;
            }
            Map<ExpVectorLong, IExpr> C = Cp.val;
            for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
                IExpr a = (IExpr) y.getValue();
                C.put(new ExpVectorLong(pfac.nvar, j, ((ExpVectorLong) y.getKey()).getVal(0)), a);
            }
            return Cp;
        }
    }

    public ExprPolynomial homogenize(ExprPolynomialRing pfac) {
        if (this.ring.equals(pfac)) {
            throw new UnsupportedOperationException("case with same ring not implemented");
        }
        ExprPolynomial Cp = pfac.getZero().copy();
        if (!isZero()) {
            long deg = totalDegree();
            Map<ExpVectorLong, IExpr> C = Cp.val;
            for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
                ExpVectorLong e = (ExpVectorLong) y.getKey();
                C.put(e.extend(1, 0, deg - e.totalDeg()), (IExpr) y.getValue());
            }
        }
        return Cp;
    }

    public ExprPolynomial deHomogenize(ExprPolynomialRing pfac) {
        if (this.ring.equals(pfac)) {
            throw new UnsupportedOperationException("case with same ring not implemented");
        }
        ExprPolynomial Cp = pfac.getZero().copy();
        if (!isZero()) {
            Map<ExpVectorLong, IExpr> C = Cp.val;
            for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
                IExpr a = (IExpr) y.getValue();
                C.put(((ExpVectorLong) y.getKey()).contract(1, pfac.nvar), a);
            }
        }
        return Cp;
    }

    public ExprPolynomial reverse(ExprPolynomialRing oring) {
        ExprPolynomial Cp = oring.getZero().copy();
        if (!isZero()) {
            int k = -1;
            if (oring.tord.getEvord2() != 0 && oring.partial) {
                k = oring.tord.getSplit();
            }
            Map<ExpVectorLong, IExpr> C = Cp.val;
            for (Entry<ExpVectorLong, IExpr> y : this.val.entrySet()) {
                ExpVectorLong f;
                ExpVectorLong e = (ExpVectorLong) y.getKey();
                if (k >= 0) {
                    f = e.reverse(k);
                } else {
                    f = e.reverse();
                }
                C.put(f, (IExpr) y.getValue());
            }
        }
        return Cp;
    }

    public Iterator coefficientIterator() {
        return this.val.values().iterator();
    }

    public Iterator<ExpVectorLong> exponentIterator() {
        return this.val.keySet().iterator();
    }

    public Iterator<ExprMonomial> iterator() {
        return new ExprPolyIterator(this.val);
    }

    public ExprPolynomial map(Function<IExpr, IExpr> f) {
        ExprPolynomial n = this.ring.getZero().copy();
        SortedMap<ExpVectorLong, IExpr> nv = n.val;
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            ExprMonomial m = (ExprMonomial) i$.next();
            IExpr c = (IExpr) f.apply(m.c);
            if (!(c == null || c.isZero())) {
                nv.put(m.e, c);
            }
        }
        return n;
    }

    public IAST monomialList() {
        IAST result = F.List();
        for (ExpVectorLong expArray : this.val.keySet()) {
            IAST times = F.Times();
            times.add(this.val.get(expArray));
            appendToExpr(times, expArray, this.ring.vars);
            result.add(times);
        }
        return result;
    }

    private void appendToExpr(IAST times, ExpVectorLong expArray, IAST variables) {
        long[] arr = expArray.getVal();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != 0) {
                if (arr[i] == 1) {
                    times.add(variables.get(i + 1));
                } else {
                    times.add(F.Power((IExpr) variables.get(i + 1), arr[i]));
                }
            }
        }
    }

    public IAST coefficientList() {
        Validate.checkSize(this.ring.getVars(), 2);
        if (this.ring.tord.getEvord() == 4) {
            IExpr[] exprs = new IExpr[(((int) degree()) + 1)];
            for (int i = 0; i < exprs.length; i++) {
                exprs[i] = F.C0;
            }
            for (ExpVectorLong expArray : this.val.keySet()) {
                exprs[(int) expArray.getVal(0)] = (IExpr) this.val.get(expArray);
            }
            return F.ast(exprs, F.List);
        }
        long lastDegree = 0;
        IAST result = F.List();
        for (ExpVectorLong expArray2 : this.val.keySet()) {
            long exp = expArray2.getVal(0);
            while (lastDegree < exp) {
                result.add(F.C0);
                lastDegree++;
            }
            if (lastDegree == exp) {
                result.add(this.val.get(expArray2));
                lastDegree++;
            }
        }
        return result;
    }

    public ExprPolynomial derivative() {
        Validate.checkSize(this.ring.getVars(), 2);
        ExprPolynomial result = new ExprPolynomial(this.ring);
        for (ExpVectorLong expArray : this.val.keySet()) {
            long exp = expArray.getVal(0);
            if (exp != 0) {
                ExpVectorLong copy = expArray.copy();
                copy.val[0] = exp - 1;
                result.doAddTo(((IExpr) this.val.get(expArray)).times(F.integer(exp)), copy);
            }
        }
        return result;
    }

    public IExpr getExpr() {
        return getExpr(null);
    }

    public IExpr getExpr(IExpr variable) {
        if (length() == 0) {
            return F.C0;
        }
        boolean getVar = variable == null;
        IAST result = F.Plus();
        IAST vars = this.ring.vars;
        Iterator i$ = iterator();
        while (i$.hasNext()) {
            ExprMonomial monomial = (ExprMonomial) i$.next();
            IExpr coeff = monomial.coefficient();
            ExpVectorLong exp = monomial.exponent();
            IAST monomTimes = F.Times();
            if (!coeff.isOne()) {
                monomTimes.add(coeff);
            }
            for (int i = 0; i < exp.length(); i++) {
                long lExp = exp.getVal(i);
                if (lExp != 0) {
                    if (getVar) {
                        variable = (IExpr) vars.get(i + 1);
                    }
                    if (lExp == 1) {
                        monomTimes.add(variable);
                    } else {
                        monomTimes.add(F.Power(variable, F.integer(lExp)));
                    }
                }
            }
            result.add(monomTimes.getOneIdentity(F.C1));
        }
        return result.getOneIdentity(F.C0);
    }
}
