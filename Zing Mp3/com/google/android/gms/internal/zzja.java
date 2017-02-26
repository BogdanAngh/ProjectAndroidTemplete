package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.internal.zzgh.zzb;
import com.google.android.gms.internal.zzgh.zze;
import com.google.android.gms.internal.zzlw.zzc;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
public class zzja {
    private static final Object zzaox;
    private static final long zzchj;
    private static boolean zzchk;
    private static zzgh zzchl;
    private final Context mContext;
    private final zzq zzbnr;
    private final zzav zzbnx;
    private final com.google.android.gms.internal.zzko.zza zzcgf;
    private zzgf zzchm;
    private zze zzchn;
    private zzge zzcho;
    private boolean zzchp;

    public static abstract class zza {
        public abstract void zze(zzgi com_google_android_gms_internal_zzgi);

        public void zzsr() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzja.1 */
    class C13911 implements zzc<zzgi> {
        final /* synthetic */ zza zzchq;
        final /* synthetic */ zzja zzchr;

        C13911(zzja com_google_android_gms_internal_zzja, zza com_google_android_gms_internal_zzja_zza) {
            this.zzchr = com_google_android_gms_internal_zzja;
            this.zzchq = com_google_android_gms_internal_zzja_zza;
        }

        public void zzb(zzgi com_google_android_gms_internal_zzgi) {
            this.zzchq.zze(com_google_android_gms_internal_zzgi);
        }

        public /* synthetic */ void zzd(Object obj) {
            zzb((zzgi) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzja.2 */
    class C13922 implements com.google.android.gms.internal.zzlw.zza {
        final /* synthetic */ zza zzchq;
        final /* synthetic */ zzja zzchr;

        C13922(zzja com_google_android_gms_internal_zzja, zza com_google_android_gms_internal_zzja_zza) {
            this.zzchr = com_google_android_gms_internal_zzja;
            this.zzchq = com_google_android_gms_internal_zzja_zza;
        }

        public void run() {
            this.zzchq.zzsr();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzja.3 */
    class C13933 implements zzlg<zzge> {
        final /* synthetic */ zzja zzchr;

        C13933(zzja com_google_android_gms_internal_zzja) {
            this.zzchr = com_google_android_gms_internal_zzja;
        }

        public void zza(zzge com_google_android_gms_internal_zzge) {
            com_google_android_gms_internal_zzge.zza(this.zzchr.zzbnr, this.zzchr.zzbnr, this.zzchr.zzbnr, this.zzchr.zzbnr, false, null, null, null, null);
        }

        public /* synthetic */ void zzd(Object obj) {
            zza((zzge) obj);
        }
    }

    static {
        zzchj = TimeUnit.SECONDS.toMillis(60);
        zzaox = new Object();
        zzchk = false;
        zzchl = null;
    }

    public zzja(Context context, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzq com_google_android_gms_ads_internal_zzq, zzav com_google_android_gms_internal_zzav) {
        this.zzchp = false;
        this.mContext = context;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzbnr = com_google_android_gms_ads_internal_zzq;
        this.zzbnx = com_google_android_gms_internal_zzav;
        this.zzchp = ((Boolean) zzdr.zzbiz.get()).booleanValue();
    }

    public static String zza(com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, String str) {
        String valueOf = String.valueOf(com_google_android_gms_internal_zzko_zza.zzcsu.zzcbo.indexOf("https") == 0 ? "https:" : "http:");
        String valueOf2 = String.valueOf(str);
        return valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
    }

    private void zzsj() {
        synchronized (zzaox) {
            if (!zzchk) {
                zzchl = new zzgh(this.mContext.getApplicationContext() != null ? this.mContext.getApplicationContext() : this.mContext, this.zzcgf.zzcmx.zzari, zza(this.zzcgf, (String) zzdr.zzbix.get()), new C13933(this), new zzb());
                zzchk = true;
            }
        }
    }

    private void zzsk() {
        this.zzchn = new zze(zzsp().zzc(this.zzbnx));
    }

    private void zzsl() {
        this.zzchm = new zzgf();
    }

    private void zzsm() throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        this.zzcho = (zzge) zzsn().zza(this.mContext, this.zzcgf.zzcmx.zzari, zza(this.zzcgf, (String) zzdr.zzbix.get()), this.zzbnx, this.zzbnr.zzec()).get(zzchj, TimeUnit.MILLISECONDS);
        this.zzcho.zza(this.zzbnr, this.zzbnr, this.zzbnr, this.zzbnr, false, null, null, null, null);
    }

    public void zza(zza com_google_android_gms_internal_zzja_zza) {
        if (this.zzchp) {
            zze zzsq = zzsq();
            if (zzsq == null) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("SharedJavascriptEngine not initialized");
                return;
            } else {
                zzsq.zza(new C13911(this, com_google_android_gms_internal_zzja_zza), new C13922(this, com_google_android_gms_internal_zzja_zza));
                return;
            }
        }
        zzgi zzso = zzso();
        if (zzso == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("JavascriptEngine not initialized");
        } else {
            com_google_android_gms_internal_zzja_zza.zze(zzso);
        }
    }

    public void zzsh() {
        if (this.zzchp) {
            zzsj();
        } else {
            zzsl();
        }
    }

    public void zzsi() throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        if (this.zzchp) {
            zzsk();
        } else {
            zzsm();
        }
    }

    protected zzgf zzsn() {
        return this.zzchm;
    }

    protected zzge zzso() {
        return this.zzcho;
    }

    protected zzgh zzsp() {
        return zzchl;
    }

    protected zze zzsq() {
        return this.zzchn;
    }
}
