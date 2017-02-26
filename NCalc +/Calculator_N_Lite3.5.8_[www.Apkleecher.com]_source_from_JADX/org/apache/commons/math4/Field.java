package org.apache.commons.math4;

public interface Field<T> {
    T getOne();

    Class<? extends FieldElement<T>> getRuntimeClass();

    T getZero();
}
