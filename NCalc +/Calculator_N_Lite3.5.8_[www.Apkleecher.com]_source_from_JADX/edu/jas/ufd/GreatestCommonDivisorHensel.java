package edu.jas.ufd;

import edu.jas.arith.BigInteger;
import edu.jas.arith.ModIntegerRing;
import edu.jas.arith.ModLongRing;
import edu.jas.arith.Modular;
import edu.jas.arith.ModularRingFactory;
import edu.jas.arith.PrimeList;
import edu.jas.arith.PrimeList.Range;
import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

public class GreatestCommonDivisorHensel<MOD extends GcdRingElem<MOD> & Modular> extends GreatestCommonDivisorAbstract<BigInteger> {
    private static final Logger logger;
    private final boolean debug;
    public final GreatestCommonDivisorAbstract<BigInteger> iufd;
    public final boolean quadratic;
    private final GreatestCommonDivisorAbstract<BigInteger> ufd;

    static {
        logger = Logger.getLogger(GreatestCommonDivisorHensel.class);
    }

    public GreatestCommonDivisorHensel() {
        this(true);
    }

    public GreatestCommonDivisorHensel(boolean quadratic) {
        this.debug = logger.isDebugEnabled();
        this.quadratic = quadratic;
        this.iufd = new GreatestCommonDivisorSubres();
        this.ufd = this;
    }

    public GenPolynomial<BigInteger> baseGcd(GenPolynomial<BigInteger> P, GenPolynomial<BigInteger> S) {
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
        BigInteger cc = (BigInteger) gcd((GcdRingElem) (BigInteger) r.leadingBaseCoefficient(), (GcdRingElem) (BigInteger) q.leadingBaseCoefficient());
        ExpVector rdegv = r.degreeVector();
        ExpVector qdegv = q.degreeVector();
        PrimeList primeList = new PrimeList(Range.medium);
        if (this.debug) {
            logger.debug("c = " + c);
            logger.debug("cc = " + cc);
            logger.debug("primes = " + primeList);
        }
        int i2 = 0;
        Iterator i$ = primeList.iterator();
        while (i$.hasNext()) {
            java.math.BigInteger p = (java.math.BigInteger) i$.next();
            i2++;
            if (i2 >= 50) {
                logger.error("prime list exhausted, pn = " + 50);
                return this.iufd.baseGcd(P, S);
            }
            ModularRingFactory<MOD> modLongRing;
            if (ModLongRing.MAX_LONG.compareTo(p) > 0) {
                modLongRing = new ModLongRing(p, true);
            } else {
                modLongRing = new ModIntegerRing(p, true);
            }
            if (!((GcdRingElem) cofac.fromInteger(cc.getVal())).isZERO()) {
                if (((GcdRingElem) cofac.fromInteger(((BigInteger) q.leadingBaseCoefficient()).getVal())).isZERO()) {
                    continue;
                } else {
                    if (((GcdRingElem) cofac.fromInteger(((BigInteger) r.leadingBaseCoefficient()).getVal())).isZERO()) {
                        continue;
                    } else {
                        GenPolynomialRing<MOD> genPolynomialRing = new GenPolynomialRing(cofac, fac.nvar, fac.tord, fac.getVars());
                        GenPolynomial<MOD> qm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) q);
                        if (qm.degreeVector().equals(qdegv)) {
                            GenPolynomial<MOD> rm = PolyUtil.fromIntegerCoefficients((GenPolynomialRing) genPolynomialRing, (GenPolynomial) r);
                            if (rm.degreeVector().equals(rdegv)) {
                                if (this.debug) {
                                    logger.info("cofac = " + cofac.getIntegerModul());
                                }
                                GenPolynomial cm = qm.gcd((GenPolynomial) rm);
                                if (cm.isConstant()) {
                                    logger.debug("cm, constant = " + cm);
                                    return fac.getONE().multiply((RingElem) c);
                                }
                                GenPolynomial<BigInteger> crq;
                                GenPolynomial<MOD> cmf;
                                GenPolynomial<MOD> sm;
                                GenPolynomial<MOD> tm;
                                GenPolynomial<MOD> rmf = rm.divide(cm);
                                GenPolynomial<MOD>[] ecm = cm.egcd((GenPolynomial) rmf);
                                if (ecm[0].isONE()) {
                                    crq = r;
                                    cmf = rmf;
                                    sm = ecm[1];
                                    tm = ecm[2];
                                } else {
                                    GenPolynomial<MOD> qmf = qm.divide(cm);
                                    ecm = cm.egcd((GenPolynomial) qmf);
                                    if (ecm[0].isONE()) {
                                        crq = q;
                                        cmf = qmf;
                                        sm = ecm[1];
                                        tm = ecm[2];
                                    } else {
                                        logger.info("both gcd != 1: Hensel not applicable");
                                        return this.iufd.baseGcd(P, S);
                                    }
                                }
                                BigInteger cn = ((BigInteger) crq.maxNorm()).multiply(((BigInteger) crq.leadingBaseCoefficient()).abs());
                                cn = cn.multiply(cn.fromInteger(2));
                                if (this.debug) {
                                    System.out.println("crq = " + crq);
                                    System.out.println("cm  = " + cm);
                                    System.out.println("cmf = " + cmf);
                                    System.out.println("sm  = " + sm);
                                    System.out.println("tm  = " + tm);
                                    System.out.println("cn  = " + cn);
                                }
                                try {
                                    HenselApprox<MOD> lift;
                                    if (this.quadratic) {
                                        lift = HenselUtil.liftHenselQuadratic(crq, cn, cm, cmf, sm, tm);
                                    } else {
                                        lift = HenselUtil.liftHensel(crq, cn, cm, cmf, sm, tm);
                                    }
                                    q = lift.A;
                                    if (this.debug) {
                                        System.out.println("q   = " + q);
                                        System.out.println("qf  = " + lift.B);
                                    }
                                    q = basePrimitivePart((GenPolynomial) q).multiply((RingElem) c).abs();
                                    if (PolyUtil.baseSparsePseudoRemainder(P, q).isZERO() && PolyUtil.baseSparsePseudoRemainder(S, q).isZERO()) {
                                        return q;
                                    }
                                    logger.info("final devision not successfull");
                                } catch (NoLiftingException nle) {
                                    logger.info("giving up on Hensel gcd reverting to Subres gcd " + nle);
                                    return this.iufd.baseGcd(P, S);
                                }
                            }
                            continue;
                        } else {
                            continue;
                        }
                    }
                }
            }
        }
        return q;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger>> recursiveUnivariateGcd(edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger>> r113, edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger>> r114) {
        /*
        r112 = this;
        if (r114 == 0) goto L_0x0008;
    L_0x0002:
        r107 = r114.isZERO();
        if (r107 == 0) goto L_0x000b;
    L_0x0008:
        r18 = r113;
    L_0x000a:
        return r18;
    L_0x000b:
        if (r113 == 0) goto L_0x0013;
    L_0x000d:
        r107 = r113.isZERO();
        if (r107 == 0) goto L_0x0016;
    L_0x0013:
        r18 = r114;
        goto L_0x000a;
    L_0x0016:
        r0 = r113;
        r0 = r0.ring;
        r107 = r0;
        r0 = r107;
        r0 = r0.nvar;
        r107 = r0;
        r108 = 1;
        r0 = r107;
        r1 = r108;
        if (r0 <= r1) goto L_0x004b;
    L_0x002a:
        r107 = new java.lang.IllegalArgumentException;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = r112.getClass();
        r109 = r109.getName();
        r108 = r108.append(r109);
        r109 = " no univariate polynomial";
        r108 = r108.append(r109);
        r108 = r108.toString();
        r107.<init>(r108);
        throw r107;
    L_0x004b:
        r107 = 0;
        r0 = r113;
        r1 = r107;
        r48 = r0.degree(r1);
        r107 = 0;
        r0 = r114;
        r1 = r107;
        r50 = r0.degree(r1);
        r107 = (r50 > r48 ? 1 : (r50 == r48 ? 0 : -1));
        if (r107 <= 0) goto L_0x0103;
    L_0x0063:
        r91 = r113;
        r82 = r114;
        r52 = r50;
        r50 = r48;
        r48 = r52;
    L_0x006d:
        r0 = r112;
        r0 = r0.debug;
        r107 = r0;
        if (r107 == 0) goto L_0x009f;
    L_0x0075:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "degrees: e = ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r48;
        r108 = r0.append(r1);
        r109 = ", f = ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r50;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.debug(r108);
    L_0x009f:
        r91 = r91.abs();
        r82 = r82.abs();
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r91;
        r19 = r0.recursiveContent(r1);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r82;
        r23 = r0.recursiveContent(r1);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r19;
        r2 = r23;
        r26 = r0.gcd(r1, r2);
        r0 = r91;
        r1 = r19;
        r91 = edu.jas.poly.PolyUtil.recursiveDivide(r0, r1);
        r0 = r82;
        r1 = r23;
        r82 = edu.jas.poly.PolyUtil.recursiveDivide(r0, r1);
        r0 = r19;
        r1 = r26;
        r19 = edu.jas.poly.PolyUtil.basePseudoDivide(r0, r1);
        r0 = r23;
        r1 = r26;
        r23 = edu.jas.poly.PolyUtil.basePseudoDivide(r0, r1);
        r107 = r91.isONE();
        if (r107 == 0) goto L_0x0109;
    L_0x00f9:
        r0 = r91;
        r1 = r26;
        r18 = r0.multiply(r1);
        goto L_0x000a;
    L_0x0103:
        r82 = r113;
        r91 = r114;
        goto L_0x006d;
    L_0x0109:
        r107 = r82.isONE();
        if (r107 == 0) goto L_0x0119;
    L_0x010f:
        r0 = r82;
        r1 = r26;
        r18 = r0.multiply(r1);
        goto L_0x000a;
    L_0x0119:
        r64 = r91.leadingBaseCoefficient();
        r64 = (edu.jas.poly.GenPolynomial) r64;
        r65 = r82.leadingBaseCoefficient();
        r65 = (edu.jas.poly.GenPolynomial) r65;
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r64;
        r2 = r65;
        r66 = r0.gcd(r1, r2);
        r107 = r66.isConstant();
        if (r107 != 0) goto L_0x01ad;
    L_0x013b:
        r0 = r112;
        r0 = r0.iufd;
        r107 = r0;
        r0 = r107;
        r1 = r91;
        r2 = r82;
        r18 = r0.recursiveUnivariateGcd(r1, r2);
        r107 = r18.abs();
        r0 = r107;
        r1 = r26;
        r18 = r0.multiply(r1);
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "non monic ldcf (";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r66;
        r108 = r0.append(r1);
        r109 = ") not implemented: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r18;
        r108 = r0.append(r1);
        r109 = "= gcd(";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r91;
        r108 = r0.append(r1);
        r109 = ",";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r82;
        r108 = r0.append(r1);
        r109 = ") * ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r26;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        goto L_0x000a;
    L_0x01ad:
        r90 = edu.jas.poly.PolyUtil.switchVariables(r82);
        r99 = edu.jas.poly.PolyUtil.switchVariables(r91);
        r0 = r90;
        r0 = r0.ring;
        r97 = r0;
        r0 = r97;
        r0 = r0.coFac;
        r98 = r0;
        r31 = r98;
        r31 = (edu.jas.poly.GenPolynomialRing) r31;
        r107 = r97.getVars();
        r0 = r31;
        r1 = r107;
        r44 = r0.extend(r1);
        r0 = r44;
        r1 = r90;
        r84 = edu.jas.poly.PolyUtil.distribute(r0, r1);
        r0 = r44;
        r1 = r99;
        r92 = edu.jas.poly.PolyUtil.distribute(r0, r1);
        r20 = r92.leadingBaseCoefficient();
        r20 = (edu.jas.arith.BigInteger) r20;
        r24 = r84.leadingBaseCoefficient();
        r24 = (edu.jas.arith.BigInteger) r24;
        r0 = r112;
        r1 = r20;
        r2 = r24;
        r27 = r0.gcd(r1, r2);
        r27 = (edu.jas.arith.BigInteger) r27;
        r81 = new edu.jas.arith.PrimeList;
        r107 = edu.jas.arith.PrimeList.Range.medium;
        r0 = r81;
        r1 = r107;
        r0.<init>(r1);
        r80 = r81.iterator();
        r79 = 50;
        r29 = 0;
        r62 = 0;
    L_0x020e:
        r107 = 11;
        r0 = r62;
        r1 = r107;
        if (r0 >= r1) goto L_0x0824;
    L_0x0216:
        r76 = 0;
        if (r62 != 0) goto L_0x0229;
    L_0x021a:
        r81 = new edu.jas.arith.PrimeList;
        r107 = edu.jas.arith.PrimeList.Range.medium;
        r0 = r81;
        r1 = r107;
        r0.<init>(r1);
        r80 = r81.iterator();
    L_0x0229:
        r107 = 4;
        r0 = r62;
        r1 = r107;
        if (r0 != r1) goto L_0x0258;
    L_0x0231:
        r81 = new edu.jas.arith.PrimeList;
        r107 = edu.jas.arith.PrimeList.Range.small;
        r0 = r81;
        r1 = r107;
        r0.<init>(r1);
        r80 = r81.iterator();
        r76 = r80.next();
        r76 = (java.math.BigInteger) r76;
        r76 = r80.next();
        r76 = (java.math.BigInteger) r76;
        r76 = r80.next();
        r76 = (java.math.BigInteger) r76;
        r76 = r80.next();
        r76 = (java.math.BigInteger) r76;
    L_0x0258:
        r107 = 9;
        r0 = r62;
        r1 = r107;
        if (r0 != r1) goto L_0x026f;
    L_0x0260:
        r81 = new edu.jas.arith.PrimeList;
        r107 = edu.jas.arith.PrimeList.Range.large;
        r0 = r81;
        r1 = r107;
        r0.<init>(r1);
        r80 = r81.iterator();
    L_0x026f:
        r35 = 0;
        r78 = 0;
    L_0x0273:
        r0 = r78;
        r1 = r79;
        if (r0 >= r1) goto L_0x0312;
    L_0x0279:
        r107 = r80.hasNext();
        if (r107 == 0) goto L_0x0312;
    L_0x027f:
        r76 = r80.next();
        r76 = (java.math.BigInteger) r76;
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "prime = ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r76;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        r30 = 0;
        r107 = edu.jas.arith.ModLongRing.MAX_LONG;
        r0 = r107;
        r1 = r76;
        r107 = r0.compareTo(r1);
        if (r107 <= 0) goto L_0x0370;
    L_0x02af:
        r30 = new edu.jas.arith.ModLongRing;
        r107 = 1;
        r0 = r30;
        r1 = r76;
        r2 = r107;
        r0.<init>(r1, r2);
    L_0x02bc:
        r107 = r27.getVal();
        r0 = r30;
        r1 = r107;
        r73 = r0.fromInteger(r1);
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r107 = r73.isZERO();
        if (r107 != 0) goto L_0x0273;
    L_0x02d0:
        r107 = r82.leadingBaseCoefficient();
        r107 = (edu.jas.poly.GenPolynomial) r107;
        r107 = r107.leadingBaseCoefficient();
        r107 = (edu.jas.arith.BigInteger) r107;
        r107 = r107.getVal();
        r0 = r30;
        r1 = r107;
        r73 = r0.fromInteger(r1);
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r107 = r73.isZERO();
        if (r107 != 0) goto L_0x0273;
    L_0x02f0:
        r107 = r91.leadingBaseCoefficient();
        r107 = (edu.jas.poly.GenPolynomial) r107;
        r107 = r107.leadingBaseCoefficient();
        r107 = (edu.jas.arith.BigInteger) r107;
        r107 = r107.getVal();
        r0 = r30;
        r1 = r107;
        r73 = r0.fromInteger(r1);
        r73 = (edu.jas.structure.GcdRingElem) r73;
        r107 = r73.isZERO();
        if (r107 != 0) goto L_0x0273;
    L_0x0310:
        r35 = r30;
    L_0x0312:
        if (r35 != 0) goto L_0x037f;
    L_0x0314:
        r0 = r112;
        r0 = r0.iufd;
        r107 = r0;
        r0 = r107;
        r1 = r82;
        r2 = r91;
        r18 = r0.recursiveUnivariateGcd(r1, r2);
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "no lucky prime, gave up on Hensel: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r18;
        r108 = r0.append(r1);
        r109 = "= gcd(";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r91;
        r108 = r0.append(r1);
        r109 = ",";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r82;
        r108 = r0.append(r1);
        r109 = ")";
        r108 = r108.append(r109);
        r108 = r108.toString();
        r107.info(r108);
        r107 = r18.abs();
        r0 = r107;
        r1 = r26;
        r18 = r0.multiply(r1);
        goto L_0x000a;
    L_0x0370:
        r30 = new edu.jas.arith.ModIntegerRing;
        r107 = 1;
        r0 = r30;
        r1 = r76;
        r2 = r107;
        r0.<init>(r1, r2);
        goto L_0x02bc;
    L_0x037f:
        r9 = new java.util.ArrayList;
        r0 = r113;
        r0 = r0.ring;
        r107 = r0;
        r0 = r107;
        r0 = r0.nvar;
        r107 = r0;
        r0 = r107;
        r9.<init>(r0);
        r33 = r44;
        r85 = r84;
        r93 = r92;
        r0 = r44;
        r0 = r0.nvar;
        r63 = r0;
    L_0x039e:
        r107 = 1;
        r0 = r63;
        r1 = r107;
        if (r0 <= r1) goto L_0x0400;
    L_0x03a6:
        r0 = r33;
        r0 = r0.nvar;
        r107 = r0;
        r107 = r107 + -2;
        r0 = r85;
        r1 = r107;
        r40 = r0.degree(r1);
        r0 = r33;
        r0 = r0.nvar;
        r107 = r0;
        r107 = r107 + -2;
        r0 = r93;
        r1 = r107;
        r42 = r0.degree(r1);
        r107 = 1;
        r0 = r33;
        r1 = r107;
        r33 = r0.contract(r1);
        r102 = 1;
        r108 = r76.longValue();
        r110 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r107 = (r108 > r110 ? 1 : (r108 == r110 ? 0 : -1));
        if (r107 <= 0) goto L_0x03de;
    L_0x03dc:
        r102 = 0;
    L_0x03de:
        r108 = 1;
        r104 = r102 + r108;
        r0 = r35;
        r1 = r102;
        r106 = r0.fromInteger(r1);
        r106 = (edu.jas.structure.GcdRingElem) r106;
        r107 = r106.isZERO();
        if (r107 == 0) goto L_0x0408;
    L_0x03f2:
        r108 = 1;
        r107 = (r104 > r108 ? 1 : (r104 == r108 ? 0 : -1));
        if (r107 == 0) goto L_0x0408;
    L_0x03f8:
        r85 = 0;
        r93 = 0;
    L_0x03fc:
        if (r85 != 0) goto L_0x0461;
    L_0x03fe:
        if (r93 != 0) goto L_0x0461;
    L_0x0400:
        if (r85 != 0) goto L_0x0465;
    L_0x0402:
        if (r93 != 0) goto L_0x0465;
    L_0x0404:
        r62 = r62 + 1;
        goto L_0x020e;
    L_0x0408:
        r101 = new edu.jas.arith.BigInteger;
        r108 = 1;
        r108 = r104 - r108;
        r0 = r101;
        r1 = r108;
        r0.<init>(r1);
        r0 = r33;
        r1 = r85;
        r2 = r101;
        r88 = edu.jas.poly.PolyUtil.evaluateMain(r0, r1, r2);
        r0 = r33;
        r1 = r93;
        r2 = r101;
        r96 = edu.jas.poly.PolyUtil.evaluateMain(r0, r1, r2);
        r0 = r33;
        r0 = r0.nvar;
        r107 = r0;
        r107 = r107 + -1;
        r0 = r88;
        r1 = r107;
        r108 = r0.degree(r1);
        r107 = (r40 > r108 ? 1 : (r40 == r108 ? 0 : -1));
        if (r107 == 0) goto L_0x0440;
    L_0x043d:
        r102 = r104;
        goto L_0x03de;
    L_0x0440:
        r0 = r33;
        r0 = r0.nvar;
        r107 = r0;
        r107 = r107 + -1;
        r0 = r96;
        r1 = r107;
        r108 = r0.degree(r1);
        r107 = (r42 > r108 ? 1 : (r42 == r108 ? 0 : -1));
        if (r107 == 0) goto L_0x0457;
    L_0x0454:
        r102 = r104;
        goto L_0x03de;
    L_0x0457:
        r0 = r101;
        r9.add(r0);
        r85 = r88;
        r93 = r96;
        goto L_0x03fc;
    L_0x0461:
        r63 = r63 + -1;
        goto L_0x039e;
    L_0x0465:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "evaluation points  = ";
        r108 = r108.append(r109);
        r0 = r108;
        r108 = r0.append(r9);
        r108 = r108.toString();
        r107.info(r108);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r85;
        r2 = r93;
        r28 = r0.baseGcd(r1, r2);
        r107 = r28.isConstant();
        if (r107 == 0) goto L_0x04a9;
    L_0x0495:
        r0 = r113;
        r0 = r0.ring;
        r107 = r0;
        r107 = r107.getONE();
        r0 = r107;
        r1 = r26;
        r18 = r0.multiply(r1);
        goto L_0x000a;
    L_0x04a9:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "base gcd = ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r28;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        if (r62 != 0) goto L_0x04cf;
    L_0x04c7:
        r86 = r85;
        r94 = r93;
        r29 = r28;
        goto L_0x0404;
    L_0x04cf:
        r107 = 0;
        r0 = r29;
        r1 = r107;
        r36 = r0.degree(r1);
        r107 = 0;
        r0 = r28;
        r1 = r107;
        r38 = r0.degree(r1);
        r107 = (r38 > r36 ? 1 : (r38 == r36 ? 0 : -1));
        if (r107 >= 0) goto L_0x04ef;
    L_0x04e7:
        r86 = r85;
        r94 = r93;
        r29 = r28;
        goto L_0x0404;
    L_0x04ef:
        r107 = (r38 > r36 ? 1 : (r38 == r36 ? 0 : -1));
        if (r107 > 0) goto L_0x0404;
    L_0x04f3:
        r107 = 0;
        r0 = r91;
        r1 = r107;
        r46 = r0.degree(r1);
        r107 = (r36 > r46 ? 1 : (r36 == r46 ? 0 : -1));
        if (r107 != 0) goto L_0x053b;
    L_0x0501:
        r0 = r82;
        r1 = r91;
        r107 = edu.jas.poly.PolyUtil.recursivePseudoRemainder(r0, r1);
        r107 = r107.isZERO();
        if (r107 == 0) goto L_0x0404;
    L_0x050f:
        r107 = r91.abs();
        r0 = r107;
        r1 = r26;
        r91 = r0.multiply(r1);
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "exit with r | q : ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r91;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        r18 = r91;
        goto L_0x000a;
    L_0x053b:
        r70 = 0;
        r0 = r93;
        r1 = r28;
        r95 = edu.jas.poly.PolyUtil.basePseudoDivide(r0, r1);
        r0 = r85;
        r1 = r28;
        r87 = edu.jas.poly.PolyUtil.basePseudoDivide(r0, r1);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r95;
        r2 = r28;
        r55 = r0.baseGcd(r1, r2);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r87;
        r2 = r28;
        r54 = r0.baseGcd(r1, r2);
        r107 = r55.isONE();
        if (r107 == 0) goto L_0x07b1;
    L_0x0573:
        r107 = r54.isONE();
        if (r107 == 0) goto L_0x07b1;
    L_0x0579:
        r108 = r64.totalDegree();
        r110 = r65.totalDegree();
        r107 = (r108 > r110 ? 1 : (r108 == r110 ? 0 : -1));
        if (r107 <= 0) goto L_0x0781;
    L_0x0585:
        r6 = r84;
        r100 = r82;
        r60 = r87;
        r77 = r85;
        r25 = r84.maxNorm();
        r25 = (edu.jas.arith.BigInteger) r25;
        r0 = r25;
        r1 = r27;
        r107 = r0.multiply(r1);
        r108 = new edu.jas.arith.BigInteger;
        r110 = 2;
        r0 = r108;
        r1 = r110;
        r0.<init>(r1);
        r70 = r107.multiply(r108);
        r45 = r65;
        r107 = logger;
        r108 = "select deg: ui = qd, g = b";
        r107.debug(r108);
    L_0x05b3:
        r69 = r66;
        r0 = r45;
        r1 = r69;
        r67 = edu.jas.poly.PolyUtil.basePseudoDivide(r0, r1);
        r0 = r45;
        r0 = r0.ring;
        r107 = r0;
        r0 = r107;
        r0 = r0.coFac;
        r107 = r0;
        r0 = r107;
        r1 = r69;
        r56 = edu.jas.poly.PolyUtil.evaluateAll(r0, r1, r9);
        r56 = (edu.jas.arith.BigInteger) r56;
        r107 = r56.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x05d9:
        r0 = r45;
        r0 = r0.ring;
        r107 = r0;
        r0 = r107;
        r0 = r0.coFac;
        r107 = r0;
        r0 = r107;
        r1 = r67;
        r57 = edu.jas.poly.PolyUtil.evaluateAll(r0, r1, r9);
        r57 = (edu.jas.arith.BigInteger) r57;
        r107 = r57.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x05f5:
        r0 = r45;
        r0 = r0.ring;
        r107 = r0;
        r0 = r107;
        r0 = r0.coFac;
        r107 = r0;
        r0 = r107;
        r1 = r45;
        r58 = edu.jas.poly.PolyUtil.evaluateAll(r0, r1, r9);
        r58 = (edu.jas.arith.BigInteger) r58;
        r107 = r58.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x0611:
        r0 = r60;
        r1 = r56;
        r60 = r0.multiply(r1);
        r107 = 0;
        r108 = 0;
        r0 = r69;
        r1 = r44;
        r2 = r107;
        r3 = r108;
        r59 = r0.extendLower(r1, r2, r3);
        r0 = r59;
        r6 = r6.multiply(r0);
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "gcd(ldcf): ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r69;
        r108 = r0.append(r1);
        r109 = ", ldcf cofactor: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r67;
        r108 = r0.append(r1);
        r109 = ", base cofactor: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r60;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        r107 = new edu.jas.arith.BigInteger;
        r0 = r107;
        r1 = r76;
        r0.<init>(r1);
        r0 = r107;
        r1 = r70;
        r10 = edu.jas.structure.Power.logarithm(r0, r1);
        r107 = r35.getIntegerModul();
        r0 = r107;
        r89 = edu.jas.structure.Power.positivePower(r0, r10);
        r89 = (edu.jas.arith.BigInteger) r89;
        r107 = edu.jas.arith.ModLongRing.MAX_LONG;
        r108 = r89.getVal();
        r107 = r107.compareTo(r108);
        if (r107 <= 0) goto L_0x0880;
    L_0x0690:
        r72 = new edu.jas.arith.ModLongRing;
        r107 = r89.getVal();
        r108 = 1;
        r0 = r72;
        r1 = r107;
        r2 = r108;
        r0.<init>(r1, r2);
    L_0x06a1:
        r71 = new edu.jas.poly.GenPolynomialRing;
        r0 = r71;
        r1 = r72;
        r2 = r33;
        r0.<init>(r1, r2);
        r107 = r56.getVal();
        r0 = r72;
        r1 = r107;
        r107 = r0.fromInteger(r1);
        r107 = (edu.jas.structure.GcdRingElem) r107;
        r107 = r107.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x06c0:
        r0 = r71;
        r1 = r28;
        r34 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r0 = r71;
        r1 = r60;
        r61 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r1);
        r107 = 0;
        r0 = r34;
        r1 = r107;
        r108 = r0.degree(r1);
        r107 = 0;
        r0 = r28;
        r1 = r107;
        r110 = r0.degree(r1);
        r107 = (r108 > r110 ? 1 : (r108 == r110 ? 0 : -1));
        if (r107 != 0) goto L_0x0404;
    L_0x06e8:
        r107 = 0;
        r0 = r61;
        r1 = r107;
        r108 = r0.degree(r1);
        r107 = 0;
        r0 = r60;
        r1 = r107;
        r110 = r0.degree(r1);
        r107 = (r108 > r110 ? 1 : (r108 == r110 ? 0 : -1));
        if (r107 != 0) goto L_0x0404;
    L_0x0700:
        r107 = r34.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x0706:
        r107 = r61.isZERO();
        if (r107 != 0) goto L_0x0404;
    L_0x070c:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "univariate modulo p^k: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r34;
        r108 = r0.append(r1);
        r109 = ", ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r61;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        r83 = new edu.jas.poly.GenPolynomialRing;
        r0 = r83;
        r1 = r72;
        r2 = r44;
        r0.<init>(r1, r2);
        r0 = r83;
        r7 = edu.jas.poly.PolyUtil.fromIntegerCoefficients(r0, r6);
        r107 = r6.leadingExpVector();
        r108 = r7.leadingExpVector();
        r107 = r107.equals(r108);
        if (r107 != 0) goto L_0x0893;
    L_0x0755:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "ev(ui) = ";
        r108 = r108.append(r109);
        r109 = r6.leadingExpVector();
        r108 = r108.append(r109);
        r109 = ", ev(uq) = ";
        r108 = r108.append(r109);
        r109 = r7.leadingExpVector();
        r108 = r108.append(r109);
        r108 = r108.toString();
        r107.info(r108);
        goto L_0x0404;
    L_0x0781:
        r6 = r92;
        r100 = r91;
        r60 = r95;
        r77 = r93;
        r22 = r92.maxNorm();
        r22 = (edu.jas.arith.BigInteger) r22;
        r0 = r22;
        r1 = r27;
        r107 = r0.multiply(r1);
        r108 = new edu.jas.arith.BigInteger;
        r110 = 2;
        r0 = r108;
        r1 = r110;
        r0.<init>(r1);
        r70 = r107.multiply(r108);
        r45 = r64;
        r107 = logger;
        r108 = "select deg: ui = rd, g = a";
        r107.debug(r108);
        goto L_0x05b3;
    L_0x07b1:
        r107 = r55.isONE();
        if (r107 == 0) goto L_0x07e7;
    L_0x07b7:
        r6 = r92;
        r100 = r91;
        r60 = r95;
        r77 = r93;
        r22 = r92.maxNorm();
        r22 = (edu.jas.arith.BigInteger) r22;
        r0 = r22;
        r1 = r27;
        r107 = r0.multiply(r1);
        r108 = new edu.jas.arith.BigInteger;
        r110 = 2;
        r0 = r108;
        r1 = r110;
        r0.<init>(r1);
        r70 = r107.multiply(r108);
        r45 = r64;
        r107 = logger;
        r108 = "select: ui = rd, g = a";
        r107.debug(r108);
        goto L_0x05b3;
    L_0x07e7:
        r107 = r54.isONE();
        if (r107 == 0) goto L_0x081d;
    L_0x07ed:
        r6 = r84;
        r100 = r82;
        r60 = r87;
        r77 = r85;
        r25 = r84.maxNorm();
        r25 = (edu.jas.arith.BigInteger) r25;
        r0 = r25;
        r1 = r27;
        r107 = r0.multiply(r1);
        r108 = new edu.jas.arith.BigInteger;
        r110 = 2;
        r0 = r108;
        r1 = r110;
        r0.<init>(r1);
        r70 = r107.multiply(r108);
        r45 = r65;
        r107 = logger;
        r108 = "select: ui = qd, g = b";
        r107.debug(r108);
        goto L_0x05b3;
    L_0x081d:
        r107 = logger;
        r108 = "both gcds != 1: method not applicable";
        r107.info(r108);
    L_0x0824:
        r0 = r112;
        r0 = r0.iufd;
        r107 = r0;
        r0 = r107;
        r1 = r91;
        r2 = r82;
        r18 = r0.recursiveUnivariateGcd(r1, r2);
        r107 = r18.abs();
        r0 = r107;
        r1 = r26;
        r18 = r0.multiply(r1);
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "no lucky prime or evaluation points, gave up on Hensel: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r18;
        r108 = r0.append(r1);
        r109 = "= gcd(";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r91;
        r108 = r0.append(r1);
        r109 = ",";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r82;
        r108 = r0.append(r1);
        r109 = ")";
        r108 = r108.append(r109);
        r108 = r108.toString();
        r107.info(r108);
        goto L_0x000a;
    L_0x0880:
        r72 = new edu.jas.arith.ModIntegerRing;
        r107 = r89.getVal();
        r108 = 1;
        r0 = r72;
        r1 = r107;
        r2 = r108;
        r0.<init>(r1, r2);
        goto L_0x06a1;
    L_0x0893:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "multivariate modulo p^k: ";
        r108 = r108.append(r109);
        r0 = r108;
        r108 = r0.append(r7);
        r108 = r108.toString();
        r107.info(r108);
        r8 = new java.util.ArrayList;
        r107 = 2;
        r0 = r107;
        r8.<init>(r0);
        r0 = r34;
        r8.add(r0);
        r0 = r61;
        r8.add(r0);
        r12 = new java.util.ArrayList;
        r107 = 2;
        r0 = r107;
        r12.<init>(r0);
        r0 = r69;
        r0 = r0.ring;
        r107 = r0;
        r107 = r107.getONE();
        r0 = r107;
        r12.add(r0);
        r0 = r69;
        r0 = r0.ring;
        r107 = r0;
        r107 = r107.getONE();
        r0 = r107;
        r12.add(r0);
        r68 = edu.jas.ufd.HenselMultUtil.liftHensel(r6, r7, r8, r9, r10, r12);	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r107 = logger;	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r108 = new java.lang.StringBuilder;	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r108.<init>();	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r109 = "lift = ";
        r108 = r108.append(r109);	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r0 = r108;
        r1 = r68;
        r108 = r0.append(r1);	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r108 = r108.toString();	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r107.info(r108);	 Catch:{ NoLiftingException -> 0x09cd, ArithmeticException -> 0x09d7, NotInvertibleException -> 0x09e1 }
        r107 = 0;
        r0 = r68;
        r1 = r107;
        r107 = r0.get(r1);
        r107 = (edu.jas.poly.GenPolynomial) r107;
        r0 = r44;
        r1 = r107;
        r32 = edu.jas.poly.PolyUtil.integerFromModularCoefficients(r0, r1);
        r0 = r112;
        r1 = r32;
        r32 = r0.basePrimitivePart(r1);
        r0 = r97;
        r1 = r32;
        r13 = edu.jas.poly.PolyUtil.recursive(r0, r1);
        r14 = edu.jas.poly.PolyUtil.switchVariables(r13);
        r0 = r14.ring;
        r107 = r0;
        r0 = r113;
        r0 = r0.ring;
        r108 = r0;
        r107 = r107.equals(r108);
        if (r107 != 0) goto L_0x096b;
    L_0x093f:
        r107 = java.lang.System.out;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "Cs.ring = ";
        r108 = r108.append(r109);
        r0 = r14.ring;
        r109 = r0;
        r108 = r108.append(r109);
        r109 = ", P.ring = ";
        r108 = r108.append(r109);
        r0 = r113;
        r0 = r0.ring;
        r109 = r0;
        r108 = r108.append(r109);
        r108 = r108.toString();
        r107.println(r108);
    L_0x096b:
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r16 = r0.recursivePrimitivePart(r14);
        r0 = r112;
        r0 = r0.ufd;
        r107 = r0;
        r0 = r107;
        r1 = r16;
        r16 = r0.baseRecursivePrimitivePart(r1);
        r107 = r16.abs();
        r0 = r107;
        r1 = r26;
        r16 = r0.multiply(r1);
        r0 = r113;
        r1 = r16;
        r15 = edu.jas.poly.PolyUtil.recursivePseudoRemainder(r0, r1);
        r0 = r114;
        r1 = r16;
        r17 = edu.jas.poly.PolyUtil.recursivePseudoRemainder(r0, r1);
        r107 = r15.isZERO();
        if (r107 == 0) goto L_0x09eb;
    L_0x09a7:
        r107 = r17.isZERO();
        if (r107 == 0) goto L_0x09eb;
    L_0x09ad:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "gcd normal exit: ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r16;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        r18 = r16;
        goto L_0x000a;
    L_0x09cd:
        r75 = move-exception;
        r107 = logger;
        r108 = "NoLiftingException";
        r107.info(r108);
        goto L_0x0404;
    L_0x09d7:
        r21 = move-exception;
        r107 = logger;
        r108 = "ArithmeticException";
        r107.info(r108);
        goto L_0x0404;
    L_0x09e1:
        r74 = move-exception;
        r107 = logger;
        r108 = "NotInvertibleException";
        r107.info(r108);
        goto L_0x0404;
    L_0x09eb:
        r107 = logger;
        r108 = new java.lang.StringBuilder;
        r108.<init>();
        r109 = "bad Q = ";
        r108 = r108.append(r109);
        r0 = r108;
        r1 = r16;
        r108 = r0.append(r1);
        r108 = r108.toString();
        r107.info(r108);
        goto L_0x0404;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.GreatestCommonDivisorHensel.recursiveUnivariateGcd(edu.jas.poly.GenPolynomial, edu.jas.poly.GenPolynomial):edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<edu.jas.arith.BigInteger>>");
    }

    GenPolynomial<BigInteger> invertPoly(ModularRingFactory<MOD> mfac, GenPolynomial<BigInteger> li, List<BigInteger> V) {
        if (li == null || li.isZERO()) {
            throw new RuntimeException("li not invertible: " + li);
        } else if (li.isONE()) {
            return li;
        } else {
            GenPolynomialRing pfac = li.ring;
            GenPolynomialRing mpfac = new GenPolynomialRing((RingFactory) mfac, pfac);
            GenPolynomial<MOD> lm = PolyUtil.fromIntegerCoefficients(mpfac, (GenPolynomial) li);
            List<GenPolynomial<MOD>> lid = new ArrayList(V.size());
            int i = 0;
            for (BigInteger bi : V) {
                lid.add(mpfac.univariate(i).subtract((GcdRingElem) mfac.fromInteger(bi.getVal())));
                i++;
            }
            return PolyUtil.integerFromModularCoefficients(pfac, (GenPolynomial) lm);
        }
    }
}
