package com.google.android.gms.internal;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzf;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;
import java.util.Set;

@zzji
public class zzhq extends zzhv {
    static final Set<String> zzbyd;
    private int zzakh;
    private int zzaki;
    private final Object zzako;
    private AdSizeParcel zzapp;
    private final zzmd zzbnz;
    private final Activity zzbxt;
    private String zzbye;
    private boolean zzbyf;
    private int zzbyg;
    private int zzbyh;
    private int zzbyi;
    private int zzbyj;
    private ImageView zzbyk;
    private LinearLayout zzbyl;
    private zzhw zzbym;
    private PopupWindow zzbyn;
    private RelativeLayout zzbyo;
    private ViewGroup zzbyp;

    /* renamed from: com.google.android.gms.internal.zzhq.1 */
    class C13791 implements OnClickListener {
        final /* synthetic */ zzhq zzbyq;

        C13791(zzhq com_google_android_gms_internal_zzhq) {
            this.zzbyq = com_google_android_gms_internal_zzhq;
        }

        public void onClick(View view) {
            this.zzbyq.zzt(true);
        }
    }

    static {
        zzbyd = zzf.zzc("top-left", "top-right", "top-center", TtmlNode.CENTER, "bottom-left", "bottom-right", "bottom-center");
    }

    public zzhq(zzmd com_google_android_gms_internal_zzmd, zzhw com_google_android_gms_internal_zzhw) {
        super(com_google_android_gms_internal_zzmd, "resize");
        this.zzbye = "top-right";
        this.zzbyf = true;
        this.zzbyg = 0;
        this.zzbyh = 0;
        this.zzaki = -1;
        this.zzbyi = 0;
        this.zzbyj = 0;
        this.zzakh = -1;
        this.zzako = new Object();
        this.zzbnz = com_google_android_gms_internal_zzmd;
        this.zzbxt = com_google_android_gms_internal_zzmd.zzwy();
        this.zzbym = com_google_android_gms_internal_zzhw;
    }

    private void zzj(Map<String, String> map) {
        if (!TextUtils.isEmpty((CharSequence) map.get("width"))) {
            this.zzakh = zzu.zzgm().zzda((String) map.get("width"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("height"))) {
            this.zzaki = zzu.zzgm().zzda((String) map.get("height"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("offsetX"))) {
            this.zzbyi = zzu.zzgm().zzda((String) map.get("offsetX"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("offsetY"))) {
            this.zzbyj = zzu.zzgm().zzda((String) map.get("offsetY"));
        }
        if (!TextUtils.isEmpty((CharSequence) map.get("allowOffscreen"))) {
            this.zzbyf = Boolean.parseBoolean((String) map.get("allowOffscreen"));
        }
        String str = (String) map.get("customClosePosition");
        if (!TextUtils.isEmpty(str)) {
            this.zzbye = str;
        }
    }

    private int[] zzot() {
        if (!zzov()) {
            return null;
        }
        if (this.zzbyf) {
            return new int[]{this.zzbyg + this.zzbyi, this.zzbyh + this.zzbyj};
        }
        int[] zzi = zzu.zzgm().zzi(this.zzbxt);
        int[] zzk = zzu.zzgm().zzk(this.zzbxt);
        int i = zzi[0];
        int i2 = this.zzbyg + this.zzbyi;
        int i3 = this.zzbyh + this.zzbyj;
        if (i2 < 0) {
            i2 = 0;
        } else if (this.zzakh + i2 > i) {
            i2 = i - this.zzakh;
        }
        if (i3 < zzk[0]) {
            i3 = zzk[0];
        } else if (this.zzaki + i3 > zzk[1]) {
            i3 = zzk[1] - this.zzaki;
        }
        return new int[]{i2, i3};
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void execute(java.util.Map<java.lang.String, java.lang.String> r13) {
        /*
        r12 = this;
        r4 = -1;
        r5 = 1;
        r3 = 0;
        r6 = r12.zzako;
        monitor-enter(r6);
        r1 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x0011;
    L_0x000a:
        r1 = "Not an activity context. Cannot resize.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
    L_0x0010:
        return;
    L_0x0011:
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r1 = r1.zzeg();	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x0023;
    L_0x0019:
        r1 = "Webview is not yet available, size is not set.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0020:
        r1 = move-exception;
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        throw r1;
    L_0x0023:
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r1 = r1.zzeg();	 Catch:{ all -> 0x0020 }
        r1 = r1.zzazr;	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0034;
    L_0x002d:
        r1 = "Is interstitial. Cannot resize an interstitial.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0034:
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r1 = r1.zzxg();	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0043;
    L_0x003c:
        r1 = "Cannot resize an expanded banner.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0043:
        r12.zzj(r13);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzos();	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x0053;
    L_0x004c:
        r1 = "Invalid width and height options. Cannot resize.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0053:
        r1 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r7 = r1.getWindow();	 Catch:{ all -> 0x0020 }
        if (r7 == 0) goto L_0x0061;
    L_0x005b:
        r1 = r7.getDecorView();	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x0068;
    L_0x0061:
        r1 = "Activity context is not ready, cannot get window or decor view.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0068:
        r8 = r12.zzot();	 Catch:{ all -> 0x0020 }
        if (r8 != 0) goto L_0x0075;
    L_0x006e:
        r1 = "Resize location out of screen or close button is not visible.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x0075:
        r1 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r9 = r12.zzakh;	 Catch:{ all -> 0x0020 }
        r9 = r1.zzb(r2, r9);	 Catch:{ all -> 0x0020 }
        r1 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r10 = r12.zzaki;	 Catch:{ all -> 0x0020 }
        r10 = r1.zzb(r2, r10);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r1 = r1.getView();	 Catch:{ all -> 0x0020 }
        r2 = r1.getParent();	 Catch:{ all -> 0x0020 }
        if (r2 == 0) goto L_0x01d5;
    L_0x0099:
        r1 = r2 instanceof android.view.ViewGroup;	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x01d5;
    L_0x009d:
        r0 = r2;
        r0 = (android.view.ViewGroup) r0;	 Catch:{ all -> 0x0020 }
        r1 = r0;
        r11 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r11 = r11.getView();	 Catch:{ all -> 0x0020 }
        r1.removeView(r11);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyn;	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x01ce;
    L_0x00ae:
        r2 = (android.view.ViewGroup) r2;	 Catch:{ all -> 0x0020 }
        r12.zzbyp = r2;	 Catch:{ all -> 0x0020 }
        r1 = com.google.android.gms.ads.internal.zzu.zzgm();	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = r2.getView();	 Catch:{ all -> 0x0020 }
        r1 = r1.zzq(r2);	 Catch:{ all -> 0x0020 }
        r2 = new android.widget.ImageView;	 Catch:{ all -> 0x0020 }
        r11 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r2.<init>(r11);	 Catch:{ all -> 0x0020 }
        r12.zzbyk = r2;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbyk;	 Catch:{ all -> 0x0020 }
        r2.setImageBitmap(r1);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r1 = r1.zzeg();	 Catch:{ all -> 0x0020 }
        r12.zzapp = r1;	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyp;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbyk;	 Catch:{ all -> 0x0020 }
        r1.addView(r2);	 Catch:{ all -> 0x0020 }
    L_0x00dd:
        r1 = new android.widget.RelativeLayout;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r1.<init>(r2);	 Catch:{ all -> 0x0020 }
        r12.zzbyo = r1;	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r2 = 0;
        r1.setBackgroundColor(r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r2 = new android.view.ViewGroup$LayoutParams;	 Catch:{ all -> 0x0020 }
        r2.<init>(r9, r10);	 Catch:{ all -> 0x0020 }
        r1.setLayoutParams(r2);	 Catch:{ all -> 0x0020 }
        r1 = com.google.android.gms.ads.internal.zzu.zzgm();	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r11 = 0;
        r1 = r1.zza(r2, r9, r10, r11);	 Catch:{ all -> 0x0020 }
        r12.zzbyn = r1;	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyn;	 Catch:{ all -> 0x0020 }
        r2 = 1;
        r1.setOutsideTouchable(r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyn;	 Catch:{ all -> 0x0020 }
        r2 = 1;
        r1.setTouchable(r2);	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbyn;	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyf;	 Catch:{ all -> 0x0020 }
        if (r1 != 0) goto L_0x01dd;
    L_0x0115:
        r1 = r5;
    L_0x0116:
        r2.setClippingEnabled(r1);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = r2.getView();	 Catch:{ all -> 0x0020 }
        r9 = -1;
        r10 = -1;
        r1.addView(r2, r9, r10);	 Catch:{ all -> 0x0020 }
        r1 = new android.widget.LinearLayout;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r1.<init>(r2);	 Catch:{ all -> 0x0020 }
        r12.zzbyl = r1;	 Catch:{ all -> 0x0020 }
        r2 = new android.widget.RelativeLayout$LayoutParams;	 Catch:{ all -> 0x0020 }
        r1 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ all -> 0x0020 }
        r9 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r10 = 50;
        r1 = r1.zzb(r9, r10);	 Catch:{ all -> 0x0020 }
        r9 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ all -> 0x0020 }
        r10 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r11 = 50;
        r9 = r9.zzb(r10, r11);	 Catch:{ all -> 0x0020 }
        r2.<init>(r1, r9);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbye;	 Catch:{ all -> 0x0020 }
        r9 = r1.hashCode();	 Catch:{ all -> 0x0020 }
        switch(r9) {
            case -1364013995: goto L_0x01f6;
            case -1012429441: goto L_0x01e0;
            case -655373719: goto L_0x0201;
            case 1163912186: goto L_0x0217;
            case 1288627767: goto L_0x020c;
            case 1755462605: goto L_0x01eb;
            default: goto L_0x0155;
        };	 Catch:{ all -> 0x0020 }
    L_0x0155:
        r1 = r4;
    L_0x0156:
        switch(r1) {
            case 0: goto L_0x0222;
            case 1: goto L_0x022e;
            case 2: goto L_0x023a;
            case 3: goto L_0x0241;
            case 4: goto L_0x024d;
            case 5: goto L_0x0259;
            default: goto L_0x0159;
        };	 Catch:{ all -> 0x0020 }
    L_0x0159:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 11;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
    L_0x0163:
        r1 = r12.zzbyl;	 Catch:{ all -> 0x0020 }
        r3 = new com.google.android.gms.internal.zzhq$1;	 Catch:{ all -> 0x0020 }
        r3.<init>(r12);	 Catch:{ all -> 0x0020 }
        r1.setOnClickListener(r3);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyl;	 Catch:{ all -> 0x0020 }
        r3 = "Close button";
        r1.setContentDescription(r3);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r3 = r12.zzbyl;	 Catch:{ all -> 0x0020 }
        r1.addView(r3, r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyn;	 Catch:{ RuntimeException -> 0x0265 }
        r2 = r7.getDecorView();	 Catch:{ RuntimeException -> 0x0265 }
        r3 = 0;
        r4 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ RuntimeException -> 0x0265 }
        r5 = r12.zzbxt;	 Catch:{ RuntimeException -> 0x0265 }
        r7 = 0;
        r7 = r8[r7];	 Catch:{ RuntimeException -> 0x0265 }
        r4 = r4.zzb(r5, r7);	 Catch:{ RuntimeException -> 0x0265 }
        r5 = com.google.android.gms.ads.internal.client.zzm.zzkr();	 Catch:{ RuntimeException -> 0x0265 }
        r7 = r12.zzbxt;	 Catch:{ RuntimeException -> 0x0265 }
        r9 = 1;
        r9 = r8[r9];	 Catch:{ RuntimeException -> 0x0265 }
        r5 = r5.zzb(r7, r9);	 Catch:{ RuntimeException -> 0x0265 }
        r1.showAtLocation(r2, r3, r4, r5);	 Catch:{ RuntimeException -> 0x0265 }
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x0020 }
        r2 = 1;
        r2 = r8[r2];	 Catch:{ all -> 0x0020 }
        r12.zzb(r1, r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = new com.google.android.gms.ads.internal.client.AdSizeParcel;	 Catch:{ all -> 0x0020 }
        r3 = r12.zzbxt;	 Catch:{ all -> 0x0020 }
        r4 = new com.google.android.gms.ads.AdSize;	 Catch:{ all -> 0x0020 }
        r5 = r12.zzakh;	 Catch:{ all -> 0x0020 }
        r7 = r12.zzaki;	 Catch:{ all -> 0x0020 }
        r4.<init>(r5, r7);	 Catch:{ all -> 0x0020 }
        r2.<init>(r3, r4);	 Catch:{ all -> 0x0020 }
        r1.zza(r2);	 Catch:{ all -> 0x0020 }
        r1 = 0;
        r1 = r8[r1];	 Catch:{ all -> 0x0020 }
        r2 = 1;
        r2 = r8[r2];	 Catch:{ all -> 0x0020 }
        r12.zzc(r1, r2);	 Catch:{ all -> 0x0020 }
        r1 = "resized";
        r12.zzcd(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x01ce:
        r1 = r12.zzbyn;	 Catch:{ all -> 0x0020 }
        r1.dismiss();	 Catch:{ all -> 0x0020 }
        goto L_0x00dd;
    L_0x01d5:
        r1 = "Webview is detached, probably in the middle of a resize or expand.";
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x01dd:
        r1 = r3;
        goto L_0x0116;
    L_0x01e0:
        r5 = "top-left";
        r1 = r1.equals(r5);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x01e8:
        r1 = r3;
        goto L_0x0156;
    L_0x01eb:
        r3 = "top-center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x01f3:
        r1 = r5;
        goto L_0x0156;
    L_0x01f6:
        r3 = "center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x01fe:
        r1 = 2;
        goto L_0x0156;
    L_0x0201:
        r3 = "bottom-left";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x0209:
        r1 = 3;
        goto L_0x0156;
    L_0x020c:
        r3 = "bottom-center";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x0214:
        r1 = 4;
        goto L_0x0156;
    L_0x0217:
        r3 = "bottom-right";
        r1 = r1.equals(r3);	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x0155;
    L_0x021f:
        r1 = 5;
        goto L_0x0156;
    L_0x0222:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 9;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x022e:
        r1 = 10;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 14;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x023a:
        r1 = 13;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x0241:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 9;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x024d:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 14;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x0259:
        r1 = 12;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        r1 = 11;
        r2.addRule(r1);	 Catch:{ all -> 0x0020 }
        goto L_0x0163;
    L_0x0265:
        r1 = move-exception;
        r2 = "Cannot show popup window: ";
        r1 = r1.getMessage();	 Catch:{ all -> 0x0020 }
        r1 = java.lang.String.valueOf(r1);	 Catch:{ all -> 0x0020 }
        r3 = r1.length();	 Catch:{ all -> 0x0020 }
        if (r3 == 0) goto L_0x02a8;
    L_0x0276:
        r1 = r2.concat(r1);	 Catch:{ all -> 0x0020 }
    L_0x027a:
        r12.zzcb(r1);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyo;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = r2.getView();	 Catch:{ all -> 0x0020 }
        r1.removeView(r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyp;	 Catch:{ all -> 0x0020 }
        if (r1 == 0) goto L_0x02a5;
    L_0x028c:
        r1 = r12.zzbyp;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbyk;	 Catch:{ all -> 0x0020 }
        r1.removeView(r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbyp;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = r2.getView();	 Catch:{ all -> 0x0020 }
        r1.addView(r2);	 Catch:{ all -> 0x0020 }
        r1 = r12.zzbnz;	 Catch:{ all -> 0x0020 }
        r2 = r12.zzapp;	 Catch:{ all -> 0x0020 }
        r1.zza(r2);	 Catch:{ all -> 0x0020 }
    L_0x02a5:
        monitor-exit(r6);	 Catch:{ all -> 0x0020 }
        goto L_0x0010;
    L_0x02a8:
        r1 = new java.lang.String;	 Catch:{ all -> 0x0020 }
        r1.<init>(r2);	 Catch:{ all -> 0x0020 }
        goto L_0x027a;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzhq.execute(java.util.Map):void");
    }

    public void zza(int i, int i2, boolean z) {
        synchronized (this.zzako) {
            this.zzbyg = i;
            this.zzbyh = i2;
            if (this.zzbyn != null && z) {
                int[] zzot = zzot();
                if (zzot != null) {
                    this.zzbyn.update(zzm.zzkr().zzb(this.zzbxt, zzot[0]), zzm.zzkr().zzb(this.zzbxt, zzot[1]), this.zzbyn.getWidth(), this.zzbyn.getHeight());
                    zzc(zzot[0], zzot[1]);
                } else {
                    zzt(true);
                }
            }
        }
    }

    void zzb(int i, int i2) {
        if (this.zzbym != null) {
            this.zzbym.zza(i, i2, this.zzakh, this.zzaki);
        }
    }

    void zzc(int i, int i2) {
        zzb(i, i2 - zzu.zzgm().zzk(this.zzbxt)[0], this.zzakh, this.zzaki);
    }

    public void zzd(int i, int i2) {
        this.zzbyg = i;
        this.zzbyh = i2;
    }

    boolean zzos() {
        return this.zzakh > -1 && this.zzaki > -1;
    }

    public boolean zzou() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzbyn != null;
        }
        return z;
    }

    boolean zzov() {
        int[] zzi = zzu.zzgm().zzi(this.zzbxt);
        int[] zzk = zzu.zzgm().zzk(this.zzbxt);
        int i = zzi[0];
        int i2 = zzi[1];
        if (this.zzakh < 50 || this.zzakh > i) {
            zzb.zzdi("Width is too small or too large.");
            return false;
        } else if (this.zzaki < 50 || this.zzaki > i2) {
            zzb.zzdi("Height is too small or too large.");
            return false;
        } else if (this.zzaki == i2 && this.zzakh == i) {
            zzb.zzdi("Cannot resize to a full-screen ad.");
            return false;
        } else {
            if (this.zzbyf) {
                int i3;
                String str = this.zzbye;
                boolean z = true;
                switch (str.hashCode()) {
                    case -1364013995:
                        if (str.equals(TtmlNode.CENTER)) {
                            z = true;
                            break;
                        }
                        break;
                    case -1012429441:
                        if (str.equals("top-left")) {
                            z = false;
                            break;
                        }
                        break;
                    case -655373719:
                        if (str.equals("bottom-left")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1163912186:
                        if (str.equals("bottom-right")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1288627767:
                        if (str.equals("bottom-center")) {
                            z = true;
                            break;
                        }
                        break;
                    case 1755462605:
                        if (str.equals("top-center")) {
                            z = true;
                            break;
                        }
                        break;
                }
                switch (z) {
                    case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                        i3 = this.zzbyi + this.zzbyg;
                        i2 = this.zzbyh + this.zzbyj;
                        break;
                    case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                        i3 = ((this.zzbyg + this.zzbyi) + (this.zzakh / 2)) - 25;
                        i2 = this.zzbyh + this.zzbyj;
                        break;
                    case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                        i3 = ((this.zzbyg + this.zzbyi) + (this.zzakh / 2)) - 25;
                        i2 = ((this.zzbyh + this.zzbyj) + (this.zzaki / 2)) - 25;
                        break;
                    case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                        i3 = this.zzbyi + this.zzbyg;
                        i2 = ((this.zzbyh + this.zzbyj) + this.zzaki) - 50;
                        break;
                    case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                        i3 = ((this.zzbyg + this.zzbyi) + (this.zzakh / 2)) - 25;
                        i2 = ((this.zzbyh + this.zzbyj) + this.zzaki) - 50;
                        break;
                    case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                        i3 = ((this.zzbyg + this.zzbyi) + this.zzakh) - 50;
                        i2 = ((this.zzbyh + this.zzbyj) + this.zzaki) - 50;
                        break;
                    default:
                        i3 = ((this.zzbyg + this.zzbyi) + this.zzakh) - 50;
                        i2 = this.zzbyh + this.zzbyj;
                        break;
                }
                if (i3 < 0 || i3 + 50 > i || r2 < zzk[0] || r2 + 50 > zzk[1]) {
                    return false;
                }
            }
            return true;
        }
    }

    public void zzt(boolean z) {
        synchronized (this.zzako) {
            if (this.zzbyn != null) {
                this.zzbyn.dismiss();
                this.zzbyo.removeView(this.zzbnz.getView());
                if (this.zzbyp != null) {
                    this.zzbyp.removeView(this.zzbyk);
                    this.zzbyp.addView(this.zzbnz.getView());
                    this.zzbnz.zza(this.zzapp);
                }
                if (z) {
                    zzcd("default");
                    if (this.zzbym != null) {
                        this.zzbym.zzfc();
                    }
                }
                this.zzbyn = null;
                this.zzbyo = null;
                this.zzbyp = null;
                this.zzbyl = null;
            }
        }
    }
}
