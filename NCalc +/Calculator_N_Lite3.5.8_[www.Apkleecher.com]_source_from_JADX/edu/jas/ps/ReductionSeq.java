package edu.jas.ps;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class ReductionSeq<C extends RingElem<C>> {
    private static final Logger logger;
    private final boolean debug;

    class 1 extends MultiVarCoefficients<C> {
        1(MultiVarPowerSeriesRing x0) {
            super(x0);
        }

        public C generate(ExpVector i) {
            return (RingElem) this.pfac.coFac.getZERO();
        }
    }

    static {
        logger = Logger.getLogger(ReductionSeq.class);
    }

    public ReductionSeq() {
        this.debug = logger.isDebugEnabled();
    }

    public boolean moduleCriterion(int modv, MultiVarPowerSeries<C> A, MultiVarPowerSeries<C> B) {
        if (modv == 0) {
            return true;
        }
        return moduleCriterion(modv, A.orderExpVector(), B.orderExpVector());
    }

    public boolean moduleCriterion(int modv, ExpVector ei, ExpVector ej) {
        if (modv == 0 || ei.invLexCompareTo(ej, 0, modv) == 0) {
            return true;
        }
        return false;
    }

    public boolean criterion4(MultiVarPowerSeries<C> A, MultiVarPowerSeries<C> B, ExpVector e) {
        if (logger.isInfoEnabled()) {
            if (!A.ring.equals(B.ring)) {
                logger.error("rings not equal " + A.ring + ", " + B.ring);
            }
            if (!A.ring.isCommutative()) {
                logger.error("GBCriterion4 not applicabable to non-commutative power series");
                return true;
            }
        }
        if (A.orderExpVector().sum(B.orderExpVector()).subtract(e).signum() == 0) {
            return false;
        }
        return true;
    }

    public MultiVarPowerSeries<C> SPolynomial(MultiVarPowerSeries<C> A, MultiVarPowerSeries<C> B) {
        if (B == null || B.isZERO()) {
            if (A == null) {
                return B;
            }
            return A.ring.getZERO();
        } else if (A == null || A.isZERO()) {
            return B.ring.getZERO();
        } else {
            if (this.debug && !A.ring.equals(B.ring)) {
                logger.error("rings not equal " + A.ring + ", " + B.ring);
            }
            Entry<ExpVector, C> ma = A.orderMonomial();
            Entry<ExpVector, C> mb = B.orderMonomial();
            ExpVector e = (ExpVector) ma.getKey();
            ExpVector f = (ExpVector) mb.getKey();
            ExpVector g = e.lcm(f);
            return A.multiply((RingElem) mb.getValue(), g.subtract(e)).subtract(B.multiply((RingElem) ma.getValue(), g.subtract(f)));
        }
    }

    public MultiVarPowerSeries<C> normalform(List<MultiVarPowerSeries<C>> Pp, MultiVarPowerSeries<C> Ap) {
        if (Pp == null || Pp.isEmpty()) {
            return Ap;
        }
        if (Ap == null || Ap.isZERO()) {
            return Ap;
        }
        if (Ap.ring.coFac.isField()) {
            int i;
            Entry<ExpVector, C> m;
            List<MultiVarPowerSeries<C>> P = new ArrayList(Pp.size());
            synchronized (Pp) {
                P.addAll(Pp);
            }
            ArrayList<ExpVector> htl = new ArrayList(P.size());
            ArrayList<C> lbc = new ArrayList(P.size());
            ArrayList<MultiVarPowerSeries<C>> p = new ArrayList(P.size());
            ArrayList<Long> ecart = new ArrayList(P.size());
            for (i = 0; i < P.size(); i++) {
                m = ((MultiVarPowerSeries) P.get(i)).orderMonomial();
                if (m != null) {
                    p.add(P.get(i));
                    htl.add(m.getKey());
                    lbc.add(m.getValue());
                    ecart.add(Long.valueOf(((MultiVarPowerSeries) P.get(i)).ecart()));
                }
            }
            MultiVarPowerSeries<C> S = Ap;
            m = S.orderMonomial();
            while (m != null && !S.isZERO()) {
                ExpVector e = (ExpVector) m.getKey();
                if (this.debug) {
                    logger.debug("e = " + e.toString(Ap.ring.vars));
                }
                List<Integer> li = new ArrayList();
                i = 0;
                while (i < htl.size()) {
                    if (e.multipleOf((ExpVector) htl.get(i))) {
                        li.add(Integer.valueOf(i));
                    }
                    i++;
                }
                if (li.isEmpty()) {
                    return S;
                }
                long mi = Long.MAX_VALUE;
                for (int k = 0; k < li.size(); k++) {
                    int ki = ((Integer) li.get(k)).intValue();
                    long x = ((Long) ecart.get(ki)).longValue();
                    if (x < mi) {
                        mi = x;
                        i = ki;
                    }
                }
                long si = S.ecart();
                if (mi > si) {
                    p.add(S);
                    htl.add(m.getKey());
                    lbc.add(m.getValue());
                    ecart.add(Long.valueOf(si));
                }
                RingElem a = (RingElem) ((RingElem) m.getValue()).divide((MonoidElem) lbc.get(i));
                MultiVarPowerSeries multiVarPowerSeries = (MultiVarPowerSeries) p.get(i);
                S = S.subtract(r22.multiply(a, e.subtract((ExpVector) htl.get(i))));
                m = S.orderMonomial();
            }
            return S;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }

    public MultiVarPowerSeries<C> totalNormalform(List<MultiVarPowerSeries<C>> P, MultiVarPowerSeries<C> A) {
        if (P == null || P.isEmpty() || A == null) {
            return A;
        }
        MultiVarPowerSeries<C> R = normalform(P, A);
        if (R.isZERO()) {
            return R;
        }
        MultiVarCoefficients Rc = new 1(A.ring);
        GenPolynomialRing<C> pfac = A.lazyCoeffs.pfac;
        while (!R.isZERO()) {
            Entry<ExpVector, C> m = R.orderMonomial();
            if (m == null) {
                break;
            }
            R = R.reductum();
            ExpVector e = (ExpVector) m.getKey();
            long t = e.totalDeg();
            GenPolynomial<C> p = (GenPolynomial) Rc.coeffCache.get(Long.valueOf(t));
            if (p == null) {
                p = pfac.getZERO();
            }
            Rc.coeffCache.put(Long.valueOf(t), p.sum((RingElem) m.getValue(), e));
            R = normalform(P, R);
        }
        return R.sum(Rc);
    }

    public List<MultiVarPowerSeries<C>> totalNormalform(List<MultiVarPowerSeries<C>> P) {
        if (P == null || P.isEmpty()) {
            return P;
        }
        List<MultiVarPowerSeries<C>> R = new ArrayList(P.size());
        List<MultiVarPowerSeries<C>> S = new ArrayList(P);
        for (MultiVarPowerSeries<C> a : P) {
            Entry m = a.orderMonomial();
            if (m != null) {
                MultiVarPowerSeries<C> b = normalform(S, a.reductum()).sum(m);
                if (!b.isZERO()) {
                    R.add(b);
                }
            }
        }
        return R;
    }

    public boolean isTopReducible(List<MultiVarPowerSeries<C>> P, MultiVarPowerSeries<C> A) {
        if (P == null || P.isEmpty() || A == null) {
            return false;
        }
        ExpVector e = A.orderExpVector();
        if (e == null) {
            return false;
        }
        for (MultiVarPowerSeries<C> p : P) {
            ExpVector ep = p.orderExpVector();
            if (ep != null && e.multipleOf(ep)) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(List<MultiVarPowerSeries<C>> S, List<MultiVarPowerSeries<C>> B) {
        if (B == null || B.size() == 0 || S == null || S.size() == 0) {
            return true;
        }
        for (MultiVarPowerSeries<C> b : B) {
            if (b != null) {
                MultiVarPowerSeries<C> z = normalform(S, b);
                if (!z.isZERO()) {
                    System.out.println("contains nf(b) != 0: " + b + ", z = " + z);
                    return false;
                }
            }
        }
        return true;
    }
}
