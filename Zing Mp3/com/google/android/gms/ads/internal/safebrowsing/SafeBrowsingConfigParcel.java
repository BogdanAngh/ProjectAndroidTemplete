package com.google.android.gms.ads.internal.safebrowsing;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class SafeBrowsingConfigParcel extends AbstractSafeParcelable {
    public static final Creator<SafeBrowsingConfigParcel> CREATOR;
    public final int versionCode;
    public final String zzcsd;
    public final String zzcse;
    public final boolean zzcsf;
    public final boolean zzcsg;
    public final List<String> zzcsh;

    static {
        CREATOR = new zzb();
    }

    public SafeBrowsingConfigParcel(int i, String str, String str2, boolean z, boolean z2, List<String> list) {
        this.versionCode = i;
        this.zzcsd = str;
        this.zzcse = str2;
        this.zzcsf = z;
        this.zzcsg = z2;
        this.zzcsh = list;
    }

    @Nullable
    public static SafeBrowsingConfigParcel zzj(JSONObject jSONObject) throws JSONException {
        int i = 0;
        if (jSONObject == null) {
            return null;
        }
        String optString = jSONObject.optString("click_string", BuildConfig.FLAVOR);
        String optString2 = jSONObject.optString("report_url", BuildConfig.FLAVOR);
        boolean optBoolean = jSONObject.optBoolean("rendered_ad_enabled", false);
        boolean optBoolean2 = jSONObject.optBoolean("non_malicious_reporting_enabled", false);
        JSONArray optJSONArray = jSONObject.optJSONArray("allowed_headers");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        List arrayList = new ArrayList();
        while (i < optJSONArray.length()) {
            Object optString3 = optJSONArray.optString(i);
            if (!TextUtils.isEmpty(optString3)) {
                arrayList.add(optString3.toLowerCase(Locale.ENGLISH));
            }
            i++;
        }
        return new SafeBrowsingConfigParcel(2, optString, optString2, optBoolean, optBoolean2, arrayList);
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzb.zza(this, parcel, i);
    }
}
