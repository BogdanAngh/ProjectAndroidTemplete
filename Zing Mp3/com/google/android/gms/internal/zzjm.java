package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import com.facebook.applinks.AppLinkData;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.SearchAdRequestParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.AutoClickProtectionConfigurationParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.safebrowsing.SafeBrowsingConfigParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzjv.zza;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public final class zzjm {
    private static final SimpleDateFormat zzcny;

    static {
        zzcny = new SimpleDateFormat("yyyyMMdd", Locale.US);
    }

    public static AdResponseParcel zza(Context context, AdRequestInfoParcel adRequestInfoParcel, String str) {
        String optString;
        try {
            String str2;
            JSONObject jSONObject = new JSONObject(str);
            String optString2 = jSONObject.optString("ad_base_url", null);
            Object optString3 = jSONObject.optString("ad_url", null);
            String optString4 = jSONObject.optString("ad_size", null);
            String optString5 = jSONObject.optString("ad_slot_size", optString4);
            boolean z = (adRequestInfoParcel == null || adRequestInfoParcel.zzcka == 0) ? false : true;
            CharSequence optString6 = jSONObject.optString("ad_json", null);
            if (optString6 == null) {
                optString6 = jSONObject.optString("ad_html", null);
            }
            if (optString6 == null) {
                optString6 = jSONObject.optString(TtmlNode.TAG_BODY, null);
            }
            long j = -1;
            String optString7 = jSONObject.optString("debug_dialog", null);
            String optString8 = jSONObject.optString("debug_signals", null);
            long j2 = jSONObject.has("interstitial_timeout") ? (long) (jSONObject.getDouble("interstitial_timeout") * 1000.0d) : -1;
            optString = jSONObject.optString("orientation", null);
            int i = -1;
            if ("portrait".equals(optString)) {
                i = zzu.zzgo().zzvx();
            } else if ("landscape".equals(optString)) {
                i = zzu.zzgo().zzvw();
            }
            AdResponseParcel adResponseParcel = null;
            if (!TextUtils.isEmpty(optString6) || TextUtils.isEmpty(optString3)) {
                CharSequence charSequence = optString6;
            } else {
                adResponseParcel = zzjl.zza(adRequestInfoParcel, context, adRequestInfoParcel.zzari.zzda, optString3, null, null, null, null);
                optString2 = adResponseParcel.zzcbo;
                str2 = adResponseParcel.body;
                j = adResponseParcel.zzclf;
            }
            if (str2 == null) {
                return new AdResponseParcel(0);
            }
            long j3;
            String optString9;
            String str3;
            boolean optBoolean;
            JSONArray optJSONArray = jSONObject.optJSONArray("click_urls");
            List list = adResponseParcel == null ? null : adResponseParcel.zzbvk;
            if (optJSONArray != null) {
                list = zza(optJSONArray, list);
            }
            optJSONArray = jSONObject.optJSONArray("impression_urls");
            List list2 = adResponseParcel == null ? null : adResponseParcel.zzbvl;
            if (optJSONArray != null) {
                list2 = zza(optJSONArray, list2);
            }
            optJSONArray = jSONObject.optJSONArray("manual_impression_urls");
            List list3 = adResponseParcel == null ? null : adResponseParcel.zzcld;
            if (optJSONArray != null) {
                list3 = zza(optJSONArray, list3);
            }
            if (adResponseParcel != null) {
                if (adResponseParcel.orientation != -1) {
                    i = adResponseParcel.orientation;
                }
                if (adResponseParcel.zzcla > 0) {
                    j3 = adResponseParcel.zzcla;
                    optString9 = jSONObject.optString("active_view");
                    str3 = null;
                    optBoolean = jSONObject.optBoolean("ad_is_javascript", false);
                    if (optBoolean) {
                        str3 = jSONObject.optString("ad_passback_url", null);
                    }
                    return new AdResponseParcel(adRequestInfoParcel, optString2, str2, list, list2, j3, jSONObject.optBoolean("mediation", false), jSONObject.optLong("mediation_config_cache_time_milliseconds", -1), list3, jSONObject.optLong("refresh_interval_milliseconds", -1), i, optString4, j, optString7, optBoolean, str3, optString9, jSONObject.optBoolean("custom_render_allowed", false), z, adRequestInfoParcel.zzckc, jSONObject.optBoolean("content_url_opted_out", true), jSONObject.optBoolean("prefetch", false), jSONObject.optString("gws_query_id", BuildConfig.FLAVOR), "height".equals(jSONObject.optString("fluid", BuildConfig.FLAVOR)), jSONObject.optBoolean("native_express", false), RewardItemParcel.zza(jSONObject.optJSONArray("rewards")), zza(jSONObject.optJSONArray("video_start_urls"), null), zza(jSONObject.optJSONArray("video_complete_urls"), null), jSONObject.optBoolean("use_displayed_impression", false), AutoClickProtectionConfigurationParcel.zzh(jSONObject.optJSONObject("auto_protection_configuration")), adRequestInfoParcel.zzcks, jSONObject.optString("set_cookie", BuildConfig.FLAVOR), zza(jSONObject.optJSONArray("remote_ping_urls"), null), jSONObject.optBoolean("render_in_browser", adRequestInfoParcel.zzbvo), optString5, SafeBrowsingConfigParcel.zzj(jSONObject.optJSONObject("safe_browsing")), optString8, jSONObject.optBoolean("content_vertical_opted_out", true));
                }
            }
            j3 = j2;
            optString9 = jSONObject.optString("active_view");
            str3 = null;
            optBoolean = jSONObject.optBoolean("ad_is_javascript", false);
            if (optBoolean) {
                str3 = jSONObject.optString("ad_passback_url", null);
            }
            return new AdResponseParcel(adRequestInfoParcel, optString2, str2, list, list2, j3, jSONObject.optBoolean("mediation", false), jSONObject.optLong("mediation_config_cache_time_milliseconds", -1), list3, jSONObject.optLong("refresh_interval_milliseconds", -1), i, optString4, j, optString7, optBoolean, str3, optString9, jSONObject.optBoolean("custom_render_allowed", false), z, adRequestInfoParcel.zzckc, jSONObject.optBoolean("content_url_opted_out", true), jSONObject.optBoolean("prefetch", false), jSONObject.optString("gws_query_id", BuildConfig.FLAVOR), "height".equals(jSONObject.optString("fluid", BuildConfig.FLAVOR)), jSONObject.optBoolean("native_express", false), RewardItemParcel.zza(jSONObject.optJSONArray("rewards")), zza(jSONObject.optJSONArray("video_start_urls"), null), zza(jSONObject.optJSONArray("video_complete_urls"), null), jSONObject.optBoolean("use_displayed_impression", false), AutoClickProtectionConfigurationParcel.zzh(jSONObject.optJSONObject("auto_protection_configuration")), adRequestInfoParcel.zzcks, jSONObject.optString("set_cookie", BuildConfig.FLAVOR), zza(jSONObject.optJSONArray("remote_ping_urls"), null), jSONObject.optBoolean("render_in_browser", adRequestInfoParcel.zzbvo), optString5, SafeBrowsingConfigParcel.zzj(jSONObject.optJSONObject("safe_browsing")), optString8, jSONObject.optBoolean("content_vertical_opted_out", true));
        } catch (JSONException e) {
            String str4 = "Could not parse the inline ad response: ";
            optString = String.valueOf(e.getMessage());
            zzb.zzdi(optString.length() != 0 ? str4.concat(optString) : new String(str4));
            return new AdResponseParcel(0);
        }
    }

    @Nullable
    private static List<String> zza(@Nullable JSONArray jSONArray, @Nullable List<String> list) throws JSONException {
        if (jSONArray == null) {
            return null;
        }
        if (list == null) {
            list = new LinkedList();
        }
        for (int i = 0; i < jSONArray.length(); i++) {
            list.add(jSONArray.getString(i));
        }
        return list;
    }

    @Nullable
    public static JSONObject zza(Context context, zzjj com_google_android_gms_internal_zzjj) {
        Object obj;
        String str;
        String valueOf;
        AdRequestInfoParcel adRequestInfoParcel = com_google_android_gms_internal_zzjj.zzcmx;
        Location location = com_google_android_gms_internal_zzjj.zzayt;
        zzjr com_google_android_gms_internal_zzjr = com_google_android_gms_internal_zzjj.zzcmy;
        Bundle bundle = com_google_android_gms_internal_zzjj.zzckb;
        JSONObject jSONObject = com_google_android_gms_internal_zzjj.zzcmz;
        HashMap hashMap = new HashMap();
        hashMap.put("extra_caps", zzdr.zzbit.get());
        if (com_google_android_gms_internal_zzjj.zzckj.size() > 0) {
            hashMap.put("eid", TextUtils.join(",", com_google_android_gms_internal_zzjj.zzckj));
        }
        if (adRequestInfoParcel.zzcjt != null) {
            hashMap.put("ad_pos", adRequestInfoParcel.zzcjt);
        }
        zza(hashMap, adRequestInfoParcel.zzcju);
        if (adRequestInfoParcel.zzarm.zzazs != null) {
            obj = null;
            Object obj2 = null;
            for (AdSizeParcel adSizeParcel : adRequestInfoParcel.zzarm.zzazs) {
                if (!adSizeParcel.zzazu && r3 == null) {
                    hashMap.put("format", adSizeParcel.zzazq);
                    obj2 = 1;
                }
                if (adSizeParcel.zzazu && r2 == null) {
                    hashMap.put("fluid", "height");
                    obj = 1;
                }
                if (obj2 != null && r2 != null) {
                    break;
                }
            }
        } else {
            hashMap.put("format", adRequestInfoParcel.zzarm.zzazq);
            if (adRequestInfoParcel.zzarm.zzazu) {
                hashMap.put("fluid", "height");
            }
        }
        if (adRequestInfoParcel.zzarm.width == -1) {
            hashMap.put("smart_w", "full");
        }
        if (adRequestInfoParcel.zzarm.height == -2) {
            hashMap.put("smart_h", "auto");
        }
        if (adRequestInfoParcel.zzarm.zzazs != null) {
            StringBuilder stringBuilder = new StringBuilder();
            obj = null;
            for (AdSizeParcel adSizeParcel2 : adRequestInfoParcel.zzarm.zzazs) {
                if (adSizeParcel2.zzazu) {
                    obj = 1;
                } else {
                    int i;
                    if (stringBuilder.length() != 0) {
                        stringBuilder.append("|");
                    }
                    if (adSizeParcel2.width == -1) {
                        i = (int) (((float) adSizeParcel2.widthPixels) / com_google_android_gms_internal_zzjr.zzavd);
                    } else {
                        try {
                            i = adSizeParcel2.width;
                        } catch (JSONException e) {
                            str = "Problem serializing ad request to JSON: ";
                            valueOf = String.valueOf(e.getMessage());
                            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                            return null;
                        }
                    }
                    stringBuilder.append(i);
                    stringBuilder.append("x");
                    stringBuilder.append(adSizeParcel2.height == -2 ? (int) (((float) adSizeParcel2.heightPixels) / com_google_android_gms_internal_zzjr.zzavd) : adSizeParcel2.height);
                }
            }
            if (obj != null) {
                if (stringBuilder.length() != 0) {
                    stringBuilder.insert(0, "|");
                }
                stringBuilder.insert(0, "320x50");
            }
            hashMap.put("sz", stringBuilder);
        }
        if (adRequestInfoParcel.zzcka != 0) {
            hashMap.put("native_version", Integer.valueOf(adRequestInfoParcel.zzcka));
            hashMap.put("native_templates", adRequestInfoParcel.zzase);
            hashMap.put("native_image_orientation", zzc(adRequestInfoParcel.zzasa));
            if (!adRequestInfoParcel.zzckk.isEmpty()) {
                hashMap.put("native_custom_templates", adRequestInfoParcel.zzckk);
            }
        }
        if (adRequestInfoParcel.zzarm.zzazv) {
            hashMap.put("ene", Boolean.valueOf(true));
        }
        hashMap.put("slotname", adRequestInfoParcel.zzarg);
        hashMap.put("pn", adRequestInfoParcel.applicationInfo.packageName);
        if (adRequestInfoParcel.zzcjv != null) {
            hashMap.put("vc", Integer.valueOf(adRequestInfoParcel.zzcjv.versionCode));
        }
        hashMap.put("ms", com_google_android_gms_internal_zzjj.zzcjw);
        hashMap.put("seq_num", adRequestInfoParcel.zzcjx);
        hashMap.put("session_id", adRequestInfoParcel.zzcjy);
        hashMap.put("js", adRequestInfoParcel.zzari.zzda);
        zza(hashMap, com_google_android_gms_internal_zzjr, com_google_android_gms_internal_zzjj.zzcmv, adRequestInfoParcel.zzckx, com_google_android_gms_internal_zzjj.zzcmu);
        zza(hashMap, com_google_android_gms_internal_zzjj.zzcmw);
        hashMap.put("platform", Build.MANUFACTURER);
        hashMap.put("submodel", Build.MODEL);
        if (location != null) {
            zza(hashMap, location);
        } else if (adRequestInfoParcel.zzcju.versionCode >= 2 && adRequestInfoParcel.zzcju.zzayt != null) {
            zza(hashMap, adRequestInfoParcel.zzcju.zzayt);
        }
        if (adRequestInfoParcel.versionCode >= 2) {
            hashMap.put("quality_signals", adRequestInfoParcel.zzcjz);
        }
        if (adRequestInfoParcel.versionCode >= 4 && adRequestInfoParcel.zzckc) {
            hashMap.put("forceHttps", Boolean.valueOf(adRequestInfoParcel.zzckc));
        }
        if (bundle != null) {
            hashMap.put("content_info", bundle);
        }
        if (adRequestInfoParcel.versionCode >= 5) {
            hashMap.put("u_sd", Float.valueOf(adRequestInfoParcel.zzavd));
            hashMap.put("sh", Integer.valueOf(adRequestInfoParcel.zzckf));
            hashMap.put("sw", Integer.valueOf(adRequestInfoParcel.zzcke));
        } else {
            hashMap.put("u_sd", Float.valueOf(com_google_android_gms_internal_zzjr.zzavd));
            hashMap.put("sh", Integer.valueOf(com_google_android_gms_internal_zzjr.zzckf));
            hashMap.put("sw", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcke));
        }
        if (adRequestInfoParcel.versionCode >= 6) {
            if (!TextUtils.isEmpty(adRequestInfoParcel.zzckg)) {
                try {
                    hashMap.put("view_hierarchy", new JSONObject(adRequestInfoParcel.zzckg));
                } catch (Throwable e2) {
                    zzb.zzc("Problem serializing view hierarchy to JSON", e2);
                }
            }
            hashMap.put("correlation_id", Long.valueOf(adRequestInfoParcel.zzckh));
        }
        if (adRequestInfoParcel.versionCode >= 7) {
            hashMap.put("request_id", adRequestInfoParcel.zzcki);
        }
        if (adRequestInfoParcel.versionCode >= 11 && adRequestInfoParcel.zzckm != null) {
            hashMap.put("capability", adRequestInfoParcel.zzckm.toBundle());
        }
        if (adRequestInfoParcel.versionCode >= 12 && !TextUtils.isEmpty(adRequestInfoParcel.zzckn)) {
            hashMap.put("anchor", adRequestInfoParcel.zzckn);
        }
        if (adRequestInfoParcel.versionCode >= 13) {
            hashMap.put("android_app_volume", Float.valueOf(adRequestInfoParcel.zzcko));
        }
        if (adRequestInfoParcel.versionCode >= 18) {
            hashMap.put("android_app_muted", Boolean.valueOf(adRequestInfoParcel.zzcku));
        }
        if (adRequestInfoParcel.versionCode >= 14 && adRequestInfoParcel.zzckp > 0) {
            hashMap.put("target_api", Integer.valueOf(adRequestInfoParcel.zzckp));
        }
        if (adRequestInfoParcel.versionCode >= 15) {
            hashMap.put("scroll_index", Integer.valueOf(adRequestInfoParcel.zzckq == -1 ? -1 : adRequestInfoParcel.zzckq));
        }
        if (adRequestInfoParcel.versionCode >= 16) {
            hashMap.put("_activity_context", Boolean.valueOf(adRequestInfoParcel.zzckr));
        }
        if (adRequestInfoParcel.versionCode >= 18) {
            if (!TextUtils.isEmpty(adRequestInfoParcel.zzckv)) {
                try {
                    hashMap.put("app_settings", new JSONObject(adRequestInfoParcel.zzckv));
                } catch (Throwable e22) {
                    zzb.zzc("Problem creating json from app settings", e22);
                }
            }
            hashMap.put("render_in_browser", Boolean.valueOf(adRequestInfoParcel.zzbvo));
        }
        if (adRequestInfoParcel.versionCode >= 18) {
            hashMap.put("android_num_video_cache_tasks", Integer.valueOf(adRequestInfoParcel.zzckw));
        }
        zza(hashMap);
        hashMap.put("cache_state", jSONObject);
        if (adRequestInfoParcel.versionCode >= 19) {
            hashMap.put("gct", adRequestInfoParcel.zzcky);
        }
        if (zzb.zzbi(2)) {
            str = "Ad Request JSON: ";
            valueOf = String.valueOf(zzu.zzgm().zzap(hashMap).toString(2));
            zzkx.m1697v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        return zzu.zzgm().zzap(hashMap);
    }

    private static void zza(HashMap<String, Object> hashMap) {
        Bundle bundle = new Bundle();
        Bundle bundle2 = new Bundle();
        bundle2.putString("cl", "135396225");
        bundle2.putString("rapid_rc", "dev");
        bundle2.putString("rapid_rollup", "HEAD");
        bundle.putBundle("build_meta", bundle2);
        bundle.putString("mf", Boolean.toString(((Boolean) zzdr.zzbiv.get()).booleanValue()));
        hashMap.put("sdk_env", bundle);
    }

    private static void zza(HashMap<String, Object> hashMap, Location location) {
        HashMap hashMap2 = new HashMap();
        Float valueOf = Float.valueOf(location.getAccuracy() * 1000.0f);
        Long valueOf2 = Long.valueOf(location.getTime() * 1000);
        Long valueOf3 = Long.valueOf((long) (location.getLatitude() * 1.0E7d));
        Long valueOf4 = Long.valueOf((long) (location.getLongitude() * 1.0E7d));
        hashMap2.put("radius", valueOf);
        hashMap2.put("lat", valueOf3);
        hashMap2.put("long", valueOf4);
        hashMap2.put("time", valueOf2);
        hashMap.put("uule", hashMap2);
    }

    private static void zza(HashMap<String, Object> hashMap, AdRequestParcel adRequestParcel) {
        String zzvl = zzkv.zzvl();
        if (zzvl != null) {
            hashMap.put("abf", zzvl);
        }
        if (adRequestParcel.zzayl != -1) {
            hashMap.put("cust_age", zzcny.format(new Date(adRequestParcel.zzayl)));
        }
        if (adRequestParcel.extras != null) {
            hashMap.put(AppLinkData.ARGUMENTS_EXTRAS_KEY, adRequestParcel.extras);
        }
        if (adRequestParcel.zzaym != -1) {
            hashMap.put("cust_gender", Integer.valueOf(adRequestParcel.zzaym));
        }
        if (adRequestParcel.zzayn != null) {
            hashMap.put("kw", adRequestParcel.zzayn);
        }
        if (adRequestParcel.zzayp != -1) {
            hashMap.put("tag_for_child_directed_treatment", Integer.valueOf(adRequestParcel.zzayp));
        }
        if (adRequestParcel.zzayo) {
            hashMap.put("adtest", "on");
        }
        if (adRequestParcel.versionCode >= 2) {
            if (adRequestParcel.zzayq) {
                hashMap.put("d_imp_hdr", Integer.valueOf(1));
            }
            if (!TextUtils.isEmpty(adRequestParcel.zzayr)) {
                hashMap.put("ppid", adRequestParcel.zzayr);
            }
            if (adRequestParcel.zzays != null) {
                zza((HashMap) hashMap, adRequestParcel.zzays);
            }
        }
        if (adRequestParcel.versionCode >= 3 && adRequestParcel.zzayu != null) {
            hashMap.put(NativeProtocol.WEB_DIALOG_URL, adRequestParcel.zzayu);
        }
        if (adRequestParcel.versionCode >= 5) {
            if (adRequestParcel.zzayw != null) {
                hashMap.put("custom_targeting", adRequestParcel.zzayw);
            }
            if (adRequestParcel.zzayx != null) {
                hashMap.put("category_exclusions", adRequestParcel.zzayx);
            }
            if (adRequestParcel.zzayy != null) {
                hashMap.put("request_agent", adRequestParcel.zzayy);
            }
        }
        if (adRequestParcel.versionCode >= 6 && adRequestParcel.zzayz != null) {
            hashMap.put("request_pkg", adRequestParcel.zzayz);
        }
        if (adRequestParcel.versionCode >= 7) {
            hashMap.put("is_designed_for_families", Boolean.valueOf(adRequestParcel.zzaza));
        }
    }

    private static void zza(HashMap<String, Object> hashMap, SearchAdRequestParcel searchAdRequestParcel) {
        Object obj;
        Object obj2 = null;
        if (Color.alpha(searchAdRequestParcel.zzbbx) != 0) {
            hashMap.put("acolor", zzaz(searchAdRequestParcel.zzbbx));
        }
        if (Color.alpha(searchAdRequestParcel.backgroundColor) != 0) {
            hashMap.put("bgcolor", zzaz(searchAdRequestParcel.backgroundColor));
        }
        if (!(Color.alpha(searchAdRequestParcel.zzbby) == 0 || Color.alpha(searchAdRequestParcel.zzbbz) == 0)) {
            hashMap.put("gradientto", zzaz(searchAdRequestParcel.zzbby));
            hashMap.put("gradientfrom", zzaz(searchAdRequestParcel.zzbbz));
        }
        if (Color.alpha(searchAdRequestParcel.zzbca) != 0) {
            hashMap.put("bcolor", zzaz(searchAdRequestParcel.zzbca));
        }
        hashMap.put("bthick", Integer.toString(searchAdRequestParcel.zzbcb));
        switch (searchAdRequestParcel.zzbcc) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                obj = "none";
                break;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                obj = "dashed";
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                obj = "dotted";
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                obj = "solid";
                break;
            default:
                obj = null;
                break;
        }
        if (obj != null) {
            hashMap.put("btype", obj);
        }
        switch (searchAdRequestParcel.zzbcd) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                obj2 = "light";
                break;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                obj2 = "medium";
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                obj2 = "dark";
                break;
        }
        if (obj2 != null) {
            hashMap.put("callbuttoncolor", obj2);
        }
        if (searchAdRequestParcel.zzbce != null) {
            hashMap.put("channel", searchAdRequestParcel.zzbce);
        }
        if (Color.alpha(searchAdRequestParcel.zzbcf) != 0) {
            hashMap.put("dcolor", zzaz(searchAdRequestParcel.zzbcf));
        }
        if (searchAdRequestParcel.zzbcg != null) {
            hashMap.put("font", searchAdRequestParcel.zzbcg);
        }
        if (Color.alpha(searchAdRequestParcel.zzbch) != 0) {
            hashMap.put("hcolor", zzaz(searchAdRequestParcel.zzbch));
        }
        hashMap.put("headersize", Integer.toString(searchAdRequestParcel.zzbci));
        if (searchAdRequestParcel.zzbcj != null) {
            hashMap.put("q", searchAdRequestParcel.zzbcj);
        }
    }

    private static void zza(HashMap<String, Object> hashMap, zzjr com_google_android_gms_internal_zzjr, zza com_google_android_gms_internal_zzjv_zza, Bundle bundle, Bundle bundle2) {
        hashMap.put("am", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcps));
        hashMap.put("cog", zzac(com_google_android_gms_internal_zzjr.zzcpt));
        hashMap.put("coh", zzac(com_google_android_gms_internal_zzjr.zzcpu));
        if (!TextUtils.isEmpty(com_google_android_gms_internal_zzjr.zzcpv)) {
            hashMap.put("carrier", com_google_android_gms_internal_zzjr.zzcpv);
        }
        hashMap.put("gl", com_google_android_gms_internal_zzjr.zzcpw);
        if (com_google_android_gms_internal_zzjr.zzcpx) {
            hashMap.put("simulator", Integer.valueOf(1));
        }
        if (com_google_android_gms_internal_zzjr.zzcpy) {
            hashMap.put("is_sidewinder", Integer.valueOf(1));
        }
        hashMap.put("ma", zzac(com_google_android_gms_internal_zzjr.zzcpz));
        hashMap.put("sp", zzac(com_google_android_gms_internal_zzjr.zzcqa));
        hashMap.put("hl", com_google_android_gms_internal_zzjr.zzcqb);
        if (!TextUtils.isEmpty(com_google_android_gms_internal_zzjr.zzcqc)) {
            hashMap.put("mv", com_google_android_gms_internal_zzjr.zzcqc);
        }
        hashMap.put("muv", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqd));
        if (com_google_android_gms_internal_zzjr.zzcqe != -2) {
            hashMap.put("cnt", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqe));
        }
        hashMap.put("gnt", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqf));
        hashMap.put("pt", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqg));
        hashMap.put("rm", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqh));
        hashMap.put("riv", Integer.valueOf(com_google_android_gms_internal_zzjr.zzcqi));
        Bundle bundle3 = new Bundle();
        bundle3.putString("build", com_google_android_gms_internal_zzjr.zzcqn);
        Bundle bundle4 = new Bundle();
        bundle4.putBoolean("is_charging", com_google_android_gms_internal_zzjr.zzcqk);
        bundle4.putDouble("battery_level", com_google_android_gms_internal_zzjr.zzcqj);
        bundle3.putBundle("battery", bundle4);
        bundle4 = new Bundle();
        bundle4.putInt("active_network_state", com_google_android_gms_internal_zzjr.zzcqm);
        bundle4.putBoolean("active_network_metered", com_google_android_gms_internal_zzjr.zzcql);
        if (com_google_android_gms_internal_zzjv_zza != null) {
            Bundle bundle5 = new Bundle();
            bundle5.putInt("predicted_latency_micros", com_google_android_gms_internal_zzjv_zza.zzcqt);
            bundle5.putLong("predicted_down_throughput_bps", com_google_android_gms_internal_zzjv_zza.zzcqu);
            bundle5.putLong("predicted_up_throughput_bps", com_google_android_gms_internal_zzjv_zza.zzcqv);
            bundle4.putBundle("predictions", bundle5);
        }
        bundle3.putBundle("network", bundle4);
        bundle4 = new Bundle();
        bundle4.putBoolean("is_browser_custom_tabs_capable", com_google_android_gms_internal_zzjr.zzcqo);
        bundle3.putBundle("browser", bundle4);
        if (bundle != null) {
            bundle3.putBundle("android_mem_info", zzg(bundle));
        }
        bundle4 = new Bundle();
        bundle4.putBundle("parental_controls", bundle2);
        bundle3.putBundle("play_store", bundle4);
        hashMap.put("device", bundle3);
    }

    private static void zza(HashMap<String, Object> hashMap, String str) {
        Bundle bundle = new Bundle();
        bundle.putString("doritos", str);
        hashMap.put("pii", bundle);
    }

    private static Integer zzac(boolean z) {
        return Integer.valueOf(z ? 1 : 0);
    }

    private static String zzaz(int i) {
        return String.format(Locale.US, "#%06x", new Object[]{Integer.valueOf(ViewCompat.MEASURED_SIZE_MASK & i)});
    }

    private static String zzc(NativeAdOptionsParcel nativeAdOptionsParcel) {
        switch (nativeAdOptionsParcel != null ? nativeAdOptionsParcel.zzbok : 0) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return "portrait";
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return "landscape";
            default:
                return "any";
        }
    }

    public static JSONObject zzc(AdResponseParcel adResponseParcel) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (adResponseParcel.zzcbo != null) {
            jSONObject.put("ad_base_url", adResponseParcel.zzcbo);
        }
        if (adResponseParcel.zzcle != null) {
            jSONObject.put("ad_size", adResponseParcel.zzcle);
        }
        jSONObject.put(AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE, adResponseParcel.zzazt);
        if (adResponseParcel.zzazt) {
            jSONObject.put("ad_json", adResponseParcel.body);
        } else {
            jSONObject.put("ad_html", adResponseParcel.body);
        }
        if (adResponseParcel.zzclg != null) {
            jSONObject.put("debug_dialog", adResponseParcel.zzclg);
        }
        if (adResponseParcel.zzclx != null) {
            jSONObject.put("debug_signals", adResponseParcel.zzclx);
        }
        if (adResponseParcel.zzcla != -1) {
            jSONObject.put("interstitial_timeout", ((double) adResponseParcel.zzcla) / 1000.0d);
        }
        if (adResponseParcel.orientation == zzu.zzgo().zzvx()) {
            jSONObject.put("orientation", "portrait");
        } else if (adResponseParcel.orientation == zzu.zzgo().zzvw()) {
            jSONObject.put("orientation", "landscape");
        }
        if (adResponseParcel.zzbvk != null) {
            jSONObject.put("click_urls", zzl(adResponseParcel.zzbvk));
        }
        if (adResponseParcel.zzbvl != null) {
            jSONObject.put("impression_urls", zzl(adResponseParcel.zzbvl));
        }
        if (adResponseParcel.zzcld != null) {
            jSONObject.put("manual_impression_urls", zzl(adResponseParcel.zzcld));
        }
        if (adResponseParcel.zzclj != null) {
            jSONObject.put("active_view", adResponseParcel.zzclj);
        }
        jSONObject.put("ad_is_javascript", adResponseParcel.zzclh);
        if (adResponseParcel.zzcli != null) {
            jSONObject.put("ad_passback_url", adResponseParcel.zzcli);
        }
        jSONObject.put("mediation", adResponseParcel.zzclb);
        jSONObject.put("custom_render_allowed", adResponseParcel.zzclk);
        jSONObject.put("content_url_opted_out", adResponseParcel.zzcll);
        jSONObject.put("content_vertical_opted_out", adResponseParcel.zzcly);
        jSONObject.put("prefetch", adResponseParcel.zzclm);
        if (adResponseParcel.zzbvq != -1) {
            jSONObject.put("refresh_interval_milliseconds", adResponseParcel.zzbvq);
        }
        if (adResponseParcel.zzclc != -1) {
            jSONObject.put("mediation_config_cache_time_milliseconds", adResponseParcel.zzclc);
        }
        if (!TextUtils.isEmpty(adResponseParcel.zzclp)) {
            jSONObject.put("gws_query_id", adResponseParcel.zzclp);
        }
        jSONObject.put("fluid", adResponseParcel.zzazu ? "height" : BuildConfig.FLAVOR);
        jSONObject.put("native_express", adResponseParcel.zzazv);
        if (adResponseParcel.zzclr != null) {
            jSONObject.put("video_start_urls", zzl(adResponseParcel.zzclr));
        }
        if (adResponseParcel.zzcls != null) {
            jSONObject.put("video_complete_urls", zzl(adResponseParcel.zzcls));
        }
        if (adResponseParcel.zzclq != null) {
            jSONObject.put("rewards", adResponseParcel.zzclq.zzue());
        }
        jSONObject.put("use_displayed_impression", adResponseParcel.zzclt);
        jSONObject.put("auto_protection_configuration", adResponseParcel.zzclu);
        jSONObject.put("render_in_browser", adResponseParcel.zzbvo);
        return jSONObject;
    }

    private static Bundle zzg(Bundle bundle) {
        Bundle bundle2 = new Bundle();
        bundle2.putString("runtime_free", Long.toString(bundle.getLong("runtime_free_memory", -1)));
        bundle2.putString("runtime_max", Long.toString(bundle.getLong("runtime_max_memory", -1)));
        bundle2.putString("runtime_total", Long.toString(bundle.getLong("runtime_total_memory", -1)));
        MemoryInfo memoryInfo = (MemoryInfo) bundle.getParcelable("debug_memory_info");
        if (memoryInfo != null) {
            bundle2.putString("debug_info_dalvik_private_dirty", Integer.toString(memoryInfo.dalvikPrivateDirty));
            bundle2.putString("debug_info_dalvik_pss", Integer.toString(memoryInfo.dalvikPss));
            bundle2.putString("debug_info_dalvik_shared_dirty", Integer.toString(memoryInfo.dalvikSharedDirty));
            bundle2.putString("debug_info_native_private_dirty", Integer.toString(memoryInfo.nativePrivateDirty));
            bundle2.putString("debug_info_native_pss", Integer.toString(memoryInfo.nativePss));
            bundle2.putString("debug_info_native_shared_dirty", Integer.toString(memoryInfo.nativeSharedDirty));
            bundle2.putString("debug_info_other_private_dirty", Integer.toString(memoryInfo.otherPrivateDirty));
            bundle2.putString("debug_info_other_pss", Integer.toString(memoryInfo.otherPss));
            bundle2.putString("debug_info_other_shared_dirty", Integer.toString(memoryInfo.otherSharedDirty));
        }
        return bundle2;
    }

    @Nullable
    static JSONArray zzl(List<String> list) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        for (String put : list) {
            jSONArray.put(put);
        }
        return jSONArray;
    }
}
