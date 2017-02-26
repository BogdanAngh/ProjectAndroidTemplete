package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;
import com.mp3download.zingmp3.BuildConfig;

public class zzc {
    private final zzf cQ;

    protected zzc(zzf com_google_android_gms_analytics_internal_zzf) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzf);
        this.cQ = com_google_android_gms_analytics_internal_zzf;
    }

    private void zza(int i, String str, Object obj, Object obj2, Object obj3) {
        zzaf com_google_android_gms_analytics_internal_zzaf = null;
        if (this.cQ != null) {
            com_google_android_gms_analytics_internal_zzaf = this.cQ.zzacm();
        }
        if (com_google_android_gms_analytics_internal_zzaf != null) {
            com_google_android_gms_analytics_internal_zzaf.zza(i, str, obj, obj2, obj3);
            return;
        }
        String str2 = (String) zzy.en.get();
        if (Log.isLoggable(str2, i)) {
            Log.println(i, str2, zzc(str, obj, obj2, obj3));
        }
    }

    protected static String zzc(String str, Object obj, Object obj2, Object obj3) {
        if (str == null) {
            Object obj4 = BuildConfig.FLAVOR;
        }
        Object zzk = zzk(obj);
        Object zzk2 = zzk(obj2);
        Object zzk3 = zzk(obj3);
        StringBuilder stringBuilder = new StringBuilder();
        String str2 = BuildConfig.FLAVOR;
        if (!TextUtils.isEmpty(obj4)) {
            stringBuilder.append(obj4);
            str2 = ": ";
        }
        if (!TextUtils.isEmpty(zzk)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzk);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzk2)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzk2);
            str2 = ", ";
        }
        if (!TextUtils.isEmpty(zzk3)) {
            stringBuilder.append(str2);
            stringBuilder.append(zzk3);
            str2 = ", ";
        }
        return stringBuilder.toString();
    }

    private static String zzk(Object obj) {
        if (obj == null) {
            return BuildConfig.FLAVOR;
        }
        if (obj instanceof String) {
            return (String) obj;
        }
        if (!(obj instanceof Boolean)) {
            return obj instanceof Throwable ? ((Throwable) obj).toString() : obj.toString();
        } else {
            return obj == Boolean.TRUE ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false";
        }
    }

    protected Context getContext() {
        return this.cQ.getContext();
    }

    public void zza(String str, Object obj) {
        zza(2, str, obj, null, null);
    }

    public void zza(String str, Object obj, Object obj2) {
        zza(2, str, obj, obj2, null);
    }

    public void zza(String str, Object obj, Object obj2, Object obj3) {
        zza(3, str, obj, obj2, obj3);
    }

    public zzf zzabx() {
        return this.cQ;
    }

    protected void zzaby() {
        zzacb();
    }

    protected zze zzabz() {
        return this.cQ.zzabz();
    }

    protected zzaf zzaca() {
        return this.cQ.zzaca();
    }

    protected zzr zzacb() {
        return this.cQ.zzacb();
    }

    protected zzi zzacc() {
        return this.cQ.zzacc();
    }

    protected zzv zzacd() {
        return this.cQ.zzacd();
    }

    protected zzai zzace() {
        return this.cQ.zzace();
    }

    protected zzn zzacf() {
        return this.cQ.zzacq();
    }

    protected zza zzacg() {
        return this.cQ.zzacp();
    }

    protected zzk zzach() {
        return this.cQ.zzach();
    }

    protected zzu zzaci() {
        return this.cQ.zzaci();
    }

    public void zzb(String str, Object obj) {
        zza(3, str, obj, null, null);
    }

    public void zzb(String str, Object obj, Object obj2) {
        zza(3, str, obj, obj2, null);
    }

    public void zzb(String str, Object obj, Object obj2, Object obj3) {
        zza(5, str, obj, obj2, obj3);
    }

    public void zzc(String str, Object obj) {
        zza(4, str, obj, null, null);
    }

    public void zzc(String str, Object obj, Object obj2) {
        zza(5, str, obj, obj2, null);
    }

    public void zzd(String str, Object obj) {
        zza(5, str, obj, null, null);
    }

    public void zzd(String str, Object obj, Object obj2) {
        zza(6, str, obj, obj2, null);
    }

    public void zze(String str, Object obj) {
        zza(6, str, obj, null, null);
    }

    public void zzes(String str) {
        zza(2, str, null, null, null);
    }

    public void zzet(String str) {
        zza(3, str, null, null, null);
    }

    public void zzeu(String str) {
        zza(4, str, null, null, null);
    }

    public void zzev(String str) {
        zza(5, str, null, null, null);
    }

    public void zzew(String str) {
        zza(6, str, null, null, null);
    }

    public boolean zzvo() {
        return Log.isLoggable((String) zzy.en.get(), 2);
    }

    public GoogleAnalytics zzza() {
        return this.cQ.zzacn();
    }

    protected zzb zzzg() {
        return this.cQ.zzzg();
    }

    protected zzap zzzh() {
        return this.cQ.zzzh();
    }

    protected void zzzx() {
        this.cQ.zzzx();
    }
}
