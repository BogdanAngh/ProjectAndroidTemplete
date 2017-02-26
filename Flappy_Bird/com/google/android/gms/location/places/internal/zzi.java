package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.PlaceDetectionApi;
import com.google.android.gms.location.places.PlaceFilter;
import com.google.android.gms.location.places.PlaceLikelihoodBuffer;
import com.google.android.gms.location.places.PlaceReport;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.location.places.zzm.zzd;
import com.google.android.gms.location.places.zzm.zzf;

public class zzi implements PlaceDetectionApi {

    /* renamed from: com.google.android.gms.location.places.internal.zzi.1 */
    class C10771 extends zzd<zzj> {
        final /* synthetic */ PlaceFilter zzaAp;
        final /* synthetic */ zzi zzaAq;

        C10771(zzi com_google_android_gms_location_places_internal_zzi, ClientKey clientKey, GoogleApiClient googleApiClient, PlaceFilter placeFilter) {
            this.zzaAq = com_google_android_gms_location_places_internal_zzi;
            this.zzaAp = placeFilter;
            super(clientKey, googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_places_internal_zzj) throws RemoteException {
            com_google_android_gms_location_places_internal_zzj.zza(new zzm((zzd) this, com_google_android_gms_location_places_internal_zzj.getContext()), this.zzaAp);
        }
    }

    /* renamed from: com.google.android.gms.location.places.internal.zzi.2 */
    class C10782 extends zzf<zzj> {
        final /* synthetic */ zzi zzaAq;
        final /* synthetic */ PlaceReport zzaAr;

        C10782(zzi com_google_android_gms_location_places_internal_zzi, ClientKey clientKey, GoogleApiClient googleApiClient, PlaceReport placeReport) {
            this.zzaAq = com_google_android_gms_location_places_internal_zzi;
            this.zzaAr = placeReport;
            super(clientKey, googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_places_internal_zzj) throws RemoteException {
            com_google_android_gms_location_places_internal_zzj.zza(new zzm((zzf) this), this.zzaAr);
        }
    }

    public PendingResult<PlaceLikelihoodBuffer> getCurrentPlace(GoogleApiClient client, PlaceFilter filter) {
        return client.zza(new C10771(this, Places.zzazR, client, filter));
    }

    public PendingResult<Status> reportDeviceAtPlace(GoogleApiClient client, PlaceReport report) {
        return client.zzb(new C10782(this, Places.zzazR, client, report));
    }
}
