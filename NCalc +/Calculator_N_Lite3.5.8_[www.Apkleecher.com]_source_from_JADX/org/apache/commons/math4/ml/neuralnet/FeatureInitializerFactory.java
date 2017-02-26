package org.apache.commons.math4.ml.neuralnet;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.function.Constant;
import org.apache.commons.math4.distribution.RealDistribution;
import org.apache.commons.math4.distribution.UniformRealDistribution;
import org.apache.commons.math4.random.RandomGenerator;

public class FeatureInitializerFactory {

    class 1 implements FeatureInitializer {
        private double arg;
        private final /* synthetic */ UnivariateFunction val$f;
        private final /* synthetic */ double val$inc;

        1(double d, UnivariateFunction univariateFunction, double d2) {
            this.val$f = univariateFunction;
            this.val$inc = d2;
            this.arg = d;
        }

        public double value() {
            double result = this.val$f.value(this.arg);
            this.arg += this.val$inc;
            return result;
        }
    }

    class 2 implements FeatureInitializer {
        private final /* synthetic */ FeatureInitializer val$orig;
        private final /* synthetic */ RealDistribution val$random;

        2(FeatureInitializer featureInitializer, RealDistribution realDistribution) {
            this.val$orig = featureInitializer;
            this.val$random = realDistribution;
        }

        public double value() {
            return this.val$orig.value() + this.val$random.sample();
        }
    }

    private FeatureInitializerFactory() {
    }

    public static FeatureInitializer uniform(RandomGenerator rng, double min, double max) {
        return randomize(new UniformRealDistribution(rng, min, max), function(new Constant(0.0d), 0.0d, 0.0d));
    }

    public static FeatureInitializer uniform(double min, double max) {
        return randomize(new UniformRealDistribution(min, max), function(new Constant(0.0d), 0.0d, 0.0d));
    }

    public static FeatureInitializer function(UnivariateFunction f, double init, double inc) {
        return new 1(init, f, inc);
    }

    public static FeatureInitializer randomize(RealDistribution random, FeatureInitializer orig) {
        return new 2(orig, random);
    }
}
