package org.apache.commons.math4.analysis.interpolation;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class FieldHermiteInterpolator<T extends FieldElement<T>> {
    private final List<T> abscissae;
    private final List<T[]> bottomDiagonal;
    private final List<T[]> topDiagonal;

    public FieldHermiteInterpolator() {
        this.abscissae = new ArrayList();
        this.topDiagonal = new ArrayList();
        this.bottomDiagonal = new ArrayList();
    }

    public void addSamplePoint(T x, T[]... value) throws ZeroException, MathArithmeticException, DimensionMismatchException, NullArgumentException {
        MathUtils.checkNotNull(x);
        FieldElement factorial = (FieldElement) x.getField().getOne();
        for (int i = 0; i < value.length; i++) {
            int j;
            FieldElement[] y = (FieldElement[]) value[i].clone();
            if (i > 1) {
                factorial = (FieldElement) factorial.multiply(i);
                Object inv = (FieldElement) factorial.reciprocal();
                for (j = 0; j < y.length; j++) {
                    y[j] = (FieldElement) y[j].multiply(inv);
                }
            }
            int n = this.abscissae.size();
            this.bottomDiagonal.add(n - i, y);
            FieldElement[] bottom0 = y;
            for (j = i; j < n; j++) {
                FieldElement[] bottom1 = (FieldElement[]) this.bottomDiagonal.get(n - (j + 1));
                if (x.equals(this.abscissae.get(n - (j + 1)))) {
                    throw new ZeroException(LocalizedFormats.DUPLICATED_ABSCISSA_DIVISION_BY_ZERO, x);
                }
                FieldElement inv2 = (FieldElement) ((FieldElement) x.subtract((FieldElement) this.abscissae.get(n - (j + 1)))).reciprocal();
                for (int k = 0; k < y.length; k++) {
                    bottom1[k] = (FieldElement) inv2.multiply((FieldElement) bottom0[k].subtract(bottom1[k]));
                }
                bottom0 = bottom1;
            }
            this.topDiagonal.add((FieldElement[]) bottom0.clone());
            this.abscissae.add(x);
        }
    }

    public T[] value(T x) throws NoDataException, NullArgumentException {
        MathUtils.checkNotNull(x);
        if (this.abscissae.isEmpty()) {
            throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
        }
        FieldElement[] value = (FieldElement[]) MathArrays.buildArray(x.getField(), ((FieldElement[]) this.topDiagonal.get(0)).length);
        Object valueCoeff = (FieldElement) x.getField().getOne();
        for (int i = 0; i < this.topDiagonal.size(); i++) {
            FieldElement[] dividedDifference = (FieldElement[]) this.topDiagonal.get(i);
            for (int k = 0; k < value.length; k++) {
                value[k] = (FieldElement) value[k].add((FieldElement) dividedDifference[k].multiply(valueCoeff));
            }
            FieldElement valueCoeff2 = (FieldElement) valueCoeff.multiply((FieldElement) x.subtract((FieldElement) this.abscissae.get(i)));
        }
        return value;
    }

    public T[][] derivatives(T x, int order) throws NoDataException, NullArgumentException {
        MathUtils.checkNotNull(x);
        if (this.abscissae.isEmpty()) {
            throw new NoDataException(LocalizedFormats.EMPTY_INTERPOLATION_SAMPLE);
        }
        int i;
        FieldElement one = (FieldElement) x.getField().getOne();
        FieldElement[] tj = (FieldElement[]) MathArrays.buildArray(x.getField(), order + 1);
        tj[0] = (FieldElement) x.getField().getZero();
        for (i = 0; i < order; i++) {
            tj[i + 1] = (FieldElement) tj[i].add(one);
        }
        FieldElement[][] derivatives = (FieldElement[][]) MathArrays.buildArray(x.getField(), order + 1, ((FieldElement[]) this.topDiagonal.get(0)).length);
        FieldElement[] valueCoeff = (FieldElement[]) MathArrays.buildArray(x.getField(), order + 1);
        valueCoeff[0] = (FieldElement) x.getField().getOne();
        for (i = 0; i < this.topDiagonal.size(); i++) {
            FieldElement[] dividedDifference = (FieldElement[]) this.topDiagonal.get(i);
            Object deltaX = (FieldElement) x.subtract((FieldElement) this.abscissae.get(i));
            for (int j = order; j >= 0; j--) {
                for (int k = 0; k < derivatives[j].length; k++) {
                    derivatives[j][k] = (FieldElement) derivatives[j][k].add((FieldElement) dividedDifference[k].multiply(valueCoeff[j]));
                }
                valueCoeff[j] = (FieldElement) valueCoeff[j].multiply(deltaX);
                if (j > 0) {
                    valueCoeff[j] = (FieldElement) valueCoeff[j].add((FieldElement) tj[j].multiply(valueCoeff[j - 1]));
                }
            }
        }
        return derivatives;
    }
}
