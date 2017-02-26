package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.ExceptionContext;
import org.apache.commons.math4.exception.util.ExceptionContextProvider;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class NullArgumentException extends NullPointerException implements ExceptionContextProvider {
    private static final long serialVersionUID = 20150225;
    private final ExceptionContext context;

    public NullArgumentException() {
        this(LocalizedFormats.NULL_NOT_ALLOWED, new Object[0]);
    }

    public NullArgumentException(Localizable pattern, Object... arguments) {
        this.context = new ExceptionContext(this);
        this.context.addMessage(pattern, arguments);
    }

    public ExceptionContext getContext() {
        return this.context;
    }

    public String getMessage() {
        return this.context.getMessage();
    }

    public String getLocalizedMessage() {
        return this.context.getLocalizedMessage();
    }
}
