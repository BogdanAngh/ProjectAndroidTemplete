package org.apache.commons.math4.exception;

import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathArrays.OrderDirection;

public class NonMonotonicSequenceException extends MathIllegalNumberException {
    private static final long serialVersionUID = 3596849179428944575L;
    private final OrderDirection direction;
    private final int index;
    private final Number previous;
    private final boolean strict;

    public NonMonotonicSequenceException(Number wrong, Number previous, int index) {
        this(wrong, previous, index, OrderDirection.INCREASING, true);
    }

    public NonMonotonicSequenceException(Number wrong, Number previous, int index, OrderDirection direction, boolean strict) {
        Localizable localizable;
        if (direction == OrderDirection.INCREASING) {
            if (strict) {
                localizable = LocalizedFormats.NOT_STRICTLY_INCREASING_SEQUENCE;
            } else {
                localizable = LocalizedFormats.NOT_INCREASING_SEQUENCE;
            }
        } else if (strict) {
            localizable = LocalizedFormats.NOT_STRICTLY_DECREASING_SEQUENCE;
        } else {
            localizable = LocalizedFormats.NOT_DECREASING_SEQUENCE;
        }
        super(localizable, wrong, previous, Integer.valueOf(index), Integer.valueOf(index - 1));
        this.direction = direction;
        this.strict = strict;
        this.index = index;
        this.previous = previous;
    }

    public OrderDirection getDirection() {
        return this.direction;
    }

    public boolean getStrict() {
        return this.strict;
    }

    public int getIndex() {
        return this.index;
    }

    public Number getPrevious() {
        return this.previous;
    }
}
