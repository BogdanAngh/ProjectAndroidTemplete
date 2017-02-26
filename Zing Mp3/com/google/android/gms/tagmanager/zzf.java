package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzf extends zzam {
    private static final String ID;
    private final Context mContext;

    static {
        ID = zzag.APP_ID.toString();
    }

    public zzf(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public zza zzay(Map<String, zza> map) {
        return zzdm.zzat(this.mContext.getPackageName());
    }

    public boolean zzcdu() {
        return true;
    }
}
