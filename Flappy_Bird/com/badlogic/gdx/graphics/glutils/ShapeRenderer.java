package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.fitness.FitnessStatusCodes;

public class ShapeRenderer {
    Color color;
    Matrix4 combined;
    ShapeType currType;
    boolean matrixDirty;
    Matrix4 projView;
    ImmediateModeRenderer renderer;
    Matrix4 tmp;
    Matrix4 transform;

    public enum ShapeType {
        Point(0),
        Line(1),
        Filled(4);
        
        private final int glType;

        private ShapeType(int glType) {
            this.glType = glType;
        }

        public int getGlType() {
            return this.glType;
        }
    }

    public ShapeRenderer() {
        this(FitnessStatusCodes.NEEDS_OAUTH_PERMISSIONS);
    }

    public ShapeRenderer(int maxVertices) {
        this.matrixDirty = false;
        this.projView = new Matrix4();
        this.transform = new Matrix4();
        this.combined = new Matrix4();
        this.tmp = new Matrix4();
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.currType = null;
        if (Gdx.graphics.isGL20Available()) {
            this.renderer = new ImmediateModeRenderer20(maxVertices, false, true, 0);
        } else {
            this.renderer = new ImmediateModeRenderer10(maxVertices);
        }
        this.projView.setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.matrixDirty = true;
    }

    public void setColor(Color color) {
        this.color.set(color);
    }

    public void setColor(float r, float g, float b, float a) {
        this.color.set(r, g, b, a);
    }

    public Color getColor() {
        return this.color;
    }

    public void setProjectionMatrix(Matrix4 matrix) {
        this.projView.set(matrix);
        this.matrixDirty = true;
    }

    public Matrix4 getProjectionMatrix() {
        return this.projView;
    }

    public void setTransformMatrix(Matrix4 matrix) {
        this.transform.set(matrix);
        this.matrixDirty = true;
    }

    public Matrix4 getTransformMatrix() {
        return this.transform;
    }

    public void identity() {
        this.transform.idt();
        this.matrixDirty = true;
    }

    public void translate(float x, float y, float z) {
        this.transform.translate(x, y, z);
        this.matrixDirty = true;
    }

    public void rotate(float axisX, float axisY, float axisZ, float angle) {
        this.transform.rotate(axisX, axisY, axisZ, angle);
        this.matrixDirty = true;
    }

    public void scale(float scaleX, float scaleY, float scaleZ) {
        this.transform.scale(scaleX, scaleY, scaleZ);
        this.matrixDirty = true;
    }

    public void begin(ShapeType type) {
        if (this.currType != null) {
            throw new GdxRuntimeException("Call end() before beginning a new shape batch");
        }
        this.currType = type;
        if (this.matrixDirty) {
            this.combined.set(this.projView);
            Matrix4.mul(this.combined.val, this.transform.val);
            this.matrixDirty = false;
        }
        this.renderer.begin(this.combined, this.currType.getGlType());
    }

    public void point(float x, float y, float z) {
        if (this.currType != ShapeType.Point) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Point)");
        }
        checkDirty();
        checkFlush(1);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z);
    }

    public final void line(float x, float y, float z, float x2, float y2, float z2) {
        line(x, y, z, x2, y2, z2, this.color, this.color);
    }

    public final void line(Vector3 v0, Vector3 v1) {
        line(v0.f105x, v0.f106y, v0.f107z, v1.f105x, v1.f106y, v1.f107z, this.color, this.color);
    }

    public final void line(float x, float y, float x2, float y2) {
        line(x, y, 0.0f, x2, y2, 0.0f, this.color, this.color);
    }

    public final void line(Vector2 v0, Vector2 v1) {
        line(v0.f100x, v0.f101y, 0.0f, v1.f100x, v1.f101y, 0.0f, this.color, this.color);
    }

    public final void line(float x, float y, float x2, float y2, Color c1, Color c2) {
        line(x, y, 0.0f, x2, y2, 0.0f, c1, c2);
    }

    public void line(float x, float y, float z, float x2, float y2, float z2, Color c1, Color c2) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        }
        checkDirty();
        checkFlush(2);
        this.renderer.color(c1.f40r, c1.f39g, c1.f38b, c1.f37a);
        this.renderer.vertex(x, y, z);
        this.renderer.color(c2.f40r, c2.f39g, c2.f38b, c2.f37a);
        this.renderer.vertex(x2, y2, z2);
    }

    public void curve(float x1, float y1, float cx1, float cy1, float cx2, float cy2, float x2, float y2, int segments) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        }
        checkDirty();
        checkFlush((segments * 2) + 2);
        float subdiv_step = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) segments);
        float subdiv_step2 = subdiv_step * subdiv_step;
        float subdiv_step3 = (subdiv_step * subdiv_step) * subdiv_step;
        float pre1 = 3.0f * subdiv_step;
        float pre2 = 3.0f * subdiv_step2;
        float pre4 = 6.0f * subdiv_step2;
        float pre5 = 6.0f * subdiv_step3;
        float tmp1x = (x1 - (2.0f * cx1)) + cx2;
        float tmp1y = (y1 - (2.0f * cy1)) + cy2;
        float tmp2x = (((cx1 - cx2) * 3.0f) - x1) + x2;
        float tmp2y = (((cy1 - cy2) * 3.0f) - y1) + y2;
        float fx = x1;
        float fy = y1;
        float dfx = (((cx1 - x1) * pre1) + (tmp1x * pre2)) + (tmp2x * subdiv_step3);
        float dfy = (((cy1 - y1) * pre1) + (tmp1y * pre2)) + (tmp2y * subdiv_step3);
        float ddfx = (tmp1x * pre4) + (tmp2x * pre5);
        float ddfy = (tmp1y * pre4) + (tmp2y * pre5);
        float dddfx = tmp2x * pre5;
        float dddfy = tmp2y * pre5;
        int segments2 = segments;
        while (true) {
            segments = segments2 - 1;
            if (segments2 > 0) {
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(fx, fy, 0.0f);
                fx += dfx;
                fy += dfy;
                dfx += ddfx;
                dfy += ddfy;
                ddfx += dddfx;
                ddfy += dddfy;
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(fx, fy, 0.0f);
                segments2 = segments;
            } else {
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(fx, fy, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                return;
            }
        }
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3) {
        if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(6);
            if (this.currType == ShapeType.Line) {
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                return;
            }
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x1, y1, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x3, y3, 0.0f);
            return;
        }
        throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
    }

    public void triangle(float x1, float y1, float x2, float y2, float x3, float y3, Color col1, Color col2, Color col3) {
        if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(6);
            if (this.currType == ShapeType.Line) {
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                return;
            }
            this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
            this.renderer.vertex(x1, y1, 0.0f);
            this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
            this.renderer.vertex(x3, y3, 0.0f);
            return;
        }
        throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
    }

    public void rect(float x, float y, float width, float height) {
        if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(8);
            if (this.currType == ShapeType.Line) {
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + width, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + width, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + width, y + height, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + width, y + height, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y + height, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y + height, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, 0.0f);
                return;
            }
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x, y, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x, y, 0.0f);
            return;
        }
        throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
    }

    public void rect(float x, float y, float width, float height, Color col1, Color col2, Color col3, Color col4) {
        if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(8);
            if (this.currType == ShapeType.Line) {
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x, y, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x + width, y, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x + width, y, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x + width, y + height, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x + width, y + height, 0.0f);
                this.renderer.color(col4.f40r, col4.f39g, col4.f38b, col4.f37a);
                this.renderer.vertex(x, y + height, 0.0f);
                this.renderer.color(col4.f40r, col4.f39g, col4.f38b, col4.f37a);
                this.renderer.vertex(x, y + height, 0.0f);
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x, y, 0.0f);
                return;
            }
            this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
            this.renderer.vertex(x, y, 0.0f);
            this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
            this.renderer.vertex(x + width, y, 0.0f);
            this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
            this.renderer.vertex(x + width, y + height, 0.0f);
            this.renderer.color(col4.f40r, col4.f39g, col4.f38b, col4.f37a);
            this.renderer.vertex(x, y + height, 0.0f);
            this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
            this.renderer.vertex(x, y, 0.0f);
            return;
        }
        throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
    }

    public void rect(float x, float y, float width, float height, float originX, float originY, float rotation) {
        rect(x, y, width, height, originX, originY, rotation, this.color, this.color, this.color, this.color);
    }

    public void rect(float x, float y, float width, float height, float originX, float originY, float rotation, Color col1, Color col2, Color col3, Color col4) {
        if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(8);
            float r = (float) Math.toRadians((double) rotation);
            float c = (float) Math.cos((double) r);
            float s = (float) Math.sin((double) r);
            float x1 = ((((0.0f - originX) * c) + x) + ((-s) * (0.0f - originY))) + originX;
            float y1 = ((((0.0f - originX) * s) + y) + ((0.0f - originY) * c)) + originY;
            float x2 = ((((width - originX) * c) + x) + ((-s) * (0.0f - originY))) + originX;
            float y2 = ((((width - originX) * s) + y) + ((0.0f - originY) * c)) + originY;
            float x3 = ((((width - originX) * c) + x) + ((-s) * (height - originY))) + originX;
            float y3 = ((((width - originX) * s) + y) + ((height - originY) * c)) + originY;
            float x4 = ((((0.0f - originX) * c) + x) + ((-s) * (height - originY))) + originX;
            float y4 = ((((0.0f - originX) * s) + y) + ((height - originY) * c)) + originY;
            if (this.currType == ShapeType.Line) {
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(col2.f40r, col2.f39g, col2.f38b, col2.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(col3.f40r, col3.f39g, col3.f38b, col3.f37a);
                this.renderer.vertex(x3, y3, 0.0f);
                this.renderer.color(col4.f40r, col4.f39g, col4.f38b, col4.f37a);
                this.renderer.vertex(x4, y4, 0.0f);
                this.renderer.color(col4.f40r, col4.f39g, col4.f38b, col4.f37a);
                this.renderer.vertex(x4, y4, 0.0f);
                this.renderer.color(col1.f40r, col1.f39g, col1.f38b, col1.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                return;
            }
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x1, y1, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x2, y2, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x3, y3, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x4, y4, 0.0f);
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x1, y1, 0.0f);
            return;
        }
        throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
    }

    public void box(float x, float y, float z, float width, float height, float depth) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        }
        checkDirty();
        checkFlush(16);
        depth = -depth;
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x + width, y + height, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y, z + depth);
        this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
        this.renderer.vertex(x, y + height, z + depth);
    }

    public void m0x(float x, float y, float radius) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        }
        line(x - radius, y - radius, x + radius, y + radius);
        line(x - radius, y + radius, x + radius, y - radius);
    }

    public void arc(float x, float y, float radius, float start, float angle) {
        arc(x, y, radius, start, angle, Math.max(1, (int) ((6.0f * ((float) Math.cbrt((double) radius))) * (angle / 360.0f))));
    }

    public void arc(float x, float y, float radius, float start, float angle, int segments) {
        if (segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        } else if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            float temp;
            checkDirty();
            float theta = (6.283185f * (angle / 360.0f)) / ((float) segments);
            float cos = MathUtils.cos(theta);
            float sin = MathUtils.sin(theta);
            float cx = radius * MathUtils.cos(MathUtils.degreesToRadians * start);
            float cy = radius * MathUtils.sin(MathUtils.degreesToRadians * start);
            int i;
            if (this.currType == ShapeType.Line) {
                checkFlush((segments * 2) + 2);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, 0.0f);
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                    temp = cx;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, 0.0f);
            } else {
                checkFlush((segments * 3) + 3);
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x, y, 0.0f);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                    temp = cx;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, 0.0f);
            }
            temp = cx;
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + 0.0f, y + 0.0f, 0.0f);
        } else {
            throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
        }
    }

    public void circle(float x, float y, float radius) {
        circle(x, y, radius, Math.max(1, (int) (6.0f * ((float) Math.cbrt((double) radius)))));
    }

    public void circle(float x, float y, float radius, int segments) {
        if (segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        } else if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            float temp;
            checkDirty();
            float angle = 6.283185f / ((float) segments);
            float cos = MathUtils.cos(angle);
            float sin = MathUtils.sin(angle);
            float cx = radius;
            float cy = 0.0f;
            int i;
            if (this.currType == ShapeType.Line) {
                checkFlush((segments * 2) + 2);
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                    temp = cx;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, 0.0f);
            } else {
                checkFlush((segments * 3) + 3);
                segments--;
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x, y, 0.0f);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                    temp = cx;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, 0.0f);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, 0.0f);
            }
            temp = cx;
            cx = radius;
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + cx, y + 0.0f, 0.0f);
        } else {
            throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
        }
    }

    public void ellipse(float x, float y, float width, float height) {
        ellipse(x, y, width, height, Math.max(1, (int) (12.0f * ((float) Math.cbrt((double) Math.max(width * 0.5f, 0.5f * height))))));
    }

    public void ellipse(float x, float y, float width, float height, int segments) {
        if (segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        } else if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush(segments * 3);
            float angle = 6.283185f / ((float) segments);
            float cx = x + (width / 2.0f);
            float cy = y + (height / 2.0f);
            int i;
            if (this.currType == ShapeType.Line) {
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(((0.5f * width) * MathUtils.cos(((float) i) * angle)) + cx, ((0.5f * height) * MathUtils.sin(((float) i) * angle)) + cy, 0.0f);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(((0.5f * width) * MathUtils.cos(((float) (i + 1)) * angle)) + cx, ((0.5f * height) * MathUtils.sin(((float) (i + 1)) * angle)) + cy, 0.0f);
                }
                return;
            }
            for (i = 0; i < segments; i++) {
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(((0.5f * width) * MathUtils.cos(((float) i) * angle)) + cx, ((0.5f * height) * MathUtils.sin(((float) i) * angle)) + cy, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(cx, cy, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(((0.5f * width) * MathUtils.cos(((float) (i + 1)) * angle)) + cx, ((0.5f * height) * MathUtils.sin(((float) (i + 1)) * angle)) + cy, 0.0f);
            }
        } else {
            throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
        }
    }

    public void cone(float x, float y, float z, float radius, float height) {
        cone(x, y, z, radius, height, Math.max(1, (int) (4.0f * ((float) Math.sqrt((double) radius)))));
    }

    public void cone(float x, float y, float z, float radius, float height, int segments) {
        if (segments <= 0) {
            throw new IllegalArgumentException("segments must be > 0.");
        } else if (this.currType == ShapeType.Filled || this.currType == ShapeType.Line) {
            checkDirty();
            checkFlush((segments * 4) + 2);
            float angle = 6.283185f / ((float) segments);
            float cos = MathUtils.cos(angle);
            float sin = MathUtils.sin(angle);
            float cx = radius;
            float cy = 0.0f;
            int i;
            float temp;
            if (this.currType == ShapeType.Line) {
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x, y, z + height);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                    temp = cx;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, z);
            } else {
                segments--;
                for (i = 0; i < segments; i++) {
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x, y, z);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                    temp = cx;
                    float temp2 = cy;
                    cx = (cos * cx) - (sin * cy);
                    cy = (sin * temp) + (cos * cy);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + temp, y + temp2, z);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x + cx, y + cy, z);
                    this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                    this.renderer.vertex(x, y, z + height);
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x, y, z);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x + cx, y + cy, z);
            }
            cx = radius;
            this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
            this.renderer.vertex(x + cx, y + 0.0f, z);
        } else {
            throw new GdxRuntimeException("Must call begin(ShapeType.Filled) or begin(ShapeType.Line)");
        }
    }

    public void polygon(float[] vertices) {
        polygon(vertices, 0, vertices.length);
    }

    public void polygon(float[] vertices, int offset, int count) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        } else if (count < 6) {
            throw new IllegalArgumentException("Polygons must contain at least 3 points.");
        } else if (count % 2 != 0) {
            throw new IllegalArgumentException("Polygons must have an even number of vertices.");
        } else {
            checkDirty();
            checkFlush(count);
            float firstX = vertices[0];
            float firstY = vertices[1];
            int n = offset + count;
            for (int i = offset; i < n; i += 2) {
                float x2;
                float y2;
                float x1 = vertices[i];
                float y1 = vertices[i + 1];
                if (i + 2 >= count) {
                    x2 = firstX;
                    y2 = firstY;
                } else {
                    x2 = vertices[i + 2];
                    y2 = vertices[i + 3];
                }
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
            }
        }
    }

    public void polyline(float[] vertices) {
        polyline(vertices, 0, vertices.length);
    }

    public void polyline(float[] vertices, int offset, int count) {
        if (this.currType != ShapeType.Line) {
            throw new GdxRuntimeException("Must call begin(ShapeType.Line)");
        } else if (count < 4) {
            throw new IllegalArgumentException("Polylines must contain at least 2 points.");
        } else if (count % 2 != 0) {
            throw new IllegalArgumentException("Polylines must have an even number of vertices.");
        } else {
            checkDirty();
            checkFlush(count);
            int n = (offset + count) - 2;
            for (int i = offset; i < n; i += 2) {
                float x1 = vertices[i];
                float y1 = vertices[i + 1];
                float x2 = vertices[i + 2];
                float y2 = vertices[i + 3];
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x1, y1, 0.0f);
                this.renderer.color(this.color.f40r, this.color.f39g, this.color.f38b, this.color.f37a);
                this.renderer.vertex(x2, y2, 0.0f);
            }
        }
    }

    private void checkDirty() {
        if (this.matrixDirty) {
            ShapeType type = this.currType;
            end();
            begin(type);
        }
    }

    private void checkFlush(int newVertices) {
        if (this.renderer.getMaxVertices() - this.renderer.getNumVertices() < newVertices) {
            ShapeType type = this.currType;
            end();
            begin(type);
        }
    }

    public void end() {
        this.renderer.end();
        this.currType = null;
    }

    public void flush() {
        ShapeType type = this.currType;
        end();
        begin(type);
    }

    public ShapeType getCurrentType() {
        return this.currType;
    }

    public ImmediateModeRenderer getRenderer() {
        return this.renderer;
    }

    public void dispose() {
        this.renderer.dispose();
    }
}
