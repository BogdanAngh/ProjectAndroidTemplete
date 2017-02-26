package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzaa;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzb extends zzd {
    private final zzl cI;

    /* renamed from: com.google.android.gms.analytics.internal.zzb.1 */
    class C11501 implements Runnable {
        final /* synthetic */ int cJ;
        final /* synthetic */ zzb cK;

        C11501(zzb com_google_android_gms_analytics_internal_zzb, int i) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
            this.cJ = i;
        }

        public void run() {
            this.cK.cI.zzw(((long) this.cJ) * 1000);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.2 */
    class C11512 implements Runnable {
        final /* synthetic */ zzb cK;
        final /* synthetic */ boolean cL;

        C11512(zzb com_google_android_gms_analytics_internal_zzb, boolean z) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
            this.cL = z;
        }

        public void run() {
            this.cK.cI.zzaw(this.cL);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.3 */
    class C11523 implements Runnable {
        final /* synthetic */ zzb cK;
        final /* synthetic */ String cM;
        final /* synthetic */ Runnable cN;

        C11523(zzb com_google_android_gms_analytics_internal_zzb, String str, Runnable runnable) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
            this.cM = str;
            this.cN = runnable;
        }

        public void run() {
            this.cK.cI.zzfa(this.cM);
            if (this.cN != null) {
                this.cN.run();
            }
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.4 */
    class C11534 implements Runnable {
        final /* synthetic */ zzb cK;
        final /* synthetic */ zzab cO;

        C11534(zzb com_google_android_gms_analytics_internal_zzb, zzab com_google_android_gms_analytics_internal_zzab) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
            this.cO = com_google_android_gms_analytics_internal_zzab;
        }

        public void run() {
            this.cK.cI.zza(this.cO);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.5 */
    class C11545 implements Runnable {
        final /* synthetic */ zzb cK;

        C11545(zzb com_google_android_gms_analytics_internal_zzb) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
        }

        public void run() {
            this.cK.cI.zzabr();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.6 */
    class C11556 implements Runnable {
        final /* synthetic */ zzb cK;
        final /* synthetic */ zzw cP;

        C11556(zzb com_google_android_gms_analytics_internal_zzb, zzw com_google_android_gms_analytics_internal_zzw) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
            this.cP = com_google_android_gms_analytics_internal_zzw;
        }

        public void run() {
            this.cK.cI.zzb(this.cP);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.7 */
    class C11567 implements Callable<Void> {
        final /* synthetic */ zzb cK;

        C11567(zzb com_google_android_gms_analytics_internal_zzb) {
            this.cK = com_google_android_gms_analytics_internal_zzb;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzdo();
        }

        public Void zzdo() throws Exception {
            this.cK.cI.zzado();
            return null;
        }
    }

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, zzg com_google_android_gms_analytics_internal_zzg) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzaa.zzy(com_google_android_gms_analytics_internal_zzg);
        this.cI = com_google_android_gms_analytics_internal_zzg.zzj(com_google_android_gms_analytics_internal_zzf);
    }

    void onServiceConnected() {
        zzzx();
        this.cI.onServiceConnected();
    }

    public void setLocalDispatchPeriod(int i) {
        zzacj();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(i));
        zzacc().zzg(new C11501(this, i));
    }

    public void start() {
        this.cI.start();
    }

    public long zza(zzh com_google_android_gms_analytics_internal_zzh) {
        zzacj();
        zzaa.zzy(com_google_android_gms_analytics_internal_zzh);
        zzzx();
        long zza = this.cI.zza(com_google_android_gms_analytics_internal_zzh, true);
        if (zza == 0) {
            this.cI.zzc(com_google_android_gms_analytics_internal_zzh);
        }
        return zza;
    }

    public void zza(zzab com_google_android_gms_analytics_internal_zzab) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
        zzacj();
        zzb("Hit delivery requested", com_google_android_gms_analytics_internal_zzab);
        zzacc().zzg(new C11534(this, com_google_android_gms_analytics_internal_zzab));
    }

    public void zza(zzw com_google_android_gms_analytics_internal_zzw) {
        zzacj();
        zzacc().zzg(new C11556(this, com_google_android_gms_analytics_internal_zzw));
    }

    public void zza(String str, Runnable runnable) {
        zzaa.zzh(str, "campaign param can't be empty");
        zzacc().zzg(new C11523(this, str, runnable));
    }

    public void zzabr() {
        zzacj();
        zzaby();
        zzacc().zzg(new C11545(this));
    }

    public void zzabs() {
        zzacj();
        Context context = getContext();
        if (zzaj.zzat(context) && zzak.zzau(context)) {
            Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
            context.startService(intent);
            return;
        }
        zza(null);
    }

    public boolean zzabt() {
        zzacj();
        try {
            zzacc().zzc(new C11567(this)).get(4, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        } catch (TimeoutException e3) {
            zzd("syncDispatchLocalHits timed out", e3);
            return false;
        }
    }

    public void zzabu() {
        zzacj();
        zzi.zzzx();
        this.cI.zzabu();
    }

    public void zzabv() {
        zzes("Radio powered up");
        zzabs();
    }

    void zzabw() {
        zzzx();
        this.cI.zzabw();
    }

    public void zzaw(boolean z) {
        zza("Network connectivity status changed", Boolean.valueOf(z));
        zzacc().zzg(new C11512(this, z));
    }

    protected void zzzy() {
        this.cI.initialize();
    }
}
