package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzaw extends zzak {
    private static final String ID;
    private static final String zzaKp;
    private final Context zzpH;

    static {
        ID = zzad.INSTALL_REFERRER.toString();
        zzaKp = zzae.COMPONENT.toString();
    }

    public zzaw(Context context) {
        super(ID, new String[0]);
        this.zzpH = context;
    }

    public zza zzE(Map<String, zza> map) {
        String zzj = zzax.zzj(this.zzpH, ((zza) map.get(zzaKp)) != null ? zzdf.zzg((zza) map.get(zzaKp)) : null);
        return zzj != null ? zzdf.zzI(zzj) : zzdf.zzzQ();
    }

    public boolean zzyh() {
        return true;
    }
}
