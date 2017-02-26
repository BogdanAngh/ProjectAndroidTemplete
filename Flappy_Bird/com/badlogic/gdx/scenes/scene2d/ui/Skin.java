package com.badlogic.gdx.scenes.scene2d.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TiledDrawable;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.Json.ReadOnlySerializer;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SerializationException;
import com.badlogic.gdx.utils.reflect.ClassReflection;
import com.badlogic.gdx.utils.reflect.Method;
import com.google.android.gms.cast.TextTrackStyle;
import java.util.Iterator;

public class Skin implements Disposable {
    TextureAtlas atlas;
    ObjectMap<Class, ObjectMap<String, Object>> resources;

    public static class TintedDrawable {
        public Color color;
        public String name;
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Skin.1 */
    class C03771 extends Json {
        C03771() {
        }

        public <T> T readValue(Class<T> type, Class elementType, JsonValue jsonData) {
            if (!jsonData.isString() || ClassReflection.isAssignableFrom(CharSequence.class, type)) {
                return super.readValue((Class) type, elementType, jsonData);
            }
            return Skin.this.get(jsonData.asString(), type);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Skin.2 */
    class C05802 extends ReadOnlySerializer<Skin> {
        final /* synthetic */ Skin val$skin;

        C05802(Skin skin) {
            this.val$skin = skin;
        }

        public Skin read(Json json, JsonValue typeToValueMap, Class ignored) {
            JsonValue valueMap = typeToValueMap.child();
            while (valueMap != null) {
                try {
                    readNamedObjects(json, ClassReflection.forName(valueMap.name()), valueMap);
                    valueMap = valueMap.next();
                } catch (Throwable ex) {
                    throw new SerializationException(ex);
                }
            }
            return this.val$skin;
        }

        private void readNamedObjects(Json json, Class type, JsonValue valueMap) {
            Class addType;
            if (type == TintedDrawable.class) {
                addType = Drawable.class;
            } else {
                addType = type;
            }
            for (JsonValue valueEntry = valueMap.child(); valueEntry != null; valueEntry = valueEntry.next()) {
                Object object = json.readValue(type, valueEntry);
                if (object != null) {
                    try {
                        Skin.this.add(valueEntry.name(), object, addType);
                    } catch (Exception ex) {
                        throw new SerializationException("Error reading " + ClassReflection.getSimpleName(type) + ": " + valueEntry.name(), ex);
                    }
                }
            }
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Skin.3 */
    class C05813 extends ReadOnlySerializer<BitmapFont> {
        final /* synthetic */ Skin val$skin;
        final /* synthetic */ FileHandle val$skinFile;

        C05813(FileHandle fileHandle, Skin skin) {
            this.val$skinFile = fileHandle;
            this.val$skin = skin;
        }

        public BitmapFont read(Json json, JsonValue jsonData, Class type) {
            String path = (String) json.readValue("file", String.class, jsonData);
            FileHandle fontFile = this.val$skinFile.parent().child(path);
            if (!fontFile.exists()) {
                fontFile = Gdx.files.internal(path);
            }
            if (fontFile.exists()) {
                String regionName = fontFile.nameWithoutExtension();
                try {
                    TextureRegion region = (TextureRegion) this.val$skin.optional(regionName, TextureRegion.class);
                    if (region != null) {
                        return new BitmapFont(fontFile, region, false);
                    }
                    FileHandle imageFile = fontFile.parent().child(regionName + ".png");
                    if (imageFile.exists()) {
                        return new BitmapFont(fontFile, imageFile, false);
                    }
                    return new BitmapFont(fontFile, false);
                } catch (RuntimeException ex) {
                    throw new SerializationException("Error loading bitmap font: " + fontFile, ex);
                }
            }
            throw new SerializationException("Font file not found: " + fontFile);
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Skin.4 */
    class C05824 extends ReadOnlySerializer<Color> {
        C05824() {
        }

        public Color read(Json json, JsonValue jsonData, Class type) {
            if (jsonData.isString()) {
                return (Color) Skin.this.get(jsonData.asString(), Color.class);
            }
            String hex = (String) json.readValue("hex", String.class, (String) null, jsonData);
            if (hex != null) {
                return Color.valueOf(hex);
            }
            return new Color(((Float) json.readValue("r", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("g", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("b", Float.TYPE, Float.valueOf(0.0f), jsonData)).floatValue(), ((Float) json.readValue("a", Float.TYPE, Float.valueOf(TextTrackStyle.DEFAULT_FONT_SCALE), jsonData)).floatValue());
        }
    }

    /* renamed from: com.badlogic.gdx.scenes.scene2d.ui.Skin.5 */
    class C05835 extends ReadOnlySerializer {
        C05835() {
        }

        public Object read(Json json, JsonValue jsonData, Class type) {
            return Skin.this.newDrawable((String) json.readValue("name", String.class, jsonData), (Color) json.readValue("color", Color.class, jsonData));
        }
    }

    public Skin() {
        this.resources = new ObjectMap();
    }

    public Skin(FileHandle skinFile) {
        this.resources = new ObjectMap();
        FileHandle atlasFile = skinFile.sibling(skinFile.nameWithoutExtension() + ".atlas");
        if (atlasFile.exists()) {
            this.atlas = new TextureAtlas(atlasFile);
            addRegions(this.atlas);
        }
        load(skinFile);
    }

    public Skin(FileHandle skinFile, TextureAtlas atlas) {
        this.resources = new ObjectMap();
        this.atlas = atlas;
        addRegions(atlas);
        load(skinFile);
    }

    public Skin(TextureAtlas atlas) {
        this.resources = new ObjectMap();
        this.atlas = atlas;
        addRegions(atlas);
    }

    public void load(FileHandle skinFile) {
        try {
            getJsonLoader(skinFile).fromJson(Skin.class, skinFile);
        } catch (SerializationException ex) {
            throw new SerializationException("Error reading file: " + skinFile, ex);
        }
    }

    public void addRegions(TextureAtlas atlas) {
        Array<AtlasRegion> regions = atlas.getRegions();
        int n = regions.size;
        for (int i = 0; i < n; i++) {
            AtlasRegion region = (AtlasRegion) regions.get(i);
            add(region.name, region, TextureRegion.class);
        }
    }

    public void add(String name, Object resource) {
        add(name, resource, resource.getClass());
    }

    public void add(String name, Object resource, Class type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (resource == null) {
            throw new IllegalArgumentException("resource cannot be null.");
        } else {
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                typeResources = new ObjectMap();
                this.resources.put(type, typeResources);
            }
            typeResources.put(name, resource);
        }
    }

    public <T> T get(Class<T> type) {
        return get("default", type);
    }

    public <T> T get(String name, Class<T> type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else if (type == Drawable.class) {
            return getDrawable(name);
        } else {
            if (type == TextureRegion.class) {
                return getRegion(name);
            }
            if (type == NinePatch.class) {
                return getPatch(name);
            }
            if (type == Sprite.class) {
                return getSprite(name);
            }
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                throw new GdxRuntimeException("No " + type.getName() + " registered with name: " + name);
            }
            T resource = typeResources.get(name);
            if (resource != null) {
                return resource;
            }
            throw new GdxRuntimeException("No " + type.getName() + " registered with name: " + name);
        }
    }

    public <T> T optional(String name, Class<T> type) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null.");
        } else if (type == null) {
            throw new IllegalArgumentException("type cannot be null.");
        } else {
            ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
            if (typeResources == null) {
                return null;
            }
            return typeResources.get(name);
        }
    }

    public boolean has(String name, Class type) {
        ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(type);
        if (typeResources == null) {
            return false;
        }
        return typeResources.containsKey(name);
    }

    public <T> ObjectMap<String, T> getAll(Class<T> type) {
        return (ObjectMap) this.resources.get(type);
    }

    public Color getColor(String name) {
        return (Color) get(name, Color.class);
    }

    public BitmapFont getFont(String name) {
        return (BitmapFont) get(name, BitmapFont.class);
    }

    public TextureRegion getRegion(String name) {
        TextureRegion region = (TextureRegion) optional(name, TextureRegion.class);
        if (region != null) {
            return region;
        }
        Texture texture = (Texture) optional(name, Texture.class);
        if (texture == null) {
            throw new GdxRuntimeException("No TextureRegion or Texture registered with name: " + name);
        }
        region = new TextureRegion(texture);
        add(name, region, Texture.class);
        return region;
    }

    public TiledDrawable getTiledDrawable(String name) {
        TiledDrawable tiled = (TiledDrawable) optional(name, TiledDrawable.class);
        if (tiled != null) {
            return tiled;
        }
        Drawable drawable = (Drawable) optional(name, Drawable.class);
        if (drawable == null) {
            tiled = new TiledDrawable(getRegion(name));
            add(name, tiled, TiledDrawable.class);
            return tiled;
        } else if (drawable instanceof TiledDrawable) {
            return (TiledDrawable) drawable;
        } else {
            throw new GdxRuntimeException("Drawable found but is not a TiledDrawable: " + name + ", " + drawable.getClass().getName());
        }
    }

    public NinePatch getPatch(String name) {
        NinePatch patch = (NinePatch) optional(name, NinePatch.class);
        if (patch != null) {
            NinePatch ninePatch = patch;
            return patch;
        }
        try {
            TextureRegion region = getRegion(name);
            if (region instanceof AtlasRegion) {
                int[] splits = ((AtlasRegion) region).splits;
                if (splits != null) {
                    ninePatch = new NinePatch(region, splits[0], splits[1], splits[2], splits[3]);
                    try {
                        int[] pads = ((AtlasRegion) region).pads;
                        if (pads != null) {
                            ninePatch.setPadding(pads[0], pads[1], pads[2], pads[3]);
                        }
                        patch = ninePatch;
                    } catch (GdxRuntimeException e) {
                        throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
                    }
                }
            }
            if (patch == null) {
                ninePatch = new NinePatch(region);
            } else {
                ninePatch = patch;
            }
            add(name, ninePatch, NinePatch.class);
            return ninePatch;
        } catch (GdxRuntimeException e2) {
            ninePatch = patch;
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
        }
    }

    public Sprite getSprite(String name) {
        Sprite sprite = (Sprite) optional(name, Sprite.class);
        if (sprite != null) {
            return sprite;
        }
        try {
            Sprite sprite2;
            TextureRegion textureRegion = getRegion(name);
            if (textureRegion instanceof AtlasRegion) {
                AtlasRegion region = (AtlasRegion) textureRegion;
                if (!(!region.rotate && region.packedWidth == region.originalWidth && region.packedHeight == region.originalHeight)) {
                    sprite2 = new AtlasSprite(region);
                    if (sprite2 != null) {
                        try {
                            sprite = new Sprite(textureRegion);
                        } catch (GdxRuntimeException e) {
                            sprite = sprite2;
                            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
                        }
                    }
                    sprite = sprite2;
                    add(name, sprite, NinePatch.class);
                    return sprite;
                }
            }
            sprite2 = sprite;
            if (sprite2 != null) {
                sprite = sprite2;
            } else {
                sprite = new Sprite(textureRegion);
            }
            add(name, sprite, NinePatch.class);
            return sprite;
        } catch (GdxRuntimeException e2) {
            throw new GdxRuntimeException("No NinePatch, TextureRegion, or Texture registered with name: " + name);
        }
    }

    public Drawable getDrawable(String name) {
        Drawable drawable = (Drawable) optional(name, Drawable.class);
        if (drawable != null) {
            return drawable;
        }
        drawable = (Drawable) optional(name, TiledDrawable.class);
        if (drawable != null) {
            return drawable;
        }
        NinePatch patch;
        Sprite sprite;
        try {
            Drawable drawable2;
            TextureRegion textureRegion = getRegion(name);
            if (textureRegion instanceof AtlasRegion) {
                AtlasRegion region = (AtlasRegion) textureRegion;
                if (region.splits != null) {
                    drawable2 = new NinePatchDrawable(getPatch(name));
                } else if (!(!region.rotate && region.packedWidth == region.originalWidth && region.packedHeight == region.originalHeight)) {
                    drawable2 = new SpriteDrawable(getSprite(name));
                }
                if (drawable2 != null) {
                    try {
                    } catch (GdxRuntimeException e) {
                        drawable = drawable2;
                    }
                }
                if (drawable == null) {
                    patch = (NinePatch) optional(name, NinePatch.class);
                    if (patch == null) {
                        drawable = new NinePatchDrawable(patch);
                    } else {
                        sprite = (Sprite) optional(name, Sprite.class);
                        if (sprite == null) {
                            drawable = new SpriteDrawable(sprite);
                        } else {
                            throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + name);
                        }
                    }
                }
                add(name, drawable, Drawable.class);
                return drawable;
            }
            drawable2 = drawable;
            drawable = drawable2 != null ? drawable2 : new TextureRegionDrawable(textureRegion);
        } catch (GdxRuntimeException e2) {
        }
        if (drawable == null) {
            patch = (NinePatch) optional(name, NinePatch.class);
            if (patch == null) {
                sprite = (Sprite) optional(name, Sprite.class);
                if (sprite == null) {
                    throw new GdxRuntimeException("No Drawable, NinePatch, TextureRegion, Texture, or Sprite registered with name: " + name);
                }
                drawable = new SpriteDrawable(sprite);
            } else {
                drawable = new NinePatchDrawable(patch);
            }
        }
        add(name, drawable, Drawable.class);
        return drawable;
    }

    public String find(Object resource) {
        if (resource == null) {
            throw new IllegalArgumentException("style cannot be null.");
        }
        ObjectMap<String, Object> typeResources = (ObjectMap) this.resources.get(resource.getClass());
        if (typeResources == null) {
            return null;
        }
        return (String) typeResources.findKey(resource, true);
    }

    public Drawable newDrawable(String name) {
        return newDrawable(getDrawable(name));
    }

    public Drawable newDrawable(String name, float r, float g, float b, float a) {
        return newDrawable(getDrawable(name), new Color(r, g, b, a));
    }

    public Drawable newDrawable(String name, Color tint) {
        return newDrawable(getDrawable(name), tint);
    }

    public Drawable newDrawable(Drawable drawable) {
        if (drawable instanceof TextureRegionDrawable) {
            return new TextureRegionDrawable((TextureRegionDrawable) drawable);
        }
        if (drawable instanceof NinePatchDrawable) {
            return new NinePatchDrawable((NinePatchDrawable) drawable);
        }
        if (drawable instanceof SpriteDrawable) {
            return new SpriteDrawable((SpriteDrawable) drawable);
        }
        throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
    }

    public Drawable newDrawable(Drawable drawable, float r, float g, float b, float a) {
        return newDrawable(drawable, new Color(r, g, b, a));
    }

    public Drawable newDrawable(Drawable drawable, Color tint) {
        Sprite sprite;
        if (drawable instanceof TextureRegionDrawable) {
            TextureRegion region = ((TextureRegionDrawable) drawable).getRegion();
            if (region instanceof AtlasRegion) {
                sprite = new AtlasSprite((AtlasRegion) region);
            } else {
                sprite = new Sprite(region);
            }
            sprite.setColor(tint);
            return new SpriteDrawable(sprite);
        } else if (drawable instanceof NinePatchDrawable) {
            Drawable patchDrawable = new NinePatchDrawable((NinePatchDrawable) drawable);
            patchDrawable.setPatch(new NinePatch(patchDrawable.getPatch(), tint));
            return patchDrawable;
        } else if (drawable instanceof SpriteDrawable) {
            SpriteDrawable spriteDrawable = new SpriteDrawable((SpriteDrawable) drawable);
            sprite = spriteDrawable.getSprite();
            if (sprite instanceof AtlasSprite) {
                sprite = new AtlasSprite((AtlasSprite) sprite);
            } else {
                sprite = new Sprite(sprite);
            }
            sprite.setColor(tint);
            spriteDrawable.setSprite(sprite);
            return spriteDrawable;
        } else {
            throw new GdxRuntimeException("Unable to copy, unknown drawable type: " + drawable.getClass());
        }
    }

    public void setEnabled(Actor actor, boolean enabled) {
        Method method = findMethod(actor.getClass(), "getStyle");
        if (method != null) {
            try {
                Object style = method.invoke(actor, new Object[0]);
                String name = find(style);
                if (name != null) {
                    style = get(name.replace("-disabled", "") + (enabled ? "" : "-disabled"), style.getClass());
                    method = findMethod(actor.getClass(), "setStyle");
                    if (method != null) {
                        try {
                            method.invoke(actor, style);
                        } catch (Exception e) {
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
    }

    public TextureAtlas getAtlas() {
        return this.atlas;
    }

    public void dispose() {
        if (this.atlas != null) {
            this.atlas.dispose();
        }
        Iterator it = this.resources.values().iterator();
        while (it.hasNext()) {
            Iterator i$ = ((ObjectMap) it.next()).values().iterator();
            while (i$.hasNext()) {
                Object resource = i$.next();
                if (resource instanceof Disposable) {
                    ((Disposable) resource).dispose();
                }
            }
        }
    }

    protected Json getJsonLoader(FileHandle skinFile) {
        Json json = new C03771();
        json.setTypeName(null);
        json.setUsePrototypes(false);
        json.setSerializer(Skin.class, new C05802(this));
        json.setSerializer(BitmapFont.class, new C05813(skinFile, this));
        json.setSerializer(Color.class, new C05824());
        json.setSerializer(TintedDrawable.class, new C05835());
        return json;
    }

    private static Method findMethod(Class type, String name) {
        for (Method method : ClassReflection.getMethods(type)) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }
}
