package com.google.android.gms.ads.internal.client;

import com.google.android.gms.ads.internal.reward.client.zzf;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.internal.zzeu;
import com.google.android.gms.internal.zzhx;
import com.google.android.gms.internal.zzim;
import com.google.android.gms.internal.zzji;

@zzji
public class zzm {
    private static final Object zzaox;
    private static zzm zzbal;
    private final zza zzbam;
    private final zzl zzban;

    static {
        zzaox = new Object();
        zza(new zzm());
    }

    protected zzm() {
        this.zzbam = new zza();
        this.zzban = new zzl(new zze(), new zzd(), new zzai(), new zzeu(), new zzf(), new zzim(), new zzhx());
    }

    protected static void zza(zzm com_google_android_gms_ads_internal_client_zzm) {
        synchronized (zzaox) {
            zzbal = com_google_android_gms_ads_internal_client_zzm;
        }
    }

    private static zzm zzkq() {
        zzm com_google_android_gms_ads_internal_client_zzm;
        synchronized (zzaox) {
            com_google_android_gms_ads_internal_client_zzm = zzbal;
        }
        return com_google_android_gms_ads_internal_client_zzm;
    }

    public static zza zzkr() {
        return zzkq().zzbam;
    }

    public static zzl zzks() {
        return zzkq().zzban;
    }
}
