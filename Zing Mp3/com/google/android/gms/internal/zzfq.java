package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzfq extends zzkw {
    final zzmd zzbnz;
    final zzfs zzbra;
    private final String zzbrb;

    /* renamed from: com.google.android.gms.internal.zzfq.1 */
    class C13071 implements Runnable {
        final /* synthetic */ zzfq zzbrc;

        C13071(zzfq com_google_android_gms_internal_zzfq) {
            this.zzbrc = com_google_android_gms_internal_zzfq;
        }

        public void run() {
            zzu.zzhj().zzb(this.zzbrc);
        }
    }

    zzfq(zzmd com_google_android_gms_internal_zzmd, zzfs com_google_android_gms_internal_zzfs, String str) {
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzbra = com_google_android_gms_internal_zzfs;
        this.zzbrb = str;
        zzu.zzhj().zza(this);
    }

    public void onStop() {
        this.zzbra.abort();
    }

    public void zzfp() {
        try {
            this.zzbra.zzbg(this.zzbrb);
        } finally {
            zzlb.zzcvl.post(new C13071(this));
        }
    }
}
