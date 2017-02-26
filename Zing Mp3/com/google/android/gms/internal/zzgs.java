package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.internal.zzhb.zza;

@zzji
public final class zzgs extends zza {
    private final Object zzako;
    private zzgu.zza zzbvy;
    private zzgr zzbvz;

    public zzgs() {
        this.zzako = new Object();
    }

    public void onAdClicked() {
        synchronized (this.zzako) {
            if (this.zzbvz != null) {
                this.zzbvz.zzes();
            }
        }
    }

    public void onAdClosed() {
        synchronized (this.zzako) {
            if (this.zzbvz != null) {
                this.zzbvz.zzet();
            }
        }
    }

    public void onAdFailedToLoad(int i) {
        synchronized (this.zzako) {
            if (this.zzbvy != null) {
                this.zzbvy.zzad(i == 3 ? 1 : 2);
                this.zzbvy = null;
            }
        }
    }

    public void onAdImpression() {
        synchronized (this.zzako) {
            if (this.zzbvz != null) {
                this.zzbvz.zzex();
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.zzako) {
            if (this.zzbvz != null) {
                this.zzbvz.zzeu();
            }
        }
    }

    public void onAdLoaded() {
        synchronized (this.zzako) {
            if (this.zzbvy != null) {
                this.zzbvy.zzad(0);
                this.zzbvy = null;
                return;
            }
            if (this.zzbvz != null) {
                this.zzbvz.zzew();
            }
        }
    }

    public void onAdOpened() {
        synchronized (this.zzako) {
            if (this.zzbvz != null) {
                this.zzbvz.zzev();
            }
        }
    }

    public void zza(@Nullable zzgr com_google_android_gms_internal_zzgr) {
        synchronized (this.zzako) {
            this.zzbvz = com_google_android_gms_internal_zzgr;
        }
    }

    public void zza(zzgu.zza com_google_android_gms_internal_zzgu_zza) {
        synchronized (this.zzako) {
            this.zzbvy = com_google_android_gms_internal_zzgu_zza;
        }
    }

    public void zza(zzhc com_google_android_gms_internal_zzhc) {
        synchronized (this.zzako) {
            if (this.zzbvy != null) {
                this.zzbvy.zza(0, com_google_android_gms_internal_zzhc);
                this.zzbvy = null;
                return;
            }
            if (this.zzbvz != null) {
                this.zzbvz.zzew();
            }
        }
    }
}
