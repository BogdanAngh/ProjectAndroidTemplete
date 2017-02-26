package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.AlertDialog.Builder;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Debug;
import android.os.Debug.MemoryInfo;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.os.PowerManager;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.PopupWindow;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.AdActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.internal.ClientApi;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzs;
import com.mp3download.zingmp3.BuildConfig;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzlb {
    public static final Handler zzcvl;
    private final Object zzako;
    private String zzbre;
    private zzgh zzcnn;
    private boolean zzcvm;
    private boolean zzcvn;

    /* renamed from: com.google.android.gms.internal.zzlb.1 */
    class C14421 implements com.google.android.gms.internal.zzef.zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ List zzcvo;
        final /* synthetic */ zzef zzcvp;
        final /* synthetic */ zzlb zzcvq;

        C14421(zzlb com_google_android_gms_internal_zzlb, List list, zzef com_google_android_gms_internal_zzef, Context context) {
            this.zzcvq = com_google_android_gms_internal_zzlb;
            this.zzcvo = list;
            this.zzcvp = com_google_android_gms_internal_zzef;
            this.zzang = context;
        }

        public void zzmh() {
            for (String str : this.zzcvo) {
                String str2 = "Pinging url: ";
                String valueOf = String.valueOf(str);
                zzb.zzdh(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                this.zzcvp.mayLaunchUrl(Uri.parse(str), null, null);
            }
            this.zzcvp.zzd((Activity) this.zzang);
        }

        public void zzmi() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlb.2 */
    class C14432 implements Runnable {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzlb zzcvq;

        C14432(zzlb com_google_android_gms_internal_zzlb, Context context) {
            this.zzcvq = com_google_android_gms_internal_zzlb;
            this.zzang = context;
        }

        public void run() {
            synchronized (this.zzcvq.zzako) {
                this.zzcvq.zzbre = this.zzcvq.zzaa(this.zzang);
                this.zzcvq.zzako.notifyAll();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlb.3 */
    class C14443 implements com.google.android.gms.ads.internal.util.client.zza.zza {
        final /* synthetic */ Context zzang;
        final /* synthetic */ zzlb zzcvq;
        final /* synthetic */ String zzcvr;

        C14443(zzlb com_google_android_gms_internal_zzlb, Context context, String str) {
            this.zzcvq = com_google_android_gms_internal_zzlb;
            this.zzang = context;
            this.zzcvr = str;
        }

        public void zzv(String str) {
            zzu.zzgm().zzc(this.zzang, this.zzcvr, str);
        }
    }

    private final class zza extends BroadcastReceiver {
        final /* synthetic */ zzlb zzcvq;

        private zza(zzlb com_google_android_gms_internal_zzlb) {
            this.zzcvq = com_google_android_gms_internal_zzlb;
        }

        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.USER_PRESENT".equals(intent.getAction())) {
                this.zzcvq.zzcvm = true;
            } else if ("android.intent.action.SCREEN_OFF".equals(intent.getAction())) {
                this.zzcvq.zzcvm = false;
            }
        }
    }

    static {
        zzcvl = new zzky(Looper.getMainLooper());
    }

    public zzlb() {
        this.zzako = new Object();
        this.zzcvm = true;
        this.zzcvn = false;
    }

    private JSONArray zza(Collection<?> collection) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : collection) {
            zza(jSONArray, zza);
        }
        return jSONArray;
    }

    private void zza(JSONArray jSONArray, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONArray.put(zzi((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONArray.put(zzap((Map) obj));
        } else if (obj instanceof Collection) {
            jSONArray.put(zza((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONArray.put(zza((Object[]) obj));
        } else {
            jSONArray.put(obj);
        }
    }

    private void zza(JSONObject jSONObject, String str, Object obj) throws JSONException {
        if (obj instanceof Bundle) {
            jSONObject.put(str, zzi((Bundle) obj));
        } else if (obj instanceof Map) {
            jSONObject.put(str, zzap((Map) obj));
        } else if (obj instanceof Collection) {
            if (str == null) {
                str = "null";
            }
            jSONObject.put(str, zza((Collection) obj));
        } else if (obj instanceof Object[]) {
            jSONObject.put(str, zza(Arrays.asList((Object[]) obj)));
        } else {
            jSONObject.put(str, obj);
        }
    }

    private boolean zza(KeyguardManager keyguardManager) {
        return keyguardManager == null ? false : keyguardManager.inKeyguardRestrictedInputMode();
    }

    private boolean zza(PowerManager powerManager) {
        return powerManager == null || powerManager.isScreenOn();
    }

    public static void zzb(Runnable runnable) {
        if (Looper.getMainLooper().getThread() != Thread.currentThread()) {
            runnable.run();
        } else {
            zzla.zza(runnable);
        }
    }

    private boolean zzh(Context context) {
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        return powerManager == null ? false : powerManager.isScreenOn();
    }

    private JSONObject zzi(Bundle bundle) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        for (String str : bundle.keySet()) {
            zza(jSONObject, str, bundle.get(str));
        }
        return jSONObject;
    }

    private Bitmap zzr(@NonNull View view) {
        try {
            int width = view.getWidth();
            int height = view.getHeight();
            if (width == 0 || height == 0) {
                zzb.zzdi("Width or height of view is zero");
                return null;
            }
            Bitmap createBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            view.layout(0, 0, width, height);
            view.draw(canvas);
            return createBitmap;
        } catch (Throwable e) {
            zzb.zzb("Fail to capture the webview", e);
            return null;
        }
    }

    private Bitmap zzs(@NonNull View view) {
        Bitmap drawingCache;
        Throwable e;
        try {
            boolean isDrawingCacheEnabled = view.isDrawingCacheEnabled();
            view.setDrawingCacheEnabled(true);
            drawingCache = view.getDrawingCache();
            drawingCache = drawingCache != null ? Bitmap.createBitmap(drawingCache) : null;
            try {
                view.setDrawingCacheEnabled(isDrawingCacheEnabled);
            } catch (RuntimeException e2) {
                e = e2;
                zzb.zzb("Fail to capture the web view", e);
                return drawingCache;
            }
        } catch (Throwable e3) {
            Throwable th = e3;
            drawingCache = null;
            e = th;
            zzb.zzb("Fail to capture the web view", e);
            return drawingCache;
        }
        return drawingCache;
    }

    public void runOnUiThread(Runnable runnable) {
        if (Looper.getMainLooper().getThread() == Thread.currentThread()) {
            runnable.run();
        } else {
            zzcvl.post(runnable);
        }
    }

    public Bundle zza(zzda com_google_android_gms_internal_zzda) {
        if (com_google_android_gms_internal_zzda == null) {
            return null;
        }
        if (!((Boolean) zzdr.zzbfa.get()).booleanValue() && !((Boolean) zzdr.zzbfc.get()).booleanValue()) {
            return null;
        }
        if (zzu.zzgq().zzuq() && zzu.zzgq().zzur()) {
            return null;
        }
        String zziy;
        String zziz;
        String str;
        if (com_google_android_gms_internal_zzda.zzjl()) {
            com_google_android_gms_internal_zzda.wakeup();
        }
        zzcx zzjj = com_google_android_gms_internal_zzda.zzjj();
        if (zzjj != null) {
            zziy = zzjj.zziy();
            zziz = zzjj.zziz();
            String zzja = zzjj.zzja();
            if (zziy != null) {
                zzu.zzgq().zzcw(zziy);
            }
            if (zzja != null) {
                zzu.zzgq().zzcx(zzja);
                str = zziy;
                zziy = zziz;
                zziz = zzja;
            } else {
                str = zziy;
                zziy = zziz;
                zziz = zzja;
            }
        } else {
            zziy = null;
            str = zzu.zzgq().zzuy();
            zziz = zzu.zzgq().zzuz();
        }
        Bundle bundle = new Bundle(1);
        if (!(zziz == null || !((Boolean) zzdr.zzbfc.get()).booleanValue() || zzu.zzgq().zzur())) {
            bundle.putString("v_fp_vertical", zziz);
        }
        if (!(str == null || !((Boolean) zzdr.zzbfa.get()).booleanValue() || zzu.zzgq().zzuq())) {
            bundle.putString("fingerprint", str);
            if (!str.equals(zziy)) {
                bundle.putString("v_fp", zziy);
            }
        }
        return !bundle.isEmpty() ? bundle : null;
    }

    public DisplayMetrics zza(WindowManager windowManager) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public PopupWindow zza(View view, int i, int i2, boolean z) {
        return new PopupWindow(view, i, i2, z);
    }

    public String zza(Context context, View view, AdSizeParcel adSizeParcel) {
        String str = null;
        if (((Boolean) zzdr.zzbfr.get()).booleanValue()) {
            try {
                JSONObject jSONObject = new JSONObject();
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("width", adSizeParcel.width);
                jSONObject2.put("height", adSizeParcel.height);
                jSONObject.put("size", jSONObject2);
                jSONObject.put("activity", zzad(context));
                if (!adSizeParcel.zzazr) {
                    JSONArray jSONArray = new JSONArray();
                    while (view != null) {
                        ViewParent parent = view.getParent();
                        if (parent != null) {
                            int i = -1;
                            if (parent instanceof ViewGroup) {
                                i = ((ViewGroup) parent).indexOfChild(view);
                            }
                            JSONObject jSONObject3 = new JSONObject();
                            jSONObject3.put(ShareConstants.MEDIA_TYPE, parent.getClass().getName());
                            jSONObject3.put("index_of_child", i);
                            jSONArray.put(jSONObject3);
                        }
                        View view2 = (parent == null || !(parent instanceof View)) ? null : (View) parent;
                        view = view2;
                    }
                    if (jSONArray.length() > 0) {
                        jSONObject.put("parents", jSONArray);
                    }
                }
                str = jSONObject.toString();
            } catch (Throwable e) {
                zzb.zzc("Fail to get view hierarchy json", e);
            }
        }
        return str;
    }

    public String zza(Context context, zzav com_google_android_gms_internal_zzav, String str, View view) {
        if (com_google_android_gms_internal_zzav != null) {
            try {
                Uri parse = Uri.parse(str);
                if (com_google_android_gms_internal_zzav.zzd(parse)) {
                    parse = com_google_android_gms_internal_zzav.zza(parse, context, view);
                }
                str = parse.toString();
            } catch (Exception e) {
            }
        }
        return str;
    }

    public String zza(zzmd com_google_android_gms_internal_zzmd, String str) {
        return zza(com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.zzxe(), str, com_google_android_gms_internal_zzmd.getView());
    }

    public String zza(InputStreamReader inputStreamReader) throws IOException {
        StringBuilder stringBuilder = new StringBuilder(Utility.DEFAULT_STREAM_BUFFER_SIZE);
        char[] cArr = new char[ItemAnimator.FLAG_MOVED];
        while (true) {
            int read = inputStreamReader.read(cArr);
            if (read == -1) {
                return stringBuilder.toString();
            }
            stringBuilder.append(cArr, 0, read);
        }
    }

    public Map<String, Integer> zza(View view, WindowManager windowManager) {
        DisplayMetrics zza = zza(windowManager);
        int i = zza.widthPixels;
        int i2 = zza.heightPixels;
        int[] iArr = new int[2];
        Map<String, Integer> hashMap = new HashMap();
        view.getLocationInWindow(iArr);
        hashMap.put("xInPixels", Integer.valueOf(iArr[0]));
        hashMap.put("yInPixels", Integer.valueOf(iArr[1]));
        hashMap.put("windowWidthInPixels", Integer.valueOf(i));
        hashMap.put("windowHeightInPixels", Integer.valueOf(i2));
        return hashMap;
    }

    JSONArray zza(Object[] objArr) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (Object zza : objArr) {
            zza(jSONArray, zza);
        }
        return jSONArray;
    }

    public void zza(Activity activity, OnGlobalLayoutListener onGlobalLayoutListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(onGlobalLayoutListener);
        }
    }

    public void zza(Activity activity, OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().addOnScrollChangedListener(onScrollChangedListener);
        }
    }

    @TargetApi(18)
    public void zza(Context context, Uri uri) {
        try {
            Bundle bundle = new Bundle();
            if (((Boolean) zzdr.zzbkv.get()).booleanValue() && zzs.zzayt()) {
                bundle.putBinder(CustomTabsIntent.EXTRA_SESSION, null);
            }
            bundle.putString("com.android.browser.application_id", context.getPackageName());
            context.startActivity(new Intent("android.intent.action.VIEW", uri).putExtras(bundle));
            String valueOf = String.valueOf(uri.toString());
            zzb.zzdg(new StringBuilder(String.valueOf(valueOf).length() + 26).append("Opening ").append(valueOf).append(" in a new browser.").toString());
        } catch (Throwable e) {
            zzb.zzb("No browser is found.", e);
        }
    }

    public void zza(Context context, String str, WebSettings webSettings) {
        webSettings.setUserAgentString(zzh(context, str));
    }

    public void zza(Context context, @Nullable String str, String str2, Bundle bundle, boolean z) {
        if (z) {
            bundle.putString("device", zzu.zzgm().zzvt());
            bundle.putString("eids", TextUtils.join(",", zzdr.zzlq()));
        }
        zzm.zzkr().zza(context, str, str2, bundle, z, new C14443(this, context, str));
    }

    public void zza(Context context, String str, List<String> list) {
        for (String com_google_android_gms_internal_zzll : list) {
            Future future = (Future) new zzll(context, str, com_google_android_gms_internal_zzll).zzrz();
        }
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection) {
        zza(context, str, z, httpURLConnection, false);
    }

    public void zza(Context context, String str, boolean z, HttpURLConnection httpURLConnection, boolean z2) {
        httpURLConnection.setConnectTimeout(60000);
        httpURLConnection.setInstanceFollowRedirects(z);
        httpURLConnection.setReadTimeout(60000);
        httpURLConnection.setRequestProperty("User-Agent", zzh(context, str));
        httpURLConnection.setUseCaches(z2);
    }

    public void zza(Context context, List<String> list) {
        if (!(context instanceof Activity) || TextUtils.isEmpty(zzasg.zzfa((Activity) context))) {
            return;
        }
        if (list == null) {
            zzkx.m1697v("Cannot ping urls: empty list.");
        } else if (zzef.zzn(context)) {
            zzef com_google_android_gms_internal_zzef = new zzef();
            com_google_android_gms_internal_zzef.zza(new C14421(this, list, com_google_android_gms_internal_zzef, context));
            com_google_android_gms_internal_zzef.zze((Activity) context);
        } else {
            zzkx.m1697v("Cannot ping url because custom tabs is not supported");
        }
    }

    public void zza(List<String> list, String str) {
        for (String com_google_android_gms_internal_zzll : list) {
            Future future = (Future) new zzll(com_google_android_gms_internal_zzll, str).zzrz();
        }
    }

    @TargetApi(24)
    public boolean zza(Activity activity, Configuration configuration) {
        com.google.android.gms.ads.internal.util.client.zza zzkr = zzm.zzkr();
        int zzb = zzkr.zzb((Context) activity, configuration.screenHeightDp);
        int zzb2 = zzkr.zzb((Context) activity, configuration.screenWidthDp);
        DisplayMetrics zza = zza((WindowManager) activity.getApplicationContext().getSystemService("window"));
        int i = zza.heightPixels;
        int i2 = zza.widthPixels;
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        int dimensionPixelSize = identifier > 0 ? activity.getResources().getDimensionPixelSize(identifier) : 0;
        identifier = ((Integer) zzdr.zzbld.get()).intValue() * ((int) Math.round(((double) activity.getResources().getDisplayMetrics().density) + 0.5d));
        return zzb(i, dimensionPixelSize + zzb, identifier) && zzb(i2, zzb2, identifier);
    }

    public boolean zza(PackageManager packageManager, String str, String str2) {
        return packageManager.checkPermission(str2, str) == 0;
    }

    public boolean zza(View view, Context context) {
        KeyguardManager keyguardManager = null;
        Context applicationContext = context.getApplicationContext();
        PowerManager powerManager = applicationContext != null ? (PowerManager) applicationContext.getSystemService("power") : null;
        Object systemService = context.getSystemService("keyguard");
        if (systemService != null && (systemService instanceof KeyguardManager)) {
            keyguardManager = (KeyguardManager) systemService;
        }
        return zza(view, powerManager, keyguardManager);
    }

    public boolean zza(View view, PowerManager powerManager, KeyguardManager keyguardManager) {
        boolean z = zzu.zzgm().zzvp() || !zza(keyguardManager);
        return view.getVisibility() == 0 && view.isShown() && zza(powerManager) && z && (!((Boolean) zzdr.zzbgs.get()).booleanValue() || view.getLocalVisibleRect(new Rect()) || view.getGlobalVisibleRect(new Rect()));
    }

    public boolean zza(ClassLoader classLoader, Class<?> cls, String str) {
        boolean z = false;
        try {
            z = cls.isAssignableFrom(Class.forName(str, false, classLoader));
        } catch (Throwable th) {
        }
        return z;
    }

    protected String zzaa(Context context) {
        return new WebView(context).getSettings().getUserAgentString();
    }

    public Builder zzab(Context context) {
        return new Builder(context);
    }

    public zzdj zzac(Context context) {
        return new zzdj(context);
    }

    public String zzad(Context context) {
        try {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            if (activityManager == null) {
                return null;
            }
            List runningTasks = activityManager.getRunningTasks(1);
            if (!(runningTasks == null || runningTasks.isEmpty())) {
                RunningTaskInfo runningTaskInfo = (RunningTaskInfo) runningTasks.get(0);
                if (!(runningTaskInfo == null || runningTaskInfo.topActivity == null)) {
                    return runningTaskInfo.topActivity.getClassName();
                }
            }
            return null;
        } catch (Exception e) {
        }
    }

    public boolean zzae(Context context) {
        try {
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
                    if (runningAppProcessInfo.importance == 100 && !keyguardManager.inKeyguardRestrictedInputMode() && zzh(context)) {
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

    public Bitmap zzaf(Context context) {
        if (!(context instanceof Activity)) {
            return null;
        }
        Bitmap zzs;
        try {
            if (((Boolean) zzdr.zzbiq.get()).booleanValue()) {
                Window window = ((Activity) context).getWindow();
                if (window != null) {
                    zzs = zzs(window.getDecorView().getRootView());
                }
                zzs = null;
            } else {
                zzs = zzr(((Activity) context).getWindow().getDecorView());
            }
        } catch (Throwable e) {
            zzb.zzb("Fail to capture screen shot", e);
        }
        return zzs;
    }

    public AudioManager zzag(Context context) {
        return (AudioManager) context.getSystemService(MimeTypes.BASE_TYPE_AUDIO);
    }

    public float zzah(Context context) {
        AudioManager zzag = zzag(context);
        if (zzag == null) {
            return 0.0f;
        }
        int streamMaxVolume = zzag.getStreamMaxVolume(3);
        return streamMaxVolume != 0 ? ((float) zzag.getStreamVolume(3)) / ((float) streamMaxVolume) : 0.0f;
    }

    public int zzai(Context context) {
        ApplicationInfo applicationInfo = context.getApplicationInfo();
        return applicationInfo == null ? 0 : applicationInfo.targetSdkVersion;
    }

    public boolean zzaj(Context context) {
        try {
            context.getClassLoader().loadClass(ClientApi.class.getName());
            return false;
        } catch (ClassNotFoundException e) {
            return true;
        }
    }

    public Bundle zzak(Context context) {
        zzda zzw = zzu.zzgq().zzw(context);
        return zzw == null ? null : zza(zzw);
    }

    public JSONObject zzap(Map<String, ?> map) throws JSONException {
        String valueOf;
        try {
            JSONObject jSONObject = new JSONObject();
            for (String valueOf2 : map.keySet()) {
                zza(jSONObject, valueOf2, map.get(valueOf2));
            }
            return jSONObject;
        } catch (ClassCastException e) {
            String str = "Could not convert map to JSON: ";
            valueOf2 = String.valueOf(e.getMessage());
            throw new JSONException(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
        }
    }

    public void zzb(Activity activity, OnScrollChangedListener onScrollChangedListener) {
        Window window = activity.getWindow();
        if (window != null && window.getDecorView() != null && window.getDecorView().getViewTreeObserver() != null) {
            window.getDecorView().getViewTreeObserver().removeOnScrollChangedListener(onScrollChangedListener);
        }
    }

    public void zzb(Context context, Intent intent) {
        try {
            context.startActivity(intent);
        } catch (Throwable th) {
            intent.addFlags(268435456);
            context.startActivity(intent);
        }
    }

    public void zzb(Context context, String str, String str2, Bundle bundle, boolean z) {
        if (((Boolean) zzdr.zzbgx.get()).booleanValue()) {
            zza(context, str, str2, bundle, z);
        }
    }

    boolean zzb(int i, int i2, int i3) {
        return Math.abs(i - i2) <= i3;
    }

    public String zzc(String str, Map<String, String> map) {
        for (String str2 : map.keySet()) {
            str = str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{str2}), String.format("$1%s$2", new Object[]{Uri.encode((String) map.get(str2))}));
        }
        return str.replaceAll(String.format("(?<!@)((?:@@)*)@%s(?<!@)((?:@@)*)@", new Object[]{"[^@]+"}), String.format("$1%s$2", new Object[]{BuildConfig.FLAVOR})).replaceAll("@@", "@");
    }

    public void zzc(Context context, String str, String str2) {
        List arrayList = new ArrayList();
        arrayList.add(str2);
        zza(context, str, arrayList);
    }

    public String zzcz(String str) {
        return Uri.parse(str).buildUpon().query(null).build().toString();
    }

    public zzgh zzd(Context context, VersionInfoParcel versionInfoParcel) {
        zzgh com_google_android_gms_internal_zzgh;
        synchronized (this.zzako) {
            if (this.zzcnn == null) {
                if (context.getApplicationContext() != null) {
                    context = context.getApplicationContext();
                }
                this.zzcnn = new zzgh(context, versionInfoParcel, (String) zzdr.zzbcx.get());
            }
            com_google_android_gms_internal_zzgh = this.zzcnn;
        }
        return com_google_android_gms_internal_zzgh;
    }

    public void zzd(Context context, String str, String str2) {
        try {
            FileOutputStream openFileOutput = context.openFileOutput(str, 0);
            openFileOutput.write(str2.getBytes(C0989C.UTF8_NAME));
            openFileOutput.close();
        } catch (Throwable e) {
            zzb.zzb("Error writing to file in internal storage.", e);
        }
    }

    public int zzda(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            String valueOf = String.valueOf(e);
            zzb.zzdi(new StringBuilder(String.valueOf(valueOf).length() + 22).append("Could not parse value:").append(valueOf).toString());
            return 0;
        }
    }

    public boolean zzdb(String str) {
        return TextUtils.isEmpty(str) ? false : str.matches("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|webp))$)");
    }

    public float zzfr() {
        zzo zzfq = zzu.zzhg().zzfq();
        return (zzfq == null || !zzfq.zzfs()) ? 1.0f : zzfq.zzfr();
    }

    public boolean zzft() {
        zzo zzfq = zzu.zzhg().zzfq();
        return zzfq != null ? zzfq.zzft() : false;
    }

    public Map<String, String> zzg(Uri uri) {
        if (uri == null) {
            return null;
        }
        HashMap hashMap = new HashMap();
        for (String str : zzu.zzgo().zzh(uri)) {
            hashMap.put(str, uri.getQueryParameter(str));
        }
        return hashMap;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.String zzh(android.content.Context r6, java.lang.String r7) {
        /*
        r5 = this;
        r1 = r5.zzako;
        monitor-enter(r1);
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x000b;
    L_0x0007:
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
    L_0x000a:
        return r0;
    L_0x000b:
        if (r7 != 0) goto L_0x0016;
    L_0x000d:
        r0 = r5.zzvq();	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x000a;
    L_0x0013:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        throw r0;
    L_0x0016:
        r0 = com.google.android.gms.ads.internal.zzu.zzgo();	 Catch:{ Exception -> 0x00b7 }
        r0 = r0.getDefaultUserAgent(r6);	 Catch:{ Exception -> 0x00b7 }
        r5.zzbre = r0;	 Catch:{ Exception -> 0x00b7 }
    L_0x0020:
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        r0 = android.text.TextUtils.isEmpty(r0);	 Catch:{ all -> 0x0013 }
        if (r0 == 0) goto L_0x0072;
    L_0x0028:
        r0 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ all -> 0x0013 }
        r0 = r0.zzwq();	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x006c;
    L_0x0032:
        r0 = 0;
        r5.zzbre = r0;	 Catch:{ all -> 0x0013 }
        r0 = zzcvl;	 Catch:{ all -> 0x0013 }
        r2 = new com.google.android.gms.internal.zzlb$2;	 Catch:{ all -> 0x0013 }
        r2.<init>(r5, r6);	 Catch:{ all -> 0x0013 }
        r0.post(r2);	 Catch:{ all -> 0x0013 }
    L_0x003f:
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        if (r0 != 0) goto L_0x0072;
    L_0x0043:
        r0 = r5.zzako;	 Catch:{ InterruptedException -> 0x0049 }
        r0.wait();	 Catch:{ InterruptedException -> 0x0049 }
        goto L_0x003f;
    L_0x0049:
        r0 = move-exception;
        r0 = r5.zzvq();	 Catch:{ all -> 0x0013 }
        r5.zzbre = r0;	 Catch:{ all -> 0x0013 }
        r2 = "Interrupted, use default user agent: ";
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0013 }
        r3 = r0.length();	 Catch:{ all -> 0x0013 }
        if (r3 == 0) goto L_0x0066;
    L_0x005e:
        r0 = r2.concat(r0);	 Catch:{ all -> 0x0013 }
    L_0x0062:
        com.google.android.gms.ads.internal.util.client.zzb.zzdi(r0);	 Catch:{ all -> 0x0013 }
        goto L_0x003f;
    L_0x0066:
        r0 = new java.lang.String;	 Catch:{ all -> 0x0013 }
        r0.<init>(r2);	 Catch:{ all -> 0x0013 }
        goto L_0x0062;
    L_0x006c:
        r0 = r5.zzaa(r6);	 Catch:{ Exception -> 0x00af }
        r5.zzbre = r0;	 Catch:{ Exception -> 0x00af }
    L_0x0072:
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        r0 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0013 }
        r2 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0013 }
        r3 = java.lang.String.valueOf(r0);	 Catch:{ all -> 0x0013 }
        r3 = r3.length();	 Catch:{ all -> 0x0013 }
        r3 = r3 + 11;
        r4 = java.lang.String.valueOf(r7);	 Catch:{ all -> 0x0013 }
        r4 = r4.length();	 Catch:{ all -> 0x0013 }
        r3 = r3 + r4;
        r2.<init>(r3);	 Catch:{ all -> 0x0013 }
        r0 = r2.append(r0);	 Catch:{ all -> 0x0013 }
        r2 = " (Mobile; ";
        r0 = r0.append(r2);	 Catch:{ all -> 0x0013 }
        r0 = r0.append(r7);	 Catch:{ all -> 0x0013 }
        r2 = ")";
        r0 = r0.append(r2);	 Catch:{ all -> 0x0013 }
        r0 = r0.toString();	 Catch:{ all -> 0x0013 }
        r5.zzbre = r0;	 Catch:{ all -> 0x0013 }
        r0 = r5.zzbre;	 Catch:{ all -> 0x0013 }
        monitor-exit(r1);	 Catch:{ all -> 0x0013 }
        goto L_0x000a;
    L_0x00af:
        r0 = move-exception;
        r0 = r5.zzvq();	 Catch:{ all -> 0x0013 }
        r5.zzbre = r0;	 Catch:{ all -> 0x0013 }
        goto L_0x0072;
    L_0x00b7:
        r0 = move-exception;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlb.zzh(android.content.Context, java.lang.String):java.lang.String");
    }

    public int[] zzh(Activity activity) {
        Window window = activity.getWindow();
        if (window == null || window.findViewById(16908290) == null) {
            return zzvu();
        }
        return new int[]{window.findViewById(16908290).getWidth(), window.findViewById(16908290).getHeight()};
    }

    public String zzi(Context context, String str) {
        try {
            return new String(com.google.android.gms.common.util.zzo.zza(context.openFileInput(str), true), C0989C.UTF8_NAME);
        } catch (Throwable e) {
            zzb.zzb("Error reading from internal storage.", e);
            return BuildConfig.FLAVOR;
        }
    }

    public int[] zzi(Activity activity) {
        int[] zzh = zzh(activity);
        return new int[]{zzm.zzkr().zzc(activity, zzh[0]), zzm.zzkr().zzc(activity, zzh[1])};
    }

    public int[] zzj(Activity activity) {
        Window window = activity.getWindow();
        if (window == null || window.findViewById(16908290) == null) {
            return zzvu();
        }
        return new int[]{window.findViewById(16908290).getTop(), window.findViewById(16908290).getBottom()};
    }

    public int[] zzk(Activity activity) {
        int[] zzj = zzj(activity);
        return new int[]{zzm.zzkr().zzc(activity, zzj[0]), zzm.zzkr().zzc(activity, zzj[1])};
    }

    public Bitmap zzq(View view) {
        view.setDrawingCacheEnabled(true);
        Bitmap createBitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return createBitmap;
    }

    public int zzt(@Nullable View view) {
        if (view == null) {
            return -1;
        }
        ViewParent parent = view.getParent();
        while (parent != null && !(parent instanceof AdapterView)) {
            parent = parent.getParent();
        }
        return parent == null ? -1 : ((AdapterView) parent).getPositionForView(view);
    }

    public boolean zzvp() {
        return this.zzcvm;
    }

    String zzvq() {
        StringBuffer stringBuffer = new StringBuffer(AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
        stringBuffer.append("Mozilla/5.0 (Linux; U; Android");
        if (VERSION.RELEASE != null) {
            stringBuffer.append(" ").append(VERSION.RELEASE);
        }
        stringBuffer.append("; ").append(Locale.getDefault());
        if (Build.DEVICE != null) {
            stringBuffer.append("; ").append(Build.DEVICE);
            if (Build.DISPLAY != null) {
                stringBuffer.append(" Build/").append(Build.DISPLAY);
            }
        }
        stringBuffer.append(") AppleWebKit/533 Version/4.0 Safari/533");
        return stringBuffer.toString();
    }

    public String zzvr() {
        return UUID.randomUUID().toString();
    }

    public String zzvs() {
        UUID randomUUID = UUID.randomUUID();
        byte[] toByteArray = BigInteger.valueOf(randomUUID.getLeastSignificantBits()).toByteArray();
        byte[] toByteArray2 = BigInteger.valueOf(randomUUID.getMostSignificantBits()).toByteArray();
        String bigInteger = new BigInteger(1, toByteArray).toString();
        for (int i = 0; i < 2; i++) {
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                instance.update(toByteArray);
                instance.update(toByteArray2);
                Object obj = new byte[8];
                System.arraycopy(instance.digest(), 0, obj, 0, 8);
                bigInteger = new BigInteger(1, obj).toString();
            } catch (NoSuchAlgorithmException e) {
            }
        }
        return bigInteger;
    }

    public String zzvt() {
        String str = Build.MANUFACTURER;
        String str2 = Build.MODEL;
        return str2.startsWith(str) ? str2 : new StringBuilder((String.valueOf(str).length() + 1) + String.valueOf(str2).length()).append(str).append(" ").append(str2).toString();
    }

    protected int[] zzvu() {
        return new int[]{0, 0};
    }

    public Bundle zzvv() {
        Bundle bundle = new Bundle();
        try {
            if (((Boolean) zzdr.zzbec.get()).booleanValue()) {
                Parcelable memoryInfo = new MemoryInfo();
                Debug.getMemoryInfo(memoryInfo);
                bundle.putParcelable("debug_memory_info", memoryInfo);
            }
            if (((Boolean) zzdr.zzbed.get()).booleanValue()) {
                Runtime runtime = Runtime.getRuntime();
                bundle.putLong("runtime_free_memory", runtime.freeMemory());
                bundle.putLong("runtime_max_memory", runtime.maxMemory());
                bundle.putLong("runtime_total_memory", runtime.totalMemory());
            }
        } catch (Throwable e) {
            zzb.zzc("Unable to gather memory stats", e);
        }
        return bundle;
    }

    public boolean zzy(Context context) {
        Intent intent = new Intent();
        intent.setClassName(context, AdActivity.CLASS_NAME);
        ResolveInfo resolveActivity = context.getPackageManager().resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (resolveActivity == null || resolveActivity.activityInfo == null) {
            zzb.zzdi("Could not find com.google.android.gms.ads.AdActivity, please make sure it is declared in AndroidManifest.xml.");
            return false;
        }
        boolean z;
        String str = "com.google.android.gms.ads.AdActivity requires the android:configChanges value to contain \"%s\".";
        if ((resolveActivity.activityInfo.configChanges & 16) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"keyboard"}));
            z = false;
        } else {
            z = true;
        }
        if ((resolveActivity.activityInfo.configChanges & 32) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"keyboardHidden"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"orientation"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"screenLayout"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AdRequest.MAX_CONTENT_URL_LENGTH) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"uiMode"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT) == 0) {
            zzb.zzdi(String.format(str, new Object[]{"screenSize"}));
            z = false;
        }
        if ((resolveActivity.activityInfo.configChanges & ItemAnimator.FLAG_MOVED) != 0) {
            return z;
        }
        zzb.zzdi(String.format(str, new Object[]{"smallestScreenSize"}));
        return false;
    }

    public boolean zzz(Context context) {
        if (this.zzcvn) {
            return false;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.getApplicationContext().registerReceiver(new zza(), intentFilter);
        this.zzcvn = true;
        return true;
    }
}
