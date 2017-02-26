package com.badlogic.gdx.physics.box2d;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Shape.Type;

public class EdgeShape extends Shape {
    static final float[] vertex;

    private native void jniGetVertex1(long j, float[] fArr);

    private native void jniGetVertex2(long j, float[] fArr);

    private native void jniSet(long j, float f, float f2, float f3, float f4);

    private native long newEdgeShape();

    public EdgeShape() {
        this.addr = newEdgeShape();
    }

    EdgeShape(long addr) {
        this.addr = addr;
    }

    public void set(Vector2 v1, Vector2 v2) {
        set(v1.f100x, v1.f101y, v2.f100x, v2.f101y);
    }

    public void set(float v1X, float v1Y, float v2X, float v2Y) {
        jniSet(this.addr, v1X, v1Y, v2X, v2Y);
    }

    static {
        vertex = new float[2];
    }

    public void getVertex1(Vector2 vec) {
        jniGetVertex1(this.addr, vertex);
        vec.f100x = vertex[0];
        vec.f101y = vertex[1];
    }

    public void getVertex2(Vector2 vec) {
        jniGetVertex2(this.addr, vertex);
        vec.f100x = vertex[0];
        vec.f101y = vertex[1];
    }

    public Type getType() {
        return Type.Edge;
    }
}
