package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

public class VertexArray implements VertexData {
    final VertexAttributes attributes;
    final FloatBuffer buffer;
    final ByteBuffer byteBuffer;
    boolean isBound;

    public VertexArray(int numVertices, VertexAttribute... attributes) {
        this(numVertices, new VertexAttributes(attributes));
    }

    public VertexArray(int numVertices, VertexAttributes attributes) {
        this.isBound = false;
        this.attributes = attributes;
        this.byteBuffer = BufferUtils.newUnsafeByteBuffer(this.attributes.vertexSize * numVertices);
        this.buffer = this.byteBuffer.asFloatBuffer();
        this.buffer.flip();
        this.byteBuffer.flip();
    }

    public void dispose() {
        BufferUtils.disposeUnsafeByteBuffer(this.byteBuffer);
    }

    public FloatBuffer getBuffer() {
        return this.buffer;
    }

    public int getNumVertices() {
        return (this.buffer.limit() * 4) / this.attributes.vertexSize;
    }

    public int getNumMaxVertices() {
        return this.byteBuffer.capacity() / this.attributes.vertexSize;
    }

    public void setVertices(float[] vertices, int offset, int count) {
        BufferUtils.copy(vertices, this.byteBuffer, count, offset);
        this.buffer.position(0);
        this.buffer.limit(count);
    }

    public void bind() {
        GL10 gl = Gdx.gl10;
        int textureUnit = 0;
        int numAttributes = this.attributes.size();
        this.byteBuffer.limit(this.buffer.limit() * 4);
        for (int i = 0; i < numAttributes; i++) {
            VertexAttribute attribute = this.attributes.get(i);
            switch (attribute.usage) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    this.byteBuffer.position(attribute.offset);
                    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                    gl.glVertexPointer(attribute.numComponents, GL20.GL_FLOAT, this.attributes.vertexSize, this.byteBuffer);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    int colorType = GL20.GL_FLOAT;
                    if (attribute.usage == 4) {
                        colorType = GL20.GL_UNSIGNED_BYTE;
                    }
                    this.byteBuffer.position(attribute.offset);
                    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
                    gl.glColorPointer(attribute.numComponents, colorType, this.attributes.vertexSize, this.byteBuffer);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    this.byteBuffer.position(attribute.offset);
                    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
                    gl.glNormalPointer(GL20.GL_FLOAT, this.attributes.vertexSize, this.byteBuffer);
                    break;
                case Place.TYPE_CAMPGROUND /*16*/:
                    gl.glClientActiveTexture(GL20.GL_TEXTURE0 + textureUnit);
                    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    this.byteBuffer.position(attribute.offset);
                    gl.glTexCoordPointer(attribute.numComponents, GL20.GL_FLOAT, this.attributes.vertexSize, this.byteBuffer);
                    textureUnit++;
                    break;
                default:
                    break;
            }
        }
        this.isBound = true;
    }

    public void unbind() {
        GL10 gl = Gdx.gl10;
        int textureUnit = 0;
        int numAttributes = this.attributes.size();
        for (int i = 0; i < numAttributes; i++) {
            switch (this.attributes.get(i).usage) {
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
                    break;
                case Place.TYPE_CAMPGROUND /*16*/:
                    gl.glClientActiveTexture(GL20.GL_TEXTURE0 + textureUnit);
                    gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    textureUnit++;
                    break;
                default:
                    break;
            }
        }
        this.byteBuffer.position(0);
        this.isBound = false;
    }

    public void bind(ShaderProgram shader) {
        bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        GL20 gl = Gdx.gl20;
        int numAttributes = this.attributes.size();
        this.byteBuffer.limit(this.buffer.limit() * 4);
        int i;
        VertexAttribute attribute;
        int location;
        if (locations == null) {
            for (i = 0; i < numAttributes; i++) {
                attribute = this.attributes.get(i);
                location = shader.getAttributeLocation(attribute.alias);
                if (location >= 0) {
                    shader.enableVertexAttribute(location);
                    this.byteBuffer.position(attribute.offset);
                    if (attribute.usage == 4) {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_UNSIGNED_BYTE, true, this.attributes.vertexSize, this.byteBuffer);
                    } else {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_FLOAT, false, this.attributes.vertexSize, this.byteBuffer);
                    }
                }
            }
        } else {
            for (i = 0; i < numAttributes; i++) {
                attribute = this.attributes.get(i);
                location = locations[i];
                if (location >= 0) {
                    shader.enableVertexAttribute(location);
                    this.byteBuffer.position(attribute.offset);
                    if (attribute.usage == 4) {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_UNSIGNED_BYTE, true, this.attributes.vertexSize, this.byteBuffer);
                    } else {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_FLOAT, false, this.attributes.vertexSize, this.byteBuffer);
                    }
                }
            }
        }
        this.isBound = true;
    }

    public void unbind(ShaderProgram shader) {
        unbind(shader, null);
    }

    public void unbind(ShaderProgram shader, int[] locations) {
        GL20 gl = Gdx.gl20;
        int numAttributes = this.attributes.size();
        int i;
        if (locations == null) {
            for (i = 0; i < numAttributes; i++) {
                shader.disableVertexAttribute(this.attributes.get(i).alias);
            }
        } else {
            for (i = 0; i < numAttributes; i++) {
                int location = locations[i];
                if (location >= 0) {
                    shader.disableVertexAttribute(location);
                }
            }
        }
        this.isBound = false;
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }
}
