package org.apache.commons.math4.ode;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class UnknownParameterException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 20120902;
    private final String name;

    public UnknownParameterException(String name) {
        super(LocalizedFormats.UNKNOWN_PARAMETER, new Object[0]);
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
