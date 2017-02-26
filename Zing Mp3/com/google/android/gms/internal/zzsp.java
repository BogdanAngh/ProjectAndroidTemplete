package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.internal.zzqo.zzb;

public final class zzsp implements zzso {

    /* renamed from: com.google.android.gms.internal.zzsp.1 */
    class C15041 extends zza {
        final /* synthetic */ zzsp EV;

        C15041(zzsp com_google_android_gms_internal_zzsp, GoogleApiClient googleApiClient) {
            this.EV = com_google_android_gms_internal_zzsp;
            super(googleApiClient);
        }

        protected void zza(zzsr com_google_android_gms_internal_zzsr) throws RemoteException {
            ((zzst) com_google_android_gms_internal_zzsr.zzavg()).zza(new zza(this));
        }
    }

    private static class zza extends zzsm {
        private final zzb<Status> EW;

        public zza(zzb<Status> com_google_android_gms_internal_zzqo_zzb_com_google_android_gms_common_api_Status) {
            this.EW = com_google_android_gms_internal_zzqo_zzb_com_google_android_gms_common_api_Status;
        }

        public void zzgv(int i) throws RemoteException {
            this.EW.setResult(new Status(i));
        }
    }

    public PendingResult<Status> zzg(GoogleApiClient googleApiClient) {
        return googleApiClient.zzb(new C15041(this, googleApiClient));
    }
}
