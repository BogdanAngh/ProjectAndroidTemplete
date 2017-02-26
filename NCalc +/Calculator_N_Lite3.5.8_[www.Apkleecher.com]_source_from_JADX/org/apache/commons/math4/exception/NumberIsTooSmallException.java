package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class NumberIsTooSmallException extends MathIllegalNumberException {
    private static final long serialVersionUID = -6100997100383932834L;
    private final boolean boundIsAllowed;
    private final Number min;

    public NumberIsTooSmallException(Number wrong, Number min, boolean boundIsAllowed) {
        Localizable localizable;
        if (boundIsAllowed) {
            localizable = LocalizedFormats.NUMBER_TOO_SMALL;
        } else {
            localizable = LocalizedFormats.NUMBER_TOO_SMALL_BOUND_EXCLUDED;
        }
        this(localizable, wrong, min, boundIsAllowed);
    }

    public NumberIsTooSmallException(Localizable specific, Number wrong, Number min, boolean boundIsAllowed) {
        super(specific, wrong, min);
        this.min = min;
        this.boundIsAllowed = boundIsAllowed;
    }

    public boolean getBoundIsAllowed() {
        return this.boundIsAllowed;
    }

    public Number getMin() {
        return this.min;
    }
}
