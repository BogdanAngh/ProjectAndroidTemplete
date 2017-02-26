package com.google.android.gms.drive.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.zzi;
import com.google.android.gms.common.api.zzi.zzb;
import com.google.android.gms.drive.DriveApi.DriveContentsResult;
import com.google.android.gms.drive.DriveFile;
import com.google.android.gms.drive.DriveFile.DownloadProgressListener;
import com.google.android.gms.drive.DriveId;

public class zzu extends zzz implements DriveFile {

    private static class zza implements DownloadProgressListener {
        private final zzi<DownloadProgressListener> zzafi;

        /* renamed from: com.google.android.gms.drive.internal.zzu.zza.1 */
        class C04191 implements zzb<DownloadProgressListener> {
            final /* synthetic */ long zzafj;
            final /* synthetic */ long zzafk;
            final /* synthetic */ zza zzafl;

            C04191(zza com_google_android_gms_drive_internal_zzu_zza, long j, long j2) {
                this.zzafl = com_google_android_gms_drive_internal_zzu_zza;
                this.zzafj = j;
                this.zzafk = j2;
            }

            public void zza(DownloadProgressListener downloadProgressListener) {
                downloadProgressListener.onProgress(this.zzafj, this.zzafk);
            }

            public void zzmw() {
            }

            public /* synthetic */ void zzn(Object obj) {
                zza((DownloadProgressListener) obj);
            }
        }

        public zza(zzi<DownloadProgressListener> com_google_android_gms_common_api_zzi_com_google_android_gms_drive_DriveFile_DownloadProgressListener) {
            this.zzafi = com_google_android_gms_common_api_zzi_com_google_android_gms_drive_DriveFile_DownloadProgressListener;
        }

        public void onProgress(long bytesDownloaded, long bytesExpected) {
            this.zzafi.zza(new C04191(this, bytesDownloaded, bytesExpected));
        }
    }

    /* renamed from: com.google.android.gms.drive.internal.zzu.1 */
    class C09331 extends zzb {
        final /* synthetic */ int zzaeI;
        final /* synthetic */ DownloadProgressListener zzafg;
        final /* synthetic */ zzu zzafh;

        C09331(zzu com_google_android_gms_drive_internal_zzu, GoogleApiClient googleApiClient, int i, DownloadProgressListener downloadProgressListener) {
            this.zzafh = com_google_android_gms_drive_internal_zzu;
            this.zzaeI = i;
            this.zzafg = downloadProgressListener;
            super(googleApiClient);
        }

        protected void zza(zzs com_google_android_gms_drive_internal_zzs) throws RemoteException {
            setCancelToken(com_google_android_gms_drive_internal_zzs.zzpB().zza(new OpenContentsRequest(this.zzafh.getDriveId(), this.zzaeI, 0), new zzbi(this, this.zzafg)).zzpF());
        }
    }

    public zzu(DriveId driveId) {
        super(driveId);
    }

    private static DownloadProgressListener zza(GoogleApiClient googleApiClient, DownloadProgressListener downloadProgressListener) {
        return downloadProgressListener == null ? null : new zza(googleApiClient.zzo(downloadProgressListener));
    }

    public PendingResult<DriveContentsResult> open(GoogleApiClient apiClient, int mode, DownloadProgressListener listener) {
        if (mode == DriveFile.MODE_READ_ONLY || mode == DriveFile.MODE_WRITE_ONLY || mode == DriveFile.MODE_READ_WRITE) {
            return apiClient.zza(new C09331(this, apiClient, mode, zza(apiClient, listener)));
        }
        throw new IllegalArgumentException("Invalid mode provided.");
    }
}
