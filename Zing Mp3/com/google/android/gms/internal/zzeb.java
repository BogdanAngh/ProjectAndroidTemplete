package com.google.android.gms.internal;

import android.view.View;
import com.google.android.gms.ads.doubleclick.CustomRenderedAd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.dynamic.zze;

@zzji
public class zzeb implements CustomRenderedAd {
    private final zzec zzbmf;

    public zzeb(zzec com_google_android_gms_internal_zzec) {
        this.zzbmf = com_google_android_gms_internal_zzec;
    }

    public String getBaseUrl() {
        try {
            return this.zzbmf.zzme();
        } catch (Throwable e) {
            zzb.zzc("Could not delegate getBaseURL to CustomRenderedAd", e);
            return null;
        }
    }

    public String getContent() {
        try {
            return this.zzbmf.getContent();
        } catch (Throwable e) {
            zzb.zzc("Could not delegate getContent to CustomRenderedAd", e);
            return null;
        }
    }

    public void onAdRendered(View view) {
        try {
            this.zzbmf.zzi(view != null ? zze.zzac(view) : null);
        } catch (Throwable e) {
            zzb.zzc("Could not delegate onAdRendered to CustomRenderedAd", e);
        }
    }

    public void recordClick() {
        try {
            this.zzbmf.recordClick();
        } catch (Throwable e) {
            zzb.zzc("Could not delegate recordClick to CustomRenderedAd", e);
        }
    }

    public void recordImpression() {
        try {
            this.zzbmf.recordImpression();
        } catch (Throwable e) {
            zzb.zzc("Could not delegate recordImpression to CustomRenderedAd", e);
        }
    }
}
