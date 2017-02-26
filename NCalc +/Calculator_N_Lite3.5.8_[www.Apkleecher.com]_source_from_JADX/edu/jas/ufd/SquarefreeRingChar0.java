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

public class SquarefreeRingChar0<C extends GcdRingElem<C>> extends SquarefreeAbstract<C> {
    private static final Logger logger;
    protected final RingFactory<C> coFac;

    static {
        logger = Logger.getLogger(SquarefreeRingChar0.class);
    }

    public SquarefreeRingChar0(RingFactory<C> fac) {
        super(GCDFactory.getProxy((RingFactory) fac));
        if (fac.isField()) {
            throw new IllegalArgumentException("fac is a field: use SquarefreeFieldChar0");
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
        GenPolynomial<C> pp = this.engine.basePrimitivePart((GenPolynomial) P);
        if (pp.isConstant()) {
            return pp;
        }
        return this.engine.basePrimitivePart(PolyUtil.basePseudoDivide(pp, this.engine.basePrimitivePart(this.engine.baseGcd(pp, this.engine.basePrimitivePart(PolyUtil.baseDeriviative(pp))))));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long> baseSquarefreeFactors(edu.jas.poly.GenPolynomial<C> r21) {
        /*
        r20 = this;
        r14 = new java.util.TreeMap;
        r14.<init>();
        if (r21 == 0) goto L_0x000d;
    L_0x0007:
        r16 = r21.isZERO();
        if (r16 == 0) goto L_0x000e;
    L_0x000d:
        return r14;
    L_0x000e:
        r16 = r21.isConstant();
        if (r16 == 0) goto L_0x0022;
    L_0x0014:
        r16 = 1;
        r16 = java.lang.Long.valueOf(r16);
        r0 = r21;
        r1 = r16;
        r14.put(r0, r1);
        goto L_0x000d;
    L_0x0022:
        r0 = r21;
        r13 = r0.ring;
        r0 = r13.nvar;
        r16 = r0;
        r17 = 1;
        r0 = r16;
        r1 = r17;
        if (r0 <= r1) goto L_0x0053;
    L_0x0032:
        r16 = new java.lang.IllegalArgumentException;
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r18 = r20.getClass();
        r18 = r18.getName();
        r17 = r17.append(r18);
        r18 = " only for univariate polynomials";
        r17 = r17.append(r18);
        r17 = r17.toString();
        r16.<init>(r17);
        throw r16;
    L_0x0053:
        r12 = r21.leadingBaseCoefficient();
        r12 = (edu.jas.structure.GcdRingElem) r12;
        r16 = r12.isONE();
        if (r16 != 0) goto L_0x0088;
    L_0x005f:
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r1 = r21;
        r7 = r0.baseContent(r1);
        r0 = r21;
        r21 = r0.divide(r7);
        r16 = r13.getONE();
        r0 = r16;
        r8 = r0.multiply(r7);
        r16 = 1;
        r16 = java.lang.Long.valueOf(r16);
        r0 = r16;
        r14.put(r8, r0);
    L_0x0088:
        r3 = r21;
        r2 = 0;
        r5 = 0;
        r10 = 0;
        r9 = 1;
    L_0x008f:
        if (r9 == 0) goto L_0x00c8;
    L_0x0091:
        r16 = r3.isConstant();
        if (r16 != 0) goto L_0x009d;
    L_0x0097:
        r16 = r3.isZERO();
        if (r16 == 0) goto L_0x00a5;
    L_0x009d:
        r0 = r20;
        r14 = r0.normalizeFactorization(r14);
        goto L_0x000d;
    L_0x00a5:
        r4 = edu.jas.poly.PolyUtil.baseDeriviative(r3);
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r2 = r0.baseGcd(r3, r4);
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r2 = r0.basePrimitivePart(r2);
        r5 = edu.jas.poly.PolyUtil.basePseudoDivide(r3, r2);
        r10 = 0;
        r9 = 0;
    L_0x00c8:
        r16 = r5.isConstant();
        if (r16 != 0) goto L_0x009d;
    L_0x00ce:
        r16 = 1;
        r10 = r10 + r16;
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r6 = r0.baseGcd(r2, r5);
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r6 = r0.basePrimitivePart(r6);
        r15 = edu.jas.poly.PolyUtil.basePseudoDivide(r5, r6);
        r5 = r6;
        r2 = edu.jas.poly.PolyUtil.basePseudoDivide(r2, r5);
        r16 = 0;
        r16 = r15.degree(r16);
        r18 = 0;
        r16 = (r16 > r18 ? 1 : (r16 == r18 ? 0 : -1));
        if (r16 <= 0) goto L_0x008f;
    L_0x00ff:
        r16 = r12.isONE();
        if (r16 == 0) goto L_0x0137;
    L_0x0105:
        r16 = r15.leadingBaseCoefficient();
        r16 = (edu.jas.structure.GcdRingElem) r16;
        r16 = r16.isONE();
        if (r16 != 0) goto L_0x0137;
    L_0x0111:
        r0 = r20;
        r0 = r0.engine;
        r16 = r0;
        r0 = r16;
        r15 = r0.basePrimitivePart(r15);
        r16 = logger;
        r17 = new java.lang.StringBuilder;
        r17.<init>();
        r18 = "z,pp = ";
        r17 = r17.append(r18);
        r0 = r17;
        r17 = r0.append(r15);
        r17 = r17.toString();
        r16.info(r17);
    L_0x0137:
        r16 = java.lang.Long.valueOf(r10);
        r14.put(r15, r16);
        goto L_0x008f;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeRingChar0.baseSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long>");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateSquarefreePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        if (P.ring.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for multivariate polynomials");
        }
        GenPolynomial<GenPolynomial<C>> pp = P;
        RingElem Pc = this.engine.basePrimitivePart(this.engine.recursiveContent(P));
        if (!Pc.isONE()) {
            pp = PolyUtil.coefficientPseudoDivide(pp, Pc);
        }
        if (pp.leadingExpVector().getVal(0) < 1) {
            return pp.multiply(Pc);
        }
        return this.engine.baseRecursivePrimitivePart(PolyUtil.recursivePseudoDivide(pp, this.engine.baseRecursivePrimitivePart(this.engine.recursiveUnivariateGcd(pp, PolyUtil.recursiveDeriviative(pp))))).multiply(Pc);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long> recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>> r30) {
        /*
        r29 = this;
        r24 = new java.util.TreeMap;
        r24.<init>();
        if (r30 == 0) goto L_0x000d;
    L_0x0007:
        r26 = r30.isZERO();
        if (r26 == 0) goto L_0x000e;
    L_0x000d:
        return r24;
    L_0x000e:
        r0 = r30;
        r0 = r0.ring;
        r20 = r0;
        r0 = r20;
        r0 = r0.nvar;
        r26 = r0;
        r27 = 1;
        r0 = r26;
        r1 = r27;
        if (r0 <= r1) goto L_0x0043;
    L_0x0022:
        r26 = new java.lang.IllegalArgumentException;
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r28 = r29.getClass();
        r28 = r28.getName();
        r27 = r27.append(r28);
        r28 = " only for univariate polynomials";
        r27 = r27.append(r28);
        r27 = r27.toString();
        r26.<init>(r27);
        throw r26;
    L_0x0043:
        r0 = r20;
        r12 = r0.coFac;
        r12 = (edu.jas.poly.GenPolynomialRing) r12;
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r1 = r30;
        r10 = r0.baseRecursiveContent(r1);
        r26 = r10.isONE();
        if (r26 != 0) goto L_0x0088;
    L_0x005d:
        r26 = r12.getONE();
        r0 = r26;
        r18 = r0.multiply(r10);
        r26 = r20.getONE();
        r0 = r26;
        r1 = r18;
        r21 = r0.multiply(r1);
        r26 = 1;
        r26 = java.lang.Long.valueOf(r26);
        r0 = r24;
        r1 = r21;
        r2 = r26;
        r0.put(r1, r2);
        r0 = r30;
        r30 = edu.jas.poly.PolyUtil.baseRecursiveDivide(r0, r10);
    L_0x0088:
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r1 = r30;
        r4 = r0.recursiveContent(r1);
        r26 = logger;
        r26 = r26.isInfoEnabled();
        if (r26 == 0) goto L_0x00b8;
    L_0x009e:
        r26 = logger;
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r28 = "Pc = ";
        r27 = r27.append(r28);
        r0 = r27;
        r27 = r0.append(r4);
        r27 = r27.toString();
        r26.info(r27);
    L_0x00b8:
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r4 = r0.basePrimitivePart(r4);
        r26 = r4.isONE();
        if (r26 != 0) goto L_0x00d0;
    L_0x00ca:
        r0 = r30;
        r30 = edu.jas.poly.PolyUtil.coefficientPseudoDivide(r0, r4);
    L_0x00d0:
        r0 = r29;
        r23 = r0.squarefreeFactors(r4);
        r26 = logger;
        r26 = r26.isInfoEnabled();
        if (r26 == 0) goto L_0x00fa;
    L_0x00de:
        r26 = logger;
        r27 = new java.lang.StringBuilder;
        r27.<init>();
        r28 = "rsf = ";
        r27 = r27.append(r28);
        r0 = r27;
        r1 = r23;
        r27 = r0.append(r1);
        r27 = r27.toString();
        r26.info(r27);
    L_0x00fa:
        r26 = r23.entrySet();
        r14 = r26.iterator();
    L_0x0102:
        r26 = r14.hasNext();
        if (r26 == 0) goto L_0x0132;
    L_0x0108:
        r19 = r14.next();
        r19 = (java.util.Map.Entry) r19;
        r11 = r19.getKey();
        r11 = (edu.jas.poly.GenPolynomial) r11;
        r26 = r11.isONE();
        if (r26 != 0) goto L_0x0102;
    L_0x011a:
        r26 = r20.getONE();
        r0 = r26;
        r13 = r0.multiply(r11);
        r22 = r19.getValue();
        r22 = (java.lang.Long) r22;
        r0 = r24;
        r1 = r22;
        r0.put(r13, r1);
        goto L_0x0102;
    L_0x0132:
        r6 = r30;
        r5 = 0;
        r8 = 0;
        r16 = 0;
        r15 = 1;
    L_0x0139:
        if (r15 == 0) goto L_0x016a;
    L_0x013b:
        r26 = r6.isConstant();
        if (r26 != 0) goto L_0x000d;
    L_0x0141:
        r26 = r6.isZERO();
        if (r26 != 0) goto L_0x000d;
    L_0x0147:
        r7 = edu.jas.poly.PolyUtil.recursiveDeriviative(r6);
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r5 = r0.recursiveUnivariateGcd(r6, r7);
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r5 = r0.baseRecursivePrimitivePart(r5);
        r8 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r6, r5);
        r16 = 0;
        r15 = 0;
    L_0x016a:
        r26 = r8.isConstant();
        if (r26 != 0) goto L_0x000d;
    L_0x0170:
        r26 = 1;
        r16 = r16 + r26;
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r9 = r0.recursiveUnivariateGcd(r5, r8);
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r9 = r0.baseRecursivePrimitivePart(r9);
        r25 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r8, r9);
        r8 = r9;
        r5 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r5, r8);
        r26 = r25.isONE();
        if (r26 != 0) goto L_0x0139;
    L_0x019b:
        r26 = r25.isZERO();
        if (r26 != 0) goto L_0x0139;
    L_0x01a1:
        r0 = r29;
        r0 = r0.engine;
        r26 = r0;
        r0 = r26;
        r1 = r25;
        r25 = r0.baseRecursivePrimitivePart(r1);
        r26 = java.lang.Long.valueOf(r16);
        r24.put(r25, r26);
        goto L_0x0139;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeRingChar0.recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long>");
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
            return PolyUtil.distribute(pfac, recursiveUnivariateSquarefreePart(Pr).multiply(squarefreePart(Pc)));
        }
    }

    public SortedMap<GenPolynomial<C>, Long> squarefreeFactors(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        }
        GenPolynomialRing pfac = P.ring;
        if (pfac.nvar <= 1) {
            return baseSquarefreeFactors(P);
        }
        SortedMap<GenPolynomial<C>, Long> sfactors = new TreeMap();
        if (P.isZERO()) {
            return sfactors;
        }
        if (P.isONE()) {
            sfactors.put(P, Long.valueOf(1));
            return sfactors;
        }
        for (Entry<GenPolynomial<GenPolynomial<C>>, Long> m : recursiveUnivariateSquarefreeFactors(PolyUtil.recursive(new GenPolynomialRing(pfac.contract(1), 1), (GenPolynomial) P)).entrySet()) {
            sfactors.put(PolyUtil.distribute(pfac, (GenPolynomial) m.getKey()), (Long) m.getValue());
        }
        return normalizeFactorization(sfactors);
    }

    public SortedMap<C, Long> squarefreeFactors(C c) {
        throw new UnsupportedOperationException("method not implemented");
    }
}
