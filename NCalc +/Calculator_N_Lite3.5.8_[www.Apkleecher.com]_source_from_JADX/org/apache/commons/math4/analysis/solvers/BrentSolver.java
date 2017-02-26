package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.FastMath;

public class BrentSolver extends AbstractUnivariateSolver {
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;

    public BrentSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public BrentSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
    }

    public BrentSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
    }

    public BrentSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
    }

    protected double doSolve() throws NoBracketingException, TooManyEvaluationsException, NumberIsTooLargeException {
        double min = getMin();
        double max = getMax();
        double initial = getStartValue();
        double functionValueAccuracy = getFunctionValueAccuracy();
        verifySequence(min, initial, max);
        double yInitial = computeObjectiveValue(initial);
        if (FastMath.abs(yInitial) <= functionValueAccuracy) {
            return initial;
        }
        double yMin = computeObjectiveValue(min);
        if (FastMath.abs(yMin) <= functionValueAccuracy) {
            return min;
        }
        if (yInitial * yMin < 0.0d) {
            return brent(min, initial, yMin, yInitial);
        }
        double yMax = computeObjectiveValue(max);
        if (FastMath.abs(yMax) <= functionValueAccuracy) {
            return max;
        }
        if (yInitial * yMax < 0.0d) {
            return brent(initial, max, yInitial, yMax);
        }
        throw new NoBracketingException(min, max, yMin, yMax);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private double brent(double r44, double r46, double r48, double r50) {
        /*
        r43 = this;
        r4 = r44;
        r16 = r48;
        r6 = r46;
        r18 = r50;
        r8 = r4;
        r20 = r16;
        r10 = r6 - r4;
        r12 = r10;
        r32 = r43.getAbsoluteAccuracy();
        r14 = r43.getRelativeAccuracy();
    L_0x0016:
        r36 = org.apache.commons.math4.util.FastMath.abs(r20);
        r38 = org.apache.commons.math4.util.FastMath.abs(r18);
        r36 = (r36 > r38 ? 1 : (r36 == r38 ? 0 : -1));
        if (r36 >= 0) goto L_0x002b;
    L_0x0022:
        r4 = r6;
        r6 = r8;
        r8 = r4;
        r16 = r18;
        r18 = r20;
        r20 = r16;
    L_0x002b:
        r36 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r36 = r36 * r14;
        r38 = org.apache.commons.math4.util.FastMath.abs(r6);
        r36 = r36 * r38;
        r34 = r36 + r32;
        r36 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r38 = r8 - r6;
        r22 = r36 * r38;
        r36 = org.apache.commons.math4.util.FastMath.abs(r22);
        r36 = (r36 > r34 ? 1 : (r36 == r34 ? 0 : -1));
        if (r36 <= 0) goto L_0x0051;
    L_0x0045:
        r36 = 0;
        r0 = r18;
        r2 = r36;
        r36 = org.apache.commons.math4.util.Precision.equals(r0, r2);
        if (r36 == 0) goto L_0x0052;
    L_0x0051:
        return r6;
    L_0x0052:
        r36 = org.apache.commons.math4.util.FastMath.abs(r12);
        r36 = (r36 > r34 ? 1 : (r36 == r34 ? 0 : -1));
        if (r36 < 0) goto L_0x0066;
    L_0x005a:
        r36 = org.apache.commons.math4.util.FastMath.abs(r16);
        r38 = org.apache.commons.math4.util.FastMath.abs(r18);
        r36 = (r36 > r38 ? 1 : (r36 == r38 ? 0 : -1));
        if (r36 > 0) goto L_0x009b;
    L_0x0066:
        r10 = r22;
        r12 = r10;
    L_0x0069:
        r4 = r6;
        r16 = r18;
        r36 = org.apache.commons.math4.util.FastMath.abs(r10);
        r36 = (r36 > r34 ? 1 : (r36 == r34 ? 0 : -1));
        if (r36 <= 0) goto L_0x0112;
    L_0x0074:
        r6 = r6 + r10;
    L_0x0075:
        r0 = r43;
        r18 = r0.computeObjectiveValue(r6);
        r36 = 0;
        r36 = (r18 > r36 ? 1 : (r18 == r36 ? 0 : -1));
        if (r36 <= 0) goto L_0x0087;
    L_0x0081:
        r36 = 0;
        r36 = (r20 > r36 ? 1 : (r20 == r36 ? 0 : -1));
        if (r36 > 0) goto L_0x0093;
    L_0x0087:
        r36 = 0;
        r36 = (r18 > r36 ? 1 : (r18 == r36 ? 0 : -1));
        if (r36 > 0) goto L_0x0016;
    L_0x008d:
        r36 = 0;
        r36 = (r20 > r36 ? 1 : (r20 == r36 ? 0 : -1));
        if (r36 > 0) goto L_0x0016;
    L_0x0093:
        r8 = r4;
        r20 = r16;
        r10 = r6 - r4;
        r12 = r10;
        goto L_0x0016;
    L_0x009b:
        r30 = r18 / r16;
        r36 = (r4 > r8 ? 1 : (r4 == r8 ? 0 : -1));
        if (r36 != 0) goto L_0x00dd;
    L_0x00a1:
        r36 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r36 = r36 * r22;
        r24 = r36 * r30;
        r36 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r26 = r36 - r30;
    L_0x00ab:
        r36 = 0;
        r36 = (r24 > r36 ? 1 : (r24 == r36 ? 0 : -1));
        if (r36 <= 0) goto L_0x0108;
    L_0x00b1:
        r0 = r26;
        r0 = -r0;
        r26 = r0;
    L_0x00b6:
        r30 = r12;
        r12 = r10;
        r36 = 4609434218613702656; // 0x3ff8000000000000 float:0.0 double:1.5;
        r36 = r36 * r22;
        r36 = r36 * r26;
        r38 = r34 * r26;
        r38 = org.apache.commons.math4.util.FastMath.abs(r38);
        r36 = r36 - r38;
        r36 = (r24 > r36 ? 1 : (r24 == r36 ? 0 : -1));
        if (r36 >= 0) goto L_0x00d9;
    L_0x00cb:
        r36 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r36 = r36 * r30;
        r36 = r36 * r26;
        r36 = org.apache.commons.math4.util.FastMath.abs(r36);
        r36 = (r24 > r36 ? 1 : (r24 == r36 ? 0 : -1));
        if (r36 < 0) goto L_0x010e;
    L_0x00d9:
        r10 = r22;
        r12 = r10;
        goto L_0x0069;
    L_0x00dd:
        r26 = r16 / r20;
        r28 = r18 / r20;
        r36 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r36 = r36 * r22;
        r36 = r36 * r26;
        r38 = r26 - r28;
        r36 = r36 * r38;
        r38 = r6 - r4;
        r40 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r40 = r28 - r40;
        r38 = r38 * r40;
        r36 = r36 - r38;
        r24 = r30 * r36;
        r36 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r36 = r26 - r36;
        r38 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r38 = r28 - r38;
        r36 = r36 * r38;
        r38 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r38 = r30 - r38;
        r26 = r36 * r38;
        goto L_0x00ab;
    L_0x0108:
        r0 = r24;
        r0 = -r0;
        r24 = r0;
        goto L_0x00b6;
    L_0x010e:
        r10 = r24 / r26;
        goto L_0x0069;
    L_0x0112:
        r36 = 0;
        r36 = (r22 > r36 ? 1 : (r22 == r36 ? 0 : -1));
        if (r36 <= 0) goto L_0x011c;
    L_0x0118:
        r6 = r6 + r34;
        goto L_0x0075;
    L_0x011c:
        r6 = r6 - r34;
        goto L_0x0075;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.solvers.BrentSolver.brent(double, double, double, double):double");
    }
}
