package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.Serializable;

public class Vector2 implements Serializable, Vector<Vector2> {
    public static final Vector2 f98X;
    public static final Vector2 f99Y;
    public static final Vector2 Zero;
    private static final long serialVersionUID = 913902788239530931L;
    public float f100x;
    public float f101y;

    static {
        f98X = new Vector2(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        f99Y = new Vector2(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Zero = new Vector2(0.0f, 0.0f);
    }

    public Vector2(float x, float y) {
        this.f100x = x;
        this.f101y = y;
    }

    public Vector2(Vector2 v) {
        set(v);
    }

    public Vector2 cpy() {
        return new Vector2(this);
    }

    public float len() {
        return (float) Math.sqrt((double) ((this.f100x * this.f100x) + (this.f101y * this.f101y)));
    }

    public float len2() {
        return (this.f100x * this.f100x) + (this.f101y * this.f101y);
    }

    public Vector2 set(Vector2 v) {
        this.f100x = v.f100x;
        this.f101y = v.f101y;
        return this;
    }

    public Vector2 set(float x, float y) {
        this.f100x = x;
        this.f101y = y;
        return this;
    }

    public Vector2 sub(Vector2 v) {
        this.f100x -= v.f100x;
        this.f101y -= v.f101y;
        return this;
    }

    public Vector2 nor() {
        float len = len();
        if (len != 0.0f) {
            this.f100x /= len;
            this.f101y /= len;
        }
        return this;
    }

    public Vector2 add(Vector2 v) {
        this.f100x += v.f100x;
        this.f101y += v.f101y;
        return this;
    }

    public Vector2 add(float x, float y) {
        this.f100x += x;
        this.f101y += y;
        return this;
    }

    public float dot(Vector2 v) {
        return (this.f100x * v.f100x) + (this.f101y * v.f101y);
    }

    public Vector2 scl(float scalar) {
        this.f100x *= scalar;
        this.f101y *= scalar;
        return this;
    }

    public Vector2 mul(float scalar) {
        return scl(scalar);
    }

    public Vector2 scl(float x, float y) {
        this.f100x *= x;
        this.f101y *= y;
        return this;
    }

    public Vector2 mul(float x, float y) {
        return scl(x, y);
    }

    public Vector2 scl(Vector2 v) {
        this.f100x *= v.f100x;
        this.f101y *= v.f101y;
        return this;
    }

    public Vector2 mul(Vector2 v) {
        return scl(v);
    }

    public Vector2 div(float value) {
        return scl(TextTrackStyle.DEFAULT_FONT_SCALE / value);
    }

    public Vector2 div(float vx, float vy) {
        return scl(TextTrackStyle.DEFAULT_FONT_SCALE / vx, TextTrackStyle.DEFAULT_FONT_SCALE / vy);
    }

    public Vector2 div(Vector2 other) {
        return scl(TextTrackStyle.DEFAULT_FONT_SCALE / other.f100x, TextTrackStyle.DEFAULT_FONT_SCALE / other.f101y);
    }

    public float dst(Vector2 v) {
        float x_d = v.f100x - this.f100x;
        float y_d = v.f101y - this.f101y;
        return (float) Math.sqrt((double) ((x_d * x_d) + (y_d * y_d)));
    }

    public float dst(float x, float y) {
        float x_d = x - this.f100x;
        float y_d = y - this.f101y;
        return (float) Math.sqrt((double) ((x_d * x_d) + (y_d * y_d)));
    }

    public float dst2(Vector2 v) {
        float x_d = v.f100x - this.f100x;
        float y_d = v.f101y - this.f101y;
        return (x_d * x_d) + (y_d * y_d);
    }

    public float dst2(float x, float y) {
        float x_d = x - this.f100x;
        float y_d = y - this.f101y;
        return (x_d * x_d) + (y_d * y_d);
    }

    public Vector2 limit(float limit) {
        if (len2() > limit * limit) {
            nor();
            scl(limit);
        }
        return this;
    }

    public Vector2 clamp(float min, float max) {
        float l2 = len2();
        if (l2 == 0.0f) {
            return this;
        }
        if (l2 > max * max) {
            return nor().scl(max);
        }
        if (l2 < min * min) {
            return nor().scl(min);
        }
        return this;
    }

    public String toString() {
        return "[" + this.f100x + ":" + this.f101y + "]";
    }

    public Vector2 sub(float x, float y) {
        this.f100x -= x;
        this.f101y -= y;
        return this;
    }

    public Vector2 mul(Matrix3 mat) {
        float y = ((this.f100x * mat.val[1]) + (this.f101y * mat.val[4])) + mat.val[7];
        this.f100x = ((this.f100x * mat.val[0]) + (this.f101y * mat.val[3])) + mat.val[6];
        this.f101y = y;
        return this;
    }

    public float crs(Vector2 v) {
        return (this.f100x * v.f101y) - (this.f101y * v.f100x);
    }

    public float crs(float x, float y) {
        return (this.f100x * y) - (this.f101y * x);
    }

    public float angle() {
        float angle = ((float) Math.atan2((double) this.f101y, (double) this.f100x)) * MathUtils.radiansToDegrees;
        if (angle < 0.0f) {
            return angle + 360.0f;
        }
        return angle;
    }

    public Vector2 setAngle(float degrees) {
        set(len(), 0.0f);
        rotate(degrees);
        return this;
    }

    public Vector2 rotate(float degrees) {
        float rad = degrees * MathUtils.degreesToRadians;
        float cos = (float) Math.cos((double) rad);
        float sin = (float) Math.sin((double) rad);
        float newY = (this.f100x * sin) + (this.f101y * cos);
        this.f100x = (this.f100x * cos) - (this.f101y * sin);
        this.f101y = newY;
        return this;
    }

    public Vector2 lerp(Vector2 target, float alpha) {
        float invAlpha = TextTrackStyle.DEFAULT_FONT_SCALE - alpha;
        this.f100x = (this.f100x * invAlpha) + (target.f100x * alpha);
        this.f101y = (this.f101y * invAlpha) + (target.f101y * alpha);
        return this;
    }

    public int hashCode() {
        return ((NumberUtils.floatToIntBits(this.f100x) + 31) * 31) + NumberUtils.floatToIntBits(this.f101y);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector2 other = (Vector2) obj;
        if (NumberUtils.floatToIntBits(this.f100x) != NumberUtils.floatToIntBits(other.f100x)) {
            return false;
        }
        if (NumberUtils.floatToIntBits(this.f101y) != NumberUtils.floatToIntBits(other.f101y)) {
            return false;
        }
        return true;
    }

    public boolean epsilonEquals(Vector2 obj, float epsilon) {
        if (obj != null && Math.abs(obj.f100x - this.f100x) <= epsilon && Math.abs(obj.f101y - this.f101y) <= epsilon) {
            return true;
        }
        return false;
    }

    public boolean epsilonEquals(float x, float y, float epsilon) {
        if (Math.abs(x - this.f100x) <= epsilon && Math.abs(y - this.f101y) <= epsilon) {
            return true;
        }
        return false;
    }
}
