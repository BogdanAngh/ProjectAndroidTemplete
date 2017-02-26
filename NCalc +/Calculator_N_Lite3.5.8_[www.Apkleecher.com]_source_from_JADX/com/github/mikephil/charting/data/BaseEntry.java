package com.github.mikephil.charting.data;

public abstract class BaseEntry {
    private Object mData;
    private float y;

    public BaseEntry() {
        this.y = 0.0f;
        this.mData = null;
    }

    public BaseEntry(float y) {
        this.y = 0.0f;
        this.mData = null;
        this.y = y;
    }

    public BaseEntry(float y, Object data) {
        this(y);
        this.mData = data;
    }

    public float getY() {
        return this.y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Object getData() {
        return this.mData;
    }

    public void setData(Object data) {
        this.mData = data;
    }
}
