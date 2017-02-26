package com.google.android.gms.ads.internal;

import android.os.Build.VERSION;
import com.google.android.gms.ads.internal.overlay.zza;
import com.google.android.gms.ads.internal.overlay.zze;
import com.google.android.gms.ads.internal.overlay.zzq;
import com.google.android.gms.ads.internal.overlay.zzr;
import com.google.android.gms.ads.internal.purchase.zzi;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzcz;
import com.google.android.gms.internal.zzdo;
import com.google.android.gms.internal.zzdp;
import com.google.android.gms.internal.zzdq;
import com.google.android.gms.internal.zzdu;
import com.google.android.gms.internal.zzfr;
import com.google.android.gms.internal.zzfz;
import com.google.android.gms.internal.zzgl;
import com.google.android.gms.internal.zzgv;
import com.google.android.gms.internal.zziu;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjs;
import com.google.android.gms.internal.zzkr;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzlc;
import com.google.android.gms.internal.zzlf;
import com.google.android.gms.internal.zzlj;
import com.google.android.gms.internal.zzlk;
import com.google.android.gms.internal.zzlv;
import com.google.android.gms.internal.zzly;
import com.google.android.gms.internal.zzmf;

@zzji
public class zzu {
    private static final Object zzaox;
    private static zzu zzaqa;
    private final zza zzaqb;
    private final com.google.android.gms.ads.internal.request.zza zzaqc;
    private final zze zzaqd;
    private final zziu zzaqe;
    private final zzlb zzaqf;
    private final zzmf zzaqg;
    private final zzlc zzaqh;
    private final zzcz zzaqi;
    private final zzkr zzaqj;
    private final com.google.android.gms.ads.internal.cache.zza zzaqk;
    private final com.google.android.gms.common.util.zze zzaql;
    private final zzg zzaqm;
    private final zzdu zzaqn;
    private final zzlf zzaqo;
    private final zzjs zzaqp;
    private final zzdo zzaqq;
    private final zzdp zzaqr;
    private final zzdq zzaqs;
    private final zzlv zzaqt;
    private final zzi zzaqu;
    private final zzfz zzaqv;
    private final zzgl zzaqw;
    private final zzlj zzaqx;
    private final zzq zzaqy;
    private final zzr zzaqz;
    private final zzgv zzara;
    private final zzlk zzarb;
    private final zzp zzarc;
    private final zzfr zzard;
    private final zzly zzare;

    static {
        zzaox = new Object();
        zza(new zzu());
    }

    protected zzu() {
        this.zzaqb = new zza();
        this.zzaqc = new com.google.android.gms.ads.internal.request.zza();
        this.zzaqd = new zze();
        this.zzaqe = new zziu();
        this.zzaqf = new zzlb();
        this.zzaqg = new zzmf();
        this.zzaqh = zzlc.zzbh(VERSION.SDK_INT);
        this.zzaqi = new zzcz();
        this.zzaqj = new zzkr(this.zzaqf);
        this.zzaqk = new com.google.android.gms.ads.internal.cache.zza();
        this.zzaql = zzh.zzayl();
        this.zzaqm = new zzg();
        this.zzaqn = new zzdu();
        this.zzaqo = new zzlf();
        this.zzaqp = new zzjs();
        this.zzaqq = new zzdo();
        this.zzaqr = new zzdp();
        this.zzaqs = new zzdq();
        this.zzaqt = new zzlv();
        this.zzaqu = new zzi();
        this.zzaqv = new zzfz();
        this.zzaqw = new zzgl();
        this.zzaqx = new zzlj();
        this.zzaqy = new zzq();
        this.zzaqz = new zzr();
        this.zzara = new zzgv();
        this.zzarb = new zzlk();
        this.zzarc = new zzp();
        this.zzard = new zzfr();
        this.zzare = new zzly();
    }

    protected static void zza(zzu com_google_android_gms_ads_internal_zzu) {
        synchronized (zzaox) {
            zzaqa = com_google_android_gms_ads_internal_zzu;
        }
    }

    private static zzu zzgh() {
        zzu com_google_android_gms_ads_internal_zzu;
        synchronized (zzaox) {
            com_google_android_gms_ads_internal_zzu = zzaqa;
        }
        return com_google_android_gms_ads_internal_zzu;
    }

    public static com.google.android.gms.ads.internal.request.zza zzgi() {
        return zzgh().zzaqc;
    }

    public static zza zzgj() {
        return zzgh().zzaqb;
    }

    public static zze zzgk() {
        return zzgh().zzaqd;
    }

    public static zziu zzgl() {
        return zzgh().zzaqe;
    }

    public static zzlb zzgm() {
        return zzgh().zzaqf;
    }

    public static zzmf zzgn() {
        return zzgh().zzaqg;
    }

    public static zzlc zzgo() {
        return zzgh().zzaqh;
    }

    public static zzcz zzgp() {
        return zzgh().zzaqi;
    }

    public static zzkr zzgq() {
        return zzgh().zzaqj;
    }

    public static com.google.android.gms.ads.internal.cache.zza zzgr() {
        return zzgh().zzaqk;
    }

    public static com.google.android.gms.common.util.zze zzgs() {
        return zzgh().zzaql;
    }

    public static zzdu zzgt() {
        return zzgh().zzaqn;
    }

    public static zzlf zzgu() {
        return zzgh().zzaqo;
    }

    public static zzjs zzgv() {
        return zzgh().zzaqp;
    }

    public static zzdp zzgw() {
        return zzgh().zzaqr;
    }

    public static zzdo zzgx() {
        return zzgh().zzaqq;
    }

    public static zzdq zzgy() {
        return zzgh().zzaqs;
    }

    public static zzlv zzgz() {
        return zzgh().zzaqt;
    }

    public static zzi zzha() {
        return zzgh().zzaqu;
    }

    public static zzfz zzhb() {
        return zzgh().zzaqv;
    }

    public static zzlj zzhc() {
        return zzgh().zzaqx;
    }

    public static zzq zzhd() {
        return zzgh().zzaqy;
    }

    public static zzr zzhe() {
        return zzgh().zzaqz;
    }

    public static zzgv zzhf() {
        return zzgh().zzara;
    }

    public static zzp zzhg() {
        return zzgh().zzarc;
    }

    public static zzlk zzhh() {
        return zzgh().zzarb;
    }

    public static zzg zzhi() {
        return zzgh().zzaqm;
    }

    public static zzfr zzhj() {
        return zzgh().zzard;
    }

    public static zzly zzhk() {
        return zzgh().zzare;
    }
}
