package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenSolvablePolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class SolvableReductionSeq<C extends RingElem<C>> extends SolvableReductionAbstract<C> {
    private static final Logger logger;

    static {
        logger = Logger.getLogger(SolvableReductionSeq.class);
    }

    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenSolvablePolynomial<C>[] p = new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            Entry<ExpVector, C> m;
            if (P[i] != null) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = (RingElem) m.getValue();
                    j++;
                }
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            if (logger.isDebugEnabled()) {
                logger.debug("red, e = " + e);
            }
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                GenSolvablePolynomial<C> Q = p[i].multiplyLeft(e);
                a = (RingElem) a.divide(Q.leadingBaseCoefficient());
                ExpVector g1 = S.leadingExpVector();
                S = S.subtractMultiple(a, Q);
                if (g1.equals(S.leadingExpVector())) {
                    throw new RuntimeException("g1.equals(g2): " + g1 + ", a = " + a + ", lc(S) = " + S.leadingBaseCoefficient());
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<C> leftNormalform(List<GenSolvablePolynomial<C>> row, List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int i;
        GenSolvablePolynomial<C>[] P = new GenSolvablePolynomial[0];
        synchronized (Pp) {
            P = (GenSolvablePolynomial[]) Pp.toArray(P);
        }
        int l = P.length;
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenSolvablePolynomial[] p = (GenSolvablePolynomial[]) new GenSolvablePolynomial[l];
        int j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (RingElem) m.getValue();
                j++;
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> zero = Ap.ring.getZERO();
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                GenSolvablePolynomial<C> Q = p[i].multiplyLeft(e);
                a = (RingElem) a.divide(Q.leadingBaseCoefficient());
                ExpVector g1 = S.leadingExpVector();
                S = S.subtractMultiple(a, Q);
                if (g1.equals(S.leadingExpVector())) {
                    throw new RuntimeException("g1.equals(g2): " + g1 + ", a = " + a + ", lc(S) = " + S.leadingBaseCoefficient());
                }
                GenSolvablePolynomial<C> fac = (GenSolvablePolynomial) row.get(i);
                if (fac == null) {
                    fac = (GenSolvablePolynomial) zero.sum(a, e);
                } else {
                    fac = (GenSolvablePolynomial) fac.sum(a, e);
                }
                row.set(i, fac);
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    public GenSolvablePolynomial<C> rightNormalform(List<GenSolvablePolynomial<C>> Pp, GenSolvablePolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        int l;
        int j;
        int i;
        synchronized (Pp) {
            l = Pp.size();
            GenSolvablePolynomial[] P = (GenSolvablePolynomial[]) new GenSolvablePolynomial[l];
            for (j = 0; j < Pp.size(); j++) {
                P[j] = (GenSolvablePolynomial) Pp.get(j);
            }
        }
        ExpVector[] htl = new ExpVector[l];
        RingElem[] lbc = (RingElem[]) new RingElem[l];
        GenSolvablePolynomial[] p = (GenSolvablePolynomial[]) new GenSolvablePolynomial[l];
        j = 0;
        for (i = 0; i < l; i++) {
            p[i] = P[i];
            Entry<ExpVector, C> m = p[i].leadingMonomial();
            if (m != null) {
                p[j] = p[i];
                htl[j] = (ExpVector) m.getKey();
                lbc[j] = (RingElem) m.getValue();
                j++;
            }
        }
        l = j;
        boolean mt = false;
        GenSolvablePolynomial<C> R = Ap.ring.getZERO().copy();
        GenSolvablePolynomial<C> S = Ap.copy();
        while (S.length() > 0) {
            m = S.leadingMonomial();
            ExpVector e = (ExpVector) m.getKey();
            RingElem a = (RingElem) m.getValue();
            i = 0;
            while (i < l) {
                mt = e.multipleOf(htl[i]);
                if (mt) {
                    break;
                }
                i++;
            }
            if (mt) {
                e = e.subtract(htl[i]);
                GenSolvablePolynomial<C> Q = p[i].multiply(e);
                a = (RingElem) a.divide(Q.leadingBaseCoefficient());
                Q = Q.multiply(a);
                ExpVector g1 = S.leadingExpVector();
                S = (GenSolvablePolynomial) S.subtract((GenPolynomial) Q);
                if (g1.equals(S.leadingExpVector())) {
                    throw new RuntimeException("g1.equals(g2): " + g1 + ", a = " + a + ", lc(S) = " + S.leadingBaseCoefficient());
                }
            } else {
                R.doPutToMap(e, a);
                S.doRemoveFromMap(e, a);
            }
        }
        return R;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenSolvablePolynomial<C> rightNormalform(java.util.List<edu.jas.poly.GenSolvablePolynomial<C>> r23, java.util.List<edu.jas.poly.GenSolvablePolynomial<C>> r24, edu.jas.poly.GenSolvablePolynomial<C> r25) {
        /*
        r22 = this;
        if (r24 == 0) goto L_0x0008;
    L_0x0002:
        r19 = r24.isEmpty();
        if (r19 == 0) goto L_0x0009;
    L_0x0008:
        return r25;
    L_0x0009:
        if (r25 == 0) goto L_0x0008;
    L_0x000b:
        r19 = r25.isZERO();
        if (r19 != 0) goto L_0x0008;
    L_0x0011:
        r13 = r24.size();
        r1 = new edu.jas.poly.GenSolvablePolynomial[r13];
        r1 = (edu.jas.poly.GenSolvablePolynomial[]) r1;
        monitor-enter(r24);
        r11 = 0;
    L_0x001b:
        r19 = r24.size();	 Catch:{ all -> 0x0062 }
        r0 = r19;
        if (r11 >= r0) goto L_0x0030;
    L_0x0023:
        r0 = r24;
        r19 = r0.get(r11);	 Catch:{ all -> 0x0062 }
        r19 = (edu.jas.poly.GenSolvablePolynomial) r19;	 Catch:{ all -> 0x0062 }
        r1[r11] = r19;	 Catch:{ all -> 0x0062 }
        r11 = r11 + 1;
        goto L_0x001b;
    L_0x0030:
        monitor-exit(r24);	 Catch:{ all -> 0x0062 }
        r10 = new edu.jas.poly.ExpVector[r13];
        r14 = new java.lang.Object[r13];
        r0 = new edu.jas.poly.GenSolvablePolynomial[r13];
        r17 = r0;
        r17 = (edu.jas.poly.GenSolvablePolynomial[]) r17;
        r12 = 0;
        r11 = 0;
    L_0x003d:
        if (r11 >= r13) goto L_0x0065;
    L_0x003f:
        r19 = r1[r11];
        r17[r11] = r19;
        r19 = r17[r11];
        r15 = r19.leadingMonomial();
        if (r15 == 0) goto L_0x005f;
    L_0x004b:
        r19 = r17[r11];
        r17[r12] = r19;
        r19 = r15.getKey();
        r19 = (edu.jas.poly.ExpVector) r19;
        r10[r12] = r19;
        r19 = r15.getValue();
        r14[r12] = r19;
        r12 = r12 + 1;
    L_0x005f:
        r11 = r11 + 1;
        goto L_0x003d;
    L_0x0062:
        r19 = move-exception;
        monitor-exit(r24);	 Catch:{ all -> 0x0062 }
        throw r19;
    L_0x0065:
        r13 = r12;
        r16 = 0;
        r0 = r25;
        r0 = r0.ring;
        r19 = r0;
        r18 = r19.getZERO();
        r0 = r25;
        r0 = r0.ring;
        r19 = r0;
        r19 = r19.getZERO();
        r3 = r19.copy();
        r7 = 0;
        r2 = 0;
        r4 = r25.copy();
    L_0x0086:
        r19 = r4.length();
        if (r19 <= 0) goto L_0x013e;
    L_0x008c:
        r15 = r4.leadingMonomial();
        r6 = r15.getKey();
        r6 = (edu.jas.poly.ExpVector) r6;
        r5 = r15.getValue();
        r5 = (edu.jas.structure.RingElem) r5;
        r11 = 0;
    L_0x009d:
        if (r11 >= r13) goto L_0x00a9;
    L_0x009f:
        r19 = r10[r11];
        r0 = r19;
        r16 = r6.multipleOf(r0);
        if (r16 == 0) goto L_0x00b2;
    L_0x00a9:
        if (r16 != 0) goto L_0x00b5;
    L_0x00ab:
        r3.doPutToMap(r6, r5);
        r4.doRemoveFromMap(r6, r5);
        goto L_0x0086;
    L_0x00b2:
        r11 = r11 + 1;
        goto L_0x009d;
    L_0x00b5:
        r19 = r10[r11];
        r0 = r19;
        r6 = r6.subtract(r0);
        r19 = r17[r11];
        r0 = r19;
        r2 = r0.multiply(r6);
        r19 = r2.leadingBaseCoefficient();
        r0 = r19;
        r5 = r5.divide(r0);
        r5 = (edu.jas.structure.RingElem) r5;
        r2 = r2.multiply(r5);
        r8 = r4.leadingExpVector();
        r4 = r4.subtract(r2);
        r4 = (edu.jas.poly.GenSolvablePolynomial) r4;
        r9 = r4.leadingExpVector();
        r19 = r8.equals(r9);
        if (r19 == 0) goto L_0x011e;
    L_0x00e9:
        r19 = new java.lang.RuntimeException;
        r20 = new java.lang.StringBuilder;
        r20.<init>();
        r21 = "g1.equals(g2): ";
        r20 = r20.append(r21);
        r0 = r20;
        r20 = r0.append(r8);
        r21 = ", a = ";
        r20 = r20.append(r21);
        r0 = r20;
        r20 = r0.append(r5);
        r21 = ", lc(S) = ";
        r20 = r20.append(r21);
        r21 = r4.leadingBaseCoefficient();
        r20 = r20.append(r21);
        r20 = r20.toString();
        r19.<init>(r20);
        throw r19;
    L_0x011e:
        r0 = r23;
        r7 = r0.get(r11);
        r7 = (edu.jas.poly.GenSolvablePolynomial) r7;
        if (r7 != 0) goto L_0x0137;
    L_0x0128:
        r0 = r18;
        r7 = r0.sum(r5, r6);
        r7 = (edu.jas.poly.GenSolvablePolynomial) r7;
    L_0x0130:
        r0 = r23;
        r0.set(r11, r7);
        goto L_0x0086;
    L_0x0137:
        r7 = r7.sum(r5, r6);
        r7 = (edu.jas.poly.GenSolvablePolynomial) r7;
        goto L_0x0130;
    L_0x013e:
        r25 = r3;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.SolvableReductionSeq.rightNormalform(java.util.List, java.util.List, edu.jas.poly.GenSolvablePolynomial):edu.jas.poly.GenSolvablePolynomial<C>");
    }
}
