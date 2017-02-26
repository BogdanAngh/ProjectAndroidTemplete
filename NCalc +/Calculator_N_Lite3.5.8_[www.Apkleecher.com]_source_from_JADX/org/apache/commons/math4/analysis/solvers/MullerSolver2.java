package org.apache.commons.math4.analysis.solvers;

public class MullerSolver2 extends AbstractUnivariateSolver {
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;

    public MullerSolver2() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public MullerSolver2(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    public MullerSolver2(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected double doSolve() throws org.apache.commons.math4.exception.TooManyEvaluationsException, org.apache.commons.math4.exception.NumberIsTooLargeException, org.apache.commons.math4.exception.NoBracketingException {
        /*
        r58 = this;
        r36 = r58.getMin();
        r34 = r58.getMax();
        r0 = r58;
        r1 = r36;
        r3 = r34;
        r0.verifyInterval(r1, r3);
        r42 = r58.getRelativeAccuracy();
        r18 = r58.getAbsoluteAccuracy();
        r32 = r58.getFunctionValueAccuracy();
        r8 = r36;
        r0 = r58;
        r12 = r0.computeObjectiveValue(r8);
        r6 = org.apache.commons.math4.util.FastMath.abs(r12);
        r6 = (r6 > r32 ? 1 : (r6 == r32 ? 0 : -1));
        if (r6 >= 0) goto L_0x002f;
    L_0x002d:
        r10 = r8;
    L_0x002e:
        return r10;
    L_0x002f:
        r10 = r34;
        r0 = r58;
        r14 = r0.computeObjectiveValue(r10);
        r6 = org.apache.commons.math4.util.FastMath.abs(r14);
        r6 = (r6 > r32 ? 1 : (r6 == r32 ? 0 : -1));
        if (r6 < 0) goto L_0x002e;
    L_0x003f:
        r6 = r12 * r14;
        r54 = 0;
        r6 = (r6 > r54 ? 1 : (r6 == r54 ? 0 : -1));
        if (r6 <= 0) goto L_0x004d;
    L_0x0047:
        r7 = new org.apache.commons.math4.exception.NoBracketingException;
        r7.<init>(r8, r10, r12, r14);
        throw r7;
    L_0x004d:
        r6 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r54 = r8 + r10;
        r48 = r6 * r54;
        r0 = r58;
        r1 = r48;
        r52 = r0.computeObjectiveValue(r1);
        r38 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
    L_0x005d:
        r6 = r48 - r10;
        r54 = r10 - r8;
        r40 = r6 / r54;
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r6 = r6 + r40;
        r6 = r6 * r14;
        r6 = r52 - r6;
        r54 = r40 * r12;
        r6 = r6 + r54;
        r16 = r40 * r6;
        r6 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r6 = r6 * r40;
        r54 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r6 = r6 + r54;
        r6 = r6 * r52;
        r54 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r54 = r54 + r40;
        r56 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r56 = r56 + r40;
        r54 = r54 * r56;
        r54 = r54 * r14;
        r6 = r6 - r54;
        r54 = r40 * r40;
        r54 = r54 * r12;
        r20 = r6 + r54;
        r6 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r6 = r6 + r40;
        r22 = r6 * r52;
        r6 = r20 * r20;
        r54 = 4616189618054758400; // 0x4010000000000000 float:0.0 double:4.0;
        r54 = r54 * r16;
        r54 = r54 * r22;
        r24 = r6 - r54;
        r6 = 0;
        r6 = (r24 > r6 ? 1 : (r24 == r6 ? 0 : -1));
        if (r6 < 0) goto L_0x0105;
    L_0x00a4:
        r6 = org.apache.commons.math4.util.FastMath.sqrt(r24);
        r30 = r20 + r6;
        r6 = org.apache.commons.math4.util.FastMath.sqrt(r24);
        r28 = r20 - r6;
        r6 = org.apache.commons.math4.util.FastMath.abs(r30);
        r54 = org.apache.commons.math4.util.FastMath.abs(r28);
        r6 = (r6 > r54 ? 1 : (r6 == r54 ? 0 : -1));
        if (r6 <= 0) goto L_0x0102;
    L_0x00bc:
        r26 = r30;
    L_0x00be:
        r6 = 0;
        r6 = (r26 > r6 ? 1 : (r26 == r6 ? 0 : -1));
        if (r6 == 0) goto L_0x0111;
    L_0x00c4:
        r6 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r6 = r6 * r22;
        r54 = r48 - r10;
        r6 = r6 * r54;
        r6 = r6 / r26;
        r46 = r48 - r6;
    L_0x00d0:
        r6 = (r46 > r10 ? 1 : (r46 == r10 ? 0 : -1));
        if (r6 == 0) goto L_0x010e;
    L_0x00d4:
        r6 = (r46 > r48 ? 1 : (r46 == r48 ? 0 : -1));
        if (r6 == 0) goto L_0x010e;
    L_0x00d8:
        r0 = r58;
        r1 = r46;
        r50 = r0.computeObjectiveValue(r1);
        r6 = org.apache.commons.math4.util.FastMath.abs(r46);
        r6 = r6 * r42;
        r0 = r18;
        r44 = org.apache.commons.math4.util.FastMath.max(r6, r0);
        r6 = r46 - r38;
        r6 = org.apache.commons.math4.util.FastMath.abs(r6);
        r6 = (r6 > r44 ? 1 : (r6 == r44 ? 0 : -1));
        if (r6 <= 0) goto L_0x00fe;
    L_0x00f6:
        r6 = org.apache.commons.math4.util.FastMath.abs(r50);
        r6 = (r6 > r32 ? 1 : (r6 == r32 ? 0 : -1));
        if (r6 > 0) goto L_0x011e;
    L_0x00fe:
        r10 = r46;
        goto L_0x002e;
    L_0x0102:
        r26 = r28;
        goto L_0x00be;
    L_0x0105:
        r6 = r20 * r20;
        r6 = r6 - r24;
        r26 = org.apache.commons.math4.util.FastMath.sqrt(r6);
        goto L_0x00be;
    L_0x010e:
        r46 = r46 + r18;
        goto L_0x00d0;
    L_0x0111:
        r6 = org.apache.commons.math4.util.FastMath.random();
        r54 = r34 - r36;
        r6 = r6 * r54;
        r46 = r36 + r6;
        r38 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        goto L_0x00d8;
    L_0x011e:
        r8 = r10;
        r12 = r14;
        r10 = r48;
        r14 = r52;
        r48 = r46;
        r52 = r50;
        r38 = r46;
        goto L_0x005d;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.solvers.MullerSolver2.doSolve():double");
    }
}
