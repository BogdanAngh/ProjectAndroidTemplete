package com.badlogic.gdx.graphics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class Cubemap extends GLTexture {
    protected final TextureData[] data;

    public enum CubemapSide {
        PositiveX(0, GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_X),
        NegativeX(1, GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_X),
        PositiveY(2, GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Y),
        NegativeY(3, GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Y),
        PositiveZ(4, GL20.GL_TEXTURE_CUBE_MAP_POSITIVE_Z),
        NegativeZ(5, GL20.GL_TEXTURE_CUBE_MAP_NEGATIVE_Z);
        
        public final int glEnum;
        public final int index;

        private CubemapSide(int index, int glEnum) {
            this.index = index;
            this.glEnum = glEnum;
        }

        public int getGLEnum() {
            return this.glEnum;
        }
    }

    public Cubemap() {
        this((TextureData) null, (TextureData) null, (TextureData) null, (TextureData) null, (TextureData) null, (TextureData) null);
    }

    public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public Cubemap(FileHandle positiveX, FileHandle negativeX, FileHandle positiveY, FileHandle negativeY, FileHandle positiveZ, FileHandle negativeZ, boolean useMipMaps) {
        this(GLTexture.createTextureData(positiveX, useMipMaps), GLTexture.createTextureData(negativeX, useMipMaps), GLTexture.createTextureData(positiveY, useMipMaps), GLTexture.createTextureData(negativeY, useMipMaps), GLTexture.createTextureData(positiveZ, useMipMaps), GLTexture.createTextureData(negativeZ, useMipMaps));
    }

    public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ) {
        this(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ, false);
    }

    public Cubemap(Pixmap positiveX, Pixmap negativeX, Pixmap positiveY, Pixmap negativeY, Pixmap positiveZ, Pixmap negativeZ, boolean useMipMaps) {
        TextureData textureData = null;
        TextureData pixmapTextureData = positiveX == null ? null : new PixmapTextureData(positiveX, null, useMipMaps, false);
        TextureData pixmapTextureData2 = negativeX == null ? null : new PixmapTextureData(negativeX, null, useMipMaps, false);
        TextureData pixmapTextureData3 = positiveY == null ? null : new PixmapTextureData(positiveY, null, useMipMaps, false);
        TextureData pixmapTextureData4 = negativeY == null ? null : new PixmapTextureData(negativeY, null, useMipMaps, false);
        TextureData pixmapTextureData5 = positiveZ == null ? null : new PixmapTextureData(positiveZ, null, useMipMaps, false);
        if (negativeZ != null) {
            Object pixmapTextureData6 = new PixmapTextureData(negativeZ, null, useMipMaps, false);
        }
        this(pixmapTextureData, pixmapTextureData2, pixmapTextureData3, pixmapTextureData4, pixmapTextureData5, textureData);
    }

    public Cubemap(int width, int height, int depth, Format format) {
        this(new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(depth, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, depth, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true), new PixmapTextureData(new Pixmap(width, height, format), null, false, true));
    }

    public Cubemap(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
        super(GL20.GL_TEXTURE_CUBE_MAP);
        this.data = new TextureData[6];
        this.minFilter = TextureFilter.Nearest;
        this.magFilter = TextureFilter.Nearest;
        this.uWrap = TextureWrap.ClampToEdge;
        this.vWrap = TextureWrap.ClampToEdge;
        load(positiveX, negativeX, positiveY, negativeY, positiveZ, negativeZ);
    }

    public void load(TextureData positiveX, TextureData negativeX, TextureData positiveY, TextureData negativeY, TextureData positiveZ, TextureData negativeZ) {
        bind();
        unsafeSetFilter(this.minFilter, this.magFilter, true);
        unsafeSetWrap(this.uWrap, this.vWrap, true);
        unsafeLoad(CubemapSide.PositiveX, positiveX);
        unsafeLoad(CubemapSide.NegativeX, negativeX);
        unsafeLoad(CubemapSide.PositiveY, positiveY);
        unsafeLoad(CubemapSide.NegativeY, negativeY);
        unsafeLoad(CubemapSide.PositiveZ, positiveZ);
        unsafeLoad(CubemapSide.NegativeZ, negativeZ);
        Gdx.gl.glBindTexture(this.glTarget, 0);
    }

    public boolean isManaged() {
        for (TextureData data : this.data) {
            if (!data.isManaged()) {
                return false;
            }
        }
        return true;
    }

    protected void reload() {
        if (isManaged()) {
            this.glHandle = GLTexture.createGLHandle();
            load(this.data[CubemapSide.PositiveX.index], this.data[CubemapSide.NegativeX.index], this.data[CubemapSide.PositiveY.index], this.data[CubemapSide.NegativeY.index], this.data[CubemapSide.PositiveZ.index], this.data[CubemapSide.NegativeZ.index]);
            return;
        }
        throw new GdxRuntimeException("Tried to reload an unmanaged Cubemap");
    }

    public void load(CubemapSide side, FileHandle file) {
        load(side, file, false);
    }

    public void load(CubemapSide side, FileHandle file, boolean useMipMaps) {
        load(side, GLTexture.createTextureData(file, useMipMaps));
    }

    public void load(CubemapSide side, Pixmap pixmap) {
        TextureData textureData = null;
        if (pixmap != null) {
            Object pixmapTextureData = new PixmapTextureData(pixmap, null, false, false);
        }
        load(side, textureData);
    }

    public void load(CubemapSide side, Pixmap pixmap, boolean useMipMaps) {
        TextureData textureData = null;
        if (pixmap != null) {
            Object pixmapTextureData = new PixmapTextureData(pixmap, null, useMipMaps, false);
        }
        load(side, textureData);
    }

    public void load(CubemapSide side, TextureData data) {
        bind();
        unsafeLoad(side, data);
        Gdx.gl.glBindTexture(this.glTarget, 0);
    }

    protected void unsafeLoad(CubemapSide side, TextureData data) {
        int idx = side.index;
        if (this.data[idx] == null || data == null || data.isManaged() == this.data[idx].isManaged()) {
            GLTexture.uploadImageData(side.glEnum, data);
            this.data[idx] = data;
            return;
        }
        throw new GdxRuntimeException("New data must have the same managed status as the old data");
    }

    public boolean isComplete() {
        for (TextureData textureData : this.data) {
            if (textureData == null) {
                return false;
            }
        }
        return true;
    }

    public TextureData getTextureData(CubemapSide side) {
        return this.data[side.index];
    }

    public int getWidth() {
        int tmp;
        int width = 0;
        if (this.data[CubemapSide.PositiveZ.index] != null) {
            tmp = this.data[CubemapSide.PositiveZ.index].getWidth();
            if (tmp > 0) {
                width = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeZ.index] != null) {
            tmp = this.data[CubemapSide.NegativeZ.index].getWidth();
            if (tmp > width) {
                width = tmp;
            }
        }
        if (this.data[CubemapSide.PositiveY.index] != null) {
            tmp = this.data[CubemapSide.PositiveY.index].getWidth();
            if (tmp > width) {
                width = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeY.index] == null) {
            return width;
        }
        tmp = this.data[CubemapSide.NegativeY.index].getWidth();
        if (tmp > width) {
            return tmp;
        }
        return width;
    }

    public int getHeight() {
        int tmp;
        int height = 0;
        if (this.data[CubemapSide.PositiveZ.index] != null) {
            tmp = this.data[CubemapSide.PositiveZ.index].getHeight();
            if (tmp > 0) {
                height = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeZ.index] != null) {
            tmp = this.data[CubemapSide.NegativeZ.index].getHeight();
            if (tmp > height) {
                height = tmp;
            }
        }
        if (this.data[CubemapSide.PositiveX.index] != null) {
            tmp = this.data[CubemapSide.PositiveX.index].getHeight();
            if (tmp > height) {
                height = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeX.index] == null) {
            return height;
        }
        tmp = this.data[CubemapSide.NegativeX.index].getHeight();
        if (tmp > height) {
            return tmp;
        }
        return height;
    }

    public int getDepth() {
        int tmp;
        int depth = 0;
        if (this.data[CubemapSide.PositiveX.index] != null) {
            tmp = this.data[CubemapSide.PositiveX.index].getWidth();
            if (tmp > 0) {
                depth = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeX.index] != null) {
            tmp = this.data[CubemapSide.NegativeX.index].getWidth();
            if (tmp > depth) {
                depth = tmp;
            }
        }
        if (this.data[CubemapSide.PositiveY.index] != null) {
            tmp = this.data[CubemapSide.PositiveY.index].getHeight();
            if (tmp > depth) {
                depth = tmp;
            }
        }
        if (this.data[CubemapSide.NegativeY.index] == null) {
            return depth;
        }
        tmp = this.data[CubemapSide.NegativeY.index].getHeight();
        if (tmp > depth) {
            return tmp;
        }
        return depth;
    }
}
