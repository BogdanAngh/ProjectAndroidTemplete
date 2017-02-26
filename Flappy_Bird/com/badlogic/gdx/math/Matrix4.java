package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.io.Serializable;

public class Matrix4 implements Serializable {
    public static final int M00 = 0;
    public static final int M01 = 4;
    public static final int M02 = 8;
    public static final int M03 = 12;
    public static final int M10 = 1;
    public static final int M11 = 5;
    public static final int M12 = 9;
    public static final int M13 = 13;
    public static final int M20 = 2;
    public static final int M21 = 6;
    public static final int M22 = 10;
    public static final int M23 = 14;
    public static final int M30 = 3;
    public static final int M31 = 7;
    public static final int M32 = 11;
    public static final int M33 = 15;
    static final Vector3 l_vex;
    static final Vector3 l_vey;
    static final Vector3 l_vez;
    static Quaternion quat = null;
    static final Vector3 right;
    private static final long serialVersionUID = -2717655254359579617L;
    static final Vector3 tmpForward;
    static final Matrix4 tmpMat;
    static final Vector3 tmpUp;
    static final Vector3 tmpVec;
    public final float[] tmp;
    public final float[] val;

    public static native float det(float[] fArr);

    public static native boolean inv(float[] fArr);

    public static native void mul(float[] fArr, float[] fArr2);

    public static native void mulVec(float[] fArr, float[] fArr2);

    public static native void mulVec(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public static native void prj(float[] fArr, float[] fArr2);

    public static native void prj(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public static native void rot(float[] fArr, float[] fArr2);

    public static native void rot(float[] fArr, float[] fArr2, int i, int i2, int i3);

    public Matrix4() {
        this.tmp = new float[16];
        this.val = new float[16];
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
    }

    public Matrix4(Matrix4 matrix) {
        this.tmp = new float[16];
        this.val = new float[16];
        set(matrix);
    }

    public Matrix4(float[] values) {
        this.tmp = new float[16];
        this.val = new float[16];
        set(values);
    }

    public Matrix4(Quaternion quaternion) {
        this.tmp = new float[16];
        this.val = new float[16];
        set(quaternion);
    }

    public Matrix4(Vector3 position, Quaternion rotation, Vector3 scale) {
        this.tmp = new float[16];
        this.val = new float[16];
        set(position, rotation, scale);
    }

    public Matrix4 set(Matrix4 matrix) {
        return set(matrix.val);
    }

    public Matrix4 set(float[] values) {
        System.arraycopy(values, M00, this.val, M00, this.val.length);
        return this;
    }

    public Matrix4 set(Quaternion quaternion) {
        return set(quaternion.f72x, quaternion.f73y, quaternion.f74z, quaternion.f71w);
    }

    public Matrix4 set(float x, float y, float z, float w) {
        float l_xx = x * x;
        float l_xy = x * y;
        float l_xz = x * z;
        float l_xw = x * w;
        float l_yy = y * y;
        float l_yz = y * z;
        float l_yw = y * w;
        float l_zz = z * z;
        float l_zw = z * w;
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE - (2.0f * (l_yy + l_zz));
        this.val[M01] = 2.0f * (l_xy - l_zw);
        this.val[M02] = 2.0f * (l_xz + l_yw);
        this.val[M03] = 0.0f;
        this.val[M10] = 2.0f * (l_xy + l_zw);
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE - (2.0f * (l_xx + l_zz));
        this.val[M12] = 2.0f * (l_yz - l_xw);
        this.val[M13] = 0.0f;
        this.val[M20] = 2.0f * (l_xz - l_yw);
        this.val[M21] = 2.0f * (l_yz + l_xw);
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE - (2.0f * (l_xx + l_yy));
        this.val[M23] = 0.0f;
        this.val[M30] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M32] = 0.0f;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix4 set(Vector3 position, Quaternion orientation, Vector3 scale) {
        float xs = orientation.f72x * 2.0f;
        float ys = orientation.f73y * 2.0f;
        float zs = orientation.f74z * 2.0f;
        float wx = orientation.f71w * xs;
        float wy = orientation.f71w * ys;
        float wz = orientation.f71w * zs;
        float xx = orientation.f72x * xs;
        float xy = orientation.f72x * ys;
        float xz = orientation.f72x * zs;
        float yy = orientation.f73y * ys;
        float yz = orientation.f73y * zs;
        float zz = orientation.f74z * zs;
        this.val[M00] = scale.f105x * (TextTrackStyle.DEFAULT_FONT_SCALE - (yy + zz));
        this.val[M01] = scale.f105x * (xy - wz);
        this.val[M02] = scale.f105x * (xz + wy);
        this.val[M03] = position.f105x;
        this.val[M10] = scale.f106y * (xy + wz);
        this.val[M11] = scale.f106y * (TextTrackStyle.DEFAULT_FONT_SCALE - (xx + zz));
        this.val[M12] = scale.f106y * (yz - wx);
        this.val[M13] = position.f106y;
        this.val[M20] = scale.f107z * (xz - wy);
        this.val[M21] = scale.f107z * (yz + wx);
        this.val[M22] = scale.f107z * (TextTrackStyle.DEFAULT_FONT_SCALE - (xx + yy));
        this.val[M23] = position.f107z;
        this.val[M30] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M32] = 0.0f;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix4 set(Vector3 xAxis, Vector3 yAxis, Vector3 zAxis, Vector3 pos) {
        this.val[M00] = xAxis.f105x;
        this.val[M01] = xAxis.f106y;
        this.val[M02] = xAxis.f107z;
        this.val[M10] = yAxis.f105x;
        this.val[M11] = yAxis.f106y;
        this.val[M12] = yAxis.f107z;
        this.val[M20] = -zAxis.f105x;
        this.val[M21] = -zAxis.f106y;
        this.val[M22] = -zAxis.f107z;
        this.val[M03] = pos.f105x;
        this.val[M13] = pos.f106y;
        this.val[M23] = pos.f107z;
        this.val[M30] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M32] = 0.0f;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix4 cpy() {
        return new Matrix4(this);
    }

    public Matrix4 trn(Vector3 vector) {
        float[] fArr = this.val;
        fArr[M03] = fArr[M03] + vector.f105x;
        fArr = this.val;
        fArr[M13] = fArr[M13] + vector.f106y;
        fArr = this.val;
        fArr[M23] = fArr[M23] + vector.f107z;
        return this;
    }

    public Matrix4 trn(float x, float y, float z) {
        float[] fArr = this.val;
        fArr[M03] = fArr[M03] + x;
        fArr = this.val;
        fArr[M13] = fArr[M13] + y;
        fArr = this.val;
        fArr[M23] = fArr[M23] + z;
        return this;
    }

    public float[] getValues() {
        return this.val;
    }

    public Matrix4 mul(Matrix4 matrix) {
        mul(this.val, matrix.val);
        return this;
    }

    public Matrix4 tra() {
        this.tmp[M00] = this.val[M00];
        this.tmp[M01] = this.val[M10];
        this.tmp[M02] = this.val[M20];
        this.tmp[M03] = this.val[M30];
        this.tmp[M10] = this.val[M01];
        this.tmp[M11] = this.val[M11];
        this.tmp[M12] = this.val[M21];
        this.tmp[M13] = this.val[M31];
        this.tmp[M20] = this.val[M02];
        this.tmp[M21] = this.val[M12];
        this.tmp[M22] = this.val[M22];
        this.tmp[M23] = this.val[M32];
        this.tmp[M30] = this.val[M03];
        this.tmp[M31] = this.val[M13];
        this.tmp[M32] = this.val[M23];
        this.tmp[M33] = this.val[M33];
        return set(this.tmp);
    }

    public Matrix4 idt() {
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M01] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M03] = 0.0f;
        this.val[M10] = 0.0f;
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M12] = 0.0f;
        this.val[M13] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M21] = 0.0f;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M23] = 0.0f;
        this.val[M30] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M32] = 0.0f;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix4 inv() {
        float l_det = (((((((((((((((((((((((((this.val[M30] * this.val[M21]) * this.val[M12]) * this.val[M03]) - (((this.val[M20] * this.val[M31]) * this.val[M12]) * this.val[M03])) - (((this.val[M30] * this.val[M11]) * this.val[M22]) * this.val[M03])) + (((this.val[M10] * this.val[M31]) * this.val[M22]) * this.val[M03])) + (((this.val[M20] * this.val[M11]) * this.val[M32]) * this.val[M03])) - (((this.val[M10] * this.val[M21]) * this.val[M32]) * this.val[M03])) - (((this.val[M30] * this.val[M21]) * this.val[M02]) * this.val[M13])) + (((this.val[M20] * this.val[M31]) * this.val[M02]) * this.val[M13])) + (((this.val[M30] * this.val[M01]) * this.val[M22]) * this.val[M13])) - (((this.val[M00] * this.val[M31]) * this.val[M22]) * this.val[M13])) - (((this.val[M20] * this.val[M01]) * this.val[M32]) * this.val[M13])) + (((this.val[M00] * this.val[M21]) * this.val[M32]) * this.val[M13])) + (((this.val[M30] * this.val[M11]) * this.val[M02]) * this.val[M23])) - (((this.val[M10] * this.val[M31]) * this.val[M02]) * this.val[M23])) - (((this.val[M30] * this.val[M01]) * this.val[M12]) * this.val[M23])) + (((this.val[M00] * this.val[M31]) * this.val[M12]) * this.val[M23])) + (((this.val[M10] * this.val[M01]) * this.val[M32]) * this.val[M23])) - (((this.val[M00] * this.val[M11]) * this.val[M32]) * this.val[M23])) - (((this.val[M20] * this.val[M11]) * this.val[M02]) * this.val[M33])) + (((this.val[M10] * this.val[M21]) * this.val[M02]) * this.val[M33])) + (((this.val[M20] * this.val[M01]) * this.val[M12]) * this.val[M33])) - (((this.val[M00] * this.val[M21]) * this.val[M12]) * this.val[M33])) - (((this.val[M10] * this.val[M01]) * this.val[M22]) * this.val[M33])) + (((this.val[M00] * this.val[M11]) * this.val[M22]) * this.val[M33]);
        if (l_det == 0.0f) {
            throw new RuntimeException("non-invertible matrix");
        }
        float inv_det = TextTrackStyle.DEFAULT_FONT_SCALE / l_det;
        this.tmp[M00] = ((((((this.val[M12] * this.val[M23]) * this.val[M31]) - ((this.val[M13] * this.val[M22]) * this.val[M31])) + ((this.val[M13] * this.val[M21]) * this.val[M32])) - ((this.val[M11] * this.val[M23]) * this.val[M32])) - ((this.val[M12] * this.val[M21]) * this.val[M33])) + ((this.val[M11] * this.val[M22]) * this.val[M33]);
        this.tmp[M01] = ((((((this.val[M03] * this.val[M22]) * this.val[M31]) - ((this.val[M02] * this.val[M23]) * this.val[M31])) - ((this.val[M03] * this.val[M21]) * this.val[M32])) + ((this.val[M01] * this.val[M23]) * this.val[M32])) + ((this.val[M02] * this.val[M21]) * this.val[M33])) - ((this.val[M01] * this.val[M22]) * this.val[M33]);
        this.tmp[M02] = ((((((this.val[M02] * this.val[M13]) * this.val[M31]) - ((this.val[M03] * this.val[M12]) * this.val[M31])) + ((this.val[M03] * this.val[M11]) * this.val[M32])) - ((this.val[M01] * this.val[M13]) * this.val[M32])) - ((this.val[M02] * this.val[M11]) * this.val[M33])) + ((this.val[M01] * this.val[M12]) * this.val[M33]);
        this.tmp[M03] = ((((((this.val[M03] * this.val[M12]) * this.val[M21]) - ((this.val[M02] * this.val[M13]) * this.val[M21])) - ((this.val[M03] * this.val[M11]) * this.val[M22])) + ((this.val[M01] * this.val[M13]) * this.val[M22])) + ((this.val[M02] * this.val[M11]) * this.val[M23])) - ((this.val[M01] * this.val[M12]) * this.val[M23]);
        this.tmp[M10] = ((((((this.val[M13] * this.val[M22]) * this.val[M30]) - ((this.val[M12] * this.val[M23]) * this.val[M30])) - ((this.val[M13] * this.val[M20]) * this.val[M32])) + ((this.val[M10] * this.val[M23]) * this.val[M32])) + ((this.val[M12] * this.val[M20]) * this.val[M33])) - ((this.val[M10] * this.val[M22]) * this.val[M33]);
        this.tmp[M11] = ((((((this.val[M02] * this.val[M23]) * this.val[M30]) - ((this.val[M03] * this.val[M22]) * this.val[M30])) + ((this.val[M03] * this.val[M20]) * this.val[M32])) - ((this.val[M00] * this.val[M23]) * this.val[M32])) - ((this.val[M02] * this.val[M20]) * this.val[M33])) + ((this.val[M00] * this.val[M22]) * this.val[M33]);
        this.tmp[M12] = ((((((this.val[M03] * this.val[M12]) * this.val[M30]) - ((this.val[M02] * this.val[M13]) * this.val[M30])) - ((this.val[M03] * this.val[M10]) * this.val[M32])) + ((this.val[M00] * this.val[M13]) * this.val[M32])) + ((this.val[M02] * this.val[M10]) * this.val[M33])) - ((this.val[M00] * this.val[M12]) * this.val[M33]);
        this.tmp[M13] = ((((((this.val[M02] * this.val[M13]) * this.val[M20]) - ((this.val[M03] * this.val[M12]) * this.val[M20])) + ((this.val[M03] * this.val[M10]) * this.val[M22])) - ((this.val[M00] * this.val[M13]) * this.val[M22])) - ((this.val[M02] * this.val[M10]) * this.val[M23])) + ((this.val[M00] * this.val[M12]) * this.val[M23]);
        this.tmp[M20] = ((((((this.val[M11] * this.val[M23]) * this.val[M30]) - ((this.val[M13] * this.val[M21]) * this.val[M30])) + ((this.val[M13] * this.val[M20]) * this.val[M31])) - ((this.val[M10] * this.val[M23]) * this.val[M31])) - ((this.val[M11] * this.val[M20]) * this.val[M33])) + ((this.val[M10] * this.val[M21]) * this.val[M33]);
        this.tmp[M21] = ((((((this.val[M03] * this.val[M21]) * this.val[M30]) - ((this.val[M01] * this.val[M23]) * this.val[M30])) - ((this.val[M03] * this.val[M20]) * this.val[M31])) + ((this.val[M00] * this.val[M23]) * this.val[M31])) + ((this.val[M01] * this.val[M20]) * this.val[M33])) - ((this.val[M00] * this.val[M21]) * this.val[M33]);
        this.tmp[M22] = ((((((this.val[M01] * this.val[M13]) * this.val[M30]) - ((this.val[M03] * this.val[M11]) * this.val[M30])) + ((this.val[M03] * this.val[M10]) * this.val[M31])) - ((this.val[M00] * this.val[M13]) * this.val[M31])) - ((this.val[M01] * this.val[M10]) * this.val[M33])) + ((this.val[M00] * this.val[M11]) * this.val[M33]);
        this.tmp[M23] = ((((((this.val[M03] * this.val[M11]) * this.val[M20]) - ((this.val[M01] * this.val[M13]) * this.val[M20])) - ((this.val[M03] * this.val[M10]) * this.val[M21])) + ((this.val[M00] * this.val[M13]) * this.val[M21])) + ((this.val[M01] * this.val[M10]) * this.val[M23])) - ((this.val[M00] * this.val[M11]) * this.val[M23]);
        this.tmp[M30] = ((((((this.val[M12] * this.val[M21]) * this.val[M30]) - ((this.val[M11] * this.val[M22]) * this.val[M30])) - ((this.val[M12] * this.val[M20]) * this.val[M31])) + ((this.val[M10] * this.val[M22]) * this.val[M31])) + ((this.val[M11] * this.val[M20]) * this.val[M32])) - ((this.val[M10] * this.val[M21]) * this.val[M32]);
        this.tmp[M31] = ((((((this.val[M01] * this.val[M22]) * this.val[M30]) - ((this.val[M02] * this.val[M21]) * this.val[M30])) + ((this.val[M02] * this.val[M20]) * this.val[M31])) - ((this.val[M00] * this.val[M22]) * this.val[M31])) - ((this.val[M01] * this.val[M20]) * this.val[M32])) + ((this.val[M00] * this.val[M21]) * this.val[M32]);
        this.tmp[M32] = ((((((this.val[M02] * this.val[M11]) * this.val[M30]) - ((this.val[M01] * this.val[M12]) * this.val[M30])) - ((this.val[M02] * this.val[M10]) * this.val[M31])) + ((this.val[M00] * this.val[M12]) * this.val[M31])) + ((this.val[M01] * this.val[M10]) * this.val[M32])) - ((this.val[M00] * this.val[M11]) * this.val[M32]);
        this.tmp[M33] = ((((((this.val[M01] * this.val[M12]) * this.val[M20]) - ((this.val[M02] * this.val[M11]) * this.val[M20])) + ((this.val[M02] * this.val[M10]) * this.val[M21])) - ((this.val[M00] * this.val[M12]) * this.val[M21])) - ((this.val[M01] * this.val[M10]) * this.val[M22])) + ((this.val[M00] * this.val[M11]) * this.val[M22]);
        this.val[M00] = this.tmp[M00] * inv_det;
        this.val[M01] = this.tmp[M01] * inv_det;
        this.val[M02] = this.tmp[M02] * inv_det;
        this.val[M03] = this.tmp[M03] * inv_det;
        this.val[M10] = this.tmp[M10] * inv_det;
        this.val[M11] = this.tmp[M11] * inv_det;
        this.val[M12] = this.tmp[M12] * inv_det;
        this.val[M13] = this.tmp[M13] * inv_det;
        this.val[M20] = this.tmp[M20] * inv_det;
        this.val[M21] = this.tmp[M21] * inv_det;
        this.val[M22] = this.tmp[M22] * inv_det;
        this.val[M23] = this.tmp[M23] * inv_det;
        this.val[M30] = this.tmp[M30] * inv_det;
        this.val[M31] = this.tmp[M31] * inv_det;
        this.val[M32] = this.tmp[M32] * inv_det;
        this.val[M33] = this.tmp[M33] * inv_det;
        return this;
    }

    public float det() {
        return (((((((((((((((((((((((((this.val[M30] * this.val[M21]) * this.val[M12]) * this.val[M03]) - (((this.val[M20] * this.val[M31]) * this.val[M12]) * this.val[M03])) - (((this.val[M30] * this.val[M11]) * this.val[M22]) * this.val[M03])) + (((this.val[M10] * this.val[M31]) * this.val[M22]) * this.val[M03])) + (((this.val[M20] * this.val[M11]) * this.val[M32]) * this.val[M03])) - (((this.val[M10] * this.val[M21]) * this.val[M32]) * this.val[M03])) - (((this.val[M30] * this.val[M21]) * this.val[M02]) * this.val[M13])) + (((this.val[M20] * this.val[M31]) * this.val[M02]) * this.val[M13])) + (((this.val[M30] * this.val[M01]) * this.val[M22]) * this.val[M13])) - (((this.val[M00] * this.val[M31]) * this.val[M22]) * this.val[M13])) - (((this.val[M20] * this.val[M01]) * this.val[M32]) * this.val[M13])) + (((this.val[M00] * this.val[M21]) * this.val[M32]) * this.val[M13])) + (((this.val[M30] * this.val[M11]) * this.val[M02]) * this.val[M23])) - (((this.val[M10] * this.val[M31]) * this.val[M02]) * this.val[M23])) - (((this.val[M30] * this.val[M01]) * this.val[M12]) * this.val[M23])) + (((this.val[M00] * this.val[M31]) * this.val[M12]) * this.val[M23])) + (((this.val[M10] * this.val[M01]) * this.val[M32]) * this.val[M23])) - (((this.val[M00] * this.val[M11]) * this.val[M32]) * this.val[M23])) - (((this.val[M20] * this.val[M11]) * this.val[M02]) * this.val[M33])) + (((this.val[M10] * this.val[M21]) * this.val[M02]) * this.val[M33])) + (((this.val[M20] * this.val[M01]) * this.val[M12]) * this.val[M33])) - (((this.val[M00] * this.val[M21]) * this.val[M12]) * this.val[M33])) - (((this.val[M10] * this.val[M01]) * this.val[M22]) * this.val[M33])) + (((this.val[M00] * this.val[M11]) * this.val[M22]) * this.val[M33]);
    }

    public Matrix4 setToProjection(float near, float far, float fov, float aspectRatio) {
        idt();
        float l_fd = (float) (1.0d / Math.tan((((double) fov) * 0.017453292519943295d) / 2.0d));
        float l_a1 = (far + near) / (near - far);
        float l_a2 = ((2.0f * far) * near) / (near - far);
        this.val[M00] = l_fd / aspectRatio;
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M30] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = l_fd;
        this.val[M21] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = l_a1;
        this.val[M32] = GroundOverlayOptions.NO_DIMENSION;
        this.val[M03] = 0.0f;
        this.val[M13] = 0.0f;
        this.val[M23] = l_a2;
        this.val[M33] = 0.0f;
        return this;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height) {
        setToOrtho(x, x + width, y, y + height, 0.0f, TextTrackStyle.DEFAULT_FONT_SCALE);
        return this;
    }

    public Matrix4 setToOrtho2D(float x, float y, float width, float height, float near, float far) {
        setToOrtho(x, x + width, y, y + height, near, far);
        return this;
    }

    public Matrix4 setToOrtho(float left, float right, float bottom, float top, float near, float far) {
        idt();
        float y_orth = 2.0f / (top - bottom);
        float z_orth = -2.0f / (far - near);
        float tx = (-(right + left)) / (right - left);
        float ty = (-(top + bottom)) / (top - bottom);
        float tz = (-(far + near)) / (far - near);
        this.val[M00] = 2.0f / (right - left);
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M30] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = y_orth;
        this.val[M21] = 0.0f;
        this.val[M31] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = z_orth;
        this.val[M32] = 0.0f;
        this.val[M03] = tx;
        this.val[M13] = ty;
        this.val[M23] = tz;
        this.val[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix4 setTranslation(Vector3 vector) {
        this.val[M03] = vector.f105x;
        this.val[M13] = vector.f106y;
        this.val[M23] = vector.f107z;
        return this;
    }

    public Matrix4 setToTranslation(Vector3 vector) {
        idt();
        this.val[M03] = vector.f105x;
        this.val[M13] = vector.f106y;
        this.val[M23] = vector.f107z;
        return this;
    }

    public Matrix4 setToTranslation(float x, float y, float z) {
        idt();
        this.val[M03] = x;
        this.val[M13] = y;
        this.val[M23] = z;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(Vector3 translation, Vector3 scaling) {
        idt();
        this.val[M03] = translation.f105x;
        this.val[M13] = translation.f106y;
        this.val[M23] = translation.f107z;
        this.val[M00] = scaling.f105x;
        this.val[M11] = scaling.f106y;
        this.val[M22] = scaling.f107z;
        return this;
    }

    public Matrix4 setToTranslationAndScaling(float translationX, float translationY, float translationZ, float scalingX, float scalingY, float scalingZ) {
        idt();
        this.val[M03] = translationX;
        this.val[M13] = translationY;
        this.val[M23] = translationZ;
        this.val[M00] = scalingX;
        this.val[M11] = scalingY;
        this.val[M22] = scalingZ;
        return this;
    }

    static {
        quat = new Quaternion();
        l_vez = new Vector3();
        l_vex = new Vector3();
        l_vey = new Vector3();
        tmpVec = new Vector3();
        tmpMat = new Matrix4();
        right = new Vector3();
        tmpForward = new Vector3();
        tmpUp = new Vector3();
    }

    public Matrix4 setToRotation(Vector3 axis, float degrees) {
        if (degrees != 0.0f) {
            return set(quat.set(axis, degrees));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotation(float axisX, float axisY, float axisZ, float degrees) {
        if (degrees != 0.0f) {
            return set(quat.setFromAxis(axisX, axisY, axisZ, degrees));
        }
        idt();
        return this;
    }

    public Matrix4 setToRotation(Vector3 v1, Vector3 v2) {
        return set(quat.setFromCross(v1, v2));
    }

    public Matrix4 setToRotation(float x1, float y1, float z1, float x2, float y2, float z2) {
        return set(quat.setFromCross(x1, y1, z1, x2, y2, z2));
    }

    public Matrix4 setFromEulerAngles(float yaw, float pitch, float roll) {
        quat.setEulerAngles(yaw, pitch, roll);
        return set(quat);
    }

    public Matrix4 setToScaling(Vector3 vector) {
        idt();
        this.val[M00] = vector.f105x;
        this.val[M11] = vector.f106y;
        this.val[M22] = vector.f107z;
        return this;
    }

    public Matrix4 setToScaling(float x, float y, float z) {
        idt();
        this.val[M00] = x;
        this.val[M11] = y;
        this.val[M22] = z;
        return this;
    }

    public Matrix4 setToLookAt(Vector3 direction, Vector3 up) {
        l_vez.set(direction).nor();
        l_vex.set(direction).nor();
        l_vex.crs(up).nor();
        l_vey.set(l_vex).crs(l_vez).nor();
        idt();
        this.val[M00] = l_vex.f105x;
        this.val[M01] = l_vex.f106y;
        this.val[M02] = l_vex.f107z;
        this.val[M10] = l_vey.f105x;
        this.val[M11] = l_vey.f106y;
        this.val[M12] = l_vey.f107z;
        this.val[M20] = -l_vez.f105x;
        this.val[M21] = -l_vez.f106y;
        this.val[M22] = -l_vez.f107z;
        return this;
    }

    public Matrix4 setToLookAt(Vector3 position, Vector3 target, Vector3 up) {
        tmpVec.set(target).sub(position);
        setToLookAt(tmpVec, up);
        mul(tmpMat.setToTranslation(position.tmp().scl((float) GroundOverlayOptions.NO_DIMENSION)));
        return this;
    }

    public Matrix4 setToWorld(Vector3 position, Vector3 forward, Vector3 up) {
        tmpForward.set(forward).nor();
        right.set(tmpForward).crs(up).nor();
        tmpUp.set(right).crs(tmpForward).nor();
        set(right, tmpUp, tmpForward, position);
        return this;
    }

    public String toString() {
        return "[" + this.val[M00] + "|" + this.val[M01] + "|" + this.val[M02] + "|" + this.val[M03] + "]\n" + "[" + this.val[M10] + "|" + this.val[M11] + "|" + this.val[M12] + "|" + this.val[M13] + "]\n" + "[" + this.val[M20] + "|" + this.val[M21] + "|" + this.val[M22] + "|" + this.val[M23] + "]\n" + "[" + this.val[M30] + "|" + this.val[M31] + "|" + this.val[M32] + "|" + this.val[M33] + "]\n";
    }

    public Matrix4 lerp(Matrix4 matrix, float alpha) {
        for (int i = M00; i < 16; i += M10) {
            this.val[i] = (this.val[i] * (TextTrackStyle.DEFAULT_FONT_SCALE - alpha)) + (matrix.val[i] * alpha);
        }
        return this;
    }

    public Matrix4 set(Matrix3 mat) {
        this.val[M00] = mat.val[M00];
        this.val[M10] = mat.val[M10];
        this.val[M20] = mat.val[M20];
        this.val[M30] = 0.0f;
        this.val[M01] = mat.val[M30];
        this.val[M11] = mat.val[M01];
        this.val[M21] = mat.val[M11];
        this.val[M31] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M32] = 0.0f;
        this.val[M03] = mat.val[M21];
        this.val[M13] = mat.val[M31];
        this.val[M23] = 0.0f;
        this.val[M33] = mat.val[M02];
        return this;
    }

    public Matrix4 scl(Vector3 scale) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * scale.f105x;
        fArr = this.val;
        fArr[M11] = fArr[M11] * scale.f106y;
        fArr = this.val;
        fArr[M22] = fArr[M22] * scale.f107z;
        return this;
    }

    public Matrix4 scl(float x, float y, float z) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * x;
        fArr = this.val;
        fArr[M11] = fArr[M11] * y;
        fArr = this.val;
        fArr[M22] = fArr[M22] * z;
        return this;
    }

    public Matrix4 scl(float scale) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * scale;
        fArr = this.val;
        fArr[M11] = fArr[M11] * scale;
        fArr = this.val;
        fArr[M22] = fArr[M22] * scale;
        return this;
    }

    public Vector3 getTranslation(Vector3 position) {
        position.f105x = this.val[M03];
        position.f106y = this.val[M13];
        position.f107z = this.val[M23];
        return position;
    }

    public Quaternion getRotation(Quaternion rotation) {
        return rotation.setFromMatrix(this);
    }

    public Vector3 getScale(Vector3 scale) {
        scale.f105x = (float) Math.sqrt((double) (((this.val[M00] * this.val[M00]) + (this.val[M01] * this.val[M01])) + (this.val[M02] * this.val[M02])));
        scale.f106y = (float) Math.sqrt((double) (((this.val[M10] * this.val[M10]) + (this.val[M11] * this.val[M11])) + (this.val[M12] * this.val[M12])));
        scale.f107z = (float) Math.sqrt((double) (((this.val[M20] * this.val[M20]) + (this.val[M21] * this.val[M21])) + (this.val[M22] * this.val[M22])));
        return scale;
    }

    public Matrix4 toNormalMatrix() {
        this.val[M03] = 0.0f;
        this.val[M13] = 0.0f;
        this.val[M23] = 0.0f;
        return inv().tra();
    }

    public Matrix4 translate(Vector3 translation) {
        return translate(translation.f105x, translation.f106y, translation.f107z);
    }

    public Matrix4 translate(float x, float y, float z) {
        this.tmp[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M01] = 0.0f;
        this.tmp[M02] = 0.0f;
        this.tmp[M03] = x;
        this.tmp[M10] = 0.0f;
        this.tmp[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M12] = 0.0f;
        this.tmp[M13] = y;
        this.tmp[M20] = 0.0f;
        this.tmp[M21] = 0.0f;
        this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M23] = z;
        this.tmp[M30] = 0.0f;
        this.tmp[M31] = 0.0f;
        this.tmp[M32] = 0.0f;
        this.tmp[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }

    public Matrix4 rotate(Vector3 axis, float degrees) {
        if (degrees == 0.0f) {
            return this;
        }
        quat.set(axis, degrees);
        return rotate(quat);
    }

    public Matrix4 rotate(float axisX, float axisY, float axisZ, float degrees) {
        if (degrees == 0.0f) {
            return this;
        }
        quat.setFromAxis(axisX, axisY, axisZ, degrees);
        return rotate(quat);
    }

    public Matrix4 rotate(Quaternion rotation) {
        rotation.toMatrix(this.tmp);
        mul(this.val, this.tmp);
        return this;
    }

    public Matrix4 rotate(Vector3 v1, Vector3 v2) {
        return rotate(quat.setFromCross(v1, v2));
    }

    public Matrix4 scale(float scaleX, float scaleY, float scaleZ) {
        this.tmp[M00] = scaleX;
        this.tmp[M01] = 0.0f;
        this.tmp[M02] = 0.0f;
        this.tmp[M03] = 0.0f;
        this.tmp[M10] = 0.0f;
        this.tmp[M11] = scaleY;
        this.tmp[M12] = 0.0f;
        this.tmp[M13] = 0.0f;
        this.tmp[M20] = 0.0f;
        this.tmp[M21] = 0.0f;
        this.tmp[M22] = scaleZ;
        this.tmp[M23] = 0.0f;
        this.tmp[M30] = 0.0f;
        this.tmp[M31] = 0.0f;
        this.tmp[M32] = 0.0f;
        this.tmp[M33] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }
}
