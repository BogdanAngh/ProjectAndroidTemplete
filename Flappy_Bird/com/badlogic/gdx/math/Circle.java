package com.badlogic.gdx.math;

import java.io.Serializable;

public class Circle implements Serializable {
    public float radius;
    public float f55x;
    public float f56y;

    public Circle(float x, float y, float radius) {
        this.f55x = x;
        this.f56y = y;
        this.radius = radius;
    }

    public Circle(Vector2 position, float radius) {
        this.f55x = position.f100x;
        this.f56y = position.f101y;
        this.radius = radius;
    }

    public Circle(Circle circle) {
        this.f55x = circle.f55x;
        this.f56y = circle.f56y;
        this.radius = circle.radius;
    }

    public void set(float x, float y, float radius) {
        this.f55x = x;
        this.f56y = y;
        this.radius = radius;
    }

    public void set(Vector2 position, float radius) {
        this.f55x = position.f100x;
        this.f56y = position.f101y;
        this.radius = radius;
    }

    public void set(Circle circle) {
        this.f55x = circle.f55x;
        this.f56y = circle.f56y;
        this.radius = circle.radius;
    }

    public void setPosition(Vector2 position) {
        this.f55x = position.f100x;
        this.f56y = position.f101y;
    }

    public void setPosition(float x, float y) {
        this.f55x = x;
        this.f56y = y;
    }

    public void setX(float x) {
        this.f55x = x;
    }

    public void setY(float y) {
        this.f56y = y;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public boolean contains(float x, float y) {
        x = this.f55x - x;
        y = this.f56y - y;
        return (x * x) + (y * y) <= this.radius * this.radius;
    }

    public boolean contains(Vector2 point) {
        float dx = this.f55x - point.f100x;
        float dy = this.f56y - point.f101y;
        return (dx * dx) + (dy * dy) <= this.radius * this.radius;
    }

    public boolean contains(Circle c) {
        float dx = this.f55x - c.f55x;
        float dy = this.f56y - c.f56y;
        return ((dx * dx) + (dy * dy)) + (c.radius * c.radius) <= this.radius * this.radius;
    }

    public boolean overlaps(Circle c) {
        float dx = this.f55x - c.f55x;
        float dy = this.f56y - c.f56y;
        float radiusSum = this.radius + c.radius;
        return (dx * dx) + (dy * dy) < radiusSum * radiusSum;
    }

    public String toString() {
        return this.f55x + "," + this.f56y + "," + this.radius;
    }
}
