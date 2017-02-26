package edu.jas.ufd;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.Monomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.math.BigInteger;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class SquarefreeInfiniteFieldCharP<C extends GcdRingElem<C>> extends SquarefreeFieldCharP<Quotient<C>> {
    private static final Logger logger;
    protected final SquarefreeAbstract<C> qengine;

    static {
        logger = Logger.getLogger(SquarefreeInfiniteFieldCharP.class);
    }

    public SquarefreeInfiniteFieldCharP(RingFactory<Quotient<C>> fac) {
        super(fac);
        if (fac.isFinite()) {
            throw new IllegalArgumentException("fac must be in-finite");
        }
        this.qengine = SquarefreeFactory.getImplementation(((QuotientRing) fac).ring);
    }

    public SortedMap<Quotient<C>, Long> squarefreeFactors(Quotient<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        SortedMap<Quotient<C>, Long> factors = new TreeMap();
        if (!P.isZERO()) {
            if (P.isONE()) {
                factors.put(P, Long.valueOf(1));
            } else {
                GenPolynomial num = P.num;
                GenPolynomial den = P.den;
                QuotientRing<C> pfac = P.ring;
                GenPolynomial<C> one = pfac.ring.getONE();
                if (!num.isONE()) {
                    for (Entry<GenPolynomial<C>, Long> me : this.qengine.squarefreeFactors(num).entrySet()) {
                        factors.put(new Quotient(pfac, (GenPolynomial) me.getKey()), me.getValue());
                    }
                }
                if (!den.isONE()) {
                    for (Entry<GenPolynomial<C>, Long> me2 : this.qengine.squarefreeFactors(den).entrySet()) {
                        factors.put(new Quotient(pfac, one, (GenPolynomial) me2.getKey()), me2.getValue());
                    }
                    if (factors.size() == 0) {
                        factors.put(P, Long.valueOf(1));
                    }
                } else if (factors.size() == 0) {
                    factors.put(P, Long.valueOf(1));
                }
            }
        }
        return factors;
    }

    public SortedMap<Quotient<C>, Long> rootCharacteristic(Quotient<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P == null");
        }
        BigInteger c = P.ring.characteristic();
        if (c.signum() == 0) {
            return null;
        }
        SortedMap<Quotient<C>, Long> root = new TreeMap();
        if (P.isZERO()) {
            return root;
        }
        if (P.isONE()) {
            root.put(P, Long.valueOf(1));
            return root;
        }
        SortedMap<Quotient<C>, Long> sf = squarefreeFactors((Quotient) P);
        if (sf.size() == 0) {
            return null;
        }
        if (logger.isInfoEnabled()) {
            logger.info("sf,quot = " + sf);
        }
        Long k = null;
        Long cl = Long.valueOf(c.longValue());
        for (Entry<Quotient<C>, Long> me : sf.entrySet()) {
            Long e;
            if (!((Quotient) me.getKey()).isConstant()) {
                e = (Long) me.getValue();
                if (e.longValue() % cl.longValue() != 0) {
                    return null;
                }
                if (k == null) {
                    k = e;
                } else if (k.longValue() >= e.longValue()) {
                    k = e;
                }
            }
        }
        if (k == null) {
            k = Long.valueOf(1);
        }
        for (Entry<Quotient<C>, Long> me2 : sf.entrySet()) {
            Quotient<C> q = (Quotient) me2.getKey();
            e = (Long) me2.getValue();
            if (e.longValue() >= k.longValue()) {
                root.put(q, Long.valueOf(e.longValue() / cl.longValue()));
            } else {
                root.put(q, e);
            }
        }
        return root;
    }

    public GenPolynomial<Quotient<C>> rootCharacteristic(GenPolynomial<Quotient<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<Quotient<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            GenPolynomial Prc = recursiveUnivariateRootCharacteristic(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P));
            if (Prc == null) {
                return null;
            }
            return PolyUtil.distribute((GenPolynomialRing) pfac, Prc);
        }
        RingFactory<Quotient<C>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for ModInteger polynomials " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<Quotient<C>> d = pfac.getZERO().copy();
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<Quotient<C>> m = (Monomial) it.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            SortedMap<Quotient<C>, Long> sm = rootCharacteristic((Quotient) m.c);
            if (sm == null) {
                return null;
            }
            if (logger.isInfoEnabled()) {
                logger.info("sm,root = " + sm);
            }
            Quotient<C> r = (Quotient) rf.getONE();
            for (Entry<Quotient<C>, Long> me : sm.entrySet()) {
                Quotient<C> rp = (Quotient) me.getKey();
                long gl = ((Long) me.getValue()).longValue();
                if (gl > 1) {
                    rp = (Quotient) Power.positivePower((RingElem) rp, gl);
                }
                r = r.multiply((Quotient) rp);
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        logger.info("sm,root,d = " + d);
        return d;
    }

    public GenPolynomial<Quotient<C>> baseRootCharacteristic(GenPolynomial<Quotient<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<Quotient<C>> pfac = P.ring;
        int i = pfac.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate polynomials");
        }
        RingFactory<Quotient<C>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<Quotient<C>> d = pfac.getZERO().copy();
        Iterator it = P.iterator();
        while (it.hasNext()) {
            Monomial<Quotient<C>> m = (Monomial) it.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            SortedMap<Quotient<C>, Long> sm = rootCharacteristic((Quotient) m.c);
            if (sm == null) {
                return null;
            }
            if (logger.isInfoEnabled()) {
                logger.info("sm,base,root = " + sm);
            }
            Quotient<C> r = (Quotient) rf.getONE();
            for (Entry<Quotient<C>, Long> me : sm.entrySet()) {
                Quotient<C> rp = (Quotient) me.getKey();
                long gl = ((Long) me.getValue()).longValue();
                Quotient re = rp;
                if (gl > 1) {
                    re = (Quotient) Power.positivePower((RingElem) rp, gl);
                }
                r = r.multiply(re);
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        if (!logger.isInfoEnabled()) {
            return d;
        }
        logger.info("sm,base,d = " + d);
        return d;
    }

    public GenPolynomial<GenPolynomial<Quotient<C>>> recursiveUnivariateRootCharacteristic(GenPolynomial<GenPolynomial<Quotient<C>>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<GenPolynomial<Quotient<C>>> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for univariate recursive polynomials");
        }
        RingFactory<GenPolynomial<Quotient<C>>> rf = pfac.coFac;
        if (rf.characteristic().signum() != 1) {
            throw new IllegalArgumentException(P.getClass().getName() + " only for char p > 0 " + rf);
        }
        long mp = rf.characteristic().longValue();
        GenPolynomial<GenPolynomial<Quotient<C>>> d = pfac.getZERO().copy();
        Iterator i$ = P.iterator();
        while (i$.hasNext()) {
            Monomial<GenPolynomial<Quotient<C>>> m = (Monomial) i$.next();
            long fl = m.e.getVal(0);
            if (fl % mp != 0) {
                return null;
            }
            fl /= mp;
            GenPolynomial<Quotient<C>> r = rootCharacteristic((GenPolynomial) m.c);
            if (r == null) {
                return null;
            }
            d.doPutToMap(ExpVector.create(1, 0, fl), r);
        }
        return d;
    }
}
