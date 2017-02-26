package com.badlogic.gdx.math.collision;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import java.io.Serializable;
import java.util.List;

public class BoundingBox implements Serializable {
    private static final long serialVersionUID = -1286036817192127343L;
    final Vector3 cnt;
    final Vector3[] crn;
    boolean crn_dirty;
    final Vector3 dim;
    public final Vector3 max;
    public final Vector3 min;

    public Vector3 getCenter() {
        return this.cnt;
    }

    protected void updateCorners() {
        if (this.crn_dirty) {
            this.crn[0].set(this.min.f105x, this.min.f106y, this.min.f107z);
            this.crn[1].set(this.max.f105x, this.min.f106y, this.min.f107z);
            this.crn[2].set(this.max.f105x, this.max.f106y, this.min.f107z);
            this.crn[3].set(this.min.f105x, this.max.f106y, this.min.f107z);
            this.crn[4].set(this.min.f105x, this.min.f106y, this.max.f107z);
            this.crn[5].set(this.max.f105x, this.min.f106y, this.max.f107z);
            this.crn[6].set(this.max.f105x, this.max.f106y, this.max.f107z);
            this.crn[7].set(this.min.f105x, this.max.f106y, this.max.f107z);
            this.crn_dirty = false;
        }
    }

    public Vector3[] getCorners() {
        updateCorners();
        return this.crn;
    }

    public Vector3 getDimensions() {
        return this.dim;
    }

    public Vector3 getMin() {
        return this.min;
    }

    public synchronized Vector3 getMax() {
        return this.max;
    }

    public BoundingBox() {
        this.crn = new Vector3[8];
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.crn_dirty = true;
        this.crn_dirty = true;
        for (int l_idx = 0; l_idx < 8; l_idx++) {
            this.crn[l_idx] = new Vector3();
        }
        clr();
    }

    public BoundingBox(BoundingBox bounds) {
        this.crn = new Vector3[8];
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.crn_dirty = true;
        this.crn_dirty = true;
        for (int l_idx = 0; l_idx < 8; l_idx++) {
            this.crn[l_idx] = new Vector3();
        }
        set(bounds);
    }

    public BoundingBox(Vector3 minimum, Vector3 maximum) {
        this.crn = new Vector3[8];
        this.min = new Vector3();
        this.max = new Vector3();
        this.cnt = new Vector3();
        this.dim = new Vector3();
        this.crn_dirty = true;
        this.crn_dirty = true;
        for (int l_idx = 0; l_idx < 8; l_idx++) {
            this.crn[l_idx] = new Vector3();
        }
        set(minimum, maximum);
    }

    public BoundingBox set(BoundingBox bounds) {
        this.crn_dirty = true;
        return set(bounds.min, bounds.max);
    }

    public BoundingBox set(Vector3 minimum, Vector3 maximum) {
        this.min.set(minimum.f105x < maximum.f105x ? minimum.f105x : maximum.f105x, minimum.f106y < maximum.f106y ? minimum.f106y : maximum.f106y, minimum.f107z < maximum.f107z ? minimum.f107z : maximum.f107z);
        this.max.set(minimum.f105x > maximum.f105x ? minimum.f105x : maximum.f105x, minimum.f106y > maximum.f106y ? minimum.f106y : maximum.f106y, minimum.f107z > maximum.f107z ? minimum.f107z : maximum.f107z);
        this.cnt.set(this.min).add(this.max).scl(0.5f);
        this.dim.set(this.max).sub(this.min);
        this.crn_dirty = true;
        return this;
    }

    public BoundingBox set(Vector3[] points) {
        inf();
        for (Vector3 l_point : points) {
            ext(l_point);
        }
        this.crn_dirty = true;
        return this;
    }

    public BoundingBox set(List<Vector3> points) {
        inf();
        for (Vector3 l_point : points) {
            ext(l_point);
        }
        this.crn_dirty = true;
        return this;
    }

    public BoundingBox inf() {
        this.min.set(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY);
        this.max.set(Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY, Float.NEGATIVE_INFINITY);
        this.cnt.set(0.0f, 0.0f, 0.0f);
        this.dim.set(0.0f, 0.0f, 0.0f);
        this.crn_dirty = true;
        return this;
    }

    public BoundingBox ext(Vector3 point) {
        this.crn_dirty = true;
        return set(this.min.set(min(this.min.f105x, point.f105x), min(this.min.f106y, point.f106y), min(this.min.f107z, point.f107z)), this.max.set(Math.max(this.max.f105x, point.f105x), Math.max(this.max.f106y, point.f106y), Math.max(this.max.f107z, point.f107z)));
    }

    public BoundingBox clr() {
        this.crn_dirty = true;
        return set(this.min.set(0.0f, 0.0f, 0.0f), this.max.set(0.0f, 0.0f, 0.0f));
    }

    public boolean isValid() {
        return this.min.f105x < this.max.f105x && this.min.f106y < this.max.f106y && this.min.f107z < this.max.f107z;
    }

    public BoundingBox ext(BoundingBox a_bounds) {
        this.crn_dirty = true;
        return set(this.min.set(min(this.min.f105x, a_bounds.min.f105x), min(this.min.f106y, a_bounds.min.f106y), min(this.min.f107z, a_bounds.min.f107z)), this.max.set(max(this.max.f105x, a_bounds.max.f105x), max(this.max.f106y, a_bounds.max.f106y), max(this.max.f107z, a_bounds.max.f107z)));
    }

    public BoundingBox ext(BoundingBox bounds, Matrix4 transform) {
        bounds.updateCorners();
        for (Vector3 pnt : this.crn) {
            pnt.mul(transform);
            this.min.set(min(this.min.f105x, pnt.f105x), min(this.min.f106y, pnt.f106y), min(this.min.f107z, pnt.f107z));
            this.max.set(max(this.max.f105x, pnt.f105x), max(this.max.f106y, pnt.f106y), max(this.max.f107z, pnt.f107z));
        }
        this.crn_dirty = true;
        bounds.crn_dirty = true;
        return set(this.min, this.max);
    }

    public BoundingBox mul(Matrix4 matrix) {
        updateCorners();
        inf();
        for (Vector3 l_pnt : this.crn) {
            l_pnt.mul(matrix);
            this.min.set(min(this.min.f105x, l_pnt.f105x), min(this.min.f106y, l_pnt.f106y), min(this.min.f107z, l_pnt.f107z));
            this.max.set(max(this.max.f105x, l_pnt.f105x), max(this.max.f106y, l_pnt.f106y), max(this.max.f107z, l_pnt.f107z));
        }
        this.crn_dirty = true;
        return set(this.min, this.max);
    }

    public boolean contains(BoundingBox b) {
        return !isValid() || (this.min.f105x <= b.min.f105x && this.min.f106y <= b.min.f106y && this.min.f107z <= b.min.f107z && this.max.f105x >= b.max.f105x && this.max.f106y >= b.max.f106y && this.max.f107z >= b.max.f107z);
    }

    public boolean intersects(BoundingBox b) {
        if (!isValid()) {
            return false;
        }
        float lx = Math.abs(this.cnt.f105x - b.cnt.f105x);
        float sumx = (this.dim.f105x / 2.0f) + (b.dim.f105x / 2.0f);
        float ly = Math.abs(this.cnt.f106y - b.cnt.f106y);
        float sumy = (this.dim.f106y / 2.0f) + (b.dim.f106y / 2.0f);
        float lz = Math.abs(this.cnt.f107z - b.cnt.f107z);
        float sumz = (this.dim.f107z / 2.0f) + (b.dim.f107z / 2.0f);
        if (lx > sumx || ly > sumy || lz > sumz) {
            return false;
        }
        return true;
    }

    public boolean contains(Vector3 v) {
        return this.min.f105x <= v.f105x && this.max.f105x >= v.f105x && this.min.f106y <= v.f106y && this.max.f106y >= v.f106y && this.min.f107z <= v.f107z && this.max.f107z >= v.f107z;
    }

    public String toString() {
        return "[" + this.min + "|" + this.max + "]";
    }

    public BoundingBox ext(float x, float y, float z) {
        this.crn_dirty = true;
        return set(this.min.set(min(this.min.f105x, x), min(this.min.f106y, y), min(this.min.f107z, z)), this.max.set(max(this.max.f105x, x), max(this.max.f106y, y), max(this.max.f107z, z)));
    }

    static float min(float a, float b) {
        return a > b ? b : a;
    }

    static float max(float a, float b) {
        return a > b ? a : b;
    }
}
