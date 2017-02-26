package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.internal.client.zzu.zza;
import com.google.android.gms.ads.internal.reward.client.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzig;
import com.google.android.gms.internal.zzik;

public class zzak extends zza {
    private zzq zzanl;

    /* renamed from: com.google.android.gms.ads.internal.client.zzak.1 */
    class C10571 implements Runnable {
        final /* synthetic */ zzak zzbbu;

        C10571(zzak com_google_android_gms_ads_internal_client_zzak) {
            this.zzbbu = com_google_android_gms_ads_internal_client_zzak;
        }

        public void run() {
            if (this.zzbbu.zzanl != null) {
                try {
                    this.zzbbu.zzanl.onAdFailedToLoad(1);
                } catch (Throwable e) {
                    zzb.zzc("Could not notify onAdFailedToLoad event.", e);
                }
            }
        }
    }

    public void destroy() {
    }

    public String getMediationAdapterClassName() {
        return null;
    }

    public boolean isLoading() {
        return false;
    }

    public boolean isReady() {
        return false;
    }

    public void pause() {
    }

    public void resume() {
    }

    public void setManualImpressionsEnabled(boolean z) {
    }

    public void setUserId(String str) {
    }

    public void showInterstitial() {
    }

    public void stopLoading() {
    }

    public void zza(AdSizeParcel adSizeParcel) {
    }

    public void zza(VideoOptionsParcel videoOptionsParcel) {
    }

    public void zza(zzp com_google_android_gms_ads_internal_client_zzp) {
    }

    public void zza(zzq com_google_android_gms_ads_internal_client_zzq) {
        this.zzanl = com_google_android_gms_ads_internal_client_zzq;
    }

    public void zza(zzw com_google_android_gms_ads_internal_client_zzw) {
    }

    public void zza(zzy com_google_android_gms_ads_internal_client_zzy) {
    }

    public void zza(zzd com_google_android_gms_ads_internal_reward_client_zzd) {
    }

    public void zza(zzed com_google_android_gms_internal_zzed) {
    }

    public void zza(zzig com_google_android_gms_internal_zzig) {
    }

    public void zza(zzik com_google_android_gms_internal_zzik, String str) {
    }

    public boolean zzb(AdRequestParcel adRequestParcel) {
        zzb.m1695e("This app is using a lightweight version of the Google Mobile Ads SDK that requires the latest Google Play services to be installed, but Google Play services is either missing or out of date.");
        com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C10571(this));
        return false;
    }

    public com.google.android.gms.dynamic.zzd zzef() {
        return null;
    }

    public AdSizeParcel zzeg() {
        return null;
    }

    public void zzei() {
    }

    public zzab zzej() {
        return null;
    }
}
