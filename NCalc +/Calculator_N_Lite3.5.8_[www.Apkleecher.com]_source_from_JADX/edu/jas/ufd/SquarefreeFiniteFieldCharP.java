package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class SquarefreeFiniteFieldCharP<C extends GcdRingElem<C>> extends SquarefreeFieldCharP<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(SquarefreeFiniteFieldCharP.class);
    }

    public SquarefreeFiniteFieldCharP(RingFactory<C> fac) {
        super(fac);
        if (!fac.isFinite()) {
            throw new IllegalArgumentException("fac must be finite");
        }
    }

    public SortedMap<C, Long> rootCharacteristic(C p) {
        if (p == null) {
            throw new IllegalArgumentException(getClass().getName() + " p == null");
        }
        SortedMap<C, Long> root = new TreeMap();
        if (!p.isZERO()) {
            root.put(p, Long.valueOf(1));
        }
        return root;
    }

    public C coeffRootCharacteristic(C c) {
        if (c == null || c.isZERO()) {
            return c;
        }
        RingElem r = c;
        if (this.aCoFac == null && this.qCoFac == null) {
            return r;
        }
        if (this.aCoFac != null) {
            long d = this.aCoFac.totalExtensionDegree();
            if (d > 1) {
                return (GcdRingElem) Power.positivePower(r, ((BigInteger) Power.positivePower(new BigInteger(this.aCoFac.characteristic()), d - 1)).getVal());
            }
            return r;
        } else if (this.qCoFac == null) {
            return r;
        } else {
            throw new UnsupportedOperationException("case QuotientRing not yet implemented");
        }
    }

    public SortedMap<GenPolynomial<C>, Long> rootCharacteristic(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        java.math.BigInteger c = P.ring.characteristic();
        if (c.signum() == 0) {
            return null;
        }
        SortedMap<GenPolynomial<C>, Long> root = new TreeMap();
        if (P.isZERO()) {
            return root;
        }
        if (P.isONE()) {
            root.put(P, Long.valueOf(1));
            return root;
        }
        Long e;
        SortedMap<GenPolynomial<C>, Long> sf = squarefreeFactors((GenPolynomial) P);
        if (logger.isInfoEnabled()) {
            logger.info("sf = " + sf);
        }
        Long k = null;
        for (Entry<GenPolynomial<C>, Long> me : sf.entrySet()) {
            if (!((GenPolynomial) me.getKey()).isConstant()) {
                e = (Long) me.getValue();
                if (!new java.math.BigInteger(e.toString()).remainder(c).equals(java.math.BigInteger.ZERO)) {
                    return null;
                }
                if (k == null) {
                    k = e;
                } else if (k.compareTo(e) >= 0) {
                    k = e;
                }
            }
        }
        Long cl = Long.valueOf(c.longValue());
        GenPolynomial<C> rp = P.ring.getONE();
        for (Entry<GenPolynomial<C>, Long> me2 : sf.entrySet()) {
            GenPolynomial q = (GenPolynomial) me2.getKey();
            e = (Long) me2.getValue();
            if (q.isConstant()) {
                C qc = (GcdRingElem) q.leadingBaseCoefficient();
                if (e.longValue() > 1) {
                    qc = (GcdRingElem) Power.positivePower((RingElem) qc, e.longValue());
                }
                C qr = coeffRootCharacteristic(qc);
                root.put(P.ring.getONE().multiply((RingElem) qr), Long.valueOf(1));
            } else {
                if (e.longValue() > k.longValue()) {
                    q = (GenPolynomial) Power.positivePower((RingElem) q, e.longValue() / cl.longValue());
                }
                rp = rp.multiply(q);
            }
        }
        if (k == null) {
            return root;
        }
        root.put(rp, Long.valueOf(k.longValue() / cl.longValue()));
        return root;
    }

    public GenPolynomial<C> baseRootCharacteristic(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<C> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<C> d = pfac.getZERO().copy();
        Iterator i$ = P.iterator();
        while (i$.hasNext()) {
            Monomial<C> m = (Monomial) i$.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            d.doPutToMap(ExpVector.create(1, 0, fl / mp), coeffRootCharacteristic((GcdRingElem) m.c));
        }
        return d;
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateRootCharacteristic(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<GenPolynomial<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<GenPolynomial<C>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<GenPolynomial<C>> d = pfac.getZERO().copy();
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<GenPolynomial<C>> m = (Monomial) it.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            SortedMap<GenPolynomial<C>, Long> sm = rootCharacteristic((GenPolynomial) m.c);
            if (sm == null) {
                return null;
            }
            if (logger.isInfoEnabled()) {
                logger.info("sm,rec = " + sm);
            }
            GenPolynomial<C> r = (GenPolynomial) rf.getONE();
            for (Entry<GenPolynomial<C>, Long> me : sm.entrySet()) {
                GenPolynomial<C> rp = (GenPolynomial) me.getKey();
                long gl = ((Long) me.getValue()).longValue();
                if (gl > 1) {
                    rp = (GenPolynomial) Power.positivePower((RingElem) rp, gl);
                }
                r = r.multiply((GenPolynomial) rp);
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        return d;
    }
}
