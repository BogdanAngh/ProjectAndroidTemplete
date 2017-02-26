package com.google.android.gms.ads;

import com.google.android.gms.ads.internal.client.zzn;
import com.google.android.gms.internal.zzji;

@zzji
public final class Correlator {
    private zzn zzakl;

    public Correlator() {
        this.zzakl = new zzn();
    }

    public void reset() {
        this.zzakl.zzkt();
    }

    public zzn zzdu() {
        return this.zzakl;
    }
}
