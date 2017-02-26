package com.google.android.gms.ads.internal.formats;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzek.zza;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.BuildConfig;
import java.util.List;

@zzji
public class zzd extends zza implements zzi.zza {
    private Bundle mExtras;
    private Object zzako;
    private String zzbmy;
    private List<zzc> zzbmz;
    private String zzbna;
    private zzeg zzbnb;
    private String zzbnc;
    private double zzbnd;
    private String zzbne;
    private String zzbnf;
    @Nullable
    private zza zzbng;
    @Nullable
    private zzab zzbnh;
    @Nullable
    private View zzbni;
    private zzi zzbnj;

    public zzd(String str, List list, String str2, zzeg com_google_android_gms_internal_zzeg, String str3, double d, String str4, String str5, @Nullable zza com_google_android_gms_ads_internal_formats_zza, Bundle bundle, zzab com_google_android_gms_ads_internal_client_zzab, View view) {
        this.zzako = new Object();
        this.zzbmy = str;
        this.zzbmz = list;
        this.zzbna = str2;
        this.zzbnb = com_google_android_gms_internal_zzeg;
        this.zzbnc = str3;
        this.zzbnd = d;
        this.zzbne = str4;
        this.zzbnf = str5;
        this.zzbng = com_google_android_gms_ads_internal_formats_zza;
        this.mExtras = bundle;
        this.zzbnh = com_google_android_gms_ads_internal_client_zzab;
        this.zzbni = view;
    }

    public void destroy() {
        this.zzbmy = null;
        this.zzbmz = null;
        this.zzbna = null;
        this.zzbnb = null;
        this.zzbnc = null;
        this.zzbnd = 0.0d;
        this.zzbne = null;
        this.zzbnf = null;
        this.zzbng = null;
        this.mExtras = null;
        this.zzako = null;
        this.zzbnj = null;
        this.zzbnh = null;
        this.zzbni = null;
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

    public String getPrice() {
        return this.zzbnf;
    }

    public double getStarRating() {
        return this.zzbnd;
    }

    public String getStore() {
        return this.zzbne;
    }

    public void zzb(zzi com_google_android_gms_ads_internal_formats_zzi) {
        synchronized (this.zzako) {
            this.zzbnj = com_google_android_gms_ads_internal_formats_zzi;
        }
    }

    public zzab zzej() {
        return this.zzbnh;
    }

    public zzeg zzmo() {
        return this.zzbnb;
    }

    public com.google.android.gms.dynamic.zzd zzmp() {
        return zze.zzac(this.zzbnj);
    }

    public String zzmq() {
        return "2";
    }

    public zza zzmr() {
        return this.zzbng;
    }

    public View zzms() {
        return this.zzbni;
    }
}
