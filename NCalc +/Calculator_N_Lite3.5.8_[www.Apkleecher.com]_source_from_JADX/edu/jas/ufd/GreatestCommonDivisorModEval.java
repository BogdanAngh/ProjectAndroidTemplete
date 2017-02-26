package edu.jas.ufd;

import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorModEval<MOD extends GcdRingElem<MOD> & Modular> extends GreatestCommonDivisorAbstract<MOD> {
    private static final Logger logger;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<MOD> mufd;

    public GreatestCommonDivisorModEval() {
        this.debug = logger.isDebugEnabled();
        this.mufd = new GreatestCommonDivisorSimple();
    }

    static {
        logger = Logger.getLogger(GreatestCommonDivisorModEval.class);
    }

    public GenPolynomial<MOD> baseGcd(GenPolynomial<MOD> P, GenPolynomial<MOD> S) {
        return this.mufd.baseGcd(P, S);
    }

    public GenPolynomial<GenPolynomial<MOD>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<MOD>> P, GenPolynomial<GenPolynomial<MOD>> S) {
        return this.mufd.recursiveUnivariateGcd(P, S);
    }

    public GenPolynomial<MOD> gcd(GenPolynomial<MOD> P, GenPolynomial<MOD> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        GenPolynomialRing<MOD> fac = P.ring;
        int i = fac.nvar;
        if (r0 <= 1) {
            return baseGcd(P, S);
        }
        long e = P.degree(fac.nvar - 1);
        long f = S.degree(fac.nvar - 1);
        if (e == 0 && f == 0) {
            GenPolynomialRing rfac = fac.recursive(1);
            return gcd((GenPolynomial) PolyUtil.recursive(rfac, (GenPolynomial) P).leadingBaseCoefficient(), (GenPolynomial) PolyUtil.recursive(rfac, (GenPolynomial) S).leadingBaseCoefficient()).extend(fac, 0, 0);
        }
        GenPolynomial<MOD> r;
        GenPolynomial<MOD> q;
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
        ModularRingFactory<MOD> cofac = (ModularRingFactory) P.ring.coFac;
        if (!cofac.isField()) {
            logger.warn("cofac is not a field: " + cofac);
        }
        rfac = fac.recursive(fac.nvar - 1);
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) cofac, rfac);
        GenPolynomialRing<MOD> ufac = (GenPolynomialRing) rfac.coFac;
        GenPolynomial<GenPolynomial<MOD>> qr = PolyUtil.recursive(rfac, (GenPolynomial) q);
        GenPolynomial<GenPolynomial<MOD>> rr = PolyUtil.recursive(rfac, (GenPolynomial) r);
        GenPolynomial<MOD> a = recursiveContent(rr);
        GenPolynomial<MOD> b = recursiveContent(qr);
        GenPolynomial<MOD> c = gcd(a, b);
        rr = PolyUtil.recursiveDivide((GenPolynomial) rr, (GenPolynomial) a);
        qr = PolyUtil.recursiveDivide((GenPolynomial) qr, (GenPolynomial) b);
        if (rr.isONE()) {
            return PolyUtil.distribute((GenPolynomialRing) fac, (GenPolynomial) rr.multiply((RingElem) c));
        } else if (qr.isONE()) {
            return PolyUtil.distribute((GenPolynomialRing) fac, (GenPolynomial) qr.multiply((RingElem) c));
        } else {
            GenPolynomial<MOD> cc = gcd((GenPolynomial) rr.leadingBaseCoefficient(), (GenPolynomial) qr.leadingBaseCoefficient());
            ExpVector rdegv = rr.degreeVector();
            ExpVector qdegv = qr.degreeVector();
            long rd0 = PolyUtil.coeffMaxDegree(rr);
            long qd0 = PolyUtil.coeffMaxDegree(qr);
            long cd0 = cc.degree(0);
            if (rd0 < qd0) {
                rd0 = qd0;
            }
            long G = rd0 + cd0;
            ExpVector wdegv = rdegv.subst(0, rdegv.getVal(0) + 1);
            GcdRingElem inc = (GcdRingElem) cofac.getONE();
            long i2 = 0;
            long en = cofac.getIntegerModul().longValue() - 1;
            GcdRingElem end = (GcdRingElem) cofac.fromInteger(en);
            GenPolynomial M = null;
            GenPolynomial<GenPolynomial<MOD>> cp = null;
            if (this.debug) {
                logger.debug("c = " + c);
                logger.debug("cc = " + cc);
                logger.debug("G = " + G);
                logger.info("wdegv = " + wdegv);
            }
            for (RingElem d = (GcdRingElem) cofac.getZERO(); d.compareTo(end) <= 0; GcdRingElem d2 = (GcdRingElem) d.sum(inc)) {
                i2++;
                if (i2 >= en) {
                    logger.warn("elements of Z_p exhausted, en = " + en);
                    return this.mufd.gcd((GenPolynomial) P, (GenPolynomial) S);
                }
                GcdRingElem nf = (GcdRingElem) PolyUtil.evaluateMain((RingFactory) cofac, (GenPolynomial) cc, d);
                if (!nf.isZERO()) {
                    GenPolynomial<MOD> qm = PolyUtil.evaluateFirstRec(ufac, genPolynomialRing, qr, d);
                    if (qm.isZERO()) {
                        continue;
                    } else {
                        if (qm.degreeVector().equals(qdegv)) {
                            GenPolynomial<MOD> rm = PolyUtil.evaluateFirstRec(ufac, genPolynomialRing, rr, d);
                            if (rm.isZERO()) {
                                continue;
                            } else {
                                if (rm.degreeVector().equals(rdegv)) {
                                    if (this.debug) {
                                        logger.debug("eval d = " + d);
                                    }
                                    GenPolynomial<MOD> cm = gcd(rm, qm);
                                    if (cm.isConstant()) {
                                        logger.debug("cm.isConstant = " + cm + ", c = " + c);
                                        if (c.ring.nvar < cm.ring.nvar) {
                                            c = c.extend(genPolynomialRing, 0, 0);
                                        }
                                        q = cm.abs().multiply((GenPolynomial) c).extend(fac, 0, 0);
                                        logger.debug("q             = " + q + ", c = " + c);
                                        return q;
                                    }
                                    GenPolynomial cp2;
                                    ExpVector mdegv = cm.degreeVector();
                                    if (!wdegv.equals(mdegv)) {
                                        boolean ok = false;
                                        if (wdegv.multipleOf(mdegv)) {
                                            M = null;
                                            ok = true;
                                        }
                                        if (mdegv.multipleOf(wdegv)) {
                                            continue;
                                        } else if (!ok) {
                                            M = null;
                                        }
                                    } else if (M != null && M.degree(0) > G) {
                                        logger.info("deg(M) > G: " + M.degree(0) + " > " + G);
                                    }
                                    GenPolynomial cm2 = cm.multiply((RingElem) nf);
                                    if (M == null) {
                                        M = ufac.getONE();
                                        cp2 = rfac.getZERO();
                                        wdegv = wdegv.gcd(mdegv);
                                    }
                                    cp = PolyUtil.interpolate(rfac, cp2, M, (GcdRingElem) ((GcdRingElem) PolyUtil.evaluateMain((RingFactory) cofac, M, d)).inverse(), cm2, d);
                                    GenPolynomial<MOD> mn = ufac.getONE().multiply(d);
                                    M = M.multiply(ufac.univariate(0).subtract((GenPolynomial) mn));
                                    if (M.degree(0) > G) {
                                        break;
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
            return PolyUtil.distribute((GenPolynomialRing) fac, recursivePrimitivePart((GenPolynomial) cp).abs().multiply((RingElem) c));
        }
    }

    public GenPolynomial<MOD> baseResultant(GenPolynomial<MOD> P, GenPolynomial<MOD> S) {
        return this.mufd.baseResultant(P, S);
    }

    public GenPolynomial<GenPolynomial<MOD>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<MOD>> P, GenPolynomial<GenPolynomial<MOD>> S) {
        return recursiveResultant(P, S);
    }

    public GenPolynomial<MOD> resultant(GenPolynomial<MOD> P, GenPolynomial<MOD> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<MOD> fac = P.ring;
        int i = fac.nvar;
        if (r0 <= 1) {
            return this.mufd.baseResultant(P, S);
        }
        long e = P.degree(fac.nvar - 1);
        long f = S.degree(fac.nvar - 1);
        if (e == 0 && f == 0) {
            GenPolynomialRing rfac = fac.recursive(1);
            return resultant((GenPolynomial) PolyUtil.recursive(rfac, (GenPolynomial) P).leadingBaseCoefficient(), (GenPolynomial) PolyUtil.recursive(rfac, (GenPolynomial) S).leadingBaseCoefficient()).extend(fac, 0, 0);
        }
        GenPolynomial<MOD> r;
        GenPolynomial<MOD> q;
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
        ModularRingFactory<MOD> cofac = (ModularRingFactory) P.ring.coFac;
        if (!cofac.isField()) {
            logger.warn("cofac is not a field: " + cofac);
        }
        rfac = fac.recursive(fac.nvar - 1);
        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing((RingFactory) cofac, rfac);
        GenPolynomialRing<MOD> ufac = (GenPolynomialRing) rfac.coFac;
        GenPolynomial<GenPolynomial<MOD>> qr = PolyUtil.recursive(rfac, (GenPolynomial) q);
        GenPolynomial<GenPolynomial<MOD>> rr = PolyUtil.recursive(rfac, (GenPolynomial) r);
        ExpVector qdegv = qr.degreeVector();
        ExpVector rdegv = rr.degreeVector();
        long qd0 = PolyUtil.coeffMaxDegree(qr);
        long rd0 = PolyUtil.coeffMaxDegree(rr);
        if (qd0 == 0) {
            qd0 = 1;
        }
        if (rd0 == 0) {
            rd0 = 1;
        }
        long qd1 = qr.degree();
        long rd1 = rr.degree();
        if (qd1 == 0) {
            qd1 = 1;
        }
        if (rd1 == 0) {
            rd1 = 1;
        }
        long G = ((qd0 * rd1) + (rd0 * qd1)) + 1;
        GcdRingElem inc = (GcdRingElem) cofac.getONE();
        long i2 = 0;
        long en = cofac.getIntegerModul().longValue() - 1;
        GcdRingElem end = (GcdRingElem) cofac.fromInteger(en);
        GenPolynomial M = null;
        GenPolynomial cp = null;
        if (this.debug) {
            logger.info("G     = " + G);
        }
        for (RingElem d = (GcdRingElem) cofac.getZERO(); d.compareTo(end) <= 0; GcdRingElem d2 = (GcdRingElem) d.sum(inc)) {
            i2++;
            if (i2 >= en) {
                logger.warn("elements of Z_p exhausted, en = " + en + ", p = " + cofac.getIntegerModul());
                return this.mufd.resultant(P, S);
            }
            GenPolynomial<MOD> qm = PolyUtil.evaluateFirstRec(ufac, genPolynomialRing, qr, d);
            if (!qm.isZERO()) {
                if (qm.degreeVector().equals(qdegv)) {
                    GenPolynomial<MOD> rm = PolyUtil.evaluateFirstRec(ufac, genPolynomialRing, rr, d);
                    if (!rm.isZERO()) {
                        if (rm.degreeVector().equals(rdegv)) {
                            GenPolynomial cm = resultant(rm, qm);
                            if (M == null) {
                                M = ufac.getONE();
                                cp = rfac.getZERO();
                            }
                            cp = PolyUtil.interpolate(rfac, cp, M, (GcdRingElem) ((GcdRingElem) PolyUtil.evaluateMain((RingFactory) cofac, M, d)).inverse(), cm, d);
                            GenPolynomial<MOD> mn = ufac.getONE().multiply(d);
                            M = M.multiply(ufac.univariate(0).subtract((GenPolynomial) mn));
                            if (M.degree(0) > G) {
                                if (this.debug) {
                                    logger.info("last lucky evaluation point " + d);
                                }
                                return PolyUtil.distribute((GenPolynomialRing) fac, cp);
                            }
                        }
                    }
                    if (this.debug) {
                        logger.info("un-lucky evaluation point " + d + ", rm = " + rm.degreeVector() + " < " + rdegv);
                    }
                }
            }
            if (this.debug) {
                logger.info("un-lucky evaluation point " + d + ", qm = " + qm.degreeVector() + " < " + qdegv);
            }
        }
        return PolyUtil.distribute((GenPolynomialRing) fac, cp);
    }
}
