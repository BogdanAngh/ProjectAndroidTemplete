package org.apache.commons.math4.stat.inference;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.distribution.RealDistribution;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.TooManyIterationsException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.fraction.BigFraction;
import org.apache.commons.math4.fraction.BigFractionField;
import org.apache.commons.math4.fraction.FractionConversionException;
import org.apache.commons.math4.linear.Array2DRowFieldMatrix;
import org.apache.commons.math4.linear.FieldMatrix;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.CombinatoricsUtils;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class KolmogorovSmirnovTest {
    protected static final double KS_SUM_CAUCHY_CRITERION = 1.0E-20d;
    protected static final int LARGE_SAMPLE_PRODUCT = 10000;
    protected static final int MAXIMUM_PARTIAL_SUM_COUNT = 100000;
    protected static final int MONTE_CARLO_ITERATIONS = 1000000;
    protected static final double PG_SUM_RELATIVE_ERROR = 1.0E-10d;
    protected static final int SMALL_SAMPLE_PRODUCT = 200;
    private final RandomGenerator rng;

    public KolmogorovSmirnovTest() {
        this.rng = new Well19937c();
    }

    public KolmogorovSmirnovTest(RandomGenerator rng) {
        this.rng = rng;
    }

    public double kolmogorovSmirnovTest(RealDistribution distribution, double[] data, boolean exact) {
        return 1.0d - cdf(kolmogorovSmirnovStatistic(distribution, data), data.length, exact);
    }

    public double kolmogorovSmirnovStatistic(RealDistribution distribution, double[] data) {
        checkArray(data);
        int n = data.length;
        double nd = (double) n;
        double[] dataCopy = new double[n];
        System.arraycopy(data, 0, dataCopy, 0, n);
        Arrays.sort(dataCopy);
        double d = 0.0d;
        for (int i = 1; i <= n; i++) {
            double yi = distribution.cumulativeProbability(dataCopy[i - 1]);
            double currD = FastMath.max(yi - (((double) (i - 1)) / nd), (((double) i) / nd) - yi);
            if (currD > d) {
                d = currD;
            }
        }
        return d;
    }

    public double kolmogorovSmirnovTest(double[] x, double[] y, boolean strict) {
        long lengthProduct = ((long) x.length) * ((long) y.length);
        if (lengthProduct < 200) {
            return exactP(kolmogorovSmirnovStatistic(x, y), x.length, y.length, strict);
        }
        if (lengthProduct < 10000) {
            return monteCarloP(kolmogorovSmirnovStatistic(x, y), x.length, y.length, strict, MONTE_CARLO_ITERATIONS);
        }
        return approximateP(kolmogorovSmirnovStatistic(x, y), x.length, y.length);
    }

    public double kolmogorovSmirnovTest(double[] x, double[] y) {
        return kolmogorovSmirnovTest(x, y, true);
    }

    public double kolmogorovSmirnovStatistic(double[] x, double[] y) {
        int i;
        checkArray(x);
        checkArray(y);
        double[] sx = MathArrays.copyOf(x);
        double[] sy = MathArrays.copyOf(y);
        Arrays.sort(sx);
        Arrays.sort(sy);
        int n = sx.length;
        int m = sy.length;
        double supD = 0.0d;
        for (i = 0; i < n; i++) {
            double cdf_y;
            double d = (double) n;
            double cdf_x = (((double) i) + 1.0d) / r0;
            int yIndex = Arrays.binarySearch(sy, sx[i]);
            if (yIndex >= 0) {
                d = (double) m;
                cdf_y = (((double) yIndex) + 1.0d) / r0;
            } else {
                d = (double) m;
                cdf_y = (((double) (-yIndex)) - 1.0d) / r0;
            }
            double curD = FastMath.abs(cdf_x - cdf_y);
            if (curD > supD) {
                supD = curD;
            }
        }
        for (i = 0; i < m; i++) {
            d = (double) m;
            cdf_y = (((double) i) + 1.0d) / r0;
            int xIndex = Arrays.binarySearch(sx, sy[i]);
            if (xIndex >= 0) {
                d = (double) n;
                cdf_x = (((double) xIndex) + 1.0d) / r0;
            } else {
                d = (double) n;
                cdf_x = (((double) (-xIndex)) - 1.0d) / r0;
            }
            curD = FastMath.abs(cdf_x - cdf_y);
            if (curD > supD) {
                supD = curD;
            }
        }
        return supD;
    }

    public double kolmogorovSmirnovTest(RealDistribution distribution, double[] data) {
        return kolmogorovSmirnovTest(distribution, data, false);
    }

    public boolean kolmogorovSmirnovTest(RealDistribution distribution, double[] data, double alpha) {
        if (alpha <= 0.0d || alpha > 0.5d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_BOUND_SIGNIFICANCE_LEVEL, Double.valueOf(alpha), Integer.valueOf(0), Double.valueOf(0.5d));
        } else if (kolmogorovSmirnovTest(distribution, data) < alpha) {
            return true;
        } else {
            return false;
        }
    }

    public double cdf(double d, int n) throws MathArithmeticException {
        return cdf(d, n, false);
    }

    public double cdfExact(double d, int n) throws MathArithmeticException {
        return cdf(d, n, true);
    }

    public double cdf(double d, int n, boolean exact) throws MathArithmeticException {
        double ninv = 1.0d / ((double) n);
        double ninvhalf = 0.5d * ninv;
        if (d <= ninvhalf) {
            return 0.0d;
        }
        if (ninvhalf < d && d <= ninv) {
            double res = 1.0d;
            double f = (2.0d * d) - ninv;
            for (int i = 1; i <= n; i++) {
                res *= ((double) i) * f;
            }
            return res;
        } else if (1.0d - ninv <= d && d < 1.0d) {
            return 1.0d - (2.0d * Math.pow(1.0d - d, (double) n));
        } else if (1.0d <= d) {
            return 1.0d;
        } else {
            if (exact) {
                return exactK(d, n);
            }
            if (n <= 140) {
                return roundedK(d, n);
            }
            return pelzGood(d, n);
        }
    }

    private double exactK(double d, int n) throws MathArithmeticException {
        int k = (int) Math.ceil(((double) n) * d);
        BigFraction pFrac = (BigFraction) createExactH(d, n).power(n).getEntry(k - 1, k - 1);
        for (int i = 1; i <= n; i++) {
            pFrac = pFrac.multiply(i).divide(n);
        }
        return pFrac.bigDecimalValue(20, 4).doubleValue();
    }

    private double roundedK(double d, int n) {
        int k = (int) Math.ceil(((double) n) * d);
        double pFrac = createRoundedH(d, n).power(n).getEntry(k - 1, k - 1);
        for (int i = 1; i <= n; i++) {
            pFrac *= ((double) i) / ((double) n);
        }
        return pFrac;
    }

    public double pelzGood(double d, int n) {
        double sqrtN = FastMath.sqrt((double) n);
        double z = d * sqrtN;
        double z2 = (d * d) * ((double) n);
        double z4 = z2 * z2;
        double z6 = z4 * z2;
        double z8 = z4 * z4;
        double sum = 0.0d;
        double z2Term = 9.869604401089358d / (8.0d * z2);
        int k = 1;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            double kTerm = (double) ((k * 2) - 1);
            double increment = FastMath.exp(((-z2Term) * kTerm) * kTerm);
            sum += increment;
            if (increment <= PG_SUM_RELATIVE_ERROR * sum) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        double ret = (FastMath.sqrt(Angle2D.M_2PI) * sum) / z;
        double twoZ2 = 2.0d * z2;
        sum = 0.0d;
        k = 0;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            kTerm = ((double) k) + 0.5d;
            double kTerm2 = kTerm * kTerm;
            increment = ((9.869604401089358d * kTerm2) - z2) * FastMath.exp((-9.869604401089358d * kTerm2) / twoZ2);
            sum += increment;
            if (FastMath.abs(increment) < PG_SUM_RELATIVE_ERROR * FastMath.abs(sum)) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        double sqrtHalfPi = FastMath.sqrt(Angle2D.M_PI_2);
        ret += (sum * sqrtHalfPi) / ((3.0d * z4) * sqrtN);
        double z4Term = 2.0d * z4;
        double z6Term = 6.0d * z6;
        z2Term = 5.0d * z2;
        sum = 0.0d;
        k = 0;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            kTerm = ((double) k) + 0.5d;
            kTerm2 = kTerm * kTerm;
            increment = (((z6Term + z4Term) + ((9.869604401089358d * (z4Term - z2Term)) * kTerm2)) + (((97.40909103400243d * (1.0d - twoZ2)) * kTerm2) * kTerm2)) * FastMath.exp((-9.869604401089358d * kTerm2) / twoZ2);
            sum += increment;
            if (FastMath.abs(increment) < PG_SUM_RELATIVE_ERROR * FastMath.abs(sum)) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        double sum2 = 0.0d;
        k = 1;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            kTerm2 = (double) (k * k);
            increment = (9.869604401089358d * kTerm2) * FastMath.exp((-9.869604401089358d * kTerm2) / twoZ2);
            sum2 += increment;
            if (FastMath.abs(increment) < PG_SUM_RELATIVE_ERROR * FastMath.abs(sum2)) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        ret += (sqrtHalfPi / ((double) n)) * ((sum / ((((36.0d * z2) * z2) * z2) * z)) - (sum2 / ((18.0d * z2) * z)));
        sum = 0.0d;
        k = 0;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            kTerm = ((double) k) + 0.5d;
            kTerm2 = kTerm * kTerm;
            double kTerm4 = kTerm2 * kTerm2;
            increment = ((((((961.3891935753043d * (kTerm4 * kTerm2)) * (5.0d - (30.0d * z2))) + ((97.40909103400243d * kTerm4) * ((-60.0d * z2) + (212.0d * z4)))) + ((9.869604401089358d * kTerm2) * ((135.0d * z4) - (96.0d * z6)))) - (30.0d * z6)) - (90.0d * z8)) * FastMath.exp((-9.869604401089358d * kTerm2) / twoZ2);
            sum += increment;
            if (FastMath.abs(increment) < PG_SUM_RELATIVE_ERROR * FastMath.abs(sum)) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        sum2 = 0.0d;
        k = 1;
        while (k < MAXIMUM_PARTIAL_SUM_COUNT) {
            kTerm2 = (double) (k * k);
            increment = ((-97.40909103400243d * (kTerm2 * kTerm2)) + ((29.608813203268074d * kTerm2) * z2)) * FastMath.exp((-9.869604401089358d * kTerm2) / twoZ2);
            sum2 += increment;
            if (FastMath.abs(increment) < PG_SUM_RELATIVE_ERROR * FastMath.abs(sum2)) {
                break;
            }
            k++;
        }
        if (k == MAXIMUM_PARTIAL_SUM_COUNT) {
            throw new TooManyIterationsException(Integer.valueOf(MAXIMUM_PARTIAL_SUM_COUNT));
        }
        return ((sqrtHalfPi / (((double) n) * sqrtN)) * ((sum / ((3240.0d * z6) * z4)) + (sum2 / (108.0d * z6)))) + ret;
    }

    private FieldMatrix<BigFraction> createExactH(double d, int n) throws NumberIsTooLargeException, FractionConversionException {
        int k = (int) Math.ceil(((double) n) * d);
        int m = (k * 2) - 1;
        double hDouble = ((double) k) - (((double) n) * d);
        if (hDouble >= 1.0d) {
            throw new NumberIsTooLargeException(Double.valueOf(hDouble), Double.valueOf(1.0d), false);
        }
        BigFraction h;
        int i;
        int j;
        try {
            h = new BigFraction(hDouble, KS_SUM_CAUCHY_CRITERION, LARGE_SAMPLE_PRODUCT);
        } catch (FractionConversionException e) {
            try {
                h = new BigFraction(hDouble, PG_SUM_RELATIVE_ERROR, LARGE_SAMPLE_PRODUCT);
            } catch (FractionConversionException e2) {
                h = new BigFraction(hDouble, 1.0E-5d, LARGE_SAMPLE_PRODUCT);
            }
        }
        FieldElement[][] Hdata = (BigFraction[][]) Array.newInstance(BigFraction.class, new int[]{m, m});
        for (i = 0; i < m; i++) {
            for (j = 0; j < m; j++) {
                if ((i - j) + 1 < 0) {
                    Hdata[i][j] = BigFraction.ZERO;
                } else {
                    Hdata[i][j] = BigFraction.ONE;
                }
            }
        }
        BigFraction[] hPowers = new BigFraction[m];
        hPowers[0] = h;
        for (i = 1; i < m; i++) {
            hPowers[i] = h.multiply(hPowers[i - 1]);
        }
        for (i = 0; i < m; i++) {
            Hdata[i][0] = Hdata[i][0].subtract(hPowers[i]);
            Hdata[m - 1][i] = Hdata[m - 1][i].subtract(hPowers[(m - i) - 1]);
        }
        if (h.compareTo(BigFraction.ONE_HALF) == 1) {
            Hdata[m - 1][0] = Hdata[m - 1][0].add(h.multiply(2).subtract(1).pow(m));
        }
        for (i = 0; i < m; i++) {
            for (j = 0; j < i + 1; j++) {
                if ((i - j) + 1 > 0) {
                    for (int g = 2; g <= (i - j) + 1; g++) {
                        Hdata[i][j] = Hdata[i][j].divide(g);
                    }
                }
            }
        }
        return new Array2DRowFieldMatrix(BigFractionField.getInstance(), Hdata);
    }

    private RealMatrix createRoundedH(double d, int n) throws NumberIsTooLargeException {
        int k = (int) Math.ceil(((double) n) * d);
        int m = (k * 2) - 1;
        double h = ((double) k) - (((double) n) * d);
        if (h >= 1.0d) {
            throw new NumberIsTooLargeException(Double.valueOf(h), Double.valueOf(1.0d), false);
        }
        int i;
        double[][] Hdata = (double[][]) Array.newInstance(Double.TYPE, new int[]{m, m});
        for (i = 0; i < m; i++) {
            int j;
            for (j = 0; j < m; j++) {
                if ((i - j) + 1 < 0) {
                    Hdata[i][j] = 0.0d;
                } else {
                    Hdata[i][j] = 1.0d;
                }
            }
        }
        double[] hPowers = new double[m];
        hPowers[0] = h;
        for (i = 1; i < m; i++) {
            hPowers[i] = hPowers[i - 1] * h;
        }
        for (i = 0; i < m; i++) {
            Hdata[i][0] = Hdata[i][0] - hPowers[i];
            double[] dArr = Hdata[m - 1];
            dArr[i] = dArr[i] - hPowers[(m - i) - 1];
        }
        if (Double.compare(h, 0.5d) > 0) {
            dArr = Hdata[m - 1];
            dArr[0] = dArr[0] + FastMath.pow((2.0d * h) - 1.0d, m);
        }
        for (i = 0; i < m; i++) {
            for (j = 0; j < i + 1; j++) {
                if ((i - j) + 1 > 0) {
                    for (int g = 2; g <= (i - j) + 1; g++) {
                        dArr = Hdata[i];
                        dArr[j] = dArr[j] / ((double) g);
                    }
                }
            }
        }
        return MatrixUtils.createRealMatrix(Hdata);
    }

    private void checkArray(double[] array) {
        if (array == null) {
            throw new NullArgumentException(LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
        } else if (array.length < 2) {
            throw new InsufficientDataException(LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE, Integer.valueOf(array.length), Integer.valueOf(2));
        }
    }

    public double ksSum(double t, double tolerance, int maxIterations) {
        double x = (-2.0d * t) * t;
        int sign = -1;
        long i = 1;
        double partialSum = 0.5d;
        double delta = 1.0d;
        while (delta > tolerance && i < ((long) maxIterations)) {
            delta = FastMath.exp((((double) i) * x) * ((double) i));
            partialSum += ((double) sign) * delta;
            sign *= -1;
            i++;
        }
        if (i != ((long) maxIterations)) {
            return 2.0d * partialSum;
        }
        throw new TooManyIterationsException(Integer.valueOf(maxIterations));
    }

    public double exactP(double d, int n, int m, boolean strict) {
        Iterator<int[]> combinationsIterator = CombinatoricsUtils.combinationsIterator(n + m, n);
        long tail = 0;
        double[] nSet = new double[n];
        double[] mSet = new double[m];
        while (combinationsIterator.hasNext()) {
            int[] nSetI = (int[]) combinationsIterator.next();
            int i = 0;
            int k = 0;
            int j = 0;
            while (i < n + m) {
                int k2;
                int j2;
                if (j >= n || nSetI[j] != i) {
                    k2 = k + 1;
                    mSet[k] = (double) i;
                    j2 = j;
                } else {
                    j2 = j + 1;
                    nSet[j] = (double) i;
                    k2 = k;
                }
                i++;
                k = k2;
                j = j2;
            }
            double curD = kolmogorovSmirnovStatistic(nSet, mSet);
            if (curD > d) {
                tail++;
            } else if (curD == d && !strict) {
                tail++;
            }
        }
        return ((double) tail) / ((double) CombinatoricsUtils.binomialCoefficient(n + m, n));
    }

    public double approximateP(double d, int n, int m) {
        double dm = (double) m;
        double dn = (double) n;
        return 1.0d - ksSum(d * FastMath.sqrt((dm * dn) / (dm + dn)), KS_SUM_CAUCHY_CRITERION, MAXIMUM_PARTIAL_SUM_COUNT);
    }

    public double monteCarloP(double d, int n, int m, boolean strict, int iterations) {
        int[] nPlusMSet = MathArrays.natural(m + n);
        double[] nSet = new double[n];
        double[] mSet = new double[m];
        int tail = 0;
        for (int i = 0; i < iterations; i++) {
            copyPartition(nSet, mSet, nPlusMSet, n, m);
            double curD = kolmogorovSmirnovStatistic(nSet, mSet);
            if (curD > d) {
                tail++;
            } else if (curD == d && !strict) {
                tail++;
            }
            MathArrays.shuffle(nPlusMSet, this.rng);
            Arrays.sort(nPlusMSet, 0, n);
        }
        return ((double) tail) / ((double) iterations);
    }

    private void copyPartition(double[] nSet, double[] mSet, int[] nSetI, int n, int m) {
        int i = 0;
        int k = 0;
        int j = 0;
        while (i < n + m) {
            int k2;
            int j2;
            if (j >= n || nSetI[j] != i) {
                k2 = k + 1;
                mSet[k] = (double) i;
                j2 = j;
            } else {
                j2 = j + 1;
                nSet[j] = (double) i;
                k2 = k;
            }
            i++;
            k = k2;
            j = j2;
        }
    }
}
