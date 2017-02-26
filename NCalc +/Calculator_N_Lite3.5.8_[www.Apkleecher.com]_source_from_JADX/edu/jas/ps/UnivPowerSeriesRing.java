package edu.jas.ps;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.Reader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class UnivPowerSeriesRing<C extends RingElem<C>> implements RingFactory<UnivPowerSeries<C>> {
    public static final String DEFAULT_NAME = "x";
    public static final int DEFAULT_TRUNCATE = 11;
    protected static final Random random;
    public final UnivPowerSeries<C> ONE;
    public final UnivPowerSeries<C> ZERO;
    public final RingFactory<C> coFac;
    int truncate;
    String var;

    class 10 extends Coefficients<C> {
        TaylorFunction<C> der;
        long k;
        long n;
        final /* synthetic */ RingElem val$a;
        final /* synthetic */ TaylorFunction val$f;

        10(TaylorFunction taylorFunction, RingElem ringElem) {
            this.val$f = taylorFunction;
            this.val$a = ringElem;
            this.der = this.val$f;
            this.k = 0;
            this.n = 1;
        }

        public C generate(int i) {
            if (i == 0) {
                C c = this.der.evaluate(this.val$a);
                this.der = this.der.deriviative();
                return c;
            }
            if (i > 0) {
                get(i - 1);
            }
            this.k++;
            this.n *= this.k;
            c = (RingElem) this.der.evaluate(this.val$a).divide((MonoidElem) UnivPowerSeriesRing.this.coFac.fromInteger(this.n));
            this.der = this.der.deriviative();
            return c;
        }
    }

    class 1 extends Coefficients<C> {
        1() {
        }

        public C generate(int i) {
            if (i == 0) {
                return (RingElem) UnivPowerSeriesRing.this.coFac.getONE();
            }
            return (RingElem) UnivPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 2 extends Coefficients<C> {
        2() {
        }

        public C generate(int i) {
            return (RingElem) UnivPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 3 extends Coefficients<C> {
        final /* synthetic */ RingElem val$cg;

        3(RingElem ringElem) {
            this.val$cg = ringElem;
        }

        public C generate(int i) {
            if (i == 0) {
                return this.val$cg;
            }
            return (RingElem) UnivPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 4 implements UnivPowerSeriesMap<C> {
        4() {
        }

        public UnivPowerSeries<C> map(UnivPowerSeries<C> e) {
            return e.integrate((RingElem) UnivPowerSeriesRing.this.coFac.getONE());
        }
    }

    class 5 implements UnivPowerSeriesMap<C> {
        5() {
        }

        public UnivPowerSeries<C> map(UnivPowerSeries<C> s) {
            return s.negate().integrate((RingElem) UnivPowerSeriesRing.this.coFac.getONE()).integrate((RingElem) UnivPowerSeriesRing.this.coFac.getZERO());
        }
    }

    class 6 implements UnivPowerSeriesMap<C> {
        6() {
        }

        public UnivPowerSeries<C> map(UnivPowerSeries<C> c) {
            return c.negate().integrate((RingElem) UnivPowerSeriesRing.this.coFac.getZERO()).integrate((RingElem) UnivPowerSeriesRing.this.coFac.getONE());
        }
    }

    class 7 implements UnivPowerSeriesMap<C> {
        7() {
        }

        public UnivPowerSeries<C> map(UnivPowerSeries<C> t) {
            return t.multiply((UnivPowerSeries) t).sum(UnivPowerSeriesRing.this.getONE()).integrate((RingElem) UnivPowerSeriesRing.this.coFac.getZERO());
        }
    }

    class 8 extends Coefficients<C> {
        8(HashMap x0) {
            super(x0);
        }

        public C generate(int i) {
            return (RingElem) UnivPowerSeriesRing.this.coFac.getZERO();
        }
    }

    class 9 extends Coefficients<C> {
        final /* synthetic */ float val$d;
        final /* synthetic */ int val$k;
        final /* synthetic */ Random val$rnd;

        9(Random random, float f, int i) {
            this.val$rnd = random;
            this.val$d = f;
            this.val$k = i;
        }

        public C generate(int i) {
            if (this.val$rnd.nextFloat() < this.val$d) {
                return (RingElem) UnivPowerSeriesRing.this.coFac.random(this.val$k, this.val$rnd);
            }
            return (RingElem) UnivPowerSeriesRing.this.coFac.getZERO();
        }
    }

    static {
        random = new Random();
    }

    private UnivPowerSeriesRing() {
        throw new IllegalArgumentException("do not use no-argument constructor");
    }

    public UnivPowerSeriesRing(RingFactory<C> coFac) {
        this(coFac, DEFAULT_TRUNCATE, DEFAULT_NAME);
    }

    public UnivPowerSeriesRing(RingFactory<C> coFac, int truncate) {
        this(coFac, truncate, DEFAULT_NAME);
    }

    public UnivPowerSeriesRing(RingFactory<C> coFac, String name) {
        this(coFac, DEFAULT_TRUNCATE, name);
    }

    public UnivPowerSeriesRing(GenPolynomialRing<C> pfac) {
        this(pfac.coFac, DEFAULT_TRUNCATE, pfac.getVars()[0]);
    }

    public UnivPowerSeriesRing(RingFactory<C> cofac, int truncate, String name) {
        this.coFac = cofac;
        this.truncate = truncate;
        this.var = name;
        this.ONE = new UnivPowerSeries(this, new 1());
        this.ZERO = new UnivPowerSeries(this, new 2());
    }

    public UnivPowerSeries<C> fixPoint(UnivPowerSeriesMap<C> map) {
        UnivPowerSeries<C> ps1 = new UnivPowerSeries(this);
        UnivPowerSeries<C> ps2 = map.map(ps1);
        ps1.lazyCoeffs = ps2.lazyCoeffs;
        return ps2;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(this.coFac.getClass().getSimpleName() + "((" + this.var + "))");
        return sb.toString();
    }

    public String toScript() {
        String f;
        StringBuffer s = new StringBuffer("PS(");
        try {
            f = ((RingElem) this.coFac).toScriptFactory();
        } catch (Exception e) {
            f = this.coFac.toScript();
        }
        s.append(f + ",\"" + this.var + "\"," + this.truncate + ")");
        return s.toString();
    }

    public boolean equals(Object B) {
        UnivPowerSeriesRing<C> a = null;
        try {
            a = (UnivPowerSeriesRing) B;
        } catch (ClassCastException e) {
        }
        if (a != null && this.coFac.equals(a.coFac) && this.var.equals(a.var)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.coFac.hashCode() + (this.var.hashCode() << 27)) + this.truncate;
    }

    public UnivPowerSeries<C> getZERO() {
        return this.ZERO;
    }

    public UnivPowerSeries<C> getONE() {
        return this.ONE;
    }

    public List<UnivPowerSeries<C>> generators() {
        List<C> rgens = this.coFac.generators();
        List<UnivPowerSeries<C>> gens = new ArrayList(rgens.size());
        for (C cg : rgens) {
            gens.add(new UnivPowerSeries(this, new 3(cg)));
        }
        gens.add(this.ONE.shift(1));
        return gens;
    }

    public boolean isFinite() {
        return false;
    }

    public UnivPowerSeries<C> getEXP() {
        return fixPoint(new 4());
    }

    public UnivPowerSeries<C> getSIN() {
        return fixPoint(new 5());
    }

    public UnivPowerSeries<C> getCOS() {
        return fixPoint(new 6());
    }

    public UnivPowerSeries<C> getTAN() {
        return fixPoint(new 7());
    }

    public UnivPowerSeries<C> solveODE(UnivPowerSeries<C> f, C c) {
        return f.integrate(c);
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

    public UnivPowerSeries<C> fromInteger(long a) {
        return this.ONE.multiply((RingElem) this.coFac.fromInteger(a));
    }

    public UnivPowerSeries<C> fromInteger(BigInteger a) {
        return this.ONE.multiply((RingElem) this.coFac.fromInteger(a));
    }

    public GenPolynomialRing<C> polyRing() {
        return new GenPolynomialRing(this.coFac, 1, new String[]{this.var});
    }

    public UnivPowerSeries<C> fromPolynomial(GenPolynomial<C> a) {
        if (a == null || a.isZERO()) {
            return this.ZERO;
        }
        if (a.isONE()) {
            return this.ONE;
        }
        if (a.ring.nvar != 1) {
            throw new IllegalArgumentException("only for univariate polynomials");
        }
        HashMap<Integer, C> cache = new HashMap(a.length());
        Iterator i$ = a.iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            cache.put(Integer.valueOf((int) m.exponent().getVal(0)), m.coefficient());
        }
        return new UnivPowerSeries(this, new 8(cache));
    }

    public UnivPowerSeries<C> random() {
        return random(5, 0.7f, random);
    }

    public UnivPowerSeries<C> random(int k) {
        return random(k, 0.7f, random);
    }

    public UnivPowerSeries<C> random(int k, Random rnd) {
        return random(k, 0.7f, rnd);
    }

    public UnivPowerSeries<C> random(int k, float d) {
        return random(k, d, random);
    }

    public UnivPowerSeries<C> random(int k, float d, Random rnd) {
        return new UnivPowerSeries(this, new 9(rnd, d, k));
    }

    public UnivPowerSeries<C> copy(UnivPowerSeries<C> c) {
        return new UnivPowerSeries(this, c.lazyCoeffs);
    }

    public UnivPowerSeries<C> parse(String s) {
        throw new UnsupportedOperationException("parse for power series not implemented");
    }

    public UnivPowerSeries<C> parse(Reader r) {
        throw new UnsupportedOperationException("parse for power series not implemented");
    }

    public UnivPowerSeries<C> seriesOfTaylor(TaylorFunction<C> f, C a) {
        return new UnivPowerSeries(this, new 10(f, a));
    }
}
