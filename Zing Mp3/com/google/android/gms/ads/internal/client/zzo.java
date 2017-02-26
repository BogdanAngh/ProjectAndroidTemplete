package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.internal.zzji;

@zzji
public class zzo extends AdListener {
    private final Object lock;
    private AdListener zzbaq;

    public zzo() {
        this.lock = new Object();
    }

    public void onAdClosed() {
        synchronized (this.lock) {
            if (this.zzbaq != null) {
                this.zzbaq.onAdClosed();
            }
        }
    }

    public void onAdFailedToLoad(int i) {
        synchronized (this.lock) {
            if (this.zzbaq != null) {
                this.zzbaq.onAdFailedToLoad(i);
            }
        }
    }

    public void onAdLeftApplication() {
        synchronized (this.lock) {
            if (this.zzbaq != null) {
                this.zzbaq.onAdLeftApplication();
            }
        }
    }

    public void onAdLoaded() {
        synchronized (this.lock) {
            if (this.zzbaq != null) {
                this.zzbaq.onAdLoaded();
            }
        }
    }

    public void onAdOpened() {
        synchronized (this.lock) {
            if (this.zzbaq != null) {
                this.zzbaq.onAdOpened();
            }
        }
    }

    public void zza(AdListener adListener) {
        synchronized (this.lock) {
            this.zzbaq = adListener;
        }
    }
}
