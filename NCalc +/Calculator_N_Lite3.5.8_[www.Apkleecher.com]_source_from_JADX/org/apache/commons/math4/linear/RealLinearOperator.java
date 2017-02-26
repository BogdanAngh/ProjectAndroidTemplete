package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;

public abstract class RealLinearOperator {
    public abstract int getColumnDimension();

    public abstract int getRowDimension();

    public abstract RealVector operate(RealVector realVector) throws DimensionMismatchException;

    public RealVector operateTranspose(RealVector x) throws DimensionMismatchException, UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    public boolean isTransposable() {
        return false;
    }
}
