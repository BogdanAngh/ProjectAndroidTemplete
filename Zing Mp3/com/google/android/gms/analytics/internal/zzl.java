package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.text.TextUtils;
import android.util.Pair;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.CampaignTrackingService;
import com.google.android.gms.analytics.zza;
import com.google.android.gms.analytics.zze;
import com.google.android.gms.analytics.zzg;
import com.google.android.gms.analytics.zzi;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzms;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzmw;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zzsz;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class zzl extends zzd {
    private final zzj dC;
    private final zzah dD;
    private final zzag dE;
    private final zzi dF;
    private long dG;
    private final zzt dH;
    private final zzt dI;
    private final zzal dJ;
    private long dK;
    private boolean dL;
    private boolean mStarted;

    /* renamed from: com.google.android.gms.analytics.internal.zzl.1 */
    class C11611 extends zzt {
        final /* synthetic */ zzl dM;

        C11611(zzl com_google_android_gms_analytics_internal_zzl, zzf com_google_android_gms_analytics_internal_zzf) {
            this.dM = com_google_android_gms_analytics_internal_zzl;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.dM.zzadj();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.2 */
    class C11622 extends zzt {
        final /* synthetic */ zzl dM;

        C11622(zzl com_google_android_gms_analytics_internal_zzl, zzf com_google_android_gms_analytics_internal_zzf) {
            this.dM = com_google_android_gms_analytics_internal_zzl;
            super(com_google_android_gms_analytics_internal_zzf);
        }

        public void run() {
            this.dM.zzadk();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.3 */
    class C11633 implements Runnable {
        final /* synthetic */ zzl dM;

        C11633(zzl com_google_android_gms_analytics_internal_zzl) {
            this.dM = com_google_android_gms_analytics_internal_zzl;
        }

        public void run() {
            this.dM.zzadi();
        }
    }

    /* renamed from: com.google.android.gms.analytics.internal.zzl.4 */
    class C11644 implements zzw {
        final /* synthetic */ zzl dM;

        C11644(zzl com_google_android_gms_analytics_internal_zzl) {
            this.dM = com_google_android_gms_analytics_internal_zzl;
        }

        public void zzf(Throwable th) {
            this.dM.zzadp();
        }
    }

    protected zzl(zzf com_google_android_gms_analytics_internal_zzf, zzg com_google_android_gms_analytics_internal_zzg) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzaa.zzy(com_google_android_gms_analytics_internal_zzg);
        this.dG = Long.MIN_VALUE;
        this.dE = com_google_android_gms_analytics_internal_zzg.zzk(com_google_android_gms_analytics_internal_zzf);
        this.dC = com_google_android_gms_analytics_internal_zzg.zzm(com_google_android_gms_analytics_internal_zzf);
        this.dD = com_google_android_gms_analytics_internal_zzg.zzn(com_google_android_gms_analytics_internal_zzf);
        this.dF = com_google_android_gms_analytics_internal_zzg.zzo(com_google_android_gms_analytics_internal_zzf);
        this.dJ = new zzal(zzabz());
        this.dH = new C11611(this, com_google_android_gms_analytics_internal_zzf);
        this.dI = new C11622(this, com_google_android_gms_analytics_internal_zzf);
    }

    private void zza(zzh com_google_android_gms_analytics_internal_zzh, zzmt com_google_android_gms_internal_zzmt) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzh);
        zzaa.zzy(com_google_android_gms_internal_zzmt);
        zza com_google_android_gms_analytics_zza = new zza(zzabx());
        com_google_android_gms_analytics_zza.zzdr(com_google_android_gms_analytics_internal_zzh.zzacs());
        com_google_android_gms_analytics_zza.enableAdvertisingIdCollection(com_google_android_gms_analytics_internal_zzh.zzact());
        zze zzyu = com_google_android_gms_analytics_zza.zzyu();
        zznb com_google_android_gms_internal_zznb = (zznb) zzyu.zzb(zznb.class);
        com_google_android_gms_internal_zznb.zzeh(ShareConstants.WEB_DIALOG_PARAM_DATA);
        com_google_android_gms_internal_zznb.zzat(true);
        zzyu.zza((zzg) com_google_android_gms_internal_zzmt);
        zzmw com_google_android_gms_internal_zzmw = (zzmw) zzyu.zzb(zzmw.class);
        zzms com_google_android_gms_internal_zzms = (zzms) zzyu.zzb(zzms.class);
        for (Entry entry : com_google_android_gms_analytics_internal_zzh.zzmc().entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) entry.getValue();
            if ("an".equals(str)) {
                com_google_android_gms_internal_zzms.setAppName(str2);
            } else if ("av".equals(str)) {
                com_google_android_gms_internal_zzms.setAppVersion(str2);
            } else if ("aid".equals(str)) {
                com_google_android_gms_internal_zzms.setAppId(str2);
            } else if ("aiid".equals(str)) {
                com_google_android_gms_internal_zzms.setAppInstallerId(str2);
            } else if ("uid".equals(str)) {
                com_google_android_gms_internal_zznb.setUserId(str2);
            } else {
                com_google_android_gms_internal_zzmw.set(str, str2);
            }
        }
        zzb("Sending installation campaign to", com_google_android_gms_analytics_internal_zzh.zzacs(), com_google_android_gms_internal_zzmt);
        zzyu.zzp(zzace().zzago());
        zzyu.zzzm();
    }

    private void zzadh() {
        zzzx();
        Context context = zzabx().getContext();
        if (!zzaj.zzat(context)) {
            zzev("AnalyticsReceiver is not registered or is disabled. Register the receiver for reliable dispatching on non-Google Play devices. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!zzak.zzau(context)) {
            zzew("AnalyticsService is not registered or is disabled. Analytics service at risk of not starting. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!CampaignTrackingReceiver.zzat(context)) {
            zzev("CampaignTrackingReceiver is not registered, not exported or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        } else if (!CampaignTrackingService.zzau(context)) {
            zzev("CampaignTrackingService is not registered or is disabled. Installation campaign tracking is not possible. See http://goo.gl/8Rd3yj for instructions.");
        }
    }

    private void zzadj() {
        zzb(new C11644(this));
    }

    private void zzadk() {
        try {
            this.dC.zzadb();
            zzadp();
        } catch (SQLiteException e) {
            zzd("Failed to delete stale hits", e);
        }
        zzt com_google_android_gms_analytics_internal_zzt = this.dI;
        zzacb();
        com_google_android_gms_analytics_internal_zzt.zzx(86400000);
    }

    private boolean zzadq() {
        if (this.dL) {
            return false;
        }
        zzacb();
        return zzadw() > 0;
    }

    private void zzadr() {
        zzv zzacd = zzacd();
        if (zzacd.zzafn() && !zzacd.zzfy()) {
            long zzadc = zzadc();
            if (zzadc != 0 && Math.abs(zzabz().currentTimeMillis() - zzadc) <= zzacb().zzaeo()) {
                zza("Dispatch alarm scheduled (ms)", Long.valueOf(zzacb().zzaen()));
                zzacd.schedule();
            }
        }
    }

    private void zzads() {
        zzadr();
        long zzadw = zzadw();
        long zzagq = zzace().zzagq();
        if (zzagq != 0) {
            zzagq = zzadw - Math.abs(zzabz().currentTimeMillis() - zzagq);
            if (zzagq <= 0) {
                zzagq = Math.min(zzacb().zzael(), zzadw);
            }
        } else {
            zzagq = Math.min(zzacb().zzael(), zzadw);
        }
        zza("Dispatch scheduled (ms)", Long.valueOf(zzagq));
        if (this.dH.zzfy()) {
            this.dH.zzy(Math.max(1, zzagq + this.dH.zzafk()));
            return;
        }
        this.dH.zzx(zzagq);
    }

    private void zzadt() {
        zzadu();
        zzadv();
    }

    private void zzadu() {
        if (this.dH.zzfy()) {
            zzes("All hits dispatched or no network/service. Going to power save mode");
        }
        this.dH.cancel();
    }

    private void zzadv() {
        zzv zzacd = zzacd();
        if (zzacd.zzfy()) {
            zzacd.cancel();
        }
    }

    private boolean zzez(String str) {
        return zzsz.zzco(getContext()).checkCallingOrSelfPermission(str) == 0;
    }

    protected void onServiceConnected() {
        zzzx();
        zzacb();
        zzadm();
    }

    void start() {
        zzacj();
        zzaa.zza(!this.mStarted, (Object) "Analytics backend already started");
        this.mStarted = true;
        zzacc().zzg(new C11633(this));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long zza(com.google.android.gms.analytics.internal.zzh r6, boolean r7) {
        /*
        r5 = this;
        com.google.android.gms.common.internal.zzaa.zzy(r6);
        r5.zzacj();
        r5.zzzx();
        r0 = r5.dC;	 Catch:{ SQLiteException -> 0x0049 }
        r0.beginTransaction();	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r5.dC;	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r6.zzacr();	 Catch:{ SQLiteException -> 0x0049 }
        r1 = r6.zzze();	 Catch:{ SQLiteException -> 0x0049 }
        r0.zza(r2, r1);	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r5.dC;	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r6.zzacr();	 Catch:{ SQLiteException -> 0x0049 }
        r1 = r6.zzze();	 Catch:{ SQLiteException -> 0x0049 }
        r4 = r6.zzacs();	 Catch:{ SQLiteException -> 0x0049 }
        r0 = r0.zza(r2, r1, r4);	 Catch:{ SQLiteException -> 0x0049 }
        if (r7 != 0) goto L_0x0042;
    L_0x002f:
        r6.zzr(r0);	 Catch:{ SQLiteException -> 0x0049 }
    L_0x0032:
        r2 = r5.dC;	 Catch:{ SQLiteException -> 0x0049 }
        r2.zzb(r6);	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r5.dC;	 Catch:{ SQLiteException -> 0x0049 }
        r2.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x0049 }
        r2 = r5.dC;	 Catch:{ SQLiteException -> 0x0057 }
        r2.endTransaction();	 Catch:{ SQLiteException -> 0x0057 }
    L_0x0041:
        return r0;
    L_0x0042:
        r2 = 1;
        r2 = r2 + r0;
        r6.zzr(r2);	 Catch:{ SQLiteException -> 0x0049 }
        goto L_0x0032;
    L_0x0049:
        r0 = move-exception;
        r1 = "Failed to update Analytics property";
        r5.zze(r1, r0);	 Catch:{ all -> 0x0065 }
        r0 = r5.dC;	 Catch:{ SQLiteException -> 0x005e }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x005e }
    L_0x0054:
        r0 = -1;
        goto L_0x0041;
    L_0x0057:
        r2 = move-exception;
        r3 = "Failed to end transaction";
        r5.zze(r3, r2);
        goto L_0x0041;
    L_0x005e:
        r0 = move-exception;
        r1 = "Failed to end transaction";
        r5.zze(r1, r0);
        goto L_0x0054;
    L_0x0065:
        r0 = move-exception;
        r1 = r5.dC;	 Catch:{ SQLiteException -> 0x006c }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x006c }
    L_0x006b:
        throw r0;
    L_0x006c:
        r1 = move-exception;
        r2 = "Failed to end transaction";
        r5.zze(r2, r1);
        goto L_0x006b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zza(com.google.android.gms.analytics.internal.zzh, boolean):long");
    }

    public void zza(zzab com_google_android_gms_analytics_internal_zzab) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzab);
        zzi.zzzx();
        zzacj();
        if (this.dL) {
            zzet("Hit delivery not possible. Missing network permissions. See http://goo.gl/8Rd3yj for instructions");
        } else {
            zza("Delivering hit", com_google_android_gms_analytics_internal_zzab);
        }
        zzab zzf = zzf(com_google_android_gms_analytics_internal_zzab);
        zzadl();
        if (this.dF.zzb(zzf)) {
            zzet("Hit sent to the device AnalyticsService for delivery");
            return;
        }
        zzacb();
        try {
            this.dC.zzc(zzf);
            zzadp();
        } catch (SQLiteException e) {
            zze("Delivery failed to save hit to a database", e);
            zzaca().zza(zzf, "deliver: failed to insert hit to database");
        }
    }

    public void zza(zzw com_google_android_gms_analytics_internal_zzw, long j) {
        zzi.zzzx();
        zzacj();
        long j2 = -1;
        long zzagq = zzace().zzagq();
        if (zzagq != 0) {
            j2 = Math.abs(zzabz().currentTimeMillis() - zzagq);
        }
        zzb("Dispatching local hits. Elapsed time since last dispatch (ms)", Long.valueOf(j2));
        zzacb();
        zzadl();
        try {
            zzadn();
            zzace().zzagr();
            zzadp();
            if (com_google_android_gms_analytics_internal_zzw != null) {
                com_google_android_gms_analytics_internal_zzw.zzf(null);
            }
            if (this.dK != j) {
                this.dE.zzagj();
            }
        } catch (Throwable th) {
            zze("Local dispatch failed", th);
            zzace().zzagr();
            zzadp();
            if (com_google_android_gms_analytics_internal_zzw != null) {
                com_google_android_gms_analytics_internal_zzw.zzf(th);
            }
        }
    }

    public void zzabr() {
        zzi.zzzx();
        zzacj();
        zzacb();
        zzes("Delete all hits from local store");
        try {
            this.dC.zzacz();
            this.dC.zzada();
            zzadp();
        } catch (SQLiteException e) {
            zzd("Failed to delete hits from store", e);
        }
        zzadl();
        if (this.dF.zzacv()) {
            zzes("Device service unavailable. Can't clear hits stored on the device service.");
        }
    }

    public void zzabu() {
        zzi.zzzx();
        zzacj();
        zzes("Service disconnected");
    }

    void zzabw() {
        zzzx();
        this.dK = zzabz().currentTimeMillis();
    }

    public long zzadc() {
        zzi.zzzx();
        zzacj();
        try {
            return this.dC.zzadc();
        } catch (SQLiteException e) {
            zze("Failed to get min/max hit times from local store", e);
            return 0;
        }
    }

    protected void zzadi() {
        zzacj();
        zzacb();
        zzadh();
        zzace().zzago();
        if (!zzez("android.permission.ACCESS_NETWORK_STATE")) {
            zzew("Missing required android.permission.ACCESS_NETWORK_STATE. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzadx();
        }
        if (!zzez("android.permission.INTERNET")) {
            zzew("Missing required android.permission.INTERNET. Google Analytics disabled. See http://goo.gl/8Rd3yj for instructions");
            zzadx();
        }
        if (zzak.zzau(getContext())) {
            zzes("AnalyticsService registered in the app manifest and enabled");
        } else {
            zzacb();
            zzev("AnalyticsService not registered in the app manifest. Hits might not be delivered reliably. See http://goo.gl/8Rd3yj for instructions.");
        }
        if (!this.dL) {
            zzacb();
            if (!this.dC.isEmpty()) {
                zzadl();
            }
        }
        zzadp();
    }

    protected void zzadl() {
        if (!this.dL && zzacb().zzaeg() && !this.dF.isConnected()) {
            if (this.dJ.zzz(zzacb().zzafb())) {
                this.dJ.start();
                zzes("Connecting to service");
                if (this.dF.connect()) {
                    zzes("Connected to service");
                    this.dJ.clear();
                    onServiceConnected();
                }
            }
        }
    }

    public void zzadm() {
        zzi.zzzx();
        zzacj();
        zzaby();
        if (!zzacb().zzaeg()) {
            zzev("Service client disabled. Can't dispatch local hits to device AnalyticsService");
        }
        if (!this.dF.isConnected()) {
            zzes("Service not connected");
        } else if (!this.dC.isEmpty()) {
            zzes("Dispatching local hits to device AnalyticsService");
            while (true) {
                try {
                    break;
                    List zzt = this.dC.zzt((long) zzacb().zzaep());
                    if (zzt.isEmpty()) {
                        zzab com_google_android_gms_analytics_internal_zzab;
                        while (true) {
                            if (!zzt.isEmpty()) {
                                com_google_android_gms_analytics_internal_zzab = (zzab) zzt.get(0);
                                if (this.dF.zzb(com_google_android_gms_analytics_internal_zzab)) {
                                    zzadp();
                                    return;
                                }
                                zzt.remove(com_google_android_gms_analytics_internal_zzab);
                                try {
                                    this.dC.zzu(com_google_android_gms_analytics_internal_zzab.zzafz());
                                } catch (SQLiteException e) {
                                    zze("Failed to remove hit that was send for delivery", e);
                                    zzadt();
                                    return;
                                }
                            }
                        }
                        List zzt2 = this.dC.zzt((long) zzacb().zzaep());
                        if (zzt2.isEmpty()) {
                            while (true) {
                                if (zzt2.isEmpty()) {
                                    com_google_android_gms_analytics_internal_zzab = (zzab) zzt2.get(0);
                                    if (this.dF.zzb(com_google_android_gms_analytics_internal_zzab)) {
                                        zzt2.remove(com_google_android_gms_analytics_internal_zzab);
                                        this.dC.zzu(com_google_android_gms_analytics_internal_zzab.zzafz());
                                    } else {
                                        zzadp();
                                        return;
                                    }
                                }
                            }
                        }
                        zzadp();
                        return;
                    }
                    zzadp();
                    return;
                } catch (SQLiteException e2) {
                    zze("Failed to read hits from store", e2);
                    zzadt();
                    return;
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected boolean zzadn() {
        /*
        r12 = this;
        r1 = 1;
        r2 = 0;
        com.google.android.gms.analytics.zzi.zzzx();
        r12.zzacj();
        r0 = "Dispatching a batch of local hits";
        r12.zzes(r0);
        r0 = r12.dF;
        r0 = r0.isConnected();
        if (r0 != 0) goto L_0x002b;
    L_0x0015:
        r12.zzacb();
        r0 = r1;
    L_0x0019:
        r3 = r12.dD;
        r3 = r3.zzagk();
        if (r3 != 0) goto L_0x002d;
    L_0x0021:
        if (r0 == 0) goto L_0x002f;
    L_0x0023:
        if (r1 == 0) goto L_0x002f;
    L_0x0025:
        r0 = "No network or service available. Will retry later";
        r12.zzes(r0);
    L_0x002a:
        return r2;
    L_0x002b:
        r0 = r2;
        goto L_0x0019;
    L_0x002d:
        r1 = r2;
        goto L_0x0021;
    L_0x002f:
        r0 = r12.zzacb();
        r0 = r0.zzaep();
        r1 = r12.zzacb();
        r1 = r1.zzaeq();
        r0 = java.lang.Math.max(r0, r1);
        r6 = (long) r0;
        r3 = new java.util.ArrayList;
        r3.<init>();
        r4 = 0;
    L_0x004b:
        r0 = r12.dC;	 Catch:{ all -> 0x01dd }
        r0.beginTransaction();	 Catch:{ all -> 0x01dd }
        r3.clear();	 Catch:{ all -> 0x01dd }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x00cc }
        r8 = r0.zzt(r6);	 Catch:{ SQLiteException -> 0x00cc }
        r0 = r8.isEmpty();	 Catch:{ SQLiteException -> 0x00cc }
        if (r0 == 0) goto L_0x007c;
    L_0x005f:
        r0 = "Store is empty, nothing to dispatch";
        r12.zzes(r0);	 Catch:{ SQLiteException -> 0x00cc }
        r12.zzadt();	 Catch:{ SQLiteException -> 0x00cc }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x0072 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x0072 }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x0072 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0072 }
        goto L_0x002a;
    L_0x0072:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x007c:
        r0 = "Hits loaded from store. count";
        r1 = r8.size();	 Catch:{ SQLiteException -> 0x00cc }
        r1 = java.lang.Integer.valueOf(r1);	 Catch:{ SQLiteException -> 0x00cc }
        r12.zza(r0, r1);	 Catch:{ SQLiteException -> 0x00cc }
        r1 = r8.iterator();	 Catch:{ all -> 0x01dd }
    L_0x008d:
        r0 = r1.hasNext();	 Catch:{ all -> 0x01dd }
        if (r0 == 0) goto L_0x00ec;
    L_0x0093:
        r0 = r1.next();	 Catch:{ all -> 0x01dd }
        r0 = (com.google.android.gms.analytics.internal.zzab) r0;	 Catch:{ all -> 0x01dd }
        r10 = r0.zzafz();	 Catch:{ all -> 0x01dd }
        r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
        if (r0 != 0) goto L_0x008d;
    L_0x00a1:
        r0 = "Database contains successfully uploaded hit";
        r1 = java.lang.Long.valueOf(r4);	 Catch:{ all -> 0x01dd }
        r3 = r8.size();	 Catch:{ all -> 0x01dd }
        r3 = java.lang.Integer.valueOf(r3);	 Catch:{ all -> 0x01dd }
        r12.zzd(r0, r1, r3);	 Catch:{ all -> 0x01dd }
        r12.zzadt();	 Catch:{ all -> 0x01dd }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x00c1 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x00c1 }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x00c1 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00c1 }
        goto L_0x002a;
    L_0x00c1:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x00cc:
        r0 = move-exception;
        r1 = "Failed to read hits from persisted store";
        r12.zzd(r1, r0);	 Catch:{ all -> 0x01dd }
        r12.zzadt();	 Catch:{ all -> 0x01dd }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x00e1 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x00e1 }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x00e1 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x00e1 }
        goto L_0x002a;
    L_0x00e1:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x00ec:
        r0 = r12.dF;	 Catch:{ all -> 0x01dd }
        r0 = r0.isConnected();	 Catch:{ all -> 0x01dd }
        if (r0 == 0) goto L_0x01f4;
    L_0x00f4:
        r12.zzacb();	 Catch:{ all -> 0x01dd }
        r0 = "Service connected, sending hits to the service";
        r12.zzes(r0);	 Catch:{ all -> 0x01dd }
    L_0x00fc:
        r0 = r8.isEmpty();	 Catch:{ all -> 0x01dd }
        if (r0 != 0) goto L_0x01f4;
    L_0x0102:
        r0 = 0;
        r0 = r8.get(r0);	 Catch:{ all -> 0x01dd }
        r0 = (com.google.android.gms.analytics.internal.zzab) r0;	 Catch:{ all -> 0x01dd }
        r1 = r12.dF;	 Catch:{ all -> 0x01dd }
        r1 = r1.zzb(r0);	 Catch:{ all -> 0x01dd }
        if (r1 != 0) goto L_0x013a;
    L_0x0111:
        r0 = r4;
    L_0x0112:
        r4 = r12.dD;	 Catch:{ all -> 0x01dd }
        r4 = r4.zzagk();	 Catch:{ all -> 0x01dd }
        if (r4 == 0) goto L_0x0188;
    L_0x011a:
        r4 = r12.dD;	 Catch:{ all -> 0x01dd }
        r8 = r4.zzt(r8);	 Catch:{ all -> 0x01dd }
        r9 = r8.iterator();	 Catch:{ all -> 0x01dd }
        r4 = r0;
    L_0x0125:
        r0 = r9.hasNext();	 Catch:{ all -> 0x01dd }
        if (r0 == 0) goto L_0x017f;
    L_0x012b:
        r0 = r9.next();	 Catch:{ all -> 0x01dd }
        r0 = (java.lang.Long) r0;	 Catch:{ all -> 0x01dd }
        r0 = r0.longValue();	 Catch:{ all -> 0x01dd }
        r4 = java.lang.Math.max(r4, r0);	 Catch:{ all -> 0x01dd }
        goto L_0x0125;
    L_0x013a:
        r10 = r0.zzafz();	 Catch:{ all -> 0x01dd }
        r4 = java.lang.Math.max(r4, r10);	 Catch:{ all -> 0x01dd }
        r8.remove(r0);	 Catch:{ all -> 0x01dd }
        r1 = "Hit sent do device AnalyticsService for delivery";
        r12.zzb(r1, r0);	 Catch:{ all -> 0x01dd }
        r1 = r12.dC;	 Catch:{ SQLiteException -> 0x015f }
        r10 = r0.zzafz();	 Catch:{ SQLiteException -> 0x015f }
        r1.zzu(r10);	 Catch:{ SQLiteException -> 0x015f }
        r0 = r0.zzafz();	 Catch:{ SQLiteException -> 0x015f }
        r0 = java.lang.Long.valueOf(r0);	 Catch:{ SQLiteException -> 0x015f }
        r3.add(r0);	 Catch:{ SQLiteException -> 0x015f }
        goto L_0x00fc;
    L_0x015f:
        r0 = move-exception;
        r1 = "Failed to remove hit that was send for delivery";
        r12.zze(r1, r0);	 Catch:{ all -> 0x01dd }
        r12.zzadt();	 Catch:{ all -> 0x01dd }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x0174 }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x0174 }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x0174 }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x0174 }
        goto L_0x002a;
    L_0x0174:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x017f:
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x01a5 }
        r0.zzr(r8);	 Catch:{ SQLiteException -> 0x01a5 }
        r3.addAll(r8);	 Catch:{ SQLiteException -> 0x01a5 }
        r0 = r4;
    L_0x0188:
        r4 = r3.isEmpty();	 Catch:{ all -> 0x01dd }
        if (r4 == 0) goto L_0x01c5;
    L_0x018e:
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x019a }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x019a }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x019a }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x019a }
        goto L_0x002a;
    L_0x019a:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x01a5:
        r0 = move-exception;
        r1 = "Failed to remove successfully uploaded hits";
        r12.zze(r1, r0);	 Catch:{ all -> 0x01dd }
        r12.zzadt();	 Catch:{ all -> 0x01dd }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x01ba }
        r0.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01ba }
        r0 = r12.dC;	 Catch:{ SQLiteException -> 0x01ba }
        r0.endTransaction();	 Catch:{ SQLiteException -> 0x01ba }
        goto L_0x002a;
    L_0x01ba:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x01c5:
        r4 = r12.dC;	 Catch:{ SQLiteException -> 0x01d2 }
        r4.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01d2 }
        r4 = r12.dC;	 Catch:{ SQLiteException -> 0x01d2 }
        r4.endTransaction();	 Catch:{ SQLiteException -> 0x01d2 }
        r4 = r0;
        goto L_0x004b;
    L_0x01d2:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x01dd:
        r0 = move-exception;
        r1 = r12.dC;	 Catch:{ SQLiteException -> 0x01e9 }
        r1.setTransactionSuccessful();	 Catch:{ SQLiteException -> 0x01e9 }
        r1 = r12.dC;	 Catch:{ SQLiteException -> 0x01e9 }
        r1.endTransaction();	 Catch:{ SQLiteException -> 0x01e9 }
        throw r0;
    L_0x01e9:
        r0 = move-exception;
        r1 = "Failed to commit local dispatch transaction";
        r12.zze(r1, r0);
        r12.zzadt();
        goto L_0x002a;
    L_0x01f4:
        r0 = r4;
        goto L_0x0112;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.analytics.internal.zzl.zzadn():boolean");
    }

    public void zzado() {
        zzi.zzzx();
        zzacj();
        zzet("Sync dispatching local hits");
        long j = this.dK;
        zzacb();
        zzadl();
        try {
            zzadn();
            zzace().zzagr();
            zzadp();
            if (this.dK != j) {
                this.dE.zzagj();
            }
        } catch (Throwable th) {
            zze("Sync local dispatch failed", th);
            zzadp();
        }
    }

    public void zzadp() {
        zzabx().zzzx();
        zzacj();
        if (!zzadq()) {
            this.dE.unregister();
            zzadt();
        } else if (this.dC.isEmpty()) {
            this.dE.unregister();
            zzadt();
        } else {
            boolean z;
            if (((Boolean) zzy.eU.get()).booleanValue()) {
                z = true;
            } else {
                this.dE.zzagh();
                z = this.dE.isConnected();
            }
            if (z) {
                zzads();
                return;
            }
            zzadt();
            zzadr();
        }
    }

    public long zzadw() {
        if (this.dG != Long.MIN_VALUE) {
            return this.dG;
        }
        return zzzh().zzafu() ? ((long) zzzh().zzahl()) * 1000 : zzacb().zzaem();
    }

    public void zzadx() {
        zzacj();
        zzzx();
        this.dL = true;
        this.dF.disconnect();
        zzadp();
    }

    public void zzaw(boolean z) {
        zzadp();
    }

    public void zzb(zzw com_google_android_gms_analytics_internal_zzw) {
        zza(com_google_android_gms_analytics_internal_zzw, this.dK);
    }

    protected void zzc(zzh com_google_android_gms_analytics_internal_zzh) {
        zzzx();
        zzb("Sending first hit to property", com_google_android_gms_analytics_internal_zzh.zzacs());
        if (!zzace().zzagp().zzz(zzacb().zzafi())) {
            String zzags = zzace().zzags();
            if (!TextUtils.isEmpty(zzags)) {
                zzmt zza = zzao.zza(zzaca(), zzags);
                zzb("Found relevant installation campaign", zza);
                zza(com_google_android_gms_analytics_internal_zzh, zza);
            }
        }
    }

    zzab zzf(zzab com_google_android_gms_analytics_internal_zzab) {
        if (!TextUtils.isEmpty(com_google_android_gms_analytics_internal_zzab.zzage())) {
            return com_google_android_gms_analytics_internal_zzab;
        }
        Pair zzagw = zzace().zzagt().zzagw();
        if (zzagw == null) {
            return com_google_android_gms_analytics_internal_zzab;
        }
        Long l = (Long) zzagw.second;
        String str = (String) zzagw.first;
        String valueOf = String.valueOf(l);
        valueOf = new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(str).length()).append(valueOf).append(":").append(str).toString();
        Map hashMap = new HashMap(com_google_android_gms_analytics_internal_zzab.zzmc());
        hashMap.put("_m", valueOf);
        return zzab.zza(this, com_google_android_gms_analytics_internal_zzab, hashMap);
    }

    public void zzfa(String str) {
        zzaa.zzib(str);
        zzzx();
        zzaby();
        zzmt zza = zzao.zza(zzaca(), str);
        if (zza == null) {
            zzd("Parsing failed. Ignoring invalid campaign data", str);
            return;
        }
        CharSequence zzags = zzace().zzags();
        if (str.equals(zzags)) {
            zzev("Ignoring duplicate install campaign");
        } else if (TextUtils.isEmpty(zzags)) {
            zzace().zzff(str);
            if (zzace().zzagp().zzz(zzacb().zzafi())) {
                zzd("Campaign received too late, ignoring", zza);
                return;
            }
            zzb("Received installation campaign", zza);
            for (zzh zza2 : this.dC.zzv(0)) {
                zza(zza2, zza);
            }
        } else {
            zzd("Ignoring multiple install campaigns. original, new", zzags, str);
        }
    }

    public void zzw(long j) {
        zzi.zzzx();
        zzacj();
        if (j < 0) {
            j = 0;
        }
        this.dG = j;
        zzadp();
    }

    protected void zzzy() {
        this.dC.initialize();
        this.dD.initialize();
        this.dF.initialize();
    }
}
