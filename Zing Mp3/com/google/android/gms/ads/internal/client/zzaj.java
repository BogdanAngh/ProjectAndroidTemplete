package com.google.android.gms.ads.internal.client;

import android.os.RemoteException;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zzes;
import com.google.android.gms.internal.zzet;

public class zzaj extends com.google.android.gms.ads.internal.client.zzs.zza {
    private zzq zzanl;

    private class zza extends com.google.android.gms.ads.internal.client.zzr.zza {
        final /* synthetic */ zzaj zzbbs;

        /* renamed from: com.google.android.gms.ads.internal.client.zzaj.zza.1 */
        class C10561 implements Runnable {
            final /* synthetic */ zza zzbbt;

            C10561(zza com_google_android_gms_ads_internal_client_zzaj_zza) {
                this.zzbbt = com_google_android_gms_ads_internal_client_zzaj_zza;
            }

            public void run() {
                if (this.zzbbt.zzbbs.zzanl != null) {
                    try {
                        this.zzbbt.zzbbs.zzanl.onAdFailedToLoad(1);
                    } catch (Throwable e) {
                        zzb.zzc("Could not notify onAdFailedToLoad event.", e);
                    }
                }
            }
        }

        private zza(zzaj com_google_android_gms_ads_internal_client_zzaj) {
            this.zzbbs = com_google_android_gms_ads_internal_client_zzaj;
        }

        public String getMediationAdapterClassName() throws RemoteException {
            return null;
        }

        public boolean isLoading() throws RemoteException {
            return false;
        }

        public void zzf(AdRequestParcel adRequestParcel) throws RemoteException {
            zzb.m1695e("This app is using a lightweight version of the Google Mobile Ads SDK that requires the latest Google Play services to be installed, but Google Play services is either missing or out of date.");
            com.google.android.gms.ads.internal.util.client.zza.zzcxr.post(new C10561(this));
        }
    }

    public void zza(NativeAdOptionsParcel nativeAdOptionsParcel) throws RemoteException {
    }

    public void zza(zzeq com_google_android_gms_internal_zzeq) throws RemoteException {
    }

    public void zza(zzer com_google_android_gms_internal_zzer) throws RemoteException {
    }

    public void zza(String str, zzet com_google_android_gms_internal_zzet, zzes com_google_android_gms_internal_zzes) throws RemoteException {
    }

    public void zzb(zzq com_google_android_gms_ads_internal_client_zzq) throws RemoteException {
        this.zzanl = com_google_android_gms_ads_internal_client_zzq;
    }

    public void zzb(zzy com_google_android_gms_ads_internal_client_zzy) throws RemoteException {
    }

    public zzr zzfl() throws RemoteException {
        return new zza();
    }
}
