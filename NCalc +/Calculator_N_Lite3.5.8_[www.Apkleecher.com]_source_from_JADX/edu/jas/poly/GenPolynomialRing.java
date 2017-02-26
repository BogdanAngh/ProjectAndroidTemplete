package edu.jas.poly;

import edu.jas.arith.ModIntegerRing;
import edu.jas.kern.PreemptStatus;
import edu.jas.kern.PrettyPrint;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.ps.UnivPowerSeriesRing;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.vector.GenVectorModul;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class GenPolynomialRing<C extends RingElem<C>> implements RingFactory<GenPolynomial<C>>, Iterable<GenPolynomial<C>> {
    public static int creations;
    private static Set<String> knownVars;
    private static final Logger logger;
    protected static final Random random;
    public final GenPolynomial<C> ONE;
    public final GenPolynomial<C> ZERO;
    final boolean checkPreempt;
    public final RingFactory<C> coFac;
    public final ExpVector evzero;
    protected int isField;
    public final int nvar;
    protected boolean partial;
    public final TermOrder tord;
    protected String[] vars;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$kern$Scripting$Lang;

        static {
            $SwitchMap$edu$jas$kern$Scripting$Lang = new int[Lang.values().length];
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Ruby.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$kern$Scripting$Lang[Lang.Python.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
        }
    }

    static {
        knownVars = new HashSet();
        random = new Random();
        logger = Logger.getLogger(GenPolynomialRing.class);
        creations = 0;
    }

    public GenPolynomialRing(RingFactory<C> cf, int n) {
        this(cf, n, new TermOrder(), null);
    }

    public GenPolynomialRing(RingFactory<C> cf, int n, TermOrder t) {
        this(cf, n, t, null);
    }

    public GenPolynomialRing(RingFactory<C> cf, String[] v) {
        this((RingFactory) cf, v.length, v);
    }

    public GenPolynomialRing(RingFactory<C> cf, int n, String[] v) {
        this(cf, n, new TermOrder(), v);
    }

    public GenPolynomialRing(RingFactory<C> cf, TermOrder t, String[] v) {
        this(cf, v.length, t, v);
    }

    public GenPolynomialRing(RingFactory<C> cf, String[] v, TermOrder t) {
        this(cf, v.length, t, v);
    }

    public GenPolynomialRing(RingFactory<C> cf, int n, TermOrder t, String[] v) {
        this.isField = -1;
        this.checkPreempt = PreemptStatus.isAllowed();
        this.coFac = cf;
        this.nvar = n;
        this.tord = t;
        this.partial = false;
        if (v == null) {
            this.vars = null;
        } else {
            this.vars = (String[]) Arrays.copyOf(v, v.length);
        }
        this.ZERO = new GenPolynomial(this);
        RingElem coeff = (RingElem) this.coFac.getONE();
        this.evzero = ExpVector.create(this.nvar);
        this.ONE = new GenPolynomial(this, coeff, this.evzero);
        if (this.vars == null) {
            if (PrettyPrint.isTrue()) {
                this.vars = newVars(UnivPowerSeriesRing.DEFAULT_NAME, this.nvar);
            }
        } else if (this.vars.length != this.nvar) {
            throw new IllegalArgumentException("incompatible variable size " + this.vars.length + ", " + this.nvar);
        } else {
            addVars(this.vars);
        }
    }

    public GenPolynomialRing(RingFactory<C> cf, GenPolynomialRing o) {
        this(cf, o.nvar, o.tord, o.vars);
    }

    public GenPolynomialRing(GenPolynomialRing<C> o, TermOrder to) {
        this(o.coFac, o.nvar, to, o.vars);
    }

    public GenPolynomialRing<C> copy() {
        return new GenPolynomialRing(this.coFac, this);
    }

    public String toString() {
        String res = null;
        if (PrettyPrint.isTrue()) {
            String scf = this.coFac.getClass().getSimpleName();
            if (this.coFac instanceof AlgebraicNumberRing) {
                AlgebraicNumberRing an = this.coFac;
                res = "AN[ (" + an.ring.varsToString() + ") (" + an.toString() + ") ]";
            }
            if (this.coFac instanceof GenPolynomialRing) {
                res = "IntFunc( " + this.coFac.toString() + " )";
            }
            if (this.coFac instanceof ModIntegerRing) {
                res = "Mod " + this.coFac.getModul() + " ";
            }
            if (res == null) {
                res = this.coFac.toString();
                if (res.matches("[0-9].*")) {
                    res = scf;
                }
            }
            return res + "( " + varsToString() + " ) " + this.tord.toString() + " ";
        }
        res = getClass().getSimpleName() + "[ " + this.coFac.toString() + " ";
        if (this.coFac instanceof AlgebraicNumberRing) {
            an = (AlgebraicNumberRing) this.coFac;
            res = "AN[ (" + an.ring.varsToString() + ") (" + an.modul + ") ]";
        }
        if (this.coFac instanceof GenPolynomialRing) {
            res = "IntFunc( " + ((GenPolynomialRing) this.coFac).toString() + " )";
        }
        if (this.coFac instanceof ModIntegerRing) {
            res = "Mod " + ((ModIntegerRing) this.coFac).getModul() + " ";
        }
        return res + "( " + varsToString() + " ) " + this.tord.toString() + " ]";
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("PolyRing.new(");
                break;
            default:
                s.append("PolyRing(");
                break;
        }
        if (this.coFac instanceof RingElem) {
            s.append(((RingElem) this.coFac).toScriptFactory());
        } else {
            s.append(this.coFac.toScript().trim());
        }
        s.append(",\"" + varsToString() + "\"");
        String to = this.tord.toString();
        if (this.tord.getEvord() == 2) {
            to = ",PolyRing.lex";
        }
        if (this.tord.getEvord() == 4) {
            to = ",PolyRing.grad";
        }
        s.append(to);
        s.append(")");
        return s.toString();
    }

    public String toScript(ExpVector e) {
        if (this.vars != null) {
            return e.toScript(this.vars);
        }
        return e.toScript();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GenPolynomialRing)) {
            return false;
        }
        GenPolynomialRing<C> oring = (GenPolynomialRing) other;
        if (this.nvar == oring.nvar && this.coFac.equals(oring.coFac) && this.tord.equals(oring.tord) && Arrays.equals(this.vars, oring.vars)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return ((this.nvar << 27) + (this.coFac.hashCode() << 11)) + this.tord.hashCode();
    }

    public int getCreations() {
        return creations;
    }

    public String[] getVars() {
        return (String[]) Arrays.copyOf(this.vars, this.vars.length);
    }

    public String[] setVars(String[] v) {
        if (v.length != this.nvar) {
            throw new IllegalArgumentException("v not matching number of variables: " + Arrays.toString(v) + ", nvar " + this.nvar);
        }
        String[] t = this.vars;
        this.vars = (String[]) Arrays.copyOf(v, v.length);
        return t;
    }

    public String varsToString() {
        if (this.vars == null) {
            return "#" + this.nvar;
        }
        return ExpVector.varsToString(this.vars);
    }

    public C getZEROCoefficient() {
        return (RingElem) this.coFac.getZERO();
    }

    public C getONECoefficient() {
        return (RingElem) this.coFac.getONE();
    }

    public GenPolynomial<C> getZERO() {
        return this.ZERO;
    }

    public GenPolynomial<C> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        return this.coFac.isCommutative();
    }

    public boolean isAssociative() {
        return this.coFac.isAssociative();
    }

    public boolean isField() {
        if (this.isField > 0) {
            return true;
        }
        if (this.isField == 0) {
            return false;
        }
        if (this.coFac.isField() && this.nvar == 0) {
            this.isField = 1;
            return true;
        }
        this.isField = 0;
        return false;
    }

    public BigInteger characteristic() {
        return this.coFac.characteristic();
    }

    public GenPolynomial<C> valueOf(C a) {
        return new GenPolynomial(this, (RingElem) a);
    }

    public GenPolynomial<C> valueOf(ExpVector e) {
        return new GenPolynomial(this, (RingElem) this.coFac.getONE(), e);
    }

    public GenPolynomial<C> valueOf(C a, ExpVector e) {
        return new GenPolynomial(this, a, e);
    }

    public GenPolynomial<C> fromInteger(long a) {
        return new GenPolynomial(this, (RingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public GenPolynomial<C> fromInteger(BigInteger a) {
        return new GenPolynomial(this, (RingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public GenPolynomial<C> random(int n) {
        return random(n, random);
    }

    public GenPolynomial<C> random(int n, Random rnd) {
        if (this.nvar == 1) {
            return random(3, n, n, GenVectorModul.DEFAULT_DENSITY, rnd);
        }
        return random(3, n, n, 0.3f, rnd);
    }

    public GenPolynomial<C> random(int k, int l, int d, float q) {
        return random(k, l, d, q, random);
    }

    public GenPolynomial<C> random(int k, int l, int d, float q, Random rnd) {
        GenPolynomial<C> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = r.sum((RingElem) this.coFac.random(k, rnd), ExpVector.EVRAND(this.nvar, (long) d, q, rnd));
        }
        return r;
    }

    public GenPolynomial<C> copy(GenPolynomial<C> c) {
        return new GenPolynomial(this, c.val);
    }

    public GenPolynomial<C> parse(String s) {
        String val = s;
        if (!s.contains("|")) {
            val = val.replace(VectorFormat.DEFAULT_PREFIX, BuildConfig.FLAVOR).replace(VectorFormat.DEFAULT_SUFFIX, BuildConfig.FLAVOR);
        }
        return parse(new StringReader(val));
    }

    public GenPolynomial<C> parse(Reader r) {
        try {
            return new GenPolynomialTokenizer(this, r).nextPolynomial();
        } catch (IOException e) {
            logger.error(e.toString() + " parse " + this);
            return this.ZERO;
        }
    }

    public GenPolynomial<C> univariate(int i) {
        return univariate(0, i, 1);
    }

    public GenPolynomial<C> univariate(int i, long e) {
        return univariate(0, i, e);
    }

    public GenPolynomial<C> univariate(int modv, int i, long e) {
        GenPolynomial<C> p = getZERO();
        int r = this.nvar - modv;
        if (i < 0 || i >= r) {
            return p;
        }
        RingElem one = (RingElem) this.coFac.getONE();
        ExpVector f = ExpVector.create(r, i, e);
        if (modv > 0) {
            f = f.extend(modv, 0, 0);
        }
        return p.sum(one, f);
    }

    public List<GenPolynomial<C>> getGenerators() {
        List<? extends GenPolynomial<C>> univs = univariateList();
        List<GenPolynomial<C>> gens = new ArrayList(univs.size() + 1);
        gens.add(getONE());
        gens.addAll(univs);
        return gens;
    }

    public List<GenPolynomial<C>> generators() {
        List<? extends C> cogens = this.coFac.generators();
        List<? extends GenPolynomial<C>> univs = univariateList();
        List<GenPolynomial<C>> gens = new ArrayList(univs.size() + cogens.size());
        Iterator i$ = cogens.iterator();
        while (i$.hasNext()) {
            gens.add(getONE().multiply((RingElem) i$.next()));
        }
        gens.addAll(univs);
        return gens;
    }

    public List<GenPolynomial<C>> generators(int modv) {
        List<? extends C> cogens = this.coFac.generators();
        List<? extends GenPolynomial<C>> univs = univariateList(modv);
        List<GenPolynomial<C>> gens = new ArrayList(univs.size() + cogens.size());
        Iterator i$ = cogens.iterator();
        while (i$.hasNext()) {
            gens.add(getONE().multiply((RingElem) i$.next()));
        }
        gens.addAll(univs);
        return gens;
    }

    public boolean isFinite() {
        return this.nvar == 0 && this.coFac.isFinite();
    }

    public List<? extends GenPolynomial<C>> univariateList() {
        return univariateList(0, 1);
    }

    public List<? extends GenPolynomial<C>> univariateList(int modv) {
        return univariateList(modv, 1);
    }

    public List<? extends GenPolynomial<C>> univariateList(int modv, long e) {
        List<GenPolynomial<C>> pols = new ArrayList(this.nvar);
        int nm = this.nvar - modv;
        for (int i = 0; i < nm; i++) {
            pols.add(univariate(modv, (nm - 1) - i, e));
        }
        return pols;
    }

    public GenPolynomialRing<C> extend(int i) {
        return extend(newVars("e", i));
    }

    public GenPolynomialRing<C> extend(String[] vn) {
        if (vn == null || this.vars == null) {
            throw new IllegalArgumentException("vn and vars may not be null");
        }
        int k;
        int i = vn.length;
        String[] v = new String[(this.vars.length + i)];
        for (k = 0; k < this.vars.length; k++) {
            v[k] = this.vars[k];
        }
        for (k = 0; k < vn.length; k++) {
            v[this.vars.length + k] = vn[k];
        }
        return new GenPolynomialRing(this.coFac, this.nvar + i, this.tord.extend(this.nvar, i), v);
    }

    public GenPolynomialRing<C> extendLower(int i) {
        return extendLower(newVars("e", i));
    }

    public GenPolynomialRing<C> extendLower(String[] vn) {
        if (vn == null || this.vars == null) {
            throw new IllegalArgumentException("vn and vars may not be null");
        }
        int k;
        int i = vn.length;
        String[] v = new String[(this.vars.length + i)];
        for (k = 0; k < vn.length; k++) {
            v[k] = vn[k];
        }
        for (k = 0; k < this.vars.length; k++) {
            v[vn.length + k] = this.vars[k];
        }
        return new GenPolynomialRing(this.coFac, this.nvar + i, this.tord.extendLower(this.nvar, i), v);
    }

    public GenPolynomialRing<C> contract(int i) {
        String[] v = null;
        if (this.vars != null) {
            v = new String[(this.vars.length - i)];
            for (int j = 0; j < this.vars.length - i; j++) {
                v[j] = this.vars[j];
            }
        }
        return new GenPolynomialRing(this.coFac, this.nvar - i, this.tord.contract(i, this.nvar - i), v);
    }

    public GenPolynomialRing<GenPolynomial<C>> recursive(int i) {
        if (i <= 0 || i >= this.nvar) {
            throw new IllegalArgumentException("wrong: 0 < " + i + " < " + this.nvar);
        }
        GenPolynomialRing<C> cfac = contract(i);
        String[] v = null;
        if (this.vars != null) {
            v = new String[i];
            int k = 0;
            int j = this.nvar - i;
            while (j < this.nvar) {
                int k2 = k + 1;
                v[k] = this.vars[j];
                j++;
                k = k2;
            }
        }
        return new GenPolynomialRing(cfac, i, this.tord.contract(0, i), v);
    }

    public GenPolynomialRing<C> distribute() {
        if (!(this.coFac instanceof GenPolynomialRing)) {
            return this;
        }
        GenPolynomialRing<C> pfac;
        GenPolynomialRing cr = (GenPolynomialRing) this.coFac;
        if (cr.vars != null) {
            pfac = extend(cr.vars);
        } else {
            pfac = extend(cr.nvar);
        }
        return pfac;
    }

    public GenPolynomialRing<C> reverse() {
        return reverse(false);
    }

    public GenPolynomialRing<C> reverse(boolean partial) {
        String[] v = null;
        if (this.vars != null) {
            v = new String[this.vars.length];
            int k = this.tord.getSplit();
            int j;
            if (!partial || k >= this.vars.length) {
                for (j = 0; j < this.vars.length; j++) {
                    v[j] = this.vars[(this.vars.length - 1) - j];
                }
            } else {
                for (j = 0; j < k; j++) {
                    v[(this.vars.length - k) + j] = this.vars[(this.vars.length - k) + j];
                }
                for (j = 0; j < this.vars.length - k; j++) {
                    v[j] = this.vars[((this.vars.length - k) - j) - 1];
                }
            }
        }
        GenPolynomialRing<C> pfac = new GenPolynomialRing(this.coFac, this.nvar, this.tord.reverse(partial), v);
        pfac.partial = partial;
        return pfac;
    }

    public PolynomialComparator<C> getComparator() {
        return new PolynomialComparator(this.tord, false);
    }

    public PolynomialComparator<C> getComparator(boolean rev) {
        return new PolynomialComparator(this.tord, rev);
    }

    public static String[] newVars(String prefix, int n) {
        String[] vars = new String[n];
        synchronized (knownVars) {
            int m = knownVars.size();
            String name = prefix + m;
            for (int i = 0; i < n; i++) {
                while (knownVars.contains(name)) {
                    m++;
                    name = prefix + m;
                }
                vars[i] = name;
                knownVars.add(name);
                m++;
                name = prefix + m;
            }
        }
        return vars;
    }

    public String[] newVars(String prefix) {
        return newVars(prefix, this.nvar);
    }

    public static String[] newVars(int n) {
        return newVars(UnivPowerSeriesRing.DEFAULT_NAME, n);
    }

    public String[] newVars() {
        return newVars(this.nvar);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void addVars(java.lang.String[] r4) {
        /*
        if (r4 != 0) goto L_0x0003;
    L_0x0002:
        return;
    L_0x0003:
        r2 = knownVars;
        monitor-enter(r2);
        r0 = 0;
    L_0x0007:
        r1 = r4.length;	 Catch:{ all -> 0x0016 }
        if (r0 >= r1) goto L_0x0014;
    L_0x000a:
        r1 = knownVars;	 Catch:{ all -> 0x0016 }
        r3 = r4[r0];	 Catch:{ all -> 0x0016 }
        r1.add(r3);	 Catch:{ all -> 0x0016 }
        r0 = r0 + 1;
        goto L_0x0007;
    L_0x0014:
        monitor-exit(r2);	 Catch:{ all -> 0x0016 }
        goto L_0x0002;
    L_0x0016:
        r1 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0016 }
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.poly.GenPolynomialRing.addVars(java.lang.String[]):void");
    }

    public static String[] permuteVars(List<Integer> P, String[] vars) {
        if (vars == null || vars.length <= 1) {
            return vars;
        }
        String[] b = new String[vars.length];
        int j = 0;
        for (Integer i : P) {
            int j2 = j + 1;
            b[j] = vars[i.intValue()];
            j = j2;
        }
        return b;
    }

    public GenPolynomialRing<C> permutation(List<Integer> P) {
        if (this.nvar <= 1) {
            return this;
        }
        TermOrder tp = this.tord.permutation(P);
        if (this.vars == null) {
            return new GenPolynomialRing(this.coFac, this.nvar, tp);
        }
        int i;
        String[] v1 = new String[this.vars.length];
        for (i = 0; i < v1.length; i++) {
            v1[i] = this.vars[(v1.length - 1) - i];
        }
        String[] vp = permuteVars(P, v1);
        String[] v2 = new String[vp.length];
        for (i = 0; i < vp.length; i++) {
            v2[i] = vp[(vp.length - 1) - i];
        }
        return new GenPolynomialRing(this.coFac, this.nvar, tp, v2);
    }

    public Iterator<GenPolynomial<C>> iterator() {
        if (this.coFac.isFinite()) {
            return new GenPolynomialIterator(this);
        }
        logger.warn("ring of coefficients " + this.coFac + " is infinite, constructing iterator only over monomials");
        return new GenPolynomialMonomialIterator(this);
    }
}
