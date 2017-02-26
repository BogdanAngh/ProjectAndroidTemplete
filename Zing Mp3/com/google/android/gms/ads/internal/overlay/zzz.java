package com.google.android.gms.ads.internal.overlay;

import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;

@zzji
class zzz implements Runnable {
    private boolean mCancelled;
    private zzk zzcep;

    zzz(zzk com_google_android_gms_ads_internal_overlay_zzk) {
        this.mCancelled = false;
        this.zzcep = com_google_android_gms_ads_internal_overlay_zzk;
    }

    public void cancel() {
        this.mCancelled = true;
        zzlb.zzcvl.removeCallbacks(this);
    }

    public void run() {
        if (!this.mCancelled) {
            this.zzcep.zzqk();
            zzrg();
        }
    }

    public void zzrg() {
        zzlb.zzcvl.postDelayed(this, 250);
    }
}
