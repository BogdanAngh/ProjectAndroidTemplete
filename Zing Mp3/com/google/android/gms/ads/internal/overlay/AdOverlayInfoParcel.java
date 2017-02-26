package com.google.android.gms.ads.internal.overlay;

import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.client.zza;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzfa;
import com.google.android.gms.internal.zzfg;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmd;

@zzji
public final class AdOverlayInfoParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<AdOverlayInfoParcel> CREATOR;
    public final int orientation;
    public final String url;
    public final int versionCode;
    public final VersionInfoParcel zzari;
    public final AdLauncherIntentInfoParcel zzcbj;
    public final zza zzcbk;
    public final zzg zzcbl;
    public final zzmd zzcbm;
    public final zzfa zzcbn;
    public final String zzcbo;
    public final boolean zzcbp;
    public final String zzcbq;
    public final zzp zzcbr;
    public final int zzcbs;
    public final zzfg zzcbt;
    public final String zzcbu;
    public final InterstitialAdParameterParcel zzcbv;

    static {
        CREATOR = new zzf();
    }

    AdOverlayInfoParcel(int i, AdLauncherIntentInfoParcel adLauncherIntentInfoParcel, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4, String str, boolean z, String str2, IBinder iBinder5, int i2, int i3, String str3, VersionInfoParcel versionInfoParcel, IBinder iBinder6, String str4, InterstitialAdParameterParcel interstitialAdParameterParcel) {
        this.versionCode = i;
        this.zzcbj = adLauncherIntentInfoParcel;
        this.zzcbk = (zza) zze.zzae(zzd.zza.zzfd(iBinder));
        this.zzcbl = (zzg) zze.zzae(zzd.zza.zzfd(iBinder2));
        this.zzcbm = (zzmd) zze.zzae(zzd.zza.zzfd(iBinder3));
        this.zzcbn = (zzfa) zze.zzae(zzd.zza.zzfd(iBinder4));
        this.zzcbo = str;
        this.zzcbp = z;
        this.zzcbq = str2;
        this.zzcbr = (zzp) zze.zzae(zzd.zza.zzfd(iBinder5));
        this.orientation = i2;
        this.zzcbs = i3;
        this.url = str3;
        this.zzari = versionInfoParcel;
        this.zzcbt = (zzfg) zze.zzae(zzd.zza.zzfd(iBinder6));
        this.zzcbu = str4;
        this.zzcbv = interstitialAdParameterParcel;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, zzmd com_google_android_gms_internal_zzmd, int i, VersionInfoParcel versionInfoParcel, String str, InterstitialAdParameterParcel interstitialAdParameterParcel) {
        this.versionCode = 4;
        this.zzcbj = null;
        this.zzcbk = com_google_android_gms_ads_internal_client_zza;
        this.zzcbl = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzcbm = com_google_android_gms_internal_zzmd;
        this.zzcbn = null;
        this.zzcbo = null;
        this.zzcbp = false;
        this.zzcbq = null;
        this.zzcbr = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzcbs = 1;
        this.url = null;
        this.zzari = versionInfoParcel;
        this.zzcbt = null;
        this.zzcbu = str;
        this.zzcbv = interstitialAdParameterParcel;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, zzmd com_google_android_gms_internal_zzmd, boolean z, int i, VersionInfoParcel versionInfoParcel) {
        this.versionCode = 4;
        this.zzcbj = null;
        this.zzcbk = com_google_android_gms_ads_internal_client_zza;
        this.zzcbl = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzcbm = com_google_android_gms_internal_zzmd;
        this.zzcbn = null;
        this.zzcbo = null;
        this.zzcbp = z;
        this.zzcbq = null;
        this.zzcbr = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzcbs = 2;
        this.url = null;
        this.zzari = versionInfoParcel;
        this.zzcbt = null;
        this.zzcbu = null;
        this.zzcbv = null;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzfa com_google_android_gms_internal_zzfa, zzp com_google_android_gms_ads_internal_overlay_zzp, zzmd com_google_android_gms_internal_zzmd, boolean z, int i, String str, VersionInfoParcel versionInfoParcel, zzfg com_google_android_gms_internal_zzfg) {
        this.versionCode = 4;
        this.zzcbj = null;
        this.zzcbk = com_google_android_gms_ads_internal_client_zza;
        this.zzcbl = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzcbm = com_google_android_gms_internal_zzmd;
        this.zzcbn = com_google_android_gms_internal_zzfa;
        this.zzcbo = null;
        this.zzcbp = z;
        this.zzcbq = null;
        this.zzcbr = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzcbs = 3;
        this.url = str;
        this.zzari = versionInfoParcel;
        this.zzcbt = com_google_android_gms_internal_zzfg;
        this.zzcbu = null;
        this.zzcbv = null;
    }

    public AdOverlayInfoParcel(zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzfa com_google_android_gms_internal_zzfa, zzp com_google_android_gms_ads_internal_overlay_zzp, zzmd com_google_android_gms_internal_zzmd, boolean z, int i, String str, String str2, VersionInfoParcel versionInfoParcel, zzfg com_google_android_gms_internal_zzfg) {
        this.versionCode = 4;
        this.zzcbj = null;
        this.zzcbk = com_google_android_gms_ads_internal_client_zza;
        this.zzcbl = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzcbm = com_google_android_gms_internal_zzmd;
        this.zzcbn = com_google_android_gms_internal_zzfa;
        this.zzcbo = str2;
        this.zzcbp = z;
        this.zzcbq = str;
        this.zzcbr = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = i;
        this.zzcbs = 3;
        this.url = null;
        this.zzari = versionInfoParcel;
        this.zzcbt = com_google_android_gms_internal_zzfg;
        this.zzcbu = null;
        this.zzcbv = null;
    }

    public AdOverlayInfoParcel(AdLauncherIntentInfoParcel adLauncherIntentInfoParcel, zza com_google_android_gms_ads_internal_client_zza, zzg com_google_android_gms_ads_internal_overlay_zzg, zzp com_google_android_gms_ads_internal_overlay_zzp, VersionInfoParcel versionInfoParcel) {
        this.versionCode = 4;
        this.zzcbj = adLauncherIntentInfoParcel;
        this.zzcbk = com_google_android_gms_ads_internal_client_zza;
        this.zzcbl = com_google_android_gms_ads_internal_overlay_zzg;
        this.zzcbm = null;
        this.zzcbn = null;
        this.zzcbo = null;
        this.zzcbp = false;
        this.zzcbq = null;
        this.zzcbr = com_google_android_gms_ads_internal_overlay_zzp;
        this.orientation = -1;
        this.zzcbs = 4;
        this.url = null;
        this.zzari = versionInfoParcel;
        this.zzcbt = null;
        this.zzcbu = null;
        this.zzcbv = null;
    }

    public static void zza(Intent intent, AdOverlayInfoParcel adOverlayInfoParcel) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", adOverlayInfoParcel);
        intent.putExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo", bundle);
    }

    public static AdOverlayInfoParcel zzb(Intent intent) {
        try {
            Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
            bundleExtra.setClassLoader(AdOverlayInfoParcel.class.getClassLoader());
            return (AdOverlayInfoParcel) bundleExtra.getParcelable("com.google.android.gms.ads.inernal.overlay.AdOverlayInfo");
        } catch (Exception e) {
            return null;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzf.zza(this, parcel, i);
    }

    IBinder zzpv() {
        return zze.zzac(this.zzcbk).asBinder();
    }

    IBinder zzpw() {
        return zze.zzac(this.zzcbl).asBinder();
    }

    IBinder zzpx() {
        return zze.zzac(this.zzcbm).asBinder();
    }

    IBinder zzpy() {
        return zze.zzac(this.zzcbn).asBinder();
    }

    IBinder zzpz() {
        return zze.zzac(this.zzcbt).asBinder();
    }

    IBinder zzqa() {
        return zze.zzac(this.zzcbr).asBinder();
    }
}
