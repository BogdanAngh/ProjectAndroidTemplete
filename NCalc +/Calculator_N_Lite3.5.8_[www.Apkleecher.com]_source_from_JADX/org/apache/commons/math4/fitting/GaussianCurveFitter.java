package org.apache.commons.math4.fitting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math4.analysis.function.Gaussian.Parametric;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math4.linear.DiagonalMatrix;
import org.apache.commons.math4.util.FastMath;

public class GaussianCurveFitter extends AbstractCurveFitter {
    private static final Parametric FUNCTION;
    private final double[] initialGuess;
    private final int maxIter;

    class 1 extends Parametric {
        1() {
        }

        public double value(double x, double... p) {
            double v = Double.POSITIVE_INFINITY;
            try {
                v = super.value(x, p);
            } catch (NotStrictlyPositiveException e) {
            }
            return v;
        }

        public double[] gradient(double x, double... p) {
            double[] v = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
            try {
                v = super.gradient(x, p);
            } catch (NotStrictlyPositiveException e) {
            }
            return v;
        }
    }

    public static class ParameterGuesser {
        private final double mean;
        private final double norm;
        private final double sigma;

        class 1 implements Comparator<WeightedObservedPoint> {
            1() {
            }

            public int compare(WeightedObservedPoint p1, WeightedObservedPoint p2) {
                if (p1 == null && p2 == null) {
                    return 0;
                }
                if (p1 == null) {
                    return -1;
                }
                if (p2 == null) {
                    return 1;
                }
                if (p1.getX() < p2.getX()) {
                    return -1;
                }
                if (p1.getX() > p2.getX()) {
                    return 1;
                }
                if (p1.getY() < p2.getY()) {
                    return -1;
                }
                if (p1.getY() > p2.getY()) {
                    return 1;
                }
                if (p1.getWeight() < p2.getWeight()) {
                    return -1;
                }
                if (p1.getWeight() > p2.getWeight()) {
                    return 1;
                }
                return 0;
            }
        }

        public ParameterGuesser(Collection<WeightedObservedPoint> observations) {
            if (observations == null) {
                throw new NullArgumentException(LocalizedFormats.INPUT_ARRAY, new Object[0]);
            } else if (observations.size() < 3) {
                throw new NumberIsTooSmallException(Integer.valueOf(observations.size()), Integer.valueOf(3), true);
            } else {
                double[] params = basicGuess((WeightedObservedPoint[]) sortObservations(observations).toArray(new WeightedObservedPoint[0]));
                this.norm = params[0];
                this.mean = params[1];
                this.sigma = params[2];
            }
        }

        public double[] guess() {
            return new double[]{this.norm, this.mean, this.sigma};
        }

        private List<WeightedObservedPoint> sortObservations(Collection<WeightedObservedPoint> unsorted) {
            List<WeightedObservedPoint> observations = new ArrayList(unsorted);
            Collections.sort(observations, new 1());
            return observations;
        }

        private double[] basicGuess(WeightedObservedPoint[] points) {
            double fwhmApprox;
            int maxYIdx = findMaxY(points);
            double n = points[maxYIdx].getY();
            double halfY = n + ((points[maxYIdx].getX() - n) / 2.0d);
            try {
                fwhmApprox = interpolateXAtY(points, maxYIdx, 1, halfY) - interpolateXAtY(points, maxYIdx, -1, halfY);
            } catch (OutOfRangeException e) {
                fwhmApprox = points[points.length - 1].getX() - points[0].getX();
            }
            double s = fwhmApprox / (2.0d * FastMath.sqrt(2.0d * FastMath.log(2.0d)));
            return new double[]{n, points[maxYIdx].getX(), s};
        }

        private int findMaxY(WeightedObservedPoint[] points) {
            int maxYIdx = 0;
            for (int i = 1; i < points.length; i++) {
                if (points[i].getY() > points[maxYIdx].getY()) {
                    maxYIdx = i;
                }
            }
            return maxYIdx;
        }

        private double interpolateXAtY(WeightedObservedPoint[] points, int startIdx, int idxStep, double y) throws OutOfRangeException {
            if (idxStep == 0) {
                throw new ZeroException();
            }
            WeightedObservedPoint[] twoPoints = getInterpolationPointsForY(points, startIdx, idxStep, y);
            WeightedObservedPoint p1 = twoPoints[0];
            WeightedObservedPoint p2 = twoPoints[1];
            if (p1.getY() == y) {
                return p1.getX();
            }
            if (p2.getY() == y) {
                return p2.getX();
            }
            return p1.getX() + (((y - p1.getY()) * (p2.getX() - p1.getX())) / (p2.getY() - p1.getY()));
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private org.apache.commons.math4.fitting.WeightedObservedPoint[] getInterpolationPointsForY(org.apache.commons.math4.fitting.WeightedObservedPoint[] r11, int r12, int r13, double r14) throws org.apache.commons.math4.exception.OutOfRangeException {
            /*
            r10 = this;
            if (r13 != 0) goto L_0x0008;
        L_0x0002:
            r1 = new org.apache.commons.math4.exception.ZeroException;
            r1.<init>();
            throw r1;
        L_0x0008:
            r0 = r12;
        L_0x0009:
            if (r13 >= 0) goto L_0x0053;
        L_0x000b:
            r1 = r0 + r13;
            if (r1 >= 0) goto L_0x0025;
        L_0x000f:
            r1 = new org.apache.commons.math4.exception.OutOfRangeException;
            r2 = java.lang.Double.valueOf(r14);
            r4 = -4503599627370496; // 0xfff0000000000000 float:0.0 double:-Infinity;
            r3 = java.lang.Double.valueOf(r4);
            r4 = 9218868437227405312; // 0x7ff0000000000000 float:0.0 double:Infinity;
            r4 = java.lang.Double.valueOf(r4);
            r1.<init>(r2, r3, r4);
            throw r1;
        L_0x0025:
            r8 = r11[r0];
            r1 = r0 + r13;
            r9 = r11[r1];
            r4 = r8.getY();
            r6 = r9.getY();
            r1 = r10;
            r2 = r14;
            r1 = r1.isBetween(r2, r4, r6);
            if (r1 == 0) goto L_0x0051;
        L_0x003b:
            if (r13 >= 0) goto L_0x0047;
        L_0x003d:
            r1 = 2;
            r1 = new org.apache.commons.math4.fitting.WeightedObservedPoint[r1];
            r2 = 0;
            r1[r2] = r9;
            r2 = 1;
            r1[r2] = r8;
        L_0x0046:
            return r1;
        L_0x0047:
            r1 = 2;
            r1 = new org.apache.commons.math4.fitting.WeightedObservedPoint[r1];
            r2 = 0;
            r1[r2] = r8;
            r2 = 1;
            r1[r2] = r9;
            goto L_0x0046;
        L_0x0051:
            r0 = r0 + r13;
            goto L_0x0009;
        L_0x0053:
            r1 = r0 + r13;
            r2 = r11.length;
            if (r1 < r2) goto L_0x0025;
        L_0x0058:
            goto L_0x000f;
            */
            throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.fitting.GaussianCurveFitter.ParameterGuesser.getInterpolationPointsForY(org.apache.commons.math4.fitting.WeightedObservedPoint[], int, int, double):org.apache.commons.math4.fitting.WeightedObservedPoint[]");
        }

        private boolean isBetween(double value, double boundary1, double boundary2) {
            return (value >= boundary1 && value <= boundary2) || (value >= boundary2 && value <= boundary1);
        }
    }

    static {
        FUNCTION = new 1();
    }

    private GaussianCurveFitter(double[] initialGuess, int maxIter) {
        this.initialGuess = initialGuess;
        this.maxIter = maxIter;
    }

    public static GaussianCurveFitter create() {
        return new GaussianCurveFitter(null, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public GaussianCurveFitter withStartPoint(double[] newStart) {
        return new GaussianCurveFitter((double[]) newStart.clone(), this.maxIter);
    }

    public GaussianCurveFitter withMaxIterations(int newMaxIter) {
        return new GaussianCurveFitter(this.initialGuess, newMaxIter);
    }

    protected LeastSquaresProblem getProblem(Collection<WeightedObservedPoint> observations) {
        double[] startPoint;
        int len = observations.size();
        double[] target = new double[len];
        double[] weights = new double[len];
        int i = 0;
        for (WeightedObservedPoint obs : observations) {
            target[i] = obs.getY();
            weights[i] = obs.getWeight();
            i++;
        }
        TheoreticalValuesFunction model = new TheoreticalValuesFunction(FUNCTION, observations);
        if (this.initialGuess != null) {
            startPoint = this.initialGuess;
        } else {
            startPoint = new ParameterGuesser(observations).guess();
        }
        return new LeastSquaresBuilder().maxEvaluations(BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT).maxIterations(this.maxIter).start(startPoint).target(target).weight(new DiagonalMatrix(weights)).model(model.getModelFunction(), model.getModelFunctionJacobian()).build();
    }
}
