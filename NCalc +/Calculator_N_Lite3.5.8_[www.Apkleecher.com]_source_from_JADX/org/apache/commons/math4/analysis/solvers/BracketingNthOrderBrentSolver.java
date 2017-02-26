package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;

public class BracketingNthOrderBrentSolver extends AbstractUnivariateSolver implements BracketedUnivariateSolver<UnivariateFunction> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution = null;
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;
    private static final int DEFAULT_MAXIMAL_ORDER = 5;
    private static final int MAXIMAL_AGING = 2;
    private static final double REDUCTION_FACTOR = 0.0625d;
    private AllowedSolution allowed;
    private final int maximalOrder;

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution;
        if (iArr == null) {
            iArr = new int[AllowedSolution.values().length];
            try {
                iArr[AllowedSolution.ABOVE_SIDE.ordinal()] = DEFAULT_MAXIMAL_ORDER;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AllowedSolution.ANY_SIDE.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[AllowedSolution.BELOW_SIDE.ordinal()] = 4;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[AllowedSolution.LEFT_SIDE.ordinal()] = MAXIMAL_AGING;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[AllowedSolution.RIGHT_SIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution = iArr;
        }
        return iArr;
    }

    public BracketingNthOrderBrentSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY, DEFAULT_MAXIMAL_ORDER);
    }

    public BracketingNthOrderBrentSolver(double absoluteAccuracy, int maximalOrder) throws NumberIsTooSmallException {
        super(absoluteAccuracy);
        if (maximalOrder < MAXIMAL_AGING) {
            throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(MAXIMAL_AGING), true);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    public BracketingNthOrderBrentSolver(double relativeAccuracy, double absoluteAccuracy, int maximalOrder) throws NumberIsTooSmallException {
        super(relativeAccuracy, absoluteAccuracy);
        if (maximalOrder < MAXIMAL_AGING) {
            throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(MAXIMAL_AGING), true);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    public BracketingNthOrderBrentSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
        if (maximalOrder < MAXIMAL_AGING) {
            throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(MAXIMAL_AGING), true);
        }
        this.maximalOrder = maximalOrder;
        this.allowed = AllowedSolution.ANY_SIDE;
    }

    public int getMaximalOrder() {
        return this.maximalOrder;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected double doSolve() throws org.apache.commons.math4.exception.TooManyEvaluationsException, org.apache.commons.math4.exception.NumberIsTooLargeException, org.apache.commons.math4.exception.NoBracketingException {
        /*
        r48 = this;
        r0 = r48;
        r5 = r0.maximalOrder;
        r5 = r5 + 1;
        r0 = new double[r5];
        r30 = r0;
        r0 = r48;
        r5 = r0.maximalOrder;
        r5 = r5 + 1;
        r0 = new double[r5];
        r31 = r0;
        r5 = 0;
        r12 = r48.getMin();
        r30[r5] = r12;
        r5 = 1;
        r12 = r48.getStartValue();
        r30[r5] = r12;
        r5 = 2;
        r12 = r48.getMax();
        r30[r5] = r12;
        r5 = 0;
        r6 = r30[r5];
        r5 = 1;
        r8 = r30[r5];
        r5 = 2;
        r10 = r30[r5];
        r5 = r48;
        r5.verifySequence(r6, r8, r10);
        r5 = 1;
        r9 = 1;
        r12 = r30[r9];
        r0 = r48;
        r12 = r0.computeObjectiveValue(r12);
        r31[r5] = r12;
        r5 = 1;
        r12 = r31[r5];
        r42 = 0;
        r5 = 1;
        r0 = r42;
        r5 = org.apache.commons.math4.util.Precision.equals(r12, r0, r5);
        if (r5 == 0) goto L_0x0055;
    L_0x0051:
        r5 = 1;
        r32 = r30[r5];
    L_0x0054:
        return r32;
    L_0x0055:
        r5 = 0;
        r9 = 0;
        r12 = r30[r9];
        r0 = r48;
        r12 = r0.computeObjectiveValue(r12);
        r31[r5] = r12;
        r5 = 0;
        r12 = r31[r5];
        r42 = 0;
        r5 = 1;
        r0 = r42;
        r5 = org.apache.commons.math4.util.Precision.equals(r12, r0, r5);
        if (r5 == 0) goto L_0x0073;
    L_0x006f:
        r5 = 0;
        r32 = r30[r5];
        goto L_0x0054;
    L_0x0073:
        r5 = 0;
        r12 = r31[r5];
        r5 = 1;
        r42 = r31[r5];
        r12 = r12 * r42;
        r42 = 0;
        r5 = (r12 > r42 ? 1 : (r12 == r42 ? 0 : -1));
        if (r5 >= 0) goto L_0x00e2;
    L_0x0081:
        r19 = 2;
        r25 = 1;
    L_0x0085:
        r0 = r30;
        r5 = r0.length;
        r8 = new double[r5];
        r5 = r25 + -1;
        r32 = r30[r5];
        r5 = r25 + -1;
        r38 = r31[r5];
        r14 = org.apache.commons.math4.util.FastMath.abs(r38);
        r4 = 0;
        r34 = r30[r25];
        r40 = r31[r25];
        r16 = org.apache.commons.math4.util.FastMath.abs(r40);
        r18 = 0;
    L_0x00a1:
        r12 = r48.getAbsoluteAccuracy();
        r42 = r48.getRelativeAccuracy();
        r44 = org.apache.commons.math4.util.FastMath.abs(r32);
        r46 = org.apache.commons.math4.util.FastMath.abs(r34);
        r44 = org.apache.commons.math4.util.FastMath.max(r44, r46);
        r42 = r42 * r44;
        r36 = r12 + r42;
        r12 = r34 - r32;
        r5 = (r12 > r36 ? 1 : (r12 == r36 ? 0 : -1));
        if (r5 <= 0) goto L_0x00cb;
    L_0x00bf:
        r12 = org.apache.commons.math4.util.FastMath.max(r14, r16);
        r42 = r48.getFunctionValueAccuracy();
        r5 = (r12 > r42 ? 1 : (r12 == r42 ? 0 : -1));
        if (r5 >= 0) goto L_0x014a;
    L_0x00cb:
        r5 = $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution();
        r0 = r48;
        r9 = r0.allowed;
        r9 = r9.ordinal();
        r5 = r5[r9];
        switch(r5) {
            case 1: goto L_0x0127;
            case 2: goto L_0x0054;
            case 3: goto L_0x012f;
            case 4: goto L_0x0133;
            case 5: goto L_0x013d;
            default: goto L_0x00dc;
        };
    L_0x00dc:
        r5 = new org.apache.commons.math4.exception.MathInternalError;
        r5.<init>();
        throw r5;
    L_0x00e2:
        r5 = 2;
        r9 = 2;
        r12 = r30[r9];
        r0 = r48;
        r12 = r0.computeObjectiveValue(r12);
        r31[r5] = r12;
        r5 = 2;
        r12 = r31[r5];
        r42 = 0;
        r5 = 1;
        r0 = r42;
        r5 = org.apache.commons.math4.util.Precision.equals(r12, r0, r5);
        if (r5 == 0) goto L_0x0101;
    L_0x00fc:
        r5 = 2;
        r32 = r30[r5];
        goto L_0x0054;
    L_0x0101:
        r5 = 1;
        r12 = r31[r5];
        r5 = 2;
        r42 = r31[r5];
        r12 = r12 * r42;
        r42 = 0;
        r5 = (r12 > r42 ? 1 : (r12 == r42 ? 0 : -1));
        if (r5 >= 0) goto L_0x0115;
    L_0x010f:
        r19 = 3;
        r25 = 2;
        goto L_0x0085;
    L_0x0115:
        r5 = new org.apache.commons.math4.exception.NoBracketingException;
        r9 = 0;
        r6 = r30[r9];
        r9 = 2;
        r8 = r30[r9];
        r12 = 0;
        r10 = r31[r12];
        r12 = 2;
        r12 = r31[r12];
        r5.<init>(r6, r8, r10, r12);
        throw r5;
    L_0x0127:
        r5 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1));
        if (r5 < 0) goto L_0x0054;
    L_0x012b:
        r32 = r34;
        goto L_0x0054;
    L_0x012f:
        r32 = r34;
        goto L_0x0054;
    L_0x0133:
        r12 = 0;
        r5 = (r38 > r12 ? 1 : (r38 == r12 ? 0 : -1));
        if (r5 <= 0) goto L_0x0054;
    L_0x0139:
        r32 = r34;
        goto L_0x0054;
    L_0x013d:
        r12 = 0;
        r5 = (r38 > r12 ? 1 : (r38 == r12 ? 0 : -1));
        if (r5 >= 0) goto L_0x0147;
    L_0x0143:
        r32 = r34;
        goto L_0x0054;
    L_0x0147:
        r34 = r32;
        goto L_0x0143;
    L_0x014a:
        r5 = 2;
        if (r4 < r5) goto L_0x01c2;
    L_0x014d:
        r24 = r4 + -2;
        r5 = 1;
        r5 = r5 << r24;
        r5 = r5 + -1;
        r0 = (double) r5;
        r26 = r0;
        r5 = r24 + 1;
        r0 = (double) r5;
        r28 = r0;
        r12 = r26 * r38;
        r42 = 4589168020290535424; // 0x3fb0000000000000 float:0.0 double:0.0625;
        r42 = r42 * r28;
        r42 = r42 * r40;
        r12 = r12 - r42;
        r42 = r26 + r28;
        r6 = r12 / r42;
    L_0x016a:
        r10 = 0;
        r11 = r19;
    L_0x016d:
        r5 = r11 - r10;
        r0 = r30;
        java.lang.System.arraycopy(r0, r10, r8, r10, r5);
        r5 = r48;
        r9 = r31;
        r20 = r5.guessX(r6, r8, r9, r10, r11);
        r5 = (r20 > r32 ? 1 : (r20 == r32 ? 0 : -1));
        if (r5 <= 0) goto L_0x0184;
    L_0x0180:
        r5 = (r20 > r34 ? 1 : (r20 == r34 ? 0 : -1));
        if (r5 < 0) goto L_0x018e;
    L_0x0184:
        r5 = r25 - r10;
        r9 = r11 - r25;
        if (r5 < r9) goto L_0x01e8;
    L_0x018a:
        r10 = r10 + 1;
    L_0x018c:
        r20 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
    L_0x018e:
        r5 = java.lang.Double.isNaN(r20);
        if (r5 == 0) goto L_0x0199;
    L_0x0194:
        r5 = r11 - r10;
        r9 = 1;
        if (r5 > r9) goto L_0x016d;
    L_0x0199:
        r5 = java.lang.Double.isNaN(r20);
        if (r5 == 0) goto L_0x01ab;
    L_0x019f:
        r12 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r42 = r34 - r32;
        r12 = r12 * r42;
        r20 = r32 + r12;
        r10 = r25 + -1;
        r11 = r25;
    L_0x01ab:
        r0 = r48;
        r1 = r20;
        r22 = r0.computeObjectiveValue(r1);
        r12 = 0;
        r5 = 1;
        r0 = r22;
        r5 = org.apache.commons.math4.util.Precision.equals(r0, r12, r5);
        if (r5 == 0) goto L_0x01eb;
    L_0x01be:
        r32 = r20;
        goto L_0x0054;
    L_0x01c2:
        r5 = 2;
        r0 = r18;
        if (r0 < r5) goto L_0x01e5;
    L_0x01c7:
        r24 = r18 + -2;
        r5 = r24 + 1;
        r0 = (double) r5;
        r26 = r0;
        r5 = 1;
        r5 = r5 << r24;
        r5 = r5 + -1;
        r0 = (double) r5;
        r28 = r0;
        r12 = r28 * r40;
        r42 = 4589168020290535424; // 0x3fb0000000000000 float:0.0 double:0.0625;
        r42 = r42 * r26;
        r42 = r42 * r38;
        r12 = r12 - r42;
        r42 = r26 + r28;
        r6 = r12 / r42;
        goto L_0x016a;
    L_0x01e5:
        r6 = 0;
        goto L_0x016a;
    L_0x01e8:
        r11 = r11 + -1;
        goto L_0x018c;
    L_0x01eb:
        r5 = 2;
        r0 = r19;
        if (r0 <= r5) goto L_0x0244;
    L_0x01f0:
        r5 = r11 - r10;
        r0 = r19;
        if (r5 == r0) goto L_0x0244;
    L_0x01f6:
        r19 = r11 - r10;
        r5 = 0;
        r0 = r30;
        r1 = r30;
        r2 = r19;
        java.lang.System.arraycopy(r0, r10, r1, r5, r2);
        r5 = 0;
        r0 = r31;
        r1 = r31;
        r2 = r19;
        java.lang.System.arraycopy(r0, r10, r1, r5, r2);
        r25 = r25 - r10;
    L_0x020e:
        r5 = r25 + 1;
        r9 = r19 - r25;
        r0 = r30;
        r1 = r25;
        r2 = r30;
        java.lang.System.arraycopy(r0, r1, r2, r5, r9);
        r30[r25] = r20;
        r5 = r25 + 1;
        r9 = r19 - r25;
        r0 = r31;
        r1 = r25;
        r2 = r31;
        java.lang.System.arraycopy(r0, r1, r2, r5, r9);
        r31[r25] = r22;
        r19 = r19 + 1;
        r12 = r22 * r38;
        r42 = 0;
        r5 = (r12 > r42 ? 1 : (r12 == r42 ? 0 : -1));
        if (r5 > 0) goto L_0x0271;
    L_0x0236:
        r34 = r20;
        r40 = r22;
        r16 = org.apache.commons.math4.util.FastMath.abs(r40);
        r4 = r4 + 1;
        r18 = 0;
        goto L_0x00a1;
    L_0x0244:
        r0 = r30;
        r5 = r0.length;
        r0 = r19;
        if (r0 != r5) goto L_0x020e;
    L_0x024b:
        r19 = r19 + -1;
        r0 = r30;
        r5 = r0.length;
        r5 = r5 + 1;
        r5 = r5 / 2;
        r0 = r25;
        if (r0 < r5) goto L_0x020e;
    L_0x0258:
        r5 = 1;
        r9 = 0;
        r0 = r30;
        r1 = r30;
        r2 = r19;
        java.lang.System.arraycopy(r0, r5, r1, r9, r2);
        r5 = 1;
        r9 = 0;
        r0 = r31;
        r1 = r31;
        r2 = r19;
        java.lang.System.arraycopy(r0, r5, r1, r9, r2);
        r25 = r25 + -1;
        goto L_0x020e;
    L_0x0271:
        r32 = r20;
        r38 = r22;
        r14 = org.apache.commons.math4.util.FastMath.abs(r38);
        r4 = 0;
        r18 = r18 + 1;
        r25 = r25 + 1;
        goto L_0x00a1;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.solvers.BracketingNthOrderBrentSolver.doSolve():double");
    }

    private double guessX(double targetY, double[] x, double[] y, int start, int end) {
        for (int i = start; i < end - 1; i++) {
            int j;
            int delta = (i + 1) - start;
            for (j = end - 1; j > i; j--) {
                x[j] = (x[j] - x[j - 1]) / (y[j] - y[j - delta]);
            }
        }
        double x0 = 0.0d;
        for (j = end - 1; j >= start; j--) {
            x0 = x[j] + ((targetY - y[j]) * x0);
        }
        return x0;
    }

    public double solve(int maxEval, UnivariateFunction f, double min, double max, AllowedSolution allowedSolution) throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
        this.allowed = allowedSolution;
        return super.solve(maxEval, f, min, max);
    }

    public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue, AllowedSolution allowedSolution) throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
        this.allowed = allowedSolution;
        return super.solve(maxEval, f, min, max, startValue);
    }
}
