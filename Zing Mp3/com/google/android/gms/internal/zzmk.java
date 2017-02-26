package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
import android.support.v4.media.session.PlaybackStateCompat;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.ConsoleMessage.MessageLevel;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebStorage.QuotaUpdater;
import android.webkit.WebView;
import android.webkit.WebView.WebViewTransport;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.C1569R;

@zzji
@TargetApi(11)
public class zzmk extends WebChromeClient {
    private final zzmd zzbnz;

    /* renamed from: com.google.android.gms.internal.zzmk.1 */
    class C14671 implements OnCancelListener {
        final /* synthetic */ JsResult f1555J;

        C14671(JsResult jsResult) {
            this.f1555J = jsResult;
        }

        public void onCancel(DialogInterface dialogInterface) {
            this.f1555J.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.2 */
    class C14682 implements OnClickListener {
        final /* synthetic */ JsResult f1556J;

        C14682(JsResult jsResult) {
            this.f1556J = jsResult;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f1556J.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.3 */
    class C14693 implements OnClickListener {
        final /* synthetic */ JsResult f1557J;

        C14693(JsResult jsResult) {
            this.f1557J = jsResult;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f1557J.confirm();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.4 */
    class C14704 implements OnCancelListener {
        final /* synthetic */ JsPromptResult f1558K;

        C14704(JsPromptResult jsPromptResult) {
            this.f1558K = jsPromptResult;
        }

        public void onCancel(DialogInterface dialogInterface) {
            this.f1558K.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.5 */
    class C14715 implements OnClickListener {
        final /* synthetic */ JsPromptResult f1559K;

        C14715(JsPromptResult jsPromptResult) {
            this.f1559K = jsPromptResult;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f1559K.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.6 */
    class C14726 implements OnClickListener {
        final /* synthetic */ JsPromptResult f1560K;
        final /* synthetic */ EditText f1561L;

        C14726(JsPromptResult jsPromptResult, EditText editText) {
            this.f1560K = jsPromptResult;
            this.f1561L = editText;
        }

        public void onClick(DialogInterface dialogInterface, int i) {
            this.f1560K.confirm(this.f1561L.getText().toString());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzmk.7 */
    static /* synthetic */ class C14737 {
        static final /* synthetic */ int[] f1562M;

        static {
            f1562M = new int[MessageLevel.values().length];
            try {
                f1562M[MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1562M[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1562M[MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1562M[MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1562M[MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public zzmk(zzmd com_google_android_gms_internal_zzmd) {
        this.zzbnz = com_google_android_gms_internal_zzmd;
    }

    private final Context zza(WebView webView) {
        if (!(webView instanceof zzmd)) {
            return webView.getContext();
        }
        zzmd com_google_android_gms_internal_zzmd = (zzmd) webView;
        Context zzwy = com_google_android_gms_internal_zzmd.zzwy();
        return zzwy == null ? com_google_android_gms_internal_zzmd.getContext() : zzwy;
    }

    private static void zza(Builder builder, String str, JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(17039370, new C14693(jsResult)).setNegativeButton(17039360, new C14682(jsResult)).setOnCancelListener(new C14671(jsResult)).create().show();
    }

    private static void zza(Context context, Builder builder, String str, String str2, JsPromptResult jsPromptResult) {
        View linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(1);
        View textView = new TextView(context);
        textView.setText(str);
        View editText = new EditText(context);
        editText.setText(str2);
        linearLayout.addView(textView);
        linearLayout.addView(editText);
        builder.setView(linearLayout).setPositiveButton(17039370, new C14726(jsPromptResult, editText)).setNegativeButton(17039360, new C14715(jsPromptResult)).setOnCancelListener(new C14704(jsPromptResult)).create().show();
    }

    private final boolean zzyr() {
        return zzu.zzgm().zza(this.zzbnz.getContext().getPackageManager(), this.zzbnz.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION") || zzu.zzgm().zza(this.zzbnz.getContext().getPackageManager(), this.zzbnz.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    public final void onCloseWindow(WebView webView) {
        if (webView instanceof zzmd) {
            zzd zzxa = ((zzmd) webView).zzxa();
            if (zzxa == null) {
                zzb.zzdi("Tried to close an AdWebView not associated with an overlay.");
                return;
            } else {
                zzxa.close();
                return;
            }
        }
        zzb.zzdi("Tried to close a WebView that wasn't an AdWebView.");
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String valueOf = String.valueOf(consoleMessage.message());
        String valueOf2 = String.valueOf(consoleMessage.sourceId());
        valueOf = new StringBuilder((String.valueOf(valueOf).length() + 19) + String.valueOf(valueOf2).length()).append("JS: ").append(valueOf).append(" (").append(valueOf2).append(":").append(consoleMessage.lineNumber()).append(")").toString();
        if (valueOf.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (C14737.f1562M[consoleMessage.messageLevel().ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                zzb.m1695e(valueOf);
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                zzb.zzdi(valueOf);
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                zzb.zzdh(valueOf);
                break;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                zzb.zzdg(valueOf);
                break;
            default:
                zzb.zzdh(valueOf);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView webView, boolean z, boolean z2, Message message) {
        WebViewTransport webViewTransport = (WebViewTransport) message.obj;
        WebView webView2 = new WebView(webView.getContext());
        webView2.setWebViewClient(this.zzbnz.zzxc());
        webViewTransport.setWebView(webView2);
        message.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String str, String str2, long j, long j2, long j3, QuotaUpdater quotaUpdater) {
        long j4 = 5242880 - j3;
        if (j4 <= 0) {
            quotaUpdater.updateQuota(j);
            return;
        }
        if (j == 0) {
            if (j2 > j4 || j2 > 1048576) {
                j2 = 0;
            }
        } else if (j2 == 0) {
            j2 = Math.min(Math.min(PlaybackStateCompat.ACTION_PREPARE_FROM_URI, j4) + j, 1048576);
        } else {
            if (j2 <= Math.min(1048576 - j, j4)) {
                j += j2;
            }
            j2 = j;
        }
        quotaUpdater.updateQuota(j2);
    }

    public final void onGeolocationPermissionsShowPrompt(String str, Callback callback) {
        if (callback != null) {
            callback.invoke(str, zzyr(), true);
        }
    }

    public final void onHideCustomView() {
        zzd zzxa = this.zzbnz.zzxa();
        if (zzxa == null) {
            zzb.zzdi("Could not get ad overlay when hiding custom view.");
        } else {
            zzxa.zzpl();
        }
    }

    public final boolean onJsAlert(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsConfirm(WebView webView, String str, String str2, JsResult jsResult) {
        return zza(zza(webView), str, str2, null, jsResult, null, false);
    }

    public final boolean onJsPrompt(WebView webView, String str, String str2, String str3, JsPromptResult jsPromptResult) {
        return zza(zza(webView), str, str2, str3, null, jsPromptResult, true);
    }

    public final void onReachedMaxAppCacheSize(long j, long j2, QuotaUpdater quotaUpdater) {
        long j3 = PlaybackStateCompat.ACTION_PREPARE_FROM_URI + j;
        if (5242880 - j2 < j3) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j3);
        }
    }

    public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    protected final void zza(View view, int i, CustomViewCallback customViewCallback) {
        zzd zzxa = this.zzbnz.zzxa();
        if (zzxa == null) {
            zzb.zzdi("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        zzxa.zza(view, customViewCallback);
        zzxa.setRequestedOrientation(i);
    }

    protected boolean zza(Context context, String str, String str2, String str3, JsResult jsResult, JsPromptResult jsPromptResult, boolean z) {
        try {
            Builder builder = new Builder(context);
            builder.setTitle(str);
            if (z) {
                zza(context, builder, str2, str3, jsPromptResult);
            } else {
                zza(builder, str2, jsResult);
            }
        } catch (Throwable e) {
            zzb.zzc("Fail to display Dialog.", e);
        }
        return true;
    }
}
