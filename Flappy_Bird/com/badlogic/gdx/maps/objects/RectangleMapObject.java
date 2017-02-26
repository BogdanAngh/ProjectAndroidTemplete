package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Rectangle;
import com.google.android.gms.cast.TextTrackStyle;

public class RectangleMapObject extends MapObject {
    private Rectangle rectangle;

    public Rectangle getRectangle() {
        return this.rectangle;
    }

    public RectangleMapObject() {
        this(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public RectangleMapObject(float x, float y, float width, float height) {
        this.rectangle = new Rectangle(x, y, width, height);
    }
}
