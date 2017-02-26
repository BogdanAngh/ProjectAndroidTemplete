package com.google.android.gms.analytics;

import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zznb;
import java.util.ListIterator;

public class zza extends zzh<zza> {
    private final zzf ao;
    private boolean ap;

    public zza(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf.zzacc(), com_google_android_gms_analytics_internal_zzf.zzabz());
        this.ao = com_google_android_gms_analytics_internal_zzf;
    }

    public void enableAdvertisingIdCollection(boolean z) {
        this.ap = z;
    }

    protected void zza(zze com_google_android_gms_analytics_zze) {
        zznb com_google_android_gms_internal_zznb = (zznb) com_google_android_gms_analytics_zze.zzb(zznb.class);
        if (TextUtils.isEmpty(com_google_android_gms_internal_zznb.zzze())) {
            com_google_android_gms_internal_zznb.setClientId(this.ao.zzacq().zzady());
        }
        if (this.ap && TextUtils.isEmpty(com_google_android_gms_internal_zznb.zzabb())) {
            com.google.android.gms.analytics.internal.zza zzacp = this.ao.zzacp();
            com_google_android_gms_internal_zznb.zzei(zzacp.zzabn());
            com_google_android_gms_internal_zznb.zzas(zzacp.zzabc());
        }
    }

    public void zzdr(String str) {
        zzaa.zzib(str);
        zzds(str);
        zzzu().add(new zzb(this.ao, str));
    }

    public void zzds(String str) {
        Uri zzdt = zzb.zzdt(str);
        ListIterator listIterator = zzzu().listIterator();
        while (listIterator.hasNext()) {
            if (zzdt.equals(((zzk) listIterator.next()).zzyx())) {
                listIterator.remove();
            }
        }
    }

    zzf zzyt() {
        return this.ao;
    }

    public zze zzyu() {
        zze zzzi = zzzt().zzzi();
        zzzi.zza(this.ao.zzach().zzadg());
        zzzi.zza(this.ao.zzaci().zzafl());
        zzd(zzzi);
        return zzzi;
    }
}
