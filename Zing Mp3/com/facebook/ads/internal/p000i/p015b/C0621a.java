package com.facebook.ads.internal.p000i.p015b;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.p000i.C0728j;
import com.facebook.ads.internal.p000i.C0736l;

/* renamed from: com.facebook.ads.internal.i.b.a */
public class C0621a extends LinearLayout {
    private C0736l f910a;
    private int f911b;

    public C0621a(Context context, NativeAd nativeAd, NativeAdViewAttributes nativeAdViewAttributes) {
        int i = 21;
        super(context);
        setOrientation(1);
        setVerticalGravity(16);
        this.f910a = new C0736l(getContext(), 2);
        this.f910a.setMinTextSize((float) (nativeAdViewAttributes.getTitleTextSize() - 2));
        this.f910a.setText(nativeAd.getAdTitle());
        C0728j.m1409a(this.f910a, nativeAdViewAttributes);
        this.f910a.setLayoutParams(new LayoutParams(-2, -2));
        addView(this.f910a);
        if (nativeAd.getAdTitle() != null) {
            i = Math.min(nativeAd.getAdTitle().length(), 21);
        }
        this.f911b = i;
        addView(C0728j.m1408a(context, nativeAd, nativeAdViewAttributes));
    }

    public int getMinVisibleTitleCharacters() {
        return this.f911b;
    }

    public TextView getTitleTextView() {
        return this.f910a;
    }
}
