package edu.jas.poly;

import edu.jas.kern.PrettyPrint;
import edu.jas.kern.Scripting;
import edu.jas.kern.Scripting.Lang;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;

public class RecSolvablePolynomialRing<C extends RingElem<C>> extends GenSolvablePolynomialRing<GenPolynomial<C>> {
    private static final Logger logger;
    public final RecSolvablePolynomial<C> ONE;
    public final RecSolvablePolynomial<C> ZERO;
    public final RelationTable<GenPolynomial<C>> coeffTable;
    private final boolean debug;

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
        logger = Logger.getLogger(RecSolvablePolynomialRing.class);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n) {
        this(cf, n, new TermOrder(), null, null);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n, RelationTable<GenPolynomial<C>> rt) {
        this(cf, n, new TermOrder(), null, rt);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n, TermOrder t) {
        this(cf, n, t, null, null);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n, TermOrder t, RelationTable<GenPolynomial<C>> rt) {
        this(cf, n, t, null, rt);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n, TermOrder t, String[] v) {
        this(cf, n, t, v, null);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, TermOrder t, String[] v) {
        this(cf, v.length, t, v, null);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, String[] v) {
        this(cf, v.length, new TermOrder(), v, null);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, int n, TermOrder t, String[] v, RelationTable<GenPolynomial<C>> rt) {
        super(cf, n, t, v, rt);
        this.debug = logger.isDebugEnabled();
        this.coeffTable = new RelationTable(this, true);
        this.ZERO = new RecSolvablePolynomial(this);
        this.ONE = new RecSolvablePolynomial(this, (GenPolynomial) this.coFac.getONE(), this.evzero);
    }

    public RecSolvablePolynomialRing(RingFactory<GenPolynomial<C>> cf, RecSolvablePolynomialRing o) {
        this(cf, o.nvar, o.tord, o.getVars(), null);
    }

    public String toString() {
        String res = super.toString();
        if (PrettyPrint.isTrue()) {
            return res + "\n" + this.coeffTable.toString(this.vars);
        }
        return res + ", #rel = " + this.table.size() + " + " + this.coeffTable.size();
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
        if (this.coeffTable.size() > 0) {
            rel = this.coeffTable.toScript();
            s.append(",coeffrel=");
            s.append(rel);
        }
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof RecSolvablePolynomialRing) || !super.equals(other)) {
            return false;
        }
        if (this.coeffTable.equals(((RecSolvablePolynomialRing) other).coeffTable)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((super.hashCode() * 37) + this.table.hashCode()) * 37) + this.coeffTable.hashCode();
    }

    public RecSolvablePolynomial<C> getZERO() {
        return this.ZERO;
    }

    public RecSolvablePolynomial<C> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        if (this.coeffTable.isEmpty()) {
            return super.isCommutative();
        }
        return false;
    }

    public boolean isAssociative() {
        if (!this.coFac.isAssociative()) {
            return false;
        }
        List<GenPolynomial<GenPolynomial<C>>> gens = generators();
        int ngen = gens.size();
        for (int i = 0; i < ngen; i++) {
            RecSolvablePolynomial Xi = (RecSolvablePolynomial) gens.get(i);
            for (int j = i + 1; j < ngen; j++) {
                RecSolvablePolynomial Xj = (RecSolvablePolynomial) gens.get(j);
                int k = j + 1;
                while (k < ngen) {
                    RecSolvablePolynomial<C> Xk = (RecSolvablePolynomial) gens.get(k);
                    RecSolvablePolynomial<C> p = Xk.multiply(Xj).multiply(Xi);
                    RecSolvablePolynomial<C> q = Xk.multiply(Xj.multiply(Xi));
                    if (p.equals(q)) {
                        k++;
                    } else {
                        logger.info("Xk = " + Xk + ", Xj = " + Xj + ", Xi = " + Xi);
                        logger.info("p = ( Xk * Xj ) * Xi = " + p);
                        logger.info("q = Xk * ( Xj * Xi ) = " + q);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public RecSolvablePolynomial<C> valueOf(GenPolynomial<C> a) {
        return new RecSolvablePolynomial(this, (GenPolynomial) a);
    }

    public RecSolvablePolynomial<C> valueOf(ExpVector e) {
        return new RecSolvablePolynomial(this, (GenPolynomial) this.coFac.getONE(), e);
    }

    public RecSolvablePolynomial<C> valueOf(GenPolynomial<C> a, ExpVector e) {
        return new RecSolvablePolynomial(this, a, e);
    }

    public RecSolvablePolynomial<C> fromInteger(long a) {
        return new RecSolvablePolynomial(this, (GenPolynomial) this.coFac.fromInteger(a), this.evzero);
    }

    public RecSolvablePolynomial<C> fromInteger(BigInteger a) {
        return new RecSolvablePolynomial(this, (GenPolynomial) this.coFac.fromInteger(a), this.evzero);
    }

    public RecSolvablePolynomial<C> random(int n) {
        return random(n, random);
    }

    public RecSolvablePolynomial<C> random(int n, Random rnd) {
        if (this.nvar == 1) {
            return random(5, n, n, 0.7f, rnd);
        }
        return random(5, n, 3, 0.3f, rnd);
    }

    public RecSolvablePolynomial<C> random(int k, int l, int d, float q) {
        return random(k, l, d, q, random);
    }

    public RecSolvablePolynomial<C> random(int k, int l, int d, float q, Random rnd) {
        RecSolvablePolynomial<C> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = (RecSolvablePolynomial) r.sum((GenPolynomial) this.coFac.random(k, rnd), ExpVector.EVRAND(this.nvar, (long) d, q, rnd));
        }
        return r;
    }

    public RecSolvablePolynomial<C> copy(RecSolvablePolynomial<C> c) {
        return new RecSolvablePolynomial(this, c.val);
    }

    public RecSolvablePolynomial<C> parse(String s) {
        return parse(new StringReader(s));
    }

    public RecSolvablePolynomial<C> parse(Reader r) {
        try {
            return new RecSolvablePolynomial(this, new GenPolynomialTokenizer(this, r).nextSolvablePolynomial());
        } catch (IOException e) {
            logger.error(e.toString() + " parse " + this);
            return this.ZERO;
        }
    }

    public RecSolvablePolynomial<C> univariate(int i) {
        return (RecSolvablePolynomial) super.univariate(i);
    }

    public RecSolvablePolynomial<C> univariate(int i, long e) {
        return (RecSolvablePolynomial) super.univariate(i, e);
    }

    public RecSolvablePolynomial<C> univariate(int modv, int i, long e) {
        return (RecSolvablePolynomial) super.univariate(modv, i, e);
    }

    public List<RecSolvablePolynomial<C>> recUnivariateList() {
        return univariateList(0, 1);
    }

    public List<RecSolvablePolynomial<C>> recUnivariateList(int modv) {
        return univariateList(modv, 1);
    }

    public List<RecSolvablePolynomial<C>> recUnivariateList(int modv, long e) {
        List<RecSolvablePolynomial<C>> pols = new ArrayList(this.nvar);
        int nm = this.nvar - modv;
        for (int i = 0; i < nm; i++) {
            pols.add(univariate(modv, (nm - 1) - i, e));
        }
        return pols;
    }

    public RecSolvablePolynomialRing<C> extend(int i) {
        GenSolvablePolynomialRing<GenPolynomial<C>> pfac = super.extend(i);
        RecSolvablePolynomialRing<C> spfac = new RecSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars, pfac.table);
        spfac.coeffTable.extend(this.coeffTable);
        return spfac;
    }

    public RecSolvablePolynomialRing<C> extend(String[] vs) {
        GenSolvablePolynomialRing<GenPolynomial<C>> pfac = super.extend(vs);
        RecSolvablePolynomialRing<C> spfac = new RecSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars, pfac.table);
        spfac.coeffTable.extend(this.coeffTable);
        return spfac;
    }

    public RecSolvablePolynomialRing<C> contract(int i) {
        GenPolynomialRing<GenPolynomial<C>> pfac = super.contract(i);
        RecSolvablePolynomialRing<C> spfac = new RecSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.table.contract(this.table);
        spfac.coeffTable.contract(this.coeffTable);
        return spfac;
    }

    public RecSolvablePolynomialRing<C> reverse() {
        return reverse(false);
    }

    public RecSolvablePolynomialRing<C> reverse(boolean partial) {
        GenPolynomialRing<GenPolynomial<C>> pfac = super.reverse(partial);
        RecSolvablePolynomialRing<C> spfac = new RecSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.partial = partial;
        spfac.table.reverse(this.table);
        spfac.coeffTable.reverse(this.coeffTable);
        return spfac;
    }

    public static <C extends RingElem<C>> GenSolvablePolynomialRing<C> distribute(RecSolvablePolynomialRing<C> rf) {
        GenPolynomialRing pfd = rf.distribute();
        pfd.table.addRelations(PolyUtil.distribute(pfd, PolynomialList.castToList(rf.coeffTable.relationList())));
        return pfd;
    }

    public GenSolvablePolynomialRing<GenPolynomial<C>> permutation(List<Integer> P) {
        if (this.coeffTable.isEmpty()) {
            return (GenSolvablePolynomialRing) super.permutation(P);
        }
        throw new UnsupportedOperationException("permutation with coeff relations: " + this);
    }
}
