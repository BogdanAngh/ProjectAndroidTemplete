package com.google.android.gms.ads.internal.reward.client;

import com.google.android.gms.ads.internal.reward.client.zzd.zza;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.internal.zzji;

@zzji
public class zzg extends zza {
    private final RewardedVideoAdListener zzgj;

    public zzg(RewardedVideoAdListener rewardedVideoAdListener) {
        this.zzgj = rewardedVideoAdListener;
    }

    public void onRewardedVideoAdClosed() {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoAdClosed();
        }
    }

    public void onRewardedVideoAdFailedToLoad(int i) {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoAdFailedToLoad(i);
        }
    }

    public void onRewardedVideoAdLeftApplication() {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoAdLeftApplication();
        }
    }

    public void onRewardedVideoAdLoaded() {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoAdLoaded();
        }
    }

    public void onRewardedVideoAdOpened() {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoAdOpened();
        }
    }

    public void onRewardedVideoStarted() {
        if (this.zzgj != null) {
            this.zzgj.onRewardedVideoStarted();
        }
    }

    public void zza(zza com_google_android_gms_ads_internal_reward_client_zza) {
        if (this.zzgj != null) {
            this.zzgj.onRewarded(new zze(com_google_android_gms_ads_internal_reward_client_zza));
        }
    }
}
