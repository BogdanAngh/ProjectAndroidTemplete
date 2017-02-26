package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;

public class MathIllegalNumberException extends MathIllegalArgumentException {
    protected static final Integer INTEGER_ZERO;
    private static final long serialVersionUID = -7447085893598031110L;
    private final Number argument;

    static {
        INTEGER_ZERO = Integer.valueOf(0);
    }

    protected MathIllegalNumberException(Localizable pattern, Number wrong, Object... arguments) {
        super(pattern, wrong, arguments);
        this.argument = wrong;
    }

    public Number getArgument() {
        return this.argument;
    }
}
