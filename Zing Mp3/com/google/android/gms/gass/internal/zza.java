package com.google.android.gms.gass.internal;

import android.content.Context;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.HandlerThread;
import com.google.android.exoplayer.upstream.UdpDataSource;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zze.zzb;
import com.google.android.gms.common.internal.zze.zzc;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class zza {

    static class zza implements zzb, zzc {
        protected zzb agD;
        private final String agE;
        private final LinkedBlockingQueue<com.google.android.gms.internal.zzaf.zza> agF;
        private final HandlerThread agG;
        private final String packageName;

        public zza(Context context, String str, String str2) {
            this.packageName = str;
            this.agE = str2;
            this.agG = new HandlerThread("GassClient");
            this.agG.start();
            this.agD = new zzb(context, this.agG.getLooper(), this, this);
            this.agF = new LinkedBlockingQueue();
            connect();
        }

        protected void connect() {
            this.agD.zzavd();
        }

        public void onConnected(Bundle bundle) {
            zze zzbnl = zzbnl();
            if (zzbnl != null) {
                try {
                    this.agF.put(zzbnl.zza(new GassRequestParcel(this.packageName, this.agE)).zzbno());
                } catch (Throwable th) {
                } finally {
                    zztb();
                    this.agG.quit();
                }
            }
        }

        public void onConnectionFailed(ConnectionResult connectionResult) {
            try {
                this.agF.put(new com.google.android.gms.internal.zzaf.zza());
            } catch (InterruptedException e) {
            }
        }

        public void onConnectionSuspended(int i) {
            try {
                this.agF.put(new com.google.android.gms.internal.zzaf.zza());
            } catch (InterruptedException e) {
            }
        }

        protected zze zzbnl() {
            try {
                return this.agD.zzbnm();
            } catch (IllegalStateException e) {
                return null;
            } catch (DeadObjectException e2) {
                return null;
            }
        }

        public com.google.android.gms.internal.zzaf.zza zzcv() {
            return zzti(UdpDataSource.DEFAULT_MAX_PACKET_SIZE);
        }

        public void zztb() {
            if (this.agD == null) {
                return;
            }
            if (this.agD.isConnected() || this.agD.isConnecting()) {
                this.agD.disconnect();
            }
        }

        public com.google.android.gms.internal.zzaf.zza zzti(int i) {
            com.google.android.gms.internal.zzaf.zza com_google_android_gms_internal_zzaf_zza;
            try {
                com_google_android_gms_internal_zzaf_zza = (com.google.android.gms.internal.zzaf.zza) this.agF.poll((long) i, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                com_google_android_gms_internal_zzaf_zza = null;
            }
            return com_google_android_gms_internal_zzaf_zza == null ? new com.google.android.gms.internal.zzaf.zza() : com_google_android_gms_internal_zzaf_zza;
        }
    }

    public static com.google.android.gms.internal.zzaf.zza zzi(Context context, String str, String str2) {
        return new zza(context, str, str2).zzcv();
    }
}
