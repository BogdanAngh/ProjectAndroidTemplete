package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Looper;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzjh.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.WeakHashMap;

@zzji
public class zzjg implements zzjh {
    private static final Object zzaox;
    private static zzjh zzcja;
    private final VersionInfoParcel zzapc;
    private final Object zzcjb;
    private final String zzcjc;
    private final WeakHashMap<Thread, Boolean> zzcjd;

    /* renamed from: com.google.android.gms.internal.zzjg.1 */
    class C14071 implements UncaughtExceptionHandler {
        final /* synthetic */ UncaughtExceptionHandler zzcje;
        final /* synthetic */ zzjg zzcjf;

        C14071(zzjg com_google_android_gms_internal_zzjg, UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.zzcjf = com_google_android_gms_internal_zzjg;
            this.zzcje = uncaughtExceptionHandler;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            try {
                this.zzcjf.zza(thread, th);
                if (this.zzcje != null) {
                    this.zzcje.uncaughtException(thread, th);
                }
            } catch (Throwable th2) {
                if (this.zzcje != null) {
                    this.zzcje.uncaughtException(thread, th);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjg.2 */
    class C14082 implements UncaughtExceptionHandler {
        final /* synthetic */ zzjg zzcjf;
        final /* synthetic */ UncaughtExceptionHandler zzcjg;

        C14082(zzjg com_google_android_gms_internal_zzjg, UncaughtExceptionHandler uncaughtExceptionHandler) {
            this.zzcjf = com_google_android_gms_internal_zzjg;
            this.zzcjg = uncaughtExceptionHandler;
        }

        public void uncaughtException(Thread thread, Throwable th) {
            try {
                this.zzcjf.zza(thread, th);
                if (this.zzcjg != null) {
                    this.zzcjg.uncaughtException(thread, th);
                }
            } catch (Throwable th2) {
                if (this.zzcjg != null) {
                    this.zzcjg.uncaughtException(thread, th);
                }
            }
        }
    }

    static {
        zzaox = new Object();
        zzcja = null;
    }

    zzjg(String str, VersionInfoParcel versionInfoParcel) {
        this.zzcjb = new Object();
        this.zzcjd = new WeakHashMap();
        this.zzcjc = str;
        this.zzapc = versionInfoParcel;
        zzsz();
        zzsy();
    }

    public static zzjh zzb(Context context, VersionInfoParcel versionInfoParcel) {
        synchronized (zzaox) {
            if (zzcja == null) {
                if (((Boolean) zzdr.zzbdc.get()).booleanValue()) {
                    String str = AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN;
                    try {
                        str = context.getApplicationContext().getPackageName();
                    } catch (Throwable th) {
                        zzb.zzdi("Cannot obtain package name, proceeding.");
                    }
                    zzcja = new zzjg(str, versionInfoParcel);
                } else {
                    zzcja = new zza();
                }
            }
        }
        return zzcja;
    }

    private Throwable zzd(Throwable th) {
        if (((Boolean) zzdr.zzbdd.get()).booleanValue()) {
            return th;
        }
        LinkedList linkedList = new LinkedList();
        while (th != null) {
            linkedList.push(th);
            th = th.getCause();
        }
        Throwable th2 = null;
        while (!linkedList.isEmpty()) {
            Throwable th3;
            Throwable th4 = (Throwable) linkedList.pop();
            StackTraceElement[] stackTrace = th4.getStackTrace();
            ArrayList arrayList = new ArrayList();
            arrayList.add(new StackTraceElement(th4.getClass().getName(), "<filtered>", "<filtered>", 1));
            int i = 0;
            for (StackTraceElement stackTraceElement : stackTrace) {
                if (zzck(stackTraceElement.getClassName())) {
                    arrayList.add(stackTraceElement);
                    i = 1;
                } else if (zzcl(stackTraceElement.getClassName())) {
                    arrayList.add(stackTraceElement);
                } else {
                    arrayList.add(new StackTraceElement("<filtered>", "<filtered>", "<filtered>", 1));
                }
            }
            if (i != 0) {
                th3 = th2 == null ? new Throwable(th4.getMessage()) : new Throwable(th4.getMessage(), th2);
                th3.setStackTrace((StackTraceElement[]) arrayList.toArray(new StackTraceElement[0]));
            } else {
                th3 = th2;
            }
            th2 = th3;
        }
        return th2;
    }

    private void zzsy() {
        Thread.setDefaultUncaughtExceptionHandler(new C14071(this, Thread.getDefaultUncaughtExceptionHandler()));
    }

    private void zzsz() {
        zza(Looper.getMainLooper().getThread());
    }

    String zza(Class cls, Throwable th, String str) {
        Writer stringWriter = new StringWriter();
        th.printStackTrace(new PrintWriter(stringWriter));
        return new Builder().scheme("https").path("//pagead2.googlesyndication.com/pagead/gen_204").appendQueryParameter(TtmlNode.ATTR_ID, "gmob-apps-report-exception").appendQueryParameter("os", VERSION.RELEASE).appendQueryParameter("api", String.valueOf(VERSION.SDK_INT)).appendQueryParameter("device", zzu.zzgm().zzvt()).appendQueryParameter("js", this.zzapc.zzda).appendQueryParameter("appid", this.zzcjc).appendQueryParameter("exceptiontype", cls.getName()).appendQueryParameter("stacktrace", stringWriter.toString()).appendQueryParameter("eids", TextUtils.join(",", zzdr.zzlq())).appendQueryParameter("exceptionkey", str).appendQueryParameter("cl", "135396225").appendQueryParameter("rc", "dev").toString();
    }

    public void zza(Thread thread) {
        if (thread != null) {
            synchronized (this.zzcjb) {
                this.zzcjd.put(thread, Boolean.valueOf(true));
            }
            thread.setUncaughtExceptionHandler(new C14082(this, thread.getUncaughtExceptionHandler()));
        }
    }

    protected void zza(Thread thread, Throwable th) {
        if (zzb(th)) {
            zzc(th);
        }
    }

    public void zza(Throwable th, String str) {
        Throwable zzd = zzd(th);
        if (zzd != null) {
            Class cls = th.getClass();
            List arrayList = new ArrayList();
            arrayList.add(zza(cls, zzd, str));
            zzu.zzgm().zza(arrayList, zzu.zzgq().zzux());
        }
    }

    protected boolean zzb(Throwable th) {
        boolean z = true;
        if (th == null) {
            return false;
        }
        boolean z2 = false;
        boolean z3 = false;
        while (th != null) {
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                if (zzck(stackTraceElement.getClassName())) {
                    z3 = true;
                }
                if (getClass().getName().equals(stackTraceElement.getClassName())) {
                    z2 = true;
                }
            }
            th = th.getCause();
        }
        if (!z3 || r2) {
            z = false;
        }
        return z;
    }

    public void zzc(Throwable th) {
        zza(th, BuildConfig.FLAVOR);
    }

    protected boolean zzck(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        if (str.startsWith((String) zzdr.zzbde.get())) {
            return true;
        }
        try {
            return Class.forName(str).isAnnotationPresent(zzji.class);
        } catch (Throwable e) {
            Throwable th = e;
            String str2 = "Fail to check class type for class ";
            String valueOf = String.valueOf(str);
            zzb.zza(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2), th);
            return false;
        }
    }

    protected boolean zzcl(String str) {
        return TextUtils.isEmpty(str) ? false : str.startsWith("android.") || str.startsWith("java.");
    }
}
