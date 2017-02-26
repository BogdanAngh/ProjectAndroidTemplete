package com.google.android.gms.location.places.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Api.ClientKey;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.location.places.AddPlaceRequest;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.AutocompletePredictionBuffer;
import com.google.android.gms.location.places.GeoDataApi;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.zzm;
import com.google.android.gms.location.places.zzm.zza;
import com.google.android.gms.maps.model.LatLngBounds;
import java.util.Arrays;

public class zzc implements GeoDataApi {

    /* renamed from: com.google.android.gms.location.places.internal.zzc.1 */
    class C10741 extends com.google.android.gms.location.places.zzm.zzc<zzd> {
        final /* synthetic */ AddPlaceRequest zzaAg;
        final /* synthetic */ zzc zzaAh;

        C10741(zzc com_google_android_gms_location_places_internal_zzc, ClientKey clientKey, GoogleApiClient googleApiClient, AddPlaceRequest addPlaceRequest) {
            this.zzaAh = com_google_android_gms_location_places_internal_zzc;
            this.zzaAg = addPlaceRequest;
            super(clientKey, googleApiClient);
        }

        protected void zza(zzd com_google_android_gms_location_places_internal_zzd) throws RemoteException {
            com_google_android_gms_location_places_internal_zzd.zza(new zzm((com.google.android.gms.location.places.zzm.zzc) this, com_google_android_gms_location_places_internal_zzd.getContext()), this.zzaAg);
        }
    }

    /* renamed from: com.google.android.gms.location.places.internal.zzc.2 */
    class C10752 extends com.google.android.gms.location.places.zzm.zzc<zzd> {
        final /* synthetic */ zzc zzaAh;
        final /* synthetic */ String[] zzaAi;

        C10752(zzc com_google_android_gms_location_places_internal_zzc, ClientKey clientKey, GoogleApiClient googleApiClient, String[] strArr) {
            this.zzaAh = com_google_android_gms_location_places_internal_zzc;
            this.zzaAi = strArr;
            super(clientKey, googleApiClient);
        }

        protected void zza(zzd com_google_android_gms_location_places_internal_zzd) throws RemoteException {
            com_google_android_gms_location_places_internal_zzd.zza(new zzm((com.google.android.gms.location.places.zzm.zzc) this, com_google_android_gms_location_places_internal_zzd.getContext()), Arrays.asList(this.zzaAi));
        }
    }

    /* renamed from: com.google.android.gms.location.places.internal.zzc.3 */
    class C10763 extends zza<zzd> {
        final /* synthetic */ zzc zzaAh;
        final /* synthetic */ LatLngBounds zzaAj;
        final /* synthetic */ AutocompleteFilter zzaAk;
        final /* synthetic */ String zzaqR;

        C10763(zzc com_google_android_gms_location_places_internal_zzc, ClientKey clientKey, GoogleApiClient googleApiClient, String str, LatLngBounds latLngBounds, AutocompleteFilter autocompleteFilter) {
            this.zzaAh = com_google_android_gms_location_places_internal_zzc;
            this.zzaqR = str;
            this.zzaAj = latLngBounds;
            this.zzaAk = autocompleteFilter;
            super(clientKey, googleApiClient);
        }

        protected void zza(zzd com_google_android_gms_location_places_internal_zzd) throws RemoteException {
            com_google_android_gms_location_places_internal_zzd.zza(new zzm((zza) this), this.zzaqR, this.zzaAj, this.zzaAk);
        }
    }

    public PendingResult<PlaceBuffer> addPlace(GoogleApiClient client, AddPlaceRequest addPlaceRequest) {
        return client.zzb(new C10741(this, Places.zzazQ, client, addPlaceRequest));
    }

    public PendingResult<AutocompletePredictionBuffer> getAutocompletePredictions(GoogleApiClient client, String query, LatLngBounds bounds, AutocompleteFilter filter) {
        return client.zza(new C10763(this, Places.zzazQ, client, query, bounds, filter));
    }

    public PendingResult<PlaceBuffer> getPlaceById(GoogleApiClient client, String... placeIds) {
        boolean z = true;
        if (placeIds == null || placeIds.length < 1) {
            z = false;
        }
        zzu.zzV(z);
        return client.zza(new C10752(this, Places.zzazQ, client, placeIds));
    }
}
