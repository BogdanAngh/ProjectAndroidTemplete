package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzme.zza;
import java.util.concurrent.atomic.AtomicBoolean;

@zzji
public abstract class zziq implements zzld<Void>, zza {
    protected final Context mContext;
    protected final zzmd zzbnz;
    protected final zziu.zza zzcge;
    protected final zzko.zza zzcgf;
    protected AdResponseParcel zzcgg;
    private Runnable zzcgh;
    protected final Object zzcgi;
    private AtomicBoolean zzcgj;

    /* renamed from: com.google.android.gms.internal.zziq.1 */
    class C13851 implements Runnable {
        final /* synthetic */ zziq zzcgk;

        C13851(zziq com_google_android_gms_internal_zziq) {
            this.zzcgk = com_google_android_gms_internal_zziq;
        }

        public void run() {
            if (this.zzcgk.zzcgj.get()) {
                zzb.m1695e("Timed out waiting for WebView to finish loading.");
                this.zzcgk.cancel();
            }
        }
    }

    protected zziq(Context context, zzko.zza com_google_android_gms_internal_zzko_zza, zzmd com_google_android_gms_internal_zzmd, zziu.zza com_google_android_gms_internal_zziu_zza) {
        this.zzcgi = new Object();
        this.zzcgj = new AtomicBoolean(true);
        this.mContext = context;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzcgg = this.zzcgf.zzcsu;
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzcge = com_google_android_gms_internal_zziu_zza;
    }

    private zzko zzap(int i) {
        AdRequestInfoParcel adRequestInfoParcel = this.zzcgf.zzcmx;
        return new zzko(adRequestInfoParcel.zzcju, this.zzbnz, this.zzcgg.zzbvk, i, this.zzcgg.zzbvl, this.zzcgg.zzcld, this.zzcgg.orientation, this.zzcgg.zzbvq, adRequestInfoParcel.zzcjx, this.zzcgg.zzclb, null, null, null, null, null, this.zzcgg.zzclc, this.zzcgf.zzarm, this.zzcgg.zzcla, this.zzcgf.zzcso, this.zzcgg.zzclf, this.zzcgg.zzclg, this.zzcgf.zzcsi, null, this.zzcgg.zzclq, this.zzcgg.zzclr, this.zzcgg.zzcls, this.zzcgg.zzclt, this.zzcgg.zzclu, null, this.zzcgg.zzbvn, this.zzcgg.zzclx);
    }

    public void cancel() {
        if (this.zzcgj.getAndSet(false)) {
            this.zzbnz.stopLoading();
            zzu.zzgo().zzl(this.zzbnz);
            zzao(-1);
            zzlb.zzcvl.removeCallbacks(this.zzcgh);
        }
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
        int i = 0;
        zzb.zzdg("WebView finished loading.");
        if (this.zzcgj.getAndSet(false)) {
            if (z) {
                i = zzry();
            }
            zzao(i);
            zzlb.zzcvl.removeCallbacks(this.zzcgh);
        }
    }

    protected void zzao(int i) {
        if (i != -2) {
            this.zzcgg = new AdResponseParcel(i, this.zzcgg.zzbvq);
        }
        this.zzbnz.zzwx();
        this.zzcge.zzb(zzap(i));
    }

    public final Void zzrw() {
        zzaa.zzhs("Webview render task needs to be called on UI thread.");
        this.zzcgh = new C13851(this);
        zzlb.zzcvl.postDelayed(this.zzcgh, ((Long) zzdr.zzbhk.get()).longValue());
        zzrx();
        return null;
    }

    protected abstract void zzrx();

    protected int zzry() {
        return -2;
    }

    public /* synthetic */ Object zzrz() {
        return zzrw();
    }
}
