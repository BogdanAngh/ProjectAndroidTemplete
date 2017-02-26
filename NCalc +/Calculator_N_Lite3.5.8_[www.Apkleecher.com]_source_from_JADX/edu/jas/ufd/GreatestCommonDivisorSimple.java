package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorSimple<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    public GreatestCommonDivisorSimple() {
        this.debug = logger.isDebugEnabled();
    }

    static {
        logger = Logger.getLogger(GreatestCommonDivisorSimple.class);
    }

    public GenPolynomial<C> baseGcd(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial<C> r;
        GenPolynomial<C> q;
        RingElem c;
        boolean field = P.ring.coFac.isField();
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
        if (field) {
            r = r.monic();
            q = q.monic();
            c = (GcdRingElem) P.ring.getONECoefficient();
        } else {
            r = r.abs();
            q = q.abs();
            C a = baseContent(r);
            C b = baseContent(q);
            c = gcd((GcdRingElem) a, (GcdRingElem) b);
            r = divide(r, a);
            q = divide(q, b);
        }
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        while (!r.isZERO()) {
            GenPolynomial<C> x = PolyUtil.baseSparsePseudoRemainder(q, r);
            q = r;
            if (field) {
                r = x.monic();
            } else {
                r = x;
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
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenPolynomial r;
        GenPolynomial q;
        GenPolynomial<GenPolynomial<C>> q2;
        GenPolynomial<GenPolynomial<C>> r2;
        boolean field = ((GenPolynomial) P.leadingBaseCoefficient()).ring.coFac.isField();
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            r = P;
            q = S;
            long g = f;
            f = e;
            e = g;
        } else {
            q2 = P;
            r2 = S;
        }
        if (this.debug) {
            logger.debug("degrees: e = " + e + ", f = " + f);
        }
        if (field) {
            r = PolyUtil.monic(r);
            q = PolyUtil.monic(q);
        } else {
            r = r.abs();
            q = q.abs();
        }
        GenPolynomial a = recursiveContent(r);
        GenPolynomial b = recursiveContent(q);
        RingElem c = gcd(a, b);
        r2 = PolyUtil.recursiveDivide(r, a);
        q2 = PolyUtil.recursiveDivide(q, b);
        if (r2.isONE()) {
            return r2.multiply(c);
        }
        if (q2.isONE()) {
            return q2.multiply(c);
        }
        while (!r2.isZERO()) {
            GenPolynomial<GenPolynomial<C>> x = PolyUtil.recursivePseudoRemainder(q2, r2);
            q2 = r2;
            if (field) {
                r2 = PolyUtil.monic((GenPolynomial) x);
            } else {
                r2 = x;
            }
        }
        return recursivePrimitivePart((GenPolynomial) q2).abs().multiply(c);
    }

    public GenPolynomial<C> baseResultant(GenPolynomial<C> P, GenPolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        int i = P.ring.nvar;
        if (r0 <= 1) {
            if (P.ring.nvar != 0) {
                long e = P.degree(0);
                long f = S.degree(0);
                if (f == 0 && e == 0) {
                    return P.ring.getONE();
                } else if (e == 0) {
                    return (GenPolynomial) Power.power((RingFactory) P.ring, (RingElem) P, f);
                } else if (f == 0) {
                    return (GenPolynomial) Power.power((RingFactory) S.ring, (RingElem) S, e);
                } else {
                    GenPolynomial<C> r;
                    GenPolynomial<C> q;
                    GcdRingElem c2;
                    int i2;
                    RingElem c;
                    int s = 0;
                    if (e < f) {
                        r = P;
                        q = S;
                        long t = e;
                        e = f;
                        f = t;
                        if (!(e % 2 == 0 || f % 2 == 0)) {
                            s = 1;
                        }
                    } else {
                        q = P;
                        r = S;
                    }
                    RingFactory<C> cofac = P.ring.coFac;
                    boolean field = cofac.isField();
                    GcdRingElem c3 = (GcdRingElem) cofac.getONE();
                    long g;
                    do {
                        GenPolynomial<C> x;
                        if (field) {
                            x = q.remainder((GenPolynomial) r);
                        } else {
                            x = PolyUtil.baseSparsePseudoRemainder(q, r);
                        }
                        if (x.isZERO()) {
                            return x;
                        }
                        e = q.degree(0);
                        f = r.degree(0);
                        if (!(e % 2 == 0 || f % 2 == 0)) {
                            s = 1 - s;
                        }
                        g = x.degree(0);
                        c2 = (GcdRingElem) r.leadingBaseCoefficient();
                        i2 = 0;
                        while (true) {
                            if (((long) i2) >= e - g) {
                                break;
                            }
                            c3 = (GcdRingElem) c.multiply(c2);
                            i2++;
                        }
                        q = r;
                        r = x;
                    } while (g != 0);
                    c2 = (GcdRingElem) r.leadingBaseCoefficient();
                    i2 = 0;
                    while (true) {
                        if (((long) i2) >= f) {
                            break;
                        }
                        c3 = (GcdRingElem) c.multiply(c2);
                        i2++;
                    }
                    if (s == 1) {
                        c = (GcdRingElem) c.negate();
                    }
                    return P.ring.getONE().multiply(c);
                }
            }
        }
        throw new IllegalArgumentException("no univariate polynomial");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        int i = P.ring.nvar;
        if (r0 <= 1) {
            if (P.ring.nvar != 0) {
                long e = P.degree(0);
                long f = S.degree(0);
                if (f == 0 && e == 0) {
                    GenPolynomial<C> t = resultant((GenPolynomial) P.leadingBaseCoefficient(), (GenPolynomial) S.leadingBaseCoefficient());
                    return P.ring.getONE().multiply((RingElem) t);
                } else if (e == 0) {
                    return (GenPolynomial) Power.power((RingFactory) P.ring, (RingElem) P, f);
                } else if (f == 0) {
                    return (GenPolynomial) Power.power((RingFactory) S.ring, (RingElem) S, e);
                } else {
                    GenPolynomial<GenPolynomial<C>> r;
                    GenPolynomial<GenPolynomial<C>> q;
                    long g;
                    GenPolynomial c2;
                    int i2;
                    RingElem c;
                    int s = 0;
                    if (f > e) {
                        r = P;
                        q = S;
                        g = f;
                        f = e;
                        if (!(g % 2 == 0 || f % 2 == 0)) {
                            s = 1;
                        }
                    } else {
                        q = P;
                        r = S;
                    }
                    GenPolynomial<C> c3 = (GenPolynomial) P.ring.coFac.getONE();
                    do {
                        GenPolynomial<GenPolynomial<C>> x = PolyUtil.recursiveSparsePseudoRemainder(q, r);
                        if (x.isZERO()) {
                            return x;
                        }
                        e = q.degree(0);
                        f = r.degree(0);
                        if (!(e % 2 == 0 || f % 2 == 0)) {
                            s = 1 - s;
                        }
                        g = x.degree(0);
                        c2 = (GenPolynomial) r.leadingBaseCoefficient();
                        i2 = 0;
                        while (true) {
                            if (((long) i2) >= e - g) {
                                break;
                            }
                            c3 = c.multiply(c2);
                            i2++;
                        }
                        q = r;
                        r = x;
                    } while (g != 0);
                    c2 = (GenPolynomial) r.leadingBaseCoefficient();
                    i2 = 0;
                    while (true) {
                        if (((long) i2) >= f) {
                            break;
                        }
                        c = c.multiply(c2);
                        i2++;
                    }
                    if (s == 1) {
                        c = c.negate();
                    }
                    return P.ring.getONE().multiply(c);
                }
            }
        }
        throw new IllegalArgumentException("no recursive univariate polynomial");
    }
}
