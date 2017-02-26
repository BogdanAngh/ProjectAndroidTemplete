package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;

public class IllConditionedOperatorException extends MathIllegalArgumentException {
    private static final long serialVersionUID = -7883263944530490135L;

    public IllConditionedOperatorException(double cond) {
        super(LocalizedFormats.ILL_CONDITIONED_OPERATOR, Double.valueOf(cond));
    }
}
