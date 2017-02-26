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
import com.badlogic.gdx.utils.NumberUtils;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.games.GamesStatusCodes;

public class PolygonSpriteBatch {
    private int blendDstFunc;
    private int blendSrcFunc;
    private boolean blendingDisabled;
    private int bufferIndex;
    private Mesh[] buffers;
    float color;
    private final Matrix4 combinedMatrix;
    private ShaderProgram customShader;
    private boolean drawing;
    private Texture lastTexture;
    public int maxTrianglesInBatch;
    private Mesh mesh;
    private boolean ownsShader;
    private final Matrix4 projectionMatrix;
    public int renderCalls;
    private final ShaderProgram shader;
    private Color tempColor;
    public int totalRenderCalls;
    private final Matrix4 transformMatrix;
    private int triangleIndex;
    private final short[] triangles;
    private int vertexIndex;
    private final float[] vertices;

    public PolygonSpriteBatch() {
        this((int) GamesStatusCodes.STATUS_REQUEST_UPDATE_PARTIAL_SUCCESS, null);
    }

    public PolygonSpriteBatch(int size) {
        this(size, 1, null);
    }

    public PolygonSpriteBatch(int size, ShaderProgram defaultShader) {
        this(size, 1, defaultShader);
    }

    public PolygonSpriteBatch(int size, int buffers) {
        this(size, buffers, null);
    }

    public PolygonSpriteBatch(int size, int buffers, ShaderProgram defaultShader) {
        this.transformMatrix = new Matrix4();
        this.projectionMatrix = new Matrix4();
        this.combinedMatrix = new Matrix4();
        this.blendSrcFunc = GL20.GL_SRC_ALPHA;
        this.blendDstFunc = GL20.GL_ONE_MINUS_SRC_ALPHA;
        this.color = Color.WHITE.toFloatBits();
        this.tempColor = new Color(TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE, TextTrackStyle.DEFAULT_FONT_SCALE);
        this.renderCalls = 0;
        this.totalRenderCalls = 0;
        this.maxTrianglesInBatch = 0;
        if (size > 10920) {
            throw new IllegalArgumentException("Can't have more than 10920 triangles per batch: " + size);
        }
        this.buffers = new Mesh[buffers];
        for (int i = 0; i < buffers; i++) {
            this.buffers[i] = new Mesh(VertexDataType.VertexArray, false, size, size * 3, new VertexAttribute(1, 2, ShaderProgram.POSITION_ATTRIBUTE), new VertexAttribute(4, 4, ShaderProgram.COLOR_ATTRIBUTE), new VertexAttribute(16, 2, "a_texCoord0"));
        }
        this.mesh = this.buffers[0];
        this.vertices = new float[(size * 5)];
        this.triangles = new short[(size * 3)];
        if (Gdx.graphics.isGL20Available() && defaultShader == null) {
            this.shader = SpriteBatch.createDefaultShader();
            this.ownsShader = true;
        } else {
            this.shader = defaultShader;
        }
        this.projectionMatrix.setToOrtho2D(0.0f, 0.0f, (float) Gdx.graphics.getWidth(), (float) Gdx.graphics.getHeight());
    }

    public void begin() {
        if (this.drawing) {
            throw new IllegalStateException("PolygonSpriteBatch.end must be called before begin.");
        }
        this.renderCalls = 0;
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
            if (this.vertexIndex > 0) {
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
        throw new IllegalStateException("PolygonSpriteBatch.begin must be called before end.");
    }

    public void setColor(Color tint) {
        this.color = tint.toFloatBits();
    }

    public void setColor(float r, float g, float b, float a) {
        this.color = NumberUtils.intToFloatColor((((((int) (255.0f * a)) << 24) | (((int) (255.0f * b)) << 16)) | (((int) (255.0f * g)) << 8)) | ((int) (255.0f * r)));
    }

    public void setColor(float color) {
        this.color = color;
    }

    public Color getColor() {
        int intBits = NumberUtils.floatToIntColor(this.color);
        Color color = this.tempColor;
        color.f40r = ((float) (intBits & Keys.F12)) / 255.0f;
        color.f39g = ((float) ((intBits >>> 8) & Keys.F12)) / 255.0f;
        color.f38b = ((float) ((intBits >>> 16) & Keys.F12)) / 255.0f;
        color.f37a = ((float) ((intBits >>> 24) & Keys.F12)) / 255.0f;
        return color;
    }

    public void draw(PolygonRegion region, float x, float y) {
        if (this.drawing) {
            short[] triangles = this.triangles;
            short[] regionTriangles = region.triangles;
            int regionTrianglesLength = regionTriangles.length;
            float[] regionVertices = region.vertices;
            int regionVerticesLength = regionVertices.length;
            Texture texture = region.region.texture;
            Texture texture2 = this.lastTexture;
            if (texture != r0) {
                switchTexture(texture);
            } else {
                if (this.triangleIndex + regionTrianglesLength > triangles.length || this.vertexIndex + regionVerticesLength > this.vertices.length) {
                    flush();
                }
            }
            int triangleIndex = this.triangleIndex;
            int vertexIndex = this.vertexIndex;
            int startVertex = vertexIndex / 5;
            int i = 0;
            int triangleIndex2 = triangleIndex;
            while (i < regionTrianglesLength) {
                triangleIndex = triangleIndex2 + 1;
                triangles[triangleIndex2] = (short) (regionTriangles[i] + startVertex);
                i++;
                triangleIndex2 = triangleIndex;
            }
            this.triangleIndex = triangleIndex2;
            float[] vertices = this.vertices;
            float color = this.color;
            float[] textureCoords = region.textureCoords;
            i = 0;
            int vertexIndex2 = vertexIndex;
            while (i < regionVerticesLength) {
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = regionVertices[i] + x;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = regionVertices[i + 1] + y;
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = color;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = textureCoords[i];
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = textureCoords[i + 1];
                i += 2;
                vertexIndex2 = vertexIndex;
            }
            this.vertexIndex = vertexIndex2;
            return;
        }
        throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
    }

    public void draw(PolygonRegion region, float x, float y, float width, float height) {
        if (this.drawing) {
            short[] triangles = this.triangles;
            short[] regionTriangles = region.triangles;
            int regionTrianglesLength = regionTriangles.length;
            float[] regionVertices = region.vertices;
            int regionVerticesLength = regionVertices.length;
            TextureRegion textureRegion = region.region;
            Texture texture = textureRegion.texture;
            Texture texture2 = this.lastTexture;
            if (texture != r0) {
                switchTexture(texture);
            } else {
                if (this.triangleIndex + regionTrianglesLength > triangles.length || this.vertexIndex + regionVerticesLength > this.vertices.length) {
                    flush();
                }
            }
            int triangleIndex = this.triangleIndex;
            int vertexIndex = this.vertexIndex;
            int startVertex = vertexIndex / 5;
            int i = 0;
            int n = regionTriangles.length;
            int triangleIndex2 = triangleIndex;
            while (i < n) {
                triangleIndex = triangleIndex2 + 1;
                triangles[triangleIndex2] = (short) (regionTriangles[i] + startVertex);
                i++;
                triangleIndex2 = triangleIndex;
            }
            this.triangleIndex = triangleIndex2;
            float[] vertices = this.vertices;
            float color = this.color;
            float[] textureCoords = region.textureCoords;
            float sX = width / ((float) textureRegion.regionWidth);
            float sY = height / ((float) textureRegion.regionHeight);
            i = 0;
            int vertexIndex2 = vertexIndex;
            while (i < regionVerticesLength) {
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = (regionVertices[i] * sX) + x;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = (regionVertices[i + 1] * sY) + y;
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = color;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = textureCoords[i];
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = textureCoords[i + 1];
                i += 2;
                vertexIndex2 = vertexIndex;
            }
            this.vertexIndex = vertexIndex2;
            return;
        }
        throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
    }

    public void draw(PolygonRegion region, float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY, float rotation) {
        if (this.drawing) {
            short[] triangles = this.triangles;
            short[] regionTriangles = region.triangles;
            int regionTrianglesLength = regionTriangles.length;
            float[] regionVertices = region.vertices;
            int regionVerticesLength = regionVertices.length;
            TextureRegion textureRegion = region.region;
            Texture texture = textureRegion.texture;
            Texture texture2 = this.lastTexture;
            if (texture != r0) {
                switchTexture(texture);
            } else {
                if (this.triangleIndex + regionTrianglesLength > triangles.length || this.vertexIndex + regionVerticesLength > this.vertices.length) {
                    flush();
                }
            }
            int triangleIndex = this.triangleIndex;
            int vertexIndex = this.vertexIndex;
            int startVertex = vertexIndex / 5;
            int i = 0;
            int triangleIndex2 = triangleIndex;
            while (i < regionTrianglesLength) {
                triangleIndex = triangleIndex2 + 1;
                triangles[triangleIndex2] = (short) (regionTriangles[i] + startVertex);
                i++;
                triangleIndex2 = triangleIndex;
            }
            this.triangleIndex = triangleIndex2;
            float[] vertices = this.vertices;
            float color = this.color;
            float[] textureCoords = region.textureCoords;
            float worldOriginX = x + originX;
            float worldOriginY = y + originY;
            float sX = width / ((float) textureRegion.regionWidth);
            float sY = height / ((float) textureRegion.regionHeight);
            float cos = MathUtils.cosDeg(rotation);
            float sin = MathUtils.sinDeg(rotation);
            i = 0;
            int vertexIndex2 = vertexIndex;
            while (i < regionVerticesLength) {
                float fx = ((regionVertices[i] * sX) - originX) * scaleX;
                float fy = ((regionVertices[i + 1] * sY) - originY) * scaleY;
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = ((cos * fx) - (sin * fy)) + worldOriginX;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = ((sin * fx) + (cos * fy)) + worldOriginY;
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = color;
                vertexIndex2 = vertexIndex + 1;
                vertices[vertexIndex] = textureCoords[i];
                vertexIndex = vertexIndex2 + 1;
                vertices[vertexIndex2] = textureCoords[i + 1];
                i += 2;
                vertexIndex2 = vertexIndex;
            }
            this.vertexIndex = vertexIndex2;
            return;
        }
        throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
    }

    public void draw(Texture texture, float[] polygonVertices, int verticesOffset, int verticesCount, short[] polygonTriangles, int trianglesOffset, int trianglesCount) {
        if (this.drawing) {
            short[] triangles = this.triangles;
            float[] vertices = this.vertices;
            if (texture != this.lastTexture) {
                switchTexture(texture);
            } else if (this.triangleIndex + trianglesCount > triangles.length || this.vertexIndex + verticesCount > vertices.length) {
                flush();
            }
            int triangleIndex = this.triangleIndex;
            int vertexIndex = this.vertexIndex;
            int startVertex = vertexIndex / 5;
            int i = trianglesOffset;
            int n = i + trianglesCount;
            int triangleIndex2 = triangleIndex;
            while (i < n) {
                triangleIndex = triangleIndex2 + 1;
                triangles[triangleIndex2] = (short) (polygonTriangles[i] + startVertex);
                i++;
                triangleIndex2 = triangleIndex;
            }
            this.triangleIndex = triangleIndex2;
            System.arraycopy(polygonVertices, verticesOffset, vertices, vertexIndex, verticesCount);
            this.vertexIndex += verticesCount;
            return;
        }
        throw new IllegalStateException("PolygonSpriteBatch.begin must be called before draw.");
    }

    public void flush() {
        if (this.vertexIndex != 0) {
            this.renderCalls++;
            this.totalRenderCalls++;
            int trianglesInBatch = this.triangleIndex;
            if (trianglesInBatch > this.maxTrianglesInBatch) {
                this.maxTrianglesInBatch = trianglesInBatch;
            }
            this.lastTexture.bind();
            Mesh mesh = this.mesh;
            mesh.setVertices(this.vertices, 0, this.vertexIndex);
            mesh.setIndices(this.triangles, 0, this.triangleIndex);
            if (this.blendingDisabled) {
                Gdx.gl.glDisable(GL20.GL_BLEND);
            } else {
                Gdx.gl.glEnable(GL20.GL_BLEND);
                if (this.blendSrcFunc != -1) {
                    Gdx.gl.glBlendFunc(this.blendSrcFunc, this.blendDstFunc);
                }
            }
            if (Gdx.graphics.isGL20Available()) {
                mesh.render(this.customShader != null ? this.customShader : this.shader, 4, 0, trianglesInBatch);
            } else {
                mesh.render(4, 0, trianglesInBatch);
            }
            this.vertexIndex = 0;
            this.triangleIndex = 0;
            this.bufferIndex++;
            if (this.bufferIndex == this.buffers.length) {
                this.bufferIndex = 0;
            }
            this.mesh = this.buffers[this.bufferIndex];
        }
    }

    public void disableBlending() {
        flush();
        this.blendingDisabled = true;
    }

    public void enableBlending() {
        flush();
        this.blendingDisabled = false;
    }

    public void setBlendFunction(int srcFunc, int dstFunc) {
        if (this.blendSrcFunc != srcFunc || this.blendDstFunc != dstFunc) {
            flush();
            this.blendSrcFunc = srcFunc;
            this.blendDstFunc = dstFunc;
        }
    }

    public void dispose() {
        for (Mesh dispose : this.buffers) {
            dispose.dispose();
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
                this.customShader.setUniformi("u_texture", 0);
                return;
            }
            this.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
            this.shader.setUniformi("u_texture", 0);
            return;
        }
        GL10 gl = Gdx.gl10;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadMatrixf(this.projectionMatrix.val, 0);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadMatrixf(this.transformMatrix.val, 0);
    }

    private void switchTexture(Texture texture) {
        flush();
        this.lastTexture = texture;
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
