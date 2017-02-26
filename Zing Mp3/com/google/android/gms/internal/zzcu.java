package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.view.Window;
import android.view.WindowManager;
import com.google.android.gms.ads.internal.zzu;
import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Iterator;

@zzji
@TargetApi(14)
public class zzcu implements ActivityLifecycleCallbacks, OnAttachStateChangeListener, OnGlobalLayoutListener, OnScrollChangedListener {
    private static final long zzauk;
    private zzlm zzasn;
    private final Context zzatc;
    private final WindowManager zzati;
    private final PowerManager zzatj;
    private final KeyguardManager zzatk;
    private boolean zzatq;
    @Nullable
    BroadcastReceiver zzatr;
    private Application zzaul;
    private WeakReference<ViewTreeObserver> zzaum;
    WeakReference<View> zzaun;
    private zzcv zzauo;
    private int zzaup;
    private HashSet<zzb> zzauq;
    private DisplayMetrics zzaur;

    /* renamed from: com.google.android.gms.internal.zzcu.1 */
    class C12701 implements Runnable {
        final /* synthetic */ zzcu zzaus;

        C12701(zzcu com_google_android_gms_internal_zzcu) {
            this.zzaus = com_google_android_gms_internal_zzcu;
        }

        public void run() {
            this.zzaus.zzl(3);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcu.2 */
    class C12712 extends BroadcastReceiver {
        final /* synthetic */ zzcu zzaus;

        C12712(zzcu com_google_android_gms_internal_zzcu) {
            this.zzaus = com_google_android_gms_internal_zzcu;
        }

        public void onReceive(Context context, Intent intent) {
            this.zzaus.zzl(3);
        }
    }

    public static class zza {
        public final long timestamp;
        public final boolean zzaut;
        public final boolean zzauu;
        public final int zzauv;
        public final Rect zzauw;
        public final Rect zzaux;
        public final Rect zzauy;
        public final boolean zzauz;
        public final Rect zzava;
        public final boolean zzavb;
        public final Rect zzavc;
        public final float zzavd;
        public final boolean zzave;

        public zza(long j, boolean z, boolean z2, int i, Rect rect, Rect rect2, Rect rect3, boolean z3, Rect rect4, boolean z4, Rect rect5, float f, boolean z5) {
            this.timestamp = j;
            this.zzaut = z;
            this.zzauu = z2;
            this.zzauv = i;
            this.zzauw = rect;
            this.zzaux = rect2;
            this.zzauy = rect3;
            this.zzauz = z3;
            this.zzava = rect4;
            this.zzavb = z4;
            this.zzavc = rect5;
            this.zzavd = f;
            this.zzave = z5;
        }
    }

    public interface zzb {
        void zza(zza com_google_android_gms_internal_zzcu_zza);
    }

    static {
        zzauk = ((Long) zzdr.zzbgu.get()).longValue();
    }

    public zzcu(Context context, View view) {
        this.zzasn = new zzlm(zzauk);
        this.zzatq = false;
        this.zzaup = -1;
        this.zzauq = new HashSet();
        this.zzatc = context.getApplicationContext();
        this.zzati = (WindowManager) context.getSystemService("window");
        this.zzatj = (PowerManager) this.zzatc.getSystemService("power");
        this.zzatk = (KeyguardManager) context.getSystemService("keyguard");
        if (this.zzatc instanceof Application) {
            this.zzaul = (Application) this.zzatc;
            this.zzauo = new zzcv((Application) this.zzatc, this);
        }
        this.zzaur = context.getResources().getDisplayMetrics();
        zze(view);
    }

    private void zza(Activity activity, int i) {
        if (this.zzaun != null) {
            Window window = activity.getWindow();
            if (window != null) {
                View peekDecorView = window.peekDecorView();
                View view = (View) this.zzaun.get();
                if (view != null && peekDecorView != null && view.getRootView() == peekDecorView.getRootView()) {
                    this.zzaup = i;
                }
            }
        }
    }

    private void zzf(View view) {
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            this.zzaum = new WeakReference(viewTreeObserver);
            viewTreeObserver.addOnScrollChangedListener(this);
            viewTreeObserver.addOnGlobalLayoutListener(this);
        }
        zzie();
        if (this.zzaul != null) {
            try {
                this.zzaul.registerActivityLifecycleCallbacks(this.zzauo);
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error registering activity lifecycle callbacks.", e);
            }
        }
    }

    private void zzg(View view) {
        ViewTreeObserver viewTreeObserver;
        try {
            if (this.zzaum != null) {
                viewTreeObserver = (ViewTreeObserver) this.zzaum.get();
                if (viewTreeObserver != null && viewTreeObserver.isAlive()) {
                    viewTreeObserver.removeOnScrollChangedListener(this);
                    viewTreeObserver.removeGlobalOnLayoutListener(this);
                }
                this.zzaum = null;
            }
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while unregistering listeners from the last ViewTreeObserver.", e);
        }
        try {
            viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnScrollChangedListener(this);
                viewTreeObserver.removeGlobalOnLayoutListener(this);
            }
        } catch (Throwable e2) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error while unregistering listeners from the ViewTreeObserver.", e2);
        }
        zzif();
        if (this.zzaul != null) {
            try {
                this.zzaul.unregisterActivityLifecycleCallbacks(this.zzauo);
            } catch (Throwable e22) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error registering activity lifecycle callbacks.", e22);
            }
        }
    }

    private void zzie() {
        if (this.zzatr == null) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            this.zzatr = new C12712(this);
            this.zzatc.registerReceiver(this.zzatr, intentFilter);
        }
    }

    private void zzif() {
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

    private void zziw() {
        zzu.zzgm();
        zzlb.zzcvl.post(new C12701(this));
    }

    private void zzl(int i) {
        if (this.zzauq.size() != 0 && this.zzaun != null) {
            View view = (View) this.zzaun.get();
            Object obj = i == 1 ? 1 : null;
            Object obj2 = view == null ? 1 : null;
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            boolean z = false;
            Rect rect3 = new Rect();
            boolean z2 = false;
            Rect rect4 = new Rect();
            Rect rect5 = new Rect();
            rect5.right = this.zzati.getDefaultDisplay().getWidth();
            rect5.bottom = this.zzati.getDefaultDisplay().getHeight();
            int[] iArr = new int[2];
            int[] iArr2 = new int[2];
            if (view != null) {
                z = view.getGlobalVisibleRect(rect2);
                z2 = view.getLocalVisibleRect(rect3);
                view.getHitRect(rect4);
                try {
                    view.getLocationOnScreen(iArr);
                    view.getLocationInWindow(iArr2);
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Failure getting view location.", e);
                }
                rect.left = iArr[0];
                rect.top = iArr[1];
                rect.right = rect.left + view.getWidth();
                rect.bottom = rect.top + view.getHeight();
            }
            int windowVisibility = view != null ? view.getWindowVisibility() : 8;
            if (this.zzaup != -1) {
                windowVisibility = this.zzaup;
            }
            boolean z3 = obj2 == null && zzu.zzgm().zza(view, this.zzatj, this.zzatk) && z && z2 && windowVisibility == 0;
            if (obj != null && !this.zzasn.tryAcquire() && z3 == this.zzatq) {
                return;
            }
            if (z3 || this.zzatq || i != 1) {
                zza com_google_android_gms_internal_zzcu_zza = new zza(zzu.zzgs().elapsedRealtime(), this.zzatj.isScreenOn(), view != null ? zzu.zzgo().isAttachedToWindow(view) : false, view != null ? view.getWindowVisibility() : 8, zza(rect5), zza(rect), zza(rect2), z, zza(rect3), z2, zza(rect4), this.zzaur.density, z3);
                Iterator it = this.zzauq.iterator();
                while (it.hasNext()) {
                    ((zzb) it.next()).zza(com_google_android_gms_internal_zzcu_zza);
                }
                this.zzatq = z3;
            }
        }
    }

    private int zzm(int i) {
        return (int) (((float) i) / this.zzaur.density);
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        zza(activity, 0);
        zzl(3);
        zziw();
    }

    public void onActivityDestroyed(Activity activity) {
        zzl(3);
        zziw();
    }

    public void onActivityPaused(Activity activity) {
        zza(activity, 4);
        zzl(3);
        zziw();
    }

    public void onActivityResumed(Activity activity) {
        zza(activity, 0);
        zzl(3);
        zziw();
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zzl(3);
        zziw();
    }

    public void onActivityStarted(Activity activity) {
        zza(activity, 0);
        zzl(3);
        zziw();
    }

    public void onActivityStopped(Activity activity) {
        zzl(3);
        zziw();
    }

    public void onGlobalLayout() {
        zzl(2);
    }

    public void onScrollChanged() {
        zzl(1);
    }

    public void onViewAttachedToWindow(View view) {
        this.zzaup = -1;
        zzf(view);
        zzl(3);
    }

    public void onViewDetachedFromWindow(View view) {
        this.zzaup = -1;
        zzl(3);
        zzg(view);
    }

    Rect zza(Rect rect) {
        return new Rect(zzm(rect.left), zzm(rect.top), zzm(rect.right), zzm(rect.bottom));
    }

    public void zza(zzb com_google_android_gms_internal_zzcu_zzb) {
        this.zzauq.add(com_google_android_gms_internal_zzcu_zzb);
        zzl(3);
    }

    public void zze(View view) {
        View view2 = this.zzaun != null ? (View) this.zzaun.get() : null;
        if (view2 != null) {
            view2.removeOnAttachStateChangeListener(this);
            zzg(view2);
        }
        this.zzaun = new WeakReference(view);
        if (view != null) {
            if (zzu.zzgo().isAttachedToWindow(view)) {
                zzf(view);
            }
            view.addOnAttachStateChangeListener(this);
        }
    }
}
