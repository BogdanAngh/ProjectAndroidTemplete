package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import edu.jas.ufd.Squarefree;
import edu.jas.ufd.SquarefreeFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import org.apache.log4j.Logger;

public abstract class ComplexRootsAbstract<C extends RingElem<C> & Rational> implements ComplexRoots<C> {
    private static final Logger logger;
    private final boolean debug;
    public final Squarefree<Complex<C>> engine;

    class 1 implements UnaryFunctor<Complex<C>, Complex<C>> {
        1() {
        }

        public Complex<C> eval(Complex<C> a) {
            return a.norm();
        }
    }

    public abstract long complexRootCount(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial) throws InvalidBoundaryException;

    public abstract List<Rectangle<C>> complexRoots(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial) throws InvalidBoundaryException;

    public abstract Rectangle<C> invariantRectangle(Rectangle<C> rectangle, GenPolynomial<Complex<C>> genPolynomial, GenPolynomial<Complex<C>> genPolynomial2) throws InvalidBoundaryException;

    static {
        logger = Logger.getLogger(ComplexRootsAbstract.class);
    }

    public ComplexRootsAbstract(RingFactory<Complex<C>> cf) {
        this.debug = logger.isDebugEnabled();
        if (cf instanceof ComplexRing) {
            this.engine = SquarefreeFactory.getImplementation((RingFactory) cf);
            return;
        }
        throw new IllegalArgumentException("cf not supported coefficients " + cf);
    }

    public Complex<C> rootBound(GenPolynomial<Complex<C>> f) {
        if (f == null) {
            return null;
        }
        RingFactory<Complex<C>> cfac = f.ring.coFac;
        Complex<C> M = (Complex) cfac.getONE();
        if (f.isZERO() || f.isConstant()) {
            return M;
        }
        Complex a = ((Complex) f.leadingBaseCoefficient()).norm();
        for (Complex<C> c : f.getMap().values()) {
            Complex<C> d = c.norm().divide(a);
            if (M.compareTo((Complex) d) < 0) {
                M = d;
            }
        }
        return M.sum((Complex) cfac.getONE());
    }

    public C magnitudeBound(Rectangle<C> rect, GenPolynomial<Complex<C>> f) {
        if (f == null) {
            return null;
        }
        if (f.isZERO()) {
            return ((Complex) f.ring.coFac.getONE()).getRe();
        }
        if (f.isConstant()) {
            return ((Complex) f.leadingBaseCoefficient()).norm().getRe();
        }
        GenPolynomial fa = f.map(new 1());
        RingElem Mc = rect.getNW().norm();
        C M = Mc.getRe();
        Complex<C> M1c = rect.getSW().norm();
        C M1 = M1c.getRe();
        if (M.compareTo(M1) < 0) {
            M = M1;
            Mc = M1c;
        }
        M1c = rect.getSE().norm();
        M1 = M1c.getRe();
        if (M.compareTo(M1) < 0) {
            M = M1;
            Mc = M1c;
        }
        M1c = rect.getNE().norm();
        if (M.compareTo(M1c.getRe()) < 0) {
            Mc = M1c;
        }
        return ((Complex) PolyUtil.evaluateMain(f.ring.coFac, fa, Mc)).getRe();
    }

    public List<Rectangle<C>> complexRoots(GenPolynomial<Complex<C>> a) {
        List<Rectangle<C>> roots = new ArrayList();
        if (!(a.isConstant() || a.isZERO())) {
            ComplexRing<C> cr = a.ring.coFac;
            for (Entry<GenPolynomial<Complex<C>>, Long> me : this.engine.squarefreeFactors(a).entrySet()) {
                GenPolynomial p = (GenPolynomial) me.getKey();
                C M = rootBound(p).getRe();
                RingElem M1 = (RingElem) M.sum((AbelianGroupElem) M.factory().fromInteger(1));
                if (this.debug) {
                    logger.info("rootBound = " + M);
                }
                Complex[] corner = (Complex[]) new Complex[4];
                corner[0] = new Complex(cr, (RingElem) M1.negate(), M);
                corner[1] = new Complex(cr, (RingElem) M1.negate(), (RingElem) M1.negate());
                corner[2] = new Complex(cr, M, (RingElem) M1.negate());
                corner[3] = new Complex(cr, M, M);
                try {
                    List<Rectangle<C>> rs = complexRoots(new Rectangle(corner), p);
                    long e = ((Long) me.getValue()).longValue();
                    int i = 0;
                    while (true) {
                        if (((long) i) < e) {
                            roots.addAll(rs);
                            i++;
                        }
                    }
                } catch (InvalidBoundaryException e2) {
                    throw new RuntimeException("this should never happen " + e2);
                }
            }
        }
        return roots;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.root.Rectangle<C> complexRootRefinement(edu.jas.root.Rectangle<C> r24, edu.jas.poly.GenPolynomial<edu.jas.poly.Complex<C>> r25, edu.jas.arith.BigRational r26) throws edu.jas.root.InvalidBoundaryException {
        /*
        r23 = this;
        r0 = r25;
        r0 = r0.ring;
        r19 = r0;
        r0 = r19;
        r6 = r0.coFac;
        r6 = (edu.jas.poly.ComplexRing) r6;
        r13 = r24;
        r0 = r23;
        r0 = r0.debug;
        r19 = r0;
        if (r19 == 0) goto L_0x0077;
    L_0x0016:
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r13, r1);
        r20 = 1;
        r19 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r19 == 0) goto L_0x0077;
    L_0x0024:
        r19 = java.lang.System.out;
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "#root = ";
        r20 = r20.append(r21);
        r0 = r20;
        r1 = r16;
        r20 = r0.append(r1);
        r20 = r20.toString();
        r19.println(r20);
        r19 = java.lang.System.out;
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "root = ";
        r20 = r20.append(r21);
        r0 = r20;
        r20 = r0.append(r13);
        r20 = r20.toString();
        r19.println(r20);
        r19 = new java.lang.ArithmeticException;
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "no initial isolating rectangle ";
        r20 = r20.append(r21);
        r0 = r20;
        r1 = r24;
        r20 = r0.append(r1);
        r20 = r20.toString();
        r19.<init>(r20);
        throw r19;
    L_0x0077:
        r20 = 1;
        r0 = r20;
        r9 = r6.fromInteger(r0);
        r20 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r20;
        r19 = r6.fromInteger(r0);
        r0 = r19;
        r9 = r9.divide(r0);
        r0 = r26;
        r1 = r26;
        r10 = r0.multiply(r1);
        r7 = 0;
        r18 = 1;
    L_0x0098:
        if (r18 == 0) goto L_0x0302;
    L_0x009a:
        r19 = r13.rationalLength();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r19;
        r19 = r0.compareTo(r10);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        if (r19 <= 0) goto L_0x02fe;
    L_0x00a6:
        if (r7 != 0) goto L_0x00ca;
    L_0x00a8:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 3;
        r19 = r19[r20];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = r0;
        r21 = 1;
        r20 = r20[r21];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r7 = r19.subtract(r20);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = 2;
        r0 = r20;
        r19 = r6.fromInteger(r0);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r19;
        r7 = r7.divide(r0);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
    L_0x00ca:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 1;
        r19 = r19[r20];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r19;
        r4 = r0.sum(r7);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r23;
        r0 = r0.debug;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        if (r19 == 0) goto L_0x00fa;
    L_0x00e0:
        r19 = logger;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = new java.lang.StringBuilder;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20.<init>();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = "new center = ";
        r20 = r20.append(r21);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r20 = r0.append(r4);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = r20.toString();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19.info(r20);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
    L_0x00fa:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 4;
        r0 = r23;
        r1 = r19;
        r2 = r20;
        r5 = r0.copyOfComplex(r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5 = (edu.jas.poly.Complex[]) r5;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 1;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = 1;
        r21 = r5[r21];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r21.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r4.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 2;
        r5[r19] = r4;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 3;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r4.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = 3;
        r22 = r5[r22];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r22.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r12 = new edu.jas.root.Rectangle;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r12.<init>(r5);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r12, r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = 1;
        r19 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r19 != 0) goto L_0x015d;
    L_0x0159:
        r13 = r12;
        r7 = 0;
        goto L_0x009a;
    L_0x015d:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 4;
        r0 = r23;
        r1 = r19;
        r2 = r20;
        r5 = r0.copyOfComplex(r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5 = (edu.jas.poly.Complex[]) r5;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 0;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = 0;
        r21 = r5[r21];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r21.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r4.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 2;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r4.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = 2;
        r22 = r5[r22];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r22.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 3;
        r5[r19] = r4;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r15 = new edu.jas.root.Rectangle;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r15.<init>(r5);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r15, r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = 1;
        r19 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r19 != 0) goto L_0x01c0;
    L_0x01bc:
        r13 = r15;
        r7 = 0;
        goto L_0x009a;
    L_0x01c0:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 4;
        r0 = r23;
        r1 = r19;
        r2 = r20;
        r5 = r0.copyOfComplex(r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5 = (edu.jas.poly.Complex[]) r5;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 0;
        r5[r19] = r4;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 1;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r4.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = 1;
        r22 = r5[r22];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r22.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 3;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = 3;
        r21 = r5[r21];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r21.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r4.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r14 = new edu.jas.root.Rectangle;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r14.<init>(r5);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r14, r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = 1;
        r19 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r19 != 0) goto L_0x0223;
    L_0x021f:
        r13 = r14;
        r7 = 0;
        goto L_0x009a;
    L_0x0223:
        r0 = r13.corners;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = r0;
        r20 = 4;
        r0 = r23;
        r1 = r19;
        r2 = r20;
        r5 = r0.copyOfComplex(r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5 = (edu.jas.poly.Complex[]) r5;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 0;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r4.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = 0;
        r22 = r5[r22];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r22.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 1;
        r5[r19] = r4;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = 2;
        r20 = new edu.jas.poly.Complex;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = 2;
        r21 = r5[r21];	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = r21.getRe();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r22 = r4.getIm();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r21;
        r2 = r22;
        r0.<init>(r6, r1, r2);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r5[r19] = r20;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r11 = new edu.jas.root.Rectangle;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r11.<init>(r5);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r11, r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = 1;
        r19 = (r16 > r20 ? 1 : (r16 == r20 ? 0 : -1));
        if (r19 != 0) goto L_0x0286;
    L_0x0282:
        r13 = r11;
        r7 = 0;
        goto L_0x009a;
    L_0x0286:
        r0 = r23;
        r1 = r25;
        r16 = r0.complexRootCount(r13, r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = java.lang.System.out;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = new java.lang.StringBuilder;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20.<init>();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = "#root = ";
        r20 = r20.append(r21);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r16;
        r20 = r0.append(r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = r20.toString();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19.println(r20);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = java.lang.System.out;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = new java.lang.StringBuilder;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20.<init>();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = "root = ";
        r20 = r20.append(r21);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r20 = r0.append(r13);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = r20.toString();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19.println(r20);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19 = new java.lang.ArithmeticException;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = new java.lang.StringBuilder;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20.<init>();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r21 = "no isolating rectangle ";
        r20 = r20.append(r21);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r0 = r20;
        r1 = r24;
        r20 = r0.append(r1);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r20 = r20.toString();	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        r19.<init>(r20);	 Catch:{ InvalidBoundaryException -> 0x02e1 }
        throw r19;	 Catch:{ InvalidBoundaryException -> 0x02e1 }
    L_0x02e1:
        r8 = move-exception;
        r19 = r7.multiply(r9);
        r0 = r19;
        r7 = r7.sum(r0);
        r19 = r6.getIMAG();
        r0 = r19;
        r19 = r9.multiply(r0);
        r0 = r19;
        r9 = r9.sum(r0);
        goto L_0x0098;
    L_0x02fe:
        r18 = 0;
        goto L_0x0098;
    L_0x0302:
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.root.ComplexRootsAbstract.complexRootRefinement(edu.jas.root.Rectangle, edu.jas.poly.GenPolynomial, edu.jas.arith.BigRational):edu.jas.root.Rectangle<C>");
    }

    public List<Rectangle<C>> complexRoots(GenPolynomial<Complex<C>> a, BigRational len) {
        ComplexRing<C> cr = a.ring.coFac;
        SortedMap<GenPolynomial<Complex<C>>, Long> sa = this.engine.squarefreeFactors(a);
        List<Rectangle<C>> roots = new ArrayList();
        for (Entry<GenPolynomial<Complex<C>>, Long> me : sa.entrySet()) {
            GenPolynomial p = (GenPolynomial) me.getKey();
            C M = rootBound(p).getRe();
            RingElem M1 = (RingElem) M.sum((AbelianGroupElem) M.factory().fromInteger(1));
            if (this.debug) {
                logger.info("rootBound = " + M);
            }
            Complex[] corner = (Complex[]) new Complex[4];
            corner[0] = new Complex(cr, (RingElem) M1.negate(), M);
            corner[1] = new Complex(cr, (RingElem) M1.negate(), (RingElem) M1.negate());
            corner[2] = new Complex(cr, M, (RingElem) M1.negate());
            corner[3] = new Complex(cr, M, M);
            try {
                List<Rectangle<C>> rs = complexRoots((Rectangle) new Rectangle(corner), p);
                List<Rectangle<C>> arrayList = new ArrayList(rs.size());
                for (Rectangle<C> complexRootRefinement : rs) {
                    arrayList.add(complexRootRefinement(complexRootRefinement, p, len));
                }
                long e = ((Long) me.getValue()).longValue();
                int i = 0;
                while (true) {
                    if (((long) i) < e) {
                        roots.addAll(arrayList);
                        i++;
                    }
                }
            } catch (InvalidBoundaryException e2) {
                throw new RuntimeException("this should never happen " + e2);
            }
        }
        return roots;
    }

    public String toDecimal(Complex<C> a) {
        return new BigDecimal(new BigRational(a.getRe().toString())).toString() + " i " + new BigDecimal(new BigRational(a.getIm().toString())).toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.Complex<edu.jas.arith.BigDecimal> approximateRoot(edu.jas.root.Rectangle<C> r37, edu.jas.poly.GenPolynomial<edu.jas.poly.Complex<C>> r38, C r39) throws edu.jas.root.NoConvergenceException {
        /*
        r36 = this;
        if (r37 != 0) goto L_0x000a;
    L_0x0002:
        r33 = new java.lang.IllegalArgumentException;
        r34 = "null interval not allowed";
        r33.<init>(r34);
        throw r33;
    L_0x000a:
        r6 = r37.getDecimalCenter();
        if (r38 == 0) goto L_0x001e;
    L_0x0010:
        r33 = r38.isZERO();
        if (r33 != 0) goto L_0x001e;
    L_0x0016:
        r33 = r38.isConstant();
        if (r33 != 0) goto L_0x001e;
    L_0x001c:
        if (r39 != 0) goto L_0x0020;
    L_0x001e:
        r11 = r6;
    L_0x001f:
        return r11;
    L_0x0020:
        r33 = r37.length();
        r0 = r33;
        r1 = r39;
        r33 = r0.compareTo(r1);
        if (r33 >= 0) goto L_0x0030;
    L_0x002e:
        r11 = r6;
        goto L_0x001f;
    L_0x0030:
        r5 = r6.ring;
        r28 = r37.getSW();
        r30 = new edu.jas.arith.BigDecimal;
        r33 = r28.getRe();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r30;
        r1 = r33;
        r0.<init>(r1);
        r29 = new edu.jas.arith.BigDecimal;
        r33 = r28.getIm();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r29;
        r1 = r33;
        r0.<init>(r1);
        r18 = new edu.jas.poly.Complex;
        r0 = r18;
        r1 = r30;
        r2 = r29;
        r0.<init>(r5, r1, r2);
        r19 = r37.getNE();
        r21 = new edu.jas.arith.BigDecimal;
        r33 = r19.getRe();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r21;
        r1 = r33;
        r0.<init>(r1);
        r20 = new edu.jas.arith.BigDecimal;
        r33 = r19.getIm();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r20;
        r1 = r33;
        r0.<init>(r1);
        r31 = new edu.jas.poly.Complex;
        r0 = r31;
        r1 = r21;
        r2 = r20;
        r0.<init>(r5, r1, r2);
        r12 = new edu.jas.arith.BigDecimal;
        r39 = (edu.jas.arith.Rational) r39;
        r33 = r39.getRational();
        r0 = r33;
        r12.<init>(r0);
        r23 = new edu.jas.poly.Complex;
        r33 = new edu.jas.arith.BigDecimal;
        r34 = "0.25";
        r33.<init>(r34);
        r0 = r23;
        r1 = r33;
        r0.<init>(r5, r1);
        r33 = r6.norm();
        r33 = r33.getRe();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r0 = r33;
        r12 = r12.multiply(r0);
        r8 = new edu.jas.poly.GenPolynomialRing;
        r0 = r38;
        r0 = r0.ring;
        r33 = r0;
        r0 = r33;
        r8.<init>(r5, r0);
        r0 = r38;
        r7 = edu.jas.poly.PolyUtil.complexDecimalFromRational(r8, r0);
        r13 = edu.jas.poly.PolyUtil.baseDeriviative(r38);
        r9 = edu.jas.poly.PolyUtil.complexDecimalFromRational(r8, r13);
        r16 = 0;
        r3 = 50;
        r10 = -1;
        r17 = r16;
    L_0x00eb:
        r16 = r17 + 1;
        r33 = 50;
        r0 = r17;
        r1 = r33;
        if (r0 >= r1) goto L_0x033e;
    L_0x00f5:
        r15 = edu.jas.poly.PolyUtil.evaluateMain(r5, r7, r6);
        r15 = (edu.jas.poly.Complex) r15;
        r33 = r15.isZERO();
        if (r33 == 0) goto L_0x0104;
    L_0x0101:
        r11 = r6;
        goto L_0x001f;
    L_0x0104:
        r14 = edu.jas.poly.PolyUtil.evaluateMain(r5, r9, r6);
        r14 = (edu.jas.poly.Complex) r14;
        r33 = r14.isZERO();
        if (r33 == 0) goto L_0x0118;
    L_0x0110:
        r33 = new edu.jas.root.NoConvergenceException;
        r34 = "zero deriviative should not happen";
        r33.<init>(r34);
        throw r33;
    L_0x0118:
        r32 = r15.divide(r14);
        r0 = r32;
        r11 = r6.subtract(r0);
        r33 = r6.subtract(r11);
        r33 = r33.norm();
        r33 = r33.getRe();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r0 = r33;
        r33 = r0.compareTo(r12);
        if (r33 <= 0) goto L_0x001f;
    L_0x0138:
        r33 = r11.getRe();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r34 = r18.getRe();
        r34 = (edu.jas.arith.BigDecimal) r34;
        r33 = r33.compareTo(r34);
        if (r33 < 0) goto L_0x0180;
    L_0x014a:
        r33 = r11.getIm();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r34 = r18.getIm();
        r34 = (edu.jas.arith.BigDecimal) r34;
        r33 = r33.compareTo(r34);
        if (r33 < 0) goto L_0x0180;
    L_0x015c:
        r33 = r11.getRe();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r34 = r31.getRe();
        r34 = (edu.jas.arith.BigDecimal) r34;
        r33 = r33.compareTo(r34);
        if (r33 > 0) goto L_0x0180;
    L_0x016e:
        r33 = r11.getIm();
        r33 = (edu.jas.arith.BigDecimal) r33;
        r34 = r31.getIm();
        r34 = (edu.jas.arith.BigDecimal) r34;
        r33 = r33.compareTo(r34);
        if (r33 <= 0) goto L_0x0339;
    L_0x0180:
        r17 = r16 + 1;
        r33 = 50;
        r0 = r16;
        r1 = r33;
        if (r0 <= r1) goto L_0x01ad;
    L_0x018a:
        r33 = new edu.jas.root.NoConvergenceException;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "no convergence after ";
        r34 = r34.append(r35);
        r0 = r34;
        r1 = r17;
        r34 = r0.append(r1);
        r35 = " steps";
        r34 = r34.append(r35);
        r34 = r34.toString();
        r33.<init>(r34);
        throw r33;
    L_0x01ad:
        r33 = 25;
        r0 = r17;
        r1 = r33;
        if (r0 <= r1) goto L_0x0361;
    L_0x01b5:
        if (r10 != 0) goto L_0x0361;
    L_0x01b7:
        r4 = r37.getCenter();
        r0 = r37;
        r22 = r0.exchangeSE(r4);
        r24 = r22.getDecimalCenter();
        r6 = r24;
        r32 = r5.getZERO();
        r33 = logger;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "trying new SE starting point ";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r6);
        r34 = r34.toString();
        r33.info(r34);
        r16 = 0;
        r10 = 1;
    L_0x01e8:
        r33 = 25;
        r0 = r16;
        r1 = r33;
        if (r0 <= r1) goto L_0x0227;
    L_0x01f0:
        r33 = 1;
        r0 = r33;
        if (r10 != r0) goto L_0x0227;
    L_0x01f6:
        r4 = r37.getCenter();
        r0 = r37;
        r22 = r0.exchangeNW(r4);
        r24 = r22.getDecimalCenter();
        r6 = r24;
        r32 = r5.getZERO();
        r33 = logger;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "trying new NW starting point ";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r6);
        r34 = r34.toString();
        r33.info(r34);
        r16 = 0;
        r10 = 2;
    L_0x0227:
        r33 = 25;
        r0 = r16;
        r1 = r33;
        if (r0 <= r1) goto L_0x0266;
    L_0x022f:
        r33 = 2;
        r0 = r33;
        if (r10 != r0) goto L_0x0266;
    L_0x0235:
        r4 = r37.getCenter();
        r0 = r37;
        r22 = r0.exchangeSW(r4);
        r24 = r22.getDecimalCenter();
        r6 = r24;
        r32 = r5.getZERO();
        r33 = logger;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "trying new SW starting point ";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r6);
        r34 = r34.toString();
        r33.info(r34);
        r16 = 0;
        r10 = 3;
    L_0x0266:
        r33 = 25;
        r0 = r16;
        r1 = r33;
        if (r0 <= r1) goto L_0x02a5;
    L_0x026e:
        r33 = 3;
        r0 = r33;
        if (r10 != r0) goto L_0x02a5;
    L_0x0274:
        r4 = r37.getCenter();
        r0 = r37;
        r22 = r0.exchangeNE(r4);
        r24 = r22.getDecimalCenter();
        r6 = r24;
        r32 = r5.getZERO();
        r33 = logger;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "trying new NE starting point ";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r6);
        r34 = r34.toString();
        r33.info(r34);
        r16 = 0;
        r10 = 4;
    L_0x02a5:
        r33 = 25;
        r0 = r16;
        r1 = r33;
        if (r0 <= r1) goto L_0x031d;
    L_0x02ad:
        r33 = -1;
        r0 = r33;
        if (r10 == r0) goto L_0x02bf;
    L_0x02b3:
        r33 = 4;
        r0 = r33;
        if (r10 == r0) goto L_0x02bf;
    L_0x02b9:
        r33 = 5;
        r0 = r33;
        if (r10 != r0) goto L_0x031d;
    L_0x02bf:
        r25 = r37.randomPoint();
        r27 = new edu.jas.arith.BigDecimal;
        r33 = r25.getRe();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r27;
        r1 = r33;
        r0.<init>(r1);
        r26 = new edu.jas.arith.BigDecimal;
        r33 = r25.getIm();
        r33 = (edu.jas.arith.Rational) r33;
        r33 = r33.getRational();
        r0 = r26;
        r1 = r33;
        r0.<init>(r1);
        r24 = new edu.jas.poly.Complex;
        r0 = r24;
        r1 = r27;
        r2 = r26;
        r0.<init>(r5, r1, r2);
        r6 = r24;
        r32 = r5.getZERO();
        r33 = logger;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "trying new random starting point ";
        r34 = r34.append(r35);
        r0 = r34;
        r34 = r0.append(r6);
        r34 = r34.toString();
        r33.info(r34);
        r33 = -1;
        r0 = r33;
        if (r10 != r0) goto L_0x032d;
    L_0x031a:
        r16 = 0;
        r10 = 0;
    L_0x031d:
        r0 = r32;
        r1 = r23;
        r32 = r0.multiply(r1);
        r0 = r32;
        r11 = r6.subtract(r0);
        goto L_0x0138;
    L_0x032d:
        r33 = 4;
        r0 = r33;
        if (r10 != r0) goto L_0x0337;
    L_0x0333:
        r16 = 0;
        r10 = 5;
        goto L_0x031d;
    L_0x0337:
        r10 = 6;
        goto L_0x031d;
    L_0x0339:
        r6 = r11;
        r17 = r16;
        goto L_0x00eb;
    L_0x033e:
        r33 = new edu.jas.root.NoConvergenceException;
        r34 = new java.lang.StringBuilder;
        r34.<init>();
        r35 = "no convergence after ";
        r34 = r34.append(r35);
        r0 = r34;
        r1 = r16;
        r34 = r0.append(r1);
        r35 = " steps";
        r34 = r34.append(r35);
        r34 = r34.toString();
        r33.<init>(r34);
        throw r33;
    L_0x0361:
        r16 = r17;
        goto L_0x01e8;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.root.ComplexRootsAbstract.approximateRoot(edu.jas.root.Rectangle, edu.jas.poly.GenPolynomial, edu.jas.structure.RingElem):edu.jas.poly.Complex<edu.jas.arith.BigDecimal>");
    }

    public List<Complex<BigDecimal>> approximateRoots(GenPolynomial<Complex<C>> a, C eps) {
        ComplexRing<C> cr = a.ring.coFac;
        SortedMap<GenPolynomial<Complex<C>>, Long> sa = this.engine.squarefreeFactors(a);
        List<Complex<BigDecimal>> roots = new ArrayList();
        for (Entry<GenPolynomial<Complex<C>>, Long> me : sa.entrySet()) {
            GenPolynomial<Complex<C>> p = (GenPolynomial) me.getKey();
            List<Complex<BigDecimal>> arrayList;
            if (p.degree(0) <= 1) {
                Complex<C> tc = ((Complex) p.trailingBaseCoefficient()).negate();
                BigDecimal bigDecimal = new BigDecimal(((Rational) tc.getRe()).getRational());
                Complex<BigDecimal> complex = new Complex(new ComplexRing(bigDecimal), bigDecimal, new BigDecimal(((Rational) tc.getIm()).getRational()));
                arrayList = new ArrayList(1);
                arrayList.add(complex);
            } else {
                C M = rootBound(p).getRe();
                RingElem M1 = (RingElem) M.sum((AbelianGroupElem) M.factory().fromInteger(1));
                if (this.debug) {
                    logger.info("rootBound = " + M);
                }
                Complex[] corner = (Complex[]) new Complex[4];
                corner[0] = new Complex(cr, (RingElem) M1.negate(), M);
                corner[1] = new Complex(cr, (RingElem) M1.negate(), (RingElem) M1.negate());
                corner[2] = new Complex(cr, M, (RingElem) M1.negate());
                corner[3] = new Complex(cr, M, M);
                try {
                    List<Rectangle<C>> rs = complexRoots((Rectangle) new Rectangle(corner), (GenPolynomial) p);
                    arrayList = new ArrayList(rs.size());
                    for (Rectangle<C> r : rs) {
                        Complex<BigDecimal> rr = null;
                        while (rr == null) {
                            Rectangle<C> r2;
                            try {
                                rr = approximateRoot(r2, p, eps);
                                arrayList.add(rr);
                            } catch (NoConvergenceException e) {
                                try {
                                    r2 = complexRootRefinement(r2, p, r2.rationalLength().multiply(new BigRational(1, 1000)));
                                    logger.info("fall back rootRefinement = " + r2);
                                } catch (InvalidBoundaryException ee) {
                                    throw new RuntimeException("this should never happen " + ee);
                                }
                            }
                        }
                    }
                } catch (InvalidBoundaryException e2) {
                    throw new RuntimeException("this should never happen " + e2);
                }
            }
            long e3 = ((Long) me.getValue()).longValue();
            int i = 0;
            while (true) {
                if (((long) i) < e3) {
                    roots.addAll(rf);
                    i++;
                }
            }
        }
        return roots;
    }

    public Complex[] copyOfComplex(Complex[] original, int newLength) {
        Complex[] copy = new Complex[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }

    public Rectangle<C> invariantMagnitudeRectangle(Rectangle<C> rect, GenPolynomial<Complex<C>> f, GenPolynomial<Complex<C>> g, C eps) throws InvalidBoundaryException {
        Rectangle<C> v = rect;
        if (g == null || g.isZERO()) {
            return v;
        }
        if (g.isConstant()) {
            return v;
        }
        if (f == null || f.isZERO() || f.isConstant()) {
            return v;
        }
        C B = magnitudeBound(rect, PolyUtil.baseDeriviative(g));
        BigRational len = v.rationalLength();
        BigRational half = new BigRational(1, 2);
        C vlen = v.length();
        vlen = (RingElem) vlen.multiply(vlen);
        while (((RingElem) B.multiply(vlen)).compareTo(eps) >= 0) {
            len = len.multiply(half);
            v = complexRootRefinement(v, f, len);
            vlen = v.length();
            RingElem vlen2 = (RingElem) vlen.multiply(vlen);
        }
        return v;
    }

    public Complex<C> complexRectangleMagnitude(Rectangle<C> rect, GenPolynomial<Complex<C>> f, GenPolynomial<Complex<C>> g) {
        if (g.isZERO() || g.isConstant()) {
            return (Complex) g.leadingBaseCoefficient();
        }
        return (Complex) PolyUtil.evaluateMain(f.ring.coFac, (GenPolynomial) g, rect.getCenter());
    }

    public Complex<C> complexMagnitude(Rectangle<C> rect, GenPolynomial<Complex<C>> f, GenPolynomial<Complex<C>> g, C eps) throws InvalidBoundaryException {
        if (g.isZERO() || g.isConstant()) {
            return (Complex) g.leadingBaseCoefficient();
        }
        return complexRectangleMagnitude(invariantMagnitudeRectangle(rect, f, g, eps), f, g);
    }
}
