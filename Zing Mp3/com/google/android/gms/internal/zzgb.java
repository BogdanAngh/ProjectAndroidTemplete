package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.zzl;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzaa;
import java.util.Iterator;
import java.util.LinkedList;

@zzji
class zzgb {
    private final String zzant;
    private final LinkedList<zza> zzbso;
    private AdRequestParcel zzbsp;
    private final int zzbsq;
    private boolean zzbsr;

    class zza {
        zzl zzbss;
        @Nullable
        AdRequestParcel zzbst;
        zzfx zzbsu;
        long zzbsv;
        boolean zzbsw;
        boolean zzbsx;
        final /* synthetic */ zzgb zzbsy;

        zza(zzgb com_google_android_gms_internal_zzgb, zzfw com_google_android_gms_internal_zzfw) {
            this.zzbsy = com_google_android_gms_internal_zzgb;
            this.zzbss = com_google_android_gms_internal_zzfw.zzbk(com_google_android_gms_internal_zzgb.zzant);
            this.zzbsu = new zzfx();
            this.zzbsu.zzc(this.zzbss);
        }

        zza(zzgb com_google_android_gms_internal_zzgb, zzfw com_google_android_gms_internal_zzfw, AdRequestParcel adRequestParcel) {
            this(com_google_android_gms_internal_zzgb, com_google_android_gms_internal_zzfw);
            this.zzbst = adRequestParcel;
        }

        void zznt() {
            if (!this.zzbsw) {
                this.zzbsx = this.zzbss.zzb(zzfz.zzl(this.zzbst != null ? this.zzbst : this.zzbsy.zzbsp));
                this.zzbsw = true;
                this.zzbsv = zzu.zzgs().currentTimeMillis();
            }
        }
    }

    zzgb(AdRequestParcel adRequestParcel, String str, int i) {
        zzaa.zzy(adRequestParcel);
        zzaa.zzy(str);
        this.zzbso = new LinkedList();
        this.zzbsp = adRequestParcel;
        this.zzant = str;
        this.zzbsq = i;
    }

    String getAdUnitId() {
        return this.zzant;
    }

    int getNetworkType() {
        return this.zzbsq;
    }

    int size() {
        return this.zzbso.size();
    }

    void zza(zzfw com_google_android_gms_internal_zzfw, AdRequestParcel adRequestParcel) {
        this.zzbso.add(new zza(this, com_google_android_gms_internal_zzfw, adRequestParcel));
    }

    void zzb(zzfw com_google_android_gms_internal_zzfw) {
        zza com_google_android_gms_internal_zzgb_zza = new zza(this, com_google_android_gms_internal_zzfw);
        this.zzbso.add(com_google_android_gms_internal_zzgb_zza);
        com_google_android_gms_internal_zzgb_zza.zznt();
    }

    AdRequestParcel zzno() {
        return this.zzbsp;
    }

    int zznp() {
        Iterator it = this.zzbso.iterator();
        int i = 0;
        while (it.hasNext()) {
            i = ((zza) it.next()).zzbsw ? i + 1 : i;
        }
        return i;
    }

    void zznq() {
        Iterator it = this.zzbso.iterator();
        while (it.hasNext()) {
            ((zza) it.next()).zznt();
        }
    }

    void zznr() {
        this.zzbsr = true;
    }

    boolean zzns() {
        return this.zzbsr;
    }

    zza zzp(@Nullable AdRequestParcel adRequestParcel) {
        if (adRequestParcel != null) {
            this.zzbsp = adRequestParcel;
        }
        return (zza) this.zzbso.remove();
    }
}
