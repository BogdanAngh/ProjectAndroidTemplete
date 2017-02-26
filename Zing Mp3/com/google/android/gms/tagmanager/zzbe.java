package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzbe extends zzam {
    private static final String ID;
    private static final String aDP;
    private final Context zzahs;

    static {
        ID = zzag.INSTALL_REFERRER.toString();
        aDP = zzah.COMPONENT.toString();
    }

    public zzbe(Context context) {
        super(ID, new String[0]);
        this.zzahs = context;
    }

    public zza zzay(Map<String, zza> map) {
        String zzag = zzbf.zzag(this.zzahs, ((zza) map.get(aDP)) != null ? zzdm.zzg((zza) map.get(aDP)) : null);
        return zzag != null ? zzdm.zzat(zzag) : zzdm.zzchm();
    }

    public boolean zzcdu() {
        return true;
    }
}
