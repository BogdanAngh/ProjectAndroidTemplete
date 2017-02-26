package edu.jas.gb;

import edu.jas.poly.GenWordPolynomial;
import edu.jas.poly.Word;
import edu.jas.structure.RingElem;
import java.util.List;
import java.util.Map.Entry;
import org.apache.log4j.Logger;

public class WordReductionSeq<C extends RingElem<C>> extends WordReductionAbstract<C> {
    private static boolean debug;
    private static final Logger logger;

    static {
        logger = Logger.getLogger(WordReductionSeq.class);
        debug = logger.isDebugEnabled();
    }

    public GenWordPolynomial<C> normalform(List<GenWordPolynomial<C>> Pp, GenWordPolynomial<C> Ap) {
        if (Pp == null || Pp.isEmpty() || Ap == null || Ap.isZERO()) {
            return Ap;
        }
        if (Ap.ring.coFac.isField()) {
            int l;
            GenWordPolynomial<C>[] P;
            int i;
            Entry<Word, C> m;
            synchronized (Pp) {
                l = Pp.size();
                P = new GenWordPolynomial[l];
                for (i = 0; i < Pp.size(); i++) {
                    P[i] = (GenWordPolynomial) Pp.get(i);
                }
            }
            Word[] htl = new Word[l];
            RingElem[] lbc = (RingElem[]) new RingElem[l];
            GenWordPolynomial<C>[] p = new GenWordPolynomial[l];
            int j = 0;
            for (i = 0; i < l; i++) {
                p[i] = P[i];
                m = p[i].leadingMonomial();
                if (m != null) {
                    p[j] = p[i];
                    htl[j] = (Word) m.getKey();
                    lbc[j] = (RingElem) m.getValue();
                    j++;
                }
            }
            l = j;
            boolean mt = false;
            GenWordPolynomial<C> R = Ap.ring.getZERO();
            RingElem cone = (RingElem) Ap.ring.coFac.getONE();
            GenWordPolynomial<C> S = Ap;
            while (S.length() > 0) {
                m = S.leadingMonomial();
                Word e = (Word) m.getKey();
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
                    Word[] elr = e.divideWord(htl[i]);
                    e = elr[0];
                    Word f = elr[1];
                    if (debug) {
                        logger.info("red divideWord: e = " + e + ", f = " + f);
                    }
                    a = (RingElem) a.divide(lbc[i]);
                    S = S.subtract(p[i].multiply(a, e, cone, f));
                } else {
                    R = R.sum(a, e);
                    S = S.subtract(a, e);
                }
            }
            return R;
        }
        throw new IllegalArgumentException("coefficients not from a field");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenWordPolynomial<C> normalform(java.util.List<edu.jas.poly.GenWordPolynomial<C>> r25, java.util.List<edu.jas.poly.GenWordPolynomial<C>> r26, java.util.List<edu.jas.poly.GenWordPolynomial<C>> r27, edu.jas.poly.GenWordPolynomial<C> r28) {
        /*
        r24 = this;
        if (r27 == 0) goto L_0x0008;
    L_0x0002:
        r21 = r27.isEmpty();
        if (r21 == 0) goto L_0x0009;
    L_0x0008:
        return r28;
    L_0x0009:
        if (r28 == 0) goto L_0x0008;
    L_0x000b:
        r21 = r28.isZERO();
        if (r21 != 0) goto L_0x0008;
    L_0x0011:
        r0 = r28;
        r0 = r0.ring;
        r21 = r0;
        r0 = r21;
        r0 = r0.coFac;
        r21 = r0;
        r21 = r21.isField();
        if (r21 != 0) goto L_0x002b;
    L_0x0023:
        r21 = new java.lang.IllegalArgumentException;
        r22 = "coefficients not from a field";
        r21.<init>(r22);
        throw r21;
    L_0x002b:
        r15 = r27.size();
        r1 = new edu.jas.poly.GenWordPolynomial[r15];
        monitor-enter(r27);
        r13 = 0;
    L_0x0033:
        r21 = r27.size();	 Catch:{ all -> 0x007e }
        r0 = r21;
        if (r13 >= r0) goto L_0x0048;
    L_0x003b:
        r0 = r27;
        r21 = r0.get(r13);	 Catch:{ all -> 0x007e }
        r21 = (edu.jas.poly.GenWordPolynomial) r21;	 Catch:{ all -> 0x007e }
        r1[r13] = r21;	 Catch:{ all -> 0x007e }
        r13 = r13 + 1;
        goto L_0x0033;
    L_0x0048:
        monitor-exit(r27);	 Catch:{ all -> 0x007e }
        r12 = new edu.jas.poly.Word[r15];
        r0 = new edu.jas.structure.RingElem[r15];
        r16 = r0;
        r16 = (edu.jas.structure.RingElem[]) r16;
        r0 = new edu.jas.poly.GenWordPolynomial[r15];
        r19 = r0;
        r14 = 0;
        r13 = 0;
    L_0x0057:
        if (r13 >= r15) goto L_0x0081;
    L_0x0059:
        r21 = r1[r13];
        r19[r13] = r21;
        r21 = r19[r13];
        r17 = r21.leadingMonomial();
        if (r17 == 0) goto L_0x007b;
    L_0x0065:
        r21 = r19[r13];
        r19[r14] = r21;
        r21 = r17.getKey();
        r21 = (edu.jas.poly.Word) r21;
        r12[r14] = r21;
        r21 = r17.getValue();
        r21 = (edu.jas.structure.RingElem) r21;
        r16[r14] = r21;
        r14 = r14 + 1;
    L_0x007b:
        r13 = r13 + 1;
        goto L_0x0057;
    L_0x007e:
        r21 = move-exception;
        monitor-exit(r27);	 Catch:{ all -> 0x007e }
        throw r21;
    L_0x0081:
        r15 = r14;
        r18 = 0;
        r0 = r28;
        r0 = r0.ring;
        r21 = r0;
        r20 = r21.getZERO();
        r0 = r28;
        r0 = r0.ring;
        r21 = r0;
        r3 = r21.getZERO();
        r0 = r28;
        r0 = r0.ring;
        r21 = r0;
        r0 = r21;
        r0 = r0.coFac;
        r21 = r0;
        r7 = r21.getONE();
        r7 = (edu.jas.structure.RingElem) r7;
        r11 = 0;
        r2 = 0;
        r4 = r28;
    L_0x00ae:
        r21 = r4.length();
        if (r21 <= 0) goto L_0x0163;
    L_0x00b4:
        r17 = r4.leadingMonomial();
        r8 = r17.getKey();
        r8 = (edu.jas.poly.Word) r8;
        r5 = r17.getValue();
        r5 = (edu.jas.structure.RingElem) r5;
        r13 = 0;
    L_0x00c5:
        if (r13 >= r15) goto L_0x00d1;
    L_0x00c7:
        r21 = r12[r13];
        r0 = r21;
        r18 = r8.multipleOf(r0);
        if (r18 == 0) goto L_0x00dc;
    L_0x00d1:
        if (r18 != 0) goto L_0x00df;
    L_0x00d3:
        r3 = r3.sum(r5, r8);
        r4 = r4.subtract(r5, r8);
        goto L_0x00ae;
    L_0x00dc:
        r13 = r13 + 1;
        goto L_0x00c5;
    L_0x00df:
        r21 = r12[r13];
        r0 = r21;
        r9 = r8.divideWord(r0);
        r21 = 0;
        r8 = r9[r21];
        r21 = 1;
        r10 = r9[r21];
        r21 = debug;
        if (r21 == 0) goto L_0x0119;
    L_0x00f3:
        r21 = logger;
        r22 = new java.lang.StringBuilder;
        r22.<init>();
        r23 = "redRec divideWord: e = ";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r8);
        r23 = ", f = ";
        r22 = r22.append(r23);
        r0 = r22;
        r22 = r0.append(r10);
        r22 = r22.toString();
        r21.info(r22);
    L_0x0119:
        r6 = r16[r13];
        r5 = r5.divide(r6);
        r5 = (edu.jas.structure.RingElem) r5;
        r21 = r19[r13];
        r0 = r21;
        r2 = r0.multiply(r5, r8, r7, r10);
        r4 = r4.subtract(r2);
        r0 = r25;
        r11 = r0.get(r13);
        r11 = (edu.jas.poly.GenWordPolynomial) r11;
        if (r11 != 0) goto L_0x0159;
    L_0x0137:
        r0 = r20;
        r11 = r0.sum(r7, r8);
    L_0x013d:
        r0 = r25;
        r0.set(r13, r11);
        r0 = r26;
        r11 = r0.get(r13);
        r11 = (edu.jas.poly.GenWordPolynomial) r11;
        if (r11 != 0) goto L_0x015e;
    L_0x014c:
        r0 = r20;
        r11 = r0.sum(r5, r10);
    L_0x0152:
        r0 = r26;
        r0.set(r13, r11);
        goto L_0x00ae;
    L_0x0159:
        r11 = r11.sum(r7, r8);
        goto L_0x013d;
    L_0x015e:
        r11 = r11.sum(r5, r10);
        goto L_0x0152;
    L_0x0163:
        r28 = r3;
        goto L_0x0008;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gb.WordReductionSeq.normalform(java.util.List, java.util.List, java.util.List, edu.jas.poly.GenWordPolynomial):edu.jas.poly.GenWordPolynomial<C>");
    }
}
