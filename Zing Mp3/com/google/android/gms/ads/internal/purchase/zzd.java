package com.google.android.gms.ads.internal.purchase;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.SystemClock;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzif.zza;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@zzji
public class zzd extends zza {
    private Context mContext;
    private String zzasx;
    private String zzcfm;
    private ArrayList<String> zzcfn;

    public zzd(String str, ArrayList<String> arrayList, Context context, String str2) {
        this.zzcfm = str;
        this.zzcfn = arrayList;
        this.zzasx = str2;
        this.mContext = context;
    }

    public String getProductId() {
        return this.zzcfm;
    }

    public void recordPlayBillingResolution(int i) {
        if (i == 0) {
            zzrt();
        }
        Map zzrs = zzrs();
        zzrs.put("google_play_status", String.valueOf(i));
        zzrs.put("sku", this.zzcfm);
        zzrs.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, String.valueOf(zzan(i)));
        List linkedList = new LinkedList();
        Iterator it = this.zzcfn.iterator();
        while (it.hasNext()) {
            linkedList.add(zzu.zzgm().zzc((String) it.next(), zzrs));
        }
        zzu.zzgm().zza(this.mContext, this.zzasx, linkedList);
    }

    public void recordResolution(int i) {
        if (i == 1) {
            zzrt();
        }
        Map zzrs = zzrs();
        zzrs.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, String.valueOf(i));
        zzrs.put("sku", this.zzcfm);
        List linkedList = new LinkedList();
        Iterator it = this.zzcfn.iterator();
        while (it.hasNext()) {
            linkedList.add(zzu.zzgm().zzc((String) it.next(), zzrs));
        }
        zzu.zzgm().zza(this.mContext, this.zzasx, linkedList);
    }

    protected int zzan(int i) {
        return i == 0 ? 1 : i == 1 ? 2 : i == 4 ? 3 : 0;
    }

    Map<String, String> zzrs() {
        String packageName = this.mContext.getPackageName();
        Object obj = BuildConfig.FLAVOR;
        try {
            obj = this.mContext.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (Throwable e) {
            zzb.zzc("Error to retrieve app version", e);
        }
        long elapsedRealtime = SystemClock.elapsedRealtime() - zzu.zzgq().zzut().zzvk();
        Map<String, String> hashMap = new HashMap();
        hashMap.put("sessionid", zzu.zzgq().getSessionId());
        hashMap.put("appid", packageName);
        hashMap.put("osversion", String.valueOf(VERSION.SDK_INT));
        hashMap.put("sdkversion", this.zzasx);
        hashMap.put("appversion", obj);
        hashMap.put("timestamp", String.valueOf(elapsedRealtime));
        return hashMap;
    }

    void zzrt() {
        try {
            this.mContext.getClassLoader().loadClass("com.google.ads.conversiontracking.IAPConversionReporter").getDeclaredMethod("reportWithProductId", new Class[]{Context.class, String.class, String.class, Boolean.TYPE}).invoke(null, new Object[]{this.mContext, this.zzcfm, BuildConfig.FLAVOR, Boolean.valueOf(true)});
        } catch (ClassNotFoundException e) {
            zzb.zzdi("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (NoSuchMethodException e2) {
            zzb.zzdi("Google Conversion Tracking SDK 1.2.0 or above is required to report a conversion.");
        } catch (Throwable e3) {
            zzb.zzc("Fail to report a conversion.", e3);
        }
    }
}
