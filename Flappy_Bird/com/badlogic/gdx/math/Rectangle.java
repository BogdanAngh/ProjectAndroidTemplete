package com.badlogic.gdx.math;

import java.io.Serializable;

public class Rectangle implements Serializable {
    private static final long serialVersionUID = 5733252015138115702L;
    public static final Rectangle tmp;
    public static final Rectangle tmp2;
    public float height;
    public float width;
    public float f75x;
    public float f76y;

    static {
        tmp = new Rectangle();
        tmp2 = new Rectangle();
    }

    public Rectangle(float x, float y, float width, float height) {
        this.f75x = x;
        this.f76y = y;
        this.width = width;
        this.height = height;
    }

    public Rectangle(Rectangle rect) {
        this.f75x = rect.f75x;
        this.f76y = rect.f76y;
        this.width = rect.width;
        this.height = rect.height;
    }

    public Rectangle set(float x, float y, float width, float height) {
        this.f75x = x;
        this.f76y = y;
        this.width = width;
        this.height = height;
        return this;
    }

    public float getX() {
        return this.f75x;
    }

    public Rectangle setX(float x) {
        this.f75x = x;
        return this;
    }

    public float getY() {
        return this.f76y;
    }

    public Rectangle setY(float y) {
        this.f76y = y;
        return this;
    }

    public float getWidth() {
        return this.width;
    }

    public Rectangle setWidth(float width) {
        this.width = width;
        return this;
    }

    public float getHeight() {
        return this.height;
    }

    public Rectangle setHeight(float height) {
        this.height = height;
        return this;
    }

    public Vector2 getPosition(Vector2 position) {
        return position.set(this.f75x, this.f76y);
    }

    public Rectangle setPosition(Vector2 position) {
        this.f75x = position.f100x;
        this.f76y = position.f101y;
        return this;
    }

    public Rectangle setPosition(float x, float y) {
        this.f75x = x;
        this.f76y = y;
        return this;
    }

    public Rectangle setSize(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    public Rectangle setSize(float sizeXY) {
        this.width = sizeXY;
        this.height = sizeXY;
        return this;
    }

    public Vector2 getSize(Vector2 size) {
        return size.set(this.width, this.height);
    }

    public boolean contains(float x, float y) {
        return this.f75x <= x && this.f75x + this.width >= x && this.f76y <= y && this.f76y + this.height >= y;
    }

    public boolean contains(Vector2 vector) {
        return contains(vector.f100x, vector.f101y);
    }

    public boolean contains(Rectangle rectangle) {
        float xmin = rectangle.f75x;
        float xmax = xmin + rectangle.width;
        float ymin = rectangle.f76y;
        float ymax = ymin + rectangle.height;
        return xmin > this.f75x && xmin < this.f75x + this.width && xmax > this.f75x && xmax < this.f75x + this.width && ymin > this.f76y && ymin < this.f76y + this.height && ymax > this.f76y && ymax < this.f76y + this.height;
    }

    public boolean overlaps(Rectangle r) {
        return this.f75x < r.f75x + r.width && this.f75x + this.width > r.f75x && this.f76y < r.f76y + r.height && this.f76y + this.height > r.f76y;
    }

    public Rectangle set(Rectangle rect) {
        this.f75x = rect.f75x;
        this.f76y = rect.f76y;
        this.width = rect.width;
        this.height = rect.height;
        return this;
    }

    public Rectangle merge(Rectangle rect) {
        float minX = Math.min(this.f75x, rect.f75x);
        float maxX = Math.max(this.f75x + this.width, rect.f75x + rect.width);
        this.f75x = minX;
        this.width = maxX - minX;
        float minY = Math.min(this.f76y, rect.f76y);
        float maxY = Math.max(this.f76y + this.height, rect.f76y + rect.height);
        this.f76y = minY;
        this.height = maxY - minY;
        return this;
    }

    public float getAspectRatio() {
        return this.height == 0.0f ? Float.NaN : this.width / this.height;
    }

    public Vector2 getCenter(Vector2 vector) {
        vector.f100x = this.f75x + (this.width / 2.0f);
        vector.f101y = this.f76y + (this.height / 2.0f);
        return vector;
    }

    public Rectangle setCenter(float x, float y) {
        setPosition(x - (this.width / 2.0f), y - (this.height / 2.0f));
        return this;
    }

    public Rectangle setCenter(Vector2 position) {
        setPosition(position.f100x - (this.width / 2.0f), position.f101y - (this.height / 2.0f));
        return this;
    }

    public Rectangle fitOutside(Rectangle rect) {
        float ratio = getAspectRatio();
        if (ratio > rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }
        setPosition((rect.f75x + (rect.width / 2.0f)) - (this.width / 2.0f), (rect.f76y + (rect.height / 2.0f)) - (this.height / 2.0f));
        return this;
    }

    public Rectangle fitInside(Rectangle rect) {
        float ratio = getAspectRatio();
        if (ratio < rect.getAspectRatio()) {
            setSize(rect.height * ratio, rect.height);
        } else {
            setSize(rect.width, rect.width / ratio);
        }
        setPosition((rect.f75x + (rect.width / 2.0f)) - (this.width / 2.0f), (rect.f76y + (rect.height / 2.0f)) - (this.height / 2.0f));
        return this;
    }

    public String toString() {
        return this.f75x + "," + this.f76y + "," + this.width + "," + this.height;
    }
}
