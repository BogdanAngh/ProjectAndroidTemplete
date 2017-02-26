package org.apache.commons.math4.optim.nonlinear.scalar.noderiv;

import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math4.util.FastMath;

public class BOBYQAOptimizer extends MultivariateOptimizer {
    public static final double DEFAULT_INITIAL_RADIUS = 10.0d;
    public static final double DEFAULT_STOPPING_RADIUS = 1.0E-8d;
    private static final double HALF = 0.5d;
    public static final int MINIMUM_PROBLEM_DIMENSION = 2;
    private static final double MINUS_ONE = -1.0d;
    private static final double ONE = 1.0d;
    private static final double ONE_OVER_A_THOUSAND = 0.001d;
    private static final double ONE_OVER_EIGHT = 0.125d;
    private static final double ONE_OVER_FOUR = 0.25d;
    private static final double ONE_OVER_TEN = 0.1d;
    private static final double SIXTEEN = 16.0d;
    private static final double TEN = 10.0d;
    private static final double TWO = 2.0d;
    private static final double TWO_HUNDRED_FIFTY = 250.0d;
    private static final double ZERO = 0.0d;
    private ArrayRealVector alternativeNewPoint;
    private Array2DRowRealMatrix bMatrix;
    private double[] boundDifference;
    private ArrayRealVector currentBest;
    private ArrayRealVector fAtInterpolationPoints;
    private ArrayRealVector gradientAtTrustRegionCenter;
    private double initialTrustRegionRadius;
    private Array2DRowRealMatrix interpolationPoints;
    private boolean isMinimize;
    private ArrayRealVector lagrangeValuesAtNewPoint;
    private ArrayRealVector lowerDifference;
    private ArrayRealVector modelSecondDerivativesParameters;
    private ArrayRealVector modelSecondDerivativesValues;
    private ArrayRealVector newPoint;
    private final int numberOfInterpolationPoints;
    private ArrayRealVector originShift;
    private final double stoppingTrustRegionRadius;
    private ArrayRealVector trialStepPoint;
    private int trustRegionCenterInterpolationPointIndex;
    private ArrayRealVector trustRegionCenterOffset;
    private ArrayRealVector upperDifference;
    private Array2DRowRealMatrix zMatrix;

    private static class PathIsExploredException extends RuntimeException {
        private static final String PATH_IS_EXPLORED = "If this exception is thrown, just remove it from the code";
        private static final long serialVersionUID = 745350979634801853L;

        PathIsExploredException() {
            super("If this exception is thrown, just remove it from the code " + BOBYQAOptimizer.caller(3));
        }
    }

    public BOBYQAOptimizer(int numberOfInterpolationPoints) {
        this(numberOfInterpolationPoints, TEN, DEFAULT_STOPPING_RADIUS);
    }

    public BOBYQAOptimizer(int numberOfInterpolationPoints, double initialTrustRegionRadius, double stoppingTrustRegionRadius) {
        super(null);
        this.numberOfInterpolationPoints = numberOfInterpolationPoints;
        this.initialTrustRegionRadius = initialTrustRegionRadius;
        this.stoppingTrustRegionRadius = stoppingTrustRegionRadius;
    }

    protected PointValuePair doOptimize() {
        double[] lowerBound = getLowerBound();
        double[] upperBound = getUpperBound();
        setup(lowerBound, upperBound);
        this.isMinimize = getGoalType() == GoalType.MINIMIZE;
        this.currentBest = new ArrayRealVector(getStartPoint());
        double value = bobyqa(lowerBound, upperBound);
        double[] dataRef = this.currentBest.getDataRef();
        if (!this.isMinimize) {
            value = -value;
        }
        return new PointValuePair(dataRef, value);
    }

    private double bobyqa(double[] lowerBound, double[] upperBound) {
        printMethod();
        int n = this.currentBest.getDimension();
        for (int j = 0; j < n; j++) {
            double boundDiff = this.boundDifference[j];
            this.lowerDifference.setEntry(j, lowerBound[j] - this.currentBest.getEntry(j));
            this.upperDifference.setEntry(j, upperBound[j] - this.currentBest.getEntry(j));
            if (this.lowerDifference.getEntry(j) >= (-this.initialTrustRegionRadius)) {
                if (this.lowerDifference.getEntry(j) >= 0.0d) {
                    this.currentBest.setEntry(j, lowerBound[j]);
                    this.lowerDifference.setEntry(j, 0.0d);
                    this.upperDifference.setEntry(j, boundDiff);
                } else {
                    this.currentBest.setEntry(j, lowerBound[j] + this.initialTrustRegionRadius);
                    this.lowerDifference.setEntry(j, -this.initialTrustRegionRadius);
                    this.upperDifference.setEntry(j, FastMath.max(upperBound[j] - this.currentBest.getEntry(j), this.initialTrustRegionRadius));
                }
            } else if (this.upperDifference.getEntry(j) <= this.initialTrustRegionRadius) {
                if (this.upperDifference.getEntry(j) <= 0.0d) {
                    this.currentBest.setEntry(j, upperBound[j]);
                    this.lowerDifference.setEntry(j, -boundDiff);
                    this.upperDifference.setEntry(j, 0.0d);
                } else {
                    this.currentBest.setEntry(j, upperBound[j] - this.initialTrustRegionRadius);
                    this.lowerDifference.setEntry(j, FastMath.min(lowerBound[j] - this.currentBest.getEntry(j), -this.initialTrustRegionRadius));
                    this.upperDifference.setEntry(j, this.initialTrustRegionRadius);
                }
            }
        }
        return bobyqb(lowerBound, upperBound);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private double bobyqb(double[] r151, double[] r152) {
        /*
        r150 = this;
        printMethod();
        r0 = r150;
        r5 = r0.currentBest;
        r107 = r5.getDimension();
        r0 = r150;
        r0 = r0.numberOfInterpolationPoints;
        r111 = r0;
        r110 = r107 + 1;
        r112 = r111 - r110;
        r5 = r107 * r110;
        r109 = r5 / 2;
        r123 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r123;
        r1 = r107;
        r0.<init>(r1);
        r140 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r140;
        r1 = r111;
        r0.<init>(r1);
        r141 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r141;
        r1 = r111;
        r0.<init>(r1);
        r32 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r22 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r72 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r34 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r5 = 0;
        r0 = r150;
        r0.trustRegionCenterInterpolationPointIndex = r5;
        r150.prelim(r151, r152);
        r142 = 0;
        r96 = 0;
    L_0x0048:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x00a0;
    L_0x004e:
        r0 = r150;
        r5 = r0.fAtInterpolationPoints;
        r13 = 0;
        r86 = r5.getEntry(r13);
        r103 = 0;
        r113 = 0;
        r99 = 0;
        r18 = 0;
        r108 = r150.getEvaluations();
        r0 = r150;
        r0 = r0.initialTrustRegionRadius;
        r118 = r0;
        r6 = r118;
        r60 = 0;
        r62 = 0;
        r64 = 0;
        r78 = 0;
        r14 = 0;
        r20 = 0;
        r16 = 0;
        r116 = 0;
        r70 = 0;
        r120 = 0;
        r28 = 0;
        r68 = 0;
        r122 = 20;
    L_0x0085:
        switch(r122) {
            case 20: goto L_0x00cf;
            case 60: goto L_0x00f4;
            case 90: goto L_0x02ea;
            case 210: goto L_0x069e;
            case 230: goto L_0x06bb;
            case 360: goto L_0x0930;
            case 650: goto L_0x115c;
            case 680: goto L_0x1201;
            case 720: goto L_0x125f;
            default: goto L_0x0088;
        };
    L_0x0088:
        r5 = new org.apache.commons.math4.exception.MathIllegalStateException;
        r13 = org.apache.commons.math4.exception.util.LocalizedFormats.SIMPLE_MESSAGE;
        r144 = 1;
        r0 = r144;
        r0 = new java.lang.Object[r0];
        r144 = r0;
        r145 = 0;
        r146 = "bobyqb";
        r144[r145] = r146;
        r0 = r144;
        r5.<init>(r13, r0);
        throw r5;
    L_0x00a0:
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r150;
        r0 = r0.trustRegionCenterInterpolationPointIndex;
        r144 = r0;
        r0 = r144;
        r1 = r96;
        r144 = r13.getEntry(r0, r1);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r50 = r5.getEntry(r0);
        r144 = r50 * r50;
        r142 = r142 + r144;
        r96 = r96 + 1;
        goto L_0x0048;
    L_0x00cf:
        r5 = 20;
        printState(r5);
        r0 = r150;
        r5 = r0.trustRegionCenterInterpolationPointIndex;
        if (r5 == 0) goto L_0x00f4;
    L_0x00da:
        r97 = 0;
        r100 = 0;
    L_0x00de:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x014e;
    L_0x00e4:
        r5 = r150.getEvaluations();
        r0 = r111;
        if (r5 <= r0) goto L_0x00f4;
    L_0x00ec:
        r102 = 0;
    L_0x00ee:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x01be;
    L_0x00f4:
        r5 = 60;
        printState(r5);
        r8 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r107;
        r8.<init>(r0);
        r9 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r107;
        r9.<init>(r0);
        r10 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r107;
        r10.<init>(r0);
        r11 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r107;
        r11.<init>(r0);
        r12 = new org.apache.commons.math4.linear.ArrayRealVector;
        r0 = r107;
        r12.<init>(r0);
        r5 = r150;
        r19 = r5.trsbox(r6, r8, r9, r10, r11, r12);
        r5 = 0;
        r72 = r19[r5];
        r5 = 1;
        r34 = r19[r5];
        r50 = r6;
        r52 = org.apache.commons.math4.util.FastMath.sqrt(r72);
        r70 = org.apache.commons.math4.util.FastMath.min(r50, r52);
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r144 = r144 * r118;
        r5 = (r70 > r144 ? 1 : (r70 == r144 ? 0 : -1));
        if (r5 >= 0) goto L_0x02e8;
    L_0x013a:
        r113 = -1;
        r144 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        r50 = r144 * r118;
        r68 = r50 * r50;
        r5 = r150.getEvaluations();
        r13 = r108 + 2;
        if (r5 > r13) goto L_0x0225;
    L_0x014a:
        r122 = 650; // 0x28a float:9.11E-43 double:3.21E-321;
        goto L_0x0085;
    L_0x014e:
        r96 = 0;
    L_0x0150:
        r0 = r96;
        r1 = r100;
        if (r0 <= r1) goto L_0x0159;
    L_0x0156:
        r100 = r100 + 1;
        goto L_0x00de;
    L_0x0159:
        r0 = r96;
        r1 = r100;
        if (r0 >= r1) goto L_0x018c;
    L_0x015f:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r100;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r146 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r100;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x018c:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r146 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r100;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r97 = r97 + 1;
        r96 = r96 + 1;
        goto L_0x0150;
    L_0x01be:
        r136 = 0;
        r100 = 0;
    L_0x01c2:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x01e0;
    L_0x01c8:
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r136 = r136 * r144;
        r96 = 0;
    L_0x01d6:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x01fd;
    L_0x01dc:
        r102 = r102 + 1;
        goto L_0x00ee;
    L_0x01e0:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r136 = r136 + r144;
        r100 = r100 + 1;
        goto L_0x01c2;
    L_0x01fd:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x01d6;
    L_0x0225:
        r50 = org.apache.commons.math4.util.FastMath.max(r60, r62);
        r0 = r50;
        r2 = r64;
        r76 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r144 = 4593671619917905920; // 0x3fc0000000000000 float:0.0 double:0.125;
        r144 = r144 * r118;
        r84 = r144 * r118;
        r144 = 0;
        r5 = (r34 > r144 ? 1 : (r34 == r144 ? 0 : -1));
        if (r5 <= 0) goto L_0x0247;
    L_0x023d:
        r144 = r84 * r34;
        r5 = (r76 > r144 ? 1 : (r76 == r144 ? 0 : -1));
        if (r5 <= 0) goto L_0x0247;
    L_0x0243:
        r122 = 650; // 0x28a float:9.11E-43 double:3.21E-321;
        goto L_0x0085;
    L_0x0247:
        r26 = r76 / r118;
        r100 = 0;
    L_0x024b:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0255;
    L_0x0251:
        r122 = 680; // 0x2a8 float:9.53E-43 double:3.36E-321;
        goto L_0x0085;
    L_0x0255:
        r24 = r26;
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.lowerDifference;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x0277;
    L_0x026f:
        r0 = r123;
        r1 = r100;
        r24 = r0.getEntry(r1);
    L_0x0277:
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.upperDifference;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x029c;
    L_0x028f:
        r0 = r123;
        r1 = r100;
        r144 = r0.getEntry(r1);
        r0 = r144;
        r0 = -r0;
        r24 = r0;
    L_0x029c:
        r5 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x02e4;
    L_0x02a0:
        r0 = r150;
        r5 = r0.modelSecondDerivativesValues;
        r13 = r100 * r100;
        r13 = r13 + r100;
        r13 = r13 / 2;
        r36 = r5.getEntry(r13);
        r102 = 0;
    L_0x02b0:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x02c5;
    L_0x02b6:
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r144 = r144 * r36;
        r144 = r144 * r118;
        r24 = r24 + r144;
        r5 = (r24 > r26 ? 1 : (r24 == r26 ? 0 : -1));
        if (r5 >= 0) goto L_0x02e4;
    L_0x02c2:
        r122 = 650; // 0x28a float:9.11E-43 double:3.21E-321;
        goto L_0x0251;
    L_0x02c5:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r38 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r146 = r38 * r38;
        r144 = r144 * r146;
        r36 = r36 + r144;
        r102 = r102 + 1;
        goto L_0x02b0;
    L_0x02e4:
        r100 = r100 + 1;
        goto L_0x024b;
    L_0x02e8:
        r113 = r113 + 1;
    L_0x02ea:
        r5 = 90;
        printState(r5);
        r144 = 4562254508917369340; // 0x3f50624dd2f1a9fc float:-5.18969491E11 double:0.001;
        r144 = r144 * r142;
        r5 = (r72 > r144 ? 1 : (r72 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x0324;
    L_0x02fa:
        r144 = 4598175219545276416; // 0x3fd0000000000000 float:0.0 double:0.25;
        r82 = r142 * r144;
        r130 = 0;
        r102 = 0;
    L_0x0302:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x032a;
    L_0x0308:
        r105 = 0;
    L_0x030a:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x040a;
    L_0x0310:
        r97 = 0;
        r100 = 0;
    L_0x0314:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x051f;
    L_0x031a:
        r96 = 0;
    L_0x031c:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0605;
    L_0x0322:
        r142 = 0;
    L_0x0324:
        if (r113 != 0) goto L_0x069a;
    L_0x0326:
        r122 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        goto L_0x0085;
    L_0x032a:
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r130 = r130 + r144;
        r144 = -4620693217682128896; // 0xbfe0000000000000 float:0.0 double:-0.5;
        r124 = r144 * r142;
        r96 = 0;
    L_0x033c:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x035c;
    L_0x0342:
        r0 = r140;
        r1 = r102;
        r2 = r124;
        r0.setEntry(r1, r2);
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r144 = r144 * r124;
        r136 = r82 - r144;
        r96 = 0;
    L_0x0353:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0379;
    L_0x0359:
        r102 = r102 + 1;
        goto L_0x0302;
    L_0x035c:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r96 = r96 + 1;
        goto L_0x033c;
    L_0x0379:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r102;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r0 = r123;
        r1 = r96;
        r2 = r144;
        r0.setEntry(r1, r2);
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r144 = r13.getEntry(r0, r1);
        r144 = r144 * r124;
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r98 = r111 + r96;
        r100 = 0;
    L_0x03b9:
        r0 = r100;
        r1 = r96;
        if (r0 <= r1) goto L_0x03c2;
    L_0x03bf:
        r96 = r96 + 1;
        goto L_0x0353;
    L_0x03c2:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r150;
        r13 = r0.bMatrix;
        r0 = r98;
        r1 = r100;
        r144 = r13.getEntry(r0, r1);
        r0 = r123;
        r1 = r96;
        r146 = r0.getEntry(r1);
        r0 = r150;
        r13 = r0.lagrangeValuesAtNewPoint;
        r0 = r100;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r150;
        r13 = r0.lagrangeValuesAtNewPoint;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r0 = r123;
        r1 = r100;
        r148 = r0.getEntry(r1);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r98;
        r1 = r100;
        r2 = r144;
        r5.setEntry(r0, r1, r2);
        r100 = r100 + 1;
        goto L_0x03b9;
    L_0x040a:
        r134 = 0;
        r132 = 0;
        r102 = 0;
    L_0x0410:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x042a;
    L_0x0416:
        r100 = 0;
    L_0x0418:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0468;
    L_0x041e:
        r96 = 0;
    L_0x0420:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x04e1;
    L_0x0426:
        r105 = r105 + 1;
        goto L_0x030a;
    L_0x042a:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r144 = r5.getEntry(r0, r1);
        r134 = r134 + r144;
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r140;
        r1 = r102;
        r144 = r0.getEntry(r1);
        r0 = r150;
        r13 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r146 = r13.getEntry(r0, r1);
        r144 = r144 * r146;
        r0 = r102;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r132 = r132 + r144;
        r102 = r102 + 1;
        goto L_0x0410;
    L_0x0468:
        r144 = r82 * r134;
        r146 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r146 = r146 * r132;
        r144 = r144 - r146;
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r124 = r144 * r146;
        r102 = 0;
    L_0x047e:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0498;
    L_0x0484:
        r0 = r123;
        r1 = r100;
        r2 = r124;
        r0.setEntry(r1, r2);
        r102 = 0;
    L_0x048f:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x04b5;
    L_0x0495:
        r100 = r100 + 1;
        goto L_0x0418;
    L_0x0498:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r146 = r5.getEntry(r0, r1);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r102 = r102 + 1;
        goto L_0x047e;
    L_0x04b5:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r150;
        r13 = r0.bMatrix;
        r0 = r102;
        r1 = r100;
        r144 = r13.getEntry(r0, r1);
        r0 = r150;
        r13 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r124;
        r144 = r144 + r146;
        r0 = r102;
        r1 = r100;
        r2 = r144;
        r5.setEntry(r0, r1, r2);
        r102 = r102 + 1;
        goto L_0x048f;
    L_0x04e1:
        r98 = r96 + r111;
        r0 = r123;
        r1 = r96;
        r136 = r0.getEntry(r1);
        r100 = 0;
    L_0x04ed:
        r0 = r100;
        r1 = r96;
        if (r0 <= r1) goto L_0x04f7;
    L_0x04f3:
        r96 = r96 + 1;
        goto L_0x0420;
    L_0x04f7:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r150;
        r13 = r0.bMatrix;
        r0 = r98;
        r1 = r100;
        r144 = r13.getEntry(r0, r1);
        r0 = r123;
        r1 = r100;
        r146 = r0.getEntry(r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r98;
        r1 = r100;
        r2 = r144;
        r5.setEntry(r0, r1, r2);
        r100 = r100 + 1;
        goto L_0x04ed;
    L_0x051f:
        r144 = -4620693217682128896; // 0xbfe0000000000000 float:0.0 double:-0.5;
        r144 = r144 * r130;
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r0 = r123;
        r1 = r100;
        r2 = r144;
        r0.setEntry(r1, r2);
        r102 = 0;
    L_0x053a:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x054c;
    L_0x0540:
        r96 = 0;
    L_0x0542:
        r0 = r96;
        r1 = r100;
        if (r0 <= r1) goto L_0x059f;
    L_0x0548:
        r100 = r100 + 1;
        goto L_0x0314;
    L_0x054c:
        r0 = r123;
        r1 = r100;
        r144 = r0.getEntry(r1);
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r146 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r148 = r5.getEntry(r0, r1);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r123;
        r1 = r100;
        r2 = r144;
        r0.setEntry(r1, r2);
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r13.getEntry(r0, r1);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r102;
        r1 = r100;
        r2 = r144;
        r5.setEntry(r0, r1, r2);
        r102 = r102 + 1;
        goto L_0x053a;
    L_0x059f:
        r0 = r150;
        r5 = r0.modelSecondDerivativesValues;
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r144 = r13.getEntry(r0);
        r0 = r123;
        r1 = r96;
        r146 = r0.getEntry(r1);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r100;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r0 = r123;
        r1 = r100;
        r148 = r0.getEntry(r1);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r97;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.bMatrix;
        r13 = r111 + r96;
        r0 = r150;
        r0 = r0.bMatrix;
        r144 = r0;
        r145 = r111 + r100;
        r0 = r144;
        r1 = r145;
        r2 = r96;
        r144 = r0.getEntry(r1, r2);
        r0 = r100;
        r1 = r144;
        r5.setEntry(r13, r0, r1);
        r97 = r97 + 1;
        r96 = r96 + 1;
        goto L_0x0542;
    L_0x0605:
        r0 = r150;
        r5 = r0.originShift;
        r0 = r150;
        r13 = r0.originShift;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r150;
        r13 = r0.newPoint;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.lowerDifference;
        r0 = r150;
        r13 = r0.lowerDifference;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.upperDifference;
        r0 = r150;
        r13 = r0.upperDifference;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r144 = 0;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x031c;
    L_0x069a:
        r122 = 230; // 0xe6 float:3.22E-43 double:1.136E-321;
        goto L_0x0085;
    L_0x069e:
        r5 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        printState(r5);
        r0 = r150;
        r1 = r18;
        r2 = r20;
        r4 = r0.altmov(r1, r2);
        r5 = 0;
        r22 = r4[r5];
        r5 = 1;
        r32 = r4[r5];
        r96 = 0;
    L_0x06b5:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x073f;
    L_0x06bb:
        r5 = 230; // 0xe6 float:3.22E-43 double:1.136E-321;
        printState(r5);
        r102 = 0;
    L_0x06c2:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0764;
    L_0x06c8:
        r14 = 0;
        r105 = 0;
    L_0x06cc:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x07ec;
    L_0x06d2:
        r72 = 0;
        r30 = 0;
        r74 = 0;
        r100 = 0;
    L_0x06da:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0849;
    L_0x06e0:
        r144 = r74 * r74;
        r146 = r142 + r74;
        r146 = r146 + r74;
        r148 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r148 = r148 * r72;
        r146 = r146 + r148;
        r146 = r146 * r72;
        r144 = r144 + r146;
        r144 = r144 + r14;
        r14 = r144 - r30;
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r150;
        r13 = r0.trustRegionCenterInterpolationPointIndex;
        r0 = r150;
        r0 = r0.lagrangeValuesAtNewPoint;
        r144 = r0;
        r0 = r150;
        r0 = r0.trustRegionCenterInterpolationPointIndex;
        r145 = r0;
        r144 = r144.getEntry(r145);
        r146 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r144 = r144 + r146;
        r0 = r144;
        r5.setEntry(r13, r0);
        if (r113 != 0) goto L_0x0920;
    L_0x0717:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r18;
        r38 = r5.getEntry(r0);
        r144 = r38 * r38;
        r146 = r22 * r14;
        r16 = r144 + r146;
        r5 = (r16 > r32 ? 1 : (r16 == r32 ? 0 : -1));
        if (r5 >= 0) goto L_0x0930;
    L_0x072b:
        r144 = 0;
        r5 = (r32 > r144 ? 1 : (r32 == r144 ? 0 : -1));
        if (r5 <= 0) goto L_0x0930;
    L_0x0731:
        r96 = 0;
    L_0x0733:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x08e6;
    L_0x0739:
        r32 = 0;
        r122 = 230; // 0xe6 float:3.22E-43 double:1.136E-321;
        goto L_0x0085;
    L_0x073f:
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r150;
        r13 = r0.newPoint;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x06b5;
    L_0x0764:
        r126 = 0;
        r128 = 0;
        r124 = 0;
        r100 = 0;
    L_0x076c:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x079b;
    L_0x0772:
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r144 = r144 * r126;
        r144 = r144 + r128;
        r144 = r144 * r126;
        r0 = r141;
        r1 = r102;
        r2 = r144;
        r0.setEntry(r1, r2);
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r1 = r124;
        r5.setEntry(r0, r1);
        r0 = r140;
        r1 = r102;
        r2 = r126;
        r0.setEntry(r1, r2);
        r102 = r102 + 1;
        goto L_0x06c2;
    L_0x079b:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r126 = r126 + r144;
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r128 = r128 + r144;
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r100 = r100 + 1;
        goto L_0x076c;
    L_0x07ec:
        r124 = 0;
        r102 = 0;
    L_0x07f0:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0806;
    L_0x07f6:
        r144 = r124 * r124;
        r14 = r14 - r144;
        r102 = 0;
    L_0x07fc:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0821;
    L_0x0802:
        r105 = r105 + 1;
        goto L_0x06cc;
    L_0x0806:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r144 = r5.getEntry(r0, r1);
        r0 = r141;
        r1 = r102;
        r146 = r0.getEntry(r1);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r102 = r102 + 1;
        goto L_0x07f0;
    L_0x0821:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r150;
        r13 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r124;
        r144 = r144 + r146;
        r0 = r102;
        r1 = r144;
        r5.setEntry(r0, r1);
        r102 = r102 + 1;
        goto L_0x07fc;
    L_0x0849:
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r38 = r5.getEntry(r0);
        r144 = r38 * r38;
        r72 = r72 + r144;
        r124 = 0;
        r102 = 0;
    L_0x085b:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x08ae;
    L_0x0861:
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r144 = r144 * r124;
        r30 = r30 + r144;
        r101 = r111 + r100;
        r96 = 0;
    L_0x0873:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x08c9;
    L_0x0879:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r101;
        r1 = r124;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r144 = r144 * r124;
        r30 = r30 + r144;
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r74 = r74 + r144;
        r100 = r100 + 1;
        goto L_0x06da;
    L_0x08ae:
        r0 = r141;
        r1 = r102;
        r144 = r0.getEntry(r1);
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r102;
        r1 = r100;
        r146 = r5.getEntry(r0, r1);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r102 = r102 + 1;
        goto L_0x085b;
    L_0x08c9:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r101;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r96 = r96 + 1;
        goto L_0x0873;
    L_0x08e6:
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r150;
        r13 = r0.alternativeNewPoint;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r150;
        r13 = r0.newPoint;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x0733;
    L_0x0920:
        r48 = r6 * r6;
        r120 = 0;
        r28 = 0;
        r18 = 0;
        r102 = 0;
    L_0x092a:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0961;
    L_0x0930:
        r5 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        printState(r5);
        r96 = 0;
    L_0x0937:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x09ef;
    L_0x093d:
        r0 = r150;
        r5 = r0.currentBest;
        r5 = r5.toArray();
        r0 = r150;
        r78 = r0.computeObjectiveValue(r5);
        r0 = r150;
        r5 = r0.isMinimize;
        if (r5 != 0) goto L_0x0956;
    L_0x0951:
        r0 = r78;
        r0 = -r0;
        r78 = r0;
    L_0x0956:
        r5 = -1;
        r0 = r113;
        if (r0 != r5) goto L_0x0a6a;
    L_0x095b:
        r86 = r78;
        r122 = 720; // 0x2d0 float:1.009E-42 double:3.557E-321;
        goto L_0x0085;
    L_0x0961:
        r0 = r150;
        r5 = r0.trustRegionCenterInterpolationPointIndex;
        r0 = r102;
        if (r0 != r5) goto L_0x096c;
    L_0x0969:
        r102 = r102 + 1;
        goto L_0x092a;
    L_0x096c:
        r94 = 0;
        r105 = 0;
    L_0x0970:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x09bd;
    L_0x0976:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r40 = r5.getEntry(r0);
        r144 = r14 * r94;
        r146 = r40 * r40;
        r54 = r144 + r146;
        r68 = 0;
        r100 = 0;
    L_0x098a:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x09d0;
    L_0x0990:
        r44 = r68 / r48;
        r144 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r146 = r44 * r44;
        r136 = org.apache.commons.math4.util.FastMath.max(r144, r146);
        r144 = r136 * r54;
        r5 = (r144 > r120 ? 1 : (r144 == r120 ? 0 : -1));
        if (r5 <= 0) goto L_0x09a6;
    L_0x09a0:
        r120 = r136 * r54;
        r18 = r102;
        r16 = r54;
    L_0x09a6:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r46 = r5.getEntry(r0);
        r144 = r46 * r46;
        r144 = r144 * r136;
        r0 = r28;
        r2 = r144;
        r28 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        goto L_0x0969;
    L_0x09bd:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r38 = r5.getEntry(r0, r1);
        r144 = r38 * r38;
        r94 = r94 + r144;
        r105 = r105 + 1;
        goto L_0x0970;
    L_0x09d0:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r42 = r144 - r146;
        r144 = r42 * r42;
        r68 = r68 + r144;
        r100 = r100 + 1;
        goto L_0x098a;
    L_0x09ef:
        r42 = r151[r96];
        r0 = r150;
        r5 = r0.originShift;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r44 = r144 + r146;
        r38 = org.apache.commons.math4.util.FastMath.max(r42, r44);
        r40 = r152[r96];
        r0 = r150;
        r5 = r0.currentBest;
        r144 = org.apache.commons.math4.util.FastMath.min(r38, r40);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.lowerDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x0a41;
    L_0x0a34:
        r0 = r150;
        r5 = r0.currentBest;
        r144 = r151[r96];
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x0a41:
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.upperDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x0a66;
    L_0x0a59:
        r0 = r150;
        r5 = r0.currentBest;
        r144 = r152[r96];
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x0a66:
        r96 = r96 + 1;
        goto L_0x0937;
    L_0x0a6a:
        r0 = r150;
        r5 = r0.fAtInterpolationPoints;
        r0 = r150;
        r13 = r0.trustRegionCenterInterpolationPointIndex;
        r80 = r5.getEntry(r13);
        r138 = 0;
        r97 = 0;
        r100 = 0;
    L_0x0a7c:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0ac0;
    L_0x0a82:
        r102 = 0;
    L_0x0a84:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0b16;
    L_0x0a8a:
        r144 = r78 - r80;
        r58 = r144 - r138;
        r64 = r62;
        r62 = r60;
        r60 = org.apache.commons.math4.util.FastMath.abs(r58);
        r5 = (r70 > r118 ? 1 : (r70 == r118 ? 0 : -1));
        if (r5 <= 0) goto L_0x0a9e;
    L_0x0a9a:
        r108 = r150.getEvaluations();
    L_0x0a9e:
        if (r113 <= 0) goto L_0x0b7d;
    L_0x0aa0:
        r144 = 0;
        r5 = (r138 > r144 ? 1 : (r138 == r144 ? 0 : -1));
        if (r5 < 0) goto L_0x0b36;
    L_0x0aa6:
        r5 = new org.apache.commons.math4.exception.MathIllegalStateException;
        r13 = org.apache.commons.math4.exception.util.LocalizedFormats.TRUST_REGION_STEP_FAILED;
        r144 = 1;
        r0 = r144;
        r0 = new java.lang.Object[r0];
        r144 = r0;
        r145 = 0;
        r146 = java.lang.Double.valueOf(r138);
        r144[r145] = r146;
        r0 = r144;
        r5.<init>(r13, r0);
        throw r5;
    L_0x0ac0:
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r138 = r138 + r144;
        r96 = 0;
    L_0x0ada:
        r0 = r96;
        r1 = r100;
        if (r0 <= r1) goto L_0x0ae3;
    L_0x0ae0:
        r100 = r100 + 1;
        goto L_0x0a7c;
    L_0x0ae3:
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r136 = r144 * r146;
        r0 = r96;
        r1 = r100;
        if (r0 != r1) goto L_0x0b03;
    L_0x0aff:
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r136 = r136 * r144;
    L_0x0b03:
        r0 = r150;
        r5 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r144 = r5.getEntry(r0);
        r144 = r144 * r136;
        r138 = r138 + r144;
        r97 = r97 + 1;
        r96 = r96 + 1;
        goto L_0x0ada;
    L_0x0b16:
        r0 = r140;
        r1 = r102;
        r38 = r0.getEntry(r1);
        r40 = r38 * r38;
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r144 = r144 * r40;
        r138 = r138 + r144;
        r102 = r102 + 1;
        goto L_0x0a84;
    L_0x0b36:
        r144 = r78 - r80;
        r116 = r144 / r138;
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r92 = r144 * r6;
        r144 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r5 = (r116 > r144 ? 1 : (r116 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x0c39;
    L_0x0b47:
        r0 = r92;
        r2 = r70;
        r6 = org.apache.commons.math4.util.FastMath.min(r0, r2);
    L_0x0b4f:
        r144 = 4609434218613702656; // 0x3ff8000000000000 float:0.0 double:1.5;
        r144 = r144 * r118;
        r5 = (r6 > r144 ? 1 : (r6 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x0b59;
    L_0x0b57:
        r6 = r118;
    L_0x0b59:
        r5 = (r78 > r80 ? 1 : (r78 == r80 ? 0 : -1));
        if (r5 >= 0) goto L_0x0b7d;
    L_0x0b5d:
        r104 = r18;
        r56 = r16;
        r48 = r6 * r6;
        r120 = 0;
        r28 = 0;
        r18 = 0;
        r102 = 0;
    L_0x0b6b:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0c5a;
    L_0x0b71:
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r144 = r144 * r28;
        r5 = (r120 > r144 ? 1 : (r120 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x0b7d;
    L_0x0b79:
        r18 = r104;
        r16 = r56;
    L_0x0b7d:
        r13 = r150;
        r13.update(r14, r16, r18);
        r97 = 0;
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r18;
        r114 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r144 = 0;
        r0 = r18;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = 0;
    L_0x0b9d:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0ce0;
    L_0x0ba3:
        r105 = 0;
    L_0x0ba5:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x0d24;
    L_0x0bab:
        r0 = r150;
        r5 = r0.fAtInterpolationPoints;
        r0 = r18;
        r1 = r78;
        r5.setEntry(r0, r1);
        r96 = 0;
    L_0x0bb8:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0d66;
    L_0x0bbe:
        r102 = 0;
    L_0x0bc0:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0d96;
    L_0x0bc6:
        r96 = 0;
    L_0x0bc8:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0e18;
    L_0x0bce:
        r5 = (r78 > r80 ? 1 : (r78 == r80 ? 0 : -1));
        if (r5 >= 0) goto L_0x0bec;
    L_0x0bd2:
        r0 = r18;
        r1 = r150;
        r1.trustRegionCenterInterpolationPointIndex = r0;
        r142 = 0;
        r97 = 0;
        r100 = 0;
    L_0x0bde:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0e3d;
    L_0x0be4:
        r102 = 0;
    L_0x0be6:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0ed1;
    L_0x0bec:
        if (r113 <= 0) goto L_0x0c33;
    L_0x0bee:
        r102 = 0;
    L_0x0bf0:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0f38;
    L_0x0bf6:
        r100 = 0;
    L_0x0bf8:
        r0 = r100;
        r1 = r112;
        if (r0 < r1) goto L_0x0f6e;
    L_0x0bfe:
        r102 = 0;
    L_0x0c00:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0fc5;
    L_0x0c06:
        r90 = 0;
        r88 = 0;
        r96 = 0;
    L_0x0c0c:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x1014;
    L_0x0c12:
        r99 = r99 + 1;
        r144 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        r144 = r144 * r88;
        r5 = (r90 > r144 ? 1 : (r90 == r144 ? 0 : -1));
        if (r5 >= 0) goto L_0x0c1e;
    L_0x0c1c:
        r99 = 0;
    L_0x0c1e:
        r5 = 3;
        r0 = r99;
        if (r0 < r5) goto L_0x0c33;
    L_0x0c23:
        r96 = 0;
        r0 = r111;
        r1 = r109;
        r106 = org.apache.commons.math4.util.FastMath.max(r0, r1);
    L_0x0c2d:
        r0 = r96;
        r1 = r106;
        if (r0 < r1) goto L_0x10ec;
    L_0x0c33:
        if (r113 != 0) goto L_0x113b;
    L_0x0c35:
        r122 = 60;
        goto L_0x0085;
    L_0x0c39:
        r144 = 4604480259023595110; // 0x3fe6666666666666 float:2.720083E23 double:0.7;
        r5 = (r116 > r144 ? 1 : (r116 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x0c4c;
    L_0x0c42:
        r0 = r92;
        r2 = r70;
        r6 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        goto L_0x0b4f;
    L_0x0c4c:
        r144 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r144 = r144 * r70;
        r0 = r92;
        r2 = r144;
        r6 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        goto L_0x0b4f;
    L_0x0c5a:
        r94 = 0;
        r105 = 0;
    L_0x0c5e:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x0cae;
    L_0x0c64:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r38 = r5.getEntry(r0);
        r144 = r14 * r94;
        r146 = r38 * r38;
        r54 = r144 + r146;
        r68 = 0;
        r100 = 0;
    L_0x0c78:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0cc1;
    L_0x0c7e:
        r42 = r68 / r48;
        r144 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r146 = r42 * r42;
        r136 = org.apache.commons.math4.util.FastMath.max(r144, r146);
        r144 = r136 * r54;
        r5 = (r144 > r120 ? 1 : (r144 == r120 ? 0 : -1));
        if (r5 <= 0) goto L_0x0c94;
    L_0x0c8e:
        r120 = r136 * r54;
        r18 = r102;
        r16 = r54;
    L_0x0c94:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r44 = r5.getEntry(r0);
        r144 = r44 * r44;
        r46 = r136 * r144;
        r0 = r28;
        r2 = r46;
        r28 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r102 = r102 + 1;
        goto L_0x0b6b;
    L_0x0cae:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r38 = r5.getEntry(r0, r1);
        r144 = r38 * r38;
        r94 = r94 + r144;
        r105 = r105 + 1;
        goto L_0x0c5e;
    L_0x0cc1:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.newPoint;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r40 = r144 - r146;
        r144 = r40 * r40;
        r68 = r68 + r144;
        r100 = r100 + 1;
        goto L_0x0c78;
    L_0x0ce0:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r18;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r136 = r114 * r144;
        r100 = 0;
    L_0x0cf0:
        r0 = r100;
        r1 = r96;
        if (r0 <= r1) goto L_0x0cfa;
    L_0x0cf6:
        r96 = r96 + 1;
        goto L_0x0b9d;
    L_0x0cfa:
        r0 = r150;
        r5 = r0.modelSecondDerivativesValues;
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r18;
        r1 = r100;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r97;
        r1 = r144;
        r5.setEntry(r0, r1);
        r97 = r97 + 1;
        r100 = r100 + 1;
        goto L_0x0cf0;
    L_0x0d24:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r18;
        r1 = r105;
        r144 = r5.getEntry(r0, r1);
        r136 = r58 * r144;
        r102 = 0;
    L_0x0d34:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0d3e;
    L_0x0d3a:
        r105 = r105 + 1;
        goto L_0x0ba5;
    L_0x0d3e:
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r150;
        r13 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r102;
        r1 = r144;
        r5.setEntry(r0, r1);
        r102 = r102 + 1;
        goto L_0x0d34;
    L_0x0d66:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r150;
        r13 = r0.newPoint;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r18;
        r1 = r96;
        r2 = r144;
        r5.setEntry(r0, r1, r2);
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r18;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r0 = r123;
        r1 = r96;
        r2 = r144;
        r0.setEntry(r1, r2);
        r96 = r96 + 1;
        goto L_0x0bb8;
    L_0x0d96:
        r126 = 0;
        r105 = 0;
    L_0x0d9a:
        r0 = r105;
        r1 = r112;
        if (r0 < r1) goto L_0x0db8;
    L_0x0da0:
        r128 = 0;
        r100 = 0;
    L_0x0da4:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0dd7;
    L_0x0daa:
        r136 = r126 * r128;
        r96 = 0;
    L_0x0dae:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0df4;
    L_0x0db4:
        r102 = r102 + 1;
        goto L_0x0bc0;
    L_0x0db8:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r18;
        r1 = r105;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r105;
        r146 = r5.getEntry(r0, r1);
        r144 = r144 * r146;
        r126 = r126 + r144;
        r105 = r105 + 1;
        goto L_0x0d9a;
    L_0x0dd7:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r128 = r128 + r144;
        r100 = r100 + 1;
        goto L_0x0da4;
    L_0x0df4:
        r0 = r123;
        r1 = r96;
        r144 = r0.getEntry(r1);
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r146 = r5.getEntry(r0, r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r123;
        r1 = r96;
        r2 = r144;
        r0.setEntry(r1, r2);
        r96 = r96 + 1;
        goto L_0x0dae;
    L_0x0e18:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r123;
        r1 = r96;
        r146 = r0.getEntry(r1);
        r146 = r146 * r58;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x0bc8;
    L_0x0e3d:
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r150;
        r13 = r0.newPoint;
        r0 = r100;
        r144 = r13.getEntry(r0);
        r0 = r100;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r38 = r5.getEntry(r0);
        r144 = r38 * r38;
        r142 = r142 + r144;
        r96 = 0;
    L_0x0e62:
        r0 = r96;
        r1 = r100;
        if (r0 <= r1) goto L_0x0e6c;
    L_0x0e68:
        r100 = r100 + 1;
        goto L_0x0bde;
    L_0x0e6c:
        r0 = r96;
        r1 = r100;
        if (r0 >= r1) goto L_0x0e9f;
    L_0x0e72:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r100;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r146 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trialStepPoint;
        r0 = r96;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r100;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x0e9f:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.modelSecondDerivativesValues;
        r0 = r97;
        r146 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.trialStepPoint;
        r0 = r100;
        r148 = r13.getEntry(r0);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r97 = r97 + 1;
        r96 = r96 + 1;
        goto L_0x0e62;
    L_0x0ed1:
        r136 = 0;
        r100 = 0;
    L_0x0ed5:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0ef3;
    L_0x0edb:
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r102;
        r144 = r5.getEntry(r0);
        r136 = r136 * r144;
        r96 = 0;
    L_0x0ee9:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x0f10;
    L_0x0eef:
        r102 = r102 + 1;
        goto L_0x0be6;
    L_0x0ef3:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trialStepPoint;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r136 = r136 + r144;
        r100 = r100 + 1;
        goto L_0x0ed5;
    L_0x0f10:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r146 = r13.getEntry(r0, r1);
        r146 = r146 * r136;
        r144 = r144 + r146;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r96 = r96 + 1;
        goto L_0x0ee9;
    L_0x0f38:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r150;
        r13 = r0.fAtInterpolationPoints;
        r0 = r102;
        r144 = r13.getEntry(r0);
        r0 = r150;
        r13 = r0.fAtInterpolationPoints;
        r0 = r150;
        r0 = r0.trustRegionCenterInterpolationPointIndex;
        r146 = r0;
        r0 = r146;
        r146 = r13.getEntry(r0);
        r144 = r144 - r146;
        r0 = r102;
        r1 = r144;
        r5.setEntry(r0, r1);
        r144 = 0;
        r0 = r141;
        r1 = r102;
        r2 = r144;
        r0.setEntry(r1, r2);
        r102 = r102 + 1;
        goto L_0x0bf0;
    L_0x0f6e:
        r124 = 0;
        r102 = 0;
    L_0x0f72:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0f84;
    L_0x0f78:
        r102 = 0;
    L_0x0f7a:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x0fa1;
    L_0x0f80:
        r100 = r100 + 1;
        goto L_0x0bf8;
    L_0x0f84:
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r102 = r102 + 1;
        goto L_0x0f72;
    L_0x0fa1:
        r0 = r141;
        r1 = r102;
        r144 = r0.getEntry(r1);
        r0 = r150;
        r5 = r0.zMatrix;
        r0 = r102;
        r1 = r100;
        r146 = r5.getEntry(r0, r1);
        r146 = r146 * r124;
        r144 = r144 + r146;
        r0 = r141;
        r1 = r102;
        r2 = r144;
        r0.setEntry(r1, r2);
        r102 = r102 + 1;
        goto L_0x0f7a;
    L_0x0fc5:
        r124 = 0;
        r100 = 0;
    L_0x0fc9:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x0ff7;
    L_0x0fcf:
        r0 = r141;
        r1 = r102;
        r144 = r0.getEntry(r1);
        r0 = r140;
        r1 = r102;
        r2 = r144;
        r0.setEntry(r1, r2);
        r0 = r141;
        r1 = r102;
        r144 = r0.getEntry(r1);
        r144 = r144 * r124;
        r0 = r141;
        r1 = r102;
        r2 = r144;
        r0.setEntry(r1, r2);
        r102 = r102 + 1;
        goto L_0x0c00;
    L_0x0ff7:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r124 = r124 + r144;
        r100 = r100 + 1;
        goto L_0x0fc9;
    L_0x1014:
        r124 = 0;
        r102 = 0;
    L_0x1018:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x1067;
    L_0x101e:
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.lowerDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x109d;
    L_0x1036:
        r144 = 0;
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r38 = org.apache.commons.math4.util.FastMath.min(r144, r146);
        r144 = r38 * r38;
        r90 = r90 + r144;
        r144 = 0;
        r0 = r144;
        r2 = r124;
        r40 = org.apache.commons.math4.util.FastMath.min(r0, r2);
        r144 = r40 * r40;
        r88 = r88 + r144;
    L_0x1058:
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r13 = r111 + r96;
        r0 = r124;
        r5.setEntry(r13, r0);
        r96 = r96 + 1;
        goto L_0x0c0c;
    L_0x1067:
        r0 = r150;
        r5 = r0.bMatrix;
        r0 = r102;
        r1 = r96;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.lagrangeValuesAtNewPoint;
        r0 = r102;
        r146 = r5.getEntry(r0);
        r144 = r144 * r146;
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r96;
        r146 = r5.getEntry(r0, r1);
        r0 = r141;
        r1 = r102;
        r148 = r0.getEntry(r1);
        r146 = r146 * r148;
        r144 = r144 + r146;
        r124 = r124 + r144;
        r102 = r102 + 1;
        goto L_0x1018;
    L_0x109d:
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.upperDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x10d8;
    L_0x10b5:
        r144 = 0;
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r38 = org.apache.commons.math4.util.FastMath.max(r144, r146);
        r144 = r38 * r38;
        r90 = r90 + r144;
        r144 = 0;
        r0 = r144;
        r2 = r124;
        r40 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r144 = r40 * r40;
        r88 = r88 + r144;
        goto L_0x1058;
    L_0x10d8:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r96;
        r38 = r5.getEntry(r0);
        r144 = r38 * r38;
        r90 = r90 + r144;
        r144 = r124 * r124;
        r88 = r88 + r144;
        goto L_0x1058;
    L_0x10ec:
        r0 = r96;
        r1 = r107;
        if (r0 >= r1) goto L_0x1109;
    L_0x10f2:
        r0 = r150;
        r5 = r0.gradientAtTrustRegionCenter;
        r0 = r150;
        r13 = r0.lagrangeValuesAtNewPoint;
        r144 = r111 + r96;
        r0 = r144;
        r144 = r13.getEntry(r0);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x1109:
        r0 = r96;
        r1 = r111;
        if (r0 >= r1) goto L_0x1122;
    L_0x110f:
        r0 = r150;
        r5 = r0.modelSecondDerivativesParameters;
        r0 = r140;
        r1 = r96;
        r144 = r0.getEntry(r1);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x1122:
        r0 = r96;
        r1 = r109;
        if (r0 >= r1) goto L_0x1135;
    L_0x1128:
        r0 = r150;
        r5 = r0.modelSecondDerivativesValues;
        r144 = 0;
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x1135:
        r99 = 0;
        r96 = r96 + 1;
        goto L_0x0c2d;
    L_0x113b:
        r144 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r144 = r144 * r138;
        r144 = r144 + r80;
        r5 = (r78 > r144 ? 1 : (r78 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x114c;
    L_0x1148:
        r122 = 60;
        goto L_0x0085;
    L_0x114c:
        r144 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r38 = r144 * r6;
        r144 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        r40 = r144 * r118;
        r144 = r38 * r38;
        r146 = r40 * r40;
        r68 = org.apache.commons.math4.util.FastMath.max(r144, r146);
    L_0x115c:
        r5 = 650; // 0x28a float:9.11E-43 double:3.21E-321;
        printState(r5);
        r18 = -1;
        r102 = 0;
    L_0x1165:
        r0 = r102;
        r1 = r111;
        if (r0 < r1) goto L_0x11ac;
    L_0x116b:
        if (r18 < 0) goto L_0x11e0;
    L_0x116d:
        r66 = org.apache.commons.math4.util.FastMath.sqrt(r68);
        r5 = -1;
        r0 = r113;
        if (r0 != r5) goto L_0x118f;
    L_0x1176:
        r144 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r144 = r144 * r6;
        r146 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r146 = r146 * r66;
        r6 = org.apache.commons.math4.util.FastMath.min(r144, r146);
        r144 = 4609434218613702656; // 0x3ff8000000000000 float:0.0 double:1.5;
        r144 = r144 * r118;
        r5 = (r6 > r144 ? 1 : (r6 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x118f;
    L_0x118d:
        r6 = r118;
    L_0x118f:
        r113 = 0;
        r144 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r144 = r144 * r66;
        r0 = r144;
        r38 = org.apache.commons.math4.util.FastMath.min(r0, r6);
        r0 = r38;
        r2 = r118;
        r20 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r72 = r20 * r20;
        r122 = 90;
        goto L_0x0085;
    L_0x11ac:
        r124 = 0;
        r100 = 0;
    L_0x11b0:
        r0 = r100;
        r1 = r107;
        if (r0 < r1) goto L_0x11c1;
    L_0x11b6:
        r5 = (r124 > r68 ? 1 : (r124 == r68 ? 0 : -1));
        if (r5 <= 0) goto L_0x11be;
    L_0x11ba:
        r18 = r102;
        r68 = r124;
    L_0x11be:
        r102 = r102 + 1;
        goto L_0x1165;
    L_0x11c1:
        r0 = r150;
        r5 = r0.interpolationPoints;
        r0 = r102;
        r1 = r100;
        r144 = r5.getEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r100;
        r146 = r5.getEntry(r0);
        r38 = r144 - r146;
        r144 = r38 * r38;
        r124 = r124 + r144;
        r100 = r100 + 1;
        goto L_0x11b0;
    L_0x11e0:
        r5 = -1;
        r0 = r113;
        if (r0 != r5) goto L_0x11e9;
    L_0x11e5:
        r122 = 680; // 0x2a8 float:9.53E-43 double:3.36E-321;
        goto L_0x0085;
    L_0x11e9:
        r144 = 0;
        r5 = (r116 > r144 ? 1 : (r116 == r144 ? 0 : -1));
        if (r5 <= 0) goto L_0x11f3;
    L_0x11ef:
        r122 = 60;
        goto L_0x0085;
    L_0x11f3:
        r0 = r70;
        r144 = org.apache.commons.math4.util.FastMath.max(r6, r0);
        r5 = (r144 > r118 ? 1 : (r144 == r118 ? 0 : -1));
        if (r5 <= 0) goto L_0x1201;
    L_0x11fd:
        r122 = 60;
        goto L_0x0085;
    L_0x1201:
        r5 = 680; // 0x2a8 float:9.53E-43 double:3.36E-321;
        printState(r5);
        r0 = r150;
        r0 = r0.stoppingTrustRegionRadius;
        r144 = r0;
        r5 = (r118 > r144 ? 1 : (r118 == r144 ? 0 : -1));
        if (r5 <= 0) goto L_0x1256;
    L_0x1210:
        r144 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r6 = r144 * r118;
        r0 = r150;
        r0 = r0.stoppingTrustRegionRadius;
        r144 = r0;
        r116 = r118 / r144;
        r144 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r5 = (r116 > r144 ? 1 : (r116 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x1238;
    L_0x1222:
        r0 = r150;
        r0 = r0.stoppingTrustRegionRadius;
        r118 = r0;
    L_0x1228:
        r0 = r118;
        r6 = org.apache.commons.math4.util.FastMath.max(r6, r0);
        r113 = 0;
        r108 = r150.getEvaluations();
        r122 = 60;
        goto L_0x0085;
    L_0x1238:
        r144 = 4643000109586448384; // 0x406f400000000000 float:0.0 double:250.0;
        r5 = (r116 > r144 ? 1 : (r116 == r144 ? 0 : -1));
        if (r5 > 0) goto L_0x124e;
    L_0x1241:
        r144 = org.apache.commons.math4.util.FastMath.sqrt(r116);
        r0 = r150;
        r0 = r0.stoppingTrustRegionRadius;
        r146 = r0;
        r118 = r144 * r146;
        goto L_0x1228;
    L_0x124e:
        r144 = 4591870180066957722; // 0x3fb999999999999a float:-1.5881868E-23 double:0.1;
        r118 = r118 * r144;
        goto L_0x1228;
    L_0x1256:
        r5 = -1;
        r0 = r113;
        if (r0 != r5) goto L_0x125f;
    L_0x125b:
        r122 = 360; // 0x168 float:5.04E-43 double:1.78E-321;
        goto L_0x0085;
    L_0x125f:
        r5 = 720; // 0x2d0 float:1.009E-42 double:3.557E-321;
        printState(r5);
        r0 = r150;
        r5 = r0.fAtInterpolationPoints;
        r0 = r150;
        r13 = r0.trustRegionCenterInterpolationPointIndex;
        r144 = r5.getEntry(r13);
        r5 = (r144 > r86 ? 1 : (r144 == r86 ? 0 : -1));
        if (r5 > 0) goto L_0x1288;
    L_0x1274:
        r96 = 0;
    L_0x1276:
        r0 = r96;
        r1 = r107;
        if (r0 < r1) goto L_0x1289;
    L_0x127c:
        r0 = r150;
        r5 = r0.fAtInterpolationPoints;
        r0 = r150;
        r13 = r0.trustRegionCenterInterpolationPointIndex;
        r78 = r5.getEntry(r13);
    L_0x1288:
        return r78;
    L_0x1289:
        r42 = r151[r96];
        r0 = r150;
        r5 = r0.originShift;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r44 = r144 + r146;
        r38 = org.apache.commons.math4.util.FastMath.max(r42, r44);
        r40 = r152[r96];
        r0 = r150;
        r5 = r0.currentBest;
        r144 = org.apache.commons.math4.util.FastMath.min(r38, r40);
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.lowerDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x12db;
    L_0x12ce:
        r0 = r150;
        r5 = r0.currentBest;
        r144 = r151[r96];
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x12db:
        r0 = r150;
        r5 = r0.trustRegionCenterOffset;
        r0 = r96;
        r144 = r5.getEntry(r0);
        r0 = r150;
        r5 = r0.upperDifference;
        r0 = r96;
        r146 = r5.getEntry(r0);
        r5 = (r144 > r146 ? 1 : (r144 == r146 ? 0 : -1));
        if (r5 != 0) goto L_0x1300;
    L_0x12f3:
        r0 = r150;
        r5 = r0.currentBest;
        r144 = r152[r96];
        r0 = r96;
        r1 = r144;
        r5.setEntry(r0, r1);
    L_0x1300:
        r96 = r96 + 1;
        goto L_0x1276;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer.bobyqb(double[], double[]):double");
    }

    private double[] altmov(int knew, double adelt) {
        int k;
        int j;
        int i;
        double[] dArr;
        double cauchy;
        printMethod();
        int n = this.currentBest.getDimension();
        int npt = this.numberOfInterpolationPoints;
        ArrayRealVector arrayRealVector = new ArrayRealVector(n);
        arrayRealVector = new ArrayRealVector(npt);
        arrayRealVector = new ArrayRealVector(n);
        arrayRealVector = new ArrayRealVector(n);
        for (k = 0; k < npt; k++) {
            arrayRealVector.setEntry(k, 0.0d);
        }
        int max = (npt - n) - 1;
        for (j = 0; j < max; j++) {
            double tmp = this.zMatrix.getEntry(knew, j);
            for (k = 0; k < npt; k++) {
                arrayRealVector.setEntry(k, arrayRealVector.getEntry(k) + (this.zMatrix.getEntry(k, j) * tmp));
            }
        }
        double alpha = arrayRealVector.getEntry(knew);
        double ha = HALF * alpha;
        for (i = 0; i < n; i++) {
            arrayRealVector.setEntry(i, this.bMatrix.getEntry(knew, i));
        }
        for (k = 0; k < npt; k++) {
            tmp = 0.0d;
            for (j = 0; j < n; j++) {
                tmp += this.interpolationPoints.getEntry(k, j) * this.trustRegionCenterOffset.getEntry(j);
            }
            tmp *= arrayRealVector.getEntry(k);
            for (i = 0; i < n; i++) {
                arrayRealVector.setEntry(i, arrayRealVector.getEntry(i) + (this.interpolationPoints.getEntry(k, i) * tmp));
            }
        }
        double presav = 0.0d;
        double step = Double.NaN;
        int ksav = 0;
        int ibdsav = 0;
        double stpsav = 0.0d;
        for (k = 0; k < npt; k++) {
            double d1;
            if (k != this.trustRegionCenterInterpolationPointIndex) {
                double vlag;
                double dderiv = 0.0d;
                double distsq = 0.0d;
                for (i = 0; i < n; i++) {
                    tmp = this.interpolationPoints.getEntry(k, i) - this.trustRegionCenterOffset.getEntry(i);
                    dderiv += arrayRealVector.getEntry(i) * tmp;
                    distsq += tmp * tmp;
                }
                double subd = adelt / FastMath.sqrt(distsq);
                double slbd = -subd;
                int ilbd = 0;
                int iubd = 0;
                double sumin = FastMath.min((double) ONE, subd);
                for (i = 0; i < n; i++) {
                    tmp = this.interpolationPoints.getEntry(k, i) - this.trustRegionCenterOffset.getEntry(i);
                    if (tmp > 0.0d) {
                        if (slbd * tmp < this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                            slbd = (this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp;
                            ilbd = (-i) - 1;
                        }
                        if (subd * tmp > this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                            subd = FastMath.max(sumin, (this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp);
                            iubd = i + 1;
                        }
                    } else if (tmp < 0.0d) {
                        if (slbd * tmp > this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                            slbd = (this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp;
                            ilbd = i + 1;
                        }
                        if (subd * tmp < this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) {
                            subd = FastMath.max(sumin, (this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i)) / tmp);
                            iubd = (-i) - 1;
                        }
                    }
                }
                step = slbd;
                int i2 = ilbd;
                if (k == knew) {
                    double diff = dderiv - ONE;
                    vlag = slbd * (dderiv - (slbd * diff));
                    d1 = subd * (dderiv - (subd * diff));
                    if (FastMath.abs(d1) > FastMath.abs(vlag)) {
                        step = subd;
                        vlag = d1;
                        i2 = iubd;
                    }
                    double d2 = HALF * dderiv;
                    if ((d2 - (diff * slbd)) * (d2 - (diff * subd)) < 0.0d) {
                        double d5 = (d2 * d2) / diff;
                        if (FastMath.abs(d5) > FastMath.abs(vlag)) {
                            step = d2 / diff;
                            vlag = d5;
                            i2 = 0;
                        }
                    }
                } else {
                    vlag = slbd * (ONE - slbd);
                    tmp = subd * (ONE - subd);
                    if (FastMath.abs(tmp) > FastMath.abs(vlag)) {
                        step = subd;
                        vlag = tmp;
                        i2 = iubd;
                    }
                    if (subd > HALF && FastMath.abs(vlag) < ONE_OVER_FOUR) {
                        step = HALF;
                        vlag = ONE_OVER_FOUR;
                        i2 = 0;
                    }
                    vlag *= dderiv;
                }
                tmp = ((ONE - step) * step) * distsq;
                double predsq = (vlag * vlag) * ((vlag * vlag) + ((ha * tmp) * tmp));
                if (predsq > presav) {
                    presav = predsq;
                    ksav = k;
                    stpsav = step;
                    ibdsav = i2;
                }
            }
        }
        for (i = 0; i < n; i++) {
            tmp = this.trustRegionCenterOffset.getEntry(i) + ((this.interpolationPoints.getEntry(ksav, i) - this.trustRegionCenterOffset.getEntry(i)) * stpsav);
            this.newPoint.setEntry(i, FastMath.max(this.lowerDifference.getEntry(i), FastMath.min(this.upperDifference.getEntry(i), tmp)));
        }
        if (ibdsav < 0) {
            this.newPoint.setEntry((-ibdsav) - 1, this.lowerDifference.getEntry((-ibdsav) - 1));
        }
        if (ibdsav > 0) {
            this.newPoint.setEntry(ibdsav - 1, this.upperDifference.getEntry(ibdsav - 1));
        }
        double bigstp = adelt + adelt;
        int iflag = 0;
        double csave = 0.0d;
        while (true) {
            double wfixsq = 0.0d;
            double ggfree = 0.0d;
            for (i = 0; i < n; i++) {
                double glagValue = arrayRealVector.getEntry(i);
                arrayRealVector.setEntry(i, 0.0d);
                if (FastMath.min(this.trustRegionCenterOffset.getEntry(i) - this.lowerDifference.getEntry(i), glagValue) <= 0.0d) {
                    if (FastMath.max(this.trustRegionCenterOffset.getEntry(i) - this.upperDifference.getEntry(i), glagValue) >= 0.0d) {
                    }
                }
                arrayRealVector.setEntry(i, bigstp);
                ggfree += glagValue * glagValue;
            }
            if (ggfree == 0.0d) {
                dArr = new double[MINIMUM_PROBLEM_DIMENSION];
                dArr[0] = alpha;
                dArr[1] = 0.0d;
                return dArr;
            }
            double tmp1 = (adelt * adelt) - 0.0d;
            if (tmp1 > 0.0d) {
                step = FastMath.sqrt(tmp1 / ggfree);
                ggfree = 0.0d;
                for (i = 0; i < n; i++) {
                    if (arrayRealVector.getEntry(i) == bigstp) {
                        double tmp2 = this.trustRegionCenterOffset.getEntry(i) - (arrayRealVector.getEntry(i) * step);
                        if (tmp2 <= this.lowerDifference.getEntry(i)) {
                            arrayRealVector.setEntry(i, this.lowerDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i));
                            d1 = arrayRealVector.getEntry(i);
                            wfixsq += d1 * d1;
                        } else {
                            if (tmp2 >= this.upperDifference.getEntry(i)) {
                                arrayRealVector.setEntry(i, this.upperDifference.getEntry(i) - this.trustRegionCenterOffset.getEntry(i));
                                d1 = arrayRealVector.getEntry(i);
                                wfixsq += d1 * d1;
                            } else {
                                d1 = arrayRealVector.getEntry(i);
                                ggfree += d1 * d1;
                            }
                        }
                    }
                }
            }
            double gw = 0.0d;
            for (i = 0; i < n; i++) {
                glagValue = arrayRealVector.getEntry(i);
                if (arrayRealVector.getEntry(i) == bigstp) {
                    arrayRealVector.setEntry(i, (-step) * glagValue);
                    double min = FastMath.min(this.upperDifference.getEntry(i), this.trustRegionCenterOffset.getEntry(i) + arrayRealVector.getEntry(i));
                    this.alternativeNewPoint.setEntry(i, FastMath.max(this.lowerDifference.getEntry(i), min));
                } else if (arrayRealVector.getEntry(i) == 0.0d) {
                    this.alternativeNewPoint.setEntry(i, this.trustRegionCenterOffset.getEntry(i));
                } else if (glagValue > 0.0d) {
                    this.alternativeNewPoint.setEntry(i, this.lowerDifference.getEntry(i));
                } else {
                    this.alternativeNewPoint.setEntry(i, this.upperDifference.getEntry(i));
                }
                gw += arrayRealVector.getEntry(i) * glagValue;
            }
            double curv = 0.0d;
            for (k = 0; k < npt; k++) {
                tmp = 0.0d;
                for (j = 0; j < n; j++) {
                    tmp += this.interpolationPoints.getEntry(k, j) * arrayRealVector.getEntry(j);
                }
                curv += (arrayRealVector.getEntry(k) * tmp) * tmp;
            }
            if (iflag == 1) {
                curv = -curv;
            }
            if (curv > (-gw)) {
                if (curv < (-gw) * (ONE + FastMath.sqrt(TWO))) {
                    double scale = (-gw) / curv;
                    for (i = 0; i < n; i++) {
                        tmp = this.trustRegionCenterOffset.getEntry(i) + (arrayRealVector.getEntry(i) * scale);
                        this.alternativeNewPoint.setEntry(i, FastMath.max(this.lowerDifference.getEntry(i), FastMath.min(this.upperDifference.getEntry(i), tmp)));
                    }
                    d1 = (HALF * gw) * scale;
                    cauchy = d1 * d1;
                    if (iflag == 0) {
                        break;
                    }
                    for (i = 0; i < n; i++) {
                        arrayRealVector.setEntry(i, -arrayRealVector.getEntry(i));
                        arrayRealVector.setEntry(i, this.alternativeNewPoint.getEntry(i));
                    }
                    csave = cauchy;
                    iflag = 1;
                }
            }
            d1 = gw + (HALF * curv);
            cauchy = d1 * d1;
            if (iflag == 0) {
                break;
            }
            for (i = 0; i < n; i++) {
                arrayRealVector.setEntry(i, -arrayRealVector.getEntry(i));
                arrayRealVector.setEntry(i, this.alternativeNewPoint.getEntry(i));
            }
            csave = cauchy;
            iflag = 1;
        }
        if (csave > cauchy) {
            for (i = 0; i < n; i++) {
                this.alternativeNewPoint.setEntry(i, arrayRealVector.getEntry(i));
            }
            cauchy = csave;
        }
        dArr = new double[MINIMUM_PROBLEM_DIMENSION];
        dArr[0] = alpha;
        dArr[1] = cauchy;
        return dArr;
    }

    private void prelim(double[] lowerBound, double[] upperBound) {
        int j;
        int i;
        printMethod();
        int n = this.currentBest.getDimension();
        int npt = this.numberOfInterpolationPoints;
        int ndim = this.bMatrix.getRowDimension();
        double rhosq = this.initialTrustRegionRadius * this.initialTrustRegionRadius;
        double recip = ONE / rhosq;
        int np = n + 1;
        for (j = 0; j < n; j++) {
            int k;
            this.originShift.setEntry(j, this.currentBest.getEntry(j));
            for (k = 0; k < npt; k++) {
                this.interpolationPoints.setEntry(k, j, 0.0d);
            }
            for (i = 0; i < ndim; i++) {
                this.bMatrix.setEntry(i, j, 0.0d);
            }
        }
        int max = (n * np) / MINIMUM_PROBLEM_DIMENSION;
        for (i = 0; i < max; i++) {
            this.modelSecondDerivativesValues.setEntry(i, 0.0d);
        }
        for (k = 0; k < npt; k++) {
            this.modelSecondDerivativesParameters.setEntry(k, 0.0d);
            max = npt - np;
            for (j = 0; j < max; j++) {
                this.zMatrix.setEntry(k, j, 0.0d);
            }
        }
        int ipt = 0;
        int jpt = 0;
        double fbeg = Double.NaN;
        do {
            int nfm = getEvaluations();
            int nfx = nfm - n;
            int nfmm = nfm - 1;
            int nfxm = nfx - 1;
            double stepa = 0.0d;
            double stepb = 0.0d;
            if (nfm > n * MINIMUM_PROBLEM_DIMENSION) {
                int tmp1 = (nfm - np) / n;
                jpt = (nfm - (tmp1 * n)) - n;
                ipt = jpt + tmp1;
                if (ipt > n) {
                    int tmp2 = jpt;
                    jpt = ipt - n;
                    ipt = tmp2;
                }
                int iptMinus1 = ipt - 1;
                int jptMinus1 = jpt - 1;
                this.interpolationPoints.setEntry(nfm, iptMinus1, this.interpolationPoints.getEntry(ipt, iptMinus1));
                this.interpolationPoints.setEntry(nfm, jptMinus1, this.interpolationPoints.getEntry(jpt, jptMinus1));
            } else if (nfm >= 1 && nfm <= n) {
                stepa = this.initialTrustRegionRadius;
                if (this.upperDifference.getEntry(nfmm) == 0.0d) {
                    stepa = -stepa;
                }
                this.interpolationPoints.setEntry(nfm, nfmm, stepa);
            } else if (nfm > n) {
                stepa = this.interpolationPoints.getEntry(nfx, nfxm);
                stepb = -this.initialTrustRegionRadius;
                if (this.lowerDifference.getEntry(nfxm) == 0.0d) {
                    stepb = FastMath.min(TWO * this.initialTrustRegionRadius, this.upperDifference.getEntry(nfxm));
                }
                if (this.upperDifference.getEntry(nfxm) == 0.0d) {
                    stepb = FastMath.max(-2.0d * this.initialTrustRegionRadius, this.lowerDifference.getEntry(nfxm));
                }
                this.interpolationPoints.setEntry(nfm, nfxm, stepb);
            }
            for (j = 0; j < n; j++) {
                this.currentBest.setEntry(j, FastMath.min(FastMath.max(lowerBound[j], this.originShift.getEntry(j) + this.interpolationPoints.getEntry(nfm, j)), upperBound[j]));
                if (this.interpolationPoints.getEntry(nfm, j) == this.lowerDifference.getEntry(j)) {
                    this.currentBest.setEntry(j, lowerBound[j]);
                }
                if (this.interpolationPoints.getEntry(nfm, j) == this.upperDifference.getEntry(j)) {
                    this.currentBest.setEntry(j, upperBound[j]);
                }
            }
            double objectiveValue = computeObjectiveValue(this.currentBest.toArray());
            double f = this.isMinimize ? objectiveValue : -objectiveValue;
            int numEval = getEvaluations();
            this.fAtInterpolationPoints.setEntry(nfm, f);
            if (numEval == 1) {
                fbeg = f;
                this.trustRegionCenterInterpolationPointIndex = 0;
            } else {
                if (f < this.fAtInterpolationPoints.getEntry(this.trustRegionCenterInterpolationPointIndex)) {
                    this.trustRegionCenterInterpolationPointIndex = nfm;
                }
            }
            if (numEval > (n * MINIMUM_PROBLEM_DIMENSION) + 1) {
                this.zMatrix.setEntry(0, nfxm, recip);
                this.zMatrix.setEntry(nfm, nfxm, recip);
                this.zMatrix.setEntry(ipt, nfxm, -recip);
                this.zMatrix.setEntry(jpt, nfxm, -recip);
                this.modelSecondDerivativesValues.setEntry(((((ipt - 1) * ipt) / MINIMUM_PROBLEM_DIMENSION) + jpt) - 1, (((fbeg - this.fAtInterpolationPoints.getEntry(ipt)) - this.fAtInterpolationPoints.getEntry(jpt)) + f) / (this.interpolationPoints.getEntry(nfm, ipt - 1) * this.interpolationPoints.getEntry(nfm, jpt - 1)));
            } else if (numEval >= MINIMUM_PROBLEM_DIMENSION && numEval <= n + 1) {
                this.gradientAtTrustRegionCenter.setEntry(nfmm, (f - fbeg) / stepa);
                if (npt < numEval + n) {
                    double oneOverStepA = ONE / stepa;
                    this.bMatrix.setEntry(0, nfmm, -oneOverStepA);
                    this.bMatrix.setEntry(nfm, nfmm, oneOverStepA);
                    this.bMatrix.setEntry(npt + nfmm, nfmm, -0.5d * rhosq);
                }
            } else if (numEval >= n + MINIMUM_PROBLEM_DIMENSION) {
                double tmp = (f - fbeg) / stepb;
                double diff = stepb - stepa;
                this.modelSecondDerivativesValues.setEntry((((nfx + 1) * nfx) / MINIMUM_PROBLEM_DIMENSION) - 1, (TWO * (tmp - this.gradientAtTrustRegionCenter.getEntry(nfxm))) / diff);
                this.gradientAtTrustRegionCenter.setEntry(nfxm, ((this.gradientAtTrustRegionCenter.getEntry(nfxm) * stepb) - (tmp * stepa)) / diff);
                if (stepa * stepb < 0.0d) {
                    if (f < this.fAtInterpolationPoints.getEntry(nfm - n)) {
                        this.fAtInterpolationPoints.setEntry(nfm, this.fAtInterpolationPoints.getEntry(nfm - n));
                        this.fAtInterpolationPoints.setEntry(nfm - n, f);
                        int i2 = this.trustRegionCenterInterpolationPointIndex;
                        if (r0 == nfm) {
                            this.trustRegionCenterInterpolationPointIndex = nfm - n;
                        }
                        this.interpolationPoints.setEntry(nfm - n, nfxm, stepb);
                        this.interpolationPoints.setEntry(nfm, nfxm, stepa);
                    }
                }
                this.bMatrix.setEntry(0, nfxm, (-(stepa + stepb)) / (stepa * stepb));
                this.bMatrix.setEntry(nfm, nfxm, -0.5d / this.interpolationPoints.getEntry(nfm - n, nfxm));
                this.bMatrix.setEntry(nfm - n, nfxm, (-this.bMatrix.getEntry(0, nfxm)) - this.bMatrix.getEntry(nfm, nfxm));
                this.zMatrix.setEntry(0, nfxm, FastMath.sqrt(TWO) / (stepa * stepb));
                this.zMatrix.setEntry(nfm, nfxm, FastMath.sqrt(HALF) / rhosq);
                this.zMatrix.setEntry(nfm - n, nfxm, (-this.zMatrix.getEntry(0, nfxm)) - this.zMatrix.getEntry(nfm, nfxm));
            }
        } while (getEvaluations() < npt);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private double[] trsbox(double r102, org.apache.commons.math4.linear.ArrayRealVector r104, org.apache.commons.math4.linear.ArrayRealVector r105, org.apache.commons.math4.linear.ArrayRealVector r106, org.apache.commons.math4.linear.ArrayRealVector r107, org.apache.commons.math4.linear.ArrayRealVector r108) {
        /*
        r101 = this;
        printMethod();
        r0 = r101;
        r0 = r0.currentBest;
        r89 = r0;
        r50 = r89.getDimension();
        r0 = r101;
        r0 = r0.numberOfInterpolationPoints;
        r52 = r0;
        r32 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r12 = 9221120237041090560; // 0x7ff8000000000000 float:0.0 double:NaN;
        r8 = 0;
        r39 = -1;
        r51 = 0;
        r6 = 0;
        r82 = 0;
        r90 = 0;
        r92 = 0;
        r4 = 0;
        r26 = 0;
        r72 = 0;
        r66 = 0;
        r20 = 0;
        r34 = 0;
        r84 = 0;
        r86 = 0;
        r60 = 0;
        r28 = 0;
        r64 = 0;
        r36 = 0;
        r62 = 0;
        r42 = 0;
        r58 = 0;
        r56 = 0;
        r80 = 0;
        r76 = 0;
        r44 = 0;
        r43 = 0;
        r51 = 0;
        r38 = 0;
    L_0x0051:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x007e;
    L_0x0057:
        r20 = r102 * r102;
        r54 = 0;
        r12 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r53 = 20;
    L_0x005f:
        switch(r53) {
            case 20: goto L_0x0144;
            case 30: goto L_0x014b;
            case 50: goto L_0x01ea;
            case 90: goto L_0x0420;
            case 100: goto L_0x0427;
            case 120: goto L_0x04b8;
            case 150: goto L_0x0677;
            case 190: goto L_0x0894;
            case 210: goto L_0x09a2;
            default: goto L_0x0062;
        };
    L_0x0062:
        r89 = new org.apache.commons.math4.exception.MathIllegalStateException;
        r94 = org.apache.commons.math4.exception.util.LocalizedFormats.SIMPLE_MESSAGE;
        r95 = 1;
        r0 = r95;
        r0 = new java.lang.Object[r0];
        r95 = r0;
        r96 = 0;
        r97 = "trsbox";
        r95[r96] = r97;
        r0 = r89;
        r1 = r94;
        r2 = r95;
        r0.<init>(r1, r2);
        throw r89;
    L_0x007e:
        r94 = 0;
        r0 = r105;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.lowerDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 > 0) goto L_0x0104;
    L_0x00a9:
        r0 = r101;
        r0 = r0.gradientAtTrustRegionCenter;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 < 0) goto L_0x00c8;
    L_0x00bd:
        r94 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r0 = r105;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x00c8:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 == 0) goto L_0x00d8;
    L_0x00d6:
        r51 = r51 + 1;
    L_0x00d8:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r94 = 0;
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r101;
        r0 = r0.gradientAtTrustRegionCenter;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r104;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = r38 + 1;
        goto L_0x0051;
    L_0x0104:
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.upperDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 < 0) goto L_0x00c8;
    L_0x0124:
        r0 = r101;
        r0 = r0.gradientAtTrustRegionCenter;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 > 0) goto L_0x00c8;
    L_0x0138:
        r94 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = r105;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        goto L_0x00c8;
    L_0x0144:
        r89 = 20;
        printState(r89);
        r8 = 0;
    L_0x014b:
        r89 = 30;
        printState(r89);
        r76 = 0;
        r38 = 0;
    L_0x0154:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x0164;
    L_0x015a:
        r94 = 0;
        r89 = (r76 > r94 ? 1 : (r76 == r94 ? 0 : -1));
        if (r89 != 0) goto L_0x01c7;
    L_0x0160:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x0164:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 == 0) goto L_0x018c;
    L_0x0172:
        r94 = 0;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x017d:
        r0 = r106;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r76 = r76 + r94;
        r38 = r38 + 1;
        goto L_0x0154;
    L_0x018c:
        r94 = 0;
        r89 = (r8 > r94 ? 1 : (r8 == r94 ? 0 : -1));
        if (r89 != 0) goto L_0x01a9;
    L_0x0192:
        r0 = r104;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r94;
        r0 = -r0;
        r94 = r0;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        goto L_0x017d;
    L_0x01a9:
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 * r8;
        r0 = r104;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 - r96;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        goto L_0x017d;
    L_0x01c7:
        r94 = 0;
        r89 = (r8 > r94 ? 1 : (r8 == r94 ? 0 : -1));
        if (r89 != 0) goto L_0x01d3;
    L_0x01cd:
        r36 = r76;
        r89 = r43 + r50;
        r44 = r89 - r51;
    L_0x01d3:
        r94 = r36 * r20;
        r96 = 4547007122018943789; // 0x3f1a36e2eb1c432d float:-1.8890966E26 double:1.0E-4;
        r96 = r96 * r54;
        r96 = r96 * r54;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 > 0) goto L_0x01e6;
    L_0x01e2:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x01e6:
        r53 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        goto L_0x005f;
    L_0x01ea:
        r89 = 50;
        printState(r89);
        r66 = r20;
        r30 = 0;
        r70 = 0;
        r38 = 0;
    L_0x01f7:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x0207;
    L_0x01fd:
        r94 = 0;
        r89 = (r66 > r94 ? 1 : (r66 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x0258;
    L_0x0203:
        r53 = 90;
        goto L_0x005f;
    L_0x0207:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x0255;
    L_0x0215:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r66 = r66 - r94;
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r30 = r30 + r94;
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r70 = r70 + r94;
    L_0x0255:
        r38 = r38 + 1;
        goto L_0x01f7;
    L_0x0258:
        r94 = r76 * r66;
        r96 = r30 * r30;
        r94 = r94 + r96;
        r82 = org.apache.commons.math4.util.FastMath.sqrt(r94);
        r94 = 0;
        r89 = (r30 > r94 ? 1 : (r30 == r94 ? 0 : -1));
        if (r89 >= 0) goto L_0x0314;
    L_0x0268:
        r94 = r82 - r30;
        r10 = r94 / r76;
    L_0x026c:
        r80 = r10;
        r94 = 0;
        r89 = (r70 > r94 ? 1 : (r70 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x027c;
    L_0x0274:
        r94 = r36 / r70;
        r0 = r94;
        r80 = org.apache.commons.math4.util.FastMath.min(r10, r0);
    L_0x027c:
        r39 = -1;
        r38 = 0;
    L_0x0280:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x031a;
    L_0x0286:
        r68 = 0;
        r94 = 0;
        r89 = (r80 > r94 ? 1 : (r80 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x02d0;
    L_0x028e:
        r43 = r43 + 1;
        r82 = r70 / r76;
        r89 = -1;
        r0 = r39;
        r1 = r89;
        if (r0 != r1) goto L_0x02ae;
    L_0x029a:
        r94 = 0;
        r89 = (r82 > r94 ? 1 : (r82 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x02ae;
    L_0x02a0:
        r0 = r82;
        r12 = org.apache.commons.math4.util.FastMath.min(r12, r0);
        r94 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r89 = (r12 > r94 ? 1 : (r12 == r94 ? 0 : -1));
        if (r89 != 0) goto L_0x02ae;
    L_0x02ac:
        r12 = r82;
    L_0x02ae:
        r34 = r36;
        r36 = 0;
        r38 = 0;
    L_0x02b4:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x0395;
    L_0x02ba:
        r94 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r94 = r94 * r80;
        r94 = r94 * r70;
        r94 = r34 - r94;
        r16 = r80 * r94;
        r94 = 0;
        r0 = r16;
        r2 = r94;
        r68 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r54 = r54 + r68;
    L_0x02d0:
        if (r39 < 0) goto L_0x03fd;
    L_0x02d2:
        r51 = r51 + 1;
        r94 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = r105;
        r1 = r39;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r106;
        r1 = r39;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 >= 0) goto L_0x02f8;
    L_0x02ed:
        r94 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r0 = r105;
        r1 = r39;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x02f8:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r39;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r20 = r20 - r94;
        r94 = 0;
        r89 = (r20 > r94 ? 1 : (r20 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x03f9;
    L_0x0310:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x0314:
        r94 = r82 + r30;
        r10 = r66 / r94;
        goto L_0x026c;
    L_0x031a:
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 == 0) goto L_0x0376;
    L_0x0328:
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r92 = r94 + r96;
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 <= 0) goto L_0x037a;
    L_0x0354:
        r0 = r101;
        r0 = r0.upperDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 - r92;
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r82 = r94 / r96;
    L_0x036e:
        r89 = (r82 > r80 ? 1 : (r82 == r80 ? 0 : -1));
        if (r89 >= 0) goto L_0x0376;
    L_0x0372:
        r80 = r82;
        r39 = r38;
    L_0x0376:
        r38 = r38 + 1;
        goto L_0x0280;
    L_0x037a:
        r0 = r101;
        r0 = r0.lowerDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 - r92;
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r82 = r94 / r96;
        goto L_0x036e;
    L_0x0395:
        r0 = r104;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r80;
        r94 = r94 + r96;
        r0 = r104;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x03cc;
    L_0x03c0:
        r0 = r104;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r36 = r36 + r94;
    L_0x03cc:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r80;
        r94 = r94 + r96;
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = r38 + 1;
        goto L_0x02b4;
    L_0x03f9:
        r53 = 20;
        goto L_0x005f;
    L_0x03fd:
        r89 = (r80 > r10 ? 1 : (r80 == r10 ? 0 : -1));
        if (r89 >= 0) goto L_0x0420;
    L_0x0401:
        r0 = r43;
        r1 = r44;
        if (r0 != r1) goto L_0x040b;
    L_0x0407:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x040b:
        r94 = 4576918229304087675; // 0x3f847ae147ae147b float:89128.96 double:0.01;
        r94 = r94 * r54;
        r89 = (r68 > r94 ? 1 : (r68 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x041a;
    L_0x0416:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x041a:
        r8 = r36 / r34;
        r53 = 30;
        goto L_0x005f;
    L_0x0420:
        r89 = 90;
        printState(r89);
        r12 = 0;
    L_0x0427:
        r89 = 100;
        printState(r89);
        r89 = r50 + -1;
        r0 = r51;
        r1 = r89;
        if (r0 < r1) goto L_0x0438;
    L_0x0434:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x0438:
        r28 = 0;
        r26 = 0;
        r36 = 0;
        r38 = 0;
    L_0x0440:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x044c;
    L_0x0446:
        r42 = r43;
        r53 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        goto L_0x005f;
    L_0x044c:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x04ac;
    L_0x045a:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r28 = r28 + r94;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r104;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r26 = r26 + r94;
        r0 = r104;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r36 = r36 + r94;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x04a9:
        r38 = r38 + 1;
        goto L_0x0440;
    L_0x04ac:
        r94 = 0;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        goto L_0x04a9;
    L_0x04b8:
        r89 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        printState(r89);
        r43 = r43 + 1;
        r94 = r36 * r28;
        r96 = r26 * r26;
        r82 = r94 - r96;
        r94 = 4547007122018943789; // 0x3f1a36e2eb1c432d float:-1.8890966E26 double:1.0E-4;
        r94 = r94 * r54;
        r94 = r94 * r54;
        r89 = (r82 > r94 ? 1 : (r82 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x04d6;
    L_0x04d2:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x04d6:
        r82 = org.apache.commons.math4.util.FastMath.sqrt(r82);
        r38 = 0;
    L_0x04dc:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x04f7;
    L_0x04e2:
        r0 = r82;
        r0 = -r0;
        r72 = r0;
        r4 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r39 = -1;
        r38 = 0;
    L_0x04ed:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x053b;
    L_0x04f3:
        r53 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        goto L_0x005f;
    L_0x04f7:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x052f;
    L_0x0505:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 * r26;
        r0 = r104;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r28;
        r94 = r94 - r96;
        r94 = r94 / r82;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x052c:
        r38 = r38 + 1;
        goto L_0x04dc;
    L_0x052f:
        r94 = 0;
        r0 = r106;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        goto L_0x052c;
    L_0x053b:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x0673;
    L_0x0549:
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 + r96;
        r0 = r101;
        r0 = r0.lowerDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r84 = r94 - r96;
        r0 = r101;
        r0 = r0.upperDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 - r96;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r86 = r94 - r96;
        r94 = 0;
        r89 = (r84 > r94 ? 1 : (r84 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x05bc;
    L_0x05ab:
        r51 = r51 + 1;
        r94 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r0 = r105;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r53 = 100;
        goto L_0x04f3;
    L_0x05bc:
        r94 = 0;
        r89 = (r86 > r94 ? 1 : (r86 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x05d3;
    L_0x05c2:
        r51 = r51 + 1;
        r94 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = r105;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r53 = 100;
        goto L_0x04f3;
    L_0x05d3:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r0 = r106;
        r1 = r38;
        r18 = r0.getEntry(r1);
        r94 = r16 * r16;
        r96 = r18 * r18;
        r74 = r94 + r96;
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.lowerDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r16 = r94 - r96;
        r94 = r16 * r16;
        r82 = r74 - r94;
        r94 = 0;
        r89 = (r82 > r94 ? 1 : (r82 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x0631;
    L_0x0617:
        r94 = org.apache.commons.math4.util.FastMath.sqrt(r82);
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r82 = r94 - r96;
        r94 = r4 * r82;
        r89 = (r94 > r84 ? 1 : (r94 == r84 ? 0 : -1));
        if (r89 <= 0) goto L_0x0631;
    L_0x062b:
        r4 = r84 / r82;
        r39 = r38;
        r90 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
    L_0x0631:
        r0 = r101;
        r0 = r0.upperDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r16 = r94 - r96;
        r94 = r16 * r16;
        r82 = r74 - r94;
        r94 = 0;
        r89 = (r82 > r94 ? 1 : (r82 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x0673;
    L_0x0659:
        r94 = org.apache.commons.math4.util.FastMath.sqrt(r82);
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r82 = r94 + r96;
        r94 = r4 * r82;
        r89 = (r94 > r86 ? 1 : (r94 == r86 ? 0 : -1));
        if (r89 <= 0) goto L_0x0673;
    L_0x066d:
        r4 = r86 / r82;
        r39 = r38;
        r90 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
    L_0x0673:
        r38 = r38 + 1;
        goto L_0x04ed;
    L_0x0677:
        r89 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        printState(r89);
        r70 = 0;
        r24 = 0;
        r22 = 0;
        r38 = 0;
    L_0x0684:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x06ae;
    L_0x068a:
        r60 = 0;
        r41 = -1;
        r64 = 0;
        r94 = 4625478292286210048; // 0x4031000000000000 float:0.0 double:17.0;
        r94 = r94 * r4;
        r96 = 4614162998222441677; // 0x4008cccccccccccd float:-1.07374184E8 double:3.1;
        r94 = r94 + r96;
        r0 = r94;
        r0 = (int) r0;
        r45 = r0;
        r38 = 0;
    L_0x06a2:
        r0 = r38;
        r1 = r45;
        if (r0 < r1) goto L_0x0708;
    L_0x06a8:
        if (r41 >= 0) goto L_0x0753;
    L_0x06aa:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x06ae:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x0704;
    L_0x06bc:
        r0 = r106;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r70 = r70 + r94;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r24 = r24 + r94;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r108;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r22 = r22 + r94;
    L_0x0704:
        r38 = r38 + 1;
        goto L_0x0684;
    L_0x0708:
        r0 = r38;
        r0 = (double) r0;
        r94 = r0;
        r94 = r94 * r4;
        r0 = r45;
        r0 = (double) r0;
        r96 = r0;
        r6 = r94 / r96;
        r94 = r6 + r6;
        r96 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r98 = r6 * r6;
        r96 = r96 + r98;
        r78 = r94 / r96;
        r94 = r6 * r22;
        r94 = r94 - r24;
        r94 = r94 - r24;
        r94 = r94 * r6;
        r82 = r70 + r94;
        r94 = r6 * r26;
        r94 = r94 - r72;
        r96 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r96 = r96 * r78;
        r96 = r96 * r82;
        r94 = r94 - r96;
        r62 = r78 * r94;
        r89 = (r62 > r60 ? 1 : (r62 == r60 ? 0 : -1));
        if (r89 <= 0) goto L_0x0748;
    L_0x073c:
        r60 = r62;
        r41 = r38;
        r58 = r64;
    L_0x0742:
        r64 = r62;
        r38 = r38 + 1;
        goto L_0x06a2;
    L_0x0748:
        r89 = r41 + 1;
        r0 = r38;
        r1 = r89;
        if (r0 != r1) goto L_0x0742;
    L_0x0750:
        r56 = r62;
        goto L_0x0742;
    L_0x0753:
        r0 = r41;
        r1 = r45;
        if (r0 >= r1) goto L_0x0777;
    L_0x0759:
        r94 = r56 - r58;
        r96 = r60 + r60;
        r96 = r96 - r58;
        r96 = r96 - r56;
        r82 = r94 / r96;
        r0 = r41;
        r0 = (double) r0;
        r94 = r0;
        r96 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r96 = r96 * r82;
        r94 = r94 + r96;
        r94 = r94 * r4;
        r0 = r45;
        r0 = (double) r0;
        r96 = r0;
        r6 = r94 / r96;
    L_0x0777:
        r94 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r96 = r6 * r6;
        r94 = r94 - r96;
        r96 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r98 = r6 * r6;
        r96 = r96 + r98;
        r14 = r94 / r96;
        r94 = r6 + r6;
        r96 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r98 = r6 * r6;
        r96 = r96 + r98;
        r78 = r94 / r96;
        r94 = r6 * r22;
        r94 = r94 - r24;
        r94 = r94 - r24;
        r94 = r94 * r6;
        r82 = r70 + r94;
        r94 = r6 * r26;
        r94 = r94 - r72;
        r96 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r96 = r96 * r78;
        r96 = r96 * r82;
        r94 = r94 - r96;
        r68 = r78 * r94;
        r94 = 0;
        r89 = (r68 > r94 ? 1 : (r68 == r94 ? 0 : -1));
        if (r89 > 0) goto L_0x07b1;
    L_0x07ad:
        r53 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        goto L_0x005f;
    L_0x07b1:
        r26 = 0;
        r36 = 0;
        r38 = 0;
    L_0x07b7:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x07d6;
    L_0x07bd:
        r54 = r54 + r68;
        if (r39 < 0) goto L_0x0885;
    L_0x07c1:
        r0 = r41;
        r1 = r45;
        if (r0 != r1) goto L_0x0885;
    L_0x07c7:
        r51 = r51 + 1;
        r0 = r105;
        r1 = r39;
        r2 = r90;
        r0.setEntry(r1, r2);
        r53 = 100;
        goto L_0x005f;
    L_0x07d6:
        r0 = r104;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r96 = r14 - r96;
        r0 = r108;
        r1 = r38;
        r98 = r0.getEntry(r1);
        r96 = r96 * r98;
        r94 = r94 + r96;
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r78;
        r94 = r94 + r96;
        r0 = r104;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x0862;
    L_0x0811:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.trialStepPoint;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 * r14;
        r0 = r106;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r78;
        r94 = r94 + r96;
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r104;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 * r96;
        r26 = r26 + r94;
        r0 = r104;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r36 = r36 + r94;
    L_0x0862:
        r0 = r108;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r94 = r94 * r14;
        r0 = r107;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r96 = r96 * r78;
        r94 = r94 + r96;
        r0 = r108;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = r38 + 1;
        goto L_0x07b7;
    L_0x0885:
        r94 = 4576918229304087675; // 0x3f847ae147ae147b float:89128.96 double:0.01;
        r94 = r94 * r54;
        r89 = (r68 > r94 ? 1 : (r68 == r94 ? 0 : -1));
        if (r89 <= 0) goto L_0x0894;
    L_0x0890:
        r53 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x005f;
    L_0x0894:
        r89 = 190; // 0xbe float:2.66E-43 double:9.4E-322;
        printState(r89);
        r32 = 0;
        r38 = 0;
    L_0x089d:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x08b4;
    L_0x08a3:
        r89 = 2;
        r0 = r89;
        r0 = new double[r0];
        r89 = r0;
        r94 = 0;
        r89[r94] = r32;
        r94 = 1;
        r89[r94] = r12;
        return r89;
    L_0x08b4:
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 + r96;
        r0 = r101;
        r0 = r0.upperDifference;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r48 = org.apache.commons.math4.util.FastMath.min(r94, r96);
        r0 = r101;
        r0 = r0.newPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.lowerDifference;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r48;
        r2 = r94;
        r94 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x0934;
    L_0x0917:
        r0 = r101;
        r0 = r0.newPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.lowerDifference;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x0934:
        r0 = r105;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r96 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 != 0) goto L_0x095f;
    L_0x0942:
        r0 = r101;
        r0 = r0.newPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.upperDifference;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x095f:
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r101;
        r0 = r0.newPoint;
        r94 = r0;
        r0 = r94;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.trustRegionCenterOffset;
        r96 = r0;
        r0 = r96;
        r1 = r38;
        r96 = r0.getEntry(r1);
        r94 = r94 - r96;
        r0 = r89;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r0 = r101;
        r0 = r0.trialStepPoint;
        r89 = r0;
        r0 = r89;
        r1 = r38;
        r16 = r0.getEntry(r1);
        r94 = r16 * r16;
        r32 = r32 + r94;
        r38 = r38 + 1;
        goto L_0x089d;
    L_0x09a2:
        r89 = 210; // 0xd2 float:2.94E-43 double:1.04E-321;
        printState(r89);
        r40 = 0;
        r46 = 0;
    L_0x09ab:
        r0 = r46;
        r1 = r50;
        if (r0 < r1) goto L_0x09df;
    L_0x09b1:
        r0 = r101;
        r0 = r0.interpolationPoints;
        r89 = r0;
        r0 = r89;
        r1 = r106;
        r89 = r0.operate(r1);
        r0 = r101;
        r0 = r0.modelSecondDerivativesParameters;
        r94 = r0;
        r0 = r89;
        r1 = r94;
        r88 = r0.ebeMultiply(r1);
        r47 = 0;
    L_0x09cf:
        r0 = r47;
        r1 = r52;
        if (r0 < r1) goto L_0x0a56;
    L_0x09d5:
        r94 = 0;
        r89 = (r12 > r94 ? 1 : (r12 == r94 ? 0 : -1));
        if (r89 == 0) goto L_0x0aa6;
    L_0x09db:
        r53 = 50;
        goto L_0x005f;
    L_0x09df:
        r94 = 0;
        r0 = r107;
        r1 = r46;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = 0;
    L_0x09ec:
        r0 = r38;
        r1 = r46;
        if (r0 <= r1) goto L_0x09f5;
    L_0x09f2:
        r46 = r46 + 1;
        goto L_0x09ab;
    L_0x09f5:
        r0 = r38;
        r1 = r46;
        if (r0 >= r1) goto L_0x0a26;
    L_0x09fb:
        r0 = r107;
        r1 = r46;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.modelSecondDerivativesValues;
        r89 = r0;
        r0 = r89;
        r1 = r40;
        r96 = r0.getEntry(r1);
        r0 = r106;
        r1 = r38;
        r98 = r0.getEntry(r1);
        r96 = r96 * r98;
        r94 = r94 + r96;
        r0 = r107;
        r1 = r46;
        r2 = r94;
        r0.setEntry(r1, r2);
    L_0x0a26:
        r0 = r107;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.modelSecondDerivativesValues;
        r89 = r0;
        r0 = r89;
        r1 = r40;
        r96 = r0.getEntry(r1);
        r0 = r106;
        r1 = r46;
        r98 = r0.getEntry(r1);
        r96 = r96 * r98;
        r94 = r94 + r96;
        r0 = r107;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r40 = r40 + 1;
        r38 = r38 + 1;
        goto L_0x09ec;
    L_0x0a56:
        r0 = r101;
        r0 = r0.modelSecondDerivativesParameters;
        r89 = r0;
        r0 = r89;
        r1 = r47;
        r94 = r0.getEntry(r1);
        r96 = 0;
        r89 = (r94 > r96 ? 1 : (r94 == r96 ? 0 : -1));
        if (r89 == 0) goto L_0x0a72;
    L_0x0a6a:
        r38 = 0;
    L_0x0a6c:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x0a76;
    L_0x0a72:
        r47 = r47 + 1;
        goto L_0x09cf;
    L_0x0a76:
        r0 = r107;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r88;
        r1 = r47;
        r96 = r0.getEntry(r1);
        r0 = r101;
        r0 = r0.interpolationPoints;
        r89 = r0;
        r0 = r89;
        r1 = r47;
        r2 = r38;
        r98 = r0.getEntry(r1, r2);
        r96 = r96 * r98;
        r94 = r94 + r96;
        r0 = r107;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = r38 + 1;
        goto L_0x0a6c;
    L_0x0aa6:
        r0 = r43;
        r1 = r42;
        if (r0 <= r1) goto L_0x0ab0;
    L_0x0aac:
        r53 = 150; // 0x96 float:2.1E-43 double:7.4E-322;
        goto L_0x005f;
    L_0x0ab0:
        r38 = 0;
    L_0x0ab2:
        r0 = r38;
        r1 = r50;
        if (r0 < r1) goto L_0x0abc;
    L_0x0ab8:
        r53 = 120; // 0x78 float:1.68E-43 double:5.93E-322;
        goto L_0x005f;
    L_0x0abc:
        r0 = r107;
        r1 = r38;
        r94 = r0.getEntry(r1);
        r0 = r108;
        r1 = r38;
        r2 = r94;
        r0.setEntry(r1, r2);
        r38 = r38 + 1;
        goto L_0x0ab2;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer.trsbox(double, org.apache.commons.math4.linear.ArrayRealVector, org.apache.commons.math4.linear.ArrayRealVector, org.apache.commons.math4.linear.ArrayRealVector, org.apache.commons.math4.linear.ArrayRealVector, org.apache.commons.math4.linear.ArrayRealVector):double[]");
    }

    private void update(double beta, double denom, int knew) {
        int j;
        double d2;
        printMethod();
        int n = this.currentBest.getDimension();
        int npt = this.numberOfInterpolationPoints;
        int nptm = (npt - n) - 1;
        ArrayRealVector arrayRealVector = new ArrayRealVector(npt + n);
        double ztest = 0.0d;
        for (int k = 0; k < npt; k++) {
            for (j = 0; j < nptm; j++) {
                ztest = FastMath.max(ztest, FastMath.abs(this.zMatrix.getEntry(k, j)));
            }
        }
        ztest *= 1.0E-20d;
        for (j = 1; j < nptm; j++) {
            int i;
            if (FastMath.abs(this.zMatrix.getEntry(knew, j)) > ztest) {
                d2 = this.zMatrix.getEntry(knew, 0);
                double d3 = this.zMatrix.getEntry(knew, j);
                double d4 = FastMath.sqrt((d2 * d2) + (d3 * d3));
                double d5 = this.zMatrix.getEntry(knew, 0) / d4;
                double d6 = this.zMatrix.getEntry(knew, j) / d4;
                for (i = 0; i < npt; i++) {
                    double d7 = (this.zMatrix.getEntry(i, 0) * d5) + (this.zMatrix.getEntry(i, j) * d6);
                    this.zMatrix.setEntry(i, j, (this.zMatrix.getEntry(i, j) * d5) - (this.zMatrix.getEntry(i, 0) * d6));
                    this.zMatrix.setEntry(i, 0, d7);
                }
            }
            this.zMatrix.setEntry(knew, j, 0.0d);
        }
        for (i = 0; i < npt; i++) {
            arrayRealVector.setEntry(i, this.zMatrix.getEntry(knew, 0) * this.zMatrix.getEntry(i, 0));
        }
        double alpha = arrayRealVector.getEntry(knew);
        double tau = this.lagrangeValuesAtNewPoint.getEntry(knew);
        this.lagrangeValuesAtNewPoint.setEntry(knew, this.lagrangeValuesAtNewPoint.getEntry(knew) - ONE);
        double sqrtDenom = FastMath.sqrt(denom);
        double d1 = tau / sqrtDenom;
        d2 = this.zMatrix.getEntry(knew, 0) / sqrtDenom;
        for (i = 0; i < npt; i++) {
            this.zMatrix.setEntry(i, 0, (this.zMatrix.getEntry(i, 0) * d1) - (this.lagrangeValuesAtNewPoint.getEntry(i) * d2));
        }
        for (j = 0; j < n; j++) {
            int jp = npt + j;
            arrayRealVector.setEntry(jp, this.bMatrix.getEntry(knew, j));
            d3 = ((this.lagrangeValuesAtNewPoint.getEntry(jp) * alpha) - (arrayRealVector.getEntry(jp) * tau)) / denom;
            d4 = (((-beta) * arrayRealVector.getEntry(jp)) - (this.lagrangeValuesAtNewPoint.getEntry(jp) * tau)) / denom;
            for (i = 0; i <= jp; i++) {
                this.bMatrix.setEntry(i, j, (this.bMatrix.getEntry(i, j) + (this.lagrangeValuesAtNewPoint.getEntry(i) * d3)) + (arrayRealVector.getEntry(i) * d4));
                if (i >= npt) {
                    this.bMatrix.setEntry(jp, i - npt, this.bMatrix.getEntry(i, j));
                }
            }
        }
    }

    private void setup(double[] lowerBound, double[] upperBound) {
        printMethod();
        int dimension = getStartPoint().length;
        if (dimension < MINIMUM_PROBLEM_DIMENSION) {
            throw new NumberIsTooSmallException(Integer.valueOf(dimension), Integer.valueOf(MINIMUM_PROBLEM_DIMENSION), true);
        }
        int[] nPointsInterval = new int[MINIMUM_PROBLEM_DIMENSION];
        nPointsInterval[0] = dimension + MINIMUM_PROBLEM_DIMENSION;
        nPointsInterval[1] = ((dimension + MINIMUM_PROBLEM_DIMENSION) * (dimension + 1)) / MINIMUM_PROBLEM_DIMENSION;
        if (this.numberOfInterpolationPoints < nPointsInterval[0] || this.numberOfInterpolationPoints > nPointsInterval[1]) {
            throw new OutOfRangeException(LocalizedFormats.NUMBER_OF_INTERPOLATION_POINTS, Integer.valueOf(this.numberOfInterpolationPoints), Integer.valueOf(nPointsInterval[0]), Integer.valueOf(nPointsInterval[1]));
        }
        this.boundDifference = new double[dimension];
        double requiredMinDiff = TWO * this.initialTrustRegionRadius;
        double minDiff = Double.POSITIVE_INFINITY;
        for (int i = 0; i < dimension; i++) {
            this.boundDifference[i] = upperBound[i] - lowerBound[i];
            minDiff = FastMath.min(minDiff, this.boundDifference[i]);
        }
        if (minDiff < requiredMinDiff) {
            this.initialTrustRegionRadius = minDiff / 3.0d;
        }
        this.bMatrix = new Array2DRowRealMatrix(this.numberOfInterpolationPoints + dimension, dimension);
        this.zMatrix = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, (this.numberOfInterpolationPoints - dimension) - 1);
        this.interpolationPoints = new Array2DRowRealMatrix(this.numberOfInterpolationPoints, dimension);
        this.originShift = new ArrayRealVector(dimension);
        this.fAtInterpolationPoints = new ArrayRealVector(this.numberOfInterpolationPoints);
        this.trustRegionCenterOffset = new ArrayRealVector(dimension);
        this.gradientAtTrustRegionCenter = new ArrayRealVector(dimension);
        this.lowerDifference = new ArrayRealVector(dimension);
        this.upperDifference = new ArrayRealVector(dimension);
        this.modelSecondDerivativesParameters = new ArrayRealVector(this.numberOfInterpolationPoints);
        this.newPoint = new ArrayRealVector(dimension);
        this.alternativeNewPoint = new ArrayRealVector(dimension);
        this.trialStepPoint = new ArrayRealVector(dimension);
        this.lagrangeValuesAtNewPoint = new ArrayRealVector(this.numberOfInterpolationPoints + dimension);
        this.modelSecondDerivativesValues = new ArrayRealVector(((dimension + 1) * dimension) / MINIMUM_PROBLEM_DIMENSION);
    }

    private static String caller(int n) {
        StackTraceElement e = new Throwable().getStackTrace()[n];
        return e.getMethodName() + " (at line " + e.getLineNumber() + ")";
    }

    private static void printState(int s) {
    }

    private static void printMethod() {
    }
}
