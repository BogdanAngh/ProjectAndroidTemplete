package com.google.android.gms.internal;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.os.Message;
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
import com.google.android.gms.ads.internal.overlay.zzc;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

@zzgd
public class zzii extends WebChromeClient {
    private final zzid zzoA;

    /* renamed from: com.google.android.gms.internal.zzii.1 */
    static class C02531 implements OnCancelListener {
        final /* synthetic */ JsResult zzHA;

        C02531(JsResult jsResult) {
            this.zzHA = jsResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.zzHA.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.2 */
    static class C02542 implements OnClickListener {
        final /* synthetic */ JsResult zzHA;

        C02542(JsResult jsResult) {
            this.zzHA = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzHA.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.3 */
    static class C02553 implements OnClickListener {
        final /* synthetic */ JsResult zzHA;

        C02553(JsResult jsResult) {
            this.zzHA = jsResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzHA.confirm();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.4 */
    static class C02564 implements OnCancelListener {
        final /* synthetic */ JsPromptResult zzHB;

        C02564(JsPromptResult jsPromptResult) {
            this.zzHB = jsPromptResult;
        }

        public void onCancel(DialogInterface dialog) {
            this.zzHB.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.5 */
    static class C02575 implements OnClickListener {
        final /* synthetic */ JsPromptResult zzHB;

        C02575(JsPromptResult jsPromptResult) {
            this.zzHB = jsPromptResult;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzHB.cancel();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.6 */
    static class C02586 implements OnClickListener {
        final /* synthetic */ JsPromptResult zzHB;
        final /* synthetic */ EditText zzHC;

        C02586(JsPromptResult jsPromptResult, EditText editText) {
            this.zzHB = jsPromptResult;
            this.zzHC = editText;
        }

        public void onClick(DialogInterface dialog, int which) {
            this.zzHB.confirm(this.zzHC.getText().toString());
        }
    }

    /* renamed from: com.google.android.gms.internal.zzii.7 */
    static /* synthetic */ class C02597 {
        static final /* synthetic */ int[] zzHD;

        static {
            zzHD = new int[MessageLevel.values().length];
            try {
                zzHD[MessageLevel.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzHD[MessageLevel.WARNING.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzHD[MessageLevel.LOG.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                zzHD[MessageLevel.TIP.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                zzHD[MessageLevel.DEBUG.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
        }
    }

    public zzii(zzid com_google_android_gms_internal_zzid) {
        this.zzoA = com_google_android_gms_internal_zzid;
    }

    private static void zza(Builder builder, String str, JsResult jsResult) {
        builder.setMessage(str).setPositiveButton(17039370, new C02553(jsResult)).setNegativeButton(17039360, new C02542(jsResult)).setOnCancelListener(new C02531(jsResult)).create().show();
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
        builder.setView(linearLayout).setPositiveButton(17039370, new C02586(jsPromptResult, editText)).setNegativeButton(17039360, new C02575(jsPromptResult)).setOnCancelListener(new C02564(jsPromptResult)).create().show();
    }

    private final Context zzc(WebView webView) {
        if (!(webView instanceof zzid)) {
            return webView.getContext();
        }
        zzid com_google_android_gms_internal_zzid = (zzid) webView;
        Context zzgB = com_google_android_gms_internal_zzid.zzgB();
        return zzgB == null ? com_google_android_gms_internal_zzid.getContext() : zzgB;
    }

    private final boolean zzha() {
        return zzo.zzbv().zza(this.zzoA.getContext().getPackageManager(), this.zzoA.getContext().getPackageName(), "android.permission.ACCESS_FINE_LOCATION") || zzo.zzbv().zza(this.zzoA.getContext().getPackageManager(), this.zzoA.getContext().getPackageName(), "android.permission.ACCESS_COARSE_LOCATION");
    }

    public final void onCloseWindow(WebView webView) {
        if (webView instanceof zzid) {
            zzc zzgD = ((zzid) webView).zzgD();
            if (zzgD == null) {
                zzb.zzaC("Tried to close an AdWebView not associated with an overlay.");
                return;
            } else {
                zzgD.close();
                return;
            }
        }
        zzb.zzaC("Tried to close a WebView that wasn't an AdWebView.");
    }

    public final boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        String str = "JS: " + consoleMessage.message() + " (" + consoleMessage.sourceId() + ":" + consoleMessage.lineNumber() + ")";
        if (str.contains("Application Cache")) {
            return super.onConsoleMessage(consoleMessage);
        }
        switch (C02597.zzHD[consoleMessage.messageLevel().ordinal()]) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                zzb.zzaz(str);
                break;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                zzb.zzaC(str);
                break;
            case CompletionEvent.STATUS_CANCELED /*3*/:
            case GameHelper.CLIENT_APPSTATE /*4*/:
                zzb.zzaA(str);
                break;
            case Place.TYPE_ART_GALLERY /*5*/:
                zzb.zzay(str);
                break;
            default:
                zzb.zzaA(str);
                break;
        }
        return super.onConsoleMessage(consoleMessage);
    }

    public final boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        WebViewTransport webViewTransport = (WebViewTransport) resultMsg.obj;
        WebView webView = new WebView(view.getContext());
        webView.setWebViewClient(this.zzoA.zzgF());
        webViewTransport.setWebView(webView);
        resultMsg.sendToTarget();
        return true;
    }

    public final void onExceededDatabaseQuota(String url, String databaseIdentifier, long currentQuota, long estimatedSize, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 5242880 - totalUsedQuota;
        if (j <= 0) {
            quotaUpdater.updateQuota(currentQuota);
            return;
        }
        if (currentQuota == 0) {
            if (estimatedSize > j || estimatedSize > 1048576) {
                estimatedSize = 0;
            }
        } else if (estimatedSize == 0) {
            estimatedSize = Math.min(Math.min(131072, j) + currentQuota, 1048576);
        } else {
            if (estimatedSize <= Math.min(1048576 - currentQuota, j)) {
                currentQuota += estimatedSize;
            }
            estimatedSize = currentQuota;
        }
        quotaUpdater.updateQuota(estimatedSize);
    }

    public final void onGeolocationPermissionsShowPrompt(String origin, Callback callback) {
        if (callback != null) {
            callback.invoke(origin, zzha(), true);
        }
    }

    public final void onHideCustomView() {
        zzc zzgD = this.zzoA.zzgD();
        if (zzgD == null) {
            zzb.zzaC("Could not get ad overlay when hiding custom view.");
        } else {
            zzgD.zzer();
        }
    }

    public final boolean onJsAlert(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsBeforeUnload(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsConfirm(WebView webView, String url, String message, JsResult result) {
        return zza(zzc(webView), url, message, null, result, null, false);
    }

    public final boolean onJsPrompt(WebView webView, String url, String message, String defaultValue, JsPromptResult result) {
        return zza(zzc(webView), url, message, defaultValue, null, result, true);
    }

    public final void onReachedMaxAppCacheSize(long spaceNeeded, long totalUsedQuota, QuotaUpdater quotaUpdater) {
        long j = 131072 + spaceNeeded;
        if (5242880 - totalUsedQuota < j) {
            quotaUpdater.updateQuota(0);
        } else {
            quotaUpdater.updateQuota(j);
        }
    }

    public final void onShowCustomView(View view, CustomViewCallback customViewCallback) {
        zza(view, -1, customViewCallback);
    }

    protected final void zza(View view, int i, CustomViewCallback customViewCallback) {
        zzc zzgD = this.zzoA.zzgD();
        if (zzgD == null) {
            zzb.zzaC("Could not get ad overlay when showing custom view.");
            customViewCallback.onCustomViewHidden();
            return;
        }
        zzgD.zza(view, customViewCallback);
        zzgD.setRequestedOrientation(i);
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
            zzb.zzd("Fail to display Dialog.", e);
        }
        return true;
    }
}
