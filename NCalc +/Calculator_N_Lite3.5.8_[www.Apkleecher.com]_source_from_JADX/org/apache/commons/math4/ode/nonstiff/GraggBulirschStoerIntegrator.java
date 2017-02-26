package org.apache.commons.math4.ode.nonstiff;

import org.apache.commons.math4.analysis.solvers.UnivariateSolver;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.linear.CholeskyDecomposition;
import org.apache.commons.math4.ode.events.EventHandler;
import org.apache.commons.math4.ode.sampling.StepHandler;
import org.apache.commons.math4.util.FastMath;

public class GraggBulirschStoerIntegrator extends AdaptiveStepsizeIntegrator {
    private static final String METHOD_NAME = "Gragg-Bulirsch-Stoer";
    private double[][] coeff;
    private int[] costPerStep;
    private double[] costPerTimeUnit;
    private int maxChecks;
    private int maxIter;
    private int maxOrder;
    private int mudif;
    private double[] optimalStep;
    private double orderControl1;
    private double orderControl2;
    private boolean performTest;
    private int[] sequence;
    private double stabilityReduction;
    private double stepControl1;
    private double stepControl2;
    private double stepControl3;
    private double stepControl4;
    private boolean useInterpolationError;

    public GraggBulirschStoerIntegrator(double minStep, double maxStep, double scalAbsoluteTolerance, double scalRelativeTolerance) {
        super(METHOD_NAME, minStep, maxStep, scalAbsoluteTolerance, scalRelativeTolerance);
        setStabilityCheck(true, -1, -1, -1.0d);
        setControlFactors(-1.0d, -1.0d, -1.0d, -1.0d);
        setOrderControl(-1, -1.0d, -1.0d);
        setInterpolationControl(true, -1);
    }

    public GraggBulirschStoerIntegrator(double minStep, double maxStep, double[] vecAbsoluteTolerance, double[] vecRelativeTolerance) {
        super(METHOD_NAME, minStep, maxStep, vecAbsoluteTolerance, vecRelativeTolerance);
        setStabilityCheck(true, -1, -1, -1.0d);
        setControlFactors(-1.0d, -1.0d, -1.0d, -1.0d);
        setOrderControl(-1, -1.0d, -1.0d);
        setInterpolationControl(true, -1);
    }

    public void setStabilityCheck(boolean performStabilityCheck, int maxNumIter, int maxNumChecks, double stepsizeReductionFactor) {
        this.performTest = performStabilityCheck;
        if (maxNumIter <= 0) {
            maxNumIter = 2;
        }
        this.maxIter = maxNumIter;
        if (maxNumChecks <= 0) {
            maxNumChecks = 1;
        }
        this.maxChecks = maxNumChecks;
        if (stepsizeReductionFactor < 1.0E-4d || stepsizeReductionFactor > 0.9999d) {
            this.stabilityReduction = 0.5d;
        } else {
            this.stabilityReduction = stepsizeReductionFactor;
        }
    }

    public void setControlFactors(double control1, double control2, double control3, double control4) {
        if (control1 < 1.0E-4d || control1 > 0.9999d) {
            this.stepControl1 = 0.65d;
        } else {
            this.stepControl1 = control1;
        }
        if (control2 < 1.0E-4d || control2 > 0.9999d) {
            this.stepControl2 = 0.94d;
        } else {
            this.stepControl2 = control2;
        }
        if (control3 < 1.0E-4d || control3 > 0.9999d) {
            this.stepControl3 = 0.02d;
        } else {
            this.stepControl3 = control3;
        }
        if (control4 < 1.0001d || control4 > 999.9d) {
            this.stepControl4 = 4.0d;
        } else {
            this.stepControl4 = control4;
        }
    }

    public void setOrderControl(int maximalOrder, double control1, double control2) {
        if (maximalOrder <= 6 || maximalOrder % 2 != 0) {
            this.maxOrder = 18;
        }
        if (control1 < 1.0E-4d || control1 > 0.9999d) {
            this.orderControl1 = 0.8d;
        } else {
            this.orderControl1 = control1;
        }
        if (control2 < 1.0E-4d || control2 > 0.9999d) {
            this.orderControl2 = 0.9d;
        } else {
            this.orderControl2 = control2;
        }
        initializeArrays();
    }

    public void addStepHandler(StepHandler handler) {
        super.addStepHandler(handler);
        initializeArrays();
    }

    public void addEventHandler(EventHandler function, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
        super.addEventHandler(function, maxCheckInterval, convergence, maxIterationCount, solver);
        initializeArrays();
    }

    private void initializeArrays() {
        int k;
        int size = this.maxOrder / 2;
        if (this.sequence == null || this.sequence.length != size) {
            this.sequence = new int[size];
            this.costPerStep = new int[size];
            this.coeff = new double[size][];
            this.costPerTimeUnit = new double[size];
            this.optimalStep = new double[size];
        }
        for (k = 0; k < size; k++) {
            this.sequence[k] = (k * 4) + 2;
        }
        this.costPerStep[0] = this.sequence[0] + 1;
        for (k = 1; k < size; k++) {
            this.costPerStep[k] = this.costPerStep[k - 1] + this.sequence[k];
        }
        k = 0;
        while (k < size) {
            this.coeff[k] = k > 0 ? new double[k] : null;
            for (int l = 0; l < k; l++) {
                double ratio = ((double) this.sequence[k]) / ((double) this.sequence[(k - l) - 1]);
                this.coeff[k][l] = 1.0d / ((ratio * ratio) - 1.0d);
            }
            k++;
        }
    }

    public void setInterpolationControl(boolean useInterpolationErrorForControl, int mudifControlParameter) {
        this.useInterpolationError = useInterpolationErrorForControl;
        if (mudifControlParameter <= 0 || mudifControlParameter >= 7) {
            this.mudif = 4;
        } else {
            this.mudif = mudifControlParameter;
        }
    }

    private void rescale(double[] y1, double[] y2, double[] scale) {
        int i;
        if (this.vecAbsoluteTolerance == null) {
            for (i = 0; i < scale.length; i++) {
                scale[i] = this.scalAbsoluteTolerance + (this.scalRelativeTolerance * FastMath.max(FastMath.abs(y1[i]), FastMath.abs(y2[i])));
            }
            return;
        }
        for (i = 0; i < scale.length; i++) {
            scale[i] = this.vecAbsoluteTolerance[i] + (this.vecRelativeTolerance[i] * FastMath.max(FastMath.abs(y1[i]), FastMath.abs(y2[i])));
        }
    }

    private boolean tryStep(double t0, double[] y0, double step, int k, double[] scale, double[][] f, double[] yMiddle, double[] yEnd, double[] yTmp) throws MaxCountExceededException, DimensionMismatchException {
        int n = this.sequence[k];
        double subStep = step / ((double) n);
        double subStep2 = 2.0d * subStep;
        double t = t0 + subStep;
        int i = 0;
        while (true) {
            int length = y0.length;
            if (i >= r0) {
                break;
            }
            yTmp[i] = y0[i];
            yEnd[i] = y0[i] + (f[0][i] * subStep);
            i++;
        }
        computeDerivatives(t, yEnd, f[1]);
        for (int j = 1; j < n; j++) {
            if (j * 2 == n) {
                System.arraycopy(yEnd, 0, yMiddle, 0, y0.length);
            }
            t += subStep;
            i = 0;
            while (true) {
                length = y0.length;
                if (i >= r0) {
                    break;
                }
                double middle = yEnd[i];
                yEnd[i] = yTmp[i] + (f[j][i] * subStep2);
                yTmp[i] = middle;
                i++;
            }
            computeDerivatives(t, yEnd, f[j + 1]);
            if (this.performTest) {
                length = this.maxChecks;
                if (j <= r0 && k < this.maxIter) {
                    double ratio;
                    double initialNorm = 0.0d;
                    int l = 0;
                    while (true) {
                        length = scale.length;
                        if (l >= r0) {
                            break;
                        }
                        ratio = f[0][l] / scale[l];
                        initialNorm += ratio * ratio;
                        l++;
                    }
                    double deltaNorm = 0.0d;
                    l = 0;
                    while (true) {
                        length = scale.length;
                        if (l >= r0) {
                            break;
                        }
                        ratio = (f[j + 1][l] - f[0][l]) / scale[l];
                        deltaNorm += ratio * ratio;
                        l++;
                    }
                    if (deltaNorm > 4.0d * FastMath.max((double) CholeskyDecomposition.DEFAULT_RELATIVE_SYMMETRY_THRESHOLD, initialNorm)) {
                        return false;
                    }
                }
            }
        }
        i = 0;
        while (true) {
            length = y0.length;
            if (i >= r0) {
                return true;
            }
            yEnd[i] = 0.5d * ((yTmp[i] + yEnd[i]) + (f[n][i] * subStep));
            i++;
        }
    }

    private void extrapolate(int offset, int k, double[][] diag, double[] last) {
        for (int j = 1; j < k; j++) {
            int i;
            for (i = 0; i < last.length; i++) {
                diag[(k - j) - 1][i] = diag[k - j][i] + (this.coeff[k + offset][j - 1] * (diag[k - j][i] - diag[(k - j) - 1][i]));
            }
        }
        for (i = 0; i < last.length; i++) {
            last[i] = diag[0][i] + (this.coeff[k + offset][k - 1] * (diag[0][i] - last[i]));
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void integrate(org.apache.commons.math4.ode.ExpandableStatefulODE r87, double r88) throws org.apache.commons.math4.exception.NumberIsTooSmallException, org.apache.commons.math4.exception.DimensionMismatchException, org.apache.commons.math4.exception.MaxCountExceededException, org.apache.commons.math4.exception.NoBracketingException {
        /*
        r86 = this;
        r86.sanityChecks(r87, r88);
        r86.setEquations(r87);
        r12 = r87.getTime();
        r11 = (r88 > r12 ? 1 : (r88 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x02a6;
    L_0x000e:
        r10 = 1;
    L_0x000f:
        r15 = r87.getCompleteState();
        r5 = r15.clone();
        r5 = (double[]) r5;
        r11 = r5.length;
        r6 = new double[r11];
        r11 = r5.length;
        r7 = new double[r11];
        r11 = r5.length;
        r0 = new double[r11];
        r24 = r0;
        r11 = r5.length;
        r0 = new double[r11];
        r25 = r0;
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r11 = r11 + -1;
        r0 = new double[r11][];
        r38 = r0;
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r11 = r11 + -1;
        r0 = new double[r11][];
        r84 = r0;
        r32 = 0;
    L_0x0041:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r11 = r11 + -1;
        r0 = r32;
        if (r0 < r11) goto L_0x02a9;
    L_0x004c:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r0 = new double[r11][][];
        r51 = r0;
        r32 = 0;
    L_0x0057:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r0 = r32;
        if (r0 < r11) goto L_0x02b7;
    L_0x0060:
        if (r5 == r15) goto L_0x0068;
    L_0x0062:
        r11 = 0;
        r12 = 0;
        r13 = r15.length;
        java.lang.System.arraycopy(r15, r11, r5, r12, r13);
    L_0x0068:
        r11 = r15.length;
        r8 = new double[r11];
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r11 = r11 * 2;
        r11 = r11 + 1;
        r12 = r15.length;
        r11 = new int[]{r11, r12};
        r12 = java.lang.Double.TYPE;
        r9 = java.lang.reflect.Array.newInstance(r12, r11);
        r9 = (double[][]) r9;
        r0 = r86;
        r11 = r0.mainSetDimension;
        r0 = new double[r11];
        r19 = r0;
        r0 = r86;
        r1 = r19;
        r0.rescale(r5, r5, r1);
        r0 = r86;
        r11 = r0.vecRelativeTolerance;
        if (r11 != 0) goto L_0x02e4;
    L_0x0096:
        r0 = r86;
        r0 = r0.scalRelativeTolerance;
        r82 = r0;
    L_0x009c:
        r12 = 4457293557087583675; // 0x3ddb7cdfd9d7bdbb float:-7.5907164E15 double:1.0E-10;
        r0 = r82;
        r12 = org.apache.commons.math4.util.FastMath.max(r12, r0);
        r64 = org.apache.commons.math4.util.FastMath.log10(r12);
        r11 = 1;
        r0 = r86;
        r12 = r0.sequence;
        r12 = r12.length;
        r12 = r12 + -2;
        r16 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r20 = 4603579539098121011; // 0x3fe3333333333333 float:4.172325E-8 double:0.6;
        r20 = r20 * r64;
        r16 = r16 - r20;
        r16 = org.apache.commons.math4.util.FastMath.floor(r16);
        r0 = r16;
        r13 = (int) r0;
        r12 = org.apache.commons.math4.util.FastMath.min(r12, r13);
        r81 = org.apache.commons.math4.util.FastMath.max(r11, r12);
        r4 = new org.apache.commons.math4.ode.nonstiff.GraggBulirschStoerStepInterpolator;
        r11 = r87.getPrimaryMapper();
        r12 = r87.getSecondaryMappers();
        r4.<init>(r5, r6, r7, r8, r9, r10, r11, r12);
        r12 = r87.getTime();
        r4.storeTime(r12);
        r12 = r87.getTime();
        r0 = r86;
        r0.stepStart = r12;
        r56 = 0;
        r68 = 9218868437227405311; // 0x7fefffffffffffff float:NaN double:1.7976931348623157E308;
        r75 = 0;
        r50 = 1;
        r71 = 1;
        r39 = 0;
        r13 = r87.getTime();
        r12 = r86;
        r16 = r88;
        r12.initIntegration(r13, r15, r16);
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = 0;
        r16 = 0;
        r11[r12] = r16;
        r11 = 0;
        r0 = r86;
        r0.isLastStep = r11;
    L_0x0111:
        r80 = 0;
        if (r71 == 0) goto L_0x013d;
    L_0x0115:
        r4.shift();
        if (r39 != 0) goto L_0x0123;
    L_0x011a:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0.computeDerivatives(r12, r5, r6);
    L_0x0123:
        if (r50 == 0) goto L_0x013b;
    L_0x0125:
        r11 = r81 * 2;
        r18 = r11 + 1;
        r0 = r86;
        r0 = r0.stepStart;
        r20 = r0;
        r16 = r86;
        r17 = r10;
        r22 = r5;
        r23 = r6;
        r56 = r16.initializeStep(r17, r18, r19, r20, r22, r23, r24, r25);
    L_0x013b:
        r71 = 0;
    L_0x013d:
        r0 = r56;
        r2 = r86;
        r2.stepSize = r0;
        if (r10 == 0) goto L_0x0155;
    L_0x0145:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r12 = r12 + r16;
        r11 = (r12 > r88 ? 1 : (r12 == r88 ? 0 : -1));
        if (r11 > 0) goto L_0x0167;
    L_0x0155:
        if (r10 != 0) goto L_0x0171;
    L_0x0157:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r12 = r12 + r16;
        r11 = (r12 > r88 ? 1 : (r12 == r88 ? 0 : -1));
        if (r11 >= 0) goto L_0x0171;
    L_0x0167:
        r0 = r86;
        r12 = r0.stepStart;
        r12 = r88 - r12;
        r0 = r86;
        r0.stepSize = r12;
    L_0x0171:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r72 = r12 + r16;
        if (r10 == 0) goto L_0x02f0;
    L_0x017f:
        r11 = (r72 > r88 ? 1 : (r72 == r88 ? 0 : -1));
        if (r11 < 0) goto L_0x02ed;
    L_0x0183:
        r11 = 1;
    L_0x0184:
        r0 = r86;
        r0.isLastStep = r11;
        r32 = -1;
        r63 = 1;
    L_0x018c:
        if (r63 != 0) goto L_0x02fa;
    L_0x018e:
        if (r80 != 0) goto L_0x01a1;
    L_0x0190:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r12 = r12 + r16;
        r0 = r86;
        r0.computeDerivatives(r12, r7, r8);
    L_0x01a1:
        r54 = r86.getMaxStep();
        if (r80 != 0) goto L_0x0211;
    L_0x01a7:
        r60 = 1;
    L_0x01a9:
        r0 = r60;
        r1 = r32;
        if (r0 <= r1) goto L_0x053f;
    L_0x01af:
        r11 = r32 * 2;
        r0 = r86;
        r12 = r0.mudif;
        r11 = r11 - r12;
        r70 = r11 + 3;
        r61 = 0;
    L_0x01ba:
        r0 = r61;
        r1 = r70;
        if (r0 < r1) goto L_0x0550;
    L_0x01c0:
        if (r70 < 0) goto L_0x0211;
    L_0x01c2:
        r52 = r4;
        r52 = (org.apache.commons.math4.ode.nonstiff.GraggBulirschStoerStepInterpolator) r52;
        r0 = r86;
        r12 = r0.stepSize;
        r0 = r52;
        r1 = r70;
        r0.computeCoefficients(r1, r12);
        r0 = r86;
        r11 = r0.useInterpolationError;
        if (r11 == 0) goto L_0x0211;
    L_0x01d7:
        r0 = r52;
        r1 = r19;
        r58 = r0.estimateError(r1);
        r0 = r86;
        r12 = r0.stepSize;
        r16 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = r70 + 4;
        r0 = (double) r11;
        r20 = r0;
        r16 = r16 / r20;
        r0 = r58;
        r2 = r16;
        r16 = org.apache.commons.math4.util.FastMath.pow(r0, r2);
        r20 = 4576918229304087675; // 0x3f847ae147ae147b float:89128.96 double:0.01;
        r0 = r16;
        r2 = r20;
        r16 = org.apache.commons.math4.util.FastMath.max(r0, r2);
        r12 = r12 / r16;
        r54 = org.apache.commons.math4.util.FastMath.abs(r12);
        r12 = 4621819117588971520; // 0x4024000000000000 float:0.0 double:10.0;
        r11 = (r58 > r12 ? 1 : (r58 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x0211;
    L_0x020d:
        r56 = r54;
        r80 = 1;
    L_0x0211:
        if (r80 != 0) goto L_0x0274;
    L_0x0213:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r12 = r12 + r16;
        r4.storeTime(r12);
        r26 = r86;
        r27 = r4;
        r28 = r7;
        r29 = r8;
        r30 = r88;
        r12 = r26.acceptStep(r27, r28, r29, r30);
        r0 = r86;
        r0.stepStart = r12;
        r0 = r86;
        r12 = r0.stepStart;
        r4.storeTime(r12);
        r11 = 0;
        r12 = 0;
        r13 = r15.length;
        java.lang.System.arraycopy(r7, r11, r5, r12, r13);
        r11 = 0;
        r12 = 0;
        r13 = r15.length;
        java.lang.System.arraycopy(r8, r11, r6, r12, r13);
        r39 = 1;
        r11 = 1;
        r0 = r32;
        if (r0 != r11) goto L_0x062f;
    L_0x024e:
        r74 = 2;
        if (r75 == 0) goto L_0x0254;
    L_0x0252:
        r74 = 1;
    L_0x0254:
        if (r75 == 0) goto L_0x06ca;
    L_0x0256:
        r0 = r74;
        r1 = r32;
        r81 = org.apache.commons.math4.util.FastMath.min(r0, r1);
        r0 = r86;
        r12 = r0.stepSize;
        r12 = org.apache.commons.math4.util.FastMath.abs(r12);
        r0 = r86;
        r11 = r0.optimalStep;
        r16 = r11[r81];
        r0 = r16;
        r56 = org.apache.commons.math4.util.FastMath.min(r12, r0);
    L_0x0272:
        r71 = 1;
    L_0x0274:
        r0 = r56;
        r2 = r54;
        r56 = org.apache.commons.math4.util.FastMath.min(r0, r2);
        if (r10 != 0) goto L_0x0283;
    L_0x027e:
        r0 = r56;
        r0 = -r0;
        r56 = r0;
    L_0x0283:
        r50 = 0;
        if (r80 == 0) goto L_0x0744;
    L_0x0287:
        r11 = 0;
        r0 = r86;
        r0.isLastStep = r11;
        r75 = 1;
    L_0x028e:
        r0 = r86;
        r11 = r0.isLastStep;
        if (r11 == 0) goto L_0x0111;
    L_0x0294:
        r0 = r86;
        r12 = r0.stepStart;
        r0 = r87;
        r0.setTime(r12);
        r0 = r87;
        r0.setCompleteState(r5);
        r86.resetInternalState();
        return;
    L_0x02a6:
        r10 = 0;
        goto L_0x000f;
    L_0x02a9:
        r11 = r5.length;
        r11 = new double[r11];
        r38[r32] = r11;
        r11 = r5.length;
        r11 = new double[r11];
        r84[r32] = r11;
        r32 = r32 + 1;
        goto L_0x0041;
    L_0x02b7:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11[r32];
        r11 = r11 + 1;
        r11 = new double[r11][];
        r51[r32] = r11;
        r11 = r51[r32];
        r12 = 0;
        r11[r12] = r6;
        r61 = 0;
    L_0x02ca:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11[r32];
        r0 = r61;
        if (r0 < r11) goto L_0x02d8;
    L_0x02d4:
        r32 = r32 + 1;
        goto L_0x0057;
    L_0x02d8:
        r11 = r51[r32];
        r12 = r61 + 1;
        r13 = r15.length;
        r13 = new double[r13];
        r11[r12] = r13;
        r61 = r61 + 1;
        goto L_0x02ca;
    L_0x02e4:
        r0 = r86;
        r11 = r0.vecRelativeTolerance;
        r12 = 0;
        r82 = r11[r12];
        goto L_0x009c;
    L_0x02ed:
        r11 = 0;
        goto L_0x0184;
    L_0x02f0:
        r11 = (r72 > r88 ? 1 : (r72 == r88 ? 0 : -1));
        if (r11 > 0) goto L_0x02f7;
    L_0x02f4:
        r11 = 1;
        goto L_0x0184;
    L_0x02f7:
        r11 = 0;
        goto L_0x0184;
    L_0x02fa:
        r32 = r32 + 1;
        r0 = r86;
        r0 = r0.stepStart;
        r27 = r0;
        r0 = r86;
        r0 = r0.stepSize;
        r30 = r0;
        r34 = r51[r32];
        if (r32 != 0) goto L_0x033e;
    L_0x030c:
        r11 = 0;
        r35 = r9[r11];
    L_0x030f:
        if (r32 != 0) goto L_0x0343;
    L_0x0311:
        r36 = r7;
    L_0x0313:
        r26 = r86;
        r29 = r5;
        r33 = r19;
        r37 = r24;
        r11 = r26.tryStep(r27, r29, r30, r32, r33, r34, r35, r36, r37);
        if (r11 != 0) goto L_0x0348;
    L_0x0321:
        r0 = r86;
        r12 = r0.stepSize;
        r0 = r86;
        r0 = r0.stabilityReduction;
        r16 = r0;
        r12 = r12 * r16;
        r11 = 0;
        r0 = r86;
        r12 = r0.filterStep(r12, r10, r11);
        r56 = org.apache.commons.math4.util.FastMath.abs(r12);
        r80 = 1;
        r63 = 0;
        goto L_0x018c;
    L_0x033e:
        r11 = r32 + -1;
        r35 = r38[r11];
        goto L_0x030f;
    L_0x0343:
        r11 = r32 + -1;
        r36 = r84[r11];
        goto L_0x0313;
    L_0x0348:
        if (r32 <= 0) goto L_0x018c;
    L_0x034a:
        r11 = 0;
        r0 = r86;
        r1 = r32;
        r2 = r84;
        r0.extrapolate(r11, r1, r2, r7);
        r0 = r86;
        r1 = r19;
        r0.rescale(r5, r7, r1);
        r42 = 0;
        r60 = 0;
    L_0x035f:
        r0 = r86;
        r11 = r0.mainSetDimension;
        r0 = r60;
        if (r0 < r11) goto L_0x03a1;
    L_0x0367:
        r0 = r86;
        r11 = r0.mainSetDimension;
        r12 = (double) r11;
        r12 = r42 / r12;
        r42 = org.apache.commons.math4.util.FastMath.sqrt(r12);
        r12 = 4831355200913801216; // 0x430c6bf526340000 float:6.2450045E-16 double:1.0E15;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 > 0) goto L_0x0384;
    L_0x037b:
        r11 = 1;
        r0 = r32;
        if (r0 <= r11) goto L_0x03b9;
    L_0x0380:
        r11 = (r42 > r68 ? 1 : (r42 == r68 ? 0 : -1));
        if (r11 <= 0) goto L_0x03b9;
    L_0x0384:
        r0 = r86;
        r12 = r0.stepSize;
        r0 = r86;
        r0 = r0.stabilityReduction;
        r16 = r0;
        r12 = r12 * r16;
        r11 = 0;
        r0 = r86;
        r12 = r0.filterStep(r12, r10, r11);
        r56 = org.apache.commons.math4.util.FastMath.abs(r12);
        r80 = 1;
        r63 = 0;
        goto L_0x018c;
    L_0x03a1:
        r12 = r7[r60];
        r11 = 0;
        r11 = r84[r11];
        r16 = r11[r60];
        r12 = r12 - r16;
        r12 = org.apache.commons.math4.util.FastMath.abs(r12);
        r16 = r19[r60];
        r40 = r12 / r16;
        r12 = r40 * r40;
        r42 = r42 + r12;
        r60 = r60 + 1;
        goto L_0x035f;
    L_0x03b9:
        r12 = 4616189618054758400; // 0x4010000000000000 float:0.0 double:4.0;
        r12 = r12 * r42;
        r16 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r0 = r16;
        r68 = org.apache.commons.math4.util.FastMath.max(r12, r0);
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = r32 * 2;
        r11 = r11 + 1;
        r0 = (double) r11;
        r16 = r0;
        r44 = r12 / r16;
        r0 = r86;
        r12 = r0.stepControl2;
        r0 = r86;
        r0 = r0.stepControl1;
        r16 = r0;
        r16 = r42 / r16;
        r0 = r16;
        r2 = r44;
        r16 = org.apache.commons.math4.util.FastMath.pow(r0, r2);
        r46 = r12 / r16;
        r0 = r86;
        r12 = r0.stepControl3;
        r0 = r44;
        r76 = org.apache.commons.math4.util.FastMath.pow(r12, r0);
        r0 = r86;
        r12 = r0.stepControl4;
        r12 = r76 / r12;
        r16 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r16 = r16 / r76;
        r0 = r16;
        r2 = r46;
        r16 = org.apache.commons.math4.util.FastMath.min(r0, r2);
        r0 = r16;
        r46 = org.apache.commons.math4.util.FastMath.max(r12, r0);
        r0 = r86;
        r11 = r0.optimalStep;
        r0 = r86;
        r12 = r0.stepSize;
        r12 = r12 * r46;
        r14 = 1;
        r0 = r86;
        r12 = r0.filterStep(r12, r10, r14);
        r12 = org.apache.commons.math4.util.FastMath.abs(r12);
        r11[r32] = r12;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r0 = r86;
        r12 = r0.costPerStep;
        r12 = r12[r32];
        r12 = (double) r12;
        r0 = r86;
        r14 = r0.optimalStep;
        r16 = r14[r32];
        r12 = r12 / r16;
        r11[r32] = r12;
        r11 = r32 - r81;
        switch(r11) {
            case -1: goto L_0x044b;
            case 0: goto L_0x04ba;
            case 1: goto L_0x050c;
            default: goto L_0x0439;
        };
    L_0x0439:
        if (r50 != 0) goto L_0x0441;
    L_0x043b:
        r0 = r86;
        r11 = r0.isLastStep;
        if (r11 == 0) goto L_0x018c;
    L_0x0441:
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 > 0) goto L_0x018c;
    L_0x0447:
        r63 = 0;
        goto L_0x018c;
    L_0x044b:
        r11 = 1;
        r0 = r81;
        if (r0 <= r11) goto L_0x018c;
    L_0x0450:
        if (r75 != 0) goto L_0x018c;
    L_0x0452:
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 > 0) goto L_0x045c;
    L_0x0458:
        r63 = 0;
        goto L_0x018c;
    L_0x045c:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11[r81];
        r12 = (double) r11;
        r0 = r86;
        r11 = r0.sequence;
        r14 = r81 + 1;
        r11 = r11[r14];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 * r16;
        r0 = r86;
        r11 = r0.sequence;
        r14 = 0;
        r11 = r11[r14];
        r0 = r86;
        r14 = r0.sequence;
        r16 = 0;
        r14 = r14[r16];
        r11 = r11 * r14;
        r0 = (double) r11;
        r16 = r0;
        r78 = r12 / r16;
        r12 = r78 * r78;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x018c;
    L_0x048b:
        r80 = 1;
        r63 = 0;
        r81 = r32;
        r11 = 1;
        r0 = r81;
        if (r0 <= r11) goto L_0x04b2;
    L_0x0496:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r81 + -1;
        r12 = r11[r12];
        r0 = r86;
        r0 = r0.orderControl1;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r20 = r11[r81];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x04b2;
    L_0x04b0:
        r81 = r81 + -1;
    L_0x04b2:
        r0 = r86;
        r11 = r0.optimalStep;
        r56 = r11[r81];
        goto L_0x018c;
    L_0x04ba:
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 > 0) goto L_0x04c4;
    L_0x04c0:
        r63 = 0;
        goto L_0x018c;
    L_0x04c4:
        r0 = r86;
        r11 = r0.sequence;
        r12 = r32 + 1;
        r11 = r11[r12];
        r12 = (double) r11;
        r0 = r86;
        r11 = r0.sequence;
        r14 = 0;
        r11 = r11[r14];
        r0 = (double) r11;
        r16 = r0;
        r78 = r12 / r16;
        r12 = r78 * r78;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x018c;
    L_0x04df:
        r80 = 1;
        r63 = 0;
        r11 = 1;
        r0 = r81;
        if (r0 <= r11) goto L_0x0504;
    L_0x04e8:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r81 + -1;
        r12 = r11[r12];
        r0 = r86;
        r0 = r0.orderControl1;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r20 = r11[r81];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0504;
    L_0x0502:
        r81 = r81 + -1;
    L_0x0504:
        r0 = r86;
        r11 = r0.optimalStep;
        r56 = r11[r81];
        goto L_0x018c;
    L_0x050c:
        r12 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r11 = (r42 > r12 ? 1 : (r42 == r12 ? 0 : -1));
        if (r11 <= 0) goto L_0x053b;
    L_0x0512:
        r80 = 1;
        r11 = 1;
        r0 = r81;
        if (r0 <= r11) goto L_0x0535;
    L_0x0519:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r81 + -1;
        r12 = r11[r12];
        r0 = r86;
        r0 = r0.orderControl1;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r20 = r11[r81];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0535;
    L_0x0533:
        r81 = r81 + -1;
    L_0x0535:
        r0 = r86;
        r11 = r0.optimalStep;
        r56 = r11[r81];
    L_0x053b:
        r63 = 0;
        goto L_0x018c;
    L_0x053f:
        r11 = 0;
        r12 = 0;
        r12 = r9[r12];
        r0 = r86;
        r1 = r60;
        r2 = r38;
        r0.extrapolate(r11, r1, r2, r12);
        r60 = r60 + 1;
        goto L_0x01a9;
    L_0x0550:
        r62 = r61 / 2;
        r12 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11[r62];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 * r16;
        r0 = r61;
        r48 = org.apache.commons.math4.util.FastMath.pow(r12, r0);
        r11 = r51[r62];
        r11 = r11.length;
        r67 = r11 / 2;
        r53 = 0;
    L_0x056c:
        r11 = r15.length;
        r0 = r53;
        if (r0 < r11) goto L_0x058e;
    L_0x0571:
        r60 = 1;
    L_0x0573:
        r11 = r32 - r62;
        r0 = r60;
        if (r0 <= r11) goto L_0x05a1;
    L_0x0579:
        r53 = 0;
    L_0x057b:
        r11 = r15.length;
        r0 = r53;
        if (r0 < r11) goto L_0x05eb;
    L_0x0580:
        r11 = r61 + 1;
        r60 = r11 / 2;
    L_0x0584:
        r0 = r60;
        r1 = r32;
        if (r0 <= r1) goto L_0x05ff;
    L_0x058a:
        r61 = r61 + 1;
        goto L_0x01ba;
    L_0x058e:
        r11 = r61 + 1;
        r11 = r9[r11];
        r12 = r51[r62];
        r13 = r67 + r61;
        r12 = r12[r13];
        r12 = r12[r53];
        r12 = r12 * r48;
        r11[r53] = r12;
        r53 = r53 + 1;
        goto L_0x056c;
    L_0x05a1:
        r12 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r86;
        r11 = r0.sequence;
        r14 = r60 + r62;
        r11 = r11[r14];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 * r16;
        r0 = r61;
        r48 = org.apache.commons.math4.util.FastMath.pow(r12, r0);
        r11 = r62 + r60;
        r11 = r51[r11];
        r11 = r11.length;
        r67 = r11 / 2;
        r53 = 0;
    L_0x05bf:
        r11 = r15.length;
        r0 = r53;
        if (r0 < r11) goto L_0x05d6;
    L_0x05c4:
        r11 = r61 + 1;
        r11 = r9[r11];
        r0 = r86;
        r1 = r62;
        r2 = r60;
        r3 = r38;
        r0.extrapolate(r1, r2, r3, r11);
        r60 = r60 + 1;
        goto L_0x0573;
    L_0x05d6:
        r11 = r60 + -1;
        r11 = r38[r11];
        r12 = r62 + r60;
        r12 = r51[r12];
        r13 = r67 + r61;
        r12 = r12[r13];
        r12 = r12[r53];
        r12 = r12 * r48;
        r11[r53] = r12;
        r53 = r53 + 1;
        goto L_0x05bf;
    L_0x05eb:
        r11 = r61 + 1;
        r11 = r9[r11];
        r12 = r11[r53];
        r0 = r86;
        r0 = r0.stepSize;
        r16 = r0;
        r12 = r12 * r16;
        r11[r53] = r12;
        r53 = r53 + 1;
        goto L_0x057b;
    L_0x05ff:
        r11 = r51[r60];
        r11 = r11.length;
        r66 = r11 + -1;
    L_0x0604:
        r11 = r61 + 1;
        r11 = r11 * 2;
        r0 = r66;
        if (r0 >= r11) goto L_0x0610;
    L_0x060c:
        r60 = r60 + 1;
        goto L_0x0584;
    L_0x0610:
        r53 = 0;
    L_0x0612:
        r11 = r15.length;
        r0 = r53;
        if (r0 < r11) goto L_0x061a;
    L_0x0617:
        r66 = r66 + -1;
        goto L_0x0604;
    L_0x061a:
        r11 = r51[r60];
        r11 = r11[r66];
        r12 = r11[r53];
        r14 = r51[r60];
        r16 = r66 + -2;
        r14 = r14[r16];
        r16 = r14[r53];
        r12 = r12 - r16;
        r11[r53] = r12;
        r53 = r53 + 1;
        goto L_0x0612;
    L_0x062f:
        r0 = r32;
        r1 = r81;
        if (r0 > r1) goto L_0x067e;
    L_0x0635:
        r74 = r32;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r32 + -1;
        r12 = r11[r12];
        r0 = r86;
        r0 = r0.orderControl1;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r20 = r11[r32];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0655;
    L_0x0651:
        r74 = r32 + -1;
        goto L_0x0254;
    L_0x0655:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r11[r32];
        r0 = r86;
        r0 = r0.orderControl2;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r14 = r32 + -1;
        r20 = r11[r14];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0254;
    L_0x066f:
        r11 = r32 + 1;
        r0 = r86;
        r12 = r0.sequence;
        r12 = r12.length;
        r12 = r12 + -2;
        r74 = org.apache.commons.math4.util.FastMath.min(r11, r12);
        goto L_0x0254;
    L_0x067e:
        r74 = r32 + -1;
        r11 = 2;
        r0 = r32;
        if (r0 <= r11) goto L_0x06a3;
    L_0x0685:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r32 + -2;
        r12 = r11[r12];
        r0 = r86;
        r0 = r0.orderControl1;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r14 = r32 + -1;
        r20 = r11[r14];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x06a3;
    L_0x06a1:
        r74 = r32 + -2;
    L_0x06a3:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r11[r32];
        r0 = r86;
        r0 = r0.orderControl2;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r20 = r11[r74];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0254;
    L_0x06bb:
        r0 = r86;
        r11 = r0.sequence;
        r11 = r11.length;
        r11 = r11 + -2;
        r0 = r32;
        r74 = org.apache.commons.math4.util.FastMath.min(r0, r11);
        goto L_0x0254;
    L_0x06ca:
        r0 = r74;
        r1 = r32;
        if (r0 > r1) goto L_0x06da;
    L_0x06d0:
        r0 = r86;
        r11 = r0.optimalStep;
        r56 = r11[r74];
    L_0x06d6:
        r81 = r74;
        goto L_0x0272;
    L_0x06da:
        r0 = r32;
        r1 = r81;
        if (r0 >= r1) goto L_0x0720;
    L_0x06e0:
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r12 = r11[r32];
        r0 = r86;
        r0 = r0.orderControl2;
        r16 = r0;
        r0 = r86;
        r11 = r0.costPerTimeUnit;
        r14 = r32 + -1;
        r20 = r11[r14];
        r16 = r16 * r20;
        r11 = (r12 > r16 ? 1 : (r12 == r16 ? 0 : -1));
        if (r11 >= 0) goto L_0x0720;
    L_0x06fa:
        r0 = r86;
        r11 = r0.optimalStep;
        r12 = r11[r32];
        r0 = r86;
        r11 = r0.costPerStep;
        r14 = r74 + 1;
        r11 = r11[r14];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 * r16;
        r0 = r86;
        r11 = r0.costPerStep;
        r11 = r11[r32];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 / r16;
        r11 = 0;
        r0 = r86;
        r56 = r0.filterStep(r12, r10, r11);
        goto L_0x06d6;
    L_0x0720:
        r0 = r86;
        r11 = r0.optimalStep;
        r12 = r11[r32];
        r0 = r86;
        r11 = r0.costPerStep;
        r11 = r11[r74];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 * r16;
        r0 = r86;
        r11 = r0.costPerStep;
        r11 = r11[r32];
        r0 = (double) r11;
        r16 = r0;
        r12 = r12 / r16;
        r11 = 0;
        r0 = r86;
        r56 = r0.filterStep(r12, r10, r11);
        goto L_0x06d6;
    L_0x0744:
        r75 = 0;
        goto L_0x028e;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.ode.nonstiff.GraggBulirschStoerIntegrator.integrate(org.apache.commons.math4.ode.ExpandableStatefulODE, double):void");
    }
}
