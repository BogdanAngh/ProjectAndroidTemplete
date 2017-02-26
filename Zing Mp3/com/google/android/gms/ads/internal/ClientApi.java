package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Keep;
import android.support.annotation.Nullable;
import android.widget.FrameLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzs;
import com.google.android.gms.ads.internal.client.zzu;
import com.google.android.gms.ads.internal.client.zzx.zza;
import com.google.android.gms.ads.internal.client.zzz;
import com.google.android.gms.ads.internal.formats.zzl;
import com.google.android.gms.ads.internal.reward.client.zzb;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.util.DynamiteApi;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzei;
import com.google.android.gms.internal.zzgc;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzhy;
import com.google.android.gms.internal.zzih;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzka;

@Keep
@zzji
@DynamiteApi
public class ClientApi extends zza {
    public zzs createAdLoaderBuilder(zzd com_google_android_gms_dynamic_zzd, String str, zzgz com_google_android_gms_internal_zzgz, int i) {
        return new zzk((Context) zze.zzae(com_google_android_gms_dynamic_zzd), str, com_google_android_gms_internal_zzgz, new VersionInfoParcel(9877000, i, true), zzd.zzfd());
    }

    public zzhy createAdOverlay(zzd com_google_android_gms_dynamic_zzd) {
        return new com.google.android.gms.ads.internal.overlay.zzd((Activity) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public zzu createBannerAdManager(zzd com_google_android_gms_dynamic_zzd, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, int i) throws RemoteException {
        return new zzf((Context) zze.zzae(com_google_android_gms_dynamic_zzd), adSizeParcel, str, com_google_android_gms_internal_zzgz, new VersionInfoParcel(9877000, i, true), zzd.zzfd());
    }

    public zzih createInAppPurchaseManager(zzd com_google_android_gms_dynamic_zzd) {
        return new com.google.android.gms.ads.internal.purchase.zze((Activity) zze.zzae(com_google_android_gms_dynamic_zzd));
    }

    public zzu createInterstitialAdManager(zzd com_google_android_gms_dynamic_zzd, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, int i) throws RemoteException {
        Context context = (Context) zze.zzae(com_google_android_gms_dynamic_zzd);
        zzdr.initialize(context);
        VersionInfoParcel versionInfoParcel = new VersionInfoParcel(9877000, i, true);
        boolean equals = "reward_mb".equals(adSizeParcel.zzazq);
        Object obj = ((equals || !((Boolean) zzdr.zzbgg.get()).booleanValue()) && !(equals && ((Boolean) zzdr.zzbgh.get()).booleanValue())) ? null : 1;
        if (obj != null) {
            return new zzgc(context, str, com_google_android_gms_internal_zzgz, versionInfoParcel, zzd.zzfd());
        }
        return new zzl(context, adSizeParcel, str, com_google_android_gms_internal_zzgz, versionInfoParcel, zzd.zzfd());
    }

    public zzei createNativeAdViewDelegate(zzd com_google_android_gms_dynamic_zzd, zzd com_google_android_gms_dynamic_zzd2) {
        return new zzl((FrameLayout) zze.zzae(com_google_android_gms_dynamic_zzd), (FrameLayout) zze.zzae(com_google_android_gms_dynamic_zzd2));
    }

    public zzb createRewardedVideoAd(zzd com_google_android_gms_dynamic_zzd, zzgz com_google_android_gms_internal_zzgz, int i) {
        return new zzka((Context) zze.zzae(com_google_android_gms_dynamic_zzd), zzd.zzfd(), com_google_android_gms_internal_zzgz, new VersionInfoParcel(9877000, i, true));
    }

    public zzu createSearchAdManager(zzd com_google_android_gms_dynamic_zzd, AdSizeParcel adSizeParcel, String str, int i) throws RemoteException {
        return new zzt((Context) zze.zzae(com_google_android_gms_dynamic_zzd), adSizeParcel, str, new VersionInfoParcel(9877000, i, true));
    }

    @Nullable
    public zzz getMobileAdsSettingsManager(zzd com_google_android_gms_dynamic_zzd) {
        return null;
    }

    public zzz getMobileAdsSettingsManagerWithClientJarVersion(zzd com_google_android_gms_dynamic_zzd, int i) {
        return zzo.zza((Context) zze.zzae(com_google_android_gms_dynamic_zzd), new VersionInfoParcel(9877000, i, true));
    }
}
