package org.apache.commons.math4.util;

import java.io.Serializable;
import org.apache.commons.math4.exception.MathIllegalArgumentException;

public class CentralPivotingStrategy implements PivotingStrategyInterface, Serializable {
    private static final long serialVersionUID = 20140713;

    public int pivotIndex(double[] work, int begin, int end) throws MathIllegalArgumentException {
        MathArrays.verifyValues(work, begin, end - begin);
        return ((end - begin) / 2) + begin;
    }
}
