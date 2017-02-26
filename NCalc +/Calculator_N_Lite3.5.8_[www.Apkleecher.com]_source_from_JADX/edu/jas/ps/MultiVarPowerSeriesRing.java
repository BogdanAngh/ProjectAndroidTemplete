package edu.jas.ps;

import edu.jas.kern.PrettyPrint;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import edu.jas.util.ListUtil;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class MultiVarPowerSeriesRing<C extends RingElem<C>> implements RingFactory<MultiVarPowerSeries<C>> {
    public static final int DEFAULT_TRUNCATE = 7;
    protected static final Random random;
    public final ExpVector EVZERO;
    public final MultiVarPowerSeries<C> ONE;
    public final MultiVarPowerSeries<C> ZERO;
    public final RingFactory<C> coFac;
    public final int nvar;
    int truncate;
    protected String[] vars;

    class 10 extends MultiVarCoefficients<C> {
        10(GenPolynomialRing x0, HashMap x1, BitSet x2) {
            super(x0, x1, x2);
        }

        public C generate(ExpVector e) {
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 11 implements UnaryFunctor<GenPolynomial<C>, MultiVarPowerSeries<C>> {
        11() {
        }

        public MultiVarPowerSeries<C> eval(GenPolynomial<C> c) {
            return MultiVarPowerSeriesRing.this.fromPolynomial((GenPolynomial) c);
        }
    }

    class 12 extends MultiVarCoefficients<C> {
        final /* synthetic */ UnivPowerSeries val$ps;
        final /* synthetic */ int val$r;

        12(MultiVarPowerSeriesRing x0, UnivPowerSeries univPowerSeries, int i) {
            this.val$ps = univPowerSeries;
            this.val$r = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return this.val$ps.coefficient(0);
            }
            int[] dep = i.dependencyOnVariables();
            if (dep.length != 1) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
            }
            if (dep[0] != this.val$r) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
            }
            int j = (int) i.getVal(this.val$r);
            if (j > 0) {
                return this.val$ps.coefficient(j);
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 13 extends MultiVarCoefficients<C> {
        final /* synthetic */ float val$d;
        final /* synthetic */ int val$k;
        final /* synthetic */ Random val$rnd;

        13(MultiVarPowerSeriesRing x0, Random random, float f, int i) {
            this.val$rnd = random;
            this.val$d = f;
            this.val$k = i;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (this.val$rnd.nextFloat() < this.val$d) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.random(this.val$k, this.val$rnd);
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 14 extends MultiVarCoefficients<C> {
        TaylorFunction<C> der;
        final List<C> v;
        final /* synthetic */ List val$a;
        final /* synthetic */ TaylorFunction val$f;

        14(MultiVarPowerSeriesRing x0, TaylorFunction taylorFunction, List list) {
            this.val$f = taylorFunction;
            this.val$a = list;
            super(x0);
            this.der = this.val$f;
            this.v = this.val$a;
        }

        public C generate(ExpVector i) {
            if (i.signum() == 0) {
                return this.der.evaluate(this.v);
            }
            TaylorFunction<C> pder = this.der.deriviative(i);
            if (pder.isZERO()) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
            }
            C c = pder.evaluate(this.v);
            if (c.isZERO()) {
                return c;
            }
            return (RingElem) c.divide((MonoidElem) MultiVarPowerSeriesRing.this.coFac.fromInteger(pder.getFacul()));
        }
    }

    class 1 extends MultiVarCoefficients<C> {
        1(MultiVarPowerSeriesRing x0) {
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.getONE();
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 2 extends MultiVarCoefficients<C> {
        2(MultiVarPowerSeriesRing x0) {
            super(x0);
        }

        public C generate(ExpVector i) {
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 3 extends MultiVarCoefficients<C> {
        final /* synthetic */ RingElem val$cg;

        3(MultiVarPowerSeriesRing x0, RingElem ringElem) {
            this.val$cg = ringElem;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return this.val$cg;
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 4 implements MultiVarPowerSeriesMap<C> {
        final /* synthetic */ int val$r;

        4(int i) {
            this.val$r = i;
        }

        public MultiVarPowerSeries<C> map(MultiVarPowerSeries<C> e) {
            return e.integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getONE(), this.val$r);
        }
    }

    class 5 implements MultiVarPowerSeriesMap<C> {
        final /* synthetic */ int val$r;

        5(int i) {
            this.val$r = i;
        }

        public MultiVarPowerSeries<C> map(MultiVarPowerSeries<C> s) {
            return s.negate().integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getONE(), this.val$r).integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO(), this.val$r);
        }
    }

    class 6 implements MultiVarPowerSeriesMap<C> {
        final /* synthetic */ int val$r;

        6(int i) {
            this.val$r = i;
        }

        public MultiVarPowerSeries<C> map(MultiVarPowerSeries<C> c) {
            return c.negate().integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO(), this.val$r).integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getONE(), this.val$r);
        }
    }

    class 7 implements MultiVarPowerSeriesMap<C> {
        final /* synthetic */ int val$r;

        7(int i) {
            this.val$r = i;
        }

        public MultiVarPowerSeries<C> map(MultiVarPowerSeries<C> t) {
            return t.multiply((MultiVarPowerSeries) t).sum(MultiVarPowerSeriesRing.this.getONE()).integrate((RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO(), this.val$r);
        }
    }

    class 8 extends MultiVarCoefficients<C> {
        final /* synthetic */ long val$a;

        8(MultiVarPowerSeriesRing x0, long j) {
            this.val$a = j;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.fromInteger(this.val$a);
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 9 extends MultiVarCoefficients<C> {
        final /* synthetic */ BigInteger val$a;

        9(MultiVarPowerSeriesRing x0, BigInteger bigInteger) {
            this.val$a = bigInteger;
            super(x0);
        }

        public C generate(ExpVector i) {
            if (i.isZERO()) {
                return (RingElem) MultiVarPowerSeriesRing.this.coFac.fromInteger(this.val$a);
            }
            return (RingElem) MultiVarPowerSeriesRing.this.coFac.getZERO();
        }
    }

    static {
        random = new Random();
    }

    private MultiVarPowerSeriesRing() {
        throw new IllegalArgumentException("do not use no-argument constructor");
    }

    public MultiVarPowerSeriesRing(GenPolynomialRing<C> fac) {
        this(fac.coFac, fac.nvar, fac.getVars());
    }

    public MultiVarPowerSeriesRing(RingFactory<C> coFac, int nv) {
        this((RingFactory) coFac, nv, (int) DEFAULT_TRUNCATE);
    }

    public MultiVarPowerSeriesRing(RingFactory<C> coFac, int nv, int truncate) {
        this(coFac, nv, truncate, null);
    }

    public MultiVarPowerSeriesRing(RingFactory<C> coFac, String[] names) {
        this(coFac, names.length, DEFAULT_TRUNCATE, names);
    }

    public MultiVarPowerSeriesRing(RingFactory<C> cofac, int nv, String[] names) {
        this(cofac, nv, DEFAULT_TRUNCATE, names);
    }

    public MultiVarPowerSeriesRing(RingFactory<C> cofac, int nv, int truncate, String[] names) {
        this.coFac = cofac;
        this.nvar = nv;
        this.truncate = truncate;
        if (names == null) {
            this.vars = null;
        } else {
            this.vars = (String[]) Arrays.copyOf(names, names.length);
        }
        if (this.vars == null) {
            if (PrettyPrint.isTrue()) {
                this.vars = GenPolynomialRing.newVars(UnivPowerSeriesRing.DEFAULT_NAME, this.nvar);
            }
        } else if (this.vars.length != this.nvar) {
            throw new IllegalArgumentException("incompatible variable size " + this.vars.length + ", " + this.nvar);
        } else {
            GenPolynomialRing.addVars(this.vars);
        }
        this.EVZERO = ExpVector.create(this.nvar);
        this.ONE = new MultiVarPowerSeries(this, new 1(this));
        this.ZERO = new MultiVarPowerSeries(this, new 2(this));
    }

    public MultiVarPowerSeries<C> fixPoint(MultiVarPowerSeriesMap<C> map) {
        MultiVarPowerSeries<C> ps1 = new MultiVarPowerSeries(this);
        MultiVarPowerSeries<C> ps2 = map.map(ps1);
        ps1.lazyCoeffs = ps2.lazyCoeffs;
        return ps2;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.coFac.getClass().getSimpleName() + "((" + varsToString() + "))");
        return sb.toString();
    }

    public String varsToString() {
        if (this.vars == null) {
            return "#" + this.nvar;
        }
        return ExpVector.varsToString(this.vars);
    }

    public String[] getVars() {
        return (String[]) Arrays.copyOf(this.vars, this.vars.length);
    }

    public String toScript() {
        String f;
        StringBuffer s = new StringBuffer("MPS(");
        try {
            f = ((RingElem) this.coFac).toScriptFactory();
        } catch (Exception e) {
            f = this.coFac.toScript();
        }
        s.append(f + ",\"" + varsToString() + "\"," + this.truncate + ")");
        return s.toString();
    }

    public boolean equals(Object B) {
        MultiVarPowerSeriesRing<C> a = null;
        try {
            a = (MultiVarPowerSeriesRing) B;
        } catch (ClassCastException e) {
        }
        if (a != null && this.coFac.equals(a.coFac) && Arrays.equals(this.vars, a.vars)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.coFac.hashCode() << DEFAULT_TRUNCATE) + (Arrays.hashCode(this.vars) << 17)) + this.truncate;
    }

    public MultiVarPowerSeries<C> getZERO() {
        return this.ZERO;
    }

    public MultiVarPowerSeries<C> getONE() {
        return this.ONE;
    }

    public List<MultiVarPowerSeries<C>> generators() {
        List<C> rgens = this.coFac.generators();
        List<MultiVarPowerSeries<C>> gens = new ArrayList(rgens.size());
        for (C cg : rgens) {
            gens.add(new MultiVarPowerSeries(this, new 3(this, cg)));
        }
        for (int i = 0; i < this.nvar; i++) {
            gens.add(this.ONE.shift(1, (this.nvar - 1) - i));
        }
        return gens;
    }

    public boolean isFinite() {
        return false;
    }

    public int truncate() {
        return this.truncate;
    }

    public int setTruncate(int t) {
        if (t < 0) {
            throw new IllegalArgumentException("negative truncate not allowed");
        }
        int ot = this.truncate;
        this.truncate = t;
        this.ONE.setTruncate(t);
        this.ZERO.setTruncate(t);
        return ot;
    }

    public MultiVarPowerSeries<C> getEXP(int r) {
        return fixPoint(new 4(r));
    }

    public MultiVarPowerSeries<C> getSIN(int r) {
        return fixPoint(new 5(r));
    }

    public MultiVarPowerSeries<C> getCOS(int r) {
        return fixPoint(new 6(r));
    }

    public MultiVarPowerSeries<C> getTAN(int r) {
        return fixPoint(new 7(r));
    }

    public MultiVarPowerSeries<C> solvePDE(MultiVarPowerSeries<C> f, C c, int r) {
        return f.integrate(c, r);
    }

    public boolean isCommutative() {
        return this.coFac.isCommutative();
    }

    public boolean isAssociative() {
        return this.coFac.isAssociative();
    }

    public boolean isField() {
        return false;
    }

    public BigInteger characteristic() {
        return this.coFac.characteristic();
    }

    public MultiVarPowerSeries<C> fromInteger(long a) {
        return new MultiVarPowerSeries(this, new 8(this, a));
    }

    public MultiVarPowerSeries<C> fromInteger(BigInteger a) {
        return new MultiVarPowerSeries(this, new 9(this, a));
    }

    public GenPolynomialRing<C> polyRing() {
        return new GenPolynomialRing(this.coFac, this.nvar, this.vars);
    }

    public MultiVarPowerSeries<C> fromPolynomial(GenPolynomial<C> a) {
        if (a == null || a.isZERO()) {
            return this.ZERO;
        }
        if (a.isONE()) {
            return this.ONE;
        }
        GenPolynomialRing<C> pfac = polyRing();
        HashMap<Long, GenPolynomial<C>> cache = new HashMap();
        int mt = 0;
        Iterator i$ = a.iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            ExpVector e = m.exponent();
            long t = e.totalDeg();
            mt = Math.max(mt, (int) t);
            GenPolynomial<C> p = (GenPolynomial) cache.get(Long.valueOf(t));
            if (p == null) {
                p = pfac.getZERO().copy();
                cache.put(Long.valueOf(t), p);
            }
            p.doPutToMap(e, m.coefficient());
        }
        mt++;
        if (mt > truncate()) {
            setTruncate(mt);
        }
        BitSet check = new BitSet();
        for (int i = 0; i <= truncate(); i++) {
            check.set(i);
            if (cache.get(Long.valueOf((long) i)) == null) {
                cache.put(Long.valueOf((long) i), pfac.getZERO().copy());
            }
        }
        return new MultiVarPowerSeries(this, new 10(pfac, cache, check));
    }

    public List<MultiVarPowerSeries<C>> fromPolynomial(List<GenPolynomial<C>> A) {
        return ListUtil.map(A, new 11());
    }

    public MultiVarPowerSeries<C> fromPowerSeries(UnivPowerSeries<C> ps, int r) {
        if (ps == null) {
            return this.ZERO;
        }
        return new MultiVarPowerSeries(this, new 12(this, ps, r));
    }

    public MultiVarPowerSeries<C> random() {
        return random(5, 0.7f, random);
    }

    public MultiVarPowerSeries<C> random(int k) {
        return random(k, 0.7f, random);
    }

    public MultiVarPowerSeries<C> random(int k, Random rnd) {
        return random(k, 0.7f, rnd);
    }

    public MultiVarPowerSeries<C> random(int k, float d) {
        return random(k, d, random);
    }

    public MultiVarPowerSeries<C> random(int k, float d, Random rnd) {
        return new MultiVarPowerSeries(this, new 13(this, rnd, d, k));
    }

    public MultiVarPowerSeries<C> copy(MultiVarPowerSeries<C> c) {
        return new MultiVarPowerSeries(this, c.lazyCoeffs);
    }

    public MultiVarPowerSeries<C> parse(String s) {
        throw new UnsupportedOperationException("parse for power series not implemented");
    }

    public MultiVarPowerSeries<C> parse(Reader r) {
        throw new UnsupportedOperationException("parse for power series not implemented");
    }

    public MultiVarPowerSeries<C> seriesOfTaylor(TaylorFunction<C> f, List<C> a) {
        return new MultiVarPowerSeries(this, new 14(this, f, a));
    }
}
