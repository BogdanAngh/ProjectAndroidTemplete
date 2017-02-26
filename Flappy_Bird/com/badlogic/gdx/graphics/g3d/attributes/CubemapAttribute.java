package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.Cubemap;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class CubemapAttribute extends Attribute {
    public static final long EnvironmentMap;
    public static final String EnvironmentMapAlias = "environmentMapTexture";
    protected static long Mask;
    public final TextureDescriptor<Cubemap> textureDescription;

    static {
        EnvironmentMap = Attribute.register(EnvironmentMapAlias);
        Mask = EnvironmentMap;
    }

    public static final boolean is(long mask) {
        return (Mask & mask) != EnvironmentMap;
    }

    public CubemapAttribute(long type) {
        super(type);
        if (is(type)) {
            this.textureDescription = new TextureDescriptor();
            return;
        }
        throw new GdxRuntimeException("Invalid type specified");
    }

    public <T extends Cubemap> CubemapAttribute(long type, TextureDescriptor<T> textureDescription) {
        this(type);
        this.textureDescription.set(textureDescription);
    }

    public CubemapAttribute(long type, Cubemap texture) {
        this(type);
        this.textureDescription.texture = texture;
    }

    public CubemapAttribute(CubemapAttribute copyFrom) {
        this(copyFrom.type, copyFrom.textureDescription);
    }

    public Attribute copy() {
        return new CubemapAttribute(this);
    }

    protected boolean equals(Attribute other) {
        return ((CubemapAttribute) other).textureDescription.equals(this.textureDescription);
    }
}
