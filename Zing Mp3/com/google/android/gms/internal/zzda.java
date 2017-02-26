package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Rect;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzs;
import com.mp3download.zingmp3.BuildConfig;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
@TargetApi(14)
public class zzda extends Thread {
    private boolean mStarted;
    private final Object zzako;
    private final int zzavi;
    private final int zzavk;
    private boolean zzawh;
    private final zzcy zzawi;
    private final zzjh zzawj;
    private final int zzawk;
    private final int zzawl;
    private final int zzawm;
    private final int zzawn;
    private final int zzawo;
    private final int zzawp;
    private final String zzawq;
    private boolean zzbl;

    /* renamed from: com.google.android.gms.internal.zzda.1 */
    class C12801 implements Runnable {
        final /* synthetic */ View zzawr;
        final /* synthetic */ zzda zzaws;

        C12801(zzda com_google_android_gms_internal_zzda, View view) {
            this.zzaws = com_google_android_gms_internal_zzda;
            this.zzawr = view;
        }

        public void run() {
            this.zzaws.zzi(this.zzawr);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzda.2 */
    class C12822 implements Runnable {
        final /* synthetic */ zzda zzaws;
        ValueCallback<String> zzawt;
        final /* synthetic */ zzcx zzawu;
        final /* synthetic */ WebView zzawv;
        final /* synthetic */ boolean zzaww;

        /* renamed from: com.google.android.gms.internal.zzda.2.1 */
        class C12811 implements ValueCallback<String> {
            final /* synthetic */ C12822 zzawx;

            C12811(C12822 c12822) {
                this.zzawx = c12822;
            }

            public /* synthetic */ void onReceiveValue(Object obj) {
                zzaf((String) obj);
            }

            public void zzaf(String str) {
                this.zzawx.zzaws.zza(this.zzawx.zzawu, this.zzawx.zzawv, str, this.zzawx.zzaww);
            }
        }

        C12822(zzda com_google_android_gms_internal_zzda, zzcx com_google_android_gms_internal_zzcx, WebView webView, boolean z) {
            this.zzaws = com_google_android_gms_internal_zzda;
            this.zzawu = com_google_android_gms_internal_zzcx;
            this.zzawv = webView;
            this.zzaww = z;
            this.zzawt = new C12811(this);
        }

        public void run() {
            if (this.zzawv.getSettings().getJavaScriptEnabled()) {
                try {
                    this.zzawv.evaluateJavascript("(function() { return  {text:document.body.innerText}})();", this.zzawt);
                } catch (Throwable th) {
                    this.zzawt.onReceiveValue(BuildConfig.FLAVOR);
                }
            }
        }
    }

    @zzji
    class zza {
        final /* synthetic */ zzda zzaws;
        final int zzawy;
        final int zzawz;

        zza(zzda com_google_android_gms_internal_zzda, int i, int i2) {
            this.zzaws = com_google_android_gms_internal_zzda;
            this.zzawy = i;
            this.zzawz = i2;
        }
    }

    public zzda(zzcy com_google_android_gms_internal_zzcy, zzjh com_google_android_gms_internal_zzjh) {
        this.mStarted = false;
        this.zzawh = false;
        this.zzbl = false;
        this.zzawi = com_google_android_gms_internal_zzcy;
        this.zzawj = com_google_android_gms_internal_zzjh;
        this.zzako = new Object();
        this.zzavi = ((Integer) zzdr.zzbev.get()).intValue();
        this.zzawl = ((Integer) zzdr.zzbew.get()).intValue();
        this.zzavk = ((Integer) zzdr.zzbex.get()).intValue();
        this.zzawm = ((Integer) zzdr.zzbey.get()).intValue();
        this.zzawn = ((Integer) zzdr.zzbfb.get()).intValue();
        this.zzawo = ((Integer) zzdr.zzbfd.get()).intValue();
        this.zzawp = ((Integer) zzdr.zzbfe.get()).intValue();
        this.zzawk = ((Integer) zzdr.zzbez.get()).intValue();
        this.zzawq = (String) zzdr.zzbfg.get();
        setName("ContentFetchTask");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void run() {
        /*
        r3 = this;
    L_0x0000:
        r0 = r3.zzji();	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        if (r0 == 0) goto L_0x003f;
    L_0x0006:
        r0 = com.google.android.gms.ads.internal.zzu.zzgp();	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        r0 = r0.getActivity();	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        if (r0 != 0) goto L_0x0034;
    L_0x0010:
        r0 = "ContentFetchThread: no activity. Sleeping.";
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(r0);	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        r3.zzjk();	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
    L_0x0018:
        r0 = r3.zzawk;	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        r0 = r0 * 1000;
        r0 = (long) r0;	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
    L_0x0020:
        r1 = r3.zzako;
        monitor-enter(r1);
    L_0x0023:
        r0 = r3.zzawh;	 Catch:{ all -> 0x0058 }
        if (r0 == 0) goto L_0x0056;
    L_0x0027:
        r0 = "ContentFetchTask: waiting";
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(r0);	 Catch:{ InterruptedException -> 0x0032 }
        r0 = r3.zzako;	 Catch:{ InterruptedException -> 0x0032 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0032 }
        goto L_0x0023;
    L_0x0032:
        r0 = move-exception;
        goto L_0x0023;
    L_0x0034:
        r3.zza(r0);	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        goto L_0x0018;
    L_0x0038:
        r0 = move-exception;
        r1 = "Error in ContentFetchTask";
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r1, r0);
        goto L_0x0020;
    L_0x003f:
        r0 = "ContentFetchTask: sleeping";
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(r0);	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        r3.zzjk();	 Catch:{ InterruptedException -> 0x0038, Throwable -> 0x0048 }
        goto L_0x0018;
    L_0x0048:
        r0 = move-exception;
        r1 = "Error in ContentFetchTask";
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r1, r0);
        r1 = r3.zzawj;
        r2 = "ContentFetchTask.run";
        r1.zza(r0, r2);
        goto L_0x0020;
    L_0x0056:
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        goto L_0x0000;
    L_0x0058:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0058 }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzda.run():void");
    }

    public void wakeup() {
        synchronized (this.zzako) {
            this.zzawh = false;
            this.zzako.notifyAll();
            zzb.zzdg("ContentFetchThread: wakeup");
        }
    }

    zza zza(@Nullable View view, zzcx com_google_android_gms_internal_zzcx) {
        int i = 0;
        if (view == null) {
            return new zza(this, 0, 0);
        }
        Context context = zzu.zzgp().getContext();
        if (context != null) {
            String str = (String) view.getTag(context.getResources().getIdentifier((String) zzdr.zzbff.get(), TtmlNode.ATTR_ID, context.getPackageName()));
            if (!(TextUtils.isEmpty(this.zzawq) || str == null || !str.equals(this.zzawq))) {
                return new zza(this, 0, 0);
            }
        }
        boolean globalVisibleRect = view.getGlobalVisibleRect(new Rect());
        if ((view instanceof TextView) && !(view instanceof EditText)) {
            CharSequence text = ((TextView) view).getText();
            if (TextUtils.isEmpty(text)) {
                return new zza(this, 0, 0);
            }
            com_google_android_gms_internal_zzcx.zzb(text.toString(), globalVisibleRect, view.getX(), view.getY(), (float) view.getWidth(), (float) view.getHeight());
            return new zza(this, 1, 0);
        } else if ((view instanceof WebView) && !(view instanceof zzmd)) {
            com_google_android_gms_internal_zzcx.zzjd();
            return zza((WebView) view, com_google_android_gms_internal_zzcx, globalVisibleRect) ? new zza(this, 0, 1) : new zza(this, 0, 0);
        } else if (!(view instanceof ViewGroup)) {
            return new zza(this, 0, 0);
        } else {
            ViewGroup viewGroup = (ViewGroup) view;
            int i2 = 0;
            for (int i3 = 0; i3 < viewGroup.getChildCount(); i3++) {
                zza zza = zza(viewGroup.getChildAt(i3), com_google_android_gms_internal_zzcx);
                i2 += zza.zzawy;
                i += zza.zzawz;
            }
            return new zza(this, i2, i);
        }
    }

    void zza(@Nullable Activity activity) {
        if (activity != null) {
            View view = null;
            try {
                if (!(activity.getWindow() == null || activity.getWindow().getDecorView() == null)) {
                    view = activity.getWindow().getDecorView().findViewById(16908290);
                }
            } catch (Throwable th) {
                zzb.zzdg("Failed getting root view of activity. Content not extracted.");
            }
            if (view != null) {
                zzh(view);
            }
        }
    }

    void zza(zzcx com_google_android_gms_internal_zzcx, WebView webView, String str, boolean z) {
        com_google_android_gms_internal_zzcx.zzjc();
        try {
            if (!TextUtils.isEmpty(str)) {
                String optString = new JSONObject(str).optString(MimeTypes.BASE_TYPE_TEXT);
                if (TextUtils.isEmpty(webView.getTitle())) {
                    com_google_android_gms_internal_zzcx.zza(optString, z, webView.getX(), webView.getY(), (float) webView.getWidth(), (float) webView.getHeight());
                } else {
                    String valueOf = String.valueOf(webView.getTitle());
                    com_google_android_gms_internal_zzcx.zza(new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(optString).length()).append(valueOf).append("\n").append(optString).toString(), z, webView.getX(), webView.getY(), (float) webView.getWidth(), (float) webView.getHeight());
                }
            }
            if (com_google_android_gms_internal_zzcx.zzix()) {
                this.zzawi.zzb(com_google_android_gms_internal_zzcx);
            }
        } catch (JSONException e) {
            zzb.zzdg("Json string may be malformed.");
        } catch (Throwable th) {
            zzb.zza("Failed to get webview content.", th);
            this.zzawj.zza(th, "ContentFetchTask.processWebViewContent");
        }
    }

    boolean zza(RunningAppProcessInfo runningAppProcessInfo) {
        return runningAppProcessInfo.importance == 100;
    }

    @TargetApi(19)
    boolean zza(WebView webView, zzcx com_google_android_gms_internal_zzcx, boolean z) {
        if (!zzs.zzayu()) {
            return false;
        }
        com_google_android_gms_internal_zzcx.zzjd();
        webView.post(new C12822(this, com_google_android_gms_internal_zzcx, webView, z));
        return true;
    }

    boolean zzh(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager == null ? false : powerManager.isScreenOn();
    }

    boolean zzh(@Nullable View view) {
        if (view == null) {
            return false;
        }
        view.post(new C12801(this, view));
        return true;
    }

    void zzi(View view) {
        try {
            zzcx com_google_android_gms_internal_zzcx = new zzcx(this.zzavi, this.zzawl, this.zzavk, this.zzawm, this.zzawn, this.zzawo, this.zzawp);
            zza zza = zza(view, com_google_android_gms_internal_zzcx);
            com_google_android_gms_internal_zzcx.zzje();
            if (zza.zzawy != 0 || zza.zzawz != 0) {
                if (zza.zzawz != 0 || com_google_android_gms_internal_zzcx.zzjf() != 0) {
                    if (zza.zzawz != 0 || !this.zzawi.zza(com_google_android_gms_internal_zzcx)) {
                        this.zzawi.zzc(com_google_android_gms_internal_zzcx);
                    }
                }
            }
        } catch (Throwable e) {
            zzb.zzb("Exception in fetchContentOnUIThread", e);
            this.zzawj.zza(e, "ContentFetchTask.fetchContent");
        }
    }

    public void zzjh() {
        synchronized (this.zzako) {
            if (this.mStarted) {
                zzb.zzdg("Content hash thread already started, quiting...");
                return;
            }
            this.mStarted = true;
            start();
        }
    }

    boolean zzji() {
        try {
            Context context = zzu.zzgp().getContext();
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
                    if (zza(runningAppProcessInfo) && !keyguardManager.inKeyguardRestrictedInputMode() && zzh(context)) {
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

    public zzcx zzjj() {
        return this.zzawi.zzjg();
    }

    public void zzjk() {
        synchronized (this.zzako) {
            this.zzawh = true;
            zzb.zzdg("ContentFetchThread: paused, mPause = " + this.zzawh);
        }
    }

    public boolean zzjl() {
        return this.zzawh;
    }
}
