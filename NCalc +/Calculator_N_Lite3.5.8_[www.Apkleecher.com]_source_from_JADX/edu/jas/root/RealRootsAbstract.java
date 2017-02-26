package edu.jas.root;

import edu.jas.arith.BigDecimal;
import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.AbelianGroupElem;
import edu.jas.structure.MonoidElem;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import edu.jas.structure.UnaryFunctor;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public abstract class RealRootsAbstract<C extends RingElem<C> & Rational> implements RealRoots<C> {
    private static final Logger logger;

    class 1 implements UnaryFunctor<C, C> {
        1() {
        }

        public C eval(C a) {
            return (RingElem) a.abs();
        }
    }

    public abstract Interval<C> invariantSignInterval(Interval<C> interval, GenPolynomial<C> genPolynomial, GenPolynomial<C> genPolynomial2);

    public abstract long realRootCount(Interval<C> interval, GenPolynomial<C> genPolynomial);

    public abstract List<Interval<C>> realRoots(GenPolynomial<C> genPolynomial);

    static {
        logger = Logger.getLogger(RealRootsAbstract.class);
    }

    public C realRootBound(GenPolynomial<C> f) {
        if (f == null) {
            return null;
        }
        RingFactory<C> cfac = f.ring.coFac;
        C M = (RingElem) cfac.getONE();
        if (f.isZERO()) {
            return M;
        }
        if (f.isConstant()) {
            return (RingElem) ((RingElem) f.leadingBaseCoefficient().abs()).sum((AbelianGroupElem) cfac.getONE());
        }
        RingElem a = (RingElem) f.leadingBaseCoefficient().abs();
        for (RingElem c : f.getMap().values()) {
            C d = (RingElem) ((RingElem) c.abs()).divide(a);
            if (M.compareTo(d) < 0) {
                M = d;
            }
        }
        BigRational r = ((Rational) M).getRational();
        logger.info("rational root bound: " + r);
        RingElem M2 = (RingElem) ((RingElem) cfac.fromInteger(new BigInteger(r.numerator().divide(r.denominator())).sum(BigInteger.ONE).getVal())).sum((AbelianGroupElem) f.ring.coFac.getONE());
        logger.info("integer root bound: " + M2);
        return M2;
    }

    public C magnitudeBound(Interval<C> iv, GenPolynomial<C> f) {
        if (f == null) {
            return null;
        }
        if (f.isZERO()) {
            return (RingElem) f.ring.coFac.getONE();
        }
        if (f.isConstant()) {
            return (RingElem) f.leadingBaseCoefficient().abs();
        }
        GenPolynomial fa = f.map(new 1());
        RingElem M = (RingElem) iv.left.abs();
        if (M.compareTo(iv.right.abs()) < 0) {
            M = (RingElem) iv.right.abs();
        }
        RingFactory cfac = f.ring.coFac;
        C B = PolyUtil.evaluateMain(cfac, fa, M);
        if (B instanceof RealAlgebraicNumber) {
            BigRational r = ((RealAlgebraicNumber) B).magnitude();
            B = (RingElem) ((RingElem) cfac.fromInteger(r.numerator())).divide((MonoidElem) cfac.fromInteger(r.denominator()));
        }
        return B;
    }

    public C bisectionPoint(Interval<C> iv, GenPolynomial<C> f) {
        if (f == null) {
            return null;
        }
        RingFactory cfac = f.ring.coFac;
        RingElem two = (RingElem) cfac.fromInteger(2);
        C c = (RingElem) ((RingElem) iv.left.sum(iv.right)).divide(two);
        if (f.isZERO() || f.isConstant()) {
            return c;
        }
        C m = PolyUtil.evaluateMain(cfac, (GenPolynomial) f, (RingElem) c);
        while (m.isZERO()) {
            C d = (RingElem) ((RingElem) iv.left.sum(c)).divide(two);
            if (d.equals(c)) {
                d = (RingElem) ((RingElem) iv.right.sum(c)).divide(two);
                if (d.equals(c)) {
                    throw new RuntimeException("should not happen " + iv);
                }
            }
            RingElem c2 = d;
            m = PolyUtil.evaluateMain(cfac, (GenPolynomial) f, c2);
        }
        return c;
    }

    public List<Interval<C>> realRoots(GenPolynomial<C> f, C eps) {
        return refineIntervals(realRoots(f), f, eps);
    }

    public List<Interval<C>> realRoots(GenPolynomial<C> f, BigRational eps) {
        return realRoots((GenPolynomial) f, (RingElem) f.ring.coFac.parse(eps.toString()));
    }

    public boolean signChange(Interval<C> iv, GenPolynomial<C> f) {
        if (f == null) {
            return false;
        }
        RingFactory cfac = f.ring.coFac;
        if (PolyUtil.evaluateMain(cfac, (GenPolynomial) f, iv.left).signum() * PolyUtil.evaluateMain(cfac, (GenPolynomial) f, iv.right).signum() < 0) {
            return true;
        }
        return false;
    }

    public Interval<C> halfInterval(Interval<C> iv, GenPolynomial<C> f) {
        if (f == null || f.isZERO()) {
            return iv;
        }
        C len = iv.length();
        return refineInterval(iv, f, (RingElem) len.divide((RingElem) len.factory().fromInteger(2)));
    }

    public Interval<C> refineInterval(Interval<C> iv, GenPolynomial<C> f, C eps) {
        if (f == null || f.isZERO() || f.isConstant() || eps == null || iv.length().compareTo(eps) < 0) {
            return iv;
        }
        RingFactory cfac = f.ring.coFac;
        RingElem two = (RingElem) cfac.fromInteger(2);
        Interval<C> v = iv;
        while (v.length().compareTo(eps) >= 0) {
            RingElem c = (RingElem) ((RingElem) v.left.sum(v.right)).divide(two);
            if (PolyUtil.evaluateMain(cfac, (GenPolynomial) f, c).isZERO()) {
                v = new Interval(c, c);
                break;
            }
            Interval<C> iv1 = new Interval(v.left, c);
            if (signChange(iv1, f)) {
                v = iv1;
            } else {
                v = new Interval(c, v.right);
            }
        }
        return v;
    }

    public List<Interval<C>> refineIntervals(List<Interval<C>> V, GenPolynomial<C> f, C eps) {
        if (f == null || f.isZERO() || f.isConstant() || eps == null) {
            return V;
        }
        List<Interval<C>> IV = new ArrayList();
        for (Interval<C> v : V) {
            IV.add(refineInterval(v, f, eps));
        }
        return IV;
    }

    public int realIntervalSign(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g) {
        if (g == null || g.isZERO()) {
            return 0;
        }
        if (f == null || f.isZERO() || f.isConstant()) {
            return g.signum();
        }
        if (g.isConstant()) {
            return g.signum();
        }
        RingFactory cfac = f.ring.coFac;
        return PolyUtil.evaluateMain(cfac, (GenPolynomial) g, (RingElem) ((RingElem) iv.left.sum(iv.right)).divide((MonoidElem) cfac.fromInteger(2))).signum();
    }

    public int realSign(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g) {
        if (g == null || g.isZERO()) {
            return 0;
        }
        if (f == null || f.isZERO() || f.isConstant()) {
            return g.signum();
        }
        if (g.isConstant()) {
            return g.signum();
        }
        return realIntervalSign(invariantSignInterval(iv, f, g), f, g);
    }

    public Interval<C> invariantMagnitudeInterval(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g, C eps) {
        Interval<C> v = iv;
        if (g == null || g.isZERO()) {
            return v;
        }
        if (g.isConstant()) {
            return v;
        }
        if (f == null || f.isZERO() || f.isConstant()) {
            return v;
        }
        C B = magnitudeBound(iv, PolyUtil.baseDeriviative(g));
        RingElem two = (RingElem) f.ring.coFac.fromInteger(2);
        while (((RingElem) B.multiply(v.length())).compareTo(eps) >= 0) {
            RingElem c = (RingElem) ((RingElem) v.left.sum(v.right)).divide(two);
            Interval<C> im = new Interval(c, v.right);
            if (signChange(im, f)) {
                v = im;
            } else {
                v = new Interval(v.left, c);
            }
        }
        return v;
    }

    public C realIntervalMagnitude(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g) {
        if (g.isZERO() || g.isConstant()) {
            return g.leadingBaseCoefficient();
        }
        RingFactory cfac = f.ring.coFac;
        C evl = PolyUtil.evaluateMain(cfac, (GenPolynomial) g, iv.left);
        C evr = PolyUtil.evaluateMain(cfac, (GenPolynomial) g, iv.right);
        C ev = evl;
        if (evl.compareTo(evr) <= 0) {
            return evr;
        }
        return ev;
    }

    public C realMagnitude(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g, C eps) {
        if (g.isZERO() || g.isConstant()) {
            return g.leadingBaseCoefficient();
        }
        return realIntervalMagnitude(invariantMagnitudeInterval(iv, f, g, eps), f, g);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.arith.BigDecimal approximateRoot(edu.jas.root.Interval<C> r26, edu.jas.poly.GenPolynomial<C> r27, C r28) throws edu.jas.root.NoConvergenceException {
        /*
        r25 = this;
        if (r26 != 0) goto L_0x000a;
    L_0x0002:
        r22 = new java.lang.IllegalArgumentException;
        r23 = "null interval not allowed";
        r22.<init>(r23);
        throw r22;
    L_0x000a:
        r3 = r26.toDecimal();
        if (r27 == 0) goto L_0x001e;
    L_0x0010:
        r22 = r27.isZERO();
        if (r22 != 0) goto L_0x001e;
    L_0x0016:
        r22 = r27.isConstant();
        if (r22 != 0) goto L_0x001e;
    L_0x001c:
        if (r28 != 0) goto L_0x0020;
    L_0x001e:
        r9 = r3;
    L_0x001f:
        return r9;
    L_0x0020:
        r22 = r26.length();
        r0 = r22;
        r1 = r28;
        r22 = r0.compareTo(r1);
        if (r22 >= 0) goto L_0x0030;
    L_0x002e:
        r9 = r3;
        goto L_0x001f;
    L_0x0030:
        r16 = new edu.jas.arith.BigDecimal;
        r0 = r26;
        r0 = r0.left;
        r22 = r0;
        r22 = (edu.jas.arith.Rational) r22;
        r22 = r22.getRational();
        r0 = r16;
        r1 = r22;
        r0.<init>(r1);
        r19 = new edu.jas.arith.BigDecimal;
        r0 = r26;
        r0 = r0.right;
        r22 = r0;
        r22 = (edu.jas.arith.Rational) r22;
        r22 = r22.getRational();
        r0 = r19;
        r1 = r22;
        r0.<init>(r1);
        r28 = (edu.jas.arith.Rational) r28;
        r18 = r28.getRational();
        r10 = new edu.jas.arith.BigDecimal;
        r0 = r18;
        r10.<init>(r0);
        r17 = new edu.jas.arith.BigDecimal;
        r22 = "0.25";
        r0 = r17;
        r1 = r22;
        r0.<init>(r1);
        r10 = r10.multiply(r3);
        r4 = edu.jas.arith.BigDecimal.ONE;
        r6 = new edu.jas.poly.GenPolynomialRing;
        r0 = r27;
        r0 = r0.ring;
        r22 = r0;
        r0 = r22;
        r6.<init>(r4, r0);
        r0 = r27;
        r5 = edu.jas.poly.PolyUtil.decimalFromRational(r6, r0);
        r11 = edu.jas.poly.PolyUtil.baseDeriviative(r27);
        r7 = edu.jas.poly.PolyUtil.decimalFromRational(r6, r11);
        r14 = 0;
        r2 = 50;
        r8 = 0;
        r15 = r14;
    L_0x0098:
        r14 = r15 + 1;
        r22 = 50;
        r0 = r22;
        if (r15 >= r0) goto L_0x01a7;
    L_0x00a0:
        r13 = edu.jas.poly.PolyUtil.evaluateMain(r4, r5, r3);
        r13 = (edu.jas.arith.BigDecimal) r13;
        r22 = r13.isZERO();
        if (r22 == 0) goto L_0x00af;
    L_0x00ac:
        r9 = r3;
        goto L_0x001f;
    L_0x00af:
        r12 = edu.jas.poly.PolyUtil.evaluateMain(r4, r7, r3);
        r12 = (edu.jas.arith.BigDecimal) r12;
        r22 = r12.isZERO();
        if (r22 == 0) goto L_0x00c3;
    L_0x00bb:
        r22 = new edu.jas.root.NoConvergenceException;
        r23 = "zero deriviative should not happen";
        r22.<init>(r23);
        throw r22;
    L_0x00c3:
        r21 = r13.divide(r12);
        r0 = r21;
        r9 = r3.subtract(r0);
        r22 = r3.subtract(r9);
        r22 = r22.abs();
        r0 = r22;
        r22 = r0.compareTo(r10);
        if (r22 <= 0) goto L_0x001f;
    L_0x00dd:
        r0 = r16;
        r22 = r9.compareTo(r0);
        if (r22 < 0) goto L_0x00ed;
    L_0x00e5:
        r0 = r19;
        r22 = r9.compareTo(r0);
        if (r22 <= 0) goto L_0x01a3;
    L_0x00ed:
        r15 = r14 + 1;
        r22 = 50;
        r0 = r22;
        if (r14 <= r0) goto L_0x0116;
    L_0x00f5:
        r22 = new edu.jas.root.NoConvergenceException;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "no convergence after ";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r15);
        r24 = " steps";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r22.<init>(r23);
        throw r22;
    L_0x0116:
        r22 = 25;
        r0 = r22;
        if (r15 <= r0) goto L_0x01c8;
    L_0x011c:
        if (r8 != 0) goto L_0x01c8;
    L_0x011e:
        r20 = new edu.jas.arith.BigDecimal;
        r22 = r26.randomPoint();
        r22 = (edu.jas.arith.Rational) r22;
        r22 = r22.getRational();
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);
        r3 = r20;
        r21 = r20.getZERO();
        r22 = logger;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "trying new random starting point ";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r3);
        r23 = r23.toString();
        r22.info(r23);
        r14 = 0;
        r8 = 1;
    L_0x0153:
        r22 = 25;
        r0 = r22;
        if (r14 <= r0) goto L_0x0193;
    L_0x0159:
        r22 = 1;
        r0 = r22;
        if (r8 != r0) goto L_0x0193;
    L_0x015f:
        r20 = new edu.jas.arith.BigDecimal;
        r22 = r26.randomPoint();
        r22 = (edu.jas.arith.Rational) r22;
        r22 = r22.getRational();
        r0 = r20;
        r1 = r22;
        r0.<init>(r1);
        r3 = r20;
        r21 = r20.getZERO();
        r22 = logger;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "trying new random starting point ";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r3);
        r23 = r23.toString();
        r22.info(r23);
        r8 = 2;
    L_0x0193:
        r0 = r21;
        r1 = r17;
        r21 = r0.multiply(r1);
        r0 = r21;
        r9 = r3.subtract(r0);
        goto L_0x00dd;
    L_0x01a3:
        r3 = r9;
        r15 = r14;
        goto L_0x0098;
    L_0x01a7:
        r22 = new edu.jas.root.NoConvergenceException;
        r23 = new java.lang.StringBuilder;
        r23.<init>();
        r24 = "no convergence after ";
        r23 = r23.append(r24);
        r0 = r23;
        r23 = r0.append(r14);
        r24 = " steps";
        r23 = r23.append(r24);
        r23 = r23.toString();
        r22.<init>(r23);
        throw r22;
    L_0x01c8:
        r14 = r15;
        goto L_0x0153;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.root.RealRootsAbstract.approximateRoot(edu.jas.root.Interval, edu.jas.poly.GenPolynomial, edu.jas.structure.RingElem):edu.jas.arith.BigDecimal");
    }

    public List<BigDecimal> approximateRoots(GenPolynomial<C> f, C eps) {
        List<Interval<C>> iv = realRoots(f);
        List<BigDecimal> roots = new ArrayList(iv.size());
        for (Interval<C> i : iv) {
            BigDecimal r = null;
            while (r == null) {
                Interval<C> i2;
                try {
                    r = approximateRoot(i2, f, eps);
                    roots.add(r);
                } catch (NoConvergenceException e) {
                    i2 = refineInterval(i2, f, (RingElem) i2.length().divide((MonoidElem) f.ring.coFac.fromInteger(1000)));
                    logger.info("fall back rootRefinement = " + i2);
                }
            }
        }
        return roots;
    }

    public boolean isApproximateRoot(BigDecimal x, GenPolynomial<C> f, C eps) {
        if (x == null) {
            throw new IllegalArgumentException("null root not allowed");
        } else if (f == null || f.isZERO() || f.isConstant() || eps == null) {
            return true;
        } else {
            BigDecimal e = new BigDecimal(((Rational) eps).getRational()).multiply(new BigDecimal("1000"));
            GenPolynomialRing<BigDecimal> dfac = new GenPolynomialRing(BigDecimal.ONE, f.ring);
            return isApproximateRoot(x, PolyUtil.decimalFromRational(dfac, f), PolyUtil.decimalFromRational(dfac, PolyUtil.baseDeriviative(f)), e);
        }
    }

    public boolean isApproximateRoot(BigDecimal x, GenPolynomial<BigDecimal> f, GenPolynomial<BigDecimal> fp, BigDecimal eps) {
        if (x == null) {
            throw new IllegalArgumentException("null root not allowed");
        } else if (f == null || f.isZERO() || f.isConstant() || eps == null) {
            return true;
        } else {
            RingFactory dc = BigDecimal.ONE;
            BigDecimal fx = (BigDecimal) PolyUtil.evaluateMain(dc, (GenPolynomial) f, (RingElem) x);
            if (fx.isZERO()) {
                return true;
            }
            BigDecimal fpx = (BigDecimal) PolyUtil.evaluateMain(dc, (GenPolynomial) fp, (RingElem) x);
            if (fpx.isZERO()) {
                return false;
            }
            BigDecimal d = fx.divide(fpx);
            if (d.isZERO() || d.abs().compareTo(eps) <= 0) {
                return true;
            }
            System.out.println("x     = " + x);
            System.out.println("d     = " + d);
            return false;
        }
    }

    public boolean isApproximateRoot(List<BigDecimal> R, GenPolynomial<C> f, C eps) {
        if (R == null) {
            throw new IllegalArgumentException("null root not allowed");
        } else if (f == null || f.isZERO() || f.isConstant() || eps == null) {
            return true;
        } else {
            BigDecimal e = new BigDecimal(((Rational) eps).getRational()).multiply(new BigDecimal("1000"));
            GenPolynomialRing<BigDecimal> dfac = new GenPolynomialRing(BigDecimal.ONE, f.ring);
            GenPolynomial<BigDecimal> df = PolyUtil.decimalFromRational(dfac, f);
            GenPolynomial<BigDecimal> dfp = PolyUtil.decimalFromRational(dfac, PolyUtil.baseDeriviative(f));
            for (BigDecimal x : R) {
                if (!isApproximateRoot(x, df, dfp, e)) {
                    return false;
                }
            }
            return true;
        }
    }
}
