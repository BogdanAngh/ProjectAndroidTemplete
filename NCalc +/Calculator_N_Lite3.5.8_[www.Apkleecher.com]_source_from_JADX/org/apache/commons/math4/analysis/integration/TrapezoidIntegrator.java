package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;

public class TrapezoidIntegrator extends BaseAbstractUnivariateIntegrator {
    public static final int TRAPEZOID_MAX_ITERATIONS_COUNT = 64;
    private double s;

    public TrapezoidIntegrator(double relativeAccuracy, double absoluteAccuracy, int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(relativeAccuracy, absoluteAccuracy, minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > TRAPEZOID_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(TRAPEZOID_MAX_ITERATIONS_COUNT), false);
        }
    }

    public TrapezoidIntegrator(int minimalIterationCount, int maximalIterationCount) throws NotStrictlyPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        super(minimalIterationCount, maximalIterationCount);
        if (maximalIterationCount > TRAPEZOID_MAX_ITERATIONS_COUNT) {
            throw new NumberIsTooLargeException(Integer.valueOf(maximalIterationCount), Integer.valueOf(TRAPEZOID_MAX_ITERATIONS_COUNT), false);
        }
    }

    public TrapezoidIntegrator() {
        super(3, (int) TRAPEZOID_MAX_ITERATIONS_COUNT);
    }

    double stage(BaseAbstractUnivariateIntegrator baseIntegrator, int n) throws TooManyEvaluationsException {
        if (n == 0) {
            double max = baseIntegrator.getMax();
            double min = baseIntegrator.getMin();
            this.s = (0.5d * (max - min)) * (baseIntegrator.computeObjectiveValue(min) + baseIntegrator.computeObjectiveValue(max));
            return this.s;
        }
        long np = 1 << (n - 1);
        double sum = 0.0d;
        max = baseIntegrator.getMax();
        min = baseIntegrator.getMin();
        double spacing = (max - min) / ((double) np);
        double x = min + (0.5d * spacing);
        for (long i = 0; i < np; i++) {
            sum += baseIntegrator.computeObjectiveValue(x);
            x += spacing;
        }
        this.s = 0.5d * (this.s + (sum * spacing));
        return this.s;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected double doIntegrate() throws org.apache.commons.math4.exception.MathIllegalArgumentException, org.apache.commons.math4.exception.TooManyEvaluationsException, org.apache.commons.math4.exception.MaxCountExceededException {
        /*
        r18 = this;
        r5 = 0;
        r0 = r18;
        r1 = r18;
        r6 = r0.stage(r1, r5);
        r0 = r18;
        r5 = r0.iterations;
        r5.incrementCount();
    L_0x0010:
        r0 = r18;
        r5 = r0.iterations;
        r4 = r5.getCount();
        r0 = r18;
        r1 = r18;
        r10 = r0.stage(r1, r4);
        r5 = r18.getMinimalIterationCount();
        if (r4 < r5) goto L_0x004c;
    L_0x0026:
        r12 = r10 - r6;
        r2 = org.apache.commons.math4.util.FastMath.abs(r12);
        r12 = r18.getRelativeAccuracy();
        r14 = org.apache.commons.math4.util.FastMath.abs(r6);
        r16 = org.apache.commons.math4.util.FastMath.abs(r10);
        r14 = r14 + r16;
        r12 = r12 * r14;
        r14 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r8 = r12 * r14;
        r5 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
        if (r5 <= 0) goto L_0x004b;
    L_0x0043:
        r12 = r18.getAbsoluteAccuracy();
        r5 = (r2 > r12 ? 1 : (r2 == r12 ? 0 : -1));
        if (r5 > 0) goto L_0x004c;
    L_0x004b:
        return r10;
    L_0x004c:
        r6 = r10;
        r0 = r18;
        r5 = r0.iterations;
        r5.incrementCount();
        goto L_0x0010;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.integration.TrapezoidIntegrator.doIntegrate():double");
    }
}
