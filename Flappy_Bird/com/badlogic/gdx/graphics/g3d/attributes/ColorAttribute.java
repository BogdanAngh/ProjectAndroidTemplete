package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class ColorAttribute extends Attribute {
    public static final long Ambient;
    public static final String AmbientAlias = "ambientColor";
    public static final long AmbientLight;
    public static final String AmbientLightAlias = "ambientLightColor";
    public static final long Diffuse;
    public static final String DiffuseAlias = "diffuseColor";
    public static final long Emissive;
    public static final String EmissiveAlias = "emissiveColor";
    public static final long Fog;
    public static final String FogAlias = "fogColor";
    protected static long Mask = 0;
    public static final long Reflection;
    public static final String ReflectionAlias = "reflectionColor";
    public static final long Specular;
    public static final String SpecularAlias = "specularColor";
    public final Color color;

    static {
        Diffuse = Attribute.register(DiffuseAlias);
        Specular = Attribute.register(SpecularAlias);
        Ambient = Attribute.register(AmbientAlias);
        Emissive = Attribute.register(EmissiveAlias);
        Reflection = Attribute.register(ReflectionAlias);
        AmbientLight = Attribute.register(AmbientLightAlias);
        Fog = Attribute.register(FogAlias);
        Mask = (((((Ambient | Diffuse) | Specular) | Emissive) | Reflection) | AmbientLight) | Fog;
    }

    public static final boolean is(long mask) {
        return (Mask & mask) != Specular;
    }

    public static final ColorAttribute createAmbient(Color color) {
        return new ColorAttribute(Ambient, color);
    }

    public static final ColorAttribute createAmbient(float r, float g, float b, float a) {
        return new ColorAttribute(Ambient, r, g, b, a);
    }

    public static final ColorAttribute createDiffuse(Color color) {
        return new ColorAttribute(Diffuse, color);
    }

    public static final ColorAttribute createDiffuse(float r, float g, float b, float a) {
        return new ColorAttribute(Diffuse, r, g, b, a);
    }

    public static final ColorAttribute createSpecular(Color color) {
        return new ColorAttribute(Specular, color);
    }

    public static final ColorAttribute createSpecular(float r, float g, float b, float a) {
        return new ColorAttribute(Specular, r, g, b, a);
    }

    public static final ColorAttribute createReflection(Color color) {
        return new ColorAttribute(Reflection, color);
    }

    public static final ColorAttribute createReflection(float r, float g, float b, float a) {
        return new ColorAttribute(Reflection, r, g, b, a);
    }

    public ColorAttribute(long type) {
        super(type);
        this.color = new Color();
        if (!is(type)) {
            throw new GdxRuntimeException("Invalid type specified");
        }
    }

    public ColorAttribute(long type, Color color) {
        this(type);
        if (color != null) {
            this.color.set(color);
        }
    }

    public ColorAttribute(long type, float r, float g, float b, float a) {
        this(type);
        this.color.set(r, g, b, a);
    }

    public ColorAttribute(ColorAttribute copyFrom) {
        this(copyFrom.type, copyFrom.color);
    }

    public Attribute copy() {
        return new ColorAttribute(this);
    }

    protected boolean equals(Attribute other) {
        return ((ColorAttribute) other).color.equals(this.color);
    }
}
