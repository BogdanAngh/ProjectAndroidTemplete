package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.graphics.g3d.utils.TextureDescriptor;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class TextureAttribute extends Attribute {
    public static final long Bump;
    public static final String BumpAlias = "bumpTexture";
    public static final long Diffuse;
    public static final String DiffuseAlias = "diffuseTexture";
    protected static long Mask = 0;
    public static final long Normal;
    public static final String NormalAlias = "normalTexture";
    public static final long Specular;
    public static final String SpecularAlias = "specularTexture";
    public final TextureDescriptor<Texture> textureDescription;

    static {
        Diffuse = Attribute.register(DiffuseAlias);
        Specular = Attribute.register(SpecularAlias);
        Bump = Attribute.register(BumpAlias);
        Normal = Attribute.register(NormalAlias);
        Mask = ((Diffuse | Specular) | Bump) | Normal;
    }

    public static final boolean is(long mask) {
        return (Mask & mask) != Specular;
    }

    public static TextureAttribute createDiffuse(Texture texture) {
        return new TextureAttribute(Diffuse, texture);
    }

    public static TextureAttribute createSpecular(Texture texture) {
        return new TextureAttribute(Specular, texture);
    }

    public TextureAttribute(long type) {
        super(type);
        if (is(type)) {
            this.textureDescription = new TextureDescriptor();
            return;
        }
        throw new GdxRuntimeException("Invalid type specified");
    }

    public <T extends Texture> TextureAttribute(long type, TextureDescriptor<T> textureDescription) {
        this(type);
        this.textureDescription.set(textureDescription);
    }

    public TextureAttribute(long type, Texture texture) {
        this(type);
        this.textureDescription.texture = texture;
    }

    public TextureAttribute(TextureAttribute copyFrom) {
        this(copyFrom.type, copyFrom.textureDescription);
    }

    public Attribute copy() {
        return new TextureAttribute(this);
    }

    protected boolean equals(Attribute other) {
        return ((TextureAttribute) other).textureDescription.equals(this.textureDescription);
    }
}
