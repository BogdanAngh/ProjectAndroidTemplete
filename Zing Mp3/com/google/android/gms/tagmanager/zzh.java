package com.google.android.gms.tagmanager;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzh extends zzam {
    private static final String ID;
    private final Context mContext;

    static {
        ID = zzag.APP_VERSION.toString();
    }

    public zzh(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public zza zzay(Map<String, zza> map) {
        try {
            return zzdm.zzat(Integer.valueOf(this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), 0).versionCode));
        } catch (NameNotFoundException e) {
            String valueOf = String.valueOf(this.mContext.getPackageName());
            String valueOf2 = String.valueOf(e.getMessage());
            zzbo.m1698e(new StringBuilder((String.valueOf(valueOf).length() + 25) + String.valueOf(valueOf2).length()).append("Package name ").append(valueOf).append(" not found. ").append(valueOf2).toString());
            return zzdm.zzchm();
        }
    }

    public boolean zzcdu() {
        return true;
    }
}
