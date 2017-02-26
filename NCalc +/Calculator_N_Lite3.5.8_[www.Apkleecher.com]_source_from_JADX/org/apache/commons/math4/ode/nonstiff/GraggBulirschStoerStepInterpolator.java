package org.apache.commons.math4.ode.nonstiff;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.sampling.AbstractStepInterpolator;
import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

class GraggBulirschStoerStepInterpolator extends AbstractStepInterpolator {
    private static final long serialVersionUID = 20110928;
    private int currentDegree;
    private double[] errfac;
    private double[][] polynomials;
    private double[] y0Dot;
    private double[] y1;
    private double[] y1Dot;
    private double[][] yMidDots;

    public GraggBulirschStoerStepInterpolator() {
        this.y0Dot = null;
        this.y1 = null;
        this.y1Dot = null;
        this.yMidDots = null;
        resetTables(-1);
    }

    public GraggBulirschStoerStepInterpolator(double[] y, double[] y0Dot, double[] y1, double[] y1Dot, double[][] yMidDots, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
        super(y, forward, primaryMapper, secondaryMappers);
        this.y0Dot = y0Dot;
        this.y1 = y1;
        this.y1Dot = y1Dot;
        this.yMidDots = yMidDots;
        resetTables(yMidDots.length + 4);
    }

    public GraggBulirschStoerStepInterpolator(GraggBulirschStoerStepInterpolator interpolator) {
        super(interpolator);
        int dimension = this.currentState.length;
        this.y0Dot = null;
        this.y1 = null;
        this.y1Dot = null;
        this.yMidDots = null;
        if (interpolator.polynomials == null) {
            this.polynomials = null;
            this.currentDegree = -1;
            return;
        }
        resetTables(interpolator.currentDegree);
        for (int i = 0; i < this.polynomials.length; i++) {
            this.polynomials[i] = new double[dimension];
            System.arraycopy(interpolator.polynomials[i], 0, this.polynomials[i], 0, dimension);
        }
        this.currentDegree = interpolator.currentDegree;
    }

    private void resetTables(int maxDegree) {
        if (maxDegree < 0) {
            this.polynomials = null;
            this.errfac = null;
            this.currentDegree = -1;
            return;
        }
        int i;
        double[][] newPols = new double[(maxDegree + 1)][];
        if (this.polynomials != null) {
            System.arraycopy(this.polynomials, 0, newPols, 0, this.polynomials.length);
            for (i = this.polynomials.length; i < newPols.length; i++) {
                newPols[i] = new double[this.currentState.length];
            }
        } else {
            for (i = 0; i < newPols.length; i++) {
                newPols[i] = new double[this.currentState.length];
            }
        }
        this.polynomials = newPols;
        if (maxDegree <= 4) {
            this.errfac = null;
        } else {
            this.errfac = new double[(maxDegree - 4)];
            for (i = 0; i < this.errfac.length; i++) {
                int ip5 = i + 5;
                this.errfac[i] = 1.0d / ((double) (ip5 * ip5));
                double e = 0.5d * FastMath.sqrt(((double) (i + 1)) / ((double) ip5));
                for (int j = 0; j <= i; j++) {
                    double[] dArr = this.errfac;
                    dArr[i] = dArr[i] * (e / ((double) (j + 1)));
                }
            }
        }
        this.currentDegree = 0;
    }

    protected StepInterpolator doCopy() {
        return new GraggBulirschStoerStepInterpolator(this);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void computeCoefficients(int r37, double r38) {
        /*
        r36 = this;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        if (r26 == 0) goto L_0x001b;
    L_0x0008:
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r0 = r26;
        r0 = r0.length;
        r26 = r0;
        r27 = r37 + 4;
        r0 = r26;
        r1 = r27;
        if (r0 > r1) goto L_0x0024;
    L_0x001b:
        r26 = r37 + 4;
        r0 = r36;
        r1 = r26;
        r0.resetTables(r1);
    L_0x0024:
        r26 = r37 + 4;
        r0 = r26;
        r1 = r36;
        r1.currentDegree = r0;
        r10 = 0;
    L_0x002d:
        r0 = r36;
        r0 = r0.currentState;
        r26 = r0;
        r0 = r26;
        r0 = r0.length;
        r26 = r0;
        r0 = r26;
        if (r10 < r0) goto L_0x003d;
    L_0x003c:
        return;
    L_0x003d:
        r0 = r36;
        r0 = r0.y0Dot;
        r26 = r0;
        r26 = r26[r10];
        r22 = r38 * r26;
        r0 = r36;
        r0 = r0.y1Dot;
        r26 = r0;
        r26 = r26[r10];
        r24 = r38 * r26;
        r0 = r36;
        r0 = r0.y1;
        r26 = r0;
        r26 = r26[r10];
        r0 = r36;
        r0 = r0.currentState;
        r28 = r0;
        r28 = r28[r10];
        r20 = r26 - r28;
        r2 = r20 - r24;
        r4 = r22 - r20;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 0;
        r26 = r26[r27];
        r0 = r36;
        r0 = r0.currentState;
        r27 = r0;
        r28 = r27[r10];
        r26[r10] = r28;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 1;
        r26 = r26[r27];
        r26[r10] = r20;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 2;
        r26 = r26[r27];
        r26[r10] = r2;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 3;
        r26 = r26[r27];
        r26[r10] = r4;
        if (r37 < 0) goto L_0x003c;
    L_0x00a1:
        r26 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = r36;
        r0 = r0.currentState;
        r28 = r0;
        r28 = r28[r10];
        r0 = r36;
        r0 = r0.y1;
        r30 = r0;
        r30 = r30[r10];
        r28 = r28 + r30;
        r26 = r26 * r28;
        r28 = 4593671619917905920; // 0x3fc0000000000000 float:0.0 double:0.125;
        r30 = r2 + r4;
        r28 = r28 * r30;
        r12 = r26 + r28;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 4;
        r26 = r26[r27];
        r28 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r0 = r36;
        r0 = r0.yMidDots;
        r27 = r0;
        r30 = 0;
        r27 = r27[r30];
        r30 = r27[r10];
        r30 = r30 - r12;
        r28 = r28 * r30;
        r26[r10] = r28;
        if (r37 <= 0) goto L_0x017e;
    L_0x00df:
        r26 = 4598175219545276416; // 0x3fd0000000000000 float:0.0 double:0.25;
        r28 = r2 - r4;
        r26 = r26 * r28;
        r14 = r20 + r26;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 5;
        r26 = r26[r27];
        r28 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r0 = r36;
        r0 = r0.yMidDots;
        r27 = r0;
        r30 = 1;
        r27 = r27[r30];
        r30 = r27[r10];
        r30 = r30 - r14;
        r28 = r28 * r30;
        r26[r10] = r28;
        r26 = 1;
        r0 = r37;
        r1 = r26;
        if (r0 <= r1) goto L_0x017e;
    L_0x010d:
        r16 = r24 - r22;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 6;
        r26 = r26[r27];
        r28 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r0 = r36;
        r0 = r0.yMidDots;
        r27 = r0;
        r30 = 2;
        r27 = r27[r30];
        r30 = r27[r10];
        r30 = r30 - r16;
        r0 = r36;
        r0 = r0.polynomials;
        r27 = r0;
        r32 = 4;
        r27 = r27[r32];
        r32 = r27[r10];
        r30 = r30 + r32;
        r28 = r28 * r30;
        r26[r10] = r28;
        r26 = 2;
        r0 = r37;
        r1 = r26;
        if (r0 <= r1) goto L_0x017e;
    L_0x0143:
        r26 = 4618441417868443648; // 0x4018000000000000 float:0.0 double:6.0;
        r28 = r4 - r2;
        r18 = r26 * r28;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = 7;
        r26 = r26[r27];
        r28 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r0 = r36;
        r0 = r0.yMidDots;
        r27 = r0;
        r30 = 3;
        r27 = r27[r30];
        r30 = r27[r10];
        r30 = r30 - r18;
        r32 = 4613937818241073152; // 0x4008000000000000 float:0.0 double:3.0;
        r0 = r36;
        r0 = r0.polynomials;
        r27 = r0;
        r34 = 5;
        r27 = r27[r34];
        r34 = r27[r10];
        r32 = r32 * r34;
        r30 = r30 + r32;
        r28 = r28 * r30;
        r26[r10] = r28;
        r11 = 4;
    L_0x017a:
        r0 = r37;
        if (r11 <= r0) goto L_0x0182;
    L_0x017e:
        r10 = r10 + 1;
        goto L_0x002d;
    L_0x0182:
        r26 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r0 = (double) r11;
        r28 = r0;
        r26 = r26 * r28;
        r28 = r11 + -1;
        r0 = r28;
        r0 = (double) r0;
        r28 = r0;
        r6 = r26 * r28;
        r26 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r26 = r26 * r6;
        r28 = r11 + -2;
        r0 = r28;
        r0 = (double) r0;
        r28 = r0;
        r26 = r26 * r28;
        r28 = r11 + -3;
        r0 = r28;
        r0 = (double) r0;
        r28 = r0;
        r8 = r26 * r28;
        r0 = r36;
        r0 = r0.polynomials;
        r26 = r0;
        r27 = r11 + 4;
        r26 = r26[r27];
        r28 = 4625196817309499392; // 0x4030000000000000 float:0.0 double:16.0;
        r0 = r36;
        r0 = r0.yMidDots;
        r27 = r0;
        r27 = r27[r11];
        r30 = r27[r10];
        r0 = r36;
        r0 = r0.polynomials;
        r27 = r0;
        r32 = r11 + 2;
        r27 = r27[r32];
        r32 = r27[r10];
        r32 = r32 * r6;
        r30 = r30 + r32;
        r0 = r36;
        r0 = r0.polynomials;
        r27 = r0;
        r27 = r27[r11];
        r32 = r27[r10];
        r32 = r32 * r8;
        r30 = r30 - r32;
        r28 = r28 * r30;
        r26[r10] = r28;
        r11 = r11 + 1;
        goto L_0x017a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.ode.nonstiff.GraggBulirschStoerStepInterpolator.computeCoefficients(int, double):void");
    }

    public double estimateError(double[] scale) {
        double error = 0.0d;
        if (this.currentDegree < 5) {
            return 0.0d;
        }
        for (int i = 0; i < scale.length; i++) {
            double e = this.polynomials[this.currentDegree][i] / scale[i];
            error += e * e;
        }
        return FastMath.sqrt(error / ((double) scale.length)) * this.errfac[this.currentDegree - 5];
    }

    protected void computeInterpolatedStateAndDerivatives(double theta, double oneMinusThetaH) {
        int dimension = this.currentState.length;
        double oneMinusTheta = 1.0d - theta;
        double theta05 = theta - 0.5d;
        double tOmT = theta * oneMinusTheta;
        double t4 = tOmT * tOmT;
        double t4Dot = (2.0d * tOmT) * (1.0d - (2.0d * theta));
        double dot1 = 1.0d / this.h;
        double dot2 = ((2.0d - (3.0d * theta)) * theta) / this.h;
        double dot3 = ((((3.0d * theta) - 4.0d) * theta) + 1.0d) / this.h;
        for (int i = 0; i < dimension; i++) {
            double p0 = this.polynomials[0][i];
            double p1 = this.polynomials[1][i];
            double p2 = this.polynomials[2][i];
            double p3 = this.polynomials[3][i];
            this.interpolatedState[i] = (((((p2 * theta) + (p3 * oneMinusTheta)) * oneMinusTheta) + p1) * theta) + p0;
            this.interpolatedDerivatives[i] = ((dot1 * p1) + (dot2 * p2)) + (dot3 * p3);
            int i2 = this.currentDegree;
            if (r0 > 3) {
                double cDot = 0.0d;
                double c = this.polynomials[this.currentDegree][i];
                for (int j = this.currentDegree - 1; j > 3; j--) {
                    double d = 1.0d / ((double) (j - 3));
                    cDot = d * ((theta05 * cDot) + c);
                    c = this.polynomials[j][i] + ((c * d) * theta05);
                }
                double[] dArr = this.interpolatedState;
                dArr[i] = dArr[i] + (t4 * c);
                dArr = this.interpolatedDerivatives;
                dArr[i] = dArr[i] + (((t4 * cDot) + (t4Dot * c)) / this.h);
            }
        }
        if (this.h == 0.0d) {
            System.arraycopy(this.yMidDots[1], 0, this.interpolatedDerivatives, 0, dimension);
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int dimension = this.currentState == null ? -1 : this.currentState.length;
        writeBaseExternal(out);
        out.writeInt(this.currentDegree);
        for (int k = 0; k <= this.currentDegree; k++) {
            for (int l = 0; l < dimension; l++) {
                out.writeDouble(this.polynomials[k][l]);
            }
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        double t = readBaseExternal(in);
        int dimension = this.currentState == null ? -1 : this.currentState.length;
        int degree = in.readInt();
        resetTables(degree);
        this.currentDegree = degree;
        for (int k = 0; k <= this.currentDegree; k++) {
            for (int l = 0; l < dimension; l++) {
                this.polynomials[k][l] = in.readDouble();
            }
        }
        setInterpolatedTime(t);
    }
}
