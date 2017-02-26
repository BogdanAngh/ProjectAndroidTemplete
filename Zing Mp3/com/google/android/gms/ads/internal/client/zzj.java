package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.doubleclick.AppEventListener;
import com.google.android.gms.ads.internal.client.zzw.zza;
import com.google.android.gms.internal.zzji;

@zzji
public final class zzj extends zza {
    private final AppEventListener zzazw;

    public zzj(AppEventListener appEventListener) {
        this.zzazw = appEventListener;
    }

    public void onAppEvent(String str, String str2) {
        this.zzazw.onAppEvent(str, str2);
    }
}
