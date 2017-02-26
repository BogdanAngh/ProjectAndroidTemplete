package com.google.android.gms.ads.formats;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzei;

public abstract class NativeAdView extends FrameLayout {
    private final FrameLayout zzald;
    private final zzei zzale;

    public NativeAdView(Context context) {
        super(context);
        this.zzald = zzd(context);
        this.zzale = zzdz();
    }

    public NativeAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzald = zzd(context);
        this.zzale = zzdz();
    }

    public NativeAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzald = zzd(context);
        this.zzale = zzdz();
    }

    @TargetApi(21)
    public NativeAdView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.zzald = zzd(context);
        this.zzale = zzdz();
    }

    private FrameLayout zzd(Context context) {
        View zze = zze(context);
        zze.setLayoutParams(new LayoutParams(-1, -1));
        addView(zze);
        return zze;
    }

    private zzei zzdz() {
        zzaa.zzb(this.zzald, (Object) "createDelegate must be called after mOverlayFrame has been created");
        return zzm.zzks().zza(this.zzald.getContext(), (FrameLayout) this, this.zzald);
    }

    public void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        super.addView(view, i, layoutParams);
        super.bringChildToFront(this.zzald);
    }

    public void bringChildToFront(View view) {
        super.bringChildToFront(view);
        if (this.zzald != view) {
            super.bringChildToFront(this.zzald);
        }
    }

    public void destroy() {
        try {
            this.zzale.destroy();
        } catch (Throwable e) {
            zzb.zzb("Unable to destroy native ad view", e);
        }
    }

    public void removeAllViews() {
        super.removeAllViews();
        super.addView(this.zzald);
    }

    public void removeView(View view) {
        if (this.zzald != view) {
            super.removeView(view);
        }
    }

    public void setNativeAd(NativeAd nativeAd) {
        try {
            this.zzale.zze((zzd) nativeAd.zzdy());
        } catch (Throwable e) {
            zzb.zzb("Unable to call setNativeAd on delegate", e);
        }
    }

    protected void zza(String str, View view) {
        try {
            this.zzale.zzc(str, zze.zzac(view));
        } catch (Throwable e) {
            zzb.zzb("Unable to call setAssetView on delegate", e);
        }
    }

    FrameLayout zze(Context context) {
        return new FrameLayout(context);
    }

    protected View zzu(String str) {
        try {
            zzd zzaw = this.zzale.zzaw(str);
            if (zzaw != null) {
                return (View) zze.zzae(zzaw);
            }
        } catch (Throwable e) {
            zzb.zzb("Unable to call getAssetView on delegate", e);
        }
        return null;
    }
}
