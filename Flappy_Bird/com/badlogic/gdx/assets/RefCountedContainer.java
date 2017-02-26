package com.badlogic.gdx.assets;

public class RefCountedContainer {
    Object object;
    int refCount;

    public RefCountedContainer(Object object) {
        this.refCount = 1;
        if (object == null) {
            throw new IllegalArgumentException("Object must not be null");
        }
        this.object = object;
    }

    public void incRefCount() {
        this.refCount++;
    }

    public void decRefCount() {
        this.refCount--;
    }

    public int getRefCount() {
        return this.refCount;
    }

    public void setRefCount(int refCount) {
        this.refCount = refCount;
    }

    public <T> T getObject(Class<T> cls) {
        return this.object;
    }

    public void setObject(Object asset) {
        this.object = asset;
    }
}
