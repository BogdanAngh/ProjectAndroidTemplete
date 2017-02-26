package edu.jas.fd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorPrimitive<C extends GcdRingElem<C>> extends GreatestCommonDivisorAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(GreatestCommonDivisorPrimitive.class);
    }

    public GreatestCommonDivisorPrimitive(RingFactory<C> cf) {
        super(cf);
        this.debug = true;
    }

    public GenSolvablePolynomial<C> leftBaseGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenSolvablePolynomial<C> r;
        GenSolvablePolynomial<C> q;
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
        logger.debug("degrees: e = " + e + ", f = " + f);
        if (field) {
            r = r.monic();
            q = q.monic();
            c = (GcdRingElem) P.ring.getONECoefficient();
        } else {
            r = (GenSolvablePolynomial) r.abs();
            q = (GenSolvablePolynomial) q.abs();
            C a = rightBaseContent(r);
            C b = rightBaseContent(q);
            r = divide(r, a);
            q = divide(q, b);
            c = gcd(a, b);
        }
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        while (!r.isZERO()) {
            GenSolvablePolynomial<C> x = FDUtil.leftBaseSparsePseudoRemainder(q, r);
            q = r;
            if (field) {
                r = x.monic();
            } else {
                r = leftBasePrimitivePart(x);
            }
        }
        return (GenSolvablePolynomial) q.multiply(c).abs();
    }

    public GenSolvablePolynomial<C> rightBaseGcd(GenSolvablePolynomial<C> P, GenSolvablePolynomial<C> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " no univariate polynomial");
        }
        GenSolvablePolynomial<C> r;
        GenSolvablePolynomial<C> q;
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
        logger.debug("degrees: e = " + e + ", f = " + f);
        if (field) {
            r = r.monic();
            q = q.monic();
            c = (GcdRingElem) P.ring.getONECoefficient();
        } else {
            r = (GenSolvablePolynomial) r.abs();
            q = (GenSolvablePolynomial) q.abs();
            C a = leftBaseContent(r);
            C b = leftBaseContent(q);
            r = divide(r, a);
            q = divide(q, b);
            c = gcd(a, b);
        }
        if (r.isONE()) {
            return r.multiply(c);
        }
        if (q.isONE()) {
            return q.multiply(c);
        }
        while (!r.isZERO()) {
            GenSolvablePolynomial<C> x = FDUtil.rightBaseSparsePseudoRemainder(q, r);
            q = r;
            if (field) {
                r = x.monic();
            } else {
                r = rightBasePrimitivePart(x);
            }
        }
        return (GenSolvablePolynomial) q.multiplyLeft(c).abs();
    }

    public GenSolvablePolynomial<GenPolynomial<C>> leftRecursiveUnivariateGcd(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException("no univariate polynomial");
        }
        GenSolvablePolynomial<GenPolynomial<C>> q;
        GenSolvablePolynomial<GenPolynomial<C>> r;
        boolean field = ((GenPolynomial) P.leadingBaseCoefficient()).ring.coFac.isField();
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            GenSolvablePolynomial r2 = P;
            GenSolvablePolynomial q2 = S;
            long g = f;
            f = e;
            e = g;
        } else if (f < e) {
            q = P;
            r = S;
        } else if (((GenPolynomial) P.leadingBaseCoefficient()).degree() > ((GenPolynomial) S.leadingBaseCoefficient()).degree()) {
            q = P;
            r = S;
        } else {
            r = P;
            q = S;
        }
        logger.debug("degrees: e = " + e + ", f = " + f);
        if (field) {
            r = PolyUtil.monic(r2);
            q = PolyUtil.monic(q2);
        } else {
            r = (GenSolvablePolynomial) r2.abs();
            q = (GenSolvablePolynomial) q2.abs();
        }
        RingElem a = rightRecursiveContent(r);
        GenSolvablePolynomial<GenPolynomial<C>> rs = FDUtil.recursiveDivideRightEval(r, a);
        logger.info("recCont a = " + a + ", r = " + r);
        logger.info("recCont r/a = " + rs + ", r%a = " + r.subtract(rs.multiply(a)));
        if (r.equals(rs.multiply(a))) {
            r = rs;
            RingElem b = rightRecursiveContent(q);
            GenSolvablePolynomial<GenPolynomial<C>> qs = FDUtil.recursiveDivideRightEval(q, b);
            logger.info("recCont b = " + b + ", q = " + q);
            logger.info("recCont q/b = " + qs + ", q%b = " + q.subtract(qs.multiply(b)));
            if (q.equals(qs.multiply(b))) {
                q = qs;
                RingElem c = rightGcd(a, b);
                logger.info("Gcd(contents) c = " + c + ", a = " + a + ", b = " + b);
                if (r.isONE()) {
                    return r.multiply(c);
                }
                if (q.isONE()) {
                    return q.multiply(c);
                }
                logger.info("r.ring = " + r.ring.toScript());
                while (!r.isZERO()) {
                    GenSolvablePolynomial<GenPolynomial<C>> x = FDUtil.recursiveSparsePseudoRemainder(q, r);
                    q = r;
                    r = leftRecursivePrimitivePart(x);
                    if (field) {
                        r = PolyUtil.monic((GenSolvablePolynomial) r);
                    }
                }
                logger.info("gcd(pp) = " + q + ", ring = " + P.ring.toScript());
                return (GenSolvablePolynomial) q.multiply(c).abs();
            }
            System.out.println("recGcd, q         = " + q);
            System.out.println("recGcd, cont(q)   = " + b);
            System.out.println("recGcd, pp(q)     = " + qs);
            System.out.println("recGcd, pp(q)c(q) = " + qs.multiply(b));
            throw new RuntimeException("recGcd, pp: not divisible");
        }
        System.out.println("recGcd, r         = " + r);
        System.out.println("recGcd, cont(r)   = " + a);
        System.out.println("recGcd, pp(r)     = " + rs);
        System.out.println("recGcd, pp(r)c(r) = " + rs.multiply(a));
        throw new RuntimeException("recGcd, pp: not divisible");
    }

    public GenSolvablePolynomial<GenPolynomial<C>> rightRecursiveUnivariateGcd(GenSolvablePolynomial<GenPolynomial<C>> P, GenSolvablePolynomial<GenPolynomial<C>> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        int i = P.ring.nvar;
        if (r0 > 1) {
            throw new IllegalArgumentException("no univariate polynomial");
        }
        GenSolvablePolynomial<GenPolynomial<C>> q;
        GenSolvablePolynomial<GenPolynomial<C>> r;
        boolean field = ((GenPolynomial) P.leadingBaseCoefficient()).ring.coFac.isField();
        long e = P.degree(0);
        long f = S.degree(0);
        if (f > e) {
            GenSolvablePolynomial r2 = P;
            GenSolvablePolynomial q2 = S;
            long g = f;
            f = e;
            e = g;
        } else if (f < e) {
            q = P;
            r = S;
        } else if (((GenPolynomial) P.leadingBaseCoefficient()).degree() > ((GenPolynomial) S.leadingBaseCoefficient()).degree()) {
            q = P;
            r = S;
        } else {
            r = P;
            q = S;
        }
        logger.debug("RI-degrees: e = " + e + ", f = " + f);
        if (field) {
            r = PolyUtil.monic(r2);
            q = PolyUtil.monic(q2);
        } else {
            r = (GenSolvablePolynomial) r2.abs();
            q = (GenSolvablePolynomial) q2.abs();
        }
        RingElem a = leftRecursiveContent(r);
        GenSolvablePolynomial<GenPolynomial<C>> rs = FDUtil.recursiveDivide(r, a);
        logger.info("RI-recCont a = " + a + ", r = " + r);
        logger.info("RI-recCont r/a = " + r + ", r%a = " + r.subtract(rs.multiplyLeft(a)));
        if (r.equals(rs.multiplyLeft(a))) {
            r = rs;
            RingElem b = leftRecursiveContent(q);
            GenSolvablePolynomial<GenPolynomial<C>> qs = FDUtil.recursiveDivide(q, b);
            logger.info("RI-recCont b = " + b + ", q = " + q);
            logger.info("RI-recCont q/b = " + qs + ", q%b = " + q.subtract(qs.multiplyLeft(b)));
            if (q.equals(qs.multiplyLeft(b))) {
                q = qs;
                RingElem c = leftGcd(a, b);
                logger.info("RI-Gcd(contents) c = " + c + ", a = " + a + ", b = " + b);
                if (r.isONE()) {
                    return r.multiplyLeft(c);
                }
                if (q.isONE()) {
                    return q.multiplyLeft(c);
                }
                logger.info("RI-r.ring = " + r.ring.toScript());
                while (!r.isZERO()) {
                    GenSolvablePolynomial<GenPolynomial<C>> x = FDUtil.recursiveRightSparsePseudoRemainder(q, r);
                    q = r;
                    r = rightRecursivePrimitivePart(x);
                    if (field) {
                        r = PolyUtil.monic((GenSolvablePolynomial) r);
                    }
                }
                logger.info("RI-gcd(pp) = " + q + ", ring = " + P.ring.toScript());
                return (GenSolvablePolynomial) q.multiplyLeft(c).abs();
            }
            System.out.println("RI-recGcd, q         = " + q);
            System.out.println("RI-recGcd, cont(q)   = " + b);
            System.out.println("RI-recGcd, pp(q)     = " + qs);
            System.out.println("RI-recGcd, pp(q)c(q) = " + qs.multiplyLeft(b));
            throw new RuntimeException("RI-recGcd, pp: not divisible");
        }
        System.out.println("RI-recGcd, r         = " + r);
        System.out.println("RI-recGcd, cont(r)   = " + a);
        System.out.println("RI-recGcd, pp(r)     = " + rs);
        System.out.println("RI-recGcd, pp(r)c(r) = " + rs.multiplyLeft(a));
        throw new RuntimeException("RI-recGcd, pp: not divisible");
    }
}
