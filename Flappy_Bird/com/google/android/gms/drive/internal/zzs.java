package com.google.android.gms.drive.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.ChangeListener;
import com.google.android.gms.drive.events.ChangesAvailableOptions;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEventService;
import com.google.android.gms.drive.events.zzc;
import com.google.android.gms.drive.events.zzg;
import com.google.android.gms.drive.internal.zzr.zza;
import com.google.example.games.basegameutils.GameHelper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class zzs extends zzi<zzak> {
    private final String zzOe;
    final ConnectionCallbacks zzaar;
    private final Bundle zzaeM;
    private final boolean zzaeN;
    private DriveId zzaeO;
    private DriveId zzaeP;
    final Map<DriveId, Map<ChangeListener, zzac>> zzaeQ;
    final Map<zzc, zzac> zzaeR;
    final Map<DriveId, Map<com.google.android.gms.drive.events.zzi, zzac>> zzaeS;
    final Map<DriveId, Map<com.google.android.gms.drive.events.zzi, zzac>> zzaeT;

    /* renamed from: com.google.android.gms.drive.internal.zzs.1 */
    class C09251 extends zza {
        final /* synthetic */ DriveId zzaeU;
        final /* synthetic */ int zzaeV;
        final /* synthetic */ ChangesAvailableOptions zzaeW;
        final /* synthetic */ zzac zzaeX;
        final /* synthetic */ zzs zzaeY;

        C09251(zzs com_google_android_gms_drive_internal_zzs, GoogleApiClient googleApiClient, DriveId driveId, int i, ChangesAvailableOptions changesAvailableOptions, zzac com_google_android_gms_drive_internal_zzac) {
            this.zzaeY = com_google_android_gms_drive_internal_zzs;
            this.zzaeU = driveId;
            this.zzaeV = i;
            this.zzaeW = changesAvailableOptions;
            this.zzaeX = com_google_android_gms_drive_internal_zzac;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new AddEventListenerRequest(this.zzaeU, this.zzaeV, this.zzaeW), this.zzaeX, null, new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzs.2 */
    class C09262 extends zza {
        final /* synthetic */ DriveId zzaeU;
        final /* synthetic */ int zzaeV;
        final /* synthetic */ zzac zzaeX;
        final /* synthetic */ zzs zzaeY;

        C09262(zzs com_google_android_gms_drive_internal_zzs, GoogleApiClient googleApiClient, DriveId driveId, int i, zzac com_google_android_gms_drive_internal_zzac) {
            this.zzaeY = com_google_android_gms_drive_internal_zzs;
            this.zzaeU = driveId;
            this.zzaeV = i;
            this.zzaeX = com_google_android_gms_drive_internal_zzac;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new RemoveEventListenerRequest(this.zzaeU, this.zzaeV), this.zzaeX, null, new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzs.3 */
    class C09273 extends zza {
        final /* synthetic */ DriveId zzaeU;
        final /* synthetic */ int zzaeV;
        final /* synthetic */ ChangesAvailableOptions zzaeW;
        final /* synthetic */ zzs zzaeY;

        C09273(zzs com_google_android_gms_drive_internal_zzs, GoogleApiClient googleApiClient, DriveId driveId, int i, ChangesAvailableOptions changesAvailableOptions) {
            this.zzaeY = com_google_android_gms_drive_internal_zzs;
            this.zzaeU = driveId;
            this.zzaeV = i;
            this.zzaeW = changesAvailableOptions;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new AddEventListenerRequest(this.zzaeU, this.zzaeV, this.zzaeW), null, null, new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzs.4 */
    class C09284 extends zza {
        final /* synthetic */ DriveId zzaeU;
        final /* synthetic */ int zzaeV;
        final /* synthetic */ zzs zzaeY;

        C09284(zzs com_google_android_gms_drive_internal_zzs, GoogleApiClient googleApiClient, DriveId driveId, int i) {
            this.zzaeY = com_google_android_gms_drive_internal_zzs;
            this.zzaeU = driveId;
            this.zzaeV = i;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new RemoveEventListenerRequest(this.zzaeU, this.zzaeV), null, null, new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzs.5 */
    class C09295 extends zza {
        final /* synthetic */ zzs zzaeY;
        final /* synthetic */ List zzaeZ;

        C09295(zzs com_google_android_gms_drive_internal_zzs, GoogleApiClient googleApiClient, List list) {
            this.zzaeY = com_google_android_gms_drive_internal_zzs;
            this.zzaeZ = list;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new CancelPendingActionsRequest(this.zzaeZ), new zzbq(this));
        }
    }

    public zzs(Context context, Looper looper, zze com_google_android_gms_common_internal_zze, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener, Bundle bundle) {
        super(context, looper, 11, connectionCallbacks, onConnectionFailedListener, com_google_android_gms_common_internal_zze);
        this.zzaeQ = new HashMap();
        this.zzaeR = new HashMap();
        this.zzaeS = new HashMap();
        this.zzaeT = new HashMap();
        this.zzOe = com_google_android_gms_common_internal_zze.zzny();
        this.zzaar = connectionCallbacks;
        this.zzaeM = bundle;
        Intent intent = new Intent(DriveEventService.ACTION_HANDLE_EVENT);
        intent.setPackage(context.getPackageName());
        List queryIntentServices = context.getPackageManager().queryIntentServices(intent, 0);
        switch (queryIntentServices.size()) {
            case GameHelper.CLIENT_NONE /*0*/:
                this.zzaeN = false;
            case CompletionEvent.STATUS_FAILURE /*1*/:
                ServiceInfo serviceInfo = ((ResolveInfo) queryIntentServices.get(0)).serviceInfo;
                if (serviceInfo.exported) {
                    this.zzaeN = true;
                    return;
                }
                throw new IllegalStateException("Drive event service " + serviceInfo.name + " must be exported in AndroidManifest.xml");
            default:
                throw new IllegalStateException("AndroidManifest.xml can only define one service that handles the " + intent.getAction() + " action");
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, int i, DriveId driveId) {
        zzu.zzb(zzg.zza(i, driveId), (Object) "id");
        zzu.zza(isConnected(), (Object) "Client must be connected");
        return googleApiClient.zzb(new C09284(this, googleApiClient, driveId, i));
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, int i, DriveId driveId, ChangesAvailableOptions changesAvailableOptions) {
        zzu.zzb(zzg.zza(i, driveId), (Object) "id");
        zzu.zza(isConnected(), (Object) "Client must be connected");
        if (this.zzaeN) {
            return googleApiClient.zzb(new C09273(this, googleApiClient, driveId, i, changesAvailableOptions));
        }
        throw new IllegalStateException("Application must define an exported DriveEventService subclass in AndroidManifest.xml to add event subscriptions");
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, int i, DriveId driveId, zzac com_google_android_gms_drive_internal_zzac) {
        return googleApiClient.zzb(new C09262(this, googleApiClient, driveId, i, com_google_android_gms_drive_internal_zzac));
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, int i, DriveId driveId, zzac com_google_android_gms_drive_internal_zzac, ChangesAvailableOptions changesAvailableOptions) {
        return googleApiClient.zzb(new C09251(this, googleApiClient, driveId, i, changesAvailableOptions, com_google_android_gms_drive_internal_zzac));
    }

    PendingResult<Status> cancelPendingActions(GoogleApiClient apiClient, List<String> pendingTags) {
        boolean z = true;
        zzu.zzV(pendingTags != null);
        if (pendingTags.isEmpty()) {
            z = false;
        }
        zzu.zzV(z);
        zzu.zza(isConnected(), (Object) "Client must be connected");
        return apiClient.zzb(new C09295(this, apiClient, pendingTags));
    }

    public void disconnect() {
        if (isConnected()) {
            try {
                ((zzak) zznM()).zza(new DisconnectRequest());
            } catch (RemoteException e) {
            }
        }
        super.disconnect();
        synchronized (this.zzaeQ) {
            this.zzaeQ.clear();
        }
        synchronized (this.zzaeR) {
            this.zzaeR.clear();
        }
        synchronized (this.zzaeS) {
            this.zzaeS.clear();
        }
        synchronized (this.zzaeT) {
            this.zzaeT.clear();
        }
    }

    protected String getServiceDescriptor() {
        return "com.google.android.gms.drive.internal.IDriveService";
    }

    protected String getStartServiceAction() {
        return "com.google.android.gms.drive.ApiService.START";
    }

    public boolean requiresSignIn() {
        return true;
    }

    protected /* synthetic */ IInterface zzT(IBinder iBinder) {
        return zzaN(iBinder);
    }

    PendingResult<Status> zza(GoogleApiClient googleApiClient, DriveId driveId) {
        return zza(googleApiClient, 1, driveId, null);
    }

    PendingResult<Status> zza(GoogleApiClient googleApiClient, DriveId driveId, ChangeListener changeListener) {
        PendingResult<Status> com_google_android_gms_drive_internal_zzq_zzj;
        zzu.zzb(zzg.zza(1, driveId), (Object) "id");
        zzu.zzb((Object) changeListener, (Object) "listener");
        zzu.zza(isConnected(), (Object) "Client must be connected");
        synchronized (this.zzaeQ) {
            Map map;
            zzac com_google_android_gms_drive_internal_zzac;
            Map map2 = (Map) this.zzaeQ.get(driveId);
            if (map2 == null) {
                HashMap hashMap = new HashMap();
                this.zzaeQ.put(driveId, hashMap);
                map = hashMap;
            } else {
                map = map2;
            }
            zzac com_google_android_gms_drive_internal_zzac2 = (zzac) map.get(changeListener);
            if (com_google_android_gms_drive_internal_zzac2 == null) {
                com_google_android_gms_drive_internal_zzac = new zzac(getLooper(), getContext(), 1, changeListener);
                map.put(changeListener, com_google_android_gms_drive_internal_zzac);
            } else if (com_google_android_gms_drive_internal_zzac2.zzcB(1)) {
                com_google_android_gms_drive_internal_zzq_zzj = new zzj(googleApiClient, Status.zzXP);
            } else {
                com_google_android_gms_drive_internal_zzac = com_google_android_gms_drive_internal_zzac2;
            }
            com_google_android_gms_drive_internal_zzac.zzcA(1);
            com_google_android_gms_drive_internal_zzq_zzj = zza(googleApiClient, 1, driveId, com_google_android_gms_drive_internal_zzac, null);
        }
        return com_google_android_gms_drive_internal_zzq_zzj;
    }

    protected void zza(int i, IBinder iBinder, Bundle bundle, int i2) {
        if (bundle != null) {
            bundle.setClassLoader(getClass().getClassLoader());
            this.zzaeO = (DriveId) bundle.getParcelable("com.google.android.gms.drive.root_id");
            this.zzaeP = (DriveId) bundle.getParcelable("com.google.android.gms.drive.appdata_id");
        }
        super.zza(i, iBinder, bundle, i2);
    }

    protected zzak zzaN(IBinder iBinder) {
        return zzak.zza.zzaO(iBinder);
    }

    PendingResult<Status> zzb(GoogleApiClient googleApiClient, DriveId driveId) {
        return zza(googleApiClient, 1, driveId);
    }

    PendingResult<Status> zzb(GoogleApiClient googleApiClient, DriveId driveId, ChangeListener changeListener) {
        PendingResult<Status> com_google_android_gms_drive_internal_zzq_zzj;
        zzu.zzb(zzg.zza(1, driveId), (Object) "id");
        zzu.zza(isConnected(), (Object) "Client must be connected");
        zzu.zzb((Object) changeListener, (Object) "listener");
        synchronized (this.zzaeQ) {
            Map map = (Map) this.zzaeQ.get(driveId);
            if (map == null) {
                com_google_android_gms_drive_internal_zzq_zzj = new zzj(googleApiClient, Status.zzXP);
            } else {
                zzac com_google_android_gms_drive_internal_zzac = (zzac) map.remove(changeListener);
                if (com_google_android_gms_drive_internal_zzac == null) {
                    com_google_android_gms_drive_internal_zzq_zzj = new zzj(googleApiClient, Status.zzXP);
                } else {
                    if (map.isEmpty()) {
                        this.zzaeQ.remove(driveId);
                    }
                    com_google_android_gms_drive_internal_zzq_zzj = zza(googleApiClient, 1, driveId, com_google_android_gms_drive_internal_zzac);
                }
            }
        }
        return com_google_android_gms_drive_internal_zzq_zzj;
    }

    protected Bundle zzkR() {
        String packageName = getContext().getPackageName();
        zzu.zzu(packageName);
        zzu.zzU(!zznK().zznw().isEmpty());
        Bundle bundle = new Bundle();
        if (!packageName.equals(this.zzOe)) {
            bundle.putString("proxy_package_name", this.zzOe);
        }
        bundle.putAll(this.zzaeM);
        return bundle;
    }

    public zzak zzpB() throws DeadObjectException {
        return (zzak) zznM();
    }

    public DriveId zzpC() {
        return this.zzaeO;
    }

    public DriveId zzpD() {
        return this.zzaeP;
    }

    public boolean zzpE() {
        return this.zzaeN;
    }
}
