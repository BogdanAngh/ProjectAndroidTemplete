package com.google.android.gms.internal;

import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.formats.NativeAd.Image;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzhd.zza;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzhi extends zza {
    private final NativeAppInstallAdMapper zzbxl;

    public zzhi(NativeAppInstallAdMapper nativeAppInstallAdMapper) {
        this.zzbxl = nativeAppInstallAdMapper;
    }

    public String getBody() {
        return this.zzbxl.getBody();
    }

    public String getCallToAction() {
        return this.zzbxl.getCallToAction();
    }

    public Bundle getExtras() {
        return this.zzbxl.getExtras();
    }

    public String getHeadline() {
        return this.zzbxl.getHeadline();
    }

    public List getImages() {
        List<Image> images = this.zzbxl.getImages();
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
        return this.zzbxl.getOverrideClickHandling();
    }

    public boolean getOverrideImpressionRecording() {
        return this.zzbxl.getOverrideImpressionRecording();
    }

    public String getPrice() {
        return this.zzbxl.getPrice();
    }

    public double getStarRating() {
        return this.zzbxl.getStarRating();
    }

    public String getStore() {
        return this.zzbxl.getStore();
    }

    public void recordImpression() {
        this.zzbxl.recordImpression();
    }

    public zzab zzej() {
        return this.zzbxl.getVideoController() != null ? this.zzbxl.getVideoController().zzdw() : null;
    }

    public void zzk(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxl.handleClick((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public void zzl(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxl.trackView((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public void zzm(zzd com_google_android_gms_dynamic_zzd) {
        this.zzbxl.untrackView((View) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public zzeg zzmo() {
        Image icon = this.zzbxl.getIcon();
        return icon != null ? new zzc(icon.getDrawable(), icon.getUri(), icon.getScale()) : null;
    }
}
