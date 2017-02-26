package com.google.android.gms.internal;

import com.google.android.gms.internal.zzlw.zzc;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@zzji
public class zzlx<T> implements zzlw<T> {
    private final Object zzako;
    protected int zzbtt;
    protected final BlockingQueue<zza> zzcys;
    protected T zzcyt;

    class zza {
        public final zzc<T> zzcyu;
        public final com.google.android.gms.internal.zzlw.zza zzcyv;
        final /* synthetic */ zzlx zzcyw;

        public zza(zzlx com_google_android_gms_internal_zzlx, zzc<T> com_google_android_gms_internal_zzlw_zzc_T, com.google.android.gms.internal.zzlw.zza com_google_android_gms_internal_zzlw_zza) {
            this.zzcyw = com_google_android_gms_internal_zzlx;
            this.zzcyu = com_google_android_gms_internal_zzlw_zzc_T;
            this.zzcyv = com_google_android_gms_internal_zzlw_zza;
        }
    }

    public zzlx() {
        this.zzako = new Object();
        this.zzbtt = 0;
        this.zzcys = new LinkedBlockingQueue();
    }

    public int getStatus() {
        return this.zzbtt;
    }

    public void reject() {
        synchronized (this.zzako) {
            if (this.zzbtt != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzbtt = -1;
            for (zza com_google_android_gms_internal_zzlx_zza : this.zzcys) {
                com_google_android_gms_internal_zzlx_zza.zzcyv.run();
            }
            this.zzcys.clear();
        }
    }

    public void zza(zzc<T> com_google_android_gms_internal_zzlw_zzc_T, com.google.android.gms.internal.zzlw.zza com_google_android_gms_internal_zzlw_zza) {
        synchronized (this.zzako) {
            if (this.zzbtt == 1) {
                com_google_android_gms_internal_zzlw_zzc_T.zzd(this.zzcyt);
            } else if (this.zzbtt == -1) {
                com_google_android_gms_internal_zzlw_zza.run();
            } else if (this.zzbtt == 0) {
                this.zzcys.add(new zza(this, com_google_android_gms_internal_zzlw_zzc_T, com_google_android_gms_internal_zzlw_zza));
            }
        }
    }

    public void zzg(T t) {
        synchronized (this.zzako) {
            if (this.zzbtt != 0) {
                throw new UnsupportedOperationException();
            }
            this.zzcyt = t;
            this.zzbtt = 1;
            for (zza com_google_android_gms_internal_zzlx_zza : this.zzcys) {
                com_google_android_gms_internal_zzlx_zza.zzcyu.zzd(t);
            }
            this.zzcys.clear();
        }
    }
}
