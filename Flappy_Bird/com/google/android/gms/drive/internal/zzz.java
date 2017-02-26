package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.drive.DriveApi.MetadataBufferResult;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveResource;
import com.google.android.gms.drive.DriveResource.MetadataResult;
import com.google.android.gms.drive.Metadata;
import com.google.android.gms.drive.MetadataBuffer;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.events.ChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class zzz implements DriveResource {
    protected final DriveId zzacT;

    private static class zzc implements MetadataResult {
        private final Status zzOt;
        private final Metadata zzafA;

        public zzc(Status status, Metadata metadata) {
            this.zzOt = status;
            this.zzafA = metadata;
        }

        public Metadata getMetadata() {
            return this.zzafA;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    private static class zza extends zzd {
        private final com.google.android.gms.common.api.zza.zzb<MetadataBufferResult> zzOs;

        public zza(com.google.android.gms.common.api.zza.zzb<MetadataBufferResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_drive_DriveApi_MetadataBufferResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_drive_DriveApi_MetadataBufferResult;
        }

        public void zza(OnListParentsResponse onListParentsResponse) throws RemoteException {
            this.zzOs.zzm(new zzf(Status.zzXP, new MetadataBuffer(onListParentsResponse.zzpR()), false));
        }

        public void zzt(Status status) throws RemoteException {
            this.zzOs.zzm(new zzf(status, null, false));
        }
    }

    private static class zzb extends zzd {
        private final com.google.android.gms.common.api.zza.zzb<MetadataResult> zzOs;

        public zzb(com.google.android.gms.common.api.zza.zzb<MetadataResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_drive_DriveResource_MetadataResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_drive_DriveResource_MetadataResult;
        }

        public void zza(OnMetadataResponse onMetadataResponse) throws RemoteException {
            this.zzOs.zzm(new zzc(Status.zzXP, new zzn(onMetadataResponse.zzpS())));
        }

        public void zzt(Status status) throws RemoteException {
            this.zzOs.zzm(new zzc(status, null));
        }
    }

    private abstract class zzd extends zzr<MetadataResult> {
        final /* synthetic */ zzz zzafy;

        private zzd(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzA(x0);
        }

        public MetadataResult zzA(Status status) {
            return new zzc(status, null);
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.1 */
    class C09381 extends zzd {
        final /* synthetic */ boolean zzafx;
        final /* synthetic */ zzz zzafy;

        C09381(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient, boolean z) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            this.zzafx = z;
            super(googleApiClient, null);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new GetMetadataRequest(this.zzafy.zzacT, this.zzafx), new zzb(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.2 */
    class C09392 extends zzg {
        final /* synthetic */ zzz zzafy;

        C09392(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new ListParentsRequest(this.zzafy.zzacT), new zza(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.3 */
    class C09403 extends com.google.android.gms.drive.internal.zzr.zza {
        final /* synthetic */ zzz zzafy;
        final /* synthetic */ List zzafz;

        C09403(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient, List list) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            this.zzafz = list;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new SetResourceParentsRequest(this.zzafy.zzacT, this.zzafz), new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.4 */
    class C09414 extends zzd {
        final /* synthetic */ MetadataChangeSet zzafm;
        final /* synthetic */ zzz zzafy;

        C09414(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            this.zzafm = metadataChangeSet;
            super(googleApiClient, null);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            this.zzafm.zzpm().setContext(com_google_android_gms_drive_internal_zzs.getContext());
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new UpdateMetadataRequest(this.zzafy.zzacT, this.zzafm.zzpm()), new zzb(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.5 */
    class C09425 extends com.google.android.gms.drive.internal.zzr.zza {
        final /* synthetic */ zzz zzafy;

        C09425(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new DeleteResourceRequest(this.zzafy.zzacT), new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.6 */
    class C09436 extends com.google.android.gms.drive.internal.zzr.zza {
        final /* synthetic */ zzz zzafy;

        C09436(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new TrashResourceRequest(this.zzafy.zzacT), new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzz.7 */
    class C09447 extends com.google.android.gms.drive.internal.zzr.zza {
        final /* synthetic */ zzz zzafy;

        C09447(zzz com_google_android_gms_drive_internal_zzz, GoogleApiClient googleApiClient) {
            this.zzafy = com_google_android_gms_drive_internal_zzz;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new UntrashResourceRequest(this.zzafy.zzacT), new zzbq(this));
        }
    }

    protected zzz(DriveId driveId) {
        this.zzacT = driveId;
    }

    private PendingResult<MetadataResult> zza(GoogleApiClient googleApiClient, boolean z) {
        return googleApiClient.zza(new C09381(this, googleApiClient, z));
    }

    public PendingResult<Status> addChangeListener(GoogleApiClient apiClient, ChangeListener listener) {
        return ((zzs) apiClient.zza(Drive.zzNX)).zza(apiClient, this.zzacT, listener);
    }

    public PendingResult<Status> addChangeSubscription(GoogleApiClient apiClient) {
        return ((zzs) apiClient.zza(Drive.zzNX)).zza(apiClient, this.zzacT);
    }

    public PendingResult<Status> delete(GoogleApiClient apiClient) {
        return apiClient.zzb(new C09425(this, apiClient));
    }

    public DriveId getDriveId() {
        return this.zzacT;
    }

    public PendingResult<MetadataResult> getMetadata(GoogleApiClient apiClient) {
        return zza(apiClient, false);
    }

    public PendingResult<MetadataBufferResult> listParents(GoogleApiClient apiClient) {
        return apiClient.zza(new C09392(this, apiClient));
    }

    public PendingResult<Status> removeChangeListener(GoogleApiClient apiClient, ChangeListener listener) {
        return ((zzs) apiClient.zza(Drive.zzNX)).zzb(apiClient, this.zzacT, listener);
    }

    public PendingResult<Status> removeChangeSubscription(GoogleApiClient apiClient) {
        return ((zzs) apiClient.zza(Drive.zzNX)).zzb(apiClient, this.zzacT);
    }

    public PendingResult<Status> setParents(GoogleApiClient apiClient, Set<DriveId> parentIds) {
        if (parentIds == null) {
            throw new IllegalArgumentException("ParentIds must be provided.");
        } else if (!parentIds.isEmpty()) {
            return apiClient.zzb(new C09403(this, apiClient, new ArrayList(parentIds)));
        } else {
            throw new IllegalArgumentException("ParentIds must contain at least one parent.");
        }
    }

    public PendingResult<Status> trash(GoogleApiClient apiClient) {
        return apiClient.zzb(new C09436(this, apiClient));
    }

    public PendingResult<Status> untrash(GoogleApiClient apiClient) {
        return apiClient.zzb(new C09447(this, apiClient));
    }

    public PendingResult<MetadataResult> updateMetadata(GoogleApiClient apiClient, MetadataChangeSet changeSet) {
        if (changeSet != null) {
            return apiClient.zzb(new C09414(this, apiClient, changeSet));
        }
        throw new IllegalArgumentException("ChangeSet must be provided.");
    }
}
