package org.apache.commons.math4.analysis.differentiation;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.UnivariateMatrixFunction;
import org.apache.commons.math4.analysis.UnivariateVectorFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.util.FastMath;

public class FiniteDifferencesDifferentiator implements UnivariateFunctionDifferentiator, UnivariateVectorFunctionDifferentiator, UnivariateMatrixFunctionDifferentiator, Serializable {
    private static final long serialVersionUID = 20120917;
    private final double halfSampleSpan;
    private final int nbPoints;
    private final double stepSize;
    private final double tMax;
    private final double tMin;

    class 1 implements UnivariateDifferentiableFunction {
        private final /* synthetic */ UnivariateFunction val$function;

        1(UnivariateFunction univariateFunction) {
            this.val$function = univariateFunction;
        }

        public double value(double x) throws MathIllegalArgumentException {
            return this.val$function.value(x);
        }

        public DerivativeStructure value(DerivativeStructure t) throws MathIllegalArgumentException {
            if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
                throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
            }
            double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
            double[] y = new double[FiniteDifferencesDifferentiator.this.nbPoints];
            for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
                y[i] = this.val$function.value((((double) i) * FiniteDifferencesDifferentiator.this.stepSize) + t0);
            }
            return FiniteDifferencesDifferentiator.this.evaluate(t, t0, y);
        }
    }

    class 2 implements UnivariateDifferentiableVectorFunction {
        private final /* synthetic */ UnivariateVectorFunction val$function;

        2(UnivariateVectorFunction univariateVectorFunction) {
            this.val$function = univariateVectorFunction;
        }

        public double[] value(double x) throws MathIllegalArgumentException {
            return this.val$function.value(x);
        }

        public DerivativeStructure[] value(DerivativeStructure t) throws MathIllegalArgumentException {
            if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
                throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
            }
            int j;
            double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
            double[][] y = null;
            for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
                double[] v = this.val$function.value((((double) i) * FiniteDifferencesDifferentiator.this.stepSize) + t0);
                if (i == 0) {
                    y = (double[][]) Array.newInstance(Double.TYPE, new int[]{v.length, FiniteDifferencesDifferentiator.this.nbPoints});
                }
                for (j = 0; j < v.length; j++) {
                    y[j][i] = v[j];
                }
            }
            DerivativeStructure[] value = new DerivativeStructure[y.length];
            for (j = 0; j < value.length; j++) {
                value[j] = FiniteDifferencesDifferentiator.this.evaluate(t, t0, y[j]);
            }
            return value;
        }
    }

    class 3 implements UnivariateDifferentiableMatrixFunction {
        private final /* synthetic */ UnivariateMatrixFunction val$function;

        3(UnivariateMatrixFunction univariateMatrixFunction) {
            this.val$function = univariateMatrixFunction;
        }

        public double[][] value(double x) throws MathIllegalArgumentException {
            return this.val$function.value(x);
        }

        public DerivativeStructure[][] value(DerivativeStructure t) throws MathIllegalArgumentException {
            if (t.getOrder() >= FiniteDifferencesDifferentiator.this.nbPoints) {
                throw new NumberIsTooLargeException(Integer.valueOf(t.getOrder()), Integer.valueOf(FiniteDifferencesDifferentiator.this.nbPoints), false);
            }
            int k;
            double t0 = FastMath.max(FastMath.min(t.getValue(), FiniteDifferencesDifferentiator.this.tMax), FiniteDifferencesDifferentiator.this.tMin) - FiniteDifferencesDifferentiator.this.halfSampleSpan;
            double[][][] y = null;
            for (int i = 0; i < FiniteDifferencesDifferentiator.this.nbPoints; i++) {
                int j;
                double[][] v = this.val$function.value((((double) i) * FiniteDifferencesDifferentiator.this.stepSize) + t0);
                if (i == 0) {
                    y = (double[][][]) Array.newInstance(Double.TYPE, new int[]{v.length, v[0].length, FiniteDifferencesDifferentiator.this.nbPoints});
                }
                for (j = 0; j < v.length; j++) {
                    for (k = 0; k < v[j].length; k++) {
                        y[j][k][i] = v[j][k];
                    }
                }
            }
            DerivativeStructure[][] value = (DerivativeStructure[][]) Array.newInstance(DerivativeStructure.class, new int[]{y.length, y[0].length});
            for (j = 0; j < value.length; j++) {
                for (k = 0; k < y[j].length; k++) {
                    value[j][k] = FiniteDifferencesDifferentiator.this.evaluate(t, t0, y[j][k]);
                }
            }
            return value;
        }
    }

    public FiniteDifferencesDifferentiator(int nbPoints, double stepSize) throws NotPositiveException, NumberIsTooSmallException {
        this(nbPoints, stepSize, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
    }

    public FiniteDifferencesDifferentiator(int nbPoints, double stepSize, double tLower, double tUpper) throws NotPositiveException, NumberIsTooSmallException, NumberIsTooLargeException {
        if (nbPoints <= 1) {
            throw new NumberIsTooSmallException(Double.valueOf(stepSize), Integer.valueOf(1), false);
        }
        this.nbPoints = nbPoints;
        if (stepSize <= 0.0d) {
            throw new NotPositiveException(Double.valueOf(stepSize));
        }
        this.stepSize = stepSize;
        this.halfSampleSpan = (0.5d * stepSize) * ((double) (nbPoints - 1));
        if (2.0d * this.halfSampleSpan >= tUpper - tLower) {
            throw new NumberIsTooLargeException(Double.valueOf(2.0d * this.halfSampleSpan), Double.valueOf(tUpper - tLower), false);
        }
        double safety = FastMath.ulp(this.halfSampleSpan);
        this.tMin = (this.halfSampleSpan + tLower) + safety;
        this.tMax = (tUpper - this.halfSampleSpan) - safety;
    }

    public int getNbPoints() {
        return this.nbPoints;
    }

    public double getStepSize() {
        return this.stepSize;
    }

    private DerivativeStructure evaluate(DerivativeStructure t, double t0, double[] y) throws NumberIsTooLargeException {
        int i;
        double[] top = new double[this.nbPoints];
        double[] bottom = new double[this.nbPoints];
        for (i = 0; i < this.nbPoints; i++) {
            bottom[i] = y[i];
            for (int j = 1; j <= i; j++) {
                bottom[i - j] = (bottom[(i - j) + 1] - bottom[i - j]) / (((double) j) * this.stepSize);
            }
            top[i] = bottom[0];
        }
        int order = t.getOrder();
        int parameters = t.getFreeParameters();
        double[] derivatives = t.getAllDerivatives();
        double dt0 = t.getValue() - t0;
        DerivativeStructure interpolation = new DerivativeStructure(parameters, order, 0.0d);
        DerivativeStructure monomial = null;
        for (i = 0; i < this.nbPoints; i++) {
            if (i == 0) {
                monomial = new DerivativeStructure(parameters, order, 1.0d);
            } else {
                derivatives[0] = dt0 - (((double) (i - 1)) * this.stepSize);
                monomial = monomial.multiply(new DerivativeStructure(parameters, order, derivatives));
            }
            interpolation = interpolation.add(monomial.multiply(top[i]));
        }
        return interpolation;
    }

    public UnivariateDifferentiableFunction differentiate(UnivariateFunction function) {
        return new 1(function);
    }

    public UnivariateDifferentiableVectorFunction differentiate(UnivariateVectorFunction function) {
        return new 2(function);
    }

    public UnivariateDifferentiableMatrixFunction differentiate(UnivariateMatrixFunction function) {
        return new 3(function);
    }
}
