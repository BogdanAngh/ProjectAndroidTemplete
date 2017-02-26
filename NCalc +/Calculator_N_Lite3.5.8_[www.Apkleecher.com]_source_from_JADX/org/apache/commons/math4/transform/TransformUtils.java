package org.apache.commons.math4.transform;

import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.common.primitives.Ints;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.complex.Complex;
import org.apache.commons.math4.dfp.Dfp;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.matheclipse.core.interfaces.IExpr;

public class TransformUtils {
    private static final int[] POWERS_OF_TWO;

    static {
        POWERS_OF_TWO = new int[]{1, 2, 4, 8, 16, 32, 64, IExpr.SYMBOLID, IExpr.BLANKID, IExpr.PATTERNID, IExpr.ASTID, IExpr.METHODSYMBOLID, StaticSettings.MAX_FILE_BUFFER_SIZE, AccessibilityNodeInfoCompat.ACTION_SCROLL_BACKWARD, AccessibilityNodeInfoCompat.ACTION_COPY, Dfp.MAX_EXP, AccessibilityNodeInfoCompat.ACTION_CUT, AccessibilityNodeInfoCompat.ACTION_SET_SELECTION, AccessibilityNodeInfoCompat.ACTION_EXPAND, AccessibilityNodeInfoCompat.ACTION_COLLAPSE, AccessibilityNodeInfoCompat.ACTION_DISMISS, AccessibilityNodeInfoCompat.ACTION_SET_TEXT, AccessibilityEventCompat.TYPE_WINDOWS_CHANGED, AccessibilityEventCompat.TYPE_VIEW_CONTEXT_CLICKED, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, 33554432, 67108864, 134217728, 268435456, 536870912, Ints.MAX_POWER_OF_TWO};
    }

    private TransformUtils() {
    }

    public static double[] scaleArray(double[] f, double d) {
        for (int i = 0; i < f.length; i++) {
            f[i] = f[i] * d;
        }
        return f;
    }

    public static Complex[] scaleArray(Complex[] f, double d) {
        for (int i = 0; i < f.length; i++) {
            f[i] = new Complex(f[i].getReal() * d, f[i].getImaginary() * d);
        }
        return f;
    }

    public static double[][] createRealImaginaryArray(Complex[] dataC) {
        double[][] dataRI = (double[][]) Array.newInstance(Double.TYPE, new int[]{2, dataC.length});
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        for (int i = 0; i < dataC.length; i++) {
            Complex c = dataC[i];
            dataR[i] = c.getReal();
            dataI[i] = c.getImaginary();
        }
        return dataRI;
    }

    public static Complex[] createComplexArray(double[][] dataRI) throws DimensionMismatchException {
        if (dataRI.length != 2) {
            throw new DimensionMismatchException(dataRI.length, 2);
        }
        double[] dataR = dataRI[0];
        double[] dataI = dataRI[1];
        if (dataR.length != dataI.length) {
            throw new DimensionMismatchException(dataI.length, dataR.length);
        }
        int n = dataR.length;
        Complex[] c = new Complex[n];
        for (int i = 0; i < n; i++) {
            c[i] = new Complex(dataR[i], dataI[i]);
        }
        return c;
    }

    public static int exactLog2(int n) throws MathIllegalArgumentException {
        int index = Arrays.binarySearch(POWERS_OF_TWO, n);
        if (index >= 0) {
            return index;
        }
        throw new MathIllegalArgumentException(LocalizedFormats.NOT_POWER_OF_TWO_CONSIDER_PADDING, Integer.valueOf(n));
    }
}
