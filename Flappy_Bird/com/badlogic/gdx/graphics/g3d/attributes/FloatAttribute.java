package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

public class FloatAttribute extends Attribute {
    public static final long AlphaTest;
    public static final String AlphaTestAlias = "alphaTest";
    public static final long Shininess;
    public static final String ShininessAlias = "shininess";
    public float value;

    static {
        Shininess = Attribute.register(ShininessAlias);
        AlphaTest = Attribute.register(AlphaTestAlias);
    }

    public static FloatAttribute createShininess(float value) {
        return new FloatAttribute(Shininess, value);
    }

    public static FloatAttribute createAlphaTest(float value) {
        return new FloatAttribute(AlphaTest, value);
    }

    public FloatAttribute(long type) {
        super(type);
    }

    public FloatAttribute(long type, float value) {
        super(type);
        this.value = value;
    }

    public Attribute copy() {
        return new FloatAttribute(this.type, this.value);
    }

    protected boolean equals(Attribute other) {
        return ((FloatAttribute) other).value == this.value;
    }
}
