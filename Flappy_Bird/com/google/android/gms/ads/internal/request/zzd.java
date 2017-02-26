package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.internal.zzbr;
import com.google.android.gms.internal.zzbz;
import com.google.android.gms.internal.zzgd;
import com.google.android.gms.internal.zzge;
import com.google.android.gms.internal.zzgf;
import com.google.android.gms.internal.zzhh;

@zzgd
public abstract class zzd extends zzhh implements com.google.android.gms.ads.internal.request.zzc.zza {
    private AdResponseParcel zzBt;
    private final com.google.android.gms.ads.internal.request.zzc.zza zzCi;
    private final Object zzqt;
    private final AdRequestInfoParcel zzxm;

    @zzgd
    public static final class zza extends zzd {
        private final Context mContext;

        public zza(Context context, AdRequestInfoParcel adRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
            super(adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
            this.mContext = context;
        }

        public void zzfv() {
        }

        public zzi zzfw() {
            return zzgf.zza(this.mContext, new zzbr((String) zzbz.zztD.get()), zzge.zzfC());
        }
    }

    @zzgd
    public static class zzb extends zzd implements ConnectionCallbacks, OnConnectionFailedListener {
        private Context mContext;
        private final com.google.android.gms.ads.internal.request.zzc.zza zzCi;
        protected zze zzCj;
        private final Object zzqt;
        private AdRequestInfoParcel zzxm;

        public zzb(Context context, AdRequestInfoParcel adRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
            super(adRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
            this.zzqt = new Object();
            this.mContext = context;
            this.zzxm = adRequestInfoParcel;
            this.zzCi = com_google_android_gms_ads_internal_request_zzc_zza;
            this.zzCj = new zze(context, this, this, adRequestInfoParcel.zzpJ.zzGI);
            connect();
        }

        protected void connect() {
            this.zzCj.zznJ();
        }

        public void onConnected(Bundle connectionHint) {
            zzgi();
        }

        public void onConnectionFailed(ConnectionResult result) {
            com.google.android.gms.ads.internal.util.client.zzb.zzay("Cannot connect to remote service, fallback to local instance.");
            zzfx().zzgi();
            Bundle bundle = new Bundle();
            bundle.putString("action", "gms_connection_failed_fallback_to_local");
            zzo.zzbv().zza(this.mContext, this.zzxm.zzpJ.zzGG, "gmob-apps", bundle, true);
        }

        public void onConnectionSuspended(int cause) {
            com.google.android.gms.ads.internal.util.client.zzb.zzay("Disconnected from remote ad request service.");
        }

        public void zzfv() {
            synchronized (this.zzqt) {
                if (this.zzCj.isConnected() || this.zzCj.isConnecting()) {
                    this.zzCj.disconnect();
                }
                Binder.flushPendingCommands();
            }
        }

        public zzi zzfw() {
            zzi zzfy;
            synchronized (this.zzqt) {
                try {
                    zzfy = this.zzCj.zzfy();
                } catch (IllegalStateException e) {
                    zzfy = null;
                    return zzfy;
                } catch (DeadObjectException e2) {
                    zzfy = null;
                    return zzfy;
                }
            }
            return zzfy;
        }

        zzhh zzfx() {
            return new zza(this.mContext, this.zzxm, this.zzCi);
        }
    }

    public zzd(AdRequestInfoParcel adRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
        this.zzqt = new Object();
        this.zzxm = adRequestInfoParcel;
        this.zzCi = com_google_android_gms_ads_internal_request_zzc_zza;
    }

    public final void onStop() {
        zzfv();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    boolean zza(com.google.android.gms.ads.internal.request.zzi r5, com.google.android.gms.ads.internal.request.AdRequestInfoParcel r6) {
        /*
        r4 = this;
        r1 = 0;
        r0 = 1;
        r2 = new com.google.android.gms.ads.internal.request.zzg;	 Catch:{ RemoteException -> 0x000b, NullPointerException -> 0x0024, SecurityException -> 0x0032, Throwable -> 0x0040 }
        r2.<init>(r4);	 Catch:{ RemoteException -> 0x000b, NullPointerException -> 0x0024, SecurityException -> 0x0032, Throwable -> 0x0040 }
        r5.zza(r6, r2);	 Catch:{ RemoteException -> 0x000b, NullPointerException -> 0x0024, SecurityException -> 0x0032, Throwable -> 0x0040 }
    L_0x000a:
        return r0;
    L_0x000b:
        r2 = move-exception;
        r3 = "Could not fetch ad response from ad request service.";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r3, r2);
        r3 = com.google.android.gms.ads.internal.zzo.zzby();
        r3.zzc(r2, r0);
    L_0x0018:
        r0 = r4.zzCi;
        r2 = new com.google.android.gms.ads.internal.request.AdResponseParcel;
        r2.<init>(r1);
        r0.zzb(r2);
        r0 = r1;
        goto L_0x000a;
    L_0x0024:
        r2 = move-exception;
        r3 = "Could not fetch ad response from ad request service due to an Exception.";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r3, r2);
        r3 = com.google.android.gms.ads.internal.zzo.zzby();
        r3.zzc(r2, r0);
        goto L_0x0018;
    L_0x0032:
        r2 = move-exception;
        r3 = "Could not fetch ad response from ad request service due to an Exception.";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r3, r2);
        r3 = com.google.android.gms.ads.internal.zzo.zzby();
        r3.zzc(r2, r0);
        goto L_0x0018;
    L_0x0040:
        r2 = move-exception;
        r3 = "Could not fetch ad response from ad request service due to an Exception.";
        com.google.android.gms.ads.internal.util.client.zzb.zzd(r3, r2);
        r3 = com.google.android.gms.ads.internal.zzo.zzby();
        r3.zzc(r2, r0);
        goto L_0x0018;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.request.zzd.zza(com.google.android.gms.ads.internal.request.zzi, com.google.android.gms.ads.internal.request.AdRequestInfoParcel):boolean");
    }

    public void zzb(AdResponseParcel adResponseParcel) {
        synchronized (this.zzqt) {
            this.zzBt = adResponseParcel;
            this.zzqt.notify();
        }
    }

    public void zzdP() {
        try {
            zzi zzfw = zzfw();
            if (zzfw == null) {
                this.zzCi.zzb(new AdResponseParcel(0));
            } else if (zza(zzfw, this.zzxm)) {
                zzi(zzo.zzbz().elapsedRealtime());
            }
            zzfv();
        } catch (Throwable th) {
            zzfv();
        }
    }

    protected boolean zze(long j) {
        long elapsedRealtime = 60000 - (zzo.zzbz().elapsedRealtime() - j);
        if (elapsedRealtime <= 0) {
            return false;
        }
        try {
            this.zzqt.wait(elapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public abstract void zzfv();

    public abstract zzi zzfw();

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected void zzi(long r6) {
        /*
        r5 = this;
        r1 = r5.zzqt;
        monitor-enter(r1);
    L_0x0003:
        r0 = r5.zzBt;	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0010;
    L_0x0007:
        r0 = r5.zzCi;	 Catch:{ all -> 0x0023 }
        r2 = r5.zzBt;	 Catch:{ all -> 0x0023 }
        r0.zzb(r2);	 Catch:{ all -> 0x0023 }
        monitor-exit(r1);	 Catch:{ all -> 0x0023 }
    L_0x000f:
        return;
    L_0x0010:
        r0 = r5.zze(r6);	 Catch:{ all -> 0x0023 }
        if (r0 != 0) goto L_0x0003;
    L_0x0016:
        r0 = r5.zzBt;	 Catch:{ all -> 0x0023 }
        if (r0 == 0) goto L_0x0026;
    L_0x001a:
        r0 = r5.zzCi;	 Catch:{ all -> 0x0023 }
        r2 = r5.zzBt;	 Catch:{ all -> 0x0023 }
        r0.zzb(r2);	 Catch:{ all -> 0x0023 }
    L_0x0021:
        monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        goto L_0x000f;
    L_0x0023:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0023 }
        throw r0;
    L_0x0026:
        r0 = r5.zzCi;	 Catch:{ all -> 0x0023 }
        r2 = new com.google.android.gms.ads.internal.request.AdResponseParcel;	 Catch:{ all -> 0x0023 }
        r3 = 0;
        r2.<init>(r3);	 Catch:{ all -> 0x0023 }
        r0.zzb(r2);	 Catch:{ all -> 0x0023 }
        goto L_0x0021;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.ads.internal.request.zzd.zzi(long):void");
    }
}
