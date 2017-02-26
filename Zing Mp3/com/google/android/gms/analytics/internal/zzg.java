package com.google.android.gms.analytics.internal;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;

public class zzg {
    private final Context di;
    private final Context zzatc;

    public zzg(Context context) {
        zzaa.zzy(context);
        Object applicationContext = context.getApplicationContext();
        zzaa.zzb(applicationContext, (Object) "Application context can't be null");
        this.zzatc = applicationContext;
        this.di = applicationContext;
    }

    public Context getApplicationContext() {
        return this.zzatc;
    }

    protected zzu zza(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzu(com_google_android_gms_analytics_internal_zzf);
    }

    public Context zzacl() {
        return this.di;
    }

    protected zzi zzax(Context context) {
        return zzi.zzav(context);
    }

    protected zzk zzb(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzk(com_google_android_gms_analytics_internal_zzf);
    }

    protected zza zzc(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zza(com_google_android_gms_analytics_internal_zzf);
    }

    protected zzn zzd(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzn(com_google_android_gms_analytics_internal_zzf);
    }

    protected zzap zze(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzap(com_google_android_gms_analytics_internal_zzf);
    }

    protected zzaf zzf(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzaf(com_google_android_gms_analytics_internal_zzf);
    }

    protected zzr zzg(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzr(com_google_android_gms_analytics_internal_zzf);
    }

    protected zze zzh(zzf com_google_android_gms_analytics_internal_zzf) {
        return zzh.zzayl();
    }

    protected GoogleAnalytics zzi(zzf com_google_android_gms_analytics_internal_zzf) {
        return new GoogleAnalytics(com_google_android_gms_analytics_internal_zzf);
    }

    zzl zzj(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzl(com_google_android_gms_analytics_internal_zzf, this);
    }

    zzag zzk(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzag(com_google_android_gms_analytics_internal_zzf);
    }

    protected zzb zzl(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzb(com_google_android_gms_analytics_internal_zzf, this);
    }

    public zzj zzm(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzj(com_google_android_gms_analytics_internal_zzf);
    }

    public zzah zzn(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzah(com_google_android_gms_analytics_internal_zzf);
    }

    public zzi zzo(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzi(com_google_android_gms_analytics_internal_zzf);
    }

    public zzv zzp(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzv(com_google_android_gms_analytics_internal_zzf);
    }

    public zzai zzq(zzf com_google_android_gms_analytics_internal_zzf) {
        return new zzai(com_google_android_gms_analytics_internal_zzf);
    }
}
