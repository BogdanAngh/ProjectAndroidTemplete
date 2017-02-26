package com.google.android.gms.internal;

import android.os.Bundle;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zze;
import com.google.android.gms.ads.internal.formats.zzi;
import com.google.android.gms.internal.zzjb.zza;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzje implements zza<zze> {
    private final boolean zzciy;
    private final boolean zzciz;

    public zzje(boolean z, boolean z2) {
        this.zzciy = z;
        this.zzciz = z2;
    }

    public /* synthetic */ zzi.zza zza(zzjb com_google_android_gms_internal_zzjb, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        return zzc(com_google_android_gms_internal_zzjb, jSONObject);
    }

    public zze zzc(zzjb com_google_android_gms_internal_zzjb, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException {
        List<zzlt> zza = com_google_android_gms_internal_zzjb.zza(jSONObject, "images", true, this.zzciy, this.zzciz);
        Future zza2 = com_google_android_gms_internal_zzjb.zza(jSONObject, "secondary_image", false, this.zzciy);
        Future zzf = com_google_android_gms_internal_zzjb.zzf(jSONObject);
        List arrayList = new ArrayList();
        for (zzlt com_google_android_gms_internal_zzlt : zza) {
            arrayList.add((zzc) com_google_android_gms_internal_zzlt.get());
        }
        return new zze(jSONObject.getString("headline"), arrayList, jSONObject.getString(TtmlNode.TAG_BODY), (zzeg) zza2.get(), jSONObject.getString("call_to_action"), jSONObject.getString("advertiser"), (com.google.android.gms.ads.internal.formats.zza) zzf.get(), new Bundle());
    }
}
