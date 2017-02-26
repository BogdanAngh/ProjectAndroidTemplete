package com.google.android.gms.tagmanager;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzaj.zza;
import java.util.Map;

class zzco extends zzam {
    private static final String ID;
    private final Context mContext;

    static {
        ID = zzag.RESOLUTION.toString();
    }

    public zzco(Context context) {
        super(ID, new String[0]);
        this.mContext = context;
    }

    public zza zzay(Map<String, zza> map) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) this.mContext.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        int i = displayMetrics.widthPixels;
        return zzdm.zzat(i + "x" + displayMetrics.heightPixels);
    }

    public boolean zzcdu() {
        return true;
    }
}
