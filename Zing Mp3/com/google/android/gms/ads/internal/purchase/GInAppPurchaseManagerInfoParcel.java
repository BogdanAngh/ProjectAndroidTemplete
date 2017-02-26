package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.dynamic.zzd.zza;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzif;
import com.google.android.gms.internal.zzji;

@zzji
public final class GInAppPurchaseManagerInfoParcel extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<GInAppPurchaseManagerInfoParcel> CREATOR;
    public final int versionCode;
    public final zzk zzasf;
    public final zzif zzcfa;
    public final Context zzcfb;
    public final zzj zzcfc;

    static {
        CREATOR = new zza();
    }

    GInAppPurchaseManagerInfoParcel(int i, IBinder iBinder, IBinder iBinder2, IBinder iBinder3, IBinder iBinder4) {
        this.versionCode = i;
        this.zzasf = (zzk) zze.zzae(zza.zzfd(iBinder));
        this.zzcfa = (zzif) zze.zzae(zza.zzfd(iBinder2));
        this.zzcfb = (Context) zze.zzae(zza.zzfd(iBinder3));
        this.zzcfc = (zzj) zze.zzae(zza.zzfd(iBinder4));
    }

    public GInAppPurchaseManagerInfoParcel(Context context, zzk com_google_android_gms_ads_internal_purchase_zzk, zzif com_google_android_gms_internal_zzif, zzj com_google_android_gms_ads_internal_purchase_zzj) {
        this.versionCode = 2;
        this.zzcfb = context;
        this.zzasf = com_google_android_gms_ads_internal_purchase_zzk;
        this.zzcfa = com_google_android_gms_internal_zzif;
        this.zzcfc = com_google_android_gms_ads_internal_purchase_zzj;
    }

    public static void zza(Intent intent, GInAppPurchaseManagerInfoParcel gInAppPurchaseManagerInfoParcel) {
        Bundle bundle = new Bundle(1);
        bundle.putParcelable("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo", gInAppPurchaseManagerInfoParcel);
        intent.putExtra("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo", bundle);
    }

    public static GInAppPurchaseManagerInfoParcel zzc(Intent intent) {
        try {
            Bundle bundleExtra = intent.getBundleExtra("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
            bundleExtra.setClassLoader(GInAppPurchaseManagerInfoParcel.class.getClassLoader());
            return (GInAppPurchaseManagerInfoParcel) bundleExtra.getParcelable("com.google.android.gms.ads.internal.purchase.InAppPurchaseManagerInfo");
        } catch (Exception e) {
            return null;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        zza.zza(this, parcel, i);
    }

    IBinder zzrn() {
        return zze.zzac(this.zzcfc).asBinder();
    }

    IBinder zzro() {
        return zze.zzac(this.zzasf).asBinder();
    }

    IBinder zzrp() {
        return zze.zzac(this.zzcfa).asBinder();
    }

    IBinder zzrq() {
        return zze.zzac(this.zzcfb).asBinder();
    }
}
