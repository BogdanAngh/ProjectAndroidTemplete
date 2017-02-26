package edu.jas.ps;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.BinaryFunctor;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.Selector;
import edu.jas.structure.UnaryFunctor;
import edu.jas.util.MapEntry;
import io.github.kexanie.library.BuildConfig;
import java.util.BitSet;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class MultiVarPowerSeries<C extends RingElem<C>> implements RingElem<MultiVarPowerSeries<C>> {
    private ExpVector evorder;
    MultiVarCoefficients<C> lazyCoeffs;
    private int order;
    public final MultiVarPowerSeriesRing<C> ring;
    private int truncate;

    class 10 extends MultiVarCoefficients<C> {
        final /* synthetic */ Selector val$sel;

        10(MultiVarPowerSeriesRing x0, Selector selector) {
            this.val$sel = selector;
            super(x0);
        }

        public C generate(ExpVector i) {
            C c = MultiVarPowerSeries.this.coefficient(i);
            return this.val$sel.select(c) ? c : (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
        }
    }

    class 11 extends MultiVarCoefficients<C> {
        ExpVectorIterable ib;
        Iterator<ExpVector> pos;
        final /* synthetic */ Selector val$sel;

        11(MultiVarPowerSeriesRing x0, Selector selector) {
            this.val$sel = selector;
            super(x0);
            this.ib = new ExpVectorIterable(MultiVarPowerSeries.this.ring.nvar, true, (long) MultiVarPowerSeries.this.truncate);
            this.pos = this.ib.iterator();
        }

        public C generate(ExpVector i) {
            if (i.signum() > 0) {
                int[] deps = i.dependencyOnVariables();
                get(i.subst(deps[0], i.getVal(deps[0]) - 1));
            }
            C c;
            do {
                c = null;
                if (!this.pos.hasNext()) {
                    break;
                }
                c = MultiVarPowerSeries.this.coefficient((ExpVector) this.pos.next());
            } while (!this.val$sel.select(c));
            if (c == null) {
                return (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
            }
            return c;
        }
    }

    class 12 extends MultiVarCoefficients<C> {
        final /* synthetic */ UnaryFunctor val$f;

        12(MultiVarPowerSeriesRing x0, UnaryFunctor unaryFunctor) {
            this.val$f = unaryFunctor;
            super(x0);
        }

        public C generate(ExpVector i) {
            return (RingElem) this.val$f.eval(MultiVarPowerSeries.this.coefficient(i));
        }
    }

    class 13 extends MultiVarCoefficients<C> {
        final /* synthetic */ BinaryFunctor val$f;
        final /* synthetic */ MultiVarPowerSeries val$ps;

        13(MultiVarPowerSeriesRing x0, BinaryFunctor binaryFunctor, MultiVarPowerSeries multiVarPowerSeries) {
            this.val$f = binaryFunctor;
            this.val$ps = multiVarPowerSeries;
            super(x0);
        }

        public C generate(ExpVector i) {
            return (RingElem) this.val$f.eval(MultiVarPowerSeries.this.coefficient(i), this.val$ps.coefficient(i));
        }
    }

    class 14 implements BinaryFunctor<C, C, C> {
        14() {
        }

        public C eval(C c1, C c2) {
            return (RingElem) c1.sum(c2);
        }
    }

    class 15 implements BinaryFunctor<C, C, C> {
        15() {
        }

        public C eval(C c1, C c2) {
            return (RingElem) c1.subtract(c2);
        }
    }

    class 16 implements UnaryFunctor<C, C> {
        final /* synthetic */ RingElem val$a;

        16(RingElem ringElem) {
            this.val$a = ringElem;
        }

        public C eval(C c) {
            return (RingElem) c.multiply(this.val$a);
        }
    }

    class 17 implements UnaryFunctor<C, C> {
        final /* synthetic */ RingElem val$b;

        17(RingElem ringElem) {
            this.val$b = ringElem;
        }

        public C eval(C c) {
            return (RingElem) this.val$b.multiply(c);
        }
    }

    class 18 implements UnaryFunctor<C, C> {
        18() {
        }

        public C eval(C c) {
            return (RingElem) c.negate();
        }
    }

    class 19 extends MultiVarCoefficients<C> {
        final /* synthetic */ MultiVarPowerSeries val$ps;

        19(MultiVarPowerSeriesRing x0, MultiVarPowerSeries multiVarPowerSeries) {
            this.val$ps = multiVarPowerSeries;
            super(x0);
        }

        public C generate(ExpVector e) {
            long tdeg = e.totalDeg();
            if (!MultiVarPowerSeries.this.lazyCoeffs.homCheck.get((int) tdeg)) {
                return (RingElem) MultiVarPowerSeries.this.coefficient(e).sum(this.val$ps.coefficient(e));
            }
            GenPolynomial<C> p = MultiVarPowerSeries.this.homogeneousPart(tdeg).sum(this.val$ps.homogeneousPart(tdeg));
            this.coeffCache.put(Long.valueOf(tdeg), p);
            this.homCheck.set((int) tdeg);
            return p.coefficient(e);
        }
    }

    class 1 extends MultiVarCoefficients<C> {
        final /* synthetic */ int val$r;

        1(MultiVarPowerSeriesRing x0, int i) {
            this.val$r = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            return MultiVarPowerSeries.this.coefficient(i.subst(this.val$r, i.getVal(this.val$r) + 1));
        }
    }

    class 20 extends MultiVarCoefficients<C> {
        final /* synthetic */ MultiVarPowerSeries val$ps;

        20(MultiVarPowerSeriesRing x0, MultiVarPowerSeries multiVarPowerSeries) {
            this.val$ps = multiVarPowerSeries;
            super(x0);
        }

        public C generate(ExpVector e) {
            long tdeg = e.totalDeg();
            if (!MultiVarPowerSeries.this.lazyCoeffs.homCheck.get((int) tdeg)) {
                return (RingElem) MultiVarPowerSeries.this.coefficient(e).subtract(this.val$ps.coefficient(e));
            }
            GenPolynomial<C> p = MultiVarPowerSeries.this.homogeneousPart(tdeg).subtract(this.val$ps.homogeneousPart(tdeg));
            this.coeffCache.put(Long.valueOf(tdeg), p);
            this.homCheck.set((int) tdeg);
            return p.coefficient(e);
        }
    }

    class 21 extends MultiVarCoefficients<C> {
        final /* synthetic */ MultiVarPowerSeries val$ps;

        21(MultiVarPowerSeriesRing x0, MultiVarPowerSeries multiVarPowerSeries) {
            this.val$ps = multiVarPowerSeries;
            super(x0);
        }

        public C generate(ExpVector e) {
            long tdeg = e.totalDeg();
            GenPolynomial<C> p = null;
            for (int k = 0; ((long) k) <= tdeg; k++) {
                GenPolynomial m = MultiVarPowerSeries.this.homogeneousPart((long) k).multiply(this.val$ps.homogeneousPart(tdeg - ((long) k)));
                if (p == null) {
                    p = m;
                } else {
                    p = p.sum(m);
                }
            }
            this.coeffCache.put(Long.valueOf(tdeg), p);
            this.homCheck.set((int) tdeg);
            return p.coefficient(e);
        }
    }

    class 22 extends MultiVarCoefficients<C> {
        22(MultiVarPowerSeriesRing x0) {
            super(x0);
        }

        public C generate(ExpVector e) {
            long tdeg = e.totalDeg();
            RingElem d = (RingElem) MultiVarPowerSeries.this.leadingCoefficient().inverse();
            if (tdeg == 0) {
                return d;
            }
            GenPolynomial<C> p = null;
            for (int k = 0; ((long) k) < tdeg; k++) {
                GenPolynomial m = getHomPart((long) k).multiply(MultiVarPowerSeries.this.homogeneousPart(tdeg - ((long) k)));
                if (p == null) {
                    p = m;
                } else {
                    p = p.sum(m);
                }
            }
            p = p.multiply((RingElem) d.negate());
            this.coeffCache.put(Long.valueOf(tdeg), p);
            this.homCheck.set((int) tdeg);
            return p.coefficient(e);
        }
    }

    class 23 extends MultiVarCoefficients<C> {
        final /* synthetic */ int val$r;

        23(MultiVarPowerSeriesRing x0, int i) {
            this.val$r = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            long d = i.getVal(this.val$r);
            return (RingElem) MultiVarPowerSeries.this.coefficient(i.subst(this.val$r, d + 1)).multiply((MonoidElem) MultiVarPowerSeries.this.ring.coFac.fromInteger(d + 1));
        }
    }

    class 24 extends MultiVarCoefficients<C> {
        final /* synthetic */ RingElem val$c;
        final /* synthetic */ int val$r;

        24(MultiVarPowerSeriesRing x0, RingElem ringElem, int i) {
            this.val$c = ringElem;
            this.val$r = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return this.val$c;
            }
            long d = i.getVal(this.val$r);
            if (d <= 0) {
                return (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
            }
            return (RingElem) MultiVarPowerSeries.this.coefficient(i.subst(this.val$r, d - 1)).divide((MonoidElem) MultiVarPowerSeries.this.ring.coFac.fromInteger(d));
        }
    }

    class 2 extends MultiVarCoefficients<C> {
        final /* synthetic */ RingElem val$h;
        final /* synthetic */ int val$r;

        2(MultiVarPowerSeriesRing x0, RingElem ringElem, int i) {
            this.val$h = ringElem;
            this.val$r = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return this.val$h;
            }
            ExpVector e = i.subst(this.val$r, i.getVal(this.val$r) - 1);
            if (e.signum() < 0) {
                return (RingElem) this.pfac.coFac.getZERO();
            }
            return MultiVarPowerSeries.this.coefficient(e);
        }
    }

    class 3 extends MultiVarCoefficients<C> {
        final /* synthetic */ int val$k;
        final /* synthetic */ int val$r;

        3(MultiVarPowerSeriesRing x0, int i, int i2) {
            this.val$r = i;
            this.val$k = i2;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.getVal(this.val$r) - ((long) this.val$k) < 0) {
                return (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
            }
            return MultiVarPowerSeries.this.coefficient(i.subst(this.val$r, i.getVal(this.val$r) - ((long) this.val$k)));
        }
    }

    class 4 extends MultiVarCoefficients<C> {
        4(GenPolynomialRing x0, HashMap x1, HashSet x2, BitSet x3) {
            super(x0, x1, x2, x3);
        }

        public C generate(ExpVector i) {
            return MultiVarPowerSeries.this.coefficient(i);
        }
    }

    class 5 extends MultiVarCoefficients<C> {
        final /* synthetic */ ExpVector val$k;

        5(MultiVarPowerSeriesRing x0, ExpVector expVector) {
            this.val$k = expVector;
            super(x0);
        }

        public C generate(ExpVector i) {
            ExpVector d = i.subtract(this.val$k);
            if (d.signum() < 0) {
                return (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
            }
            return MultiVarPowerSeries.this.coefficient(d);
        }
    }

    class 6 extends MultiVarCoefficients<C> {
        final /* synthetic */ RingElem val$c;
        final /* synthetic */ ExpVector val$k;

        6(MultiVarPowerSeriesRing x0, ExpVector expVector, RingElem ringElem) {
            this.val$k = expVector;
            this.val$c = ringElem;
            super(x0);
        }

        public C generate(ExpVector i) {
            ExpVector d = i.subtract(this.val$k);
            if (d.signum() < 0) {
                return (RingElem) MultiVarPowerSeries.this.ring.coFac.getZERO();
            }
            long tdegd = d.totalDeg();
            if (!MultiVarPowerSeries.this.lazyCoeffs.homCheck.get((int) tdegd)) {
                return (RingElem) MultiVarPowerSeries.this.coefficient(d).multiply(this.val$c);
            }
            GenPolynomial<C> p = MultiVarPowerSeries.this.homogeneousPart(tdegd).multiply(this.val$c, this.val$k);
            long tdegi = i.totalDeg();
            this.coeffCache.put(Long.valueOf(tdegi), p);
            this.homCheck.set((int) tdegi);
            return p.coefficient(i);
        }
    }

    class 7 extends MultiVarCoefficients<C> {
        7(GenPolynomialRing x0, HashMap x1, HashSet x2, BitSet x3) {
            super(x0, x1, x2, x3);
        }

        public C generate(ExpVector i) {
            return MultiVarPowerSeries.this.coefficient(i);
        }
    }

    class 8 extends MultiVarCoefficients<C> {
        8(GenPolynomialRing x0, HashMap x1, HashSet x2, BitSet x3) {
            super(x0, x1, x2, x3);
        }

        public C generate(ExpVector i) {
            return MultiVarPowerSeries.this.coefficient(i);
        }
    }

    class 9 extends MultiVarCoefficients<C> {
        9(GenPolynomialRing x0, HashMap x1, HashSet x2, BitSet x3) {
            super(x0, x1, x2, x3);
        }

        public C generate(ExpVector i) {
            return MultiVarPowerSeries.this.coefficient(i);
        }
    }

    private MultiVarPowerSeries() {
        this.order = -1;
        this.evorder = null;
        throw new IllegalArgumentException("do not use no-argument constructor");
    }

    MultiVarPowerSeries(MultiVarPowerSeriesRing<C> ring) {
        this.order = -1;
        this.evorder = null;
        this.ring = ring;
        this.lazyCoeffs = null;
        this.truncate = ring.truncate;
    }

    public MultiVarPowerSeries(MultiVarPowerSeriesRing<C> ring, MultiVarCoefficients<C> lazyCoeffs) {
        this(ring, lazyCoeffs, ring.truncate);
    }

    public MultiVarPowerSeries(MultiVarPowerSeriesRing<C> ring, MultiVarCoefficients<C> lazyCoeffs, int trunc) {
        this.order = -1;
        this.evorder = null;
        if (lazyCoeffs == null || ring == null) {
            throw new IllegalArgumentException("null not allowed: ring = " + ring + ", lazyCoeffs = " + lazyCoeffs);
        }
        this.ring = ring;
        this.lazyCoeffs = lazyCoeffs;
        this.truncate = Math.min(trunc, ring.truncate);
    }

    public MultiVarPowerSeriesRing<C> factory() {
        return this.ring;
    }

    public MultiVarPowerSeries<C> copy() {
        return new MultiVarPowerSeries(this.ring, this.lazyCoeffs);
    }

    public String toString() {
        return toString(this.truncate);
    }

    public String toString(int trunc) {
        StringBuffer sb = new StringBuffer();
        MultiVarPowerSeries<C> s = this;
        String[] vars = this.ring.vars;
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) trunc).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            C c = coefficient(i);
            int si = c.signum();
            if (si != 0) {
                if (si <= 0) {
                    RingElem c2 = (RingElem) c.negate();
                    sb.append(" - ");
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (!c.isONE() || i.isZERO()) {
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append("{ ");
                    }
                    sb.append(c.toString());
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append(" }");
                    }
                    if (!i.isZERO()) {
                        sb.append(" * ");
                    }
                }
                if (!i.isZERO()) {
                    sb.append(i.toString(vars));
                }
            }
        }
        if (sb.length() == 0) {
            sb.append(Constants.ZERO);
        }
        sb.append(" + BigO( (" + this.ring.varsToString() + ")^" + (trunc + 1) + "(" + (this.ring.truncate + 1) + ") )");
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer(BuildConfig.FLAVOR);
        MultiVarPowerSeries<C> s = this;
        String[] vars = this.ring.vars;
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) this.truncate).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            C c = coefficient(i);
            int si = c.signum();
            if (si != 0) {
                if (si <= 0) {
                    RingElem c2 = (RingElem) c.negate();
                    sb.append(" - ");
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (!c.isONE() || i.isZERO()) {
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append("( ");
                    }
                    sb.append(c.toScript());
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append(" )");
                    }
                    if (!i.isZERO()) {
                        sb.append(" * ");
                    }
                }
                if (!i.isZERO()) {
                    sb.append(i.toScript(vars));
                }
            }
        }
        if (sb.length() == 0) {
            sb.append(Constants.ZERO);
        }
        sb.append(" + BigO( (" + this.ring.varsToString() + ")**" + (this.truncate + 1) + " )");
        return sb.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public C coefficient(ExpVector index) {
        if (index != null) {
            return this.lazyCoeffs.get(index);
        }
        throw new IndexOutOfBoundsException("null index not allowed");
    }

    public GenPolynomial<C> homogeneousPart(long tdeg) {
        return this.lazyCoeffs.getHomPart(tdeg);
    }

    public GenPolynomial<C> asPolynomial() {
        GenPolynomial<C> p = homogeneousPart(0);
        for (int i = 1; i <= this.truncate; i++) {
            p = p.sum(homogeneousPart((long) i));
        }
        return p;
    }

    public C leadingCoefficient() {
        return coefficient(this.ring.EVZERO);
    }

    public MultiVarPowerSeries<C> reductum(int r) {
        if (r >= 0 && this.ring.nvar >= r) {
            return new MultiVarPowerSeries(this.ring, new 1(this.ring, r));
        }
        throw new IllegalArgumentException("variable index out of bound");
    }

    public MultiVarPowerSeries<C> prepend(C h, int r) {
        if (r >= 0 && this.ring.nvar >= r) {
            return new MultiVarPowerSeries(this.ring, new 2(this.ring, h, r));
        }
        throw new IllegalArgumentException("variable index out of bound");
    }

    public MultiVarPowerSeries<C> shift(int k, int r) {
        if (r < 0 || this.ring.nvar < r) {
            throw new IllegalArgumentException("variable index out of bound");
        }
        return new MultiVarPowerSeries(this.ring, new 3(this.ring, r, k), Math.min(this.truncate + k, this.ring.truncate));
    }

    public MultiVarPowerSeries<C> reductum() {
        Entry<ExpVector, C> m = orderMonomial();
        if (m == null) {
            return this.ring.getZERO();
        }
        ExpVector e = (ExpVector) m.getKey();
        long d = e.totalDeg();
        MultiVarCoefficients<C> mc = this.lazyCoeffs;
        HashMap<Long, GenPolynomial<C>> cc = new HashMap(mc.coeffCache);
        GenPolynomial<C> p = (GenPolynomial) cc.get(Long.valueOf(d));
        if (!(p == null || p.isZERO())) {
            cc.put(Long.valueOf(d), p.subtract((RingElem) m.getValue(), e));
        }
        HashSet<ExpVector> z = new HashSet(mc.zeroCache);
        if (!mc.homCheck.get((int) d)) {
            z.add(e);
        }
        return new MultiVarPowerSeries(this.ring, new 4(mc.pfac, cc, z, mc.homCheck));
    }

    public MultiVarPowerSeries<C> shift(ExpVector k) {
        if (k == null) {
            throw new IllegalArgumentException("null ExpVector not allowed");
        } else if (k.signum() == 0) {
            return this;
        } else {
            return new MultiVarPowerSeries(this.ring, new 5(this.ring, k), Math.min(this.truncate + ((int) k.totalDeg()), this.ring.truncate));
        }
    }

    public MultiVarPowerSeries<C> multiply(C c, ExpVector k) {
        if (k == null) {
            throw new IllegalArgumentException("null ExpVector not allowed");
        } else if (k.signum() == 0) {
            return multiply((RingElem) c);
        } else {
            if (c.signum() == 0) {
                return this.ring.getZERO();
            }
            return new MultiVarPowerSeries(this.ring, new 6(this.ring, k, c), Math.min(this.ring.truncate, this.truncate + ((int) k.totalDeg())));
        }
    }

    public MultiVarPowerSeries<C> sum(Entry<ExpVector, C> m) {
        if (m != null) {
            return sum((RingElem) m.getValue(), (ExpVector) m.getKey());
        }
        throw new IllegalArgumentException("null Map.Entry not allowed");
    }

    public MultiVarPowerSeries<C> sum(C c, ExpVector k) {
        if (k == null) {
            throw new IllegalArgumentException("null ExpVector not allowed");
        } else if (c.signum() == 0) {
            return this;
        } else {
            long d = k.totalDeg();
            MultiVarCoefficients<C> mc = this.lazyCoeffs;
            HashMap<Long, GenPolynomial<C>> cc = new HashMap(mc.coeffCache);
            GenPolynomial<C> p = (GenPolynomial) cc.get(Long.valueOf(d));
            if (p == null) {
                p = mc.pfac.getZERO();
            }
            p = p.sum(c, k);
            cc.put(Long.valueOf(d), p);
            HashSet<ExpVector> z = new HashSet(mc.zeroCache);
            if (p.coefficient(k).isZERO() && !mc.homCheck.get((int) d)) {
                z.add(k);
            }
            return new MultiVarPowerSeries(this.ring, new 7(mc.pfac, cc, z, mc.homCheck));
        }
    }

    public MultiVarPowerSeries<C> subtract(C c, ExpVector k) {
        if (k == null) {
            throw new IllegalArgumentException("null ExpVector not allowed");
        } else if (c.signum() == 0) {
            return this;
        } else {
            long d = k.totalDeg();
            MultiVarCoefficients<C> mc = this.lazyCoeffs;
            HashMap<Long, GenPolynomial<C>> cc = new HashMap(mc.coeffCache);
            GenPolynomial<C> p = (GenPolynomial) cc.get(Long.valueOf(d));
            if (p == null) {
                p = mc.pfac.getZERO();
            }
            p = p.subtract(c, k);
            cc.put(Long.valueOf(d), p);
            HashSet<ExpVector> z = new HashSet(mc.zeroCache);
            if (p.coefficient(k).isZERO() && !mc.homCheck.get((int) d)) {
                z.add(k);
            }
            return new MultiVarPowerSeries(this.ring, new 8(mc.pfac, cc, z, mc.homCheck));
        }
    }

    public MultiVarPowerSeries<C> sum(MultiVarCoefficients<C> mvc) {
        MultiVarCoefficients<C> mc = this.lazyCoeffs;
        TreeMap<Long, GenPolynomial<C>> cc = new TreeMap(mc.coeffCache);
        TreeMap<Long, GenPolynomial<C>> ccv = new TreeMap(mvc.coeffCache);
        long d1 = cc.size() > 0 ? ((Long) cc.lastKey()).longValue() : 0;
        long d2 = ccv.size() > 0 ? ((Long) ccv.lastKey()).longValue() : 0;
        HashSet<ExpVector> z = new HashSet(mc.zeroCache);
        z.addAll(mvc.zeroCache);
        long d = Math.max(d1, d2);
        BitSet hc = new BitSet((int) d);
        long i = 0;
        while (i <= d) {
            GenPolynomial<C> p1 = (GenPolynomial) cc.get(Long.valueOf(i));
            GenPolynomial p2 = (GenPolynomial) mvc.coeffCache.get(Long.valueOf(i));
            if (p1 == null) {
                p1 = mc.pfac.getZERO();
            }
            if (p2 == null) {
                p2 = mc.pfac.getZERO();
            }
            GenPolynomial<C> p = p1.sum(p2);
            cc.put(Long.valueOf(i), p);
            if (mc.homCheck.get((int) i) && mvc.homCheck.get((int) i)) {
                hc.set((int) i);
            } else {
                Set<ExpVector> hashSet = new HashSet(p1.getMap().keySet());
                hashSet.addAll(p2.getMap().keySet());
                hashSet.removeAll(p.getMap().keySet());
                z.addAll(hashSet);
            }
            i++;
        }
        return new MultiVarPowerSeries(this.ring, new 9(mc.pfac, new HashMap(cc), z, hc));
    }

    public MultiVarPowerSeries<C> select(Selector<? super C> sel) {
        return new MultiVarPowerSeries(this.ring, new 10(this.ring, sel));
    }

    public MultiVarPowerSeries<C> shiftSelect(Selector<? super C> sel) {
        return new MultiVarPowerSeries(this.ring, new 11(this.ring, sel));
    }

    public MultiVarPowerSeries<C> map(UnaryFunctor<? super C, C> f) {
        return new MultiVarPowerSeries(this.ring, new 12(this.ring, f));
    }

    public MultiVarPowerSeries<C> zip(BinaryFunctor<? super C, ? super C, C> f, MultiVarPowerSeries<C> ps) {
        return new MultiVarPowerSeries(this.ring, new 13(this.ring, f, ps), Math.min(this.ring.truncate, Math.max(this.truncate, ps.truncate())));
    }

    public MultiVarPowerSeries<C> sumZip(MultiVarPowerSeries<C> ps) {
        return zip(new 14(), ps);
    }

    public MultiVarPowerSeries<C> subtractZip(MultiVarPowerSeries<C> ps) {
        return zip(new 15(), ps);
    }

    public MultiVarPowerSeries<C> multiply(C a) {
        if (a.isZERO()) {
            return this.ring.getZERO();
        }
        return !a.isONE() ? map(new 16(a)) : this;
    }

    public MultiVarPowerSeries<C> monic() {
        ExpVector e = orderExpVector();
        if (e == null) {
            return this;
        }
        C a = coefficient(e);
        return (a.isONE() || a.isZERO()) ? this : map(new 17((RingElem) a.inverse()));
    }

    public MultiVarPowerSeries<C> negate() {
        return map(new 18());
    }

    public MultiVarPowerSeries<C> abs() {
        if (signum() < 0) {
            return negate();
        }
        return this;
    }

    public C evaluate(List<C> a) {
        C v = (RingElem) this.ring.coFac.getZERO();
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) this.truncate).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            C c = coefficient(i);
            if (!c.isZERO()) {
                v = (RingElem) v.sum((RingElem) c.multiply(i.evaluate(this.ring.coFac, a)));
            }
        }
        return v;
    }

    public int order() {
        if (this.order >= 0) {
            return this.order;
        }
        int t = 0;
        while (this.lazyCoeffs.homCheck.get(t)) {
            GenPolynomial<C> p = (GenPolynomial) this.lazyCoeffs.coeffCache.get(Integer.valueOf(t));
            if (p == null || p.isZERO()) {
                t++;
            } else {
                this.order = t;
                this.evorder = p.trailingExpVector();
                return this.order;
            }
        }
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) this.truncate).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            if (!coefficient(i).isZERO()) {
                this.order = (int) i.totalDeg();
                this.evorder = i;
                return this.order;
            }
        }
        this.order = this.truncate + 1;
        return this.order;
    }

    public ExpVector orderExpVector() {
        order();
        return this.evorder;
    }

    public Entry<ExpVector, C> orderMonomial() {
        ExpVector e = orderExpVector();
        if (e == null) {
            return null;
        }
        return new MapEntry(e, coefficient(e));
    }

    public int truncate() {
        return this.truncate;
    }

    public int setTruncate(int t) {
        if (t < 0) {
            throw new IllegalArgumentException("negative truncate not allowed");
        }
        int ot = this.truncate;
        if (this.order >= 0 && this.order > this.truncate) {
            this.order = -1;
            this.evorder = null;
        }
        this.truncate = t;
        return ot;
    }

    public long ecart() {
        ExpVector e = orderExpVector();
        if (e == null) {
            return 0;
        }
        long d = e.totalDeg();
        long hd = d;
        for (long i = d + 1; i <= ((long) this.truncate); i++) {
            if (!homogeneousPart(i).isZERO()) {
                hd = i;
            }
        }
        return hd - d;
    }

    public int signum() {
        ExpVector ev = orderExpVector();
        if (ev != null) {
            return coefficient(ev).signum();
        }
        return 0;
    }

    public int compareTo(MultiVarPowerSeries<C> ps) {
        int m = truncate();
        int n = ps.truncate();
        int pos = Math.min(this.ring.truncate, Math.min(m, n));
        int s = 0;
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) pos).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            s = coefficient(i).compareTo(ps.coefficient(i));
            if (s != 0) {
                return s;
            }
        }
        for (int j = pos + 1; j <= Math.min(this.ring.truncate, Math.max(m, n)); j++) {
            i$ = new ExpVectorIterable(this.ring.nvar, (long) j).iterator();
            while (i$.hasNext()) {
                i = (ExpVector) i$.next();
                s = coefficient(i).compareTo(ps.coefficient(i));
                if (s != 0) {
                    return s;
                }
            }
        }
        return s;
    }

    public boolean isZERO() {
        return signum() == 0;
    }

    public boolean isONE() {
        if (leadingCoefficient().isONE() && compareTo(this.ring.ONE) == 0) {
            return true;
        }
        return false;
    }

    public boolean equals(Object B) {
        if (B != null && (B instanceof MultiVarPowerSeries) && compareTo((MultiVarPowerSeries) B) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h = 0;
        Iterator i$ = new ExpVectorIterable(this.ring.nvar, true, (long) this.truncate).iterator();
        while (i$.hasNext()) {
            ExpVector i = (ExpVector) i$.next();
            C c = coefficient(i);
            if (!c.isZERO()) {
                h = (h + i.hashCode()) << 23;
            }
            h = (h + c.hashCode()) << 23;
        }
        return h;
    }

    public boolean isUnit() {
        return leadingCoefficient().isUnit();
    }

    public MultiVarPowerSeries<C> sum(MultiVarPowerSeries<C> ps) {
        return new MultiVarPowerSeries(this.ring, new 19(this.ring, ps), Math.min(this.ring.truncate, Math.max(truncate(), ps.truncate())));
    }

    public MultiVarPowerSeries<C> subtract(MultiVarPowerSeries<C> ps) {
        return new MultiVarPowerSeries(this.ring, new 20(this.ring, ps), Math.min(this.ring.truncate, Math.max(truncate(), ps.truncate())));
    }

    public MultiVarPowerSeries<C> multiply(MultiVarPowerSeries<C> ps) {
        return new MultiVarPowerSeries(this.ring, new 21(this.ring, ps), Math.min(this.ring.truncate, truncate() + ps.truncate()));
    }

    public MultiVarPowerSeries<C> inverse() {
        return new MultiVarPowerSeries(this.ring, new 22(this.ring));
    }

    public MultiVarPowerSeries<C> divide(MultiVarPowerSeries<C> ps) {
        if (ps.isUnit()) {
            return multiply(ps.inverse());
        }
        int m = order();
        int n = ps.order();
        if (m < n) {
            return this.ring.getZERO();
        }
        ExpVector em = orderExpVector();
        ExpVector en = ps.orderExpVector();
        if (ps.coefficient(en).isUnit()) {
            MultiVarPowerSeries<C> st;
            MultiVarPowerSeries<C> sps;
            if (m == 0) {
                st = this;
            } else {
                st = shift(em.negate());
            }
            if (n == 0) {
                sps = ps;
            } else {
                sps = ps.shift(en.negate());
            }
            return st.multiply(sps.inverse()).shift(em.subtract(en));
        }
        throw new ArithmeticException("division by non unit coefficient " + ps.coefficient(ps.evorder) + ", evorder = " + ps.evorder);
    }

    public MultiVarPowerSeries<C> remainder(MultiVarPowerSeries<C> ps) {
        if (order() >= ps.order()) {
            return this.ring.getZERO();
        }
        return this;
    }

    public MultiVarPowerSeries<C>[] quotientRemainder(MultiVarPowerSeries<C> S) {
        return new MultiVarPowerSeries[]{divide((MultiVarPowerSeries) S), remainder((MultiVarPowerSeries) S)};
    }

    public MultiVarPowerSeries<C> differentiate(int r) {
        if (r >= 0 && this.ring.nvar >= r) {
            return new MultiVarPowerSeries(this.ring, new 23(this.ring, r));
        }
        throw new IllegalArgumentException("variable index out of bound");
    }

    public MultiVarPowerSeries<C> integrate(C c, int r) {
        if (r < 0 || this.ring.nvar < r) {
            throw new IllegalArgumentException("variable index out of bound");
        }
        return new MultiVarPowerSeries(this.ring, new 24(this.ring, c, r), Math.min(this.ring.truncate, this.truncate + 1));
    }

    public MultiVarPowerSeries<C> gcd(MultiVarPowerSeries<C> ps) {
        if (ps.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return ps;
        }
        return this.ring.getONE().shift(orderExpVector().gcd(ps.orderExpVector()));
    }

    public MultiVarPowerSeries<C>[] egcd(MultiVarPowerSeries<C> multiVarPowerSeries) {
        throw new UnsupportedOperationException("egcd for power series not implemented");
    }
}
