package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.ExceptionContext;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class NonPositiveDefiniteMatrixException extends NumberIsTooSmallException {
    private static final long serialVersionUID = 1641613838113738061L;
    private final int index;
    private final double threshold;

    public NonPositiveDefiniteMatrixException(double wrong, int index, double threshold) {
        super(Double.valueOf(wrong), Double.valueOf(threshold), false);
        this.index = index;
        this.threshold = threshold;
        ExceptionContext context = getContext();
        context.addMessage(LocalizedFormats.NOT_POSITIVE_DEFINITE_MATRIX, new Object[0]);
        context.addMessage(LocalizedFormats.ARRAY_ELEMENT, Double.valueOf(wrong), Integer.valueOf(index));
    }

    public int getRow() {
        return this.index;
    }

    public int getColumn() {
        return this.index;
    }

    public double getThreshold() {
        return this.threshold;
    }
}
