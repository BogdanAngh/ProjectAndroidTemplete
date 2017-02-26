package com.google.android.gms.ads.internal.overlay;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.annotation.Nullable;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmd;

@zzji
public abstract class zzj {
    @Nullable
    public abstract zzi zza(Context context, zzmd com_google_android_gms_internal_zzmd, int i, boolean z, zzdz com_google_android_gms_internal_zzdz);

    protected boolean zzh(zzmd com_google_android_gms_internal_zzmd) {
        return com_google_android_gms_internal_zzmd.zzeg().zzazr;
    }

    protected boolean zzp(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return zzs.zzayq() && (applicationInfo == null || applicationInfo.targetSdkVersion >= 11);
    }
}
