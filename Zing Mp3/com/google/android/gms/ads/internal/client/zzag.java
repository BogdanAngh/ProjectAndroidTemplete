package com.google.android.gms.ads.internal.client;

import android.content.Context;
import com.google.android.gms.ads.internal.reward.client.zzi;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzgy;
import com.google.android.gms.internal.zzji;

@zzji
public class zzag {
    private static final Object zzaox;
    private static zzag zzbbp;
    private zzz zzbbq;
    private RewardedVideoAd zzbbr;

    static {
        zzaox = new Object();
    }

    private zzag() {
    }

    public static zzag zzli() {
        zzag com_google_android_gms_ads_internal_client_zzag;
        synchronized (zzaox) {
            if (zzbbp == null) {
                zzbbp = new zzag();
            }
            com_google_android_gms_ads_internal_client_zzag = zzbbp;
        }
        return com_google_android_gms_ads_internal_client_zzag;
    }

    public RewardedVideoAd getRewardedVideoAdInstance(Context context) {
        RewardedVideoAd rewardedVideoAd;
        synchronized (zzaox) {
            if (this.zzbbr != null) {
                rewardedVideoAd = this.zzbbr;
            } else {
                this.zzbbr = new zzi(context, zzm.zzks().zza(context, new zzgy()));
                rewardedVideoAd = this.zzbbr;
            }
        }
        return rewardedVideoAd;
    }

    public void openDebugMenu(Context context, String str) {
        zzaa.zza(this.zzbbq != null, (Object) "MobileAds.initialize() must be called prior to opening debug menu.");
        try {
            this.zzbbq.zzb(zze.zzac(context), str);
        } catch (Throwable e) {
            zzb.zzb("Unable to open debug menu.", e);
        }
    }

    public void setAppMuted(boolean z) {
        zzaa.zza(this.zzbbq != null, (Object) "MobileAds.initialize() must be called prior to setting the app volume.");
        try {
            this.zzbbq.setAppMuted(z);
        } catch (Throwable e) {
            zzb.zzb("Unable to set app mute state.", e);
        }
    }

    public void setAppVolume(float f) {
        boolean z = true;
        boolean z2 = 0.0f <= f && f <= 1.0f;
        zzaa.zzb(z2, (Object) "The app volume must be a value between 0 and 1 inclusive.");
        if (this.zzbbq == null) {
            z = false;
        }
        zzaa.zza(z, (Object) "MobileAds.initialize() must be called prior to setting the app volume.");
        try {
            this.zzbbq.setAppVolume(f);
        } catch (Throwable e) {
            zzb.zzb("Unable to set app volume.", e);
        }
    }

    public void zza(Context context, String str, zzah com_google_android_gms_ads_internal_client_zzah) {
        synchronized (zzaox) {
            if (this.zzbbq != null) {
            } else if (context == null) {
                throw new IllegalArgumentException("Context cannot be null.");
            } else {
                try {
                    this.zzbbq = zzm.zzks().zzk(context);
                    this.zzbbq.initialize();
                    if (str != null) {
                        this.zzbbq.zzz(str);
                    }
                } catch (Throwable e) {
                    zzb.zzc("Fail to initialize or set applicationCode on mobile ads setting manager", e);
                }
            }
        }
    }
}
