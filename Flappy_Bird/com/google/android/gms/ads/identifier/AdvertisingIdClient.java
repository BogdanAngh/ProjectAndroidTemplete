package com.google.android.gms.ads.identifier;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.stats.zzb;
import com.google.android.gms.internal.zzav;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdvertisingIdClient {
    private final Context mContext;
    com.google.android.gms.common.zza zznX;
    zzav zznY;
    boolean zznZ;
    Object zzoa;
    zza zzob;
    final long zzoc;

    public static final class Info {
        private final String zzoh;
        private final boolean zzoi;

        public Info(String advertisingId, boolean limitAdTrackingEnabled) {
            this.zzoh = advertisingId;
            this.zzoi = limitAdTrackingEnabled;
        }

        public String getId() {
            return this.zzoh;
        }

        public boolean isLimitAdTrackingEnabled() {
            return this.zzoi;
        }

        public String toString() {
            return "{" + this.zzoh + "}" + this.zzoi;
        }
    }

    static class zza extends Thread {
        private WeakReference<AdvertisingIdClient> zzod;
        private long zzoe;
        CountDownLatch zzof;
        boolean zzog;

        public zza(AdvertisingIdClient advertisingIdClient, long j) {
            this.zzod = new WeakReference(advertisingIdClient);
            this.zzoe = j;
            this.zzof = new CountDownLatch(1);
            this.zzog = false;
            start();
        }

        private void disconnect() {
            AdvertisingIdClient advertisingIdClient = (AdvertisingIdClient) this.zzod.get();
            if (advertisingIdClient != null) {
                advertisingIdClient.finish();
                this.zzog = true;
            }
        }

        public void cancel() {
            this.zzof.countDown();
        }

        public void run() {
            try {
                if (!this.zzof.await(this.zzoe, TimeUnit.MILLISECONDS)) {
                    disconnect();
                }
            } catch (InterruptedException e) {
                disconnect();
            }
        }

        public boolean zzaK() {
            return this.zzog;
        }
    }

    public AdvertisingIdClient(Context context) {
        this(context, 30000);
    }

    public AdvertisingIdClient(Context context, long timeoutInMillis) {
        this.zzoa = new Object();
        zzu.zzu(context);
        this.mContext = context;
        this.zznZ = false;
        this.zzoc = timeoutInMillis;
    }

    public static Info getAdvertisingIdInfo(Context context) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(context, -1);
        try {
            advertisingIdClient.zzb(false);
            Info info = advertisingIdClient.getInfo();
            return info;
        } finally {
            advertisingIdClient.finish();
        }
    }

    static zzav zza(Context context, com.google.android.gms.common.zza com_google_android_gms_common_zza) throws IOException {
        try {
            return com.google.android.gms.internal.zzav.zza.zzb(com_google_android_gms_common_zza.zzmh());
        } catch (InterruptedException e) {
            throw new IOException("Interrupted exception");
        }
    }

    private void zzaJ() {
        synchronized (this.zzoa) {
            if (this.zzob != null) {
                this.zzob.cancel();
                try {
                    this.zzob.join();
                } catch (InterruptedException e) {
                }
            }
            if (this.zzoc > 0) {
                this.zzob = new zza(this, this.zzoc);
            }
        }
    }

    static com.google.android.gms.common.zza zzo(Context context) throws IOException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        try {
            context.getPackageManager().getPackageInfo(GooglePlayServicesUtil.GOOGLE_PLAY_STORE_PACKAGE, 0);
            try {
                GooglePlayServicesUtil.zzY(context);
                ServiceConnection com_google_android_gms_common_zza = new com.google.android.gms.common.zza();
                Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
                intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
                if (zzb.zzoO().zza(context, intent, com_google_android_gms_common_zza, 1)) {
                    return com_google_android_gms_common_zza;
                }
                throw new IOException("Connection failure");
            } catch (Throwable e) {
                throw new IOException(e);
            }
        } catch (NameNotFoundException e2) {
            throw new GooglePlayServicesNotAvailableException(9);
        }
    }

    protected void finalize() throws Throwable {
        finish();
        super.finalize();
    }

    public void finish() {
        zzu.zzbZ("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.mContext == null || this.zznX == null) {
                return;
            }
            try {
                if (this.zznZ) {
                    zzb.zzoO().zza(this.mContext, this.zznX);
                }
            } catch (Throwable e) {
                Log.i("AdvertisingIdClient", "AdvertisingIdClient unbindService failed.", e);
            }
            this.zznZ = false;
            this.zznY = null;
            this.zznX = null;
        }
    }

    public Info getInfo() throws IOException {
        Info info;
        zzu.zzbZ("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (!this.zznZ) {
                synchronized (this.zzoa) {
                    if (this.zzob == null || !this.zzob.zzaK()) {
                        throw new IOException("AdvertisingIdClient is not connected.");
                    }
                }
                try {
                    zzb(false);
                    if (!this.zznZ) {
                        throw new IOException("AdvertisingIdClient cannot reconnect.");
                    }
                } catch (Throwable e) {
                    Log.i("AdvertisingIdClient", "GMS remote exception ", e);
                    throw new IOException("Remote exception");
                } catch (Throwable e2) {
                    throw new IOException("AdvertisingIdClient cannot reconnect.", e2);
                }
            }
            zzu.zzu(this.zznX);
            zzu.zzu(this.zznY);
            info = new Info(this.zznY.getId(), this.zznY.zzc(true));
        }
        zzaJ();
        return info;
    }

    public void start() throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzb(true);
    }

    protected void zzb(boolean z) throws IOException, IllegalStateException, GooglePlayServicesNotAvailableException, GooglePlayServicesRepairableException {
        zzu.zzbZ("Calling this from your main thread can lead to deadlock");
        synchronized (this) {
            if (this.zznZ) {
                finish();
            }
            this.zznX = zzo(this.mContext);
            this.zznY = zza(this.mContext, this.zznX);
            this.zznZ = true;
            if (z) {
                zzaJ();
            }
        }
    }
}
