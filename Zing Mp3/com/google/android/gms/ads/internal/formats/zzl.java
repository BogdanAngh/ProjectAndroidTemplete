package com.google.android.gms.ads.internal.formats;

import android.graphics.Point;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.dynamic.zzd;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzei.zza;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzl extends zza implements OnClickListener, OnTouchListener, OnGlobalLayoutListener, OnScrollChangedListener {
    private final Object zzako;
    @Nullable
    private FrameLayout zzald;
    @Nullable
    private zzi zzbnj;
    private final FrameLayout zzboo;
    private Map<String, WeakReference<View>> zzbop;
    @Nullable
    private zzb zzboq;
    boolean zzbor;
    int zzbos;
    int zzbot;

    /* renamed from: com.google.android.gms.ads.internal.formats.zzl.1 */
    class C10771 implements Runnable {
        final /* synthetic */ zzj zzbou;
        final /* synthetic */ zzl zzbov;

        C10771(zzl com_google_android_gms_ads_internal_formats_zzl, zzj com_google_android_gms_ads_internal_formats_zzj) {
            this.zzbov = com_google_android_gms_ads_internal_formats_zzl;
            this.zzbou = com_google_android_gms_ads_internal_formats_zzj;
        }

        public void run() {
            zzmd zzmx = this.zzbou.zzmx();
            if (!(zzmx == null || this.zzbov.zzald == null)) {
                this.zzbov.zzald.addView(zzmx.getView());
            }
            if (!(this.zzbou instanceof zzh)) {
                this.zzbov.zzd(this.zzbou);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.formats.zzl.2 */
    class C10782 implements zzg {
        final /* synthetic */ zzl zzbov;
        final /* synthetic */ View zzbow;

        C10782(zzl com_google_android_gms_ads_internal_formats_zzl, View view) {
            this.zzbov = com_google_android_gms_ads_internal_formats_zzl;
            this.zzbow = view;
        }

        public void zzc(MotionEvent motionEvent) {
            this.zzbov.onTouch(null, motionEvent);
        }

        public void zzmu() {
            this.zzbov.onClick(this.zzbow);
        }
    }

    public zzl(FrameLayout frameLayout, FrameLayout frameLayout2) {
        this.zzako = new Object();
        this.zzbop = new HashMap();
        this.zzbor = false;
        this.zzboo = frameLayout;
        this.zzald = frameLayout2;
        zzu.zzhk().zza(this.zzboo, (OnGlobalLayoutListener) this);
        zzu.zzhk().zza(this.zzboo, (OnScrollChangedListener) this);
        this.zzboo.setOnTouchListener(this);
        this.zzboo.setOnClickListener(this);
    }

    private void zzd(zzj com_google_android_gms_ads_internal_formats_zzj) {
        if (this.zzbop.containsKey("2011")) {
            View view = (View) ((WeakReference) this.zzbop.get("2011")).get();
            if (view instanceof FrameLayout) {
                com_google_android_gms_ads_internal_formats_zzj.zza(view, new C10782(this, view));
                return;
            } else {
                com_google_android_gms_ads_internal_formats_zzj.zzmz();
                return;
            }
        }
        com_google_android_gms_ads_internal_formats_zzj.zzmz();
    }

    public void destroy() {
        synchronized (this.zzako) {
            if (this.zzald != null) {
                this.zzald.removeAllViews();
            }
            this.zzald = null;
            this.zzbop = null;
            this.zzboq = null;
            this.zzbnj = null;
        }
    }

    int getMeasuredHeight() {
        return this.zzboo.getMeasuredHeight();
    }

    int getMeasuredWidth() {
        return this.zzboo.getMeasuredWidth();
    }

    public void onClick(View view) {
        synchronized (this.zzako) {
            if (this.zzbnj == null) {
                return;
            }
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put("x", zzab(this.zzbos));
                jSONObject.put("y", zzab(this.zzbot));
            } catch (JSONException e) {
                zzb.zzdi("Unable to get click location");
            }
            if (this.zzboq == null || !this.zzboq.zzmm().equals(view)) {
                this.zzbnj.zza(view, this.zzbop, jSONObject, this.zzboo);
            } else if (!(this.zzbnj instanceof zzh) || ((zzh) this.zzbnj).zzmw() == null) {
                this.zzbnj.zza(view, "1007", jSONObject, this.zzbop, this.zzboo);
            } else {
                ((zzh) this.zzbnj).zzmw().zza(view, "1007", jSONObject, this.zzbop, this.zzboo);
            }
        }
    }

    public void onGlobalLayout() {
        synchronized (this.zzako) {
            if (this.zzbor) {
                int measuredWidth = getMeasuredWidth();
                int measuredHeight = getMeasuredHeight();
                if (!(measuredWidth == 0 || measuredHeight == 0 || this.zzald == null)) {
                    this.zzald.setLayoutParams(new LayoutParams(measuredWidth, measuredHeight));
                    this.zzbor = false;
                }
            }
            if (this.zzbnj != null) {
                this.zzbnj.zzd(this.zzboo, this.zzbop);
            }
        }
    }

    public void onScrollChanged() {
        synchronized (this.zzako) {
            if (this.zzbnj != null) {
                this.zzbnj.zzd(this.zzboo, this.zzbop);
            }
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        synchronized (this.zzako) {
            if (this.zzbnj == null) {
            } else {
                Point zze = zze(motionEvent);
                this.zzbos = zze.x;
                this.zzbot = zze.y;
                MotionEvent obtain = MotionEvent.obtain(motionEvent);
                obtain.setLocation((float) zze.x, (float) zze.y);
                this.zzbnj.zzd(obtain);
                obtain.recycle();
            }
        }
        return false;
    }

    int zzab(int i) {
        return zzm.zzkr().zzc(this.zzbnj.getContext(), i);
    }

    public zzd zzaw(String str) {
        zzd zzac;
        synchronized (this.zzako) {
            Object obj;
            WeakReference weakReference = (WeakReference) this.zzbop.get(str);
            if (weakReference == null) {
                obj = null;
            } else {
                View view = (View) weakReference.get();
            }
            zzac = zze.zzac(obj);
        }
        return zzac;
    }

    @Nullable
    zzb zzc(zzj com_google_android_gms_ads_internal_formats_zzj) {
        return com_google_android_gms_ads_internal_formats_zzj.zza((OnClickListener) this);
    }

    public void zzc(String str, zzd com_google_android_gms_dynamic_zzd) {
        View view = (View) zze.zzae(com_google_android_gms_dynamic_zzd);
        synchronized (this.zzako) {
            if (view == null) {
                this.zzbop.remove(str);
            } else {
                this.zzbop.put(str, new WeakReference(view));
                view.setOnTouchListener(this);
                view.setClickable(true);
                view.setOnClickListener(this);
            }
        }
    }

    Point zze(MotionEvent motionEvent) {
        int[] iArr = new int[2];
        this.zzboo.getLocationOnScreen(iArr);
        return new Point((int) (motionEvent.getRawX() - ((float) iArr[0])), (int) (motionEvent.getRawY() - ((float) iArr[1])));
    }

    public void zze(zzd com_google_android_gms_dynamic_zzd) {
        synchronized (this.zzako) {
            zzj(null);
            Object zzae = zze.zzae(com_google_android_gms_dynamic_zzd);
            if (zzae instanceof zzj) {
                if (this.zzald != null) {
                    this.zzald.setLayoutParams(new LayoutParams(0, 0));
                    this.zzboo.requestLayout();
                }
                this.zzbor = true;
                zzj com_google_android_gms_ads_internal_formats_zzj = (zzj) zzae;
                if (this.zzbnj != null && ((Boolean) zzdr.zzbjb.get()).booleanValue()) {
                    this.zzbnj.zzc(this.zzboo, this.zzbop);
                }
                if ((this.zzbnj instanceof zzh) && ((zzh) this.zzbnj).zzmv()) {
                    ((zzh) this.zzbnj).zzc(com_google_android_gms_ads_internal_formats_zzj);
                } else {
                    this.zzbnj = com_google_android_gms_ads_internal_formats_zzj;
                    if (com_google_android_gms_ads_internal_formats_zzj instanceof zzh) {
                        ((zzh) com_google_android_gms_ads_internal_formats_zzj).zzc(null);
                    }
                }
                if (((Boolean) zzdr.zzbjb.get()).booleanValue()) {
                    this.zzald.setClickable(false);
                }
                this.zzald.removeAllViews();
                this.zzboq = zzc(com_google_android_gms_ads_internal_formats_zzj);
                if (this.zzboq != null) {
                    this.zzbop.put("1007", new WeakReference(this.zzboq.zzmm()));
                    this.zzald.addView(this.zzboq);
                }
                com_google_android_gms_ads_internal_formats_zzj.zza(this.zzboo, this.zzbop, (OnTouchListener) this, (OnClickListener) this);
                zzlb.zzcvl.post(new C10771(this, com_google_android_gms_ads_internal_formats_zzj));
                zzj(this.zzboo);
                return;
            }
            zzb.zzdi("Not an instance of native engine. This is most likely a transient error");
        }
    }

    void zzj(@Nullable View view) {
        if (this.zzbnj != null) {
            zzi zzmw = this.zzbnj instanceof zzh ? ((zzh) this.zzbnj).zzmw() : this.zzbnj;
            if (zzmw != null) {
                zzmw.zzj(view);
            }
        }
    }
}
