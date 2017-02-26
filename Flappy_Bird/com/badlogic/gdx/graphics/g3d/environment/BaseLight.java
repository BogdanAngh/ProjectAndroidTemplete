package com.badlogic.gdx.graphics.g3d.environment;

import com.badlogic.gdx.graphics.Color;
import com.google.android.gms.cast.TextTrackStyle;

public abstract class BaseLight {
    public final Color color;

    public BaseLight() {
        this.color = new Color(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
    }
}
