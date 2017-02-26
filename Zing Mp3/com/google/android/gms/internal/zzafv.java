package com.google.android.gms.internal;

import android.content.Context;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.util.HashMap;
import java.util.Map;

public class zzafv {
    private String aEC;
    Map<String, Object> aLk;
    private final Map<String, Object> aLl;
    private final zzafx aMw;
    private final Context mContext;
    private final zze zzaql;

    public zzafv(Context context) {
        this(context, new HashMap(), new zzafx(context), zzh.zzayl());
    }

    zzafv(Context context, Map<String, Object> map, zzafx com_google_android_gms_internal_zzafx, zze com_google_android_gms_common_util_zze) {
        this.aEC = null;
        this.aLk = new HashMap();
        this.mContext = context;
        this.zzaql = com_google_android_gms_common_util_zze;
        this.aMw = com_google_android_gms_internal_zzafx;
        this.aLl = map;
    }

    public void zzqy(String str) {
        this.aEC = str;
    }
}
