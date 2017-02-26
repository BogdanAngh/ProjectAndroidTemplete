package com.google.android.gms.internal;

import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzji
public final class zzhm<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {
    private final zzhb zzbxj;

    /* renamed from: com.google.android.gms.internal.zzhm.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ zzhm zzbxp;
        final /* synthetic */ ErrorCode zzbxq;

        AnonymousClass10(zzhm com_google_android_gms_internal_zzhm, ErrorCode errorCode) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
            this.zzbxq = errorCode;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdFailedToLoad(zzhn.zza(this.zzbxq));
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.1 */
    class C13641 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13641(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdClicked();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClicked.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.2 */
    class C13652 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13652(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdOpened();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.3 */
    class C13663 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13663(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdLoaded();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.4 */
    class C13674 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13674(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdClosed();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClosed.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.5 */
    class C13685 implements Runnable {
        final /* synthetic */ zzhm zzbxp;
        final /* synthetic */ ErrorCode zzbxq;

        C13685(zzhm com_google_android_gms_internal_zzhm, ErrorCode errorCode) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
            this.zzbxq = errorCode;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdFailedToLoad(zzhn.zza(this.zzbxq));
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.6 */
    class C13696 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13696(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdLeftApplication();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLeftApplication.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.7 */
    class C13707 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13707(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdOpened();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.8 */
    class C13718 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13718(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdLoaded();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzhm.9 */
    class C13729 implements Runnable {
        final /* synthetic */ zzhm zzbxp;

        C13729(zzhm com_google_android_gms_internal_zzhm) {
            this.zzbxp = com_google_android_gms_internal_zzhm;
        }

        public void run() {
            try {
                this.zzbxp.zzbxj.onAdClosed();
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClosed.", e);
            }
        }
    }

    public zzhm(zzhb com_google_android_gms_internal_zzhb) {
        this.zzbxj = com_google_android_gms_internal_zzhb;
    }

    public void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzdg("Adapter called onClick.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdClicked();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClicked.", e);
                return;
            }
        }
        zzb.zzdi("onClick must be called on the main UI thread.");
        zza.zzcxr.post(new C13641(this));
    }

    public void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzdg("Adapter called onDismissScreen.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdClosed();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClosed.", e);
                return;
            }
        }
        zzb.zzdi("onDismissScreen must be called on the main UI thread.");
        zza.zzcxr.post(new C13674(this));
    }

    public void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzdg("Adapter called onDismissScreen.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdClosed();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdClosed.", e);
                return;
            }
        }
        zzb.zzdi("onDismissScreen must be called on the main UI thread.");
        zza.zzcxr.post(new C13729(this));
    }

    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, ErrorCode errorCode) {
        String valueOf = String.valueOf(errorCode);
        zzb.zzdg(new StringBuilder(String.valueOf(valueOf).length() + 47).append("Adapter called onFailedToReceiveAd with error. ").append(valueOf).toString());
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdFailedToLoad(zzhn.zza(errorCode));
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        zzb.zzdi("onFailedToReceiveAd must be called on the main UI thread.");
        zza.zzcxr.post(new C13685(this, errorCode));
    }

    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, ErrorCode errorCode) {
        String valueOf = String.valueOf(errorCode);
        zzb.zzdg(new StringBuilder(String.valueOf(valueOf).length() + 47).append("Adapter called onFailedToReceiveAd with error ").append(valueOf).append(".").toString());
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdFailedToLoad(zzhn.zza(errorCode));
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        zzb.zzdi("onFailedToReceiveAd must be called on the main UI thread.");
        zza.zzcxr.post(new AnonymousClass10(this, errorCode));
    }

    public void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzdg("Adapter called onLeaveApplication.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        zzb.zzdi("onLeaveApplication must be called on the main UI thread.");
        zza.zzcxr.post(new C13696(this));
    }

    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzdg("Adapter called onLeaveApplication.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        zzb.zzdi("onLeaveApplication must be called on the main UI thread.");
        zza.zzcxr.post(new Runnable() {
            final /* synthetic */ zzhm zzbxp;

            {
                this.zzbxp = r1;
            }

            public void run() {
                try {
                    this.zzbxp.zzbxj.onAdLeftApplication();
                } catch (Throwable e) {
                    zzb.zzc("Could not call onAdLeftApplication.", e);
                }
            }
        });
    }

    public void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzdg("Adapter called onPresentScreen.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdOpened();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdOpened.", e);
                return;
            }
        }
        zzb.zzdi("onPresentScreen must be called on the main UI thread.");
        zza.zzcxr.post(new C13707(this));
    }

    public void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzdg("Adapter called onPresentScreen.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdOpened();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdOpened.", e);
                return;
            }
        }
        zzb.zzdi("onPresentScreen must be called on the main UI thread.");
        zza.zzcxr.post(new C13652(this));
    }

    public void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzdg("Adapter called onReceivedAd.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdLoaded();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLoaded.", e);
                return;
            }
        }
        zzb.zzdi("onReceivedAd must be called on the main UI thread.");
        zza.zzcxr.post(new C13718(this));
    }

    public void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzdg("Adapter called onReceivedAd.");
        if (zzm.zzkr().zzwq()) {
            try {
                this.zzbxj.onAdLoaded();
                return;
            } catch (Throwable e) {
                zzb.zzc("Could not call onAdLoaded.", e);
                return;
            }
        }
        zzb.zzdi("onReceivedAd must be called on the main UI thread.");
        zza.zzcxr.post(new C13663(this));
    }
}
