package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseInputStream;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.wearable.Asset;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataApi.DataItemResult;
import com.google.android.gms.wearable.DataApi.DataListener;
import com.google.android.gms.wearable.DataApi.DeleteDataItemsResult;
import com.google.android.gms.wearable.DataApi.GetFdForAssetResult;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataItemAsset;
import com.google.android.gms.wearable.DataItemBuffer;
import com.google.android.gms.wearable.PutDataRequest;
import java.io.IOException;
import java.io.InputStream;

public final class zzu implements DataApi {

    public static class zzb implements DataItemResult {
        private final Status zzOt;
        private final DataItem zzaUl;

        public zzb(Status status, DataItem dataItem) {
            this.zzOt = status;
            this.zzaUl = dataItem;
        }

        public DataItem getDataItem() {
            return this.zzaUl;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    public static class zzc implements DeleteDataItemsResult {
        private final Status zzOt;
        private final int zzaUm;

        public zzc(Status status, int i) {
            this.zzOt = status;
            this.zzaUm = i;
        }

        public int getNumDeleted() {
            return this.zzaUm;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    public static class zzd implements GetFdForAssetResult {
        private volatile boolean mClosed;
        private final Status zzOt;
        private volatile InputStream zzaTV;
        private volatile ParcelFileDescriptor zzaUn;

        public zzd(Status status, ParcelFileDescriptor parcelFileDescriptor) {
            this.mClosed = false;
            this.zzOt = status;
            this.zzaUn = parcelFileDescriptor;
        }

        public ParcelFileDescriptor getFd() {
            if (!this.mClosed) {
                return this.zzaUn;
            }
            throw new IllegalStateException("Cannot access the file descriptor after release().");
        }

        public InputStream getInputStream() {
            if (this.mClosed) {
                throw new IllegalStateException("Cannot access the input stream after release().");
            } else if (this.zzaUn == null) {
                return null;
            } else {
                if (this.zzaTV == null) {
                    this.zzaTV = new AutoCloseInputStream(this.zzaUn);
                }
                return this.zzaTV;
            }
        }

        public Status getStatus() {
            return this.zzOt;
        }

        public void release() {
            if (this.zzaUn != null) {
                if (this.mClosed) {
                    throw new IllegalStateException("releasing an already released result.");
                }
                try {
                    if (this.zzaTV != null) {
                        this.zzaTV.close();
                    } else {
                        this.zzaUn.close();
                    }
                    this.mClosed = true;
                    this.zzaUn = null;
                    this.zzaTV = null;
                } catch (IOException e) {
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.1 */
    class C08891 extends zzf<DataItemResult> {
        final /* synthetic */ PutDataRequest zzaUd;
        final /* synthetic */ zzu zzaUe;

        C08891(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, PutDataRequest putDataRequest) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaUd = putDataRequest;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzbd(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUd);
        }

        public DataItemResult zzbd(Status status) {
            return new zzb(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.2 */
    class C08902 extends zzf<DataItemResult> {
        final /* synthetic */ Uri zzaGx;
        final /* synthetic */ zzu zzaUe;

        C08902(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, Uri uri) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaGx = uri;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbd(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaGx);
        }

        protected DataItemResult zzbd(Status status) {
            return new zzb(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.3 */
    class C08913 extends zzf<DataItemBuffer> {
        final /* synthetic */ zzu zzaUe;

        C08913(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbe(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzl(this);
        }

        protected DataItemBuffer zzbe(Status status) {
            return new DataItemBuffer(DataHolder.zzbi(status.getStatusCode()));
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.4 */
    class C08924 extends zzf<DataItemBuffer> {
        final /* synthetic */ Uri zzaGx;
        final /* synthetic */ zzu zzaUe;
        final /* synthetic */ int zzaUf;

        C08924(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, Uri uri, int i) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaGx = uri;
            this.zzaUf = i;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbe(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaGx, this.zzaUf);
        }

        protected DataItemBuffer zzbe(Status status) {
            return new DataItemBuffer(DataHolder.zzbi(status.getStatusCode()));
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.5 */
    class C08935 extends zzf<DeleteDataItemsResult> {
        final /* synthetic */ Uri zzaGx;
        final /* synthetic */ zzu zzaUe;
        final /* synthetic */ int zzaUf;

        C08935(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, Uri uri, int i) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaGx = uri;
            this.zzaUf = i;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbf(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzb((com.google.android.gms.common.api.zza.zzb) this, this.zzaGx, this.zzaUf);
        }

        protected DeleteDataItemsResult zzbf(Status status) {
            return new zzc(status, 0);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.6 */
    class C08946 extends zzf<GetFdForAssetResult> {
        final /* synthetic */ zzu zzaUe;
        final /* synthetic */ Asset zzaUg;

        C08946(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, Asset asset) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaUg = asset;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbg(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUg);
        }

        protected GetFdForAssetResult zzbg(Status status) {
            return new zzd(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.7 */
    class C08957 extends zzf<GetFdForAssetResult> {
        final /* synthetic */ zzu zzaUe;
        final /* synthetic */ DataItemAsset zzaUh;

        C08957(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, DataItemAsset dataItemAsset) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaUh = dataItemAsset;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzbg(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUh);
        }

        protected GetFdForAssetResult zzbg(Status status) {
            return new zzd(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzu.8 */
    class C08968 extends zzf<Status> {
        final /* synthetic */ zzu zzaUe;
        final /* synthetic */ DataListener zzaUi;

        C08968(zzu com_google_android_gms_wearable_internal_zzu, GoogleApiClient googleApiClient, DataListener dataListener) {
            this.zzaUe = com_google_android_gms_wearable_internal_zzu;
            this.zzaUi = dataListener;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUi);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    private static final class zza extends zzf<Status> {
        private DataListener zzaUj;
        private IntentFilter[] zzaUk;

        private zza(GoogleApiClient googleApiClient, DataListener dataListener, IntentFilter[] intentFilterArr) {
            super(googleApiClient);
            this.zzaUj = dataListener;
            this.zzaUk = intentFilterArr;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaUj, this.zzaUk);
            this.zzaUj = null;
            this.zzaUk = null;
        }

        public Status zzb(Status status) {
            this.zzaUj = null;
            this.zzaUk = null;
            return status;
        }
    }

    private PendingResult<Status> zza(GoogleApiClient googleApiClient, DataListener dataListener, IntentFilter[] intentFilterArr) {
        return googleApiClient.zza(new zza(dataListener, intentFilterArr, null));
    }

    private void zza(Asset asset) {
        if (asset == null) {
            throw new IllegalArgumentException("asset is null");
        } else if (asset.getDigest() == null) {
            throw new IllegalArgumentException("invalid asset");
        } else if (asset.getData() != null) {
            throw new IllegalArgumentException("invalid asset");
        }
    }

    public PendingResult<Status> addListener(GoogleApiClient client, DataListener listener) {
        return zza(client, listener, null);
    }

    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient client, Uri uri) {
        return deleteDataItems(client, uri, 0);
    }

    public PendingResult<DeleteDataItemsResult> deleteDataItems(GoogleApiClient client, Uri uri, int filterType) {
        return client.zza(new C08935(this, client, uri, filterType));
    }

    public PendingResult<DataItemResult> getDataItem(GoogleApiClient client, Uri uri) {
        return client.zza(new C08902(this, client, uri));
    }

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient client) {
        return client.zza(new C08913(this, client));
    }

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient client, Uri uri) {
        return getDataItems(client, uri, 0);
    }

    public PendingResult<DataItemBuffer> getDataItems(GoogleApiClient client, Uri uri, int filterType) {
        return client.zza(new C08924(this, client, uri, filterType));
    }

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient client, Asset asset) {
        zza(asset);
        return client.zza(new C08946(this, client, asset));
    }

    public PendingResult<GetFdForAssetResult> getFdForAsset(GoogleApiClient client, DataItemAsset asset) {
        return client.zza(new C08957(this, client, asset));
    }

    public PendingResult<DataItemResult> putDataItem(GoogleApiClient client, PutDataRequest request) {
        return client.zza(new C08891(this, client, request));
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, DataListener listener) {
        return client.zza(new C08968(this, client, listener));
    }
}
