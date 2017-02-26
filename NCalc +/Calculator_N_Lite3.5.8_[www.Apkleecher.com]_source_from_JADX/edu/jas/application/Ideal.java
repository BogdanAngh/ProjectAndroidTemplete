package edu.jas.application;

import edu.jas.gb.ExtendedGB;
import edu.jas.gb.GroebnerBaseAbstract;
import edu.jas.gb.Reduction;
import edu.jas.gbmod.SyzygySeq;
import edu.jas.gbufd.GBFactory;
import edu.jas.gbufd.GroebnerBasePartial;
import edu.jas.gbufd.PolyGBUtil;
import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.OptimizedPolynomialList;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.PolynomialList;
import edu.jas.poly.TermOrder;
import edu.jas.poly.TermOrderOptimization;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.NotInvertibleException;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import edu.jas.ufd.PolyUfdUtil;
import edu.jas.ufd.Quotient;
import edu.jas.ufd.QuotientRing;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class Ideal<C extends GcdRingElem<C>> implements Comparable<Ideal<C>>, Serializable {
    private static final Logger logger;
    protected final GroebnerBaseAbstract<C> bb;
    private final boolean debug;
    protected final SquarefreeAbstract<C> engine;
    protected boolean isGB;
    protected boolean isTopt;
    protected PolynomialList<C> list;
    protected final Reduction<C> red;
    protected boolean testGB;

    static {
        logger = Logger.getLogger(Ideal.class);
    }

    public Ideal(GenPolynomialRing<C> ring) {
        this((GenPolynomialRing) ring, new ArrayList());
    }

    public Ideal(GenPolynomialRing<C> ring, List<GenPolynomial<C>> F) {
        this(new PolynomialList((GenPolynomialRing) ring, (List) F));
    }

    public Ideal(GenPolynomialRing<C> ring, List<GenPolynomial<C>> F, boolean gb) {
        this(new PolynomialList((GenPolynomialRing) ring, (List) F), gb);
    }

    public Ideal(GenPolynomialRing<C> ring, List<GenPolynomial<C>> F, boolean gb, boolean topt) {
        this(new PolynomialList((GenPolynomialRing) ring, (List) F), gb, topt);
    }

    public Ideal(PolynomialList<C> list) {
        this((PolynomialList) list, false);
    }

    public Ideal(PolynomialList<C> list, GroebnerBaseAbstract<C> bb, Reduction<C> red) {
        this((PolynomialList) list, false, (GroebnerBaseAbstract) bb, (Reduction) red);
    }

    public Ideal(PolynomialList<C> list, boolean gb) {
        this((PolynomialList) list, gb, GBFactory.getImplementation(list.ring.coFac));
    }

    public Ideal(PolynomialList<C> list, boolean gb, boolean topt) {
        this((PolynomialList) list, gb, topt, GBFactory.getImplementation(list.ring.coFac));
    }

    public Ideal(PolynomialList<C> list, boolean gb, GroebnerBaseAbstract<C> bb, Reduction<C> red) {
        this(list, gb, false, bb, red);
    }

    public Ideal(PolynomialList<C> list, boolean gb, GroebnerBaseAbstract<C> bb) {
        this(list, gb, false, bb, bb.red);
    }

    public Ideal(PolynomialList<C> list, boolean gb, boolean topt, GroebnerBaseAbstract<C> bb) {
        this(list, gb, topt, bb, bb.red);
    }

    public Ideal(PolynomialList<C> list, boolean gb, boolean topt, GroebnerBaseAbstract<C> bb, Reduction<C> red) {
        this.debug = logger.isDebugEnabled();
        if (list == null || list.list == null) {
            throw new IllegalArgumentException("list and list.list may not be null");
        }
        this.list = list;
        this.isGB = gb;
        this.isTopt = topt;
        this.testGB = gb;
        this.bb = bb;
        this.red = red;
        this.engine = SquarefreeFactory.getImplementation(list.ring.coFac);
    }

    public Ideal<C> copy() {
        return new Ideal(this.list.copy(), this.isGB, this.isTopt, this.bb, this.red);
    }

    public List<GenPolynomial<C>> getList() {
        return this.list.list;
    }

    public GenPolynomialRing<C> getRing() {
        return this.list.ring;
    }

    public Ideal<C> getZERO() {
        return new Ideal(new PolynomialList(getRing(), new ArrayList(0)), true, this.isTopt, this.bb, this.red);
    }

    public Ideal<C> getONE() {
        List one = new ArrayList(1);
        one.add(this.list.ring.getONE());
        return new Ideal(new PolynomialList(getRing(), one), true, this.isTopt, this.bb, this.red);
    }

    public String toString() {
        return this.list.toString();
    }

    public String toScript() {
        return this.list.toScript();
    }

    public boolean equals(Object b) {
        if (b instanceof Ideal) {
            try {
                Ideal B = (Ideal) b;
                if (contains(B) && B.contains(this)) {
                    return true;
                }
                return false;
            } catch (ClassCastException e) {
                return false;
            }
        }
        logger.warn("equals no Ideal");
        return false;
    }

    public int compareTo(Ideal<C> L) {
        return this.list.compareTo(L.list);
    }

    public int hashCode() {
        int h = this.list.hashCode();
        if (this.isGB) {
            h <<= 1;
        }
        if (this.testGB) {
            return h + 1;
        }
        return h;
    }

    public boolean isZERO() {
        return this.list.isZERO();
    }

    public boolean isONE() {
        return this.list.isONE();
    }

    public void doToptimize() {
        if (!this.isTopt) {
            this.list = TermOrderOptimization.optimizeTermOrder(this.list);
            this.isTopt = true;
            if (this.isGB) {
                this.isGB = false;
                doGB();
            }
        }
    }

    public boolean isGB() {
        if (this.testGB) {
            return this.isGB;
        }
        logger.warn("isGB computing");
        this.isGB = this.bb.isGB(getList());
        this.testGB = true;
        return this.isGB;
    }

    public void doGB() {
        if (!this.isGB || !this.testGB) {
            List<GenPolynomial<C>> G = getList();
            logger.info("GB computing = " + G);
            List G2 = this.bb.GB(G);
            if (this.isTopt) {
                this.list = new OptimizedPolynomialList(((OptimizedPolynomialList) this.list).perm, getRing(), G2);
            } else {
                this.list = new PolynomialList(getRing(), G2);
            }
            this.isGB = true;
            this.testGB = true;
        }
    }

    public Ideal<C> GB() {
        if (!this.isGB) {
            doGB();
        }
        return this;
    }

    public boolean contains(Ideal<C> B) {
        if (B == null || B.isZERO()) {
            return true;
        }
        return contains(B.getList());
    }

    public boolean contains(GenPolynomial<C> b) {
        if (b == null || b.isZERO() || isONE()) {
            return true;
        }
        if (isZERO()) {
            return false;
        }
        if (!this.isGB) {
            doGB();
        }
        GenPolynomial<C> z = this.red.normalform(getList(), (GenPolynomial) b);
        if (z == null || z.isZERO()) {
            return true;
        }
        return false;
    }

    public boolean contains(List<GenPolynomial<C>> B) {
        if (B == null || B.size() == 0 || isONE()) {
            return true;
        }
        if (!this.isGB) {
            doGB();
        }
        for (GenPolynomial b : B) {
            if (b != null && !this.red.normalform(getList(), b).isZERO()) {
                return false;
            }
        }
        return true;
    }

    public Ideal<C> sum(Ideal<C> B) {
        if (B == null || B.isZERO()) {
            return this;
        }
        if (isZERO()) {
            return B;
        }
        List c = new ArrayList(getList().size() + B.getList().size());
        c.addAll(getList());
        c.addAll(B.getList());
        Ideal<C> I = new Ideal(getRing(), c, false);
        if (this.isGB && B.isGB) {
            I.doGB();
        }
        return I;
    }

    public Ideal<C> sum(GenPolynomial<C> b) {
        if (b == null || b.isZERO()) {
            return this;
        }
        List c = new ArrayList(getList().size() + 1);
        c.addAll(getList());
        c.add(b);
        Ideal<C> I = new Ideal(getRing(), c, false);
        if (!this.isGB) {
            return I;
        }
        I.doGB();
        return I;
    }

    public Ideal<C> sum(List<GenPolynomial<C>> L) {
        if (L == null || L.isEmpty()) {
            return this;
        }
        List c = new ArrayList(getList().size() + L.size());
        c.addAll(getList());
        c.addAll(L);
        Ideal<C> I = new Ideal(getRing(), c, false);
        if (!this.isGB) {
            return I;
        }
        I.doGB();
        return I;
    }

    public Ideal<C> product(Ideal<C> B) {
        if (B == null || B.isZERO()) {
            return B;
        }
        if (isZERO()) {
            return this;
        }
        List c = new ArrayList(getList().size() * B.getList().size());
        for (GenPolynomial<C> p : getList()) {
            for (GenPolynomial q : B.getList()) {
                c.add(p.multiply(q));
            }
        }
        Ideal<C> I = new Ideal(getRing(), c, false);
        if (this.isGB && B.isGB) {
            I.doGB();
        }
        return I;
    }

    public Ideal<C> product(GenPolynomial<C> b) {
        if (b == null || b.isZERO()) {
            return getZERO();
        }
        if (isZERO()) {
            return this;
        }
        List c = new ArrayList(getList().size());
        for (GenPolynomial<C> p : getList()) {
            c.add(p.multiply((GenPolynomial) b));
        }
        Ideal<C> I = new Ideal(getRing(), c, false);
        if (this.isGB) {
            I.doGB();
        }
        return I;
    }

    public Ideal<C> intersect(List<Ideal<C>> Bl) {
        if (Bl == null || Bl.size() == 0) {
            return getZERO();
        }
        Ideal<C> I = null;
        for (Ideal B : Bl) {
            if (I == null) {
                I = B;
            } else if (I.isONE()) {
                return I;
            } else {
                I = I.intersect(B);
            }
        }
        return I;
    }

    public Ideal<C> intersect(Ideal<C> B) {
        if (B == null || B.isZERO()) {
            return B;
        }
        if (isZERO()) {
            return this;
        }
        return new Ideal(getRing(), PolyGBUtil.intersect(getRing(), getList(), B.getList()), true);
    }

    public Ideal<C> intersect(GenPolynomialRing<C> R) {
        if (R != null) {
            return new Ideal((GenPolynomialRing) R, PolyUtil.intersect((GenPolynomialRing) R, getList()), this.isGB, this.isTopt);
        }
        throw new IllegalArgumentException("R may not be null");
    }

    public Ideal<C> eliminate(GenPolynomialRing<C> R) {
        if (R != null) {
            return this.list.ring.equals(R) ? this : eliminate(R.getVars()).intersect((GenPolynomialRing) R);
        } else {
            throw new IllegalArgumentException("R may not be null");
        }
    }

    public Ideal<C> eliminate(String... ename) {
        if (ename == null) {
            throw new IllegalArgumentException("ename may not be null");
        }
        String[] aname = getRing().getVars();
        if (aname == null) {
            throw new IllegalArgumentException("aname may not be null");
        }
        PolynomialList Pl;
        GroebnerBasePartial<C> bbp = new GroebnerBasePartial(this.bb, null);
        String[] rname = GroebnerBasePartial.remainingVars(aname, ename);
        if (rname.length != 0) {
            Pl = bbp.elimPartialGB(getList(), rname, ename);
        } else if (Arrays.equals(aname, ename)) {
            return this;
        } else {
            Pl = bbp.partialGB(getList(), ename);
        }
        if (this.debug) {
            logger.debug("elimination GB = " + Pl);
        }
        return new Ideal(Pl, true);
    }

    public Ideal<C> quotient(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return this;
        }
        List H = new ArrayList(1);
        H.add(h);
        Ideal<C> I = intersect(new Ideal(getRing(), H, true));
        List Q = new ArrayList(I.getList().size());
        for (GenPolynomial<C> q : I.getList()) {
            Q.add(q.divide((GenPolynomial) h));
        }
        return new Ideal(getRing(), Q, true);
    }

    public Ideal<C> quotient(Ideal<C> H) {
        if (H == null || H.isZERO() || isZERO()) {
            return this;
        }
        Ideal<C> Q = null;
        for (GenPolynomial h : H.getList()) {
            Ideal Hi = quotient(h);
            if (Q == null) {
                Q = Hi;
            } else {
                Q = Q.intersect(Hi);
            }
        }
        return Q;
    }

    public Ideal<C> infiniteQuotientRab(GenPolynomial<C> h) {
        if (h == null || h.isZERO()) {
            return getONE();
        }
        if (h.isONE() || isZERO()) {
            return this;
        }
        List<GenPolynomial<C>> a = GB().getList();
        List<GenPolynomial<C>> c = new ArrayList(a.size() + 1);
        GenPolynomialRing tfac = getRing().extend(1);
        for (GenPolynomial<C> p : a) {
            c.add(p.extend(tfac, 0, 0));
        }
        c.add(h.extend(tfac, 0, 1).subtract(tfac.getONE()));
        logger.warn("infiniteQuotientRab computing GB ");
        List g = this.bb.GB(c);
        if (this.debug) {
            logger.info("infiniteQuotientRab    = " + tfac + ", c = " + c);
            logger.info("infiniteQuotientRab GB = " + g);
        }
        return new Ideal(tfac, g, true).intersect(getRing());
    }

    public int infiniteQuotientExponent(GenPolynomial<C> h, Ideal<C> Q) {
        int s = 0;
        if (h == null) {
            return 0;
        }
        if (h.isZERO() || h.isONE()) {
            return 0;
        }
        if (isZERO() || isONE()) {
            return 0;
        }
        GenPolynomial<C> p = getRing().getONE();
        for (GenPolynomial q : Q.getList()) {
            if (!contains(q)) {
                GenPolynomial qp = q.multiply((GenPolynomial) p);
                while (!contains(qp)) {
                    GenPolynomial p2 = p.multiply((GenPolynomial) h);
                    s++;
                    qp = q.multiply(p2);
                }
            }
        }
        return s;
    }

    public Ideal<C> infiniteQuotient(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return this;
        }
        int s = 0;
        Ideal<C> I = GB();
        GenPolynomial hs = h;
        Ideal<C> Is = I;
        boolean eq = false;
        while (!eq) {
            Is = I.quotient(hs).GB();
            logger.info("infiniteQuotient s = " + s);
            eq = Is.contains((Ideal) I);
            if (!eq) {
                I = Is;
                s++;
            }
        }
        return Is;
    }

    public boolean isRadicalMember(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return true;
        }
        Ideal<C> x = infiniteQuotientRab((GenPolynomial) h);
        if (this.debug) {
            logger.debug("infiniteQuotientRab = " + x);
        }
        return x.isONE();
    }

    public Ideal<C> infiniteQuotientOld(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return this;
        }
        int s = 0;
        Ideal<C> I = GB();
        GenPolynomial<C> hs = h;
        boolean eq = false;
        while (!eq) {
            Ideal<C> Is = I.quotient((GenPolynomial) hs).GB();
            logger.debug("infiniteQuotient s = " + s);
            eq = Is.contains((Ideal) I);
            if (!eq) {
                I = Is;
                s++;
                hs = hs.multiply((GenPolynomial) h);
            }
        }
        return I;
    }

    public Ideal<C> infiniteQuotient(Ideal<C> H) {
        if (H == null || H.isZERO() || isZERO()) {
            return this;
        }
        Ideal<C> Q = null;
        for (GenPolynomial h : H.getList()) {
            Ideal Hi = infiniteQuotient(h);
            if (Q == null) {
                Q = Hi;
            } else {
                Q = Q.intersect(Hi);
            }
        }
        return Q;
    }

    public Ideal<C> infiniteQuotientRab(Ideal<C> H) {
        if (H == null || H.isZERO() || isZERO()) {
            return this;
        }
        Ideal<C> Q = null;
        for (GenPolynomial h : H.getList()) {
            Ideal Hi = infiniteQuotientRab(h);
            if (Q == null) {
                Q = Hi;
            } else {
                Q = Q.intersect(Hi);
            }
        }
        return Q;
    }

    public Ideal<C> power(int d) {
        if (d <= 0) {
            return getONE();
        }
        if (isZERO() || isONE()) {
            return this;
        }
        Ideal<C> c = this;
        for (int i = 1; i < d; i++) {
            c = c.product(this);
        }
        return c;
    }

    public GenPolynomial<C> normalform(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return h;
        }
        return this.red.normalform(this.list.list, (GenPolynomial) h);
    }

    public List<GenPolynomial<C>> normalform(List<GenPolynomial<C>> L) {
        if (L == null || L.size() == 0 || isZERO()) {
            return L;
        }
        List<GenPolynomial<C>> M = new ArrayList(L.size());
        for (GenPolynomial h : L) {
            GenPolynomial<C> r = normalform(h);
            if (!(r == null || r.isZERO())) {
                M.add(r);
            }
        }
        return M;
    }

    public Ideal<C> annihilator(GenPolynomial<C> h) {
        if (h == null || h.isZERO()) {
            return getZERO();
        }
        if (isZERO()) {
            return this;
        }
        doGB();
        List F = new ArrayList(getList().size() + 1);
        F.add(h);
        F.addAll(getList());
        List<List<GenPolynomial<C>>> S = new SyzygySeq(getRing().coFac).zeroRelationsArbitrary(F);
        List gen = new ArrayList(S.size());
        for (List<GenPolynomial<C>> rel : S) {
            if (!(rel == null || rel.isEmpty())) {
                GenPolynomial<C> p = (GenPolynomial) rel.get(0);
                if (!(p == null || p.isZERO())) {
                    gen.add(p);
                }
            }
        }
        return new Ideal(getRing(), gen);
    }

    public boolean isAnnihilator(GenPolynomial<C> h, Ideal<C> A) {
        return contains(A.product((GenPolynomial) h));
    }

    public Ideal<C> annihilator(Ideal<C> H) {
        if (H == null || H.isZERO()) {
            return getZERO();
        }
        if (isZERO()) {
            return this;
        }
        Ideal<C> ann = null;
        for (GenPolynomial h : H.getList()) {
            Ideal Hi = annihilator(h);
            if (ann == null) {
                ann = Hi;
            } else {
                ann = ann.intersect(Hi);
            }
        }
        return ann;
    }

    public boolean isAnnihilator(Ideal<C> H, Ideal<C> A) {
        return contains(A.product((Ideal) H));
    }

    public GenPolynomial<C> inverse(GenPolynomial<C> h) {
        if (h == null || h.isZERO()) {
            throw new NotInvertibleException("zero not invertible");
        } else if (isZERO()) {
            throw new NotInvertibleException("zero ideal");
        } else if (h.isUnit()) {
            return h.inverse();
        } else {
            doGB();
            List<GenPolynomial<C>> F = new ArrayList(this.list.list.size() + 1);
            F.add(h);
            F.addAll(this.list.list);
            ExtendedGB<C> x = this.bb.extGB(F);
            GenPolynomial<C> one = null;
            int i = -1;
            for (GenPolynomial<C> p : x.G) {
                i++;
                if (p != null && p.isUnit()) {
                    one = p;
                    break;
                }
            }
            if (one == null) {
                throw new NotInvertibleException(" h = " + h);
            }
            GenPolynomial<C> g = (GenPolynomial) ((List) x.G2F.get(i)).get(0);
            if (g == null || g.isZERO()) {
                throw new NotInvertibleException(" h = " + h);
            }
            GenPolynomial<C> k = this.red.normalform(this.list.list, g.multiply((GenPolynomial) h));
            if (!k.isONE()) {
                g = g.multiply((GcdRingElem) ((GcdRingElem) k.leadingBaseCoefficient()).inverse());
            }
            if (!this.debug) {
                return g;
            }
            k = this.red.normalform(this.list.list, g.multiply((GenPolynomial) h));
            logger.debug("inv k = " + k);
            if (k.isUnit()) {
                return g;
            }
            throw new NotInvertibleException(" k = " + k);
        }
    }

    public boolean isUnit(GenPolynomial<C> h) {
        if (h == null || h.isZERO() || isZERO()) {
            return false;
        }
        List<GenPolynomial<C>> F = new ArrayList(this.list.list.size() + 1);
        F.add(h);
        F.addAll(this.list.list);
        for (GenPolynomial<C> p : this.bb.GB(F)) {
            if (p != null && p.isUnit()) {
                return true;
            }
        }
        return false;
    }

    public Ideal<C> squarefree() {
        if (isZERO()) {
            return this;
        }
        Ideal<C> R = this;
        while (true) {
            List<GenPolynomial<C>> li = R.getList();
            List ri = new ArrayList(li);
            for (GenPolynomial h : li) {
                ri.add(this.engine.squarefreePart(h));
            }
            Ideal<C> Rp = new Ideal(R.getRing(), ri, false);
            Rp.doGB();
            if (R.equals(Rp)) {
                return R;
            }
            R = Rp;
        }
    }

    public int commonZeroTest() {
        if (isZERO()) {
            return 1;
        }
        if (!this.isGB) {
            doGB();
        }
        if (isONE()) {
            return -1;
        }
        return this.bb.commonZeroTest(getList());
    }

    public boolean isMaximal() {
        if (commonZeroTest() != 0) {
            return false;
        }
        for (Long d : univariateDegrees()) {
            if (d.longValue() > 1) {
                return false;
            }
        }
        return true;
    }

    public List<Long> univariateDegrees() {
        List<Long> ud = new ArrayList();
        if (isZERO()) {
            return ud;
        }
        if (!this.isGB) {
            doGB();
        }
        return !isONE() ? this.bb.univariateDegrees(getList()) : ud;
    }

    public Dimension dimension() {
        int t = commonZeroTest();
        Set<Integer> S = new HashSet();
        Set<Set<Integer>> M = new HashSet();
        if (t <= 0) {
            return new Dimension(t, S, M, this.list.ring.getVars());
        }
        int d = 0;
        Set<Integer> U = new HashSet();
        for (int i = 0; i < this.list.ring.nvar; i++) {
            U.add(Integer.valueOf(i));
        }
        M = dimension(S, U, M);
        for (Set<Integer> m : M) {
            int dp = m.size();
            if (dp > d) {
                d = dp;
                S = m;
            }
        }
        return new Dimension(d, S, M, this.list.ring.getVars());
    }

    protected Set<Set<Integer>> dimension(Set<Integer> S, Set<Integer> U, Set<Set<Integer>> M) {
        Set<Set<Integer>> MP = M;
        Set<Integer> UP = new HashSet(U);
        for (Integer j : U) {
            UP.remove(j);
            Set<Integer> SP = new HashSet(S);
            SP.add(j);
            if (!containsHT(SP, getList())) {
                MP = dimension(SP, UP, MP);
            }
        }
        boolean contained = false;
        for (Set<Integer> m : MP) {
            if (m.containsAll(S)) {
                contained = true;
                break;
            }
        }
        if (!contained) {
            MP.add(S);
        }
        return MP;
    }

    protected boolean containsHT(Set<Integer> H, List<GenPolynomial<C>> G) {
        Set<Integer> S = null;
        for (GenPolynomial<C> p : G) {
            if (p != null) {
                ExpVector e = p.leadingExpVector();
                if (e != null) {
                    int[] v = e.dependencyOnVariables();
                    if (v == null) {
                        continue;
                    } else {
                        if (S == null) {
                            S = new HashSet(H.size());
                            int r = e.length() - 1;
                            for (Integer i : H) {
                                S.add(Integer.valueOf(r - i.intValue()));
                            }
                        }
                        if (contains(v, S)) {
                            return true;
                        }
                    }
                } else {
                    continue;
                }
            }
        }
        return false;
    }

    protected boolean contains(int[] v, Set<Integer> H) {
        for (int valueOf : v) {
            if (!H.contains(Integer.valueOf(valueOf))) {
                return false;
            }
        }
        return true;
    }

    public List<GenPolynomial<C>> constructUnivariate() {
        List<GenPolynomial<C>> univs = new ArrayList();
        for (int i = this.list.ring.nvar - 1; i >= 0; i--) {
            univs.add(constructUnivariate(i));
        }
        return univs;
    }

    public GenPolynomial<C> constructUnivariate(int i) {
        doGB();
        return this.bb.constructUnivariate(i, getList());
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.application.IdealWithUniv<C>> zeroDimRadicalDecomposition() {
        /*
        r22 = this;
        r4 = new java.util.ArrayList;
        r4.<init>();
        r18 = r22.isZERO();
        if (r18 == 0) goto L_0x000e;
    L_0x000b:
        r18 = r4;
    L_0x000d:
        return r18;
    L_0x000e:
        r11 = new edu.jas.application.IdealWithUniv;
        r18 = new java.util.ArrayList;
        r18.<init>();
        r0 = r22;
        r1 = r18;
        r11.<init>(r0, r1);
        r4.add(r11);
        r18 = r22.isONE();
        if (r18 == 0) goto L_0x0028;
    L_0x0025:
        r18 = r4;
        goto L_0x000d;
    L_0x0028:
        r0 = r22;
        r0 = r0.list;
        r18 = r0;
        r0 = r18;
        r0 = r0.ring;
        r18 = r0;
        r0 = r18;
        r0 = r0.coFac;
        r18 = r0;
        r18 = r18.characteristic();
        r18 = r18.signum();
        if (r18 <= 0) goto L_0x0090;
    L_0x0044:
        r0 = r22;
        r0 = r0.list;
        r18 = r0;
        r0 = r18;
        r0 = r0.ring;
        r18 = r0;
        r0 = r18;
        r0 = r0.coFac;
        r18 = r0;
        r18 = r18.isFinite();
        if (r18 != 0) goto L_0x0090;
    L_0x005c:
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "must use prime decomposition for char p and infinite coefficient rings, found ";
        r19 = r19.append(r20);
        r0 = r22;
        r0 = r0.list;
        r20 = r0;
        r0 = r20;
        r0 = r0.ring;
        r20 = r0;
        r0 = r20;
        r0 = r0.coFac;
        r20 = r0;
        r20 = r20.toScript();
        r19 = r19.append(r20);
        r19 = r19.toString();
        r18.warn(r19);
        r18 = r22.zeroDimPrimeDecomposition();
        goto L_0x000d;
    L_0x0090:
        r0 = r22;
        r0 = r0.list;
        r18 = r0;
        r0 = r18;
        r0 = r0.ring;
        r18 = r0;
        r0 = r18;
        r0 = r0.nvar;
        r18 = r0;
        r6 = r18 + -1;
    L_0x00a4:
        if (r6 < 0) goto L_0x01bc;
    L_0x00a6:
        r15 = new java.util.ArrayList;
        r15.<init>();
        r7 = r4.iterator();
    L_0x00af:
        r18 = r7.hasNext();
        if (r18 == 0) goto L_0x01b7;
    L_0x00b5:
        r9 = r7.next();
        r9 = (edu.jas.application.IdealWithUniv) r9;
        r0 = r9.ideal;
        r18 = r0;
        r0 = r18;
        r17 = r0.constructUnivariate(r6);
        r0 = r22;
        r0 = r0.engine;
        r18 = r0;
        r0 = r18;
        r1 = r17;
        r5 = r0.baseSquarefreeFactors(r1);
        if (r5 == 0) goto L_0x00fd;
    L_0x00d5:
        r18 = r5.size();
        if (r18 == 0) goto L_0x00fd;
    L_0x00db:
        r18 = r5.size();
        r19 = 1;
        r0 = r18;
        r1 = r19;
        if (r0 != r1) goto L_0x011f;
    L_0x00e7:
        r18 = r5.firstKey();
        r0 = r18;
        r18 = r5.get(r0);
        r18 = (java.lang.Long) r18;
        r18 = r18.longValue();
        r20 = 1;
        r18 = (r18 > r20 ? 1 : (r18 == r20 ? 0 : -1));
        if (r18 != 0) goto L_0x011f;
    L_0x00fd:
        r10 = new java.util.ArrayList;
        r10.<init>();
        r0 = r9.upolys;
        r18 = r0;
        r0 = r18;
        r10.addAll(r0);
        r0 = r17;
        r10.add(r0);
        r3 = new edu.jas.application.IdealWithUniv;
        r0 = r9.ideal;
        r18 = r0;
        r0 = r18;
        r3.<init>(r0, r10);
        r15.add(r3);
        goto L_0x00af;
    L_0x011f:
        r18 = logger;
        r18 = r18.isInfoEnabled();
        if (r18 == 0) goto L_0x0141;
    L_0x0127:
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "squarefree facs = ";
        r19 = r19.append(r20);
        r0 = r19;
        r19 = r0.append(r5);
        r19 = r19.toString();
        r18.info(r19);
    L_0x0141:
        r0 = r9.ideal;
        r18 = r0;
        r0 = r18;
        r0 = r0.list;
        r18 = r0;
        r0 = r18;
        r13 = r0.ring;
        r0 = r13.nvar;
        r18 = r0;
        r18 = r18 + -1;
        r12 = r18 - r6;
        r18 = r5.keySet();
        r8 = r18.iterator();
    L_0x015f:
        r18 = r8.hasNext();
        if (r18 == 0) goto L_0x00af;
    L_0x0165:
        r14 = r8.next();
        r14 = (edu.jas.poly.GenPolynomial) r14;
        r16 = r14.extendUnivariate(r13, r12);
        r0 = r9.ideal;
        r18 = r0;
        r0 = r18;
        r1 = r16;
        r2 = r0.sum(r1);
        r10 = new java.util.ArrayList;
        r10.<init>();
        r0 = r9.upolys;
        r18 = r0;
        r0 = r18;
        r10.addAll(r0);
        r10.add(r14);
        r3 = new edu.jas.application.IdealWithUniv;
        r3.<init>(r2, r10);
        r0 = r22;
        r0 = r0.debug;
        r18 = r0;
        if (r18 == 0) goto L_0x01b3;
    L_0x0199:
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "ideal with squarefree facs = ";
        r19 = r19.append(r20);
        r0 = r19;
        r19 = r0.append(r3);
        r19 = r19.toString();
        r18.info(r19);
    L_0x01b3:
        r15.add(r3);
        goto L_0x015f;
    L_0x01b7:
        r4 = r15;
        r6 = r6 + -1;
        goto L_0x00a4;
    L_0x01bc:
        r18 = r4;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.application.Ideal.zeroDimRadicalDecomposition():java.util.List<edu.jas.application.IdealWithUniv<C>>");
    }

    public boolean isZeroDimRadical() {
        if (isZERO() || isONE()) {
            return false;
        }
        if (this.list.ring.coFac.characteristic().signum() > 0 && !this.list.ring.coFac.isFinite()) {
            logger.warn("radical only for char 0 or finite coefficient rings, but found " + this.list.ring.coFac.toScript());
        }
        int i = this.list.ring.nvar - 1;
        while (i >= 0) {
            GenPolynomial u = constructUnivariate(i);
            if (this.engine.isSquarefree(u)) {
                i--;
            } else {
                System.out.println("not squarefree " + this.engine.squarefreePart(u) + ", " + u);
                return false;
            }
        }
        return true;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.application.IdealWithUniv<C>> zeroDimDecomposition() {
        /*
        r24 = this;
        r4 = new java.util.ArrayList;
        r4.<init>();
        r20 = r24.isZERO();
        if (r20 == 0) goto L_0x000d;
    L_0x000b:
        r5 = r4;
    L_0x000c:
        return r5;
    L_0x000d:
        r12 = new edu.jas.application.IdealWithUniv;
        r20 = new java.util.ArrayList;
        r20.<init>();
        r0 = r24;
        r1 = r20;
        r12.<init>(r0, r1);
        r4.add(r12);
        r20 = r24.isONE();
        if (r20 == 0) goto L_0x0026;
    L_0x0024:
        r5 = r4;
        goto L_0x000c;
    L_0x0026:
        r0 = r24;
        r0 = r0.list;
        r20 = r0;
        r0 = r20;
        r0 = r0.ring;
        r20 = r0;
        r0 = r20;
        r0 = r0.coFac;
        r20 = r0;
        r19 = edu.jas.application.FactorFactory.getImplementation(r20);
        r0 = r24;
        r0 = r0.list;
        r20 = r0;
        r0 = r20;
        r0 = r0.ring;
        r20 = r0;
        r0 = r20;
        r0 = r0.nvar;
        r20 = r0;
        r7 = r20 + -1;
    L_0x0050:
        if (r7 < 0) goto L_0x0143;
    L_0x0052:
        r16 = new java.util.ArrayList;
        r16.<init>();
        r8 = r4.iterator();
    L_0x005b:
        r20 = r8.hasNext();
        if (r20 == 0) goto L_0x013d;
    L_0x0061:
        r10 = r8.next();
        r10 = (edu.jas.application.IdealWithUniv) r10;
        r0 = r10.ideal;
        r20 = r0;
        r0 = r20;
        r18 = r0.constructUnivariate(r7);
        r0 = r19;
        r1 = r18;
        r6 = r0.baseFactors(r1);
        r20 = r6.size();
        if (r20 == 0) goto L_0x00a1;
    L_0x007f:
        r20 = r6.size();
        r21 = 1;
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x00c5;
    L_0x008b:
        r20 = r6.firstKey();
        r0 = r20;
        r20 = r6.get(r0);
        r20 = (java.lang.Long) r20;
        r20 = r20.longValue();
        r22 = 1;
        r20 = (r20 > r22 ? 1 : (r20 == r22 ? 0 : -1));
        if (r20 != 0) goto L_0x00c5;
    L_0x00a1:
        r11 = new java.util.ArrayList;
        r11.<init>();
        r0 = r10.upolys;
        r20 = r0;
        r0 = r20;
        r11.addAll(r0);
        r0 = r18;
        r11.add(r0);
        r3 = new edu.jas.application.IdealWithUniv;
        r0 = r10.ideal;
        r20 = r0;
        r0 = r20;
        r3.<init>(r0, r11);
        r0 = r16;
        r0.add(r3);
        goto L_0x005b;
    L_0x00c5:
        r0 = r24;
        r0 = r0.debug;
        r20 = r0;
        if (r20 == 0) goto L_0x00e7;
    L_0x00cd:
        r20 = logger;
        r21 = new java.lang.StringBuilder;
        r21.<init>();
        r22 = "irreducible facs = ";
        r21 = r21.append(r22);
        r0 = r21;
        r21 = r0.append(r6);
        r21 = r21.toString();
        r20.info(r21);
    L_0x00e7:
        r0 = r10.ideal;
        r20 = r0;
        r0 = r20;
        r0 = r0.list;
        r20 = r0;
        r0 = r20;
        r14 = r0.ring;
        r0 = r14.nvar;
        r20 = r0;
        r20 = r20 + -1;
        r13 = r20 - r7;
        r20 = r6.keySet();
        r9 = r20.iterator();
    L_0x0105:
        r20 = r9.hasNext();
        if (r20 == 0) goto L_0x005b;
    L_0x010b:
        r15 = r9.next();
        r15 = (edu.jas.poly.GenPolynomial) r15;
        r17 = r15.extendUnivariate(r14, r13);
        r0 = r10.ideal;
        r20 = r0;
        r0 = r20;
        r1 = r17;
        r2 = r0.sum(r1);
        r11 = new java.util.ArrayList;
        r11.<init>();
        r0 = r10.upolys;
        r20 = r0;
        r0 = r20;
        r11.addAll(r0);
        r11.add(r15);
        r3 = new edu.jas.application.IdealWithUniv;
        r3.<init>(r2, r11);
        r0 = r16;
        r0.add(r3);
        goto L_0x0105;
    L_0x013d:
        r4 = r16;
        r7 = r7 + -1;
        goto L_0x0050;
    L_0x0143:
        r5 = r4;
        goto L_0x000c;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.application.Ideal.zeroDimDecomposition():java.util.List<edu.jas.application.IdealWithUniv<C>>");
    }

    public List<IdealWithUniv<C>> zeroDimDecompositionExtension(List<GenPolynomial<C>> upol, List<GenPolynomial<C>> og) {
        if (upol == null || upol.size() + 1 != this.list.ring.nvar) {
            throw new IllegalArgumentException("univariate polynomial list not correct " + upol);
        }
        List<IdealWithUniv<C>> dec = new ArrayList();
        if (!isZERO()) {
            IdealWithUniv<C> iwu = new IdealWithUniv(this, upol);
            if (isONE()) {
                dec.add(iwu);
            } else {
                List<GenPolynomial<C>> iup;
                FactorAbstract<C> ufd = FactorFactory.getImplementation(this.list.ring.coFac);
                int i = this.list.ring.nvar - 1;
                GenPolynomial<C> u = constructUnivariate(i);
                SortedMap<GenPolynomial<C>, Long> facs = ufd.baseFactors(u);
                if (facs.size() == 1) {
                    if (((Long) facs.get(facs.firstKey())).longValue() == 1) {
                        iup = new ArrayList();
                        iup.add(u);
                        iup.addAll(upol);
                        dec.add(new IdealWithUniv(this, iup, og));
                    }
                }
                logger.info("irreducible facs = " + facs);
                GenPolynomialRing<C> mfac = this.list.ring;
                int j = (mfac.nvar - 1) - i;
                for (GenPolynomial<C> p : facs.keySet()) {
                    Ideal<C> Ip = sum(p.extendUnivariate(mfac, j));
                    iup = new ArrayList();
                    iup.add(p);
                    iup.addAll(upol);
                    dec.add(new IdealWithUniv(Ip, iup, og));
                }
            }
        }
        return dec;
    }

    public boolean isZeroDimDecomposition(List<IdealWithUniv<C>> L) {
        if (L != null && L.size() != 0) {
            StringBuilder append;
            GenPolynomialRing<C> ofac = this.list.ring;
            int r = ofac.nvar;
            int d = ((IdealWithUniv) L.get(0)).ideal.list.ring.nvar - r;
            Ideal Id = this;
            if (d > 0) {
                GenPolynomialRing nfac = ofac.extendLower(d);
                List elist = new ArrayList(this.list.list.size());
                for (GenPolynomial<C> p : getList()) {
                    elist.add(p.extendLower(nfac, 0, 0));
                }
                Id = new Ideal(nfac, elist, this.isGB, this.isTopt);
            }
            for (IdealWithUniv<C> I : L) {
                if (!I.ideal.contains(Id)) {
                    append = new StringBuilder().append("not contained ");
                    System.out.println(r22.append(this).append(" in ").append(I.ideal).toString());
                    return false;
                }
            }
            for (IdealWithUniv<C> I2 : L) {
                GenPolynomialRing<C> mfac = I2.ideal.list.ring;
                int i = 0;
                for (GenPolynomial<C> p2 : I2.upolys) {
                    int i2 = i + 1;
                    GenPolynomial<C> pm = p2.extendUnivariate(mfac, i);
                    if (I2.ideal.contains((GenPolynomial) pm)) {
                        i = i2;
                    } else {
                        append = new StringBuilder().append("not contained ");
                        System.out.println(r22.append(pm).append(" in ").append(I2.ideal).toString());
                        return false;
                    }
                }
            }
            return true;
        } else if (isZERO()) {
            return true;
        } else {
            return false;
        }
    }

    public IdealWithUniv<C> normalPositionFor(int i, int j, List<GenPolynomial<C>> og) {
        GenPolynomialRing<C> ofac = this.list.ring;
        if (ofac.tord.getEvord() != 2) {
            throw new IllegalArgumentException("invalid term order for normalPosition " + ofac.tord);
        } else if (ofac.characteristic().signum() == 0) {
            return normalPositionForChar0(i, j, og);
        } else {
            return normalPositionForCharP(i, j, og);
        }
    }

    IdealWithUniv<C> normalPositionForChar0(int i, int j, List<GenPolynomial<C>> og) {
        Ideal<C> Ip;
        GenPolynomial<C> zp;
        GenPolynomialRing nfac = this.list.ring.extendLower(1);
        List elist = new ArrayList(this.list.list.size() + 1);
        for (GenPolynomial<C> p : getList()) {
            elist.add(p.extendLower(nfac, 0, 0));
        }
        List<GenPolynomial<C>> ogen = new ArrayList();
        if (og != null && og.size() > 0) {
            for (GenPolynomial<C> p2 : og) {
                ogen.add(p2.extendLower(nfac, 0, 0));
            }
        }
        Ideal<C> I = new Ideal(nfac, elist, true);
        int ip = (this.list.ring.nvar - 1) - i;
        int jp = (this.list.ring.nvar - 1) - j;
        GenPolynomial<C> xi = nfac.univariate(ip);
        GenPolynomial<C> xj = nfac.univariate(jp);
        GenPolynomial<C> z = nfac.univariate(nfac.nvar - 1);
        int t = 0;
        do {
            t--;
            zp = z.subtract(xj.subtract(xi.multiply(nfac.fromInteger((long) t)))).monic();
            Ip = I.sum((GenPolynomial) zp);
            if ((-t) % 5 == 0) {
                logger.info("normal position, t = " + t);
            }
        } while (!Ip.isNormalPositionFor(i + 1, j + 1));
        if (this.debug) {
            logger.info("normal position = " + Ip);
        }
        ogen.add(zp);
        return new IdealWithUniv(Ip, null, ogen);
    }

    IdealWithUniv<C> normalPositionForCharP(int i, int j, List<GenPolynomial<C>> og) {
        Ideal<C> Ip;
        GenPolynomial<C> zp;
        GenPolynomialRing<C> nfac = this.list.ring.extendLower(1);
        List elist = new ArrayList(this.list.list.size() + 1);
        for (GenPolynomial<C> extendLower : getList()) {
            elist.add(extendLower.extendLower(nfac, 0, 0));
        }
        List<GenPolynomial<C>> ogen = new ArrayList();
        if (og != null && og.size() > 0) {
            for (GenPolynomial<C> extendLower2 : og) {
                ogen.add(extendLower2.extendLower(nfac, 0, 0));
            }
        }
        Ideal<C> I = new Ideal((GenPolynomialRing) nfac, elist, true);
        int ip = (this.list.ring.nvar - 1) - i;
        int jp = (this.list.ring.nvar - 1) - j;
        GenPolynomial<C> xi = nfac.univariate(ip);
        GenPolynomial<C> xj = nfac.univariate(jp);
        GenPolynomial<C> z = nfac.univariate(nfac.nvar - 1);
        AlgebraicNumberRing<C> afac = null;
        Iterator<AlgebraicNumber<C>> aiter = null;
        int t = 0;
        do {
            GenPolynomial<C> tn;
            t--;
            if (afac == null) {
                tn = nfac.fromInteger((long) t);
                if (tn.isZERO()) {
                    RingFactory<C> fac = nfac.coFac;
                    while (!(fac instanceof AlgebraicNumberRing)) {
                        if (fac instanceof GenPolynomialRing) {
                            fac = ((GenPolynomialRing) fac).coFac;
                        } else if (fac instanceof QuotientRing) {
                            fac = ((QuotientRing) fac).ring.coFac;
                        } else {
                            throw new ArithmeticException("field elements exhausted, need algebraic extension of base ring");
                        }
                    }
                    afac = (AlgebraicNumberRing) fac;
                    logger.info("afac = " + afac.toScript());
                    aiter = afac.iterator();
                    AlgebraicNumber<C> an = (AlgebraicNumber) aiter.next();
                    for (int kk = 0; kk < afac.characteristic().intValue(); kk++) {
                        an = (AlgebraicNumber) aiter.next();
                    }
                    tn = nfac.parse(an.toString());
                }
            } else if (aiter.hasNext()) {
                tn = nfac.parse(((AlgebraicNumber) aiter.next()).toString());
            } else {
                throw new ArithmeticException("field elements exhausted, normal position not reachable: !aiter.hasNext(): " + t);
            }
            if (tn.isZERO()) {
                throw new ArithmeticException("field elements exhausted, normal position not reachable: tn == 0: " + t);
            }
            zp = z.subtract(xj.subtract(xi.multiply((GenPolynomial) tn))).monic();
            Ip = I.sum((GenPolynomial) zp);
            if ((-t) % 4 == 0) {
                logger.info("normal position, t = " + t);
                logger.info("normal position, GB = " + Ip);
                if (t < -550) {
                    throw new ArithmeticException("normal position not reached in " + t + " steps");
                }
            }
        } while (!Ip.isNormalPositionFor(i + 1, j + 1));
        if (this.debug) {
            logger.info("normal position = " + Ip);
        }
        ogen.add(zp);
        return new IdealWithUniv(Ip, null, ogen);
    }

    public boolean isNormalPositionFor(int i, int j) {
        int ip = (this.list.ring.nvar - 1) - i;
        int jp = (this.list.ring.nvar - 1) - j;
        boolean iOK = false;
        boolean jOK = false;
        for (GenPolynomial<C> p : getList()) {
            ExpVector e = p.leadingExpVector();
            int[] dov = e.dependencyOnVariables();
            if (dov.length == 0) {
                throw new IllegalArgumentException("ideal dimension is not zero");
            }
            if (dov[0] == ip) {
                if (e.totalDeg() != 1) {
                    return false;
                }
                iOK = true;
            } else if (dov[0] == jp) {
                if (e.totalDeg() != 1) {
                    return false;
                }
                jOK = true;
            }
            if (iOK && jOK) {
                return true;
            }
        }
        return iOK && jOK;
    }

    public int[] normalPositionIndex2Vars() {
        int i = -1;
        int j = -1;
        for (GenPolynomial<C> p : getList()) {
            int[] dov = p.leadingExpVector().dependencyOnVariables();
            if (dov.length != 0) {
                if (dov.length < 2) {
                    int n = dov[0];
                    dov = p.reductum().degreeVector().dependencyOnVariables();
                    int k = Arrays.binarySearch(dov, n);
                    int len = 2;
                    if (k >= 0) {
                        len = 3;
                    }
                    if (dov.length >= len) {
                        switch (k) {
                            case ValueServer.DIGEST_MODE /*0*/:
                                i = dov[1];
                                j = dov[2];
                                break;
                            case ValueServer.REPLAY_MODE /*1*/:
                                i = dov[0];
                                j = dov[2];
                                break;
                            case IExpr.DOUBLEID /*2*/:
                                i = dov[0];
                                j = dov[1];
                                break;
                            default:
                                i = dov[0];
                                j = dov[1];
                                break;
                        }
                    }
                }
                i = dov[0];
                j = dov[1];
                break;
            }
            throw new IllegalArgumentException("ideal dimension is not zero " + p);
        }
        if (i < 0 || j < 0) {
            return null;
        }
        i = (this.list.ring.nvar - 1) - i;
        int[] np = new int[]{(this.list.ring.nvar - 1) - j, i};
        logger.info("normalPositionIndex2Vars, np = " + Arrays.toString(np));
        return np;
    }

    public int[] normalPositionIndexUnivars() {
        int i = -1;
        int j = -1;
        for (GenPolynomial<C> p : getList()) {
            ExpVector e = p.degreeVector();
            int[] dov = e.dependencyOnVariables();
            long t = e.totalDeg();
            if (dov.length != 0) {
                if (dov.length == 1 && t >= 2) {
                    if (i == -1) {
                        i = dov[0];
                    } else if (j == -1) {
                        j = dov[0];
                        if (i > j) {
                            int x = i;
                            i = j;
                            j = x;
                        }
                    }
                }
                if (i >= 0 && j >= 0) {
                    break;
                }
            } else {
                throw new IllegalArgumentException("ideal dimension is not zero");
            }
        }
        if (i < 0 || j < 0) {
            for (GenPolynomial<C> p2 : getList()) {
                if (p2.leadingExpVector().totalDeg() >= 2) {
                    dov = p2.degreeVector().dependencyOnVariables();
                    if (dov.length == 0) {
                        throw new IllegalArgumentException("ideal dimension is not zero");
                    } else if (dov.length >= 2) {
                        i = dov[0];
                        j = dov[1];
                    }
                }
                if (i >= 0 && j >= 0) {
                    break;
                }
            }
        }
        if (i < 0 || j < 0) {
            return null;
        }
        i = (this.list.ring.nvar - 1) - i;
        int[] np = new int[]{(this.list.ring.nvar - 1) - j, i};
        logger.info("normalPositionIndexUnivars, np = " + Arrays.toString(np));
        return np;
    }

    public List<IdealWithUniv<C>> zeroDimRootDecomposition() {
        List<IdealWithUniv<C>> dec = zeroDimDecomposition();
        if (isZERO() || isONE()) {
            return dec;
        }
        List<IdealWithUniv<C>> rdec = new ArrayList();
        while (dec.size() > 0) {
            IdealWithUniv<C> id = (IdealWithUniv) dec.remove(0);
            int[] ri = id.ideal.normalPositionIndex2Vars();
            if (ri == null || ri.length != 2) {
                rdec.add(id);
            } else {
                IdealWithUniv<C> I = id.ideal.normalPositionFor(ri[0], ri[1], id.others);
                dec.addAll(I.ideal.zeroDimDecompositionExtension(id.upolys, I.others));
            }
        }
        return rdec;
    }

    public List<IdealWithUniv<C>> zeroDimPrimeDecomposition() {
        List<IdealWithUniv<C>> pdec = zeroDimPrimeDecompositionFE();
        List<IdealWithUniv<C>> dec = new ArrayList();
        IdealWithUniv<C> Ip;
        if (pdec.size() == 1) {
            Ip = (IdealWithUniv) pdec.get(0);
            dec.add(new IdealWithUniv(this, Ip.upolys.subList(Ip.upolys.size() - getRing().nvar, Ip.upolys.size())));
        } else {
            for (IdealWithUniv<C> Ip2 : pdec) {
                if (Ip2.ideal.getRing().nvar == getRing().nvar) {
                    dec.add(Ip2);
                } else {
                    Ideal<C> Id = Ip2.ideal;
                    if (Ip2.others != null) {
                        List pp = new ArrayList();
                        pp.addAll(Id.getList());
                        pp.addAll(Ip2.others);
                        Id = new Ideal(Id.getRing(), pp);
                    }
                    dec.add(new IdealWithUniv(Id.eliminate(getRing()), Ip2.upolys.subList(Ip2.upolys.size() - getRing().nvar, Ip2.upolys.size())));
                }
            }
        }
        return dec;
    }

    public List<IdealWithUniv<C>> zeroDimPrimeDecompositionFE() {
        List<IdealWithUniv<C>> dec = zeroDimRootDecomposition();
        if (isZERO() || isONE()) {
            return dec;
        }
        List<IdealWithUniv<C>> rdec = new ArrayList();
        while (dec.size() > 0) {
            IdealWithUniv<C> id = (IdealWithUniv) dec.remove(0);
            int[] ri = id.ideal.normalPositionIndexUnivars();
            if (ri == null || ri.length != 2) {
                rdec.add(id);
            } else {
                IdealWithUniv<C> I = id.ideal.normalPositionFor(ri[0], ri[1], id.others);
                dec.addAll(I.ideal.zeroDimDecompositionExtension(id.upolys, I.others));
            }
        }
        return rdec;
    }

    public Ideal<C> primaryIdeal(Ideal<C> P) {
        Ideal<C> As;
        Ideal<C> Qs = P;
        int e = 0;
        do {
            Ideal<C> Q = Qs;
            e++;
            Qs = Q.product((Ideal) P);
        } while (Qs.contains(this));
        boolean t;
        do {
            As = sum((Ideal) Qs);
            t = As.contains((Ideal) Q);
            if (!t) {
                Q = Qs;
                e++;
                Qs = Q.product((Ideal) P);
                continue;
            }
        } while (!t);
        logger.info("exponent = " + e);
        return As;
    }

    public List<PrimaryComponent<C>> zeroDimPrimaryDecomposition() {
        List<IdealWithUniv<C>> pdec = zeroDimPrimeDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("prim decomp = " + pdec);
        }
        return zeroDimPrimaryDecomposition(pdec);
    }

    public List<IdealWithUniv<C>> zeroDimElimination(List<IdealWithUniv<C>> pdec) {
        List<IdealWithUniv<C>> dec = new ArrayList();
        if (isZERO()) {
            return dec;
        }
        if (isONE()) {
            dec.add(pdec.get(0));
            return dec;
        }
        List<IdealWithUniv<C>> qdec = new ArrayList();
        for (IdealWithUniv<C> Ip : pdec) {
            List epols;
            List<GenPolynomial> epol = new ArrayList();
            epol.addAll(Ip.ideal.getList());
            GenPolynomialRing<C> mfac = Ip.ideal.list.ring;
            int j = 0;
            for (GenPolynomial<C> extendUnivariate : Ip.upolys) {
                int j2 = j + 1;
                GenPolynomial<C> pm = extendUnivariate.extendUnivariate(mfac, j);
                if (j2 != 1) {
                    epol.add(pm);
                }
                j = j2;
            }
            if (Ip.others != null) {
                epol.addAll(Ip.others);
            }
            Ideal<C> Ipp = new Ideal((GenPolynomialRing) mfac, (List) epol);
            TermOrder to = null;
            if (mfac.tord.getEvord() != 4) {
                epols = new ArrayList();
                TermOrder termOrder = new TermOrder(4);
                GenPolynomialRing<C> genPolynomialRing = new GenPolynomialRing(mfac.coFac, mfac.nvar, termOrder, mfac.getVars());
                for (GenPolynomial copy : epol) {
                    epols.add(genPolynomialRing.copy(copy).monic());
                }
                Ipp = new Ideal((GenPolynomialRing) genPolynomialRing, epols);
            }
            List epol2 = this.red.irreducibleSet(Ipp.getList());
            Ideal<C> Ipp2 = new Ideal(Ipp.getRing(), epol2);
            if (logger.isInfoEnabled()) {
                logger.info("eliminate = " + Ipp2);
            }
            Ideal<C> Is = Ipp2.eliminate(this.list.ring);
            if (to != null) {
                if (!Is.list.ring.equals(this.list.ring)) {
                    epols = new ArrayList();
                    for (GenPolynomial<C> p : Is.getList()) {
                        epols.add(this.list.ring.copy((GenPolynomial) p));
                    }
                    Is = new Ideal(this.list.ring, epols);
                }
            }
            List list = Ip.upolys;
            int k = r0.size() - this.list.ring.nvar;
            List<GenPolynomial<C>> up = new ArrayList();
            int i = 0;
            while (true) {
                int i2 = this.list.ring.nvar;
                if (i >= r0) {
                    break;
                }
                up.add(Ip.upolys.get(i + k));
                i++;
            }
            qdec.add(new IdealWithUniv(Is, up));
        }
        return qdec;
    }

    public List<PrimaryComponent<C>> zeroDimPrimaryDecomposition(List<IdealWithUniv<C>> pdec) {
        List<PrimaryComponent<C>> dec = new ArrayList();
        if (!isZERO()) {
            if (isONE()) {
                dec.add(new PrimaryComponent(((IdealWithUniv) pdec.get(0)).ideal, (IdealWithUniv) pdec.get(0)));
            } else {
                for (IdealWithUniv<C> Ip : pdec) {
                    dec.add(new PrimaryComponent(primaryIdeal(Ip.ideal), Ip));
                }
            }
        }
        return dec;
    }

    public boolean isPrimaryDecomposition(List<PrimaryComponent<C>> L) {
        for (PrimaryComponent<C> I : L) {
            if (!I.primary.contains(this)) {
                System.out.println("not contained " + this + " in " + I);
                return false;
            }
        }
        Ideal isec = null;
        for (PrimaryComponent<C> I2 : L) {
            if (isec == null) {
                isec = I2.primary;
            } else {
                isec = isec.intersect(I2.primary);
            }
        }
        return contains(isec);
    }

    public IdealWithUniv<Quotient<C>> extension(String... vars) {
        GenPolynomialRing<C> fac = getRing();
        return extension(new GenPolynomialRing(fac.coFac, vars.length, fac.tord, vars));
    }

    public IdealWithUniv<Quotient<C>> extension(GenPolynomialRing<C> efac) {
        return extension(new QuotientRing(efac));
    }

    public IdealWithUniv<Quotient<C>> extension(QuotientRing<C> qfac) {
        GenPolynomialRing<C> fac = getRing();
        GenPolynomialRing<C> efac = qfac.ring;
        String[] rvars = GroebnerBasePartial.remainingVars(fac.getVars(), efac.getVars());
        OptimizedPolynomialList<C> pgb = new GroebnerBasePartial().elimPartialGB(getList(), rvars, efac.getVars());
        if (logger.isInfoEnabled()) {
            logger.info("rvars = " + Arrays.toString(rvars));
            logger.info("partialGB = " + pgb);
        }
        GenPolynomialRing<GenPolynomial<C>> genPolynomialRing = new GenPolynomialRing(efac, rvars.length, fac.tord, rvars);
        List<GenPolynomial<GenPolynomial<C>>> rpgb = PolyUtil.recursive((GenPolynomialRing) genPolynomialRing, (List) pgb.list);
        GenPolynomialRing<Quotient<C>> genPolynomialRing2 = new GenPolynomialRing((RingFactory) qfac, (GenPolynomialRing) genPolynomialRing);
        List qpgb = PolyUfdUtil.quotientFromIntegralCoefficients((GenPolynomialRing) genPolynomialRing2, (Collection) rpgb);
        GreatestCommonDivisor<C> ufd = GCDFactory.getImplementation(fac.coFac);
        RingElem f = null;
        for (GenPolynomial<GenPolynomial<C>> p : rpgb) {
            if (f == null) {
                f = (GenPolynomial) p.leadingBaseCoefficient();
            } else {
                f = ufd.lcm(f, (GenPolynomial) p.leadingBaseCoefficient());
            }
        }
        GenPolynomial<Quotient<C>> fq = PolyUfdUtil.quotientFromIntegralCoefficients((GenPolynomialRing) genPolynomialRing2, genPolynomialRing.getONE().multiply(f));
        if (logger.isInfoEnabled()) {
            logger.info("extension f = " + f);
            logger.info("ext = " + qpgb);
        }
        List<GenPolynomial<Quotient<C>>> upols = new ArrayList(0);
        List<GenPolynomial<Quotient<C>>> opols = new ArrayList(1);
        opols.add(fq);
        return new IdealWithUniv(new Ideal((GenPolynomialRing) genPolynomialRing2, PolyUtil.monic(qpgb)), upols, opols);
    }

    public IdealWithUniv<C> permContraction(IdealWithUniv<Quotient<C>> eideal) {
        return permutation(getRing(), contraction(eideal));
    }

    public static <C extends GcdRingElem<C>> IdealWithUniv<C> contraction(IdealWithUniv<Quotient<C>> eid) {
        Ideal<Quotient<C>> eideal = eid.ideal;
        List<GenPolynomial<Quotient<C>>> qgb = eideal.getList();
        QuotientRing<C> qfac = (QuotientRing) eideal.getRing().coFac;
        GenPolynomialRing<GenPolynomial<C>> genPolynomialRing = new GenPolynomialRing((RingFactory) qfac.ring, eideal.getRing());
        GenPolynomialRing<C> dfac = qfac.ring.extend(eideal.getRing().getVars());
        TermOrder termOrder = new TermOrder(qfac.ring.tord.getEvord());
        GenPolynomialRing dfac2 = new GenPolynomialRing(dfac.coFac, dfac.nvar, termOrder, dfac.getVars());
        List<GenPolynomial<GenPolynomial<C>>> cgb = PolyUfdUtil.integralFromQuotientCoefficients((GenPolynomialRing) genPolynomialRing, (Collection) qgb);
        Ideal<C> cont = new Ideal(dfac2, PolyUtil.distribute(dfac2, (List) cgb));
        List<GenPolynomial<C>> opols = new ArrayList();
        if (eid.others != null) {
            if (eid.others.size() > 0) {
                opols.addAll(PolyUtil.distribute(dfac2, PolyUfdUtil.integralFromQuotientCoefficients((GenPolynomialRing) genPolynomialRing, (Collection) eid.others)));
            }
        }
        List<GenPolynomial<C>> arrayList = new ArrayList(0);
        int i = 0;
        for (GenPolynomial<Quotient<C>> p : eid.upolys) {
            int i2 = i + 1;
            arrayList.add(PolyUtil.distribute(dfac2, PolyUfdUtil.integralFromQuotientCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) p.extendUnivariate(eideal.getRing(), i))));
            i = i2;
        }
        GreatestCommonDivisor<C> ufd = GCDFactory.getImplementation(qfac.ring.coFac);
        RingElem f = null;
        for (GenPolynomial<GenPolynomial<C>> p2 : cgb) {
            if (f == null) {
                f = (GenPolynomial) p2.leadingBaseCoefficient();
            } else {
                f = ufd.lcm(f, (GenPolynomial) p2.leadingBaseCoefficient());
            }
        }
        GenPolynomial f2 = PolyUtil.distribute(dfac2, genPolynomialRing.getONE().multiply(f));
        if (logger.isInfoEnabled()) {
            logger.info("contraction f = " + f2);
            logger.info("cont = " + cont);
        }
        opols.add(f2);
        if (f2.isONE()) {
            return new IdealWithUniv(cont, arrayList, opols);
        }
        return new IdealWithUniv(cont.infiniteQuotientRab(f2), arrayList, opols);
    }

    public static <C extends GcdRingElem<C>> IdealWithUniv<C> permutation(GenPolynomialRing<C> oring, IdealWithUniv<C> Cont) {
        Ideal<C> cont = Cont.ideal;
        GenPolynomialRing<C> dfac = cont.getRing();
        String[] ovars = oring.getVars();
        String[] dvars = dfac.getVars();
        if (Arrays.equals(ovars, dvars)) {
            return Cont;
        }
        List perm = GroebnerBasePartial.getPermutation(dvars, ovars);
        GenPolynomialRing pfac = TermOrderOptimization.permutation(perm, cont.getRing());
        if (logger.isInfoEnabled()) {
            logger.info("pfac = " + pfac);
        }
        cont = new Ideal(pfac, TermOrderOptimization.permutation(perm, pfac, cont.getList()));
        if (logger.isDebugEnabled()) {
            logger.info("perm cont = " + cont);
        }
        return new IdealWithUniv(cont, TermOrderOptimization.permutation(perm, pfac, Cont.upolys), TermOrderOptimization.permutation(perm, pfac, Cont.others));
    }

    public Ideal<C> radical() {
        List<IdealWithUniv<C>> rdec = radicalDecomposition();
        List dec = new ArrayList(rdec.size());
        for (IdealWithUniv<C> ru : rdec) {
            dec.add(ru.ideal);
        }
        return intersect(dec);
    }

    public List<IdealWithUniv<C>> radicalDecomposition() {
        int z = commonZeroTest();
        List<IdealWithUniv<C>> dec = new ArrayList();
        List<GenPolynomial<C>> ups = new ArrayList();
        if (z < 0) {
            dec.add(new IdealWithUniv(this, ups));
            return dec;
        } else if (z == 0) {
            return zeroDimRadicalDecomposition();
        } else {
            if (isZERO()) {
                return dec;
            }
            if (this.list.ring.coFac.characteristic().signum() > 0) {
                if (!this.list.ring.coFac.isFinite()) {
                    logger.warn("must use prime decomposition for char p and infinite coefficient rings, found " + this.list.ring.coFac.toScript());
                    return primeDecomposition();
                }
            }
            Dimension dim = dimension();
            if (logger.isInfoEnabled()) {
                logger.info("dimension = " + dim);
            }
            Set<Set<Integer>> M = dim.M;
            Set<Integer> min = null;
            for (Set<Integer> m : M) {
                if (min == null) {
                    min = m;
                } else {
                    if (m.size() < min.size()) {
                        min = m;
                    }
                }
            }
            int ms = min.size();
            Integer[] ia = new Integer[0];
            int mx = ((Integer[]) min.toArray(ia))[ms - 1].intValue();
            for (Set<Integer> m2 : M) {
                if (m2.size() == ms) {
                    int mxx = ((Integer[]) m2.toArray(ia))[ms - 1].intValue();
                    if (mxx < mx) {
                        min = m2;
                        mx = mxx;
                    }
                }
            }
            String[] mvars = new String[min.size()];
            int j = 0;
            for (Integer i : min) {
                int j2 = j + 1;
                mvars[j] = dim.v[i.intValue()];
                j = j2;
            }
            if (logger.isInfoEnabled()) {
                logger.info("extension for variables = " + Arrays.toString(mvars) + ", indexes = " + min);
            }
            IdealWithUniv<Quotient<C>> Ext = extension(mvars);
            if (logger.isInfoEnabled()) {
                logger.info("extension = " + Ext);
            }
            List<IdealWithUniv<Quotient<C>>> edec = Ext.ideal.zeroDimRadicalDecomposition();
            if (logger.isInfoEnabled()) {
                logger.info("0-dim radical decomp = " + edec);
            }
            for (IdealWithUniv<Quotient<C>> ep : edec) {
                dec.add(permContraction(ep));
            }
            List<GenPolynomial<C>> ql = permContraction(Ext).others;
            if (ql.size() == 0) {
                return dec;
            }
            GenPolynomial fx = (GenPolynomial) ql.get(0);
            if (fx.isONE()) {
                return dec;
            }
            Ideal<C> T = sum(fx);
            if (T.isONE()) {
                logger.info("1 in ideal for " + fx);
                return dec;
            }
            if (logger.isInfoEnabled()) {
                logger.info("radical decomp ext-cont fx = " + fx);
                logger.info("recursion radical decomp T = " + T);
            }
            List<IdealWithUniv<C>> Tdec = T.radicalDecomposition();
            if (logger.isInfoEnabled()) {
                logger.info("recursion radical decomp = " + Tdec);
            }
            dec.addAll(Tdec);
            return dec;
        }
    }

    public List<IdealWithUniv<C>> decomposition() {
        int z = commonZeroTest();
        List<IdealWithUniv<C>> dec = new ArrayList();
        if (z < 0) {
            return dec;
        }
        if (z == 0) {
            return zeroDimDecomposition();
        }
        if (isZERO()) {
            return dec;
        }
        Dimension dim = dimension();
        if (logger.isInfoEnabled()) {
            logger.info("dimension = " + dim);
        }
        Set<Integer> min = null;
        for (Set<Integer> m : dim.M) {
            if (min == null) {
                min = m;
            } else {
                if (m.size() < min.size()) {
                    min = m;
                }
            }
        }
        String[] mvars = new String[min.size()];
        int j = 0;
        for (Integer i : min) {
            int j2 = j + 1;
            mvars[j] = dim.v[i.intValue()];
            j = j2;
        }
        if (logger.isInfoEnabled()) {
            logger.info("extension for variables = " + Arrays.toString(mvars));
        }
        IdealWithUniv<Quotient<C>> Ext = extension(mvars);
        if (logger.isInfoEnabled()) {
            logger.info("extension = " + Ext);
        }
        List<IdealWithUniv<Quotient<C>>> edec = Ext.ideal.zeroDimDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("0-dim irred decomp = " + edec);
        }
        for (IdealWithUniv<Quotient<C>> ep : edec) {
            dec.add(permContraction(ep));
        }
        List<GenPolynomial<C>> ql = permContraction(Ext).others;
        if (ql.size() == 0) {
            return dec;
        }
        GenPolynomial fx = (GenPolynomial) ql.get(0);
        if (fx.isONE()) {
            return dec;
        }
        Ideal<C> T = sum(fx);
        if (T.isONE()) {
            logger.info("1 in ideal for " + fx);
            return dec;
        }
        if (logger.isInfoEnabled()) {
            logger.info("irred decomp ext-cont fx = " + fx);
            logger.info("recursion irred decomp T = " + T);
        }
        List<IdealWithUniv<C>> Tdec = T.decomposition();
        if (logger.isInfoEnabled()) {
            logger.info("recursion irred decomposition = " + Tdec);
        }
        dec.addAll(Tdec);
        return dec;
    }

    public List<IdealWithUniv<C>> primeDecomposition() {
        int z = commonZeroTest();
        List<IdealWithUniv<C>> dec = new ArrayList();
        if (z < 0) {
            return dec;
        }
        if (z == 0) {
            return zeroDimPrimeDecomposition();
        }
        if (isZERO()) {
            return dec;
        }
        Dimension dim = dimension();
        if (logger.isInfoEnabled()) {
            logger.info("dimension = " + dim);
        }
        Set<Integer> min = null;
        for (Set<Integer> m : dim.M) {
            if (min == null) {
                min = m;
            } else {
                if (m.size() < min.size()) {
                    min = m;
                }
            }
        }
        String[] mvars = new String[min.size()];
        int j = 0;
        for (Integer i : min) {
            int j2 = j + 1;
            mvars[j] = dim.v[i.intValue()];
            j = j2;
        }
        if (logger.isInfoEnabled()) {
            logger.info("extension for variables = " + Arrays.toString(mvars));
        }
        IdealWithUniv<Quotient<C>> Ext = extension(mvars);
        if (logger.isInfoEnabled()) {
            logger.info("extension = " + Ext);
        }
        List<IdealWithUniv<Quotient<C>>> edec = Ext.ideal.zeroDimPrimeDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("0-dim prime decomp = " + edec);
        }
        for (IdealWithUniv<Quotient<C>> ep : edec) {
            dec.add(permContraction(ep));
        }
        List<GenPolynomial<C>> ql = permContraction(Ext).others;
        if (ql.size() == 0) {
            return dec;
        }
        GenPolynomial fx = (GenPolynomial) ql.get(0);
        if (fx.isONE()) {
            return dec;
        }
        Ideal<C> T = sum(fx);
        if (T.isONE()) {
            logger.info("1 in ideal for " + fx);
            return dec;
        }
        if (logger.isInfoEnabled()) {
            logger.info("prime decomp ext-cont fx = " + fx);
            logger.info("recursion prime decomp T = " + T);
        }
        List<IdealWithUniv<C>> Tdec = T.primeDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("recursion prime decomp = " + Tdec);
        }
        dec.addAll(Tdec);
        return dec;
    }

    public boolean isDecomposition(List<IdealWithUniv<C>> L) {
        if (L != null && L.size() != 0) {
            GenPolynomialRing<C> ofac = this.list.ring;
            int d = ((IdealWithUniv) L.get(0)).ideal.list.ring.nvar - ofac.nvar;
            Ideal Id = this;
            if (d > 0) {
                GenPolynomialRing nfac = ofac.extendLower(d);
                List elist = new ArrayList(this.list.list.size());
                for (GenPolynomial<C> p : getList()) {
                    elist.add(p.extendLower(nfac, 0, 0));
                }
                Id = new Ideal(nfac, elist, this.isGB, this.isTopt);
            }
            for (IdealWithUniv<C> I : L) {
                if (!I.ideal.contains(Id)) {
                    System.out.println("not contained " + this + " in " + I.ideal);
                    return false;
                }
            }
            return true;
        } else if (isZERO()) {
            return true;
        } else {
            return false;
        }
    }

    public List<PrimaryComponent<C>> primaryDecomposition() {
        int z = commonZeroTest();
        List<PrimaryComponent<C>> dec = new ArrayList();
        if (z < 0) {
            return dec;
        }
        if (z == 0) {
            return zeroDimPrimaryDecomposition();
        }
        if (isZERO()) {
            return dec;
        }
        Dimension dim = dimension();
        if (logger.isInfoEnabled()) {
            logger.info("dimension = " + dim);
        }
        Set<Integer> min = null;
        for (Set<Integer> m : dim.M) {
            if (min == null) {
                min = m;
            } else {
                if (m.size() < min.size()) {
                    min = m;
                }
            }
        }
        String[] mvars = new String[min.size()];
        int j = 0;
        for (Integer i : min) {
            int j2 = j + 1;
            mvars[j] = dim.v[i.intValue()];
            j = j2;
        }
        if (logger.isInfoEnabled()) {
            logger.info("extension for variables = " + Arrays.toString(mvars));
        }
        IdealWithUniv<Quotient<C>> Ext = extension(mvars);
        if (logger.isInfoEnabled()) {
            logger.info("extension = " + Ext);
        }
        List<PrimaryComponent<Quotient<C>>> edec = Ext.ideal.zeroDimPrimaryDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("0-dim primary decomp = " + edec);
        }
        List<GenPolynomial<Quotient<C>>> upq = new ArrayList();
        for (PrimaryComponent<Quotient<C>> ep : edec) {
            IdealWithUniv<C> contq = permContraction(new IdealWithUniv(ep.primary, upq));
            IdealWithUniv<C> contp = permContraction(ep.prime);
            dec.add(new PrimaryComponent(contq.ideal, contp));
        }
        IdealWithUniv<C> extcont = permContraction(Ext);
        if (this.debug) {
            logger.info("cont(Ext) = " + extcont);
        }
        List<GenPolynomial<C>> ql = extcont.others;
        if (ql.size() == 0) {
            return dec;
        }
        GenPolynomial fx = (GenPolynomial) ql.get(0);
        if (fx.isONE()) {
            return dec;
        }
        int s = infiniteQuotientExponent(fx, extcont.ideal);
        if (s == 0) {
            logger.info("exponent is 0 ");
            return dec;
        }
        if (s > 1) {
            fx = (GenPolynomial) Power.positivePower((RingElem) fx, (long) s);
        }
        if (this.debug) {
            logger.info("exponent fx = " + s + ", fx^s = " + fx);
        }
        Ideal<C> T = sum(fx);
        if (T.isONE()) {
            logger.info("1 in ideal for " + fx);
            return dec;
        }
        if (logger.isInfoEnabled()) {
            logger.info("primmary decomp ext-cont fx = " + fx);
            logger.info("recursion primary decomp T = " + T);
        }
        List<PrimaryComponent<C>> Tdec = T.primaryDecomposition();
        if (logger.isInfoEnabled()) {
            logger.info("recursion primary decomp = " + Tdec);
        }
        dec.addAll(Tdec);
        return dec;
    }
}
