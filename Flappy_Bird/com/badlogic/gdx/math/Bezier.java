package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.cast.TextTrackStyle;

public class Bezier<T extends Vector<T>> implements Path<T> {
    public Array<T> points;
    private T tmp;

    public static <T extends Vector<T>> T linear(T out, float t, T p0, T p1, T tmp) {
        return out.set(p0).scl(TextTrackStyle.DEFAULT_FONT_SCALE - t).add(tmp.set(p1).scl(t));
    }

    public static <T extends Vector<T>> T quadratic(T out, float t, T p0, T p1, T p2, T tmp) {
        float dt = TextTrackStyle.DEFAULT_FONT_SCALE - t;
        return out.set(p0).scl(dt * dt).add(tmp.set(p1).scl((2.0f * dt) * t)).add(tmp.set(p2).scl(t * t));
    }

    public static <T extends Vector<T>> T cubic(T out, float t, T p0, T p1, T p2, T p3, T tmp) {
        float dt = TextTrackStyle.DEFAULT_FONT_SCALE - t;
        float dt2 = dt * dt;
        float t2 = t * t;
        return out.set(p0).scl(dt2 * dt).add(tmp.set(p1).scl((3.0f * dt2) * t)).add(tmp.set(p2).scl((3.0f * dt) * t2)).add(tmp.set(p3).scl(t2 * t));
    }

    public Bezier() {
        this.points = new Array();
    }

    public Bezier(T... points) {
        this.points = new Array();
        set(points);
    }

    public Bezier(T[] points, int offset, int length) {
        this.points = new Array();
        set((Vector[]) points, offset, length);
    }

    public Bezier(Array<T> points, int offset, int length) {
        this.points = new Array();
        set((Array) points, offset, length);
    }

    public Bezier set(T... points) {
        return set((Vector[]) points, 0, points.length);
    }

    public Bezier set(T[] points, int offset, int length) {
        if (length < 2 || length > 4) {
            throw new GdxRuntimeException("Only first, second and third degree Bezier curves are supported.");
        }
        if (this.tmp == null) {
            this.tmp = points[0].cpy();
        }
        this.points.clear();
        this.points.addAll((Object[]) points, offset, length);
        return this;
    }

    public Bezier set(Array<T> points, int offset, int length) {
        if (length < 2 || length > 4) {
            throw new GdxRuntimeException("Only first, second and third degree Bezier curves are supported.");
        }
        if (this.tmp == null) {
            this.tmp = ((Vector) points.get(0)).cpy();
        }
        this.points.clear();
        this.points.addAll((Array) points, offset, length);
        return this;
    }

    public T valueAt(T out, float t) {
        int n = this.points.size;
        if (n == 2) {
            linear(out, t, (Vector) this.points.get(0), (Vector) this.points.get(1), this.tmp);
        } else if (n == 3) {
            quadratic(out, t, (Vector) this.points.get(0), (Vector) this.points.get(1), (Vector) this.points.get(2), this.tmp);
        } else if (n == 4) {
            cubic(out, t, (Vector) this.points.get(0), (Vector) this.points.get(1), (Vector) this.points.get(2), (Vector) this.points.get(3), this.tmp);
        }
        return out;
    }

    public float approximate(T v) {
        Vector p1 = (Vector) this.points.get(0);
        Vector p2 = (Vector) this.points.get(this.points.size - 1);
        T p3 = v;
        float l1 = p1.dst(p2);
        float l2 = p3.dst(p2);
        float l3 = p3.dst(p1);
        return MathUtils.clamp((l1 - ((((l2 * l2) + (l1 * l1)) - (l3 * l3)) / (2.0f * l1))) / l1, 0.0f, (float) TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public float locate(T v) {
        return approximate((Vector) v);
    }
}
