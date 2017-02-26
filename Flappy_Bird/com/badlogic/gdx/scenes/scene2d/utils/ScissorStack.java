package com.badlogic.gdx.scenes.scene2d.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.google.android.gms.cast.TextTrackStyle;

public class ScissorStack {
    private static Array<Rectangle> scissors;
    static Vector3 tmp;
    static final Rectangle viewport;

    static {
        scissors = new Array();
        tmp = new Vector3();
        viewport = new Rectangle();
    }

    public static boolean pushScissors(Rectangle scissor) {
        fix(scissor);
        if (scissors.size != 0) {
            Rectangle parent = (Rectangle) scissors.get(scissors.size - 1);
            float minX = Math.max(parent.f75x, scissor.f75x);
            float maxX = Math.min(parent.f75x + parent.width, scissor.f75x + scissor.width);
            if (maxX - minX < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return false;
            }
            float minY = Math.max(parent.f76y, scissor.f76y);
            float maxY = Math.min(parent.f76y + parent.height, scissor.f76y + scissor.height);
            if (maxY - minY < TextTrackStyle.DEFAULT_FONT_SCALE) {
                return false;
            }
            scissor.f75x = minX;
            scissor.f76y = minY;
            scissor.width = maxX - minX;
            scissor.height = Math.max(TextTrackStyle.DEFAULT_FONT_SCALE, maxY - minY);
        } else if (scissor.width < TextTrackStyle.DEFAULT_FONT_SCALE || scissor.height < TextTrackStyle.DEFAULT_FONT_SCALE) {
            return false;
        } else {
            Gdx.gl.glEnable(GL20.GL_SCISSOR_TEST);
        }
        scissors.add(scissor);
        Gdx.gl.glScissor((int) scissor.f75x, (int) scissor.f76y, (int) scissor.width, (int) scissor.height);
        return true;
    }

    public static Rectangle popScissors() {
        Rectangle old = (Rectangle) scissors.pop();
        if (scissors.size == 0) {
            Gdx.gl.glDisable(GL20.GL_SCISSOR_TEST);
        } else {
            Rectangle scissor = (Rectangle) scissors.peek();
            Gdx.gl.glScissor((int) scissor.f75x, (int) scissor.f76y, (int) scissor.width, (int) scissor.height);
        }
        return old;
    }

    private static void fix(Rectangle rect) {
        rect.f75x = (float) Math.round(rect.f75x);
        rect.f76y = (float) Math.round(rect.f76y);
        rect.width = (float) Math.round(rect.width);
        rect.height = (float) Math.round(rect.height);
        if (rect.width < 0.0f) {
            rect.width = -rect.width;
            rect.f75x -= rect.width;
        }
        if (rect.height < 0.0f) {
            rect.height = -rect.height;
            rect.f76y -= rect.height;
        }
    }

    public static void calculateScissors(Camera camera, float viewportX, float viewportY, float viewportWidth, float viewportHeight, Matrix4 batchTransform, Rectangle area, Rectangle scissor) {
        tmp.set(area.f75x, area.f76y, 0.0f);
        tmp.mul(batchTransform);
        camera.project(tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        scissor.f75x = tmp.f105x;
        scissor.f76y = tmp.f106y;
        tmp.set(area.f75x + area.width, area.f76y + area.height, 0.0f);
        tmp.mul(batchTransform);
        camera.project(tmp, viewportX, viewportY, viewportWidth, viewportHeight);
        scissor.width = tmp.f105x - scissor.f75x;
        scissor.height = tmp.f106y - scissor.f76y;
    }

    public static Rectangle getViewport() {
        if (scissors.size == 0) {
            viewport.set(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
            return viewport;
        }
        viewport.set((Rectangle) scissors.peek());
        return viewport;
    }

    public static void toWindowCoordinates(Camera camera, Matrix4 transformMatrix, Vector2 point) {
        tmp.set(point.f100x, point.f101y, 0.0f);
        tmp.mul(transformMatrix);
        camera.project(tmp);
        tmp.f106y = ((float) Gdx.graphics.getHeight()) - tmp.f106y;
        point.f100x = tmp.f105x;
        point.f101y = tmp.f106y;
    }
}
