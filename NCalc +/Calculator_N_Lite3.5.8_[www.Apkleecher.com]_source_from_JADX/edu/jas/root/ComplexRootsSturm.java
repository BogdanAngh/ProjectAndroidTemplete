package edu.jas.root;

import edu.jas.arith.Rational;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class ComplexRootsSturm<C extends RingElem<C> & Rational> extends ComplexRootsAbstract<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(ComplexRootsSturm.class);
    }

    public ComplexRootsSturm(RingFactory<Complex<C>> cf) {
        super(cf);
        this.debug = logger.isDebugEnabled();
    }

    public long indexOfCauchy(C a, C b, GenPolynomial<C> f, GenPolynomial<C> g) {
        List S = sturmSequence(g, f);
        if (this.debug) {
            logger.info("sturmSeq = " + S);
        }
        RingFactory cfac = f.ring.coFac;
        return RootUtil.signVar(PolyUtil.evaluateMain(cfac, S, (RingElem) a)) - RootUtil.signVar(PolyUtil.evaluateMain(cfac, S, (RingElem) b));
    }

    public long[] indexOfRouth(C a, C b, GenPolynomial<C> f, GenPolynomial<C> g) {
        List S = sturmSequence(f, g);
        RingFactory cfac = f.ring.coFac;
        long v = RootUtil.signVar(PolyUtil.evaluateMain(cfac, S, (RingElem) a)) - RootUtil.signVar(PolyUtil.evaluateMain(cfac, S, (RingElem) b));
        long d = f.degree(0);
        if (d < g.degree(0)) {
            d = g.degree(0);
        }
        long ui = (d - v) / 2;
        long li = (d + v) / 2;
        return new long[]{ui, li};
    }

    public List<GenPolynomial<C>> sturmSequence(GenPolynomial<C> f, GenPolynomial<C> g) {
        List<GenPolynomial<C>> S = new ArrayList();
        if (f == null || f.isZERO()) {
            return S;
        }
        if (f.isConstant()) {
            S.add(f.monic());
            return S;
        }
        GenPolynomial F = f;
        S.add(F);
        GenPolynomial G = g;
        while (!G.isZERO()) {
            GenPolynomial<C> r = F.remainder(G);
            F = G;
            G = r.negate();
            S.add(F);
        }
        if (F.isConstant()) {
            return S;
        }
        List<GenPolynomial<C>> Sp = new ArrayList(S.size());
        for (GenPolynomial<C> p : S) {
            Sp.add(p.divide(F));
        }
        return Sp;
    }

    public long complexRootCount(Rectangle<C> rect, GenPolynomial<Complex<C>> a) throws InvalidBoundaryException {
        C rl = rect.lengthReal();
        C il = rect.lengthImag();
        if (!rl.isZERO() || !il.isZERO()) {
            if (rl.isZERO() || il.isZERO()) {
                Complex<C> sw;
                Complex<C> ne;
                Complex cd;
                if (rl.isZERO()) {
                    sw = rect.getSW();
                    ne = rect.getNE();
                    cd = new Complex(sw.ring, (RingElem) sw.ring.ring.parse("1"));
                    sw = sw.subtract(cd);
                    rect = rect.exchangeSW(sw).exchangeNE(ne.sum(cd));
                    logger.info("new rectangle: " + rect.toScript());
                }
                if (il.isZERO()) {
                    sw = rect.getSW();
                    ne = rect.getNE();
                    cd = new Complex(sw.ring, (RingElem) sw.ring.ring.getZERO(), (RingElem) sw.ring.ring.parse("1"));
                    sw = sw.subtract(cd);
                    rect = rect.exchangeSW(sw).exchangeNE(ne.sum(cd));
                    logger.info("new rectangle: " + rect.toScript());
                }
            }
            return windingNumber(rect, a);
        } else if (((Complex) PolyUtil.evaluateMain(a.ring.coFac, (GenPolynomial) a, rect.getSW())).isZERO()) {
            return 1;
        } else {
            return 0;
        }
    }

    public long windingNumber(Rectangle<C> rect, GenPolynomial<Complex<C>> A) throws InvalidBoundaryException {
        Boundary<C> bound = new Boundary(rect, A);
        RingFactory<C> cf = A.ring.coFac.ring;
        RingElem zero = (RingElem) cf.getZERO();
        RingElem one = (RingElem) cf.getONE();
        long ix = 0;
        for (int i = 0; i < 4; i++) {
            ix += indexOfCauchy(zero, one, bound.getRealPart(i), bound.getImagPart(i));
        }
        if (ix % 2 == 0) {
            return ix / 2;
        }
        throw new InvalidBoundaryException("odd winding number " + ix);
    }

    public List<Rectangle<C>> complexRoots(Rectangle<C> rect, GenPolynomial<Complex<C>> a) throws InvalidBoundaryException {
        ComplexRing<C> cr = a.ring.coFac;
        List<Rectangle<C>> roots = new ArrayList();
        if (!(a.isConstant() || a.isZERO())) {
            long n = windingNumber(rect, a);
            if (n < 0) {
                throw new RuntimeException("negative winding number " + n);
            } else if (n != 0) {
                if (n == 1) {
                    roots.add(rect);
                } else {
                    Complex<C> eps = cr.fromInteger(1).divide(cr.fromInteger(1000));
                    Complex[] complexArr = rect.corners;
                    Complex[] complexArr2 = rect.corners;
                    Complex<C> delta = r0[3].subtract(r0[1]).divide(cr.fromInteger(2));
                    boolean work = true;
                    while (work) {
                        Complex<C> center = rect.corners[1].sum((Complex) delta);
                        if (this.debug) {
                            logger.info("new center = " + center);
                        }
                        try {
                            Complex[] cp = copyOfComplex(rect.corners, 4);
                            cp[1] = new Complex(cr, cp[1].getRe(), center.getIm());
                            cp[2] = center;
                            cp[3] = new Complex(cr, center.getRe(), cp[3].getIm());
                            roots.addAll(complexRoots(new Rectangle(cp), a));
                            if (((long) roots.size()) == a.degree(0)) {
                                break;
                            }
                            cp = copyOfComplex(rect.corners, 4);
                            cp[0] = new Complex(cr, cp[0].getRe(), center.getIm());
                            cp[2] = new Complex(cr, center.getRe(), cp[2].getIm());
                            cp[3] = center;
                            roots.addAll(complexRoots(new Rectangle(cp), a));
                            if (((long) roots.size()) == a.degree(0)) {
                                break;
                            }
                            cp = copyOfComplex(rect.corners, 4);
                            cp[0] = center;
                            cp[1] = new Complex(cr, center.getRe(), cp[1].getIm());
                            cp[3] = new Complex(cr, cp[3].getRe(), center.getIm());
                            roots.addAll(complexRoots(new Rectangle(cp), a));
                            if (((long) roots.size()) == a.degree(0)) {
                                break;
                            }
                            cp = copyOfComplex(rect.corners, 4);
                            cp[0] = new Complex(cr, center.getRe(), cp[0].getIm());
                            cp[1] = center;
                            cp[2] = new Complex(cr, cp[2].getRe(), center.getIm());
                            roots.addAll(complexRoots(new Rectangle(cp), a));
                            work = false;
                        } catch (InvalidBoundaryException e) {
                            delta = delta.sum(delta.multiply((Complex) eps));
                            eps = eps.sum(eps.multiply(cr.getIMAG()));
                        }
                    }
                }
            }
        }
        return roots;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.root.Rectangle<C> invariantRectangle(edu.jas.root.Rectangle<C> r13, edu.jas.poly.GenPolynomial<edu.jas.poly.Complex<C>> r14, edu.jas.poly.GenPolynomial<edu.jas.poly.Complex<C>> r15) throws edu.jas.root.InvalidBoundaryException {
        /*
        r12 = this;
        r4 = r13;
        if (r15 == 0) goto L_0x0009;
    L_0x0003:
        r7 = r15.isZERO();
        if (r7 == 0) goto L_0x000b;
    L_0x0009:
        r5 = r4;
    L_0x000a:
        return r5;
    L_0x000b:
        r7 = r15.isConstant();
        if (r7 == 0) goto L_0x0013;
    L_0x0011:
        r5 = r4;
        goto L_0x000a;
    L_0x0013:
        if (r14 == 0) goto L_0x0021;
    L_0x0015:
        r7 = r14.isZERO();
        if (r7 != 0) goto L_0x0021;
    L_0x001b:
        r7 = r14.isConstant();
        if (r7 == 0) goto L_0x0023;
    L_0x0021:
        r5 = r4;
        goto L_0x000a;
    L_0x0023:
        r1 = r4.rationalLength();
        r0 = new edu.jas.arith.BigRational;
        r8 = 1;
        r10 = 2;
        r0.<init>(r8, r10);
    L_0x0030:
        r2 = r12.windingNumber(r4, r15);
        r8 = 0;
        r7 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r7 >= 0) goto L_0x0053;
    L_0x003a:
        r7 = new java.lang.RuntimeException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "negative winding number ";
        r8 = r8.append(r9);
        r8 = r8.append(r2);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
    L_0x0053:
        r8 = 0;
        r7 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r7 != 0) goto L_0x005b;
    L_0x0059:
        r5 = r4;
        goto L_0x000a;
    L_0x005b:
        r1 = r1.multiply(r0);
        r6 = r4;
        r4 = r12.complexRootRefinement(r4, r14, r1);
        r7 = r4.equals(r6);
        if (r7 == 0) goto L_0x0030;
    L_0x006a:
        r7 = r14.gcd(r15);
        r7 = r7.isONE();
        if (r7 != 0) goto L_0x0030;
    L_0x0074:
        r7 = java.lang.System.out;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "f.gcd(g) = ";
        r8 = r8.append(r9);
        r9 = r14.gcd(r15);
        r8 = r8.append(r9);
        r8 = r8.toString();
        r7.println(r8);
        r7 = new java.lang.RuntimeException;
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r9 = "no convergence ";
        r8 = r8.append(r9);
        r8 = r8.append(r4);
        r8 = r8.toString();
        r7.<init>(r8);
        throw r7;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.root.ComplexRootsSturm.invariantRectangle(edu.jas.root.Rectangle, edu.jas.poly.GenPolynomial, edu.jas.poly.GenPolynomial):edu.jas.root.Rectangle<C>");
    }
}
