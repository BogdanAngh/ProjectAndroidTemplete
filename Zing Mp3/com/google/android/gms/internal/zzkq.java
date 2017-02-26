package com.google.android.gms.internal;

import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.zzu;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzkq {
    private final long zzctg;
    private final List<String> zzcth;
    private final Map<String, zzb> zzcti;
    private String zzctj;
    private String zzctk;
    private boolean zzctl;

    class zza {
        private final List<String> zzctm;
        private final Bundle zzctn;
        final /* synthetic */ zzkq zzcto;

        public zza(zzkq com_google_android_gms_internal_zzkq, List<String> list, Bundle bundle) {
            this.zzcto = com_google_android_gms_internal_zzkq;
            this.zzctm = list;
            this.zzctn = bundle;
        }
    }

    class zzb {
        final /* synthetic */ zzkq zzcto;
        final List<zza> zzctp;

        zzb(zzkq com_google_android_gms_internal_zzkq) {
            this.zzcto = com_google_android_gms_internal_zzkq;
            this.zzctp = new ArrayList();
        }

        public void zza(zza com_google_android_gms_internal_zzkq_zza) {
            this.zzctp.add(com_google_android_gms_internal_zzkq_zza);
        }
    }

    public zzkq(String str, long j) {
        this.zzcth = new ArrayList();
        this.zzcti = new HashMap();
        this.zzctl = false;
        this.zzctk = str;
        this.zzctg = j;
        zzcv(str);
    }

    private void zzcv(String str) {
        if (!TextUtils.isEmpty(str)) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.optInt(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, -1) != 1) {
                    this.zzctl = false;
                    com.google.android.gms.ads.internal.util.client.zzb.zzdi("App settings could not be fetched successfully.");
                    return;
                }
                this.zzctl = true;
                this.zzctj = jSONObject.optString(ServerProtocol.FALLBACK_DIALOG_PARAM_APP_ID);
                JSONArray optJSONArray = jSONObject.optJSONArray("ad_unit_id_settings");
                if (optJSONArray != null) {
                    for (int i = 0; i < optJSONArray.length(); i++) {
                        zzk(optJSONArray.getJSONObject(i));
                    }
                }
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Exception occurred while processing app setting json", e);
                zzu.zzgq().zza(e, "AppSettings.parseAppSettingsJson");
            }
        }
    }

    private void zzk(JSONObject jSONObject) throws JSONException {
        String optString = jSONObject.optString("format");
        CharSequence optString2 = jSONObject.optString("ad_unit_id");
        if (!TextUtils.isEmpty(optString) && !TextUtils.isEmpty(optString2)) {
            if ("interstitial".equalsIgnoreCase(optString)) {
                this.zzcth.add(optString2);
            } else if ("rewarded".equalsIgnoreCase(optString)) {
                JSONObject optJSONObject = jSONObject.optJSONObject("mediation_config");
                if (optJSONObject != null) {
                    JSONArray optJSONArray = optJSONObject.optJSONArray("ad_networks");
                    if (optJSONArray != null) {
                        int i = 0;
                        while (i < optJSONArray.length()) {
                            JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                            JSONArray optJSONArray2 = jSONObject2.optJSONArray("adapters");
                            if (optJSONArray2 != null) {
                                List arrayList = new ArrayList();
                                for (int i2 = 0; i2 < optJSONArray2.length(); i2++) {
                                    arrayList.add(optJSONArray2.getString(i2));
                                }
                                jSONObject2 = jSONObject2.optJSONObject(ShareConstants.WEB_DIALOG_PARAM_DATA);
                                if (jSONObject2 != null) {
                                    Bundle bundle = new Bundle();
                                    Iterator keys = jSONObject2.keys();
                                    while (keys.hasNext()) {
                                        optString = (String) keys.next();
                                        bundle.putString(optString, jSONObject2.getString(optString));
                                    }
                                    zza com_google_android_gms_internal_zzkq_zza = new zza(this, arrayList, bundle);
                                    zzb com_google_android_gms_internal_zzkq_zzb = this.zzcti.containsKey(optString2) ? (zzb) this.zzcti.get(optString2) : new zzb(this);
                                    com_google_android_gms_internal_zzkq_zzb.zza(com_google_android_gms_internal_zzkq_zza);
                                    this.zzcti.put(optString2, com_google_android_gms_internal_zzkq_zzb);
                                    i++;
                                } else {
                                    return;
                                }
                            }
                            return;
                        }
                    }
                }
            }
        }
    }

    public long zzum() {
        return this.zzctg;
    }

    public boolean zzun() {
        return this.zzctl;
    }

    public String zzuo() {
        return this.zzctk;
    }

    public String zzup() {
        return this.zzctj;
    }
}
