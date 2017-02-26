package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;

public class Sprite extends TextureRegion {
    static final int SPRITE_SIZE = 20;
    static final int VERTEX_SIZE = 5;
    private Rectangle bounds;
    private final Color color;
    private boolean dirty;
    float height;
    private float originX;
    private float originY;
    private float rotation;
    private float scaleX;
    private float scaleY;
    final float[] vertices;
    float width;
    private float f94x;
    private float f95y;

    public Sprite() {
        this.vertices = new float[SPRITE_SIZE];
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.dirty = true;
        setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public Sprite(Texture texture) {
        this(texture, 0, 0, texture.getWidth(), texture.getHeight());
    }

    public Sprite(Texture texture, int srcWidth, int srcHeight) {
        this(texture, 0, 0, srcWidth, srcHeight);
    }

    public Sprite(Texture texture, int srcX, int srcY, int srcWidth, int srcHeight) {
        this.vertices = new float[SPRITE_SIZE];
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.dirty = true;
        if (texture == null) {
            throw new IllegalArgumentException("texture cannot be null.");
        }
        this.texture = texture;
        setRegion(srcX, srcY, srcWidth, srcHeight);
        setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        setSize((float) Math.abs(srcWidth), (float) Math.abs(srcHeight));
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(TextureRegion region) {
        this.vertices = new float[SPRITE_SIZE];
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.dirty = true;
        setRegion(region);
        setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        setSize((float) region.getRegionWidth(), (float) region.getRegionHeight());
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(TextureRegion region, int srcX, int srcY, int srcWidth, int srcHeight) {
        this.vertices = new float[SPRITE_SIZE];
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.dirty = true;
        setRegion(region, srcX, srcY, srcWidth, srcHeight);
        setColor(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        setSize((float) Math.abs(srcWidth), (float) Math.abs(srcHeight));
        setOrigin(this.width / 2.0f, this.height / 2.0f);
    }

    public Sprite(Sprite sprite) {
        this.vertices = new float[SPRITE_SIZE];
        this.color = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.scaleX = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.scaleY = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.dirty = true;
        set(sprite);
    }

    public void set(Sprite sprite) {
        if (sprite == null) {
            throw new IllegalArgumentException("sprite cannot be null.");
        }
        System.arraycopy(sprite.vertices, 0, this.vertices, 0, SPRITE_SIZE);
        this.texture = sprite.texture;
        this.u = sprite.u;
        this.v = sprite.v;
        this.u2 = sprite.u2;
        this.v2 = sprite.v2;
        this.f94x = sprite.f94x;
        this.f95y = sprite.f95y;
        this.width = sprite.width;
        this.height = sprite.height;
        this.regionWidth = sprite.regionWidth;
        this.regionHeight = sprite.regionHeight;
        this.originX = sprite.originX;
        this.originY = sprite.originY;
        this.rotation = sprite.rotation;
        this.scaleX = sprite.scaleX;
        this.scaleY = sprite.scaleY;
        this.color.set(sprite.color);
        this.dirty = sprite.dirty;
    }

    public void setBounds(float x, float y, float width, float height) {
        this.f94x = x;
        this.f95y = y;
        this.width = width;
        this.height = height;
        if (!this.dirty) {
            float x2 = x + width;
            float y2 = y + height;
            float[] vertices = this.vertices;
            vertices[0] = x;
            vertices[1] = y;
            vertices[VERTEX_SIZE] = x;
            vertices[6] = y2;
            vertices[10] = x2;
            vertices[11] = y2;
            vertices[15] = x2;
            vertices[16] = y;
            if (this.rotation != 0.0f || this.scaleX != TextTrackStyle.DEFAULT_FONT_SCALE || this.scaleY != TextTrackStyle.DEFAULT_FONT_SCALE) {
                this.dirty = true;
            }
        }
    }

    public void setSize(float width, float height) {
        this.width = width;
        this.height = height;
        if (!this.dirty) {
            float x2 = this.f94x + width;
            float y2 = this.f95y + height;
            float[] vertices = this.vertices;
            vertices[0] = this.f94x;
            vertices[1] = this.f95y;
            vertices[VERTEX_SIZE] = this.f94x;
            vertices[6] = y2;
            vertices[10] = x2;
            vertices[11] = y2;
            vertices[15] = x2;
            vertices[16] = this.f95y;
            if (this.rotation != 0.0f || this.scaleX != TextTrackStyle.DEFAULT_FONT_SCALE || this.scaleY != TextTrackStyle.DEFAULT_FONT_SCALE) {
                this.dirty = true;
            }
        }
    }

    public void setPosition(float x, float y) {
        translate(x - this.f94x, y - this.f95y);
    }

    public void setX(float x) {
        translateX(x - this.f94x);
    }

    public void setY(float y) {
        translateY(y - this.f95y);
    }

    public void translateX(float xAmount) {
        this.f94x += xAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[0] = vertices[0] + xAmount;
            vertices[VERTEX_SIZE] = vertices[VERTEX_SIZE] + xAmount;
            vertices[10] = vertices[10] + xAmount;
            vertices[15] = vertices[15] + xAmount;
        }
    }

    public void translateY(float yAmount) {
        this.f95y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[1] = vertices[1] + yAmount;
            vertices[6] = vertices[6] + yAmount;
            vertices[11] = vertices[11] + yAmount;
            vertices[16] = vertices[16] + yAmount;
        }
    }

    public void translate(float xAmount, float yAmount) {
        this.f94x += xAmount;
        this.f95y += yAmount;
        if (!this.dirty) {
            float[] vertices = this.vertices;
            vertices[0] = vertices[0] + xAmount;
            vertices[1] = vertices[1] + yAmount;
            vertices[VERTEX_SIZE] = vertices[VERTEX_SIZE] + xAmount;
            vertices[6] = vertices[6] + yAmount;
            vertices[10] = vertices[10] + xAmount;
            vertices[11] = vertices[11] + yAmount;
            vertices[15] = vertices[15] + xAmount;
            vertices[16] = vertices[16] + yAmount;
        }
    }

    public void setColor(Color tint) {
        float color = tint.toFloatBits();
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
    }

    public void setColor(float r, float g, float b, float a) {
        float color = NumberUtils.intToFloatColor((((((int) (255.0f * a)) << 24) | (((int) (255.0f * b)) << 16)) | (((int) (255.0f * g)) << 8)) | ((int) (255.0f * r)));
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
    }

    public void setColor(float color) {
        float[] vertices = this.vertices;
        vertices[2] = color;
        vertices[7] = color;
        vertices[12] = color;
        vertices[17] = color;
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

    public float getRotation() {
        return this.rotation;
    }

    public void rotate(float degrees) {
        if (degrees != 0.0f) {
            this.rotation += degrees;
            this.dirty = true;
        }
    }

    public void rotate90(boolean clockwise) {
        float[] vertices = this.vertices;
        if (clockwise) {
            float temp = vertices[4];
            vertices[4] = vertices[19];
            vertices[19] = vertices[14];
            vertices[14] = vertices[9];
            vertices[9] = temp;
            temp = vertices[3];
            vertices[3] = vertices[18];
            vertices[18] = vertices[13];
            vertices[13] = vertices[8];
            vertices[8] = temp;
            return;
        }
        temp = vertices[4];
        vertices[4] = vertices[9];
        vertices[9] = vertices[14];
        vertices[14] = vertices[19];
        vertices[19] = temp;
        temp = vertices[3];
        vertices[3] = vertices[8];
        vertices[8] = vertices[13];
        vertices[13] = vertices[18];
        vertices[18] = temp;
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

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public float[] getVertices() {
        /*
        r27 = this;
        r0 = r27;
        r0 = r0.dirty;
        r25 = r0;
        if (r25 == 0) goto L_0x00f0;
    L_0x0008:
        r25 = 0;
        r0 = r25;
        r1 = r27;
        r1.dirty = r0;
        r0 = r27;
        r0 = r0.vertices;
        r16 = r0;
        r0 = r27;
        r0 = r0.originX;
        r25 = r0;
        r0 = r25;
        r3 = -r0;
        r0 = r27;
        r0 = r0.originY;
        r25 = r0;
        r0 = r25;
        r9 = -r0;
        r0 = r27;
        r0 = r0.width;
        r25 = r0;
        r4 = r3 + r25;
        r0 = r27;
        r0 = r0.height;
        r25 = r0;
        r10 = r9 + r25;
        r0 = r27;
        r0 = r0.f94x;
        r25 = r0;
        r17 = r25 - r3;
        r0 = r27;
        r0 = r0.f95y;
        r25 = r0;
        r18 = r25 - r9;
        r0 = r27;
        r0 = r0.scaleX;
        r25 = r0;
        r26 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r25 = (r25 > r26 ? 1 : (r25 == r26 ? 0 : -1));
        if (r25 != 0) goto L_0x0060;
    L_0x0054:
        r0 = r27;
        r0 = r0.scaleY;
        r25 = r0;
        r26 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r25 = (r25 > r26 ? 1 : (r25 == r26 ? 0 : -1));
        if (r25 == 0) goto L_0x0080;
    L_0x0060:
        r0 = r27;
        r0 = r0.scaleX;
        r25 = r0;
        r3 = r3 * r25;
        r0 = r27;
        r0 = r0.scaleY;
        r25 = r0;
        r9 = r9 * r25;
        r0 = r27;
        r0 = r0.scaleX;
        r25 = r0;
        r4 = r4 * r25;
        r0 = r27;
        r0 = r0.scaleY;
        r25 = r0;
        r10 = r10 * r25;
    L_0x0080:
        r0 = r27;
        r0 = r0.rotation;
        r25 = r0;
        r26 = 0;
        r25 = (r25 > r26 ? 1 : (r25 == r26 ? 0 : -1));
        if (r25 == 0) goto L_0x00f7;
    L_0x008c:
        r0 = r27;
        r0 = r0.rotation;
        r25 = r0;
        r2 = com.badlogic.gdx.math.MathUtils.cosDeg(r25);
        r0 = r27;
        r0 = r0.rotation;
        r25 = r0;
        r15 = com.badlogic.gdx.math.MathUtils.sinDeg(r25);
        r7 = r3 * r2;
        r8 = r3 * r15;
        r13 = r9 * r2;
        r14 = r9 * r15;
        r5 = r4 * r2;
        r6 = r4 * r15;
        r11 = r10 * r2;
        r12 = r10 * r15;
        r25 = r7 - r14;
        r19 = r25 + r17;
        r25 = r13 + r8;
        r22 = r25 + r18;
        r25 = 0;
        r16[r25] = r19;
        r25 = 1;
        r16[r25] = r22;
        r25 = r7 - r12;
        r20 = r25 + r17;
        r25 = r11 + r8;
        r23 = r25 + r18;
        r25 = 5;
        r16[r25] = r20;
        r25 = 6;
        r16[r25] = r23;
        r25 = r5 - r12;
        r21 = r25 + r17;
        r25 = r11 + r6;
        r24 = r25 + r18;
        r25 = 10;
        r16[r25] = r21;
        r25 = 11;
        r16[r25] = r24;
        r25 = 15;
        r26 = r21 - r20;
        r26 = r26 + r19;
        r16[r25] = r26;
        r25 = 16;
        r26 = r23 - r22;
        r26 = r24 - r26;
        r16[r25] = r26;
    L_0x00f0:
        r0 = r27;
        r0 = r0.vertices;
        r25 = r0;
        return r25;
    L_0x00f7:
        r19 = r3 + r17;
        r22 = r9 + r18;
        r20 = r4 + r17;
        r23 = r10 + r18;
        r25 = 0;
        r16[r25] = r19;
        r25 = 1;
        r16[r25] = r22;
        r25 = 5;
        r16[r25] = r19;
        r25 = 6;
        r16[r25] = r23;
        r25 = 10;
        r16[r25] = r20;
        r25 = 11;
        r16[r25] = r23;
        r25 = 15;
        r16[r25] = r20;
        r25 = 16;
        r16[r25] = r22;
        goto L_0x00f0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.graphics.g2d.Sprite.getVertices():float[]");
    }

    public Rectangle getBoundingRectangle() {
        float[] vertices = getVertices();
        float minx = vertices[0];
        float miny = vertices[1];
        float maxx = vertices[0];
        float maxy = vertices[1];
        if (minx > vertices[VERTEX_SIZE]) {
            minx = vertices[VERTEX_SIZE];
        }
        if (minx > vertices[10]) {
            minx = vertices[10];
        }
        if (minx > vertices[15]) {
            minx = vertices[15];
        }
        if (maxx < vertices[VERTEX_SIZE]) {
            maxx = vertices[VERTEX_SIZE];
        }
        if (maxx < vertices[10]) {
            maxx = vertices[10];
        }
        if (maxx < vertices[15]) {
            maxx = vertices[15];
        }
        if (miny > vertices[6]) {
            miny = vertices[6];
        }
        if (miny > vertices[11]) {
            miny = vertices[11];
        }
        if (miny > vertices[16]) {
            miny = vertices[16];
        }
        if (maxy < vertices[6]) {
            maxy = vertices[6];
        }
        if (maxy < vertices[11]) {
            maxy = vertices[11];
        }
        if (maxy < vertices[16]) {
            maxy = vertices[16];
        }
        if (this.bounds == null) {
            this.bounds = new Rectangle();
        }
        this.bounds.f75x = minx;
        this.bounds.f76y = miny;
        this.bounds.width = maxx - minx;
        this.bounds.height = maxy - miny;
        return this.bounds;
    }

    public void draw(SpriteBatch spriteBatch) {
        spriteBatch.draw(this.texture, getVertices(), 0, SPRITE_SIZE);
    }

    public void draw(SpriteBatch spriteBatch, float alphaModulation) {
        Color color = getColor();
        float oldAlpha = color.f37a;
        color.f37a *= alphaModulation;
        setColor(color);
        draw(spriteBatch);
        color.f37a = oldAlpha;
        setColor(color);
    }

    public float getX() {
        return this.f94x;
    }

    public float getY() {
        return this.f95y;
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

    public void setRegion(float u, float v, float u2, float v2) {
        super.setRegion(u, v, u2, v2);
        float[] vertices = this.vertices;
        vertices[3] = u;
        vertices[4] = v2;
        vertices[8] = u;
        vertices[9] = v;
        vertices[13] = u2;
        vertices[14] = v;
        vertices[18] = u2;
        vertices[19] = v2;
    }

    public void setU(float u) {
        super.setU(u);
        this.vertices[3] = u;
        this.vertices[8] = u;
    }

    public void setV(float v) {
        super.setV(v);
        this.vertices[9] = v;
        this.vertices[14] = v;
    }

    public void setU2(float u2) {
        super.setU2(u2);
        this.vertices[13] = u2;
        this.vertices[18] = u2;
    }

    public void setV2(float v2) {
        super.setV2(v2);
        this.vertices[4] = v2;
        this.vertices[19] = v2;
    }

    public void flip(boolean x, boolean y) {
        super.flip(x, y);
        float[] vertices = this.vertices;
        if (x) {
            float temp = vertices[3];
            vertices[3] = vertices[13];
            vertices[13] = temp;
            temp = vertices[8];
            vertices[8] = vertices[18];
            vertices[18] = temp;
        }
        if (y) {
            temp = vertices[4];
            vertices[4] = vertices[14];
            vertices[14] = temp;
            temp = vertices[9];
            vertices[9] = vertices[19];
            vertices[19] = temp;
        }
    }

    public void scroll(float xAmount, float yAmount) {
        float[] vertices = this.vertices;
        if (xAmount != 0.0f) {
            float u = (vertices[3] + xAmount) % TextTrackStyle.DEFAULT_FONT_SCALE;
            float u2 = u + (this.width / ((float) this.texture.getWidth()));
            this.u = u;
            this.u2 = u2;
            vertices[3] = u;
            vertices[8] = u;
            vertices[13] = u2;
            vertices[18] = u2;
        }
        if (yAmount != 0.0f) {
            float v = (vertices[9] + yAmount) % TextTrackStyle.DEFAULT_FONT_SCALE;
            float v2 = v + (this.height / ((float) this.texture.getHeight()));
            this.v = v;
            this.v2 = v2;
            vertices[4] = v2;
            vertices[9] = v;
            vertices[14] = v;
            vertices[19] = v2;
        }
    }
}
