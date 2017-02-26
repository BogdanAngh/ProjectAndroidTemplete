package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class MullerSolver extends AbstractUnivariateSolver {
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;

    public MullerSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public MullerSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    public MullerSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    protected double doSolve() throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
        double min = getMin();
        double max = getMax();
        double initial = getStartValue();
        double functionValueAccuracy = getFunctionValueAccuracy();
        verifySequence(min, initial, max);
        double fMin = computeObjectiveValue(min);
        if (FastMath.abs(fMin) < functionValueAccuracy) {
            return min;
        }
        double fMax = computeObjectiveValue(max);
        if (FastMath.abs(fMax) < functionValueAccuracy) {
            return max;
        }
        double fInitial = computeObjectiveValue(initial);
        if (FastMath.abs(fInitial) < functionValueAccuracy) {
            return initial;
        }
        verifyBracketing(min, max);
        if (isBracketing(min, initial)) {
            return solve(min, initial, fMin, fInitial);
        }
        return solve(initial, max, fInitial, fMax);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private double solve(double r58, double r60, double r62, double r64) throws org.apache.commons.math4.exception.TooManyEvaluationsException {
        /*
        r57 = this;
        r28 = r57.getRelativeAccuracy();
        r12 = r57.getAbsoluteAccuracy();
        r24 = r57.getFunctionValueAccuracy();
        r6 = r58;
        r42 = r62;
        r10 = r60;
        r46 = r64;
        r50 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r52 = r6 + r10;
        r34 = r50 * r52;
        r0 = r57;
        r1 = r34;
        r44 = r0.computeObjectiveValue(r1);
        r26 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
    L_0x0024:
        r50 = r44 - r42;
        r52 = r34 - r6;
        r16 = r50 / r52;
        r50 = r46 - r44;
        r52 = r10 - r34;
        r20 = r50 / r52;
        r50 = r20 - r16;
        r52 = r10 - r6;
        r18 = r50 / r52;
        r50 = r34 - r6;
        r50 = r50 * r18;
        r14 = r16 + r50;
        r50 = r14 * r14;
        r52 = 4616189618054758400; // 0x4010000000000000 float:0.0 double:4.0;
        r52 = r52 * r44;
        r52 = r52 * r18;
        r22 = r50 - r52;
        r50 = -4611686018427387904; // 0xc000000000000000 float:0.0 double:-2.0;
        r50 = r50 * r44;
        r52 = org.apache.commons.math4.util.FastMath.sqrt(r22);
        r52 = r52 + r14;
        r50 = r50 / r52;
        r8 = r34 + r50;
        r50 = -4611686018427387904; // 0xc000000000000000 float:0.0 double:-2.0;
        r50 = r50 * r44;
        r52 = org.apache.commons.math4.util.FastMath.sqrt(r22);
        r52 = r14 - r52;
        r50 = r50 / r52;
        r38 = r34 + r50;
        r5 = r57;
        r5 = r5.isSequence(r6, r8, r10);
        if (r5 == 0) goto L_0x0093;
    L_0x006a:
        r32 = r8;
    L_0x006c:
        r0 = r57;
        r1 = r32;
        r40 = r0.computeObjectiveValue(r1);
        r50 = org.apache.commons.math4.util.FastMath.abs(r32);
        r50 = r50 * r28;
        r0 = r50;
        r30 = org.apache.commons.math4.util.FastMath.max(r0, r12);
        r50 = r32 - r26;
        r50 = org.apache.commons.math4.util.FastMath.abs(r50);
        r5 = (r50 > r30 ? 1 : (r50 == r30 ? 0 : -1));
        if (r5 <= 0) goto L_0x0092;
    L_0x008a:
        r50 = org.apache.commons.math4.util.FastMath.abs(r40);
        r5 = (r50 > r24 ? 1 : (r50 == r24 ? 0 : -1));
        if (r5 > 0) goto L_0x0096;
    L_0x0092:
        return r32;
    L_0x0093:
        r32 = r38;
        goto L_0x006c;
    L_0x0096:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 >= 0) goto L_0x00a9;
    L_0x009a:
        r50 = r34 - r6;
        r52 = 4606732058837280358; // 0x3fee666666666666 float:2.720083E23 double:0.95;
        r54 = r10 - r6;
        r52 = r52 * r54;
        r5 = (r50 > r52 ? 1 : (r50 == r52 ? 0 : -1));
        if (r5 > 0) goto L_0x00db;
    L_0x00a9:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 <= 0) goto L_0x00bc;
    L_0x00ad:
        r50 = r10 - r34;
        r52 = 4606732058837280358; // 0x3fee666666666666 float:2.720083E23 double:0.95;
        r54 = r10 - r6;
        r52 = r52 * r54;
        r5 = (r50 > r52 ? 1 : (r50 == r52 ? 0 : -1));
        if (r5 > 0) goto L_0x00db;
    L_0x00bc:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 == 0) goto L_0x00db;
    L_0x00c0:
        r4 = 0;
    L_0x00c1:
        if (r4 != 0) goto L_0x00e9;
    L_0x00c3:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 >= 0) goto L_0x00dd;
    L_0x00c7:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 >= 0) goto L_0x00e0;
    L_0x00cb:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 <= 0) goto L_0x00e3;
    L_0x00cf:
        r5 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
        if (r5 <= 0) goto L_0x00e6;
    L_0x00d3:
        r34 = r32;
        r44 = r40;
        r26 = r32;
        goto L_0x0024;
    L_0x00db:
        r4 = 1;
        goto L_0x00c1;
    L_0x00dd:
        r6 = r34;
        goto L_0x00c7;
    L_0x00e0:
        r42 = r44;
        goto L_0x00cb;
    L_0x00e3:
        r10 = r34;
        goto L_0x00cf;
    L_0x00e6:
        r46 = r44;
        goto L_0x00d3;
    L_0x00e9:
        r50 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r52 = r6 + r10;
        r36 = r50 * r52;
        r0 = r57;
        r1 = r36;
        r48 = r0.computeObjectiveValue(r1);
        r50 = org.apache.commons.math4.util.FastMath.signum(r42);
        r52 = org.apache.commons.math4.util.FastMath.signum(r48);
        r50 = r50 + r52;
        r52 = 0;
        r5 = (r50 > r52 ? 1 : (r50 == r52 ? 0 : -1));
        if (r5 != 0) goto L_0x011d;
    L_0x0107:
        r10 = r36;
        r46 = r48;
    L_0x010b:
        r50 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r52 = r6 + r10;
        r34 = r50 * r52;
        r0 = r57;
        r1 = r34;
        r44 = r0.computeObjectiveValue(r1);
        r26 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
        goto L_0x0024;
    L_0x011d:
        r6 = r36;
        r42 = r48;
        goto L_0x010b;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.solvers.MullerSolver.solve(double, double, double, double):double");
    }
}
