package org.apache.commons.math4.analysis.interpolation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.random.UnitSphereRandomVectorGenerator;
import org.apache.commons.math4.util.FastMath;

public class MicrosphereInterpolatingFunction implements MultivariateFunction {
    private final double brightnessExponent;
    private final int dimension;
    private final List<MicrosphereSurfaceElement> microsphere;
    private final Map<RealVector, Double> samples;

    private static class MicrosphereSurfaceElement {
        private double brightestIllumination;
        private Entry<RealVector, Double> brightestSample;
        private final RealVector normal;

        MicrosphereSurfaceElement(double[] n) {
            this.normal = new ArrayRealVector(n);
        }

        RealVector normal() {
            return this.normal;
        }

        void reset() {
            this.brightestIllumination = 0.0d;
            this.brightestSample = null;
        }

        void store(double illuminationFromSample, Entry<RealVector, Double> sample) {
            if (illuminationFromSample > this.brightestIllumination) {
                this.brightestIllumination = illuminationFromSample;
                this.brightestSample = sample;
            }
        }

        double illumination() {
            return this.brightestIllumination;
        }

        Entry<RealVector, Double> sample() {
            return this.brightestSample;
        }
    }

    public MicrosphereInterpolatingFunction(double[][] xval, double[] yval, int brightnessExponent, int microsphereElements, UnitSphereRandomVectorGenerator rand) throws DimensionMismatchException, NoDataException, NullArgumentException {
        if (xval == null || yval == null) {
            throw new NullArgumentException();
        } else if (xval.length == 0) {
            throw new NoDataException();
        } else if (xval.length != yval.length) {
            throw new DimensionMismatchException(xval.length, yval.length);
        } else if (xval[0] == null) {
            throw new NullArgumentException();
        } else {
            this.dimension = xval[0].length;
            this.brightnessExponent = (double) brightnessExponent;
            this.samples = new HashMap(yval.length);
            int i = 0;
            while (i < xval.length) {
                double[] xvalI = xval[i];
                if (xvalI == null) {
                    throw new NullArgumentException();
                } else if (xvalI.length != this.dimension) {
                    throw new DimensionMismatchException(xvalI.length, this.dimension);
                } else {
                    this.samples.put(new ArrayRealVector(xvalI), Double.valueOf(yval[i]));
                    i++;
                }
            }
            this.microsphere = new ArrayList(microsphereElements);
            for (i = 0; i < microsphereElements; i++) {
                this.microsphere.add(new MicrosphereSurfaceElement(rand.nextVector()));
            }
        }
    }

    public double value(double[] point) throws DimensionMismatchException {
        Entry<RealVector, Double> sd;
        RealVector p = new ArrayRealVector(point);
        for (MicrosphereSurfaceElement md : this.microsphere) {
            md.reset();
        }
        for (Entry<RealVector, Double> sd2 : this.samples.entrySet()) {
            RealVector diff = ((RealVector) sd2.getKey()).subtract(p);
            double diffNorm = diff.getNorm();
            if (FastMath.abs(diffNorm) < FastMath.ulp(1.0d)) {
                return ((Double) sd2.getValue()).doubleValue();
            }
            for (MicrosphereSurfaceElement md2 : this.microsphere) {
                md2.store(cosAngle(diff, md2.normal()) * FastMath.pow(diffNorm, -this.brightnessExponent), sd2);
            }
        }
        double value = 0.0d;
        double totalWeight = 0.0d;
        for (MicrosphereSurfaceElement md22 : this.microsphere) {
            double iV = md22.illumination();
            sd2 = md22.sample();
            if (sd2 != null) {
                value += ((Double) sd2.getValue()).doubleValue() * iV;
                totalWeight += iV;
            }
        }
        return value / totalWeight;
    }

    private double cosAngle(RealVector v, RealVector w) {
        return v.dotProduct(w) / (v.getNorm() * w.getNorm());
    }
}
