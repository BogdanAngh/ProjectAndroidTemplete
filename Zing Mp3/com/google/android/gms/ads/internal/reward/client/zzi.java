package com.google.android.gms.ads.internal.reward.client;

import android.content.Context;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.internal.client.zzh;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzji;

@zzji
public class zzi implements RewardedVideoAd {
    private final Context mContext;
    private final Object zzako;
    private final zzb zzcrd;
    private RewardedVideoAdListener zzgj;

    public zzi(Context context, zzb com_google_android_gms_ads_internal_reward_client_zzb) {
        this.zzako = new Object();
        this.zzcrd = com_google_android_gms_ads_internal_reward_client_zzb;
        this.mContext = context;
    }

    public void destroy() {
        destroy(null);
    }

    public void destroy(Context context) {
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
                return;
            }
            try {
                this.zzcrd.zzh(zze.zzac(context));
            } catch (Throwable e) {
                zzb.zzc("Could not forward destroy to RewardedVideoAd", e);
            }
        }
    }

    public RewardedVideoAdListener getRewardedVideoAdListener() {
        RewardedVideoAdListener rewardedVideoAdListener;
        synchronized (this.zzako) {
            rewardedVideoAdListener = this.zzgj;
        }
        return rewardedVideoAdListener;
    }

    public String getUserId() {
        zzb.zzdi("RewardedVideoAd.getUserId() is deprecated. Please do not call this method.");
        return null;
    }

    public boolean isLoaded() {
        boolean z = false;
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
            } else {
                try {
                    z = this.zzcrd.isLoaded();
                } catch (Throwable e) {
                    zzb.zzc("Could not forward isLoaded to RewardedVideoAd", e);
                }
            }
        }
        return z;
    }

    public void loadAd(String str, AdRequest adRequest) {
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
                return;
            }
            try {
                this.zzcrd.zza(zzh.zzkb().zza(this.mContext, adRequest.zzdt(), str));
            } catch (Throwable e) {
                zzb.zzc("Could not forward loadAd to RewardedVideoAd", e);
            }
        }
    }

    public void pause() {
        pause(null);
    }

    public void pause(Context context) {
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
                return;
            }
            try {
                this.zzcrd.zzf(zze.zzac(context));
            } catch (Throwable e) {
                zzb.zzc("Could not forward pause to RewardedVideoAd", e);
            }
        }
    }

    public void resume() {
        resume(null);
    }

    public void resume(Context context) {
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
                return;
            }
            try {
                this.zzcrd.zzg(zze.zzac(context));
            } catch (Throwable e) {
                zzb.zzc("Could not forward resume to RewardedVideoAd", e);
            }
        }
    }

    public void setRewardedVideoAdListener(RewardedVideoAdListener rewardedVideoAdListener) {
        synchronized (this.zzako) {
            this.zzgj = rewardedVideoAdListener;
            if (this.zzcrd != null) {
                try {
                    this.zzcrd.zza(new zzg(rewardedVideoAdListener));
                } catch (Throwable e) {
                    zzb.zzc("Could not forward setRewardedVideoAdListener to RewardedVideoAd", e);
                }
            }
        }
    }

    public void setUserId(String str) {
        zzb.zzdi("RewardedVideoAd.setUserId() is deprecated. Please do not call this method.");
    }

    public void show() {
        synchronized (this.zzako) {
            if (this.zzcrd == null) {
                return;
            }
            try {
                this.zzcrd.show();
            } catch (Throwable e) {
                zzb.zzc("Could not forward show to RewardedVideoAd", e);
            }
        }
    }
}
