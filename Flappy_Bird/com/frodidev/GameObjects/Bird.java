package com.frodidev.GameObjects;

import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.frodidev.ZBHelpers.AssetLoader;

public class Bird {
    private Vector2 acceleration;
    private Circle boundingCircle;
    private float height;
    private boolean isAlive;
    private float originalY;
    private Vector2 position;
    private float rotation;
    private Vector2 velocity;
    private int width;

    public Bird(float x, float y, int width, int height) {
        this.width = width;
        this.height = (float) height;
        this.originalY = y;
        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0.0f, 0.0f);
        this.acceleration = new Vector2(0.0f, 460.0f);
        this.boundingCircle = new Circle();
        this.isAlive = true;
    }

    public void update(float delta) {
        this.velocity.add(this.acceleration.cpy().scl(delta));
        if (this.velocity.f101y > 200.0f) {
            this.velocity.f101y = 200.0f;
        }
        if (this.position.f101y < -13.0f) {
            this.position.f101y = -13.0f;
            this.velocity.f101y = 0.0f;
        }
        this.position.add(this.velocity.cpy().scl(delta));
        this.boundingCircle.set(this.position.f100x + 9.0f, this.position.f101y + 6.0f, 6.5f);
        if (this.velocity.f101y < 0.0f) {
            this.rotation -= 600.0f * delta;
            if (this.rotation < -20.0f) {
                this.rotation = -20.0f;
            }
        }
        if (isFalling() || !this.isAlive) {
            this.rotation += 480.0f * delta;
            if (this.rotation > 90.0f) {
                this.rotation = 90.0f;
            }
        }
    }

    public void updateReady(float runTime) {
        this.position.f101y = (2.0f * ((float) Math.sin((double) (7.0f * runTime)))) + this.originalY;
    }

    public boolean isFalling() {
        return this.velocity.f101y > 110.0f;
    }

    public boolean shouldntFlap() {
        return this.velocity.f101y > 70.0f || !this.isAlive;
    }

    public void onClick() {
        if (this.isAlive) {
            AssetLoader.flap.play();
            this.velocity.f101y = -140.0f;
        }
    }

    public void die() {
        this.isAlive = false;
        this.velocity.f101y = 0.0f;
    }

    public void decelerate() {
        this.acceleration.f101y = 0.0f;
    }

    public void onRestart(int y) {
        this.rotation = 0.0f;
        this.position.f101y = (float) y;
        this.velocity.f100x = 0.0f;
        this.velocity.f101y = 0.0f;
        this.acceleration.f100x = 0.0f;
        this.acceleration.f101y = 460.0f;
        this.isAlive = true;
    }

    public float getX() {
        return this.position.f100x;
    }

    public float getY() {
        return this.position.f101y;
    }

    public float getWidth() {
        return (float) this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getRotation() {
        return this.rotation;
    }

    public Circle getBoundingCircle() {
        return this.boundingCircle;
    }

    public boolean isAlive() {
        return this.isAlive;
    }
}
