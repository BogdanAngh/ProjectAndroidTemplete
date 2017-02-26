package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zza;
import java.util.ArrayList;
import java.util.List;

class zzhw {
    private final Object zzGQ;
    private final List<Runnable> zzGR;
    private final List<Runnable> zzGS;
    private boolean zzGT;

    public zzhw() {
        this.zzGQ = new Object();
        this.zzGR = new ArrayList();
        this.zzGS = new ArrayList();
        this.zzGT = false;
    }

    private void zzc(Runnable runnable) {
        zzhk.zza(runnable);
    }

    private void zzd(Runnable runnable) {
        zza.zzGF.post(runnable);
    }

    public void zzb(Runnable runnable) {
        synchronized (this.zzGQ) {
            if (this.zzGT) {
                zzc(runnable);
            } else {
                this.zzGR.add(runnable);
            }
        }
    }

    public void zzgy() {
        synchronized (this.zzGQ) {
            if (this.zzGT) {
                return;
            }
            for (Runnable zzc : this.zzGR) {
                zzc(zzc);
            }
            for (Runnable zzc2 : this.zzGS) {
                zzd(zzc2);
            }
            this.zzGR.clear();
            this.zzGS.clear();
            this.zzGT = true;
        }
    }
}
