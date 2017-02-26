package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class BitmapFontLoader extends AsynchronousAssetLoader<BitmapFont, BitmapFontParameter> {
    BitmapFontData data;

    public static class BitmapFontParameter extends AssetLoaderParameters<BitmapFont> {
        public BitmapFontData bitmapFontData;
        public boolean flip;
        public TextureFilter maxFilter;
        public TextureFilter minFilter;

        public BitmapFontParameter() {
            this.flip = false;
            this.minFilter = TextureFilter.Nearest;
            this.maxFilter = TextureFilter.Nearest;
            this.bitmapFontData = null;
        }
    }

    public BitmapFontLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, BitmapFontParameter parameter) {
        Array<AssetDescriptor> deps = new Array();
        if (parameter == null || parameter.bitmapFontData == null) {
            this.data = new BitmapFontData(file, parameter != null ? parameter.flip : false);
            for (int i = 0; i < this.data.getImagePaths().length; i++) {
                deps.add(new AssetDescriptor(this.data.getImagePath(i), Texture.class));
            }
        } else {
            this.data = parameter.bitmapFontData;
        }
        return deps;
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, BitmapFontParameter parameter) {
    }

    public BitmapFont loadSync(AssetManager manager, String fileName, FileHandle file, BitmapFontParameter parameter) {
        TextureRegion[] regs = new TextureRegion[this.data.getImagePaths().length];
        for (int i = 0; i < regs.length; i++) {
            TextureRegion region = new TextureRegion((Texture) manager.get(this.data.getImagePath(i), Texture.class));
            if (parameter != null) {
                region.getTexture().setFilter(parameter.minFilter, parameter.maxFilter);
            }
            regs[i] = region;
        }
        return new BitmapFont(this.data, regs, true);
    }
}
