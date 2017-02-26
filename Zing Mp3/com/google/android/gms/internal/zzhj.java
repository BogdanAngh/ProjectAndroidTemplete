package com.google.android.gms.internal;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzhe.zza;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzhj extends zza {
    private final NativeContentAdMapper zzbxm;

    public zzhj(NativeContentAdMapper nativeContentAdMapper) {
        this.zzbxm = nativeContentAdMapper;
    }

    public String getAdvertiser() {
        return this.zzbxm.getAdvertiser();
    }

    public String getBody() {
        return this.zzbxm.getBody();
    }

    public String getCallToAction() {
        return this.zzbxm.getCallToAction();
    }

    public Bundle getExtras() {
        return this.zzbxm.getExtras();
    }

    public String getHeadline() {
        return this.zzbxm.getHeadline();
    }

    public List getImages() {
        List<Image> images = this.zzbxm.getImages();
        if (images == null) {
            return null;
        }
        List arrayList = new ArrayList();
        for (Image image : images) {
            arrayList.add(new zzc(image.getDrawable(), image.getUri(), image.getScale()));
        }
        return arrayList;
    }

    public boolean getOverrideClickHandling() {
        return this.zzbxm.getOverrideClickHandling();
    }

    public boolean getOverrideImpressionRecording() {
        return this.zzbxm.getOverrideImpressionRecording();
    }

    public void recordImpression() {
        this.zzbxm.recordImpression();
    }

    public void zzk(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxm.handleClick((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public void zzl(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxm.trackView((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public void zzm(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxm.untrackView((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public zzeg zzmt() {
        Image logo = this.zzbxm.getLogo();
        return logo != null ? new zzc(logo.getDrawable(), logo.getUri(), logo.getScale()) : null;
    }
}
