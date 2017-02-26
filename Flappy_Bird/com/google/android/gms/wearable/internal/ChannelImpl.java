package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ChannelImpl implements SafeParcelable, Channel {
    public static final Creator<ChannelImpl> CREATOR;
    final int zzCY;
    private final String zzaST;
    private final String zzaTK;
    private final String zzaTQ;

    static final class zza implements GetInputStreamResult {
        private final Status zzOt;
        private final InputStream zzaTV;

        zza(Status status, InputStream inputStream) {
            this.zzOt = (Status) zzu.zzu(status);
            this.zzaTV = inputStream;
        }

        public InputStream getInputStream() {
            return this.zzaTV;
        }

        public Status getStatus() {
            return this.zzOt;
        }

        public void release() {
            if (this.zzaTV != null) {
                try {
                    this.zzaTV.close();
                } catch (IOException e) {
                }
            }
        }
    }

    static final class zzb implements GetOutputStreamResult {
        private final Status zzOt;
        private final OutputStream zzaTW;

        zzb(Status status, OutputStream outputStream) {
            this.zzOt = (Status) zzu.zzu(status);
            this.zzaTW = outputStream;
        }

        public OutputStream getOutputStream() {
            return this.zzaTW;
        }

        public Status getStatus() {
            return this.zzOt;
        }

        public void release() {
            if (this.zzaTW != null) {
                try {
                    this.zzaTW.close();
                } catch (IOException e) {
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.1 */
    class C08731 extends zzf<Status> {
        final /* synthetic */ ChannelImpl zzaTR;

        C08731(ChannelImpl channelImpl, GoogleApiClient googleApiClient) {
            this.zzaTR = channelImpl;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzt(this, this.zzaTR.zzaTK);
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.2 */
    class C08742 extends zzf<Status> {
        final /* synthetic */ ChannelImpl zzaTR;
        final /* synthetic */ int zzajs;

        C08742(ChannelImpl channelImpl, GoogleApiClient googleApiClient, int i) {
            this.zzaTR = channelImpl;
            this.zzajs = i;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzh(this, this.zzaTR.zzaTK, this.zzajs);
        }

        protected Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.3 */
    class C08753 extends zzf<GetInputStreamResult> {
        final /* synthetic */ ChannelImpl zzaTR;

        C08753(ChannelImpl channelImpl, GoogleApiClient googleApiClient) {
            this.zzaTR = channelImpl;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzbb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzu(this, this.zzaTR.zzaTK);
        }

        public GetInputStreamResult zzbb(Status status) {
            return new zza(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.4 */
    class C08764 extends zzf<GetOutputStreamResult> {
        final /* synthetic */ ChannelImpl zzaTR;

        C08764(ChannelImpl channelImpl, GoogleApiClient googleApiClient) {
            this.zzaTR = channelImpl;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzbc(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzv(this, this.zzaTR.zzaTK);
        }

        public GetOutputStreamResult zzbc(Status status) {
            return new zzb(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.5 */
    class C08775 extends zzf<Status> {
        final /* synthetic */ Uri zzaGx;
        final /* synthetic */ ChannelImpl zzaTR;
        final /* synthetic */ boolean zzaTS;

        C08775(ChannelImpl channelImpl, GoogleApiClient googleApiClient, Uri uri, boolean z) {
            this.zzaTR = channelImpl;
            this.zzaGx = uri;
            this.zzaTS = z;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaTR.zzaTK, this.zzaGx, this.zzaTS);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.ChannelImpl.6 */
    class C08786 extends zzf<Status> {
        final /* synthetic */ Uri zzaGx;
        final /* synthetic */ ChannelImpl zzaTR;
        final /* synthetic */ long zzaTT;
        final /* synthetic */ long zzaTU;

        C08786(ChannelImpl channelImpl, GoogleApiClient googleApiClient, Uri uri, long j, long j2) {
            this.zzaTR = channelImpl;
            this.zzaGx = uri;
            this.zzaTT = j;
            this.zzaTU = j2;
            super(googleApiClient);
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza(this, this.zzaTR.zzaTK, this.zzaGx, this.zzaTT, this.zzaTU);
        }

        public Status zzb(Status status) {
            return status;
        }
    }

    static {
        CREATOR = new zzl();
    }

    ChannelImpl(int versionCode, String token, String nodeId, String path) {
        this.zzCY = versionCode;
        this.zzaTK = (String) zzu.zzu(token);
        this.zzaST = (String) zzu.zzu(nodeId);
        this.zzaTQ = (String) zzu.zzu(path);
    }

    public PendingResult<Status> addListener(GoogleApiClient client, ChannelListener listener) {
        zzu.zzb((Object) client, (Object) "client is null");
        zzu.zzb((Object) listener, (Object) "listener is null");
        return client.zza(new zza(client, listener, this.zzaTK));
    }

    public PendingResult<Status> close(GoogleApiClient client) {
        return client.zzb(new C08731(this, client));
    }

    public PendingResult<Status> close(GoogleApiClient client, int errorCode) {
        return client.zzb(new C08742(this, client, errorCode));
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ChannelImpl)) {
            return false;
        }
        ChannelImpl channelImpl = (ChannelImpl) other;
        return this.zzaTK.equals(channelImpl.zzaTK) && zzt.equal(channelImpl.zzaST, this.zzaST) && zzt.equal(channelImpl.zzaTQ, this.zzaTQ) && channelImpl.zzCY == this.zzCY;
    }

    public PendingResult<GetInputStreamResult> getInputStream(GoogleApiClient client) {
        return client.zzb(new C08753(this, client));
    }

    public String getNodeId() {
        return this.zzaST;
    }

    public PendingResult<GetOutputStreamResult> getOutputStream(GoogleApiClient client) {
        return client.zzb(new C08764(this, client));
    }

    public String getPath() {
        return this.zzaTQ;
    }

    public String getToken() {
        return this.zzaTK;
    }

    public int hashCode() {
        return this.zzaTK.hashCode();
    }

    public PendingResult<Status> receiveFile(GoogleApiClient client, Uri uri, boolean append) {
        zzu.zzb((Object) client, (Object) "client is null");
        zzu.zzb((Object) uri, (Object) "uri is null");
        return client.zzb(new C08775(this, client, uri, append));
    }

    public PendingResult<Status> removeListener(GoogleApiClient client, ChannelListener listener) {
        zzu.zzb((Object) client, (Object) "client is null");
        zzu.zzb((Object) listener, (Object) "listener is null");
        return client.zza(new zzc(client, listener, this.zzaTK));
    }

    public PendingResult<Status> sendFile(GoogleApiClient client, Uri uri) {
        return sendFile(client, uri, 0, -1);
    }

    public PendingResult<Status> sendFile(GoogleApiClient client, Uri uri, long startOffset, long length) {
        zzu.zzb((Object) client, (Object) "client is null");
        zzu.zzb(this.zzaTK, (Object) "token is null");
        zzu.zzb((Object) uri, (Object) "uri is null");
        zzu.zzb(startOffset >= 0, "startOffset is negative: %s", Long.valueOf(startOffset));
        boolean z = length >= 0 || length == -1;
        zzu.zzb(z, "invalid length: %s", Long.valueOf(length));
        return client.zzb(new C08786(this, client, uri, startOffset, length));
    }

    public String toString() {
        return "ChannelImpl{versionCode=" + this.zzCY + ", token='" + this.zzaTK + '\'' + ", nodeId='" + this.zzaST + '\'' + ", path='" + this.zzaTQ + '\'' + "}";
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzl.zza(this, dest, flags);
    }
}
