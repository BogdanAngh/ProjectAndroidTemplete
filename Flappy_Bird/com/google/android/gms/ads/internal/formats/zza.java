package com.google.android.gms.ads.internal.formats;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import com.badlogic.gdx.net.HttpStatus;

public class zza {
    private static final int zzuW;
    private static final int zzuX;
    static final int zzuY;
    static final int zzuZ;
    private final int mTextColor;
    private final String zzva;
    private final Drawable zzvb;
    private final int zzvc;
    private final int zzvd;

    static {
        zzuW = Color.rgb(12, 174, HttpStatus.SC_PARTIAL_CONTENT);
        zzuX = Color.rgb(HttpStatus.SC_NO_CONTENT, HttpStatus.SC_NO_CONTENT, HttpStatus.SC_NO_CONTENT);
        zzuY = zzuX;
        zzuZ = zzuW;
    }

    public zza(String str, Drawable drawable, Integer num, Integer num2, Integer num3) {
        this.zzva = str;
        this.zzvb = drawable;
        this.zzvc = num != null ? num.intValue() : zzuY;
        this.mTextColor = num2 != null ? num2.intValue() : zzuZ;
        this.zzvd = num3 != null ? num3.intValue() : 12;
    }

    public int getBackgroundColor() {
        return this.zzvc;
    }

    public Drawable getIcon() {
        return this.zzvb;
    }

    public String getText() {
        return this.zzva;
    }

    public int getTextSize() {
        return this.zzvd;
    }

    public int zzdu() {
        return this.mTextColor;
    }
}
