package com.badlogic.gdx.maps.objects;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapObject;
import com.google.android.gms.cast.TextTrackStyle;

public class TextureMapObject extends MapObject {
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private TextureRegion textureRegion;
    private float f96x;
    private float f97y;

    public float getX() {
        return this.f96x;
    }

    public void setX(float x) {
        this.f96x = x;
    }

    public float getY() {
        return this.f97y;
    }

    public void setY(float y) {
        this.f97y = y;
    }

    public float getOriginX() {
        return this.originX;
    }

    public void setOriginX(float x) {
        this.originX = x;
    }

    public float getOriginY() {
        return this.originY;
    }

    public void setOriginY(float y) {
        this.originY = y;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public void setScaleX(float x) {
        this.scaleX = x;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public void setScaleY(float y) {
        this.scaleY = y;
    }

    public float getRotation() {
        return this.rotation;
    }

    public void setRotation(float rotation) {
        this.rotation = rotation;
    }

    public TextureRegion getTextureRegion() {
        return this.textureRegion;
    }

    public void setTextureRegion(TextureRegion region) {
        this.textureRegion = region;
    }

    public TextureMapObject() {
        this(null);
    }

    public TextureMapObject(TextureRegion textureRegion) {
        this.f96x = 0.0f;
        this.f97y = 0.0f;
        this.originX = 0.0f;
        this.originY = 0.0f;
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.rotation = 0.0f;
        this.textureRegion = null;
        this.textureRegion = textureRegion;
    }
}
