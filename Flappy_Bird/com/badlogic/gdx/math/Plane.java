package com.badlogic.gdx.math;

import java.io.Serializable;

public class Plane implements Serializable {
    private static final long serialVersionUID = -1240652082930747866L;
    public float f66d;
    public final Vector3 normal;

    public enum PlaneSide {
        OnPlane,
        Back,
        Front
    }

    public Plane(Vector3 normal, float d) {
        this.normal = new Vector3();
        this.f66d = 0.0f;
        this.normal.set(normal).nor();
        this.f66d = d;
    }

    public Plane(Vector3 normal, Vector3 point) {
        this.normal = new Vector3();
        this.f66d = 0.0f;
        this.normal.set(normal).nor();
        this.f66d = -this.normal.dot(point);
    }

    public Plane(Vector3 point1, Vector3 point2, Vector3 point3) {
        this.normal = new Vector3();
        this.f66d = 0.0f;
        set(point1, point2, point3);
    }

    public void set(Vector3 point1, Vector3 point2, Vector3 point3) {
        Vector3 nor = point1.tmp().sub(point2).crs(point2.tmp2().sub(point3)).nor();
        this.normal.set(nor);
        this.f66d = -point1.dot(nor);
    }

    public void set(float nx, float ny, float nz, float d) {
        this.normal.set(nx, ny, nz);
        this.f66d = d;
    }

    public float distance(Vector3 point) {
        return this.normal.dot(point) + this.f66d;
    }

    public PlaneSide testPoint(Vector3 point) {
        float dist = this.normal.dot(point) + this.f66d;
        if (dist == 0.0f) {
            return PlaneSide.OnPlane;
        }
        if (dist < 0.0f) {
            return PlaneSide.Back;
        }
        return PlaneSide.Front;
    }

    public PlaneSide testPoint(float x, float y, float z) {
        float dist = this.normal.dot(x, y, z) + this.f66d;
        if (dist == 0.0f) {
            return PlaneSide.OnPlane;
        }
        if (dist < 0.0f) {
            return PlaneSide.Back;
        }
        return PlaneSide.Front;
    }

    public boolean isFrontFacing(Vector3 direction) {
        return this.normal.dot(direction) <= 0.0f;
    }

    public Vector3 getNormal() {
        return this.normal;
    }

    public float getD() {
        return this.f66d;
    }

    public void set(Vector3 point, Vector3 normal) {
        this.normal.set(normal);
        this.f66d = -point.dot(normal);
    }

    public void set(float pointX, float pointY, float pointZ, float norX, float norY, float norZ) {
        this.normal.set(norX, norY, norZ);
        this.f66d = -(((pointX * norX) + (pointY * norY)) + (pointZ * norZ));
    }

    public void set(Plane plane) {
        this.normal.set(plane.normal);
        this.f66d = plane.f66d;
    }

    public String toString() {
        return this.normal.toString() + ", " + this.f66d;
    }
}
