package edu.jas.gb;

import edu.jas.poly.ExpVector;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;

public class ReductionSeq<C extends RingElem<C>> extends ReductionAbstract<C> {
    public GenPolynomial<C> normalform(List<GenPolynomial<C>> Pp, GenPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        if (Ap.ring.coFac.isField()) {
            int l;
            GenPolynomial<C>[] P;
            int i;
            Entry<ExpVector, C> m;
            synchronized (Pp) {
                l = Pp.size();
                P = new GenPolynomial[l];
                for (i = 0; i < Pp.size(); i++) {
                    P[i] = (GenPolynomial) Pp.get(i);
                }
            }
            ExpVector[] htl = new ExpVector[l];
            Object[] lbc = new Object[l];
            GenPolynomial<C>[] p = new GenPolynomial[l];
            int j = 0;
            for (i = 0; i < l; i++) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (ExpVector) m.getKey();
                    lbc[j] = m.getValue();
                    j++;
                }
            }
            l = j;
            boolean mt = false;
            GenPolynomial<C> R = Ap.ring.getZERO().copy();
            GenPolynomial<C> S = Ap.copy();
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
                    S = S.subtractMultiple((RingElem) a.divide((RingElem) lbc[i]), e.subtract(htl[i]), p[i]);
                } else {
                    R.doPutToMap(e, a);
                    S.doRemoveFromMap(e, a);
                }
            }
            return R;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenPolynomial<C> normalform(java.util.List<edu.jas.poly.GenPolynomial<C>> r20, java.util.List<edu.jas.poly.GenPolynomial<C>> r21, edu.jas.poly.GenPolynomial<C> r22) {
        /*
        r19 = this;
        if (r21 == 0) goto L_0x0008;
    L_0x0002:
        r17 = r21.isEmpty();
        if (r17 == 0) goto L_0x0009;
    L_0x0008:
        return r22;
    L_0x0009:
        if (r22 == 0) goto L_0x0008;
    L_0x000b:
        r17 = r22.isZERO();
        if (r17 != 0) goto L_0x0008;
    L_0x0011:
        r0 = r22;
        r0 = r0.ring;
        r17 = r0;
        r0 = r17;
        r0 = r0.coFac;
        r17 = r0;
        r17 = r17.isField();
        if (r17 != 0) goto L_0x002b;
    L_0x0023:
        r17 = new java.lang.IllegalArgumentException;
        r18 = "coefficients not from a field";
        r17.<init>(r18);
        throw r17;
    L_0x002b:
        r11 = r21.size();
        r1 = new edu.jas.poly.GenPolynomial[r11];
        monitor-enter(r21);
        r9 = 0;
    L_0x0033:
        r17 = r21.size();	 Catch:{ all -> 0x0076 }
        r0 = r17;
        if (r9 >= r0) goto L_0x0048;
    L_0x003b:
        r0 = r21;
        r17 = r0.get(r9);	 Catch:{ all -> 0x0076 }
        r17 = (edu.jas.poly.GenPolynomial) r17;	 Catch:{ all -> 0x0076 }
        r1[r9] = r17;	 Catch:{ all -> 0x0076 }
        r9 = r9 + 1;
        goto L_0x0033;
    L_0x0048:
        monitor-exit(r21);	 Catch:{ all -> 0x0076 }
        r8 = new edu.jas.poly.ExpVector[r11];
        r12 = new java.lang.Object[r11];
        r15 = new edu.jas.poly.GenPolynomial[r11];
        r10 = 0;
        r9 = 0;
    L_0x0051:
        if (r9 >= r11) goto L_0x0079;
    L_0x0053:
        r17 = r1[r9];
        r15[r9] = r17;
        r17 = r15[r9];
        r13 = r17.leadingMonomial();
        if (r13 == 0) goto L_0x0073;
    L_0x005f:
        r17 = r15[r9];
        r15[r10] = r17;
        r17 = r13.getKey();
        r17 = (edu.jas.poly.ExpVector) r17;
        r8[r10] = r17;
        r17 = r13.getValue();
        r12[r10] = r17;
        r10 = r10 + 1;
    L_0x0073:
        r9 = r9 + 1;
        goto L_0x0051;
    L_0x0076:
        r17 = move-exception;
        monitor-exit(r21);	 Catch:{ all -> 0x0076 }
        throw r17;
    L_0x0079:
        r11 = r10;
        r14 = 0;
        r0 = r22;
        r0 = r0.ring;
        r17 = r0;
        r16 = r17.getZERO();
        r0 = r22;
        r0 = r0.ring;
        r17 = r0;
        r17 = r17.getZERO();
        r2 = r17.copy();
        r7 = 0;
        r3 = r22.copy();
    L_0x0098:
        r17 = r3.length();
        if (r17 <= 0) goto L_0x00fc;
    L_0x009e:
        r13 = r3.leadingMonomial();
        r6 = r13.getKey();
        r6 = (edu.jas.poly.ExpVector) r6;
        r4 = r13.getValue();
        r4 = (edu.jas.structure.RingElem) r4;
        r9 = 0;
    L_0x00af:
        if (r9 >= r11) goto L_0x00bb;
    L_0x00b1:
        r17 = r8[r9];
        r0 = r17;
        r14 = r6.multipleOf(r0);
        if (r14 == 0) goto L_0x00c4;
    L_0x00bb:
        if (r14 != 0) goto L_0x00c7;
    L_0x00bd:
        r2.doPutToMap(r6, r4);
        r3.doRemoveFromMap(r6, r4);
        goto L_0x0098;
    L_0x00c4:
        r9 = r9 + 1;
        goto L_0x00af;
    L_0x00c7:
        r17 = r8[r9];
        r0 = r17;
        r6 = r6.subtract(r0);
        r5 = r12[r9];
        r5 = (edu.jas.structure.RingElem) r5;
        r4 = r4.divide(r5);
        r4 = (edu.jas.structure.RingElem) r4;
        r17 = r15[r9];
        r0 = r17;
        r3 = r3.subtractMultiple(r4, r6, r0);
        r0 = r20;
        r7 = r0.get(r9);
        r7 = (edu.jas.poly.GenPolynomial) r7;
        if (r7 != 0) goto L_0x00f7;
    L_0x00eb:
        r0 = r16;
        r7 = r0.sum(r4, r6);
    L_0x00f1:
        r0 = r20;
        r0.set(r9, r7);
        goto L_0x0098;
    L_0x00f7:
        r7 = r7.sum(r4, r6);
        goto L_0x00f1;
    L_0x00fc:
        r22 = r2;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.ReductionSeq.normalform(java.util.List, java.util.List, edu.jas.poly.GenPolynomial):edu.jas.poly.GenPolynomial<C>");
    }
}
