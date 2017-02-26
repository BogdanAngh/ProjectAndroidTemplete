package com.google.android.gms.ads.internal.request;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.internal.zzdk;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzjk;
import com.google.android.gms.internal.zzjl;
import com.google.android.gms.internal.zzld;
import com.google.android.gms.internal.zzlw;
import com.google.android.gms.internal.zzlw.zzc;

@zzji
public abstract class zzd implements com.google.android.gms.ads.internal.request.zzc.zza, zzld<Void> {
    private final Object zzako;
    private final zzlw<AdRequestInfoParcel> zzcjm;
    private final com.google.android.gms.ads.internal.request.zzc.zza zzcjn;

    /* renamed from: com.google.android.gms.ads.internal.request.zzd.1 */
    class C11001 implements zzc<AdRequestInfoParcel> {
        final /* synthetic */ zzk zzcjo;
        final /* synthetic */ zzd zzcjp;

        C11001(zzd com_google_android_gms_ads_internal_request_zzd, zzk com_google_android_gms_ads_internal_request_zzk) {
            this.zzcjp = com_google_android_gms_ads_internal_request_zzd;
            this.zzcjo = com_google_android_gms_ads_internal_request_zzk;
        }

        public void zzc(AdRequestInfoParcel adRequestInfoParcel) {
            if (!this.zzcjp.zza(this.zzcjo, adRequestInfoParcel)) {
                this.zzcjp.zztb();
            }
        }

        public /* synthetic */ void zzd(Object obj) {
            zzc((AdRequestInfoParcel) obj);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.request.zzd.2 */
    class C11012 implements com.google.android.gms.internal.zzlw.zza {
        final /* synthetic */ zzd zzcjp;

        C11012(zzd com_google_android_gms_ads_internal_request_zzd) {
            this.zzcjp = com_google_android_gms_ads_internal_request_zzd;
        }

        public void run() {
            this.zzcjp.zztb();
        }
    }

    @zzji
    public static final class zza extends zzd {
        private final Context mContext;

        public zza(Context context, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
            super(com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
            this.mContext = context;
        }

        public /* synthetic */ Object zzrz() {
            return super.zzrw();
        }

        public void zztb() {
        }

        public zzk zztc() {
            return zzjl.zza(this.mContext, new zzdk((String) zzdr.zzbcx.get()), zzjk.zzti());
        }
    }

    @zzji
    public static class zzb extends zzd implements com.google.android.gms.common.internal.zze.zzb, zze.zzc {
        private Context mContext;
        private final Object zzako;
        private VersionInfoParcel zzanu;
        private zzlw<AdRequestInfoParcel> zzcjm;
        private final com.google.android.gms.ads.internal.request.zzc.zza zzcjn;
        protected zze zzcjq;
        private boolean zzcjr;

        public zzb(Context context, VersionInfoParcel versionInfoParcel, zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
            Looper zzwj;
            super(com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com_google_android_gms_ads_internal_request_zzc_zza);
            this.zzako = new Object();
            this.mContext = context;
            this.zzanu = versionInfoParcel;
            this.zzcjm = com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel;
            this.zzcjn = com_google_android_gms_ads_internal_request_zzc_zza;
            if (((Boolean) zzdr.zzbek.get()).booleanValue()) {
                this.zzcjr = true;
                zzwj = zzu.zzhc().zzwj();
            } else {
                zzwj = context.getMainLooper();
            }
            this.zzcjq = new zze(context, zzwj, this, this, this.zzanu.zzcyb);
            connect();
        }

        protected void connect() {
            this.zzcjq.zzavd();
        }

        public void onConnected(Bundle bundle) {
            Void voidR = (Void) zzrz();
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Cannot connect to remote service, fallback to local instance.");
            zztd().zzrz();
            Bundle bundle = new Bundle();
            bundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "gms_connection_failed_fallback_to_local");
            zzu.zzgm().zzb(this.mContext, this.zzanu.zzda, "gmob-apps", bundle, true);
        }

        public void onConnectionSuspended(int i) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Disconnected from remote ad request service.");
        }

        public /* synthetic */ Object zzrz() {
            return super.zzrw();
        }

        public void zztb() {
            synchronized (this.zzako) {
                if (this.zzcjq.isConnected() || this.zzcjq.isConnecting()) {
                    this.zzcjq.disconnect();
                }
                Binder.flushPendingCommands();
                if (this.zzcjr) {
                    zzu.zzhc().zzwk();
                    this.zzcjr = false;
                }
            }
        }

        public zzk zztc() {
            zzk zzte;
            synchronized (this.zzako) {
                try {
                    zzte = this.zzcjq.zzte();
                } catch (IllegalStateException e) {
                    zzte = null;
                    return zzte;
                } catch (DeadObjectException e2) {
                    zzte = null;
                    return zzte;
                }
            }
            return zzte;
        }

        zzld zztd() {
            return new zza(this.mContext, this.zzcjm, this.zzcjn);
        }
    }

    public zzd(zzlw<AdRequestInfoParcel> com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel, com.google.android.gms.ads.internal.request.zzc.zza com_google_android_gms_ads_internal_request_zzc_zza) {
        this.zzako = new Object();
        this.zzcjm = com_google_android_gms_internal_zzlw_com_google_android_gms_ads_internal_request_AdRequestInfoParcel;
        this.zzcjn = com_google_android_gms_ads_internal_request_zzc_zza;
    }

    public void cancel() {
        zztb();
    }

    boolean zza(zzk com_google_android_gms_ads_internal_request_zzk, AdRequestInfoParcel adRequestInfoParcel) {
        try {
            com_google_android_gms_ads_internal_request_zzk.zza(adRequestInfoParcel, new zzg(this));
            return true;
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not fetch ad response from ad request service.", e);
            zzu.zzgq().zza(e, "AdRequestClientTask.getAdResponseFromService");
            this.zzcjn.zzb(new AdResponseParcel(0));
            return false;
        } catch (Throwable e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not fetch ad response from ad request service due to an Exception.", e2);
            zzu.zzgq().zza(e2, "AdRequestClientTask.getAdResponseFromService");
            this.zzcjn.zzb(new AdResponseParcel(0));
            return false;
        } catch (Throwable e22) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not fetch ad response from ad request service due to an Exception.", e22);
            zzu.zzgq().zza(e22, "AdRequestClientTask.getAdResponseFromService");
            this.zzcjn.zzb(new AdResponseParcel(0));
            return false;
        } catch (Throwable e222) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Could not fetch ad response from ad request service due to an Exception.", e222);
            zzu.zzgq().zza(e222, "AdRequestClientTask.getAdResponseFromService");
            this.zzcjn.zzb(new AdResponseParcel(0));
            return false;
        }
    }

    public void zzb(AdResponseParcel adResponseParcel) {
        synchronized (this.zzako) {
            this.zzcjn.zzb(adResponseParcel);
            zztb();
        }
    }

    public Void zzrw() {
        zzk zztc = zztc();
        if (zztc == null) {
            this.zzcjn.zzb(new AdResponseParcel(0));
            zztb();
        } else {
            this.zzcjm.zza(new C11001(this, zztc), new C11012(this));
        }
        return null;
    }

    public /* synthetic */ Object zzrz() {
        return zzrw();
    }

    public abstract void zztb();

    public abstract zzk zztc();
}
