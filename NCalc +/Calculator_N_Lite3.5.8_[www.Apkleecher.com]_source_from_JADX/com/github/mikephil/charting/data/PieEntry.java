package com.github.mikephil.charting.data;

import android.annotation.SuppressLint;

@SuppressLint({"ParcelCreator"})
public class PieEntry extends Entry {
    private String label;

    public PieEntry(float value) {
        super(0.0f, value);
    }

    public PieEntry(float value, Object data) {
        super(0.0f, value, data);
    }

    public PieEntry(float value, String label) {
        super(0.0f, value);
        this.label = label;
    }

    public PieEntry(float value, String label, Object data) {
        super(0.0f, value, data);
        this.label = label;
    }

    public float getValue() {
        return getY();
    }

    public String getLabel() {
        return this.label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Deprecated
    public void setX(float x) {
        super.setX(x);
    }

    @Deprecated
    public float getX() {
        return super.getX();
    }

    public PieEntry copy() {
        return new PieEntry(getY(), this.label, getData());
    }
}
