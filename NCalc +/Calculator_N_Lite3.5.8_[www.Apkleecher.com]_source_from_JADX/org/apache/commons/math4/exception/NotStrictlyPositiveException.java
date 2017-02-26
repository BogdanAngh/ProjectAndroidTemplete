package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;

public class NotStrictlyPositiveException extends NumberIsTooSmallException {
    private static final long serialVersionUID = -7824848630829852237L;

    public NotStrictlyPositiveException(Number value) {
        super(value, INTEGER_ZERO, false);
    }

    public NotStrictlyPositiveException(Localizable specific, Number value) {
        super(specific, value, INTEGER_ZERO, false);
    }
}
