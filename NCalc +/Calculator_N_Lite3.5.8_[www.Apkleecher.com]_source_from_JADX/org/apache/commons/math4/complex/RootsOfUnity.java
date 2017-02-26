package org.apache.commons.math4.complex;

import com.example.duy.calculator.geom2d.util.Angle2D;
import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public class RootsOfUnity implements Serializable {
    private static final long serialVersionUID = 20120201;
    private boolean isCounterClockWise;
    private int omegaCount;
    private double[] omegaImaginaryClockwise;
    private double[] omegaImaginaryCounterClockwise;
    private double[] omegaReal;

    public RootsOfUnity() {
        this.omegaCount = 0;
        this.omegaReal = null;
        this.omegaImaginaryCounterClockwise = null;
        this.omegaImaginaryClockwise = null;
        this.isCounterClockWise = true;
    }

    public synchronized boolean isCounterClockWise() throws MathIllegalStateException {
        if (this.omegaCount == 0) {
            throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
        }
        return this.isCounterClockWise;
    }

    public synchronized void computeRoots(int n) throws ZeroException {
        boolean z = false;
        synchronized (this) {
            if (n == 0) {
                throw new ZeroException(LocalizedFormats.CANNOT_COMPUTE_0TH_ROOT_OF_UNITY, new Object[0]);
            }
            if (n > 0) {
                z = true;
            }
            this.isCounterClockWise = z;
            int absN = FastMath.abs(n);
            if (absN != this.omegaCount) {
                double t = Angle2D.M_2PI / ((double) absN);
                double cosT = FastMath.cos(t);
                double sinT = FastMath.sin(t);
                this.omegaReal = new double[absN];
                this.omegaImaginaryCounterClockwise = new double[absN];
                this.omegaImaginaryClockwise = new double[absN];
                this.omegaReal[0] = 1.0d;
                this.omegaImaginaryCounterClockwise[0] = 0.0d;
                this.omegaImaginaryClockwise[0] = 0.0d;
                for (int i = 1; i < absN; i++) {
                    this.omegaReal[i] = (this.omegaReal[i - 1] * cosT) - (this.omegaImaginaryCounterClockwise[i - 1] * sinT);
                    this.omegaImaginaryCounterClockwise[i] = (this.omegaReal[i - 1] * sinT) + (this.omegaImaginaryCounterClockwise[i - 1] * cosT);
                    this.omegaImaginaryClockwise[i] = -this.omegaImaginaryCounterClockwise[i];
                }
                this.omegaCount = absN;
            }
        }
    }

    public synchronized double getReal(int k) throws MathIllegalStateException, MathIllegalArgumentException {
        if (this.omegaCount == 0) {
            throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
        }
        if (k >= 0) {
            if (k < this.omegaCount) {
            }
        }
        throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, Integer.valueOf(k), Integer.valueOf(0), Integer.valueOf(this.omegaCount - 1));
        return this.omegaReal[k];
    }

    public synchronized double getImaginary(int k) throws MathIllegalStateException, OutOfRangeException {
        double d;
        if (this.omegaCount == 0) {
            throw new MathIllegalStateException(LocalizedFormats.ROOTS_OF_UNITY_NOT_COMPUTED_YET, new Object[0]);
        }
        if (k >= 0) {
            if (k < this.omegaCount) {
                if (this.isCounterClockWise) {
                    d = this.omegaImaginaryCounterClockwise[k];
                } else {
                    d = this.omegaImaginaryClockwise[k];
                }
            }
        }
        throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE_ROOT_OF_UNITY_INDEX, Integer.valueOf(k), Integer.valueOf(0), Integer.valueOf(this.omegaCount - 1));
        return d;
    }

    public synchronized int getNumberOfRoots() {
        return this.omegaCount;
    }
}
