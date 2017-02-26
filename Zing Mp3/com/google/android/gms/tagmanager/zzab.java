package com.google.android.gms.tagmanager;

import android.content.Context;
import android.provider.Settings.Secure;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzab extends zzam {
    private static final String ID;
    private final Context mContext;

    static {
        ID = zzag.DEVICE_ID.toString();
    }

    public zzab(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public zza zzay(Map<String, zza> map) {
        String zzdy = zzdy(this.mContext);
        return zzdy == null ? zzdm.zzchm() : zzdm.zzat(zzdy);
    }

    public boolean zzcdu() {
        return true;
    }

    protected String zzdy(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }
}
