package com.google.android.gms.drive.internal;

import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveContents;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.ExecutionOptions;
import com.google.android.gms.drive.ExecutionOptions.Builder;
import com.google.android.gms.drive.MetadataChangeSet;
import com.google.android.gms.drive.internal.zzr.zza;
import com.google.android.gms.internal.zzlg;
import java.io.InputStream;
import java.io.OutputStream;

public class zzt implements DriveContents {
    private boolean mClosed;
    private final Contents zzafa;
    private boolean zzafb;
    private boolean zzafc;

    /* renamed from: com.google.android.gms.drive.internal.zzt.3 */
    class C04183 implements ResultCallback<Status> {
        final /* synthetic */ zzt zzafd;

        C04183(zzt com_google_android_gms_drive_internal_zzt) {
            this.zzafd = com_google_android_gms_drive_internal_zzt;
        }

        public /* synthetic */ void onResult(Result x0) {
            zzm((Status) x0);
        }

        public void zzm(Status status) {
            if (status.isSuccess()) {
                zzx.zzt("DriveContentsImpl", "Contents discarded");
            } else {
                zzx.zzv("DriveContentsImpl", "Error discarding contents");
            }
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzt.1 */
    class C09301 extends zzb {
        final /* synthetic */ zzt zzafd;

        C09301(zzt com_google_android_gms_drive_internal_zzt, GoogleApiClient googleApiClient) {
            this.zzafd = com_google_android_gms_drive_internal_zzt;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new OpenContentsRequest(this.zzafd.getDriveId(), DriveFile.MODE_WRITE_ONLY, this.zzafd.zzafa.getRequestId()), new zzbi(this, null));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzt.2 */
    class C09312 extends zza {
        final /* synthetic */ zzt zzafd;
        final /* synthetic */ MetadataChangeSet zzafe;
        final /* synthetic */ ExecutionOptions zzaff;

        C09312(zzt com_google_android_gms_drive_internal_zzt, GoogleApiClient googleApiClient, MetadataChangeSet metadataChangeSet, ExecutionOptions executionOptions) {
            this.zzafd = com_google_android_gms_drive_internal_zzt;
            this.zzafe = metadataChangeSet;
            this.zzaff = executionOptions;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            this.zzafe.zzpm().setContext(com_google_android_gms_drive_internal_zzs.getContext());
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new CloseContentsAndUpdateMetadataRequest(this.zzafd.zzafa.getDriveId(), this.zzafe.zzpm(), this.zzafd.zzafa.getRequestId(), this.zzafd.zzafa.zzpc(), this.zzaff), new zzbq(this));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzt.4 */
    class C09324 extends zza {
        final /* synthetic */ zzt zzafd;

        C09324(zzt com_google_android_gms_drive_internal_zzt, GoogleApiClient googleApiClient) {
            this.zzafd = com_google_android_gms_drive_internal_zzt;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            com_google_android_gms_drive_internal_zzs.zzpB().zza(new CloseContentsRequest(this.zzafd.zzafa.getRequestId(), false), new zzbq(this));
        }
    }

    public zzt(Contents contents) {
        this.mClosed = false;
        this.zzafb = false;
        this.zzafc = false;
        this.zzafa = (Contents) zzu.zzu(contents);
    }

    public PendingResult<Status> commit(GoogleApiClient apiClient, MetadataChangeSet changeSet) {
        return commit(apiClient, changeSet, null);
    }

    public PendingResult<Status> commit(GoogleApiClient apiClient, MetadataChangeSet changeSet, ExecutionOptions executionOptions) {
        if (executionOptions == null) {
            executionOptions = new Builder().build();
        }
        if (this.zzafa.getMode() == DriveFile.MODE_READ_ONLY) {
            throw new IllegalStateException("Cannot commit contents opened with MODE_READ_ONLY");
        } else if (!ExecutionOptions.zzbX(executionOptions.zzpk()) || this.zzafa.zzpc()) {
            ExecutionOptions.zza(apiClient, executionOptions);
            if (zzpg()) {
                throw new IllegalStateException("DriveContents already closed.");
            } else if (getDriveId() == null) {
                throw new IllegalStateException("Only DriveContents obtained through DriveFile.open can be committed.");
            } else {
                if (changeSet == null) {
                    changeSet = MetadataChangeSet.zzads;
                }
                zzpf();
                return apiClient.zzb(new C09312(this, apiClient, changeSet, executionOptions));
            }
        } else {
            throw new IllegalStateException("DriveContents must be valid for conflict detection.");
        }
    }

    public void discard(GoogleApiClient apiClient) {
        if (zzpg()) {
            throw new IllegalStateException("DriveContents already closed.");
        }
        zzpf();
        ((C09324) apiClient.zzb(new C09324(this, apiClient))).setResultCallback(new C04183(this));
    }

    public DriveId getDriveId() {
        return this.zzafa.getDriveId();
    }

    public InputStream getInputStream() {
        if (zzpg()) {
            throw new IllegalStateException("Contents have been closed, cannot access the input stream.");
        } else if (this.zzafa.getMode() != DriveFile.MODE_READ_ONLY) {
            throw new IllegalStateException("getInputStream() can only be used with contents opened with MODE_READ_ONLY.");
        } else if (this.zzafb) {
            throw new IllegalStateException("getInputStream() can only be called once per Contents instance.");
        } else {
            this.zzafb = true;
            return this.zzafa.getInputStream();
        }
    }

    public int getMode() {
        return this.zzafa.getMode();
    }

    public OutputStream getOutputStream() {
        if (zzpg()) {
            throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
        } else if (this.zzafa.getMode() != DriveFile.MODE_WRITE_ONLY) {
            throw new IllegalStateException("getOutputStream() can only be used with contents opened with MODE_WRITE_ONLY.");
        } else if (this.zzafc) {
            throw new IllegalStateException("getOutputStream() can only be called once per Contents instance.");
        } else {
            this.zzafc = true;
            return this.zzafa.getOutputStream();
        }
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        if (!zzpg()) {
            return this.zzafa.getParcelFileDescriptor();
        }
        throw new IllegalStateException("Contents have been closed, cannot access the output stream.");
    }

    public PendingResult<DriveContentsResult> reopenForWrite(GoogleApiClient apiClient) {
        if (zzpg()) {
            throw new IllegalStateException("DriveContents already closed.");
        } else if (this.zzafa.getMode() != DriveFile.MODE_READ_ONLY) {
            throw new IllegalStateException("reopenForWrite can only be used with DriveContents opened with MODE_READ_ONLY.");
        } else {
            zzpf();
            return apiClient.zza(new C09301(this, apiClient));
        }
    }

    public Contents zzpe() {
        return this.zzafa;
    }

    public void zzpf() {
        zzlg.zza(this.zzafa.getParcelFileDescriptor());
        this.mClosed = true;
    }

    public boolean zzpg() {
        return this.mClosed;
    }
}
