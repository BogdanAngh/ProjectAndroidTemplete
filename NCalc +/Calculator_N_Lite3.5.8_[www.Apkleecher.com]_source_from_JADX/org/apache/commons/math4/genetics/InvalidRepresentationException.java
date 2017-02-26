package org.apache.commons.math4.genetics;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.Localizable;

public class InvalidRepresentationException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 1;

    public InvalidRepresentationException(Localizable pattern, Object... args) {
        super(pattern, args);
    }
}
