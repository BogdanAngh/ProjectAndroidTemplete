package com.badlogic.gdx.utils;

import java.lang.reflect.Constructor;

public class ReflectionPool<T> extends Pool<T> {
    private final Class<T> type;

    public ReflectionPool(Class<T> type) {
        this.type = type;
    }

    public ReflectionPool(Class<T> type, int initialCapacity, int max) {
        super(initialCapacity, max);
        this.type = type;
    }

    public ReflectionPool(Class<T> type, int initialCapacity) {
        super(initialCapacity);
        this.type = type;
    }

    protected T newObject() {
        T newInstance;
        Constructor ctor;
        try {
            newInstance = this.type.newInstance();
        } catch (Exception ex) {
            try {
                ctor = this.type.getConstructor((Class[]) null);
            } catch (Exception e) {
                try {
                    ctor = this.type.getDeclaredConstructor((Class[]) null);
                    ctor.setAccessible(true);
                } catch (NoSuchMethodException e2) {
                    throw new RuntimeException("Class cannot be created (missing no-arg constructor): " + this.type.getName());
                }
            }
            try {
                newInstance = ctor.newInstance(new Object[0]);
            } catch (Exception e3) {
                throw new GdxRuntimeException("Unable to create new instance: " + this.type.getName(), ex);
            }
        }
        return newInstance;
    }
}
