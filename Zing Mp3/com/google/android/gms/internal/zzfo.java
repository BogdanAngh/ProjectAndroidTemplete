package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.MotionEvent;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.overlay.zzk;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.analytics.ecommerce.Promotion;
import java.util.Map;
import org.json.JSONObject;

@zzji
public final class zzfo implements zzfe {
    private boolean zzbqz;

    private static int zza(Context context, Map<String, String> map, String str, int i) {
        String str2 = (String) map.get(str);
        if (str2 != null) {
            try {
                i = zzm.zzkr().zzb(context, Integer.parseInt(str2));
            } catch (NumberFormatException e) {
                zzb.zzdi(new StringBuilder((String.valueOf(str).length() + 34) + String.valueOf(str2).length()).append("Could not parse ").append(str).append(" in a video GMSG: ").append(str2).toString());
            }
        }
        return i;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
        if (str == null) {
            zzb.zzdi("Action missing from video GMSG.");
            return;
        }
        if (zzb.zzbi(3)) {
            JSONObject jSONObject = new JSONObject(map);
            jSONObject.remove("google.afma.Notify_dt");
            String valueOf = String.valueOf(jSONObject.toString());
            zzb.zzdg(new StringBuilder((String.valueOf(str).length() + 13) + String.valueOf(valueOf).length()).append("Video GMSG: ").append(str).append(" ").append(valueOf).toString());
        }
        if ("background".equals(str)) {
            valueOf = (String) map.get(TtmlNode.ATTR_TTS_COLOR);
            if (TextUtils.isEmpty(valueOf)) {
                zzb.zzdi("Color parameter missing from color video GMSG.");
                return;
            }
            try {
                com_google_android_gms_internal_zzmd.setBackgroundColor(Color.parseColor(valueOf));
                return;
            } catch (IllegalArgumentException e) {
                zzb.zzdi("Invalid color parameter in video GMSG.");
                return;
            }
        }
        zzmc zzxk = com_google_android_gms_internal_zzmd.zzxk();
        if (zzxk == null) {
            zzb.zzdi("Could not get underlay container for a video GMSG.");
            return;
        }
        boolean equals = "new".equals(str);
        boolean equals2 = "position".equals(str);
        int zza;
        int min;
        if (equals || equals2) {
            Context context = com_google_android_gms_internal_zzmd.getContext();
            int zza2 = zza(context, map, "x", 0);
            zza = zza(context, map, "y", 0);
            int zza3 = zza(context, map, "w", -1);
            int zza4 = zza(context, map, "h", -1);
            if (((Boolean) zzdr.zzbjh.get()).booleanValue()) {
                min = Math.min(zza3, com_google_android_gms_internal_zzmd.getMeasuredWidth() - zza2);
                zza4 = Math.min(zza4, com_google_android_gms_internal_zzmd.getMeasuredHeight() - zza);
            } else {
                min = zza3;
            }
            try {
                zza3 = Integer.parseInt((String) map.get("player"));
            } catch (NumberFormatException e2) {
                zza3 = 0;
            }
            boolean parseBoolean = Boolean.parseBoolean((String) map.get("spherical"));
            if (equals && zzxk.zzwv() == null) {
                zzxk.zza(zza2, zza, min, zza4, zza3, parseBoolean);
                return;
            } else {
                zzxk.zze(zza2, zza, min, zza4);
                return;
            }
        }
        zzk zzwv = zzxk.zzwv();
        if (zzwv == null) {
            zzk.zzi(com_google_android_gms_internal_zzmd);
        } else if (Promotion.ACTION_CLICK.equals(str)) {
            r0 = com_google_android_gms_internal_zzmd.getContext();
            zza = zza(r0, map, "x", 0);
            min = zza(r0, map, "y", 0);
            long uptimeMillis = SystemClock.uptimeMillis();
            MotionEvent obtain = MotionEvent.obtain(uptimeMillis, uptimeMillis, 0, (float) zza, (float) min, 0);
            zzwv.zzf(obtain);
            obtain.recycle();
        } else if ("currentTime".equals(str)) {
            valueOf = (String) map.get("time");
            if (valueOf == null) {
                zzb.zzdi("Time parameter missing from currentTime video GMSG.");
                return;
            }
            try {
                zzwv.seekTo((int) (Float.parseFloat(valueOf) * 1000.0f));
            } catch (NumberFormatException e3) {
                str = "Could not parse time parameter from currentTime video GMSG: ";
                valueOf = String.valueOf(valueOf);
                zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if ("hide".equals(str)) {
            zzwv.setVisibility(4);
        } else if ("load".equals(str)) {
            zzwv.zznt();
        } else if ("muted".equals(str)) {
            if (Boolean.parseBoolean((String) map.get("muted"))) {
                zzwv.zzqh();
            } else {
                zzwv.zzqi();
            }
        } else if ("pause".equals(str)) {
            zzwv.pause();
        } else if ("play".equals(str)) {
            zzwv.play();
        } else if ("show".equals(str)) {
            zzwv.setVisibility(0);
        } else if ("src".equals(str)) {
            zzwv.zzce((String) map.get("src"));
        } else if ("touchMove".equals(str)) {
            r0 = com_google_android_gms_internal_zzmd.getContext();
            zzwv.zza((float) zza(r0, map, "dx", 0), (float) zza(r0, map, "dy", 0));
            if (!this.zzbqz) {
                com_google_android_gms_internal_zzmd.zzxa().zzpt();
                this.zzbqz = true;
            }
        } else if ("volume".equals(str)) {
            valueOf = (String) map.get("volume");
            if (valueOf == null) {
                zzb.zzdi("Level parameter missing from volume video GMSG.");
                return;
            }
            try {
                zzwv.zzb(Float.parseFloat(valueOf));
            } catch (NumberFormatException e4) {
                str = "Could not parse volume parameter from volume video GMSG: ";
                valueOf = String.valueOf(valueOf);
                zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        } else if ("watermark".equals(str)) {
            zzwv.zzqj();
        } else {
            String str2 = "Unknown video action: ";
            valueOf = String.valueOf(str);
            zzb.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
    }
}
