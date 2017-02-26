package com.badlogic.gdx.graphics.g3d.model;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.google.android.gms.cast.TextTrackStyle;

public class NodeKeyframe {
    public float keytime;
    public final Quaternion rotation;
    public final Vector3 scale;
    public final Vector3 translation;

    public NodeKeyframe() {
        this.translation = new Vector3();
        this.scale = new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.rotation = new Quaternion();
    }
}
