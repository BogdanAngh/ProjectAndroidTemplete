package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zzd;
import com.google.android.gms.ads.internal.formats.zzi;
import com.google.android.gms.internal.zzjb.zza;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzjd implements zza<zzd> {
    private final boolean zzciy;
    private final boolean zzciz;

    public zzjd(boolean z, boolean z2) {
        this.zzciy = z;
        this.zzciz = z2;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.google.android.gms.internal.zzmd zzb(com.google.android.gms.internal.zzlt<com.google.android.gms.internal.zzmd> r4) {
        /*
        r3 = this;
        r0 = com.google.android.gms.internal.zzdr.zzbje;	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = r0.get();	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = (java.lang.Integer) r0;	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = r0.intValue();	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = (long) r0;	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r2 = java.util.concurrent.TimeUnit.SECONDS;	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = r4.get(r0, r2);	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
        r0 = (com.google.android.gms.internal.zzmd) r0;	 Catch:{ InterruptedException -> 0x0016, ExecutionException -> 0x0025, TimeoutException -> 0x002e, CancellationException -> 0x002c }
    L_0x0015:
        return r0;
    L_0x0016:
        r0 = move-exception;
        r1 = "InterruptedException occurred while waiting for video to load";
        com.google.android.gms.ads.internal.util.client.zzb.zzc(r1, r0);
        r0 = java.lang.Thread.currentThread();
        r0.interrupt();
    L_0x0023:
        r0 = 0;
        goto L_0x0015;
    L_0x0025:
        r0 = move-exception;
    L_0x0026:
        r1 = "Exception occurred while waiting for video to load";
        com.google.android.gms.ads.internal.util.client.zzb.zzc(r1, r0);
        goto L_0x0023;
    L_0x002c:
        r0 = move-exception;
        goto L_0x0026;
    L_0x002e:
        r0 = move-exception;
        goto L_0x0026;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzjd.zzb(com.google.android.gms.internal.zzlt):com.google.android.gms.internal.zzmd");
    }

    public /* synthetic */ zzi.zza zza(zzjb com_google_android_gms_internal_zzjb, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        return zzb(com_google_android_gms_internal_zzjb, jSONObject);
    }

    public zzd zzb(zzjb com_google_android_gms_internal_zzjb, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        List<zzlt> zza = com_google_android_gms_internal_zzjb.zza(jSONObject, "images", true, this.zzciy, this.zzciz);
        Future zza2 = com_google_android_gms_internal_zzjb.zza(jSONObject, "app_icon", true, this.zzciy);
        zzlt zzc = com_google_android_gms_internal_zzjb.zzc(jSONObject, MimeTypes.BASE_TYPE_VIDEO);
        Future zzf = com_google_android_gms_internal_zzjb.zzf(jSONObject);
        List arrayList = new ArrayList();
        for (zzlt com_google_android_gms_internal_zzlt : zza) {
            arrayList.add((zzc) com_google_android_gms_internal_zzlt.get());
        }
        zzmd zzb = zzb(zzc);
        return new zzd(jSONObject.getString("headline"), arrayList, jSONObject.getString(TtmlNode.TAG_BODY), (zzeg) zza2.get(), jSONObject.getString("call_to_action"), jSONObject.optDouble("rating", -1.0d), jSONObject.optString("store"), jSONObject.optString("price"), (com.google.android.gms.ads.internal.formats.zza) zzf.get(), new Bundle(), zzb != null ? zzb.zzxn() : null, zzb != null ? zzb.getView() : null);
    }
}
