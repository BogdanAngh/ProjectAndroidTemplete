package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zza;
import java.util.ArrayList;
import java.util.List;

@zzji
class zzlu {
    private final Object zzcyo;
    private final List<Runnable> zzcyp;
    private final List<Runnable> zzcyq;
    private boolean zzcyr;

    public zzlu() {
        this.zzcyo = new Object();
        this.zzcyp = new ArrayList();
        this.zzcyq = new ArrayList();
        this.zzcyr = false;
    }

    private void zze(Runnable runnable) {
        zzla.zza(runnable);
    }

    private void zzf(Runnable runnable) {
        zza.zzcxr.post(runnable);
    }

    public void zzc(Runnable runnable) {
        synchronized (this.zzcyo) {
            if (this.zzcyr) {
                zze(runnable);
            } else {
                this.zzcyp.add(runnable);
            }
        }
    }

    public void zzd(Runnable runnable) {
        synchronized (this.zzcyo) {
            if (this.zzcyr) {
                zzf(runnable);
            } else {
                this.zzcyq.add(runnable);
            }
        }
    }

    public void zzwt() {
        synchronized (this.zzcyo) {
            if (this.zzcyr) {
                return;
            }
            for (Runnable zze : this.zzcyp) {
                zze(zze);
            }
            for (Runnable zze2 : this.zzcyq) {
                zzf(zze2);
            }
            this.zzcyp.clear();
            this.zzcyq.clear();
            this.zzcyr = true;
        }
    }
}
