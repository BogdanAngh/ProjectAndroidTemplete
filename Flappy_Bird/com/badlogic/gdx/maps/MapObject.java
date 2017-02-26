package com.badlogic.gdx.maps;

import com.badlogic.gdx.graphics.Color;
import com.google.android.gms.cast.TextTrackStyle;

public class MapObject {
    private Color color;
    private String name;
    private float opacity;
    private MapProperties properties;
    private boolean visible;

    public MapObject() {
        this.name = "";
        this.opacity = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.visible = true;
        this.properties = new MapProperties();
        this.color = Color.WHITE.cpy();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return this.color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public boolean isVisible() {
        return this.visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public MapProperties getProperties() {
        return this.properties;
    }
}
