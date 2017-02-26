package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Ellipse;
import com.google.android.gms.cast.TextTrackStyle;

public class EllipseMapObject extends MapObject {
    private Ellipse ellipse;

    public Ellipse getEllipse() {
        return this.ellipse;
    }

    public EllipseMapObject() {
        this(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public EllipseMapObject(float x, float y, float width, float height) {
        this.ellipse = new Ellipse(x, y, width, height);
    }
}
