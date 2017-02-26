package com.google.android.gms.internal;

import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.google.ads.mediation.MediationServerParameters;
import com.google.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.internal.client.zzk;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzgd
public final class zzem<NETWORK_EXTRAS extends NetworkExtras, SERVER_PARAMETERS extends MediationServerParameters> implements MediationBannerListener, MediationInterstitialListener {
    private final zzeh zzyg;

    /* renamed from: com.google.android.gms.internal.zzem.10 */
    class AnonymousClass10 implements Runnable {
        final /* synthetic */ zzem zzyj;
        final /* synthetic */ ErrorCode zzyk;

        AnonymousClass10(zzem com_google_android_gms_internal_zzem, ErrorCode errorCode) {
            this.zzyj = com_google_android_gms_internal_zzem;
            this.zzyk = errorCode;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdFailedToLoad(zzen.zza(this.zzyk));
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.1 */
    class C02111 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02111(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdClicked();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClicked.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.2 */
    class C02122 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02122(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdOpened();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.3 */
    class C02133 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02133(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdLoaded();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.4 */
    class C02144 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02144(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdClosed();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClosed.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.5 */
    class C02155 implements Runnable {
        final /* synthetic */ zzem zzyj;
        final /* synthetic */ ErrorCode zzyk;

        C02155(zzem com_google_android_gms_internal_zzem, ErrorCode errorCode) {
            this.zzyj = com_google_android_gms_internal_zzem;
            this.zzyk = errorCode;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdFailedToLoad(zzen.zza(this.zzyk));
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdFailedToLoad.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.6 */
    class C02166 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02166(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdLeftApplication();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLeftApplication.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.7 */
    class C02177 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02177(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdOpened();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdOpened.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.8 */
    class C02188 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02188(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdLoaded();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLoaded.", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzem.9 */
    class C02199 implements Runnable {
        final /* synthetic */ zzem zzyj;

        C02199(zzem com_google_android_gms_internal_zzem) {
            this.zzyj = com_google_android_gms_internal_zzem;
        }

        public void run() {
            try {
                this.zzyj.zzyg.onAdClosed();
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClosed.", e);
            }
        }
    }

    public zzem(zzeh com_google_android_gms_internal_zzeh) {
        this.zzyg = com_google_android_gms_internal_zzeh;
    }

    public void onClick(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzay("Adapter called onClick.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdClicked();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClicked.", e);
                return;
            }
        }
        zzb.zzaC("onClick must be called on the main UI thread.");
        zza.zzGF.post(new C02111(this));
    }

    public void onDismissScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzay("Adapter called onDismissScreen.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdClosed();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClosed.", e);
                return;
            }
        }
        zzb.zzaC("onDismissScreen must be called on the main UI thread.");
        zza.zzGF.post(new C02144(this));
    }

    public void onDismissScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzay("Adapter called onDismissScreen.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdClosed();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdClosed.", e);
                return;
            }
        }
        zzb.zzaC("onDismissScreen must be called on the main UI thread.");
        zza.zzGF.post(new C02199(this));
    }

    public void onFailedToReceiveAd(MediationBannerAdapter<?, ?> mediationBannerAdapter, ErrorCode errorCode) {
        zzb.zzay("Adapter called onFailedToReceiveAd with error. " + errorCode);
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdFailedToLoad(zzen.zza(errorCode));
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        zzb.zzaC("onFailedToReceiveAd must be called on the main UI thread.");
        zza.zzGF.post(new C02155(this, errorCode));
    }

    public void onFailedToReceiveAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter, ErrorCode errorCode) {
        zzb.zzay("Adapter called onFailedToReceiveAd with error " + errorCode + ".");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdFailedToLoad(zzen.zza(errorCode));
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdFailedToLoad.", e);
                return;
            }
        }
        zzb.zzaC("onFailedToReceiveAd must be called on the main UI thread.");
        zza.zzGF.post(new AnonymousClass10(this, errorCode));
    }

    public void onLeaveApplication(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzay("Adapter called onLeaveApplication.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        zzb.zzaC("onLeaveApplication must be called on the main UI thread.");
        zza.zzGF.post(new C02166(this));
    }

    public void onLeaveApplication(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzay("Adapter called onLeaveApplication.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdLeftApplication();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLeftApplication.", e);
                return;
            }
        }
        zzb.zzaC("onLeaveApplication must be called on the main UI thread.");
        zza.zzGF.post(new Runnable() {
            final /* synthetic */ zzem zzyj;

            {
                this.zzyj = r1;
            }

            public void run() {
                try {
                    this.zzyj.zzyg.onAdLeftApplication();
                } catch (Throwable e) {
                    zzb.zzd("Could not call onAdLeftApplication.", e);
                }
            }
        });
    }

    public void onPresentScreen(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzay("Adapter called onPresentScreen.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdOpened();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdOpened.", e);
                return;
            }
        }
        zzb.zzaC("onPresentScreen must be called on the main UI thread.");
        zza.zzGF.post(new C02177(this));
    }

    public void onPresentScreen(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzay("Adapter called onPresentScreen.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdOpened();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdOpened.", e);
                return;
            }
        }
        zzb.zzaC("onPresentScreen must be called on the main UI thread.");
        zza.zzGF.post(new C02122(this));
    }

    public void onReceivedAd(MediationBannerAdapter<?, ?> mediationBannerAdapter) {
        zzb.zzay("Adapter called onReceivedAd.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdLoaded();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLoaded.", e);
                return;
            }
        }
        zzb.zzaC("onReceivedAd must be called on the main UI thread.");
        zza.zzGF.post(new C02188(this));
    }

    public void onReceivedAd(MediationInterstitialAdapter<?, ?> mediationInterstitialAdapter) {
        zzb.zzay("Adapter called onReceivedAd.");
        if (zzk.zzcA().zzgw()) {
            try {
                this.zzyg.onAdLoaded();
                return;
            } catch (Throwable e) {
                zzb.zzd("Could not call onAdLoaded.", e);
                return;
            }
        }
        zzb.zzaC("onReceivedAd must be called on the main UI thread.");
        zza.zzGF.post(new C02133(this));
    }
}
