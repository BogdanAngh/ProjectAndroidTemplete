package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorSubres<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;
    private boolean debug;

    public GreatestCommonDivisorSubres() {
        this.debug = logger.isDebugEnabled();
    }

    static {
        logger = Logger.getLogger(GreatestCommonDivisorSubres.class);
    }

    @Deprecated
    public GenPolynomial<C> basePseudoRemainder(GenPolynomial<C> P, GenPolynomial<C> S) {
        return PolyUtil.baseDensePseudoRemainder(P, S);
    }

    @Deprecated
    public GenPolynomial<GenPolynomial<C>> recursivePseudoRemainder(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        return PolyUtil.recursiveDensePseudoRemainder(P, S);
    }

    public GenPolynomial<C> baseGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial<C> r;
        GenPolynomial<C> q;
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            r = P;
            q = S;
            long g = f;
            f = e;
            e = g;
        } else {
            q = P;
            r = S;
        }
        if (this.debug) {
            logger.debug("degrees: e = " + e + ", f = " + f);
        }
        r = r.abs();
        q = q.abs();
        C a = baseContent(r);
        C b = baseContent(q);
        RingElem c = gcd((GcdRingElem) a, (GcdRingElem) b);
        r = divide(r, a);
        q = divide(q, b);
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        C g2 = (GcdRingElem) r.ring.getONECoefficient();
        C h = (GcdRingElem) r.ring.getONECoefficient();
        while (!r.isZERO()) {
            long delta = q.degree(0) - r.degree(0);
            GenPolynomial<C> x = PolyUtil.baseDensePseudoRemainder(q, r);
            q = r;
            if (x.isZERO()) {
                r = x;
            } else {
                r = x.divide((GcdRingElem) g2.multiply(power(P.ring.coFac, (GcdRingElem) h, delta)));
                g2 = (GcdRingElem) q.leadingBaseCoefficient();
                h = (GcdRingElem) power(P.ring.coFac, (GcdRingElem) g2, delta).divide(power(P.ring.coFac, (GcdRingElem) h, delta - 1));
            }
        }
        return basePrimitivePart((GenPolynomial) q).multiply(c).abs();
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial<GenPolynomial<C>> r;
        GenPolynomial<GenPolynomial<C>> q;
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            r = P;
            q = S;
            long g = f;
            f = e;
            e = g;
        } else {
            q = P;
            r = S;
        }
        if (this.debug) {
            logger.debug("degrees: e = " + e + ", f = " + f);
        }
        r = r.abs();
        q = q.abs();
        GenPolynomial a = recursiveContent(r);
        GenPolynomial b = recursiveContent(q);
        RingElem c = gcd(a, b);
        r = PolyUtil.recursiveDivide((GenPolynomial) r, a);
        q = PolyUtil.recursiveDivide((GenPolynomial) q, b);
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        GenPolynomial<C> g2 = (GenPolynomial) r.ring.getONECoefficient();
        GenPolynomial<C> h = (GenPolynomial) r.ring.getONECoefficient();
        while (!r.isZERO()) {
            long delta = q.degree(0) - r.degree(0);
            GenPolynomial<GenPolynomial<C>> x = PolyUtil.recursiveDensePseudoRemainder(q, r);
            q = r;
            if (x.isZERO()) {
                r = x;
            } else {
                r = PolyUtil.recursiveDivide((GenPolynomial) x, g2.multiply(power(P.ring.coFac, (GenPolynomial) h, delta)));
                g2 = (GenPolynomial) q.leadingBaseCoefficient();
                h = PolyUtil.basePseudoDivide(power(P.ring.coFac, (GenPolynomial) g2, delta), power(P.ring.coFac, (GenPolynomial) h, delta - 1));
            }
        }
        return recursivePrimitivePart((GenPolynomial) q).abs().multiply(c);
    }

    public GenPolynomial<C> baseResultant(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial<C> r;
        GenPolynomial<C> q;
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            r = P;
            q = S;
            long g = f;
            f = e;
            e = g;
        } else {
            q = P;
            r = S;
        }
        GcdRingElem a = baseContent(r);
        GcdRingElem b = baseContent(q);
        r = divide(r, a);
        q = divide(q, b);
        RingFactory cofac = P.ring.coFac;
        C g2 = (GcdRingElem) cofac.getONE();
        C h = (GcdRingElem) cofac.getONE();
        GcdRingElem t = (GcdRingElem) power(cofac, a, e).multiply(power(cofac, b, f));
        long s = 1;
        while (r.degree(0) > 0) {
            long delta = q.degree(0) - r.degree(0);
            if (!(q.degree(0) % 2 == 0 || r.degree(0) % 2 == 0)) {
                s = -s;
            }
            GenPolynomial<C> x = PolyUtil.baseDensePseudoRemainder(q, r);
            q = r;
            if (x.degree(0) > 0) {
                r = x.divide((GcdRingElem) g2.multiply(power(cofac, (GcdRingElem) h, delta)));
                g2 = (GcdRingElem) q.leadingBaseCoefficient();
                h = (GcdRingElem) power(cofac, (GcdRingElem) g2, delta).divide(power(cofac, (GcdRingElem) h, delta - 1));
            } else {
                r = x;
            }
        }
        C z = (GcdRingElem) ((GcdRingElem) power(cofac, (GcdRingElem) r.leadingBaseCoefficient(), q.degree(0)).divide(power(cofac, (GcdRingElem) h, q.degree(0) - 1))).multiply(t);
        if (s < 0) {
            z = (GcdRingElem) z.negate();
        }
        return P.ring.getONE().multiply((RingElem) z);
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial<GenPolynomial<C>> r;
        GenPolynomial<GenPolynomial<C>> q;
        GenPolynomial<C> t;
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            r = P;
            q = S;
            long g = f;
            f = e;
            e = g;
        } else {
            q = P;
            r = S;
        }
        r = r.abs();
        q = q.abs();
        GenPolynomial a = recursiveContent(r);
        GenPolynomial b = recursiveContent(q);
        r = PolyUtil.recursiveDivide((GenPolynomial) r, a);
        q = PolyUtil.recursiveDivide((GenPolynomial) q, b);
        RingFactory cofac = P.ring.coFac;
        GenPolynomial<C> g2 = (GenPolynomial) cofac.getONE();
        GenPolynomial<C> h = (GenPolynomial) cofac.getONE();
        if (f == 0 && e == 0) {
            if (g2.ring.nvar > 0) {
                t = resultant(a, b);
                return P.ring.getONE().multiply((RingElem) t);
            }
        }
        t = power(cofac, a, e).multiply(power(cofac, b, f));
        long s = 1;
        while (r.degree(0) > 0) {
            long delta = q.degree(0) - r.degree(0);
            if (!(q.degree(0) % 2 == 0 || r.degree(0) % 2 == 0)) {
                s = -s;
            }
            GenPolynomial<GenPolynomial<C>> x = PolyUtil.recursiveDensePseudoRemainder(q, r);
            q = r;
            if (x.degree(0) > 0) {
                r = PolyUtil.recursiveDivide((GenPolynomial) x, g2.multiply(power(P.ring.coFac, (GenPolynomial) h, delta)));
                g2 = (GenPolynomial) q.leadingBaseCoefficient();
                h = PolyUtil.basePseudoDivide(power(cofac, (GenPolynomial) g2, delta), power(cofac, (GenPolynomial) h, delta - 1));
            } else {
                r = x;
            }
        }
        GenPolynomial<C> z = PolyUtil.basePseudoDivide(power(cofac, (GenPolynomial) r.leadingBaseCoefficient(), q.degree(0)), power(cofac, (GenPolynomial) h, q.degree(0) - 1)).multiply((GenPolynomial) t);
        if (s < 0) {
            z = z.negate();
        }
        return P.ring.getONE().multiply((RingElem) z);
    }

    public GenPolynomial<C> baseDiscriminant(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            if (P.ring.nvar > 1) {
                throw new IllegalArgumentException(getClass().getName() + " P not univariate");
            }
            GenPolynomial<C> disc = baseResultant(P, PolyUtil.baseDeriviative(P)).multiply((GcdRingElem) ((GcdRingElem) P.leadingBaseCoefficient()).inverse());
            long n = P.degree(0);
            if (((n * (n - 1)) / 2) % 2 != 0) {
                disc = disc.negate();
            }
            return disc;
        }
    }

    C power(RingFactory<C> fac, C A, long i) {
        return (GcdRingElem) Power.power((RingFactory) fac, (RingElem) A, i);
    }

    GenPolynomial<C> power(RingFactory<GenPolynomial<C>> fac, GenPolynomial<C> A, long i) {
        return (GenPolynomial) Power.power((RingFactory) fac, (RingElem) A, i);
    }
}
