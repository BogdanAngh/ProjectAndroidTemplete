package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.zzi;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzcp implements OnGlobalLayoutListener, OnScrollChangedListener {
    protected final Object zzako;
    private boolean zzapl;
    private zzlm zzasn;
    private final Context zzatc;
    private final WeakReference<zzko> zzate;
    private WeakReference<ViewTreeObserver> zzatf;
    private final zzcw zzatg;
    protected final zzcn zzath;
    private final WindowManager zzati;
    private final PowerManager zzatj;
    private final KeyguardManager zzatk;
    @Nullable
    private zzcq zzatl;
    private boolean zzatm;
    private boolean zzatn;
    private boolean zzato;
    private boolean zzatp;
    private boolean zzatq;
    @Nullable
    BroadcastReceiver zzatr;
    private final HashSet<zzcm> zzats;
    private final HashSet<zzct> zzatt;

    /* renamed from: com.google.android.gms.internal.zzcp.1 */
    class C12591 extends BroadcastReceiver {
        final /* synthetic */ zzcp zzatu;

        C12591(zzcp com_google_android_gms_internal_zzcp) {
            this.zzatu = com_google_android_gms_internal_zzcp;
        }

        public void onReceive(Context context, Intent intent) {
            this.zzatu.zzk(3);
        }
    }

    public static class zza implements zzcw {
        private WeakReference<zzi> zzatv;

        public zza(zzi com_google_android_gms_ads_internal_formats_zzi) {
            this.zzatv = new WeakReference(com_google_android_gms_ads_internal_formats_zzi);
        }

        @Nullable
        public View zzir() {
            zzi com_google_android_gms_ads_internal_formats_zzi = (zzi) this.zzatv.get();
            return com_google_android_gms_ads_internal_formats_zzi != null ? com_google_android_gms_ads_internal_formats_zzi.zzmy() : null;
        }

        public boolean zzis() {
            return this.zzatv.get() == null;
        }

        public zzcw zzit() {
            return new zzb((zzi) this.zzatv.get());
        }
    }

    public static class zzb implements zzcw {
        private zzi zzatw;

        public zzb(zzi com_google_android_gms_ads_internal_formats_zzi) {
            this.zzatw = com_google_android_gms_ads_internal_formats_zzi;
        }

        public View zzir() {
            return this.zzatw != null ? this.zzatw.zzmy() : null;
        }

        public boolean zzis() {
            return this.zzatw == null;
        }

        public zzcw zzit() {
            return this;
        }
    }

    public static class zzc implements zzcw {
        @Nullable
        private final View mView;
        @Nullable
        private final zzko zzatx;

        public zzc(View view, zzko com_google_android_gms_internal_zzko) {
            this.mView = view;
            this.zzatx = com_google_android_gms_internal_zzko;
        }

        public View zzir() {
            return this.mView;
        }

        public boolean zzis() {
            return this.zzatx == null || this.mView == null;
        }

        public zzcw zzit() {
            return this;
        }
    }

    public static class zzd implements zzcw {
        private final WeakReference<View> zzaty;
        private final WeakReference<zzko> zzatz;

        public zzd(View view, zzko com_google_android_gms_internal_zzko) {
            this.zzaty = new WeakReference(view);
            this.zzatz = new WeakReference(com_google_android_gms_internal_zzko);
        }

        public View zzir() {
            return (View) this.zzaty.get();
        }

        public boolean zzis() {
            return this.zzaty.get() == null || this.zzatz.get() == null;
        }

        public zzcw zzit() {
            return new zzc((View) this.zzaty.get(), (zzko) this.zzatz.get());
        }
    }

    public zzcp(Context context, AdSizeParcel adSizeParcel, zzko com_google_android_gms_internal_zzko, VersionInfoParcel versionInfoParcel, zzcw com_google_android_gms_internal_zzcw) {
        this.zzako = new Object();
        this.zzapl = false;
        this.zzatn = false;
        this.zzats = new HashSet();
        this.zzatt = new HashSet();
        this.zzate = new WeakReference(com_google_android_gms_internal_zzko);
        this.zzatg = com_google_android_gms_internal_zzcw;
        this.zzatf = new WeakReference(null);
        this.zzato = true;
        this.zzatq = false;
        this.zzasn = new zzlm(200);
        this.zzath = new zzcn(UUID.randomUUID().toString(), versionInfoParcel, adSizeParcel.zzazq, com_google_android_gms_internal_zzko.zzcsi, com_google_android_gms_internal_zzko.zzic(), adSizeParcel.zzazt);
        this.zzati = (WindowManager) context.getSystemService("window");
        this.zzatj = (PowerManager) context.getApplicationContext().getSystemService("power");
        this.zzatk = (KeyguardManager) context.getSystemService("keyguard");
        this.zzatc = context;
    }

    protected void destroy() {
        synchronized (this.zzako) {
            zzik();
            zzif();
            this.zzato = false;
            zzih();
            zzim();
        }
    }

    boolean isScreenOn() {
        return this.zzatj.isScreenOn();
    }

    public void onGlobalLayout() {
        zzk(2);
    }

    public void onScrollChanged() {
        zzk(1);
    }

    public void pause() {
        synchronized (this.zzako) {
            this.zzapl = true;
            zzk(3);
        }
    }

    public void resume() {
        synchronized (this.zzako) {
            this.zzapl = false;
            zzk(3);
        }
    }

    public void stop() {
        synchronized (this.zzako) {
            this.zzatn = true;
            zzk(3);
        }
    }

    protected int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    JSONObject zza(JSONObject jSONObject) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        JSONObject jSONObject2 = new JSONObject();
        jSONArray.put(jSONObject);
        jSONObject2.put("units", jSONArray);
        return jSONObject2;
    }

    protected void zza(View view, Map<String, String> map) {
        zzk(3);
    }

    public void zza(zzcq com_google_android_gms_internal_zzcq) {
        synchronized (this.zzako) {
            this.zzatl = com_google_android_gms_internal_zzcq;
        }
    }

    public void zza(zzct com_google_android_gms_internal_zzct) {
        if (this.zzatt.isEmpty()) {
            zzie();
            zzk(3);
        }
        this.zzatt.add(com_google_android_gms_internal_zzct);
        try {
            com_google_android_gms_internal_zzct.zzc(zza(zzd(this.zzatg.zzir())), false);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Skipping measurement update for new client.", e);
        }
    }

    void zza(zzct com_google_android_gms_internal_zzct, Map<String, String> map) {
        String str = "Received request to untrack: ";
        String valueOf = String.valueOf(this.zzath.zzib());
        com.google.android.gms.ads.internal.util.client.zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        zzb(com_google_android_gms_internal_zzct);
    }

    protected void zza(JSONObject jSONObject, boolean z) {
        try {
            zzb(zza(jSONObject), z);
        } catch (Throwable th) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Skipping active view message.", th);
        }
    }

    public void zzb(zzct com_google_android_gms_internal_zzct) {
        this.zzatt.remove(com_google_android_gms_internal_zzct);
        com_google_android_gms_internal_zzct.zziv();
        if (this.zzatt.isEmpty()) {
            destroy();
        }
    }

    void zzb(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        zza(com_google_android_gms_internal_zzmd.getView(), (Map) map);
    }

    protected void zzb(JSONObject jSONObject, boolean z) {
        Iterator it = new ArrayList(this.zzatt).iterator();
        while (it.hasNext()) {
            ((zzct) it.next()).zzc(jSONObject, z);
        }
    }

    boolean zzb(@Nullable Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = (String) map.get("hashCode");
        boolean z = !TextUtils.isEmpty(str) && str.equals(this.zzath.zzib());
        return z;
    }

    void zzc(Map<String, String> map) {
        if (map.containsKey("isVisible")) {
            boolean z = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("isVisible")) || ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equals(map.get("isVisible"));
            zzj(z);
        }
    }

    protected JSONObject zzd(@Nullable View view) throws JSONException {
        if (view == null) {
            return zzio();
        }
        boolean isAttachedToWindow = zzu.zzgo().isAttachedToWindow(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure getting view location.", e);
        }
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.zzati.getDefaultDisplay().getWidth();
        rect2.bottom = this.zzati.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect4);
        Rect rect5 = new Rect();
        view.getHitRect(rect5);
        JSONObject zzil = zzil();
        zzil.put("windowVisibility", view.getWindowVisibility()).put("isAttachedToWindow", isAttachedToWindow).put("viewBox", new JSONObject().put("top", zza(rect2.top, displayMetrics)).put("bottom", zza(rect2.bottom, displayMetrics)).put(TtmlNode.LEFT, zza(rect2.left, displayMetrics)).put(TtmlNode.RIGHT, zza(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", zza(rect.top, displayMetrics)).put("bottom", zza(rect.bottom, displayMetrics)).put(TtmlNode.LEFT, zza(rect.left, displayMetrics)).put(TtmlNode.RIGHT, zza(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", zza(rect3.top, displayMetrics)).put("bottom", zza(rect3.bottom, displayMetrics)).put(TtmlNode.LEFT, zza(rect3.left, displayMetrics)).put(TtmlNode.RIGHT, zza(rect3.right, displayMetrics))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect4.top, displayMetrics)).put("bottom", zza(rect4.bottom, displayMetrics)).put(TtmlNode.LEFT, zza(rect4.left, displayMetrics)).put(TtmlNode.RIGHT, zza(rect4.right, displayMetrics))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect5.top, displayMetrics)).put("bottom", zza(rect5.bottom, displayMetrics)).put(TtmlNode.LEFT, zza(rect5.left, displayMetrics)).put(TtmlNode.RIGHT, zza(rect5.right, displayMetrics))).put("screenDensity", (double) displayMetrics.density).put("isVisible", zzu.zzgm().zza(view, this.zzatj, this.zzatk));
        return zzil;
    }

    protected void zzie() {
        synchronized (this.zzako) {
            if (this.zzatr != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.zzatr = new C12591(this);
            this.zzatc.registerReceiver(this.zzatr, intentFilter);
        }
    }

    protected void zzif() {
        synchronized (this.zzako) {
            if (this.zzatr != null) {
                try {
                    this.zzatc.unregisterReceiver(this.zzatr);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Failed trying to unregister the receiver", e);
                } catch (Throwable e2) {
                    zzu.zzgq().zza(e2, "ActiveViewUnit.stopScreenStatusMonitoring");
                }
                this.zzatr = null;
            }
        }
    }

    public void zzig() {
        synchronized (this.zzako) {
            if (this.zzato) {
                this.zzatp = true;
                try {
                    zza(zzip(), true);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("JSON failure while processing active view data.", e);
                } catch (Throwable e2) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure while processing active view data.", e2);
                }
                String str = "Untracking ad unit: ";
                String valueOf = String.valueOf(this.zzath.zzib());
                com.google.android.gms.ads.internal.util.client.zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        }
    }

    protected void zzih() {
        if (this.zzatl != null) {
            this.zzatl.zza(this);
        }
    }

    public boolean zzii() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzato;
        }
        return z;
    }

    protected void zzij() {
        View zzir = this.zzatg.zzit().zzir();
        if (zzir != null) {
            ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzatf.get();
            ViewTreeObserver viewTreeObserver2 = zzir.getViewTreeObserver();
            if (viewTreeObserver2 != viewTreeObserver) {
                zzik();
                if (!this.zzatm || (viewTreeObserver != null && viewTreeObserver.isAlive())) {
                    this.zzatm = true;
                    viewTreeObserver2.addOnScrollChangedListener(this);
                    viewTreeObserver2.addOnGlobalLayoutListener(this);
                }
                this.zzatf = new WeakReference(viewTreeObserver2);
            }
        }
    }

    protected void zzik() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzatf.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    protected JSONObject zzil() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.zzath.zzhz()).put("activeViewJSON", this.zzath.zzia()).put("timestamp", zzu.zzgs().elapsedRealtime()).put("adFormat", this.zzath.zzhy()).put("hashCode", this.zzath.zzib()).put("isMraid", this.zzath.zzic()).put("isStopped", this.zzatn).put("isPaused", this.zzapl).put("isScreenOn", isScreenOn()).put("isNative", this.zzath.zzid()).put("appMuted", zzu.zzgm().zzft()).put("appVolume", (double) zzu.zzgm().zzfr()).put("deviceVolume", (double) zzu.zzgm().zzah(this.zzatc));
        return jSONObject;
    }

    protected void zzim() {
        Iterator it = new ArrayList(this.zzatt).iterator();
        while (it.hasNext()) {
            zzb((zzct) it.next());
        }
    }

    protected boolean zzin() {
        Iterator it = this.zzatt.iterator();
        while (it.hasNext()) {
            if (((zzct) it.next()).zziu()) {
                return true;
            }
        }
        return false;
    }

    protected JSONObject zzio() throws JSONException {
        return zzil().put("isAttachedToWindow", false).put("isScreenOn", isScreenOn()).put("isVisible", false);
    }

    protected JSONObject zzip() throws JSONException {
        JSONObject zzil = zzil();
        zzil.put("doneReasonCode", "u");
        return zzil;
    }

    public zzcn zziq() {
        return this.zzath;
    }

    protected void zzj(boolean z) {
        Iterator it = this.zzats.iterator();
        while (it.hasNext()) {
            ((zzcm) it.next()).zza(this, z);
        }
    }

    protected void zzk(int i) {
        Object obj = null;
        synchronized (this.zzako) {
            if (zzin() && this.zzato) {
                View zzir = this.zzatg.zzir();
                boolean z = zzir != null && zzu.zzgm().zza(zzir, this.zzatj, this.zzatk) && zzir.getGlobalVisibleRect(new Rect(), null);
                if (this.zzatg.zzis()) {
                    zzig();
                    return;
                }
                if (i == 1) {
                    obj = 1;
                }
                if (obj != null) {
                    if (!this.zzasn.tryAcquire() && z == this.zzatq) {
                        return;
                    }
                }
                if (z || this.zzatq || i != 1) {
                    try {
                        zza(zzd(zzir), false);
                        this.zzatq = z;
                    } catch (JSONException e) {
                        Throwable e2 = e;
                        com.google.android.gms.ads.internal.util.client.zzb.zza("Active view update failed.", e2);
                        zzij();
                        zzih();
                        return;
                    } catch (RuntimeException e3) {
                        e2 = e3;
                        com.google.android.gms.ads.internal.util.client.zzb.zza("Active view update failed.", e2);
                        zzij();
                        zzih();
                        return;
                    }
                    zzij();
                    zzih();
                    return;
                }
                return;
            }
        }
    }
}
