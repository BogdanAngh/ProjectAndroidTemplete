package org.apache.commons.math4.fitting;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.math4.analysis.function.HarmonicOscillator.Parametric;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresBuilder;
import org.apache.commons.math4.fitting.leastsquares.LeastSquaresProblem;
import org.apache.commons.math4.linear.DiagonalMatrix;
import org.apache.commons.math4.util.FastMath;

public class HarmonicCurveFitter extends AbstractCurveFitter {
    private static final Parametric FUNCTION;
    private final double[] initialGuess;
    private final int maxIter;

    public static class ParameterGuesser {
        private final double a;
        private final double omega;
        private final double phi;

        public ParameterGuesser(Collection<WeightedObservedPoint> observations) {
            if (observations.size() < 4) {
                throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(observations.size()), Integer.valueOf(4), true);
            }
            WeightedObservedPoint[] sorted = (WeightedObservedPoint[]) sortObservations(observations).toArray(new WeightedObservedPoint[0]);
            double[] aOmega = guessAOmega(sorted);
            this.a = aOmega[0];
            this.omega = aOmega[1];
            this.phi = guessPhi(sorted);
        }

        public double[] guess() {
            return new double[]{this.a, this.omega, this.phi};
        }

        private List<WeightedObservedPoint> sortObservations(Collection<WeightedObservedPoint> unsorted) {
            List<WeightedObservedPoint> observations = new ArrayList(unsorted);
            WeightedObservedPoint curr = (WeightedObservedPoint) observations.get(0);
            int len = observations.size();
            for (int j = 1; j < len; j++) {
                WeightedObservedPoint prec = curr;
                curr = (WeightedObservedPoint) observations.get(j);
                if (curr.getX() < prec.getX()) {
                    int i = j - 1;
                    WeightedObservedPoint mI = (WeightedObservedPoint) observations.get(i);
                    int i2 = i;
                    while (i2 >= 0 && curr.getX() < mI.getX()) {
                        observations.set(i2 + 1, mI);
                        i = i2 - 1;
                        if (i2 != 0) {
                            mI = (WeightedObservedPoint) observations.get(i);
                            i2 = i;
                        } else {
                            i2 = i;
                        }
                    }
                    observations.set(i2 + 1, curr);
                    curr = (WeightedObservedPoint) observations.get(j);
                }
            }
            return observations;
        }

        private double[] guessAOmega(WeightedObservedPoint[] observations) {
            double[] aOmega = new double[2];
            double sx2 = 0.0d;
            double sy2 = 0.0d;
            double sxy = 0.0d;
            double sxz = 0.0d;
            double syz = 0.0d;
            double currentX = observations[0].getX();
            double currentY = observations[0].getY();
            double f2Integral = 0.0d;
            double fPrime2Integral = 0.0d;
            double startX = currentX;
            int i = 1;
            while (true) {
                int length = observations.length;
                if (i >= r0) {
                    break;
                }
                double previousX = currentX;
                double previousY = currentY;
                currentX = observations[i].getX();
                currentY = observations[i].getY();
                double dx = currentX - previousX;
                double dy = currentY - previousY;
                double x = currentX - startX;
                f2Integral += ((((previousY * previousY) + (previousY * currentY)) + (currentY * currentY)) * dx) / 3.0d;
                fPrime2Integral += (dy * dy) / dx;
                sx2 += x * x;
                sy2 += f2Integral * f2Integral;
                sxy += x * f2Integral;
                sxz += x * fPrime2Integral;
                syz += f2Integral * fPrime2Integral;
                i++;
            }
            double c1 = (sy2 * sxz) - (sxy * syz);
            double c2 = (sxy * sxz) - (sx2 * syz);
            double c3 = (sx2 * sy2) - (sxy * sxy);
            if (c1 / c2 < 0.0d || c2 / c3 < 0.0d) {
                double xRange = observations[observations.length - 1].getX() - observations[0].getX();
                if (xRange == 0.0d) {
                    throw new ZeroException();
                }
                aOmega[1] = Angle2D.M_2PI / xRange;
                double yMin = Double.POSITIVE_INFINITY;
                double yMax = Double.NEGATIVE_INFINITY;
                i = 1;
                while (true) {
                    length = observations.length;
                    if (i >= r0) {
                        break;
                    }
                    double y = observations[i].getY();
                    if (y < yMin) {
                        yMin = y;
                    }
                    if (y > yMax) {
                        yMax = y;
                    }
                    i++;
                }
                aOmega[0] = 0.5d * (yMax - yMin);
            } else if (c2 == 0.0d) {
                Object[] objArr = new Object[0];
                throw new MathIllegalStateException(LocalizedFormats.ZERO_DENOMINATOR, objArr);
            } else {
                aOmega[0] = FastMath.sqrt(c1 / c2);
                aOmega[1] = FastMath.sqrt(c2 / c3);
            }
            return aOmega;
        }

        private double guessPhi(WeightedObservedPoint[] observations) {
            double fcMean = 0.0d;
            double fsMean = 0.0d;
            double currentX = observations[0].getX();
            double currentY = observations[0].getY();
            for (int i = 1; i < observations.length; i++) {
                double previousX = currentX;
                double previousY = currentY;
                currentX = observations[i].getX();
                currentY = observations[i].getY();
                double currentYPrime = (currentY - previousY) / (currentX - previousX);
                double omegaX = this.omega * currentX;
                double cosine = FastMath.cos(omegaX);
                double sine = FastMath.sin(omegaX);
                fcMean += ((this.omega * currentY) * cosine) - (currentYPrime * sine);
                fsMean += ((this.omega * currentY) * sine) + (currentYPrime * cosine);
            }
            return FastMath.atan2(-fsMean, fcMean);
        }
    }

    static {
        FUNCTION = new Parametric();
    }

    private HarmonicCurveFitter(double[] initialGuess, int maxIter) {
        this.initialGuess = initialGuess;
        this.maxIter = maxIter;
    }

    public static HarmonicCurveFitter create() {
        return new HarmonicCurveFitter(null, BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT);
    }

    public HarmonicCurveFitter withStartPoint(double[] newStart) {
        return new HarmonicCurveFitter((double[]) newStart.clone(), this.maxIter);
    }

    public HarmonicCurveFitter withMaxIterations(int newMaxIter) {
        return new HarmonicCurveFitter(this.initialGuess, newMaxIter);
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
