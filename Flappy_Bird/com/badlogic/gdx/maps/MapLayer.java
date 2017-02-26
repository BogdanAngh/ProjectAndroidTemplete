package com.badlogic.gdx.maps;

import com.google.android.gms.cast.TextTrackStyle;

public class MapLayer {
    private String name;
    private MapObjects objects;
    private float opacity;
    private MapProperties properties;
    private boolean visible;

    public MapLayer() {
        this.name = "";
        this.opacity = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.visible = true;
        this.objects = new MapObjects();
        this.properties = new MapProperties();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getOpacity() {
        return this.opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public MapObjects getObjects() {
        return this.objects;
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
