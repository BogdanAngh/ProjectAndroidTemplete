package edu.jas.ps;

import com.example.duy.calculator.math_eval.Constants;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.BinaryFunctor;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.Selector;
import edu.jas.structure.UnaryFunctor;
import io.github.kexanie.library.BuildConfig;

public class UnivPowerSeries<C extends RingElem<C>> implements RingElem<UnivPowerSeries<C>> {
    Coefficients<C> lazyCoeffs;
    private int order;
    public final UnivPowerSeriesRing<C> ring;
    private int truncate;

    class 10 extends Coefficients<C> {
        10() {
        }

        public C generate(int i) {
            RingElem d = (RingElem) UnivPowerSeries.this.leadingCoefficient().inverse();
            if (i == 0) {
                return d;
            }
            C c = null;
            for (int k = 0; k < i; k++) {
                C m = (RingElem) get(k).multiply(UnivPowerSeries.this.coefficient(i - k));
                if (c == null) {
                    c = m;
                } else {
                    RingElem c2 = (RingElem) c.sum(m);
                }
            }
            return (RingElem) c.multiply((MonoidElem) d.negate());
        }
    }

    class 11 extends Coefficients<C> {
        11() {
        }

        public C generate(int i) {
            return (RingElem) UnivPowerSeries.this.coefficient(i + 1).multiply((MonoidElem) UnivPowerSeries.this.ring.coFac.fromInteger((long) (i + 1)));
        }
    }

    class 12 extends Coefficients<C> {
        final /* synthetic */ RingElem val$c;

        12(RingElem ringElem) {
            this.val$c = ringElem;
        }

        public C generate(int i) {
            if (i == 0) {
                return this.val$c;
            }
            return (RingElem) UnivPowerSeries.this.coefficient(i - 1).divide((MonoidElem) UnivPowerSeries.this.ring.coFac.fromInteger((long) i));
        }
    }

    class 1 extends Coefficients<C> {
        1() {
        }

        public C generate(int i) {
            return UnivPowerSeries.this.coefficient(i + 1);
        }
    }

    class 2 extends Coefficients<C> {
        final /* synthetic */ RingElem val$h;

        2(RingElem ringElem) {
            this.val$h = ringElem;
        }

        public C generate(int i) {
            if (i == 0) {
                return this.val$h;
            }
            return UnivPowerSeries.this.coefficient(i - 1);
        }
    }

    class 3 extends Coefficients<C> {
        final /* synthetic */ int val$k;

        3(int i) {
            this.val$k = i;
        }

        public C generate(int i) {
            if (i - this.val$k < 0) {
                return (RingElem) UnivPowerSeries.this.ring.coFac.getZERO();
            }
            return UnivPowerSeries.this.coefficient(i - this.val$k);
        }
    }

    class 4 extends Coefficients<C> {
        final /* synthetic */ Selector val$sel;

        4(Selector selector) {
            this.val$sel = selector;
        }

        public C generate(int i) {
            C c = UnivPowerSeries.this.coefficient(i);
            return this.val$sel.select(c) ? c : (RingElem) UnivPowerSeries.this.ring.coFac.getZERO();
        }
    }

    class 5 extends Coefficients<C> {
        int pos;
        final /* synthetic */ Selector val$sel;

        5(Selector selector) {
            this.val$sel = selector;
            this.pos = 0;
        }

        public C generate(int i) {
            C c;
            if (i > 0) {
                get(i - 1);
            }
            do {
                UnivPowerSeries univPowerSeries = UnivPowerSeries.this;
                int i2 = this.pos;
                this.pos = i2 + 1;
                c = univPowerSeries.coefficient(i2);
            } while (!this.val$sel.select(c));
            return c;
        }
    }

    class 6 extends Coefficients<C> {
        final /* synthetic */ UnaryFunctor val$f;

        6(UnaryFunctor unaryFunctor) {
            this.val$f = unaryFunctor;
        }

        public C generate(int i) {
            return (RingElem) this.val$f.eval(UnivPowerSeries.this.coefficient(i));
        }
    }

    class 7 extends Coefficients<C> {
        final /* synthetic */ BinaryFunctor val$f;
        final /* synthetic */ UnivPowerSeries val$ps;

        7(BinaryFunctor binaryFunctor, UnivPowerSeries univPowerSeries) {
            this.val$f = binaryFunctor;
            this.val$ps = univPowerSeries;
        }

        public C generate(int i) {
            return (RingElem) this.val$f.eval(UnivPowerSeries.this.coefficient(i), this.val$ps.coefficient(i));
        }
    }

    class 8 implements UnaryFunctor<C, C> {
        final /* synthetic */ RingElem val$b;

        8(RingElem ringElem) {
            this.val$b = ringElem;
        }

        public C eval(C c) {
            return (RingElem) this.val$b.multiply(c);
        }
    }

    class 9 extends Coefficients<C> {
        final /* synthetic */ UnivPowerSeries val$ps;

        9(UnivPowerSeries univPowerSeries) {
            this.val$ps = univPowerSeries;
        }

        public C generate(int i) {
            C c = null;
            for (int k = 0; k <= i; k++) {
                C m = (RingElem) UnivPowerSeries.this.coefficient(k).multiply(this.val$ps.coefficient(i - k));
                if (c == null) {
                    c = m;
                } else {
                    RingElem c2 = (RingElem) c.sum(m);
                }
            }
            return c;
        }
    }

    private UnivPowerSeries() {
        this.truncate = 11;
        this.order = -1;
        throw new IllegalArgumentException("do not use no-argument constructor");
    }

    UnivPowerSeries(UnivPowerSeriesRing<C> ring) {
        this.truncate = 11;
        this.order = -1;
        this.ring = ring;
        this.lazyCoeffs = null;
    }

    public UnivPowerSeries(UnivPowerSeriesRing<C> ring, Coefficients<C> lazyCoeffs) {
        this.truncate = 11;
        this.order = -1;
        if (lazyCoeffs == null || ring == null) {
            throw new IllegalArgumentException("null not allowed: ring = " + ring + ", lazyCoeffs = " + lazyCoeffs);
        }
        this.ring = ring;
        this.lazyCoeffs = lazyCoeffs;
        this.truncate = ring.truncate;
    }

    public UnivPowerSeriesRing<C> factory() {
        return this.ring;
    }

    public UnivPowerSeries<C> copy() {
        return new UnivPowerSeries(this.ring, this.lazyCoeffs);
    }

    public String toString() {
        return toString(this.truncate);
    }

    public String toString(int truncate) {
        StringBuffer sb = new StringBuffer();
        UnivPowerSeries<C> s = this;
        String var = this.ring.var;
        int i = 0;
        while (i < truncate) {
            C c = coefficient(i);
            int si = c.signum();
            if (si != 0) {
                if (si <= 0) {
                    RingElem c2 = (RingElem) c.negate();
                    sb.append(" - ");
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (!c.isONE() || i == 0) {
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append("{ ");
                    }
                    sb.append(c.toString());
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append(" }");
                    }
                    if (i > 0) {
                        sb.append(" * ");
                    }
                }
                if (i != 0) {
                    if (i == 1) {
                        sb.append(var);
                    } else {
                        sb.append(var + "^" + i);
                    }
                }
            }
            i++;
        }
        if (sb.length() == 0) {
            sb.append(Constants.ZERO);
        }
        sb.append(" + BigO(" + var + "^" + truncate + ")");
        return sb.toString();
    }

    public String toScript() {
        StringBuffer sb = new StringBuffer(BuildConfig.FLAVOR);
        UnivPowerSeries<C> s = this;
        String var = this.ring.var;
        int i = 0;
        while (i < this.truncate) {
            C c = coefficient(i);
            int si = c.signum();
            if (si != 0) {
                if (si <= 0) {
                    RingElem c2 = (RingElem) c.negate();
                    sb.append(" - ");
                } else if (sb.length() > 0) {
                    sb.append(" + ");
                }
                if (!c.isONE() || i == 0) {
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append("{ ");
                    }
                    sb.append(c.toScript());
                    if ((c instanceof GenPolynomial) || (c instanceof AlgebraicNumber)) {
                        sb.append(" }");
                    }
                    if (i > 0) {
                        sb.append(" * ");
                    }
                }
                if (i != 0) {
                    if (i == 1) {
                        sb.append(var);
                    } else {
                        sb.append(var + "**" + i);
                    }
                }
            }
            i++;
        }
        if (sb.length() == 0) {
            sb.append(Constants.ZERO);
        }
        return sb.toString();
    }

    public String toScriptFactory() {
        return factory().toScript();
    }

    public C coefficient(int index) {
        if (index >= 0) {
            return this.lazyCoeffs.get(index);
        }
        throw new IndexOutOfBoundsException("negative index not allowed");
    }

    public GenPolynomial<C> asPolynomial() {
        GenPolynomial<C> p = this.ring.polyRing().getZERO();
        for (int i = 0; i <= this.truncate; i++) {
            p = p.sum(coefficient(i), ExpVector.create(1, 0, (long) i));
        }
        return p;
    }

    public C leadingCoefficient() {
        return coefficient(0);
    }

    public UnivPowerSeries<C> reductum() {
        return new UnivPowerSeries(this.ring, new 1());
    }

    public UnivPowerSeries<C> prepend(C h) {
        return new UnivPowerSeries(this.ring, new 2(h));
    }

    public UnivPowerSeries<C> shift(int k) {
        return new UnivPowerSeries(this.ring, new 3(k));
    }

    public UnivPowerSeries<C> select(Selector<? super C> sel) {
        return new UnivPowerSeries(this.ring, new 4(sel));
    }

    public UnivPowerSeries<C> shiftSelect(Selector<? super C> sel) {
        return new UnivPowerSeries(this.ring, new 5(sel));
    }

    public UnivPowerSeries<C> map(UnaryFunctor<? super C, C> f) {
        return new UnivPowerSeries(this.ring, new 6(f));
    }

    public <C2 extends RingElem<C2>> UnivPowerSeries<C> zip(BinaryFunctor<? super C, ? super C2, C> f, UnivPowerSeries<C2> ps) {
        return new UnivPowerSeries(this.ring, new 7(f, ps));
    }

    public UnivPowerSeries<C> sum(UnivPowerSeries<C> ps) {
        return zip(new Sum(), ps);
    }

    public UnivPowerSeries<C> subtract(UnivPowerSeries<C> ps) {
        return zip(new Subtract(), ps);
    }

    public UnivPowerSeries<C> multiply(C c) {
        return map(new Multiply(c));
    }

    public UnivPowerSeries<C> monic() {
        C a = coefficient(order());
        return (a.isONE() || a.isZERO()) ? this : map(new 8((RingElem) a.inverse()));
    }

    public UnivPowerSeries<C> negate() {
        return map(new Negate());
    }

    public UnivPowerSeries<C> abs() {
        if (signum() < 0) {
            return negate();
        }
        return this;
    }

    public C evaluate(C e) {
        C v = coefficient(0);
        C p = e;
        for (int i = 1; i < this.truncate; i++) {
            RingElem v2 = (RingElem) v.sum((RingElem) coefficient(i).multiply(p));
            RingElem p2 = (RingElem) p.multiply(e);
        }
        return v;
    }

    public int order() {
        if (this.order < 0) {
            int i = 0;
            while (i <= this.truncate) {
                if (coefficient(i).isZERO()) {
                    i++;
                } else {
                    this.order = i;
                    return this.order;
                }
            }
            this.order = this.truncate + 1;
        }
        return this.order;
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
        return ot;
    }

    public int signum() {
        return coefficient(order()).signum();
    }

    public int compareTo(UnivPowerSeries<C> ps) {
        int pos;
        int s;
        int m = order();
        int n = ps.order();
        if (m <= n) {
            pos = m;
        } else {
            pos = n;
        }
        do {
            s = coefficient(pos).compareTo(ps.coefficient(pos));
            pos++;
            if (s != 0) {
                break;
            }
        } while (pos <= this.truncate);
        return s;
    }

    public boolean isZERO() {
        return compareTo(this.ring.ZERO) == 0;
    }

    public boolean isONE() {
        return compareTo(this.ring.ONE) == 0;
    }

    public boolean equals(Object B) {
        UnivPowerSeries a = null;
        try {
            a = (UnivPowerSeries) B;
        } catch (ClassCastException e) {
        }
        if (a != null && compareTo(a) == 0) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int h = 0;
        for (int i = 0; i <= this.truncate; i++) {
            h = (h + coefficient(i).hashCode()) << 23;
        }
        return h;
    }

    public boolean isUnit() {
        return leadingCoefficient().isUnit();
    }

    public UnivPowerSeries<C> multiply(UnivPowerSeries<C> ps) {
        return new UnivPowerSeries(this.ring, new 9(ps));
    }

    public UnivPowerSeries<C> inverse() {
        return new UnivPowerSeries(this.ring, new 10());
    }

    public UnivPowerSeries<C> divide(UnivPowerSeries<C> ps) {
        if (ps.isUnit()) {
            return multiply(ps.inverse());
        }
        int m = order();
        int n = ps.order();
        if (m < n) {
            return this.ring.getZERO();
        }
        if (ps.coefficient(n).isUnit()) {
            UnivPowerSeries<C> st;
            UnivPowerSeries<C> sps;
            if (m == 0) {
                st = this;
            } else {
                st = shift(-m);
            }
            if (n == 0) {
                sps = ps;
            } else {
                sps = ps.shift(-n);
            }
            UnivPowerSeries<C> q = st.multiply(sps.inverse());
            if (m == n) {
                return q;
            }
            return q.shift(m - n);
        }
        throw new ArithmeticException("division by non unit coefficient " + ps.coefficient(n) + ", n = " + n);
    }

    public UnivPowerSeries<C> remainder(UnivPowerSeries<C> ps) {
        if (order() >= ps.order()) {
            return this.ring.getZERO();
        }
        return this;
    }

    public UnivPowerSeries<C>[] quotientRemainder(UnivPowerSeries<C> S) {
        return new UnivPowerSeries[]{divide((UnivPowerSeries) S), remainder((UnivPowerSeries) S)};
    }

    public UnivPowerSeries<C> differentiate() {
        return new UnivPowerSeries(this.ring, new 11());
    }

    public UnivPowerSeries<C> integrate(C c) {
        return new UnivPowerSeries(this.ring, new 12(c));
    }

    public UnivPowerSeries<C> gcd(UnivPowerSeries<C> ps) {
        if (ps.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return ps;
        }
        int ll;
        int m = order();
        int n = ps.order();
        if (m < n) {
            ll = m;
        } else {
            ll = n;
        }
        return this.ring.getONE().shift(ll);
    }

    public UnivPowerSeries<C>[] egcd(UnivPowerSeries<C> univPowerSeries) {
        throw new UnsupportedOperationException("egcd for power series not implemented");
    }
}
