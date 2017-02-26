package com.google.android.gms.ads.internal.reward.client;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.internal.zzji;

@zzji
public class zze implements RewardItem {
    private final zza zzcrc;

    public zze(zza com_google_android_gms_ads_internal_reward_client_zza) {
        this.zzcrc = com_google_android_gms_ads_internal_reward_client_zza;
    }

    public int getAmount() {
        int i = 0;
        if (this.zzcrc != null) {
            try {
                i = this.zzcrc.getAmount();
            } catch (Throwable e) {
                zzb.zzc("Could not forward getAmount to RewardItem", e);
            }
        }
        return i;
    }

    public String getType() {
        String str = null;
        if (this.zzcrc != null) {
            try {
                str = this.zzcrc.getType();
            } catch (Throwable e) {
                zzb.zzc("Could not forward getType to RewardItem", e);
            }
        }
        return str;
    }
}
