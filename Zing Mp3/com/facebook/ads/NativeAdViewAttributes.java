package com.facebook.ads;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.view.ViewCompat;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import org.json.JSONObject;

public class NativeAdViewAttributes {
    private Typeface f174a;
    private int f175b;
    private int f176c;
    private int f177d;
    private int f178e;
    private int f179f;
    private int f180g;
    private boolean f181h;
    private boolean f182i;

    public NativeAdViewAttributes() {
        this.f174a = Typeface.DEFAULT;
        this.f175b = -1;
        this.f176c = ViewCompat.MEASURED_STATE_MASK;
        this.f177d = -11643291;
        this.f178e = 0;
        this.f179f = -12420889;
        this.f180g = -12420889;
        this.f181h = AdSettings.isVideoAutoplay();
        this.f182i = AdSettings.isVideoAutoplayOnMobile();
    }

    public NativeAdViewAttributes(JSONObject jSONObject) {
        int i = 0;
        this.f174a = Typeface.DEFAULT;
        this.f175b = -1;
        this.f176c = ViewCompat.MEASURED_STATE_MASK;
        this.f177d = -11643291;
        this.f178e = 0;
        this.f179f = -12420889;
        this.f180g = -12420889;
        this.f181h = AdSettings.isVideoAutoplay();
        this.f182i = AdSettings.isVideoAutoplayOnMobile();
        try {
            int parseColor = jSONObject.getBoolean("background_transparent") ? 0 : Color.parseColor(jSONObject.getString("background_color"));
            int parseColor2 = Color.parseColor(jSONObject.getString("title_text_color"));
            int parseColor3 = Color.parseColor(jSONObject.getString("description_text_color"));
            int parseColor4 = jSONObject.getBoolean("button_transparent") ? 0 : Color.parseColor(jSONObject.getString("button_color"));
            if (!jSONObject.getBoolean("button_border_transparent")) {
                i = Color.parseColor(jSONObject.getString("button_border_color"));
            }
            int parseColor5 = Color.parseColor(jSONObject.getString("button_text_color"));
            Typeface create = Typeface.create(jSONObject.getString("android_typeface"), 0);
            this.f175b = parseColor;
            this.f176c = parseColor2;
            this.f177d = parseColor3;
            this.f178e = parseColor4;
            this.f180g = i;
            this.f179f = parseColor5;
            this.f174a = create;
        } catch (Throwable e) {
            C0778d.m1599a(C0777c.m1596a(e, "Error retrieving native ui configuration data"));
        }
    }

    public boolean getAutoplay() {
        return this.f181h;
    }

    public boolean getAutoplayOnMobile() {
        return AdSettings.isVideoAutoplayOnMobile();
    }

    public int getBackgroundColor() {
        return this.f175b;
    }

    public int getButtonBorderColor() {
        return this.f180g;
    }

    public int getButtonColor() {
        return this.f178e;
    }

    public int getButtonTextColor() {
        return this.f179f;
    }

    public int getDescriptionTextColor() {
        return this.f177d;
    }

    public int getDescriptionTextSize() {
        return 10;
    }

    public int getTitleTextColor() {
        return this.f176c;
    }

    public int getTitleTextSize() {
        return 16;
    }

    public Typeface getTypeface() {
        return this.f174a;
    }

    public NativeAdViewAttributes setAutoplay(boolean z) {
        this.f181h = z;
        return this;
    }

    public NativeAdViewAttributes setAutoplayOnMobile(boolean z) {
        this.f182i = z;
        return this;
    }

    public NativeAdViewAttributes setBackgroundColor(int i) {
        this.f175b = i;
        return this;
    }

    public NativeAdViewAttributes setButtonBorderColor(int i) {
        this.f180g = i;
        return this;
    }

    public NativeAdViewAttributes setButtonColor(int i) {
        this.f178e = i;
        return this;
    }

    public NativeAdViewAttributes setButtonTextColor(int i) {
        this.f179f = i;
        return this;
    }

    public NativeAdViewAttributes setDescriptionTextColor(int i) {
        this.f177d = i;
        return this;
    }

    public NativeAdViewAttributes setTitleTextColor(int i) {
        this.f176c = i;
        return this;
    }

    public NativeAdViewAttributes setTypeface(Typeface typeface) {
        this.f174a = typeface;
        return this;
    }
}
