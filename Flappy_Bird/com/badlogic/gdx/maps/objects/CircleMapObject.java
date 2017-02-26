package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.math.Circle;
import com.google.android.gms.cast.TextTrackStyle;

public class CircleMapObject extends MapObject {
    private Circle circle;

    public Circle getCircle() {
        return this.circle;
    }

    public CircleMapObject() {
        this(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public CircleMapObject(float x, float y, float radius) {
        this.circle = new Circle(x, y, radius);
    }
}
