package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.internal.client.zzq.zza;
import com.google.android.gms.internal.zzji;

@zzji
public final class zzc extends zza {
    private final AdListener zzayk;

    public zzc(AdListener adListener) {
        this.zzayk = adListener;
    }

    public void onAdClosed() {
        this.zzayk.onAdClosed();
    }

    public void onAdFailedToLoad(int i) {
        this.zzayk.onAdFailedToLoad(i);
    }

    public void onAdLeftApplication() {
        this.zzayk.onAdLeftApplication();
    }

    public void onAdLoaded() {
        this.zzayk.onAdLoaded();
    }

    public void onAdOpened() {
        this.zzayk.onAdOpened();
    }
}
