package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math4.complex.Complex;
import org.apache.commons.math4.complex.ComplexUtils;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public class LaguerreSolver extends AbstractPolynomialSolver {
    private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;
    private final ComplexSolver complexSolver;

    private class ComplexSolver {
        private ComplexSolver() {
        }

        public boolean isRoot(double min, double max, Complex z) {
            if (!LaguerreSolver.this.isSequence(min, z.getReal(), max)) {
                return false;
            }
            if (FastMath.abs(z.getImaginary()) <= FastMath.max(LaguerreSolver.this.getRelativeAccuracy() * z.abs(), LaguerreSolver.this.getAbsoluteAccuracy()) || z.abs() <= LaguerreSolver.this.getFunctionValueAccuracy()) {
                return true;
            }
            return false;
        }

        public Complex[] solveAll(Complex[] coefficients, Complex initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
            if (coefficients == null) {
                throw new NullArgumentException();
            }
            int n = coefficients.length - 1;
            if (n == 0) {
                throw new NoDataException(LocalizedFormats.POLYNOMIAL);
            }
            int i;
            Complex[] c = new Complex[(n + 1)];
            for (i = 0; i <= n; i++) {
                c[i] = coefficients[i];
            }
            Complex[] root = new Complex[n];
            for (i = 0; i < n; i++) {
                Complex[] subarray = new Complex[((n - i) + 1)];
                System.arraycopy(c, 0, subarray, 0, subarray.length);
                root[i] = solve(subarray, initial);
                Complex newc = c[n - i];
                for (int j = (n - i) - 1; j >= 0; j--) {
                    Complex oldc = c[j];
                    c[j] = newc;
                    newc = oldc.add(newc.multiply(root[i]));
                }
            }
            return root;
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public org.apache.commons.math4.complex.Complex solve(org.apache.commons.math4.complex.Complex[] r37, org.apache.commons.math4.complex.Complex r38) throws org.apache.commons.math4.exception.NullArgumentException, org.apache.commons.math4.exception.NoDataException, org.apache.commons.math4.exception.TooManyEvaluationsException {
            /*
            r36 = this;
            if (r37 != 0) goto L_0x0008;
        L_0x0002:
            r31 = new org.apache.commons.math4.exception.NullArgumentException;
            r31.<init>();
            throw r31;
        L_0x0008:
            r0 = r37;
            r0 = r0.length;
            r31 = r0;
            r21 = r31 + -1;
            if (r21 != 0) goto L_0x0019;
        L_0x0011:
            r31 = new org.apache.commons.math4.exception.NoDataException;
            r32 = org.apache.commons.math4.exception.util.LocalizedFormats.POLYNOMIAL;
            r31.<init>(r32);
            throw r31;
        L_0x0019:
            r0 = r36;
            r0 = org.apache.commons.math4.analysis.solvers.LaguerreSolver.this;
            r31 = r0;
            r10 = r31.getAbsoluteAccuracy();
            r0 = r36;
            r0 = org.apache.commons.math4.analysis.solvers.LaguerreSolver.this;
            r31 = r0;
            r26 = r31.getRelativeAccuracy();
            r0 = r36;
            r0 = org.apache.commons.math4.analysis.solvers.LaguerreSolver.this;
            r31 = r0;
            r18 = r31.getFunctionValueAccuracy();
            r23 = new org.apache.commons.math4.complex.Complex;
            r0 = r21;
            r0 = (double) r0;
            r32 = r0;
            r34 = 0;
            r0 = r23;
            r1 = r32;
            r3 = r34;
            r0.<init>(r1, r3);
            r22 = new org.apache.commons.math4.complex.Complex;
            r31 = r21 + -1;
            r0 = r31;
            r0 = (double) r0;
            r32 = r0;
            r34 = 0;
            r0 = r22;
            r1 = r32;
            r3 = r34;
            r0.<init>(r1, r3);
            r30 = r38;
            r24 = new org.apache.commons.math4.complex.Complex;
            r32 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
            r34 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
            r0 = r24;
            r1 = r32;
            r3 = r34;
            r0.<init>(r1, r3);
        L_0x006e:
            r25 = r37[r21];
            r17 = org.apache.commons.math4.complex.Complex.ZERO;
            r9 = org.apache.commons.math4.complex.Complex.ZERO;
            r20 = r21 + -1;
        L_0x0076:
            if (r20 >= 0) goto L_0x00a4;
        L_0x0078:
            r31 = new org.apache.commons.math4.complex.Complex;
            r32 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
            r34 = 0;
            r31.<init>(r32, r34);
            r0 = r31;
            r9 = r9.multiply(r0);
            r32 = r30.abs();
            r32 = r32 * r26;
            r0 = r32;
            r28 = org.apache.commons.math4.util.FastMath.max(r0, r10);
            r0 = r30;
            r1 = r24;
            r31 = r0.subtract(r1);
            r32 = r31.abs();
            r31 = (r32 > r28 ? 1 : (r32 == r28 ? 0 : -1));
            if (r31 > 0) goto L_0x00d3;
        L_0x00a3:
            return r30;
        L_0x00a4:
            r0 = r30;
            r31 = r0.multiply(r9);
            r0 = r17;
            r1 = r31;
            r9 = r0.add(r1);
            r0 = r30;
            r1 = r17;
            r31 = r0.multiply(r1);
            r0 = r25;
            r1 = r31;
            r17 = r0.add(r1);
            r31 = r37[r20];
            r0 = r30;
            r1 = r25;
            r32 = r0.multiply(r1);
            r25 = r31.add(r32);
            r20 = r20 + -1;
            goto L_0x0076;
        L_0x00d3:
            r32 = r25.abs();
            r31 = (r32 > r18 ? 1 : (r32 == r18 ? 0 : -1));
            if (r31 <= 0) goto L_0x00a3;
        L_0x00db:
            r0 = r17;
            r1 = r25;
            r6 = r0.divide(r1);
            r7 = r6.multiply(r6);
            r0 = r25;
            r31 = r9.divide(r0);
            r0 = r31;
            r8 = r7.subtract(r0);
            r0 = r23;
            r31 = r0.multiply(r8);
            r0 = r31;
            r31 = r0.subtract(r7);
            r0 = r22;
            r1 = r31;
            r12 = r0.multiply(r1);
            r13 = r12.sqrt();
            r16 = r6.add(r13);
            r15 = r6.subtract(r13);
            r32 = r16.abs();
            r34 = r15.abs();
            r31 = (r32 > r34 ? 1 : (r32 == r34 ? 0 : -1));
            if (r31 <= 0) goto L_0x0157;
        L_0x011f:
            r14 = r16;
        L_0x0121:
            r31 = new org.apache.commons.math4.complex.Complex;
            r32 = 0;
            r34 = 0;
            r31.<init>(r32, r34);
            r0 = r31;
            r31 = r14.equals(r0);
            if (r31 == 0) goto L_0x0159;
        L_0x0132:
            r31 = new org.apache.commons.math4.complex.Complex;
            r0 = r31;
            r0.<init>(r10, r10);
            r30 = r30.add(r31);
            r24 = new org.apache.commons.math4.complex.Complex;
            r32 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
            r34 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
            r0 = r24;
            r1 = r32;
            r3 = r34;
            r0.<init>(r1, r3);
        L_0x014c:
            r0 = r36;
            r0 = org.apache.commons.math4.analysis.solvers.LaguerreSolver.this;
            r31 = r0;
            r31.incrementEvaluationCount();
            goto L_0x006e;
        L_0x0157:
            r14 = r15;
            goto L_0x0121;
        L_0x0159:
            r24 = r30;
            r0 = r23;
            r31 = r0.divide(r14);
            r30 = r30.subtract(r31);
            goto L_0x014c;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.analysis.solvers.LaguerreSolver.ComplexSolver.solve(org.apache.commons.math4.complex.Complex[], org.apache.commons.math4.complex.Complex):org.apache.commons.math4.complex.Complex");
        }
    }

    public LaguerreSolver() {
        this(DEFAULT_ABSOLUTE_ACCURACY);
    }

    public LaguerreSolver(double absoluteAccuracy) {
        super(absoluteAccuracy);
        this.complexSolver = new ComplexSolver();
    }

    public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy) {
        super(relativeAccuracy, absoluteAccuracy);
        this.complexSolver = new ComplexSolver();
    }

    public LaguerreSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
        this.complexSolver = new ComplexSolver();
    }

    public double doSolve() throws TooManyEvaluationsException, NumberIsTooLargeException, NoBracketingException {
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
            return laguerre(min, initial, yMin, yInitial);
        }
        double yMax = computeObjectiveValue(max);
        if (FastMath.abs(yMax) <= functionValueAccuracy) {
            return max;
        }
        if (yInitial * yMax < 0.0d) {
            return laguerre(initial, max, yInitial, yMax);
        }
        throw new NoBracketingException(min, max, yMin, yMax);
    }

    private double laguerre(double lo, double hi, double fLo, double fHi) {
        Complex[] c = ComplexUtils.convertToComplex(getCoefficients());
        Complex complex = new Complex(0.5d * (lo + hi), 0.0d);
        Complex z = this.complexSolver.solve(c, complex);
        if (this.complexSolver.isRoot(lo, hi, z)) {
            return z.getReal();
        }
        Complex[] root = this.complexSolver.solveAll(c, complex);
        for (int i = 0; i < root.length; i++) {
            if (this.complexSolver.isRoot(lo, hi, root[i])) {
                return root[i].getReal();
            }
        }
        return Double.NaN;
    }

    public Complex[] solveAllComplex(double[] coefficients, double initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
        setup((int) BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, new PolynomialFunction(coefficients), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, initial);
        return this.complexSolver.solveAll(ComplexUtils.convertToComplex(coefficients), new Complex(initial, 0.0d));
    }

    public Complex solveComplex(double[] coefficients, double initial) throws NullArgumentException, NoDataException, TooManyEvaluationsException {
        setup((int) BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT, new PolynomialFunction(coefficients), Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, initial);
        return this.complexSolver.solve(ComplexUtils.convertToComplex(coefficients), new Complex(initial, 0.0d));
    }
}
