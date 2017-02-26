package org.apache.commons.math4.stat.correlation;

import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

class StorelessBivariateCovariance {
    private boolean biasCorrected;
    private double covarianceNumerator;
    private double meanX;
    private double meanY;
    private double n;

    public StorelessBivariateCovariance() {
        this(true);
    }

    public StorelessBivariateCovariance(boolean biasCorrection) {
        this.meanY = 0.0d;
        this.meanX = 0.0d;
        this.n = 0.0d;
        this.covarianceNumerator = 0.0d;
        this.biasCorrected = biasCorrection;
    }

    public void increment(double x, double y) {
        this.n += 1.0d;
        double deltaX = x - this.meanX;
        double deltaY = y - this.meanY;
        this.meanX += deltaX / this.n;
        this.meanY += deltaY / this.n;
        this.covarianceNumerator += (((this.n - 1.0d) / this.n) * deltaX) * deltaY;
    }

    public void append(StorelessBivariateCovariance cov) {
        double oldN = this.n;
        this.n += cov.n;
        double deltaX = cov.meanX - this.meanX;
        double deltaY = cov.meanY - this.meanY;
        this.meanX += (cov.n * deltaX) / this.n;
        this.meanY += (cov.n * deltaY) / this.n;
        this.covarianceNumerator += cov.covarianceNumerator + ((((cov.n * oldN) / this.n) * deltaX) * deltaY);
    }

    public double getN() {
        return this.n;
    }

    public double getResult() throws NumberIsTooSmallException {
        if (this.n < 2.0d) {
            throw new NumberIsTooSmallException(LocalizedFormats.INSUFFICIENT_DIMENSION, Double.valueOf(this.n), Integer.valueOf(2), true);
        } else if (this.biasCorrected) {
            return this.covarianceNumerator / (this.n - 1.0d);
        } else {
            return this.covarianceNumerator / this.n;
        }
    }
}
