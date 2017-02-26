package com.badlogic.gdx.math;

import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.Serializable;

public class Matrix3 implements Serializable {
    private static final float DEGREE_TO_RAD = 0.017453292f;
    public static final int M00 = 0;
    public static final int M01 = 3;
    public static final int M02 = 6;
    public static final int M10 = 1;
    public static final int M11 = 4;
    public static final int M12 = 7;
    public static final int M20 = 2;
    public static final int M21 = 5;
    public static final int M22 = 8;
    private static final long serialVersionUID = 7907569533774959788L;
    private float[] tmp;
    public float[] val;

    public Matrix3() {
        this.val = new float[9];
        this.tmp = new float[9];
        idt();
    }

    public Matrix3(Matrix3 matrix) {
        this.val = new float[9];
        this.tmp = new float[9];
        set(matrix);
    }

    public Matrix3 idt() {
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M21] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix3 mul(Matrix3 m) {
        float v01 = ((this.val[M00] * m.val[M01]) + (this.val[M01] * m.val[M11])) + (this.val[M02] * m.val[M21]);
        float v02 = ((this.val[M00] * m.val[M02]) + (this.val[M01] * m.val[M12])) + (this.val[M02] * m.val[M22]);
        float v10 = ((this.val[M10] * m.val[M00]) + (this.val[M11] * m.val[M10])) + (this.val[M12] * m.val[M20]);
        float v11 = ((this.val[M10] * m.val[M01]) + (this.val[M11] * m.val[M11])) + (this.val[M12] * m.val[M21]);
        float v12 = ((this.val[M10] * m.val[M02]) + (this.val[M11] * m.val[M12])) + (this.val[M12] * m.val[M22]);
        float v20 = ((this.val[M20] * m.val[M00]) + (this.val[M21] * m.val[M10])) + (this.val[M22] * m.val[M20]);
        float v21 = ((this.val[M20] * m.val[M01]) + (this.val[M21] * m.val[M11])) + (this.val[M22] * m.val[M21]);
        float v22 = ((this.val[M20] * m.val[M02]) + (this.val[M21] * m.val[M12])) + (this.val[M22] * m.val[M22]);
        this.val[M00] = ((this.val[M00] * m.val[M00]) + (this.val[M01] * m.val[M10])) + (this.val[M02] * m.val[M20]);
        this.val[M10] = v10;
        this.val[M20] = v20;
        this.val[M01] = v01;
        this.val[M11] = v11;
        this.val[M21] = v21;
        this.val[M02] = v02;
        this.val[M12] = v12;
        this.val[M22] = v22;
        return this;
    }

    public Matrix3 setToRotation(float degrees) {
        float angle = DEGREE_TO_RAD * degrees;
        float cos = (float) Math.cos((double) angle);
        float sin = (float) Math.sin((double) angle);
        this.val[M00] = cos;
        this.val[M10] = sin;
        this.val[M20] = 0.0f;
        this.val[M01] = -sin;
        this.val[M11] = cos;
        this.val[M21] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix3 setToTranslation(float x, float y) {
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M21] = 0.0f;
        this.val[M02] = x;
        this.val[M12] = y;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix3 setToTranslation(Vector2 translation) {
        this.val[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.val[M21] = 0.0f;
        this.val[M02] = translation.f100x;
        this.val[M12] = translation.f101y;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public Matrix3 setToScaling(float scaleX, float scaleY) {
        this.val[M00] = scaleX;
        this.val[M10] = 0.0f;
        this.val[M20] = 0.0f;
        this.val[M01] = 0.0f;
        this.val[M11] = scaleY;
        this.val[M21] = 0.0f;
        this.val[M02] = 0.0f;
        this.val[M12] = 0.0f;
        this.val[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        return this;
    }

    public String toString() {
        return "[" + this.val[M00] + "|" + this.val[M01] + "|" + this.val[M02] + "]\n" + "[" + this.val[M10] + "|" + this.val[M11] + "|" + this.val[M12] + "]\n" + "[" + this.val[M20] + "|" + this.val[M21] + "|" + this.val[M22] + "]";
    }

    public float det() {
        return ((((((this.val[M00] * this.val[M11]) * this.val[M22]) + ((this.val[M01] * this.val[M12]) * this.val[M20])) + ((this.val[M02] * this.val[M10]) * this.val[M21])) - ((this.val[M00] * this.val[M12]) * this.val[M21])) - ((this.val[M01] * this.val[M10]) * this.val[M22])) - ((this.val[M02] * this.val[M11]) * this.val[M20]);
    }

    public Matrix3 inv() {
        float det = det();
        if (det == 0.0f) {
            throw new GdxRuntimeException("Can't invert a singular matrix");
        }
        float inv_det = TextTrackStyle.DEFAULT_FONT_SCALE / det;
        this.tmp[M00] = (this.val[M11] * this.val[M22]) - (this.val[M21] * this.val[M12]);
        this.tmp[M10] = (this.val[M20] * this.val[M12]) - (this.val[M10] * this.val[M22]);
        this.tmp[M20] = (this.val[M10] * this.val[M21]) - (this.val[M20] * this.val[M11]);
        this.tmp[M01] = (this.val[M21] * this.val[M02]) - (this.val[M01] * this.val[M22]);
        this.tmp[M11] = (this.val[M00] * this.val[M22]) - (this.val[M20] * this.val[M02]);
        this.tmp[M21] = (this.val[M20] * this.val[M01]) - (this.val[M00] * this.val[M21]);
        this.tmp[M02] = (this.val[M01] * this.val[M12]) - (this.val[M11] * this.val[M02]);
        this.tmp[M12] = (this.val[M10] * this.val[M02]) - (this.val[M00] * this.val[M12]);
        this.tmp[M22] = (this.val[M00] * this.val[M11]) - (this.val[M10] * this.val[M01]);
        this.val[M00] = this.tmp[M00] * inv_det;
        this.val[M10] = this.tmp[M10] * inv_det;
        this.val[M20] = this.tmp[M20] * inv_det;
        this.val[M01] = this.tmp[M01] * inv_det;
        this.val[M11] = this.tmp[M11] * inv_det;
        this.val[M21] = this.tmp[M21] * inv_det;
        this.val[M02] = this.tmp[M02] * inv_det;
        this.val[M12] = this.tmp[M12] * inv_det;
        this.val[M22] = this.tmp[M22] * inv_det;
        return this;
    }

    public Matrix3 set(Matrix3 mat) {
        System.arraycopy(mat.val, M00, this.val, M00, this.val.length);
        return this;
    }

    public Matrix3 set(Matrix4 mat) {
        this.val[M00] = mat.val[M00];
        this.val[M10] = mat.val[M10];
        this.val[M20] = mat.val[M20];
        this.val[M01] = mat.val[M11];
        this.val[M11] = mat.val[M21];
        this.val[M21] = mat.val[M02];
        this.val[M02] = mat.val[M22];
        this.val[M12] = mat.val[9];
        this.val[M22] = mat.val[10];
        return this;
    }

    public Matrix3 trn(Vector2 vector) {
        float[] fArr = this.val;
        fArr[M02] = fArr[M02] + vector.f100x;
        fArr = this.val;
        fArr[M12] = fArr[M12] + vector.f101y;
        return this;
    }

    public Matrix3 trn(float x, float y) {
        float[] fArr = this.val;
        fArr[M02] = fArr[M02] + x;
        fArr = this.val;
        fArr[M12] = fArr[M12] + y;
        return this;
    }

    public Matrix3 trn(Vector3 vector) {
        float[] fArr = this.val;
        fArr[M02] = fArr[M02] + vector.f105x;
        fArr = this.val;
        fArr[M12] = fArr[M12] + vector.f106y;
        return this;
    }

    public Matrix3 translate(float x, float y) {
        this.tmp[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M10] = 0.0f;
        this.tmp[M20] = 0.0f;
        this.tmp[M01] = 0.0f;
        this.tmp[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M21] = 0.0f;
        this.tmp[M02] = x;
        this.tmp[M12] = y;
        this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }

    public Matrix3 translate(Vector2 translation) {
        this.tmp[M00] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M10] = 0.0f;
        this.tmp[M20] = 0.0f;
        this.tmp[M01] = 0.0f;
        this.tmp[M11] = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.tmp[M21] = 0.0f;
        this.tmp[M02] = translation.f100x;
        this.tmp[M12] = translation.f101y;
        this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }

    public Matrix3 rotate(float degrees) {
        if (degrees != 0.0f) {
            degrees *= DEGREE_TO_RAD;
            float cos = (float) Math.cos((double) degrees);
            float sin = (float) Math.sin((double) degrees);
            this.tmp[M00] = cos;
            this.tmp[M10] = sin;
            this.tmp[M20] = 0.0f;
            this.tmp[M01] = -sin;
            this.tmp[M11] = cos;
            this.tmp[M21] = 0.0f;
            this.tmp[M02] = 0.0f;
            this.tmp[M12] = 0.0f;
            this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
            mul(this.val, this.tmp);
        }
        return this;
    }

    public Matrix3 scale(float scaleX, float scaleY) {
        this.tmp[M00] = scaleX;
        this.tmp[M10] = 0.0f;
        this.tmp[M20] = 0.0f;
        this.tmp[M01] = 0.0f;
        this.tmp[M11] = scaleY;
        this.tmp[M21] = 0.0f;
        this.tmp[M02] = 0.0f;
        this.tmp[M12] = 0.0f;
        this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }

    public Matrix3 scale(Vector2 scale) {
        this.tmp[M00] = scale.f100x;
        this.tmp[M10] = 0.0f;
        this.tmp[M20] = 0.0f;
        this.tmp[M01] = 0.0f;
        this.tmp[M11] = scale.f101y;
        this.tmp[M21] = 0.0f;
        this.tmp[M02] = 0.0f;
        this.tmp[M12] = 0.0f;
        this.tmp[M22] = TextTrackStyle.DEFAULT_FONT_SCALE;
        mul(this.val, this.tmp);
        return this;
    }

    public float[] getValues() {
        return this.val;
    }

    public Matrix3 scl(float scale) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * scale;
        fArr = this.val;
        fArr[M11] = fArr[M11] * scale;
        return this;
    }

    public Matrix3 scl(Vector2 scale) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * scale.f100x;
        fArr = this.val;
        fArr[M11] = fArr[M11] * scale.f101y;
        return this;
    }

    public Matrix3 scl(Vector3 scale) {
        float[] fArr = this.val;
        fArr[M00] = fArr[M00] * scale.f105x;
        fArr = this.val;
        fArr[M11] = fArr[M11] * scale.f106y;
        return this;
    }

    public Matrix3 transpose() {
        float v01 = this.val[M10];
        float v02 = this.val[M20];
        float v10 = this.val[M01];
        float v12 = this.val[M21];
        float v20 = this.val[M02];
        float v21 = this.val[M12];
        this.val[M01] = v01;
        this.val[M02] = v02;
        this.val[M10] = v10;
        this.val[M12] = v12;
        this.val[M20] = v20;
        this.val[M21] = v21;
        return this;
    }

    private static void mul(float[] mata, float[] matb) {
        float v01 = ((mata[M00] * matb[M01]) + (mata[M01] * matb[M11])) + (mata[M02] * matb[M21]);
        float v02 = ((mata[M00] * matb[M02]) + (mata[M01] * matb[M12])) + (mata[M02] * matb[M22]);
        float v10 = ((mata[M10] * matb[M00]) + (mata[M11] * matb[M10])) + (mata[M12] * matb[M20]);
        float v11 = ((mata[M10] * matb[M01]) + (mata[M11] * matb[M11])) + (mata[M12] * matb[M21]);
        float v12 = ((mata[M10] * matb[M02]) + (mata[M11] * matb[M12])) + (mata[M12] * matb[M22]);
        float v20 = ((mata[M20] * matb[M00]) + (mata[M21] * matb[M10])) + (mata[M22] * matb[M20]);
        float v21 = ((mata[M20] * matb[M01]) + (mata[M21] * matb[M11])) + (mata[M22] * matb[M21]);
        float v22 = ((mata[M20] * matb[M02]) + (mata[M21] * matb[M12])) + (mata[M22] * matb[M22]);
        mata[M00] = ((mata[M00] * matb[M00]) + (mata[M01] * matb[M10])) + (mata[M02] * matb[M20]);
        mata[M10] = v10;
        mata[M20] = v20;
        mata[M01] = v01;
        mata[M11] = v11;
        mata[M21] = v21;
        mata[M02] = v02;
        mata[M12] = v12;
        mata[M22] = v22;
    }
}
