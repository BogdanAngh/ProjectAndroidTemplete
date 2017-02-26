package edu.jas.poly;

import edu.jas.kern.PreemptStatus;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import io.github.kexanie.library.BuildConfig;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public final class GenWordPolynomialRing<C extends RingElem<C>> implements RingFactory<GenWordPolynomial<C>> {
    private static final Logger logger;
    static final Random random;
    public final GenWordPolynomial<C> ONE;
    public final GenWordPolynomial<C> ZERO;
    public final WordFactory alphabet;
    final boolean checkPreempt;
    public final RingFactory<C> coFac;
    private int isField;
    public final Word wone;

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
        random = new Random();
        logger = Logger.getLogger(GenWordPolynomialRing.class);
    }

    public GenWordPolynomialRing(RingFactory<C> cf, WordFactory wf) {
        this.isField = -1;
        this.checkPreempt = PreemptStatus.isAllowed();
        this.coFac = cf;
        this.alphabet = wf;
        this.ZERO = new GenWordPolynomial(this);
        RingElem coeff = (RingElem) this.coFac.getONE();
        this.wone = wf.getONE();
        this.ONE = new GenWordPolynomial(this, coeff, this.wone);
    }

    public GenWordPolynomialRing(RingFactory<C> cf, String[] s) {
        this((RingFactory) cf, new WordFactory(s));
    }

    public GenWordPolynomialRing(RingFactory<C> cf, String s) {
        this((RingFactory) cf, new WordFactory(s));
    }

    public GenWordPolynomialRing(RingFactory<C> cf, GenWordPolynomialRing o) {
        this((RingFactory) cf, o.alphabet);
    }

    public GenWordPolynomialRing(GenPolynomialRing<C> fac) {
        this(fac.coFac, new WordFactory(fac.vars));
    }

    public GenWordPolynomialRing<C> copy() {
        return new GenWordPolynomialRing(this.coFac, this);
    }

    public String toString() {
        StringBuffer s = new StringBuffer();
        s.append("WordPolyRing(");
        if (this.coFac instanceof RingElem) {
            s.append(((RingElem) this.coFac).toScriptFactory());
        } else {
            s.append(this.coFac.toString().trim());
        }
        s.append(",");
        s.append(this.alphabet.toString());
        s.append(")");
        return s.toString();
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("WordPolyRing.new(");
                break;
            default:
                s.append("WordPolyRing(");
                break;
        }
        if (this.coFac instanceof RingElem) {
            s.append(((RingElem) this.coFac).toScriptFactory());
        } else {
            s.append(this.coFac.toScript().trim());
        }
        s.append(",");
        s.append(this.alphabet.toScript());
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GenWordPolynomialRing)) {
            return false;
        }
        GenWordPolynomialRing<C> oring = (GenWordPolynomialRing) other;
        if (this.coFac.equals(oring.coFac) && this.alphabet.equals(oring.alphabet)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.coFac.hashCode() << 11) + this.alphabet.hashCode();
    }

    public String[] getVars() {
        return this.alphabet.getVars();
    }

    public C getZEROCoefficient() {
        return (RingElem) this.coFac.getZERO();
    }

    public C getONECoefficient() {
        return (RingElem) this.coFac.getONE();
    }

    public GenWordPolynomial<C> getZERO() {
        return this.ZERO;
    }

    public GenWordPolynomial<C> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        return this.coFac.isCommutative() && this.alphabet.isFinite();
    }

    public boolean isAssociative() {
        return this.coFac.isAssociative();
    }

    public boolean isFinite() {
        return this.alphabet.isFinite() && this.coFac.isFinite();
    }

    public boolean isField() {
        if (this.isField > 0) {
            return true;
        }
        if (this.isField == 0) {
            return false;
        }
        if (this.coFac.isField() && this.alphabet.isFinite()) {
            this.isField = 1;
            return true;
        }
        this.isField = 0;
        return false;
    }

    public BigInteger characteristic() {
        return this.coFac.characteristic();
    }

    public GenWordPolynomial<C> valueOf(C a) {
        return new GenWordPolynomial(this, (RingElem) a);
    }

    public GenWordPolynomial<C> valueOf(Word e) {
        return valueOf((RingElem) this.coFac.getONE(), e);
    }

    public GenWordPolynomial<C> valueOf(ExpVector e) {
        return valueOf((RingElem) this.coFac.getONE(), e);
    }

    public GenWordPolynomial<C> valueOf(C a, Word e) {
        return new GenWordPolynomial(this, (RingElem) a, e);
    }

    public GenWordPolynomial<C> valueOf(C a, ExpVector e) {
        return new GenWordPolynomial(this, (RingElem) a, this.alphabet.valueOf(e));
    }

    public GenWordPolynomial<C> valueOf(GenPolynomial<C> a) {
        if (a.isZERO()) {
            return getZERO();
        }
        if (a.isONE()) {
            return getONE();
        }
        GenWordPolynomial<C> p = getZERO().copy();
        for (Entry<ExpVector, C> m : a.val.entrySet()) {
            p.doPutToMap(this.alphabet.valueOf((ExpVector) m.getKey()), (RingElem) m.getValue());
        }
        return p;
    }

    public List<GenWordPolynomial<C>> valueOf(List<GenPolynomial<C>> A) {
        List<GenWordPolynomial<C>> B = new ArrayList(A.size());
        if (!A.isEmpty()) {
            for (GenPolynomial a : A) {
                B.add(valueOf(a));
            }
        }
        return B;
    }

    public GenWordPolynomial<C> fromInteger(long a) {
        return new GenWordPolynomial(this, (RingElem) this.coFac.fromInteger(a), this.wone);
    }

    public GenWordPolynomial<C> fromInteger(BigInteger a) {
        return new GenWordPolynomial(this, (RingElem) this.coFac.fromInteger(a), this.wone);
    }

    public GenWordPolynomial<C> random(int n) {
        return random(n, random);
    }

    public GenWordPolynomial<C> random(int n, Random rnd) {
        return random(5, n, 3, rnd);
    }

    public GenWordPolynomial<C> random(int k, int l, int d) {
        return random(k, l, d, random);
    }

    public GenWordPolynomial<C> random(int k, int l, int d, Random rnd) {
        GenWordPolynomial<C> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = r.sum((RingElem) this.coFac.random(k, rnd), this.alphabet.random(Math.abs(rnd.nextInt() % d), rnd));
        }
        return r;
    }

    public GenWordPolynomial<C> copy(GenWordPolynomial<C> c) {
        return new GenWordPolynomial(this, c.val);
    }

    public GenWordPolynomial<C> parse(String s) {
        String val = s;
        if (!s.contains("|")) {
            val = val.replace(VectorFormat.DEFAULT_PREFIX, BuildConfig.FLAVOR).replace(VectorFormat.DEFAULT_SUFFIX, BuildConfig.FLAVOR);
        }
        return parse(new StringReader(val));
    }

    public GenWordPolynomial<C> parse(Reader r) {
        if (this.alphabet.length() <= 2) {
            GenPolynomial p = null;
            try {
                p = new GenPolynomialTokenizer(new GenPolynomialRing(this.coFac, this.alphabet.getVars()), r).nextPolynomial();
            } catch (IOException e) {
                logger.error(e.toString() + " parse " + this);
            }
            return valueOf(p);
        }
        logger.error("parse not implemented");
        throw new UnsupportedOperationException("not implemented");
    }

    public GenWordPolynomial<C> univariate(int i) {
        GenWordPolynomial<C> p = getZERO();
        List<Word> wgen = this.alphabet.generators();
        if (i < 0 || i >= wgen.size()) {
            return p;
        }
        return p.sum((RingElem) this.coFac.getONE(), (Word) wgen.get(i));
    }

    public GenWordPolynomial<C> commute(int i, int j) {
        GenWordPolynomial<C> p = getZERO();
        List<Word> wgen = this.alphabet.generators();
        if (i < 0 || i >= wgen.size() || j < 0 || j >= wgen.size()) {
            return p;
        }
        RingElem one = (RingElem) this.coFac.getONE();
        Word f = (Word) wgen.get(i);
        Word e = (Word) wgen.get(j);
        p = p.sum(one, e.multiply(f)).subtract(one, f.multiply(e));
        if (i > j) {
            return p.negate();
        }
        return p;
    }

    public List<GenWordPolynomial<C>> commute(int i) {
        int n = this.alphabet.length();
        List<GenWordPolynomial<C>> pols = new ArrayList(n - 1);
        for (int j = 0; j < n; j++) {
            if (i != j) {
                pols.add(commute(i, j));
            }
        }
        return pols;
    }

    public List<GenWordPolynomial<C>> commute() {
        int n = this.alphabet.length();
        List<GenWordPolynomial<C>> pols = new ArrayList((n - 1) * n);
        for (int i = 0; i < n; i++) {
            pols.addAll(commute(i));
        }
        return pols;
    }

    public List<GenWordPolynomial<C>> univariateList() {
        int n = this.alphabet.length();
        List<GenWordPolynomial<C>> pols = new ArrayList(n);
        for (int i = 0; i < n; i++) {
            pols.add(univariate(i));
        }
        return pols;
    }

    public List<GenWordPolynomial<C>> getGenerators() {
        List<GenWordPolynomial<C>> univs = univariateList();
        List<GenWordPolynomial<C>> gens = new ArrayList(univs.size() + 1);
        gens.add(getONE());
        gens.addAll(univs);
        return gens;
    }

    public List<GenWordPolynomial<C>> generators() {
        List<C> cogens = this.coFac.generators();
        List<GenWordPolynomial<C>> univs = univariateList();
        List<GenWordPolynomial<C>> gens = new ArrayList(univs.size() + cogens.size());
        for (C c : cogens) {
            gens.add(getONE().multiply((RingElem) c));
        }
        gens.addAll(univs);
        return gens;
    }
}
