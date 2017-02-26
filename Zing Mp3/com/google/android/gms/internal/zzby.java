package com.google.android.gms.internal;

import com.google.android.exoplayer.text.Cue;
import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

public abstract class zzby implements Callable {
    protected final String TAG;
    protected final String className;
    protected final zzbc zzagd;
    protected final zza zzajb;
    protected final String zzaji;
    protected Method zzajk;
    protected final int zzajo;
    protected final int zzajp;

    public zzby(zzbc com_google_android_gms_internal_zzbc, String str, String str2, zza com_google_android_gms_internal_zzaf_zza, int i, int i2) {
        this.TAG = getClass().getSimpleName();
        this.zzagd = com_google_android_gms_internal_zzbc;
        this.className = str;
        this.zzaji = str2;
        this.zzajb = com_google_android_gms_internal_zzaf_zza;
        this.zzajo = i;
        this.zzajp = i2;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzdo();
    }

    protected abstract void zzdh() throws IllegalAccessException, InvocationTargetException;

    public Void zzdo() throws Exception {
        try {
            long nanoTime = System.nanoTime();
            this.zzajk = this.zzagd.zzc(this.className, this.zzaji);
            if (this.zzajk != null) {
                zzdh();
                zzap zzct = this.zzagd.zzct();
                if (!(zzct == null || this.zzajo == Cue.TYPE_UNSET)) {
                    zzct.zza(this.zzajp, this.zzajo, (System.nanoTime() - nanoTime) / 1000);
                }
            }
        } catch (IllegalAccessException e) {
        } catch (InvocationTargetException e2) {
        }
        return null;
    }
}
