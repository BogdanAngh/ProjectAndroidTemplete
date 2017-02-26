package com.badlogic.gdx.graphics.g2d;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Mesh.VertexDataType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.GeofenceStatusCodes;

public class SpriteBatch implements Disposable {
    public static final int C1 = 2;
    public static final int C2 = 7;
    public static final int C3 = 12;
    public static final int C4 = 17;
    public static final int U1 = 3;
    public static final int U2 = 8;
    public static final int U3 = 13;
    public static final int U4 = 18;
    public static final int V1 = 4;
    public static final int V2 = 9;
    public static final int V3 = 14;
    public static final int V4 = 19;
    public static final int X1 = 0;
    public static final int X2 = 5;
    public static final int X3 = 10;
    public static final int X4 = 15;
    public static final int Y1 = 1;
    public static final int Y2 = 6;
    public static final int Y3 = 11;
    public static final int Y4 = 16;
    private int blendDstFunc;
    private int blendSrcFunc;
    private boolean blendingDisabled;
    private Mesh[] buffers;
    float color;
    private final Matrix4 combinedMatrix;
    private int currBufferIdx;
    private ShaderProgram customShader;
    private boolean drawing;
    private int idx;
    private float invTexHeight;
    private float invTexWidth;
    private Texture lastTexture;
    public int maxSpritesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    private final float[] vertices;

    public SpriteBatch() {
        this(GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, Y1, null);
    }

    public SpriteBatch(int size) {
        this(size, Y1, null);
    }

    public SpriteBatch(int size, ShaderProgram defaultShader) {
        this(size, Y1, defaultShader);
    }

    public SpriteBatch(int size, int buffers) {
        this(size, buffers, null);
    }

    public SpriteBatch(int size, int buffers, ShaderProgram defaultShader) {
        this.currBufferIdx = X1;
        this.idx = X1;
        this.lastTexture = null;
        this.invTexWidth = 0.0f;
        this.invTexHeight = 0.0f;
        this.drawing = false;
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.blendingDisabled = false;
        this.blendSrcFunc = GL20.GL_SRC_ALPHA;
        this.blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
        this.customShader = null;
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.renderCalls = X1;
        this.totalRenderCalls = X1;
        this.maxSpritesInBatch = X1;
        if (size > 5460) {
            throw new IllegalArgumentException("Can't have more than 5460 sprites per batch: " + size);
        }
        int i;
        this.buffers = new Mesh[buffers];
        for (i = X1; i < buffers; i += Y1) {
            Mesh[] meshArr = this.buffers;
            VertexDataType vertexDataType = VertexDataType.VertexArray;
            int i2 = size * V1;
            int i3 = size * Y2;
            VertexAttribute[] vertexAttributeArr = new VertexAttribute[U1];
            vertexAttributeArr[X1] = new VertexAttribute(Y1, C1, ShaderProgram.POSITION_ATTRIBUTE);
            vertexAttributeArr[Y1] = new VertexAttribute(V1, V1, ShaderProgram.COLOR_ATTRIBUTE);
            vertexAttributeArr[C1] = new VertexAttribute(Y4, C1, "a_texCoord0");
            meshArr[i] = new Mesh(vertexDataType, false, i2, i3, vertexAttributeArr);
        }
        this.projectionMatrix.setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
        this.vertices = new float[(size * 20)];
        int len = size * Y2;
        short[] indices = new short[len];
        short j = (short) 0;
        i = X1;
        while (i < len) {
            indices[i + X1] = (short) (j + X1);
            indices[i + Y1] = (short) (j + Y1);
            indices[i + C1] = (short) (j + C1);
            indices[i + U1] = (short) (j + C1);
            indices[i + V1] = (short) (j + U1);
            indices[i + X2] = (short) (j + X1);
            i += Y2;
            j = (short) (j + V1);
        }
        for (i = X1; i < buffers; i += Y1) {
            this.buffers[i].setIndices(indices);
        }
        this.mesh = this.buffers[X1];
        if (Gdx.graphics.isGL20Available() && defaultShader == null) {
            this.shader = createDefaultShader();
            this.ownsShader = true;
            return;
        }
        this.shader = defaultShader;
    }

    public static ShaderProgram createDefaultShader() {
        ShaderProgram shader = new ShaderProgram("attribute vec4 a_position;\nattribute vec4 a_color;\nattribute vec2 a_texCoord0;\nuniform mat4 u_projTrans;\nvarying vec4 v_color;\nvarying vec2 v_texCoords;\n\nvoid main()\n{\n   v_color = a_color;\n   v_texCoords = a_texCoord0;\n   gl_Position =  u_projTrans * a_position;\n}\n", "#ifdef GL_ES\n#define LOWP lowp\nprecision mediump float;\n#else\n#define LOWP \n#endif\nvarying LOWP vec4 v_color;\nvarying vec2 v_texCoords;\nuniform sampler2D u_texture;\nvoid main()\n{\n  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n}");
        if (shader.isCompiled()) {
            return shader;
        }
        throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
    }

    public void begin() {
        if (this.drawing) {
            throw new IllegalStateException("SpriteBatch.end must be called before begin.");
        }
        this.renderCalls = X1;
        Gdx.gl.glDepthMask(false);
        if (!Gdx.graphics.isGL20Available()) {
            Gdx.gl.glEnable(GL20.GL_TEXTURE_2D);
        } else if (this.customShader != null) {
            this.customShader.begin();
        } else {
            this.shader.begin();
        }
        setupMatrices();
        this.drawing = true;
    }

    public void end() {
        if (this.drawing) {
            if (this.idx > 0) {
                flush();
            }
            this.lastTexture = null;
            this.drawing = false;
            GLCommon gl = Gdx.gl;
            gl.glDepthMask(true);
            if (isBlendingEnabled()) {
                gl.glDisable(GL20.GL_BLEND);
            }
            if (!Gdx.graphics.isGL20Available()) {
                gl.glDisable(GL20.GL_TEXTURE_2D);
                return;
            } else if (this.customShader != null) {
                this.customShader.end();
                return;
            } else {
                this.shader.end();
                return;
            }
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before end.");
    }

    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = NumberUtils.intToFloatColor((((((int) (255.0f * a)) << 24) | (((int) (255.0f * b)) << Y4)) | (((int) (255.0f * g)) << U2)) | ((int) (255.0f * r)));
    }

    public void setColor(float color) {
        this.color = color;
    }

    public Color getColor() {
        int intBits = NumberUtils.floatToIntColor(this.color);
        Color color = this.tempColor;
        color.f40r = ((float) (intBits & Keys.F12)) / 255.0f;
        color.f39g = ((float) ((intBits >>> U2) & Keys.F12)) / 255.0f;
        color.f38b = ((float) ((intBits >>> Y4) & Keys.F12)) / 255.0f;
        color.f37a = ((float) ((intBits >>> 24) & Keys.F12)) / 255.0f;
        return color;
    }

    public void draw(Texture texture, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        if (this.drawing) {
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float y4;
            float tmp;
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else {
                if (this.idx == vertices.length) {
                    flush();
                }
            }
            float worldOriginX = x + originX;
            float worldOriginY = y + originY;
            float fx = -originX;
            float fy = -originY;
            float fx2 = width - originX;
            float fy2 = height - originY;
            if (!(scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                float cos = MathUtils.cosDeg(rotation);
                float sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                y4 = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                y4 = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            y4 += worldOriginY;
            float u = ((float) srcX) * this.invTexWidth;
            float v = ((float) (srcY + srcHeight)) * this.invTexHeight;
            float u2 = ((float) (srcX + srcWidth)) * this.invTexWidth;
            float v2 = ((float) srcY) * this.invTexHeight;
            if (flipX) {
                tmp = u;
                u = u2;
                u2 = tmp;
            }
            if (flipY) {
                tmp = v;
                v = v2;
                v2 = tmp;
            }
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x1;
            idx = idx2 + Y1;
            vertices[idx2] = y1;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x2;
            idx2 = idx + Y1;
            vertices[idx] = y2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = x3;
            idx = idx2 + Y1;
            vertices[idx2] = y3;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = x4;
            idx2 = idx + Y1;
            vertices[idx] = y4;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, float width, float height, int srcX, int srcY, int srcWidth, int srcHeight, boolean flipX, boolean flipY) {
        if (this.drawing) {
            float tmp;
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float u = ((float) srcX) * this.invTexWidth;
            float v = ((float) (srcY + srcHeight)) * this.invTexHeight;
            float u2 = ((float) (srcX + srcWidth)) * this.invTexWidth;
            float v2 = ((float) srcY) * this.invTexHeight;
            float fx2 = x + width;
            float fy2 = y + height;
            if (flipX) {
                tmp = u;
                u = u2;
                u2 = tmp;
            }
            if (flipY) {
                tmp = v;
                v = v2;
                v2 = tmp;
            }
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, int srcX, int srcY, int srcWidth, int srcHeight) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float u = ((float) srcX) * this.invTexWidth;
            float v = ((float) (srcY + srcHeight)) * this.invTexHeight;
            float u2 = ((float) (srcX + srcWidth)) * this.invTexWidth;
            float v2 = ((float) srcY) * this.invTexHeight;
            float fx2 = x + ((float) srcWidth);
            float fy2 = y + ((float) srcHeight);
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, float width, float height, float u, float v, float u2, float v2) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float fx2 = x + ((float) texture.getWidth());
            float fy2 = y + ((float) texture.getHeight());
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = 0.0f;
            idx2 = idx + Y1;
            vertices[idx] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = 0.0f;
            idx = idx2 + Y1;
            vertices[idx2] = 0.0f;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx2 = idx + Y1;
            vertices[idx] = 0.0f;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx = idx2 + Y1;
            vertices[idx2] = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float x, float y, float width, float height) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = 0.0f;
            idx2 = idx + Y1;
            vertices[idx] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = 0.0f;
            idx = idx2 + Y1;
            vertices[idx2] = 0.0f;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx2 = idx + Y1;
            vertices[idx] = 0.0f;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = TextTrackStyle.DEFAULT_FONT_SCALE;
            idx = idx2 + Y1;
            vertices[idx2] = TextTrackStyle.DEFAULT_FONT_SCALE;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float[] spriteVertices, int offset, int count) {
        if (this.drawing) {
            int verticesLength = this.vertices.length;
            int remainingVertices = verticesLength;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else {
                remainingVertices -= this.idx;
                if (remainingVertices == 0) {
                    flush();
                    remainingVertices = verticesLength;
                }
            }
            int copyCount = Math.min(remainingVertices, count);
            System.arraycopy(spriteVertices, offset, this.vertices, this.idx, copyCount);
            this.idx += copyCount;
            count -= copyCount;
            while (count > 0) {
                offset += copyCount;
                flush();
                copyCount = Math.min(verticesLength, count);
                System.arraycopy(spriteVertices, offset, this.vertices, X1, copyCount);
                this.idx += copyCount;
                count -= copyCount;
            }
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y) {
        draw(region, x, y, (float) region.getRegionWidth(), (float) region.getRegionHeight());
    }

    public void draw(TextureRegion region, float x, float y, float width, float height) {
        if (this.drawing) {
            float[] vertices = this.vertices;
            Texture texture = region.texture;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.idx == vertices.length) {
                flush();
            }
            float fx2 = x + width;
            float fy2 = y + height;
            float u = region.f51u;
            float v = region.v2;
            float u2 = region.u2;
            float v2 = region.f52v;
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x;
            idx = idx2 + Y1;
            vertices[idx2] = y;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x;
            idx2 = idx + Y1;
            vertices[idx] = fy2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = fx2;
            idx = idx2 + Y1;
            vertices[idx2] = fy2;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = fx2;
            idx2 = idx + Y1;
            vertices[idx] = y;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if (this.drawing) {
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float y4;
            float[] vertices = this.vertices;
            Texture texture = region.texture;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else {
                if (this.idx == vertices.length) {
                    flush();
                }
            }
            float worldOriginX = x + originX;
            float worldOriginY = y + originY;
            float fx = -originX;
            float fy = -originY;
            float fx2 = width - originX;
            float fy2 = height - originY;
            if (!(scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                float cos = MathUtils.cosDeg(rotation);
                float sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                y4 = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                y4 = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            y4 += worldOriginY;
            float u = region.f51u;
            float v = region.v2;
            float u2 = region.u2;
            float v2 = region.f52v;
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x1;
            idx = idx2 + Y1;
            vertices[idx2] = y1;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u;
            idx2 = idx + Y1;
            vertices[idx] = v;
            idx = idx2 + Y1;
            vertices[idx2] = x2;
            idx2 = idx + Y1;
            vertices[idx] = y2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = x3;
            idx = idx2 + Y1;
            vertices[idx2] = y3;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u2;
            idx2 = idx + Y1;
            vertices[idx] = v2;
            idx = idx2 + Y1;
            vertices[idx2] = x4;
            idx2 = idx + Y1;
            vertices[idx] = y4;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void draw(TextureRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation, boolean clockwise) {
        if (this.drawing) {
            float x1;
            float y1;
            float x2;
            float y2;
            float x3;
            float y3;
            float x4;
            float y4;
            float u1;
            float v1;
            float u2;
            float v2;
            float u3;
            float v3;
            float u4;
            float v4;
            float[] vertices = this.vertices;
            Texture texture = region.texture;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else {
                if (this.idx == vertices.length) {
                    flush();
                }
            }
            float worldOriginX = x + originX;
            float worldOriginY = y + originY;
            float fx = -originX;
            float fy = -originY;
            float fx2 = width - originX;
            float fy2 = height - originY;
            if (!(scaleX == TextTrackStyle.DEFAULT_FONT_SCALE && scaleY == TextTrackStyle.DEFAULT_FONT_SCALE)) {
                fx *= scaleX;
                fy *= scaleY;
                fx2 *= scaleX;
                fy2 *= scaleY;
            }
            float p1x = fx;
            float p1y = fy;
            float p2x = fx;
            float p2y = fy2;
            float p3x = fx2;
            float p3y = fy2;
            float p4x = fx2;
            float p4y = fy;
            if (rotation != 0.0f) {
                float cos = MathUtils.cosDeg(rotation);
                float sin = MathUtils.sinDeg(rotation);
                x1 = (cos * p1x) - (sin * p1y);
                y1 = (sin * p1x) + (cos * p1y);
                x2 = (cos * p2x) - (sin * p2y);
                y2 = (sin * p2x) + (cos * p2y);
                x3 = (cos * p3x) - (sin * p3y);
                y3 = (sin * p3x) + (cos * p3y);
                x4 = x1 + (x3 - x2);
                y4 = y3 - (y2 - y1);
            } else {
                x1 = p1x;
                y1 = p1y;
                x2 = p2x;
                y2 = p2y;
                x3 = p3x;
                y3 = p3y;
                x4 = p4x;
                y4 = p4y;
            }
            x1 += worldOriginX;
            y1 += worldOriginY;
            x2 += worldOriginX;
            y2 += worldOriginY;
            x3 += worldOriginX;
            y3 += worldOriginY;
            x4 += worldOriginX;
            y4 += worldOriginY;
            if (clockwise) {
                u1 = region.u2;
                v1 = region.v2;
                u2 = region.f51u;
                v2 = region.v2;
                u3 = region.f51u;
                v3 = region.f52v;
                u4 = region.u2;
                v4 = region.f52v;
            } else {
                u1 = region.f51u;
                v1 = region.f52v;
                u2 = region.u2;
                v2 = region.f52v;
                u3 = region.u2;
                v3 = region.v2;
                u4 = region.f51u;
                v4 = region.v2;
            }
            float color = this.color;
            int idx = this.idx;
            int idx2 = idx + Y1;
            vertices[idx] = x1;
            idx = idx2 + Y1;
            vertices[idx2] = y1;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u1;
            idx2 = idx + Y1;
            vertices[idx] = v1;
            idx = idx2 + Y1;
            vertices[idx2] = x2;
            idx2 = idx + Y1;
            vertices[idx] = y2;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u2;
            idx = idx2 + Y1;
            vertices[idx2] = v2;
            idx2 = idx + Y1;
            vertices[idx] = x3;
            idx = idx2 + Y1;
            vertices[idx2] = y3;
            idx2 = idx + Y1;
            vertices[idx] = color;
            idx = idx2 + Y1;
            vertices[idx2] = u3;
            idx2 = idx + Y1;
            vertices[idx] = v3;
            idx = idx2 + Y1;
            vertices[idx2] = x4;
            idx2 = idx + Y1;
            vertices[idx] = y4;
            idx = idx2 + Y1;
            vertices[idx2] = color;
            idx2 = idx + Y1;
            vertices[idx] = u4;
            idx = idx2 + Y1;
            vertices[idx2] = v4;
            this.idx = idx;
            return;
        }
        throw new IllegalStateException("SpriteBatch.begin must be called before draw.");
    }

    public void flush() {
        if (this.idx != 0) {
            this.renderCalls += Y1;
            this.totalRenderCalls += Y1;
            int spritesInBatch = this.idx / 20;
            if (spritesInBatch > this.maxSpritesInBatch) {
                this.maxSpritesInBatch = spritesInBatch;
            }
            int count = spritesInBatch * Y2;
            this.lastTexture.bind();
            Mesh mesh = this.mesh;
            mesh.setVertices(this.vertices, X1, this.idx);
            mesh.getIndicesBuffer().position(X1);
            mesh.getIndicesBuffer().limit(count);
            if (this.blendingDisabled) {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            } else {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                if (this.blendSrcFunc != -1) {
                    Gdx.gl.glBlendFunc(this.blendSrcFunc, this.blendDstFunc);
                }
            }
            if (Gdx.graphics.isGL20Available()) {
                mesh.render(this.customShader != null ? this.customShader : this.shader, (int) V1, (int) X1, count);
            } else {
                mesh.render(V1, X1, count);
            }
            this.idx = X1;
            this.currBufferIdx += Y1;
            if (this.currBufferIdx == this.buffers.length) {
                this.currBufferIdx = X1;
            }
            this.mesh = this.buffers[this.currBufferIdx];
        }
    }

    public void disableBlending() {
        if (!this.blendingDisabled) {
            flush();
            this.blendingDisabled = true;
        }
    }

    public void enableBlending() {
        if (this.blendingDisabled) {
            flush();
            this.blendingDisabled = false;
        }
    }

    public void setBlendFunction(int srcFunc, int dstFunc) {
        if (this.blendSrcFunc != srcFunc || this.blendDstFunc != dstFunc) {
            flush();
            this.blendSrcFunc = srcFunc;
            this.blendDstFunc = dstFunc;
        }
    }

    public int getBlendSrcFunc() {
        return this.blendSrcFunc;
    }

    public int getBlendDstFunc() {
        return this.blendDstFunc;
    }

    public void dispose() {
        for (int i = X1; i < this.buffers.length; i += Y1) {
            this.buffers[i].dispose();
        }
        if (this.ownsShader && this.shader != null) {
            this.shader.dispose();
        }
    }

    public Matrix4 getProjectionMatrix() {
        return this.projectionMatrix;
    }

    public Matrix4 getTransformMatrix() {
        return this.transformMatrix;
    }

    public void setProjectionMatrix(Matrix4 projection) {
        if (this.drawing) {
            flush();
        }
        this.projectionMatrix.set(projection);
        if (this.drawing) {
            setupMatrices();
        }
    }

    public void setTransformMatrix(Matrix4 transform) {
        if (this.drawing) {
            flush();
        }
        this.transformMatrix.set(transform);
        if (this.drawing) {
            setupMatrices();
        }
    }

    private void setupMatrices() {
        if (Gdx.graphics.isGL20Available()) {
            this.combinedMatrix.set(this.projectionMatrix).mul(this.transformMatrix);
            if (this.customShader != null) {
                this.customShader.setUniformMatrix("u_projTrans", this.combinedMatrix);
                this.customShader.setUniformi("u_texture", (int) X1);
                return;
            }
            this.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.shader.setUniformi("u_texture", (int) X1);
            return;
        }
        GL10 gl = Gdx.gl10;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadMatrixf(this.projectionMatrix.val, X1);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadMatrixf(this.transformMatrix.val, X1);
    }

    private void switchTexture(Texture texture) {
        flush();
        this.lastTexture = texture;
        this.invTexWidth = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) texture.getWidth());
        this.invTexHeight = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) texture.getHeight());
    }

    public void setShader(ShaderProgram shader) {
        if (this.drawing) {
            flush();
            if (this.customShader != null) {
                this.customShader.end();
            } else {
                this.shader.end();
            }
        }
        this.customShader = shader;
        if (this.drawing) {
            if (this.customShader != null) {
                this.customShader.begin();
            } else {
                this.shader.begin();
            }
            setupMatrices();
        }
    }

    public boolean isBlendingEnabled() {
        return !this.blendingDisabled;
    }
}
