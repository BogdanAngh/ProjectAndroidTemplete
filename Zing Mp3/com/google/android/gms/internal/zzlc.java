package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.net.http.SslError;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.Window;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.BuildConfig;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;

@zzji
@TargetApi(8)
public class zzlc {

    @TargetApi(9)
    public static class zza extends zzlc {
        public zza() {
            super();
        }

        public boolean zza(Request request) {
            request.setShowRunningNotification(true);
            return true;
        }

        public int zzvw() {
            return 6;
        }

        public int zzvx() {
            return 7;
        }
    }

    @TargetApi(11)
    public static class zzb extends zza {

        /* renamed from: com.google.android.gms.internal.zzlc.zzb.1 */
        class C14461 implements Callable<Boolean> {
            final /* synthetic */ Context zzang;
            final /* synthetic */ WebSettings zzcvs;
            final /* synthetic */ zzb zzcvt;

            C14461(zzb com_google_android_gms_internal_zzlc_zzb, Context context, WebSettings webSettings) {
                this.zzcvt = com_google_android_gms_internal_zzlc_zzb;
                this.zzang = context;
                this.zzcvs = webSettings;
            }

            public /* synthetic */ Object call() throws Exception {
                return zzwa();
            }

            public Boolean zzwa() {
                if (this.zzang.getCacheDir() != null) {
                    this.zzcvs.setAppCachePath(this.zzang.getCacheDir().getAbsolutePath());
                    this.zzcvs.setAppCacheMaxSize(0);
                    this.zzcvs.setAppCacheEnabled(true);
                }
                this.zzcvs.setDatabasePath(this.zzang.getDatabasePath("com.google.android.gms.ads.db").getAbsolutePath());
                this.zzcvs.setDatabaseEnabled(true);
                this.zzcvs.setDomStorageEnabled(true);
                this.zzcvs.setDisplayZoomControls(false);
                this.zzcvs.setBuiltInZoomControls(true);
                this.zzcvs.setSupportZoom(true);
                this.zzcvs.setAllowContentAccess(false);
                return Boolean.valueOf(true);
            }
        }

        public boolean zza(Request request) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(1);
            return true;
        }

        public boolean zza(Context context, WebSettings webSettings) {
            super.zza(context, webSettings);
            return ((Boolean) zzlo.zzb(new C14461(this, context, webSettings))).booleanValue();
        }

        public boolean zza(Window window) {
            window.setFlags(AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT, AccessibilityEventCompat.TYPE_ASSIST_READING_CONTEXT);
            return true;
        }

        public zzme zzb(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            return new zzml(com_google_android_gms_internal_zzmd, z);
        }

        public Set<String> zzh(Uri uri) {
            return uri.getQueryParameterNames();
        }

        public WebChromeClient zzn(zzmd com_google_android_gms_internal_zzmd) {
            return new zzmk(com_google_android_gms_internal_zzmd);
        }

        public boolean zzu(View view) {
            view.setLayerType(0, null);
            return true;
        }

        public boolean zzv(View view) {
            view.setLayerType(1, null);
            return true;
        }
    }

    @TargetApi(14)
    public static class zzc extends zzb {
        public String zza(SslError sslError) {
            return sslError.getUrl();
        }

        public WebChromeClient zzn(zzmd com_google_android_gms_internal_zzmd) {
            return new zzmm(com_google_android_gms_internal_zzmd);
        }
    }

    @TargetApi(16)
    public static class zzf extends zzc {
        public void zza(View view, Drawable drawable) {
            view.setBackground(drawable);
        }

        public void zza(ViewTreeObserver viewTreeObserver, OnGlobalLayoutListener onGlobalLayoutListener) {
            viewTreeObserver.removeOnGlobalLayoutListener(onGlobalLayoutListener);
        }

        public boolean zza(Context context, WebSettings webSettings) {
            super.zza(context, webSettings);
            webSettings.setAllowFileAccessFromFileURLs(false);
            webSettings.setAllowUniversalAccessFromFileURLs(false);
            return true;
        }

        public void zzb(Activity activity, OnGlobalLayoutListener onGlobalLayoutListener) {
            Window window = activity.getWindow();
            if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
                zza(window.getDecorView().getViewTreeObserver(), onGlobalLayoutListener);
            }
        }
    }

    @TargetApi(17)
    public static class zzd extends zzf {
        public String getDefaultUserAgent(Context context) {
            return WebSettings.getDefaultUserAgent(context);
        }

        public Drawable zza(Context context, Bitmap bitmap, boolean z, float f) {
            if (!z || f <= 0.0f || f > 25.0f) {
                return new BitmapDrawable(context.getResources(), bitmap);
            }
            try {
                Bitmap createScaledBitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), false);
                Bitmap createBitmap = Bitmap.createBitmap(createScaledBitmap);
                RenderScript create = RenderScript.create(context);
                ScriptIntrinsicBlur create2 = ScriptIntrinsicBlur.create(create, Element.U8_4(create));
                Allocation createFromBitmap = Allocation.createFromBitmap(create, createScaledBitmap);
                Allocation createFromBitmap2 = Allocation.createFromBitmap(create, createBitmap);
                create2.setRadius(f);
                create2.setInput(createFromBitmap);
                create2.forEach(createFromBitmap2);
                createFromBitmap2.copyTo(createBitmap);
                return new BitmapDrawable(context.getResources(), createBitmap);
            } catch (RuntimeException e) {
                return new BitmapDrawable(context.getResources(), bitmap);
            }
        }

        public boolean zza(Context context, WebSettings webSettings) {
            super.zza(context, webSettings);
            webSettings.setMediaPlaybackRequiresUserGesture(false);
            return true;
        }
    }

    @TargetApi(18)
    public static class zze extends zzd {
        public boolean isAttachedToWindow(View view) {
            return super.isAttachedToWindow(view) || view.getWindowId() != null;
        }

        public int zzvy() {
            return 14;
        }
    }

    @TargetApi(19)
    public static class zzg extends zze {
        public boolean isAttachedToWindow(View view) {
            return view.isAttachedToWindow();
        }

        public LayoutParams zzvz() {
            return new LayoutParams(-1, -1);
        }
    }

    @TargetApi(21)
    public static class zzh extends zzg {
        public CookieManager zzal(Context context) {
            return CookieManager.getInstance();
        }

        public zzme zzb(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            return new zzmp(com_google_android_gms_internal_zzmd, z);
        }
    }

    private zzlc() {
    }

    public static zzlc zzbh(int i) {
        return i >= 21 ? new zzh() : i >= 19 ? new zzg() : i >= 18 ? new zze() : i >= 17 ? new zzd() : i >= 16 ? new zzf() : i >= 14 ? new zzc() : i >= 11 ? new zzb() : i >= 9 ? new zza() : new zzlc();
    }

    public String getDefaultUserAgent(Context context) {
        return BuildConfig.FLAVOR;
    }

    public boolean isAttachedToWindow(View view) {
        return (view.getWindowToken() == null && view.getWindowVisibility() == 8) ? false : true;
    }

    public Drawable zza(Context context, Bitmap bitmap, boolean z, float f) {
        return new BitmapDrawable(context.getResources(), bitmap);
    }

    public String zza(SslError sslError) {
        return BuildConfig.FLAVOR;
    }

    public void zza(View view, Drawable drawable) {
        view.setBackgroundDrawable(drawable);
    }

    public void zza(ViewTreeObserver viewTreeObserver, OnGlobalLayoutListener onGlobalLayoutListener) {
        viewTreeObserver.removeGlobalOnLayoutListener(onGlobalLayoutListener);
    }

    public boolean zza(Request request) {
        return false;
    }

    public boolean zza(Context context, WebSettings webSettings) {
        return false;
    }

    public boolean zza(Window window) {
        return false;
    }

    public CookieManager zzal(Context context) {
        try {
            CookieSyncManager.createInstance(context);
            return CookieManager.getInstance();
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed to obtain CookieManager.", e);
            zzu.zzgq().zza(e, "ApiLevelUtil.getCookieManager");
            return null;
        }
    }

    public zzme zzb(zzmd com_google_android_gms_internal_zzmd, boolean z) {
        return new zzme(com_google_android_gms_internal_zzmd, z);
    }

    public void zzb(Activity activity, OnGlobalLayoutListener onGlobalLayoutListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            zza(window.getDecorView().getViewTreeObserver(), onGlobalLayoutListener);
        }
    }

    public Set<String> zzh(Uri uri) {
        if (uri.isOpaque()) {
            return Collections.emptySet();
        }
        String encodedQuery = uri.getEncodedQuery();
        if (encodedQuery == null) {
            return Collections.emptySet();
        }
        Set linkedHashSet = new LinkedHashSet();
        int i = 0;
        do {
            int indexOf = encodedQuery.indexOf(38, i);
            if (indexOf == -1) {
                indexOf = encodedQuery.length();
            }
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > indexOf || indexOf2 == -1) {
                indexOf2 = indexOf;
            }
            linkedHashSet.add(Uri.decode(encodedQuery.substring(i, indexOf2)));
            i = indexOf + 1;
        } while (i < encodedQuery.length());
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public boolean zzl(zzmd com_google_android_gms_internal_zzmd) {
        if (com_google_android_gms_internal_zzmd == null) {
            return false;
        }
        com_google_android_gms_internal_zzmd.onPause();
        return true;
    }

    public boolean zzm(zzmd com_google_android_gms_internal_zzmd) {
        if (com_google_android_gms_internal_zzmd == null) {
            return false;
        }
        com_google_android_gms_internal_zzmd.onResume();
        return true;
    }

    public WebChromeClient zzn(zzmd com_google_android_gms_internal_zzmd) {
        return null;
    }

    public boolean zzu(View view) {
        return false;
    }

    public boolean zzv(View view) {
        return false;
    }

    public int zzvw() {
        return 0;
    }

    public int zzvx() {
        return 1;
    }

    public int zzvy() {
        return 5;
    }

    public LayoutParams zzvz() {
        return new LayoutParams(-2, -2);
    }
}
