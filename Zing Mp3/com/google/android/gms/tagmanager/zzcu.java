package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzai.zzj;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class zzcu implements zze {
    private final String aDY;
    private String aEw;
    private zzbn<zzj> aGB;
    private zzt aGC;
    private final ScheduledExecutorService aGE;
    private final zza aGF;
    private ScheduledFuture<?> aGG;
    private boolean mClosed;
    private final Context mContext;

    interface zzb {
        ScheduledExecutorService zzcgg();
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.1 */
    class C15341 implements zzb {
        final /* synthetic */ zzcu aGH;

        C15341(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.aGH = com_google_android_gms_tagmanager_zzcu;
        }

        public ScheduledExecutorService zzcgg() {
            return Executors.newSingleThreadScheduledExecutor();
        }
    }

    interface zza {
        zzct zza(zzt com_google_android_gms_tagmanager_zzt);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.2 */
    class C15352 implements zza {
        final /* synthetic */ zzcu aGH;

        C15352(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.aGH = com_google_android_gms_tagmanager_zzcu;
        }

        public zzct zza(zzt com_google_android_gms_tagmanager_zzt) {
            return new zzct(this.aGH.mContext, this.aGH.aDY, com_google_android_gms_tagmanager_zzt);
        }
    }

    public zzcu(Context context, String str, zzt com_google_android_gms_tagmanager_zzt) {
        this(context, str, com_google_android_gms_tagmanager_zzt, null, null);
    }

    zzcu(Context context, String str, zzt com_google_android_gms_tagmanager_zzt, zzb com_google_android_gms_tagmanager_zzcu_zzb, zza com_google_android_gms_tagmanager_zzcu_zza) {
        this.aGC = com_google_android_gms_tagmanager_zzt;
        this.mContext = context;
        this.aDY = str;
        if (com_google_android_gms_tagmanager_zzcu_zzb == null) {
            com_google_android_gms_tagmanager_zzcu_zzb = new C15341(this);
        }
        this.aGE = com_google_android_gms_tagmanager_zzcu_zzb.zzcgg();
        if (com_google_android_gms_tagmanager_zzcu_zza == null) {
            this.aGF = new C15352(this);
        } else {
            this.aGF = com_google_android_gms_tagmanager_zzcu_zza;
        }
    }

    private synchronized void zzcgf() {
        if (this.mClosed) {
            throw new IllegalStateException("called method after closed");
        }
    }

    private zzct zzpq(String str) {
        zzct zza = this.aGF.zza(this.aGC);
        zza.zza(this.aGB);
        zza.zzpa(this.aEw);
        zza.zzpp(str);
        return zza;
    }

    public synchronized void release() {
        zzcgf();
        if (this.aGG != null) {
            this.aGG.cancel(false);
        }
        this.aGE.shutdown();
        this.mClosed = true;
    }

    public synchronized void zza(zzbn<zzj> com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzai_zzj) {
        zzcgf();
        this.aGB = com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzai_zzj;
    }

    public synchronized void zzf(long j, String str) {
        String str2 = this.aDY;
        zzbo.m1699v(new StringBuilder(String.valueOf(str2).length() + 55).append("loadAfterDelay: containerId=").append(str2).append(" delay=").append(j).toString());
        zzcgf();
        if (this.aGB == null) {
            throw new IllegalStateException("callback must be set before loadAfterDelay() is called.");
        }
        if (this.aGG != null) {
            this.aGG.cancel(false);
        }
        this.aGG = this.aGE.schedule(zzpq(str), j, TimeUnit.MILLISECONDS);
    }

    public synchronized void zzpa(String str) {
        zzcgf();
        this.aEw = str;
    }
}
