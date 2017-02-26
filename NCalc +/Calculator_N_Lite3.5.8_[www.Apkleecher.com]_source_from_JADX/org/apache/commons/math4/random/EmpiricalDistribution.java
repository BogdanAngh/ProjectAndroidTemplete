package org.apache.commons.math4.random;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.distribution.ConstantRealDistribution;
import org.apache.commons.math4.distribution.NormalDistribution;
import org.apache.commons.math4.distribution.RealDistribution;
import org.apache.commons.math4.distribution.WeibullDistribution;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.stat.descriptive.StatisticalSummary;
import org.apache.commons.math4.stat.descriptive.SummaryStatistics;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class EmpiricalDistribution extends AbstractRealDistribution {
    public static final int DEFAULT_BIN_COUNT = 1000;
    private static final String FILE_CHARSET = "US-ASCII";
    private static final long serialVersionUID = 5729073523949762654L;
    private final int binCount;
    private final List<SummaryStatistics> binStats;
    private double delta;
    private boolean loaded;
    private double max;
    private double min;
    protected final RandomDataGenerator randomData;
    private SummaryStatistics sampleStats;
    private double[] upperBounds;

    private abstract class DataAdapter {
        public abstract void computeBinStats() throws IOException;

        public abstract void computeStats() throws IOException;

        private DataAdapter() {
        }
    }

    private class ArrayDataAdapter extends DataAdapter {
        private final double[] inputArray;

        public ArrayDataAdapter(double[] in) throws NullArgumentException {
            super(null);
            MathUtils.checkNotNull(in);
            this.inputArray = in;
        }

        public void computeStats() throws IOException {
            EmpiricalDistribution.this.sampleStats = new SummaryStatistics();
            for (double addValue : this.inputArray) {
                EmpiricalDistribution.this.sampleStats.addValue(addValue);
            }
        }

        public void computeBinStats() throws IOException {
            for (int i = 0; i < this.inputArray.length; i++) {
                ((SummaryStatistics) EmpiricalDistribution.this.binStats.get(EmpiricalDistribution.this.findBin(this.inputArray[i]))).addValue(this.inputArray[i]);
            }
        }
    }

    private class StreamDataAdapter extends DataAdapter {
        private BufferedReader inputStream;

        public StreamDataAdapter(BufferedReader in) {
            super(null);
            this.inputStream = in;
        }

        public void computeBinStats() throws IOException {
            while (true) {
                String str = this.inputStream.readLine();
                if (str == null) {
                    this.inputStream.close();
                    this.inputStream = null;
                    return;
                }
                double val = Double.parseDouble(str);
                ((SummaryStatistics) EmpiricalDistribution.this.binStats.get(EmpiricalDistribution.this.findBin(val))).addValue(val);
            }
        }

        public void computeStats() throws IOException {
            EmpiricalDistribution.this.sampleStats = new SummaryStatistics();
            while (true) {
                String str = this.inputStream.readLine();
                if (str == null) {
                    this.inputStream.close();
                    this.inputStream = null;
                    return;
                }
                EmpiricalDistribution.this.sampleStats.addValue(Double.parseDouble(str));
            }
        }
    }

    public EmpiricalDistribution() {
        this((int) DEFAULT_BIN_COUNT);
    }

    public EmpiricalDistribution(int binCount) {
        this(binCount, new RandomDataGenerator());
    }

    public EmpiricalDistribution(int binCount, RandomGenerator generator) {
        this(binCount, new RandomDataGenerator(generator));
    }

    public EmpiricalDistribution(RandomGenerator generator) {
        this((int) DEFAULT_BIN_COUNT, generator);
    }

    private EmpiricalDistribution(int binCount, RandomDataGenerator randomData) {
        super(randomData.getRandomGenerator());
        this.sampleStats = null;
        this.max = Double.NEGATIVE_INFINITY;
        this.min = Double.POSITIVE_INFINITY;
        this.delta = 0.0d;
        this.loaded = false;
        this.upperBounds = null;
        this.binCount = binCount;
        this.randomData = randomData;
        this.binStats = new ArrayList();
    }

    public void load(double[] in) throws NullArgumentException {
        try {
            new ArrayDataAdapter(in).computeStats();
            fillBinStats(new ArrayDataAdapter(in));
            this.loaded = true;
        } catch (IOException e) {
            throw new MathInternalError();
        }
    }

    public void load(URL url) throws IOException, NullArgumentException, ZeroException {
        Throwable th;
        MathUtils.checkNotNull(url);
        Charset charset = Charset.forName(FILE_CHARSET);
        BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream(), charset));
        try {
            new StreamDataAdapter(in).computeStats();
            if (this.sampleStats.getN() == 0) {
                throw new ZeroException(LocalizedFormats.URL_CONTAINS_NO_DATA, url);
            }
            BufferedReader in2 = new BufferedReader(new InputStreamReader(url.openStream(), charset));
            try {
                fillBinStats(new StreamDataAdapter(in2));
                this.loaded = true;
                try {
                    in2.close();
                } catch (IOException e) {
                }
            } catch (Throwable th2) {
                th = th2;
                in = in2;
                try {
                    in.close();
                } catch (IOException e2) {
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            in.close();
            throw th;
        }
    }

    public void load(File file) throws IOException, NullArgumentException {
        Throwable th;
        InputStream inputStream;
        MathUtils.checkNotNull(file);
        Charset charset = Charset.forName(FILE_CHARSET);
        BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
        try {
            new StreamDataAdapter(in).computeStats();
            InputStream is = new FileInputStream(file);
            try {
                BufferedReader in2 = new BufferedReader(new InputStreamReader(is, charset));
                try {
                    fillBinStats(new StreamDataAdapter(in2));
                    this.loaded = true;
                    try {
                        in2.close();
                    } catch (IOException e) {
                    }
                } catch (Throwable th2) {
                    th = th2;
                    in = in2;
                    inputStream = is;
                    try {
                        in.close();
                    } catch (IOException e2) {
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                th = th3;
                inputStream = is;
                in.close();
                throw th;
            }
        } catch (Throwable th4) {
            th = th4;
            in.close();
            throw th;
        }
    }

    private void fillBinStats(DataAdapter da) throws IOException {
        int i;
        this.min = this.sampleStats.getMin();
        this.max = this.sampleStats.getMax();
        this.delta = (this.max - this.min) / ((double) this.binCount);
        if (!this.binStats.isEmpty()) {
            this.binStats.clear();
        }
        for (i = 0; i < this.binCount; i++) {
            this.binStats.add(i, new SummaryStatistics());
        }
        da.computeBinStats();
        this.upperBounds = new double[this.binCount];
        this.upperBounds[0] = ((double) ((SummaryStatistics) this.binStats.get(0)).getN()) / ((double) this.sampleStats.getN());
        for (i = 1; i < this.binCount - 1; i++) {
            this.upperBounds[i] = this.upperBounds[i - 1] + (((double) ((SummaryStatistics) this.binStats.get(i)).getN()) / ((double) this.sampleStats.getN()));
        }
        this.upperBounds[this.binCount - 1] = 1.0d;
    }

    private int findBin(double value) {
        return FastMath.min(FastMath.max(((int) FastMath.ceil((value - this.min) / this.delta)) - 1, 0), this.binCount - 1);
    }

    public double getNextValue() throws MathIllegalStateException {
        if (this.loaded) {
            return sample();
        }
        throw new MathIllegalStateException(LocalizedFormats.DISTRIBUTION_NOT_LOADED, new Object[0]);
    }

    public StatisticalSummary getSampleStats() {
        return this.sampleStats;
    }

    public int getBinCount() {
        return this.binCount;
    }

    public List<SummaryStatistics> getBinStats() {
        return this.binStats;
    }

    public double[] getUpperBounds() {
        double[] binUpperBounds = new double[this.binCount];
        for (int i = 0; i < this.binCount - 1; i++) {
            binUpperBounds[i] = this.min + (this.delta * ((double) (i + 1)));
        }
        binUpperBounds[this.binCount - 1] = this.max;
        return binUpperBounds;
    }

    public double[] getGeneratorUpperBounds() {
        int len = this.upperBounds.length;
        double[] out = new double[len];
        System.arraycopy(this.upperBounds, 0, out, 0, len);
        return out;
    }

    public boolean isLoaded() {
        return this.loaded;
    }

    public void reSeed(long seed) {
        this.randomData.reSeed(seed);
    }

    public double probability(double x) {
        return 0.0d;
    }

    public double density(double x) {
        if (x < this.min || x > this.max) {
            return 0.0d;
        }
        int binIndex = findBin(x);
        return (getKernel((SummaryStatistics) this.binStats.get(binIndex)).density(x) * pB(binIndex)) / kB(binIndex);
    }

    public double cumulativeProbability(double x) {
        if (x < this.min) {
            return 0.0d;
        }
        if (x >= this.max) {
            return 1.0d;
        }
        int binIndex = findBin(x);
        double pBminus = pBminus(binIndex);
        double pB = pB(binIndex);
        RealDistribution kernel = k(x);
        if (!(kernel instanceof ConstantRealDistribution)) {
            return pBminus + (pB * ((kernel.cumulativeProbability(x) - kernel.cumulativeProbability(binIndex == 0 ? this.min : getUpperBounds()[binIndex - 1])) / kB(binIndex)));
        } else if (x >= kernel.getNumericalMean()) {
            return pBminus + pB;
        } else {
            return pBminus;
        }
    }

    public double inverseCumulativeProbability(double p) throws OutOfRangeException {
        if (p < 0.0d || p > 1.0d) {
            throw new OutOfRangeException(Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(1));
        } else if (p == 0.0d) {
            return getSupportLowerBound();
        } else {
            if (p == 1.0d) {
                return getSupportUpperBound();
            }
            int i = 0;
            while (cumBinP(i) < p) {
                i++;
            }
            RealDistribution kernel = getKernel((SummaryStatistics) this.binStats.get(i));
            double kB = kB(i);
            double lower = i == 0 ? this.min : getUpperBounds()[i - 1];
            double kBminus = kernel.cumulativeProbability(lower);
            double pB = pB(i);
            double pCrit = p - pBminus(i);
            if (pCrit <= 0.0d) {
                return lower;
            }
            return kernel.inverseCumulativeProbability(((pCrit * kB) / pB) + kBminus);
        }
    }

    public double getNumericalMean() {
        return this.sampleStats.getMean();
    }

    public double getNumericalVariance() {
        return this.sampleStats.getVariance();
    }

    public double getSupportLowerBound() {
        return this.min;
    }

    public double getSupportUpperBound() {
        return this.max;
    }

    public boolean isSupportConnected() {
        return true;
    }

    public void reseedRandomGenerator(long seed) {
        this.randomData.reSeed(seed);
    }

    private double pB(int i) {
        if (i == 0) {
            return this.upperBounds[0];
        }
        return this.upperBounds[i] - this.upperBounds[i - 1];
    }

    private double pBminus(int i) {
        return i == 0 ? 0.0d : this.upperBounds[i - 1];
    }

    private double kB(int i) {
        double[] binBounds = getUpperBounds();
        RealDistribution kernel = getKernel((SummaryStatistics) this.binStats.get(i));
        if (i == 0) {
            return kernel.probability(this.min, binBounds[0]);
        }
        return kernel.probability(binBounds[i - 1], binBounds[i]);
    }

    private RealDistribution k(double x) {
        return getKernel((SummaryStatistics) this.binStats.get(findBin(x)));
    }

    private double cumBinP(int binIndex) {
        return this.upperBounds[binIndex];
    }

    protected RealDistribution getKernel(SummaryStatistics bStats) {
        if (bStats.getN() == 1 || bStats.getVariance() == 0.0d) {
            return new ConstantRealDistribution(bStats.getMean());
        }
        return new NormalDistribution(this.randomData.getRandomGenerator(), bStats.getMean(), bStats.getStandardDeviation(), WeibullDistribution.DEFAULT_INVERSE_ABSOLUTE_ACCURACY);
    }
}
