package org.matheclipse.commons.math.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.matheclipse.core.interfaces.IExpr;

public interface FieldVector {
    FieldVector add(FieldVector fieldVector) throws DimensionMismatchException;

    FieldVector append(FieldVector fieldVector);

    FieldVector append(IExpr iExpr);

    FieldVector copy();

    IExpr dotProduct(FieldVector fieldVector) throws DimensionMismatchException;

    FieldVector ebeDivide(FieldVector fieldVector) throws DimensionMismatchException, MathArithmeticException;

    FieldVector ebeMultiply(FieldVector fieldVector) throws DimensionMismatchException;

    @Deprecated
    IExpr[] getData();

    int getDimension();

    IExpr getEntry(int i) throws OutOfRangeException;

    FieldVector getSubVector(int i, int i2) throws OutOfRangeException, NotPositiveException;

    FieldVector mapAdd(IExpr iExpr) throws NullArgumentException;

    FieldVector mapAddToSelf(IExpr iExpr) throws NullArgumentException;

    FieldVector mapDivide(IExpr iExpr) throws NullArgumentException, MathArithmeticException;

    FieldVector mapDivideToSelf(IExpr iExpr) throws NullArgumentException, MathArithmeticException;

    FieldVector mapInv() throws MathArithmeticException;

    FieldVector mapInvToSelf() throws MathArithmeticException;

    FieldVector mapMultiply(IExpr iExpr) throws NullArgumentException;

    FieldVector mapMultiplyToSelf(IExpr iExpr) throws NullArgumentException;

    FieldVector mapSubtract(IExpr iExpr) throws NullArgumentException;

    FieldVector mapSubtractToSelf(IExpr iExpr) throws NullArgumentException;

    FieldMatrix outerProduct(FieldVector fieldVector);

    FieldVector projection(FieldVector fieldVector) throws DimensionMismatchException, MathArithmeticException;

    void set(IExpr iExpr);

    void setEntry(int i, IExpr iExpr) throws OutOfRangeException;

    void setSubVector(int i, FieldVector fieldVector) throws OutOfRangeException;

    FieldVector subtract(FieldVector fieldVector) throws DimensionMismatchException;

    IExpr[] toArray();
}
