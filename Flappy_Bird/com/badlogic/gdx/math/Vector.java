package com.badlogic.gdx.math;

public interface Vector<T extends Vector<T>> {
    T add(T t);

    T clamp(float f, float f2);

    T cpy();

    float dot(T t);

    float dst(T t);

    float dst2(T t);

    float len();

    float len2();

    T lerp(T t, float f);

    T limit(float f);

    T nor();

    T scl(float f);

    T scl(T t);

    T set(T t);

    T sub(T t);
}
