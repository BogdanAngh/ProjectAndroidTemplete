package com.google.android.gms.ads.internal;

import android.os.Build.VERSION;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.purchase.zzi;
import com.google.android.gms.ads.internal.request.zza;
import com.google.android.gms.internal.zzbw;
import com.google.android.gms.internal.zzbx;
import com.google.android.gms.internal.zzby;
import com.google.android.gms.internal.zzcc;
import com.google.android.gms.internal.zzdq;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzft;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzgl;
import com.google.android.gms.internal.zzhc;
import com.google.android.gms.internal.zzhl;
import com.google.android.gms.internal.zzhm;
import com.google.android.gms.internal.zzif;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;

@zzgd
public class zzo {
    private static final Object zzoW;
    private static zzo zzpn;
    private final zzbw zzpA;
    private final zzby zzpB;
    private final zzi zzpC;
    private final zzed zzpD;
    private final zzdq zzpE;
    private final zza zzpo;
    private final com.google.android.gms.ads.internal.overlay.zza zzpp;
    private final zzd zzpq;
    private final zzft zzpr;
    private final zzhl zzps;
    private final zzif zzpt;
    private final zzhm zzpu;
    private final zzhc zzpv;
    private final zzlb zzpw;
    private final zzcc zzpx;
    private final zzgl zzpy;
    private final zzbx zzpz;

    static {
        zzoW = new Object();
        zza(new zzo());
    }

    protected zzo() {
        this.zzpo = new zza();
        this.zzpp = new com.google.android.gms.ads.internal.overlay.zza();
        this.zzpq = new zzd();
        this.zzpr = new zzft();
        this.zzps = new zzhl();
        this.zzpt = new zzif();
        this.zzpu = zzhm.zzK(VERSION.SDK_INT);
        this.zzpv = new zzhc(this.zzps);
        this.zzpw = new zzld();
        this.zzpx = new zzcc();
        this.zzpy = new zzgl();
        this.zzpz = new zzbx();
        this.zzpA = new zzbw();
        this.zzpB = new zzby();
        this.zzpC = new zzi();
        this.zzpD = new zzed();
        this.zzpE = new zzdq();
    }

    protected static void zza(zzo com_google_android_gms_ads_internal_zzo) {
        synchronized (zzoW) {
            zzpn = com_google_android_gms_ads_internal_zzo;
        }
    }

    public static zzcc zzbA() {
        return zzbq().zzpx;
    }

    public static zzgl zzbB() {
        return zzbq().zzpy;
    }

    public static zzbx zzbC() {
        return zzbq().zzpz;
    }

    public static zzbw zzbD() {
        return zzbq().zzpA;
    }

    public static zzby zzbE() {
        return zzbq().zzpB;
    }

    public static zzi zzbF() {
        return zzbq().zzpC;
    }

    public static zzed zzbG() {
        return zzbq().zzpD;
    }

    public static zzdq zzbH() {
        return zzbq().zzpE;
    }

    private static zzo zzbq() {
        zzo com_google_android_gms_ads_internal_zzo;
        synchronized (zzoW) {
            com_google_android_gms_ads_internal_zzo = zzpn;
        }
        return com_google_android_gms_ads_internal_zzo;
    }

    public static zza zzbr() {
        return zzbq().zzpo;
    }

    public static com.google.android.gms.ads.internal.overlay.zza zzbs() {
        return zzbq().zzpp;
    }

    public static zzd zzbt() {
        return zzbq().zzpq;
    }

    public static zzft zzbu() {
        return zzbq().zzpr;
    }

    public static zzhl zzbv() {
        return zzbq().zzps;
    }

    public static zzif zzbw() {
        return zzbq().zzpt;
    }

    public static zzhm zzbx() {
        return zzbq().zzpu;
    }

    public static zzhc zzby() {
        return zzbq().zzpv;
    }

    public static zzlb zzbz() {
        return zzbq().zzpw;
    }
}
