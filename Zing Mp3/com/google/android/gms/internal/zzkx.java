package com.google.android.gms.internal;

import android.util.Log;
import com.google.ads.AdRequest;
import com.google.android.gms.ads.internal.util.client.zzb;

@zzji
public final class zzkx extends zzb {
    public static void m1697v(String str) {
        if (zzvo()) {
            Log.v(AdRequest.LOGTAG, str);
        }
    }

    public static boolean zzvn() {
        return ((Boolean) zzdr.zzbgr.get()).booleanValue();
    }

    public static boolean zzvo() {
        return zzb.zzbi(2) && zzvn();
    }
}
