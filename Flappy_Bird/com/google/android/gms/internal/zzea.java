package com.google.android.gms.internal;

import com.google.android.gms.internal.zzeh.zza;

@zzgd
public final class zzea extends zza {
    private final Object zzqt;
    private zzec.zza zzxO;
    private zzdz zzxP;

    public zzea() {
        this.zzqt = new Object();
    }

    public void onAdClicked() {
        synchronized (this.zzqt) {
            if (this.zzxP != null) {
                this.zzxP.zzaX();
            }
        }
    }

    public void onAdClosed() {
        synchronized (this.zzqt) {
            if (this.zzxP != null) {
                this.zzxP.zzaY();
            }
        }
    }

    public void onAdFailedToLoad(int error) {
        synchronized (this.zzqt) {
            if (this.zzxO != null) {
                this.zzxO.zzs(error == 3 ? 1 : 2);
                this.zzxO = null;
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.zzqt) {
            if (this.zzxP != null) {
                this.zzxP.zzaZ();
            }
        }
    }

    public void onAdLoaded() {
        synchronized (this.zzqt) {
            if (this.zzxO != null) {
                this.zzxO.zzs(0);
                this.zzxO = null;
                return;
            }
            if (this.zzxP != null) {
                this.zzxP.zzbb();
            }
        }
    }

    public void onAdOpened() {
        synchronized (this.zzqt) {
            if (this.zzxP != null) {
                this.zzxP.zzba();
            }
        }
    }

    public void zza(zzdz com_google_android_gms_internal_zzdz) {
        synchronized (this.zzqt) {
            this.zzxP = com_google_android_gms_internal_zzdz;
        }
    }

    public void zza(zzec.zza com_google_android_gms_internal_zzec_zza) {
        synchronized (this.zzqt) {
            this.zzxO = com_google_android_gms_internal_zzec_zza;
        }
    }
}
