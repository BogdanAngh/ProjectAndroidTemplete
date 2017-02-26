package edu.jas.root;

import edu.jas.arith.BigRational;
import edu.jas.arith.Rational;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.PolyUtil;
import edu.jas.structure.RingElem;
import edu.jas.structure.RingFactory;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class RealRootsSturm<C extends RingElem<C> & Rational> extends RealRootsAbstract<C> {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(RealRootsSturm.class);
        debug = logger.isDebugEnabled();
    }

    public List<GenPolynomial<C>> sturmSequence(GenPolynomial<C> f) {
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
        GenPolynomial G = PolyUtil.baseDeriviative(f);
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

    public List<Interval<C>> realRoots(GenPolynomial<C> f) {
        List<Interval<C>> R = new ArrayList();
        if (!(f == null || f.isConstant())) {
            if (f.isZERO()) {
                R.add(new Interval((RingElem) f.ring.coFac.getZERO()));
            } else if (f.degree(0) == 1) {
                R.add(new Interval((RingElem) f.monic().trailingBaseCoefficient().negate()));
            } else {
                GenPolynomial<C> F = f;
                C M = realRootBound(F);
                List<Interval<C>> Rp = realRoots(new Interval((RingElem) M.negate(), M), sturmSequence(F));
                if (logger.isInfoEnabled() && !(f.ring.coFac instanceof BigRational)) {
                    logger.info("realRoots: " + Rp);
                }
                R.addAll(Rp);
            }
        }
        return R;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.util.List<edu.jas.root.Interval<C>> realRoots(edu.jas.root.Interval<C> r27, java.util.List<edu.jas.poly.GenPolynomial<C>> r28) {
        /*
        r26 = this;
        r2 = new java.util.ArrayList;
        r2.<init>();
        r21 = 0;
        r0 = r28;
        r1 = r21;
        r11 = r0.get(r1);
        r11 = (edu.jas.poly.GenPolynomial) r11;
        r21 = r11.isZERO();
        if (r21 == 0) goto L_0x0067;
    L_0x0017:
        r20 = r11.leadingBaseCoefficient();
        r0 = r27;
        r1 = r20;
        r21 = r0.contains(r1);
        if (r21 != 0) goto L_0x005c;
    L_0x0025:
        r21 = new java.lang.IllegalArgumentException;
        r22 = new java.lang.StringBuilder;
        r22.<init>();
        r23 = "root not in interval: f = ";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r11);
        r23 = ", iv = ";
        r22 = r22.append(r23);
        r0 = r22;
        r1 = r27;
        r22 = r0.append(r1);
        r23 = ", z = ";
        r22 = r22.append(r23);
        r0 = r22;
        r1 = r20;
        r22 = r0.append(r1);
        r22 = r22.toString();
        r21.<init>(r22);
        throw r21;
    L_0x005c:
        r12 = new edu.jas.root.Interval;
        r0 = r20;
        r12.<init>(r0);
        r2.add(r12);
    L_0x0066:
        return r2;
    L_0x0067:
        r21 = r11.isConstant();
        if (r21 != 0) goto L_0x0066;
    L_0x006d:
        r21 = 0;
        r0 = r21;
        r22 = r11.degree(r0);
        r24 = 1;
        r21 = (r22 > r24 ? 1 : (r22 == r24 ? 0 : -1));
        if (r21 != 0) goto L_0x009e;
    L_0x007b:
        r21 = r11.monic();
        r21 = r21.trailingBaseCoefficient();
        r20 = r21.negate();
        r20 = (edu.jas.structure.RingElem) r20;
        r0 = r27;
        r1 = r20;
        r21 = r0.contains(r1);
        if (r21 == 0) goto L_0x0066;
    L_0x0093:
        r12 = new edu.jas.root.Interval;
        r0 = r20;
        r12.<init>(r0);
        r2.add(r12);
        goto L_0x0066;
    L_0x009e:
        r18 = r26.realRootCount(r27, r28);
        r22 = 0;
        r21 = (r18 > r22 ? 1 : (r18 == r22 ? 0 : -1));
        if (r21 == 0) goto L_0x0066;
    L_0x00a8:
        r22 = 1;
        r21 = (r18 > r22 ? 1 : (r18 == r22 ? 0 : -1));
        if (r21 != 0) goto L_0x00b4;
    L_0x00ae:
        r0 = r27;
        r2.add(r0);
        goto L_0x0066;
    L_0x00b4:
        r0 = r26;
        r1 = r27;
        r8 = r0.bisectionPoint(r1, r11);
        r12 = new edu.jas.root.Interval;
        r0 = r27;
        r0 = r0.left;
        r21 = r0;
        r0 = r21;
        r12.<init>(r0, r8);
        r15 = new edu.jas.root.Interval;
        r0 = r27;
        r0 = r0.right;
        r21 = r0;
        r0 = r21;
        r15.<init>(r8, r0);
        r0 = r26;
        r1 = r28;
        r3 = r0.realRoots(r12, r1);
        r21 = debug;
        if (r21 == 0) goto L_0x00fc;
    L_0x00e2:
        r21 = logger;
        r22 = new java.lang.StringBuilder;
        r22.<init>();
        r23 = "R1 = ";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r3);
        r22 = r22.toString();
        r21.info(r22);
    L_0x00fc:
        r0 = r26;
        r1 = r28;
        r4 = r0.realRoots(r15, r1);
        r21 = debug;
        if (r21 == 0) goto L_0x0122;
    L_0x0108:
        r21 = logger;
        r22 = new java.lang.StringBuilder;
        r22.<init>();
        r23 = "R2 = ";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r4);
        r22 = r22.toString();
        r21.info(r22);
    L_0x0122:
        r21 = r3.isEmpty();
        if (r21 == 0) goto L_0x012d;
    L_0x0128:
        r2.addAll(r4);
        goto L_0x0066;
    L_0x012d:
        r21 = r4.isEmpty();
        if (r21 == 0) goto L_0x0138;
    L_0x0133:
        r2.addAll(r3);
        goto L_0x0066;
    L_0x0138:
        r21 = r3.size();
        r21 = r21 + -1;
        r0 = r21;
        r12 = r3.get(r0);
        r12 = (edu.jas.root.Interval) r12;
        r21 = 0;
        r0 = r21;
        r15 = r4.get(r0);
        r15 = (edu.jas.root.Interval) r15;
        r0 = r12.right;
        r21 = r0;
        r0 = r15.left;
        r22 = r0;
        r21 = r21.compareTo(r22);
        if (r21 >= 0) goto L_0x0166;
    L_0x015e:
        r2.addAll(r3);
        r2.addAll(r4);
        goto L_0x0066;
    L_0x0166:
        r3.remove(r12);
        r4.remove(r15);
    L_0x016c:
        r0 = r12.right;
        r21 = r0;
        r0 = r15.left;
        r22 = r0;
        r21 = r21.equals(r22);
        if (r21 == 0) goto L_0x01d1;
    L_0x017a:
        r0 = r26;
        r9 = r0.bisectionPoint(r12, r11);
        r0 = r26;
        r10 = r0.bisectionPoint(r15, r11);
        r13 = new edu.jas.root.Interval;
        r0 = r12.left;
        r21 = r0;
        r0 = r21;
        r13.<init>(r0, r9);
        r14 = new edu.jas.root.Interval;
        r0 = r12.right;
        r21 = r0;
        r0 = r21;
        r14.<init>(r9, r0);
        r16 = new edu.jas.root.Interval;
        r0 = r15.left;
        r21 = r0;
        r0 = r16;
        r1 = r21;
        r0.<init>(r1, r10);
        r17 = new edu.jas.root.Interval;
        r0 = r15.right;
        r21 = r0;
        r0 = r17;
        r1 = r21;
        r0.<init>(r10, r1);
        r0 = r26;
        r5 = r0.signChange(r13, r11);
        r0 = r26;
        r6 = r0.signChange(r14, r11);
        r0 = r26;
        r1 = r17;
        r7 = r0.signChange(r1, r11);
        if (r5 == 0) goto L_0x01e2;
    L_0x01cc:
        r12 = r13;
        if (r7 == 0) goto L_0x01df;
    L_0x01cf:
        r15 = r17;
    L_0x01d1:
        r2.addAll(r3);
        r2.add(r12);
        r2.add(r15);
        r2.addAll(r4);
        goto L_0x0066;
    L_0x01df:
        r15 = r16;
        goto L_0x01d1;
    L_0x01e2:
        if (r7 == 0) goto L_0x01ec;
    L_0x01e4:
        r15 = r17;
        if (r6 == 0) goto L_0x01ea;
    L_0x01e8:
        r12 = r14;
        goto L_0x01d1;
    L_0x01ea:
        r12 = r13;
        goto L_0x01d1;
    L_0x01ec:
        r12 = r14;
        r15 = r16;
        goto L_0x016c;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.root.RealRootsSturm.realRoots(edu.jas.root.Interval, java.util.List):java.util.List<edu.jas.root.Interval<C>>");
    }

    public long realRootCount(Interval<C> iv, List<GenPolynomial<C>> S) {
        RingFactory cfac = ((GenPolynomial) S.get(0)).ring.coFac;
        long v = RootUtil.signVar(PolyUtil.evaluateMain(cfac, (List) S, iv.left)) - RootUtil.signVar(PolyUtil.evaluateMain(cfac, (List) S, iv.right));
        if (v < 0) {
            return -v;
        }
        return v;
    }

    public long realRootCount(Interval<C> iv, GenPolynomial<C> f) {
        if (f == null || f.isConstant()) {
            return 0;
        }
        if (!f.isZERO()) {
            return realRootCount((Interval) iv, sturmSequence(f));
        }
        if (iv.contains(f.leadingBaseCoefficient())) {
            return 1;
        }
        return 0;
    }

    public Interval<C> invariantSignInterval(Interval<C> iv, GenPolynomial<C> f, GenPolynomial<C> g) {
        Interval<C> v = iv;
        if (g == null || g.isZERO() || g.isConstant()) {
            return v;
        }
        if (f != null && !f.isZERO()) {
            return invariantSignInterval((Interval) iv, (GenPolynomial) f, sturmSequence(g.monic()));
        }
        throw new IllegalArgumentException("f == 0");
    }

    public Interval<C> invariantSignInterval(Interval<C> iv, GenPolynomial<C> f, List<GenPolynomial<C>> Sg) {
        Interval<C> v = iv;
        GenPolynomial<C> g = (GenPolynomial) Sg.get(0);
        if (g == null || g.isZERO()) {
            return v;
        }
        if (g.isConstant()) {
            return v;
        }
        if (f == null || f.isZERO()) {
            return v;
        }
        RingElem two = (RingElem) f.ring.coFac.fromInteger(2);
        while (true) {
            long n = realRootCount((Interval) v, (List) Sg);
            logger.debug("n = " + n);
            if (n == 0) {
                return v;
            }
            RingElem c = (RingElem) ((RingElem) v.left.sum(v.right)).divide(two);
            Interval<C> im = new Interval(c, v.right);
            if (signChange(im, f)) {
                v = im;
            } else {
                v = new Interval(v.left, c);
            }
        }
    }
}
