package com.google.ads.mediation.customevent;

import android.app.Activity;
import android.view.View;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.customevent.CustomEventExtras;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class CustomEventAdapter implements MediationBannerAdapter<CustomEventExtras, CustomEventServerParameters>, MediationInterstitialAdapter<CustomEventExtras, CustomEventServerParameters> {
    private View zzgw;
    CustomEventBanner zzgx;
    CustomEventInterstitial zzgy;

    static final class zza implements CustomEventBannerListener {
        private final CustomEventAdapter zzgz;
        private final MediationBannerListener zzha;

        public zza(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.zzgz = customEventAdapter;
            this.zzha = mediationBannerListener;
        }

        public void onClick() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzha.onClick(this.zzgz);
        }

        public void onDismissScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzha.onDismissScreen(this.zzgz);
        }

        public void onFailedToReceiveAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzha.onFailedToReceiveAd(this.zzgz, ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzha.onLeaveApplication(this.zzgz);
        }

        public void onPresentScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzha.onPresentScreen(this.zzgz);
        }

        public void onReceivedAd(View view) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onReceivedAd.");
            this.zzgz.zza(view);
            this.zzha.onReceivedAd(this.zzgz);
        }
    }

    class zzb implements CustomEventInterstitialListener {
        private final CustomEventAdapter zzgz;
        private final MediationInterstitialListener zzhb;
        final /* synthetic */ CustomEventAdapter zzhc;

        public zzb(CustomEventAdapter customEventAdapter, CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
            this.zzhc = customEventAdapter;
            this.zzgz = customEventAdapter2;
            this.zzhb = mediationInterstitialListener;
        }

        public void onDismissScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onDismissScreen.");
            this.zzhb.onDismissScreen(this.zzgz);
        }

        public void onFailedToReceiveAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzhb.onFailedToReceiveAd(this.zzgz, ErrorCode.NO_FILL);
        }

        public void onLeaveApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onLeaveApplication.");
            this.zzhb.onLeaveApplication(this.zzgz);
        }

        public void onPresentScreen() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onPresentScreen.");
            this.zzhb.onPresentScreen(this.zzgz);
        }

        public void onReceivedAd() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onReceivedAd.");
            this.zzhb.onReceivedAd(this.zzhc);
        }
    }

    private void zza(View view) {
        this.zzgw = view;
    }

    private static <T> T zzj(String str) {
        try {
            return Class.forName(str).newInstance();
        } catch (Throwable th) {
            String valueOf = String.valueOf(th.getMessage());
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(new StringBuilder((String.valueOf(str).length() + 46) + String.valueOf(valueOf).length()).append("Could not instantiate custom event adapter: ").append(str).append(". ").append(valueOf).toString());
            return null;
        }
    }

    public void destroy() {
        if (this.zzgx != null) {
            this.zzgx.destroy();
        }
        if (this.zzgy != null) {
            this.zzgy.destroy();
        }
    }

    public Class<CustomEventExtras> getAdditionalParametersType() {
        return CustomEventExtras.class;
    }

    public View getBannerView() {
        return this.zzgw;
    }

    public Class<CustomEventServerParameters> getServerParametersType() {
        return CustomEventServerParameters.class;
    }

    public void requestBannerAd(MediationBannerListener mediationBannerListener, Activity activity, CustomEventServerParameters customEventServerParameters, AdSize adSize, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.zzgx = (CustomEventBanner) zzj(customEventServerParameters.className);
        if (this.zzgx == null) {
            mediationBannerListener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.zzgx.requestBannerAd(new zza(this, mediationBannerListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, adSize, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, Activity activity, CustomEventServerParameters customEventServerParameters, MediationAdRequest mediationAdRequest, CustomEventExtras customEventExtras) {
        this.zzgy = (CustomEventInterstitial) zzj(customEventServerParameters.className);
        if (this.zzgy == null) {
            mediationInterstitialListener.onFailedToReceiveAd(this, ErrorCode.INTERNAL_ERROR);
        } else {
            this.zzgy.requestInterstitialAd(zza(mediationInterstitialListener), activity, customEventServerParameters.label, customEventServerParameters.parameter, mediationAdRequest, customEventExtras == null ? null : customEventExtras.getExtra(customEventServerParameters.label));
        }
    }

    public void showInterstitial() {
        this.zzgy.showInterstitial();
    }

    zzb zza(MediationInterstitialListener mediationInterstitialListener) {
        return new zzb(this, this, mediationInterstitialListener);
    }
}
