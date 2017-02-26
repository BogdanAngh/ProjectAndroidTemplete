package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;

public class zza {
    private static Object aDL;
    private static zza aDM;
    private volatile long aDF;
    private volatile long aDG;
    private volatile long aDH;
    private volatile long aDI;
    private final Object aDJ;
    private zza aDK;
    private volatile Info cD;
    private volatile boolean mClosed;
    private final Context mContext;
    private final zze zzaql;
    private final Thread zzcur;

    public interface zza {
        Info zzcdt();
    }

    /* renamed from: com.google.android.gms.tagmanager.zza.1 */
    class C15291 implements zza {
        final /* synthetic */ zza aDN;

        C15291(zza com_google_android_gms_tagmanager_zza) {
            this.aDN = com_google_android_gms_tagmanager_zza;
        }

        public Info zzcdt() {
            Info info = null;
            try {
                info = AdvertisingIdClient.getAdvertisingIdInfo(this.aDN.mContext);
            } catch (Throwable e) {
                zzbo.zzc("IllegalStateException getting Advertising Id Info", e);
            } catch (Throwable e2) {
                zzbo.zzc("GooglePlayServicesRepairableException getting Advertising Id Info", e2);
            } catch (Throwable e22) {
                zzbo.zzc("IOException getting Ad Id Info", e22);
            } catch (Throwable e222) {
                zzbo.zzc("GooglePlayServicesNotAvailableException getting Advertising Id Info", e222);
            } catch (Throwable e2222) {
                zzbo.zzc("Unknown exception. Could not get the Advertising Id Info.", e2222);
            }
            return info;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zza.2 */
    class C15302 implements Runnable {
        final /* synthetic */ zza aDN;

        C15302(zza com_google_android_gms_tagmanager_zza) {
            this.aDN = com_google_android_gms_tagmanager_zza;
        }

        public void run() {
            this.aDN.zzcds();
        }
    }

    static {
        aDL = new Object();
    }

    private zza(Context context) {
        this(context, null, zzh.zzayl());
    }

    public zza(Context context, zza com_google_android_gms_tagmanager_zza_zza, zze com_google_android_gms_common_util_zze) {
        this.aDF = 900000;
        this.aDG = 30000;
        this.mClosed = false;
        this.aDJ = new Object();
        this.aDK = new C15291(this);
        this.zzaql = com_google_android_gms_common_util_zze;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (com_google_android_gms_tagmanager_zza_zza != null) {
            this.aDK = com_google_android_gms_tagmanager_zza_zza;
        }
        this.aDH = this.zzaql.currentTimeMillis();
        this.zzcur = new Thread(new C15302(this));
    }

    private void zzcdp() {
        synchronized (this) {
            try {
                zzcdq();
                wait(500);
            } catch (InterruptedException e) {
            }
        }
    }

    private void zzcdq() {
        if (this.zzaql.currentTimeMillis() - this.aDH > this.aDG) {
            synchronized (this.aDJ) {
                this.aDJ.notify();
            }
            this.aDH = this.zzaql.currentTimeMillis();
        }
    }

    private void zzcdr() {
        if (this.zzaql.currentTimeMillis() - this.aDI > 3600000) {
            this.cD = null;
        }
    }

    private void zzcds() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            Info zzcdt = this.aDK.zzcdt();
            if (zzcdt != null) {
                this.cD = zzcdt;
                this.aDI = this.zzaql.currentTimeMillis();
                zzbo.zzdh("Obtained fresh AdvertisingId info from GmsCore.");
            }
            synchronized (this) {
                notifyAll();
            }
            try {
                synchronized (this.aDJ) {
                    this.aDJ.wait(this.aDF);
                }
            } catch (InterruptedException e) {
                zzbo.zzdh("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    public static zza zzdw(Context context) {
        if (aDM == null) {
            synchronized (aDL) {
                if (aDM == null) {
                    aDM = new zza(context);
                    aDM.start();
                }
            }
        }
        return aDM;
    }

    public boolean isLimitAdTrackingEnabled() {
        if (this.cD == null) {
            zzcdp();
        } else {
            zzcdq();
        }
        zzcdr();
        return this.cD == null ? true : this.cD.isLimitAdTrackingEnabled();
    }

    public void start() {
        this.zzcur.start();
    }

    public String zzcdo() {
        if (this.cD == null) {
            zzcdp();
        } else {
            zzcdq();
        }
        zzcdr();
        return this.cD == null ? null : this.cD.getId();
    }
}
