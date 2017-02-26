package org.apache.commons.math4.fitting.leastsquares;

import java.util.Arrays;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Precision;

public class LevenbergMarquardtOptimizer implements LeastSquaresOptimizer {
    private static final double TWO_EPS;
    private final double costRelativeTolerance;
    private final double initialStepBoundFactor;
    private final double orthoTolerance;
    private final double parRelativeTolerance;
    private final double qrRankingThreshold;

    private static class InternalData {
        private final double[] beta;
        private final double[] diagR;
        private final double[] jacNorm;
        private final int[] permutation;
        private final int rank;
        private final double[][] weightedJacobian;

        InternalData(double[][] weightedJacobian, int[] permutation, int rank, double[] diagR, double[] jacNorm, double[] beta) {
            this.weightedJacobian = weightedJacobian;
            this.permutation = permutation;
            this.rank = rank;
            this.diagR = diagR;
            this.jacNorm = jacNorm;
            this.beta = beta;
        }
    }

    static {
        TWO_EPS = 2.0d * Precision.EPSILON;
    }

    public LevenbergMarquardtOptimizer() {
        this(100.0d, CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD, CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD, CholeskyDecomposition.DEFAULT_ABSOLUTE_POSITIVITY_THRESHOLD, Precision.SAFE_MIN);
    }

    public LevenbergMarquardtOptimizer(double initialStepBoundFactor, double costRelativeTolerance, double parRelativeTolerance, double orthoTolerance, double qrRankingThreshold) {
        this.initialStepBoundFactor = initialStepBoundFactor;
        this.costRelativeTolerance = costRelativeTolerance;
        this.parRelativeTolerance = parRelativeTolerance;
        this.orthoTolerance = orthoTolerance;
        this.qrRankingThreshold = qrRankingThreshold;
    }

    public LevenbergMarquardtOptimizer withInitialStepBoundFactor(double newInitialStepBoundFactor) {
        return new LevenbergMarquardtOptimizer(newInitialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
    }

    public LevenbergMarquardtOptimizer withCostRelativeTolerance(double newCostRelativeTolerance) {
        return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, newCostRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
    }

    public LevenbergMarquardtOptimizer withParameterRelativeTolerance(double newParRelativeTolerance) {
        return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, newParRelativeTolerance, this.orthoTolerance, this.qrRankingThreshold);
    }

    public LevenbergMarquardtOptimizer withOrthoTolerance(double newOrthoTolerance) {
        return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, newOrthoTolerance, this.qrRankingThreshold);
    }

    public LevenbergMarquardtOptimizer withRankingThreshold(double newQRRankingThreshold) {
        return new LevenbergMarquardtOptimizer(this.initialStepBoundFactor, this.costRelativeTolerance, this.parRelativeTolerance, this.orthoTolerance, newQRRankingThreshold);
    }

    public double getInitialStepBoundFactor() {
        return this.initialStepBoundFactor;
    }

    public double getCostRelativeTolerance() {
        return this.costRelativeTolerance;
    }

    public double getParameterRelativeTolerance() {
        return this.parRelativeTolerance;
    }

    public double getOrthoTolerance() {
        return this.orthoTolerance;
    }

    public double getRankingThreshold() {
        return this.qrRankingThreshold;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public org.apache.commons.math4.fitting.leastsquares.LeastSquaresOptimizer.Optimum optimize(org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem r87) {
        /*
        r86 = this;
        r48 = r87.getObservationSize();
        r43 = r87.getParameterSize();
        r39 = r87.getIterationCounter();
        r36 = r87.getEvaluationCounter();
        r17 = r87.getConvergenceChecker();
        r0 = r48;
        r1 = r43;
        r10 = org.apache.commons.math4.util.FastMath.min(r0, r1);
        r0 = r43;
        r14 = new double[r0];
        r15 = 0;
        r6 = 0;
        r76 = 0;
        r0 = r43;
        r8 = new double[r0];
        r0 = r43;
        r0 = new double[r0];
        r50 = r0;
        r0 = r48;
        r0 = new double[r0];
        r49 = r0;
        r0 = r48;
        r5 = new double[r0];
        r0 = r43;
        r11 = new double[r0];
        r0 = r43;
        r12 = new double[r0];
        r0 = r43;
        r13 = new double[r0];
        r36.incrementCount();
        r4 = r87.getStart();
        r0 = r87;
        r24 = r0.evaluate(r4);
        r4 = r24.getResiduals();
        r28 = r4.toArray();
        r26 = r24.getCost();
        r4 = r24.getPoint();
        r25 = r4.toArray();
        r37 = 1;
    L_0x0069:
        r39.incrementCount();
        r58 = r24;
        r4 = r24.getJacobian();
        r0 = r86;
        r9 = r0.qrDecomposition(r4, r10);
        r72 = r9.weightedJacobian;
        r51 = r9.permutation;
        r29 = r9.diagR;
        r41 = r9.jacNorm;
        r73 = r28;
        r38 = 0;
    L_0x008c:
        r0 = r38;
        r1 = r48;
        if (r0 < r1) goto L_0x00e3;
    L_0x0092:
        r0 = r86;
        r0.qTy(r5, r9);
        r42 = 0;
    L_0x0099:
        r0 = r42;
        if (r0 < r10) goto L_0x00ea;
    L_0x009d:
        if (r37 == 0) goto L_0x00b7;
    L_0x009f:
        r76 = 0;
        r42 = 0;
    L_0x00a3:
        r0 = r42;
        r1 = r43;
        if (r0 < r1) goto L_0x00f5;
    L_0x00a9:
        r76 = org.apache.commons.math4.util.FastMath.sqrt(r76);
        r80 = 0;
        r4 = (r76 > r80 ? 1 : (r76 == r80 ? 0 : -1));
        if (r4 != 0) goto L_0x010c;
    L_0x00b3:
        r0 = r86;
        r6 = r0.initialStepBoundFactor;
    L_0x00b7:
        r46 = 0;
        r80 = 0;
        r4 = (r26 > r80 ? 1 : (r26 == r80 ? 0 : -1));
        if (r4 == 0) goto L_0x00c5;
    L_0x00bf:
        r40 = 0;
    L_0x00c1:
        r0 = r40;
        if (r0 < r10) goto L_0x0115;
    L_0x00c5:
        r0 = r86;
        r0 = r0.orthoTolerance;
        r80 = r0;
        r4 = (r46 > r80 ? 1 : (r46 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x0149;
    L_0x00cf:
        r4 = new org.apache.commons.math4.fitting.leastsquares.OptimumImpl;
        r80 = r36.getCount();
        r81 = r39.getCount();
        r0 = r24;
        r1 = r80;
        r2 = r81;
        r4.<init>(r0, r1, r2);
    L_0x00e2:
        return r4;
    L_0x00e3:
        r80 = r73[r38];
        r5[r38] = r80;
        r38 = r38 + 1;
        goto L_0x008c;
    L_0x00ea:
        r55 = r51[r42];
        r4 = r72[r42];
        r80 = r29[r55];
        r4[r55] = r80;
        r42 = r42 + 1;
        goto L_0x0099;
    L_0x00f5:
        r34 = r41[r42];
        r80 = 0;
        r4 = (r34 > r80 ? 1 : (r34 == r80 ? 0 : -1));
        if (r4 != 0) goto L_0x00ff;
    L_0x00fd:
        r34 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
    L_0x00ff:
        r80 = r25[r42];
        r78 = r34 * r80;
        r80 = r78 * r78;
        r76 = r76 + r80;
        r8[r42] = r34;
        r42 = r42 + 1;
        goto L_0x00a3;
    L_0x010c:
        r0 = r86;
        r0 = r0.initialStepBoundFactor;
        r80 = r0;
        r6 = r80 * r76;
        goto L_0x00b7;
    L_0x0115:
        r54 = r51[r40];
        r66 = r41[r54];
        r80 = 0;
        r4 = (r66 > r80 ? 1 : (r66 == r80 ? 0 : -1));
        if (r4 == 0) goto L_0x0139;
    L_0x011f:
        r68 = 0;
        r38 = 0;
    L_0x0123:
        r0 = r38;
        r1 = r40;
        if (r0 <= r1) goto L_0x013c;
    L_0x0129:
        r80 = org.apache.commons.math4.util.FastMath.abs(r68);
        r82 = r66 * r26;
        r80 = r80 / r82;
        r0 = r46;
        r2 = r80;
        r46 = org.apache.commons.math4.util.FastMath.max(r0, r2);
    L_0x0139:
        r40 = r40 + 1;
        goto L_0x00c1;
    L_0x013c:
        r4 = r72[r38];
        r80 = r4[r54];
        r82 = r5[r38];
        r80 = r80 * r82;
        r68 = r68 + r80;
        r38 = r38 + 1;
        goto L_0x0123;
    L_0x0149:
        r40 = 0;
    L_0x014b:
        r0 = r40;
        r1 = r43;
        if (r0 < r1) goto L_0x0269;
    L_0x0151:
        r64 = 0;
    L_0x0153:
        r80 = 4547007122018943789; // 0x3f1a36e2eb1c432d float:-1.8890966E26 double:1.0E-4;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 >= 0) goto L_0x0069;
    L_0x015c:
        r40 = 0;
    L_0x015e:
        r0 = r40;
        if (r0 < r10) goto L_0x0277;
    L_0x0162:
        r60 = r26;
        r59 = r73;
        r73 = r49;
        r49 = r59;
        r4 = r86;
        r15 = r4.determineLMParameter(r5, r6, r8, r9, r10, r11, r12, r13, r14, r15);
        r44 = 0;
        r40 = 0;
    L_0x0174:
        r0 = r40;
        if (r0 < r10) goto L_0x0281;
    L_0x0178:
        r44 = org.apache.commons.math4.util.FastMath.sqrt(r44);
        if (r37 == 0) goto L_0x0184;
    L_0x017e:
        r0 = r44;
        r6 = org.apache.commons.math4.util.FastMath.min(r6, r0);
    L_0x0184:
        r36.incrementCount();
        r4 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r25;
        r4.<init>(r0);
        r0 = r87;
        r24 = r0.evaluate(r4);
        r4 = r24.getResiduals();
        r28 = r4.toArray();
        r26 = r24.getCost();
        r4 = r24.getPoint();
        r25 = r4.toArray();
        r18 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r80 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r80 = r80 * r26;
        r4 = (r80 > r60 ? 1 : (r80 == r60 ? 0 : -1));
        if (r4 >= 0) goto L_0x01bd;
    L_0x01b5:
        r62 = r26 / r60;
        r80 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r82 = r62 * r62;
        r18 = r80 - r82;
    L_0x01bd:
        r40 = 0;
    L_0x01bf:
        r0 = r40;
        if (r0 < r10) goto L_0x02a2;
    L_0x01c3:
        r20 = 0;
        r40 = 0;
    L_0x01c7:
        r0 = r40;
        if (r0 < r10) goto L_0x02c5;
    L_0x01cb:
        r52 = r60 * r60;
        r20 = r20 / r52;
        r80 = r15 * r44;
        r80 = r80 * r44;
        r22 = r80 / r52;
        r80 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r80 = r80 * r22;
        r56 = r20 + r80;
        r80 = r20 + r22;
        r0 = r80;
        r0 = -r0;
        r30 = r0;
        r80 = 0;
        r4 = (r56 > r80 ? 1 : (r56 == r80 ? 0 : -1));
        if (r4 != 0) goto L_0x02d1;
    L_0x01e8:
        r64 = 0;
    L_0x01ea:
        r80 = 4598175219545276416; // 0x3fd0000000000000 float:0.0 double:0.25;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x02d9;
    L_0x01f0:
        r80 = 0;
        r4 = (r18 > r80 ? 1 : (r18 == r80 ? 0 : -1));
        if (r4 >= 0) goto L_0x02d5;
    L_0x01f6:
        r80 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r80 = r80 * r30;
        r82 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r82 = r82 * r18;
        r82 = r82 + r30;
        r70 = r80 / r82;
    L_0x0202:
        r80 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r80 = r80 * r26;
        r4 = (r80 > r60 ? 1 : (r80 == r60 ? 0 : -1));
        if (r4 >= 0) goto L_0x0216;
    L_0x020d:
        r80 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r4 = (r70 > r80 ? 1 : (r70 == r80 ? 0 : -1));
        if (r4 >= 0) goto L_0x021b;
    L_0x0216:
        r70 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
    L_0x021b:
        r80 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        r80 = r80 * r44;
        r0 = r80;
        r80 = org.apache.commons.math4.util.FastMath.min(r6, r0);
        r6 = r70 * r80;
        r15 = r15 / r70;
    L_0x0229:
        r80 = 4547007122018943789; // 0x3f1a36e2eb1c432d float:-1.8890966E26 double:1.0E-4;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 < 0) goto L_0x02fd;
    L_0x0232:
        r37 = 0;
        r76 = 0;
        r42 = 0;
    L_0x0238:
        r0 = r42;
        r1 = r43;
        if (r0 < r1) goto L_0x02ef;
    L_0x023e:
        r76 = org.apache.commons.math4.util.FastMath.sqrt(r76);
        if (r17 == 0) goto L_0x030d;
    L_0x0244:
        r4 = r39.getCount();
        r0 = r17;
        r1 = r58;
        r2 = r24;
        r4 = r0.converged(r4, r1, r2);
        if (r4 == 0) goto L_0x030d;
    L_0x0254:
        r4 = new org.apache.commons.math4.fitting.leastsquares.OptimumImpl;
        r80 = r36.getCount();
        r81 = r39.getCount();
        r0 = r24;
        r1 = r80;
        r2 = r81;
        r4.<init>(r0, r1, r2);
        goto L_0x00e2;
    L_0x0269:
        r80 = r8[r40];
        r82 = r41[r40];
        r80 = org.apache.commons.math4.util.FastMath.max(r80, r82);
        r8[r40] = r80;
        r40 = r40 + 1;
        goto L_0x014b;
    L_0x0277:
        r54 = r51[r40];
        r80 = r25[r54];
        r50[r54] = r80;
        r40 = r40 + 1;
        goto L_0x015e;
    L_0x0281:
        r54 = r51[r40];
        r80 = r14[r54];
        r0 = r80;
        r0 = -r0;
        r80 = r0;
        r14[r54] = r80;
        r80 = r50[r54];
        r82 = r14[r54];
        r80 = r80 + r82;
        r25[r54] = r80;
        r80 = r8[r54];
        r82 = r14[r54];
        r66 = r80 * r82;
        r80 = r66 * r66;
        r44 = r44 + r80;
        r40 = r40 + 1;
        goto L_0x0174;
    L_0x02a2:
        r54 = r51[r40];
        r32 = r14[r54];
        r80 = 0;
        r11[r40] = r80;
        r38 = 0;
    L_0x02ac:
        r0 = r38;
        r1 = r40;
        if (r0 <= r1) goto L_0x02b6;
    L_0x02b2:
        r40 = r40 + 1;
        goto L_0x01bf;
    L_0x02b6:
        r80 = r11[r38];
        r4 = r72[r38];
        r82 = r4[r54];
        r82 = r82 * r32;
        r80 = r80 + r82;
        r11[r38] = r80;
        r38 = r38 + 1;
        goto L_0x02ac;
    L_0x02c5:
        r80 = r11[r40];
        r82 = r11[r40];
        r80 = r80 * r82;
        r20 = r20 + r80;
        r40 = r40 + 1;
        goto L_0x01c7;
    L_0x02d1:
        r64 = r18 / r56;
        goto L_0x01ea;
    L_0x02d5:
        r70 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        goto L_0x0202;
    L_0x02d9:
        r80 = 0;
        r4 = (r15 > r80 ? 1 : (r15 == r80 ? 0 : -1));
        if (r4 == 0) goto L_0x02e5;
    L_0x02df:
        r80 = 4604930618986332160; // 0x3fe8000000000000 float:0.0 double:0.75;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 < 0) goto L_0x0229;
    L_0x02e5:
        r80 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r6 = r80 * r44;
        r80 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r15 = r15 * r80;
        goto L_0x0229;
    L_0x02ef:
        r80 = r8[r42];
        r82 = r25[r42];
        r74 = r80 * r82;
        r80 = r74 * r74;
        r76 = r76 + r80;
        r42 = r42 + 1;
        goto L_0x0238;
    L_0x02fd:
        r26 = r60;
        r40 = 0;
    L_0x0301:
        r0 = r40;
        if (r0 < r10) goto L_0x034c;
    L_0x0305:
        r59 = r73;
        r73 = r49;
        r49 = r59;
        r24 = r58;
    L_0x030d:
        r80 = org.apache.commons.math4.util.FastMath.abs(r18);
        r0 = r86;
        r0 = r0.costRelativeTolerance;
        r82 = r0;
        r4 = (r80 > r82 ? 1 : (r80 == r82 ? 0 : -1));
        if (r4 > 0) goto L_0x032b;
    L_0x031b:
        r0 = r86;
        r0 = r0.costRelativeTolerance;
        r80 = r0;
        r4 = (r56 > r80 ? 1 : (r56 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x032b;
    L_0x0325:
        r80 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 <= 0) goto L_0x0337;
    L_0x032b:
        r0 = r86;
        r0 = r0.parRelativeTolerance;
        r80 = r0;
        r80 = r80 * r76;
        r4 = (r6 > r80 ? 1 : (r6 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x0355;
    L_0x0337:
        r4 = new org.apache.commons.math4.fitting.leastsquares.OptimumImpl;
        r80 = r36.getCount();
        r81 = r39.getCount();
        r0 = r24;
        r1 = r80;
        r2 = r81;
        r4.<init>(r0, r1, r2);
        goto L_0x00e2;
    L_0x034c:
        r54 = r51[r40];
        r80 = r50[r54];
        r25[r54] = r80;
        r40 = r40 + 1;
        goto L_0x0301;
    L_0x0355:
        r80 = org.apache.commons.math4.util.FastMath.abs(r18);
        r82 = TWO_EPS;
        r4 = (r80 > r82 ? 1 : (r80 == r82 ? 0 : -1));
        if (r4 > 0) goto L_0x038d;
    L_0x035f:
        r80 = TWO_EPS;
        r4 = (r56 > r80 ? 1 : (r56 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x038d;
    L_0x0365:
        r80 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r4 = (r64 > r80 ? 1 : (r64 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x038d;
    L_0x036b:
        r4 = new org.apache.commons.math4.exception.ConvergenceException;
        r80 = org.apache.commons.math4.exception.util.LocalizedFormats.TOO_SMALL_COST_RELATIVE_TOLERANCE;
        r81 = 1;
        r0 = r81;
        r0 = new java.lang.Object[r0];
        r81 = r0;
        r82 = 0;
        r0 = r86;
        r0 = r0.costRelativeTolerance;
        r84 = r0;
        r83 = java.lang.Double.valueOf(r84);
        r81[r82] = r83;
        r0 = r80;
        r1 = r81;
        r4.<init>(r0, r1);
        throw r4;
    L_0x038d:
        r80 = TWO_EPS;
        r80 = r80 * r76;
        r4 = (r6 > r80 ? 1 : (r6 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x03b7;
    L_0x0395:
        r4 = new org.apache.commons.math4.exception.ConvergenceException;
        r80 = org.apache.commons.math4.exception.util.LocalizedFormats.TOO_SMALL_PARAMETERS_RELATIVE_TOLERANCE;
        r81 = 1;
        r0 = r81;
        r0 = new java.lang.Object[r0];
        r81 = r0;
        r82 = 0;
        r0 = r86;
        r0 = r0.parRelativeTolerance;
        r84 = r0;
        r83 = java.lang.Double.valueOf(r84);
        r81[r82] = r83;
        r0 = r80;
        r1 = r81;
        r4.<init>(r0, r1);
        throw r4;
    L_0x03b7:
        r80 = TWO_EPS;
        r4 = (r46 > r80 ? 1 : (r46 == r80 ? 0 : -1));
        if (r4 > 0) goto L_0x0153;
    L_0x03bd:
        r4 = new org.apache.commons.math4.exception.ConvergenceException;
        r80 = org.apache.commons.math4.exception.util.LocalizedFormats.TOO_SMALL_ORTHOGONALITY_TOLERANCE;
        r81 = 1;
        r0 = r81;
        r0 = new java.lang.Object[r0];
        r81 = r0;
        r82 = 0;
        r0 = r86;
        r0 = r0.orthoTolerance;
        r84 = r0;
        r83 = java.lang.Double.valueOf(r84);
        r81[r82] = r83;
        r0 = r80;
        r1 = r81;
        r4.<init>(r0, r1);
        throw r4;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.fitting.leastsquares.LevenbergMarquardtOptimizer.optimize(org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem):org.apache.commons.math4.fitting.leastsquares.LeastSquaresOptimizer$Optimum");
    }

    private double determineLMParameter(double[] qy, double delta, double[] diag, InternalData internalData, int solvedCols, double[] work1, double[] work2, double[] work3, double[] lmDir, double lmPar) {
        int j;
        int i;
        double[][] weightedJacobian = internalData.weightedJacobian;
        int[] permutation = internalData.permutation;
        int rank = internalData.rank;
        double[] diagR = internalData.diagR;
        int nC = weightedJacobian[0].length;
        for (j = 0; j < rank; j++) {
            lmDir[permutation[j]] = qy[j];
        }
        for (j = rank; j < nC; j++) {
            lmDir[permutation[j]] = 0.0d;
        }
        for (int k = rank - 1; k >= 0; k--) {
            int pk = permutation[k];
            double ypk = lmDir[pk] / diagR[pk];
            for (i = 0; i < k; i++) {
                int i2 = permutation[i];
                lmDir[i2] = lmDir[i2] - (weightedJacobian[i][pk] * ypk);
            }
            lmDir[pk] = ypk;
        }
        double dxNorm = 0.0d;
        for (j = 0; j < solvedCols; j++) {
            int pj = permutation[j];
            double s = diag[pj] * lmDir[pj];
            work1[pj] = s;
            dxNorm += s * s;
        }
        dxNorm = FastMath.sqrt(dxNorm);
        double fp = dxNorm - delta;
        if (fp <= 0.1d * delta) {
            return 0.0d;
        }
        double sum2;
        double sum;
        double parl = 0.0d;
        if (rank == solvedCols) {
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                work1[pj] = work1[pj] * (diag[pj] / dxNorm);
            }
            sum2 = 0.0d;
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                sum = 0.0d;
                for (i = 0; i < j; i++) {
                    sum += weightedJacobian[i][pj] * work1[permutation[i]];
                }
                s = (work1[pj] - sum) / diagR[pj];
                work1[pj] = s;
                sum2 += s * s;
            }
            parl = fp / (delta * sum2);
        }
        sum2 = 0.0d;
        for (j = 0; j < solvedCols; j++) {
            pj = permutation[j];
            sum = 0.0d;
            for (i = 0; i <= j; i++) {
                sum += weightedJacobian[i][pj] * qy[i];
            }
            sum /= diag[pj];
            sum2 += sum * sum;
        }
        double gNorm = FastMath.sqrt(sum2);
        double paru = gNorm / delta;
        if (paru == 0.0d) {
            paru = Precision.SAFE_MIN / FastMath.min(delta, 0.1d);
        }
        lmPar = FastMath.min(paru, FastMath.max(lmPar, parl));
        if (lmPar == 0.0d) {
            lmPar = gNorm / dxNorm;
        }
        for (int countdown = 10; countdown >= 0; countdown--) {
            if (lmPar == 0.0d) {
                lmPar = FastMath.max(Precision.SAFE_MIN, 0.001d * paru);
            }
            double sPar = FastMath.sqrt(lmPar);
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                work1[pj] = diag[pj] * sPar;
            }
            determineLMDirection(qy, work1, work2, internalData, solvedCols, work3, lmDir);
            dxNorm = 0.0d;
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                s = diag[pj] * lmDir[pj];
                work3[pj] = s;
                dxNorm += s * s;
            }
            dxNorm = FastMath.sqrt(dxNorm);
            double previousFP = fp;
            fp = dxNorm - delta;
            if (FastMath.abs(fp) <= 0.1d * delta || (parl == 0.0d && fp <= previousFP && previousFP < 0.0d)) {
                return lmPar;
            }
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                work1[pj] = (work3[pj] * diag[pj]) / dxNorm;
            }
            for (j = 0; j < solvedCols; j++) {
                pj = permutation[j];
                work1[pj] = work1[pj] / work2[j];
                double tmp = work1[pj];
                for (i = j + 1; i < solvedCols; i++) {
                    i2 = permutation[i];
                    work1[i2] = work1[i2] - (weightedJacobian[i][pj] * tmp);
                }
            }
            sum2 = 0.0d;
            for (j = 0; j < solvedCols; j++) {
                s = work1[permutation[j]];
                sum2 += s * s;
            }
            double correction = fp / (delta * sum2);
            if (fp > 0.0d) {
                parl = FastMath.max(parl, lmPar);
            } else if (fp < 0.0d) {
                paru = FastMath.min(paru, lmPar);
            }
            lmPar = FastMath.max(parl, lmPar + correction);
        }
        return lmPar;
    }

    private void determineLMDirection(double[] qy, double[] diag, double[] lmDiag, InternalData internalData, int solvedCols, double[] work, double[] lmDir) {
        int j;
        int i;
        int[] permutation = internalData.permutation;
        double[][] weightedJacobian = internalData.weightedJacobian;
        double[] diagR = internalData.diagR;
        for (j = 0; j < solvedCols; j++) {
            int pj = permutation[j];
            for (i = j + 1; i < solvedCols; i++) {
                weightedJacobian[i][pj] = weightedJacobian[j][permutation[i]];
            }
            lmDir[j] = diagR[pj];
            work[j] = qy[j];
        }
        for (j = 0; j < solvedCols; j++) {
            double dpj = diag[permutation[j]];
            if (dpj != 0.0d) {
                Arrays.fill(lmDiag, j + 1, lmDiag.length, 0.0d);
            }
            lmDiag[j] = dpj;
            double qtbpj = 0.0d;
            for (int k = j; k < solvedCols; k++) {
                int pk = permutation[k];
                if (lmDiag[k] != 0.0d) {
                    double sin;
                    double cos;
                    double rkk = weightedJacobian[k][pk];
                    if (FastMath.abs(rkk) < FastMath.abs(lmDiag[k])) {
                        double cotan = rkk / lmDiag[k];
                        sin = 1.0d / FastMath.sqrt(1.0d + (cotan * cotan));
                        cos = sin * cotan;
                    } else {
                        double tan = lmDiag[k] / rkk;
                        cos = 1.0d / FastMath.sqrt(1.0d + (tan * tan));
                        sin = cos * tan;
                    }
                    weightedJacobian[k][pk] = (cos * rkk) + (lmDiag[k] * sin);
                    double temp = (work[k] * cos) + (sin * qtbpj);
                    qtbpj = ((-sin) * work[k]) + (cos * qtbpj);
                    work[k] = temp;
                    for (i = k + 1; i < solvedCols; i++) {
                        double rik = weightedJacobian[i][pk];
                        double temp2 = (cos * rik) + (lmDiag[i] * sin);
                        lmDiag[i] = ((-sin) * rik) + (lmDiag[i] * cos);
                        weightedJacobian[i][pk] = temp2;
                    }
                }
            }
            lmDiag[j] = weightedJacobian[j][permutation[j]];
            weightedJacobian[j][permutation[j]] = lmDir[j];
        }
        int nSing = solvedCols;
        for (j = 0; j < solvedCols; j++) {
            if (lmDiag[j] == 0.0d && nSing == solvedCols) {
                nSing = j;
            }
            if (nSing < solvedCols) {
                work[j] = 0.0d;
            }
        }
        if (nSing > 0) {
            for (j = nSing - 1; j >= 0; j--) {
                pj = permutation[j];
                double sum = 0.0d;
                for (i = j + 1; i < nSing; i++) {
                    sum += weightedJacobian[i][pj] * work[i];
                }
                work[j] = (work[j] - sum) / lmDiag[j];
            }
        }
        j = 0;
        while (true) {
            int length = lmDir.length;
            if (j < r0) {
                lmDir[permutation[j]] = work[j];
                j++;
            } else {
                return;
            }
        }
    }

    private InternalData qrDecomposition(RealMatrix jacobian, int solvedCols) throws ConvergenceException {
        int k;
        int i;
        double[] dArr;
        double[][] weightedJacobian = jacobian.scalarMultiply(-1.0d).getData();
        int nC = weightedJacobian[0].length;
        int[] permutation = new int[nC];
        double[] diagR = new double[nC];
        double[] jacNorm = new double[nC];
        double[] beta = new double[nC];
        for (k = 0; k < nC; k++) {
            permutation[k] = k;
            double norm2 = 0.0d;
            for (double[] dArr2 : weightedJacobian) {
                double akk = dArr2[k];
                norm2 += akk * akk;
            }
            jacNorm[k] = FastMath.sqrt(norm2);
        }
        for (k = 0; k < nC; k++) {
            int nextColumn = -1;
            double ak2 = Double.NEGATIVE_INFINITY;
            for (i = k; i < nC; i++) {
                int j;
                norm2 = 0.0d;
                for (j = k; j < nR; j++) {
                    double aki = weightedJacobian[j][permutation[i]];
                    norm2 += aki * aki;
                }
                if (Double.isInfinite(norm2) || Double.isNaN(norm2)) {
                    throw new ConvergenceException(LocalizedFormats.UNABLE_TO_PERFORM_QR_DECOMPOSITION_ON_JACOBIAN, Integer.valueOf(nR), Integer.valueOf(nC));
                }
                if (norm2 > ak2) {
                    nextColumn = i;
                    ak2 = norm2;
                }
            }
            if (ak2 <= this.qrRankingThreshold) {
                return new InternalData(weightedJacobian, permutation, k, diagR, jacNorm, beta);
            }
            int pk = permutation[nextColumn];
            permutation[nextColumn] = permutation[k];
            permutation[k] = pk;
            akk = weightedJacobian[k][pk];
            double alpha = akk > 0.0d ? -FastMath.sqrt(ak2) : FastMath.sqrt(ak2);
            double betak = 1.0d / (ak2 - (akk * alpha));
            beta[pk] = betak;
            diagR[pk] = alpha;
            dArr2 = weightedJacobian[k];
            dArr2[pk] = dArr2[pk] - alpha;
            for (int dk = (nC - 1) - k; dk > 0; dk--) {
                double gamma = 0.0d;
                for (j = k; j < nR; j++) {
                    gamma += weightedJacobian[j][pk] * weightedJacobian[j][permutation[k + dk]];
                }
                gamma *= betak;
                for (j = k; j < nR; j++) {
                    dArr2 = weightedJacobian[j];
                    int i2 = permutation[k + dk];
                    dArr2[i2] = dArr2[i2] - (weightedJacobian[j][pk] * gamma);
                }
            }
        }
        return new InternalData(weightedJacobian, permutation, solvedCols, diagR, jacNorm, beta);
    }

    private void qTy(double[] y, InternalData internalData) {
        double[][] weightedJacobian = internalData.weightedJacobian;
        int[] permutation = internalData.permutation;
        double[] beta = internalData.beta;
        int nR = weightedJacobian.length;
        int nC = weightedJacobian[0].length;
        for (int k = 0; k < nC; k++) {
            int i;
            int pk = permutation[k];
            double gamma = 0.0d;
            for (i = k; i < nR; i++) {
                gamma += weightedJacobian[i][pk] * y[i];
            }
            gamma *= beta[pk];
            for (i = k; i < nR; i++) {
                y[i] = y[i] - (weightedJacobian[i][pk] * gamma);
            }
        }
    }
}
