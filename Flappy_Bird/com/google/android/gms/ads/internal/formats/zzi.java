package com.google.android.gms.ads.internal.formats;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzcm.zza;
import com.google.android.gms.internal.zzhz;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class zzi extends zza implements OnClickListener, OnTouchListener, OnGlobalLayoutListener, OnScrollChangedListener {
    private final FrameLayout zznV;
    private final Object zzqt;
    private final FrameLayout zzvF;
    private final Map<String, WeakReference<View>> zzvG;
    private zzb zzvH;
    private zzg zzvq;

    public zzi(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzqt = new Object();
        this.zzvG = new HashMap();
        this.zzvF = frameLayout;
        this.zznV = frameLayout2;
        zzhz.zza(this.zzvF, (OnGlobalLayoutListener) this);
        zzhz.zza(this.zzvF, (OnScrollChangedListener) this);
        this.zzvF.setOnTouchListener(this);
    }

    private String zzi(View view) {
        synchronized (this.zzqt) {
            if (this.zzvH == null || !this.zzvH.zzdv().equals(view)) {
                for (Entry entry : this.zzvG.entrySet()) {
                    if (view.equals((View) ((WeakReference) entry.getValue()).get())) {
                        String str = (String) entry.getKey();
                        return str;
                    }
                }
                return null;
            }
            str = "1007";
            return str;
        }
    }

    public void onClick(View view) {
        synchronized (this.zzqt) {
            if (this.zzvq == null) {
                return;
            }
            String zzi = zzi(view);
            if (zzi != null) {
                this.zzvq.performClick(zzi);
            }
        }
    }

    public void onGlobalLayout() {
        synchronized (this.zzqt) {
            if (this.zzvq != null) {
                this.zzvq.zzh(this.zzvF);
            }
        }
    }

    public void onScrollChanged() {
        synchronized (this.zzqt) {
            if (this.zzvq != null) {
                this.zzvq.zzh(this.zzvF);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        boolean z;
        synchronized (this.zzqt) {
            if (this.zzvq == null) {
                z = false;
            } else {
                this.zzvq.zzb(motionEvent);
                z = true;
            }
        }
        return z;
    }

    public zzd zzS(String str) {
        zzd zzw;
        synchronized (this.zzqt) {
            Object obj;
            WeakReference weakReference = (WeakReference) this.zzvG.get(str);
            if (weakReference == null) {
                obj = null;
            } else {
                View view = (View) weakReference.get();
            }
            zzw = zze.zzw(obj);
        }
        return zzw;
    }

    public void zza(String str, zzd com_google_android_gms_dynamic_zzd) {
        View view = (View) zze.zzn(com_google_android_gms_dynamic_zzd);
        synchronized (this.zzqt) {
            if (view == null) {
                this.zzvG.remove(str);
            } else {
                this.zzvG.put(str, new WeakReference(view));
                view.setOnClickListener(this);
            }
        }
    }

    public void zzb(zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzqt) {
            this.zzvq = (zzg) zze.zzn(com_google_android_gms_dynamic_zzd);
            this.zznV.removeAllViews();
            this.zzvH = zzdI();
            if (this.zzvH != null) {
                this.zznV.addView(this.zzvH);
            }
        }
    }

    zzb zzdI() {
        return this.zzvq.zza(this);
    }
}
