package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;
import java.io.Serializable;

public class Ellipse implements Serializable {
    private static final long serialVersionUID = 7381533206532032099L;
    public float height;
    public float width;
    public float f57x;
    public float f58y;

    public Ellipse(Ellipse ellipse) {
        this.f57x = ellipse.f57x;
        this.f58y = ellipse.f58y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public Ellipse(float x, float y, float width, float height) {
        this.f57x = x;
        this.f58y = y;
        this.width = width;
        this.height = height;
    }

    public Ellipse(Vector2 position, float width, float height) {
        this.f57x = position.f100x;
        this.f58y = position.f101y;
        this.width = width;
        this.height = height;
    }

    public boolean contains(float x, float y) {
        x -= this.f57x;
        y -= this.f58y;
        return ((x * x) / (((this.width * 0.5f) * this.width) * 0.5f)) + ((y * y) / (((this.height * 0.5f) * this.height) * 0.5f)) <= TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    public boolean contains(Vector2 point) {
        return contains(point.f100x, point.f101y);
    }

    public void set(float x, float y, float width, float height) {
        this.f57x = x;
        this.f58y = y;
        this.width = width;
        this.height = height;
    }

    public void set(Ellipse ellipse) {
        this.f57x = ellipse.f57x;
        this.f58y = ellipse.f58y;
        this.width = ellipse.width;
        this.height = ellipse.height;
    }

    public Ellipse setPosition(Vector2 position) {
        this.f57x = position.f100x;
        this.f58y = position.f101y;
        return this;
    }

    public Ellipse setPosition(float x, float y) {
        this.f57x = x;
        this.f58y = y;
        return this;
    }

    public Ellipse setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }
}
