package com.github.mikephil.charting.components;

import com.github.mikephil.charting.utils.Utils;

public class XAxis extends AxisBase {
    private boolean mAvoidFirstLastClipping;
    public int mLabelHeight;
    public int mLabelRotatedHeight;
    public int mLabelRotatedWidth;
    protected float mLabelRotationAngle;
    public int mLabelWidth;
    private XAxisPosition mPosition;

    public enum XAxisPosition {
        TOP,
        BOTTOM,
        BOTH_SIDED,
        TOP_INSIDE,
        BOTTOM_INSIDE
    }

    public XAxis() {
        this.mLabelWidth = 1;
        this.mLabelHeight = 1;
        this.mLabelRotatedWidth = 1;
        this.mLabelRotatedHeight = 1;
        this.mLabelRotationAngle = 0.0f;
        this.mAvoidFirstLastClipping = false;
        this.mPosition = XAxisPosition.TOP;
        this.mYOffset = Utils.convertDpToPixel(4.0f);
    }

    public XAxisPosition getPosition() {
        return this.mPosition;
    }

    public void setPosition(XAxisPosition pos) {
        this.mPosition = pos;
    }

    public float getLabelRotationAngle() {
        return this.mLabelRotationAngle;
    }

    public void setLabelRotationAngle(float angle) {
        this.mLabelRotationAngle = angle;
    }

    public void setAvoidFirstLastClipping(boolean enabled) {
        this.mAvoidFirstLastClipping = enabled;
    }

    public boolean isAvoidFirstLastClippingEnabled() {
        return this.mAvoidFirstLastClipping;
    }
}
