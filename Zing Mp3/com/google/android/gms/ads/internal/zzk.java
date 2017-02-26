package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzr;
import com.google.android.gms.ads.internal.client.zzs.zza;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zzes;
import com.google.android.gms.internal.zzet;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzji;

@zzji
public class zzk extends zza {
    private final Context mContext;
    private final zzd zzamb;
    private final zzgz zzamf;
    private zzq zzanl;
    private NativeAdOptionsParcel zzanq;
    private zzy zzans;
    private final String zzant;
    private final VersionInfoParcel zzanu;
    private zzeq zzany;
    private zzer zzanz;
    private SimpleArrayMap<String, zzes> zzaoa;
    private SimpleArrayMap<String, zzet> zzaob;

    public zzk(Context context, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        this.mContext = context;
        this.zzant = str;
        this.zzamf = com_google_android_gms_internal_zzgz;
        this.zzanu = versionInfoParcel;
        this.zzaob = new SimpleArrayMap();
        this.zzaoa = new SimpleArrayMap();
        this.zzamb = com_google_android_gms_ads_internal_zzd;
    }

    public void zza(NativeAdOptionsParcel nativeAdOptionsParcel) {
        this.zzanq = nativeAdOptionsParcel;
    }

    public void zza(zzeq com_google_android_gms_internal_zzeq) {
        this.zzany = com_google_android_gms_internal_zzeq;
    }

    public void zza(zzer com_google_android_gms_internal_zzer) {
        this.zzanz = com_google_android_gms_internal_zzer;
    }

    public void zza(String str, zzet com_google_android_gms_internal_zzet, zzes com_google_android_gms_internal_zzes) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Custom template ID for native custom template ad is empty. Please provide a valid template id.");
        }
        this.zzaob.put(str, com_google_android_gms_internal_zzet);
        this.zzaoa.put(str, com_google_android_gms_internal_zzes);
    }

    public void zzb(zzq com_google_android_gms_ads_internal_client_zzq) {
        this.zzanl = com_google_android_gms_ads_internal_client_zzq;
    }

    public void zzb(zzy com_google_android_gms_ads_internal_client_zzy) {
        this.zzans = com_google_android_gms_ads_internal_client_zzy;
    }

    public zzr zzfl() {
        return new zzj(this.mContext, this.zzant, this.zzamf, this.zzanu, this.zzanl, this.zzany, this.zzanz, this.zzaob, this.zzaoa, this.zzanq, this.zzans, this.zzamb);
    }
}
