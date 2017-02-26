package com.google.android.gms.ads.mediation.customevent;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.MediationNativeListener;
import com.google.android.gms.ads.mediation.NativeAdMapper;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class CustomEventAdapter implements MediationBannerAdapter, MediationInterstitialAdapter, MediationNativeAdapter {
    CustomEventBanner f1513S;
    CustomEventInterstitial f1514T;
    CustomEventNative f1515U;
    private View zzgw;

    static final class zza implements CustomEventBannerListener {
        private final CustomEventAdapter f1509V;
        private final MediationBannerListener zzgo;

        public zza(CustomEventAdapter customEventAdapter, MediationBannerListener mediationBannerListener) {
            this.f1509V = customEventAdapter;
            this.zzgo = mediationBannerListener;
        }

        public void onAdClicked() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClicked.");
            this.zzgo.onAdClicked(this.f1509V);
        }

        public void onAdClosed() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClosed.");
            this.zzgo.onAdClosed(this.f1509V);
        }

        public void onAdFailedToLoad(int i) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdFailedToLoad.");
            this.zzgo.onAdFailedToLoad(this.f1509V, i);
        }

        public void onAdLeftApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdLeftApplication.");
            this.zzgo.onAdLeftApplication(this.f1509V);
        }

        public void onAdLoaded(View view) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdLoaded.");
            this.f1509V.zza(view);
            this.zzgo.onAdLoaded(this.f1509V);
        }

        public void onAdOpened() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdOpened.");
            this.zzgo.onAdOpened(this.f1509V);
        }
    }

    class zzb implements CustomEventInterstitialListener {
        private final CustomEventAdapter f1510V;
        final /* synthetic */ CustomEventAdapter f1511W;
        private final MediationInterstitialListener zzgp;

        public zzb(CustomEventAdapter customEventAdapter, CustomEventAdapter customEventAdapter2, MediationInterstitialListener mediationInterstitialListener) {
            this.f1511W = customEventAdapter;
            this.f1510V = customEventAdapter2;
            this.zzgp = mediationInterstitialListener;
        }

        public void onAdClicked() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClicked.");
            this.zzgp.onAdClicked(this.f1510V);
        }

        public void onAdClosed() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClosed.");
            this.zzgp.onAdClosed(this.f1510V);
        }

        public void onAdFailedToLoad(int i) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onFailedToReceiveAd.");
            this.zzgp.onAdFailedToLoad(this.f1510V, i);
        }

        public void onAdLeftApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdLeftApplication.");
            this.zzgp.onAdLeftApplication(this.f1510V);
        }

        public void onAdLoaded() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onReceivedAd.");
            this.zzgp.onAdLoaded(this.f1511W);
        }

        public void onAdOpened() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdOpened.");
            this.zzgp.onAdOpened(this.f1510V);
        }
    }

    static class zzc implements CustomEventNativeListener {
        private final CustomEventAdapter f1512V;
        private final MediationNativeListener zzgq;

        public zzc(CustomEventAdapter customEventAdapter, MediationNativeListener mediationNativeListener) {
            this.f1512V = customEventAdapter;
            this.zzgq = mediationNativeListener;
        }

        public void onAdClicked() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClicked.");
            this.zzgq.onAdClicked(this.f1512V);
        }

        public void onAdClosed() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdClosed.");
            this.zzgq.onAdClosed(this.f1512V);
        }

        public void onAdFailedToLoad(int i) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdFailedToLoad.");
            this.zzgq.onAdFailedToLoad(this.f1512V, i);
        }

        public void onAdImpression() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdImpression.");
            this.zzgq.onAdImpression(this.f1512V);
        }

        public void onAdLeftApplication() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdLeftApplication.");
            this.zzgq.onAdLeftApplication(this.f1512V);
        }

        public void onAdLoaded(NativeAdMapper nativeAdMapper) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdLoaded.");
            this.zzgq.onAdLoaded(this.f1512V, nativeAdMapper);
        }

        public void onAdOpened() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Custom event adapter called onAdOpened.");
            this.zzgq.onAdOpened(this.f1512V);
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

    public View getBannerView() {
        return this.zzgw;
    }

    public void onDestroy() {
        if (this.f1513S != null) {
            this.f1513S.onDestroy();
        }
        if (this.f1514T != null) {
            this.f1514T.onDestroy();
        }
        if (this.f1515U != null) {
            this.f1515U.onDestroy();
        }
    }

    public void onPause() {
        if (this.f1513S != null) {
            this.f1513S.onPause();
        }
        if (this.f1514T != null) {
            this.f1514T.onPause();
        }
        if (this.f1515U != null) {
            this.f1515U.onPause();
        }
    }

    public void onResume() {
        if (this.f1513S != null) {
            this.f1513S.onResume();
        }
        if (this.f1514T != null) {
            this.f1514T.onResume();
        }
        if (this.f1515U != null) {
            this.f1515U.onResume();
        }
    }

    public void requestBannerAd(Context context, MediationBannerListener mediationBannerListener, Bundle bundle, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.f1513S = (CustomEventBanner) zzj(bundle.getString("class_name"));
        if (this.f1513S == null) {
            mediationBannerListener.onAdFailedToLoad(this, 0);
            return;
        }
        this.f1513S.requestBannerAd(context, new zza(this, mediationBannerListener), bundle.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD), adSize, mediationAdRequest, bundle2 == null ? null : bundle2.getBundle(bundle.getString("class_name")));
    }

    public void requestInterstitialAd(Context context, MediationInterstitialListener mediationInterstitialListener, Bundle bundle, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.f1514T = (CustomEventInterstitial) zzj(bundle.getString("class_name"));
        if (this.f1514T == null) {
            mediationInterstitialListener.onAdFailedToLoad(this, 0);
            return;
        }
        this.f1514T.requestInterstitialAd(context, zza(mediationInterstitialListener), bundle.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD), mediationAdRequest, bundle2 == null ? null : bundle2.getBundle(bundle.getString("class_name")));
    }

    public void requestNativeAd(Context context, MediationNativeListener mediationNativeListener, Bundle bundle, NativeMediationAdRequest nativeMediationAdRequest, Bundle bundle2) {
        this.f1515U = (CustomEventNative) zzj(bundle.getString("class_name"));
        if (this.f1515U == null) {
            mediationNativeListener.onAdFailedToLoad(this, 0);
            return;
        }
        this.f1515U.requestNativeAd(context, new zzc(this, mediationNativeListener), bundle.getString(MediationRewardedVideoAdAdapter.CUSTOM_EVENT_SERVER_PARAMETER_FIELD), nativeMediationAdRequest, bundle2 == null ? null : bundle2.getBundle(bundle.getString("class_name")));
    }

    public void showInterstitial() {
        this.f1514T.showInterstitial();
    }

    zzb zza(MediationInterstitialListener mediationInterstitialListener) {
        return new zzb(this, this, mediationInterstitialListener);
    }
}
