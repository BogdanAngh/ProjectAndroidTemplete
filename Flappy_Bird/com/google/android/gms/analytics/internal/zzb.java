package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.analytics.AnalyticsReceiver;
import com.google.android.gms.analytics.AnalyticsService;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzns;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

public class zzb extends zzd {
    private final zzl zzJq;

    /* renamed from: com.google.android.gms.analytics.internal.zzb.1 */
    class C01081 implements Runnable {
        final /* synthetic */ int zzJr;
        final /* synthetic */ zzb zzJs;

        C01081(zzb com_google_android_gms_analytics_internal_zzb, int i) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
            this.zzJr = i;
        }

        public void run() {
            this.zzJs.zzJq.zzs(((long) this.zzJr) * 1000);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.2 */
    class C01092 implements Runnable {
        final /* synthetic */ zzb zzJs;
        final /* synthetic */ boolean zzJt;

        C01092(zzb com_google_android_gms_analytics_internal_zzb, boolean z) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
            this.zzJt = z;
        }

        public void run() {
            this.zzJs.zzJq.zzG(this.zzJt);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.3 */
    class C01103 implements Runnable {
        final /* synthetic */ zzb zzJs;
        final /* synthetic */ String zzJu;
        final /* synthetic */ Runnable zzJv;

        C01103(zzb com_google_android_gms_analytics_internal_zzb, String str, Runnable runnable) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
            this.zzJu = str;
            this.zzJv = runnable;
        }

        public void run() {
            this.zzJs.zzJq.zzbb(this.zzJu);
            if (this.zzJv != null) {
                this.zzJv.run();
            }
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.4 */
    class C01114 implements Runnable {
        final /* synthetic */ zzb zzJs;
        final /* synthetic */ zzab zzJw;

        C01114(zzb com_google_android_gms_analytics_internal_zzb, zzab com_google_android_gms_analytics_internal_zzab) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
            this.zzJw = com_google_android_gms_analytics_internal_zzab;
        }

        public void run() {
            this.zzJs.zzJq.zza(this.zzJw);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.5 */
    class C01125 implements Runnable {
        final /* synthetic */ zzb zzJs;

        C01125(zzb com_google_android_gms_analytics_internal_zzb) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
        }

        public void run() {
            this.zzJs.zzJq.zzhG();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.6 */
    class C01136 implements Runnable {
        final /* synthetic */ zzb zzJs;
        final /* synthetic */ zzw zzJx;

        C01136(zzb com_google_android_gms_analytics_internal_zzb, zzw com_google_android_gms_analytics_internal_zzw) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
            this.zzJx = com_google_android_gms_analytics_internal_zzw;
        }

        public void run() {
            this.zzJs.zzJq.zzb(this.zzJx);
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzb.7 */
    class C01147 implements Callable<Void> {
        final /* synthetic */ zzb zzJs;

        C01147(zzb com_google_android_gms_analytics_internal_zzb) {
            this.zzJs = com_google_android_gms_analytics_internal_zzb;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzgk();
        }

        public Void zzgk() throws Exception {
            this.zzJs.zzJq.zziF();
            return null;
        }
    }

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, zzg com_google_android_gms_analytics_internal_zzg) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzu.zzu(com_google_android_gms_analytics_internal_zzg);
        this.zzJq = com_google_android_gms_analytics_internal_zzg.zzj(com_google_android_gms_analytics_internal_zzf);
    }

    void onServiceConnected() {
        zzhO();
        this.zzJq.onServiceConnected();
    }

    public void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        zzia();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(dispatchPeriodInSeconds));
        zzhS().zze(new C01081(this, dispatchPeriodInSeconds));
    }

    public void start() {
        this.zzJq.start();
    }

    public void zzG(boolean z) {
        zza("Network connectivity status changed", Boolean.valueOf(z));
        zzhS().zze(new C01092(this, z));
    }

    public long zza(zzh com_google_android_gms_analytics_internal_zzh) {
        zzia();
        zzu.zzu(com_google_android_gms_analytics_internal_zzh);
        zzhO();
        long zza = this.zzJq.zza(com_google_android_gms_analytics_internal_zzh, true);
        if (zza == 0) {
            this.zzJq.zzc(com_google_android_gms_analytics_internal_zzh);
        }
        return zza;
    }

    public void zza(zzab com_google_android_gms_analytics_internal_zzab) {
        zzu.zzu(com_google_android_gms_analytics_internal_zzab);
        zzia();
        zzb("Hit delivery requested", com_google_android_gms_analytics_internal_zzab);
        zzhS().zze(new C01114(this, com_google_android_gms_analytics_internal_zzab));
    }

    public void zza(zzw com_google_android_gms_analytics_internal_zzw) {
        zzia();
        zzhS().zze(new C01136(this, com_google_android_gms_analytics_internal_zzw));
    }

    public void zza(String str, Runnable runnable) {
        zzu.zzh(str, "campaign param can't be empty");
        zzhS().zze(new C01103(this, str, runnable));
    }

    public void zzhG() {
        zzia();
        zzhN();
        zzhS().zze(new C01125(this));
    }

    public void zzhH() {
        zzia();
        Context context = getContext();
        if (AnalyticsReceiver.zzT(context) && AnalyticsService.zzU(context)) {
            Intent intent = new Intent(context, AnalyticsService.class);
            intent.setAction("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
            context.startService(intent);
            return;
        }
        zza(null);
    }

    public boolean zzhI() {
        zzia();
        try {
            zzhS().zzb(new C01147(this)).get();
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        }
    }

    public void zzhJ() {
        zzia();
        zzns.zzhO();
        this.zzJq.zzhJ();
    }

    public void zzhK() {
        zzaT("Radio powered up");
        zzhH();
    }

    void zzhL() {
        zzhO();
        this.zzJq.zzhL();
    }

    protected void zzhn() {
        this.zzJq.zza();
    }
}
