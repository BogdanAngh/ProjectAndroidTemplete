package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.io.Serializable;

public class Quaternion implements Serializable {
    private static final float NORMALIZATION_TOLERANCE = 1.0E-5f;
    private static final long serialVersionUID = -7661875440774897168L;
    private static Quaternion tmp1;
    private static Quaternion tmp2;
    public float f71w;
    public float f72x;
    public float f73y;
    public float f74z;

    static {
        tmp1 = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
        tmp2 = new Quaternion(0.0f, 0.0f, 0.0f, 0.0f);
    }

    public Quaternion(float x, float y, float z, float w) {
        set(x, y, z, w);
    }

    public Quaternion() {
        idt();
    }

    public Quaternion(Quaternion quaternion) {
        set(quaternion);
    }

    public Quaternion(Vector3 axis, float angle) {
        set(axis, angle);
    }

    public Quaternion set(float x, float y, float z, float w) {
        this.f72x = x;
        this.f73y = y;
        this.f74z = z;
        this.f71w = w;
        return this;
    }

    public Quaternion set(Quaternion quaternion) {
        return set(quaternion.f72x, quaternion.f73y, quaternion.f74z, quaternion.f71w);
    }

    public Quaternion set(Vector3 axis, float angle) {
        return setFromAxis(axis.f105x, axis.f106y, axis.f107z, angle);
    }

    public Quaternion cpy() {
        return new Quaternion(this);
    }

    public float len() {
        return (float) Math.sqrt((double) ((((this.f72x * this.f72x) + (this.f73y * this.f73y)) + (this.f74z * this.f74z)) + (this.f71w * this.f71w)));
    }

    public String toString() {
        return "[" + this.f72x + "|" + this.f73y + "|" + this.f74z + "|" + this.f71w + "]";
    }

    public Quaternion setEulerAngles(float yaw, float pitch, float roll) {
        yaw = (float) Math.toRadians((double) yaw);
        pitch = (float) Math.toRadians((double) pitch);
        float num9 = ((float) Math.toRadians((double) roll)) * 0.5f;
        float num6 = (float) Math.sin((double) num9);
        float num5 = (float) Math.cos((double) num9);
        float num8 = pitch * 0.5f;
        float num4 = (float) Math.sin((double) num8);
        float num3 = (float) Math.cos((double) num8);
        float num7 = yaw * 0.5f;
        float num2 = (float) Math.sin((double) num7);
        float num = (float) Math.cos((double) num7);
        float f1 = num * num4;
        float f2 = num2 * num3;
        float f3 = num * num3;
        float f4 = num2 * num4;
        this.f72x = (f1 * num5) + (f2 * num6);
        this.f73y = (f2 * num5) - (f1 * num6);
        this.f74z = (f3 * num6) - (f4 * num5);
        this.f71w = (f3 * num5) + (f4 * num6);
        return this;
    }

    public float len2() {
        return (((this.f72x * this.f72x) + (this.f73y * this.f73y)) + (this.f74z * this.f74z)) + (this.f71w * this.f71w);
    }

    public Quaternion nor() {
        float len = len2();
        if (len != 0.0f && Math.abs(len - TextTrackStyle.DEFAULT_FONT_SCALE) > NORMALIZATION_TOLERANCE) {
            len = (float) Math.sqrt((double) len);
            this.f71w /= len;
            this.f72x /= len;
            this.f73y /= len;
            this.f74z /= len;
        }
        return this;
    }

    public Quaternion conjugate() {
        this.f72x = -this.f72x;
        this.f73y = -this.f73y;
        this.f74z = -this.f74z;
        return this;
    }

    public Vector3 transform(Vector3 v) {
        tmp2.set(this);
        tmp2.conjugate();
        tmp2.mulLeft(tmp1.set(v.f105x, v.f106y, v.f107z, 0.0f)).mulLeft(this);
        v.f105x = tmp2.f72x;
        v.f106y = tmp2.f73y;
        v.f107z = tmp2.f74z;
        return v;
    }

    public Quaternion mul(Quaternion q) {
        float newY = (((this.f71w * q.f73y) + (this.f73y * q.f71w)) + (this.f74z * q.f72x)) - (this.f72x * q.f74z);
        float newZ = (((this.f71w * q.f74z) + (this.f74z * q.f71w)) + (this.f72x * q.f73y)) - (this.f73y * q.f72x);
        float newW = (((this.f71w * q.f71w) - (this.f72x * q.f72x)) - (this.f73y * q.f73y)) - (this.f74z * q.f74z);
        this.f72x = (((this.f71w * q.f72x) + (this.f72x * q.f71w)) + (this.f73y * q.f74z)) - (this.f74z * q.f73y);
        this.f73y = newY;
        this.f74z = newZ;
        this.f71w = newW;
        return this;
    }

    public Quaternion mulLeft(Quaternion q) {
        float newY = (((q.f71w * this.f73y) + (q.f73y * this.f71w)) + (q.f74z * this.f72x)) - (q.f72x * this.f74z);
        float newZ = (((q.f71w * this.f74z) + (q.f74z * this.f71w)) + (q.f72x * this.f73y)) - (q.f73y * this.f72x);
        float newW = (((q.f71w * this.f71w) - (q.f72x * this.f72x)) - (q.f73y * this.f73y)) - (q.f74z * this.f74z);
        this.f72x = (((q.f71w * this.f72x) + (q.f72x * this.f71w)) + (q.f73y * this.f74z)) - (q.f74z * this.f73y);
        this.f73y = newY;
        this.f74z = newZ;
        this.f71w = newW;
        return this;
    }

    public void toMatrix(float[] matrix) {
        float xx = this.f72x * this.f72x;
        float xy = this.f72x * this.f73y;
        float xz = this.f72x * this.f74z;
        float xw = this.f72x * this.f71w;
        float yy = this.f73y * this.f73y;
        float yz = this.f73y * this.f74z;
        float yw = this.f73y * this.f71w;
        float zz = this.f74z * this.f74z;
        float zw = this.f74z * this.f71w;
        matrix[0] = TextTrackStyle.DEFAULT_FONT_SCALE - ((yy + zz) * 2.0f);
        matrix[4] = (xy - zw) * 2.0f;
        matrix[8] = (xz + yw) * 2.0f;
        matrix[12] = 0.0f;
        matrix[1] = (xy + zw) * 2.0f;
        matrix[5] = TextTrackStyle.DEFAULT_FONT_SCALE - ((xx + zz) * 2.0f);
        matrix[9] = (yz - xw) * 2.0f;
        matrix[13] = 0.0f;
        matrix[2] = (xz - yw) * 2.0f;
        matrix[6] = (yz + xw) * 2.0f;
        matrix[10] = TextTrackStyle.DEFAULT_FONT_SCALE - ((xx + yy) * 2.0f);
        matrix[14] = 0.0f;
        matrix[3] = 0.0f;
        matrix[7] = 0.0f;
        matrix[11] = 0.0f;
        matrix[15] = TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    public Quaternion idt() {
        return set(0.0f, 0.0f, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public Quaternion setFromAxis(Vector3 axis, float angle) {
        return setFromAxis(axis.f105x, axis.f106y, axis.f107z, angle);
    }

    public Quaternion setFromAxis(float x, float y, float z, float angle) {
        float d = Vector3.len(x, y, z);
        if (d == 0.0f) {
            return idt();
        }
        d = TextTrackStyle.DEFAULT_FONT_SCALE / d;
        float l_ang = angle * MathUtils.degreesToRadians;
        float l_sin = (float) Math.sin((double) (l_ang / 2.0f));
        return set((d * x) * l_sin, (d * y) * l_sin, (d * z) * l_sin, (float) Math.cos((double) (l_ang / 2.0f))).nor();
    }

    public Quaternion setFromMatrix(Matrix4 matrix) {
        return setFromAxes(matrix.val[0], matrix.val[4], matrix.val[8], matrix.val[1], matrix.val[5], matrix.val[9], matrix.val[2], matrix.val[6], matrix.val[10]);
    }

    public Quaternion setFromAxes(float xx, float xy, float xz, float yx, float yy, float yz, float zx, float zy, float zz) {
        double w;
        double x;
        double y;
        double z;
        float m00 = xx;
        float m01 = xy;
        float m02 = xz;
        float m10 = yx;
        float m11 = yy;
        float m12 = yz;
        float m20 = zx;
        float m21 = zy;
        float m22 = zz;
        float t = (m00 + m11) + m22;
        double s;
        if (t >= 0.0f) {
            s = Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE + t));
            w = 0.5d * s;
            s = 0.5d / s;
            x = ((double) (m21 - m12)) * s;
            y = ((double) (m02 - m20)) * s;
            z = ((double) (m10 - m01)) * s;
        } else if (m00 > m11 && m00 > m22) {
            r28 = (double) m11;
            r28 = (double) m22;
            s = Math.sqrt(((1.0d + ((double) m00)) - r0) - r0);
            x = s * 0.5d;
            s = 0.5d / s;
            y = ((double) (m10 + m01)) * s;
            z = ((double) (m02 + m20)) * s;
            w = ((double) (m21 - m12)) * s;
        } else if (m11 > m22) {
            r28 = (double) m00;
            r28 = (double) m22;
            s = Math.sqrt(((1.0d + ((double) m11)) - r0) - r0);
            y = s * 0.5d;
            s = 0.5d / s;
            x = ((double) (m10 + m01)) * s;
            z = ((double) (m21 + m12)) * s;
            w = ((double) (m02 - m20)) * s;
        } else {
            r28 = (double) m00;
            r28 = (double) m11;
            s = Math.sqrt(((1.0d + ((double) m22)) - r0) - r0);
            z = s * 0.5d;
            s = 0.5d / s;
            x = ((double) (m02 + m20)) * s;
            y = ((double) (m21 + m12)) * s;
            w = ((double) (m10 - m01)) * s;
        }
        return set((float) x, (float) y, (float) z, (float) w);
    }

    public Quaternion setFromCross(Vector3 v1, Vector3 v2) {
        return setFromAxis((v1.f106y * v2.f107z) - (v1.f107z * v2.f106y), (v1.f107z * v2.f105x) - (v1.f105x * v2.f107z), (v1.f105x * v2.f106y) - (v1.f106y * v2.f105x), ((float) Math.acos((double) MathUtils.clamp(v1.dot(v2), (float) GroundOverlayOptions.NO_DIMENSION, (float) TextTrackStyle.DEFAULT_FONT_SCALE))) * MathUtils.radiansToDegrees);
    }

    public Quaternion setFromCross(float x1, float y1, float z1, float x2, float y2, float z2) {
        return setFromAxis((y1 * z2) - (z1 * y2), (z1 * x2) - (x1 * z2), (x1 * y2) - (y1 * x2), ((float) Math.acos((double) MathUtils.clamp(Vector3.dot(x1, y1, z1, x2, y2, z2), (float) GroundOverlayOptions.NO_DIMENSION, (float) TextTrackStyle.DEFAULT_FONT_SCALE))) * MathUtils.radiansToDegrees);
    }

    public Quaternion slerp(Quaternion end, float alpha) {
        float absDot;
        float dot = dot(end);
        if (dot < 0.0f) {
            absDot = -dot;
        } else {
            absDot = dot;
        }
        float scale0 = TextTrackStyle.DEFAULT_FONT_SCALE - alpha;
        float scale1 = alpha;
        if (((double) (TextTrackStyle.DEFAULT_FONT_SCALE - absDot)) > 0.1d) {
            double angle = Math.acos((double) absDot);
            double invSinTheta = 1.0d / Math.sin(angle);
            scale0 = (float) (Math.sin(((double) (TextTrackStyle.DEFAULT_FONT_SCALE - alpha)) * angle) * invSinTheta);
            scale1 = (float) (Math.sin(((double) alpha) * angle) * invSinTheta);
        }
        if (dot < 0.0f) {
            scale1 = -scale1;
        }
        this.f72x = (this.f72x * scale0) + (end.f72x * scale1);
        this.f73y = (this.f73y * scale0) + (end.f73y * scale1);
        this.f74z = (this.f74z * scale0) + (end.f74z * scale1);
        this.f71w = (this.f71w * scale0) + (end.f71w * scale1);
        return this;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Quaternion)) {
            return false;
        }
        Quaternion comp = (Quaternion) o;
        if (this.f72x == comp.f72x && this.f73y == comp.f73y && this.f74z == comp.f74z && this.f71w == comp.f71w) {
            return true;
        }
        return false;
    }

    public float dot(Quaternion other) {
        return (((this.f72x * other.f72x) + (this.f73y * other.f73y)) + (this.f74z * other.f74z)) + (this.f71w * other.f71w);
    }

    public Quaternion mul(float scalar) {
        this.f72x *= scalar;
        this.f73y *= scalar;
        this.f74z *= scalar;
        this.f71w *= scalar;
        return this;
    }

    public float getAxisAngle(Vector3 axis) {
        if (this.f71w > TextTrackStyle.DEFAULT_FONT_SCALE) {
            nor();
        }
        float angle = (float) (2.0d * Math.acos((double) this.f71w));
        double s = Math.sqrt((double) (TextTrackStyle.DEFAULT_FONT_SCALE - (this.f71w * this.f71w)));
        if (s < 9.999999747378752E-6d) {
            axis.f105x = this.f72x;
            axis.f106y = this.f73y;
            axis.f107z = this.f74z;
        } else {
            axis.f105x = (float) (((double) this.f72x) / s);
            axis.f106y = (float) (((double) this.f73y) / s);
            axis.f107z = (float) (((double) this.f74z) / s);
        }
        return MathUtils.radiansToDegrees * angle;
    }
}
