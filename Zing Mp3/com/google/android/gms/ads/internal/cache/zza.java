package com.google.android.gms.ads.internal.cache;

import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zze.zzc;
import com.google.android.gms.internal.zzcz.zzb;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;

@zzji
public class zza {
    @Nullable
    private Context mContext;
    private final Object zzako;
    private final Runnable zzaxy;
    @Nullable
    private zzc zzaxz;
    @Nullable
    private zzf zzaya;

    /* renamed from: com.google.android.gms.ads.internal.cache.zza.1 */
    class C10501 implements Runnable {
        final /* synthetic */ zza zzayb;

        C10501(zza com_google_android_gms_ads_internal_cache_zza) {
            this.zzayb = com_google_android_gms_ads_internal_cache_zza;
        }

        public void run() {
            this.zzayb.disconnect();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.cache.zza.2 */
    class C10512 implements zzb {
        final /* synthetic */ zza zzayb;

        C10512(zza com_google_android_gms_ads_internal_cache_zza) {
            this.zzayb = com_google_android_gms_ads_internal_cache_zza;
        }

        public void zzk(boolean z) {
            if (z) {
                this.zzayb.connect();
            } else {
                this.zzayb.disconnect();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.cache.zza.3 */
    class C10523 implements zze.zzb {
        final /* synthetic */ zza zzayb;

        C10523(zza com_google_android_gms_ads_internal_cache_zza) {
            this.zzayb = com_google_android_gms_ads_internal_cache_zza;
        }

        public void onConnected(@Nullable Bundle bundle) {
            synchronized (this.zzayb.zzako) {
                try {
                    this.zzayb.zzaya = this.zzayb.zzaxz.zzjz();
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to obtain a cache service instance.", e);
                    this.zzayb.disconnect();
                }
                this.zzayb.zzako.notifyAll();
            }
        }

        public void onConnectionSuspended(int i) {
            synchronized (this.zzayb.zzako) {
                this.zzayb.zzaxz = null;
                this.zzayb.zzaya = null;
                this.zzayb.zzako.notifyAll();
                zzu.zzhc().zzwk();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.cache.zza.4 */
    class C10534 implements zzc {
        final /* synthetic */ zza zzayb;

        C10534(zza com_google_android_gms_ads_internal_cache_zza) {
            this.zzayb = com_google_android_gms_ads_internal_cache_zza;
        }

        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            synchronized (this.zzayb.zzako) {
                this.zzayb.zzaxz = null;
                this.zzayb.zzaya = null;
                this.zzayb.zzako.notifyAll();
                zzu.zzhc().zzwk();
            }
        }
    }

    public zza() {
        this.zzaxy = new C10501(this);
        this.zzako = new Object();
    }

    private void connect() {
        synchronized (this.zzako) {
            if (this.mContext == null || this.zzaxz != null) {
                return;
            }
            this.zzaxz = zza(new C10523(this), new C10534(this));
            this.zzaxz.zzavd();
        }
    }

    private void disconnect() {
        synchronized (this.zzako) {
            if (this.zzaxz == null) {
                return;
            }
            if (this.zzaxz.isConnected() || this.zzaxz.isConnecting()) {
                this.zzaxz.disconnect();
            }
            this.zzaxz = null;
            this.zzaya = null;
            Binder.flushPendingCommands();
            zzu.zzhc().zzwk();
        }
    }

    public void initialize(Context context) {
        if (context != null) {
            synchronized (this.zzako) {
                if (this.mContext != null) {
                    return;
                }
                this.mContext = context.getApplicationContext();
                if (((Boolean) zzdr.zzbkr.get()).booleanValue()) {
                    connect();
                } else if (((Boolean) zzdr.zzbkq.get()).booleanValue()) {
                    zza(new C10512(this));
                }
            }
        }
    }

    public CacheEntryParcel zza(CacheOffering cacheOffering) {
        CacheEntryParcel cacheEntryParcel;
        synchronized (this.zzako) {
            if (this.zzaya == null) {
                cacheEntryParcel = new CacheEntryParcel();
            } else {
                try {
                    cacheEntryParcel = this.zzaya.zza(cacheOffering);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Unable to call into cache service.", e);
                    cacheEntryParcel = new CacheEntryParcel();
                }
            }
        }
        return cacheEntryParcel;
    }

    protected zzc zza(zze.zzb com_google_android_gms_common_internal_zze_zzb, zzc com_google_android_gms_common_internal_zze_zzc) {
        return new zzc(this.mContext, zzu.zzhc().zzwj(), com_google_android_gms_common_internal_zze_zzb, com_google_android_gms_common_internal_zze_zzc);
    }

    protected void zza(zzb com_google_android_gms_internal_zzcz_zzb) {
        zzu.zzgp().zza(com_google_android_gms_internal_zzcz_zzb);
    }

    public void zzjt() {
        if (((Boolean) zzdr.zzbks.get()).booleanValue()) {
            synchronized (this.zzako) {
                connect();
                zzu.zzgm();
                zzlb.zzcvl.removeCallbacks(this.zzaxy);
                zzu.zzgm();
                zzlb.zzcvl.postDelayed(this.zzaxy, ((Long) zzdr.zzbkt.get()).longValue());
            }
        }
    }
}
