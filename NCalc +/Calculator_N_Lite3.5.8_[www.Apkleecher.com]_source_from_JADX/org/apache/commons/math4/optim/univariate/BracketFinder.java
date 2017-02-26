package org.apache.commons.math4.optim.univariate;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.util.Incrementor;

public class BracketFinder {
    private static final double EPS_MIN = 1.0E-21d;
    private static final double GOLD = 1.618034d;
    private final Incrementor evaluations;
    private double fHi;
    private double fLo;
    private double fMid;
    private final double growLimit;
    private double hi;
    private double lo;
    private double mid;

    public BracketFinder() {
        this(100.0d, 50);
    }

    public BracketFinder(double growLimit, int maxEvaluations) {
        this.evaluations = new Incrementor();
        if (growLimit <= 0.0d) {
            throw new NotStrictlyPositiveException(Double.valueOf(growLimit));
        } else if (maxEvaluations <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(maxEvaluations));
        } else {
            this.growLimit = growLimit;
            this.evaluations.setMaximalCount(maxEvaluations);
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void search(org.apache.commons.math4.analysis.UnivariateFunction r36, org.apache.commons.math4.optim.nonlinear.scalar.GoalType r37, double r38, double r40) {
        /*
        r35 = this;
        r0 = r35;
        r15 = r0.evaluations;
        r15.resetCount();
        r15 = org.apache.commons.math4.optim.nonlinear.scalar.GoalType.MINIMIZE;
        r0 = r37;
        if (r0 != r15) goto L_0x00ad;
    L_0x000d:
        r14 = 1;
    L_0x000e:
        r0 = r35;
        r1 = r36;
        r2 = r38;
        r6 = r0.eval(r1, r2);
        r0 = r35;
        r1 = r36;
        r2 = r40;
        r8 = r0.eval(r1, r2);
        if (r14 == 0) goto L_0x00b0;
    L_0x0024:
        r15 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r15 >= 0) goto L_0x0033;
    L_0x0028:
        r16 = r38;
        r38 = r40;
        r40 = r16;
        r16 = r6;
        r6 = r8;
        r8 = r16;
    L_0x0033:
        r30 = 4609965796492119705; // 0x3ff9e3779e9d0e99 float:-1.6629059E-20 double:1.618034;
        r32 = r40 - r38;
        r30 = r30 * r32;
        r28 = r40 + r30;
        r0 = r35;
        r1 = r36;
        r2 = r28;
        r10 = r0.eval(r1, r2);
    L_0x0048:
        if (r14 == 0) goto L_0x01b8;
    L_0x004a:
        r15 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1));
        if (r15 < 0) goto L_0x00b6;
    L_0x004e:
        r0 = r38;
        r2 = r35;
        r2.lo = r0;
        r0 = r35;
        r0.fLo = r6;
        r0 = r40;
        r2 = r35;
        r2.mid = r0;
        r0 = r35;
        r0.fMid = r8;
        r0 = r28;
        r2 = r35;
        r2.hi = r0;
        r0 = r35;
        r0.fHi = r10;
        r0 = r35;
        r0 = r0.lo;
        r30 = r0;
        r0 = r35;
        r0 = r0.hi;
        r32 = r0;
        r15 = (r30 > r32 ? 1 : (r30 == r32 ? 0 : -1));
        if (r15 <= 0) goto L_0x00ac;
    L_0x007c:
        r0 = r35;
        r0 = r0.lo;
        r16 = r0;
        r0 = r35;
        r0 = r0.hi;
        r30 = r0;
        r0 = r30;
        r2 = r35;
        r2.lo = r0;
        r0 = r16;
        r2 = r35;
        r2.hi = r0;
        r0 = r35;
        r0 = r0.fLo;
        r16 = r0;
        r0 = r35;
        r0 = r0.fHi;
        r30 = r0;
        r0 = r30;
        r2 = r35;
        r2.fLo = r0;
        r0 = r16;
        r2 = r35;
        r2.fHi = r0;
    L_0x00ac:
        return;
    L_0x00ad:
        r14 = 0;
        goto L_0x000e;
    L_0x00b0:
        r15 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1));
        if (r15 <= 0) goto L_0x0033;
    L_0x00b4:
        goto L_0x0028;
    L_0x00b6:
        r30 = r40 - r38;
        r32 = r8 - r10;
        r18 = r30 * r32;
        r30 = r40 - r28;
        r32 = r8 - r6;
        r20 = r30 * r32;
        r22 = r20 - r18;
        r30 = org.apache.commons.math4.util.FastMath.abs(r22);
        r32 = 4292743757239851855; // 0x3b92e3b40a0e9b4f float:6.8662616E-33 double:1.0E-21;
        r15 = (r30 > r32 ? 1 : (r30 == r32 ? 0 : -1));
        if (r15 >= 0) goto L_0x0114;
    L_0x00d1:
        r4 = 4297247356867222351; // 0x3ba2e3b40a0e9b4f float:6.8662616E-33 double:2.0E-21;
    L_0x00d6:
        r30 = r40 - r28;
        r30 = r30 * r20;
        r32 = r40 - r38;
        r32 = r32 * r18;
        r30 = r30 - r32;
        r30 = r30 / r4;
        r24 = r40 - r30;
        r0 = r35;
        r0 = r0.growLimit;
        r30 = r0;
        r32 = r28 - r40;
        r30 = r30 * r32;
        r26 = r40 + r30;
        r30 = r24 - r28;
        r32 = r40 - r24;
        r30 = r30 * r32;
        r32 = 0;
        r15 = (r30 > r32 ? 1 : (r30 == r32 ? 0 : -1));
        if (r15 <= 0) goto L_0x014c;
    L_0x00fc:
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
        if (r14 == 0) goto L_0x0119;
    L_0x0108:
        r15 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r15 >= 0) goto L_0x011d;
    L_0x010c:
        r38 = r40;
        r40 = r24;
        r6 = r8;
        r8 = r12;
        goto L_0x004e;
    L_0x0114:
        r30 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r4 = r30 * r22;
        goto L_0x00d6;
    L_0x0119:
        r15 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r15 > 0) goto L_0x010c;
    L_0x011d:
        if (r14 == 0) goto L_0x0128;
    L_0x011f:
        r15 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r15 <= 0) goto L_0x012c;
    L_0x0123:
        r28 = r24;
        r10 = r12;
        goto L_0x004e;
    L_0x0128:
        r15 = (r12 > r8 ? 1 : (r12 == r8 ? 0 : -1));
        if (r15 < 0) goto L_0x0123;
    L_0x012c:
        r30 = 4609965796492119705; // 0x3ff9e3779e9d0e99 float:-1.6629059E-20 double:1.618034;
        r32 = r28 - r40;
        r30 = r30 * r32;
        r24 = r28 + r30;
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
    L_0x0141:
        r38 = r40;
        r6 = r8;
        r40 = r28;
        r8 = r10;
        r28 = r24;
        r10 = r12;
        goto L_0x0048;
    L_0x014c:
        r30 = r24 - r26;
        r32 = r26 - r28;
        r30 = r30 * r32;
        r32 = 0;
        r15 = (r30 > r32 ? 1 : (r30 == r32 ? 0 : -1));
        if (r15 < 0) goto L_0x0165;
    L_0x0158:
        r24 = r26;
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
        goto L_0x0141;
    L_0x0165:
        r30 = r24 - r26;
        r32 = r28 - r24;
        r30 = r30 * r32;
        r32 = 0;
        r15 = (r30 > r32 ? 1 : (r30 == r32 ? 0 : -1));
        if (r15 <= 0) goto L_0x01a2;
    L_0x0171:
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
        if (r14 == 0) goto L_0x019d;
    L_0x017d:
        r15 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r15 >= 0) goto L_0x0141;
    L_0x0181:
        r40 = r28;
        r28 = r24;
        r30 = 4609965796492119705; // 0x3ff9e3779e9d0e99 float:-1.6629059E-20 double:1.618034;
        r32 = r28 - r40;
        r30 = r30 * r32;
        r24 = r28 + r30;
        r8 = r10;
        r10 = r12;
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
        goto L_0x0141;
    L_0x019d:
        r15 = (r12 > r10 ? 1 : (r12 == r10 ? 0 : -1));
        if (r15 <= 0) goto L_0x0141;
    L_0x01a1:
        goto L_0x0181;
    L_0x01a2:
        r30 = 4609965796492119705; // 0x3ff9e3779e9d0e99 float:-1.6629059E-20 double:1.618034;
        r32 = r28 - r40;
        r30 = r30 * r32;
        r24 = r28 + r30;
        r0 = r35;
        r1 = r36;
        r2 = r24;
        r12 = r0.eval(r1, r2);
        goto L_0x0141;
    L_0x01b8:
        r15 = (r10 > r8 ? 1 : (r10 == r8 ? 0 : -1));
        if (r15 > 0) goto L_0x00b6;
    L_0x01bc:
        goto L_0x004e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.optim.univariate.BracketFinder.search(org.apache.commons.math4.analysis.UnivariateFunction, org.apache.commons.math4.optim.nonlinear.scalar.GoalType, double, double):void");
    }

    public int getMaxEvaluations() {
        return this.evaluations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    public double getLo() {
        return this.lo;
    }

    public double getFLo() {
        return this.fLo;
    }

    public double getHi() {
        return this.hi;
    }

    public double getFHi() {
        return this.fHi;
    }

    public double getMid() {
        return this.mid;
    }

    public double getFMid() {
        return this.fMid;
    }

    private double eval(UnivariateFunction f, double x) {
        try {
            this.evaluations.incrementCount();
            return f.value(x);
        } catch (MaxCountExceededException e) {
            throw new TooManyEvaluationsException(e.getMax());
        }
    }
}
