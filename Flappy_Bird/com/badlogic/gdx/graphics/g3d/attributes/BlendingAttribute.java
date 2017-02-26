package com.badlogic.gdx.graphics.g3d.attributes;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g3d.Attribute;
import com.google.android.gms.cast.TextTrackStyle;

public class BlendingAttribute extends Attribute {
    public static final String Alias = "blended";
    public static final long Type;
    public boolean blended;
    public int destFunction;
    public float opacity;
    public int sourceFunction;

    static {
        Type = Attribute.register(Alias);
    }

    public static final boolean is(long mask) {
        return (Type & mask) == mask;
    }

    public BlendingAttribute() {
        this(null);
    }

    public BlendingAttribute(boolean blended, int sourceFunc, int destFunc, float opacity) {
        super(Type);
        this.opacity = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.blended = blended;
        this.sourceFunction = sourceFunc;
        this.destFunction = destFunc;
        this.opacity = opacity;
    }

    public BlendingAttribute(int sourceFunc, int destFunc, float opacity) {
        this(true, sourceFunc, destFunc, opacity);
    }

    public BlendingAttribute(int sourceFunc, int destFunc) {
        this(sourceFunc, destFunc, TextTrackStyle.DEFAULT_FONT_SCALE);
    }

    public BlendingAttribute(boolean blended, float opacity) {
        this(blended, GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, opacity);
    }

    public BlendingAttribute(float opacity) {
        this(true, opacity);
    }

    public BlendingAttribute(BlendingAttribute copyFrom) {
        this(copyFrom == null ? true : copyFrom.blended, copyFrom == null ? GL20.GL_SRC_ALPHA : copyFrom.sourceFunction, copyFrom == null ? GL20.GL_ONE_MINUS_SRC_ALPHA : copyFrom.destFunction, copyFrom == null ? TextTrackStyle.DEFAULT_FONT_SCALE : copyFrom.opacity);
    }

    public BlendingAttribute copy() {
        return new BlendingAttribute(this);
    }

    protected boolean equals(Attribute other) {
        return ((BlendingAttribute) other).sourceFunction == this.sourceFunction && ((BlendingAttribute) other).destFunction == this.destFunction;
    }
}
