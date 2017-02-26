package org.apache.commons.math4.random;

import android.support.v4.media.TransportMediator;
import com.getkeepsafe.taptargetview.R;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.util.MathUtils;

public class HaltonSequenceGenerator implements RandomVectorGenerator {
    private static final int[] PRIMES;
    private static final int[] WEIGHTS;
    private final int[] base;
    private int count;
    private final int dimension;
    private final int[] weight;

    static {
        PRIMES = new int[]{2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, R.styleable.AppCompatTheme_autoCompleteTextViewStyle, R.styleable.AppCompatTheme_buttonStyleSmall, R.styleable.AppCompatTheme_radioButtonStyle, R.styleable.AppCompatTheme_ratingBarStyleIndicator, R.styleable.AppCompatTheme_switchStyle, TransportMediator.KEYCODE_MEDIA_PAUSE, 131, 137, 139, 149, 151, 157, 163, 167, 173};
        WEIGHTS = new int[]{1, 2, 3, 3, 8, 11, 12, 14, 7, 18, 12, 13, 17, 18, 29, 14, 18, 43, 41, 44, 40, 30, 47, 65, 71, 28, 40, 60, 79, 89, 56, 50, 52, 61, R.styleable.AppCompatTheme_ratingBarStyle, 56, 66, 63, 60, 66};
    }

    public HaltonSequenceGenerator(int dimension) throws OutOfRangeException {
        this(dimension, PRIMES, WEIGHTS);
    }

    public HaltonSequenceGenerator(int dimension, int[] bases, int[] weights) throws NullArgumentException, OutOfRangeException, DimensionMismatchException {
        this.count = 0;
        MathUtils.checkNotNull(bases);
        if (dimension < 1 || dimension > bases.length) {
            throw new OutOfRangeException(Integer.valueOf(dimension), Integer.valueOf(1), Integer.valueOf(PRIMES.length));
        } else if (weights == null || weights.length == bases.length) {
            this.dimension = dimension;
            this.base = (int[]) bases.clone();
            this.weight = weights == null ? null : (int[]) weights.clone();
            this.count = 0;
        } else {
            throw new DimensionMismatchException(weights.length, bases.length);
        }
    }

    public double[] nextVector() {
        double[] v = new double[this.dimension];
        for (int i = 0; i < this.dimension; i++) {
            int index = this.count;
            double f = 1.0d / ((double) this.base[i]);
            while (index > 0) {
                v[i] = v[i] + (((double) scramble(i, 0, this.base[i], index % this.base[i])) * f);
                index /= this.base[i];
                f /= (double) this.base[i];
            }
        }
        this.count++;
        return v;
    }

    protected int scramble(int i, int j, int b, int digit) {
        return this.weight != null ? (this.weight[i] * digit) % b : digit;
    }

    public double[] skipTo(int index) throws NotPositiveException {
        this.count = index;
        return nextVector();
    }

    public int getNextIndex() {
        return this.count;
    }
}
