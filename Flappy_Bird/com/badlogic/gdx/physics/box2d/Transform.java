package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

public class Transform {
    public static final int COS = 2;
    public static final int POS_X = 0;
    public static final int POS_Y = 1;
    public static final int SIN = 3;
    private Vector2 position;
    public float[] vals;

    public Transform() {
        this.vals = new float[4];
        this.position = new Vector2();
    }

    public Transform(Vector2 position, float angle) {
        this.vals = new float[4];
        this.position = new Vector2();
        setPosition(position);
        setRotation(angle);
    }

    public Vector2 mul(Vector2 v) {
        float y = (this.vals[POS_Y] + (this.vals[SIN] * v.f100x)) + (this.vals[COS] * v.f101y);
        v.f100x = (this.vals[POS_X] + (this.vals[COS] * v.f100x)) + ((-this.vals[SIN]) * v.f101y);
        v.f101y = y;
        return v;
    }

    public Vector2 getPosition() {
        return this.position.set(this.vals[POS_X], this.vals[POS_Y]);
    }

    public void setRotation(float angle) {
        float s = (float) Math.sin((double) angle);
        this.vals[COS] = (float) Math.cos((double) angle);
        this.vals[SIN] = s;
    }

    public float getRotation() {
        return (float) Math.atan2((double) this.vals[SIN], (double) this.vals[COS]);
    }

    public void setPosition(Vector2 pos) {
        this.vals[POS_X] = pos.f100x;
        this.vals[POS_Y] = pos.f101y;
    }
}
