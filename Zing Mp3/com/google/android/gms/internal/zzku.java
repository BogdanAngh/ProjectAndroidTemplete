package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzku {
    private final Object zzako;
    private final zzkr zzaqj;
    private final String zzcsx;
    private int zzcuo;
    private int zzcup;

    zzku(zzkr com_google_android_gms_internal_zzkr, String str) {
        this.zzako = new Object();
        this.zzaqj = com_google_android_gms_internal_zzkr;
        this.zzcsx = str;
    }

    public zzku(String str) {
        this(zzu.zzgq(), str);
    }

    public Bundle toBundle() {
        Bundle bundle;
        synchronized (this.zzako) {
            bundle = new Bundle();
            bundle.putInt("pmnli", this.zzcuo);
            bundle.putInt("pmnll", this.zzcup);
        }
        return bundle;
    }

    public void zzj(int i, int i2) {
        synchronized (this.zzako) {
            this.zzcuo = i;
            this.zzcup = i2;
            this.zzaqj.zza(this.zzcsx, this);
        }
    }
}
