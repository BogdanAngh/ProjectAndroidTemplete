package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;

@zzji
public class zzfn implements zzfe {
    private final zza zzbqy;

    public interface zza {
        void zzb(RewardItemParcel rewardItemParcel);

        void zzfo();
    }

    public zzfn(zza com_google_android_gms_internal_zzfn_zza) {
        this.zzbqy = com_google_android_gms_internal_zzfn_zza;
    }

    public static void zza(zzmd com_google_android_gms_internal_zzmd, zza com_google_android_gms_internal_zzfn_zza) {
        com_google_android_gms_internal_zzmd.zzxc().zza("/reward", new zzfn(com_google_android_gms_internal_zzfn_zza));
    }

    private void zzf(Map<String, String> map) {
        RewardItemParcel rewardItemParcel;
        try {
            int parseInt = Integer.parseInt((String) map.get("amount"));
            String str = (String) map.get(ShareConstants.MEDIA_TYPE);
            if (!TextUtils.isEmpty(str)) {
                rewardItemParcel = new RewardItemParcel(str, parseInt);
                this.zzbqy.zzb(rewardItemParcel);
            }
        } catch (Throwable e) {
            zzb.zzc("Unable to parse reward amount.", e);
        }
        rewardItemParcel = null;
        this.zzbqy.zzb(rewardItemParcel);
    }

    private void zzg(Map<String, String> map) {
        this.zzbqy.zzfo();
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if ("grant".equals(str)) {
            zzf(map);
        } else if ("video_start".equals(str)) {
            zzg(map);
        }
    }
}
