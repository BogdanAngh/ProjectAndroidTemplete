package edu.jas.gbmod;

import edu.jas.kern.PrettyPrint;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.GenPolynomialTokenizer;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.GenSolvablePolynomialRing;
import edu.jas.poly.RecSolvablePolynomial;
import edu.jas.poly.RecSolvablePolynomialRing;
import edu.jas.poly.RelationTable;
import edu.jas.poly.TermOrder;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
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

public class QuotSolvablePolynomialRing<C extends GcdRingElem<C>> extends GenSolvablePolynomialRing<SolvableQuotient<C>> {
    private static final Logger logger;
    public final QuotSolvablePolynomial<C> ONE;
    public final QuotSolvablePolynomial<C> ZERO;
    public final RecSolvablePolynomialRing<C> polCoeff;

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
        logger = Logger.getLogger(QuotSolvablePolynomialRing.class);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n) {
        this(cf, n, new TermOrder(), null, null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n, RelationTable<SolvableQuotient<C>> rt) {
        this(cf, n, new TermOrder(), null, rt);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n, TermOrder t) {
        this(cf, n, t, null, null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n, TermOrder t, RelationTable<SolvableQuotient<C>> rt) {
        this(cf, n, t, null, rt);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n, TermOrder t, String[] v) {
        this(cf, n, t, v, null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, TermOrder t, String[] v) {
        this(cf, v.length, t, v, null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, String[] v) {
        this(cf, v.length, new TermOrder(), v, null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, int n, TermOrder t, String[] v, RelationTable<SolvableQuotient<C>> rt) {
        super(cf, n, t, v, rt);
        this.polCoeff = new RecSolvablePolynomialRing(((SolvableQuotientRing) cf).ring, n, t, v);
        if (this.table.size() > 0) {
            this.polCoeff.table.update(null, null, null);
        }
        this.ZERO = new QuotSolvablePolynomial(this);
        this.ONE = new QuotSolvablePolynomial(this, (SolvableQuotient) this.coFac.getONE(), this.evzero);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, GenSolvablePolynomialRing o) {
        this(cf, o.nvar, o.tord, o.getVars(), null);
    }

    public QuotSolvablePolynomialRing(RingFactory<SolvableQuotient<C>> cf, QuotSolvablePolynomialRing o) {
        this((RingFactory) cf, (GenSolvablePolynomialRing) o);
    }

    public String toString() {
        String res = super.toString();
        if (PrettyPrint.isTrue()) {
            return res + "\n" + this.polCoeff.coeffTable.toString(this.vars);
        }
        return res + ", #rel = " + this.table.size() + " + " + this.polCoeff.coeffTable.size();
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
        if (this.table.size() > 0) {
            String rel = this.table.toScript();
            s.append(",rel=");
            s.append(rel);
        }
        if (this.polCoeff.coeffTable.size() > 0) {
            rel = this.polCoeff.coeffTable.toScript();
            s.append(",coeffrel=");
            s.append(rel);
        }
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof QuotSolvablePolynomialRing)) {
            return false;
        }
        QuotSolvablePolynomialRing<C> oring = (QuotSolvablePolynomialRing) other;
        if (super.equals(other) && this.polCoeff.coeffTable.equals(oring.polCoeff.coeffTable)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((super.hashCode() * 37) + this.table.hashCode()) * 37) + this.polCoeff.coeffTable.hashCode();
    }

    public QuotSolvablePolynomial<C> getZERO() {
        return this.ZERO;
    }

    public QuotSolvablePolynomial<C> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        if (this.polCoeff.coeffTable.size() == 0) {
            return super.isCommutative();
        }
        return false;
    }

    public boolean isAssociative() {
        if (!this.coFac.isAssociative()) {
            return false;
        }
        List<GenPolynomial<SolvableQuotient<C>>> gens = generators();
        int ngen = gens.size();
        for (int i = 0; i < ngen; i++) {
            QuotSolvablePolynomial Xi = (QuotSolvablePolynomial) gens.get(i);
            for (int j = i + 1; j < ngen; j++) {
                QuotSolvablePolynomial Xj = (QuotSolvablePolynomial) gens.get(j);
                int k = j + 1;
                while (k < ngen) {
                    QuotSolvablePolynomial<C> Xk = (QuotSolvablePolynomial) gens.get(k);
                    QuotSolvablePolynomial<C> p = Xk.multiply(Xj).multiply(Xi);
                    QuotSolvablePolynomial<C> q = Xk.multiply(Xj.multiply(Xi));
                    if (p.equals(q)) {
                        k++;
                    } else if (!logger.isInfoEnabled()) {
                        return false;
                    } else {
                        logger.info("Xk = " + Xk + ", Xj = " + Xj + ", Xi = " + Xi);
                        logger.info("p = ( Xk * Xj ) * Xi = " + p);
                        logger.info("q = Xk * ( Xj * Xi ) = " + q);
                        logger.info("q-p = " + p.subtract((GenPolynomial) q));
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public QuotSolvablePolynomial<C> fromInteger(long a) {
        return new QuotSolvablePolynomial(this, (SolvableQuotient) this.coFac.fromInteger(a), this.evzero);
    }

    public QuotSolvablePolynomial<C> fromInteger(BigInteger a) {
        return new QuotSolvablePolynomial(this, (SolvableQuotient) this.coFac.fromInteger(a), this.evzero);
    }

    public QuotSolvablePolynomial<C> random(int n) {
        return random(n, random);
    }

    public QuotSolvablePolynomial<C> random(int n, Random rnd) {
        if (this.nvar == 1) {
            return random(5, n, n, 0.7f, rnd);
        }
        return random(5, n, 3, 0.3f, rnd);
    }

    public QuotSolvablePolynomial<C> random(int k, int l, int d, float q) {
        return random(k, l, d, q, random);
    }

    public QuotSolvablePolynomial<C> random(int k, int l, int d, float q, Random rnd) {
        QuotSolvablePolynomial<C> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = (QuotSolvablePolynomial) r.sum((SolvableQuotient) this.coFac.random(k, rnd), ExpVector.EVRAND(this.nvar, (long) d, q, rnd));
        }
        return r;
    }

    public QuotSolvablePolynomial<C> copy(QuotSolvablePolynomial<C> c) {
        return new QuotSolvablePolynomial(this, c.getMap());
    }

    public QuotSolvablePolynomial<C> parse(String s) {
        return parse(new StringReader(s));
    }

    public QuotSolvablePolynomial<C> parse(Reader r) {
        try {
            return new QuotSolvablePolynomial(this, new GenPolynomialTokenizer(this, r).nextSolvablePolynomial());
        } catch (IOException e) {
            logger.error(e.toString() + " parse " + this);
            return this.ZERO;
        }
    }

    public QuotSolvablePolynomial<C> univariate(int i) {
        return (QuotSolvablePolynomial) super.univariate(i);
    }

    public QuotSolvablePolynomial<C> univariate(int i, long e) {
        return (QuotSolvablePolynomial) super.univariate(i, e);
    }

    public QuotSolvablePolynomial<C> univariate(int modv, int i, long e) {
        return (QuotSolvablePolynomial) super.univariate(modv, i, e);
    }

    public List<QuotSolvablePolynomial<C>> recUnivariateList() {
        return univariateList(0, 1);
    }

    public List<QuotSolvablePolynomial<C>> recUnivariateList(int modv) {
        return univariateList(modv, 1);
    }

    public List<QuotSolvablePolynomial<C>> recUnivariateList(int modv, long e) {
        List<QuotSolvablePolynomial<C>> pols = new ArrayList(this.nvar);
        int nm = this.nvar - modv;
        for (int i = 0; i < nm; i++) {
            pols.add(univariate(modv, (nm - 1) - i, e));
        }
        return pols;
    }

    public QuotSolvablePolynomialRing<C> extend(int i) {
        GenPolynomialRing<SolvableQuotient<C>> pfac = super.extend(i);
        QuotSolvablePolynomialRing<C> spfac = new QuotSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.table.extend(this.table);
        spfac.polCoeff.coeffTable.extend(this.polCoeff.coeffTable);
        return spfac;
    }

    public QuotSolvablePolynomialRing<C> contract(int i) {
        GenPolynomialRing<SolvableQuotient<C>> pfac = super.contract(i);
        QuotSolvablePolynomialRing<C> spfac = new QuotSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.table.contract(this.table);
        spfac.polCoeff.coeffTable.contract(this.polCoeff.coeffTable);
        return spfac;
    }

    public QuotSolvablePolynomialRing<C> reverse() {
        return reverse(false);
    }

    public QuotSolvablePolynomialRing<C> reverse(boolean partial) {
        GenPolynomialRing<SolvableQuotient<C>> pfac = super.reverse(partial);
        QuotSolvablePolynomialRing<C> spfac = new QuotSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.getVars());
        spfac.partial = partial;
        spfac.table.reverse(this.table);
        spfac.polCoeff.coeffTable.reverse(this.polCoeff.coeffTable);
        return spfac;
    }

    public QuotSolvablePolynomial<C> fromPolyCoefficients(GenSolvablePolynomial<GenPolynomial<C>> A) {
        QuotSolvablePolynomial<C> B = getZERO().copy();
        if (!(A == null || A.isZERO())) {
            SolvableQuotientRing<C> qfac = (SolvableQuotientRing) this.coFac;
            for (Entry<ExpVector, GenPolynomial<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                SolvableQuotient<C> p = new SolvableQuotient(qfac, (GenSolvablePolynomial) y.getValue());
                if (!p.isZERO()) {
                    B.doPutToMap(e, p);
                }
            }
        }
        return B;
    }

    public RecSolvablePolynomial<C> toPolyCoefficients(QuotSolvablePolynomial<C> A) {
        RecSolvablePolynomial<C> B = this.polCoeff.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            for (Entry<ExpVector, SolvableQuotient<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                SolvableQuotient<C> a = (SolvableQuotient) y.getValue();
                if (a.den.isONE()) {
                    GenPolynomial<C> p = a.num;
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

    public RecSolvablePolynomial<C> toPolyCoefficients(GenPolynomial<SolvableQuotient<C>> A) {
        RecSolvablePolynomial<C> B = this.polCoeff.getZERO().copy();
        if (!(A == null || A.isZERO())) {
            for (Entry<ExpVector, SolvableQuotient<C>> y : A.getMap().entrySet()) {
                ExpVector e = (ExpVector) y.getKey();
                SolvableQuotient<C> a = (SolvableQuotient) y.getValue();
                if (a.den.isONE()) {
                    GenPolynomial<C> p = a.num;
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
