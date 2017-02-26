package com.google.android.gms.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.internal.zzf;
import com.google.android.gms.common.internal.zzj;
import com.google.android.gms.internal.zzqg.zza;

public class zzqd extends zzj<zzqg> {
    public zzqd(Context context, Looper looper, zzf com_google_android_gms_common_internal_zzf, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
        super(context, looper, 40, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
    }

    public void zza(zzqf com_google_android_gms_internal_zzqf, LogEventParcelable logEventParcelable) throws RemoteException {
        ((zzqg) zzavg()).zza(com_google_android_gms_internal_zzqf, logEventParcelable);
    }

    protected zzqg zzdm(IBinder iBinder) {
        return zza.zzdo(iBinder);
    }

    protected /* synthetic */ IInterface zzh(IBinder iBinder) {
        return zzdm(iBinder);
    }

    protected String zzjx() {
        return "com.google.android.gms.clearcut.service.START";
    }

    protected String zzjy() {
        return "com.google.android.gms.clearcut.internal.IClearcutLoggerService";
    }
}
