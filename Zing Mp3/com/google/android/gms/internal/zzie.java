package com.google.android.gms.internal;

import android.content.Context;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@zzji
public class zzie implements zzic {
    private final Context mContext;
    final Set<WebView> zzcev;

    /* renamed from: com.google.android.gms.internal.zzie.1 */
    class C13841 implements Runnable {
        final /* synthetic */ String zzcew;
        final /* synthetic */ String zzcex;
        final /* synthetic */ zzie zzcey;

        /* renamed from: com.google.android.gms.internal.zzie.1.1 */
        class C13831 extends WebViewClient {
            final /* synthetic */ WebView zzawv;
            final /* synthetic */ C13841 zzcez;

            C13831(C13841 c13841, WebView webView) {
                this.zzcez = c13841;
                this.zzawv = webView;
            }

            public void onPageFinished(WebView webView, String str) {
                zzb.zzdg("Loading assets have finished");
                this.zzcez.zzcey.zzcev.remove(this.zzawv);
            }

            public void onReceivedError(WebView webView, int i, String str, String str2) {
                zzb.zzdi("Loading assets have failed.");
                this.zzcez.zzcey.zzcev.remove(this.zzawv);
            }
        }

        C13841(zzie com_google_android_gms_internal_zzie, String str, String str2) {
            this.zzcey = com_google_android_gms_internal_zzie;
            this.zzcew = str;
            this.zzcex = str2;
        }

        public void run() {
            WebView zzrm = this.zzcey.zzrm();
            zzrm.setWebViewClient(new C13831(this, zzrm));
            this.zzcey.zzcev.add(zzrm);
            zzrm.loadDataWithBaseURL(this.zzcew, this.zzcex, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
            zzb.zzdg("Fetching assets finished.");
        }
    }

    public zzie(Context context) {
        this.zzcev = Collections.synchronizedSet(new HashSet());
        this.mContext = context;
    }

    public void zza(String str, String str2, String str3) {
        zzb.zzdg("Fetching assets for the given html");
        zzlb.zzcvl.post(new C13841(this, str2, str3));
    }

    public WebView zzrm() {
        WebView webView = new WebView(this.mContext);
        webView.getSettings().setJavaScriptEnabled(true);
        return webView;
    }
}
