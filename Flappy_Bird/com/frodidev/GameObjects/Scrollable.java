package com.frodidev.GameObjects;

import com.badlogic.gdx.math.Vector2;

public class Scrollable {
    protected int height;
    protected boolean isScrolledLeft;
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(scrollSpeed, 0.0f);
        this.width = width;
        this.height = height;
        this.isScrolledLeft = false;
    }

    public void update(float delta) {
        this.position.add(this.velocity.cpy().scl(delta));
        if (this.position.f100x + ((float) this.width) < 0.0f) {
            this.isScrolledLeft = true;
        }
    }

    public void reset(float newX) {
        this.position.f100x = newX;
        this.isScrolledLeft = false;
    }

    public void stop() {
        this.velocity.f100x = 0.0f;
    }

    public boolean isScrolledLeft() {
        return this.isScrolledLeft;
    }

    public float getTailX() {
        return this.position.f100x + ((float) this.width);
    }

    public float getX() {
        return this.position.f100x;
    }

    public float getY() {
        return this.position.f101y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }
}
