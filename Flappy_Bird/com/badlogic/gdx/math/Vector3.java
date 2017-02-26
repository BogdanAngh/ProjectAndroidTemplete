package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.Serializable;

public class Vector3 implements Serializable, Vector<Vector3> {
    public static final Vector3 f102X;
    public static final Vector3 f103Y;
    public static final Vector3 f104Z;
    public static final Vector3 Zero;
    private static final long serialVersionUID = 3840054589595372522L;
    public static final Vector3 tmp;
    public static final Vector3 tmp2;
    public static final Vector3 tmp3;
    private static final Matrix4 tmpMat;
    public float f105x;
    public float f106y;
    public float f107z;

    static {
        tmp = new Vector3();
        tmp2 = new Vector3();
        tmp3 = new Vector3();
        f102X = new Vector3(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f, 0.0f);
        f103Y = new Vector3(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        f104Z = new Vector3(0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        Zero = new Vector3(0.0f, 0.0f, 0.0f);
        tmpMat = new Matrix4();
    }

    public Vector3(float x, float y, float z) {
        set(x, y, z);
    }

    public Vector3(Vector3 vector) {
        set(vector);
    }

    public Vector3(float[] values) {
        set(values[0], values[1], values[2]);
    }

    public Vector3(Vector2 vector, float z) {
        set(vector.f100x, vector.f101y, z);
    }

    public Vector3 set(float x, float y, float z) {
        this.f105x = x;
        this.f106y = y;
        this.f107z = z;
        return this;
    }

    public Vector3 set(Vector3 vector) {
        return set(vector.f105x, vector.f106y, vector.f107z);
    }

    public Vector3 set(float[] values) {
        return set(values[0], values[1], values[2]);
    }

    public Vector3 set(Vector2 vector, float z) {
        return set(vector.f100x, vector.f101y, z);
    }

    public Vector3 cpy() {
        return new Vector3(this);
    }

    public Vector3 tmp() {
        return tmp.set(this);
    }

    public Vector3 tmp2() {
        return tmp2.set(this);
    }

    Vector3 tmp3() {
        return tmp3.set(this);
    }

    public Vector3 add(Vector3 vector) {
        return add(vector.f105x, vector.f106y, vector.f107z);
    }

    public Vector3 add(float x, float y, float z) {
        return set(this.f105x + x, this.f106y + y, this.f107z + z);
    }

    public Vector3 add(float values) {
        return set(this.f105x + values, this.f106y + values, this.f107z + values);
    }

    public Vector3 sub(Vector3 a_vec) {
        return sub(a_vec.f105x, a_vec.f106y, a_vec.f107z);
    }

    public Vector3 sub(float x, float y, float z) {
        return set(this.f105x - x, this.f106y - y, this.f107z - z);
    }

    public Vector3 sub(float value) {
        return set(this.f105x - value, this.f106y - value, this.f107z - value);
    }

    public Vector3 scl(float value) {
        return set(this.f105x * value, this.f106y * value, this.f107z * value);
    }

    public Vector3 mul(float value) {
        return scl(value);
    }

    public Vector3 scl(Vector3 other) {
        return set(this.f105x * other.f105x, this.f106y * other.f106y, this.f107z * other.f107z);
    }

    public Vector3 mul(Vector3 other) {
        return scl(other);
    }

    public Vector3 scl(float vx, float vy, float vz) {
        return set(this.f105x * vx, this.f106y * vy, this.f107z * vz);
    }

    public Vector3 mul(float vx, float vy, float vz) {
        return scl(vx, vy, vz);
    }

    public Vector3 scale(float scalarX, float scalarY, float scalarZ) {
        return scl(scalarX, scalarY, scalarZ);
    }

    public Vector3 div(float value) {
        return scl(TextTrackStyle.DEFAULT_FONT_SCALE / value);
    }

    public Vector3 div(float vx, float vy, float vz) {
        return set(this.f105x / vx, this.f106y / vy, this.f107z / vz);
    }

    public Vector3 div(Vector3 other) {
        return set(this.f105x / other.f105x, this.f106y / other.f106y, this.f107z / other.f107z);
    }

    public static float len(float x, float y, float z) {
        return (float) Math.sqrt((double) (((x * x) + (y * y)) + (z * z)));
    }

    public float len() {
        return (float) Math.sqrt((double) (((this.f105x * this.f105x) + (this.f106y * this.f106y)) + (this.f107z * this.f107z)));
    }

    public static float len2(float x, float y, float z) {
        return ((x * x) + (y * y)) + (z * z);
    }

    public float len2() {
        return ((this.f105x * this.f105x) + (this.f106y * this.f106y)) + (this.f107z * this.f107z);
    }

    public boolean idt(Vector3 vector) {
        return this.f105x == vector.f105x && this.f106y == vector.f106y && this.f107z == vector.f107z;
    }

    public static float dst(float x1, float y1, float z1, float x2, float y2, float z2) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public float dst(Vector3 vector) {
        float a = vector.f105x - this.f105x;
        float b = vector.f106y - this.f106y;
        float c = vector.f107z - this.f107z;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public float dst(float x, float y, float z) {
        float a = x - this.f105x;
        float b = y - this.f106y;
        float c = z - this.f107z;
        return (float) Math.sqrt((double) (((a * a) + (b * b)) + (c * c)));
    }

    public static float dst2(float x1, float y1, float z1, float x2, float y2, float z2) {
        float a = x2 - x1;
        float b = y2 - y1;
        float c = z2 - z1;
        return ((a * a) + (b * b)) + (c * c);
    }

    public float dst2(Vector3 point) {
        float a = point.f105x - this.f105x;
        float b = point.f106y - this.f106y;
        float c = point.f107z - this.f107z;
        return ((a * a) + (b * b)) + (c * c);
    }

    public float dst2(float x, float y, float z) {
        float a = x - this.f105x;
        float b = y - this.f106y;
        float c = z - this.f107z;
        return ((a * a) + (b * b)) + (c * c);
    }

    public Vector3 nor() {
        float len2 = len2();
        return (len2 == 0.0f || len2 == TextTrackStyle.DEFAULT_FONT_SCALE) ? this : scl(TextTrackStyle.DEFAULT_FONT_SCALE / ((float) Math.sqrt((double) len2)));
    }

    public static float dot(float x1, float y1, float z1, float x2, float y2, float z2) {
        return ((x1 * x2) + (y1 * y2)) + (z1 * z2);
    }

    public float dot(Vector3 vector) {
        return ((this.f105x * vector.f105x) + (this.f106y * vector.f106y)) + (this.f107z * vector.f107z);
    }

    public float dot(float x, float y, float z) {
        return ((this.f105x * x) + (this.f106y * y)) + (this.f107z * z);
    }

    public Vector3 crs(Vector3 vector) {
        return set((this.f106y * vector.f107z) - (this.f107z * vector.f106y), (this.f107z * vector.f105x) - (this.f105x * vector.f107z), (this.f105x * vector.f106y) - (this.f106y * vector.f105x));
    }

    public Vector3 crs(float x, float y, float z) {
        return set((this.f106y * z) - (this.f107z * y), (this.f107z * x) - (this.f105x * z), (this.f105x * y) - (this.f106y * x));
    }

    public Vector3 mul(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set((((this.f105x * l_mat[0]) + (this.f106y * l_mat[4])) + (this.f107z * l_mat[8])) + l_mat[12], (((this.f105x * l_mat[1]) + (this.f106y * l_mat[5])) + (this.f107z * l_mat[9])) + l_mat[13], (((this.f105x * l_mat[2]) + (this.f106y * l_mat[6])) + (this.f107z * l_mat[10])) + l_mat[14]);
    }

    public Vector3 mul(Quaternion quat) {
        return quat.transform(this);
    }

    public Vector3 prj(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        float l_w = TextTrackStyle.DEFAULT_FONT_SCALE / ((((this.f105x * l_mat[3]) + (this.f106y * l_mat[7])) + (this.f107z * l_mat[11])) + l_mat[15]);
        return set(((((this.f105x * l_mat[0]) + (this.f106y * l_mat[4])) + (this.f107z * l_mat[8])) + l_mat[12]) * l_w, ((((this.f105x * l_mat[1]) + (this.f106y * l_mat[5])) + (this.f107z * l_mat[9])) + l_mat[13]) * l_w, ((((this.f105x * l_mat[2]) + (this.f106y * l_mat[6])) + (this.f107z * l_mat[10])) + l_mat[14]) * l_w);
    }

    public Vector3 rot(Matrix4 matrix) {
        float[] l_mat = matrix.val;
        return set(((this.f105x * l_mat[0]) + (this.f106y * l_mat[4])) + (this.f107z * l_mat[8]), ((this.f105x * l_mat[1]) + (this.f106y * l_mat[5])) + (this.f107z * l_mat[9]), ((this.f105x * l_mat[2]) + (this.f106y * l_mat[6])) + (this.f107z * l_mat[10]));
    }

    public Vector3 rotate(float degrees, float axisX, float axisY, float axisZ) {
        return mul(tmpMat.setToRotation(axisX, axisY, axisZ, degrees));
    }

    public Vector3 rotate(Vector3 axis, float degrees) {
        tmpMat.setToRotation(axis, degrees);
        return mul(tmpMat);
    }

    public boolean isUnit() {
        return isUnit(MathUtils.nanoToSec);
    }

    public boolean isUnit(float margin) {
        return Math.abs(len2() - TextTrackStyle.DEFAULT_FONT_SCALE) < margin * margin;
    }

    public boolean isZero() {
        return this.f105x == 0.0f && this.f106y == 0.0f && this.f107z == 0.0f;
    }

    public boolean isZero(float margin) {
        return len2() < margin * margin;
    }

    public Vector3 lerp(Vector3 target, float alpha) {
        scl(TextTrackStyle.DEFAULT_FONT_SCALE - alpha);
        add(target.f105x * alpha, target.f106y * alpha, target.f107z * alpha);
        return this;
    }

    public Vector3 slerp(Vector3 target, float alpha) {
        float dot = dot(target);
        if (((double) dot) > 0.9995d || ((double) dot) < -0.9995d) {
            return lerp(target, alpha);
        }
        float theta = ((float) Math.acos((double) dot)) * alpha;
        float tx = target.f105x - (this.f105x * dot);
        float ty = target.f106y - (this.f106y * dot);
        float tz = target.f107z - (this.f107z * dot);
        float l2 = ((tx * tx) + (ty * ty)) + (tz * tz);
        float dl = ((float) Math.sin((double) theta)) * (l2 < 1.0E-4f ? TextTrackStyle.DEFAULT_FONT_SCALE : TextTrackStyle.DEFAULT_FONT_SCALE / ((float) Math.sqrt((double) l2)));
        return scl((float) Math.cos((double) theta)).add(tx * dl, ty * dl, tz * dl).nor();
    }

    public String toString() {
        return this.f105x + "," + this.f106y + "," + this.f107z;
    }

    public Vector3 limit(float limit) {
        if (len2() > limit * limit) {
            nor().scl(limit);
        }
        return this;
    }

    public Vector3 clamp(float min, float max) {
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

    public int hashCode() {
        return ((((NumberUtils.floatToIntBits(this.f105x) + 31) * 31) + NumberUtils.floatToIntBits(this.f106y)) * 31) + NumberUtils.floatToIntBits(this.f107z);
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
        Vector3 other = (Vector3) obj;
        if (NumberUtils.floatToIntBits(this.f105x) != NumberUtils.floatToIntBits(other.f105x)) {
            return false;
        }
        if (NumberUtils.floatToIntBits(this.f106y) != NumberUtils.floatToIntBits(other.f106y)) {
            return false;
        }
        if (NumberUtils.floatToIntBits(this.f107z) != NumberUtils.floatToIntBits(other.f107z)) {
            return false;
        }
        return true;
    }

    public boolean epsilonEquals(Vector3 obj, float epsilon) {
        if (obj != null && Math.abs(obj.f105x - this.f105x) <= epsilon && Math.abs(obj.f106y - this.f106y) <= epsilon && Math.abs(obj.f107z - this.f107z) <= epsilon) {
            return true;
        }
        return false;
    }

    public boolean epsilonEquals(float x, float y, float z, float epsilon) {
        if (Math.abs(x - this.f105x) <= epsilon && Math.abs(y - this.f106y) <= epsilon && Math.abs(z - this.f107z) <= epsilon) {
            return true;
        }
        return false;
    }
}
