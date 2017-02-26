package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.VideoOptionsParcel;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzu.zza;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.ads.internal.client.zzy;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzd;
import com.google.android.gms.ads.internal.zzl;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzgc extends zza {
    private final String zzant;
    private final zzfw zzbsn;
    @Nullable
    private zzl zzbss;
    private final zzfy zzbsz;
    @Nullable
    private zzik zzbta;
    private String zzbtb;

    public zzgc(Context context, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        this(str, new zzfw(context, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd));
    }

    zzgc(String str, zzfw com_google_android_gms_internal_zzfw) {
        this.zzant = str;
        this.zzbsn = com_google_android_gms_internal_zzfw;
        this.zzbsz = new zzfy();
        zzu.zzhb().zza(com_google_android_gms_internal_zzfw);
    }

    private void zznu() {
        if (this.zzbss != null && this.zzbta != null) {
            this.zzbss.zza(this.zzbta, this.zzbtb);
        }
    }

    static boolean zzq(AdRequestParcel adRequestParcel) {
        Bundle zzk = zzfz.zzk(adRequestParcel);
        return zzk != null && zzk.containsKey("gw");
    }

    static boolean zzr(AdRequestParcel adRequestParcel) {
        Bundle zzk = zzfz.zzk(adRequestParcel);
        return zzk != null && zzk.containsKey("_ad");
    }

    void abort() {
        if (this.zzbss == null) {
            this.zzbss = this.zzbsn.zzbj(this.zzant);
            this.zzbsz.zzc(this.zzbss);
            zznu();
        }
    }

    public void destroy() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.destroy();
        }
    }

    @Nullable
    public String getMediationAdapterClassName() throws RemoteException {
        return this.zzbss != null ? this.zzbss.getMediationAdapterClassName() : null;
    }

    public boolean isLoading() throws RemoteException {
        return this.zzbss != null && this.zzbss.isLoading();
    }

    public boolean isReady() throws RemoteException {
        return this.zzbss != null && this.zzbss.isReady();
    }

    public void pause() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.pause();
        }
    }

    public void resume() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.resume();
        }
    }

    public void setManualImpressionsEnabled(boolean z) throws RemoteException {
        abort();
        if (this.zzbss != null) {
            this.zzbss.setManualImpressionsEnabled(z);
        }
    }

    public void setUserId(String str) {
    }

    public void showInterstitial() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.showInterstitial();
        } else {
            zzb.zzdi("Interstitial ad must be loaded before showInterstitial().");
        }
    }

    public void stopLoading() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.stopLoading();
        }
    }

    public void zza(AdSizeParcel adSizeParcel) throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.zza(adSizeParcel);
        }
    }

    public void zza(VideoOptionsParcel videoOptionsParcel) {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }

    public void zza(zzp com_google_android_gms_ads_internal_client_zzp) throws RemoteException {
        this.zzbsz.zzbsi = com_google_android_gms_ads_internal_client_zzp;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzq com_google_android_gms_ads_internal_client_zzq) throws RemoteException {
        this.zzbsz.zzanl = com_google_android_gms_ads_internal_client_zzq;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzw com_google_android_gms_ads_internal_client_zzw) throws RemoteException {
        this.zzbsz.zzbsf = com_google_android_gms_ads_internal_client_zzw;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzy com_google_android_gms_ads_internal_client_zzy) throws RemoteException {
        abort();
        if (this.zzbss != null) {
            this.zzbss.zza(com_google_android_gms_ads_internal_client_zzy);
        }
    }

    public void zza(com.google.android.gms.ads.internal.reward.client.zzd com_google_android_gms_ads_internal_reward_client_zzd) {
        this.zzbsz.zzbsj = com_google_android_gms_ads_internal_reward_client_zzd;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzed com_google_android_gms_internal_zzed) throws RemoteException {
        this.zzbsz.zzbsh = com_google_android_gms_internal_zzed;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzig com_google_android_gms_internal_zzig) throws RemoteException {
        this.zzbsz.zzbsg = com_google_android_gms_internal_zzig;
        if (this.zzbss != null) {
            this.zzbsz.zzc(this.zzbss);
        }
    }

    public void zza(zzik com_google_android_gms_internal_zzik, String str) throws RemoteException {
        this.zzbta = com_google_android_gms_internal_zzik;
        this.zzbtb = str;
        zznu();
    }

    public boolean zzb(AdRequestParcel adRequestParcel) throws RemoteException {
        if (((Boolean) zzdr.zzbge.get()).booleanValue()) {
            AdRequestParcel.zzj(adRequestParcel);
        }
        if (!zzq(adRequestParcel)) {
            abort();
        }
        if (zzfz.zzm(adRequestParcel)) {
            abort();
        }
        if (adRequestParcel.zzays != null) {
            abort();
        }
        if (this.zzbss != null) {
            return this.zzbss.zzb(adRequestParcel);
        }
        zzfz zzhb = zzu.zzhb();
        if (zzr(adRequestParcel)) {
            zzhb.zzb(adRequestParcel, this.zzant);
        }
        zza zza = zzhb.zza(adRequestParcel, this.zzant);
        if (zza != null) {
            if (!zza.zzbsw) {
                zza.zznt();
            }
            this.zzbss = zza.zzbss;
            zza.zzbsu.zza(this.zzbsz);
            this.zzbsz.zzc(this.zzbss);
            zznu();
            return zza.zzbsx;
        }
        abort();
        return this.zzbss.zzb(adRequestParcel);
    }

    @Nullable
    public com.google.android.gms.dynamic.zzd zzef() throws RemoteException {
        return this.zzbss != null ? this.zzbss.zzef() : null;
    }

    @Nullable
    public AdSizeParcel zzeg() throws RemoteException {
        return this.zzbss != null ? this.zzbss.zzeg() : null;
    }

    public void zzei() throws RemoteException {
        if (this.zzbss != null) {
            this.zzbss.zzei();
        } else {
            zzb.zzdi("Interstitial ad must be loaded before pingManualTrackingUrl().");
        }
    }

    public zzab zzej() {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }
}
