package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.location.Location;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class zzd implements FusedLocationProviderApi {

    private static abstract class zza extends com.google.android.gms.location.LocationServices.zza<Status> {
        public zza(GoogleApiClient googleApiClient) {
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.1 */
    class C10621 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ LocationRequest zzayy;
        final /* synthetic */ LocationListener zzayz;

        C10621(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayy = locationRequest;
            this.zzayz = locationListener;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayy, this.zzayz, null);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.2 */
    class C10632 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ boolean zzayB;

        C10632(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, boolean z) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayB = z;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zzac(this.zzayB);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.3 */
    class C10643 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ Location zzayC;

        C10643(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, Location location) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayC = location;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zzb(this.zzayC);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.4 */
    class C10654 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ Looper zzayD;
        final /* synthetic */ LocationRequest zzayy;
        final /* synthetic */ LocationListener zzayz;

        C10654(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationListener locationListener, Looper looper) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayy = locationRequest;
            this.zzayz = locationListener;
            this.zzayD = looper;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayy, this.zzayz, this.zzayD);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.5 */
    class C10665 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ Looper zzayD;
        final /* synthetic */ LocationCallback zzayE;
        final /* synthetic */ LocationRequest zzayy;

        C10665(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationRequest locationRequest, LocationCallback locationCallback, Looper looper) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayy = locationRequest;
            this.zzayE = locationCallback;
            this.zzayD = looper;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(LocationRequestInternal.zzb(this.zzayy), this.zzayE, this.zzayD);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.6 */
    class C10676 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ PendingIntent zzayu;
        final /* synthetic */ LocationRequest zzayy;

        C10676(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationRequest locationRequest, PendingIntent pendingIntent) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayy = locationRequest;
            this.zzayu = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zzb(this.zzayy, this.zzayu);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.7 */
    class C10687 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ LocationListener zzayz;

        C10687(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationListener locationListener) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayz = locationListener;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayz);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.8 */
    class C10698 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ PendingIntent zzayu;

        C10698(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayu = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zzd(this.zzayu);
            setResult(Status.zzXP);
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zzd.9 */
    class C10709 extends zza {
        final /* synthetic */ zzd zzayA;
        final /* synthetic */ LocationCallback zzayE;

        C10709(zzd com_google_android_gms_location_internal_zzd, GoogleApiClient googleApiClient, LocationCallback locationCallback) {
            this.zzayA = com_google_android_gms_location_internal_zzd;
            this.zzayE = locationCallback;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayE);
            setResult(Status.zzXP);
        }
    }

    public Location getLastLocation(GoogleApiClient client) {
        try {
            return LocationServices.zze(client).getLastLocation();
        } catch (Exception e) {
            return null;
        }
    }

    public LocationAvailability getLocationAvailability(GoogleApiClient client) {
        try {
            return LocationServices.zze(client).zzuw();
        } catch (Exception e) {
            return null;
        }
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient client, PendingIntent callbackIntent) {
        return client.zzb(new C10698(this, client, callbackIntent));
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient client, LocationCallback callback) {
        return client.zzb(new C10709(this, client, callback));
    }

    public PendingResult<Status> removeLocationUpdates(GoogleApiClient client, LocationListener listener) {
        return client.zzb(new C10687(this, client, listener));
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient client, LocationRequest request, PendingIntent callbackIntent) {
        return client.zzb(new C10676(this, client, request, callbackIntent));
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient client, LocationRequest request, LocationCallback callback, Looper looper) {
        return client.zzb(new C10665(this, client, request, callback, looper));
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient client, LocationRequest request, LocationListener listener) {
        return client.zzb(new C10621(this, client, request, listener));
    }

    public PendingResult<Status> requestLocationUpdates(GoogleApiClient client, LocationRequest request, LocationListener listener, Looper looper) {
        return client.zzb(new C10654(this, client, request, listener, looper));
    }

    public PendingResult<Status> setMockLocation(GoogleApiClient client, Location mockLocation) {
        return client.zzb(new C10643(this, client, mockLocation));
    }

    public PendingResult<Status> setMockMode(GoogleApiClient client, boolean isMockMode) {
        return client.zzb(new C10632(this, client, isMockMode));
    }
}
