package edu.jas.ufd;

import edu.jas.poly.AlgebraicNumber;
import edu.jas.poly.AlgebraicNumberRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.GcdRingElem;
import edu.jas.structure.Power;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;
import org.apache.log4j.Logger;

public abstract class SquarefreeFieldCharP<C extends GcdRingElem<C>> extends SquarefreeAbstract<C> {
    private static final Logger logger;
    protected final AlgebraicNumberRing<C> aCoFac;
    protected final RingFactory<C> coFac;
    private final boolean debug;
    protected final QuotientRing<C> qCoFac;

    public abstract GenPolynomial<C> baseRootCharacteristic(GenPolynomial<C> genPolynomial);

    public abstract GenPolynomial<GenPolynomial<C>> recursiveUnivariateRootCharacteristic(GenPolynomial<GenPolynomial<C>> genPolynomial);

    static {
        logger = Logger.getLogger(SquarefreeFieldCharP.class);
    }

    public SquarefreeFieldCharP(RingFactory<C> fac) {
        super(GCDFactory.getProxy((RingFactory) fac));
        this.debug = logger.isDebugEnabled();
        if (!fac.isField()) {
            logger.warn("fac should be a field: " + fac.toScript());
        }
        if (fac.characteristic().signum() == 0) {
            throw new IllegalArgumentException("characterisic(fac) must be non-zero");
        }
        this.coFac = fac;
        RingFactory oFac = this.coFac;
        if (oFac instanceof AlgebraicNumberRing) {
            this.aCoFac = (AlgebraicNumberRing) oFac;
            this.qCoFac = null;
            return;
        }
        this.aCoFac = null;
        if (oFac instanceof QuotientRing) {
            this.qCoFac = (QuotientRing) oFac;
        } else {
            this.qCoFac = null;
        }
    }

    public String toString() {
        return getClass().getName() + " with " + this.engine + " over " + this.coFac;
    }

    public GenPolynomial<C> baseSquarefreePart(GenPolynomial<C> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<C> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for univariate polynomials");
        }
        GenPolynomial<C> s = pfac.getONE();
        SortedMap<GenPolynomial<C>, Long> factors = baseSquarefreeFactors(P);
        logger.info("sqfPart,factors = " + factors);
        for (GenPolynomial sp : factors.keySet()) {
            s = s.multiply(sp);
        }
        return s.monic();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long> baseSquarefreeFactors(edu.jas.poly.GenPolynomial<C> r33) {
        /*
        r32 = this;
        r26 = new java.util.TreeMap;
        r26.<init>();
        if (r33 == 0) goto L_0x000d;
    L_0x0007:
        r28 = r33.isZERO();
        if (r28 == 0) goto L_0x000e;
    L_0x000d:
        return r26;
    L_0x000e:
        r0 = r33;
        r0 = r0.ring;
        r21 = r0;
        r28 = r33.isConstant();
        if (r28 == 0) goto L_0x0076;
    L_0x001a:
        r10 = r33.leadingBaseCoefficient();
        r10 = (edu.jas.structure.GcdRingElem) r10;
        r0 = r32;
        r24 = r0.squarefreeFactors(r10);
        if (r24 == 0) goto L_0x0066;
    L_0x0028:
        r28 = r24.size();
        if (r28 <= 0) goto L_0x0066;
    L_0x002e:
        r28 = r24.entrySet();
        r15 = r28.iterator();
    L_0x0036:
        r28 = r15.hasNext();
        if (r28 == 0) goto L_0x000d;
    L_0x003c:
        r20 = r15.next();
        r20 = (java.util.Map.Entry) r20;
        r9 = r20.getKey();
        r9 = (edu.jas.structure.GcdRingElem) r9;
        r28 = r9.isONE();
        if (r28 != 0) goto L_0x0036;
    L_0x004e:
        r28 = r21.getONE();
        r0 = r28;
        r11 = r0.multiply(r9);
        r25 = r20.getValue();
        r25 = (java.lang.Long) r25;
        r0 = r26;
        r1 = r25;
        r0.put(r11, r1);
        goto L_0x0036;
    L_0x0066:
        r28 = 1;
        r28 = java.lang.Long.valueOf(r28);
        r0 = r26;
        r1 = r33;
        r2 = r28;
        r0.put(r1, r2);
        goto L_0x000d;
    L_0x0076:
        r0 = r21;
        r0 = r0.nvar;
        r28 = r0;
        r29 = 1;
        r0 = r28;
        r1 = r29;
        if (r0 <= r1) goto L_0x00a5;
    L_0x0084:
        r28 = new java.lang.IllegalArgumentException;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = r32.getClass();
        r30 = r30.getName();
        r29 = r29.append(r30);
        r30 = " only for univariate polynomials";
        r29 = r29.append(r30);
        r29 = r29.toString();
        r28.<init>(r29);
        throw r28;
    L_0x00a5:
        r17 = r33.leadingBaseCoefficient();
        r17 = (edu.jas.structure.GcdRingElem) r17;
        r28 = r17.isONE();
        if (r28 != 0) goto L_0x0126;
    L_0x00b1:
        r0 = r33;
        r1 = r17;
        r33 = r0.divide(r1);
        r0 = r32;
        r1 = r17;
        r24 = r0.squarefreeFactors(r1);
        if (r24 == 0) goto L_0x0101;
    L_0x00c3:
        r28 = r24.size();
        if (r28 <= 0) goto L_0x0101;
    L_0x00c9:
        r28 = r24.entrySet();
        r15 = r28.iterator();
    L_0x00d1:
        r28 = r15.hasNext();
        if (r28 == 0) goto L_0x011a;
    L_0x00d7:
        r20 = r15.next();
        r20 = (java.util.Map.Entry) r20;
        r9 = r20.getKey();
        r9 = (edu.jas.structure.GcdRingElem) r9;
        r28 = r9.isONE();
        if (r28 != 0) goto L_0x00d1;
    L_0x00e9:
        r28 = r21.getONE();
        r0 = r28;
        r11 = r0.multiply(r9);
        r25 = r20.getValue();
        r25 = (java.lang.Long) r25;
        r0 = r26;
        r1 = r25;
        r0.put(r11, r1);
        goto L_0x00d1;
    L_0x0101:
        r28 = r21.getONE();
        r0 = r28;
        r1 = r17;
        r14 = r0.multiply(r1);
        r28 = 1;
        r28 = java.lang.Long.valueOf(r28);
        r0 = r26;
        r1 = r28;
        r0.put(r14, r1);
    L_0x011a:
        r0 = r21;
        r0 = r0.coFac;
        r28 = r0;
        r17 = r28.getONE();
        r17 = (edu.jas.structure.GcdRingElem) r17;
    L_0x0126:
        r5 = r33;
        r12 = 1;
        r4 = 0;
        r7 = 0;
        r18 = 0;
        r22 = 0;
        r16 = 1;
    L_0x0132:
        if (r16 == 0) goto L_0x0186;
    L_0x0134:
        r28 = r5.isConstant();
        if (r28 != 0) goto L_0x0140;
    L_0x013a:
        r28 = r5.isZERO();
        if (r28 == 0) goto L_0x0168;
    L_0x0140:
        r28 = logger;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "exit char root: T0 = ";
        r29 = r29.append(r30);
        r0 = r29;
        r29 = r0.append(r5);
        r30 = ", T = ";
        r29 = r29.append(r30);
        r0 = r29;
        r29 = r0.append(r4);
        r29 = r29.toString();
        r28.info(r29);
        goto L_0x000d;
    L_0x0168:
        r6 = edu.jas.poly.PolyUtil.baseDeriviative(r5);
        r0 = r32;
        r0 = r0.engine;
        r28 = r0;
        r0 = r28;
        r4 = r0.baseGcd(r5, r6);
        r4 = r4.monic();
        r7 = edu.jas.poly.PolyUtil.basePseudoDivide(r5, r4);
        r18 = 0;
        r22 = 0;
        r16 = 0;
    L_0x0186:
        r28 = r7.isConstant();
        if (r28 == 0) goto L_0x01cc;
    L_0x018c:
        r28 = r21.characteristic();
        r22 = r28.longValue();
        r0 = r32;
        r5 = r0.baseRootCharacteristic(r4);
        r28 = logger;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "char root: T0 = ";
        r29 = r29.append(r30);
        r0 = r29;
        r29 = r0.append(r5);
        r30 = ", T = ";
        r29 = r29.append(r30);
        r0 = r29;
        r29 = r0.append(r4);
        r29 = r29.toString();
        r28.info(r29);
        if (r5 != 0) goto L_0x01c6;
    L_0x01c2:
        r5 = r21.getZERO();
    L_0x01c6:
        r12 = r12 * r22;
        r16 = 1;
        goto L_0x0132;
    L_0x01cc:
        r28 = 1;
        r18 = r18 + r28;
        r28 = 0;
        r28 = (r22 > r28 ? 1 : (r22 == r28 ? 0 : -1));
        if (r28 == 0) goto L_0x0202;
    L_0x01d6:
        r28 = r18 % r22;
        r30 = 0;
        r28 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1));
        if (r28 != 0) goto L_0x0202;
    L_0x01de:
        r4 = edu.jas.poly.PolyUtil.basePseudoDivide(r4, r7);
        r28 = java.lang.System.out;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "k = ";
        r29 = r29.append(r30);
        r0 = r29;
        r1 = r18;
        r29 = r0.append(r1);
        r29 = r29.toString();
        r28.println(r29);
        r28 = 1;
        r18 = r18 + r28;
    L_0x0202:
        r0 = r32;
        r0 = r0.engine;
        r28 = r0;
        r0 = r28;
        r8 = r0.baseGcd(r4, r7);
        r8 = r8.monic();
        r27 = edu.jas.poly.PolyUtil.basePseudoDivide(r7, r8);
        r7 = r8;
        r4 = edu.jas.poly.PolyUtil.basePseudoDivide(r4, r7);
        r28 = 0;
        r28 = r27.degree(r28);
        r30 = 0;
        r28 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1));
        if (r28 <= 0) goto L_0x0132;
    L_0x0227:
        r28 = r17.isONE();
        if (r28 == 0) goto L_0x0259;
    L_0x022d:
        r28 = r27.leadingBaseCoefficient();
        r28 = (edu.jas.structure.GcdRingElem) r28;
        r28 = r28.isONE();
        if (r28 != 0) goto L_0x0259;
    L_0x0239:
        r27 = r27.monic();
        r28 = logger;
        r29 = new java.lang.StringBuilder;
        r29.<init>();
        r30 = "z,monic = ";
        r29 = r29.append(r30);
        r0 = r29;
        r1 = r27;
        r29 = r0.append(r1);
        r29 = r29.toString();
        r28.info(r29);
    L_0x0259:
        r28 = r12 * r18;
        r28 = java.lang.Long.valueOf(r28);
        r26.put(r27, r28);
        goto L_0x0132;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeFieldCharP.baseSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<C>, java.lang.Long>");
    }

    public GenPolynomial<GenPolynomial<C>> recursiveUnivariateSquarefreePart(GenPolynomial<GenPolynomial<C>> P) {
        if (P == null || P.isZERO()) {
            return P;
        }
        GenPolynomialRing<GenPolynomial<C>> pfac = P.ring;
        if (pfac.nvar > 1) {
            throw new IllegalArgumentException(getClass().getName() + " only for multivariate polynomials");
        }
        GenPolynomial s = pfac.getONE();
        SortedMap<GenPolynomial<GenPolynomial<C>>, Long> factors = recursiveUnivariateSquarefreeFactors(P);
        if (logger.isInfoEnabled()) {
            logger.info("sqfPart,factors = " + factors);
        }
        for (GenPolynomial sp : factors.keySet()) {
            s = s.multiply(sp);
        }
        return PolyUtil.monic(s);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long> recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>> r37) {
        /*
        r36 = this;
        r29 = new java.util.TreeMap;
        r29.<init>();
        if (r37 == 0) goto L_0x000d;
    L_0x0007:
        r31 = r37.isZERO();
        if (r31 == 0) goto L_0x000e;
    L_0x000d:
        return r29;
    L_0x000e:
        r0 = r37;
        r0 = r0.ring;
        r23 = r0;
        r0 = r23;
        r0 = r0.nvar;
        r31 = r0;
        r32 = 1;
        r0 = r31;
        r1 = r32;
        if (r0 <= r1) goto L_0x0043;
    L_0x0022:
        r31 = new java.lang.IllegalArgumentException;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = r36.getClass();
        r33 = r33.getName();
        r32 = r32.append(r33);
        r33 = " only for univariate polynomials";
        r32 = r32.append(r33);
        r32 = r32.toString();
        r31.<init>(r32);
        throw r31;
    L_0x0043:
        r0 = r23;
        r11 = r0.coFac;
        r11 = (edu.jas.poly.GenPolynomialRing) r11;
        r31 = r37.leadingBaseCoefficient();
        r31 = (edu.jas.poly.GenPolynomial) r31;
        r20 = r31.leadingBaseCoefficient();
        r20 = (edu.jas.structure.GcdRingElem) r20;
        r31 = r20.isONE();
        if (r31 != 0) goto L_0x00cc;
    L_0x005b:
        r31 = r11.getONE();
        r0 = r31;
        r1 = r20;
        r17 = r0.multiply(r1);
        r31 = r23.getONE();
        r0 = r31;
        r1 = r17;
        r26 = r0.multiply(r1);
        r32 = 1;
        r31 = java.lang.Long.valueOf(r32);
        r0 = r29;
        r1 = r26;
        r2 = r31;
        r0.put(r1, r2);
        r21 = r20.inverse();
        r21 = (edu.jas.structure.GcdRingElem) r21;
        r31 = r11.getONE();
        r0 = r31;
        r1 = r21;
        r31 = r0.multiply(r1);
        r0 = r37;
        r1 = r31;
        r37 = r0.multiply(r1);
        r31 = r37.leadingBaseCoefficient();
        r31 = (edu.jas.poly.GenPolynomial) r31;
        r20 = r31.leadingBaseCoefficient();
        r20 = (edu.jas.structure.GcdRingElem) r20;
        r0 = r36;
        r0 = r0.debug;
        r31 = r0;
        if (r31 == 0) goto L_0x00cc;
    L_0x00b0:
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "new ldbcf: ";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r20;
        r32 = r0.append(r1);
        r32 = r32.toString();
        r31.debug(r32);
    L_0x00cc:
        r0 = r36;
        r0 = r0.engine;
        r31 = r0;
        r0 = r31;
        r1 = r37;
        r4 = r0.recursiveContent(r1);
        r31 = logger;
        r31 = r31.isInfoEnabled();
        if (r31 == 0) goto L_0x00fc;
    L_0x00e2:
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "Pc = ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r4);
        r32 = r32.toString();
        r31.info(r32);
    L_0x00fc:
        r4 = r4.monic();
        r31 = r4.isONE();
        if (r31 != 0) goto L_0x010c;
    L_0x0106:
        r0 = r37;
        r37 = edu.jas.poly.PolyUtil.coefficientPseudoDivide(r0, r4);
    L_0x010c:
        r0 = r36;
        r28 = r0.squarefreeFactors(r4);
        r31 = logger;
        r31 = r31.isInfoEnabled();
        if (r31 == 0) goto L_0x0136;
    L_0x011a:
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "rsf = ";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r28;
        r32 = r0.append(r1);
        r32 = r32.toString();
        r31.info(r32);
    L_0x0136:
        r31 = r28.entrySet();
        r13 = r31.iterator();
    L_0x013e:
        r31 = r13.hasNext();
        if (r31 == 0) goto L_0x016e;
    L_0x0144:
        r22 = r13.next();
        r22 = (java.util.Map.Entry) r22;
        r10 = r22.getKey();
        r10 = (edu.jas.poly.GenPolynomial) r10;
        r31 = r10.isONE();
        if (r31 != 0) goto L_0x013e;
    L_0x0156:
        r31 = r23.getONE();
        r0 = r31;
        r12 = r0.multiply(r10);
        r27 = r22.getValue();
        r27 = (java.lang.Long) r27;
        r0 = r29;
        r1 = r27;
        r0.put(r12, r1);
        goto L_0x013e;
    L_0x016e:
        r6 = r37;
        r14 = 1;
        r5 = 0;
        r8 = 0;
        r18 = 0;
        r24 = 0;
        r16 = 1;
    L_0x017a:
        if (r16 == 0) goto L_0x01e7;
    L_0x017c:
        r31 = r6.isConstant();
        if (r31 != 0) goto L_0x0188;
    L_0x0182:
        r31 = r6.isZERO();
        if (r31 == 0) goto L_0x01c9;
    L_0x0188:
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "exit char root: T0 = ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r6);
        r33 = ", T = ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r5);
        r32 = r32.toString();
        r31.info(r32);
        r31 = r29.size();
        if (r31 != 0) goto L_0x000d;
    L_0x01b4:
        r31 = r23.getONE();
        r32 = 1;
        r32 = java.lang.Long.valueOf(r32);
        r0 = r29;
        r1 = r31;
        r2 = r32;
        r0.put(r1, r2);
        goto L_0x000d;
    L_0x01c9:
        r7 = edu.jas.poly.PolyUtil.recursiveDeriviative(r6);
        r0 = r36;
        r0 = r0.engine;
        r31 = r0;
        r0 = r31;
        r5 = r0.recursiveUnivariateGcd(r6, r7);
        r5 = edu.jas.poly.PolyUtil.monic(r5);
        r8 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r6, r5);
        r18 = 0;
        r24 = 0;
        r16 = 0;
    L_0x01e7:
        r31 = r8.isConstant();
        if (r31 == 0) goto L_0x022b;
    L_0x01ed:
        r31 = r23.characteristic();
        r24 = r31.longValue();
        r0 = r36;
        r6 = r0.recursiveUnivariateRootCharacteristic(r5);
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "char root: T0r = ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r6);
        r33 = ", Tr = ";
        r32 = r32.append(r33);
        r0 = r32;
        r32 = r0.append(r5);
        r32 = r32.toString();
        r31.info(r32);
        if (r6 != 0) goto L_0x0227;
    L_0x0223:
        r6 = r23.getZERO();
    L_0x0227:
        r14 = r14 * r24;
        r16 = 1;
    L_0x022b:
        r32 = 1;
        r18 = r18 + r32;
        r32 = 0;
        r31 = (r24 > r32 ? 1 : (r24 == r32 ? 0 : -1));
        if (r31 == 0) goto L_0x0261;
    L_0x0235:
        r32 = r18 % r24;
        r34 = 0;
        r31 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r31 != 0) goto L_0x0261;
    L_0x023d:
        r5 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r5, r8);
        r31 = java.lang.System.out;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "k = ";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r18;
        r32 = r0.append(r1);
        r32 = r32.toString();
        r31.println(r32);
        r32 = 1;
        r18 = r18 + r32;
    L_0x0261:
        r0 = r36;
        r0 = r0.engine;
        r31 = r0;
        r0 = r31;
        r9 = r0.recursiveUnivariateGcd(r5, r8);
        r9 = edu.jas.poly.PolyUtil.monic(r9);
        r30 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r8, r9);
        r8 = r9;
        r5 = edu.jas.poly.PolyUtil.recursivePseudoDivide(r5, r8);
        r31 = r30.isONE();
        if (r31 != 0) goto L_0x017a;
    L_0x0280:
        r31 = r30.isZERO();
        if (r31 != 0) goto L_0x017a;
    L_0x0286:
        r30 = edu.jas.poly.PolyUtil.monic(r30);
        r31 = logger;
        r32 = new java.lang.StringBuilder;
        r32.<init>();
        r33 = "z,put = ";
        r32 = r32.append(r33);
        r0 = r32;
        r1 = r30;
        r32 = r0.append(r1);
        r32 = r32.toString();
        r31.info(r32);
        r32 = r14 * r18;
        r31 = java.lang.Long.valueOf(r32);
        r29.put(r30, r31);
        goto L_0x017a;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.ufd.SquarefreeFieldCharP.recursiveUnivariateSquarefreeFactors(edu.jas.poly.GenPolynomial):java.util.SortedMap<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>, java.lang.Long>");
    }

    public GenPolynomial<C> squarefreePart(GenPolynomial<C> P) {
        if (P == null) {
            throw new IllegalArgumentException(getClass().getName() + " P != null");
        } else if (P.isZERO()) {
            return P;
        } else {
            GenPolynomialRing<C> pfac = P.ring;
            if (pfac.nvar <= 1) {
                return baseSquarefreePart(P);
            }
            GenPolynomial<C> s = pfac.getONE();
            SortedMap<GenPolynomial<C>, Long> factors = squarefreeFactors((GenPolynomial) P);
            if (logger.isInfoEnabled()) {
                logger.info("sqfPart,factors = " + factors);
            }
            for (GenPolynomial sp : factors.keySet()) {
                if (!sp.isConstant()) {
                    s = s.multiply(sp);
                }
            }
            return s.monic();
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
        return sfactors;
    }

    public SortedMap<C, Long> squarefreeFactors(C coeff) {
        if (coeff == null) {
            return null;
        }
        SortedMap<C, Long> factors = new TreeMap();
        RingFactory cfac = (RingFactory) coeff.factory();
        SortedMap<C, Long> rfactors;
        if (this.aCoFac != null) {
            AlgebraicNumber an = (AlgebraicNumber) coeff;
            if (cfac.isFinite()) {
                rfactors = ((SquarefreeFiniteFieldCharP) SquarefreeFactory.getImplementation(cfac)).rootCharacteristic((GcdRingElem) coeff);
                logger.info("rfactors,finite = " + rfactors);
                factors.putAll(rfactors);
                return factors;
            }
            SortedMap<AlgebraicNumber<C>, Long> rfactors2 = ((SquarefreeInfiniteAlgebraicFieldCharP) SquarefreeFactory.getImplementation(cfac)).squarefreeFactors(an);
            logger.info("rfactors,infinite,algeb = " + rfactors2);
            for (Entry<AlgebraicNumber<C>, Long> me : rfactors2.entrySet()) {
                C c = (AlgebraicNumber) me.getKey();
                if (!c.isONE()) {
                    factors.put((GcdRingElem) c, (Long) me.getValue());
                }
            }
            return factors;
        } else if (this.qCoFac != null) {
            SortedMap<Quotient<C>, Long> rfactors3 = ((SquarefreeInfiniteFieldCharP) SquarefreeFactory.getImplementation(cfac)).squarefreeFactors((Quotient) coeff);
            logger.info("rfactors,infinite = " + rfactors3);
            for (Entry<Quotient<C>, Long> me2 : rfactors3.entrySet()) {
                Quotient<C> c2 = (Quotient) me2.getKey();
                if (!c2.isONE()) {
                    factors.put(c2, (Long) me2.getValue());
                }
            }
            return factors;
        } else if (cfac.isFinite()) {
            rfactors = ((SquarefreeFiniteFieldCharP) SquarefreeFactory.getImplementation(cfac)).rootCharacteristic((GcdRingElem) coeff);
            logger.info("rfactors,finite = " + rfactors);
            factors.putAll(rfactors);
            return factors;
        } else {
            logger.warn("case " + cfac + " not implemented");
            return factors;
        }
    }

    public boolean isCharRoot(GenPolynomial<C> P, SortedMap<GenPolynomial<C>, Long> F) {
        if (P == null || F == null) {
            throw new IllegalArgumentException("P and F may not be null");
        } else if (P.isZERO() && F.size() == 0) {
            return true;
        } else {
            GenPolynomial<C> t = P.ring.getONE();
            long p = P.ring.characteristic().longValue();
            for (Entry<GenPolynomial<C>, Long> me : F.entrySet()) {
                RingElem f = (GenPolynomial) me.getKey();
                GenPolynomial g = (GenPolynomial) Power.positivePower(f, ((Long) me.getValue()).longValue());
                if (!f.isConstant()) {
                    g = (GenPolynomial) Power.positivePower((RingElem) g, p);
                }
                t = t.multiply(g);
            }
            boolean f2 = P.equals(t) || P.equals(t.negate());
            if (f2) {
                return f2;
            }
            System.out.println("\nfactorization(map): " + f2);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            P = P.monic();
            t = t.monic();
            f2 = P.equals(t) || P.equals(t.negate());
            if (f2) {
                return f2;
            }
            System.out.println("\nfactorization(map): " + f2);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            return f2;
        }
    }

    public boolean isRecursiveCharRoot(GenPolynomial<GenPolynomial<C>> P, SortedMap<GenPolynomial<GenPolynomial<C>>, Long> F) {
        if (P == null || F == null) {
            throw new IllegalArgumentException("P and F may not be null");
        } else if (P.isZERO() && F.size() == 0) {
            return true;
        } else {
            GenPolynomial<GenPolynomial<C>> t = P.ring.getONE();
            long p = P.ring.characteristic().longValue();
            for (Entry<GenPolynomial<GenPolynomial<C>>, Long> me : F.entrySet()) {
                RingElem f = (GenPolynomial) me.getKey();
                GenPolynomial g = (GenPolynomial) Power.positivePower(f, ((Long) me.getValue()).longValue());
                if (!f.isConstant()) {
                    g = (GenPolynomial) Power.positivePower((RingElem) g, p);
                }
                t = t.multiply(g);
            }
            boolean f2 = P.equals(t) || P.equals(t.negate());
            if (f2) {
                return f2;
            }
            System.out.println("\nfactorization(map): " + f2);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            P = P.monic();
            t = t.monic();
            f2 = P.equals(t) || P.equals(t.negate());
            if (f2) {
                return f2;
            }
            System.out.println("\nfactorization(map): " + f2);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            return f2;
        }
    }

    public boolean isRecursiveCharRoot(GenPolynomial<GenPolynomial<C>> P, GenPolynomial<GenPolynomial<C>> r) {
        if (P == null || r == null) {
            throw new IllegalArgumentException("P and r may not be null");
        } else if (P.isZERO() && r.isZERO()) {
            return true;
        } else {
            boolean f;
            GenPolynomial<GenPolynomial<C>> t = (GenPolynomial) Power.positivePower((RingElem) r, P.ring.characteristic().longValue());
            if (P.equals(t) || P.equals(t.negate())) {
                f = true;
            } else {
                f = false;
            }
            if (f) {
                return f;
            }
            System.out.println("\nisCharRoot: " + f);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            P = P.monic();
            t = t.monic();
            if (P.equals(t) || P.equals(t.negate())) {
                f = true;
            } else {
                f = false;
            }
            if (f) {
                return f;
            }
            System.out.println("\nisCharRoot: " + f);
            System.out.println("P = " + P);
            System.out.println("t = " + t);
            return f;
        }
    }
}
