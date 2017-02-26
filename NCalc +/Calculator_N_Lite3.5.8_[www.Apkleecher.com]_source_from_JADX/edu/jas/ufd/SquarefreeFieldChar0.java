package edu.jas.ufd;

import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public class SquarefreeFieldChar0<C extends GcdRingElem<C>> extends SquarefreeAbstract<C> {
    private static final Logger logger;
    protected final RingFactory<C> coFac;

    static {
        logger = Logger.getLogger(SquarefreeFieldChar0.class);
    }

    public SquarefreeFieldChar0(RingFactory<C> fac) {
        super(GCDFactory.getProxy((RingFactory) fac));
        if (!fac.isField()) {
            throw new IllegalArgumentException("fac must be a field");
        } else if (fac.characteristic().signum() != 0) {
            throw new IllegalArgumentException("characterisic(fac) must be zero");
        } else {
            this.coFac = fac;
        }
    }

    public String toString() {
        return getClass().getName() + " with " + this.engine + " over " + this.coFac;
    }

    public GenPolynomial<C> baseSquarefreePart(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
        }
        GenPolynomial<C> pp = P.monic();
        if (pp.isConstant()) {
            return pp;
        }
        return PolyUtil.basePseudoDivide(pp, this.engine.baseGcd(pp, PolyUtil.baseDeriviative(pp).monic()).monic()).monic();
    }

    public boolean isBaseSquarefree(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return true;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
        }
        GenPolynomial<C> pp = P.monic();
        if (pp.isConstant()) {
            return true;
        }
        return this.engine.baseGcd(pp, PolyUtil.baseDeriviative(pp).monic()).monic().isONE();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long> baseSquarefreeFactors(edu.jas.poly.GenPolynomial<C> r21) {
        /*
        r20 = this;
        r13 = new java.util.TreeMap;
        r13.<init>();
        if (r21 == 0) goto L_0x000d;
    L_0x0007:
        r15 = r21.isZERO();
        if (r15 == 0) goto L_0x000e;
    L_0x000d:
        return r13;
    L_0x000e:
        r15 = r21.isConstant();
        if (r15 == 0) goto L_0x0020;
    L_0x0014:
        r16 = 1;
        r15 = java.lang.Long.valueOf(r16);
        r0 = r21;
        r13.put(r0, r15);
        goto L_0x000d;
    L_0x0020:
        r0 = r21;
        r12 = r0.ring;
        r15 = r12.nvar;
        r16 = 1;
        r0 = r16;
        if (r15 <= r0) goto L_0x004d;
    L_0x002c:
        r15 = new java.lang.IllegalArgumentException;
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r17 = r20.getClass();
        r17 = r17.getName();
        r16 = r16.append(r17);
        r17 = " only for univariate polynomials";
        r16 = r16.append(r17);
        r16 = r16.toString();
        r15.<init>(r16);
        throw r15;
    L_0x004d:
        r9 = r21.leadingBaseCoefficient();
        r9 = (edu.jas.structure.GcdRingElem) r9;
        r15 = r9.isONE();
        if (r15 != 0) goto L_0x0078;
    L_0x0059:
        r0 = r21;
        r21 = r0.divide(r9);
        r15 = r12.getONE();
        r7 = r15.multiply(r9);
        r16 = 1;
        r15 = java.lang.Long.valueOf(r16);
        r13.put(r7, r15);
        r15 = r12.coFac;
        r9 = r15.getONE();
        r9 = (edu.jas.structure.GcdRingElem) r9;
    L_0x0078:
        r3 = r21;
        r2 = 0;
        r5 = 0;
        r10 = 0;
        r8 = 1;
    L_0x007f:
        if (r8 == 0) goto L_0x00ac;
    L_0x0081:
        r15 = r3.isConstant();
        if (r15 != 0) goto L_0x008d;
    L_0x0087:
        r15 = r3.isZERO();
        if (r15 == 0) goto L_0x0095;
    L_0x008d:
        r0 = r20;
        r13 = r0.normalizeFactorization(r13);
        goto L_0x000d;
    L_0x0095:
        r4 = edu.jas.poly.PolyUtil.baseDeriviative(r3);
        r0 = r20;
        r15 = r0.engine;
        r2 = r15.baseGcd(r3, r4);
        r2 = r2.monic();
        r5 = edu.jas.poly.PolyUtil.basePseudoDivide(r3, r2);
        r10 = 0;
        r8 = 0;
    L_0x00ac:
        r15 = r5.isConstant();
        if (r15 != 0) goto L_0x008d;
    L_0x00b2:
        r16 = 1;
        r10 = r10 + r16;
        r0 = r20;
        r15 = r0.engine;
        r6 = r15.baseGcd(r2, r5);
        r6 = r6.monic();
        r14 = edu.jas.poly.PolyUtil.basePseudoDivide(r5, r6);
        r5 = r6;
        r2 = edu.jas.poly.PolyUtil.basePseudoDivide(r2, r5);
        r15 = 0;
        r16 = r14.degree(r15);
        r18 = 0;
        r15 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r15 <= 0) goto L_0x007f;
    L_0x00d6:
        r15 = r9.isONE();
        if (r15 == 0) goto L_0x0106;
    L_0x00dc:
        r15 = r14.leadingBaseCoefficient();
        r15 = (edu.jas.structure.GcdRingElem) r15;
        r15 = r15.isONE();
        if (r15 != 0) goto L_0x0106;
    L_0x00e8:
        r14 = r14.monic();
        r15 = logger;
        r16 = new java.lang.StringBuilder;
        r16.<init>();
        r17 = "z,monic = ";
        r16 = r16.append(r17);
        r0 = r16;
        r16 = r0.append(r14);
        r16 = r16.toString();
        r15.info(r16);
    L_0x0106:
        r15 = java.lang.Long.valueOf(r10);
        r13.put(r14, r15);
        goto L_0x007f;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeFieldChar0.baseSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long>");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateSquarefreePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for multivariate polynomials");
        }
        GenPolynomial<GenPolynomial<C>> pp = P;
        RingElem Pc = this.engine.recursiveContent(P).monic();
        if (!Pc.isONE()) {
            pp = PolyUtil.coefficientPseudoDivide(pp, Pc);
        }
        if (pp.leadingExpVector().getVal(0) < 1) {
            return pp.multiply(Pc);
        }
        return PolyUtil.monic(PolyUtil.recursivePseudoDivide(pp, PolyUtil.monic(this.engine.recursiveUnivariateGcd(pp, PolyUtil.recursiveDeriviative(pp))))).multiply(Pc);
    }

    public boolean isRecursiveUnivariateSquarefree(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return true;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for multivariate polynomials");
        }
        GenPolynomial<GenPolynomial<C>> pp = P;
        GenPolynomial<C> Pc = this.engine.recursiveContent(P);
        if (logger.isInfoEnabled()) {
            logger.info("recursiveContent = " + Pc);
        }
        if (!isSquarefree(Pc)) {
            return false;
        }
        Pc = Pc.monic();
        if (!Pc.isONE()) {
            pp = PolyUtil.coefficientPseudoDivide(pp, Pc);
        }
        if (pp.leadingExpVector().getVal(0) <= 1) {
            return true;
        }
        GenPolynomial g = this.engine.recursiveUnivariateGcd(pp, PolyUtil.recursiveDeriviative(pp));
        if (logger.isInfoEnabled()) {
            logger.info("gcd = " + g);
        }
        return PolyUtil.monic(g).isONE();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long> recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>> r31) {
        /*
        r30 = this;
        r25 = new java.util.TreeMap;
        r25.<init>();
        if (r31 == 0) goto L_0x000d;
    L_0x0007:
        r27 = r31.isZERO();
        if (r27 == 0) goto L_0x000e;
    L_0x000d:
        return r25;
    L_0x000e:
        r0 = r31;
        r0 = r0.ring;
        r21 = r0;
        r0 = r21;
        r0 = r0.nvar;
        r27 = r0;
        r28 = 1;
        r0 = r27;
        r1 = r28;
        if (r0 <= r1) goto L_0x0043;
    L_0x0022:
        r27 = new java.lang.IllegalArgumentException;
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = r30.getClass();
        r29 = r29.getName();
        r28 = r28.append(r29);
        r29 = " only for univariate polynomials";
        r28 = r28.append(r29);
        r28 = r28.toString();
        r27.<init>(r28);
        throw r27;
    L_0x0043:
        r0 = r21;
        r11 = r0.coFac;
        r11 = (edu.jas.poly.GenPolynomialRing) r11;
        r27 = r31.leadingBaseCoefficient();
        r27 = (edu.jas.poly.GenPolynomial) r27;
        r18 = r27.leadingBaseCoefficient();
        r18 = (edu.jas.structure.GcdRingElem) r18;
        r27 = r18.isONE();
        if (r27 != 0) goto L_0x00a6;
    L_0x005b:
        r27 = r11.getONE();
        r0 = r27;
        r1 = r18;
        r15 = r0.multiply(r1);
        r27 = r21.getONE();
        r0 = r27;
        r22 = r0.multiply(r15);
        r28 = 1;
        r27 = java.lang.Long.valueOf(r28);
        r0 = r25;
        r1 = r22;
        r2 = r27;
        r0.put(r1, r2);
        r19 = r18.inverse();
        r19 = (edu.jas.structure.GcdRingElem) r19;
        r27 = r11.getONE();
        r0 = r27;
        r1 = r19;
        r27 = r0.multiply(r1);
        r0 = r31;
        r1 = r27;
        r31 = r0.multiply(r1);
        r27 = r31.leadingBaseCoefficient();
        r27 = (edu.jas.poly.GenPolynomial) r27;
        r18 = r27.leadingBaseCoefficient();
        r18 = (edu.jas.structure.GcdRingElem) r18;
    L_0x00a6:
        r0 = r30;
        r0 = r0.engine;
        r27 = r0;
        r0 = r27;
        r1 = r31;
        r4 = r0.recursiveContent(r1);
        r27 = logger;
        r27 = r27.isInfoEnabled();
        if (r27 == 0) goto L_0x00d6;
    L_0x00bc:
        r27 = logger;
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "recursiveContent = ";
        r28 = r28.append(r29);
        r0 = r28;
        r28 = r0.append(r4);
        r28 = r28.toString();
        r27.info(r28);
    L_0x00d6:
        r4 = r4.monic();
        r27 = r4.isONE();
        if (r27 != 0) goto L_0x00e6;
    L_0x00e0:
        r0 = r31;
        r31 = edu.jas.poly.PolyUtil.coefficientPseudoDivide(r0, r4);
    L_0x00e6:
        r0 = r30;
        r24 = r0.squarefreeFactors(r4);
        r27 = logger;
        r27 = r27.isInfoEnabled();
        if (r27 == 0) goto L_0x0110;
    L_0x00f4:
        r27 = logger;
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "squarefreeFactors = ";
        r28 = r28.append(r29);
        r0 = r28;
        r1 = r24;
        r28 = r0.append(r1);
        r28 = r28.toString();
        r27.info(r28);
    L_0x0110:
        r27 = r24.entrySet();
        r13 = r27.iterator();
    L_0x0118:
        r27 = r13.hasNext();
        if (r27 == 0) goto L_0x0148;
    L_0x011e:
        r20 = r13.next();
        r20 = (java.util.Map.Entry) r20;
        r10 = r20.getKey();
        r10 = (edu.jas.poly.GenPolynomial) r10;
        r27 = r10.isONE();
        if (r27 != 0) goto L_0x0118;
    L_0x0130:
        r27 = r21.getONE();
        r0 = r27;
        r12 = r0.multiply(r10);
        r23 = r20.getValue();
        r23 = (java.lang.Long) r23;
        r0 = r25;
        r1 = r23;
        r0.put(r12, r1);
        goto L_0x0118;
    L_0x0148:
        r6 = r31;
        r5 = 0;
        r8 = 0;
        r16 = 0;
        r14 = 1;
    L_0x014f:
        if (r14 == 0) goto L_0x0178;
    L_0x0151:
        r27 = r6.isConstant();
        if (r27 != 0) goto L_0x000d;
    L_0x0157:
        r27 = r6.isZERO();
        if (r27 != 0) goto L_0x000d;
    L_0x015d:
        r7 = edu.jas.poly.PolyUtil.recursiveDeriviative(r6);
        r0 = r30;
        r0 = r0.engine;
        r27 = r0;
        r0 = r27;
        r5 = r0.recursiveUnivariateGcd(r6, r7);
        r5 = edu.jas.poly.PolyUtil.monic(r5);
        r8 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r6, r5);
        r16 = 0;
        r14 = 0;
    L_0x0178:
        r27 = r8.isConstant();
        if (r27 != 0) goto L_0x000d;
    L_0x017e:
        r28 = 1;
        r16 = r16 + r28;
        r0 = r30;
        r0 = r0.engine;
        r27 = r0;
        r0 = r27;
        r9 = r0.recursiveUnivariateGcd(r5, r8);
        r9 = edu.jas.poly.PolyUtil.monic(r9);
        r26 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r8, r9);
        r8 = r9;
        r5 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r5, r8);
        r27 = r26.isONE();
        if (r27 != 0) goto L_0x014f;
    L_0x01a1:
        r27 = r26.isZERO();
        if (r27 != 0) goto L_0x014f;
    L_0x01a7:
        r27 = r18.isONE();
        if (r27 == 0) goto L_0x01cd;
    L_0x01ad:
        r26 = edu.jas.poly.PolyUtil.monic(r26);
        r27 = logger;
        r28 = new java.lang.StringBuilder;
        r28.<init>();
        r29 = "z,monic = ";
        r28 = r28.append(r29);
        r0 = r28;
        r1 = r26;
        r28 = r0.append(r1);
        r28 = r28.toString();
        r27.info(r28);
    L_0x01cd:
        r27 = java.lang.Long.valueOf(r16);
        r25.put(r26, r27);
        goto L_0x014f;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeFieldChar0.recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long>");
    }

    public GenPolynomial<C> squarefreePart(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomialRing pfac = P.ring;
            if (pfac.nvar <= 1) {
                return baseSquarefreePart(P);
            }
            GenPolynomial<GenPolynomial<C>> Pr = PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P);
            GenPolynomial<C> Pc = this.engine.recursiveContent(Pr);
            Pr = PolyUtil.coefficientPseudoDivide(Pr, Pc);
            RingElem Ps = squarefreePart(Pc);
            if (logger.isInfoEnabled()) {
                logger.info("content = " + Pc + ", squarefreePart = " + Ps);
            }
            GenPolynomial<GenPolynomial<C>> PP = recursiveUnivariateSquarefreePart(Pr);
            GenPolynomial<C> D = PolyUtil.distribute(pfac, PP.multiply(Ps));
            if (logger.isInfoEnabled()) {
                logger.info("univRec = " + Pr + ", squarefreePart = " + PP);
            }
            return D;
        }
    }

    public boolean isSquarefree(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return true;
        } else {
            GenPolynomialRing<C> pfac = P.ring;
            if (pfac.nvar <= 1) {
                return isBaseSquarefree(P);
            }
            return isRecursiveUnivariateSquarefree(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P));
        }
    }

    public SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return normalizeFactorization(baseSquarefreeFactors(P));
        }
        SortedMap<GenPolynomial<C>, Long> sfactors = new TreeMap();
        if (P.isZERO()) {
            return normalizeFactorization(sfactors);
        }
        if (P.isONE()) {
            sfactors.put(P, Long.valueOf(1));
            return normalizeFactorization(sfactors);
        }
        for (Entry<GenPolynomial<GenPolynomial<C>>, Long> m : recursiveUnivariateSquarefreeFactors(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P)).entrySet()) {
            sfactors.put(PolyUtil.distribute(pfac, (GenPolynomial) m.getKey()), (Long) m.getValue());
        }
        if (logger.isInfoEnabled()) {
            logger.info("squarefreeFactors(" + P + ") = " + sfactors);
        }
        return normalizeFactorization(sfactors);
    }

    public SortedMap<C, Long> squarefreeFactors(C c) {
        throw new UnsupportedOperationException("method not implemented");
    }
}
