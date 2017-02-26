package edu.jas.poly;

import edu.jas.kern.PrettyPrint;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.QuotPair;
import edu.jas.structure.QuotPairFactory;
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
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class QLRSolvablePolynomialRing<C extends GcdRingElem<C> & QuotPair<GenPolynomial<D>>, D extends GcdRingElem<D>> extends GenSolvablePolynomialRing<C> {
    private static final Logger logger;
    public final QLRSolvablePolynomial<C, D> ONE;
    public final QLRSolvablePolynomial<C, D> ZERO;
    public final RecSolvablePolynomialRing<D> polCoeff;
    public final QuotPairFactory<GenPolynomial<D>, C> qpfac;

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
        logger = Logger.getLogger(QLRSolvablePolynomialRing.class);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n) {
        this(cf, n, new TermOrder(), null, null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n, RelationTable<C> rt) {
        this(cf, n, new TermOrder(), null, rt);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t) {
        this(cf, n, t, null, null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, RelationTable<C> rt) {
        this(cf, n, t, null, rt);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, String[] v) {
        this(cf, n, t, v, null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, TermOrder t, String[] v) {
        this(cf, v.length, t, v, null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, String[] v) {
        this(cf, v.length, new TermOrder(), v, null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, GenSolvablePolynomialRing o) {
        this(cf, o.nvar, o.tord, o.getVars(), null);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, QLRSolvablePolynomialRing o) {
        this((RingFactory) cf, (GenSolvablePolynomialRing) o);
    }

    public QLRSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, String[] v, RelationTable<C> rt) {
        super(cf, n, t, v, rt);
        this.qpfac = (QuotPairFactory) cf;
        this.polCoeff = new RecSolvablePolynomialRing(this.qpfac.pairFactory(), n, t, v);
        if (this.table.size() > 0) {
            this.polCoeff.table.update(null, null, null);
            throw new RuntimeException("TODO");
        }
        this.ZERO = new QLRSolvablePolynomial(this);
        this.ONE = new QLRSolvablePolynomial(this, (GcdRingElem) this.coFac.getONE(), this.evzero);
    }

    public String toString() {
        String res = super.toString();
        if (!PrettyPrint.isTrue()) {
            return res + ", #rel = " + this.table.size() + " + " + this.polCoeff.coeffTable.size() + " + " + this.polCoeff.table.size();
        }
        return (res + "\n" + this.polCoeff.coeffTable.toString(this.vars)) + "\n" + this.polCoeff.table.toString(this.vars);
    }

    public String toScript() {
        StringBuffer s = new StringBuffer();
        switch (1.$SwitchMap$edu$jas$kern$Scripting$Lang[Scripting.getLang().ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                s.append("SolvPolyRing.new(");
                break;
            default:
                s.append("SolvPolyRing(");
                break;
        }
        if (this.coFac instanceof RingElem) {
            s.append(((RingElem) this.coFac).toScriptFactory());
        } else {
            s.append(this.coFac.toScript().trim());
        }
        s.append(",\"" + varsToString() + "\",");
        String to = this.tord.toString();
        if (this.tord.getEvord() == 2) {
            to = "PolyRing.lex";
        }
        if (this.tord.getEvord() == 4) {
            to = "PolyRing.grad";
        }
        s.append(to);
        String rel = BuildConfig.FLAVOR;
        if (this.table.size() > 0) {
            rel = this.table.toScript();
            s.append(",rel=");
            s.append(rel);
        }
        if (this.polCoeff.coeffTable.size() > 0) {
            String crel = this.polCoeff.coeffTable.toScript();
            s.append(",coeffrel=");
            s.append(crel);
        }
        if (this.polCoeff.table.size() > 0) {
            String polrel = this.polCoeff.table.toScript();
            if (!rel.equals(polrel)) {
                s.append(",polrel=");
                s.append(polrel);
            }
        }
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object other) {
        if (!(other instanceof QLRSolvablePolynomialRing)) {
            return false;
        }
        QLRSolvablePolynomialRing<C, D> oring = null;
        try {
            oring = (QLRSolvablePolynomialRing) other;
        } catch (ClassCastException e) {
        }
        if (oring != null && super.equals(other) && this.polCoeff.coeffTable.equals(oring.polCoeff.coeffTable)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((super.hashCode() * 37) + this.table.hashCode()) * 37) + this.polCoeff.coeffTable.hashCode();
    }

    public QLRSolvablePolynomial<C, D> getZERO() {
        return this.ZERO;
    }

    public QLRSolvablePolynomial<C, D> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        if (this.polCoeff.isCommutative()) {
            return super.isCommutative();
        }
        return false;
    }

    public boolean isAssociative() {
        if (!this.coFac.isAssociative()) {
            return false;
        }
        if (!this.polCoeff.isAssociative()) {
            return false;
        }
        List<GenPolynomial<C>> gens = generators();
        int ngen = gens.size();
        for (int i = 0; i < ngen; i++) {
            GcdRingElem lbc;
            QLRSolvablePolynomial Xi = (QLRSolvablePolynomial) gens.get(i);
            if (Xi.degree() == 0) {
                lbc = (GcdRingElem) Xi.leadingBaseCoefficient();
                if (((GenPolynomial) ((QuotPair) lbc).numerator()).degree() == 0 && ((GenPolynomial) ((QuotPair) lbc).denominator()).degree() == 0) {
                }
            }
            for (int j = i + 1; j < ngen; j++) {
                QLRSolvablePolynomial Xj = (QLRSolvablePolynomial) gens.get(j);
                if (Xj.degree() == 0) {
                    lbc = (GcdRingElem) Xi.leadingBaseCoefficient();
                    if (((GenPolynomial) ((QuotPair) lbc).numerator()).degree() == 0 && ((GenPolynomial) ((QuotPair) lbc).denominator()).degree() == 0) {
                    }
                }
                for (int k = j + 1; k < ngen; k++) {
                    QLRSolvablePolynomial<C, D> Xk = (QLRSolvablePolynomial) gens.get(k);
                    if (Xi.degree() != 0 || Xj.degree() != 0 || Xk.degree() != 0) {
                        try {
                            QLRSolvablePolynomial<C, D> p = Xk.multiply(Xj).multiply(Xi);
                            QLRSolvablePolynomial<C, D> q = Xk.multiply(Xj.multiply(Xi));
                            if (!p.equals(q)) {
                                if (logger.isInfoEnabled()) {
                                    logger.info("Xk = " + Xk + ", Xj = " + Xj + ", Xi = " + Xi);
                                    logger.info("p = ( Xk * Xj ) * Xi = " + p);
                                    logger.info("q = Xk * ( Xj * Xi ) = " + q);
                                    logger.info("q-p = " + p.subtract((GenPolynomial) q));
                                }
                                return false;
                            }
                        } catch (IllegalArgumentException e) {
                            System.out.println("qlr assoc: Xi = " + Xi);
                            System.out.println("qlr assoc: Xj = " + Xj);
                            System.out.println("qlr assoc: Xk = " + Xk);
                            e.printStackTrace();
                        }
                    }
                }
                continue;
            }
            continue;
        }
        return true;
    }

    public QLRSolvablePolynomial<C, D> fromInteger(long a) {
        return new QLRSolvablePolynomial(this, (GcdRingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public QLRSolvablePolynomial<C, D> fromInteger(BigInteger a) {
        return new QLRSolvablePolynomial(this, (GcdRingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public QLRSolvablePolynomial<C, D> random(int n) {
        return random(n, random);
    }

    public QLRSolvablePolynomial<C, D> random(int n, Random rnd) {
        if (this.nvar == 1) {
            return random(5, n, n, 0.7f, rnd);
        }
        return random(5, n, 3, 0.3f, rnd);
    }

    public QLRSolvablePolynomial<C, D> random(int k, int l, int d, float q) {
        return random(k, l, d, q, random);
    }

    public QLRSolvablePolynomial<C, D> random(int k, int l, int d, float q, Random rnd) {
        QLRSolvablePolynomial<C, D> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = (QLRSolvablePolynomial) r.sum((GcdRingElem) this.coFac.random(k, rnd), ExpVector.EVRAND(this.nvar, (long) d, q, rnd));
        }
        return r;
    }

    public QLRSolvablePolynomial<C, D> copy(QLRSolvablePolynomial<C, D> c) {
        return new QLRSolvablePolynomial(this, c.getMap());
    }

    public QLRSolvablePolynomial<C, D> parse(String s) {
        return parse(new StringReader(s));
    }

    public QLRSolvablePolynomial<C, D> parse(Reader r) {
        try {
            return new QLRSolvablePolynomial(this, new GenPolynomialTokenizer(this, r).nextSolvablePolynomial());
        } catch (IOException e) {
            logger.error(e.toString() + " parse " + this);
            return this.ZERO;
        }
    }

    public QLRSolvablePolynomial<C, D> univariate(int i) {
        return (QLRSolvablePolynomial) super.univariate(i);
    }

    public QLRSolvablePolynomial<C, D> univariate(int i, long e) {
        return (QLRSolvablePolynomial) super.univariate(i, e);
    }

    public QLRSolvablePolynomial<C, D> univariate(int modv, int i, long e) {
        return (QLRSolvablePolynomial) super.univariate(modv, i, e);
    }

    public List<QLRSolvablePolynomial<C, D>> recUnivariateList() {
        return univariateList(0, 1);
    }

    public List<QLRSolvablePolynomial<C, D>> recUnivariateList(int modv) {
        return univariateList(modv, 1);
    }

    public List<QLRSolvablePolynomial<C, D>> recUnivariateList(int modv, long e) {
        List<QLRSolvablePolynomial<C, D>> pols = new ArrayList(this.nvar);
        int nm = this.nvar - modv;
        for (int i = 0; i < nm; i++) {
            pols.add(univariate(modv, (nm - 1) - i, e));
        }
        return pols;
    }

    public QLRSolvablePolynomialRing<C, D> extend(int i) {
        GenPolynomialRing<C> pfac = super.extend(i);
        QLRSolvablePolynomialRing<C, D> spfac = new QLRSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.table.extend(this.table);
        spfac.polCoeff.coeffTable.extend(this.polCoeff.coeffTable);
        return spfac;
    }

    public QLRSolvablePolynomialRing<C, D> contract(int i) {
        GenPolynomialRing<C> pfac = super.contract(i);
        QLRSolvablePolynomialRing<C, D> spfac = new QLRSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.table.contract(this.table);
        spfac.polCoeff.coeffTable.contract(this.polCoeff.coeffTable);
        return spfac;
    }

    public QLRSolvablePolynomialRing<C, D> reverse() {
        return reverse(false);
    }

    public QLRSolvablePolynomialRing<C, D> reverse(boolean partial) {
        GenPolynomialRing<C> pfac = super.reverse(partial);
        QLRSolvablePolynomialRing<C, D> spfac = new QLRSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.partial = partial;
        spfac.table.reverse(this.table);
        spfac.polCoeff.coeffTable.reverse(this.polCoeff.coeffTable);
        return spfac;
    }

    public QLRSolvablePolynomial<C, D> fromPolyCoefficients(GenSolvablePolynomial<GenPolynomial<D>> A) {
        QLRSolvablePolynomial<C, D> B = getZERO().copy();
        if (!(A == null || A.isZERO())) {
            for (Entry<ExpVector, GenPolynomial<D>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GcdRingElem p = (GcdRingElem) this.qpfac.create((GenSolvablePolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public RecSolvablePolynomial<D> toPolyCoefficients(QLRSolvablePolynomial<C, D> A) {
        RecSolvablePolynomial<D> B = this.polCoeff.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GcdRingElem a = (GcdRingElem) y.getValue();
                if (((GenPolynomial) ((QuotPair) a).denominator()).isONE()) {
                    GenPolynomial<D> p = (GenPolynomial) ((QuotPair) a).numerator();
                    if (!p.isZERO()) {
                        B.doPutToMap(e, p);
                    }
                } else {
                    throw new IllegalArgumentException("den != 1 not supported: " + a);
                }
            }
        }
        return B;
    }

    public RecSolvablePolynomial<D> toPolyCoefficients(GenPolynomial<C> A) {
        RecSolvablePolynomial<D> B = this.polCoeff.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            for (Entry<ExpVector, C> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                GcdRingElem a = (GcdRingElem) y.getValue();
                if (((GenPolynomial) ((QuotPair) a).denominator()).isONE()) {
                    GenPolynomial<D> p = (GenPolynomial) ((QuotPair) a).numerator();
                    if (!p.isZERO()) {
                        B.doPutToMap(e, p);
                    }
                } else {
                    throw new IllegalArgumentException("den != 1 not supported: " + a);
                }
            }
        }
        return B;
    }
}
