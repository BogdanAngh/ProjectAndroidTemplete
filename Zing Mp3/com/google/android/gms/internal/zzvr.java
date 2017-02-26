package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.internal.zzvq.zzd;
import java.util.ArrayList;
import java.util.Collection;

public class zzvr {
    private final Collection<zzvq> zzbcp;
    private final Collection<zzd> zzbcq;
    private final Collection<zzd> zzbcr;

    public zzvr() {
        this.zzbcp = new ArrayList();
        this.zzbcq = new ArrayList();
        this.zzbcr = new ArrayList();
    }

    public static void initialize(Context context) {
        zzvu.zzbhf().initialize(context);
    }

    public void zza(zzvq com_google_android_gms_internal_zzvq) {
        this.zzbcp.add(com_google_android_gms_internal_zzvq);
    }
}
