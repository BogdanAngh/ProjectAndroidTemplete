package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public class RombergIntegrator extends BaseAbstractUnivariateIntegrator {
    public static final int ROMBERG_MAX_ITERATIONS_COUNT = 32;

    public RombergIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > ROMBERG_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(ROMBERG_MAX_ITERATIONS_COUNT), false);
        }
    }

    public RombergIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > ROMBERG_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(ROMBERG_MAX_ITERATIONS_COUNT), false);
        }
    }

    public RombergIntegrator() {
        super(3, (int) ROMBERG_MAX_ITERATIONS_COUNT);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected double doIntegrate() throws org.apache.commons.math4.exception.TooManyEvaluationsException, org.apache.commons.math4.exception.MaxCountExceededException {
        /*
        r28 = this;
        r0 = r28;
        r0 = r0.iterations;
        r21 = r0;
        r21 = r21.getMaximalCount();
        r7 = r21 + 1;
        r10 = new double[r7];
        r2 = new double[r7];
        r11 = new org.apache.commons.math4.analysis.integration.TrapezoidIntegrator;
        r11.<init>();
        r21 = 0;
        r22 = 0;
        r0 = r28;
        r1 = r22;
        r22 = r11.stage(r0, r1);
        r2[r21] = r22;
        r0 = r28;
        r0 = r0.iterations;
        r21 = r0;
        r21.incrementCount();
        r21 = 0;
        r8 = r2[r21];
    L_0x0030:
        r0 = r28;
        r0 = r0.iterations;
        r21 = r0;
        r3 = r21.getCount();
        r20 = r10;
        r10 = r2;
        r2 = r20;
        r21 = 0;
        r0 = r28;
        r22 = r11.stage(r0, r3);
        r2[r21] = r22;
        r0 = r28;
        r0 = r0.iterations;
        r21 = r0;
        r21.incrementCount();
        r6 = 1;
    L_0x0053:
        if (r6 <= r3) goto L_0x0086;
    L_0x0055:
        r16 = r2[r3];
        r21 = r28.getMinimalIterationCount();
        r0 = r21;
        if (r3 < r0) goto L_0x00a6;
    L_0x005f:
        r22 = r16 - r8;
        r4 = org.apache.commons.math4.util.FastMath.abs(r22);
        r22 = r28.getRelativeAccuracy();
        r24 = org.apache.commons.math4.util.FastMath.abs(r8);
        r26 = org.apache.commons.math4.util.FastMath.abs(r16);
        r24 = r24 + r26;
        r22 = r22 * r24;
        r24 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r14 = r22 * r24;
        r21 = (r4 > r14 ? 1 : (r4 == r14 ? 0 : -1));
        if (r21 <= 0) goto L_0x0085;
    L_0x007d:
        r22 = r28.getAbsoluteAccuracy();
        r21 = (r4 > r22 ? 1 : (r4 == r22 ? 0 : -1));
        if (r21 > 0) goto L_0x00a6;
    L_0x0085:
        return r16;
    L_0x0086:
        r22 = 1;
        r21 = r6 * 2;
        r22 = r22 << r21;
        r24 = 1;
        r22 = r22 - r24;
        r0 = r22;
        r12 = (double) r0;
        r21 = r6 + -1;
        r18 = r2[r21];
        r21 = r6 + -1;
        r22 = r10[r21];
        r22 = r18 - r22;
        r22 = r22 / r12;
        r22 = r22 + r18;
        r2[r6] = r22;
        r6 = r6 + 1;
        goto L_0x0053;
    L_0x00a6:
        r8 = r16;
        goto L_0x0030;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.integration.RombergIntegrator.doIntegrate():double");
    }
}
