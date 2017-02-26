package org.apache.commons.math4.random;

import com.example.duy.calculator.math_eval.Constants;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Collection;
import org.apache.commons.math4.distribution.BetaDistribution;
import org.apache.commons.math4.distribution.BinomialDistribution;
import org.apache.commons.math4.distribution.CauchyDistribution;
import org.apache.commons.math4.distribution.ChiSquaredDistribution;
import org.apache.commons.math4.distribution.ExponentialDistribution;
import org.apache.commons.math4.distribution.FDistribution;
import org.apache.commons.math4.distribution.GammaDistribution;
import org.apache.commons.math4.distribution.HypergeometricDistribution;
import org.apache.commons.math4.distribution.PascalDistribution;
import org.apache.commons.math4.distribution.PoissonDistribution;
import org.apache.commons.math4.distribution.TDistribution;
import org.apache.commons.math4.distribution.UniformIntegerDistribution;
import org.apache.commons.math4.distribution.WeibullDistribution;
import org.apache.commons.math4.distribution.ZipfDistribution;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NotANumberException;
import org.apache.commons.math4.exception.NotFiniteNumberException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.util.MathArrays;
import org.matheclipse.core.interfaces.IExpr;

public class RandomDataGenerator implements Serializable {
    private static final long serialVersionUID = -626730818244969716L;
    private RandomGenerator rand;
    private RandomGenerator secRand;

    public RandomDataGenerator() {
        this.rand = null;
        this.secRand = null;
    }

    public RandomDataGenerator(RandomGenerator rand) {
        this.rand = null;
        this.secRand = null;
        this.rand = rand;
    }

    public String nextHexString(int len) throws NotStrictlyPositiveException {
        if (len <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(len));
        }
        RandomGenerator ran = getRandomGenerator();
        StringBuilder outBuffer = new StringBuilder();
        byte[] randomBytes = new byte[((len / 2) + 1)];
        ran.nextBytes(randomBytes);
        for (byte valueOf : randomBytes) {
            String hex = Integer.toHexString(Integer.valueOf(valueOf).intValue() + IExpr.SYMBOLID);
            if (hex.length() == 1) {
                hex = new StringBuilder(Constants.ZERO).append(hex).toString();
            }
            outBuffer.append(hex);
        }
        return outBuffer.toString().substring(0, len);
    }

    public int nextInt(int lower, int upper) throws NumberIsTooLargeException {
        return new UniformIntegerDistribution(getRandomGenerator(), lower, upper).sample();
    }

    public long nextLong(long lower, long upper) throws NumberIsTooLargeException {
        if (lower >= upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Long.valueOf(lower), Long.valueOf(upper), false);
        }
        long max = (upper - lower) + 1;
        if (max <= 0) {
            RandomGenerator rng = getRandomGenerator();
            while (true) {
                long r = rng.nextLong();
                if (r >= lower && r <= upper) {
                    return r;
                }
            }
        } else if (max < 2147483647L) {
            return lower + ((long) getRandomGenerator().nextInt((int) max));
        } else {
            return lower + nextLong(getRandomGenerator(), max);
        }
    }

    private static long nextLong(RandomGenerator rng, long n) throws IllegalArgumentException {
        if (n > 0) {
            long val;
            byte[] byteArray = new byte[8];
            long bits;
            do {
                rng.nextBytes(byteArray);
                bits = 0;
                for (byte b : byteArray) {
                    bits = (bits << 8) | (((long) b) & 255);
                }
                bits &= Long.MAX_VALUE;
                val = bits % n;
            } while ((bits - val) + (n - 1) < 0);
            return val;
        }
        throw new NotStrictlyPositiveException(Long.valueOf(n));
    }

    public String nextSecureHexString(int len) throws NotStrictlyPositiveException {
        if (len <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.LENGTH, Integer.valueOf(len));
        }
        RandomGenerator secRan = getSecRan();
        try {
            MessageDigest alg = MessageDigest.getInstance("SHA-1");
            alg.reset();
            int numIter = (len / 40) + 1;
            StringBuilder outBuffer = new StringBuilder();
            for (int iter = 1; iter < numIter + 1; iter++) {
                byte[] randomBytes = new byte[40];
                secRan.nextBytes(randomBytes);
                alg.update(randomBytes);
                byte[] hash = alg.digest();
                for (byte valueOf : hash) {
                    String hex = Integer.toHexString(Integer.valueOf(valueOf).intValue() + IExpr.SYMBOLID);
                    if (hex.length() == 1) {
                        hex = new StringBuilder(Constants.ZERO).append(hex).toString();
                    }
                    outBuffer.append(hex);
                }
            }
            return outBuffer.toString().substring(0, len);
        } catch (NoSuchAlgorithmException ex) {
            throw new MathInternalError(ex);
        }
    }

    public int nextSecureInt(int lower, int upper) throws NumberIsTooLargeException {
        return new UniformIntegerDistribution(getSecRan(), lower, upper).sample();
    }

    public long nextSecureLong(long lower, long upper) throws NumberIsTooLargeException {
        if (lower >= upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Long.valueOf(lower), Long.valueOf(upper), false);
        }
        RandomGenerator rng = getSecRan();
        long max = (upper - lower) + 1;
        if (max <= 0) {
            while (true) {
                long r = rng.nextLong();
                if (r >= lower && r <= upper) {
                    return r;
                }
            }
        } else if (max < 2147483647L) {
            return lower + ((long) rng.nextInt((int) max));
        } else {
            return lower + nextLong(rng, max);
        }
    }

    public long nextPoisson(double mean) throws NotStrictlyPositiveException {
        return (long) new PoissonDistribution(getRandomGenerator(), mean, OpenMapRealVector.DEFAULT_ZERO_TOLERANCE, PoissonDistribution.DEFAULT_MAX_ITERATIONS).sample();
    }

    public double nextGaussian(double mu, double sigma) throws NotStrictlyPositiveException {
        if (sigma > 0.0d) {
            return (getRandomGenerator().nextGaussian() * sigma) + mu;
        }
        throw new NotStrictlyPositiveException(LocalizedFormats.STANDARD_DEVIATION, Double.valueOf(sigma));
    }

    public double nextExponential(double mean) throws NotStrictlyPositiveException {
        return new ExponentialDistribution(getRandomGenerator(), mean, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public double nextGamma(double shape, double scale) throws NotStrictlyPositiveException {
        return new GammaDistribution(getRandomGenerator(), shape, scale, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public int nextHypergeometric(int populationSize, int numberOfSuccesses, int sampleSize) throws NotPositiveException, NotStrictlyPositiveException, NumberIsTooLargeException {
        return new HypergeometricDistribution(getRandomGenerator(), populationSize, numberOfSuccesses, sampleSize).sample();
    }

    public int nextPascal(int r, double p) throws NotStrictlyPositiveException, OutOfRangeException {
        return new PascalDistribution(getRandomGenerator(), r, p).sample();
    }

    public double nextT(double df) throws NotStrictlyPositiveException {
        return new TDistribution(getRandomGenerator(), df, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public double nextWeibull(double shape, double scale) throws NotStrictlyPositiveException {
        return new WeibullDistribution(getRandomGenerator(), shape, scale, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public int nextZipf(int numberOfElements, double exponent) throws NotStrictlyPositiveException {
        return new ZipfDistribution(getRandomGenerator(), numberOfElements, exponent).sample();
    }

    public double nextBeta(double alpha, double beta) {
        return new BetaDistribution(getRandomGenerator(), alpha, beta, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public int nextBinomial(int numberOfTrials, double probabilityOfSuccess) {
        return new BinomialDistribution(getRandomGenerator(), numberOfTrials, probabilityOfSuccess).sample();
    }

    public double nextCauchy(double median, double scale) {
        return new CauchyDistribution(getRandomGenerator(), median, scale, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public double nextChiSquare(double df) {
        return new ChiSquaredDistribution(getRandomGenerator(), df, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public double nextF(double numeratorDf, double denominatorDf) throws NotStrictlyPositiveException {
        return new FDistribution(getRandomGenerator(), numeratorDf, denominatorDf, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY).sample();
    }

    public double nextUniform(double lower, double upper) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
        return nextUniform(lower, upper, false);
    }

    public double nextUniform(double lower, double upper, boolean lowerInclusive) throws NumberIsTooLargeException, NotFiniteNumberException, NotANumberException {
        if (lower >= upper) {
            throw new NumberIsTooLargeException(LocalizedFormats.LOWER_BOUND_NOT_BELOW_UPPER_BOUND, Double.valueOf(lower), Double.valueOf(upper), false);
        } else if (Double.isInfinite(lower)) {
            throw new NotFiniteNumberException(LocalizedFormats.INFINITE_BOUND, Double.valueOf(lower), new Object[0]);
        } else if (Double.isInfinite(upper)) {
            throw new NotFiniteNumberException(LocalizedFormats.INFINITE_BOUND, Double.valueOf(upper), new Object[0]);
        } else if (Double.isNaN(lower) || Double.isNaN(upper)) {
            throw new NotANumberException();
        } else {
            RandomGenerator generator = getRandomGenerator();
            double u = generator.nextDouble();
            while (!lowerInclusive && u <= 0.0d) {
                u = generator.nextDouble();
            }
            return (u * upper) + ((1.0d - u) * lower);
        }
    }

    public int[] nextPermutation(int n, int k) throws NumberIsTooLargeException, NotStrictlyPositiveException {
        if (k > n) {
            throw new NumberIsTooLargeException(LocalizedFormats.PERMUTATION_EXCEEDS_N, Integer.valueOf(k), Integer.valueOf(n), true);
        } else if (k <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.PERMUTATION_SIZE, Integer.valueOf(k));
        } else {
            int[] index = MathArrays.natural(n);
            MathArrays.shuffle(index, getRandomGenerator());
            return MathArrays.copyOf(index, k);
        }
    }

    public Object[] nextSample(Collection<?> c, int k) throws NumberIsTooLargeException, NotStrictlyPositiveException {
        int len = c.size();
        if (k > len) {
            throw new NumberIsTooLargeException(LocalizedFormats.SAMPLE_SIZE_EXCEEDS_COLLECTION_SIZE, Integer.valueOf(k), Integer.valueOf(len), true);
        } else if (k <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.NUMBER_OF_SAMPLES, Integer.valueOf(k));
        } else {
            Object[] objects = c.toArray();
            int[] index = nextPermutation(len, k);
            Object[] result = new Object[k];
            for (int i = 0; i < k; i++) {
                result[i] = objects[index[i]];
            }
            return result;
        }
    }

    public void reSeed(long seed) {
        getRandomGenerator().setSeed(seed);
    }

    public void reSeedSecure() {
        getSecRan().setSeed(System.currentTimeMillis());
    }

    public void reSeedSecure(long seed) {
        getSecRan().setSeed(seed);
    }

    public void reSeed() {
        getRandomGenerator().setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
    }

    public void setSecureAlgorithm(String algorithm, String provider) throws NoSuchAlgorithmException, NoSuchProviderException {
        this.secRand = RandomGeneratorFactory.createRandomGenerator(SecureRandom.getInstance(algorithm, provider));
    }

    public RandomGenerator getRandomGenerator() {
        if (this.rand == null) {
            initRan();
        }
        return this.rand;
    }

    private void initRan() {
        this.rand = new Well19937c(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
    }

    private RandomGenerator getSecRan() {
        if (this.secRand == null) {
            this.secRand = RandomGeneratorFactory.createRandomGenerator(new SecureRandom());
            this.secRand.setSeed(System.currentTimeMillis() + ((long) System.identityHashCode(this)));
        }
        return this.secRand;
    }
}
