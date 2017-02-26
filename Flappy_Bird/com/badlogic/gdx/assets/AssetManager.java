package com.badlogic.gdx.assets;

import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.MusicLoader;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.PixmapLoader;
import com.badlogic.gdx.assets.loaders.SkinLoader;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.loader.ObjLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.UBJsonReader;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.ThreadUtils;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;
import java.util.Stack;

public class AssetManager implements Disposable {
    final ObjectMap<String, Array<String>> assetDependencies;
    final ObjectMap<String, Class> assetTypes;
    final ObjectMap<Class, ObjectMap<String, RefCountedContainer>> assets;
    final AsyncExecutor executor;
    AssetErrorListener listener;
    final Array<AssetDescriptor> loadQueue;
    int loaded;
    final ObjectMap<Class, ObjectMap<String, AssetLoader>> loaders;
    Logger log;
    Stack<AssetLoadingTask> tasks;
    int toLoad;

    public AssetManager() {
        this(new InternalFileHandleResolver());
    }

    public AssetManager(FileHandleResolver resolver) {
        this.assets = new ObjectMap();
        this.assetTypes = new ObjectMap();
        this.assetDependencies = new ObjectMap();
        this.loaders = new ObjectMap();
        this.loadQueue = new Array();
        this.tasks = new Stack();
        this.listener = null;
        this.loaded = 0;
        this.toLoad = 0;
        this.log = new Logger("AssetManager", 0);
        setLoader(BitmapFont.class, new BitmapFontLoader(resolver));
        setLoader(Music.class, new MusicLoader(resolver));
        setLoader(Pixmap.class, new PixmapLoader(resolver));
        setLoader(Sound.class, new SoundLoader(resolver));
        setLoader(TextureAtlas.class, new TextureAtlasLoader(resolver));
        setLoader(Texture.class, new TextureLoader(resolver));
        setLoader(Skin.class, new SkinLoader(resolver));
        setLoader(ParticleEffect.class, new ParticleEffectLoader(resolver));
        setLoader(Model.class, ".g3dj", new G3dModelLoader(new JsonReader(), resolver));
        setLoader(Model.class, ".g3db", new G3dModelLoader(new UBJsonReader(), resolver));
        setLoader(Model.class, ".obj", new ObjLoader(resolver));
        this.executor = new AsyncExecutor(1);
    }

    public synchronized <T> T get(String fileName) {
        T asset;
        Class<T> type = (Class) this.assetTypes.get(fileName);
        ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
        if (assetsByType == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        RefCountedContainer assetContainer = (RefCountedContainer) assetsByType.get(fileName);
        if (assetContainer == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        asset = assetContainer.getObject(type);
        if (asset == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        return asset;
    }

    public synchronized <T> T get(String fileName, Class<T> type) {
        T asset;
        ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
        if (assetsByType == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        RefCountedContainer assetContainer = (RefCountedContainer) assetsByType.get(fileName);
        if (assetContainer == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        asset = assetContainer.getObject(type);
        if (asset == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        return asset;
    }

    public synchronized <T> T get(AssetDescriptor<T> assetDescriptor) {
        return get(assetDescriptor.fileName, assetDescriptor.type);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized void unload(java.lang.String r12) {
        /*
        r11 = this;
        monitor-enter(r11);
        r4 = -1;
        r5 = 0;
    L_0x0003:
        r8 = r11.loadQueue;	 Catch:{ all -> 0x0075 }
        r8 = r8.size;	 Catch:{ all -> 0x0075 }
        if (r5 >= r8) goto L_0x001a;
    L_0x0009:
        r8 = r11.loadQueue;	 Catch:{ all -> 0x0075 }
        r8 = r8.get(r5);	 Catch:{ all -> 0x0075 }
        r8 = (com.badlogic.gdx.assets.AssetDescriptor) r8;	 Catch:{ all -> 0x0075 }
        r8 = r8.fileName;	 Catch:{ all -> 0x0075 }
        r8 = r8.equals(r12);	 Catch:{ all -> 0x0075 }
        if (r8 == 0) goto L_0x003c;
    L_0x0019:
        r4 = r5;
    L_0x001a:
        r8 = -1;
        if (r4 == r8) goto L_0x003f;
    L_0x001d:
        r8 = r11.loadQueue;	 Catch:{ all -> 0x0075 }
        r8.removeIndex(r4);	 Catch:{ all -> 0x0075 }
        r8 = r11.log;	 Catch:{ all -> 0x0075 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r9.<init>();	 Catch:{ all -> 0x0075 }
        r10 = "Unload (from queue): ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0075 }
        r9 = r9.append(r12);	 Catch:{ all -> 0x0075 }
        r9 = r9.toString();	 Catch:{ all -> 0x0075 }
        r8.debug(r9);	 Catch:{ all -> 0x0075 }
    L_0x003a:
        monitor-exit(r11);
        return;
    L_0x003c:
        r5 = r5 + 1;
        goto L_0x0003;
    L_0x003f:
        r8 = r11.tasks;	 Catch:{ all -> 0x0075 }
        r8 = r8.size();	 Catch:{ all -> 0x0075 }
        if (r8 <= 0) goto L_0x0078;
    L_0x0047:
        r8 = r11.tasks;	 Catch:{ all -> 0x0075 }
        r1 = r8.firstElement();	 Catch:{ all -> 0x0075 }
        r1 = (com.badlogic.gdx.assets.AssetLoadingTask) r1;	 Catch:{ all -> 0x0075 }
        r8 = r1.assetDesc;	 Catch:{ all -> 0x0075 }
        r8 = r8.fileName;	 Catch:{ all -> 0x0075 }
        r8 = r8.equals(r12);	 Catch:{ all -> 0x0075 }
        if (r8 == 0) goto L_0x0078;
    L_0x0059:
        r8 = 1;
        r1.cancel = r8;	 Catch:{ all -> 0x0075 }
        r8 = r11.log;	 Catch:{ all -> 0x0075 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r9.<init>();	 Catch:{ all -> 0x0075 }
        r10 = "Unload (from tasks): ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0075 }
        r9 = r9.append(r12);	 Catch:{ all -> 0x0075 }
        r9 = r9.toString();	 Catch:{ all -> 0x0075 }
        r8.debug(r9);	 Catch:{ all -> 0x0075 }
        goto L_0x003a;
    L_0x0075:
        r8 = move-exception;
        monitor-exit(r11);
        throw r8;
    L_0x0078:
        r8 = r11.assetTypes;	 Catch:{ all -> 0x0075 }
        r7 = r8.get(r12);	 Catch:{ all -> 0x0075 }
        r7 = (java.lang.Class) r7;	 Catch:{ all -> 0x0075 }
        if (r7 != 0) goto L_0x009b;
    L_0x0082:
        r8 = new com.badlogic.gdx.utils.GdxRuntimeException;	 Catch:{ all -> 0x0075 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r9.<init>();	 Catch:{ all -> 0x0075 }
        r10 = "Asset not loaded: ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0075 }
        r9 = r9.append(r12);	 Catch:{ all -> 0x0075 }
        r9 = r9.toString();	 Catch:{ all -> 0x0075 }
        r8.<init>(r9);	 Catch:{ all -> 0x0075 }
        throw r8;	 Catch:{ all -> 0x0075 }
    L_0x009b:
        r8 = r11.assets;	 Catch:{ all -> 0x0075 }
        r8 = r8.get(r7);	 Catch:{ all -> 0x0075 }
        r8 = (com.badlogic.gdx.utils.ObjectMap) r8;	 Catch:{ all -> 0x0075 }
        r0 = r8.get(r12);	 Catch:{ all -> 0x0075 }
        r0 = (com.badlogic.gdx.assets.RefCountedContainer) r0;	 Catch:{ all -> 0x0075 }
        r0.decRefCount();	 Catch:{ all -> 0x0075 }
        r8 = r0.getRefCount();	 Catch:{ all -> 0x0075 }
        if (r8 > 0) goto L_0x010d;
    L_0x00b2:
        r8 = r11.log;	 Catch:{ all -> 0x0075 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r9.<init>();	 Catch:{ all -> 0x0075 }
        r10 = "Unload (dispose): ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0075 }
        r9 = r9.append(r12);	 Catch:{ all -> 0x0075 }
        r9 = r9.toString();	 Catch:{ all -> 0x0075 }
        r8.debug(r9);	 Catch:{ all -> 0x0075 }
        r8 = java.lang.Object.class;
        r8 = r0.getObject(r8);	 Catch:{ all -> 0x0075 }
        r8 = r8 instanceof com.badlogic.gdx.utils.Disposable;	 Catch:{ all -> 0x0075 }
        if (r8 == 0) goto L_0x00df;
    L_0x00d4:
        r8 = java.lang.Object.class;
        r8 = r0.getObject(r8);	 Catch:{ all -> 0x0075 }
        r8 = (com.badlogic.gdx.utils.Disposable) r8;	 Catch:{ all -> 0x0075 }
        r8.dispose();	 Catch:{ all -> 0x0075 }
    L_0x00df:
        r8 = r11.assetTypes;	 Catch:{ all -> 0x0075 }
        r8.remove(r12);	 Catch:{ all -> 0x0075 }
        r8 = r11.assets;	 Catch:{ all -> 0x0075 }
        r8 = r8.get(r7);	 Catch:{ all -> 0x0075 }
        r8 = (com.badlogic.gdx.utils.ObjectMap) r8;	 Catch:{ all -> 0x0075 }
        r8.remove(r12);	 Catch:{ all -> 0x0075 }
    L_0x00ef:
        r8 = r11.assetDependencies;	 Catch:{ all -> 0x0075 }
        r2 = r8.get(r12);	 Catch:{ all -> 0x0075 }
        r2 = (com.badlogic.gdx.utils.Array) r2;	 Catch:{ all -> 0x0075 }
        if (r2 == 0) goto L_0x0126;
    L_0x00f9:
        r6 = r2.iterator();	 Catch:{ all -> 0x0075 }
    L_0x00fd:
        r8 = r6.hasNext();	 Catch:{ all -> 0x0075 }
        if (r8 == 0) goto L_0x0126;
    L_0x0103:
        r3 = r6.next();	 Catch:{ all -> 0x0075 }
        r3 = (java.lang.String) r3;	 Catch:{ all -> 0x0075 }
        r11.unload(r3);	 Catch:{ all -> 0x0075 }
        goto L_0x00fd;
    L_0x010d:
        r8 = r11.log;	 Catch:{ all -> 0x0075 }
        r9 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0075 }
        r9.<init>();	 Catch:{ all -> 0x0075 }
        r10 = "Unload (decrement): ";
        r9 = r9.append(r10);	 Catch:{ all -> 0x0075 }
        r9 = r9.append(r12);	 Catch:{ all -> 0x0075 }
        r9 = r9.toString();	 Catch:{ all -> 0x0075 }
        r8.debug(r9);	 Catch:{ all -> 0x0075 }
        goto L_0x00ef;
    L_0x0126:
        r8 = r0.getRefCount();	 Catch:{ all -> 0x0075 }
        if (r8 > 0) goto L_0x003a;
    L_0x012c:
        r8 = r11.assetDependencies;	 Catch:{ all -> 0x0075 }
        r8.remove(r12);	 Catch:{ all -> 0x0075 }
        goto L_0x003a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.unload(java.lang.String):void");
    }

    public synchronized <T> boolean containsAsset(T asset) {
        boolean z;
        ObjectMap<String, RefCountedContainer> typedAssets = (ObjectMap) this.assets.get(asset.getClass());
        if (typedAssets == null) {
            z = false;
        } else {
            Iterator i$ = typedAssets.keys().iterator();
            while (i$.hasNext()) {
                T otherAsset = ((RefCountedContainer) typedAssets.get((String) i$.next())).getObject(Object.class);
                if (otherAsset != asset) {
                    if (asset.equals(otherAsset)) {
                    }
                }
                z = true;
                break;
            }
            z = false;
        }
        return z;
    }

    public synchronized <T> String getAssetFileName(T asset) {
        String fileName;
        Iterator it = this.assets.keys().iterator();
        loop0:
        while (it.hasNext()) {
            ObjectMap<String, RefCountedContainer> typedAssets = (ObjectMap) this.assets.get((Class) it.next());
            Iterator i$ = typedAssets.keys().iterator();
            while (i$.hasNext()) {
                fileName = (String) i$.next();
                T otherAsset = ((RefCountedContainer) typedAssets.get(fileName)).getObject(Object.class);
                if (otherAsset != asset) {
                    if (asset.equals(otherAsset)) {
                        break loop0;
                    }
                }
                break loop0;
            }
        }
        fileName = null;
        return fileName;
    }

    public synchronized boolean isLoaded(String fileName) {
        boolean z;
        if (fileName == null) {
            z = false;
        } else {
            z = this.assetTypes.containsKey(fileName);
        }
        return z;
    }

    public synchronized boolean isLoaded(String fileName, Class type) {
        boolean z = false;
        synchronized (this) {
            ObjectMap<String, RefCountedContainer> assetsByType = (ObjectMap) this.assets.get(type);
            if (assetsByType != null) {
                RefCountedContainer assetContainer = (RefCountedContainer) assetsByType.get(fileName);
                if (!(assetContainer == null || assetContainer.getObject(type) == null)) {
                    z = true;
                }
            }
        }
        return z;
    }

    public <T> AssetLoader getLoader(Class<T> type) {
        return getLoader(type, null);
    }

    public <T> AssetLoader getLoader(Class<T> type, String fileName) {
        ObjectMap<String, AssetLoader> loaders = (ObjectMap) this.loaders.get(type);
        if (loaders == null || loaders.size < 1) {
            return null;
        }
        if (fileName == null) {
            return (AssetLoader) loaders.get("");
        }
        AssetLoader result = null;
        int l = -1;
        Iterator i$ = loaders.entries().iterator();
        while (i$.hasNext()) {
            Entry<String, AssetLoader> entry = (Entry) i$.next();
            if (((String) entry.key).length() > l && fileName.endsWith((String) entry.key)) {
                result = entry.value;
                l = ((String) entry.key).length();
            }
        }
        return result;
    }

    public synchronized <T> void load(String fileName, Class<T> type) {
        load(fileName, type, null);
    }

    public synchronized <T> void load(String fileName, Class<T> type, AssetLoaderParameters<T> parameter) {
        if (getLoader(type, fileName) == null) {
            throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(type));
        }
        if (this.loadQueue.size == 0) {
            this.loaded = 0;
            this.toLoad = 0;
        }
        int i = 0;
        while (i < this.loadQueue.size) {
            AssetDescriptor desc = (AssetDescriptor) this.loadQueue.get(i);
            if (!desc.fileName.equals(fileName) || desc.type.equals(type)) {
                i++;
            } else {
                throw new GdxRuntimeException("Asset with name '" + fileName + "' already in preload queue, but has different type (expected: " + ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(desc.type) + ")");
            }
        }
        i = 0;
        while (i < this.tasks.size()) {
            desc = ((AssetLoadingTask) this.tasks.get(i)).assetDesc;
            if (!desc.fileName.equals(fileName) || desc.type.equals(type)) {
                i++;
            } else {
                throw new GdxRuntimeException("Asset with name '" + fileName + "' already in task list, but has different type (expected: " + ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(desc.type) + ")");
            }
        }
        Class otherType = (Class) this.assetTypes.get(fileName);
        if (otherType == null || otherType.equals(type)) {
            this.toLoad++;
            AssetDescriptor assetDesc = new AssetDescriptor(fileName, (Class) type, (AssetLoaderParameters) parameter);
            this.loadQueue.add(assetDesc);
            this.log.debug("Queued: " + assetDesc);
        } else {
            throw new GdxRuntimeException("Asset with name '" + fileName + "' already loaded, but has different type (expected: " + ClassReflection.getSimpleName(type) + ", found: " + ClassReflection.getSimpleName(otherType) + ")");
        }
    }

    public synchronized void load(AssetDescriptor desc) {
        load(desc.fileName, desc.type, desc.params);
    }

    private void disposeDependencies(String fileName) {
        Array<String> dependencies = (Array) this.assetDependencies.get(fileName);
        if (dependencies != null) {
            Iterator i$ = dependencies.iterator();
            while (i$.hasNext()) {
                disposeDependencies((String) i$.next());
            }
        }
        Object asset = ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(fileName))).get(fileName)).getObject(Object.class);
        if (asset instanceof Disposable) {
            ((Disposable) asset).dispose();
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized boolean update() {
        /*
        r4 = this;
        r2 = 0;
        r1 = 1;
        monitor-enter(r4);
        r3 = r4.tasks;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size();	 Catch:{ Throwable -> 0x001d }
        if (r3 != 0) goto L_0x0031;
    L_0x000b:
        r3 = r4.loadQueue;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size;	 Catch:{ Throwable -> 0x001d }
        if (r3 == 0) goto L_0x0029;
    L_0x0011:
        r3 = r4.tasks;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size();	 Catch:{ Throwable -> 0x001d }
        if (r3 != 0) goto L_0x0029;
    L_0x0019:
        r4.nextTask();	 Catch:{ Throwable -> 0x001d }
        goto L_0x000b;
    L_0x001d:
        r0 = move-exception;
        r4.handleTaskError(r0);	 Catch:{ all -> 0x0049 }
        r3 = r4.loadQueue;	 Catch:{ all -> 0x0049 }
        r3 = r3.size;	 Catch:{ all -> 0x0049 }
        if (r3 != 0) goto L_0x0047;
    L_0x0027:
        monitor-exit(r4);
        return r1;
    L_0x0029:
        r3 = r4.tasks;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size();	 Catch:{ Throwable -> 0x001d }
        if (r3 == 0) goto L_0x0027;
    L_0x0031:
        r3 = r4.updateTask();	 Catch:{ Throwable -> 0x001d }
        if (r3 == 0) goto L_0x0045;
    L_0x0037:
        r3 = r4.loadQueue;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size;	 Catch:{ Throwable -> 0x001d }
        if (r3 != 0) goto L_0x0045;
    L_0x003d:
        r3 = r4.tasks;	 Catch:{ Throwable -> 0x001d }
        r3 = r3.size();	 Catch:{ Throwable -> 0x001d }
        if (r3 == 0) goto L_0x0027;
    L_0x0045:
        r1 = r2;
        goto L_0x0027;
    L_0x0047:
        r1 = r2;
        goto L_0x0027;
    L_0x0049:
        r1 = move-exception;
        monitor-exit(r4);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.update():boolean");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean update(int r9) {
        /*
        r8 = this;
        r4 = java.lang.System.currentTimeMillis();
        r6 = (long) r9;
        r2 = r4 + r6;
    L_0x0007:
        r0 = r8.update();
        if (r0 != 0) goto L_0x0015;
    L_0x000d:
        r4 = java.lang.System.currentTimeMillis();
        r1 = (r4 > r2 ? 1 : (r4 == r2 ? 0 : -1));
        if (r1 <= 0) goto L_0x0016;
    L_0x0015:
        return r0;
    L_0x0016:
        com.badlogic.gdx.utils.async.ThreadUtils.yield();
        goto L_0x0007;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.badlogic.gdx.assets.AssetManager.update(int):boolean");
    }

    public void finishLoading() {
        this.log.debug("Waiting for loading to complete...");
        while (!update()) {
            ThreadUtils.yield();
        }
        this.log.debug("Loading complete.");
    }

    synchronized void injectDependencies(String parentAssetFilename, Array<AssetDescriptor> dependendAssetDescs) {
        Iterator i$ = dependendAssetDescs.iterator();
        while (i$.hasNext()) {
            injectDependency(parentAssetFilename, (AssetDescriptor) i$.next());
        }
    }

    private synchronized void injectDependency(String parentAssetFilename, AssetDescriptor dependendAssetDesc) {
        Array<String> dependencies = (Array) this.assetDependencies.get(parentAssetFilename);
        if (dependencies == null) {
            dependencies = new Array();
            this.assetDependencies.put(parentAssetFilename, dependencies);
        }
        dependencies.add(dependendAssetDesc.fileName);
        if (isLoaded(dependendAssetDesc.fileName)) {
            this.log.debug("Dependency already loaded: " + dependendAssetDesc);
            ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(dependendAssetDesc.fileName))).get(dependendAssetDesc.fileName)).incRefCount();
            incrementRefCountedDependencies(dependendAssetDesc.fileName);
        } else {
            this.log.info("Loading dependency: " + dependendAssetDesc);
            addTask(dependendAssetDesc);
        }
    }

    private void nextTask() {
        AssetDescriptor assetDesc = (AssetDescriptor) this.loadQueue.removeIndex(0);
        if (isLoaded(assetDesc.fileName)) {
            this.log.debug("Already loaded: " + assetDesc);
            ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(assetDesc.fileName))).get(assetDesc.fileName)).incRefCount();
            incrementRefCountedDependencies(assetDesc.fileName);
            this.loaded++;
            return;
        }
        this.log.info("Loading: " + assetDesc);
        addTask(assetDesc);
    }

    private void addTask(AssetDescriptor assetDesc) {
        AssetLoader loader = getLoader(assetDesc.type, assetDesc.fileName);
        if (loader == null) {
            throw new GdxRuntimeException("No loader for type: " + ClassReflection.getSimpleName(assetDesc.type));
        }
        this.tasks.push(new AssetLoadingTask(this, assetDesc, loader, this.executor));
    }

    protected <T> void addAsset(String fileName, Class<T> type, T asset) {
        this.assetTypes.put(fileName, type);
        ObjectMap<String, RefCountedContainer> typeToAssets = (ObjectMap) this.assets.get(type);
        if (typeToAssets == null) {
            typeToAssets = new ObjectMap();
            this.assets.put(type, typeToAssets);
        }
        typeToAssets.put(fileName, new RefCountedContainer(asset));
    }

    private boolean updateTask() {
        AssetLoadingTask task = (AssetLoadingTask) this.tasks.peek();
        if (!task.update()) {
            return false;
        }
        addAsset(task.assetDesc.fileName, task.assetDesc.type, task.getAsset());
        if (this.tasks.size() == 1) {
            this.loaded++;
        }
        this.tasks.pop();
        if (task.cancel) {
            unload(task.assetDesc.fileName);
            return true;
        }
        if (!(task.assetDesc.params == null || task.assetDesc.params.loadedCallback == null)) {
            task.assetDesc.params.loadedCallback.finishedLoading(this, task.assetDesc.fileName, task.assetDesc.type);
        }
        this.log.debug("Loaded: " + (((float) (TimeUtils.nanoTime() - task.startTime)) / 1000000.0f) + "ms " + task.assetDesc);
        return true;
    }

    private void incrementRefCountedDependencies(String parent) {
        Array<String> dependencies = (Array) this.assetDependencies.get(parent);
        if (dependencies != null) {
            Iterator i$ = dependencies.iterator();
            while (i$.hasNext()) {
                String dependency = (String) i$.next();
                ((RefCountedContainer) ((ObjectMap) this.assets.get((Class) this.assetTypes.get(dependency))).get(dependency)).incRefCount();
                incrementRefCountedDependencies(dependency);
            }
        }
    }

    private void handleTaskError(Throwable t) {
        this.log.error("Error loading asset.", t);
        if (this.tasks.isEmpty()) {
            throw new GdxRuntimeException(t);
        }
        AssetLoadingTask task = (AssetLoadingTask) this.tasks.pop();
        AssetDescriptor assetDesc = task.assetDesc;
        if (task.dependenciesLoaded && task.dependencies != null) {
            Iterator i$ = task.dependencies.iterator();
            while (i$.hasNext()) {
                unload(((AssetDescriptor) i$.next()).fileName);
            }
        }
        this.tasks.clear();
        if (this.listener != null) {
            this.listener.error(assetDesc, t);
            return;
        }
        throw new GdxRuntimeException(t);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, AssetLoader<T, P> loader) {
        setLoader(type, null, loader);
    }

    public synchronized <T, P extends AssetLoaderParameters<T>> void setLoader(Class<T> type, String suffix, AssetLoader<T, P> loader) {
        if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (loader == null) {
            throw new IllegalArgumentException("loader cannot be null.");
        } else {
            this.log.debug("Loader set: " + ClassReflection.getSimpleName(type) + " -> " + ClassReflection.getSimpleName(loader.getClass()));
            ObjectMap<String, AssetLoader> loaders = (ObjectMap) this.loaders.get(type);
            if (loaders == null) {
                ObjectMap objectMap = this.loaders;
                loaders = new ObjectMap();
                objectMap.put(type, loaders);
            }
            if (suffix == null) {
                suffix = "";
            }
            loaders.put(suffix, loader);
        }
    }

    public synchronized int getLoadedAssets() {
        return this.assetTypes.size;
    }

    public synchronized int getQueuedAssets() {
        return this.loadQueue.size + this.tasks.size();
    }

    public synchronized float getProgress() {
        float f = TextTrackStyle.DEFAULT_FONT_SCALE;
        synchronized (this) {
            if (this.toLoad != 0) {
                f = Math.min(TextTrackStyle.DEFAULT_FONT_SCALE, ((float) this.loaded) / ((float) this.toLoad));
            }
        }
        return f;
    }

    public synchronized void setErrorListener(AssetErrorListener listener) {
        this.listener = listener;
    }

    public synchronized void dispose() {
        this.log.debug("Disposing.");
        clear();
        this.executor.dispose();
    }

    public synchronized void clear() {
        this.loadQueue.clear();
        do {
        } while (!update());
        ObjectIntMap<String> dependencyCount = new ObjectIntMap();
        while (this.assetTypes.size > 0) {
            dependencyCount.clear();
            Array<String> assets = this.assetTypes.keys().toArray();
            Iterator i$ = assets.iterator();
            while (i$.hasNext()) {
                dependencyCount.put((String) i$.next(), 0);
            }
            i$ = assets.iterator();
            while (i$.hasNext()) {
                Array<String> dependencies = (Array) this.assetDependencies.get((String) i$.next());
                if (dependencies != null) {
                    Iterator i$2 = dependencies.iterator();
                    while (i$2.hasNext()) {
                        String dependency = (String) i$2.next();
                        dependencyCount.put(dependency, dependencyCount.get(dependency, 0) + 1);
                    }
                }
            }
            i$ = assets.iterator();
            while (i$.hasNext()) {
                String asset = (String) i$.next();
                if (dependencyCount.get(asset, 0) == 0) {
                    unload(asset);
                }
            }
        }
        this.assets.clear();
        this.assetTypes.clear();
        this.assetDependencies.clear();
        this.loaded = 0;
        this.toLoad = 0;
        this.loadQueue.clear();
        this.tasks.clear();
    }

    public Logger getLogger() {
        return this.log;
    }

    public synchronized int getReferenceCount(String fileName) {
        Class type;
        type = (Class) this.assetTypes.get(fileName);
        if (type == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        return ((RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName)).getRefCount();
    }

    public synchronized void setReferenceCount(String fileName, int refCount) {
        Class type = (Class) this.assetTypes.get(fileName);
        if (type == null) {
            throw new GdxRuntimeException("Asset not loaded: " + fileName);
        }
        ((RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName)).setRefCount(refCount);
    }

    public synchronized String getDiagnostics() {
        StringBuffer buffer;
        buffer = new StringBuffer();
        Iterator it = this.assetTypes.keys().iterator();
        while (it.hasNext()) {
            String fileName = (String) it.next();
            buffer.append(fileName);
            buffer.append(", ");
            Class type = (Class) this.assetTypes.get(fileName);
            RefCountedContainer assetRef = (RefCountedContainer) ((ObjectMap) this.assets.get(type)).get(fileName);
            Array<String> dependencies = (Array) this.assetDependencies.get(fileName);
            buffer.append(ClassReflection.getSimpleName(type));
            buffer.append(", refs: ");
            buffer.append(assetRef.getRefCount());
            if (dependencies != null) {
                buffer.append(", deps: [");
                Iterator i$ = dependencies.iterator();
                while (i$.hasNext()) {
                    buffer.append((String) i$.next());
                    buffer.append(",");
                }
                buffer.append("]");
            }
            buffer.append("\n");
        }
        return buffer.toString();
    }

    public synchronized Array<String> getAssetNames() {
        return this.assetTypes.keys().toArray();
    }

    public synchronized Array<String> getDependencies(String fileName) {
        return (Array) this.assetDependencies.get(fileName);
    }

    public synchronized Class getAssetType(String fileName) {
        return (Class) this.assetTypes.get(fileName);
    }
}
