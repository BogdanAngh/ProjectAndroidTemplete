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

public class GenSolvablePolynomialRing<C extends RingElem<C>> extends GenPolynomialRing<C> {
    private static final Logger logger;
    public final GenSolvablePolynomial<C> ONE;
    public final GenSolvablePolynomial<C> ZERO;
    private final boolean debug;
    public final RelationTable<C> table;

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
        logger = Logger.getLogger(GenSolvablePolynomialRing.class);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n) {
        this(cf, n, new TermOrder(), null, null);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n, RelationTable<C> rt) {
        this(cf, n, new TermOrder(), null, rt);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t) {
        this(cf, n, t, null, null);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, RelationTable<C> rt) {
        this(cf, n, t, null, rt);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, String[] v) {
        this(cf, n, t, v, null);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, TermOrder t, String[] v) {
        this(cf, v.length, t, v, null);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, String[] v) {
        this(cf, v.length, new TermOrder(), v, null);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, int n, TermOrder t, String[] v, RelationTable<C> rt) {
        super(cf, n, t, v);
        this.debug = logger.isDebugEnabled();
        if (rt == null) {
            this.table = new RelationTable(this);
        } else {
            this.table = rt;
        }
        this.ZERO = new GenSolvablePolynomial(this);
        this.ONE = new GenSolvablePolynomial(this, (RingElem) this.coFac.getONE(), this.evzero);
    }

    public GenSolvablePolynomialRing(RingFactory<C> cf, GenPolynomialRing o) {
        this(cf, o.nvar, o.tord, o.getVars(), null);
    }

    public void addRelations(RelationGenerator<C> rg) {
        rg.generate(this);
    }

    public void addRelations(List<GenPolynomial<C>> rel) {
        this.table.addRelations(rel);
    }

    public void addSolvRelations(List<GenSolvablePolynomial<C>> rel) {
        this.table.addSolvRelations(rel);
    }

    public String toString() {
        String res = super.toString();
        if (PrettyPrint.isTrue()) {
            return res + "\n" + this.table.toString(this.vars);
        }
        return res + ", #rel = " + this.table.size();
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
        s.append(")");
        return s.toString();
    }

    public boolean equals(Object other) {
        if (other == null || !(other instanceof GenSolvablePolynomialRing)) {
            return false;
        }
        GenSolvablePolynomialRing<C> oring = (GenSolvablePolynomialRing) other;
        if (super.equals(other) && this.table.equals(oring.table)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (super.hashCode() * 37) + this.table.hashCode();
    }

    public GenSolvablePolynomial<C> getZERO() {
        return this.ZERO;
    }

    public GenSolvablePolynomial<C> getONE() {
        return this.ONE;
    }

    public boolean isCommutative() {
        if (this.table.isEmpty()) {
            return super.isCommutative();
        }
        return false;
    }

    public boolean isAssociative() {
        for (int i = 0; i < this.nvar; i++) {
            GenSolvablePolynomial Xi = univariate(i);
            for (int j = i + 1; j < this.nvar; j++) {
                GenSolvablePolynomial Xj = univariate(j);
                int k = j + 1;
                while (k < this.nvar) {
                    GenSolvablePolynomial<C> Xk = univariate(k);
                    GenSolvablePolynomial<C> p = Xk.multiply(Xj).multiply(Xi);
                    GenSolvablePolynomial<C> q = Xk.multiply(Xj.multiply(Xi));
                    if (p.equals(q)) {
                        k++;
                    } else {
                        logger.info("Xi = " + Xi + ", Xj = " + Xj + ", Xk = " + Xk);
                        logger.info("p = ( Xk * Xj ) * Xi = " + p);
                        logger.info("q = Xk * ( Xj * Xi ) = " + q);
                        return false;
                    }
                }
            }
        }
        return this.coFac.isAssociative();
    }

    public GenSolvablePolynomial<C> valueOf(C a) {
        return new GenSolvablePolynomial(this, (RingElem) a);
    }

    public GenSolvablePolynomial<C> valueOf(ExpVector e) {
        return new GenSolvablePolynomial(this, (RingElem) this.coFac.getONE(), e);
    }

    public GenSolvablePolynomial<C> valueOf(C a, ExpVector e) {
        return new GenSolvablePolynomial(this, a, e);
    }

    public GenSolvablePolynomial<C> fromInteger(long a) {
        return new GenSolvablePolynomial(this, (RingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public GenSolvablePolynomial<C> fromInteger(BigInteger a) {
        return new GenSolvablePolynomial(this, (RingElem) this.coFac.fromInteger(a), this.evzero);
    }

    public GenSolvablePolynomial<C> random(int n) {
        return random(n, random);
    }

    public GenSolvablePolynomial<C> random(int n, Random rnd) {
        if (this.nvar == 1) {
            return random(5, n, n, 0.7f, rnd);
        }
        return random(5, n, 3, 0.3f, rnd);
    }

    public GenSolvablePolynomial<C> random(int k, int l, int d, float q) {
        return random(k, l, d, q, random);
    }

    public GenSolvablePolynomial<C> random(int k, int l, int d, float q, Random rnd) {
        GenSolvablePolynomial<C> r = getZERO();
        for (int i = 0; i < l; i++) {
            r = (GenSolvablePolynomial) r.sum((RingElem) this.coFac.random(k, rnd), ExpVector.EVRAND(this.nvar, (long) d, q, rnd));
        }
        return r;
    }

    public GenSolvablePolynomial<C> copy(GenSolvablePolynomial<C> c) {
        return new GenSolvablePolynomial(this, c.val);
    }

    public GenSolvablePolynomial<C> parse(String s) {
        return parse(new StringReader(s));
    }

    public GenSolvablePolynomial<C> parse(Reader r) {
        try {
            return new GenPolynomialTokenizer(this, r).nextSolvablePolynomial();
        } catch (IOException e) {
            logger.error(e.toString() + " parse " + this);
            return this.ZERO;
        }
    }

    public GenSolvablePolynomial<C> univariate(int i) {
        return (GenSolvablePolynomial) super.univariate(i);
    }

    public GenSolvablePolynomial<C> univariate(int i, long e) {
        return (GenSolvablePolynomial) super.univariate(i, e);
    }

    public GenSolvablePolynomial<C> univariate(int modv, int i, long e) {
        return (GenSolvablePolynomial) super.univariate(modv, i, e);
    }

    public List<GenSolvablePolynomial<C>> univariateList() {
        return univariateList(0, 1);
    }

    public List<GenSolvablePolynomial<C>> univariateList(int modv) {
        return univariateList(modv, 1);
    }

    public List<GenSolvablePolynomial<C>> univariateList(int modv, long e) {
        List<GenSolvablePolynomial<C>> pols = new ArrayList(this.nvar);
        int nm = this.nvar - modv;
        for (int i = 0; i < nm; i++) {
            pols.add(univariate(modv, (nm - 1) - i, e));
        }
        return pols;
    }

    public GenSolvablePolynomialRing<C> extend(int i) {
        GenPolynomialRing<C> pfac = super.extend(i);
        GenSolvablePolynomialRing<C> spfac = new GenSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.table.extend(this.table);
        return spfac;
    }

    public GenSolvablePolynomialRing<C> extend(String[] vn) {
        GenPolynomialRing<C> pfac = super.extend(vn);
        GenSolvablePolynomialRing<C> spfac = new GenSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.table.extend(this.table);
        return spfac;
    }

    public GenSolvablePolynomialRing<C> contract(int i) {
        GenPolynomialRing<C> pfac = super.contract(i);
        GenSolvablePolynomialRing<C> spfac = new GenSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.table.contract(this.table);
        return spfac;
    }

    public GenSolvablePolynomialRing<C> reverse() {
        return reverse(false);
    }

    public GenSolvablePolynomialRing<C> reverse(boolean partial) {
        GenPolynomialRing<C> pfac = super.reverse(partial);
        GenSolvablePolynomialRing<C> spfac = new GenSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.partial = partial;
        spfac.table.reverse(this.table);
        return spfac;
    }

    public GenSolvablePolynomialRing<GenPolynomial<C>> recursive(int i) {
        if (i <= 0 || i >= this.nvar) {
            throw new IllegalArgumentException("wrong: 0 < " + i + " < " + this.nvar);
        }
        RingFactory cfac = contract(i);
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
        RecSolvablePolynomialRing<C> pfac = new RecSolvablePolynomialRing(cfac, i, this.tord.contract(0, i), v);
        pfac.table.recursive(this.table);
        pfac.coeffTable.recursive(this.table);
        return pfac;
    }

    public GenSolvablePolynomialRing<C> distribute() {
        if (!(this.coFac instanceof GenPolynomialRing)) {
            return this;
        }
        GenPolynomialRing<C> fac;
        GenPolynomialRing cr = (GenPolynomialRing) this.coFac;
        if (cr.vars != null) {
            fac = cr.extend(this.vars);
        } else {
            fac = cr.extend(this.nvar);
        }
        GenPolynomialRing pfac = new GenSolvablePolynomialRing(fac.coFac, fac.nvar, this.tord, fac.vars);
        if (fac instanceof GenSolvablePolynomialRing) {
            pfac.table.addSolvRelations(((GenSolvablePolynomialRing) fac).table.relationList());
        }
        pfac.table.addRelations(PolyUtil.distribute(pfac, PolynomialList.castToList(this.table.relationList())));
        return pfac;
    }

    public GenPolynomialRing<C> permutation(List<Integer> P) {
        GenPolynomialRing<C> pfac = super.permutation(P);
        GenPolynomialRing spfac = new GenSolvablePolynomialRing(pfac.coFac, pfac.nvar, pfac.tord, pfac.vars);
        spfac.table.addRelations(TermOrderOptimization.permutation((List) P, spfac, new PolynomialList((GenSolvablePolynomialRing) spfac, this.table.relationList()).getList()));
        return spfac;
    }
}
