package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.zzi;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.internal.zzcp.zza;
import com.google.android.gms.internal.zzcp.zzd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.WeakHashMap;

@zzji
public class zzco implements zzcq {
    private final Object zzako;
    private final VersionInfoParcel zzanu;
    private final WeakHashMap<zzko, zzcp> zzata;
    private final ArrayList<zzcp> zzatb;
    private final Context zzatc;
    private final zzgh zzatd;

    public zzco(Context context, VersionInfoParcel versionInfoParcel, zzgh com_google_android_gms_internal_zzgh) {
        this.zzako = new Object();
        this.zzata = new WeakHashMap();
        this.zzatb = new ArrayList();
        this.zzatc = context.getApplicationContext();
        this.zzanu = versionInfoParcel;
        this.zzatd = com_google_android_gms_internal_zzgh;
    }

    public void zza(AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko) {
        zza(adSizeParcel, com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko.zzcbm.getView());
    }

    public void zza(AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko, View view) {
        zza(adSizeParcel, com_google_android_gms_internal_zzko, new zzd(view, com_google_android_gms_internal_zzko), null);
    }

    public void zza(AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko, View view, zzgi com_google_android_gms_internal_zzgi) {
        zza(adSizeParcel, com_google_android_gms_internal_zzko, new zzd(view, com_google_android_gms_internal_zzko), com_google_android_gms_internal_zzgi);
    }

    public void zza(AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko, zzi com_google_android_gms_ads_internal_formats_zzi) {
        zza(adSizeParcel, com_google_android_gms_internal_zzko, new zza(com_google_android_gms_ads_internal_formats_zzi), null);
    }

    public void zza(AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko, zzcw com_google_android_gms_internal_zzcw, @Nullable zzgi com_google_android_gms_internal_zzgi) {
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp;
            if (zzi(com_google_android_gms_internal_zzko)) {
                com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            } else {
                com_google_android_gms_internal_zzcp = new zzcp(this.zzatc, adSizeParcel, com_google_android_gms_internal_zzko, this.zzanu, com_google_android_gms_internal_zzcw);
                com_google_android_gms_internal_zzcp.zza((zzcq) this);
                this.zzata.put(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzcp);
                this.zzatb.add(com_google_android_gms_internal_zzcp);
            }
            if (com_google_android_gms_internal_zzgi != null) {
                com_google_android_gms_internal_zzcp.zza(new zzcr(com_google_android_gms_internal_zzcp, com_google_android_gms_internal_zzgi));
            } else {
                com_google_android_gms_internal_zzcp.zza(new zzcs(com_google_android_gms_internal_zzcp, this.zzatd));
            }
        }
    }

    public void zza(zzcp com_google_android_gms_internal_zzcp) {
        synchronized (this.zzako) {
            if (!com_google_android_gms_internal_zzcp.zzii()) {
                this.zzatb.remove(com_google_android_gms_internal_zzcp);
                Iterator it = this.zzata.entrySet().iterator();
                while (it.hasNext()) {
                    if (((Entry) it.next()).getValue() == com_google_android_gms_internal_zzcp) {
                        it.remove();
                    }
                }
            }
        }
    }

    public boolean zzi(zzko com_google_android_gms_internal_zzko) {
        boolean z;
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            z = com_google_android_gms_internal_zzcp != null && com_google_android_gms_internal_zzcp.zzii();
        }
        return z;
    }

    public void zzj(zzko com_google_android_gms_internal_zzko) {
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            if (com_google_android_gms_internal_zzcp != null) {
                com_google_android_gms_internal_zzcp.zzig();
            }
        }
    }

    public void zzk(zzko com_google_android_gms_internal_zzko) {
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            if (com_google_android_gms_internal_zzcp != null) {
                com_google_android_gms_internal_zzcp.stop();
            }
        }
    }

    public void zzl(zzko com_google_android_gms_internal_zzko) {
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            if (com_google_android_gms_internal_zzcp != null) {
                com_google_android_gms_internal_zzcp.pause();
            }
        }
    }

    public void zzm(zzko com_google_android_gms_internal_zzko) {
        synchronized (this.zzako) {
            zzcp com_google_android_gms_internal_zzcp = (zzcp) this.zzata.get(com_google_android_gms_internal_zzko);
            if (com_google_android_gms_internal_zzcp != null) {
                com_google_android_gms_internal_zzcp.resume();
            }
        }
    }
}
