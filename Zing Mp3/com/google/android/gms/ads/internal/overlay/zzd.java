package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView.ItemAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.facebook.ads.AdError;
import com.facebook.ads.AudienceNetworkActivity;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkw;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzle;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme;
import com.mp3download.zingmp3.C1569R;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.Future;

@zzji
public class zzd extends com.google.android.gms.internal.zzhy.zza implements zzu {
    static final int zzcak;
    private final Activity mActivity;
    zzmd zzbnz;
    AdOverlayInfoParcel zzcal;
    zzc zzcam;
    zzo zzcan;
    boolean zzcao;
    FrameLayout zzcap;
    CustomViewCallback zzcaq;
    boolean zzcar;
    boolean zzcas;
    zzb zzcat;
    boolean zzcau;
    int zzcav;
    zzl zzcaw;
    private final Object zzcax;
    private Runnable zzcay;
    private boolean zzcaz;
    private boolean zzcba;
    private boolean zzcbb;
    private boolean zzcbc;
    private boolean zzcbd;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzd.1 */
    class C10871 implements com.google.android.gms.internal.zzme.zza {
        final /* synthetic */ zzd zzcbe;

        C10871(zzd com_google_android_gms_ads_internal_overlay_zzd) {
            this.zzcbe = com_google_android_gms_ads_internal_overlay_zzd;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            com_google_android_gms_internal_zzmd.zzps();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzd.2 */
    class C10882 implements Runnable {
        final /* synthetic */ zzd zzcbe;

        C10882(zzd com_google_android_gms_ads_internal_overlay_zzd) {
            this.zzcbe = com_google_android_gms_ads_internal_overlay_zzd;
        }

        public void run() {
            this.zzcbe.zzpq();
        }
    }

    @zzji
    private static final class zza extends Exception {
        public zza(String str) {
            super(str);
        }
    }

    @zzji
    static class zzb extends RelativeLayout {
        zzle zzasr;
        boolean zzcbf;

        public zzb(Context context, String str) {
            super(context);
            this.zzasr = new zzle(context, str);
        }

        void disable() {
            this.zzcbf = true;
        }

        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (!this.zzcbf) {
                this.zzasr.zzg(motionEvent);
            }
            return false;
        }
    }

    @zzji
    public static class zzc {
        public final int index;
        public final ViewGroup parent;
        public final Context zzahs;
        public final LayoutParams zzcbg;

        public zzc(zzmd com_google_android_gms_internal_zzmd) throws zza {
            this.zzcbg = com_google_android_gms_internal_zzmd.getLayoutParams();
            ViewParent parent = com_google_android_gms_internal_zzmd.getParent();
            this.zzahs = com_google_android_gms_internal_zzmd.zzwz();
            if (parent == null || !(parent instanceof ViewGroup)) {
                throw new zza("Could not get the parent of the WebView for an overlay.");
            }
            this.parent = (ViewGroup) parent;
            this.index = this.parent.indexOfChild(com_google_android_gms_internal_zzmd.getView());
            this.parent.removeView(com_google_android_gms_internal_zzmd.getView());
            com_google_android_gms_internal_zzmd.zzak(true);
        }
    }

    @zzji
    private class zzd extends zzkw {
        final /* synthetic */ zzd zzcbe;

        /* renamed from: com.google.android.gms.ads.internal.overlay.zzd.zzd.1 */
        class C10891 implements Runnable {
            final /* synthetic */ Drawable zzcbh;
            final /* synthetic */ zzd zzcbi;

            C10891(zzd com_google_android_gms_ads_internal_overlay_zzd_zzd, Drawable drawable) {
                this.zzcbi = com_google_android_gms_ads_internal_overlay_zzd_zzd;
                this.zzcbh = drawable;
            }

            public void run() {
                this.zzcbi.zzcbe.mActivity.getWindow().setBackgroundDrawable(this.zzcbh);
            }
        }

        private zzd(zzd com_google_android_gms_ads_internal_overlay_zzd) {
            this.zzcbe = com_google_android_gms_ads_internal_overlay_zzd;
        }

        public void onStop() {
        }

        public void zzfp() {
            Bitmap zza = zzu.zzhh().zza(Integer.valueOf(this.zzcbe.zzcal.zzcbv.zzaop));
            if (zza != null) {
                zzlb.zzcvl.post(new C10891(this, zzu.zzgo().zza(this.zzcbe.mActivity, zza, this.zzcbe.zzcal.zzcbv.zzaon, this.zzcbe.zzcal.zzcbv.zzaoo)));
            }
        }
    }

    static {
        zzcak = Color.argb(0, 0, 0, 0);
    }

    public zzd(Activity activity) {
        this.zzcao = false;
        this.zzcar = false;
        this.zzcas = false;
        this.zzcau = false;
        this.zzcav = 0;
        this.zzcax = new Object();
        this.zzcbb = false;
        this.zzcbc = false;
        this.zzcbd = true;
        this.mActivity = activity;
        this.zzcaw = new zzs();
    }

    public void close() {
        this.zzcav = 2;
        this.mActivity.finish();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
    }

    public void onBackPressed() {
        this.zzcav = 0;
    }

    public void onCreate(Bundle bundle) {
        boolean z = false;
        this.mActivity.requestWindowFeature(1);
        if (bundle != null) {
            z = bundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false);
        }
        this.zzcar = z;
        try {
            this.zzcal = AdOverlayInfoParcel.zzb(this.mActivity.getIntent());
            if (this.zzcal == null) {
                throw new zza("Could not get info for ad overlay.");
            }
            if (this.zzcal.zzari.zzcyb > 7500000) {
                this.zzcav = 3;
            }
            if (this.mActivity.getIntent() != null) {
                this.zzcbd = this.mActivity.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true);
            }
            if (this.zzcal.zzcbv != null) {
                this.zzcas = this.zzcal.zzcbv.zzaok;
            } else {
                this.zzcas = false;
            }
            if (((Boolean) zzdr.zzbip.get()).booleanValue() && this.zzcas && this.zzcal.zzcbv.zzaop != -1) {
                Future future = (Future) new zzd().zzrz();
            }
            if (bundle == null) {
                if (this.zzcal.zzcbl != null && this.zzcbd) {
                    this.zzcal.zzcbl.zzer();
                }
                if (!(this.zzcal.zzcbs == 1 || this.zzcal.zzcbk == null)) {
                    this.zzcal.zzcbk.onAdClicked();
                }
            }
            this.zzcat = new zzb(this.mActivity, this.zzcal.zzcbu);
            this.zzcat.setId(AdError.NETWORK_ERROR_CODE);
            switch (this.zzcal.zzcbs) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    zzab(false);
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    this.zzcam = new zzc(this.zzcal.zzcbm);
                    zzab(false);
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    zzab(true);
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    if (this.zzcar) {
                        this.zzcav = 3;
                        this.mActivity.finish();
                    } else if (!zzu.zzgj().zza(this.mActivity, this.zzcal.zzcbj, this.zzcal.zzcbr)) {
                        this.zzcav = 3;
                        this.mActivity.finish();
                    }
                default:
                    throw new zza("Could not determine ad overlay type.");
            }
        } catch (zza e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi(e.getMessage());
            this.zzcav = 3;
            this.mActivity.finish();
        }
    }

    public void onDestroy() {
        if (this.zzbnz != null) {
            this.zzcat.removeView(this.zzbnz.getView());
        }
        zzpp();
    }

    public void onPause() {
        this.zzcaw.pause();
        zzpl();
        if (this.zzcal.zzcbl != null) {
            this.zzcal.zzcbl.onPause();
        }
        if (!(((Boolean) zzdr.zzblf.get()).booleanValue() || this.zzbnz == null || (this.mActivity.isFinishing() && this.zzcam != null))) {
            zzu.zzgo().zzl(this.zzbnz);
        }
        zzpp();
    }

    public void onRestart() {
    }

    public void onResume() {
        if (this.zzcal != null && this.zzcal.zzcbs == 4) {
            if (this.zzcar) {
                this.zzcav = 3;
                this.mActivity.finish();
            } else {
                this.zzcar = true;
            }
        }
        if (this.zzcal.zzcbl != null) {
            this.zzcal.zzcbl.onResume();
        }
        if (!((Boolean) zzdr.zzblf.get()).booleanValue()) {
            if (this.zzbnz == null || this.zzbnz.isDestroyed()) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdi("The webview does not exist. Ignoring action.");
            } else {
                zzu.zzgo().zzm(this.zzbnz);
            }
        }
        this.zzcaw.resume();
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.zzcar);
    }

    public void onStart() {
        if (!((Boolean) zzdr.zzblf.get()).booleanValue()) {
            return;
        }
        if (this.zzbnz == null || this.zzbnz.isDestroyed()) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("The webview does not exist. Ignoring action.");
        } else {
            zzu.zzgo().zzm(this.zzbnz);
        }
    }

    public void onStop() {
        if (((Boolean) zzdr.zzblf.get()).booleanValue() && this.zzbnz != null && (!this.mActivity.isFinishing() || this.zzcam == null)) {
            zzu.zzgo().zzl(this.zzbnz);
        }
        zzpp();
    }

    public void setRequestedOrientation(int i) {
        this.mActivity.setRequestedOrientation(i);
    }

    public void zza(View view, CustomViewCallback customViewCallback) {
        this.zzcap = new FrameLayout(this.mActivity);
        this.zzcap.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        this.zzcap.addView(view, -1, -1);
        this.mActivity.setContentView(this.zzcap);
        zzds();
        this.zzcaq = customViewCallback;
        this.zzcao = true;
    }

    public void zza(boolean z, boolean z2) {
        if (this.zzcan != null) {
            this.zzcan.zza(z, z2);
        }
    }

    public void zzaa(boolean z) {
        this.zzcan = new zzo(this.mActivity, z ? 50 : 32, this);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        this.zzcan.zza(z, this.zzcal.zzcbp);
        this.zzcat.addView(this.zzcan, layoutParams);
    }

    protected void zzab(boolean z) throws zza {
        if (!this.zzcba) {
            this.mActivity.requestWindowFeature(1);
        }
        Window window = this.mActivity.getWindow();
        if (window == null) {
            throw new zza("Invalid activity, no window available.");
        }
        boolean zza = (zzs.isAtLeastN() && ((Boolean) zzdr.zzble.get()).booleanValue()) ? zzu.zzgm().zza(this.mActivity, this.mActivity.getResources().getConfiguration()) : true;
        Object obj = (this.zzcal.zzcbv == null || !this.zzcal.zzcbv.zzaol) ? null : 1;
        if (!(this.zzcas && obj == null) && zza) {
            window.setFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT, AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        }
        zzme zzxc = this.zzcal.zzcbm.zzxc();
        boolean zzic = zzxc != null ? zzxc.zzic() : false;
        this.zzcau = false;
        if (zzic) {
            if (this.zzcal.orientation == zzu.zzgo().zzvw()) {
                this.zzcau = this.mActivity.getResources().getConfiguration().orientation == 1;
            } else if (this.zzcal.orientation == zzu.zzgo().zzvx()) {
                this.zzcau = this.mActivity.getResources().getConfiguration().orientation == 2;
            }
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzdg("Delay onShow to next orientation change: " + this.zzcau);
        setRequestedOrientation(this.zzcal.orientation);
        if (zzu.zzgo().zza(window)) {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Hardware acceleration on the AdActivity window enabled.");
        }
        if (this.zzcas) {
            this.zzcat.setBackgroundColor(zzcak);
        } else {
            this.zzcat.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        this.mActivity.setContentView(this.zzcat);
        zzds();
        if (z) {
            this.zzbnz = zzu.zzgn().zza(this.mActivity, this.zzcal.zzcbm.zzeg(), true, zzic, null, this.zzcal.zzari, null, null, this.zzcal.zzcbm.zzec());
            this.zzbnz.zzxc().zza(null, null, this.zzcal.zzcbn, this.zzcal.zzcbr, true, this.zzcal.zzcbt, null, this.zzcal.zzcbm.zzxc().zzxu(), null, null);
            this.zzbnz.zzxc().zza(new C10871(this));
            if (this.zzcal.url != null) {
                this.zzbnz.loadUrl(this.zzcal.url);
            } else if (this.zzcal.zzcbq != null) {
                this.zzbnz.loadDataWithBaseURL(this.zzcal.zzcbo, this.zzcal.zzcbq, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
            } else {
                throw new zza("No URL or HTML to display in ad overlay.");
            }
            if (this.zzcal.zzcbm != null) {
                this.zzcal.zzcbm.zzc(this);
            }
        } else {
            this.zzbnz = this.zzcal.zzcbm;
            this.zzbnz.setContext(this.mActivity);
        }
        this.zzbnz.zzb(this);
        ViewParent parent = this.zzbnz.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this.zzbnz.getView());
        }
        if (this.zzcas) {
            this.zzbnz.zzxt();
        }
        this.zzcat.addView(this.zzbnz.getView(), -1, -1);
        if (!(z || this.zzcau)) {
            zzps();
        }
        zzaa(zzic);
        if (this.zzbnz.zzxd()) {
            zza(zzic, true);
        }
        com.google.android.gms.ads.internal.zzd zzec = this.zzbnz.zzec();
        zzm com_google_android_gms_ads_internal_overlay_zzm = zzec != null ? zzec.zzamr : null;
        if (com_google_android_gms_ads_internal_overlay_zzm != null) {
            this.zzcaw = com_google_android_gms_ads_internal_overlay_zzm.zza(this.mActivity, this.zzbnz, this.zzcat);
        } else {
            com.google.android.gms.ads.internal.util.client.zzb.zzdi("Appstreaming controller is null.");
        }
    }

    protected void zzak(int i) {
        this.zzbnz.zzak(i);
    }

    public void zzds() {
        this.zzcba = true;
    }

    public void zzg(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        this.zzcaw.zzg(com_google_android_gms_internal_zzmd, map);
    }

    public void zzn(com.google.android.gms.dynamic.zzd com_google_android_gms_dynamic_zzd) {
        if (((Boolean) zzdr.zzble.get()).booleanValue() && zzs.isAtLeastN()) {
            if (zzu.zzgm().zza(this.mActivity, (Configuration) zze.zzae(com_google_android_gms_dynamic_zzd))) {
                this.mActivity.getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
                this.mActivity.getWindow().clearFlags(ItemAnimator.FLAG_MOVED);
                return;
            }
            this.mActivity.getWindow().addFlags(ItemAnimator.FLAG_MOVED);
            this.mActivity.getWindow().clearFlags(AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT);
        }
    }

    public void zzpl() {
        if (this.zzcal != null && this.zzcao) {
            setRequestedOrientation(this.zzcal.orientation);
        }
        if (this.zzcap != null) {
            this.mActivity.setContentView(this.zzcat);
            zzds();
            this.zzcap.removeAllViews();
            this.zzcap = null;
        }
        if (this.zzcaq != null) {
            this.zzcaq.onCustomViewHidden();
            this.zzcaq = null;
        }
        this.zzcao = false;
    }

    public void zzpm() {
        this.zzcav = 1;
        this.mActivity.finish();
    }

    public boolean zzpn() {
        boolean z = true;
        this.zzcav = 0;
        if (this.zzbnz != null) {
            if (this.zzbnz.zzxi()) {
                zzl com_google_android_gms_ads_internal_overlay_zzl = this.zzcaw;
            } else {
                z = false;
            }
            if (!z) {
                this.zzbnz.zza("onbackblocked", Collections.emptyMap());
            }
        }
        return z;
    }

    public void zzpo() {
        this.zzcat.removeView(this.zzcan);
        zzaa(true);
    }

    protected void zzpp() {
        if (this.mActivity.isFinishing() && !this.zzcbb) {
            this.zzcbb = true;
            if (this.zzbnz != null) {
                zzak(this.zzcav);
                synchronized (this.zzcax) {
                    if (this.zzcaz || !this.zzbnz.zzxo()) {
                    } else {
                        this.zzcay = new C10882(this);
                        zzlb.zzcvl.postDelayed(this.zzcay, ((Long) zzdr.zzbgf.get()).longValue());
                        return;
                    }
                }
            }
            zzpq();
        }
    }

    void zzpq() {
        if (!this.zzcbc) {
            this.zzcbc = true;
            if (this.zzbnz != null) {
                this.zzcat.removeView(this.zzbnz.getView());
                if (this.zzcam != null) {
                    this.zzbnz.setContext(this.zzcam.zzahs);
                    this.zzbnz.zzak(false);
                    this.zzcam.parent.addView(this.zzbnz.getView(), this.zzcam.index, this.zzcam.zzcbg);
                    this.zzcam = null;
                } else if (this.mActivity.getApplicationContext() != null) {
                    this.zzbnz.setContext(this.mActivity.getApplicationContext());
                }
                this.zzbnz = null;
            }
            if (!(this.zzcal == null || this.zzcal.zzcbl == null)) {
                this.zzcal.zzcbl.zzeq();
            }
            this.zzcaw.destroy();
        }
    }

    public void zzpr() {
        if (this.zzcau) {
            this.zzcau = false;
            zzps();
        }
    }

    protected void zzps() {
        this.zzbnz.zzps();
    }

    public void zzpt() {
        this.zzcat.disable();
    }

    public void zzpu() {
        synchronized (this.zzcax) {
            this.zzcaz = true;
            if (this.zzcay != null) {
                zzlb.zzcvl.removeCallbacks(this.zzcay);
                zzlb.zzcvl.post(this.zzcay);
            }
        }
    }
}
