package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class NumberIsTooLargeException extends MathIllegalNumberException {
    private static final long serialVersionUID = 4330003017885151975L;
    private final boolean boundIsAllowed;
    private final Number max;

    public NumberIsTooLargeException(Number wrong, Number max, boolean boundIsAllowed) {
        Localizable localizable;
        if (boundIsAllowed) {
            localizable = LocalizedFormats.NUMBER_TOO_LARGE;
        } else {
            localizable = LocalizedFormats.NUMBER_TOO_LARGE_BOUND_EXCLUDED;
        }
        this(localizable, wrong, max, boundIsAllowed);
    }

    public NumberIsTooLargeException(Localizable specific, Number wrong, Number max, boolean boundIsAllowed) {
        super(specific, wrong, max);
        this.max = max;
        this.boundIsAllowed = boundIsAllowed;
    }

    public boolean getBoundIsAllowed() {
        return this.boundIsAllowed;
    }

    public Number getMax() {
        return this.max;
    }
}
