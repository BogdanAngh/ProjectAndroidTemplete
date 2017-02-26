package com.badlogic.gdx.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.model.data.ModelData;
import com.badlogic.gdx.graphics.g3d.model.data.ModelMaterial;
import com.badlogic.gdx.graphics.g3d.model.data.ModelTexture;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider;
import com.badlogic.gdx.graphics.g3d.utils.TextureProvider.FileTextureProvider;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import java.util.Iterator;

public abstract class ModelLoader<P extends AssetLoaderParameters<Model>> extends AsynchronousAssetLoader<Model, P> {
    protected Array<Entry<String, ModelData>> items;

    public abstract ModelData loadModelData(FileHandle fileHandle, P p);

    public ModelLoader(FileHandleResolver resolver) {
        super(resolver);
        this.items = new Array();
    }

    public ModelData loadModelData(FileHandle fileHandle) {
        return loadModelData(fileHandle, null);
    }

    public Model loadModel(FileHandle fileHandle, TextureProvider textureProvider, P parameters) {
        ModelData data = loadModelData(fileHandle, parameters);
        return data == null ? null : new Model(data, textureProvider);
    }

    public Model loadModel(FileHandle fileHandle, P parameters) {
        return loadModel(fileHandle, new FileTextureProvider(), parameters);
    }

    public Model loadModel(FileHandle fileHandle, TextureProvider textureProvider) {
        return loadModel(fileHandle, textureProvider, null);
    }

    public Model loadModel(FileHandle fileHandle) {
        return loadModel(fileHandle, new FileTextureProvider(), null);
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, P parameters) {
        Array<AssetDescriptor> deps = new Array();
        ModelData data = loadModelData(file, parameters);
        if (data != null) {
            Entry<String, ModelData> item = new Entry();
            item.key = fileName;
            item.value = data;
            synchronized (this.items) {
                this.items.add(item);
            }
            Iterator it = data.materials.iterator();
            while (it.hasNext()) {
                ModelMaterial modelMaterial = (ModelMaterial) it.next();
                if (modelMaterial.textures != null) {
                    Iterator i$ = modelMaterial.textures.iterator();
                    while (i$.hasNext()) {
                        deps.add(new AssetDescriptor(((ModelTexture) i$.next()).fileName, Texture.class));
                    }
                }
            }
        }
        return deps;
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, P p) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.badlogic.gdx.graphics.g3d.Model loadSync(com.badlogic.gdx.assets.AssetManager r9, java.lang.String r10, com.badlogic.gdx.files.FileHandle r11, P r12) {
        /*
        r8 = this;
        r1 = 0;
        r7 = r8.items;
        monitor-enter(r7);
        r4 = 0;
    L_0x0005:
        r6 = r8.items;	 Catch:{ all -> 0x0038 }
        r6 = r6.size;	 Catch:{ all -> 0x0038 }
        if (r4 >= r6) goto L_0x0033;
    L_0x000b:
        r6 = r8.items;	 Catch:{ all -> 0x0038 }
        r6 = r6.get(r4);	 Catch:{ all -> 0x0038 }
        r6 = (com.badlogic.gdx.utils.ObjectMap.Entry) r6;	 Catch:{ all -> 0x0038 }
        r6 = r6.key;	 Catch:{ all -> 0x0038 }
        r6 = (java.lang.String) r6;	 Catch:{ all -> 0x0038 }
        r6 = r6.equals(r10);	 Catch:{ all -> 0x0038 }
        if (r6 == 0) goto L_0x0030;
    L_0x001d:
        r6 = r8.items;	 Catch:{ all -> 0x0038 }
        r6 = r6.get(r4);	 Catch:{ all -> 0x0038 }
        r6 = (com.badlogic.gdx.utils.ObjectMap.Entry) r6;	 Catch:{ all -> 0x0038 }
        r6 = r6.value;	 Catch:{ all -> 0x0038 }
        r0 = r6;
        r0 = (com.badlogic.gdx.graphics.g3d.model.data.ModelData) r0;	 Catch:{ all -> 0x0038 }
        r1 = r0;
        r6 = r8.items;	 Catch:{ all -> 0x0038 }
        r6.removeIndex(r4);	 Catch:{ all -> 0x0038 }
    L_0x0030:
        r4 = r4 + 1;
        goto L_0x0005;
    L_0x0033:
        monitor-exit(r7);	 Catch:{ all -> 0x0038 }
        if (r1 != 0) goto L_0x003b;
    L_0x0036:
        r5 = 0;
    L_0x0037:
        return r5;
    L_0x0038:
        r6 = move-exception;
        monitor-exit(r7);	 Catch:{ all -> 0x0038 }
        throw r6;
    L_0x003b:
        r5 = new com.badlogic.gdx.graphics.g3d.Model;
        r6 = new com.badlogic.gdx.graphics.g3d.utils.TextureProvider$AssetTextureProvider;
        r6.<init>(r9);
        r5.<init>(r1, r6);
        r6 = r5.getManagedDisposables();
        r3 = r6.iterator();
    L_0x004d:
        r6 = r3.hasNext();
        if (r6 == 0) goto L_0x0061;
    L_0x0053:
        r2 = r3.next();
        r2 = (com.badlogic.gdx.utils.Disposable) r2;
        r6 = r2 instanceof com.badlogic.gdx.graphics.Texture;
        if (r6 == 0) goto L_0x004d;
    L_0x005d:
        r3.remove();
        goto L_0x004d;
    L_0x0061:
        r1 = 0;
        goto L_0x0037;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.loaders.ModelLoader.loadSync(com.badlogic.gdx.assets.AssetManager, java.lang.String, com.badlogic.gdx.files.FileHandle, com.badlogic.gdx.assets.AssetLoaderParameters):com.badlogic.gdx.graphics.g3d.Model");
    }
}
