package com.google.android.gms.internal;

import android.os.RemoteException;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.zzp;
import com.google.android.gms.ads.internal.client.zzq;
import com.google.android.gms.ads.internal.client.zzw;
import com.google.android.gms.ads.internal.reward.client.zzd;
import com.google.android.gms.ads.internal.zzl;
import com.google.android.gms.ads.internal.zzu;

@zzji
class zzfy {
    @Nullable
    zzq zzanl;
    @Nullable
    zzw zzbsf;
    @Nullable
    zzig zzbsg;
    @Nullable
    zzed zzbsh;
    @Nullable
    zzp zzbsi;
    @Nullable
    zzd zzbsj;

    private static class zza extends com.google.android.gms.ads.internal.client.zzq.zza {
        private final zzq zzbsk;

        zza(zzq com_google_android_gms_ads_internal_client_zzq) {
            this.zzbsk = com_google_android_gms_ads_internal_client_zzq;
        }

        public void onAdClosed() throws RemoteException {
            this.zzbsk.onAdClosed();
            zzu.zzhb().zznm();
        }

        public void onAdFailedToLoad(int i) throws RemoteException {
            this.zzbsk.onAdFailedToLoad(i);
        }

        public void onAdLeftApplication() throws RemoteException {
            this.zzbsk.onAdLeftApplication();
        }

        public void onAdLoaded() throws RemoteException {
            this.zzbsk.onAdLoaded();
        }

        public void onAdOpened() throws RemoteException {
            this.zzbsk.onAdOpened();
        }
    }

    zzfy() {
    }

    void zzc(zzl com_google_android_gms_ads_internal_zzl) {
        if (this.zzanl != null) {
            com_google_android_gms_ads_internal_zzl.zza(new zza(this.zzanl));
        }
        if (this.zzbsf != null) {
            com_google_android_gms_ads_internal_zzl.zza(this.zzbsf);
        }
        if (this.zzbsg != null) {
            com_google_android_gms_ads_internal_zzl.zza(this.zzbsg);
        }
        if (this.zzbsh != null) {
            com_google_android_gms_ads_internal_zzl.zza(this.zzbsh);
        }
        if (this.zzbsi != null) {
            com_google_android_gms_ads_internal_zzl.zza(this.zzbsi);
        }
        if (this.zzbsj != null) {
            com_google_android_gms_ads_internal_zzl.zza(this.zzbsj);
        }
    }
}
