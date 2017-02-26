package org.apache.commons.math4.stat.regression;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.Localizable;

public class ModelSpecificationException extends MathIllegalArgumentException {
    private static final long serialVersionUID = 4206514456095401070L;

    public ModelSpecificationException(Localizable pattern, Object... args) {
        super(pattern, args);
    }
}
