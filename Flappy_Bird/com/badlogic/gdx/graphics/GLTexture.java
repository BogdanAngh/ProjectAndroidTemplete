package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Blending;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData.TextureDataType;
import com.badlogic.gdx.graphics.glutils.ETC1TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.graphics.glutils.MipMapGenerator;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.BufferUtils;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.nio.IntBuffer;

public abstract class GLTexture implements Disposable {
    private static final IntBuffer buffer;
    private static boolean enforcePotImages;
    protected int glHandle;
    public final int glTarget;
    protected TextureFilter magFilter;
    protected TextureFilter minFilter;
    protected TextureWrap uWrap;
    protected TextureWrap vWrap;

    public abstract int getDepth();

    public abstract int getHeight();

    public abstract int getWidth();

    public abstract boolean isManaged();

    protected abstract void reload();

    public GLTexture(int glTarget) {
        this(glTarget, createGLHandle());
    }

    public GLTexture(int glTarget, int glHandle) {
        this.minFilter = TextureFilter.Nearest;
        this.magFilter = TextureFilter.Nearest;
        this.uWrap = TextureWrap.ClampToEdge;
        this.vWrap = TextureWrap.ClampToEdge;
        this.glTarget = glTarget;
        this.glHandle = glHandle;
    }

    public void bind() {
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    public void bind(int unit) {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0 + unit);
        Gdx.gl.glBindTexture(this.glTarget, this.glHandle);
    }

    public TextureFilter getMinFilter() {
        return this.minFilter;
    }

    public TextureFilter getMagFilter() {
        return this.magFilter;
    }

    public TextureWrap getUWrap() {
        return this.uWrap;
    }

    public TextureWrap getVWrap() {
        return this.vWrap;
    }

    public int getTextureObjectHandle() {
        return this.glHandle;
    }

    public void unsafeSetWrap(TextureWrap u, TextureWrap v) {
        unsafeSetWrap(u, v, false);
    }

    public void unsafeSetWrap(TextureWrap u, TextureWrap v, boolean force) {
        if (u != null && (force || this.uWrap != u)) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_S, (float) u.getGLEnum());
            this.uWrap = u;
        }
        if (v == null) {
            return;
        }
        if (force || this.vWrap != v) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_T, (float) v.getGLEnum());
            this.vWrap = v;
        }
    }

    public void setWrap(TextureWrap u, TextureWrap v) {
        this.uWrap = u;
        this.vWrap = v;
        bind();
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_S, (float) u.getGLEnum());
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_WRAP_T, (float) v.getGLEnum());
    }

    public void unsafeSetFilter(TextureFilter minFilter, TextureFilter magFilter) {
        unsafeSetFilter(minFilter, magFilter, false);
    }

    public void unsafeSetFilter(TextureFilter minFilter, TextureFilter magFilter, boolean force) {
        if (minFilter != null && (force || this.minFilter != minFilter)) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MIN_FILTER, (float) minFilter.getGLEnum());
            this.minFilter = minFilter;
        }
        if (magFilter == null) {
            return;
        }
        if (force || this.magFilter != magFilter) {
            Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MAG_FILTER, (float) magFilter.getGLEnum());
            this.magFilter = magFilter;
        }
    }

    public void setFilter(TextureFilter minFilter, TextureFilter magFilter) {
        this.minFilter = minFilter;
        this.magFilter = magFilter;
        bind();
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MIN_FILTER, (float) minFilter.getGLEnum());
        Gdx.gl.glTexParameterf(this.glTarget, GL20.GL_TEXTURE_MAG_FILTER, (float) magFilter.getGLEnum());
    }

    protected void delete() {
        if (this.glHandle != 0) {
            buffer.put(0, this.glHandle);
            Gdx.gl.glDeleteTextures(1, buffer);
            this.glHandle = 0;
        }
    }

    public void dispose() {
        delete();
    }

    static {
        enforcePotImages = true;
        buffer = BufferUtils.newIntBuffer(1);
    }

    public static void setEnforcePotImages(boolean enforcePotImages) {
        enforcePotImages = enforcePotImages;
    }

    public static boolean getEnforcePotImage() {
        return enforcePotImages;
    }

    protected static TextureData createTextureData(FileHandle file, Format format, boolean useMipMaps) {
        if (file == null) {
            return null;
        }
        if (file.name().endsWith(".etc1")) {
            return new ETC1TextureData(file, useMipMaps);
        }
        return new FileTextureData(file, null, format, useMipMaps);
    }

    protected static TextureData createTextureData(FileHandle file, boolean useMipMaps) {
        return createTextureData(file, null, useMipMaps);
    }

    protected static int createGLHandle() {
        buffer.position(0);
        buffer.limit(buffer.capacity());
        Gdx.gl.glGenTextures(1, buffer);
        return buffer.get(0);
    }

    protected static void uploadImageData(int target, TextureData data) {
        if (data != null) {
            if (!data.isPrepared()) {
                data.prepare();
            }
            if (enforcePotImages && Gdx.gl20 == null && (!MathUtils.isPowerOfTwo(data.getWidth()) || !MathUtils.isPowerOfTwo(data.getHeight()))) {
                throw new GdxRuntimeException("Texture width and height must be powers of two: " + data.getWidth() + "x" + data.getHeight());
            }
            TextureDataType type = data.getType();
            if (type == TextureDataType.Compressed || type == TextureDataType.Float) {
                data.consumeCompressedData(target);
                return;
            }
            Pixmap pixmap = data.consumePixmap();
            boolean disposePixmap = data.disposePixmap();
            if (data.getFormat() != pixmap.getFormat()) {
                Pixmap tmp = new Pixmap(pixmap.getWidth(), pixmap.getHeight(), data.getFormat());
                Blending blend = Pixmap.getBlending();
                Pixmap.setBlending(Blending.None);
                tmp.drawPixmap(pixmap, 0, 0, 0, 0, pixmap.getWidth(), pixmap.getHeight());
                Pixmap.setBlending(blend);
                if (data.disposePixmap()) {
                    pixmap.dispose();
                }
                pixmap = tmp;
                disposePixmap = true;
            }
            Gdx.gl.glPixelStorei(GL20.GL_UNPACK_ALIGNMENT, 1);
            if (data.useMipMaps()) {
                MipMapGenerator.generateMipMap(target, pixmap, pixmap.getWidth(), pixmap.getHeight());
            } else {
                Gdx.gl.glTexImage2D(target, 0, pixmap.getGLInternalFormat(), pixmap.getWidth(), pixmap.getHeight(), 0, pixmap.getGLFormat(), pixmap.getGLType(), pixmap.getPixels());
            }
            if (disposePixmap) {
                pixmap.dispose();
            }
        }
    }
}
