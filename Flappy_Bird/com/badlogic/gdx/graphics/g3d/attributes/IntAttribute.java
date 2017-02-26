package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.g3d.Attribute;

public class IntAttribute extends Attribute {
    public static final long CullFace;
    public static final String CullFaceAlias = "cullface";
    public int value;

    static {
        CullFace = Attribute.register(CullFaceAlias);
    }

    public static IntAttribute createCullFace(int value) {
        return new IntAttribute(CullFace, value);
    }

    public IntAttribute(long type) {
        super(type);
    }

    public IntAttribute(long type, int value) {
        super(type);
        this.value = value;
    }

    public Attribute copy() {
        return new IntAttribute(this.type, this.value);
    }

    protected boolean equals(Attribute other) {
        return ((IntAttribute) other).value == this.value;
    }
}
