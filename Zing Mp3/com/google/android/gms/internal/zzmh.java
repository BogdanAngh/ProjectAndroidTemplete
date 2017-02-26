package com.google.android.gms.internal;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import android.webkit.DownloadListener;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.text.Cue;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzs;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
class zzmh extends WebView implements OnGlobalLayoutListener, DownloadListener, zzmd {
    private zzd f1523a;
    private boolean f1524b;
    private boolean f1525c;
    private boolean f1526d;
    private boolean f1527e;
    private int f1528f;
    private boolean f1529g;
    boolean f1530h;
    private zzmi f1531i;
    private boolean f1532j;
    private boolean f1533k;
    private zzg f1534l;
    private int f1535m;
    private int f1536n;
    private zzdx f1537o;
    private zzdx f1538p;
    private zzdy f1539q;
    private WeakReference<OnClickListener> f1540r;
    private zzd f1541s;
    private Map<String, zzfs> f1542t;
    private final Object zzako;
    private final com.google.android.gms.ads.internal.zzd zzamb;
    private final VersionInfoParcel zzanu;
    private AdSizeParcel zzapp;
    private zzlp zzass;
    private final WindowManager zzati;
    @Nullable
    private final zzav zzbnx;
    private int zzbzd;
    private int zzbze;
    private int zzbzg;
    private int zzbzh;
    private String zzcec;
    private zzdx zzced;
    private Boolean zzcub;
    private final zza zzczx;
    private final zzs zzczy;
    private zzme zzczz;

    /* renamed from: com.google.android.gms.internal.zzmh.1 */
    class C14631 implements zzfe {
        final /* synthetic */ zzmh f1520u;

        C14631(zzmh com_google_android_gms_internal_zzmh) {
            this.f1520u = com_google_android_gms_internal_zzmh;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (map != null) {
                String str = (String) map.get("height");
                if (!TextUtils.isEmpty(str)) {
                    try {
                        int parseInt = Integer.parseInt(str);
                        synchronized (this.f1520u.zzako) {
                            if (this.f1520u.f1536n != parseInt) {
                                this.f1520u.f1536n = parseInt;
                                this.f1520u.requestLayout();
                            }
                        }
                    } catch (Throwable e) {
                        zzb.zzc("Exception occurred while getting webview content height", e);
                    }
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmh.2 */
    class C14642 implements Runnable {
        final /* synthetic */ zzmh f1521u;

        C14642(zzmh com_google_android_gms_internal_zzmh) {
            this.f1521u = com_google_android_gms_internal_zzmh;
        }

        public void run() {
            super.destroy();
        }
    }

    @zzji
    public static class zza extends MutableContextWrapper {
        private Context f1522v;
        private Context zzatc;
        private Activity zzcxl;

        public zza(Context context) {
            super(context);
            setBaseContext(context);
        }

        public Object getSystemService(String str) {
            return this.f1522v.getSystemService(str);
        }

        public void setBaseContext(Context context) {
            this.zzatc = context.getApplicationContext();
            this.zzcxl = context instanceof Activity ? (Activity) context : null;
            this.f1522v = context;
            super.setBaseContext(this.zzatc);
        }

        public void startActivity(Intent intent) {
            if (this.zzcxl != null) {
                this.zzcxl.startActivity(intent);
                return;
            }
            intent.setFlags(268435456);
            this.zzatc.startActivity(intent);
        }

        public Activity zzwy() {
            return this.zzcxl;
        }

        public Context zzwz() {
            return this.f1522v;
        }
    }

    protected zzmh(zza com_google_android_gms_internal_zzmh_zza, AdSizeParcel adSizeParcel, boolean z, boolean z2, @Nullable zzav com_google_android_gms_internal_zzav, VersionInfoParcel versionInfoParcel, zzdz com_google_android_gms_internal_zzdz, zzs com_google_android_gms_ads_internal_zzs, com.google.android.gms.ads.internal.zzd com_google_android_gms_ads_internal_zzd) {
        super(com_google_android_gms_internal_zzmh_zza);
        this.zzako = new Object();
        this.f1529g = true;
        this.f1530h = false;
        this.zzcec = BuildConfig.FLAVOR;
        this.zzbze = -1;
        this.zzbzd = -1;
        this.zzbzg = -1;
        this.zzbzh = -1;
        this.zzczx = com_google_android_gms_internal_zzmh_zza;
        this.zzapp = adSizeParcel;
        this.f1526d = z;
        this.f1528f = -1;
        this.zzbnx = com_google_android_gms_internal_zzav;
        this.zzanu = versionInfoParcel;
        this.zzczy = com_google_android_gms_ads_internal_zzs;
        this.zzamb = com_google_android_gms_ads_internal_zzd;
        this.zzati = (WindowManager) getContext().getSystemService("window");
        setBackgroundColor(0);
        WebSettings settings = getSettings();
        settings.setAllowFileAccess(false);
        settings.setJavaScriptEnabled(true);
        settings.setSavePassword(false);
        settings.setSupportMultipleWindows(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        if (VERSION.SDK_INT >= 21) {
            settings.setMixedContentMode(2);
        }
        zzu.zzgm().zza((Context) com_google_android_gms_internal_zzmh_zza, versionInfoParcel.zzda, settings);
        zzu.zzgo().zza(getContext(), settings);
        setDownloadListener(this);
        zzym();
        if (com.google.android.gms.common.util.zzs.zzays()) {
            addJavascriptInterface(new zzmj(this), "googleAdsJsInterface");
        }
        if (com.google.android.gms.common.util.zzs.zzayn()) {
            removeJavascriptInterface("accessibility");
            removeJavascriptInterface("accessibilityTraversal");
        }
        this.zzass = new zzlp(this.zzczx.zzwy(), this, this, null);
        zzd(com_google_android_gms_internal_zzdz);
    }

    private void zzap(boolean z) {
        Map hashMap = new HashMap();
        hashMap.put("isVisible", z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        zza("onAdVisibilityChanged", hashMap);
    }

    static zzmh zzb(Context context, AdSizeParcel adSizeParcel, boolean z, boolean z2, @Nullable zzav com_google_android_gms_internal_zzav, VersionInfoParcel versionInfoParcel, zzdz com_google_android_gms_internal_zzdz, zzs com_google_android_gms_ads_internal_zzs, com.google.android.gms.ads.internal.zzd com_google_android_gms_ads_internal_zzd) {
        return new zzmh(new zza(context), adSizeParcel, z, z2, com_google_android_gms_internal_zzav, versionInfoParcel, com_google_android_gms_internal_zzdz, com_google_android_gms_ads_internal_zzs, com_google_android_gms_ads_internal_zzd);
    }

    private void zzd(zzdz com_google_android_gms_internal_zzdz) {
        zzyq();
        this.f1539q = new zzdy(new zzdz(true, "make_wv", this.zzapp.zzazq));
        this.f1539q.zzly().zzc(com_google_android_gms_internal_zzdz);
        this.zzced = zzdv.zzb(this.f1539q.zzly());
        this.f1539q.zza("native:view_create", this.zzced);
        this.f1538p = null;
        this.f1537o = null;
    }

    private void zzyi() {
        synchronized (this.zzako) {
            this.zzcub = zzu.zzgq().zzva();
            if (this.zzcub == null) {
                try {
                    evaluateJavascript("(function(){})()", null);
                    zzb(Boolean.valueOf(true));
                } catch (IllegalStateException e) {
                    zzb(Boolean.valueOf(false));
                }
            }
        }
    }

    private void zzyj() {
        zzdv.zza(this.f1539q.zzly(), this.zzced, "aeh2");
    }

    private void zzyk() {
        zzdv.zza(this.f1539q.zzly(), this.zzced, "aebb2");
    }

    private void zzym() {
        synchronized (this.zzako) {
            if (this.f1526d || this.zzapp.zzazr) {
                if (VERSION.SDK_INT < 14) {
                    zzb.zzdg("Disabling hardware acceleration on an overlay.");
                    zzyn();
                } else {
                    zzb.zzdg("Enabling hardware acceleration on an overlay.");
                    zzyo();
                }
            } else if (VERSION.SDK_INT < 18) {
                zzb.zzdg("Disabling hardware acceleration on an AdView.");
                zzyn();
            } else {
                zzb.zzdg("Enabling hardware acceleration on an AdView.");
                zzyo();
            }
        }
    }

    private void zzyn() {
        synchronized (this.zzako) {
            if (!this.f1527e) {
                zzu.zzgo().zzv(this);
            }
            this.f1527e = true;
        }
    }

    private void zzyo() {
        synchronized (this.zzako) {
            if (this.f1527e) {
                zzu.zzgo().zzu(this);
            }
            this.f1527e = false;
        }
    }

    private void zzyp() {
        synchronized (this.zzako) {
            this.f1542t = null;
        }
    }

    private void zzyq() {
        if (this.f1539q != null) {
            zzdz zzly = this.f1539q.zzly();
            if (zzly != null && zzu.zzgq().zzuu() != null) {
                zzu.zzgq().zzuu().zza(zzly);
            }
        }
    }

    public void destroy() {
        synchronized (this.zzako) {
            zzyq();
            this.zzass.zzwm();
            if (this.f1523a != null) {
                this.f1523a.close();
                this.f1523a.onDestroy();
                this.f1523a = null;
            }
            this.zzczz.reset();
            if (this.f1525c) {
                return;
            }
            zzu.zzhj().zze(this);
            zzyp();
            this.f1525c = true;
            zzkx.m1697v("Initiating WebView self destruct sequence in 3...");
            this.zzczz.zzxz();
        }
    }

    @TargetApi(19)
    public void evaluateJavascript(String str, ValueCallback<String> valueCallback) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                }
                return;
            }
            super.evaluateJavascript(str, valueCallback);
        }
    }

    protected void finalize() throws Throwable {
        synchronized (this.zzako) {
            if (!this.f1525c) {
                this.zzczz.reset();
                zzu.zzhj().zze(this);
                zzyp();
            }
        }
        super.finalize();
    }

    public String getRequestId() {
        String str;
        synchronized (this.zzako) {
            str = this.zzcec;
        }
        return str;
    }

    public int getRequestedOrientation() {
        int i;
        synchronized (this.zzako) {
            i = this.f1528f;
        }
        return i;
    }

    public View getView() {
        return this;
    }

    public WebView getWebView() {
        return this;
    }

    public boolean isDestroyed() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1525c;
        }
        return z;
    }

    public void loadData(String str, String str2, String str3) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
            } else {
                super.loadData(str, str2, str3);
            }
        }
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
            } else {
                super.loadDataWithBaseURL(str, str2, str3, str4, str5);
            }
        }
    }

    public void loadUrl(String str) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
            } else {
                try {
                    super.loadUrl(str);
                } catch (Throwable th) {
                    String valueOf = String.valueOf(th);
                    zzb.zzdi(new StringBuilder(String.valueOf(valueOf).length() + 24).append("Could not call loadUrl. ").append(valueOf).toString());
                }
            }
        }
    }

    protected void onAttachedToWindow() {
        boolean z = true;
        synchronized (this.zzako) {
            super.onAttachedToWindow();
            if (!isDestroyed()) {
                this.zzass.onAttachedToWindow();
            }
            boolean z2 = this.f1532j;
            if (zzxc() == null || !zzxc().zzxv()) {
                z = z2;
            } else if (!this.f1533k) {
                OnGlobalLayoutListener zzxw = zzxc().zzxw();
                if (zzxw != null) {
                    zzu.zzhk().zza(getView(), zzxw);
                }
                OnScrollChangedListener zzxx = zzxc().zzxx();
                if (zzxx != null) {
                    zzu.zzhk().zza(getView(), zzxx);
                }
                this.f1533k = true;
            }
            zzap(z);
        }
    }

    protected void onDetachedFromWindow() {
        synchronized (this.zzako) {
            if (!isDestroyed()) {
                this.zzass.onDetachedFromWindow();
            }
            super.onDetachedFromWindow();
            if (this.f1533k && zzxc() != null && zzxc().zzxv() && getViewTreeObserver() != null && getViewTreeObserver().isAlive()) {
                OnGlobalLayoutListener zzxw = zzxc().zzxw();
                if (zzxw != null) {
                    zzu.zzgo().zza(getViewTreeObserver(), zzxw);
                }
                OnScrollChangedListener zzxx = zzxc().zzxx();
                if (zzxx != null) {
                    getViewTreeObserver().removeOnScrollChangedListener(zzxx);
                }
                this.f1533k = false;
            }
        }
        zzap(false);
    }

    public void onDownloadStart(String str, String str2, String str3, String str4, long j) {
        try {
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.setDataAndType(Uri.parse(str), str4);
            zzu.zzgm().zzb(getContext(), intent);
        } catch (ActivityNotFoundException e) {
            zzb.zzdg(new StringBuilder((String.valueOf(str).length() + 51) + String.valueOf(str4).length()).append("Couldn't find an Activity to view url/mimetype: ").append(str).append(" / ").append(str4).toString());
        }
    }

    @TargetApi(21)
    protected void onDraw(Canvas canvas) {
        if (!isDestroyed()) {
            if (VERSION.SDK_INT != 21 || !canvas.isHardwareAccelerated() || isAttachedToWindow()) {
                super.onDraw(canvas);
                if (zzxc() != null && zzxc().zzyg() != null) {
                    zzxc().zzyg().zzff();
                }
            }
        }
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        if (((Boolean) zzdr.zzbfv.get()).booleanValue()) {
            float axisValue = motionEvent.getAxisValue(9);
            float axisValue2 = motionEvent.getAxisValue(10);
            if ((motionEvent.getActionMasked() == 8 ? 1 : 0) != 0 && ((axisValue > 0.0f && !canScrollVertically(-1)) || ((axisValue < 0.0f && !canScrollVertically(1)) || ((axisValue2 > 0.0f && !canScrollHorizontally(-1)) || (axisValue2 < 0.0f && !canScrollHorizontally(1)))))) {
                return false;
            }
        }
        return super.onGenericMotionEvent(motionEvent);
    }

    public void onGlobalLayout() {
        boolean zzyh = zzyh();
        zzd zzxa = zzxa();
        if (zzxa != null && zzyh) {
            zzxa.zzpr();
        }
    }

    @SuppressLint({"DrawAllocation"})
    protected void onMeasure(int i, int i2) {
        int i3 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        synchronized (this.zzako) {
            if (isDestroyed()) {
                setMeasuredDimension(0, 0);
            } else if (isInEditMode() || this.f1526d || this.zzapp.zzazt) {
                super.onMeasure(i, i2);
            } else if (this.zzapp.zzazu) {
                if (((Boolean) zzdr.zzbjk.get()).booleanValue() || !com.google.android.gms.common.util.zzs.zzays()) {
                    super.onMeasure(i, i2);
                    return;
                }
                zza("/contentHeight", zzyl());
                zzdn("(function() {  var height = -1;  if (document.body) { height = document.body.offsetHeight;}  else if (document.documentElement) {      height = document.documentElement.offsetHeight;  }  var url = 'gmsg://mobileads.google.com/contentHeight?';  url += 'height=' + height;  window.googleAdsJsInterface.notify(url);  })();");
                r0 = this.zzczx.getResources().getDisplayMetrics().density;
                r1 = MeasureSpec.getSize(i);
                switch (this.f1536n) {
                    case CommonStatusCodes.SUCCESS_CACHE /*-1*/:
                        i3 = MeasureSpec.getSize(i2);
                        break;
                    default:
                        i3 = (int) (r0 * ((float) this.f1536n));
                        break;
                }
                setMeasuredDimension(r1, i3);
            } else if (this.zzapp.zzazr) {
                DisplayMetrics displayMetrics = new DisplayMetrics();
                this.zzati.getDefaultDisplay().getMetrics(displayMetrics);
                setMeasuredDimension(displayMetrics.widthPixels, displayMetrics.heightPixels);
            } else {
                int mode = MeasureSpec.getMode(i);
                int size = MeasureSpec.getSize(i);
                int mode2 = MeasureSpec.getMode(i2);
                r1 = MeasureSpec.getSize(i2);
                mode = (mode == Cue.TYPE_UNSET || mode == C0989C.ENCODING_PCM_32BIT) ? size : Integer.MAX_VALUE;
                if (mode2 == Cue.TYPE_UNSET || mode2 == C0989C.ENCODING_PCM_32BIT) {
                    i3 = r1;
                }
                if (this.zzapp.widthPixels > mode || this.zzapp.heightPixels > r0) {
                    r0 = this.zzczx.getResources().getDisplayMetrics().density;
                    mode2 = (int) (((float) this.zzapp.heightPixels) / r0);
                    size = (int) (((float) size) / r0);
                    zzb.zzdi(new StringBuilder(C1569R.styleable.AppCompatTheme_buttonStyleSmall).append("Not enough space to show ad. Needs ").append((int) (((float) this.zzapp.widthPixels) / r0)).append("x").append(mode2).append(" dp, but only has ").append(size).append("x").append((int) (((float) r1) / r0)).append(" dp.").toString());
                    if (getVisibility() != 8) {
                        setVisibility(4);
                    }
                    setMeasuredDimension(0, 0);
                } else {
                    if (getVisibility() != 8) {
                        setVisibility(0);
                    }
                    setMeasuredDimension(this.zzapp.widthPixels, this.zzapp.heightPixels);
                }
            }
        }
    }

    public void onPause() {
        if (!isDestroyed()) {
            try {
                if (com.google.android.gms.common.util.zzs.zzayn()) {
                    super.onPause();
                }
            } catch (Throwable e) {
                zzb.zzb("Could not pause webview.", e);
            }
        }
    }

    public void onResume() {
        if (!isDestroyed()) {
            try {
                if (com.google.android.gms.common.util.zzs.zzayn()) {
                    super.onResume();
                }
            } catch (Throwable e) {
                zzb.zzb("Could not resume webview.", e);
            }
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (zzxc().zzxv()) {
            synchronized (this.zzako) {
                if (this.f1534l != null) {
                    this.f1534l.zzc(motionEvent);
                }
            }
        } else if (this.zzbnx != null) {
            this.zzbnx.zza(motionEvent);
        }
        return isDestroyed() ? false : super.onTouchEvent(motionEvent);
    }

    public void setContext(Context context) {
        this.zzczx.setBaseContext(context);
        this.zzass.zzl(this.zzczx.zzwy());
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.f1540r = new WeakReference(onClickListener);
        super.setOnClickListener(onClickListener);
    }

    public void setRequestedOrientation(int i) {
        synchronized (this.zzako) {
            this.f1528f = i;
            if (this.f1523a != null) {
                this.f1523a.setRequestedOrientation(this.f1528f);
            }
        }
    }

    public void setWebViewClient(WebViewClient webViewClient) {
        super.setWebViewClient(webViewClient);
        if (webViewClient instanceof zzme) {
            this.zzczz = (zzme) webViewClient;
        }
    }

    public void stopLoading() {
        if (!isDestroyed()) {
            try {
                super.stopLoading();
            } catch (Throwable e) {
                zzb.zzb("Could not stop loading webview.", e);
            }
        }
    }

    public void zza(Context context, AdSizeParcel adSizeParcel, zzdz com_google_android_gms_internal_zzdz) {
        synchronized (this.zzako) {
            this.zzass.zzwm();
            setContext(context);
            this.f1523a = null;
            this.zzapp = adSizeParcel;
            this.f1526d = false;
            this.f1524b = false;
            this.zzcec = BuildConfig.FLAVOR;
            this.f1528f = -1;
            zzu.zzgo().zzm(this);
            loadUrl("about:blank");
            this.zzczz.reset();
            setOnTouchListener(null);
            setOnClickListener(null);
            this.f1529g = true;
            this.f1530h = false;
            this.f1531i = null;
            zzd(com_google_android_gms_internal_zzdz);
            this.f1532j = false;
            this.f1535m = 0;
            zzu.zzhj().zze(this);
            zzyp();
        }
    }

    public void zza(AdSizeParcel adSizeParcel) {
        synchronized (this.zzako) {
            this.zzapp = adSizeParcel;
            requestLayout();
        }
    }

    public void zza(com.google.android.gms.internal.zzcu.zza com_google_android_gms_internal_zzcu_zza) {
        synchronized (this.zzako) {
            this.f1532j = com_google_android_gms_internal_zzcu_zza.zzave;
        }
        zzap(com_google_android_gms_internal_zzcu_zza.zzave);
    }

    public void zza(zzmi com_google_android_gms_internal_zzmi) {
        synchronized (this.zzako) {
            if (this.f1531i != null) {
                zzb.m1695e("Attempt to create multiple AdWebViewVideoControllers.");
                return;
            }
            this.f1531i = com_google_android_gms_internal_zzmi;
        }
    }

    @TargetApi(19)
    protected void zza(String str, ValueCallback<String> valueCallback) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
                if (valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                }
            } else {
                evaluateJavascript(str, valueCallback);
            }
        }
    }

    public void zza(String str, zzfe com_google_android_gms_internal_zzfe) {
        if (this.zzczz != null) {
            this.zzczz.zza(str, com_google_android_gms_internal_zzfe);
        }
    }

    public void zza(String str, Map<String, ?> map) {
        try {
            zzb(str, zzu.zzgm().zzap(map));
        } catch (JSONException e) {
            zzb.zzdi("Could not convert parameters to JSON.");
        }
    }

    public void zza(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        zzi(str, jSONObject.toString());
    }

    public void zzak(int i) {
        if (i == 0) {
            zzyk();
        }
        zzyj();
        if (this.f1539q.zzly() != null) {
            this.f1539q.zzly().zzg("close_type", String.valueOf(i));
        }
        Map hashMap = new HashMap(2);
        hashMap.put("closetype", String.valueOf(i));
        hashMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, this.zzanu.zzda);
        zza("onhide", hashMap);
    }

    public void zzak(boolean z) {
        synchronized (this.zzako) {
            this.f1526d = z;
            zzym();
        }
    }

    public void zzal(boolean z) {
        synchronized (this.zzako) {
            if (this.f1523a != null) {
                this.f1523a.zza(this.zzczz.zzic(), z);
            } else {
                this.f1524b = z;
            }
        }
    }

    public void zzam(boolean z) {
        synchronized (this.zzako) {
            this.f1529g = z;
        }
    }

    public void zzan(boolean z) {
        synchronized (this.zzako) {
            this.f1535m = (z ? 1 : -1) + this.f1535m;
            if (this.f1535m <= 0 && this.f1523a != null) {
                this.f1523a.zzpu();
            }
        }
    }

    public void zzb(zzg com_google_android_gms_ads_internal_formats_zzg) {
        synchronized (this.zzako) {
            this.f1534l = com_google_android_gms_ads_internal_formats_zzg;
        }
    }

    public void zzb(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        synchronized (this.zzako) {
            this.f1523a = com_google_android_gms_ads_internal_overlay_zzd;
        }
    }

    void zzb(Boolean bool) {
        synchronized (this.zzako) {
            this.zzcub = bool;
        }
        zzu.zzgq().zzb(bool);
    }

    public void zzb(String str, zzfe com_google_android_gms_internal_zzfe) {
        if (this.zzczz != null) {
            this.zzczz.zzb(str, com_google_android_gms_internal_zzfe);
        }
    }

    public void zzb(String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String jSONObject2 = jSONObject.toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(window.AFMA_ReceiveMessage || function() {})('");
        stringBuilder.append(str);
        stringBuilder.append("'");
        stringBuilder.append(",");
        stringBuilder.append(jSONObject2);
        stringBuilder.append(");");
        String str2 = "Dispatching AFMA event: ";
        jSONObject2 = String.valueOf(stringBuilder.toString());
        zzb.zzdg(jSONObject2.length() != 0 ? str2.concat(jSONObject2) : new String(str2));
        zzdn(stringBuilder.toString());
    }

    public void zzc(zzd com_google_android_gms_ads_internal_overlay_zzd) {
        synchronized (this.zzako) {
            this.f1541s = com_google_android_gms_ads_internal_overlay_zzd;
        }
    }

    public void zzdj(String str) {
        synchronized (this.zzako) {
            try {
                super.loadUrl(str);
            } catch (Throwable th) {
                String valueOf = String.valueOf(th);
                zzb.zzdi(new StringBuilder(String.valueOf(valueOf).length() + 24).append("Could not call loadUrl. ").append(valueOf).toString());
            }
        }
    }

    public void zzdk(String str) {
        synchronized (this.zzako) {
            if (str == null) {
                str = BuildConfig.FLAVOR;
            }
            this.zzcec = str;
        }
    }

    protected void zzdm(String str) {
        synchronized (this.zzako) {
            if (isDestroyed()) {
                zzb.zzdi("The webview is destroyed. Ignoring action.");
            } else {
                loadUrl(str);
            }
        }
    }

    protected void zzdn(String str) {
        if (com.google.android.gms.common.util.zzs.zzayu()) {
            if (zzva() == null) {
                zzyi();
            }
            if (zzva().booleanValue()) {
                zza(str, null);
                return;
            }
            String str2 = "javascript:";
            String valueOf = String.valueOf(str);
            zzdm(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
            return;
        }
        str2 = "javascript:";
        valueOf = String.valueOf(str);
        zzdm(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
    }

    public com.google.android.gms.ads.internal.zzd zzec() {
        return this.zzamb;
    }

    public AdSizeParcel zzeg() {
        AdSizeParcel adSizeParcel;
        synchronized (this.zzako) {
            adSizeParcel = this.zzapp;
        }
        return adSizeParcel;
    }

    public void zzey() {
        synchronized (this.zzako) {
            this.f1530h = true;
            if (this.zzczy != null) {
                this.zzczy.zzey();
            }
        }
    }

    public void zzez() {
        synchronized (this.zzako) {
            this.f1530h = false;
            if (this.zzczy != null) {
                this.zzczy.zzez();
            }
        }
    }

    public void zzi(String str, String str2) {
        zzdn(new StringBuilder((String.valueOf(str).length() + 3) + String.valueOf(str2).length()).append(str).append("(").append(str2).append(");").toString());
    }

    public void zzps() {
        if (this.f1537o == null) {
            zzdv.zza(this.f1539q.zzly(), this.zzced, "aes2");
            this.f1537o = zzdv.zzb(this.f1539q.zzly());
            this.f1539q.zza("native:view_show", this.f1537o);
        }
        Map hashMap = new HashMap(1);
        hashMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, this.zzanu.zzda);
        zza("onshow", hashMap);
    }

    Boolean zzva() {
        Boolean bool;
        synchronized (this.zzako) {
            bool = this.zzcub;
        }
        return bool;
    }

    public void zzww() {
        zzyj();
        Map hashMap = new HashMap(1);
        hashMap.put(ServerProtocol.FALLBACK_DIALOG_PARAM_VERSION, this.zzanu.zzda);
        zza("onhide", hashMap);
    }

    public void zzwx() {
        Map hashMap = new HashMap(3);
        hashMap.put("app_muted", String.valueOf(zzu.zzgm().zzft()));
        hashMap.put("app_volume", String.valueOf(zzu.zzgm().zzfr()));
        hashMap.put("device_volume", String.valueOf(zzu.zzgm().zzah(getContext())));
        zza("volume", hashMap);
    }

    public Activity zzwy() {
        return this.zzczx.zzwy();
    }

    public Context zzwz() {
        return this.zzczx.zzwz();
    }

    public zzd zzxa() {
        zzd com_google_android_gms_ads_internal_overlay_zzd;
        synchronized (this.zzako) {
            com_google_android_gms_ads_internal_overlay_zzd = this.f1523a;
        }
        return com_google_android_gms_ads_internal_overlay_zzd;
    }

    public zzd zzxb() {
        zzd com_google_android_gms_ads_internal_overlay_zzd;
        synchronized (this.zzako) {
            com_google_android_gms_ads_internal_overlay_zzd = this.f1541s;
        }
        return com_google_android_gms_ads_internal_overlay_zzd;
    }

    public zzme zzxc() {
        return this.zzczz;
    }

    public boolean zzxd() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1524b;
        }
        return z;
    }

    public zzav zzxe() {
        return this.zzbnx;
    }

    public VersionInfoParcel zzxf() {
        return this.zzanu;
    }

    public boolean zzxg() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1526d;
        }
        return z;
    }

    public void zzxh() {
        synchronized (this.zzako) {
            zzkx.m1697v("Destroying WebView!");
            zzlb.zzcvl.post(new C14642(this));
        }
    }

    public boolean zzxi() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1529g;
        }
        return z;
    }

    public boolean zzxj() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1530h;
        }
        return z;
    }

    public zzmc zzxk() {
        return null;
    }

    public zzdx zzxl() {
        return this.zzced;
    }

    public zzdy zzxm() {
        return this.f1539q;
    }

    public zzmi zzxn() {
        zzmi com_google_android_gms_internal_zzmi;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzmi = this.f1531i;
        }
        return com_google_android_gms_internal_zzmi;
    }

    public boolean zzxo() {
        boolean z;
        synchronized (this.zzako) {
            z = this.f1535m > 0;
        }
        return z;
    }

    public void zzxp() {
        this.zzass.zzwl();
    }

    public void zzxq() {
        if (this.f1538p == null) {
            this.f1538p = zzdv.zzb(this.f1539q.zzly());
            this.f1539q.zza("native:view_load", this.f1538p);
        }
    }

    public OnClickListener zzxr() {
        return (OnClickListener) this.f1540r.get();
    }

    public zzg zzxs() {
        zzg com_google_android_gms_ads_internal_formats_zzg;
        synchronized (this.zzako) {
            com_google_android_gms_ads_internal_formats_zzg = this.f1534l;
        }
        return com_google_android_gms_ads_internal_formats_zzg;
    }

    public void zzxt() {
        setBackgroundColor(0);
    }

    public boolean zzyh() {
        if (!zzxc().zzic() && !zzxc().zzxv()) {
            return false;
        }
        int i;
        int i2;
        DisplayMetrics zza = zzu.zzgm().zza(this.zzati);
        int zzb = zzm.zzkr().zzb(zza, zza.widthPixels);
        int zzb2 = zzm.zzkr().zzb(zza, zza.heightPixels);
        Activity zzwy = zzwy();
        if (zzwy == null || zzwy.getWindow() == null) {
            i = zzb2;
            i2 = zzb;
        } else {
            int[] zzh = zzu.zzgm().zzh(zzwy);
            i2 = zzm.zzkr().zzb(zza, zzh[0]);
            i = zzm.zzkr().zzb(zza, zzh[1]);
        }
        if (this.zzbzd == zzb && this.zzbze == zzb2 && this.zzbzg == i2 && this.zzbzh == i) {
            return false;
        }
        boolean z = (this.zzbzd == zzb && this.zzbze == zzb2) ? false : true;
        this.zzbzd = zzb;
        this.zzbze = zzb2;
        this.zzbzg = i2;
        this.zzbzh = i;
        new zzhv(this).zza(zzb, zzb2, i2, i, zza.density, this.zzati.getDefaultDisplay().getRotation());
        return z;
    }

    zzfe zzyl() {
        return new C14631(this);
    }
}
