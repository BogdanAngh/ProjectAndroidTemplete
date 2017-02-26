package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzky extends Handler {
    public zzky(Looper looper) {
        super(looper);
    }

    public void handleMessage(Message message) {
        try {
            super.handleMessage(message);
        } catch (Throwable e) {
            zzu.zzgq().zza(e, "AdMobHandler.handleMessage");
        }
    }
}
