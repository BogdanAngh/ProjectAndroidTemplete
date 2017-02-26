package org.apache.commons.math4;

import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NullArgumentException;

public interface FieldElement<T> {
    T add(T t) throws NullArgumentException;

    T divide(T t) throws NullArgumentException, MathArithmeticException;

    Field<T> getField();

    T multiply(int i);

    T multiply(T t) throws NullArgumentException;

    T negate();

    T reciprocal() throws MathArithmeticException;

    T subtract(T t) throws NullArgumentException;
}
