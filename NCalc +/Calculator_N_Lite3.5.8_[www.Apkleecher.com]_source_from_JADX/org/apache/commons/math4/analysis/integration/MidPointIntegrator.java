package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;

public class MidPointIntegrator extends BaseAbstractUnivariateIntegrator {
    public static final int MIDPOINT_MAX_ITERATIONS_COUNT = 64;

    public MidPointIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > MIDPOINT_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(MIDPOINT_MAX_ITERATIONS_COUNT), false);
        }
    }

    public MidPointIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > MIDPOINT_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(MIDPOINT_MAX_ITERATIONS_COUNT), false);
        }
    }

    public MidPointIntegrator() {
        super(3, (int) MIDPOINT_MAX_ITERATIONS_COUNT);
    }

    private double stage(int n, double previousStageResult, double min, double diffMaxMin) throws TooManyEvaluationsException {
        long np = 1 << (n - 1);
        double sum = 0.0d;
        double spacing = diffMaxMin / ((double) np);
        double x = min + (0.5d * spacing);
        for (long i = 0; i < np; i++) {
            sum += computeObjectiveValue(x);
            x += spacing;
        }
        return 0.5d * ((sum * spacing) + previousStageResult);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected double doIntegrate() throws org.apache.commons.math4.exception.MathIllegalArgumentException, org.apache.commons.math4.exception.TooManyEvaluationsException, org.apache.commons.math4.exception.MaxCountExceededException {
        /*
        r24 = this;
        r6 = r24.getMin();
        r18 = r24.getMax();
        r8 = r18 - r6;
        r18 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r18 = r18 * r8;
        r12 = r6 + r18;
        r0 = r24;
        r18 = r0.computeObjectiveValue(r12);
        r4 = r8 * r18;
    L_0x0018:
        r0 = r24;
        r2 = r0.iterations;
        r2.incrementCount();
        r0 = r24;
        r2 = r0.iterations;
        r3 = r2.getCount();
        r2 = r24;
        r16 = r2.stage(r3, r4, r6, r8);
        r2 = r24.getMinimalIterationCount();
        if (r3 < r2) goto L_0x005a;
    L_0x0033:
        r18 = r16 - r4;
        r10 = org.apache.commons.math4.util.FastMath.abs(r18);
        r18 = r24.getRelativeAccuracy();
        r20 = org.apache.commons.math4.util.FastMath.abs(r4);
        r22 = org.apache.commons.math4.util.FastMath.abs(r16);
        r20 = r20 + r22;
        r18 = r18 * r20;
        r20 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r14 = r18 * r20;
        r2 = (r10 > r14 ? 1 : (r10 == r14 ? 0 : -1));
        if (r2 <= 0) goto L_0x0059;
    L_0x0051:
        r18 = r24.getAbsoluteAccuracy();
        r2 = (r10 > r18 ? 1 : (r10 == r18 ? 0 : -1));
        if (r2 > 0) goto L_0x005a;
    L_0x0059:
        return r16;
    L_0x005a:
        r4 = r16;
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.integration.MidPointIntegrator.doIntegrate():double");
    }
}
