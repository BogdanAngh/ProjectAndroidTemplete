package com.google.android.gms.internal;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
public class zzbk extends Thread {
    private boolean mStarted;
    private boolean zzam;
    private final Object zzqt;
    private final int zzrA;
    private final int zzrB;
    private final int zzrj;
    private final int zzrl;
    private boolean zzrv;
    private final zzbj zzrw;
    private final zzbi zzrx;
    private final zzgc zzry;
    private final int zzrz;

    /* renamed from: com.google.android.gms.internal.zzbk.1 */
    class C01891 implements Runnable {
        final /* synthetic */ View zzrC;
        final /* synthetic */ zzbk zzrD;

        C01891(zzbk com_google_android_gms_internal_zzbk, View view) {
            this.zzrD = com_google_android_gms_internal_zzbk;
            this.zzrC = view;
        }

        public void run() {
            this.zzrD.zzg(this.zzrC);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbk.2 */
    class C01912 implements Runnable {
        final /* synthetic */ zzbk zzrD;
        ValueCallback<String> zzrE;
        final /* synthetic */ zzbh zzrF;
        final /* synthetic */ WebView zzrG;

        /* renamed from: com.google.android.gms.internal.zzbk.2.1 */
        class C01901 implements ValueCallback<String> {
            final /* synthetic */ C01912 zzrH;

            C01901(C01912 c01912) {
                this.zzrH = c01912;
            }

            public /* synthetic */ void onReceiveValue(Object x0) {
                zzx((String) x0);
            }

            public void zzx(String str) {
                this.zzrH.zzrD.zza(this.zzrH.zzrF, this.zzrH.zzrG, str);
            }
        }

        C01912(zzbk com_google_android_gms_internal_zzbk, zzbh com_google_android_gms_internal_zzbh, WebView webView) {
            this.zzrD = com_google_android_gms_internal_zzbk;
            this.zzrF = com_google_android_gms_internal_zzbh;
            this.zzrG = webView;
            this.zzrE = new C01901(this);
        }

        public void run() {
            if (this.zzrG.getSettings().getJavaScriptEnabled()) {
                try {
                    this.zzrG.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zzrE);
                } catch (Throwable th) {
                    this.zzrE.onReceiveValue("");
                }
            }
        }
    }

    @zzgd
    class zza {
        final /* synthetic */ zzbk zzrD;
        final int zzrI;
        final int zzrJ;

        zza(zzbk com_google_android_gms_internal_zzbk, int i, int i2) {
            this.zzrD = com_google_android_gms_internal_zzbk;
            this.zzrI = i;
            this.zzrJ = i2;
        }
    }

    public zzbk(zzbj com_google_android_gms_internal_zzbj, zzbi com_google_android_gms_internal_zzbi, zzgc com_google_android_gms_internal_zzgc) {
        this.mStarted = false;
        this.zzrv = false;
        this.zzam = false;
        this.zzrw = com_google_android_gms_internal_zzbj;
        this.zzrx = com_google_android_gms_internal_zzbi;
        this.zzry = com_google_android_gms_internal_zzgc;
        this.zzqt = new Object();
        this.zzrj = ((Integer) zzbz.zzud.get()).intValue();
        this.zzrA = ((Integer) zzbz.zzue.get()).intValue();
        this.zzrl = ((Integer) zzbz.zzuf.get()).intValue();
        this.zzrB = ((Integer) zzbz.zzug.get()).intValue();
        this.zzrz = ((Integer) zzbz.zzuh.get()).intValue();
        setName("ContentFetchTask");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r3 = this;
    L_0x0000:
        r0 = r3.zzam;
        if (r0 != 0) goto L_0x0052;
    L_0x0004:
        r0 = r3.zzcq();	 Catch:{ Throwable -> 0x0018 }
        if (r0 == 0) goto L_0x0044;
    L_0x000a:
        r0 = r3.zzrw;	 Catch:{ Throwable -> 0x0018 }
        r0 = r0.getActivity();	 Catch:{ Throwable -> 0x0018 }
        if (r0 != 0) goto L_0x0038;
    L_0x0012:
        r0 = "ContentFetchThread: no activity";
        com.google.android.gms.ads.internal.util.client.zzb.zzay(r0);	 Catch:{ Throwable -> 0x0018 }
        goto L_0x0000;
    L_0x0018:
        r0 = move-exception;
        r1 = "Error in ContentFetchTask";
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r1, r0);
        r1 = r3.zzry;
        r2 = 1;
        r1.zza(r0, r2);
    L_0x0024:
        r1 = r3.zzqt;
        monitor-enter(r1);
    L_0x0027:
        r0 = r3.zzrv;	 Catch:{ all -> 0x004f }
        if (r0 == 0) goto L_0x004d;
    L_0x002b:
        r0 = "ContentFetchTask: waiting";
        com.google.android.gms.ads.internal.util.client.zzb.zzay(r0);	 Catch:{ InterruptedException -> 0x0036 }
        r0 = r3.zzqt;	 Catch:{ InterruptedException -> 0x0036 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0036 }
        goto L_0x0027;
    L_0x0036:
        r0 = move-exception;
        goto L_0x0027;
    L_0x0038:
        r3.zza(r0);	 Catch:{ Throwable -> 0x0018 }
    L_0x003b:
        r0 = r3.zzrz;	 Catch:{ Throwable -> 0x0018 }
        r0 = r0 * 1000;
        r0 = (long) r0;	 Catch:{ Throwable -> 0x0018 }
        java.lang.Thread.sleep(r0);	 Catch:{ Throwable -> 0x0018 }
        goto L_0x0024;
    L_0x0044:
        r0 = "ContentFetchTask: sleeping";
        com.google.android.gms.ads.internal.util.client.zzb.zzay(r0);	 Catch:{ Throwable -> 0x0018 }
        r3.zzcs();	 Catch:{ Throwable -> 0x0018 }
        goto L_0x003b;
    L_0x004d:
        monitor-exit(r1);	 Catch:{ all -> 0x004f }
        goto L_0x0000;
    L_0x004f:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x004f }
        throw r0;
    L_0x0052:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzbk.run():void");
    }

    public void wakeup() {
        synchronized (this.zzqt) {
            this.zzrv = false;
            this.zzqt.notifyAll();
            zzb.zzay("ContentFetchThread: wakeup");
        }
    }

    zza zza(View view, zzbh com_google_android_gms_internal_zzbh) {
        int i = 0;
        if (view == null) {
            return new zza(this, 0, 0);
        }
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zza(this, 0, 0);
            }
            com_google_android_gms_internal_zzbh.zzv(text.toString());
            return new zza(this, 1, 0);
        } else if ((view instanceof WebView) && !(view instanceof zzid)) {
            com_google_android_gms_internal_zzbh.zzcl();
            return zza((WebView) view, com_google_android_gms_internal_zzbh) ? new zza(this, 0, 1) : new zza(this, 0, 0);
        } else if (!(view instanceof ViewGroup)) {
            return new zza(this, 0, 0);
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            int i2 = 0;
            int i3 = 0;
            while (i < viewGroup.getChildCount()) {
                zza zza = zza(viewGroup.getChildAt(i), com_google_android_gms_internal_zzbh);
                i3 += zza.zzrI;
                i2 += zza.zzrJ;
                i++;
            }
            return new zza(this, i3, i2);
        }
    }

    void zza(Activity activity) {
        if (activity != null) {
            View view = null;
            if (!(activity.getWindow() == null || activity.getWindow().getDecorView() == null)) {
                view = activity.getWindow().getDecorView().findViewById(16908290);
            }
            if (view != null) {
                zzf(view);
            }
        }
    }

    void zza(zzbh com_google_android_gms_internal_zzbh, WebView webView, String str) {
        com_google_android_gms_internal_zzbh.zzck();
        try {
            if (!TextUtils.isEmpty(str)) {
                String optString = new JSONObject(str).optString("text");
                if (TextUtils.isEmpty(webView.getTitle())) {
                    com_google_android_gms_internal_zzbh.zzu(optString);
                } else {
                    com_google_android_gms_internal_zzbh.zzu(webView.getTitle() + "\n" + optString);
                }
            }
            if (com_google_android_gms_internal_zzbh.zzch()) {
                this.zzrx.zzb(com_google_android_gms_internal_zzbh);
            }
        } catch (JSONException e) {
            zzb.zzay("Json string may be malformed.");
        } catch (Throwable th) {
            zzb.zza("Failed to get webview content.", th);
            this.zzry.zza(th, true);
        }
    }

    boolean zza(RunningAppProcessInfo runningAppProcessInfo) {
        return runningAppProcessInfo.importance == 100;
    }

    boolean zza(WebView webView, zzbh com_google_android_gms_internal_zzbh) {
        if (!zzlk.zzoX()) {
            return false;
        }
        com_google_android_gms_internal_zzbh.zzcl();
        webView.post(new C01912(this, com_google_android_gms_internal_zzbh, webView));
        return true;
    }

    public void zzcp() {
        synchronized (this.zzqt) {
            if (this.mStarted) {
                zzb.zzay("Content hash thread already started, quiting...");
                return;
            }
            this.mStarted = true;
            start();
        }
    }

    boolean zzcq() {
        try {
            Context context = this.zzrw.getContext();
            if (context == null) {
                return false;
            }
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            KeyguardManager keyguardManager = (KeyguardManager) context.getSystemService("keyguard");
            if (activityManager == null || keyguardManager == null) {
                return false;
            }
            List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
            if (runningAppProcesses == null) {
                return false;
            }
            for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                if (Process.myPid() == runningAppProcessInfo.pid) {
                    if (zza(runningAppProcessInfo) && !keyguardManager.inKeyguardRestrictedInputMode() && zzr(context)) {
                        return true;
                    }
                    return false;
                }
            }
            return false;
        } catch (Throwable th) {
            return false;
        }
    }

    public zzbh zzcr() {
        return this.zzrx.zzco();
    }

    public void zzcs() {
        synchronized (this.zzqt) {
            this.zzrv = true;
            zzb.zzay("ContentFetchThread: paused, mPause = " + this.zzrv);
        }
    }

    public boolean zzct() {
        return this.zzrv;
    }

    boolean zzf(View view) {
        if (view == null) {
            return false;
        }
        view.post(new C01891(this, view));
        return true;
    }

    void zzg(View view) {
        try {
            zzbh com_google_android_gms_internal_zzbh = new zzbh(this.zzrj, this.zzrA, this.zzrl, this.zzrB);
            zza zza = zza(view, com_google_android_gms_internal_zzbh);
            com_google_android_gms_internal_zzbh.zzcm();
            if (zza.zzrI != 0 || zza.zzrJ != 0) {
                if (zza.zzrJ != 0 || com_google_android_gms_internal_zzbh.zzcn() != 0) {
                    if (zza.zzrJ != 0 || !this.zzrx.zza(com_google_android_gms_internal_zzbh)) {
                        this.zzrx.zzc(com_google_android_gms_internal_zzbh);
                    }
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Exception in fetchContentOnUIThread", e);
            this.zzry.zza(e, true);
        }
    }

    boolean zzr(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager == null ? false : powerManager.isScreenOn();
    }
}
