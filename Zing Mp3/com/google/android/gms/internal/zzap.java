package com.google.android.gms.internal;

import android.os.Build.VERSION;
import android.os.ConditionVariable;
import com.google.android.gms.clearcut.zza;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadLocalRandom;

public class zzap {
    private static final ConditionVariable zzage;
    protected static volatile zza zzagf;
    private static volatile Random zzagh;
    private zzbc zzagd;
    protected Boolean zzagg;

    /* renamed from: com.google.android.gms.internal.zzap.1 */
    class C12121 implements Runnable {
        final /* synthetic */ zzap zzagi;

        C12121(zzap com_google_android_gms_internal_zzap) {
            this.zzagi = com_google_android_gms_internal_zzap;
        }

        public void run() {
            if (this.zzagi.zzagg == null) {
                synchronized (zzap.zzage) {
                    if (this.zzagi.zzagg != null) {
                        return;
                    }
                    boolean booleanValue = ((Boolean) zzdr.zzbhl.get()).booleanValue();
                    if (booleanValue) {
                        zzap.zzagf = new zza(this.zzagi.zzagd.getContext(), "ADSHIELD", null);
                    }
                    this.zzagi.zzagg = Boolean.valueOf(booleanValue);
                    zzap.zzage.open();
                }
            }
        }
    }

    static {
        zzage = new ConditionVariable();
        zzagf = null;
        zzagh = null;
    }

    public zzap(zzbc com_google_android_gms_internal_zzbc) {
        this.zzagd = com_google_android_gms_internal_zzbc;
        zza(com_google_android_gms_internal_zzbc.zzcm());
    }

    private void zza(Executor executor) {
        executor.execute(new C12121(this));
    }

    private static Random zzax() {
        if (zzagh == null) {
            synchronized (zzap.class) {
                if (zzagh == null) {
                    zzagh = new Random();
                }
            }
        }
        return zzagh;
    }

    public void zza(int i, int i2, long j) throws IOException {
        try {
            zzage.block();
            if (this.zzagg.booleanValue() && zzagf != null && this.zzagd.zzcs()) {
                zzasa com_google_android_gms_internal_zzae_zza = new zzae.zza();
                com_google_android_gms_internal_zzae_zza.zzcs = this.zzagd.getContext().getPackageName();
                com_google_android_gms_internal_zzae_zza.zzct = Long.valueOf(j);
                zza.zza zzm = zzagf.zzm(zzasa.zzf(com_google_android_gms_internal_zzae_zza));
                zzm.zzfm(i2);
                zzm.zzfl(i);
                zzm.zze(this.zzagd.zzcq());
            }
        } catch (Exception e) {
        }
    }

    public int zzaw() {
        try {
            return VERSION.SDK_INT >= 21 ? ThreadLocalRandom.current().nextInt() : zzax().nextInt();
        } catch (RuntimeException e) {
            return zzax().nextInt();
        }
    }
}
