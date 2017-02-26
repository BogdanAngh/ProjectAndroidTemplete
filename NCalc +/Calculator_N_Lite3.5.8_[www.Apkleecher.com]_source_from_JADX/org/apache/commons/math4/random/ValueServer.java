package org.apache.commons.math4.random;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class ValueServer {
    public static final int CONSTANT_MODE = 5;
    public static final int DIGEST_MODE = 0;
    public static final int EXPONENTIAL_MODE = 3;
    public static final int GAUSSIAN_MODE = 4;
    public static final int REPLAY_MODE = 1;
    public static final int UNIFORM_MODE = 2;
    private EmpiricalDistribution empiricalDistribution;
    private BufferedReader filePointer;
    private int mode;
    private double mu;
    private final RandomDataGenerator randomData;
    private double sigma;
    private URL valuesFileURL;

    public ValueServer() {
        this.mode = CONSTANT_MODE;
        this.valuesFileURL = null;
        this.mu = 0.0d;
        this.sigma = 0.0d;
        this.empiricalDistribution = null;
        this.filePointer = null;
        this.randomData = new RandomDataGenerator();
    }

    public ValueServer(RandomGenerator generator) {
        this.mode = CONSTANT_MODE;
        this.valuesFileURL = null;
        this.mu = 0.0d;
        this.sigma = 0.0d;
        this.empiricalDistribution = null;
        this.filePointer = null;
        this.randomData = new RandomDataGenerator(generator);
    }

    public double getNext() throws IOException, MathIllegalStateException, MathIllegalArgumentException {
        switch (this.mode) {
            case DIGEST_MODE /*0*/:
                return getNextDigest();
            case REPLAY_MODE /*1*/:
                return getNextReplay();
            case UNIFORM_MODE /*2*/:
                return getNextUniform();
            case EXPONENTIAL_MODE /*3*/:
                return getNextExponential();
            case GAUSSIAN_MODE /*4*/:
                return getNextGaussian();
            case CONSTANT_MODE /*5*/:
                return this.mu;
            default:
                throw new MathIllegalStateException(LocalizedFormats.UNKNOWN_MODE, Integer.valueOf(this.mode), "DIGEST_MODE", Integer.valueOf(DIGEST_MODE), "REPLAY_MODE", Integer.valueOf(REPLAY_MODE), "UNIFORM_MODE", Integer.valueOf(UNIFORM_MODE), "EXPONENTIAL_MODE", Integer.valueOf(EXPONENTIAL_MODE), "GAUSSIAN_MODE", Integer.valueOf(GAUSSIAN_MODE), "CONSTANT_MODE", Integer.valueOf(CONSTANT_MODE));
        }
    }

    public void fill(double[] values) throws IOException, MathIllegalStateException, MathIllegalArgumentException {
        for (int i = DIGEST_MODE; i < values.length; i += REPLAY_MODE) {
            values[i] = getNext();
        }
    }

    public double[] fill(int length) throws IOException, MathIllegalStateException, MathIllegalArgumentException {
        double[] out = new double[length];
        for (int i = DIGEST_MODE; i < length; i += REPLAY_MODE) {
            out[i] = getNext();
        }
        return out;
    }

    public void computeDistribution() throws IOException, ZeroException, NullArgumentException {
        computeDistribution(EmpiricalDistribution.DEFAULT_BIN_COUNT);
    }

    public void computeDistribution(int binCount) throws NullArgumentException, IOException, ZeroException {
        this.empiricalDistribution = new EmpiricalDistribution(binCount, this.randomData.getRandomGenerator());
        this.empiricalDistribution.load(this.valuesFileURL);
        this.mu = this.empiricalDistribution.getSampleStats().getMean();
        this.sigma = this.empiricalDistribution.getSampleStats().getStandardDeviation();
    }

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public URL getValuesFileURL() {
        return this.valuesFileURL;
    }

    public void setValuesFileURL(String url) throws MalformedURLException {
        this.valuesFileURL = new URL(url);
    }

    public void setValuesFileURL(URL url) {
        this.valuesFileURL = url;
    }

    public EmpiricalDistribution getEmpiricalDistribution() {
        return this.empiricalDistribution;
    }

    public void resetReplayFile() throws IOException {
        if (this.filePointer != null) {
            try {
                this.filePointer.close();
                this.filePointer = null;
            } catch (IOException e) {
            }
        }
        this.filePointer = new BufferedReader(new InputStreamReader(this.valuesFileURL.openStream(), "UTF-8"));
    }

    public void closeReplayFile() throws IOException {
        if (this.filePointer != null) {
            this.filePointer.close();
            this.filePointer = null;
        }
    }

    public double getMu() {
        return this.mu;
    }

    public void setMu(double mu) {
        this.mu = mu;
    }

    public double getSigma() {
        return this.sigma;
    }

    public void setSigma(double sigma) {
        this.sigma = sigma;
    }

    public void reSeed(long seed) {
        this.randomData.reSeed(seed);
    }

    private double getNextDigest() throws MathIllegalStateException {
        if (this.empiricalDistribution != null && this.empiricalDistribution.getBinStats().size() != 0) {
            return this.empiricalDistribution.getNextValue();
        }
        throw new MathIllegalStateException(LocalizedFormats.DIGEST_NOT_INITIALIZED, new Object[DIGEST_MODE]);
    }

    private double getNextReplay() throws IOException, MathIllegalStateException {
        if (this.filePointer == null) {
            resetReplayFile();
        }
        String str = this.filePointer.readLine();
        if (str == null) {
            closeReplayFile();
            resetReplayFile();
            str = this.filePointer.readLine();
            if (str == null) {
                Localizable localizable = LocalizedFormats.URL_CONTAINS_NO_DATA;
                Object[] objArr = new Object[REPLAY_MODE];
                objArr[DIGEST_MODE] = this.valuesFileURL;
                throw new MathIllegalStateException(localizable, objArr);
            }
        }
        return Double.parseDouble(str);
    }

    private double getNextUniform() throws MathIllegalArgumentException {
        return this.randomData.nextUniform(0.0d, 2.0d * this.mu);
    }

    private double getNextExponential() throws MathIllegalArgumentException {
        return this.randomData.nextExponential(this.mu);
    }

    private double getNextGaussian() throws MathIllegalArgumentException {
        return this.randomData.nextGaussian(this.mu, this.sigma);
    }
}
