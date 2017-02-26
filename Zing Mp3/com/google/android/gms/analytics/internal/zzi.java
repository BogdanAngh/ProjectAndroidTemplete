package com.google.android.gms.analytics.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.RemoteException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzaa;
import java.util.Collections;

public class zzi extends zzd {
    private final zza dn;
    private zzac f1519do;
    private final zzt dp;
    private zzal dq;

    /* renamed from: com.google.android.gms.analytics.internal.zzi.1 */
    class C11581 extends zzt {
        final /* synthetic */ zzi dr;

        C11581(zzi com_google_android_gms_analytics_internal_zzi, zzf com_google_android_gms_analytics_internal_zzf) {
            this.dr = com_google_android_gms_analytics_internal_zzi;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.dr.zzacx();
        }
    }

    protected class zza implements ServiceConnection {
        final /* synthetic */ zzi dr;
        private volatile zzac ds;
        private volatile boolean dt;

        /* renamed from: com.google.android.gms.analytics.internal.zzi.zza.1 */
        class C11591 implements Runnable {
            final /* synthetic */ zzac du;
            final /* synthetic */ zza dv;

            C11591(zza com_google_android_gms_analytics_internal_zzi_zza, zzac com_google_android_gms_analytics_internal_zzac) {
                this.dv = com_google_android_gms_analytics_internal_zzi_zza;
                this.du = com_google_android_gms_analytics_internal_zzac;
            }

            public void run() {
                if (!this.dv.dr.isConnected()) {
                    this.dv.dr.zzet("Connected to service after a timeout");
                    this.dv.dr.zza(this.du);
                }
            }
        }

        /* renamed from: com.google.android.gms.analytics.internal.zzi.zza.2 */
        class C11602 implements Runnable {
            final /* synthetic */ zza dv;
            final /* synthetic */ ComponentName val$name;

            C11602(zza com_google_android_gms_analytics_internal_zzi_zza, ComponentName componentName) {
                this.dv = com_google_android_gms_analytics_internal_zzi_zza;
                this.val$name = componentName;
            }

            public void run() {
                this.dv.dr.onServiceDisconnected(this.val$name);
            }
        }

        protected zza(zzi com_google_android_gms_analytics_internal_zzi) {
            this.dr = com_google_android_gms_analytics_internal_zzi;
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
            com.google.android.gms.common.internal.zzaa.zzhs(r0);
            monitor-enter(r4);
            if (r6 != 0) goto L_0x0014;
        L_0x0008:
            r0 = r4.dr;	 Catch:{ all -> 0x005a }
            r1 = "Service connected with null binder";	 Catch:{ all -> 0x005a }
            r0.zzew(r1);	 Catch:{ all -> 0x005a }
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
            r0 = com.google.android.gms.analytics.internal.zzac.zza.zzbm(r6);	 Catch:{ RemoteException -> 0x0051 }
            r1 = r4.dr;	 Catch:{ RemoteException -> 0x0051 }
            r2 = "Bound to IAnalyticsService interface";	 Catch:{ RemoteException -> 0x0051 }
            r1.zzes(r2);	 Catch:{ RemoteException -> 0x0051 }
        L_0x002c:
            if (r0 != 0) goto L_0x005f;
        L_0x002e:
            r0 = com.google.android.gms.common.stats.zza.zzaxr();	 Catch:{ IllegalArgumentException -> 0x007c }
            r1 = r4.dr;	 Catch:{ IllegalArgumentException -> 0x007c }
            r1 = r1.getContext();	 Catch:{ IllegalArgumentException -> 0x007c }
            r2 = r4.dr;	 Catch:{ IllegalArgumentException -> 0x007c }
            r2 = r2.dn;	 Catch:{ IllegalArgumentException -> 0x007c }
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
            r2 = r4.dr;	 Catch:{ RemoteException -> 0x0051 }
            r3 = "Got binder with a wrong descriptor";	 Catch:{ RemoteException -> 0x0051 }
            r2.zze(r3, r1);	 Catch:{ RemoteException -> 0x0051 }
            goto L_0x002c;
        L_0x0051:
            r1 = move-exception;
            r1 = r4.dr;	 Catch:{ all -> 0x005a }
            r2 = "Service connect failed to get IAnalyticsService";	 Catch:{ all -> 0x005a }
            r1.zzew(r2);	 Catch:{ all -> 0x005a }
            goto L_0x002c;
        L_0x005a:
            r0 = move-exception;
            r4.notifyAll();	 Catch:{ all -> 0x0046 }
            throw r0;	 Catch:{ all -> 0x0046 }
        L_0x005f:
            r1 = r4.dt;	 Catch:{ all -> 0x005a }
            if (r1 != 0) goto L_0x0079;	 Catch:{ all -> 0x005a }
        L_0x0063:
            r1 = r4.dr;	 Catch:{ all -> 0x005a }
            r2 = "onServiceConnected received after the timeout limit";	 Catch:{ all -> 0x005a }
            r1.zzev(r2);	 Catch:{ all -> 0x005a }
            r1 = r4.dr;	 Catch:{ all -> 0x005a }
            r1 = r1.zzacc();	 Catch:{ all -> 0x005a }
            r2 = new com.google.android.gms.analytics.internal.zzi$zza$1;	 Catch:{ all -> 0x005a }
            r2.<init>(r4, r0);	 Catch:{ all -> 0x005a }
            r1.zzg(r2);	 Catch:{ all -> 0x005a }
            goto L_0x0041;	 Catch:{ all -> 0x005a }
        L_0x0079:
            r4.ds = r0;	 Catch:{ all -> 0x005a }
            goto L_0x0041;
        L_0x007c:
            r0 = move-exception;
            goto L_0x0041;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzi.zza.onServiceConnected(android.content.ComponentName, android.os.IBinder):void");
        }

        public void onServiceDisconnected(ComponentName componentName) {
            zzaa.zzhs("AnalyticsServiceConnection.onServiceDisconnected");
            this.dr.zzacc().zzg(new C11602(this, componentName));
        }

        public zzac zzacy() {
            zzac com_google_android_gms_analytics_internal_zzac = null;
            this.dr.zzzx();
            Intent intent = new Intent("com.google.android.gms.analytics.service.START");
            intent.setComponent(new ComponentName(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE, "com.google.android.gms.analytics.service.AnalyticsService"));
            Context context = this.dr.getContext();
            intent.putExtra("app_package_name", context.getPackageName());
            com.google.android.gms.common.stats.zza zzaxr = com.google.android.gms.common.stats.zza.zzaxr();
            synchronized (this) {
                this.ds = null;
                this.dt = true;
                boolean zza = zzaxr.zza(context, intent, this.dr.dn, 129);
                this.dr.zza("Bind to service requested", Boolean.valueOf(zza));
                if (zza) {
                    try {
                        wait(this.dr.zzacb().zzafa());
                    } catch (InterruptedException e) {
                        this.dr.zzev("Wait for service connect was interrupted");
                    }
                    this.dt = false;
                    com_google_android_gms_analytics_internal_zzac = this.ds;
                    this.ds = null;
                    if (com_google_android_gms_analytics_internal_zzac == null) {
                        this.dr.zzew("Successfully bound to service but never got onServiceConnected callback");
                    }
                } else {
                    this.dt = false;
                }
            }
            return com_google_android_gms_analytics_internal_zzac;
        }
    }

    protected zzi(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.dq = new zzal(com_google_android_gms_analytics_internal_zzf.zzabz());
        this.dn = new zza(this);
        this.dp = new C11581(this, com_google_android_gms_analytics_internal_zzf);
    }

    private void onDisconnect() {
        zzzg().zzabu();
    }

    private void onServiceDisconnected(ComponentName componentName) {
        zzzx();
        if (this.f1519do != null) {
            this.f1519do = null;
            zza("Disconnected from device AnalyticsService", componentName);
            onDisconnect();
        }
    }

    private void zza(zzac com_google_android_gms_analytics_internal_zzac) {
        zzzx();
        this.f1519do = com_google_android_gms_analytics_internal_zzac;
        zzacw();
        zzzg().onServiceConnected();
    }

    private void zzacw() {
        this.dq.start();
        this.dp.zzx(zzacb().zzaez());
    }

    private void zzacx() {
        zzzx();
        if (isConnected()) {
            zzes("Inactivity, disconnecting from device AnalyticsService");
            disconnect();
        }
    }

    public boolean connect() {
        zzzx();
        zzacj();
        if (this.f1519do != null) {
            return true;
        }
        zzac zzacy = this.dn.zzacy();
        if (zzacy == null) {
            return false;
        }
        this.f1519do = zzacy;
        zzacw();
        return true;
    }

    public void disconnect() {
        zzzx();
        zzacj();
        try {
            com.google.android.gms.common.stats.zza.zzaxr().zza(getContext(), this.dn);
        } catch (IllegalStateException e) {
        } catch (IllegalArgumentException e2) {
        }
        if (this.f1519do != null) {
            this.f1519do = null;
            onDisconnect();
        }
    }

    public boolean isConnected() {
        zzzx();
        zzacj();
        return this.f1519do != null;
    }

    public boolean zzacv() {
        zzzx();
        zzacj();
        zzac com_google_android_gms_analytics_internal_zzac = this.f1519do;
        if (com_google_android_gms_analytics_internal_zzac == null) {
            return false;
        }
        try {
            com_google_android_gms_analytics_internal_zzac.zzabr();
            zzacw();
            return true;
        } catch (RemoteException e) {
            zzes("Failed to clear hits from AnalyticsService");
            return false;
        }
    }

    public boolean zzb(zzab com_google_android_gms_analytics_internal_zzab) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
        zzzx();
        zzacj();
        zzac com_google_android_gms_analytics_internal_zzac = this.f1519do;
        if (com_google_android_gms_analytics_internal_zzac == null) {
            return false;
        }
        try {
            com_google_android_gms_analytics_internal_zzac.zza(com_google_android_gms_analytics_internal_zzab.zzmc(), com_google_android_gms_analytics_internal_zzab.zzaga(), com_google_android_gms_analytics_internal_zzab.zzagc() ? zzacb().zzaes() : zzacb().zzaet(), Collections.emptyList());
            zzacw();
            return true;
        } catch (RemoteException e) {
            zzes("Failed to send hits to AnalyticsService");
            return false;
        }
    }

    protected void zzzy() {
    }
}
