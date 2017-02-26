package com.frodidev.TweenAccessors;

import com.google.android.gms.cast.TextTrackStyle;

public class Value {
    private float val;

    public Value() {
        this.val = TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    public float getValue() {
        return this.val;
    }

    public void setValue(float newVal) {
        this.val = newVal;
    }
}
