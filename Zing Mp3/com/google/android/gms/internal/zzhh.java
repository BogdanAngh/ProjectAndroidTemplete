package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialAdapter;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.MediationNativeListener;
import com.google.android.gms.ads.mediation.NativeAdMapper;
import com.google.android.gms.common.internal.zzaa;

@zzji
public final class zzhh implements MediationBannerListener, MediationInterstitialListener, MediationNativeListener {
    private final zzhb zzbxj;
    private NativeAdMapper zzbxk;

    public zzhh(zzhb com_google_android_gms_internal_zzhb) {
        this.zzbxj = com_google_android_gms_internal_zzhb;
    }

    public void onAdClicked(MediationBannerAdapter mediationBannerAdapter) {
        zzaa.zzhs("onAdClicked must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdClicked.");
        try {
            this.zzbxj.onAdClicked();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdClicked.", e);
        }
    }

    public void onAdClicked(MediationInterstitialAdapter mediationInterstitialAdapter) {
        zzaa.zzhs("onAdClicked must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdClicked.");
        try {
            this.zzbxj.onAdClicked();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdClicked.", e);
        }
    }

    public void onAdClicked(MediationNativeAdapter mediationNativeAdapter) {
        zzaa.zzhs("onAdClicked must be called on the main UI thread.");
        NativeAdMapper zzoq = zzoq();
        if (zzoq == null) {
            zzb.zzdi("Could not call onAdClicked since NativeAdMapper is null.");
        } else if (zzoq.getOverrideClickHandling()) {
            zzb.zzdg("Adapter called onAdClicked.");
            try {
                this.zzbxj.onAdClicked();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClicked.", e);
            }
        } else {
            zzb.zzdg("Could not call onAdClicked since setOverrideClickHandling is not set to true");
        }
    }

    public void onAdClosed(MediationBannerAdapter mediationBannerAdapter) {
        zzaa.zzhs("onAdClosed must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdClosed.");
        try {
            this.zzbxj.onAdClosed();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdClosed.", e);
        }
    }

    public void onAdClosed(MediationInterstitialAdapter mediationInterstitialAdapter) {
        zzaa.zzhs("onAdClosed must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdClosed.");
        try {
            this.zzbxj.onAdClosed();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdClosed.", e);
        }
    }

    public void onAdClosed(MediationNativeAdapter mediationNativeAdapter) {
        zzaa.zzhs("onAdClosed must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdClosed.");
        try {
            this.zzbxj.onAdClosed();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdClosed.", e);
        }
    }

    public void onAdFailedToLoad(MediationBannerAdapter mediationBannerAdapter, int i) {
        zzaa.zzhs("onAdFailedToLoad must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdFailedToLoad with error. " + i);
        try {
            this.zzbxj.onAdFailedToLoad(i);
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onAdFailedToLoad(MediationInterstitialAdapter mediationInterstitialAdapter, int i) {
        zzaa.zzhs("onAdFailedToLoad must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdFailedToLoad with error " + i + ".");
        try {
            this.zzbxj.onAdFailedToLoad(i);
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onAdFailedToLoad(MediationNativeAdapter mediationNativeAdapter, int i) {
        zzaa.zzhs("onAdFailedToLoad must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdFailedToLoad with error " + i + ".");
        try {
            this.zzbxj.onAdFailedToLoad(i);
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdFailedToLoad.", e);
        }
    }

    public void onAdImpression(MediationNativeAdapter mediationNativeAdapter) {
        zzaa.zzhs("onAdImpression must be called on the main UI thread.");
        NativeAdMapper zzoq = zzoq();
        if (zzoq == null) {
            zzb.zzdi("Could not call onAdImpression since NativeAdMapper is null. ");
        } else if (zzoq.getOverrideImpressionRecording()) {
            zzb.zzdg("Adapter called onAdImpression.");
            try {
                this.zzbxj.onAdImpression();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdImpression.", e);
            }
        } else {
            zzb.zzdg("Could not call onAdImpression since setOverrideImpressionRecording is not set to true");
        }
    }

    public void onAdLeftApplication(MediationBannerAdapter mediationBannerAdapter) {
        zzaa.zzhs("onAdLeftApplication must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLeftApplication.");
        try {
            this.zzbxj.onAdLeftApplication();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLeftApplication.", e);
        }
    }

    public void onAdLeftApplication(MediationInterstitialAdapter mediationInterstitialAdapter) {
        zzaa.zzhs("onAdLeftApplication must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLeftApplication.");
        try {
            this.zzbxj.onAdLeftApplication();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLeftApplication.", e);
        }
    }

    public void onAdLeftApplication(MediationNativeAdapter mediationNativeAdapter) {
        zzaa.zzhs("onAdLeftApplication must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLeftApplication.");
        try {
            this.zzbxj.onAdLeftApplication();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLeftApplication.", e);
        }
    }

    public void onAdLoaded(MediationBannerAdapter mediationBannerAdapter) {
        zzaa.zzhs("onAdLoaded must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLoaded.");
        try {
            this.zzbxj.onAdLoaded();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLoaded.", e);
        }
    }

    public void onAdLoaded(MediationInterstitialAdapter mediationInterstitialAdapter) {
        zzaa.zzhs("onAdLoaded must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLoaded.");
        try {
            this.zzbxj.onAdLoaded();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLoaded.", e);
        }
    }

    public void onAdLoaded(MediationNativeAdapter mediationNativeAdapter, NativeAdMapper nativeAdMapper) {
        zzaa.zzhs("onAdLoaded must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdLoaded.");
        this.zzbxk = nativeAdMapper;
        try {
            this.zzbxj.onAdLoaded();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdLoaded.", e);
        }
    }

    public void onAdOpened(MediationBannerAdapter mediationBannerAdapter) {
        zzaa.zzhs("onAdOpened must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdOpened.");
        try {
            this.zzbxj.onAdOpened();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdOpened.", e);
        }
    }

    public void onAdOpened(MediationInterstitialAdapter mediationInterstitialAdapter) {
        zzaa.zzhs("onAdOpened must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdOpened.");
        try {
            this.zzbxj.onAdOpened();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdOpened.", e);
        }
    }

    public void onAdOpened(MediationNativeAdapter mediationNativeAdapter) {
        zzaa.zzhs("onAdOpened must be called on the main UI thread.");
        zzb.zzdg("Adapter called onAdOpened.");
        try {
            this.zzbxj.onAdOpened();
        } catch (Throwable e) {
            zzb.zzc("Could not call onAdOpened.", e);
        }
    }

    public NativeAdMapper zzoq() {
        return this.zzbxk;
    }
}
