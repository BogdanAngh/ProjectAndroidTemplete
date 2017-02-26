package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class InsufficientDataException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -2629324471511903359L;

    public InsufficientDataException() {
        this(LocalizedFormats.INSUFFICIENT_DATA, new Object[0]);
    }

    public InsufficientDataException(Localizable pattern, Object... arguments) {
        super(pattern, arguments);
    }
}
