package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityApi.AddLocalCapabilityResult;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;
import com.google.android.gms.wearable.CapabilityApi.RemoveLocalCapabilityResult;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.Node;
import java.util.Map;
import java.util.Set;

public class zzg implements CapabilityApi {

    public static class zzc implements CapabilityInfo {
        private final String mName;
        private final Set<Node> zzaTE;

        public zzc(CapabilityInfo capabilityInfo) {
            this(capabilityInfo.getName(), capabilityInfo.getNodes());
        }

        public zzc(String str, Set<Node> set) {
            this.mName = str;
            this.zzaTE = set;
        }

        public String getName() {
            return this.mName;
        }

        public Set<Node> getNodes() {
            return this.zzaTE;
        }
    }

    public static class zzb implements AddLocalCapabilityResult, RemoveLocalCapabilityResult {
        private final Status zzOt;

        public zzb(Status status) {
            this.zzOt = status;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    public static class zzd implements GetAllCapabilitiesResult {
        private final Status zzOt;
        private final Map<String, CapabilityInfo> zzaTF;

        public zzd(Status status, Map<String, CapabilityInfo> map) {
            this.zzOt = status;
            this.zzaTF = map;
        }

        public Map<String, CapabilityInfo> getAllCapabilities() {
            return this.zzaTF;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    public static class zze implements GetCapabilityResult {
        private final Status zzOt;
        private final CapabilityInfo zzaTG;

        public zze(Status status, CapabilityInfo capabilityInfo) {
            this.zzOt = status;
            this.zzaTG = capabilityInfo;
        }

        public CapabilityInfo getCapability() {
            return this.zzaTG;
        }

        public Status getStatus() {
            return this.zzOt;
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzg.1 */
    class C08841 extends zzf<GetCapabilityResult> {
        final /* synthetic */ int zzaTA;
        final /* synthetic */ zzg zzaTB;
        final /* synthetic */ String zzaTz;

        C08841(zzg com_google_android_gms_wearable_internal_zzg, GoogleApiClient googleApiClient, String str, int i) {
            this.zzaTB = com_google_android_gms_wearable_internal_zzg;
            this.zzaTz = str;
            this.zzaTA = i;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzaW(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzg(this, this.zzaTz, this.zzaTA);
        }

        protected GetCapabilityResult zzaW(Status status) {
            return new zze(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzg.2 */
    class C08852 extends zzf<GetAllCapabilitiesResult> {
        final /* synthetic */ int zzaTA;
        final /* synthetic */ zzg zzaTB;

        C08852(zzg com_google_android_gms_wearable_internal_zzg, GoogleApiClient googleApiClient, int i) {
            this.zzaTB = com_google_android_gms_wearable_internal_zzg;
            this.zzaTA = i;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzaX(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzd(this, this.zzaTA);
        }

        protected GetAllCapabilitiesResult zzaX(Status status) {
            return new zzd(status, null);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzg.3 */
    class C08863 extends zzf<AddLocalCapabilityResult> {
        final /* synthetic */ zzg zzaTB;
        final /* synthetic */ String zzaTz;

        C08863(zzg com_google_android_gms_wearable_internal_zzg, GoogleApiClient googleApiClient, String str) {
            this.zzaTB = com_google_android_gms_wearable_internal_zzg;
            this.zzaTz = str;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzaY(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzr(this, this.zzaTz);
        }

        protected AddLocalCapabilityResult zzaY(Status status) {
            return new zzb(status);
        }
    }

    /* renamed from: com.google.android.gms.wearable.internal.zzg.4 */
    class C08874 extends zzf<RemoveLocalCapabilityResult> {
        final /* synthetic */ zzg zzaTB;
        final /* synthetic */ String zzaTz;

        C08874(zzg com_google_android_gms_wearable_internal_zzg, GoogleApiClient googleApiClient, String str) {
            this.zzaTB = com_google_android_gms_wearable_internal_zzg;
            this.zzaTz = str;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzaZ(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzs(this, this.zzaTz);
        }

        protected RemoveLocalCapabilityResult zzaZ(Status status) {
            return new zzb(status);
        }
    }

    private static final class zza extends zzf<Status> {
        private CapabilityListener zzaTC;
        private String zzaTD;

        private zza(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, String str) {
            super(googleApiClient);
            this.zzaTC = capabilityListener;
            this.zzaTD = str;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zza((com.google.android.gms.common.api.zza.zzb) this, this.zzaTC, this.zzaTD);
            this.zzaTC = null;
            this.zzaTD = null;
        }

        public Status zzb(Status status) {
            this.zzaTC = null;
            this.zzaTD = null;
            return status;
        }
    }

    private static final class zzf extends zzf<Status> {
        private CapabilityListener zzaTC;
        private String zzaTD;

        private zzf(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, String str) {
            super(googleApiClient);
            this.zzaTC = capabilityListener;
            this.zzaTD = str;
        }

        public /* synthetic */ Result createFailedResult(Status x0) {
            return zzb(x0);
        }

        protected void zza(zzbk com_google_android_gms_wearable_internal_zzbk) throws RemoteException {
            com_google_android_gms_wearable_internal_zzbk.zzb((com.google.android.gms.common.api.zza.zzb) this, this.zzaTC, this.zzaTD);
            this.zzaTC = null;
            this.zzaTD = null;
        }

        public Status zzb(Status status) {
            this.zzaTC = null;
            this.zzaTD = null;
            return status;
        }
    }

    public PendingResult<Status> addCapabilityListener(GoogleApiClient client, CapabilityListener listener, String capability) {
        return client.zza(new zza(listener, capability, null));
    }

    public PendingResult<AddLocalCapabilityResult> addLocalCapability(GoogleApiClient client, String capability) {
        return client.zza(new C08863(this, client, capability));
    }

    public PendingResult<GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient client, int filter) {
        return client.zza(new C08852(this, client, filter));
    }

    public PendingResult<GetCapabilityResult> getCapability(GoogleApiClient client, String capability, int filter) {
        return client.zza(new C08841(this, client, capability, filter));
    }

    public PendingResult<Status> removeCapabilityListener(GoogleApiClient client, CapabilityListener listener, String capability) {
        return client.zza(new zzf(listener, capability, null));
    }

    public PendingResult<RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient client, String capability) {
        return client.zza(new C08874(this, client, capability));
    }
}
