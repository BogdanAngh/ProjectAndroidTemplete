package com.google.android.gms.location.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.location.LocationServices.zza;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.SettingsApi;

public class zzo implements SettingsApi {

    /* renamed from: com.google.android.gms.location.internal.zzo.1 */
    class C08721 extends zza<LocationSettingsResult> {
        final /* synthetic */ LocationSettingsRequest zzazk;
        final /* synthetic */ String zzazl;
        final /* synthetic */ zzo zzazm;

        C08721(zzo com_google_android_gms_location_internal_zzo, GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
            this.zzazm = com_google_android_gms_location_internal_zzo;
            this.zzazk = locationSettingsRequest;
            this.zzazl = str;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzaE(x0);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzazk, (zzb) this, this.zzazl);
        }

        public LocationSettingsResult zzaE(Status status) {
            return new LocationSettingsResult(status);
        }
    }

    public PendingResult<LocationSettingsResult> checkLocationSettings(GoogleApiClient client, LocationSettingsRequest request) {
        return zza(client, request, null);
    }

    public PendingResult<LocationSettingsResult> zza(GoogleApiClient googleApiClient, LocationSettingsRequest locationSettingsRequest, String str) {
        return googleApiClient.zza(new C08721(this, googleApiClient, locationSettingsRequest, str));
    }
}
