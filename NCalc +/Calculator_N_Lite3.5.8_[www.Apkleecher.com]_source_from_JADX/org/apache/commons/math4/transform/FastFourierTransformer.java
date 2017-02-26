package org.apache.commons.math4.transform;

import java.io.Serializable;
import java.lang.reflect.Array;
import org.apache.commons.math4.analysis.FunctionUtils;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.complex.Complex;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.ArithmeticUtils;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.matheclipse.core.interfaces.IExpr;

public class FastFourierTransformer implements Serializable {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$transform$DftNormalization = null;
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final double[] W_SUB_N_I;
    private static final double[] W_SUB_N_R;
    static final long serialVersionUID = 20120210;
    private final DftNormalization normalization;

    @Deprecated
    private static class MultiDimensionalComplexMatrix implements Cloneable {
        protected int[] dimensionSize;
        protected Object multiDimensionalComplexArray;

        public MultiDimensionalComplexMatrix(Object multiDimensionalComplexArray) {
            Object lastDimension;
            this.multiDimensionalComplexArray = multiDimensionalComplexArray;
            int numOfDimensions = 0;
            for (lastDimension = multiDimensionalComplexArray; lastDimension instanceof Object[]; lastDimension = ((Object[]) lastDimension)[0]) {
                numOfDimensions++;
            }
            this.dimensionSize = new int[numOfDimensions];
            numOfDimensions = 0;
            lastDimension = multiDimensionalComplexArray;
            while (lastDimension instanceof Object[]) {
                Object[] array = (Object[]) lastDimension;
                int numOfDimensions2 = numOfDimensions + 1;
                this.dimensionSize[numOfDimensions] = array.length;
                lastDimension = array[0];
                numOfDimensions = numOfDimensions2;
            }
        }

        public Complex get(int... vector) throws DimensionMismatchException {
            if (vector == null) {
                if (this.dimensionSize.length <= 0) {
                    return null;
                }
                throw new DimensionMismatchException(0, this.dimensionSize.length);
            } else if (vector.length != this.dimensionSize.length) {
                throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
            } else {
                Object lastDimension = this.multiDimensionalComplexArray;
                for (int i = 0; i < this.dimensionSize.length; i++) {
                    lastDimension = ((Object[]) lastDimension)[vector[i]];
                }
                return (Complex) lastDimension;
            }
        }

        public Complex set(Complex magnitude, int... vector) throws DimensionMismatchException {
            if (vector == null) {
                if (this.dimensionSize.length <= 0) {
                    return null;
                }
                throw new DimensionMismatchException(0, this.dimensionSize.length);
            } else if (vector.length != this.dimensionSize.length) {
                throw new DimensionMismatchException(vector.length, this.dimensionSize.length);
            } else {
                Object[] lastDimension = this.multiDimensionalComplexArray;
                for (int i = 0; i < this.dimensionSize.length - 1; i++) {
                    lastDimension = lastDimension[vector[i]];
                }
                Complex lastValue = lastDimension[vector[this.dimensionSize.length - 1]];
                lastDimension[vector[this.dimensionSize.length - 1]] = magnitude;
                return lastValue;
            }
        }

        public int[] getDimensionSizes() {
            return (int[]) this.dimensionSize.clone();
        }

        public Object getArray() {
            return this.multiDimensionalComplexArray;
        }

        public Object clone() {
            MultiDimensionalComplexMatrix mdcm = new MultiDimensionalComplexMatrix(Array.newInstance(Complex.class, this.dimensionSize));
            clone(mdcm);
            return mdcm;
        }

        private void clone(MultiDimensionalComplexMatrix mdcm) {
            int i;
            int i2 = 0;
            int[] vector = new int[this.dimensionSize.length];
            int size = 1;
            for (int i3 : this.dimensionSize) {
                size *= i3;
            }
            int[][] vectorList = (int[][]) Array.newInstance(Integer.TYPE, new int[]{size, this.dimensionSize.length});
            for (int[] nextVector : vectorList) {
                int[] nextVector2;
                System.arraycopy(vector, 0, nextVector2, 0, this.dimensionSize.length);
                for (i = 0; i < this.dimensionSize.length; i++) {
                    vector[i] = vector[i] + 1;
                    if (vector[i] < this.dimensionSize[i]) {
                        break;
                    }
                    vector[i] = 0;
                }
            }
            int i32 = vectorList.length;
            while (i2 < i32) {
                nextVector2 = vectorList[i2];
                mdcm.set(get(nextVector2), nextVector2);
                i2++;
            }
        }
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$transform$DftNormalization() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$transform$DftNormalization;
        if (iArr == null) {
            iArr = new int[DftNormalization.values().length];
            try {
                iArr[DftNormalization.STANDARD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[DftNormalization.UNITARY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$transform$DftNormalization = iArr;
        }
        return iArr;
    }

    static {
        boolean z;
        if (FastFourierTransformer.class.desiredAssertionStatus()) {
            z = $assertionsDisabled;
        } else {
            z = true;
        }
        $assertionsDisabled = z;
        W_SUB_N_R = new double[]{1.0d, -1.0d, 6.123233995736766E-17d, 0.7071067811865476d, 0.9238795325112867d, 0.9807852804032304d, 0.9951847266721969d, 0.9987954562051724d, 0.9996988186962042d, 0.9999247018391445d, 0.9999811752826011d, 0.9999952938095762d, 0.9999988234517019d, 0.9999997058628822d, 0.9999999264657179d, 0.9999999816164293d, 0.9999999954041073d, 0.9999999988510269d, 0.9999999997127567d, 0.9999999999281892d, 0.9999999999820472d, 0.9999999999955118d, 0.999999999998878d, 0.9999999999997194d, 0.9999999999999298d, 0.9999999999999825d, 0.9999999999999957d, 0.9999999999999989d, 0.9999999999999998d, 0.9999999999999999d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d};
        W_SUB_N_I = new double[]{2.4492935982947064E-16d, -1.2246467991473532E-16d, -1.0d, -0.7071067811865475d, -0.3826834323650898d, -0.19509032201612825d, -0.0980171403295606d, -0.049067674327418015d, -0.024541228522912288d, -0.012271538285719925d, -0.006135884649154475d, -0.003067956762965976d, -0.0015339801862847655d, -7.669903187427045E-4d, -3.8349518757139556E-4d, -1.917475973107033E-4d, -9.587379909597734E-5d, -4.793689960306688E-5d, -2.396844980841822E-5d, -1.1984224905069705E-5d, -5.9921124526424275E-6d, -2.996056226334661E-6d, -1.4980281131690111E-6d, -7.490140565847157E-7d, -3.7450702829238413E-7d, -1.8725351414619535E-7d, -9.362675707309808E-8d, -4.681337853654909E-8d, -2.340668926827455E-8d, -1.1703344634137277E-8d, -5.8516723170686385E-9d, -2.9258361585343192E-9d, -1.4629180792671596E-9d, -7.314590396335798E-10d, -3.657295198167899E-10d, -1.8286475990839495E-10d, -9.143237995419748E-11d, -4.571618997709874E-11d, -2.285809498854937E-11d, -1.1429047494274685E-11d, -5.714523747137342E-12d, -2.857261873568671E-12d, -1.4286309367843356E-12d, -7.143154683921678E-13d, -3.571577341960839E-13d, -1.7857886709804195E-13d, -8.928943354902097E-14d, -4.4644716774510487E-14d, -2.2322358387255243E-14d, -1.1161179193627622E-14d, -5.580589596813811E-15d, -2.7902947984069054E-15d, -1.3951473992034527E-15d, -6.975736996017264E-16d, -3.487868498008632E-16d, -1.743934249004316E-16d, -8.71967124502158E-17d, -4.35983562251079E-17d, -2.179917811255395E-17d, -1.0899589056276974E-17d, -5.449794528138487E-18d, -2.7248972640692436E-18d, -1.3624486320346218E-18d};
    }

    public FastFourierTransformer(DftNormalization normalization) {
        this.normalization = normalization;
    }

    private static void bitReversalShuffle2(double[] a, double[] b) {
        int n = a.length;
        if ($assertionsDisabled || b.length == n) {
            int halfOfN = n >> 1;
            int j = 0;
            for (int i = 0; i < n; i++) {
                if (i < j) {
                    double temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                    temp = b[i];
                    b[i] = b[j];
                    b[j] = temp;
                }
                int k = halfOfN;
                while (k <= j && k > 0) {
                    j -= k;
                    k >>= 1;
                }
                j += k;
            }
            return;
        }
        throw new AssertionError();
    }

    private static void normalizeTransformedData(double[][] dataRI, DftNormalization normalization, TransformType type) {
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        int n = dataR.length;
        if ($assertionsDisabled || dataI.length == n) {
            double scaleFactor;
            int i;
            switch ($SWITCH_TABLE$org$apache$commons$math4$transform$DftNormalization()[normalization.ordinal()]) {
                case ValueServer.REPLAY_MODE /*1*/:
                    if (type == TransformType.INVERSE) {
                        scaleFactor = 1.0d / ((double) n);
                        for (i = 0; i < n; i++) {
                            dataR[i] = dataR[i] * scaleFactor;
                            dataI[i] = dataI[i] * scaleFactor;
                        }
                        return;
                    }
                    return;
                case IExpr.DOUBLEID /*2*/:
                    scaleFactor = 1.0d / FastMath.sqrt((double) n);
                    for (i = 0; i < n; i++) {
                        dataR[i] = dataR[i] * scaleFactor;
                        dataI[i] = dataI[i] * scaleFactor;
                    }
                    return;
                default:
                    throw new MathIllegalStateException();
            }
        }
        throw new AssertionError();
    }

    public static void transformInPlace(double[][] dataRI, DftNormalization normalization, TransformType type) {
        int length = dataRI.length;
        if (r0 != 2) {
            throw new DimensionMismatchException(dataRI.length, 2);
        }
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        if (dataR.length != dataI.length) {
            throw new DimensionMismatchException(dataI.length, dataR.length);
        }
        int n = dataR.length;
        if (!ArithmeticUtils.isPowerOfTwo((long) n)) {
            throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, Integer.valueOf(n));
        } else if (n != 1) {
            double srcR0;
            double srcI0;
            double srcR1;
            double srcI1;
            if (n == 2) {
                srcR0 = dataR[0];
                srcI0 = dataI[0];
                srcR1 = dataR[1];
                srcI1 = dataI[1];
                dataR[0] = srcR0 + srcR1;
                dataI[0] = srcI0 + srcI1;
                dataR[1] = srcR0 - srcR1;
                dataI[1] = srcI0 - srcI1;
                normalizeTransformedData(dataRI, normalization, type);
                return;
            }
            bitReversalShuffle2(dataR, dataI);
            int i0;
            int i1;
            int i2;
            int i3;
            double srcR2;
            double srcI2;
            double srcR3;
            double srcI3;
            if (type == TransformType.INVERSE) {
                for (i0 = 0; i0 < n; i0 += 4) {
                    i1 = i0 + 1;
                    i2 = i0 + 2;
                    i3 = i0 + 3;
                    srcR0 = dataR[i0];
                    srcI0 = dataI[i0];
                    srcR1 = dataR[i2];
                    srcI1 = dataI[i2];
                    srcR2 = dataR[i1];
                    srcI2 = dataI[i1];
                    srcR3 = dataR[i3];
                    srcI3 = dataI[i3];
                    dataR[i0] = ((srcR0 + srcR1) + srcR2) + srcR3;
                    dataI[i0] = ((srcI0 + srcI1) + srcI2) + srcI3;
                    dataR[i1] = (srcR0 - srcR2) + (srcI3 - srcI1);
                    dataI[i1] = (srcI0 - srcI2) + (srcR1 - srcR3);
                    dataR[i2] = ((srcR0 - srcR1) + srcR2) - srcR3;
                    dataI[i2] = ((srcI0 - srcI1) + srcI2) - srcI3;
                    dataR[i3] = (srcR0 - srcR2) + (srcI1 - srcI3);
                    dataI[i3] = (srcI0 - srcI2) + (srcR3 - srcR1);
                }
            } else {
                for (i0 = 0; i0 < n; i0 += 4) {
                    i1 = i0 + 1;
                    i2 = i0 + 2;
                    i3 = i0 + 3;
                    srcR0 = dataR[i0];
                    srcI0 = dataI[i0];
                    srcR1 = dataR[i2];
                    srcI1 = dataI[i2];
                    srcR2 = dataR[i1];
                    srcI2 = dataI[i1];
                    srcR3 = dataR[i3];
                    srcI3 = dataI[i3];
                    dataR[i0] = ((srcR0 + srcR1) + srcR2) + srcR3;
                    dataI[i0] = ((srcI0 + srcI1) + srcI2) + srcI3;
                    dataR[i1] = (srcR0 - srcR2) + (srcI1 - srcI3);
                    dataI[i1] = (srcI0 - srcI2) + (srcR3 - srcR1);
                    dataR[i2] = ((srcR0 - srcR1) + srcR2) - srcR3;
                    dataI[i2] = ((srcI0 - srcI1) + srcI2) - srcI3;
                    dataR[i3] = (srcR0 - srcR2) + (srcI3 - srcI1);
                    dataI[i3] = (srcI0 - srcI2) + (srcR1 - srcR3);
                }
            }
            int lastN0 = 4;
            int lastLogN0 = 2;
            while (lastN0 < n) {
                int n0 = lastN0 << 1;
                int logN0 = lastLogN0 + 1;
                double wSubN0R = W_SUB_N_R[logN0];
                double wSubN0I = W_SUB_N_I[logN0];
                if (type == TransformType.INVERSE) {
                    wSubN0I = -wSubN0I;
                }
                for (int destEvenStartIndex = 0; destEvenStartIndex < n; destEvenStartIndex += n0) {
                    int destOddStartIndex = destEvenStartIndex + lastN0;
                    double wSubN0ToRR = 1.0d;
                    double wSubN0ToRI = 0.0d;
                    for (int r = 0; r < lastN0; r++) {
                        double grR = dataR[destEvenStartIndex + r];
                        double grI = dataI[destEvenStartIndex + r];
                        double hrR = dataR[destOddStartIndex + r];
                        double hrI = dataI[destOddStartIndex + r];
                        dataR[destEvenStartIndex + r] = ((wSubN0ToRR * hrR) + grR) - (wSubN0ToRI * hrI);
                        dataI[destEvenStartIndex + r] = ((wSubN0ToRR * hrI) + grI) + (wSubN0ToRI * hrR);
                        dataR[destOddStartIndex + r] = grR - ((wSubN0ToRR * hrR) - (wSubN0ToRI * hrI));
                        dataI[destOddStartIndex + r] = grI - ((wSubN0ToRR * hrI) + (wSubN0ToRI * hrR));
                        wSubN0ToRR = (wSubN0ToRR * wSubN0R) - (wSubN0ToRI * wSubN0I);
                        wSubN0ToRI = (wSubN0ToRR * wSubN0I) + (wSubN0ToRI * wSubN0R);
                    }
                }
                lastN0 = n0;
                lastLogN0 = logN0;
            }
            normalizeTransformedData(dataRI, normalization, type);
        }
    }

    public Complex[] transform(double[] f, TransformType type) {
        double[][] dataRI = new double[][]{MathArrays.copyOf(f, f.length), new double[f.length]};
        transformInPlace(dataRI, this.normalization, type);
        return TransformUtils.createComplexArray(dataRI);
    }

    public Complex[] transform(UnivariateFunction f, double min, double max, int n, TransformType type) {
        return transform(FunctionUtils.sample(f, min, max, n), type);
    }

    public Complex[] transform(Complex[] f, TransformType type) {
        double[][] dataRI = TransformUtils.createRealImaginaryArray(f);
        transformInPlace(dataRI, this.normalization, type);
        return TransformUtils.createComplexArray(dataRI);
    }

    @Deprecated
    public Object mdfft(Object mdca, TransformType type) {
        MultiDimensionalComplexMatrix mdcm = (MultiDimensionalComplexMatrix) new MultiDimensionalComplexMatrix(mdca).clone();
        int[] dimensionSize = mdcm.getDimensionSizes();
        for (int i = 0; i < dimensionSize.length; i++) {
            mdfft(mdcm, type, i, new int[0]);
        }
        return mdcm.getArray();
    }

    @Deprecated
    private void mdfft(MultiDimensionalComplexMatrix mdcm, TransformType type, int d, int[] subVector) {
        int[] dimensionSize = mdcm.getDimensionSizes();
        int i;
        if (subVector.length == dimensionSize.length) {
            Complex[] temp = new Complex[dimensionSize[d]];
            for (i = 0; i < dimensionSize[d]; i++) {
                subVector[d] = i;
                temp[i] = mdcm.get(subVector);
            }
            temp = transform(temp, type);
            for (i = 0; i < dimensionSize[d]; i++) {
                subVector[d] = i;
                mdcm.set(temp[i], subVector);
            }
            return;
        }
        int[] vector = new int[(subVector.length + 1)];
        System.arraycopy(subVector, 0, vector, 0, subVector.length);
        if (subVector.length == d) {
            vector[d] = 0;
            mdfft(mdcm, type, d, vector);
            return;
        }
        for (i = 0; i < dimensionSize[subVector.length]; i++) {
            vector[subVector.length] = i;
            mdfft(mdcm, type, d, vector);
        }
    }
}
