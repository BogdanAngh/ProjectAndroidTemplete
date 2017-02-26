package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;

public class Segment implements Serializable {
    private static final long serialVersionUID = 2739667069736519602L;
    public final Vector3 f77a;
    public final Vector3 f78b;

    public Segment(Vector3 a, Vector3 b) {
        this.f77a = new Vector3();
        this.f78b = new Vector3();
        this.f77a.set(a);
        this.f78b.set(b);
    }

    public Segment(float aX, float aY, float aZ, float bX, float bY, float bZ) {
        this.f77a = new Vector3();
        this.f78b = new Vector3();
        this.f77a.set(aX, aY, aZ);
        this.f78b.set(bX, bY, bZ);
    }
}
