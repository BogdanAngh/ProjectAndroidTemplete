package com.google.android.gms.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View.MeasureSpec;
import android.webkit.WebView;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;

@zzji
public class zzir implements Runnable {
    private final int zzakh;
    private final int zzaki;
    protected final zzmd zzbnz;
    private final Handler zzcgl;
    private final long zzcgm;
    private long zzcgn;
    private com.google.android.gms.internal.zzme.zza zzcgo;
    protected boolean zzcgp;
    protected boolean zzcgq;

    protected final class zza extends AsyncTask<Void, Void, Boolean> {
        private final WebView zzcgr;
        private Bitmap zzcgs;
        final /* synthetic */ zzir zzcgt;

        public zza(zzir com_google_android_gms_internal_zzir, WebView webView) {
            this.zzcgt = com_google_android_gms_internal_zzir;
            this.zzcgr = webView;
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return zzb((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            zza((Boolean) obj);
        }

        protected synchronized void onPreExecute() {
            this.zzcgs = Bitmap.createBitmap(this.zzcgt.zzakh, this.zzcgt.zzaki, Config.ARGB_8888);
            this.zzcgr.setVisibility(0);
            this.zzcgr.measure(MeasureSpec.makeMeasureSpec(this.zzcgt.zzakh, 0), MeasureSpec.makeMeasureSpec(this.zzcgt.zzaki, 0));
            this.zzcgr.layout(0, 0, this.zzcgt.zzakh, this.zzcgt.zzaki);
            this.zzcgr.draw(new Canvas(this.zzcgs));
            this.zzcgr.invalidate();
        }

        protected void zza(Boolean bool) {
            zzir.zzc(this.zzcgt);
            if (bool.booleanValue() || this.zzcgt.zzsc() || this.zzcgt.zzcgn <= 0) {
                this.zzcgt.zzcgq = bool.booleanValue();
                this.zzcgt.zzcgo.zza(this.zzcgt.zzbnz, true);
            } else if (this.zzcgt.zzcgn > 0) {
                if (zzb.zzbi(2)) {
                    zzb.zzdg("Ad not detected, scheduling another run.");
                }
                this.zzcgt.zzcgl.postDelayed(this.zzcgt, this.zzcgt.zzcgm);
            }
        }

        protected synchronized Boolean zzb(Void... voidArr) {
            Boolean valueOf;
            int width = this.zzcgs.getWidth();
            int height = this.zzcgs.getHeight();
            if (width == 0 || height == 0) {
                valueOf = Boolean.valueOf(false);
            } else {
                int i = 0;
                for (int i2 = 0; i2 < width; i2 += 10) {
                    for (int i3 = 0; i3 < height; i3 += 10) {
                        if (this.zzcgs.getPixel(i2, i3) != 0) {
                            i++;
                        }
                    }
                }
                valueOf = Boolean.valueOf(((double) i) / (((double) (width * height)) / 100.0d) > 0.1d);
            }
            return valueOf;
        }
    }

    public zzir(com.google.android.gms.internal.zzme.zza com_google_android_gms_internal_zzme_zza, zzmd com_google_android_gms_internal_zzmd, int i, int i2) {
        this(com_google_android_gms_internal_zzme_zza, com_google_android_gms_internal_zzmd, i, i2, 200, 50);
    }

    public zzir(com.google.android.gms.internal.zzme.zza com_google_android_gms_internal_zzme_zza, zzmd com_google_android_gms_internal_zzmd, int i, int i2, long j, long j2) {
        this.zzcgm = j;
        this.zzcgn = j2;
        this.zzcgl = new Handler(Looper.getMainLooper());
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzcgo = com_google_android_gms_internal_zzme_zza;
        this.zzcgp = false;
        this.zzcgq = false;
        this.zzaki = i2;
        this.zzakh = i;
    }

    static /* synthetic */ long zzc(zzir com_google_android_gms_internal_zzir) {
        long j = com_google_android_gms_internal_zzir.zzcgn - 1;
        com_google_android_gms_internal_zzir.zzcgn = j;
        return j;
    }

    public void run() {
        if (this.zzbnz == null || zzsc()) {
            this.zzcgo.zza(this.zzbnz, true);
        } else {
            new zza(this, this.zzbnz.getWebView()).execute(new Void[0]);
        }
    }

    public void zza(AdResponseParcel adResponseParcel) {
        zza(adResponseParcel, new zzmo(this, this.zzbnz, adResponseParcel.zzcli));
    }

    public void zza(AdResponseParcel adResponseParcel, zzmo com_google_android_gms_internal_zzmo) {
        this.zzbnz.setWebViewClient(com_google_android_gms_internal_zzmo);
        this.zzbnz.loadDataWithBaseURL(TextUtils.isEmpty(adResponseParcel.zzcbo) ? null : zzu.zzgm().zzcz(adResponseParcel.zzcbo), adResponseParcel.body, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
    }

    public void zzsa() {
        this.zzcgl.postDelayed(this, this.zzcgm);
    }

    public synchronized void zzsb() {
        this.zzcgp = true;
    }

    public synchronized boolean zzsc() {
        return this.zzcgp;
    }

    public boolean zzsd() {
        return this.zzcgq;
    }
}
