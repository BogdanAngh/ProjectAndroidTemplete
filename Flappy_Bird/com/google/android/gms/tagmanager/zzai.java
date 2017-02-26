package com.google.android.gms.tagmanager;

import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzag.zza;
import java.util.Map;

class zzai extends zzak {
    private static final String ID;
    private final zzcp zzaKA;

    static {
        ID = zzad.EVENT.toString();
    }

    public zzai(zzcp com_google_android_gms_tagmanager_zzcp) {
        super(ID, new String[0]);
        this.zzaKA = com_google_android_gms_tagmanager_zzcp;
    }

    public zza zzE(Map<String, zza> map) {
        String zzzp = this.zzaKA.zzzp();
        return zzzp == null ? zzdf.zzzQ() : zzdf.zzI(zzzp);
    }

    public boolean zzyh() {
        return false;
    }
}
