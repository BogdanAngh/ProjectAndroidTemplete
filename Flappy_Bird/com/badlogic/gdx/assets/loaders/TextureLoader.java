package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.glutils.ETC1TextureData;
import com.badlogic.gdx.graphics.glutils.FileTextureData;
import com.badlogic.gdx.utils.Array;

public class TextureLoader extends AsynchronousAssetLoader<Texture, TextureParameter> {
    TextureLoaderInfo info;

    public static class TextureLoaderInfo {
        TextureData data;
        String filename;
        Texture texture;
    }

    public static class TextureParameter extends AssetLoaderParameters<Texture> {
        public Format format;
        public boolean genMipMaps;
        public TextureFilter magFilter;
        public TextureFilter minFilter;
        public Texture texture;
        public TextureData textureData;
        public TextureWrap wrapU;
        public TextureWrap wrapV;

        public TextureParameter() {
            this.format = null;
            this.genMipMaps = false;
            this.texture = null;
            this.textureData = null;
            this.minFilter = TextureFilter.Nearest;
            this.magFilter = TextureFilter.Nearest;
            this.wrapU = TextureWrap.ClampToEdge;
            this.wrapV = TextureWrap.ClampToEdge;
        }
    }

    public TextureLoader(FileHandleResolver resolver) {
        super(resolver);
        this.info = new TextureLoaderInfo();
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
        this.info.filename = fileName;
        if (parameter == null || parameter.textureData == null) {
            Format format = null;
            boolean genMipMaps = false;
            this.info.texture = null;
            if (parameter != null) {
                format = parameter.format;
                genMipMaps = parameter.genMipMaps;
                this.info.texture = parameter.texture;
            }
            if (fileName.contains(".etc1")) {
                this.info.data = new ETC1TextureData(file, genMipMaps);
                return;
            }
            Pixmap pixmap;
            if (fileName.contains(".cim")) {
                pixmap = PixmapIO.readCIM(file);
            } else {
                pixmap = new Pixmap(file);
            }
            this.info.data = new FileTextureData(file, pixmap, format, genMipMaps);
            return;
        }
        this.info.data = parameter.textureData;
        if (!this.info.data.isPrepared()) {
            this.info.data.prepare();
        }
        this.info.texture = parameter.texture;
    }

    public Texture loadSync(AssetManager manager, String fileName, FileHandle file, TextureParameter parameter) {
        if (this.info == null) {
            return null;
        }
        Texture texture = this.info.texture;
        if (texture != null) {
            texture.load(this.info.data);
        } else {
            texture = new Texture(this.info.data);
        }
        if (parameter == null) {
            return texture;
        }
        texture.setFilter(parameter.minFilter, parameter.magFilter);
        texture.setWrap(parameter.wrapU, parameter.wrapV);
        return texture;
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextureParameter parameter) {
        return null;
    }
}
