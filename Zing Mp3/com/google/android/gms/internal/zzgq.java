package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public final class zzgq {
    public final List<zzgp> zzbvi;
    public final long zzbvj;
    public final List<String> zzbvk;
    public final List<String> zzbvl;
    public final List<String> zzbvm;
    public final List<String> zzbvn;
    public final boolean zzbvo;
    public final String zzbvp;
    public final long zzbvq;
    public final String zzbvr;
    public final int zzbvs;
    public final int zzbvt;
    public final long zzbvu;
    public final boolean zzbvv;
    public int zzbvw;
    public int zzbvx;

    public zzgq(String str) throws JSONException {
        JSONObject jSONObject = new JSONObject(str);
        if (zzb.zzbi(2)) {
            String str2 = "Mediation Response JSON: ";
            String valueOf = String.valueOf(jSONObject.toString(2));
            zzkx.m1697v(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        }
        JSONArray jSONArray = jSONObject.getJSONArray("ad_networks");
        List arrayList = new ArrayList(jSONArray.length());
        int i = -1;
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            zzgp com_google_android_gms_internal_zzgp = new zzgp(jSONArray.getJSONObject(i2));
            arrayList.add(com_google_android_gms_internal_zzgp);
            if (i < 0 && zza(com_google_android_gms_internal_zzgp)) {
                i = i2;
            }
        }
        this.zzbvw = i;
        this.zzbvx = jSONArray.length();
        this.zzbvi = Collections.unmodifiableList(arrayList);
        this.zzbvp = jSONObject.getString("qdata");
        this.zzbvt = jSONObject.optInt("fs_model_type", -1);
        this.zzbvu = jSONObject.optLong("timeout_ms", -1);
        JSONObject optJSONObject = jSONObject.optJSONObject("settings");
        if (optJSONObject != null) {
            this.zzbvj = optJSONObject.optLong("ad_network_timeout_millis", -1);
            this.zzbvk = zzu.zzhf().zza(optJSONObject, "click_urls");
            this.zzbvl = zzu.zzhf().zza(optJSONObject, "imp_urls");
            this.zzbvm = zzu.zzhf().zza(optJSONObject, "nofill_urls");
            this.zzbvn = zzu.zzhf().zza(optJSONObject, "remote_ping_urls");
            this.zzbvo = optJSONObject.optBoolean("render_in_browser", false);
            long optLong = optJSONObject.optLong("refresh", -1);
            this.zzbvq = optLong > 0 ? optLong * 1000 : -1;
            RewardItemParcel zza = RewardItemParcel.zza(optJSONObject.optJSONArray("rewards"));
            if (zza == null) {
                this.zzbvr = null;
                this.zzbvs = 0;
            } else {
                this.zzbvr = zza.type;
                this.zzbvs = zza.zzcsc;
            }
            this.zzbvv = optJSONObject.optBoolean("use_displayed_impression", false);
            return;
        }
        this.zzbvj = -1;
        this.zzbvk = null;
        this.zzbvl = null;
        this.zzbvm = null;
        this.zzbvn = null;
        this.zzbvq = -1;
        this.zzbvr = null;
        this.zzbvs = 0;
        this.zzbvv = false;
        this.zzbvo = false;
    }

    public zzgq(List<zzgp> list, long j, List<String> list2, List<String> list3, List<String> list4, List<String> list5, boolean z, String str, long j2, int i, int i2, String str2, int i3, int i4, long j3, boolean z2) {
        this.zzbvi = list;
        this.zzbvj = j;
        this.zzbvk = list2;
        this.zzbvl = list3;
        this.zzbvm = list4;
        this.zzbvn = list5;
        this.zzbvo = z;
        this.zzbvp = str;
        this.zzbvq = j2;
        this.zzbvw = i;
        this.zzbvx = i2;
        this.zzbvr = str2;
        this.zzbvs = i3;
        this.zzbvt = i4;
        this.zzbvu = j3;
        this.zzbvv = z2;
    }

    private boolean zza(zzgp com_google_android_gms_internal_zzgp) {
        for (String equals : com_google_android_gms_internal_zzgp.zzbuu) {
            if (equals.equals("com.google.ads.mediation.admob.AdMobAdapter")) {
                return true;
            }
        }
        return false;
    }
}
