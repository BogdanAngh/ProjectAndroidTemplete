package com.google.android.gms.ads.internal.formats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzem.zza;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.BuildConfig;
import java.util.List;

@zzji
public class zze extends zza implements zzi.zza {
    private Bundle mExtras;
    private Object zzako;
    private String zzbmy;
    private List<zzc> zzbmz;
    private String zzbna;
    private String zzbnc;
    @Nullable
    private zza zzbng;
    private zzi zzbnj;
    private zzeg zzbnk;
    private String zzbnl;

    public zze(String str, List list, String str2, zzeg com_google_android_gms_internal_zzeg, String str3, String str4, @Nullable zza com_google_android_gms_ads_internal_formats_zza, Bundle bundle) {
        this.zzako = new Object();
        this.zzbmy = str;
        this.zzbmz = list;
        this.zzbna = str2;
        this.zzbnk = com_google_android_gms_internal_zzeg;
        this.zzbnc = str3;
        this.zzbnl = str4;
        this.zzbng = com_google_android_gms_ads_internal_formats_zza;
        this.mExtras = bundle;
    }

    public void destroy() {
        this.zzbmy = null;
        this.zzbmz = null;
        this.zzbna = null;
        this.zzbnk = null;
        this.zzbnc = null;
        this.zzbnl = null;
        this.zzbng = null;
        this.mExtras = null;
        this.zzako = null;
        this.zzbnj = null;
    }

    public String getAdvertiser() {
        return this.zzbnl;
    }

    public String getBody() {
        return this.zzbna;
    }

    public String getCallToAction() {
        return this.zzbnc;
    }

    public String getCustomTemplateId() {
        return BuildConfig.FLAVOR;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public String getHeadline() {
        return this.zzbmy;
    }

    public List getImages() {
        return this.zzbmz;
    }

    public void zzb(zzi com_google_android_gms_ads_internal_formats_zzi) {
        synchronized (this.zzako) {
            this.zzbnj = com_google_android_gms_ads_internal_formats_zzi;
        }
    }

    public zzd zzmp() {
        return com.google.android.gms.dynamic.zze.zzac(this.zzbnj);
    }

    public String zzmq() {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES;
    }

    public zza zzmr() {
        return this.zzbng;
    }

    public zzeg zzmt() {
        return this.zzbnk;
    }
}
