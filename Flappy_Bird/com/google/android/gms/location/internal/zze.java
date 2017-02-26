package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingApi;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.GeofencingRequest.Builder;
import com.google.android.gms.location.LocationStatusCodes;
import com.google.android.gms.location.zze.zzb;
import java.util.List;

public class zze implements GeofencingApi {

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

    /* renamed from: com.google.android.gms.location.internal.zze.1 */
    class C10711 extends zza {
        final /* synthetic */ PendingIntent zzalz;
        final /* synthetic */ GeofencingRequest zzayF;
        final /* synthetic */ zze zzayG;

        /* renamed from: com.google.android.gms.location.internal.zze.1.1 */
        class C05061 implements com.google.android.gms.location.zze.zza {
            final /* synthetic */ C10711 zzayH;

            C05061(C10711 c10711) {
                this.zzayH = c10711;
            }

            public void zza(int i, String[] strArr) {
                this.zzayH.setResult(LocationStatusCodes.zzgB(i));
            }
        }

        C10711(zze com_google_android_gms_location_internal_zze, GoogleApiClient googleApiClient, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
            this.zzayG = com_google_android_gms_location_internal_zze;
            this.zzayF = geofencingRequest;
            this.zzalz = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayF, this.zzalz, new C05061(this));
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zze.2 */
    class C10722 extends zza {
        final /* synthetic */ PendingIntent zzalz;
        final /* synthetic */ zze zzayG;

        /* renamed from: com.google.android.gms.location.internal.zze.2.1 */
        class C05071 implements zzb {
            final /* synthetic */ C10722 zzayI;

            C05071(C10722 c10722) {
                this.zzayI = c10722;
            }

            public void zza(int i, PendingIntent pendingIntent) {
                this.zzayI.setResult(LocationStatusCodes.zzgB(i));
            }

            public void zzb(int i, String[] strArr) {
                Log.wtf("GeofencingImpl", "Request ID callback shouldn't have been called");
            }
        }

        C10722(zze com_google_android_gms_location_internal_zze, GoogleApiClient googleApiClient, PendingIntent pendingIntent) {
            this.zzayG = com_google_android_gms_location_internal_zze;
            this.zzalz = pendingIntent;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzalz, new C05071(this));
        }
    }

    /* renamed from: com.google.android.gms.location.internal.zze.3 */
    class C10733 extends zza {
        final /* synthetic */ zze zzayG;
        final /* synthetic */ List zzayJ;

        /* renamed from: com.google.android.gms.location.internal.zze.3.1 */
        class C05081 implements zzb {
            final /* synthetic */ C10733 zzayK;

            C05081(C10733 c10733) {
                this.zzayK = c10733;
            }

            public void zza(int i, PendingIntent pendingIntent) {
                Log.wtf("GeofencingImpl", "PendingIntent callback shouldn't have been called");
            }

            public void zzb(int i, String[] strArr) {
                this.zzayK.setResult(LocationStatusCodes.zzgB(i));
            }
        }

        C10733(zze com_google_android_gms_location_internal_zze, GoogleApiClient googleApiClient, List list) {
            this.zzayG = com_google_android_gms_location_internal_zze;
            this.zzayJ = list;
            super(googleApiClient);
        }

        protected void zza(zzj com_google_android_gms_location_internal_zzj) throws RemoteException {
            com_google_android_gms_location_internal_zzj.zza(this.zzayJ, new C05081(this));
        }
    }

    public PendingResult<Status> addGeofences(GoogleApiClient client, GeofencingRequest geofencingRequest, PendingIntent pendingIntent) {
        return client.zzb(new C10711(this, client, geofencingRequest, pendingIntent));
    }

    @Deprecated
    public PendingResult<Status> addGeofences(GoogleApiClient client, List<Geofence> geofences, PendingIntent pendingIntent) {
        Builder builder = new Builder();
        builder.addGeofences(geofences);
        builder.setInitialTrigger(5);
        return addGeofences(client, builder.build(), pendingIntent);
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient client, PendingIntent pendingIntent) {
        return client.zzb(new C10722(this, client, pendingIntent));
    }

    public PendingResult<Status> removeGeofences(GoogleApiClient client, List<String> geofenceRequestIds) {
        return client.zzb(new C10733(this, client, geofenceRequestIds));
    }
}
