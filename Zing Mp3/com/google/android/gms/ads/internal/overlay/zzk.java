package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.InputDeviceCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.HashMap;
import java.util.Map;

@zzji
public class zzk extends FrameLayout implements zzh {
    private final zzmd zzbnz;
    private String zzbrb;
    private final FrameLayout zzcby;
    private final zzdz zzcbz;
    private final zzz zzcca;
    private final long zzccb;
    @Nullable
    private zzi zzccc;
    private boolean zzccd;
    private boolean zzcce;
    private boolean zzccf;
    private boolean zzccg;
    private long zzcch;
    private long zzcci;
    private Bitmap zzccj;
    private ImageView zzcck;
    private boolean zzccl;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzk.1 */
    class C10901 implements Runnable {
        final /* synthetic */ zzk zzccm;

        C10901(zzk com_google_android_gms_ads_internal_overlay_zzk) {
            this.zzccm = com_google_android_gms_ads_internal_overlay_zzk;
        }

        public void run() {
            this.zzccm.zza("surfaceCreated", new String[0]);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzk.2 */
    class C10912 implements Runnable {
        final /* synthetic */ zzk zzccm;

        C10912(zzk com_google_android_gms_ads_internal_overlay_zzk) {
            this.zzccm = com_google_android_gms_ads_internal_overlay_zzk;
        }

        public void run() {
            this.zzccm.zza("surfaceDestroyed", new String[0]);
        }
    }

    public zzk(Context context, zzmd com_google_android_gms_internal_zzmd, int i, boolean z, zzdz com_google_android_gms_internal_zzdz) {
        super(context);
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzcbz = com_google_android_gms_internal_zzdz;
        this.zzcby = new FrameLayout(context);
        addView(this.zzcby, new LayoutParams(-1, -1));
        zzc.zzu(com_google_android_gms_internal_zzmd.zzec());
        this.zzccc = com_google_android_gms_internal_zzmd.zzec().zzamq.zza(context, com_google_android_gms_internal_zzmd, i, z, com_google_android_gms_internal_zzdz);
        if (this.zzccc != null) {
            this.zzcby.addView(this.zzccc, new LayoutParams(-1, -1, 17));
            if (((Boolean) zzdr.zzbdx.get()).booleanValue()) {
                zzqj();
            }
        }
        this.zzcck = new ImageView(context);
        this.zzccb = ((Long) zzdr.zzbeb.get()).longValue();
        this.zzccg = ((Boolean) zzdr.zzbdz.get()).booleanValue();
        if (this.zzcbz != null) {
            this.zzcbz.zzg("spinner_used", this.zzccg ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        }
        this.zzcca = new zzz(this);
        this.zzcca.zzrg();
        if (this.zzccc != null) {
            this.zzccc.zza(this);
        }
        if (this.zzccc == null) {
            zzk("AdVideoUnderlay Error", "Allocating player failed.");
        }
    }

    private void zza(String str, String... strArr) {
        Map hashMap = new HashMap();
        hashMap.put(DataLayer.EVENT_KEY, str);
        int length = strArr.length;
        int i = 0;
        Object obj = null;
        while (i < length) {
            Object obj2 = strArr[i];
            if (obj != null) {
                hashMap.put(obj, obj2);
                obj2 = null;
            }
            i++;
            obj = obj2;
        }
        this.zzbnz.zza("onVideoEvent", hashMap);
    }

    private void zzg(int i, int i2) {
        if (this.zzccg) {
            int max = Math.max(i / ((Integer) zzdr.zzbea.get()).intValue(), 1);
            int max2 = Math.max(i2 / ((Integer) zzdr.zzbea.get()).intValue(), 1);
            if (this.zzccj == null || this.zzccj.getWidth() != max || this.zzccj.getHeight() != max2) {
                this.zzccj = Bitmap.createBitmap(max, max2, Config.ARGB_8888);
                this.zzccl = false;
            }
        }
    }

    public static void zzi(zzmd com_google_android_gms_internal_zzmd) {
        Map hashMap = new HashMap();
        hashMap.put(DataLayer.EVENT_KEY, "no_video_view");
        com_google_android_gms_internal_zzmd.zza("onVideoEvent", hashMap);
    }

    @TargetApi(14)
    private void zzql() {
        if (this.zzccj != null) {
            long elapsedRealtime = zzu.zzgs().elapsedRealtime();
            if (this.zzccc.getBitmap(this.zzccj) != null) {
                this.zzccl = true;
            }
            elapsedRealtime = zzu.zzgs().elapsedRealtime() - elapsedRealtime;
            if (zzkx.zzvo()) {
                zzkx.m1697v("Spinner frame grab took " + elapsedRealtime + "ms");
            }
            if (elapsedRealtime > this.zzccb) {
                zzb.zzdi("Spinner frame grab crossed jank threshold! Suspending spinner.");
                this.zzccg = false;
                this.zzccj = null;
                if (this.zzcbz != null) {
                    this.zzcbz.zzg("spinner_jank", Long.toString(elapsedRealtime));
                }
            }
        }
    }

    private void zzqm() {
        if (this.zzccl && this.zzccj != null && !zzqo()) {
            this.zzcck.setImageBitmap(this.zzccj);
            this.zzcck.invalidate();
            this.zzcby.addView(this.zzcck, new LayoutParams(-1, -1));
            this.zzcby.bringChildToFront(this.zzcck);
        }
    }

    private void zzqn() {
        if (zzqo()) {
            this.zzcby.removeView(this.zzcck);
        }
    }

    private boolean zzqo() {
        return this.zzcck.getParent() != null;
    }

    private void zzqp() {
        if (this.zzbnz.zzwy() != null && !this.zzcce) {
            this.zzccf = (this.zzbnz.zzwy().getWindow().getAttributes().flags & AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS) != 0;
            if (!this.zzccf) {
                this.zzbnz.zzwy().getWindow().addFlags(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
                this.zzcce = true;
            }
        }
    }

    private void zzqq() {
        if (this.zzbnz.zzwy() != null && this.zzcce && !this.zzccf) {
            this.zzbnz.zzwy().getWindow().clearFlags(AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            this.zzcce = false;
        }
    }

    public void destroy() {
        this.zzcca.cancel();
        if (this.zzccc != null) {
            this.zzccc.stop();
        }
        zzqq();
    }

    public void onPaused() {
        zza("pause", new String[0]);
        zzqq();
        this.zzccd = false;
    }

    public void pause() {
        if (this.zzccc != null) {
            this.zzccc.pause();
        }
    }

    public void play() {
        if (this.zzccc != null) {
            this.zzccc.play();
        }
    }

    public void seekTo(int i) {
        if (this.zzccc != null) {
            this.zzccc.seekTo(i);
        }
    }

    public void zza(float f, float f2) {
        if (this.zzccc != null) {
            this.zzccc.zza(f, f2);
        }
    }

    public void zzb(float f) {
        if (this.zzccc != null) {
            this.zzccc.zzb(f);
        }
    }

    public void zzce(String str) {
        this.zzbrb = str;
    }

    public void zzd(int i, int i2, int i3, int i4) {
        if (i3 != 0 && i4 != 0) {
            ViewGroup.LayoutParams layoutParams = new LayoutParams(i3, i4);
            layoutParams.setMargins(i, i2, 0, 0);
            this.zzcby.setLayoutParams(layoutParams);
            requestLayout();
        }
    }

    public void zzf(int i, int i2) {
        zzg(i, i2);
    }

    @TargetApi(14)
    public void zzf(MotionEvent motionEvent) {
        if (this.zzccc != null) {
            this.zzccc.dispatchTouchEvent(motionEvent);
        }
    }

    public void zzk(String str, @Nullable String str2) {
        zza(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE, "what", str, "extra", str2);
    }

    public void zznt() {
        if (this.zzccc != null) {
            if (TextUtils.isEmpty(this.zzbrb)) {
                zza("no_src", new String[0]);
            } else {
                this.zzccc.setVideoPath(this.zzbrb);
            }
        }
    }

    public void zzqb() {
        zzlb.zzcvl.post(new C10901(this));
    }

    public void zzqc() {
        if (this.zzccc != null && this.zzcci == 0) {
            float duration = ((float) this.zzccc.getDuration()) / 1000.0f;
            int videoWidth = this.zzccc.getVideoWidth();
            int videoHeight = this.zzccc.getVideoHeight();
            zza("canplaythrough", "duration", String.valueOf(duration), "videoWidth", String.valueOf(videoWidth), "videoHeight", String.valueOf(videoHeight));
        }
    }

    public void zzqd() {
        zzqp();
        this.zzccd = true;
    }

    public void zzqe() {
        zza("ended", new String[0]);
        zzqq();
    }

    public void zzqf() {
        zzqm();
        this.zzcci = this.zzcch;
        zzlb.zzcvl.post(new C10912(this));
    }

    public void zzqg() {
        if (this.zzccd) {
            zzqn();
        }
        zzql();
    }

    public void zzqh() {
        if (this.zzccc != null) {
            this.zzccc.zzqh();
        }
    }

    public void zzqi() {
        if (this.zzccc != null) {
            this.zzccc.zzqi();
        }
    }

    @TargetApi(14)
    public void zzqj() {
        if (this.zzccc != null) {
            View textView = new TextView(this.zzccc.getContext());
            String str = "AdMob - ";
            String valueOf = String.valueOf(this.zzccc.zzpg());
            textView.setText(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            textView.setTextColor(SupportMenu.CATEGORY_MASK);
            textView.setBackgroundColor(InputDeviceCompat.SOURCE_ANY);
            this.zzcby.addView(textView, new LayoutParams(-2, -2, 17));
            this.zzcby.bringChildToFront(textView);
        }
    }

    void zzqk() {
        if (this.zzccc != null) {
            long currentPosition = (long) this.zzccc.getCurrentPosition();
            if (this.zzcch != currentPosition && currentPosition > 0) {
                float f = ((float) currentPosition) / 1000.0f;
                zza("timeupdate", "time", String.valueOf(f));
                this.zzcch = currentPosition;
            }
        }
    }
}
