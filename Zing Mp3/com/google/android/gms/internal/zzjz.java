package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.reward.client.zza.zza;
import com.google.android.gms.common.internal.zzz;

@zzji
public class zzjz extends zza {
    private final String zzcpo;
    private final int zzcqw;

    public zzjz(String str, int i) {
        this.zzcpo = str;
        this.zzcqw = i;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof zzjz)) {
            return false;
        }
        zzjz com_google_android_gms_internal_zzjz = (zzjz) obj;
        return zzz.equal(getType(), com_google_android_gms_internal_zzjz.getType()) && zzz.equal(Integer.valueOf(getAmount()), Integer.valueOf(com_google_android_gms_internal_zzjz.getAmount()));
    }

    public int getAmount() {
        return this.zzcqw;
    }

    public String getType() {
        return this.zzcpo;
    }
}
