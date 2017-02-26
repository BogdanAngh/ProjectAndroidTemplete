package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaf.zza;
import java.util.concurrent.Callable;

public class zzbn implements Callable {
    private final zzbc zzagd;
    private final zza zzajb;

    public zzbn(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza) {
        this.zzagd = com_google_android_gms_internal_zzbc;
        this.zzajb = com_google_android_gms_internal_zzaf_zza;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzdo();
    }

    public Void zzdo() throws Exception {
        if (this.zzagd.zzcw() != null) {
            this.zzagd.zzcw().get();
        }
        zzasa zzcv = this.zzagd.zzcv();
        if (zzcv != null) {
            try {
                synchronized (this.zzajb) {
                    zzasa.zza(this.zzajb, zzasa.zzf(zzcv));
                }
            } catch (zzarz e) {
            }
        }
        return null;
    }
}
