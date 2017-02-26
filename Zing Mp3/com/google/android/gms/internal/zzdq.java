package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.ConditionVariable;
import android.support.annotation.Nullable;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.zze;
import java.util.concurrent.Callable;

@zzji
public class zzdq {
    private final Object zzako;
    private volatile boolean zzaoz;
    private final ConditionVariable zzbcs;
    @Nullable
    private SharedPreferences zzbct;

    /* renamed from: com.google.android.gms.internal.zzdq.1 */
    class C12891 implements Callable<T> {
        final /* synthetic */ zzdn zzbcu;
        final /* synthetic */ zzdq zzbcv;

        C12891(zzdq com_google_android_gms_internal_zzdq, zzdn com_google_android_gms_internal_zzdn) {
            this.zzbcv = com_google_android_gms_internal_zzdq;
            this.zzbcu = com_google_android_gms_internal_zzdn;
        }

        public T call() {
            return this.zzbcu.zza(this.zzbcv.zzbct);
        }
    }

    public zzdq() {
        this.zzako = new Object();
        this.zzbcs = new ConditionVariable();
        this.zzaoz = false;
        this.zzbct = null;
    }

    public void initialize(Context context) {
        if (!this.zzaoz) {
            synchronized (this.zzako) {
                if (this.zzaoz) {
                    return;
                }
                try {
                    Context remoteContext = zze.getRemoteContext(context);
                    if (remoteContext == null) {
                        return;
                    }
                    this.zzbct = zzu.zzgw().zzm(remoteContext);
                    this.zzaoz = true;
                    this.zzbcs.open();
                } finally {
                    this.zzbcs.open();
                }
            }
        }
    }

    public <T> T zzd(zzdn<T> com_google_android_gms_internal_zzdn_T) {
        if (this.zzbcs.block(HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS)) {
            if (!this.zzaoz) {
                synchronized (this.zzako) {
                    if (this.zzaoz) {
                    } else {
                        T zzlp = com_google_android_gms_internal_zzdn_T.zzlp();
                        return zzlp;
                    }
                }
            }
            return zzlo.zzb(new C12891(this, com_google_android_gms_internal_zzdn_T));
        }
        throw new IllegalStateException("Flags.initialize() was not called!");
    }
}
