package com.google.android.gms.flags.impl;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.internal.zzaps;
import java.util.concurrent.Callable;

public class zzb {
    private static SharedPreferences zzaWS;

    class 1 implements Callable<SharedPreferences> {
        final /* synthetic */ Context zztd;

        1(Context context) {
            this.zztd = context;
        }

        public /* synthetic */ Object call() throws Exception {
            return zzCU();
        }

        public SharedPreferences zzCU() {
            return this.zztd.getSharedPreferences("google_sdk_flags", 1);
        }
    }

    static {
        zzaWS = null;
    }

    public static SharedPreferences zzm(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (SharedPreferences.class) {
            if (zzaWS == null) {
                zzaWS = (SharedPreferences) zzaps.zzb(new 1(context));
            }
            sharedPreferences = zzaWS;
        }
        return sharedPreferences;
    }
}
