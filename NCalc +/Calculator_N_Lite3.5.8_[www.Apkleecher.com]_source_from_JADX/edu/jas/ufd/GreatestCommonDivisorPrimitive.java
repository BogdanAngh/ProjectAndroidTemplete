package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorPrimitive<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    public GreatestCommonDivisorPrimitive() {
        this.debug = logger.isDebugEnabled();
    }

    static {
        logger = Logger.getLogger(GreatestCommonDivisorPrimitive.class);
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
        while (!r.isZERO()) {
            GenPolynomial<C> x = PolyUtil.baseSparsePseudoRemainder(q, r);
            q = r;
            r = basePrimitivePart((GenPolynomial) x);
        }
        return q.multiply(c).abs();
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
        GenPolynomial r2 = r.abs();
        GenPolynomial q2 = q.abs();
        GenPolynomial a = recursiveContent(r2);
        GenPolynomial b = recursiveContent(q2);
        RingElem c = gcd(a, b);
        r = PolyUtil.recursiveDivide(r2, a);
        q = PolyUtil.recursiveDivide(q2, b);
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        while (!r.isZERO()) {
            GenPolynomial<GenPolynomial<C>> x = PolyUtil.recursivePseudoRemainder(q, r);
            q = r;
            r = recursivePrimitivePart((GenPolynomial) x);
        }
        return q.abs().multiply(c);
    }
}
