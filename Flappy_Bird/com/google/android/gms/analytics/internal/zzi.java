package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import com.badlogic.gdx.Input.Keys;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.stats.zzb;
import java.util.Collections;

public class zzi extends zzd {
    private final zza zzJW;
    private zzac zzJX;
    private final zzt zzJY;
    private zzaj zzJZ;

    protected class zza implements ServiceConnection {
        final /* synthetic */ zzi zzKa;
        private volatile zzac zzKb;
        private volatile boolean zzKc;

        /* renamed from: com.google.android.gms.analytics.internal.zzi.zza.1 */
        class C01161 implements Runnable {
            final /* synthetic */ zzac zzKd;
            final /* synthetic */ zza zzKe;

            C01161(zza com_google_android_gms_analytics_internal_zzi_zza, zzac com_google_android_gms_analytics_internal_zzac) {
                this.zzKe = com_google_android_gms_analytics_internal_zzi_zza;
                this.zzKd = com_google_android_gms_analytics_internal_zzac;
            }

            public void run() {
                if (!this.zzKe.zzKa.isConnected()) {
                    this.zzKe.zzKa.zzaU("Connected to service after a timeout");
                    this.zzKe.zzKa.zza(this.zzKd);
                }
            }
        }

        /* renamed from: com.google.android.gms.analytics.internal.zzi.zza.2 */
        class C01172 implements Runnable {
            final /* synthetic */ zza zzKe;
            final /* synthetic */ ComponentName zzKf;

            C01172(zza com_google_android_gms_analytics_internal_zzi_zza, ComponentName componentName) {
                this.zzKe = com_google_android_gms_analytics_internal_zzi_zza;
                this.zzKf = componentName;
            }

            public void run() {
                this.zzKe.zzKa.onServiceDisconnected(this.zzKf);
            }
        }

        protected zza(zzi com_google_android_gms_analytics_internal_zzi) {
            this.zzKa = com_google_android_gms_analytics_internal_zzi;
        }

        public void onServiceConnected(android.content.ComponentName r5, android.os.IBinder r6) {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: Exception block dominator not found, method:com.google.android.gms.analytics.internal.zzi.zza.onServiceConnected(android.content.ComponentName, android.os.IBinder):void. bs: [B:3:0x0008, B:9:0x0015]
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.searchTryCatchDominators(ProcessTryCatchRegions.java:86)
	at jadx.core.dex.visitors.regions.ProcessTryCatchRegions.process(ProcessTryCatchRegions.java:45)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.postProcessRegions(RegionMakerVisitor.java:57)
	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:52)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r4 = this;
            r0 = "AnalyticsServiceConnection.onServiceConnected";
            com.google.android.gms.common.internal.zzu.zzbY(r0);
            monitor-enter(r4);
            if (r6 != 0) goto L_0x0014;
        L_0x0008:
            r0 = r4.zzKa;	 Catch:{ all -> 0x005a }
            r1 = "Service connected with null binder";	 Catch:{ all -> 0x005a }
            r0.zzaX(r1);	 Catch:{ all -> 0x005a }
            r4.notifyAll();	 Catch:{ all -> 0x0046 }
            monitor-exit(r4);	 Catch:{ all -> 0x0046 }
        L_0x0013:
            return;
        L_0x0014:
            r0 = 0;
            r1 = r6.getInterfaceDescriptor();	 Catch:{ RemoteException -> 0x0051 }
            r2 = "com.google.android.gms.analytics.internal.IAnalyticsService";	 Catch:{ RemoteException -> 0x0051 }
            r2 = r2.equals(r1);	 Catch:{ RemoteException -> 0x0051 }
            if (r2 == 0) goto L_0x0049;	 Catch:{ RemoteException -> 0x0051 }
        L_0x0021:
            r0 = com.google.android.gms.analytics.internal.zzac.zza.zzac(r6);	 Catch:{ RemoteException -> 0x0051 }
            r1 = r4.zzKa;	 Catch:{ RemoteException -> 0x0051 }
            r2 = "Bound to IAnalyticsService interface";	 Catch:{ RemoteException -> 0x0051 }
            r1.zzaT(r2);	 Catch:{ RemoteException -> 0x0051 }
        L_0x002c:
            if (r0 != 0) goto L_0x005f;
        L_0x002e:
            r0 = com.google.android.gms.common.stats.zzb.zzoO();	 Catch:{ IllegalArgumentException -> 0x007c }
            r1 = r4.zzKa;	 Catch:{ IllegalArgumentException -> 0x007c }
            r1 = r1.getContext();	 Catch:{ IllegalArgumentException -> 0x007c }
            r2 = r4.zzKa;	 Catch:{ IllegalArgumentException -> 0x007c }
            r2 = r2.zzJW;	 Catch:{ IllegalArgumentException -> 0x007c }
            r0.zza(r1, r2);	 Catch:{ IllegalArgumentException -> 0x007c }
        L_0x0041:
            r4.notifyAll();	 Catch:{ all -> 0x0046 }
            monitor-exit(r4);	 Catch:{ all -> 0x0046 }
            goto L_0x0013;	 Catch:{ all -> 0x0046 }
        L_0x0046:
            r0 = move-exception;	 Catch:{ all -> 0x0046 }
            monitor-exit(r4);	 Catch:{ all -> 0x0046 }
            throw r0;
        L_0x0049:
            r2 = r4.zzKa;	 Catch:{ RemoteException -> 0x0051 }
            r3 = "Got binder with a wrong descriptor";	 Catch:{ RemoteException -> 0x0051 }
            r2.zze(r3, r1);	 Catch:{ RemoteException -> 0x0051 }
            goto L_0x002c;
        L_0x0051:
            r1 = move-exception;
            r1 = r4.zzKa;	 Catch:{ all -> 0x005a }
            r2 = "Service connect failed to get IAnalyticsService";	 Catch:{ all -> 0x005a }
            r1.zzaX(r2);	 Catch:{ all -> 0x005a }
            goto L_0x002c;
        L_0x005a:
            r0 = move-exception;
            r4.notifyAll();	 Catch:{ all -> 0x0046 }
            throw r0;	 Catch:{ all -> 0x0046 }
        L_0x005f:
            r1 = r4.zzKc;	 Catch:{ all -> 0x005a }
            if (r1 != 0) goto L_0x0079;	 Catch:{ all -> 0x005a }
        L_0x0063:
            r1 = r4.zzKa;	 Catch:{ all -> 0x005a }
            r2 = "onServiceConnected received after the timeout limit";	 Catch:{ all -> 0x005a }
            r1.zzaW(r2);	 Catch:{ all -> 0x005a }
            r1 = r4.zzKa;	 Catch:{ all -> 0x005a }
            r1 = r1.zzhS();	 Catch:{ all -> 0x005a }
            r2 = new com.google.android.gms.analytics.internal.zzi$zza$1;	 Catch:{ all -> 0x005a }
            r2.<init>(r4, r0);	 Catch:{ all -> 0x005a }
            r1.zze(r2);	 Catch:{ all -> 0x005a }
            goto L_0x0041;	 Catch:{ all -> 0x005a }
        L_0x0079:
            r4.zzKb = r0;	 Catch:{ all -> 0x005a }
            goto L_0x0041;
        L_0x007c:
            r0 = move-exception;
            goto L_0x0041;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzi.zza.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }

        public void onServiceDisconnected(ComponentName name) {
            zzu.zzbY("AnalyticsServiceConnection.onServiceDisconnected");
            this.zzKa.zzhS().zze(new C01172(this, name));
        }

        public zzac zzip() {
            zzac com_google_android_gms_analytics_internal_zzac = null;
            this.zzKa.zzhO();
            Intent intent = new Intent("com.google.android.gms.analytics.service.START");
            intent.setComponent(new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.analytics.service.AnalyticsService"));
            Context context = this.zzKa.getContext();
            intent.putExtra("app_package_name", context.getPackageName());
            zzb zzoO = zzb.zzoO();
            synchronized (this) {
                this.zzKb = null;
                this.zzKc = true;
                boolean zza = zzoO.zza(context, intent, this.zzKa.zzJW, (int) Keys.CONTROL_LEFT);
                this.zzKa.zza("Bind to service requested", Boolean.valueOf(zza));
                if (zza) {
                    try {
                        wait(this.zzKa.zzhR().zzjs());
                    } catch (InterruptedException e) {
                        this.zzKa.zzaW("Wait for service connect was interrupted");
                    }
                    this.zzKc = false;
                    com_google_android_gms_analytics_internal_zzac = this.zzKb;
                    this.zzKb = null;
                    if (com_google_android_gms_analytics_internal_zzac == null) {
                        this.zzKa.zzaX("Successfully bound to service but never got onServiceConnected callback");
                    }
                } else {
                    this.zzKc = false;
                }
            }
            return com_google_android_gms_analytics_internal_zzac;
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzi.1 */
    class C03901 extends zzt {
        final /* synthetic */ zzi zzKa;

        C03901(zzi com_google_android_gms_analytics_internal_zzi, zzf com_google_android_gms_analytics_internal_zzf) {
            this.zzKa = com_google_android_gms_analytics_internal_zzi;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.zzKa.zzio();
        }
    }

    protected zzi(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.zzJZ = new zzaj(com_google_android_gms_analytics_internal_zzf.zzhP());
        this.zzJW = new zza(this);
        this.zzJY = new C03901(this, com_google_android_gms_analytics_internal_zzf);
    }

    private void onDisconnect() {
        zzhl().zzhJ();
    }

    private void onServiceDisconnected(ComponentName name) {
        zzhO();
        if (this.zzJX != null) {
            this.zzJX = null;
            zza("Disconnected from device AnalyticsService", name);
            onDisconnect();
        }
    }

    private void zza(zzac com_google_android_gms_analytics_internal_zzac) {
        zzhO();
        this.zzJX = com_google_android_gms_analytics_internal_zzac;
        zzin();
        zzhl().onServiceConnected();
    }

    private void zzin() {
        this.zzJZ.start();
        this.zzJY.zzt(zzhR().zzjr());
    }

    private void zzio() {
        zzhO();
        if (isConnected()) {
            zzaT("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public boolean connect() {
        zzhO();
        zzia();
        if (this.zzJX != null) {
            return true;
        }
        zzac zzip = this.zzJW.zzip();
        if (zzip == null) {
            return false;
        }
        this.zzJX = zzip;
        zzin();
        return true;
    }

    public void disconnect() {
        zzhO();
        zzia();
        try {
            zzb.zzoO().zza(getContext(), this.zzJW);
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e2) {
        }
        if (this.zzJX != null) {
            this.zzJX = null;
            onDisconnect();
        }
    }

    public boolean isConnected() {
        zzhO();
        zzia();
        return this.zzJX != null;
    }

    public boolean zzb(zzab com_google_android_gms_analytics_internal_zzab) {
        zzu.zzu(com_google_android_gms_analytics_internal_zzab);
        zzhO();
        zzia();
        zzac com_google_android_gms_analytics_internal_zzac = this.zzJX;
        if (com_google_android_gms_analytics_internal_zzac == null) {
            return false;
        }
        try {
            com_google_android_gms_analytics_internal_zzac.zza(com_google_android_gms_analytics_internal_zzab.zzn(), com_google_android_gms_analytics_internal_zzab.zzjW(), com_google_android_gms_analytics_internal_zzab.zzjY() ? zzhR().zzjk() : zzhR().zzjl(), Collections.emptyList());
            zzin();
            return true;
        } catch (RemoteException e) {
            zzaT("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    protected void zzhn() {
    }

    public boolean zzim() {
        zzhO();
        zzia();
        zzac com_google_android_gms_analytics_internal_zzac = this.zzJX;
        if (com_google_android_gms_analytics_internal_zzac == null) {
            return false;
        }
        try {
            com_google_android_gms_analytics_internal_zzac.zzhG();
            zzin();
            return true;
        } catch (RemoteException e) {
            zzaT("Failed to clear hits from AnalyticsService");
            return false;
        }
    }
}
