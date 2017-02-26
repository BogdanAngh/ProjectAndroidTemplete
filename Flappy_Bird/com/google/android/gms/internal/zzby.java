package com.google.android.gms.internal;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.GooglePlayServicesUtil;

@zzgd
public class zzby {
    private boolean zzpb;
    private final Object zzqt;
    private SharedPreferences zztB;

    public zzby() {
        this.zzqt = new Object();
        this.zzpb = false;
        this.zztB = null;
    }

    public <T> T zzc(zzbv<T> com_google_android_gms_internal_zzbv_T) {
        synchronized (this.zzqt) {
            if (this.zzpb) {
                return com_google_android_gms_internal_zzbv_T.zza(this.zztB);
            }
            T zzcY = com_google_android_gms_internal_zzbv_T.zzcY();
            return zzcY;
        }
    }

    public void zzw(Context context) {
        synchronized (this.zzqt) {
            if (this.zzpb) {
                return;
            }
            Context remoteContext = GooglePlayServicesUtil.getRemoteContext(context);
            if (remoteContext == null) {
                return;
            }
            this.zztB = zzo.zzbC().zzv(remoteContext);
            this.zzpb = true;
        }
    }
}
