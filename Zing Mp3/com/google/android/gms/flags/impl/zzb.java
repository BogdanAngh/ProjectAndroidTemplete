package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzvv;
import java.util.concurrent.Callable;

public class zzb {
    private static SharedPreferences WM;

    /* renamed from: com.google.android.gms.flags.impl.zzb.1 */
    class C11961 implements Callable<SharedPreferences> {
        final /* synthetic */ Context zzang;

        C11961(Context context) {
            this.zzang = context;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzbhi();
        }

        public SharedPreferences zzbhi() {
            return this.zzang.getSharedPreferences("google_sdk_flags", 1);
        }
    }

    static {
        WM = null;
    }

    public static SharedPreferences zzm(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (WM == null) {
                WM = (SharedPreferences) zzvv.zzb(new C11961(context));
            }
            sharedPreferences = WM;
        }
        return sharedPreferences;
    }
}
