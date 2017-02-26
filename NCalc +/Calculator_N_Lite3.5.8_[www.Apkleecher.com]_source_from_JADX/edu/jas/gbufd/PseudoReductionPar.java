package edu.jas.gbufd;

import edu.jas.gb.ReductionAbstract;
import edu.jas.poly.GenPolynomial;
import edu.jas.structure.RingElem;
import java.util.List;
import org.apache.log4j.Logger;

public class PseudoReductionPar<C extends RingElem<C>> extends ReductionAbstract<C> implements PseudoReduction<C> {
    private static final Logger logger;
    private final boolean debug;

    static {
        logger = Logger.getLogger(PseudoReductionPar.class);
    }

    public PseudoReductionPar() {
        this.debug = logger.isDebugEnabled();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenPolynomial<C> normalform(java.util.List<edu.jas.poly.GenPolynomial<C>> r17, edu.jas.poly.GenPolynomial<C> r18) {
        /*
        r16 = this;
        if (r17 == 0) goto L_0x0008;
    L_0x0002:
        r15 = r17.isEmpty();
        if (r15 == 0) goto L_0x0009;
    L_0x0008:
        return r18;
    L_0x0009:
        if (r18 == 0) goto L_0x0008;
    L_0x000b:
        r15 = r18.isZERO();
        if (r15 != 0) goto L_0x0008;
    L_0x0011:
        r15 = 0;
        r1 = new edu.jas.poly.GenPolynomial[r15];
        monitor-enter(r17);
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0089 }
        r0 = r17;
        r2.<init>(r0);	 Catch:{ all -> 0x0089 }
        monitor-exit(r17);	 Catch:{ all -> 0x0089 }
        r1 = r2.toArray(r1);
        r1 = (edu.jas.poly.GenPolynomial[]) r1;
        r12 = r2.size();
        r0 = r18;
        r15 = r0.ring;
        r5 = r15.getZERO();
        r4 = r5.copy();
        r6 = r18.copy();
    L_0x0037:
        r15 = r6.length();
        if (r15 <= 0) goto L_0x00c1;
    L_0x003d:
        r15 = r17.size();
        if (r15 == r12) goto L_0x005f;
    L_0x0043:
        monitor-enter(r17);
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x008c }
        r0 = r17;
        r3.<init>(r0);	 Catch:{ all -> 0x008c }
        monitor-exit(r17);	 Catch:{ all -> 0x00c5 }
        r1 = r3.toArray(r1);
        r1 = (edu.jas.poly.GenPolynomial[]) r1;
        r12 = r3.size();
        r6 = r18.copy();
        r4 = r5.copy();
        r2 = r3;
    L_0x005f:
        r14 = 0;
        r13 = r6.leadingMonomial();
        r9 = r13.getKey();
        r9 = (edu.jas.poly.ExpVector) r9;
        r7 = r13.getValue();
        r7 = (edu.jas.structure.RingElem) r7;
        r10 = 0;
        r11 = 0;
    L_0x0072:
        if (r11 >= r12) goto L_0x0080;
    L_0x0074:
        r15 = r1[r11];
        r10 = r15.leadingExpVector();
        r14 = r9.multipleOf(r10);
        if (r14 == 0) goto L_0x008f;
    L_0x0080:
        if (r14 != 0) goto L_0x0092;
    L_0x0082:
        r4.doPutToMap(r9, r7);
        r6.doRemoveFromMap(r9, r7);
        goto L_0x0037;
    L_0x0089:
        r15 = move-exception;
        monitor-exit(r17);	 Catch:{ all -> 0x0089 }
        throw r15;
    L_0x008c:
        r15 = move-exception;
    L_0x008d:
        monitor-exit(r17);	 Catch:{ all -> 0x008c }
        throw r15;
    L_0x008f:
        r11 = r11 + 1;
        goto L_0x0072;
    L_0x0092:
        r9 = r9.subtract(r10);
        r15 = r1[r11];
        r8 = r15.leadingBaseCoefficient();
        r15 = r7.remainder(r8);
        r15 = (edu.jas.structure.RingElem) r15;
        r15 = r15.isZERO();
        if (r15 == 0) goto L_0x00b5;
    L_0x00a8:
        r7 = r7.divide(r8);
        r7 = (edu.jas.structure.RingElem) r7;
        r15 = r1[r11];
        r6 = r6.subtractMultiple(r7, r9, r15);
        goto L_0x0037;
    L_0x00b5:
        r4 = r4.multiply(r8);
        r15 = r1[r11];
        r6 = r6.scaleSubtractMultiple(r8, r7, r9, r15);
        goto L_0x0037;
    L_0x00c1:
        r18 = r4;
        goto L_0x0008;
    L_0x00c5:
        r15 = move-exception;
        r2 = r3;
        goto L_0x008d;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbufd.PseudoReductionPar.normalform(java.util.List, edu.jas.poly.GenPolynomial):edu.jas.poly.GenPolynomial<C>");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.gbufd.PseudoReductionEntry<C> normalformFactor(java.util.List<edu.jas.poly.GenPolynomial<C>> r22, edu.jas.poly.GenPolynomial<C> r23) {
        /*
        r21 = this;
        if (r23 != 0) goto L_0x0005;
    L_0x0002:
        r17 = 0;
    L_0x0004:
        return r17;
    L_0x0005:
        r0 = r23;
        r0 = r0.ring;
        r18 = r0;
        r15 = r18.getONECoefficient();
        r17 = new edu.jas.gbufd.PseudoReductionEntry;
        r0 = r17;
        r1 = r23;
        r0.<init>(r1, r15);
        if (r22 == 0) goto L_0x0004;
    L_0x001a:
        r18 = r22.isEmpty();
        if (r18 != 0) goto L_0x0004;
    L_0x0020:
        r18 = r23.isZERO();
        if (r18 != 0) goto L_0x0004;
    L_0x0026:
        r18 = 0;
        r0 = r18;
        r2 = new edu.jas.poly.GenPolynomial[r0];
        monitor-enter(r22);
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x00a6 }
        r0 = r22;
        r3.<init>(r0);	 Catch:{ all -> 0x00a6 }
        monitor-exit(r22);	 Catch:{ all -> 0x00a6 }
        r2 = r3.toArray(r2);
        r2 = (edu.jas.poly.GenPolynomial[]) r2;
        r13 = r3.size();
        r16 = 0;
        r0 = r23;
        r0 = r0.ring;
        r18 = r0;
        r6 = r18.getZERO();
        r5 = r6.copy();
        r7 = r23.copy();
    L_0x0053:
        r18 = r7.length();
        if (r18 <= 0) goto L_0x00e8;
    L_0x0059:
        r18 = r22.size();
        r0 = r18;
        if (r0 == r13) goto L_0x007d;
    L_0x0061:
        monitor-enter(r22);
        r4 = new java.util.ArrayList;	 Catch:{ all -> 0x00a9 }
        r0 = r22;
        r4.<init>(r0);	 Catch:{ all -> 0x00a9 }
        monitor-exit(r22);	 Catch:{ all -> 0x0113 }
        r2 = r4.toArray(r2);
        r2 = (edu.jas.poly.GenPolynomial[]) r2;
        r13 = r4.size();
        r7 = r23.copy();
        r5 = r6.copy();
        r3 = r4;
    L_0x007d:
        r14 = r7.leadingMonomial();
        r10 = r14.getKey();
        r10 = (edu.jas.poly.ExpVector) r10;
        r8 = r14.getValue();
        r8 = (edu.jas.structure.RingElem) r8;
        r11 = 0;
        r12 = 0;
    L_0x008f:
        if (r12 >= r13) goto L_0x009d;
    L_0x0091:
        r18 = r2[r12];
        r11 = r18.leadingExpVector();
        r16 = r10.multipleOf(r11);
        if (r16 == 0) goto L_0x00ac;
    L_0x009d:
        if (r16 != 0) goto L_0x00af;
    L_0x009f:
        r5.doPutToMap(r10, r8);
        r7.doRemoveFromMap(r10, r8);
        goto L_0x0053;
    L_0x00a6:
        r18 = move-exception;
        monitor-exit(r22);	 Catch:{ all -> 0x00a6 }
        throw r18;
    L_0x00a9:
        r18 = move-exception;
    L_0x00aa:
        monitor-exit(r22);	 Catch:{ all -> 0x00a9 }
        throw r18;
    L_0x00ac:
        r12 = r12 + 1;
        goto L_0x008f;
    L_0x00af:
        r10 = r10.subtract(r11);
        r18 = r2[r12];
        r9 = r18.leadingBaseCoefficient();
        r18 = r8.remainder(r9);
        r18 = (edu.jas.structure.RingElem) r18;
        r18 = r18.isZERO();
        if (r18 == 0) goto L_0x00d4;
    L_0x00c5:
        r8 = r8.divide(r9);
        r8 = (edu.jas.structure.RingElem) r8;
        r18 = r2[r12];
        r0 = r18;
        r7 = r7.subtractMultiple(r8, r10, r0);
        goto L_0x0053;
    L_0x00d4:
        r15 = r15.multiply(r9);
        r15 = (edu.jas.structure.RingElem) r15;
        r5 = r5.multiply(r9);
        r18 = r2[r12];
        r0 = r18;
        r7 = r7.scaleSubtractMultiple(r9, r8, r10, r0);
        goto L_0x0053;
    L_0x00e8:
        r18 = logger;
        r18 = r18.isInfoEnabled();
        if (r18 == 0) goto L_0x010a;
    L_0x00f0:
        r18 = logger;
        r19 = new java.lang.StringBuilder;
        r19.<init>();
        r20 = "multiplicative factor = ";
        r19 = r19.append(r20);
        r0 = r19;
        r19 = r0.append(r15);
        r19 = r19.toString();
        r18.info(r19);
    L_0x010a:
        r17 = new edu.jas.gbufd.PseudoReductionEntry;
        r0 = r17;
        r0.<init>(r5, r15);
        goto L_0x0004;
    L_0x0113:
        r18 = move-exception;
        r3 = r4;
        goto L_0x00aa;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbufd.PseudoReductionPar.normalformFactor(java.util.List, edu.jas.poly.GenPolynomial):edu.jas.gbufd.PseudoReductionEntry<C>");
    }

    public GenPolynomial<C> normalform(List<GenPolynomial<C>> list, List<GenPolynomial<C>> list2, GenPolynomial<C> genPolynomial) {
        throw new RuntimeException("normalform with recording not implemented");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>> normalformRecursive(java.util.List<edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>> r21, edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>> r22) {
        /*
        r20 = this;
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
        r17 = 0;
        r0 = r17;
        r1 = new edu.jas.poly.GenPolynomial[r0];
        monitor-enter(r21);
        r2 = new java.util.ArrayList;	 Catch:{ all -> 0x0091 }
        r0 = r21;
        r2.<init>(r0);	 Catch:{ all -> 0x0091 }
        monitor-exit(r21);	 Catch:{ all -> 0x0091 }
        r1 = r2.toArray(r1);
        r1 = (edu.jas.poly.GenPolynomial[]) r1;
        r14 = r2.size();
        r0 = r22;
        r0 = r0.ring;
        r17 = r0;
        r5 = r17.getZERO();
        r4 = r5.copy();
        r6 = r22.copy();
    L_0x003c:
        r17 = r6.length();
        if (r17 <= 0) goto L_0x014f;
    L_0x0042:
        r17 = r21.size();
        r0 = r17;
        if (r0 == r14) goto L_0x0066;
    L_0x004a:
        monitor-enter(r21);
        r3 = new java.util.ArrayList;	 Catch:{ all -> 0x0094 }
        r0 = r21;
        r3.<init>(r0);	 Catch:{ all -> 0x0094 }
        monitor-exit(r21);	 Catch:{ all -> 0x0153 }
        r1 = r3.toArray(r1);
        r1 = (edu.jas.poly.GenPolynomial[]) r1;
        r14 = r3.size();
        r6 = r22.copy();
        r4 = r5.copy();
        r2 = r3;
    L_0x0066:
        r16 = 0;
        r15 = r6.leadingMonomial();
        r11 = r15.getKey();
        r11 = (edu.jas.poly.ExpVector) r11;
        r8 = r15.getValue();
        r8 = (edu.jas.poly.GenPolynomial) r8;
        r12 = 0;
        r13 = 0;
    L_0x007a:
        if (r13 >= r14) goto L_0x0088;
    L_0x007c:
        r17 = r1[r13];
        r12 = r17.leadingExpVector();
        r16 = r11.multipleOf(r12);
        if (r16 == 0) goto L_0x0097;
    L_0x0088:
        if (r16 != 0) goto L_0x009a;
    L_0x008a:
        r4.doPutToMap(r11, r8);
        r6.doRemoveFromMap(r11, r8);
        goto L_0x003c;
    L_0x0091:
        r17 = move-exception;
        monitor-exit(r21);	 Catch:{ all -> 0x0091 }
        throw r17;
    L_0x0094:
        r17 = move-exception;
    L_0x0095:
        monitor-exit(r21);	 Catch:{ all -> 0x0094 }
        throw r17;
    L_0x0097:
        r13 = r13 + 1;
        goto L_0x007a;
    L_0x009a:
        r12 = r11.subtract(r12);
        r0 = r20;
        r0 = r0.debug;
        r17 = r0;
        if (r17 == 0) goto L_0x00c0;
    L_0x00a6:
        r17 = logger;
        r18 = new java.lang.StringBuilder;
        r18.<init>();
        r19 = "red div = ";
        r18 = r18.append(r19);
        r0 = r18;
        r18 = r0.append(r11);
        r18 = r18.toString();
        r17.info(r18);
    L_0x00c0:
        r17 = r1[r13];
        r10 = r17.leadingBaseCoefficient();
        r10 = (edu.jas.poly.GenPolynomial) r10;
        r17 = edu.jas.poly.PolyUtil.baseSparsePseudoRemainder(r8, r10);
        r17 = r17.isZERO();
        if (r17 == 0) goto L_0x0141;
    L_0x00d2:
        r0 = r20;
        r0 = r0.debug;
        r17 = r0;
        if (r17 == 0) goto L_0x00f4;
    L_0x00da:
        r17 = logger;
        r18 = new java.lang.StringBuilder;
        r18.<init>();
        r19 = "red c = ";
        r18 = r18.append(r19);
        r0 = r18;
        r18 = r0.append(r10);
        r18 = r18.toString();
        r17.info(r18);
    L_0x00f4:
        r9 = edu.jas.poly.PolyUtil.basePseudoDivide(r8, r10);
        r17 = r1[r13];
        r0 = r17;
        r7 = r6.subtractMultiple(r9, r12, r0);
        r17 = r7.leadingExpVector();
        r0 = r17;
        r17 = r11.equals(r0);
        if (r17 == 0) goto L_0x013e;
    L_0x010c:
        r17 = logger;
        r18 = new java.lang.StringBuilder;
        r18.<init>();
        r19 = "degree not descending: S = ";
        r18 = r18.append(r19);
        r0 = r18;
        r18 = r0.append(r6);
        r19 = ", Sp = ";
        r18 = r18.append(r19);
        r0 = r18;
        r18 = r0.append(r7);
        r18 = r18.toString();
        r17.info(r18);
        r4 = r4.multiply(r10);
        r17 = r1[r13];
        r0 = r17;
        r7 = r6.scaleSubtractMultiple(r10, r8, r12, r0);
    L_0x013e:
        r6 = r7;
        goto L_0x003c;
    L_0x0141:
        r4 = r4.multiply(r10);
        r17 = r1[r13];
        r0 = r17;
        r6 = r6.scaleSubtractMultiple(r10, r8, r12, r0);
        goto L_0x003c;
    L_0x014f:
        r22 = r4;
        goto L_0x0008;
    L_0x0153:
        r17 = move-exception;
        r2 = r3;
        goto L_0x0095;
        */
        throw new UnsupportedOperationException("Method not decompiled: edu.jas.gbufd.PseudoReductionPar.normalformRecursive(java.util.List, edu.jas.poly.GenPolynomial):edu.jas.poly.GenPolynomial<edu.jas.poly.GenPolynomial<C>>");
    }
}
