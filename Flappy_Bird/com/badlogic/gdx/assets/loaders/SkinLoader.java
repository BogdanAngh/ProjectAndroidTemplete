package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class SkinLoader extends AsynchronousAssetLoader<Skin, SkinParameter> {

    public static class SkinParameter extends AssetLoaderParameters<Skin> {
        public final String textureAtlasPath;

        public SkinParameter(String textureAtlasPath) {
            this.textureAtlasPath = textureAtlasPath;
        }
    }

    public SkinLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, SkinParameter parameter) {
        Array<AssetDescriptor> deps = new Array();
        if (parameter == null) {
            deps.add(new AssetDescriptor(file.pathWithoutExtension() + ".atlas", TextureAtlas.class));
        } else if (parameter.textureAtlasPath != null) {
            deps.add(new AssetDescriptor(parameter.textureAtlasPath, TextureAtlas.class));
        }
        return deps;
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, SkinParameter parameter) {
    }

    public Skin loadSync(AssetManager manager, String fileName, FileHandle file, SkinParameter parameter) {
        String textureAtlasPath;
        if (parameter == null) {
            textureAtlasPath = file.pathWithoutExtension() + ".atlas";
        } else {
            textureAtlasPath = parameter.textureAtlasPath;
        }
        return new Skin(file, (TextureAtlas) manager.get(textureAtlasPath, TextureAtlas.class));
    }
}
