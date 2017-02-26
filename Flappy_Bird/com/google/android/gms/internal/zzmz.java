package com.google.android.gms.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zza.zzb;
import com.google.android.gms.fitness.BleApi;
import com.google.android.gms.fitness.data.BleDevice;
import com.google.android.gms.fitness.request.BleScanCallback;
import com.google.android.gms.fitness.request.ClaimBleDeviceRequest;
import com.google.android.gms.fitness.request.ListClaimedBleDevicesRequest;
import com.google.android.gms.fitness.request.StartBleScanRequest;
import com.google.android.gms.fitness.request.StopBleScanRequest;
import com.google.android.gms.fitness.request.UnclaimBleDeviceRequest;
import com.google.android.gms.fitness.result.BleDevicesResult;

public class zzmz implements BleApi {

    private static class zza extends com.google.android.gms.internal.zzni.zza {
        private final zzb<BleDevicesResult> zzOs;

        private zza(zzb<BleDevicesResult> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_BleDevicesResult) {
            this.zzOs = com_google_android_gms_common_api_zza_zzb_com_google_android_gms_fitness_result_BleDevicesResult;
        }

        public void zza(BleDevicesResult bleDevicesResult) {
            this.zzOs.zzm(bleDevicesResult);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.6 */
    class C08626 extends zza<BleDevicesResult> {
        final /* synthetic */ zzmz zzakY;

        C08626(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            super(googleApiClient);
        }

        protected /* synthetic */ Result createFailedResult(Status x0) {
            return zzB(x0);
        }

        protected BleDevicesResult zzB(Status status) {
            return BleDevicesResult.zzJ(status);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new ListClaimedBleDevicesRequest(new zza(null), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.1 */
    class C10191 extends zzc {
        final /* synthetic */ StartBleScanRequest zzakX;
        final /* synthetic */ zzmz zzakY;

        C10191(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient, StartBleScanRequest startBleScanRequest) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            this.zzakX = startBleScanRequest;
            super(googleApiClient);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new StartBleScanRequest(this.zzakX, new zzng(this), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.2 */
    class C10202 extends zzc {
        final /* synthetic */ zzmz zzakY;
        final /* synthetic */ BleScanCallback zzakZ;

        C10202(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient, BleScanCallback bleScanCallback) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            this.zzakZ = bleScanCallback;
            super(googleApiClient);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new StopBleScanRequest(this.zzakZ, new zzng(this), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.3 */
    class C10213 extends zzc {
        final /* synthetic */ zzmz zzakY;
        final /* synthetic */ String zzala;

        C10213(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient, String str) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            this.zzala = str;
            super(googleApiClient);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new ClaimBleDeviceRequest(this.zzala, null, new zzng(this), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.4 */
    class C10224 extends zzc {
        final /* synthetic */ zzmz zzakY;
        final /* synthetic */ BleDevice zzalb;

        C10224(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient, BleDevice bleDevice) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            this.zzalb = bleDevice;
            super(googleApiClient);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new ClaimBleDeviceRequest(this.zzalb.getAddress(), this.zzalb, new zzng(this), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmz.5 */
    class C10235 extends zzc {
        final /* synthetic */ zzmz zzakY;
        final /* synthetic */ String zzala;

        C10235(zzmz com_google_android_gms_internal_zzmz, GoogleApiClient googleApiClient, String str) {
            this.zzakY = com_google_android_gms_internal_zzmz;
            this.zzala = str;
            super(googleApiClient);
        }

        protected void zza(zzlx com_google_android_gms_internal_zzlx) throws RemoteException {
            ((zzmi) com_google_android_gms_internal_zzlx.zznM()).zza(new UnclaimBleDeviceRequest(this.zzala, new zzng(this), com_google_android_gms_internal_zzlx.getContext().getPackageName()));
        }
    }

    public PendingResult<Status> claimBleDevice(GoogleApiClient client, BleDevice bleDevice) {
        return client.zzb(new C10224(this, client, bleDevice));
    }

    public PendingResult<Status> claimBleDevice(GoogleApiClient client, String deviceAddress) {
        return client.zzb(new C10213(this, client, deviceAddress));
    }

    public PendingResult<BleDevicesResult> listClaimedBleDevices(GoogleApiClient client) {
        return client.zza(new C08626(this, client));
    }

    public PendingResult<Status> startBleScan(GoogleApiClient client, StartBleScanRequest request) {
        return client.zza(new C10191(this, client, request));
    }

    public PendingResult<Status> stopBleScan(GoogleApiClient client, BleScanCallback requestCallback) {
        return client.zza(new C10202(this, client, requestCallback));
    }

    public PendingResult<Status> unclaimBleDevice(GoogleApiClient client, BleDevice bleDevice) {
        return unclaimBleDevice(client, bleDevice.getAddress());
    }

    public PendingResult<Status> unclaimBleDevice(GoogleApiClient client, String deviceAddress) {
        return client.zzb(new C10235(this, client, deviceAddress));
    }
}
