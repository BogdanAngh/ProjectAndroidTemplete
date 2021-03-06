package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.ExceptionContext;
import org.apache.commons.math4.exception.util.ExceptionContextProvider;
import org.apache.commons.math4.exception.util.Localizable;

public class MathRuntimeException extends RuntimeException implements ExceptionContextProvider {
    private static final long serialVersionUID = 20120926;
    private final ExceptionContext context;

    public MathRuntimeException(Localizable pattern, Object... args) {
        this.context = new ExceptionContext(this);
        this.context.addMessage(pattern, args);
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
