package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.ads.internal.util.client.zzc;
import com.google.android.gms.ads.internal.zzu;

@zzji
public final class zzll extends zzkw {
    private final String zzae;
    private final zzc zzcxh;

    public zzll(Context context, String str, String str2) {
        this(str2, zzu.zzgm().zzh(context, str));
    }

    public zzll(String str, String str2) {
        this.zzcxh = new zzc(str2);
        this.zzae = str;
    }

    public void onStop() {
    }

    public void zzfp() {
        this.zzcxh.zzv(this.zzae);
    }
}
