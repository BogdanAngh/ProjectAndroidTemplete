package com.google.android.gms.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.NativeAdMapper;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.mediation.OnContextChangedListener;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzha.zza;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;

@zzji
public final class zzhg extends zza {
    private final MediationAdapter zzbxh;
    private zzhh zzbxi;

    public zzhg(MediationAdapter mediationAdapter) {
        this.zzbxh = mediationAdapter;
    }

    private Bundle zza(String str, int i, String str2) throws RemoteException {
        String str3 = "Server parameters: ";
        String valueOf = String.valueOf(str);
        zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
        try {
            Bundle bundle = new Bundle();
            if (str != null) {
                JSONObject jSONObject = new JSONObject(str);
                Bundle bundle2 = new Bundle();
                Iterator keys = jSONObject.keys();
                while (keys.hasNext()) {
                    valueOf = (String) keys.next();
                    bundle2.putString(valueOf, jSONObject.getString(valueOf));
                }
                bundle = bundle2;
            }
            if (this.zzbxh instanceof AdMobAdapter) {
                bundle.putString("adJson", str2);
                bundle.putInt("tagForChildDirectedTreatment", i);
            }
            return bundle;
        } catch (Throwable th) {
            zzb.zzc("Could not get Server Parameters Bundle.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void destroy() throws RemoteException {
        try {
            this.zzbxh.onDestroy();
        } catch (Throwable th) {
            zzb.zzc("Could not destroy adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public Bundle getInterstitialAdapterInfo() {
        if (this.zzbxh instanceof zzmr) {
            return ((zzmr) this.zzbxh).getInterstitialAdapterInfo();
        }
        String str = "MediationAdapter is not a v2 MediationInterstitialAdapter: ";
        String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
        zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        return new Bundle();
    }

    public zzd getView() throws RemoteException {
        if (this.zzbxh instanceof MediationBannerAdapter) {
            try {
                return zze.zzac(((MediationBannerAdapter) this.zzbxh).getBannerView());
            } catch (Throwable th) {
                zzb.zzc("Could not get banner view from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str = "MediationAdapter is not a MediationBannerAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw new RemoteException();
        }
    }

    public boolean isInitialized() throws RemoteException {
        if (this.zzbxh instanceof MediationRewardedVideoAdAdapter) {
            zzb.zzdg("Check if adapter is initialized.");
            try {
                return ((MediationRewardedVideoAdAdapter) this.zzbxh).isInitialized();
            } catch (Throwable th) {
                zzb.zzc("Could not check if adapter is initialized.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str = "MediationAdapter is not a MediationRewardedVideoAdAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw new RemoteException();
        }
    }

    public void pause() throws RemoteException {
        try {
            this.zzbxh.onPause();
        } catch (Throwable th) {
            zzb.zzc("Could not pause adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void resume() throws RemoteException {
        try {
            this.zzbxh.onResume();
        } catch (Throwable th) {
            zzb.zzc("Could not resume adapter.", th);
            RemoteException remoteException = new RemoteException();
        }
    }

    public void showInterstitial() throws RemoteException {
        if (this.zzbxh instanceof MediationInterstitialAdapter) {
            zzb.zzdg("Showing interstitial from adapter.");
            try {
                ((MediationInterstitialAdapter) this.zzbxh).showInterstitial();
            } catch (Throwable th) {
                zzb.zzc("Could not show interstitial from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str = "MediationAdapter is not a MediationInterstitialAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw new RemoteException();
        }
    }

    public void showVideo() throws RemoteException {
        if (this.zzbxh instanceof MediationRewardedVideoAdAdapter) {
            zzb.zzdg("Show rewarded video ad from adapter.");
            try {
                ((MediationRewardedVideoAdAdapter) this.zzbxh).showVideo();
            } catch (Throwable th) {
                zzb.zzc("Could not show rewarded video ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str = "MediationAdapter is not a MediationRewardedVideoAdAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            throw new RemoteException();
        }
    }

    public void zza(AdRequestParcel adRequestParcel, String str, String str2) throws RemoteException {
        if (this.zzbxh instanceof MediationRewardedVideoAdAdapter) {
            zzb.zzdg("Requesting rewarded video ad from adapter.");
            try {
                MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter = (MediationRewardedVideoAdAdapter) this.zzbxh;
                mediationRewardedVideoAdAdapter.loadAd(new zzhf(adRequestParcel.zzayl == -1 ? null : new Date(adRequestParcel.zzayl), adRequestParcel.zzaym, adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayt, adRequestParcel.zzayo, adRequestParcel.zzayp, adRequestParcel.zzaza), zza(str, adRequestParcel.zzayp, str2), adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(mediationRewardedVideoAdAdapter.getClass().getName()) : null);
            } catch (Throwable th) {
                zzb.zzc("Could not load rewarded video ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str3 = "MediationAdapter is not a MediationRewardedVideoAdAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            throw new RemoteException();
        }
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdRequestParcel adRequestParcel, String str, com.google.android.gms.ads.internal.reward.mediation.client.zza com_google_android_gms_ads_internal_reward_mediation_client_zza, String str2) throws RemoteException {
        if (this.zzbxh instanceof MediationRewardedVideoAdAdapter) {
            zzb.zzdg("Initialize rewarded video adapter.");
            try {
                MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter = (MediationRewardedVideoAdAdapter) this.zzbxh;
                mediationRewardedVideoAdAdapter.initialize((Context) zze.zzae(com_google_android_gms_dynamic_zzd), new zzhf(adRequestParcel.zzayl == -1 ? null : new Date(adRequestParcel.zzayl), adRequestParcel.zzaym, adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayt, adRequestParcel.zzayo, adRequestParcel.zzayp, adRequestParcel.zzaza), str, new com.google.android.gms.ads.internal.reward.mediation.client.zzb(com_google_android_gms_ads_internal_reward_mediation_client_zza), zza(str2, adRequestParcel.zzayp, null), adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(mediationRewardedVideoAdAdapter.getClass().getName()) : null);
            } catch (Throwable th) {
                zzb.zzc("Could not initialize rewarded video adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str3 = "MediationAdapter is not a MediationRewardedVideoAdAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            throw new RemoteException();
        }
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdRequestParcel adRequestParcel, String str, zzhb com_google_android_gms_internal_zzhb) throws RemoteException {
        zza(com_google_android_gms_dynamic_zzd, adRequestParcel, str, null, com_google_android_gms_internal_zzhb);
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdRequestParcel adRequestParcel, String str, String str2, zzhb com_google_android_gms_internal_zzhb) throws RemoteException {
        if (this.zzbxh instanceof MediationInterstitialAdapter) {
            zzb.zzdg("Requesting interstitial ad from adapter.");
            try {
                MediationInterstitialAdapter mediationInterstitialAdapter = (MediationInterstitialAdapter) this.zzbxh;
                mediationInterstitialAdapter.requestInterstitialAd((Context) zze.zzae(com_google_android_gms_dynamic_zzd), new zzhh(com_google_android_gms_internal_zzhb), zza(str, adRequestParcel.zzayp, str2), new zzhf(adRequestParcel.zzayl == -1 ? null : new Date(adRequestParcel.zzayl), adRequestParcel.zzaym, adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayt, adRequestParcel.zzayo, adRequestParcel.zzayp, adRequestParcel.zzaza), adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(mediationInterstitialAdapter.getClass().getName()) : null);
            } catch (Throwable th) {
                zzb.zzc("Could not request interstitial ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str3 = "MediationAdapter is not a MediationInterstitialAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            throw new RemoteException();
        }
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdRequestParcel adRequestParcel, String str, String str2, zzhb com_google_android_gms_internal_zzhb, NativeAdOptionsParcel nativeAdOptionsParcel, List<String> list) throws RemoteException {
        if (this.zzbxh instanceof MediationNativeAdapter) {
            try {
                MediationNativeAdapter mediationNativeAdapter = (MediationNativeAdapter) this.zzbxh;
                zzhk com_google_android_gms_internal_zzhk = new zzhk(adRequestParcel.zzayl == -1 ? null : new Date(adRequestParcel.zzayl), adRequestParcel.zzaym, adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayt, adRequestParcel.zzayo, adRequestParcel.zzayp, nativeAdOptionsParcel, list, adRequestParcel.zzaza);
                Bundle bundle = adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(mediationNativeAdapter.getClass().getName()) : null;
                this.zzbxi = new zzhh(com_google_android_gms_internal_zzhb);
                mediationNativeAdapter.requestNativeAd((Context) zze.zzae(com_google_android_gms_dynamic_zzd), this.zzbxi, zza(str, adRequestParcel.zzayp, str2), com_google_android_gms_internal_zzhk, bundle);
            } catch (Throwable th) {
                zzb.zzc("Could not request native ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str3 = "MediationAdapter is not a MediationNativeAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            throw new RemoteException();
        }
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdSizeParcel adSizeParcel, AdRequestParcel adRequestParcel, String str, zzhb com_google_android_gms_internal_zzhb) throws RemoteException {
        zza(com_google_android_gms_dynamic_zzd, adSizeParcel, adRequestParcel, str, null, com_google_android_gms_internal_zzhb);
    }

    public void zza(zzd com_google_android_gms_dynamic_zzd, AdSizeParcel adSizeParcel, AdRequestParcel adRequestParcel, String str, String str2, zzhb com_google_android_gms_internal_zzhb) throws RemoteException {
        if (this.zzbxh instanceof MediationBannerAdapter) {
            zzb.zzdg("Requesting banner ad from adapter.");
            try {
                MediationBannerAdapter mediationBannerAdapter = (MediationBannerAdapter) this.zzbxh;
                mediationBannerAdapter.requestBannerAd((Context) zze.zzae(com_google_android_gms_dynamic_zzd), new zzhh(com_google_android_gms_internal_zzhb), zza(str, adRequestParcel.zzayp, str2), com.google.android.gms.ads.zza.zza(adSizeParcel.width, adSizeParcel.height, adSizeParcel.zzazq), new zzhf(adRequestParcel.zzayl == -1 ? null : new Date(adRequestParcel.zzayl), adRequestParcel.zzaym, adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayt, adRequestParcel.zzayo, adRequestParcel.zzayp, adRequestParcel.zzaza), adRequestParcel.zzayv != null ? adRequestParcel.zzayv.getBundle(mediationBannerAdapter.getClass().getName()) : null);
            } catch (Throwable th) {
                zzb.zzc("Could not request banner ad from adapter.", th);
                RemoteException remoteException = new RemoteException();
            }
        } else {
            String str3 = "MediationAdapter is not a MediationBannerAdapter: ";
            String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
            zzb.zzdi(valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            throw new RemoteException();
        }
    }

    public void zzc(AdRequestParcel adRequestParcel, String str) throws RemoteException {
        zza(adRequestParcel, str, null);
    }

    public void zzj(zzd com_google_android_gms_dynamic_zzd) throws RemoteException {
        try {
            ((OnContextChangedListener) this.zzbxh).onContextChanged((Context) zze.zzae(com_google_android_gms_dynamic_zzd));
        } catch (Throwable th) {
            zzb.zza("Could not inform adapter of changed context", th);
        }
    }

    public zzhd zzom() {
        NativeAdMapper zzoq = this.zzbxi.zzoq();
        return zzoq instanceof NativeAppInstallAdMapper ? new zzhi((NativeAppInstallAdMapper) zzoq) : null;
    }

    public zzhe zzon() {
        NativeAdMapper zzoq = this.zzbxi.zzoq();
        return zzoq instanceof NativeContentAdMapper ? new zzhj((NativeContentAdMapper) zzoq) : null;
    }

    public Bundle zzoo() {
        if (this.zzbxh instanceof zzmq) {
            return ((zzmq) this.zzbxh).zzoo();
        }
        String str = "MediationAdapter is not a v2 MediationBannerAdapter: ";
        String valueOf = String.valueOf(this.zzbxh.getClass().getCanonicalName());
        zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        return new Bundle();
    }

    public Bundle zzop() {
        return new Bundle();
    }
}
