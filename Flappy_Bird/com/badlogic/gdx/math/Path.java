package com.badlogic.gdx.math;

public interface Path<T> {
    float approximate(T t);

    float locate(T t);

    T valueAt(T t, float f);
}
