package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.zzu;
import java.util.WeakHashMap;

@zzji
public final class zzjs {
    private WeakHashMap<Context, zza> zzcqp;

    private class zza {
        public final long zzcqq;
        public final zzjr zzcqr;
        final /* synthetic */ zzjs zzcqs;

        public zza(zzjs com_google_android_gms_internal_zzjs, zzjr com_google_android_gms_internal_zzjr) {
            this.zzcqs = com_google_android_gms_internal_zzjs;
            this.zzcqq = zzu.zzgs().currentTimeMillis();
            this.zzcqr = com_google_android_gms_internal_zzjr;
        }

        public boolean hasExpired() {
            return ((Long) zzdr.zzbgw.get()).longValue() + this.zzcqq < zzu.zzgs().currentTimeMillis();
        }
    }

    public zzjs() {
        this.zzcqp = new WeakHashMap();
    }

    public zzjr zzv(Context context) {
        zza com_google_android_gms_internal_zzjs_zza = (zza) this.zzcqp.get(context);
        zzjr zztr = (com_google_android_gms_internal_zzjs_zza == null || com_google_android_gms_internal_zzjs_zza.hasExpired() || !((Boolean) zzdr.zzbgv.get()).booleanValue()) ? new com.google.android.gms.internal.zzjr.zza(context).zztr() : new com.google.android.gms.internal.zzjr.zza(context, com_google_android_gms_internal_zzjs_zza.zzcqr).zztr();
        this.zzcqp.put(context, new zza(this, zztr));
        return zztr;
    }
}
