package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Process;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzld;
import java.io.IOException;

class zza {
    private static Object zzaKl;
    private static zza zzaKm;
    private volatile boolean mClosed;
    private final Context mContext;
    private final Thread zzFZ;
    private volatile Info zzJl;
    private volatile long zzaKh;
    private volatile long zzaKi;
    private volatile long zzaKj;
    private zza zzaKk;
    private final zzlb zzpw;

    /* renamed from: com.google.android.gms.tagmanager.zza.2 */
    class C02902 implements Runnable {
        final /* synthetic */ zza zzaKn;

        C02902(zza com_google_android_gms_tagmanager_zza) {
            this.zzaKn = com_google_android_gms_tagmanager_zza;
        }

        public void run() {
            this.zzaKn.zzye();
        }
    }

    public interface zza {
        Info zzyg();
    }

    /* renamed from: com.google.android.gms.tagmanager.zza.1 */
    class C05211 implements zza {
        final /* synthetic */ zza zzaKn;

        C05211(zza com_google_android_gms_tagmanager_zza) {
            this.zzaKn = com_google_android_gms_tagmanager_zza;
        }

        public Info zzyg() {
            Info info = null;
            try {
                info = AdvertisingIdClient.getAdvertisingIdInfo(this.zzaKn.mContext);
            } catch (IllegalStateException e) {
                zzbg.zzaC("IllegalStateException getting Advertising Id Info");
            } catch (GooglePlayServicesRepairableException e2) {
                zzbg.zzaC("GooglePlayServicesRepairableException getting Advertising Id Info");
            } catch (IOException e3) {
                zzbg.zzaC("IOException getting Ad Id Info");
            } catch (GooglePlayServicesNotAvailableException e4) {
                zzbg.zzaC("GooglePlayServicesNotAvailableException getting Advertising Id Info");
            } catch (Exception e5) {
                zzbg.zzaC("Unknown exception. Could not get the Advertising Id Info.");
            }
            return info;
        }
    }

    static {
        zzaKl = new Object();
    }

    private zza(Context context) {
        this(context, null, zzld.zzoQ());
    }

    zza(Context context, zza com_google_android_gms_tagmanager_zza_zza, zzlb com_google_android_gms_internal_zzlb) {
        this.zzaKh = 900000;
        this.zzaKi = 30000;
        this.mClosed = false;
        this.zzaKk = new C05211(this);
        this.zzpw = com_google_android_gms_internal_zzlb;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        if (com_google_android_gms_tagmanager_zza_zza != null) {
            this.zzaKk = com_google_android_gms_tagmanager_zza_zza;
        }
        this.zzFZ = new Thread(new C02902(this));
    }

    static zza zzaE(Context context) {
        if (zzaKm == null) {
            synchronized (zzaKl) {
                if (zzaKm == null) {
                    zzaKm = new zza(context);
                    zzaKm.start();
                }
            }
        }
        return zzaKm;
    }

    private void zzye() {
        Process.setThreadPriority(10);
        while (!this.mClosed) {
            try {
                this.zzJl = this.zzaKk.zzyg();
                Thread.sleep(this.zzaKh);
            } catch (InterruptedException e) {
                zzbg.zzaA("sleep interrupted in AdvertiserDataPoller thread; continuing");
            }
        }
    }

    private void zzyf() {
        if (this.zzpw.currentTimeMillis() - this.zzaKj >= this.zzaKi) {
            interrupt();
            this.zzaKj = this.zzpw.currentTimeMillis();
        }
    }

    void interrupt() {
        this.zzFZ.interrupt();
    }

    public boolean isLimitAdTrackingEnabled() {
        zzyf();
        return this.zzJl == null ? true : this.zzJl.isLimitAdTrackingEnabled();
    }

    void start() {
        this.zzFZ.start();
    }

    public String zzyd() {
        zzyf();
        return this.zzJl == null ? null : this.zzJl.getId();
    }
}
