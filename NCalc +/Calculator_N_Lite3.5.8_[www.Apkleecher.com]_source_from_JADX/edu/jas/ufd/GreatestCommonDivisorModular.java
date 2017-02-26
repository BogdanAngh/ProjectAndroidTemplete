package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.Combinatoric;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.arith.PrimeList;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Iterator;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorModular<MOD extends GcdRingElem<MOD> & Modular> extends GreatestCommonDivisorAbstract<BigInteger> {
    private static final Logger logger;
    private final boolean debug;
    protected final GreatestCommonDivisorAbstract<BigInteger> iufd;
    protected final GreatestCommonDivisorAbstract<MOD> mufd;

    static {
        logger = Logger.getLogger(GreatestCommonDivisorModular.class);
    }

    public GreatestCommonDivisorModular() {
        this(false);
    }

    public GreatestCommonDivisorModular(boolean simple) {
        this.debug = logger.isDebugEnabled();
        this.iufd = new GreatestCommonDivisorSubres();
        if (simple) {
            this.mufd = new GreatestCommonDivisorSimple();
        } else {
            this.mufd = new GreatestCommonDivisorModEval();
        }
    }

    public GenPolynomial<BigInteger> baseGcd(GenPolynomial<BigInteger> P, GenPolynomial<BigInteger> S) {
        return this.iufd.baseGcd(P, S);
    }

    public GenPolynomial<GenPolynomial<BigInteger>> recursiveUnivariateGcd(GenPolynomial<GenPolynomial<BigInteger>> P, GenPolynomial<GenPolynomial<BigInteger>> S) {
        return this.iufd.recursiveUnivariateGcd(P, S);
    }

    public GenPolynomial<BigInteger> gcd(GenPolynomial<BigInteger> P, GenPolynomial<BigInteger> S) {
        if (S == null || S.isZERO()) {
            return P;
        }
        if (P == null || P.isZERO()) {
            return S;
        }
        GenPolynomialRing<BigInteger> fac = P.ring;
        int i = fac.nvar;
        if (r0 <= 1) {
            return baseGcd(P, S);
        }
        GenPolynomial<BigInteger> r;
        GenPolynomial<BigInteger> q;
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
        BigInteger a = (BigInteger) baseContent(r);
        BigInteger b = (BigInteger) baseContent(q);
        BigInteger c = (BigInteger) gcd((GcdRingElem) a, (GcdRingElem) b);
        r = divide(r, a);
        q = divide(q, b);
        if (r.isONE()) {
            return r.multiply((RingElem) c);
        }
        if (q.isONE()) {
            return q.multiply((RingElem) c);
        }
        BigInteger n;
        BigInteger cf;
        BigInteger cc = (BigInteger) gcd((GcdRingElem) (BigInteger) r.leadingBaseCoefficient(), (GcdRingElem) (BigInteger) q.leadingBaseCoefficient());
        BigInteger an = (BigInteger) r.maxNorm();
        BigInteger bn = (BigInteger) q.maxNorm();
        if (an.compareTo(bn) < 0) {
            n = bn;
        } else {
            n = an;
        }
        n = n.multiply(cc).multiply(n.fromInteger(2));
        ExpVector rdegv = r.degreeVector();
        ExpVector qdegv = q.degreeVector();
        BigInteger af = an.multiply(PolyUtil.factorBound(rdegv));
        BigInteger bf = bn.multiply(PolyUtil.factorBound(qdegv));
        if (af.compareTo(bf) < 0) {
            cf = bf;
        } else {
            cf = af;
        }
        cf = cf.multiply(cc.multiply(cc.fromInteger(8)));
        PrimeList primes = new PrimeList();
        ExpVector wdegv = rdegv.subst(0, rdegv.getVal(0) + 1);
        int i2 = 0;
        BigInteger M = null;
        BigInteger cfe = null;
        GenPolynomial<MOD> cp = null;
        if (this.debug) {
            logger.debug("c = " + c);
            logger.debug("cc = " + cc);
            logger.debug("n  = " + n);
            logger.debug("cf = " + cf);
            logger.info("wdegv = " + wdegv);
        }
        Iterator i$ = primes.iterator();
        while (i$.hasNext()) {
            java.math.BigInteger p = (java.math.BigInteger) i$.next();
            if (p.longValue() != 2) {
                i2++;
                if (i2 >= 10) {
                    logger.warn("prime list exhausted, pn = " + 10);
                    return this.iufd.gcd((GenPolynomial) P, (GenPolynomial) S);
                }
                ModularRingFactory<MOD> modLongRing;
                if (ModLongRing.MAX_LONG.compareTo(p) > 0) {
                    modLongRing = new ModLongRing(p, true);
                } else {
                    modLongRing = new ModIntegerRing(p, true);
                }
                GcdRingElem nf = (GcdRingElem) cofac.fromInteger(cc.getVal());
                if (nf.isZERO()) {
                    continue;
                } else {
                    GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing(cofac, fac.nvar, fac.tord, fac.getVars());
                    GenPolynomial<MOD> qm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) q);
                    if (qm.isZERO()) {
                        continue;
                    } else {
                        if (qm.degreeVector().equals(qdegv)) {
                            GenPolynomial<MOD> rm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) r);
                            if (!rm.isZERO()) {
                                if (rm.degreeVector().equals(rdegv)) {
                                    if (this.debug) {
                                        logger.info("cofac = " + cofac.getIntegerModul());
                                    }
                                    GenPolynomial<MOD> cm = this.mufd.gcd((GenPolynomial) rm, (GenPolynomial) qm);
                                    if (!cm.isConstant()) {
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
                                        } else if (M != null && M.compareTo(cfe) > 0) {
                                            System.out.println("M > cfe: " + M + " > " + cfe);
                                        }
                                        cm = cm.multiply((RingElem) nf);
                                        ModularRingFactory<MOD> cofacM;
                                        if (M == null) {
                                            M = new BigInteger(p);
                                            cofacM = cofac;
                                            GenPolynomialRing<MOD> rfac = genPolynomialRing;
                                            cp = cm;
                                            wdegv = wdegv.gcd(mdegv);
                                            cfe = cf;
                                            for (int k = 0; k < wdegv.length(); k++) {
                                                cfe = cfe.multiply(new BigInteger(wdegv.getVal(k) + 1));
                                            }
                                        } else {
                                            BigInteger Mp = M;
                                            MOD mi = (GcdRingElem) ((GcdRingElem) cofac.fromInteger(Mp.getVal())).inverse();
                                            M = M.multiply(new BigInteger(p));
                                            if (ModLongRing.MAX_LONG.compareTo(M.getVal()) > 0) {
                                                modLongRing = new ModLongRing(M.getVal());
                                            } else {
                                                modLongRing = new ModIntegerRing(M.getVal());
                                            }
                                            genPolynomialRing = new GenPolynomialRing((RingFactory) cofacM, (GenPolynomialRing) fac);
                                            if (!cofac.getClass().equals(cofacM.getClass())) {
                                                logger.info("adjusting coefficents: cofacM = " + cofacM.getClass() + ", cofacP = " + cofac.getClass());
                                                modLongRing = new ModIntegerRing(p);
                                                cm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) new GenPolynomialRing((RingFactory) modLongRing, (GenPolynomialRing) fac), (GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cm));
                                                mi = (GcdRingElem) ((GcdRingElem) modLongRing.fromInteger(Mp.getVal())).inverse();
                                            }
                                            if (!cp.ring.coFac.getClass().equals(cofacM.getClass())) {
                                                logger.info("adjusting coefficents: cofacM = " + cofacM.getClass() + ", cofacM' = " + cp.ring.coFac.getClass());
                                                cp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) new GenPolynomialRing(new ModIntegerRing(((ModularRingFactory) cp.ring.coFac).getIntegerModul().getVal()), (GenPolynomialRing) fac), (GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cp));
                                            }
                                            cp = PolyUtil.chineseRemainder(genPolynomialRing, cp, mi, cm);
                                        }
                                        if (n.compareTo(M) > 0) {
                                            BigInteger cmn = (BigInteger) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cp).sumNorm();
                                            cmn = cmn.multiply(cmn.fromInteger(4));
                                            if (!(i2 % 2 == 0 || cp.isZERO())) {
                                                GenPolynomial<BigInteger> x = basePrimitivePart((GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cp));
                                                if (PolyUtil.baseSparsePseudoRemainder(q, x).isZERO() && PolyUtil.baseSparsePseudoRemainder(r, x).isZERO()) {
                                                    logger.info("done on exact division, #primes = " + i2);
                                                    break;
                                                }
                                            }
                                        }
                                        break;
                                    }
                                    logger.debug("cm, constant = " + cm);
                                    return fac.getONE().multiply((RingElem) c);
                                }
                                continue;
                            } else {
                                continue;
                            }
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        if (this.debug) {
            logger.info("done on M = " + M + ", #primes = " + i2);
        }
        return basePrimitivePart((GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cp)).abs().multiply((RingElem) c);
    }

    public GenPolynomial<BigInteger> baseResultant(GenPolynomial<BigInteger> P, GenPolynomial<BigInteger> S) {
        return resultant(P, S);
    }

    public GenPolynomial<GenPolynomial<BigInteger>> recursiveUnivariateResultant(GenPolynomial<GenPolynomial<BigInteger>> P, GenPolynomial<GenPolynomial<BigInteger>> S) {
        return recursiveResultant(P, S);
    }

    public GenPolynomial<BigInteger> resultant(GenPolynomial<BigInteger> P, GenPolynomial<BigInteger> S) {
        if (S == null || S.isZERO()) {
            return S;
        }
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomial<BigInteger> r;
        GenPolynomial<BigInteger> q;
        GenPolynomialRing<BigInteger> fac = P.ring;
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
        RingElem an = (BigInteger) r.maxNorm();
        RingElem bn = (BigInteger) q.maxNorm();
        BigInteger an2 = (BigInteger) Power.power(fac.coFac, an, f);
        BigInteger bn2 = (BigInteger) Power.power(fac.coFac, bn, e);
        BigInteger cn = Combinatoric.factorial(e + f);
        BigInteger n = cn.multiply(an2).multiply(bn2);
        ExpVector rdegv = r.leadingExpVector();
        ExpVector qdegv = q.leadingExpVector();
        PrimeList primes = new PrimeList();
        int i = 0;
        BigInteger M = null;
        GenPolynomial<MOD> cp = null;
        if (this.debug) {
            logger.debug("an  = " + an2);
            logger.debug("bn  = " + bn2);
            logger.debug("e+f = " + (e + f));
            logger.debug("cn  = " + cn);
            logger.info("n     = " + n);
        }
        Iterator i$ = primes.iterator();
        while (i$.hasNext()) {
            java.math.BigInteger p = (java.math.BigInteger) i$.next();
            if (p.longValue() != 2) {
                i++;
                if (i >= 30) {
                    logger.warn("prime list exhausted, pn = " + 30);
                    return this.iufd.resultant(P, S);
                }
                RingFactory cofac;
                if (ModLongRing.MAX_LONG.compareTo(p) > 0) {
                    cofac = new ModLongRing(p, true);
                } else {
                    cofac = new ModIntegerRing(p, true);
                }
                GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing(cofac, (GenPolynomialRing) fac);
                GenPolynomial<MOD> qm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) q);
                if (!qm.isZERO()) {
                    if (qm.leadingExpVector().equals(qdegv)) {
                        GenPolynomial<MOD> rm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) r);
                        if (!rm.isZERO()) {
                            if (rm.leadingExpVector().equals(rdegv)) {
                                logger.info("lucky prime = " + cofac.getIntegerModul());
                                GenPolynomial<MOD> cm = this.mufd.resultant(qm, rm);
                                if (this.debug) {
                                    logger.info("res_p = " + cm);
                                }
                                RingFactory cofacM;
                                if (M == null) {
                                    M = new BigInteger(p);
                                    cofacM = cofac;
                                    cp = cm;
                                } else {
                                    BigInteger Mp = M;
                                    MOD mi = (GcdRingElem) ((GcdRingElem) cofac.fromInteger(Mp.getVal())).inverse();
                                    M = M.multiply(new BigInteger(p));
                                    if (ModLongRing.MAX_LONG.compareTo(M.getVal()) > 0) {
                                        cofacM = new ModLongRing(M.getVal());
                                    } else {
                                        cofacM = new ModIntegerRing(M.getVal());
                                    }
                                    genPolynomialRing = new GenPolynomialRing(cofacM, (GenPolynomialRing) fac);
                                    if (!cofac.getClass().equals(cofacM.getClass())) {
                                        logger.info("adjusting coefficents: cofacM = " + cofacM.getClass() + ", cofacP = " + cofac.getClass());
                                        cofac = new ModIntegerRing(p);
                                        cm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) new GenPolynomialRing(cofac, (GenPolynomialRing) fac), (GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cm));
                                        mi = (GcdRingElem) ((GcdRingElem) cofac.fromInteger(Mp.getVal())).inverse();
                                    }
                                    if (!cp.ring.coFac.getClass().equals(cofacM.getClass())) {
                                        logger.info("adjusting coefficents: cofacM = " + cofacM.getClass() + ", cofacM' = " + cp.ring.coFac.getClass());
                                        cp = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) new GenPolynomialRing(new ModIntegerRing(cp.ring.coFac.getIntegerModul().getVal()), (GenPolynomialRing) fac), (GenPolynomial) PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, (GenPolynomial) cp));
                                    }
                                    cp = PolyUtil.chineseRemainder(genPolynomialRing, cp, mi, cm);
                                }
                                if (n.compareTo(M) <= 0) {
                                    break;
                                }
                            }
                        }
                        if (this.debug) {
                            logger.info("unlucky prime = " + cofac.getIntegerModul() + ", degv = " + rm.leadingExpVector());
                        }
                    }
                }
                if (this.debug) {
                    logger.info("unlucky prime = " + cofac.getIntegerModul() + ", degv = " + qm.leadingExpVector());
                }
            }
        }
        if (this.debug) {
            logger.info("done on M = " + M + ", #primes = " + i);
        }
        return PolyUtil.integerFromModularCoefficients((GenPolynomialRing) fac, cp);
    }
}
