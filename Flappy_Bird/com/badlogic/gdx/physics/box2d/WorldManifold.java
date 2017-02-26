package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;

public class WorldManifold {
    protected final Vector2 normal;
    protected int numContactPoints;
    protected final Vector2[] points;

    protected WorldManifold() {
        this.normal = new Vector2();
        this.points = new Vector2[]{new Vector2(), new Vector2()};
    }

    public Vector2 getNormal() {
        return this.normal;
    }

    public Vector2[] getPoints() {
        return this.points;
    }

    public int getNumberOfContactPoints() {
        return this.numContactPoints;
    }
}
