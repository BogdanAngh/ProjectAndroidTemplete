package org.apache.commons.math4.fraction;

import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class FractionConversionException extends ConvergenceException {
    private static final long serialVersionUID = -4661812640132576263L;

    public FractionConversionException(double value, int maxIterations) {
        super(LocalizedFormats.FAILED_FRACTION_CONVERSION, Double.valueOf(value), Integer.valueOf(maxIterations));
    }

    public FractionConversionException(double value, long p, long q) {
        super(LocalizedFormats.FRACTION_CONVERSION_OVERFLOW, Double.valueOf(value), Long.valueOf(p), Long.valueOf(q));
    }
}
