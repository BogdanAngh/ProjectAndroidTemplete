package org.apache.commons.math4.distribution;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.special.Gamma;
import org.apache.commons.math4.util.FastMath;

public class PoissonDistribution extends AbstractIntegerDistribution {
    public static final double DEFAULT_EPSILON = 1.0E-12d;
    public static final int DEFAULT_MAX_ITERATIONS = 10000000;
    private static final long serialVersionUID = -3349935121172596109L;
    private final double epsilon;
    private final ExponentialDistribution exponential;
    private final int maxIterations;
    private final double mean;
    private final NormalDistribution normal;

    public PoissonDistribution(double p) throws NotStrictlyPositiveException {
        this(p, DEFAULT_EPSILON, DEFAULT_MAX_ITERATIONS);
    }

    public PoissonDistribution(double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
        this(new Well19937c(), p, epsilon, maxIterations);
    }

    public PoissonDistribution(RandomGenerator rng, double p, double epsilon, int maxIterations) throws NotStrictlyPositiveException {
        super(rng);
        if (p <= 0.0d) {
            throw new NotStrictlyPositiveException(LocalizedFormats.MEAN, Double.valueOf(p));
        }
        this.mean = p;
        this.epsilon = epsilon;
        this.maxIterations = maxIterations;
        this.normal = new NormalDistribution(rng, p, FastMath.sqrt(p), WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
        this.exponential = new ExponentialDistribution(rng, 1.0d, WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }

    public PoissonDistribution(double p, double epsilon) throws NotStrictlyPositiveException {
        this(p, epsilon, DEFAULT_MAX_ITERATIONS);
    }

    public PoissonDistribution(double p, int maxIterations) {
        this(p, DEFAULT_EPSILON, maxIterations);
    }

    public double getMean() {
        return this.mean;
    }

    public double probability(int x) {
        double logProbability = logProbability(x);
        return logProbability == Double.NEGATIVE_INFINITY ? 0.0d : FastMath.exp(logProbability);
    }

    public double logProbability(int x) {
        if (x < 0 || x == BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT) {
            return Double.NEGATIVE_INFINITY;
        }
        if (x == 0) {
            return -this.mean;
        }
        return (((-SaddlePointExpansion.getStirlingError((double) x)) - SaddlePointExpansion.getDeviancePart((double) x, this.mean)) - (FastMath.log(Angle2D.M_2PI) * 0.5d)) - (FastMath.log((double) x) * 0.5d);
    }

    public double cumulativeProbability(int x) {
        if (x < 0) {
            return 0.0d;
        }
        if (x != BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT) {
            return Gamma.regularizedGammaQ(1.0d + ((double) x), this.mean, this.epsilon, this.maxIterations);
        }
        return 1.0d;
    }

    public double normalApproximateProbability(int x) {
        return this.normal.cumulativeProbability(((double) x) + 0.5d);
    }

    public double getNumericalMean() {
        return getMean();
    }

    public double getNumericalVariance() {
        return getMean();
    }

    public int getSupportLowerBound() {
        return 0;
    }

    public int getSupportUpperBound() {
        return BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public int sample() {
        return (int) FastMath.min(nextPoisson(this.mean), 2147483647L);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private long nextPoisson(double r70) {
        /*
        r69 = this;
        r36 = 4630826316843712512; // 0x4044000000000000 float:0.0 double:40.0;
        r60 = 4630826316843712512; // 0x4044000000000000 float:0.0 double:40.0;
        r5 = (r70 > r60 ? 1 : (r70 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x003b;
    L_0x0008:
        r0 = r70;
        r0 = -r0;
        r60 = r0;
        r30 = org.apache.commons.math4.util.FastMath.exp(r60);
        r28 = 0;
        r42 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r44 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
    L_0x0017:
        r0 = r28;
        r0 = (double) r0;
        r60 = r0;
        r62 = 4652007308841189376; // 0x408f400000000000 float:0.0 double:1000.0;
        r62 = r62 * r70;
        r5 = (r60 > r62 ? 1 : (r60 == r62 ? 0 : -1));
        if (r5 < 0) goto L_0x0028;
    L_0x0027:
        return r28;
    L_0x0028:
        r0 = r69;
        r5 = r0.random;
        r44 = r5.nextDouble();
        r42 = r42 * r44;
        r5 = (r42 > r30 ? 1 : (r42 == r30 ? 0 : -1));
        if (r5 < 0) goto L_0x0027;
    L_0x0036:
        r60 = 1;
        r28 = r28 + r60;
        goto L_0x0017;
    L_0x003b:
        r20 = org.apache.commons.math4.util.FastMath.floor(r70);
        r22 = r70 - r20;
        r24 = org.apache.commons.math4.util.FastMath.log(r20);
        r0 = r20;
        r5 = (int) r0;
        r26 = org.apache.commons.math4.util.CombinatoricsUtils.factorialLog(r5);
        r60 = 1;
        r5 = (r22 > r60 ? 1 : (r22 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x0139;
    L_0x0052:
        r58 = 0;
    L_0x0054:
        r60 = 4629700416936869888; // 0x4040000000000000 float:0.0 double:32.0;
        r60 = r60 * r20;
        r62 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r60 = r60 / r62;
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r60 = r60 + r62;
        r60 = org.apache.commons.math4.util.FastMath.log(r60);
        r60 = r60 * r20;
        r14 = org.apache.commons.math4.util.FastMath.sqrt(r60);
        r60 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r18 = r14 / r60;
        r60 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r60 = r60 * r20;
        r48 = r60 + r14;
        r60 = 4614256656552045848; // 0x400921fb54442d18 float:3.37028055E12 double:3.141592653589793;
        r60 = r60 * r48;
        r60 = org.apache.commons.math4.util.FastMath.sqrt(r60);
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r64 = 4620693217682128896; // 0x4020000000000000 float:0.0 double:8.0;
        r64 = r64 * r20;
        r62 = r62 / r64;
        r62 = org.apache.commons.math4.util.FastMath.exp(r62);
        r6 = r60 * r62;
        r60 = r48 / r14;
        r0 = -r14;
        r62 = r0;
        r64 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r64 = r64 + r14;
        r62 = r62 * r64;
        r62 = r62 / r48;
        r62 = org.apache.commons.math4.util.FastMath.exp(r62);
        r8 = r60 * r62;
        r60 = r6 + r8;
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r10 = r60 + r62;
        r32 = r6 / r10;
        r34 = r8 / r10;
        r60 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r62 = 4620693217682128896; // 0x4020000000000000 float:0.0 double:8.0;
        r62 = r62 * r20;
        r12 = r60 / r62;
        r54 = 0;
        r56 = 0;
        r52 = 0;
        r4 = 0;
        r46 = 0;
        r40 = 0;
        r38 = 0;
    L_0x00c2:
        r0 = r69;
        r5 = r0.random;
        r50 = r5.nextDouble();
        r5 = (r50 > r32 ? 1 : (r50 == r32 ? 0 : -1));
        if (r5 > 0) goto L_0x0148;
    L_0x00ce:
        r0 = r69;
        r5 = r0.random;
        r28 = r5.nextGaussian();
        r60 = r20 + r18;
        r60 = org.apache.commons.math4.util.FastMath.sqrt(r60);
        r60 = r60 * r28;
        r62 = 4602678819172646912; // 0x3fe0000000000000 float:0.0 double:0.5;
        r54 = r60 - r62;
        r5 = (r54 > r14 ? 1 : (r54 == r14 ? 0 : -1));
        if (r5 > 0) goto L_0x00c2;
    L_0x00e6:
        r0 = r20;
        r0 = -r0;
        r60 = r0;
        r5 = (r54 > r60 ? 1 : (r54 == r60 ? 0 : -1));
        if (r5 < 0) goto L_0x00c2;
    L_0x00ef:
        r60 = 0;
        r5 = (r54 > r60 ? 1 : (r54 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x0143;
    L_0x00f5:
        r56 = org.apache.commons.math4.util.FastMath.floor(r54);
    L_0x00f9:
        r0 = r69;
        r5 = r0.exponential;
        r16 = r5.sample();
        r0 = r16;
        r0 = -r0;
        r60 = r0;
        r62 = r28 * r28;
        r64 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r62 = r62 / r64;
        r60 = r60 - r62;
        r52 = r60 + r12;
    L_0x0110:
        r60 = 0;
        r5 = (r54 > r60 ? 1 : (r54 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x017b;
    L_0x0116:
        r4 = 1;
    L_0x0117:
        r60 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r60 = r60 + r56;
        r60 = r60 * r56;
        r62 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r62 = r62 * r20;
        r46 = r60 / r62;
        r0 = r46;
        r0 = -r0;
        r60 = r0;
        r5 = (r52 > r60 ? 1 : (r52 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x017d;
    L_0x012c:
        if (r4 != 0) goto L_0x017d;
    L_0x012e:
        r56 = r56 + r20;
    L_0x0130:
        r0 = r56;
        r0 = (long) r0;
        r60 = r0;
        r28 = r58 + r60;
        goto L_0x0027;
    L_0x0139:
        r0 = r69;
        r1 = r22;
        r58 = r0.nextPoisson(r1);
        goto L_0x0054;
    L_0x0143:
        r56 = org.apache.commons.math4.util.FastMath.ceil(r54);
        goto L_0x00f9;
    L_0x0148:
        r60 = r32 + r34;
        r5 = (r50 > r60 ? 1 : (r50 == r60 ? 0 : -1));
        if (r5 <= 0) goto L_0x0151;
    L_0x014e:
        r56 = r20;
        goto L_0x0130;
    L_0x0151:
        r60 = r48 / r14;
        r0 = r69;
        r5 = r0.exponential;
        r62 = r5.sample();
        r60 = r60 * r62;
        r54 = r14 + r60;
        r56 = org.apache.commons.math4.util.FastMath.ceil(r54);
        r0 = r69;
        r5 = r0.exponential;
        r60 = r5.sample();
        r0 = r60;
        r0 = -r0;
        r60 = r0;
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r62 = r62 + r54;
        r62 = r62 * r14;
        r62 = r62 / r48;
        r52 = r60 - r62;
        goto L_0x0110;
    L_0x017b:
        r4 = 0;
        goto L_0x0117;
    L_0x017d:
        r60 = 4611686018427387904; // 0x4000000000000000 float:0.0 double:2.0;
        r60 = r60 * r56;
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r60 = r60 + r62;
        r62 = 4618441417868443648; // 0x4018000000000000 float:0.0 double:6.0;
        r62 = r62 * r20;
        r60 = r60 / r62;
        r62 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r60 = r60 - r62;
        r40 = r46 * r60;
        r60 = r46 * r46;
        r62 = 4613937818241073152; // 0x4008000000000000 float:0.0 double:3.0;
        r0 = (double) r4;
        r64 = r0;
        r66 = 4607182418800017408; // 0x3ff0000000000000 float:0.0 double:1.0;
        r66 = r66 + r56;
        r64 = r64 * r66;
        r64 = r64 + r20;
        r62 = r62 * r64;
        r60 = r60 / r62;
        r38 = r40 - r60;
        r5 = (r52 > r38 ? 1 : (r52 == r38 ? 0 : -1));
        if (r5 >= 0) goto L_0x01ad;
    L_0x01aa:
        r56 = r56 + r20;
        goto L_0x0130;
    L_0x01ad:
        r5 = (r52 > r40 ? 1 : (r52 == r40 ? 0 : -1));
        if (r5 > 0) goto L_0x00c2;
    L_0x01b1:
        r60 = r56 * r24;
        r62 = r56 + r20;
        r0 = r62;
        r5 = (int) r0;
        r62 = org.apache.commons.math4.util.CombinatoricsUtils.factorialLog(r5);
        r60 = r60 - r62;
        r60 = r60 + r26;
        r5 = (r52 > r60 ? 1 : (r52 == r60 ? 0 : -1));
        if (r5 >= 0) goto L_0x00c2;
    L_0x01c4:
        r56 = r56 + r20;
        goto L_0x0130;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.distribution.PoissonDistribution.nextPoisson(double):long");
    }
}
