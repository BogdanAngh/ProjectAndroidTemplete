package com.badlogic.gdx.graphics.glutils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

public class VertexBufferObjectSubData implements VertexData {
    static final IntBuffer tmpHandle;
    final VertexAttributes attributes;
    final FloatBuffer buffer;
    int bufferHandle;
    final ByteBuffer byteBuffer;
    boolean isBound;
    final boolean isDirect;
    boolean isDirty;
    final boolean isStatic;
    final int usage;

    static {
        tmpHandle = BufferUtils.newIntBuffer(1);
    }

    public VertexBufferObjectSubData(boolean isStatic, int numVertices, VertexAttribute... attributes) {
        this.isDirty = false;
        this.isBound = false;
        this.isStatic = isStatic;
        this.attributes = new VertexAttributes(attributes);
        this.byteBuffer = BufferUtils.newByteBuffer(this.attributes.vertexSize * numVertices);
        this.isDirect = true;
        this.usage = isStatic ? GL11.GL_STATIC_DRAW : GL11.GL_DYNAMIC_DRAW;
        this.buffer = this.byteBuffer.asFloatBuffer();
        this.bufferHandle = createBufferObject();
        this.buffer.flip();
        this.byteBuffer.flip();
    }

    private int createBufferObject() {
        if (Gdx.gl20 != null) {
            Gdx.gl20.glGenBuffers(1, tmpHandle);
            Gdx.gl20.glBindBuffer(GL11.GL_ARRAY_BUFFER, tmpHandle.get(0));
            Gdx.gl20.glBufferData(GL11.GL_ARRAY_BUFFER, this.byteBuffer.capacity(), null, this.usage);
            Gdx.gl20.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        } else {
            Gdx.gl11.glGenBuffers(1, tmpHandle);
            Gdx.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, tmpHandle.get(0));
            Gdx.gl11.glBufferData(GL11.GL_ARRAY_BUFFER, this.byteBuffer.capacity(), null, this.usage);
            Gdx.gl11.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        }
        return tmpHandle.get(0);
    }

    public VertexAttributes getAttributes() {
        return this.attributes;
    }

    public int getNumVertices() {
        return (this.buffer.limit() * 4) / this.attributes.vertexSize;
    }

    public int getNumMaxVertices() {
        return this.byteBuffer.capacity() / this.attributes.vertexSize;
    }

    public FloatBuffer getBuffer() {
        this.isDirty = true;
        return this.buffer;
    }

    public void setVertices(float[] vertices, int offset, int count) {
        this.isDirty = true;
        if (this.isDirect) {
            BufferUtils.copy(vertices, this.byteBuffer, count, offset);
            this.buffer.position(0);
            this.buffer.limit(count);
        } else {
            this.buffer.clear();
            this.buffer.put(vertices, offset, count);
            this.buffer.flip();
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.buffer.limit() << 2);
        }
        if (this.isBound) {
            if (Gdx.gl20 != null) {
                Gdx.gl20.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, this.byteBuffer.limit(), this.byteBuffer);
            } else {
                Gdx.gl11.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, this.byteBuffer.limit(), this.byteBuffer);
            }
            this.isDirty = false;
        }
    }

    public void bind() {
        GL11 gl = Gdx.gl11;
        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.bufferHandle);
        if (this.isDirty) {
            this.byteBuffer.limit(this.buffer.limit() * 4);
            gl.glBufferSubData(GL11.GL_ARRAY_BUFFER, 0, this.byteBuffer.limit(), this.byteBuffer);
            this.isDirty = false;
        }
        int textureUnit = 0;
        int numAttributes = this.attributes.size();
        for (int i = 0; i < numAttributes; i++) {
            VertexAttribute attribute = this.attributes.get(i);
            switch (attribute.usage) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
                    gl.glVertexPointer(attribute.numComponents, GL20.GL_FLOAT, this.attributes.vertexSize, attribute.offset);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    int colorType = GL20.GL_FLOAT;
                    if (attribute.usage == 4) {
                        colorType = GL20.GL_UNSIGNED_BYTE;
                    }
                    gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
                    gl.glColorPointer(attribute.numComponents, colorType, this.attributes.vertexSize, attribute.offset);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
                    gl.glNormalPointer(GL20.GL_FLOAT, this.attributes.vertexSize, attribute.offset);
                    break;
                case Place.TYPE_CAMPGROUND /*16*/:
                    gl.glClientActiveTexture(GL20.GL_TEXTURE0 + textureUnit);
                    gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
                    gl.glTexCoordPointer(attribute.numComponents, GL20.GL_FLOAT, this.attributes.vertexSize, attribute.offset);
                    textureUnit++;
                    break;
                default:
                    throw new GdxRuntimeException("unkown vertex attribute type: " + attribute.usage);
            }
        }
        this.isBound = true;
    }

    public void bind(ShaderProgram shader) {
        bind(shader, null);
    }

    public void bind(ShaderProgram shader, int[] locations) {
        GL20 gl = Gdx.gl20;
        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, this.bufferHandle);
        if (this.isDirty) {
            this.byteBuffer.limit(this.buffer.limit() * 4);
            gl.glBufferData(GL11.GL_ARRAY_BUFFER, this.byteBuffer.limit(), this.byteBuffer, this.usage);
            this.isDirty = false;
        }
        int numAttributes = this.attributes.size();
        int i;
        VertexAttribute attribute;
        int location;
        if (locations == null) {
            for (i = 0; i < numAttributes; i++) {
                attribute = this.attributes.get(i);
                location = shader.getAttributeLocation(attribute.alias);
                if (location >= 0) {
                    shader.enableVertexAttribute(location);
                    if (attribute.usage == 4) {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_UNSIGNED_BYTE, true, this.attributes.vertexSize, attribute.offset);
                    } else {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_FLOAT, false, this.attributes.vertexSize, attribute.offset);
                    }
                }
            }
        } else {
            for (i = 0; i < numAttributes; i++) {
                attribute = this.attributes.get(i);
                location = locations[i];
                if (location >= 0) {
                    shader.enableVertexAttribute(location);
                    if (attribute.usage == 4) {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_UNSIGNED_BYTE, true, this.attributes.vertexSize, attribute.offset);
                    } else {
                        shader.setVertexAttribute(location, attribute.numComponents, (int) GL20.GL_FLOAT, false, this.attributes.vertexSize, attribute.offset);
                    }
                }
            }
        }
        this.isBound = true;
    }

    public void unbind() {
        GL11 gl = Gdx.gl11;
        int textureUnit = 0;
        int numAttributes = this.attributes.size();
        for (int i = 0; i < numAttributes; i++) {
            VertexAttribute attribute = this.attributes.get(i);
            switch (attribute.usage) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    break;
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
                    throw new GdxRuntimeException("unkown vertex attribute type: " + attribute.usage);
            }
        }
        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        this.isBound = false;
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
        gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        this.isBound = false;
    }

    public void invalidate() {
        this.bufferHandle = createBufferObject();
        this.isDirty = true;
    }

    public void dispose() {
        if (Gdx.gl20 != null) {
            tmpHandle.clear();
            tmpHandle.put(this.bufferHandle);
            tmpHandle.flip();
            GL20 gl = Gdx.gl20;
            gl.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
            gl.glDeleteBuffers(1, tmpHandle);
            this.bufferHandle = 0;
            return;
        }
        tmpHandle.clear();
        tmpHandle.put(this.bufferHandle);
        tmpHandle.flip();
        GL11 gl2 = Gdx.gl11;
        gl2.glBindBuffer(GL11.GL_ARRAY_BUFFER, 0);
        gl2.glDeleteBuffers(1, tmpHandle);
        this.bufferHandle = 0;
    }

    public int getBufferHandle() {
        return this.bufferHandle;
    }
}
