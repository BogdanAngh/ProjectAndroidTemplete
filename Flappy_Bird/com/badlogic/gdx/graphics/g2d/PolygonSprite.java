package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;

public class PolygonSprite {
    private Rectangle bounds;
    private final Color color;
    private boolean dirty;
    private float height;
    private float originX;
    private float originY;
    PolygonRegion region;
    private float rotation;
    private float scaleX;
    private float scaleY;
    private float[] vertices;
    private float width;
    private float f49x;
    private float f50y;

    public PolygonSprite(PolygonRegion region) {
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.bounds = new Rectangle();
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        setRegion(region);
        setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        setSize((float) region.region.regionWidth, (float) region.region.regionHeight);
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public PolygonSprite(PolygonSprite sprite) {
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.bounds = new Rectangle();
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        set(sprite);
    }

    public void set(PolygonSprite sprite) {
        if (sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }
        setRegion(sprite.region);
        this.f49x = sprite.f49x;
        this.f50y = sprite.f50y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.f49x = x;
        this.f50y = y;
        this.width = width;
        this.height = height;
        this.dirty = true;
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        this.dirty = true;
    }

    public void setPosition(float x, float y) {
        translate(x - this.f49x, y - this.f50y);
    }

    public void setX(float x) {
        translateX(x - this.f49x);
    }

    public void setY(float y) {
        translateY(y - this.f50y);
    }

    public void translateX(float xAmount) {
        this.f49x += xAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 0; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + xAmount;
            }
        }
    }

    public void translateY(float yAmount) {
        this.f50y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 1; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + yAmount;
            }
        }
    }

    public void translate(float xAmount, float yAmount) {
        this.f49x += xAmount;
        this.f50y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            for (int i = 0; i < vertices.length; i += 5) {
                vertices[i] = vertices[i] + xAmount;
                int i2 = i + 1;
                vertices[i2] = vertices[i2] + yAmount;
            }
        }
    }

    public void setColor(Color tint) {
        float color = tint.toFloatBits();
        float[] vertices = this.vertices;
        for (int i = 2; i < vertices.length; i += 5) {
            vertices[i] = color;
        }
    }

    public void setColor(float r, float g, float b, float a) {
        float color = NumberUtils.intToFloatColor((((((int) (255.0f * a)) << 24) | (((int) (255.0f * b)) << 16)) | (((int) (255.0f * g)) << 8)) | ((int) (255.0f * r)));
        float[] vertices = this.vertices;
        for (int i = 2; i < vertices.length; i += 5) {
            vertices[i] = color;
        }
    }

    public void setOrigin(float originX, float originY) {
        this.originX = originX;
        this.originY = originY;
        this.dirty = true;
    }

    public void setRotation(float degrees) {
        this.rotation = degrees;
        this.dirty = true;
    }

    public void rotate(float degrees) {
        this.rotation += degrees;
        this.dirty = true;
    }

    public void setScale(float scaleXY) {
        this.scaleX = scaleXY;
        this.scaleY = scaleXY;
        this.dirty = true;
    }

    public void setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.dirty = true;
    }

    public void scale(float amount) {
        this.scaleX += amount;
        this.scaleY += amount;
        this.dirty = true;
    }

    public float[] getVertices() {
        if (!this.dirty) {
            return this.vertices;
        }
        this.dirty = false;
        float originX = this.originX;
        float originY = this.originY;
        float scaleX = this.scaleX;
        float scaleY = this.scaleY;
        PolygonRegion region = this.region;
        float[] vertices = this.vertices;
        float[] regionVertices = region.vertices;
        float worldOriginX = this.f49x + originX;
        float worldOriginY = this.f50y + originY;
        float sX = this.width / ((float) region.region.getRegionWidth());
        float sY = this.height / ((float) region.region.getRegionHeight());
        float cos = MathUtils.cosDeg(this.rotation);
        float sin = MathUtils.sinDeg(this.rotation);
        int i = 0;
        int v = 0;
        int n = regionVertices.length;
        while (i < n) {
            float fx = ((regionVertices[i] * sX) - originX) * scaleX;
            float fy = ((regionVertices[i + 1] * sY) - originY) * scaleY;
            vertices[v] = ((cos * fx) - (sin * fy)) + worldOriginX;
            vertices[v + 1] = ((sin * fx) + (cos * fy)) + worldOriginY;
            i += 2;
            v += 5;
        }
        return vertices;
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getVertices();
        float minx = vertices[0];
        float miny = vertices[1];
        float maxx = vertices[0];
        float maxy = vertices[1];
        for (int i = 5; i < vertices.length; i += 5) {
            float x = vertices[i];
            float y = vertices[i + 1];
            if (minx > x) {
                minx = x;
            }
            if (maxx < x) {
                maxx = x;
            }
            if (miny > y) {
                miny = y;
            }
            if (maxy < y) {
                maxy = y;
            }
        }
        this.bounds.f75x = minx;
        this.bounds.f76y = miny;
        this.bounds.width = maxx - minx;
        this.bounds.height = maxy - miny;
        return this.bounds;
    }

    public void draw(PolygonSpriteBatch spriteBatch) {
        PolygonRegion region = this.region;
        spriteBatch.draw(region.region.texture, getVertices(), 0, this.vertices.length, region.triangles, 0, region.triangles.length);
    }

    public void draw(PolygonSpriteBatch spriteBatch, float alphaModulation) {
        Color color = getColor();
        float oldAlpha = color.f37a;
        color.f37a *= alphaModulation;
        setColor(color);
        draw(spriteBatch);
        color.f37a = oldAlpha;
        setColor(color);
    }

    public float getX() {
        return this.f49x;
    }

    public float getY() {
        return this.f50y;
    }

    public float getWidth() {
        return this.width;
    }

    public float getHeight() {
        return this.height;
    }

    public float getOriginX() {
        return this.originX;
    }

    public float getOriginY() {
        return this.originY;
    }

    public float getRotation() {
        return this.rotation;
    }

    public float getScaleX() {
        return this.scaleX;
    }

    public float getScaleY() {
        return this.scaleY;
    }

    public Color getColor() {
        int intBits = NumberUtils.floatToIntColor(this.vertices[2]);
        Color color = this.color;
        color.f40r = ((float) (intBits & Keys.F12)) / 255.0f;
        color.f39g = ((float) ((intBits >>> 8) & Keys.F12)) / 255.0f;
        color.f38b = ((float) ((intBits >>> 16) & Keys.F12)) / 255.0f;
        color.f37a = ((float) ((intBits >>> 24) & Keys.F12)) / 255.0f;
        return color;
    }

    public void setRegion(PolygonRegion region) {
        this.region = region;
        float[] regionVertices = region.vertices;
        float[] textureCoords = region.textureCoords;
        if (this.vertices == null || regionVertices.length != this.vertices.length) {
            this.vertices = new float[((regionVertices.length / 2) * 5)];
        }
        float[] vertices = this.vertices;
        int i = 0;
        int v = 2;
        int n = regionVertices.length;
        while (i < n) {
            vertices[v] = this.color.toFloatBits();
            vertices[v + 1] = textureCoords[i];
            vertices[v + 2] = textureCoords[i + 1];
            i += 2;
            v += 5;
        }
        this.dirty = true;
    }
}
