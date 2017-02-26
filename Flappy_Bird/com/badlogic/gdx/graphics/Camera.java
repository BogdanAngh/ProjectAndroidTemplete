package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Frustum;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.Ray;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;

public abstract class Camera {
    public final Matrix4 combined;
    public final Vector3 direction;
    public float far;
    public final Frustum frustum;
    public final Matrix4 invProjectionView;
    public float near;
    public final Vector3 position;
    public final Matrix4 projection;
    final Ray ray;
    private final Vector3 tmpVec;
    public final Vector3 up;
    public final Matrix4 view;
    public float viewportHeight;
    public float viewportWidth;

    public abstract void update();

    public abstract void update(boolean z);

    public Camera() {
        this.position = new Vector3();
        this.direction = new Vector3(0.0f, 0.0f, GroundOverlayOptions.NO_DIMENSION);
        this.up = new Vector3(0.0f, TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
        this.projection = new Matrix4();
        this.view = new Matrix4();
        this.combined = new Matrix4();
        this.invProjectionView = new Matrix4();
        this.near = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.far = 100.0f;
        this.viewportWidth = 0.0f;
        this.viewportHeight = 0.0f;
        this.frustum = new Frustum();
        this.tmpVec = new Vector3();
        this.ray = new Ray(new Vector3(), new Vector3());
    }

    public void apply(GL10 gl) {
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadMatrixf(this.projection.val, 0);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadMatrixf(this.view.val, 0);
    }

    public void lookAt(float x, float y, float z) {
        this.direction.set(x, y, z).sub(this.position).nor();
        normalizeUp();
    }

    public void lookAt(Vector3 target) {
        this.direction.set(target).sub(this.position).nor();
        normalizeUp();
    }

    public void normalizeUp() {
        this.tmpVec.set(this.direction).crs(this.up).nor();
        this.up.set(this.tmpVec).crs(this.direction).nor();
    }

    public void rotate(float angle, float axisX, float axisY, float axisZ) {
        this.direction.rotate(angle, axisX, axisY, axisZ);
        this.up.rotate(angle, axisX, axisY, axisZ);
    }

    public void rotate(Vector3 axis, float angle) {
        this.direction.rotate(axis, angle);
        this.up.rotate(axis, angle);
    }

    public void rotate(Matrix4 transform) {
        this.direction.rot(transform);
        this.up.rot(transform);
    }

    public void rotate(Quaternion quat) {
        quat.transform(this.direction);
        quat.transform(this.up);
    }

    public void rotateAround(Vector3 point, Vector3 axis, float angle) {
        this.tmpVec.set(point);
        this.tmpVec.sub(this.position);
        translate(this.tmpVec);
        rotate(axis, angle);
        this.tmpVec.rotate(axis, angle);
        translate(-this.tmpVec.f105x, -this.tmpVec.f106y, -this.tmpVec.f107z);
    }

    public void transform(Matrix4 transform) {
        this.position.mul(transform);
        rotate(transform);
    }

    public void translate(float x, float y, float z) {
        this.position.add(x, y, z);
    }

    public void translate(Vector3 vec) {
        this.position.add(vec);
    }

    public void unproject(Vector3 vec, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        float x = vec.f105x;
        float y = ((((float) Gdx.graphics.getHeight()) - vec.f106y) - TextTrackStyle.DEFAULT_FONT_SCALE) - viewportY;
        vec.f105x = ((2.0f * (x - viewportX)) / viewportWidth) - TextTrackStyle.DEFAULT_FONT_SCALE;
        vec.f106y = ((2.0f * y) / viewportHeight) - TextTrackStyle.DEFAULT_FONT_SCALE;
        vec.f107z = (vec.f107z * 2.0f) - TextTrackStyle.DEFAULT_FONT_SCALE;
        vec.prj(this.invProjectionView);
    }

    public void unproject(Vector3 vec) {
        unproject(vec, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    public void project(Vector3 vec) {
        project(vec, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    public void project(Vector3 vec, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        vec.prj(this.combined);
        vec.f105x = (((vec.f105x + TextTrackStyle.DEFAULT_FONT_SCALE) * viewportWidth) / 2.0f) + viewportX;
        vec.f106y = (((vec.f106y + TextTrackStyle.DEFAULT_FONT_SCALE) * viewportHeight) / 2.0f) + viewportY;
        vec.f107z = (vec.f107z + TextTrackStyle.DEFAULT_FONT_SCALE) / 2.0f;
    }

    public Ray getPickRay(float x, float y, float viewportX, float viewportY, float viewportWidth, float viewportHeight) {
        unproject(this.ray.origin.set(x, y, 0.0f), viewportX, viewportY, viewportWidth, viewportHeight);
        unproject(this.ray.direction.set(x, y, TextTrackStyle.DEFAULT_FONT_SCALE), viewportX, viewportY, viewportWidth, viewportHeight);
        this.ray.direction.sub(this.ray.origin).nor();
        return this.ray;
    }

    public Ray getPickRay(float x, float y) {
        return getPickRay(x, y, 0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }
}
