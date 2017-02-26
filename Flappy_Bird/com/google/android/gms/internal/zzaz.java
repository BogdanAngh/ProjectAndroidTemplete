package com.google.android.gms.internal;

import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.PowerManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzo;
import com.google.android.gms.internal.zzdt.zzd;
import com.google.android.gms.internal.zzhx.zza;
import com.google.android.gms.internal.zzhx.zzc;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
public class zzaz implements OnGlobalLayoutListener, OnScrollChangedListener {
    private boolean zzpj;
    private final WeakReference<View> zzqA;
    private final zzax zzqB;
    private final zzdt zzqC;
    private final zzd zzqD;
    private boolean zzqE;
    private final WindowManager zzqF;
    private final PowerManager zzqG;
    private final KeyguardManager zzqH;
    private zzba zzqI;
    private boolean zzqJ;
    private boolean zzqK;
    private boolean zzqL;
    private boolean zzqM;
    private BroadcastReceiver zzqN;
    private final HashSet<zzaw> zzqO;
    private final zzdg zzqP;
    private final zzdg zzqQ;
    private final zzdg zzqR;
    private zzhq zzqm;
    private final Object zzqt;
    private final Context zzqw;
    private final WeakReference<zzha> zzqy;
    private WeakReference<ViewTreeObserver> zzqz;

    /* renamed from: com.google.android.gms.internal.zzaz.5 */
    class C01825 extends BroadcastReceiver {
        final /* synthetic */ zzaz zzqT;

        C01825(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void onReceive(Context context, Intent intent) {
            this.zzqT.zzh(false);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.1 */
    class C04341 implements zzc<zzbe> {
        final /* synthetic */ JSONObject zzqS;
        final /* synthetic */ zzaz zzqT;

        C04341(zzaz com_google_android_gms_internal_zzaz, JSONObject jSONObject) {
            this.zzqT = com_google_android_gms_internal_zzaz;
            this.zzqS = jSONObject;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            this.zzqT.zza(this.zzqS);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.2 */
    class C04352 implements zza {
        final /* synthetic */ zzaz zzqT;

        C04352(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void run() {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.3 */
    class C04363 implements zzc<zzbe> {
        final /* synthetic */ zzaz zzqT;

        C04363(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            this.zzqT.zzqE = true;
            this.zzqT.zza(com_google_android_gms_internal_zzbe);
            this.zzqT.zzbV();
            this.zzqT.zzh(false);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.4 */
    class C04374 implements zza {
        final /* synthetic */ zzaz zzqT;

        C04374(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void run() {
            this.zzqT.destroy();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.6 */
    class C04386 implements zzdg {
        final /* synthetic */ zzaz zzqT;

        C04386(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            if (this.zzqT.zzb((Map) map)) {
                this.zzqT.zza(com_google_android_gms_internal_zzid.getWebView(), (Map) map);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.7 */
    class C04397 implements zzdg {
        final /* synthetic */ zzaz zzqT;

        C04397(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            if (this.zzqT.zzb((Map) map)) {
                zzb.zzay("Received request to untrack: " + this.zzqT.zzqB.zzbT());
                this.zzqT.destroy();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.8 */
    class C04408 implements zzdg {
        final /* synthetic */ zzaz zzqT;

        C04408(zzaz com_google_android_gms_internal_zzaz) {
            this.zzqT = com_google_android_gms_internal_zzaz;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            if (this.zzqT.zzb((Map) map) && map.containsKey("isVisible")) {
                boolean z = "1".equals(map.get("isVisible")) || "true".equals(map.get("isVisible"));
                this.zzqT.zzg(Boolean.valueOf(z).booleanValue());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaz.9 */
    class C04419 implements zzc<zzbe> {
        final /* synthetic */ zzaz zzqT;
        final /* synthetic */ JSONObject zzqU;

        C04419(zzaz com_google_android_gms_internal_zzaz, JSONObject jSONObject) {
            this.zzqT = com_google_android_gms_internal_zzaz;
            this.zzqU = jSONObject;
        }

        public void zzb(zzbe com_google_android_gms_internal_zzbe) {
            com_google_android_gms_internal_zzbe.zza("AFMA_updateActiveView", this.zzqU);
        }

        public /* synthetic */ void zzc(Object obj) {
            zzb((zzbe) obj);
        }
    }

    public zzaz(AdSizeParcel adSizeParcel, zzha com_google_android_gms_internal_zzha, VersionInfoParcel versionInfoParcel, View view, zzdt com_google_android_gms_internal_zzdt) {
        this.zzqt = new Object();
        this.zzpj = false;
        this.zzqK = false;
        this.zzqO = new HashSet();
        this.zzqP = new C04386(this);
        this.zzqQ = new C04397(this);
        this.zzqR = new C04408(this);
        this.zzqC = com_google_android_gms_internal_zzdt;
        this.zzqy = new WeakReference(com_google_android_gms_internal_zzha);
        this.zzqA = new WeakReference(view);
        this.zzqz = new WeakReference(null);
        this.zzqL = true;
        this.zzqm = new zzhq(200);
        this.zzqB = new zzax(UUID.randomUUID().toString(), versionInfoParcel, adSizeParcel.zzsm, com_google_android_gms_internal_zzha.zzFl, com_google_android_gms_internal_zzha.zzbU());
        this.zzqD = this.zzqC.zzdU();
        this.zzqF = (WindowManager) view.getContext().getSystemService("window");
        this.zzqG = (PowerManager) view.getContext().getApplicationContext().getSystemService("power");
        this.zzqH = (KeyguardManager) view.getContext().getSystemService("keyguard");
        this.zzqw = view.getContext().getApplicationContext();
        try {
            this.zzqD.zza(new C04341(this, zzd(view)), new C04352(this));
        } catch (JSONException e) {
        } catch (Throwable e2) {
            zzb.zzb("Failure while processing active view data.", e2);
        }
        this.zzqD.zza(new C04363(this), new C04374(this));
        zzb.zzay("Tracking ad unit: " + this.zzqB.zzbT());
    }

    protected void destroy() {
        synchronized (this.zzqt) {
            zzcb();
            zzbW();
            this.zzqL = false;
            zzbY();
            this.zzqD.release();
        }
    }

    boolean isScreenOn() {
        return this.zzqG.isScreenOn();
    }

    public void onGlobalLayout() {
        zzh(false);
    }

    public void onScrollChanged() {
        zzh(true);
    }

    public void pause() {
        synchronized (this.zzqt) {
            this.zzpj = true;
            zzh(false);
        }
    }

    public void resume() {
        synchronized (this.zzqt) {
            this.zzpj = false;
            zzh(false);
        }
    }

    public void stop() {
        synchronized (this.zzqt) {
            this.zzqK = true;
            zzh(false);
        }
    }

    protected int zza(int i, DisplayMetrics displayMetrics) {
        return (int) (((float) i) / displayMetrics.density);
    }

    protected void zza(View view, Map<String, String> map) {
        zzh(false);
    }

    public void zza(zzaw com_google_android_gms_internal_zzaw) {
        this.zzqO.add(com_google_android_gms_internal_zzaw);
    }

    public void zza(zzba com_google_android_gms_internal_zzba) {
        synchronized (this.zzqt) {
            this.zzqI = com_google_android_gms_internal_zzba;
        }
    }

    protected void zza(zzbe com_google_android_gms_internal_zzbe) {
        com_google_android_gms_internal_zzbe.zza("/updateActiveView", this.zzqP);
        com_google_android_gms_internal_zzbe.zza("/untrackActiveViewUnit", this.zzqQ);
        com_google_android_gms_internal_zzbe.zza("/visibilityChanged", this.zzqR);
    }

    protected void zza(JSONObject jSONObject) {
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject2 = new JSONObject();
            jSONArray.put(jSONObject);
            jSONObject2.put("units", jSONArray);
            this.zzqD.zza(new C04419(this, jSONObject2), new zzhx.zzb());
        } catch (Throwable th) {
            zzb.zzb("Skipping active view message.", th);
        }
    }

    protected boolean zzb(Map<String, String> map) {
        if (map == null) {
            return false;
        }
        String str = (String) map.get("hashCode");
        boolean z = !TextUtils.isEmpty(str) && str.equals(this.zzqB.zzbT());
        return z;
    }

    protected void zzbV() {
        synchronized (this.zzqt) {
            if (this.zzqN != null) {
                return;
            }
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            this.zzqN = new C01825(this);
            this.zzqw.registerReceiver(this.zzqN, intentFilter);
        }
    }

    protected void zzbW() {
        synchronized (this.zzqt) {
            if (this.zzqN != null) {
                this.zzqw.unregisterReceiver(this.zzqN);
                this.zzqN = null;
            }
        }
    }

    public void zzbX() {
        synchronized (this.zzqt) {
            if (this.zzqL) {
                this.zzqM = true;
                try {
                    zza(zzcd());
                } catch (Throwable e) {
                    zzb.zzb("JSON failure while processing active view data.", e);
                } catch (Throwable e2) {
                    zzb.zzb("Failure while processing active view data.", e2);
                }
                zzb.zzay("Untracking ad unit: " + this.zzqB.zzbT());
            }
        }
    }

    protected void zzbY() {
        if (this.zzqI != null) {
            this.zzqI.zza(this);
        }
    }

    public boolean zzbZ() {
        boolean z;
        synchronized (this.zzqt) {
            z = this.zzqL;
        }
        return z;
    }

    protected void zzca() {
        View view = (View) this.zzqA.get();
        if (view != null) {
            ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzqz.get();
            ViewTreeObserver viewTreeObserver2 = view.getViewTreeObserver();
            if (viewTreeObserver2 != viewTreeObserver) {
                zzcb();
                if (!this.zzqJ || (viewTreeObserver != null && viewTreeObserver.isAlive())) {
                    this.zzqJ = true;
                    viewTreeObserver2.addOnScrollChangedListener(this);
                    viewTreeObserver2.addOnGlobalLayoutListener(this);
                }
                this.zzqz = new WeakReference(viewTreeObserver2);
            }
        }
    }

    protected void zzcb() {
        ViewTreeObserver viewTreeObserver = (ViewTreeObserver) this.zzqz.get();
        if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnScrollChangedListener(this);
            viewTreeObserver.removeGlobalOnLayoutListener(this);
        }
    }

    protected JSONObject zzcc() throws JSONException {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("afmaVersion", this.zzqB.zzbR()).put("activeViewJSON", this.zzqB.zzbS()).put("timestamp", zzo.zzbz().elapsedRealtime()).put("adFormat", this.zzqB.zzbQ()).put("hashCode", this.zzqB.zzbT()).put("isMraid", this.zzqB.zzbU());
        return jSONObject;
    }

    protected JSONObject zzcd() throws JSONException {
        JSONObject zzcc = zzcc();
        zzcc.put("doneReasonCode", "u");
        return zzcc;
    }

    protected JSONObject zzd(View view) throws JSONException {
        boolean zzk = zzo.zzbx().zzk(view);
        int[] iArr = new int[2];
        int[] iArr2 = new int[2];
        try {
            view.getLocationOnScreen(iArr);
            view.getLocationInWindow(iArr2);
        } catch (Throwable e) {
            zzb.zzb("Failure getting view location.", e);
        }
        DisplayMetrics displayMetrics = view.getContext().getResources().getDisplayMetrics();
        Rect rect = new Rect();
        rect.left = iArr[0];
        rect.top = iArr[1];
        rect.right = rect.left + view.getWidth();
        rect.bottom = rect.top + view.getHeight();
        Rect rect2 = new Rect();
        rect2.right = this.zzqF.getDefaultDisplay().getWidth();
        rect2.bottom = this.zzqF.getDefaultDisplay().getHeight();
        Rect rect3 = new Rect();
        boolean globalVisibleRect = view.getGlobalVisibleRect(rect3, null);
        Rect rect4 = new Rect();
        boolean localVisibleRect = view.getLocalVisibleRect(rect4);
        Rect rect5 = new Rect();
        view.getHitRect(rect5);
        JSONObject zzcc = zzcc();
        zzcc.put("windowVisibility", view.getWindowVisibility()).put("isStopped", this.zzqK).put("isPaused", this.zzpj).put("isAttachedToWindow", zzk).put("viewBox", new JSONObject().put("top", zza(rect2.top, displayMetrics)).put("bottom", zza(rect2.bottom, displayMetrics)).put("left", zza(rect2.left, displayMetrics)).put("right", zza(rect2.right, displayMetrics))).put("adBox", new JSONObject().put("top", zza(rect.top, displayMetrics)).put("bottom", zza(rect.bottom, displayMetrics)).put("left", zza(rect.left, displayMetrics)).put("right", zza(rect.right, displayMetrics))).put("globalVisibleBox", new JSONObject().put("top", zza(rect3.top, displayMetrics)).put("bottom", zza(rect3.bottom, displayMetrics)).put("left", zza(rect3.left, displayMetrics)).put("right", zza(rect3.right, displayMetrics))).put("globalVisibleBoxVisible", globalVisibleRect).put("localVisibleBox", new JSONObject().put("top", zza(rect4.top, displayMetrics)).put("bottom", zza(rect4.bottom, displayMetrics)).put("left", zza(rect4.left, displayMetrics)).put("right", zza(rect4.right, displayMetrics))).put("localVisibleBoxVisible", localVisibleRect).put("hitBox", new JSONObject().put("top", zza(rect5.top, displayMetrics)).put("bottom", zza(rect5.bottom, displayMetrics)).put("left", zza(rect5.left, displayMetrics)).put("right", zza(rect5.right, displayMetrics))).put("screenDensity", (double) displayMetrics.density).put("isVisible", zze(view));
        return zzcc;
    }

    protected boolean zze(View view) {
        return view.getVisibility() == 0 && view.isShown() && isScreenOn() && (!this.zzqH.inKeyguardRestrictedInputMode() || zzo.zzbv().zzgl());
    }

    protected void zzg(boolean z) {
        Iterator it = this.zzqO.iterator();
        while (it.hasNext()) {
            ((zzaw) it.next()).zza(this, z);
        }
    }

    protected void zzh(boolean z) {
        Throwable e;
        synchronized (this.zzqt) {
            if (!this.zzqE || !this.zzqL) {
            } else if (!z || this.zzqm.tryAcquire()) {
                View view = (View) this.zzqA.get();
                Object obj = (view == null || ((zzha) this.zzqy.get()) == null) ? 1 : null;
                if (obj != null) {
                    zzbX();
                    return;
                }
                try {
                    zza(zzd(view));
                } catch (JSONException e2) {
                    e = e2;
                    zzb.zza("Active view update failed.", e);
                    zzca();
                    zzbY();
                } catch (RuntimeException e3) {
                    e = e3;
                    zzb.zza("Active view update failed.", e);
                    zzca();
                    zzbY();
                }
                zzca();
                zzbY();
            }
        }
    }
}
