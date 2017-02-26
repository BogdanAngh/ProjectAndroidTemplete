package edu.jas.application;

import edu.jas.application.Condition.Color;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.commons.math4.random.ValueServer;
import org.apache.log4j.Logger;
import org.matheclipse.core.interfaces.IExpr;

public class CReductionSeq<C extends GcdRingElem<C>> implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final Logger logger;
    protected final RingFactory<C> cofac;
    protected final GreatestCommonDivisor<C> engine;
    private final boolean info;
    protected boolean top;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$edu$jas$application$Condition$Color;

        static {
            $SwitchMap$edu$jas$application$Condition$Color = new int[Color.values().length];
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.GREEN.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.RED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$edu$jas$application$Condition$Color[Color.WHITE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    static {
        $assertionsDisabled = !CReductionSeq.class.desiredAssertionStatus();
        logger = Logger.getLogger(CReductionSeq.class);
    }

    public CReductionSeq(RingFactory<C> rf) {
        this.info = logger.isInfoEnabled();
        this.top = true;
        this.cofac = rf;
        this.engine = GCDFactory.getImplementation(this.cofac);
    }

    public ColorPolynomial<C> SPolynomial(ColorPolynomial<C> Ap, ColorPolynomial<C> Bp) {
        if (Bp == null || Bp.isZERO()) {
            return Bp;
        }
        if (Ap == null || Ap.isZERO()) {
            return Ap;
        }
        Entry<ExpVector, GenPolynomial<C>> ma = Ap.red.leadingMonomial();
        Entry<ExpVector, GenPolynomial<C>> mb = Bp.red.leadingMonomial();
        ExpVector e = (ExpVector) ma.getKey();
        ExpVector f = (ExpVector) mb.getKey();
        ExpVector g = e.lcm(f);
        ExpVector e1 = g.subtract(e);
        ExpVector f1 = g.subtract(f);
        GenPolynomial<C> a = (GenPolynomial) ma.getValue();
        GenPolynomial<C> b = (GenPolynomial) mb.getValue();
        GenPolynomial c = this.engine.gcd(a, b);
        if (!c.isONE()) {
            a = a.divide(c);
            b = b.divide(c);
        }
        ColorPolynomial<C> Cp = Ap.multiply(b, e1).subtract(Bp.multiply(a, f1));
        if ($assertionsDisabled || !g.equals(Cp.getEssentialPolynomial().leadingExpVector())) {
            return Cp;
        }
        throw new AssertionError("g == lt(Cp)");
    }

    public boolean isTopReducible(List<ColorPolynomial<C>> P, ColorPolynomial<C> A) {
        if (P == null || P.isEmpty() || A == null || A.isZERO()) {
            return false;
        }
        ExpVector e = A.leadingExpVector();
        for (ColorPolynomial<C> p : P) {
            if (p == null) {
                return false;
            }
            ExpVector f = p.leadingExpVector();
            if (f == null || e == null) {
                return false;
            }
            if (e.multipleOf(f)) {
                return true;
            }
        }
        return false;
    }

    public boolean isReducible(List<ColorPolynomial<C>> Pp, ColorPolynomial<C> Ap) {
        return !isNormalform(Pp, Ap);
    }

    public boolean isNormalform(List<ColorPolynomial<C>> Pp, ColorPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        if (Ap == null || Ap.isZERO()) {
            return true;
        }
        int l;
        int i;
        synchronized (Pp) {
            l = Pp.size();
            ColorPolynomial<C>[] P = new ColorPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (ColorPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        ColorPolynomial<C>[] p = new ColorPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, GenPolynomial<C>> m = p[i].red.leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                j++;
            }
        }
        l = j;
        for (ExpVector e : Ap.red.getMap().keySet()) {
            for (i = 0; i < l; i++) {
                if (e.multipleOf(htl[i])) {
                    System.out.println("not normalform " + Ap + ", P[i] = " + P[i]);
                    return false;
                }
            }
            if (this.top) {
                return true;
            }
        }
        for (ExpVector e2 : Ap.white.getMap().keySet()) {
            for (i = 0; i < l; i++) {
                if (e2.multipleOf(htl[i])) {
                    System.out.println("not normalform " + Ap + ", P[i] = " + P[i]);
                    return false;
                }
            }
            if (this.top) {
                return true;
            }
        }
        return true;
    }

    public boolean isNormalform(List<ColorPolynomial<C>> Pp) {
        if (Pp == null || Pp.isEmpty()) {
            return true;
        }
        List<ColorPolynomial<C>> P = new LinkedList(Pp);
        int s = P.size();
        for (int i = 0; i < s; i++) {
            ColorPolynomial<C> Ap = (ColorPolynomial) P.remove(i);
            if (!isNormalform(P, Ap)) {
                return false;
            }
            P.add(Ap);
        }
        return true;
    }

    public ColorPolynomial<C> normalform(Condition<C> cond, List<ColorPolynomial<C>> Pp, ColorPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int l;
        int i;
        Entry<ExpVector, GenPolynomial<C>> m;
        synchronized (Pp) {
            l = Pp.size();
            ColorPolynomial<C>[] P = new ColorPolynomial[l];
            for (i = 0; i < Pp.size(); i++) {
                P[i] = (ColorPolynomial) Pp.get(i);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        Object[] lbc = new Object[l];
        ColorPolynomial<C>[] p = new ColorPolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].red.leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenPolynomial<GenPolynomial<C>> zero = p[0].red.ring.getZERO();
        ColorPolynomial<C> R = new ColorPolynomial(zero, zero, zero);
        ColorPolynomial<C> S = Ap;
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            GenPolynomial<C> a = (GenPolynomial) m.getValue();
            if (cond.color(a) == Color.GREEN) {
                GenPolynomial<GenPolynomial<C>> g = S.green.sum(a, e);
                GenPolynomial<GenPolynomial<C>> r = S.red;
                GenPolynomial<GenPolynomial<C>> w = S.white;
                if (S.red.isZERO()) {
                    w = w.subtract(a, e);
                } else {
                    logger.info("green_red = " + zero.sum(a, e));
                    r = r.subtract(a, e);
                }
                S = new ColorPolynomial(g, r, w);
            } else {
                i = 0;
                while (i < l) {
                    mt = e.multipleOf(htl[i]);
                    if (mt) {
                        break;
                    }
                    i++;
                }
                if (mt) {
                    ExpVector f = e;
                    e = e.subtract(htl[i]);
                    GenPolynomial<C> c = lbc[i];
                    GenPolynomial g2 = this.engine.gcd(a, c);
                    if (!g2.isONE()) {
                        a = a.divide(g2);
                        c = c.divide(g2);
                    }
                    S = S.multiply(c);
                    R = R.multiply(c);
                    S = S.subtract(p[i].multiply(a, e));
                    if ($assertionsDisabled) {
                        continue;
                    } else {
                        if (f.equals(S.getEssentialPolynomial().leadingExpVector())) {
                            throw new AssertionError("f == lt(S)");
                        }
                    }
                } else if (this.top) {
                    return S;
                } else {
                    R = R.sum(a, e);
                    S = S.subtract(a, e);
                }
            }
        }
        return R;
    }

    public List<Condition<C>> caseDistinction(List<GenPolynomial<GenPolynomial<C>>> L) {
        List<Condition<C>> cd = new ArrayList();
        if (L == null || L.size() == 0) {
            return cd;
        }
        for (GenPolynomial A : L) {
            if (!(A == null || A.isZERO())) {
                cd = caseDistinction((List) cd, A);
            }
        }
        return cd;
    }

    public List<Condition<C>> caseDistinction(List<Condition<C>> cd, GenPolynomial<GenPolynomial<C>> A) {
        if (A == null || A.isZERO()) {
            return cd;
        }
        if (cd == null) {
            cd = new ArrayList();
        }
        if (cd.size() == 0) {
            List<Condition<C>> list = cd;
            list.add(new Condition((GenPolynomialRing) A.ring.coFac));
        }
        List<Condition<C>> C = new ArrayList();
        for (Condition<C> cz : cd) {
            GenPolynomial<GenPolynomial<C>> Ap = A;
            while (!Ap.isZERO()) {
                Condition<C> cz2;
                GenPolynomial<C> c = (GenPolynomial) Ap.leadingBaseCoefficient();
                GenPolynomial<GenPolynomial<C>> Bp = Ap.reductum();
                switch (1.$SwitchMap$edu$jas$application$Condition$Color[cz2.color(c).ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        Ap = Bp;
                        break;
                    case IExpr.DOUBLEID /*2*/:
                        C.add(cz2);
                        Ap = A.ring.getZERO();
                        break;
                    default:
                        Condition<C> nc = cz2.extendNonZero(c);
                        if (nc != null) {
                            if (cz2.equals(nc)) {
                                cz2 = null;
                                Ap = A.ring.getZERO();
                                break;
                            }
                            C.add(nc);
                        }
                        Condition<C> ez = cz2.extendZero(c);
                        if (ez == null) {
                            cz2 = null;
                            Ap = A.ring.getZERO();
                            break;
                        }
                        cz2 = ez;
                        Ap = Bp;
                        break;
                }
            }
            if (!(cz2 == null || cz2.isContradictory() || C.contains(cz2))) {
                C.add(cz2);
            }
        }
        return C;
    }

    public List<Condition<C>> caseDistinction(Condition<C> cond, GenPolynomial<GenPolynomial<C>> A) {
        List cd = new ArrayList();
        if (A == null || A.isZERO()) {
            return cd;
        }
        cd.add(cond);
        List<Condition<C>> cd2 = caseDistinction(cd, (GenPolynomial) A);
        if (this.info) {
            StringBuffer s = new StringBuffer("extending condition: " + cond + "\n");
            s.append("case distinctions: [ \n");
            for (Condition<C> c : cd2) {
                s.append(c.toString() + "\n");
            }
            s.append("]");
            logger.info(s.toString());
        }
        return cd2;
    }

    public List<ColoredSystem<C>> determine(List<GenPolynomial<GenPolynomial<C>>> H) {
        if (H == null || H.size() == 0) {
            return new ArrayList();
        }
        Collections.reverse(H);
        return determine(caseDistinction(H), H);
    }

    public List<ColoredSystem<C>> determine(List<Condition<C>> cd, List<GenPolynomial<GenPolynomial<C>>> H) {
        List<ColoredSystem<C>> CS = new ArrayList();
        if (!(H == null || H.size() == 0)) {
            for (Condition<C> cond : cd) {
                logger.info("determine wrt cond = " + cond);
                if (cond.zero.isONE()) {
                    System.out.println("ideal is one = " + cond.zero);
                }
                CS.add(new ColoredSystem(cond, cond.determine((List) H)));
            }
        }
        return CS;
    }
}
