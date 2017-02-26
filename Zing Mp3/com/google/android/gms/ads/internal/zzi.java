package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzaq;
import com.google.android.gms.internal.zzau;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzla;
import com.mp3download.zingmp3.BuildConfig;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

@zzji
class zzi implements zzaq, Runnable {
    private zzv zzaly;
    private final List<Object[]> zzani;
    private final AtomicReference<zzaq> zzanj;
    CountDownLatch zzank;

    public zzi(zzv com_google_android_gms_ads_internal_zzv) {
        this.zzani = new Vector();
        this.zzanj = new AtomicReference();
        this.zzank = new CountDownLatch(1);
        this.zzaly = com_google_android_gms_ads_internal_zzv;
        if (zzm.zzkr().zzwq()) {
            zzla.zza((Runnable) this);
        } else {
            run();
        }
    }

    private void zzfi() {
        if (!this.zzani.isEmpty()) {
            for (Object[] objArr : this.zzani) {
                if (objArr.length == 1) {
                    ((zzaq) this.zzanj.get()).zza((MotionEvent) objArr[0]);
                } else if (objArr.length == 3) {
                    ((zzaq) this.zzanj.get()).zza(((Integer) objArr[0]).intValue(), ((Integer) objArr[1]).intValue(), ((Integer) objArr[2]).intValue());
                }
            }
            this.zzani.clear();
        }
    }

    private Context zzg(Context context) {
        if (!((Boolean) zzdr.zzbdj.get()).booleanValue()) {
            return context;
        }
        Context applicationContext = context.getApplicationContext();
        return applicationContext != null ? applicationContext : context;
    }

    public void run() {
        try {
            boolean z = !((Boolean) zzdr.zzbef.get()).booleanValue() || this.zzaly.zzari.zzcyc;
            zza(zzd(this.zzaly.zzari.zzda, zzg(this.zzaly.zzahs), z));
        } finally {
            this.zzank.countDown();
            this.zzaly = null;
        }
    }

    public String zza(Context context, String str, View view) {
        if (zzfh()) {
            zzaq com_google_android_gms_internal_zzaq = (zzaq) this.zzanj.get();
            if (com_google_android_gms_internal_zzaq != null) {
                zzfi();
                return com_google_android_gms_internal_zzaq.zza(zzg(context), str, view);
            }
        }
        return BuildConfig.FLAVOR;
    }

    public String zza(Context context, byte[] bArr) {
        if (zzfh()) {
            zzaq com_google_android_gms_internal_zzaq = (zzaq) this.zzanj.get();
            if (com_google_android_gms_internal_zzaq != null) {
                zzfi();
                return com_google_android_gms_internal_zzaq.zzb(zzg(context));
            }
        }
        return BuildConfig.FLAVOR;
    }

    public void zza(int i, int i2, int i3) {
        zzaq com_google_android_gms_internal_zzaq = (zzaq) this.zzanj.get();
        if (com_google_android_gms_internal_zzaq != null) {
            zzfi();
            com_google_android_gms_internal_zzaq.zza(i, i2, i3);
            return;
        }
        this.zzani.add(new Object[]{Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3)});
    }

    public void zza(MotionEvent motionEvent) {
        zzaq com_google_android_gms_internal_zzaq = (zzaq) this.zzanj.get();
        if (com_google_android_gms_internal_zzaq != null) {
            zzfi();
            com_google_android_gms_internal_zzaq.zza(motionEvent);
            return;
        }
        this.zzani.add(new Object[]{motionEvent});
    }

    protected void zza(zzaq com_google_android_gms_internal_zzaq) {
        this.zzanj.set(com_google_android_gms_internal_zzaq);
    }

    public String zzb(Context context) {
        return zza(context, null);
    }

    protected zzaq zzd(String str, Context context, boolean z) {
        return zzau.zza(str, context, z);
    }

    protected boolean zzfh() {
        try {
            this.zzank.await();
            return true;
        } catch (Throwable e) {
            zzb.zzc("Interrupted during GADSignals creation.", e);
            return false;
        }
    }
}
